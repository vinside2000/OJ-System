<template>
    <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b">
        <el-menu-item index="1" style="margin-left: 200px;" @click="toStuHome">
            <h2>绍大OJ平台</h2>
        </el-menu-item>

        <el-menu-item index="2" style="margin-left: 200px" >我的题目</el-menu-item>
        <el-menu-item index="3" style="margin-left: 100px">我的成绩</el-menu-item>

        <el-submenu index="5" style="float: right;margin-right: 200px">
            <template #title>{{studentName}}</template>

            <el-menu-item index="2-1" @click="changePassword()">修改密码</el-menu-item>

            <el-menu-item index="2-2" @click="logout()">退出登录</el-menu-item>
        </el-submenu>
    </el-menu>

    <el-dialog title="修改密码" v-model="dialogFormVisible" width="500px">
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="80px" class="demo-ruleForm" style="padding: 0 30px">
            <el-form-item label="旧密码" prop="oldPassword">
                <el-input type="password" v-model="ruleForm.oldPassword" autocomplete="off" placeholder="最少6位字符"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="pass">
                <el-input type="password" v-model="ruleForm.pass" autocomplete="off" placeholder="最少6位字符"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass">
                <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" placeholder="最少6位字符"></el-input>
            </el-form-item>
            <el-form-item style="margin-left: 45px;margin-top: 35px">
                <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<script>
    import {ElMessage} from "element-plus";

    export default {
        name: "ProblemHeader",
        data(){
            var checkOldPassword = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('请输入旧密码'));
                }else if (value.length < 6){
                    return callback(new Error('长度至少6位'));
                } else {
                    callback();
                }
            };
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入新密码'));
                }else if (value.length < 6){
                    return callback(new Error('长度至少6位'));
                }else if (value === this.ruleForm.oldPassword){
                    callback(new Error('新密码不能与旧密码相同'));
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                }else if (value.length < 6){
                    return callback(new Error('长度至少6位'));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return{
                studentName:'',
                activeIndex: '2',
                dialogFormVisible: false,
                formLabelWidth: '100px',
                ruleForm: {
                    oldPassword: '',
                    pass: '',
                    checkPass: '',
                },
                rules: {
                    oldPassword: [
                        { validator: checkOldPassword, trigger: 'blur' }
                    ],
                    pass: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                    checkPass: [
                        { validator: validatePass2, trigger: 'blur' }
                    ],
                }
            }
        },
        created() {
            let stuJson = sessionStorage.getItem("student");
            // console.log(JSON.parse(stuJson).name);
            this.studentName = JSON.parse(stuJson).name;
        },
        methods:{
            toStuHome(){
                this.$router.push('/stuLay')
            },
            logout(){
                this.$router.push("/")
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        ElMessage.success({
                            message: '修改成功',
                            type: 'success'
                        });
                        this.dialogFormVisible=false;
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            changePassword(){
                this.dialogFormVisible = true;
                this.resetForm("ruleForm");
            }
        }
    }
</script>

<style scoped>

</style>