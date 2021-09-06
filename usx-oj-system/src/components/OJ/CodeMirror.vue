<template>
    <div>

        <el-tag  round style="margin-bottom: 10px">Python在线编辑器</el-tag>
        <!--        编辑窗口-->
        <el-card style="width:100%;height: 500px;" :body-style="{padding:'0'}">
                <textarea id="code" ></textarea>
        </el-card>

<!--        运行按钮-->
        <el-button size="mini" type="success"  round  @click="doSubmit()" style="margin: 5px 0;float: right">运行<i class="el-icon-caret-right el-icon--right"></i></el-button>

<!--        运行信息-->
        <el-card style="width:100%;height: 120px;margin-top: 25px;" :body-style="{padding:'0'}" shadow="hover">
                <el-alert
                        title="运行信息"
                        type="success"
                        :closable="false"
                        style="height: 40px;"
                >
                </el-alert>
                <div style="padding: 20px">
                    <p>运行耗时(毫秒)：{{result.excuteDurationTime}}</p>
                    <p>编译状态：{{result.message}}</p>
                </div>
            </el-card>

    </div>
</template>

<script>
    //引入全局实例
    import  codeMirror  from 'codemirror'

    //核心样式
    import 'codemirror/lib/codemirror.css'
    //语法高亮
    import 'codemirror/mode/clike/clike.js'
    import 'codemirror/mode/python/python.js'
    const CodeMirror = window.CodeMirror || codeMirror


    export default {
        name: "CodeMirror",
        data () {
            return {
                ifLimitTime:false,//是否限时
                ifArgs:false,//是否输入参数
                postData:{//用户界面输入信息
                    excuteTimeLimit:"",//限时
                    excuteArgs:"",//输入参数
                    pythonSource:"",//源码
                },
                result:{//用户得到结果
                    message:"暂未编译",//编译状态
                    excuteDurationTime:"0",//运行耗时(毫秒)
                    excuteResult:">",//执行结果
                },
                editorCodeMirror:null,//python编辑器文本域
            }
        },
        mounted () {
            // 初始化
            this.initCodeMirror();
        },
        methods: {
            initCodeMirror(){
                // 初始化编辑器实例，传入需要被实例化的文本域对象和默认配置
                this.editorCodeMirror = CodeMirror.fromTextArea(document.getElementById("code"),{
                    mode:"text/x-python",//选择对应代码编辑器的语言
                    tabSize:2,//缩进格式
                    lineNumbers:true,//显示行号
                    line:true,
                    matchBrackets:true,
                    theme: "dracula",
                    autofocus: true
                })
                this.editorCodeMirror.setSize("100%","500px");//设置编辑器大小

            },
            doSubmit(){//运行Python代码
                this.postData.pythonSource=this.editorCodeMirror.getValue();
                this.$http({
                    url: this.$http.adornUrl('/tool/PythonComplier'),
                    method: 'post',
                    data: this.$http.adornData({
                        'pythonSource':this.postData.pythonSource,
                        'excuteTimeLimit':this.postData.excuteTimeLimit,
                        'excuteArgs':this.postData.excuteArgs,
                    })
                }).then(({data}) => {
                    if (data && data.code === 0) {
                        this.result = data.result;
                        if(this.result.excuteDurationTime==-1){
                            this.$message.error(data.result.message)
                        }
                    } else {
                        // if(data.result.code===-100){
                        //     this.result =this.$message.error("代码不能为空");
                        // }
                        this.$message.error(data.msg)
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>