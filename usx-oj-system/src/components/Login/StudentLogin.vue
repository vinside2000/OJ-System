<template>
    <div style="text-align: center;font-size: 30px;padding: 20px 0">学生账号登录</div>
    <!--表单-->
    <el-form ref="stuLoginForm" :model="stuLoginForm" :rules="stuRules">
        <el-form-item prop="username">
            <el-input type="text" v-model="stuLoginForm.username"  prefix-icon="el-icon-user-solid"></el-input>
        </el-form-item>
        <el-form-item prop="password">
            <el-input type="password" v-model="stuLoginForm.password" prefix-icon="el-icon-lock" show-password></el-input>
        </el-form-item>
        <el-form-item prop="verifyCode">
            <el-input type="text" v-model="stuLoginForm.verifyCode" placeholder="请输入验证码" style="width: 180px;margin-right: 5px"></el-input>
            <!--随机验证码-->
            <div style="float: right">
                <div @click="stuRefreshCode" style="cursor: pointer" title="点击刷新验证码">
                    <s-identify :identifyCode="stuIdentifyCode"></s-identify>
                </div>
            </div>
        </el-form-item>



        <button class="btn" @click="studentLogin" type="button">登录
            <span></span>
            <span></span>
            <span></span>
            <span></span>
        </button>
    </el-form>
</template>

<script>
    import SIdentify from "./StudentIdentify";
    import request from "../../utils/request";
    export default {
        name: "StudentLogin",
        components:{SIdentify},
        data() {
            // 自定义验证规则：验证码验证规则
            const stuVerifyCode = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入验证码'))
                } else if (value !== this.stuIdentifyCode) {
                    // console.log('stuVerifyCode:', value)
                    callback(new Error('验证码不正确'))
                    this.stuRefreshCode();
                } else {
                    callback()
                }
            }
            return{
                stuLoginForm:{
                    verifyCode: '',
                },
                stuIdentifyCodes: '1234567890',
                stuIdentifyCode: '',
                stuRules:{
                    username:[{required:true,message:'请输入用户名',trigger:'blur'}],
                    password:[{required:true,message:'请输入密码',trigger:'blur'}],
                    verifyCode:[{required:true,trigger:'blur',validator: stuVerifyCode}],
                },
            }
        },
        mounted() {
            // 验证码初始化
            this.stuIdentifyCode = '';
            this.makeCode(this.stuIdentifyCodes, 4);
        },
        methods:{
            // 生成随机数
            randomNum(min, max) {
                return Math.floor(Math.random() * (max - min) + min)
            },
            // 切换验证码
            stuRefreshCode() {
                this.stuIdentifyCode = '';
                this.makeCode(this.stuIdentifyCode, 4);
            },
            // 生成四位随机验证码
            makeCode(o, l) {
                for (let i = 0; i < l; i++) {
                    this.stuIdentifyCode += this.stuIdentifyCodes[this.randomNum(0, this.stuIdentifyCodes.length)]
                }
                // console.log("stu:" +this.stuIdentifyCode)
            },
            studentLogin(){
                this.$refs.stuLoginForm.validate((valid) => {
                    if (valid) {
                        request.post("/stu/login",this.stuLoginForm).then(res => {
                            // console.log(res);
                            if (res.code === '0'){
                                this.$message({
                                    type: 'success',
                                    message: '登录成功'
                                })
                                sessionStorage.setItem("student",JSON.stringify(res.data))
                                this.$router.push("/stuLay");
                            }else{
                                this.$message({
                                    type: 'error',
                                    message: res.msg
                                })
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },

        }
    }

</script>

<style scoped>
    .btn {
        padding: 10px 20px;
        color: #03e9f4;
        position: relative;
        overflow: hidden;
        text-transform: uppercase;
        letter-spacing: 2px;
        left: 39%;
        background: #141e30;
    }
    .btn:hover {
        cursor: pointer;
        border-radius: 5px;
        color: #fff;
        background: #03e9f4;
        box-shadow: 0 0 5px 0 #03e9f4,
        0 0 25px 0 #03e9f4,
        0 0 50px 0 #03e9f4,
        0 0 100px 0 #03e9f4;
        transition: all 1s linear;
    }
    .btn>span {
        position: absolute;
    }

    .btn>span:nth-child(1) {
        width: 100%;
        height: 2px;
        background: -webkit-linear-gradient(left, transparent, #03e9f4);
        left: -100%;
        top: 0px;
        animation: line1 1s linear infinite;
    }

    @keyframes line1 {

        50%,
        100% {
            left: 100%;
        }
    }

    .btn>span:nth-child(2) {
        width: 2px;
        height: 100%;
        background: -webkit-linear-gradient(top, transparent, #03e9f4);
        right: 0px;
        top: -100%;
        animation: line2 1s 0.25s linear infinite;
    }

    @keyframes line2 {

        50%,
        100% {
            top: 100%;
        }
    }

    .btn>span:nth-child(3) {
        width: 100%;
        height: 2px;
        background: -webkit-linear-gradient(left, #03e9f4, transparent);
        left: 100%;
        bottom: 0px;
        animation: line3 1s 0.75s linear infinite;
    }

    @keyframes line3 {

        50%,
        100% {
            left: -100%;
        }
    }

    .btn>span:nth-child(4) {
        width: 2px;
        height: 100%;
        background: -webkit-linear-gradient(top, transparent, #03e9f4);
        left: 0px;
        top: 100%;
        animation: line4 1s 1s linear infinite;
    }

    @keyframes line4 {

        50%,
        100% {
            top: -100%;
        }
    }
</style>

