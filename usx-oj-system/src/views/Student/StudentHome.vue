<template>
    <el-row :gutter="20">
        <el-col :span="5"><div class="grid-content bg-purple"></div></el-col>


        <el-col :span="14">
            <p style="display: block;margin-top: 20px">我的课程</p>
            <div class="grid-content bg-purple"  v-for="list in courseList">
                <el-col :span="20">
                    <div @click="toProList" style="margin-top: 20px;width: 320px;cursor: pointer;height: 250px">
                        <el-card :body-style="{ padding: '0px'}" shadow="hover">
                            <img :src="list.imgUrl" class="image" style="width: 320px;height: 180px">
                            <div style="padding: 10px;">
                                <span>{{list.course}}</span>
                                <div class="bottom">
                                    <time class="time">{{list.setDate}}</time>
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
    export default {
        name: "StudentHome",
        data() {
            return {
                courseList:[
                    {
                        imgUrl: 'https://python123.io/images/7a/01/32a07f0cede6bceda81628ad36c6.jpg',
                        setDate: '2021-06-05',
                        course: 'Python编程技术',
                    },
                    {
                        imgUrl: require('@/assets/img/login.jpg'),
                        setDate: '2021-06-05',
                        course: '高级语言',
                    },
                    {
                        imgUrl: require('@/assets/img/login.jpg'),
                        setDate: '2021-06-05',
                        course: '高级语言',
                    },
                    {
                        imgUrl: require('@/assets/img/login.jpg'),
                        setDate: '2021-06-05',
                        course: '高级语言',
                    },
                ],

            };
        },
        methods:{
            toProList(){
              this.$router.push('/stuLay/proList')
            },
        }
    }
</script>

<style scoped>

</style>