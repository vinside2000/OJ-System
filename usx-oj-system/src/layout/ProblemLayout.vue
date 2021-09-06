<template>
    <el-container style="min-width: 1500px">
        <el-header style="padding: 0">
            <ProblemHeader/>
        </el-header>
        <el-container style="margin-top: 20px">
            <el-aside width="200px" ><ProblemAside /></el-aside>
            <el-main><router-view /></el-main>
        </el-container>
    </el-container>

</template>

<script>

    import ProblemAside from "../components/OJ/ProblemAside";
    import ProblemHeader from "../components/OJ/ProblemHeader";
    export default {
        name: "ProblemLayout",
        components: {ProblemHeader, ProblemAside},
    }
</script>

<style scoped>

</style>