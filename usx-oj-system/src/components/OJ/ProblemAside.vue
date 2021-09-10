<template>
    <el-row class="tac" >
        <el-col :span="12">
            <el-menu
                    :uniqueOpened="true"
                    default-active="2"
                    class="el-menu-vertical-demo"
                    style="width: 400px;height: 100vh;float: left"
            >
                <el-menu-item index="1">
                    <i class="el-icon-menu"></i>
                    <template #title >题目列表</template>
                </el-menu-item>
                <el-divider></el-divider>
                <el-menu-item index="2" @click="toProgram">
                    <i class="el-icon-document"></i>
                    <template #title>编程题</template>
                </el-menu-item>
<!--                <a :href="'/proLay/proDel?id='+ list.proIndex" style="text-decoration: none"  v-for="list in spanData">-->
<!--                    <span class="tag">{{list.proIndex}}</span>-->
<!--                </a>-->
<!--                <div>-->
<!--                    <a :href="'/proLay/proDel'" style="text-decoration: none"  v-for="count in Number(proCount)" @click="setProId">-->
<!--                        <span class="tag">{{count}}</span>-->
<!--                    </a>-->
<!--                </div>-->
<!--                <el-menu-item style="height: 0"></el-menu-item>-->
<!--                <div style="padding-top: 50px">-->
<!--                    <el-divider></el-divider>-->
<!--                </div>-->
                <el-divider></el-divider>
                <el-menu-item index="3" @click="toSel">
                    <i class="el-icon-document"></i>
                    <template #title>选择题</template>
                </el-menu-item>
            </el-menu>
        </el-col>
    </el-row>

</template>

<script>
    export default {
        name: "ProblemAside",
        data(){
            return{
                proCount:'',
            }
        },
        created() {
            let proCount = sessionStorage.getItem("proCount");
            this.proCount = proCount;
        },
        methods:{
            //跳转程序题
            toProgram() {
                this.$router.push("/proLay/problem")
            },

            //跳转选择题
            toSel(){
                this.$router.push("/proLay/selPro")
            },

            //点击时设置session里的proId
            setProId(){

            }
        }
    }
</script>

<style scoped>
    .tag{
        display: block;
        width: 25px;
        height: 25px;
        font-size: small;
        margin-left: 20px;
        margin-top: 10px;
        text-align: center;
        line-height: 25px;
        color: #8590B8;
        background-color: #F7F7F7;
        border-radius: 2px;
        float: left;
    }
</style>