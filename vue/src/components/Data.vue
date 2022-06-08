<template>
  <div>

    <el-table
        :data="tableData"
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
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Data",
  data(){
    return{
      tableData:[]
    }
  },
  created() {
    // 请求分页查询数据
    // 调用方法
    this.load()
  },
  methods:{
    load(){
      request.get("data",{
      }).then(res =>{
        console.log('hellog')
        this.tableData = res
      })
    },
  },


}
</script>

<style scoped>

</style>