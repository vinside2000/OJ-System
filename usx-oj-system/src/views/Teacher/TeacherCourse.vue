<template>
    <div style="margin:20px 0 20px 65px">
        <el-button type="primary" icon="el-icon-circle-plus-outline" @click="createNewCourse">创建新课程</el-button>
    </div>
<!--我的课程-->
    <el-row :gutter="20">
        <el-col :span="1"><div class="grid-content bg-purple"></div></el-col>
        <el-col :span="22">
            <p style="display: block;margin-top: 20px">我的课程</p>
            <div class="grid-content bg-purple"  v-for="list in courseList">
                <el-col :span="20">
                    <div @click="toCourseDesc(list.courseUuid)" style="margin-top: 20px;width: 320px;cursor: pointer;height: 250px">
                        <el-card :body-style="{ padding: '0px'}" shadow="hover">
                            <img :src="list.courseImg" class="avatar" style="width: 320px;height: 180px">
                            <div>
                                <div style="padding: 10px;width: 150px;float: left">
                                    <span>{{list.courseName}}</span>
                                    <div class="bottom">
                                        <time class="time">{{list.courseDate}}</time>
                                    </div>
                                </div>
<!--                                <el-popconfirm-->
<!--                                        title="这是一段内容确定删除吗？" @confirm="delCourse(list.courseUuid)"-->
<!--                                >-->
<!--                                    <template #reference>-->
<!--                                        <el-button type="danger" icon="el-icon-delete" style="float: right;margin-top: 17px" size="small">删除课程</el-button>-->
<!--                                    </template>-->
<!--                                </el-popconfirm>-->
                            </div>
                        </el-card>
                    </div>
                </el-col>
            </div>
        </el-col>
        <el-col :span="1"><div class="grid-content bg-purple"></div></el-col>
    </el-row>

<!--未拥有的课程-->
<!--    <el-collapse @change="handleChange">-->
<!--        <el-collapse-item title="所有课程（仅显示未拥有的课程）" name="1">-->
<!--            <el-row :gutter="20">-->
<!--                <el-col :span="1"><div class="grid-content bg-purple"></div></el-col>-->
<!--                <el-col :span="22">-->
<!--                    <div class="grid-content bg-purple"  v-for="aList in allCourseList">-->
<!--                        <el-col :span="20">-->
<!--                            <div @click="toCourseDesc" style="margin-top: 20px;width: 320px;cursor: pointer;height: 250px">-->
<!--                                <el-card :body-style="{ padding: '0px'}" shadow="hover">-->
<!--                                    <img :src="aList.courseImg" class="avatar" style="width: 320px;height: 180px">-->
<!--                                    <div>-->
<!--                                        <div style="padding: 10px;width: 150px;float: left">-->
<!--                                            <span>{{aList.courseName}}</span>-->
<!--                                            <div class="bottom">-->
<!--                                                <time class="time">{{aList.courseDate}}</time>-->
<!--                                            </div>-->
<!--                                        </div>-->
<!--                                        <el-button type="success" icon="el-icon-circle-plus-outline" @click="addCourse(aList.courseUuid)" style="float: right;margin-top: 18px" size="small">添加到我的课程</el-button>-->
<!--                                    </div>-->
<!--                                </el-card>-->
<!--                            </div>-->
<!--                        </el-col>-->
<!--                    </div>-->
<!--                </el-col>-->
<!--                <el-col :span="1"><div class="grid-content bg-purple"></div></el-col>-->
<!--            </el-row>-->
<!--        </el-collapse-item>-->
<!--    </el-collapse>-->

<!--    添加课程-->
    <el-dialog title="创建新的课程" v-model="dialogFormVisible" width="650px">
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="课程名称" prop="courseName">
                <el-input type="text" v-model="ruleForm.courseName" autocomplete="off" placeholder="请输入课程名称"></el-input>
            </el-form-item>
            <el-form-item label="封面" prop="courseImg">
                <el-upload
                        class="avatar-uploader"
                        action="http://localhost:9090/file/uploadImg"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                >
                    <img v-if="imageUrl" :src="imageUrl" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </el-form-item>
        </el-form>
        <template #footer>
    <span class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
    </span>
        </template>
    </el-dialog>
</template>

<script>
    import {ElMessage} from "element-plus";
    import request from "../../utils/request";

    export default {
        name: "TeacherCourse",
        data() {
            var checkCourse = (rule, value, callback) => {
                    if (!value) {
                        return callback(new Error('课程名称不能为空'));
                    }else {
                        callback();
                    }
                };
            return {
                teacherUuid:'',
                courseList:[{}],
                allCourseList:[{}],
                imageUrl:'',
                dialogFormVisible: false,
                ruleForm: {
                        courseName: '',
                        courseImg:'',
                    }
                ,
                rules: {
                    course: [
                        { validator: checkCourse, trigger: 'blur' }
                    ]
                }
            };
        },
        created() {
            let teaJson = sessionStorage.getItem("teacher");//从session中取得当前账号的信息
            let uuid = JSON.parse(teaJson).uuid;
            this.teacherUuid = uuid;
            // console.log(uuid)
            this.load(uuid)
        },
        methods:{
            //跳转到该课程详情页
            toCourseDesc(uuid){
                sessionStorage.setItem("courseUuid",uuid);
                this.$router.push('/courseLay')
            },

            //将课程添加到该用户下
            addCourse(uuid){
                request.post("/tc",{
                    teacherUuid:this.teacherUuid,
                    courseUuid:uuid
                }).then(res => {
                    if (res.code === '0'){
                        this.$message({
                            type:'success',
                            message:'添加成功'
                        })
                        this.load(this.teacherUuid);
                        this.loadAll();
                    }else {
                        this.$message({
                            type: 'error',
                            message: '添加失败'
                        })
                    }
                })
            },

            handleChange(){
              this.loadAll();
            },

            //删除课程
            delCourse(uuid){
                console.log(uuid)
            },

            //根据教师用户ID，查询未绑定在该教师账户下的所有课程
            loadAll(){
              request.get("/course/all/" + this.teacherUuid).then(res => {
                  console.log(res.data)
                  this.allCourseList = res.data;
              })
            },

            //根据当前教师账号UUID查询课程
            load(uuid){
              request.get("/course/" + uuid).then(res => {
                  console.log(res.data);
                  this.courseList = res.data;
              })
            },

            //上传成功时
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
                this.ruleForm.courseImg = res.data;//从返回值res中取得图片地址
            },

            //打开创建课程弹窗
            createNewCourse(){
                this.dialogFormVisible = true;
                this.imageUrl = '';
                this.$refs['ruleForm'].resetFields();
            },

            //提交创建课程的表单
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        request.post("/course/save",{
                            courseName:this.ruleForm.courseName,
                            courseImg:this.ruleForm.courseImg,
                            uuid:this.teacherUuid
                        }).then(res => {
                            if (res.code === '0'){
                                this.$message({
                                    type: 'success',
                                    message: '创建成功'
                                })
                                this.dialogFormVisible = false;
                                this.load(this.teacherUuid);
                            }else {
                                this.$message({
                                    type: 'error',
                                    message: res.msg
                                })
                            }
                        })
                    }else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
        },
    }
</script>

<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        text-align: center;
        line-height: 178px !important;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>