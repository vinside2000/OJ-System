package com.usxoj.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.usxoj.entity.Student;
import com.usxoj.entity.StudentCourse;
import com.usxoj.entity.StudentExcel;
import com.usxoj.entity.Teacher;
import com.usxoj.service.StudentCourseService;
import com.usxoj.service.StudentExcelService;
import com.usxoj.service.StudentService;
import com.usxoj.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * Description:
 * date: 2021/8/9 19:31
 *
 * @author vinside
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/stu")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private StudentExcelService studentExcelService;

    //分页条件查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam String courseUuid){
        return Result.success(studentService.findStudentByCourseByPage(new Page<Integer>(pageNum,pageSize), courseUuid,search));
//        return Result.success(studentService.page(new Page<>(pageNum,pageSize), Wrappers.<Student>lambdaQuery().like(Student::getName,search)));
    }

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

    //根据学生UUID删除数据
    @DeleteMapping("/{uuid}")
    public Result<?> del(@PathVariable String uuid){
        studentCourseService.remove(Wrappers.<StudentCourse>lambdaQuery().eq(StudentCourse::getStudentUuid,uuid));//先删除关联课程表中的学生数据
        studentService.removeById(uuid);
        return Result.success();
    }

    //根据学生UUID查找数据
    @GetMapping("/findById/{uuid}")
    public Result<?> findById(@PathVariable String uuid){
        return Result.success(studentService.getById(uuid));
    }

    //根据学生UUID修改数据
    @PostMapping("/update")
    public Result<?> updateById(@RequestBody Student student){
        studentService.updateById(student);
        return Result.success();
    }

    //根据选中的学生UUID批量删除数据
    @PostMapping("/delSelected")
    public Result<?> delSelected(@RequestBody Collection<String> uuidList){

        //根据条件循环删除数据
        for (String s : uuidList) {
            studentCourseService.remove(Wrappers.<StudentCourse>lambdaQuery().eq(StudentCourse::getStudentUuid,s));
        }
        studentService.removeByIds(uuidList);//根据主键UUID批量删除数据
        return Result.success();
    }

    //学生登录模块
    @PostMapping("/login")
    public Result<?> login(@RequestBody Student student){
        Student res = studentService.getOne(Wrappers.<Student>lambdaQuery().eq(Student::getUsername,student.getUsername()).eq(Student::getPassword,student.getPassword()).last("LIMIT 1"));
        if (res == null){
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success(res);
    }
}
