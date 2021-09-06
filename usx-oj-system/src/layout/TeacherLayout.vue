<template>
    <el-container style="min-width: 1500px">
        <el-header style="padding: 0">
            <TeacherHeader/>
        </el-header>
        <el-container style="margin-top: 20px">
            <el-aside width="200px" ></el-aside>
            <el-main><router-view /></el-main>
        </el-container>
    </el-container>
</template>

<script>
    import TeacherHeader from "../components/Teacher/TeacherHeader";
    import TeacherAside from "../components/Teacher/TeacherAside";
    export default {
        name: "TeacherLayout",
        components: {TeacherAside, TeacherHeader}
    }
</script>

<style scoped>

</style>