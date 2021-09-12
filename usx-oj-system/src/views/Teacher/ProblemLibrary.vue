<template>
<!--    <el-tabs v-model="activeName" @tab-click="handleClick">-->
<!--        <el-tab-pane label="题集" name="first">-->
            <div style="margin: 20px 0;">
                <div>


                    <!--搜索框-->
                    <el-input
                            placeholder="请输入题集名称"
                            v-model="search"
                            type="text"
                            clearable
                            style="width: 300px"
                    >
                        <template #append>
                            <el-button icon="el-icon-search" @click="load"></el-button>
                        </template>
                    </el-input>


                    <!--创建按钮-->
                    <el-button type="primary"
                               @click="createProList"
                               style="float: right;"
                               class="el-icon-circle-plus">
                        新建题集
                    </el-button>
                </div>
          

            <!--创建题集-->
                <el-dialog title="新建题集" v-model="dialogFormVisible" width="500px">
                    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
                        <el-form-item label="创建者" prop="author" label-width="100px">
                            <el-input v-model="ruleForm.author" disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="题集名称" prop="name" label-width="100px">
                            <el-input v-model="ruleForm.name"></el-input>
                        </el-form-item>
                        <el-form-item prop="startDate" label="开始日期" label-width="100px">
                            <el-date-picker type="date" placeholder="选择开始日期" v-model="ruleForm.startDate" style="width: 100%;"></el-date-picker>
                        </el-form-item>
                        <el-form-item prop="endDate" label="结束日期" label-width="100px">
                            <el-date-picker type="date" placeholder="选择结束日期" v-model="ruleForm.endDate" style="width: 100%;"></el-date-picker>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                            <el-button @click="resetForm('ruleForm')">重置</el-button>
                        </el-form-item>
                    </el-form>
                </el-dialog>
            </div>

            <!--题集展示-->
            <el-table
                    :data="tableData"
                    border
                    style="width: 100%"
                    :cell-style="cellStyle"
                    @cell-click="toProblem">
                <el-table-column
                        prop="uuid"
                        label="编号"
                        v-if="false">
                </el-table-column>
                <el-table-column
                        type="index"
                        align="center"
                        :index="indexFn(0)"
                        label="序号"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="题集名称">
                </el-table-column>
                <el-table-column
                        prop="startDate"
                        label="开始时间"
                        width="150"
                        sortable>
                </el-table-column>
                <el-table-column
                        prop="endDate"
                        label="结束时间"
                        width="150"
                        sortable>
                    <template #default="scope">
                        <span v-if="scope.row.endDate < currentTime" style="color: red">{{scope.row.endDate}}</span>
                        <span v-else>{{scope.row.endDate}}</span>
                        <!--                    <span>{{scope.row.status}}</span>-->
                    </template>
                </el-table-column>
                <el-table-column
                        prop="proCount"
                        label="程序题数量"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="selCount"
                        label="选择题数量"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="author"
                        label="创建者"
                        width="150">
                </el-table-column>
                <el-table-column
                        label="操作"
                        width="100">
                    <template #default="scope">
                        <el-button type="primary" size="mini" @click="update(scope.row.uuid)">编辑</el-button>
                    </template>
                </el-table-column>
            </el-table>

<!--    修改题集-->
            <el-dialog title="编辑题集" v-model="dialogFormVisible2" width="500px">
                <el-form :model="updateForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
                    <el-form-item label="创建者" prop="author" label-width="100px">
                        <el-input v-model="updateForm.author" disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="题集名称" prop="name" label-width="100px">
                        <el-input v-model="updateForm.name"></el-input>
                    </el-form-item>
                    <el-form-item prop="startDate" label="开始日期" label-width="100px">
                        <el-date-picker type="date" placeholder="选择开始日期" v-model="updateForm.startDate" style="width: 100%;"></el-date-picker>
                    </el-form-item>
                    <el-form-item prop="endDate" label="结束日期" label-width="100px">
                        <el-date-picker type="date" placeholder="选择结束日期" v-model="updateForm.endDate" style="width: 100%;"></el-date-picker>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitUpdate">提交</el-button>
                        <el-button @click="update(updateForm.uuid)">重置</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>



<!--&lt;!&ndash;            分页组件&ndash;&gt;-->
<!--            <div style="float: left;margin-top: 20px">-->
<!--                <el-pagination-->
<!--                        @size-change="handleSizeChange"-->
<!--                        @current-change="handleCurrentChange"-->
<!--                        :current-page="currentPage"-->
<!--                        :page-sizes="[25,50]"-->
<!--                        :page-size="25"-->
<!--                        layout="total, sizes, prev, pager, next, jumper"-->
<!--                        :total="100">-->
<!--                </el-pagination>-->
<!--            </div>-->
<!--        </el-tab-pane>-->

<!--        <el-tab-pane label="试题" name="second">-->
<!--            <TeacherProblem/>-->
<!--        </el-tab-pane>-->
<!--    </el-tabs>-->
</template>

<script>
    import {ElMessage} from "element-plus";
    import TeacherProblem from "./TeacherProblem";
    import CourseAside from "../../components/Course/CourseAside";
    import request from "../../utils/request";

    export default {
        name: "ProblemLibrary",
        components:{CourseAside},
        data() {
            return {
                currentTime:'',
                search:'',
                courseUuid: '',
                teacherName:'',
                currentPage: 1,
                // activeName: 'first',
                dialogFormVisible: false,
                dialogFormVisible2: false,
                tableData: [{}],
                ruleForm:{
                    name:'',
                    startDate:'',
                    endDate:''
                },
                updateForm:{endDate:''},
                rules: {
                    name: [
                        {  required: true, message: '请输入题集名称', trigger: 'change' }
                    ],
                    startDate: [
                        {  type: 'date', required: true, message: '请选择题集开始日期', trigger: 'change' }
                    ],
                    endDate: [
                        {  type: 'date', required: true, message: '请选择题集结束日期', trigger: 'change' }
                    ],
                }
            };
        },
        created() {
            let teacherJson = sessionStorage.getItem("teacher");//从session中获取到teacherJSON字符串
            this.teacherName = JSON.parse(teacherJson).name;
            this.ruleForm.author = this.teacherName;

            let courseUuidJson = sessionStorage.getItem("courseUuid");
            this.courseUuid = courseUuidJson;


            //获取当前日期
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;				//月
            var dateDate = date.getDate();					//日
            var dateHours = date.getHours();               //时
            var dateMinutes = date.getMinutes();           //分
            var dateSeconds = date.getSeconds();           //秒
            if (month >= 1 && month <= 9)  month = "0" + month;
            if (dateDate >= 0 && dateDate <= 9)  dateDate = "0" + dateDate;
            if(dateHours >= 0 && dateHours <= 9) dateHours="0" + dateHours;
            if(dateMinutes >= 0 && dateMinutes <= 9) dateMinutes="0" + dateMinutes;
            if(dateSeconds >= 0 && dateSeconds <= 9) dateSeconds="0" + dateSeconds;
            var currentdate = year + "-" + month + "-" + dateDate ;
            this.currentTime = currentdate;

            this.load();
        },
        methods: {
            //更新题集，回显数据
            update(uuid){
                // console.log(uuid)
                this.dialogFormVisible2 = true;
                request.get("/proList/getOne/" + uuid).then(res => {
                    this.updateForm = res.data;
                })
            },

            //提交更新
            submitUpdate(){
                console.log(this.updateForm)
              request.post("/proList/update",this.updateForm).then(res => {
                  if (res.code === '0'){
                      this.$message({
                          type:'success',
                          message:'修改成功'
                      })
                      this.dialogFormVisible2=false;
                      this.load(this.courseUuid);
                  }else {
                      this.$message({
                          type:'error',
                          message:res.msg
                      })
                  }
              })
            },

            //题集表格加载
            load(){
              request.get("/proList", {
                  params:{
                   search:this.search,
                   courseUuid: this.courseUuid
                  }
                }).then(res => {
                  this.tableData = res.data
              })
            },
            //表格序号自增
            indexFn(index) {
                index = index + 1
                return index
            },
            cellStyle({row, column, rowIndex, columnIndex}) {
                if (columnIndex === 1) {
                    return "cursor:pointer;padding:20px 0;color:rgb(18,157,250);"
                }
            },

            //跳转到题集下的题目
            toProblem(row, column, event, cell) {
                // console.log(row.uuid);
                if (column.property === "name") {
                    sessionStorage.setItem("proListUuid",row.uuid);
                    sessionStorage.setItem("proListName",row.name);
                    this.$router.push("/courseLay/pro");
                }
            },
            createProList(){
                this.dialogFormVisible=true;
                this.resetForm("ruleForm");
            },

            //创建题集到该课程下
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        request.post("/proList",{
                            author:this.ruleForm.author,
                            name:this.ruleForm.name,
                            startDate:this.ruleForm.startDate,
                            endDate:this.ruleForm.endDate,
                            courseUuid:this.courseUuid
                        }).then(res => {
                            if (res.code === '0'){
                                this.$message({
                                    type:'success',
                                    message:'创建成功'
                                })
                                this.dialogFormVisible=false;
                                this.load(this.courseUuid);
                            }else {
                                this.$message({
                                    type:'error',
                                    message:res.msg
                                })
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },
        }
    }
</script>

<style scoped>

</style>