# OJ系统后端开发日志-JAVA

## Day-01



### Excel文件上传



#### 导入相关工具依赖

##### hutool

```xml
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.7.3</version>
</dependency>
```



#### Excel文件上传-vue

```html
<el-upload
        action="http://localhost:9090/file/upload"
        multiple
        ref="upload"
        accept=".xls,.xlsx"
        :on-success="fileUploadSuccess"
>
    <el-button size="small" type="primary" style="margin-top: 20px">点击上传</el-button>
    <template #tip>
        <div class="el-upload__tip">只能上传 xls或xlsx 文件</div>
    </template>
</el-upload>
```



#### Excel文件下载-vue

```html
<div>
    使用 Excel 表格批量预录入学生信息 <br>
    上传 .xlsx 表格文件，每一行为一个学生信息，支持多次上传文件，<el-link type="primary" href="http://localhost:9090/file/download">点击下载模板</el-link>
</div>
```



#### Excel文件上传与下载-java

```java
@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";

    /**
     * 下载模板
     */
    @GetMapping("/download")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        String basePath = System.getProperty("user.dir") + "/src/main/resources/excelTemplates/";
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("模板.xlsx", "utf-8"));
        byte[] bytes = FileUtil.readBytes(basePath + "模板.xlsx");//通过文件路径读取文件字节流
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
    
    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();//获取上传文件名称
        //定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        //上传文件的绝对路径
        String rootFilePath = System.getProperty("user.dir") + "/src/main/resources/excelTemplates/" + flag + "_" + originalFilename;
        FileUtil.writeBytes(file.getBytes(),rootFilePath);//把文件写入上传的路径
        return Result.success(ip + ":" + port + "/file/" + flag);//返回结果 url
    }
}
```





### java对Excel文件的读操作



#### EasyExcel

使用EasyExcel对Excel文件进行操作

github地址:https://github.com/alibaba/easyexcel

文档地址：[EasyExcel · 语雀 (yuque.com)](https://www.yuque.com/easyexcel/doc/easyexcel)



##### 导入依赖

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>2.2.6</version>
</dependency>
```



##### 使用监听器

```java
public class StudentDataListener extends AnalysisEventListener<StudentExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;
    List<StudentExcel> list = new ArrayList<StudentExcel>();
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private StudentExcelService studentExcelService;
    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param studentExcelService
     */
    public StudentDataListener(StudentExcelService studentExcelService) {
        this.studentExcelService = studentExcelService;
    }


    @Override
    public void invoke(StudentExcel data, AnalysisContext analysisContext) {
        data.setUuid(IdUtil.fastSimpleUUID());
        data.setUsername(data.getNumber());
        data.setPassword(data.getNumber());
        list.add(data);
        System.out.println(list);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        studentExcelService.saveBatch(list);
    }
}
```



##### 读取Excel并添加到数据库

加在上面Excel文件上传的接口里

```java
// 有个很重要的点 StudentDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
// 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
EasyExcel.read(rootFilePath, StudentExcel.class, new StudentDataListener(studentExcelService)).sheet().doRead();
```



## Day-02



### 学生信息查询功能

（条件查询、分页）

#### studentController

```java
//分页条件查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam String courseUuid){
        return Result.success(studentService.findStudentByCourseByPage(new Page<Integer>(pageNum,pageSize), courseUuid,search));
    }
```



#### studentMapper

```java
//分页查询该课程下的所有学生
IPage<Student> findStudentByCourseByPage(Page<?> page, @Param("courseUuid") String courseUuid, @Param("search") String search);
```



#### studentMapper.xml

```xml
<select id="findStudentByCourseByPage" resultType="Student">
    select * from student s,student_course sc
    where #{courseUuid} = sc.course_uuid
    and sc.student_uuid = s.uuid
    and s.name like "%"#{search}"%"
</select>
```



### 学生信息添加功能



#### 单条数据的添加

##### studentController

```java
 //储存一条学生数据
    @PostMapping
    public Result<?> saveOne(@RequestBody Map<String ,String> map){

        Student student = new Student();
        student.setUuid(IdUtil.simpleUUID());
        student.setNumber(map.get("number"));
        student.setGender(map.get("gender"));
        student.setSchool(map.get("school"));
        student.setName(map.get("name"));
        student.setMajor(map.get("major"));
        student.setStudentClass(map.get("studentClass"));
        student.setUsername(student.getNumber());
        student.setPassword(student.getNumber());
        studentService.save(student);

        StudentCourse studentCourse = new StudentCourse(student.getUuid(),map.get("courseUuid"));
        studentCourseService.save(studentCourse);
        return Result.success();
    }
```



#### Excel导入，批量添加

##### studentController

```java
//从student_excel表中获取所有数据添加到student表中，并将学生添加到对应课程里teacher_course表中，最后清空student_excel表
@PostMapping("/save")
public Result<?> saveStudentToCourse(@RequestBody String courseUuid){
    List<StudentCourse> list = new ArrayList<StudentCourse>();
    List<StudentExcel> se_list = studentExcelService.list();
    for (StudentExcel s: se_list) {
        list.add(new StudentCourse(s.getUuid(),courseUuid));//创建StudentCourse对象并加入到一个集合中
    }
    studentService.copy();//复制数据
    studentCourseService.saveBatch(list);//批量加入数据到student_course表中
    studentExcelService.clear();//清空student_excel表
    return Result.success();
}
```



##### studentMapper

```java
//从student_excel表复制数据到student表
boolean copy();
```



##### studentMapper.xml

```xml
<insert id="copy">
    INSERT INTO student SELECT * FROM student_excel;
</insert>
```



##### studentExcelMapper

```java
//清除student_excel表中数据
void clear();
```



##### studentExcelMapper.xml

```xml
<delete id="clear">
    TRUNCATE TABLE student_excel
</delete>
```



### 学生信息删除功能

#### 根据ID单条删除

##### studentController

```java
//根据学生UUID删除数据
    @DeleteMapping("/{uuid}")
    public Result<?> del(@PathVariable String uuid){
        //先删除student_course表中数据
        studentCourseService.remove(Wrappers.<StudentCourse>lambdaQuery().eq(StudentCourse::getStudentUuid,uuid));
        
        studentService.removeById(uuid);
        return Result.success();
    }
```

使用Mybatis-plus的removeById（），需要在实体类中定义主键，否则默认字段名为id

```java
@TableId(value = "uuid")
private String uuid;
```



#### 根据ID批量删除

##### studentController

```java
//根据选中的学生UUID批量删除数据
    @PostMapping("/delSelected")
    public Result<?> delSelected(@RequestBody Collection<String> uuidList){

        //根据条件循环删除student_course表中数据
        for (String s : uuidList) {
            studentCourseService.remove(Wrappers.<StudentCourse>lambdaQuery().eq(StudentCourse::getStudentUuid,s));
        }
        studentService.removeByIds(uuidList);//根据主键UUID批量删除数据
        return Result.success();
    }
```



### 学生信息修改功能

#### studentController

##### 根据ID查询，数据回显

```java
@GetMapping("/findById/{uuid}")
public Result<?> findById(@PathVariable String uuid){
    return Result.success(studentService.getById(uuid));
}
```



##### 根据ID修改数据

```java
@PostMapping("/update")
public Result<?> updateById(@RequestBody Student student){
    studentService.updateById(student);
    return Result.success();
}
```



## Day-03



### 教师登录功能

#### teacherController

```java
@PostMapping("/login")
public Result<?> login(@RequestBody Teacher teacher){
    Teacher res = teacherService.getOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getUsername,teacher.getUsername()).eq(Teacher::getPassword,teacher.getPassword()));
    if (res == null){
        return Result.error("-1","用户名或密码错误");
    }
    return Result.success();
}
```

通过条件查询用户名和密码，如果能在数据库里成功找到则返回成功代码 ‘0’ ，反之返回错误代码 ‘-1’ 和错误信息。



### 图片上传功能

#### fileController

```java
@PostMapping("/uploadImg")
public Result<?> uploadImg(MultipartFile file) throws IOException {
    String originalFilename = file.getOriginalFilename();//获取上传文件名称
    //定义文件的唯一标识（前缀）
    String flag = IdUtil.fastSimpleUUID();
    //上传文件的绝对路径
    String rootFilePath = System.getProperty("user.dir") + "/target/classes/static/" + flag + "_" + originalFilename;
    FileUtil.writeBytes(file.getBytes(),rootFilePath);//把文件写入上传的路径
    return Result.success(ip + ":" + port + "/" + flag + "_" + originalFilename);//返回结果 url
}
```

直接上传到已经编译好的路径/target/classes/static/，返回的url由前端接收，并赋值给参数传递给后端存储到数据库



### 课程添加功能

根据教师用户ID，查询该账户下的所有课程

#### courseController

```java
@PostMapping("/save")
public Result<?> save(@RequestBody Map<String,String> map){
    System.out.println(map);
    Course course = new Course(IdUtil.simpleUUID(),map.get("courseName"),map.get("courseImg"),new Date());
    TeacherCourse teacherCourse = new TeacherCourse(map.get("uuid"),course.getCourseUuid());
    courseService.save(course);//添加一条课程数据
    teacherCourseService.save(teacherCourse);//添加教师-课程中间表数据
    return Result.success();
}
```

前端传来多个参数，用map一起接收body里的全部数据，用map.get（）根据key取得值，通过构造器创建实例对象，调用mybatis-plus已封装好的方法添加到对应的表中。当前教师账户创建课程时，一并添加到该账户下。



### 课程展示功能

#### courseController

```java
@GetMapping("/{uuid}")
public Result<?> findCourseByTeacher(@PathVariable String uuid){
    return Result.success(courseService.findCourseByTeacher(uuid));
}
```



#### courseMapper

```java
//根据教师用户ID，查询该账户下的所有课程
List<Course> findCourseByTeacher(String teacherUuid);
```



#### courseMapper.xml

```xml
<select id="findCourseByTeacher" parameterType="string" resultType="Course">
    select c.course_name, c.course_img, c.course_date
    from teacher t, course c, teacher_course tc
    where #{teacherUuid} = tc.teacher_uuid
    and tc.course_uuid = c.course_uuid
</select>
```

