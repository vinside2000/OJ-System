<template>
  <div class="select-problem-item">
    <el-card :body-style="{ padding: '0px'}" shadow="hover" style="width: 80%;margin-left: 100px;margin-top: 20px">
      <div>
        <div style="padding: 10px;width: 90%;float: left">
          <h3 style="text-indent: 1em"><a :name="autoIndex(index)"></a> {{ index + 1 }}.&nbsp <p
              style="display: inline-block;font-size: large;font-weight: normal;margin-left: -25px">
            {{ list.title }}</p></h3>
          <div style="padding-top: 20px;margin-left: 20px" :id="autoId(index)">
            <div style="float: left;width: 100%" class="sel">
                                        <span class="selOption" tabindex="1"
                                              :class="{span_color:isTableIndex === 1 && changeColor === index}"
                                              @click="select(index,1,$event,list.id)" sel-option="A">A</span>
              <p>{{ list.optionA }}</p>
            </div>
            <div style="float: left;width: 100%" class="sel">
                                        <span class="selOption" tabindex="2"
                                              :class="{span_color:isTableIndex === 2 && changeColor === index}"
                                              @click="select(index,2,$event,list.id)" sel-option="B">B</span>
              <p>{{ list.optionB }}</p>
            </div>
            <div style="float: left;width: 100%" class="sel">
                                        <span class="selOption" tabindex="3"
                                              :class="{span_color:isTableIndex === 3 && changeColor === index}"
                                              @click="select(index,3,$event,list.id)" sel-option="C">C</span>
              <p>{{ list.optionC }}</p>
            </div>
            <div style="float: left;width: 100%" class="sel">
                                        <span class="selOption" tabindex="4"
                                              :class="{span_color:isTableIndex === 4 && changeColor === index}"
                                              @click="select(index,4,$event,list.id)" sel-option="D">D</span>
              <p>{{ list.optionD }}</p>
            </div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "SelectProblemItem",
  props: ['index', "list"],
  data() {
    return {
      isTableIndex: '',
      changeColor: '',
      answers:'',
    }
  },
  methods: {
    //自动赋值不同的id
    autoId: function (index) {
      index = index + 1;
      // console.log("selOp_" + index);
      return "selOp_" + index;
    },

    select(index, tabindex,event,id) {
      this.isTableIndex = tabindex
      // console.log(id)
      this.changeColor = index
      // this.$emit('handleChange', index+1)
      this.answers = event.currentTarget.getAttribute('sel-option')
      const abs = {'index':index+1,'answers':this.answers,"id":id}
      this.$emit('handleChange', abs);//将对象传给父组件
      // console.log(id)
      // console.log(this.answers)
    },
    autoIndex: function (index) {
      index = index + 1;
      return index
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

.error_color{
  background: red;
}
</style>