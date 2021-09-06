<template>
    <el-container style="min-width: 1500px">
        <el-header style="padding: 0">
            <TeacherHeader/>
        </el-header>
        <el-container style="margin-top: 20px">
            <el-aside width="200px" >
                <CourseAside/>
            </el-aside>

            <el-main>
                <el-page-header @back="goBack" content="我的课程"></el-page-header>
                <br>
                <router-view />
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import CourseAside from "../components/Course/CourseAside";
    import TeacherHeader from "../components/Teacher/TeacherHeader";
    export default {
        name: "CourseLayout",
        components: {TeacherHeader, CourseAside},
        methods:{
            goBack() {
                // console.log('go back');
                sessionStorage.removeItem("courseUuid");
                this.$router.push('/teaLay')
            }
        }
    }
</script>

<style scoped>

</style>