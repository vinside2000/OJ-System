<template>
    <div style="width: 100%;height: 200px">
        <div class="header">
            学生管理
        </div>


<!--        添加与批量删除-->
        <div class="content">
            <el-button class="el-icon-circle-plus-outline" type="primary" @click="show3 = !show3">
                添加学生
            </el-button>
            <el-popconfirm
                    title="这是一段内容确定删除吗？" @confirm="delSelected"
            >
                <template #reference>
                    <el-button type="danger" class="el-icon-delete">删除选中</el-button>
                </template>
            </el-popconfirm>
            <div style="margin: 10px 20px; height: 200px;">
                <el-collapse-transition>
                    <div v-show="show3">
                        <el-button type="success" @click="uploadXlsFile">上传xlsx表格学生名单</el-button>
                        <el-button type="success" @click="addStudent">直接添加学生</el-button>
                    </div>
                </el-collapse-transition>
            </div>
        </div>


<!--        搜索框-->
        <div class="footer">
            <div style="width: 300px;float: right">
                <el-input
                    placeholder="请输入内容"
                    v-model="search"
                    type="text"
                    clearable
                >
                <template #append>
                    <el-button icon="el-icon-search" @click="load"></el-button>
                </template>
                </el-input>
            </div>
        </div>
    </div>


<!--    表格主体-->
    <el-table
            :data="tableData"
            stripe
            style="width: 100%;margin-top: 20px"
            @selection-change="handleSelectionChange">
        <el-table-column
                type="selection"
                width="55">
        </el-table-column>
        <el-table-column
                prop="uuid"
                label="编号"
                v-if="false">
        </el-table-column>
        <el-table-column
                prop="school"
                label="学校"
                width="180">
        </el-table-column>
        <el-table-column
                prop="major"
                label="专业"
                width="180">
        </el-table-column>
        <el-table-column
                prop="studentClass"
                label="班级"
                width="180">
        </el-table-column>
        <el-table-column
                prop="number"
                label="学号"
                width="180"
                sortable>
        </el-table-column>
        <el-table-column
                prop="name"
                label="姓名"
                width="180">
        </el-table-column>
        <el-table-column
                prop="gender"
                label="性别">
        </el-table-column>
        <el-table-column label="操作">
            <template #default="scope">
                <el-button
                        size="mini"
                        @click="handleEdit(scope.row.uuid)">编辑</el-button>
                <el-popconfirm
                        title="这是一段内容确定删除吗？" @confirm="handleDel(scope.row.uuid)"
                >
                    <template #reference>
                        <el-button type="danger" size="mini" >删除</el-button>
                    </template>
                </el-popconfirm>
            </template>
        </el-table-column>
    </el-table>


<!--分页控件-->
    <div style="float: left;margin-top: 20px">
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[10,20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
        </el-pagination>
    </div>


<!--上传Excel文件批量导入学生信息-->
    <el-dialog
            title="上传xlsx表格学生名单"
            v-model="dialogVisible1"
            width="40%"
            >
            <div>
                使用 Excel 表格批量预录入学生信息 <br>
                上传 .xlsx 表格文件，每一行为一个学生信息，支持多次上传文件，<el-link type="primary" href="http://localhost:9090/file/download">点击下载模板</el-link>
            </div>


        <el-upload
                action="http://localhost:9090/file/upload"
                multiple
                ref="upload"
                accept=".xls,.xlsx"
                :on-success="fileUploadSuccess"
                :auto-upload="false"
        >
            <template #trigger>
                <el-button size="small" type="primary" style="margin-top: 20px">选择文件</el-button>
            </template>
            <el-button type="success" size="small" style="margin-top: 20px;margin-left: 20px" @click="submitUpload">点击上传</el-button>
            <template #tip>
                <div class="el-upload__tip">只能上传 xls或xlsx 文件</div>
            </template>
        </el-upload>

    </el-dialog>


<!--直接添加学生信息-->
    <el-dialog
            title="添加学生"
            v-model="dialogVisible2"
            width="30%"
    >
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
            <el-form-item label="学校" prop="school">
                <el-input v-model="ruleForm.school" autocomplete="off" ></el-input>
            </el-form-item>
            <el-form-item label="专业" prop="major">
                <el-input v-model="ruleForm.major" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="班级" prop="studentClass">
                <el-input v-model="ruleForm.studentClass"></el-input>
            </el-form-item>
            <el-form-item label="学号" prop="number">
                <el-input v-model="ruleForm.number"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
                <el-input v-model="ruleForm.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="ruleForm.gender">
                    <el-radio label="男" ></el-radio>
                    <el-radio label="女" ></el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>


<!--修改学生信息-->
    <el-dialog
            title="修改学生"
            v-model="dialogVisible3"
            width="30%"
    >
        <el-form :model="updateForm" status-icon class="demo-ruleForm">
            <el-form-item label="学校" prop="school">
                <el-input v-model="updateForm.school" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="专业" prop="major">
                <el-input v-model="updateForm.major" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="班级" prop="studentClass">
                <el-input v-model="updateForm.studentClass"></el-input>
            </el-form-item>
            <el-form-item label="学号" prop="number">
                <el-input v-model="updateForm.number"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
                <el-input v-model="updateForm.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="updateForm.gender">
                    <el-radio label="男" ></el-radio>
                    <el-radio label="女" ></el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitUpdateForm">提交</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<script>
    import {ElMessage} from "element-plus";
    import request from "../../utils/request";


    export default {
        name: "StudentList",
        data() {
            var checkSchool = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('学校不能为空'));
                }else {
                    callback();
                }
            }
            var checkMajor = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('专业不能为空'));
                }else {
                    callback();
                }
            }
            var checkNumber = (rule, value, callback) => {
                    if (!value) {
                        return callback(new Error('学号不能为空'));
                    }else {
                        callback();
                    }
            }
            var checkName = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('姓名不能为空'));
                }else {
                    callback();
                }
            }
            var checkStudentClass = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('班级不能为空'));
                }else {
                    callback();
                }
            }
            return {
                courseUuid:'',
                currentPage: 1,
                pageSize: 10,
                total: 0,
                search: '',
                tableData: [{}],
                show3: false,
                dialogVisible1:false,
                dialogVisible2:false,
                dialogVisible3:false,
                selected:[],
                selectedUuid:[],
                ruleForm:{
                    school:'',
                    major:'',
                    studentClass: '',
                    number: '',
                    name:'',
                    gender:'男',
                },
                updateForm:{},
                rules: {
                    school: [
                        { validator: checkSchool, trigger: 'blur' }
                    ],
                    major: [
                        { validator: checkMajor, trigger: 'blur' }
                    ],
                    studentClass: [
                        { validator: checkStudentClass, trigger: 'blur' }
                    ],
                    name: [
                        { validator: checkName, trigger: 'blur' }
                    ],
                    number: [
                        { validator: checkNumber, trigger: 'blur' }
                    ],
                }
            }
        },
        created() {
            let uuid = sessionStorage.getItem("courseUuid");//获取当前课程的uuid
            this.courseUuid = uuid;
            // console.log(uuid)
            this.load();
        },
        methods: {
            //点击上传
            submitUpload() {
                this.$refs.upload.submit();
            },
            //选择项发生变化时,获取选中行的值
            handleSelectionChange(val){
                // console.log(val);
                this.selected = val;
            },
            //删除选中
            delSelected(){
                for (let i=0;i<this.selected.length;i++){
                    this.selectedUuid[i] = this.selected[i].uuid;//获取选中行的uuid
                }
                request.post("/stu/delSelected",this.selectedUuid).then(res => {
                    console.log(res)
                    if (res.code === '0'){
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        })
                    }else {
                        this.$message({
                            type: 'error',
                            message: res.msg
                        })
                    }
                    this.load();
                })
            },
            //数据回显
            handleEdit(uuid){
                console.log(uuid)
                this.dialogVisible3 = true;
                request.get("/stu/findById/" + uuid).then(res => {
                    console.log(res)
                    this.updateForm = res.data;
                })
            },
            //提交更新表单
            submitUpdateForm(){
                request.post("/stu/update",this.updateForm).then(res =>{
                    console.log(res)
                    this.dialogVisible3=false;
                    if (res.code === '0'){
                        this.$message({
                            type: 'success',
                            message: '修改成功'
                        })
                    }else{
                        this.$message({
                            type: 'error',
                            message: res.msg
                        })
                    }
                    this.load()
                })
            },
            //页面数据加载
            load(){
                request.get("/stu",{
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.search,
                        courseUuid: this.courseUuid
                    }
                }).then(res => {
                    // console.log(res)
                    this.tableData = res.data.records
                    this.total = res.data.total
                    this.currentPage = res.data.current
                })
            },
            //文件上传成功钩子
            fileUploadSuccess(res){
                request.post("/stu/save",this.courseUuid).then(res => {
                    if (res.code === '0'){
                        this.$message({
                            type: 'success',
                            message: '上传成功'
                        })
                        this.dialogVisible1 = false;
                    }else {
                        this.$message({
                            type: 'error',
                            message: res.msg
                        })
                    }
                    this.load();
                })
                // console.log(res);
                this.load();
            },
            handleSizeChange(val) {
                // console.log(`每页 ${val} 条`);
                this.pageSize=val;
                this.load();
            },
            handleCurrentChange(val) {
                // console.log(`当前页: ${val}`);
                this.currentPage=val;
                this.load();
            },
            //提交增加数据表单
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                if (valid) {
                    request.post("/stu",{
                        school: this.ruleForm.school,
                        major: this.ruleForm.major,
                        studentClass:this.ruleForm.studentClass,
                        number: this.ruleForm.number,
                        name: this.ruleForm.name,
                        gender: this.ruleForm.gender,
                        courseUuid: this.courseUuid
                    }).then(res =>{
                        console.log(res)
                        this.dialogVisible2=false;
                        if (res.code === '0'){
                            this.$message({
                                type: 'success',
                                message: '添加成功'
                            })
                        }else{
                            this.$message({
                                type: 'error',
                                message: res.msg
                            })
                        }
                        this.load()
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
            },
            //删除
            handleDel(uuid){
                console.log(uuid)
                request.delete("/stu/" + uuid).then(res => {
                    if (res.code === '0'){
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        })
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.msg
                        })
                    }
                    this.load()
                })
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            uploadXlsFile(){
                this.dialogVisible1 = true;
                this.$refs['upload'].clearFiles();
            },
            addStudent(){
                this.dialogVisible2 = true;
                this.resetForm("ruleForm");
            }
        },
    }
</script>

<style scoped>
    .header{
        font-weight: bolder;
        height: 25px;
        padding:0 10px;
    }
    .content{
        margin: 10px 0;
        background: #E8EBF0;
        height: 150px;
    }
    .footer{
        height: 25px;
        padding: 10px;
    }
    .el-icon-circle-plus-outline{
        margin: 20px;
    }
</style>