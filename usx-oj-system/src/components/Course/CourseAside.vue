<template>
    <el-row class="tac" >
        <el-col :span="12">
            <el-menu
                    :uniqueOpened="true"
                    :default-active="activeIndex"
                    class="el-menu-vertical-demo"
                    style="width: 400px;height: 100vh;float: left"
            >
                <el-menu-item index="1" @click="toStudent">
                    <i class="el-icon-s-custom"></i>
                    <template #title>学生</template>
                </el-menu-item>

                <el-menu-item index="2" @click="toProblemLib">
                    <i class="el-icon-collection"></i>
                    <template #title>题集</template>
                </el-menu-item>

                <el-menu-item index="3" @click="toProblem">
                    <i class="el-icon-edit"></i>
                    <template #title>题库</template>
                </el-menu-item>

                <el-menu-item index="4" @click="toScore">
                    <i class="el-icon-s-marketing"></i>
                    <template #title>成绩</template>
                </el-menu-item>
            </el-menu>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "CourseAside",
        data(){
            return{
                activeIndex: '',
            }
        },
        created() {
            if (this.$route.path == "/courseLay/stuList"){
                this.activeIndex = 1;
            }else if (this.$route.path == "/courseLay/proLib"){
                this.activeIndex = 2;
            }else if (this.$route.path == "/courseLay/pro"){
                this.activeIndex = 3;
            }else if (this.$route.path == "/courseLay/score"){
                this.activeIndex = 4;
            }
        },
        methods:{
            toStudent(){
                this.$router.push("/courseLay/stuList")
            },
            toProblemLib(){
                this.$router.push("/courseLay/proLib")
            },
            toProblem(){
                this.$router.push("/courseLay/pro")
            },
            toScore(){
                this.$router.push("/courseLay/score")
            }
        }
    }
</script>

<style scoped>

</style>