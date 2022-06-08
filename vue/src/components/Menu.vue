<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入性名" suffix-icon="el-icon-search" v-model="username"></el-input>

      <el-button style="margin-left: 5px;" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 5px;" type="warning" @click="reSet">重置</el-button>
    </div>
    <div style="margin: 10px 0;">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          style="margin:0 10px"
          confirm-button-text='确定'
          cancel-button-text='点错了'
          icon="el-icon-info"
          icon-color="red"
          title="确定删除吗？"
          @confirm = "delBatch"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload action="http://localhost:9090/user/import"
                 accept="xlsx"
                 :show-file-list="false"
                 :on-success="handleExcelImport"
                 style="display: inline-block; margin-right: 10px">
        <el-button type="primary">导入 <i class="el-icon-circle-plus-outline"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp">导出 <i class="el-icon-circle-plus-outline"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-style="{background:'#eee'}"
              row-key="id" default-expand-all @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="100"></el-table-column>
      <el-table-column prop="name" label="性名"></el-table-column>
      <el-table-column prop="path" label="路径"></el-table-column>
      <el-table-column label="图标" align="center">
        <template slot-scope="scope">
          <i :class="scope.row.icon" style="font-size: 18px;"/>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column prop="pid" label="父级ID"></el-table-column>
      <el-table-column prop="page_path" label="页面路径"></el-table-column>
      <el-table-column prop="sort_num" label="排序"></el-table-column>
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <!--          新增子菜单-->
          <el-button type="primary" @click="addChildren(scope.row.id)" v-if="!scope.row.pid && !scope.row.path">新增子菜单<i class="el-icon-plus"></i></el-button>
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

<!--    <div class="block" style="margin-top: 20px;">-->
<!--      <span class="demonstration"></span>-->
<!--      <el-pagination-->
<!--          @size-change="handleSizeChange"-->
<!--          @current-change="handleCurrentChange"-->
<!--          :current-page="pageIndex"-->
<!--          :page-sizes="[1, 2, 5, 10]"-->
<!--          :page-size="pageSize"-->
<!--          layout="total, sizes, prev, pager, next, jumper"-->
<!--          :total="total">-->
<!--      </el-pagination>-->
<!--    </div>-->

    <!-- Form -->

    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="35%">
      <el-form label-width="80px">
        <el-form-item label="性名">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
              <i :class="item.value"/>{{item.name}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="父级ID">
          <el-input v-model="form.pid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="页面路径">
          <el-input v-model="form.page_path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="form.sort_num" autocomplete="off"></el-input>
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
  name: "Menu",
  data(){
    return {
      tableData:[],
      multipleSelection: [],
      total:0,
      pageSize:5,
      pageIndex:1,
      username:"",
      form:{},
      dialogFormVisible:false,
      options:[]
    }
  },
  created() {
    // 请求分页查询数据
    this.load()
  },
  methods: {
    load(){
      request.get("http://localhost:9090/menu",{
        params:{
          name:this.username
        }
      }).then(res =>{
        console.log(res)
        this.tableData = res.data
      })
    },
    saveHandle(){
      request.post("/menu", this.form).then(res => {
        if(res.data){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    reSet(){
      this.username = ""
      this.load()
    },
    exp(){
      window.open("http://localhost:9090/menu/export")
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
    addChildren(pid){
      this.dialogFormVisible = true
      this.form = {}
      if(pid){
        this.form.pid = pid;
      }
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row){
      this.form = Object.assign({},row)
      this.dialogFormVisible = true

      // 请求图标数据
      this.request.get("/menu/icons").then(res => {
        this.options = res.data
      })
    },
    handleDel(id){
      request.delete("/menu/"+id).then(res => {
        if(res.data){
          this.$message.success("保存成功")
          this.load()
        }else{
          this.$message.error("保存失败")
        }
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
      request.post("/menu/batch",ids).then(res => {
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