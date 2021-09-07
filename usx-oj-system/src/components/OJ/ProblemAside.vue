<template>
    <el-row class="tac" >
        <el-col :span="12">
            <el-menu
                    :uniqueOpened="true"
                    default-active="2"
                    class="el-menu-vertical-demo"
                    style="width: 400px;height: 100vh;float: left"
            >
                <el-menu-item index="1" @click="titleGoto">
                    <i class="el-icon-menu"></i>
                    <template #title >题目列表</template>
                </el-menu-item>
                <el-divider></el-divider>
                <el-menu-item index="2">
                    <i class="el-icon-document"></i>
                    <template #title>编程题</template>
                </el-menu-item>
<!--                <a :href="'/proLay/proDel?id='+ list.proIndex" style="text-decoration: none"  v-for="list in spanData">-->
<!--                    <span class="tag">{{list.proIndex}}</span>-->
<!--                </a>-->
                <a :href="'/proLay/proDel'" style="text-decoration: none"  v-for="list in spanData">
                    <span class="tag">{{list.proIndex}}</span>
                </a>
            </el-menu>
        </el-col>
    </el-row>

</template>

<script>
    export default {
        name: "ProblemAside",
        data(){
            return{
                spanData:[{proIndex: '1'}],
            }
        },
        methods:{
            titleGoto() {
                this.$router.push("/proLay/problem")
            },

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