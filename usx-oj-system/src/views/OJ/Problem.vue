<template>
    <!--    主界面题目展示-->
    <el-table
            :data="tableData"
            border
            style="width: 100%"
            :cell-style="cellStyle"
            @cell-click="showProDetail">
        <el-table-column
                prop="id"
                label="编号"
                width="150"
                sortable>
        </el-table-column>
        <!--        <el-table-column-->
        <!--                prop="date"-->
        <!--                label="创建日期"-->
        <!--                width="150"-->
        <!--                sortable>-->
        <!--        </el-table-column>-->
        <el-table-column
                prop="title"
                label="标题">
        </el-table-column>
        <el-table-column
                prop="mark"
                label="分值"
                width="150">
        </el-table-column>
        <el-table-column
                prop="score"
                label="我的得分"
                width="150">
        </el-table-column>
    </el-table>

    <!--    分页控件-->
    <div style="float: left;margin-top: 20px">
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[10,20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
        </el-pagination>
    </div>
</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "Problem",
        data(){
            return{
                tableData: [{}],
                currentPage: 1,

            }
        },
        created() {
            this.load();
        },
        methods:{
            //加载程序题
            load(){
                request.get("/pro", {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.search,
                        problemListUuid: sessionStorage.getItem("proListUuid"),
                        courseUuid: sessionStorage.getItem("courseUuid")
                    }
                }).then(res => {
                    this.tableData = res.data.records;
                    this.total = res.data.total;
                    this.currentPage = res.data.current;
                })
            },

            handleSizeChange(val) {
                // console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                // console.log(`当前页: ${val}`);
            },
            cellStyle({row, column, rowIndex, columnIndex}){
                // console.log(row.status)
                if (columnIndex === 1){
                    return "cursor:pointer;color:rgb(18,157,250);"
                }
            } ,
            showProDetail(row,column,event,cell){
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