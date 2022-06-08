<!--还差个搜索查询-->
<template>
  <div>
<!--   功能区域-->
<!--    <div style="margin: 10px 0;">-->
<!--      <el-button style="margin-left: 5px;" type="primary" >新增</el-button>-->
<!--      <el-button style="margin-left: 5px;" type="primary" >导入</el-button>-->
<!--      <el-button style="margin-left: 5px;" type="primary" >导出</el-button>-->
<!--    </div>-->
<!--搜索区域-->
    <div style="margin: 10px 0;">
      <el-input style="width: 200px" placeholder="请输入关键字" suffix-icon="el-icon-search" v-model="search"></el-input>
      <el-button style="margin-left: 5px;" type="primary" @click="load" >查询</el-button>
    </div>
    <el-table
        :data="tableAllData"
        border
        style="width: 100%">
      <el-table-column
          prop="ts"
          label="日期"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="place"
          label="数据来源">
      </el-table-column>
      <el-table-column
          prop="temperature"
          label="温度">
      </el-table-column>
      <el-table-column
          prop="humidity"
          label="湿度">
      </el-table-column>
    </el-table>

    <div class="block" style="margin-top: 20px;">
      <span class="demonstration"></span>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageIndex"
          :page-sizes="[1, 2, 5, 10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "AllData",
  data(){
    return{
      tableAllData:[],
      multipleSelection: [],
      total:0,
      pageSize:10,
      pageIndex:1,
      search:'',
      form:{},
      dialogFormVisible:false
    }
  },
  created() {
    // 请求分页查询数据
    // 调用方法
    this.load()
  },
  methods:{
    load(){
      request.get("alldata",{
        params:{
          pageIndex:this.pageIndex,
          pageSize:this.pageSize,
          search:this.search
        }
      }).then(res =>{
        console.log(res)
        this.tableAllData = res.data.records
        this.total = res.data.total
      })
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
      // this.pageIndex = 1
    },
    handleCurrentChange(pageIndex){
      this.pageIndex = pageIndex
      this.load()
      // this.pageIndex = 1
    },
  }
}
</script>

<style scoped>

</style>