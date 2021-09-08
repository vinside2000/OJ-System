<template>

    <div style="padding: 20px">

        <!--搜索框-->
        <el-input
                placeholder="请输入题集名称"
                v-model="search"
                type="text"
                clearable
                style="width: 300px;margin-bottom: 20px"
        >
            <template #append>
                <el-button icon="el-icon-search" @click="load"></el-button>
            </template>
        </el-input>

        <!--    题集展示-->
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
                    label="试题数量"
                    width="150">
            </el-table-column>
            <el-table-column
                    prop="score"
                    label="得分"
                    width="150">
            </el-table-column>
        </el-table>
    </div>

<!--    <div class="block" style="margin: 20px 20px;">-->
<!--        <el-pagination-->
<!--                @size-change="handleSizeChange"-->
<!--                @current-change="handleCurrentChange"-->
<!--                :current-page="currentPage"-->
<!--                :page-sizes="[10, 20]"-->
<!--                :page-size="10"-->
<!--                layout="total, sizes, prev, pager, next, jumper"-->
<!--                :total="40">-->
<!--        </el-pagination>-->
<!--    </div>-->

</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "ProblemList",
        data(){
            return {
                search: '',
                courseUuid: '',
                tableData: [{}],
                currentPage: 1,
                currentTime: ''
            }
        },
        created:
            function() {
                let courseUuidJson = sessionStorage.getItem("courseUuid");
                this.courseUuid = courseUuidJson;

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
                // console.log(this.currentTime)
                this.load();
        },
        methods:{
            //加载题集
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

            //跳转到题集下的题目
            toProblem(row, column, event, cell) {
                // console.log(row.uuid);
                if (column.property === "name") {
                    sessionStorage.setItem("proListUuid",row.uuid);
                    sessionStorage.setItem("proCount",row.proCount);
                    this.$router.push("/proLay/problem");
                }
            },

            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },
            cellStyle({row, column, rowIndex, columnIndex}){
                if (columnIndex === 1){
                    return "cursor:pointer;padding:20px 0;color:rgb(18,157,250);"
                }
            } ,
        }
    }
</script>

<style scoped>


</style>