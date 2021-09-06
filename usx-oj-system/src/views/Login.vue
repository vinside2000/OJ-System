<!--登录界面-->
<template>
    <div style="min-width: 1500px">
        <el-container>


            <!--header-->
            <el-header >

            </el-header>


            <!--main-->
            <el-main>
                <!--背景-->
                <div class="background">
                    <!-- 登录模块-->
                    <div class="loginContainer">
                        <el-tabs type="card" @tab-click="handleClick" v-model="activeName" >

                            <!--学生登录-->
                            <el-tab-pane label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学生入口&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" name="first">
                                    <StudentLogin/>
                            </el-tab-pane>

                            <!--教师登录-->
                            <el-tab-pane label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;教师入口&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" name="second">
                                    <TeacherLogin/>
                            </el-tab-pane>

                        </el-tabs>
                    </div>
                </div>
            </el-main>


            <!--footer-->
            <el-footer>

            </el-footer>


        </el-container>
    </div>


</template>

<script>


    import StudentLogin from "../components/Login/StudentLogin";
    import TeacherLogin from "../components/Login/TeacherLogin";

    export default {
        name: "Login",
        components:{TeacherLogin, StudentLogin},
        data() {
            return{
                activeName: 'first',
            }
        },
        methods:{
            handleClick(tab, event) {
                console.log(tab, event);
            }
        }
    }

</script>

<style scoped>
    .background{
        z-index: 1;
        height: 80vh;
        background: url("../assets/img/login.jpg");
        background-repeat: no-repeat;
        background-size: 100% 100%;
        background-position: 0 50%;
        align-items: center;
        display: flex;
        color: #03e9f4;
        overflow: hidden;
    }
    .loginContainer{
        border-radius: 15px;
        background-clip: padding-box;
        margin: 0 70%;
        width: 400px;
        padding: 20px 30px 20px 30px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }
</style>