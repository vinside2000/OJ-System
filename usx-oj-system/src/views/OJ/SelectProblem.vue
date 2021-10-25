<template>
  <el-page-header @back="goBack" content="我的题集"></el-page-header>

  <div v-loading="loading">
    <el-row>
      <el-col :span="16">

        <div class="grid-content bg-purple" v-for="(list, index) in showSelPro">
          <select-problem-item :index="index" :list="list" @handleChange="changeNUm"></select-problem-item>
          <!--                    <el-card :body-style="{ padding: '0px'}" shadow="hover" style="width: 80%;margin-left: 100px;margin-top: 20px">-->
          <!--                        <div>-->
          <!--                            <div style="padding: 10px;width: 90%;float: left">-->
          <!--                                <h3 style="text-indent: 1em" ><a :name="autoIndex(index)"></a> {{index + 1}}.&nbsp <p style="display: inline-block;font-size: large;font-weight: normal;margin-left: -25px">{{list.title}}</p> </h3>-->
          <!--                                <div style="padding-top: 20px;margin-left: 20px" :id="autoId(index)">-->
          <!--                                    <div style="float: left;width: 100%" class="sel">-->
          <!--                                        <span class="selOption" tabindex="1"-->
          <!--                                              :class="{span_color:isTableIndex === 1 && changeColor === index}" @click="select(index,1)" >A</span>-->
          <!--                                        <p>{{list.optionA}}</p>-->
          <!--                                    </div>-->
          <!--                                    <div style="float: left;width: 100%" class="sel">-->
          <!--                                        <span class="selOption" tabindex="2"-->
          <!--                                              :class="{span_color:isTableIndex === 2 && changeColor === index}" @click="select(index,2)">B</span>-->
          <!--                                        <p>{{list.optionB}}</p>-->
          <!--                                    </div>-->
          <!--                                    <div style="float: left;width: 100%" class="sel">-->
          <!--                                        <span class="selOption" tabindex="3"-->
          <!--                                              :class="{span_color:isTableIndex === 3 && changeColor === index}" @click="select(index,3)">C</span>-->
          <!--                                        <p>{{list.optionC}}</p>-->
          <!--                                    </div>-->
          <!--                                    <div style="float: left;width: 100%" class="sel">-->
          <!--                                        <span class="selOption" tabindex="4"-->
          <!--                                              :class="{span_color:isTableIndex === 4 && changeColor === index}" @click="select(index,4)">D</span>-->
          <!--                                        <p>{{list.optionD}}</p>-->
          <!--                                    </div>-->
          <!--                                </div>-->
          <!--                            </div>-->
          <!--                        </div>-->
          <!--                    </el-card>-->
        </div>

        <!--                <el-radio-group v-model="radio" size="small">-->
        <!--                    <div style="padding-top: 10px">-->
        <!--                        <el-radio-button label="A"></el-radio-button>-->
        <!--                    </div>-->
        <!--                    <div style="padding-top: 10px">-->
        <!--                        <el-radio-button label="B"></el-radio-button>-->
        <!--                    </div>-->
        <!--                    <div style="padding-top: 10px">-->
        <!--                        <el-radio-button label="C"></el-radio-button>-->
        <!--                    </div>-->
        <!--                    <div style="padding-top: 10px">-->
        <!--                        <el-radio-button label="D"></el-radio-button>-->
        <!--                    </div>-->
        <!--                </el-radio-group>-->

      </el-col>
      <el-col :span="8">
        <div class="grid-content bg-purple-light">
          <el-card :body-style="{ padding: '0px'}" shadow="hover"
                   style="width: 300px;margin-top: 20px;margin-left: -20px; height: 300px;position: fixed">
            <!--                      提交-->
            <div style="margin:20px 0 0 20px">
              <p style="font-size: small">操作</p>

              <el-popconfirm title="提交后还可以更改，确定提交吗？"
                              @confirm="submitAnswer">
                <template #reference>
                  <el-button icon="el-icon-check" type="success" size="mini" style="margin-top: 5px">提交</el-button>
                </template>
              </el-popconfirm>
            </div>

            <!--                      答题卡-->
            <div style="margin:20px 0 0 20px">
              <p style="font-size: small">答题卡</p>
              <div style="margin-left: -20px">
                <a :href="autoHref(count)" style="text-decoration: none" v-for="count in Number(selCount)">
<!--                  <select-tag :count="count" :sels="sels" :isFlag=isFlag(count)></select-tag>-->
                  <span class="tag" :class="{change_color:isFlag(count)}">{{ count }}</span>
                </a>
              </div>
            </div>

            <br><br>
            <!--                      总分-->
            <div style="margin:20px 0 0 20px">
              <p style="font-size: small;color: red">总分<span style="font-size: 10px;color: #1a1a1a">（{{ score }}分&nbsp每题5分）</span></p>
<!--              <p style="text-indent: 10px;padding-top: 10px">{{ score }}</p>-->
              <p style="padding-top: 20px;font-size: small">我的得分</p>
              <p style="text-indent: 2em; padding-top: 10px;font-size: small;color: red">{{myMark}}</p>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>
  </div>


</template>

<script>
import request from "../../utils/request";
import SelectProblemItem from "../../components/OJ/SelectProblemItem";
import SelectTag from "@/components/OJ/SelectTag";

const s = new Set()//记录题号
const m = new Map()//记录id和答案

export default {
  name: "SelectProblem",
  components: {SelectTag, SelectProblemItem},
  data() {
    return {
      myMark:'0',
      sels:[],
      sel:'',
      isTableIndex: '',
      changeColor: '',
      radio: '',
      selCount: '',
      score: '',
      loading: false,
      showSelPro: [{}]
    }
  },
  created() {
    this.loading = true;
    let proListUuid = sessionStorage.getItem("proListUuid");
    this.load(proListUuid);

    let selCount = sessionStorage.getItem("selCount");
    this.selCount = selCount;
    this.score = this.selCount * 5;
  },
  methods: {
    //提交答题卡
    submitAnswer(){
      let obj= Object.create(null);
      for (let[k,v] of m) {
        obj[k] = v;
      }
      let jsonObject = JSON.stringify(obj);
      // console.log(jsonObject)
      request.post("/selPro/answer",{
        studentUuid: JSON.parse(sessionStorage.getItem("student")).uuid,
        proListUuid: sessionStorage.getItem("proListUuid"),
        answerMap: jsonObject
      }).then(res => {
        if (res.code === '0') {
          this.$message({
            type: 'success',
            message: '提交成功'
          })
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          })
        }
      })
    },

    //判断题目是否已经回答，改变颜色
    isFlag(count){
      for (let i = 0; i < this.sels.length; i++) {
        if (count === this.sels[i]){
          return true;
        }
      }return false
    },

    changeNUm(abs){
      this.sel = abs//子组件SelectProblemItem传来的对象

      s.add(this.sel.index)
      m.set(this.sel.id,this.sel.answers)
      // console.log(m)
      // this.sels.push(this.sel.index)
      this.sels = Array.from(s)
      // console.log(this.sels)
      // console.log(num)
    },

    //加载选择题
    load(uuid) {
      request.get("/selPro/getSel/" + uuid).then(res => {
        this.showSelPro = res.data;
        this.loading = false;
      })
    },

    goBack() {
      this.$router.push('/stuLay/proList');
    },

    // autoIndex:function(index){
    //   index = index + 1;
    //   return index
    // },

    autoHref: function (count) {
      return "#" + count;
    },

    // //自动赋值不同的id
    // autoId:function(index){
    //     index = index + 1;
    //     // console.log("selOp_" + index);
    //     return "selOp_" + index;
    // },
    //
    // select(id,tabindex){
    //     this.isTableIndex = tabindex
    //     console.log(id)
    //     this.changeColor = id
    // }
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

/*.selOption:focus {*/
/*    background: #67C23A;*/
/*}*/
.sel {
  padding-top: 10px;
}

.tag {
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

.span_color {
  background: #67C23A;
}

.change_color{
  background-color: #33a3dc;
  color: white;
}
</style>