<template>
    <div style="min-width: 1500px">
        <div id="nav" >
            <StudentHeader/>
        </div>
        <div >
            <router-view/>
        </div>
    </div>


</template>

<script>

    import StudentHeader from "../components/Student/StudentHeader";
    export default {
        name: "StudentLayout",
        components: {StudentHeader},
    }
</script>

<style scoped>

</style>