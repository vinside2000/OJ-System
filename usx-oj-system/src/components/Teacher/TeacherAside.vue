<template>
    <el-row class="tac" >
        <el-col :span="12">
            <el-menu
                    :uniqueOpened="true"
                    :default-active="activeIndex"
                    class="el-menu-vertical-demo"
                    style="width: 400px;height: 100vh;float: left"
            >
                <el-menu-item index="1" @click="toCourse">
                    <i class="el-icon-s-management"></i>
                    <template #title>课程</template>
                </el-menu-item>

                <el-menu-item index="2" @click="toStudent">
                    <i class="el-icon-s-custom"></i>
                    <template #title>学生</template>
                </el-menu-item>

                <el-menu-item index="3" @click="toProblemLib">
                    <i class="el-icon-edit"></i>
                    <template #title>题库</template>
                </el-menu-item>

                <el-menu-item index="4">
                    <i class="el-icon-s-marketing"></i>
                    <template #title>成绩</template>
                </el-menu-item>
            </el-menu>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "TeacherAside",
        data(){
            return{
                activeIndex: 1,
            }
        },
        created() {
            if (this.$route.path == "/teaLay/teaCourse"){
                this.activeIndex = 1;
            }else if (this.$route.path == "/teaLay/stuList"){
                this.activeIndex = 2;
            }else if (this.$route.path == "/teaLay/proLib"){
                this.activeIndex = 3;
            }
        },
        methods:{
            toCourse(){
                this.$router.push("/teaLay/teaCourse")
                console.log(this.$route.path)
            },
            toStudent(){
                this.$router.push("/teaLay/stuList")
            },
            toProblemLib(){
                this.$router.push("/teaLay/proLib")
            }
        }
    }
</script>

<style scoped>

</style>