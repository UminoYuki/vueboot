<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入昵称" suffix-icon="el-icon-search" v-model="search"></el-input>

      <el-button style="margin-left: 5px;" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 5px;" type="warning" @click="reSet">重置</el-button>
    </div>
    <div style="margin: 10px 0;">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-style="{background:'#eee'}" @selection-change="handleSelectionChange">

      <el-table-column type="selection" width="55"></el-table-column>

      <el-table-column prop="id" label="ID" width="50"></el-table-column>

      <el-table-column prop="username" label="用户名" width="140">
      </el-table-column>
      <el-table-column prop="nickName" label="昵称" width="120">
      </el-table-column>
      <el-table-column prop="age" label="年龄">
      </el-table-column>
      <el-table-column prop="sex" label="性别">
      </el-table-column>
      <el-table-column prop="address" label="地址">
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <!--              编辑-->
          <el-button type="success" @click="handleEdit(scope.row)"><i class="el-icon-edit"></i></el-button>
          <!--              删除-->
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='点错了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm = "handleDel(scope.row.id)"
          >
            <el-button type="danger" slot="reference"><i class="el-icon-delete"></i></el-button>
          </el-popconfirm>
        </template>
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

    <!-- Form -->

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="35%">
      <el-form label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
          <el-radio v-model="form.sex" label="未知">未知</el-radio>
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="this.dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveHandle">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "User",
  data(){
    return {
      tableData:[],
      multipleSelection: [],
      total:0,
      pageSize:5,
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
  methods: {
    load(){
      request.get("user",{
        params:{
          pageIndex:this.pageIndex,
          pageSize:this.pageSize,
          search:this.search
        }
      }).then(res =>{
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    //保存至后端
    saveHandle(){
      if(this.form.id){  //更新
        request.put("/user", this.form).then(res =>{
          console.log(res)
          if(res.code == '0'){
            this.$message({
              type:"success",
              message:"更新成功!",
            })
          }else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load();   //更新数据
          this.dialogFormVisible = false  //弹窗关闭
        })
      }else{
        request.post("/user", this.form).then(res =>{
          console.log(res)
          if(res.code == '0'){
            this.$message({
              type:"success",
              message:"新增成功!",
            })
          }else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load();   //更新数据
          this.dialogFormVisible = false  //弹窗关闭
        })
      }
    },
    reSet(){
      this.username = ""
      this.load()
    },
    exp(){
      window.open("http://localhost:9090/user/export")
    },
    handleSizeChange(pageSize){         //改变当前每个页的触发
      this.pageSize = pageSize
      this.load()
      // this.pageIndex = 1
    },
    handleCurrentChange(pageIndex){     //改变当前页码触发
      this.pageIndex = pageIndex
      this.load()
      // this.pageIndex = 1
    },
    //新增数据方法
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}     //清空表单
    },

    handleEdit(row){
      // this.form = Object.assign({},row)
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },

    handleDel(id){
      request.delete("/user/"+id).then(res => {
        if(res.code == '0'){
          this.$message({
            type:"success",
            message:"删除成功!",
          })
        }else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.load();   //更新数据
      })
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },
    handleExcelImport(){
      this.$message.success("上传成功")
      this.load()
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)  // [{},{},{}] => [1,2,3]
      // let string = "("+ids.join(",")+")";
      request.post("/user/batch",ids).then(res => {
        if(res.data){
          this.$message.success("保存成功")
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    }
  }
}
</script>

<style scoped>

</style>