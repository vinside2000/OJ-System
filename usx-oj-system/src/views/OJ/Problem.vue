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
                    prop="status"
                    label=""
                    width="50px"
                    id="statusRows"
            >
                <template #default="scope">
                    <span v-if="scope.row.status ==='1'" class="el-icon-check"></span>
                    <span v-else></span>
<!--                    <span>{{scope.row.status}}</span>-->
                </template>
            </el-table-column>
            <el-table-column
                    prop="index"
                    label="序号"
                    width="200px"
            >
            </el-table-column>
            <el-table-column
                    prop="title"
                    label="标题"
                    width="600px"
            >
            </el-table-column>
            <el-table-column
                    prop="proScore"
                    label="分数"
                    width="200px"
            >
            </el-table-column>
            <el-table-column
                    prop="myScore"
                    label="我的得分">
            </el-table-column>
        </el-table>
    </div>

    <div class="block" style="margin: 20px 20px">
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
        name: "Problem",
        data(){
            return{
                tableData: [{
                    status: '1',
                    index: '1',
                    title: 'a+b',
                    proScore: '10',
                    myScore: '10'
                }, {
                    status: '0',
                    index: '2021-05-04',
                    title: '2021-07-02',
                    proScore: '10',
                    myScore: '10'
                }, {
                    status: '0',
                    index: '2021-05-01',
                    title: '2021-07-16',
                    proScore: '10',
                    myScore: '10'
                }, {
                    status: '0',
                    index: '2021-05-03',
                    title: '2021-07-20',
                    proScore: '10',
                    myScore: '10'
                }],
                currentPage: 1,

            }
        },
        methods:{
            handleSizeChange(val) {
                // console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                // console.log(`当前页: ${val}`);
            },
            cellStyle({row, column, rowIndex, columnIndex}){
                // console.log(row.status)
                if (columnIndex === 2){
                    return "cursor:pointer;color:rgb(18,157,250);"
                }
            } ,
            goto(row,column,event,cell){
                // console.log(row);
                if (column.property === "title"){
                    this.$router.push("/proLay/proDel")
                }
            },
        }
    }
</script>

<style scoped>
    .el-icon-check{
        color: red;
        font-weight: bolder;
    }
</style>