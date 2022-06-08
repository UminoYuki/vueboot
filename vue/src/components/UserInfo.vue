<template>
  <el-card style="width: 500px; padding: 20px;">
    <el-form label-width="80px" size="small">
      <el-form-item label="用户名">
        <el-input v-model="form.username" disabled auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input type="textarea" v-model="form.address" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveHandle">确定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "UserInfo",
  data(){
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.request.get("/user/username/" + this.user.username).then(res => {
      if(res.code === '200'){
        this.form = res.data
      }
    })
  },
  methods: {
    saveHandle(){
      request.post("/user", this.form).then(res => {
        if(res.data){
          this.$message.success("保存成功")
        }else{
          this.$message.error("保存失败")
        }
      })
    },
  }
}
</script>

<style scoped>

</style>