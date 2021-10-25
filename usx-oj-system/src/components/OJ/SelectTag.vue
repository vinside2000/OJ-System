<template>
  <div class="select-tag">
<!--    <a :href="autoHref(count)" style="text-decoration: none" v-for="count in Number(selCount)">-->
      <span class="tag" :class="{change_color:isFlag}">{{ count }}</span>
<!--    </a>-->
  </div>
</template>

<script>
export default {
  name: "SelectTag",
  props:['count','isFlag'],
  data(){
    return{

    }
  },
  // watch: {
  //   num(newVal) {
  //     // this.newNum = newVal//对父组件传过来的值进行监听，如果改变也对子组件内部的值进行改变
  //     // console.log(this.newNum)
  //     // console.log("c:"+this.count)
  //     console.log(this.num)
  //   }
  // },
  computed:{

  },
  methods:{
    autoHref: function (count) {
      return "#" + count;
    },
  }
}
</script>

<style scoped>
.change_color{
  background: deepskyblue;
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
  background: #F7F7F7;
  border-radius: 2px;
  float: left;
}
</style>