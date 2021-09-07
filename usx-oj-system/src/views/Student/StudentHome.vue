<template>
    <el-row :gutter="20">
        <el-col :span="5"><div class="grid-content bg-purple"></div></el-col>


        <el-col :span="14">
            <p style="display: block;margin-top: 20px">我的课程</p>
            <div class="grid-content bg-purple"  v-for="list in courseList">
                <el-col :span="20">
                    <div @click="toProList(list.courseUuid)" style="margin-top: 20px;width: 320px;cursor: pointer;height: 250px">
                        <el-card :body-style="{ padding: '0px'}" shadow="hover">
                            <img :src="list.courseImg" class="image" style="width: 320px;height: 180px">
                            <div style="padding: 10px;">
                                <span>{{list.courseName}}</span>
                                <div class="bottom">
                                    <time class="time">{{list.courseDate}}</time>
                                </div>
                            </div>
                        </el-card>
                    </div>
                </el-col>
            </div>
        </el-col>


        <el-col :span="5"><div class="grid-content bg-purple"></div></el-col>
    </el-row>
</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "StudentHome",
        data() {
            return {
                courseList:[{}],
            };
        },
        created() {
            let stuJson = sessionStorage.getItem("student");//从session中取得当前账号的信息
            let number = JSON.parse(stuJson).number;
            // this.teacherUuid = uuid;
            // console.log(number)
            this.load(number)
        },
        methods:{
            //加载课程
            load(number){
                request.get("/course/stu/"+ number).then(res => {
                    console.log(res.data);
                    this.courseList = res.data;
                })
            },
            toProList(uuid){
                sessionStorage.setItem("courseUuid",uuid);
                this.$router.push('/stuLay/proList');
            },
        }
    }
</script>

<style scoped>

</style>