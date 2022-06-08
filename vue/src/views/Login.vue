<template>
  <div style="width: 100%; height: 100vh; background-color: #ffffff; overflow:hidden">
    <div style="width: 400px; margin: 150px auto">
      <div style="color: #3f0202; font-size: 30px; text-align: center; padding:30px 0 ">登录</div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-icon><user /></el-icon>
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input v-model="form.password" placeholder="密码" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style = "width:100%" type="primary" @click="login">登陆</el-button>
        </el-form-item>
        <el-form-item>
          <el-button style = "width:100%" type="primary" @click="jump">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>

</template>

<script>

export default {
  name: "Login",
  data(){
    return {
      form: {},
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.request.post("/user/login",this.form).then(res => {
            console.log(this.form)
            if(res.code == '0'){
              this.$message({
                type:"success",
                message:"登录成功!",
              })
              this.$router.push("/alldata")   //登录成功之后跳转
            }else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })
    },
    jump(){
      this.$router.push("/register")   //
    }
  }
}
// document.querySelector(".img-btn").addEventListener("click",function (){
//   document.querySelector(".cont").classList.toggle("s-signup")
// });
</script>

<style scoped>

</style>