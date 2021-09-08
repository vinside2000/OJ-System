<template>
    <el-page-header @back="goBack" content="我的题集"> </el-page-header>

    <el-row>
        <el-col :span="16">
            <div class="grid-content bg-purple" v-for="list in showSelPro">
                <el-card :body-style="{ padding: '0px'}" shadow="hover" style="width: 80%;margin-left: 100px;margin-top: 20px">
                    <div>
                        <div style="padding: 10px;width: 150px;float: left">

                            <h3 style="text-indent: 2em">{{list.title}}</h3>
                            <div style="padding-top: 20px;margin-left: 20px">
                                <div style="float: left;width: 200px" class="sel">
                                    <span class="selOption" tabindex="1">A</span>
                                    <p>{{list.optionA}}</p>
                                </div>
                                <div style="float: left;width: 200px" class="sel">
                                    <span class="selOption" tabindex="2">B</span>
                                    <p>{{list.optionB}}</p>
                                </div>
                                <div style="float: left;width: 200px" class="sel">
                                    <span class="selOption" tabindex="3">C</span>
                                    <p>{{list.optionC}}</p>
                                </div>
                                <div style="float: left;width: 200px" class="sel">
                                    <span class="selOption" tabindex="4">D</span>
                                    <p>{{list.optionD}}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </el-card>
            </div></el-col>
        <el-col :span="8">
            <div class="grid-content bg-purple-light">

            </div></el-col>
    </el-row>



</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "SelectProblem",
        data(){
            return{
                showSelPro:[{}]
            }
        },
        created() {
            let proListUuid = sessionStorage.getItem("proListUuid");
            this.load(proListUuid);
        },
        methods:{
            load(uuid){
                request.get("/selPro/getSel/" + uuid).then(res => {
                    this.showSelPro = res.data;
                })
            },

            goBack() {
                this.$router.push('/stuLay/proList');
            },
        }
    }
</script>

<style scoped>
    .selOption {
        height: 30px;
        width: 30px;
        line-height: 30px;
        text-align: center;
        background: #F5F5F5;
        display: inline-block;
        margin-right: 10px;
        font-weight: bold;
        float: left;
        border-radius: 10%;
    }

    .selOption:hover {
        background: #dcdcdc;
        cursor: pointer;
    }

    .selOption:focus {
        background: #0DD1B2;
    }
    .sel {
        padding-top: 10px;
    }
</style>