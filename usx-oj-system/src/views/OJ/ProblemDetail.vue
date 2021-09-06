<template>
    <el-row :gutter="20">
        <el-col :span="12">
            <div class="grid-content bg-purple"
                 style="height: 80vh;border: 1px solid #D7DAE2;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);padding: 20px" >
                <!--                题目-->
                <div style="margin-top: 10px;text-align: center"><h2>{{title}}</h2></div>
                <!--                题干-->
                <div style="margin-top: 30px">
                    <p>{{content}}</p>
                </div>
<!--                输入格式-->
                <div style="margin-top: 30px;">
                    <h3>输入格式</h3>
                    <p>{{inputDes}}</p>
                </div>
<!--                输出格式-->
                <div style="margin-top: 30px;">
                    <h3>输出格式</h3>
                    <p>{{outputDes}}</p>
                </div>
                <!--                输入样例-->
                <div style="margin-top: 30px;">
                    <h3>输入样例</h3>
                    <div style="margin-top: 10px;padding: 10px;background-color: #F5F5F5">
                        <p>{{inputExp}}</p>
                        </div>
                </div >
<!--                输出样例-->
                <div style="margin-top: 30px;">
                    <h3>输出样例</h3>
                    <div style="margin-top: 10px;padding: 10px;background-color: #F5F5F5">
                        <p>{{outputExp}}</p></div>
                </div>
                <!--                提示-->
                <div style="margin-top: 30px;" v-bind:hidden="isTipsExist()">
                    <h4>提示：</h4>
                    <div style="margin-top: 10px;padding: 10px;">
                        <p>{{tips}}</p>
                    </div>
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
    export default {
        name: "ProblemDetail",
        components: {CodeMirror},
        data() {
            return {
                title: '能否通过',
                content: '最近，很多老师参加PTA的监考执照考试。100分的卷子，得到95分及以上才算通过。一开始，通过者寥寥无几。有了题库之后，满分者屡见不鲜。某人参加了总分为a，通过分数线为b的考试，最终得分为c，请判断其能否通过该考试。若通过，输出“pass”及距离满分还差多少分；否则，输出“lose”及距离通过分数线还差多少分。',
                inputDes: '首先输入一个正整数T（T≤20），表示测试数据的组数，然后是T组测试数据。每组测试数据输入一行，包含3个整数a,b,c（0 &lt; a,b,c ≤ 1000），分别表示总分、通过分数线、最终得分。',
                outputDes: '对于每组测试数据，输出一行。若通过考试，输出“pass”及距离满分还差多少分；否则输出“lose”及距离通过分数线还差多少分。数据之间以一个空格分隔，引号不必输出。',
                inputExp: '2\n' +
                    '100 95 100\n' +
                    '100 95 70',
                outputExp: 'pass 0\n' +
                    'lose 25',
                tips: '',
            }
        },
        methods: {
            isTipsExist(){
                if (this.tips != ''){
                    return false;
                }else {
                    return true;
                }
            },
        }
    }

</script>

<style scoped>

</style>