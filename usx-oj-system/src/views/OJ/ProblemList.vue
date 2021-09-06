<template>
    <div >
        <el-table
                :data="tableData"
                stripe
                style="width: 98%;margin: 20px;"
                highlight-current-row="true"
                :cell-style="cellStyle"
                @cell-click="goto"
            >
            <el-table-column
                    prop="startDate"
                    label="开始时间"
                    sortable
                    width="300px"
            >
            </el-table-column>
            <el-table-column
                    label="结束时间"
                    sortable
                    width="500px"
            >
                <template #default="scope">
                    <span v-if="scope.row.endDate < currentTime" style="color: red">{{scope.row.endDate}}</span>
                    <span v-else>{{scope.row.endDate}}</span>
                    <!--                    <span>{{scope.row.status}}</span>-->
                </template>
            </el-table-column>
            <el-table-column
                    prop="problem"
                    label="题集"
            >
            </el-table-column>
            <el-table-column
                    prop="status"
                    label="状态">
            </el-table-column>
        </el-table>
    </div>

    <div class="block" style="margin: 20px 20px;">
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[10, 20]"
                :page-size="10"
                layout="total, sizes, prev, pager, next, jumper"
                :total="40">
        </el-pagination>
    </div>

</template>

<script>
    export default {
        name: "ProblemList",
        data(){
            return {
                tableData: [{
                    startDate: '2021-05-02',
                    endDate: '2021-07-01',
                    problem: 'python简介',
                    status: '已开启'
                }, {
                    startDate: '2021-05-04',
                    endDate: '2021-07-02',
                    problem: '初识Python',
                    status: '已开启'
                }, {
                    startDate: '2021-05-01',
                    endDate: '2021-07-16',
                    problem: '基本语法',
                    status: '已关闭'
                }, {
                    startDate: '2021-05-03',
                    endDate: '2021-08-20',
                    problem: '数组',
                    status: '已关闭'
                }],
                currentPage: 1,
                currentTime: ''
            }
        },
        created:
            function() {
            // var aData = new Date();
            // console.log(aData) //Wed Aug 21 2019 10:00:58 GMT+0800 (中国标准时间)
            //
            // this.value =
            //     aData.getFullYear() + "-" + (aData.getMonth() + 1) + "-" + aData.getDate();
            // console.log(this.value) //2019-8-20
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
                console.log(this.currentTime)
        },
        methods:{
            goto(row,column,event,cell){
                console.log(row);
                if (column.property === "problem"){
                    this.$router.push("/proLay/problem")
                }
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },
            cellStyle({row, column, rowIndex, columnIndex}){
                if (columnIndex === 2){
                    return "cursor:pointer;padding:20px 0;color:rgb(18,157,250);"
                }
            } ,
        }
    }
</script>

<style scoped>


</style>