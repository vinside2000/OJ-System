<template>
    <el-row :gutter="20">
        <el-col :span="12">
            <div class="grid-content bg-purple"
                 style="height: 80vh;border: 1px solid #D7DAE2;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);padding: 20px" >
                <!--                题目-->
                <div style="margin-top: 30px">
                    <p v-html="content"></p>
                </div>
            </div>
        </el-col>
        <el-col :span="12">
            <div class="grid-content bg-purple"
                 style="height: 80vh;border: 1px solid #D7DAE2;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);padding: 20px">
                <CodeMirror/>
            </div>
        </el-col>
    </el-row>
</template>

<script>

    import CodeMirror from "../../components/OJ/CodeMirror";
    import request from "../../utils/request";

    export default {
        name: "ProblemDetail",
        components: {CodeMirror},
        data() {
            return {
                content:'',
            }
        },
        created() {
            let proId = sessionStorage.getItem("proId");
            this.load(proId);
        },
        methods: {
            //加载题目内容
            load(id){
                request.get("/pro/" + id).then(res => {
                    this.content = res.data.content;
                })
            }
        }
    }

</script>

<style scoped>

</style>