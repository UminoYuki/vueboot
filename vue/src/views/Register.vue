<template>
  <div style="width: 100%; height: 100vh; background-color: #ffffff; overflow:hidden">
    <div style="width: 400px; margin: 150px auto">
      <div style="color: #3f0202; font-size: 30px; text-align: center; padding:30px 0 ">欢迎注册</div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-icon><user /></el-icon>
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" placeholder="密码" show-password></el-input>
        </el-form-item>
        <el-form-item prop="confirm">
          <el-input v-model="form.confirm" placeholder="重新输入密码" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style = "width:100%" type="primary" @click="register">注册</el-button>
        </el-form-item>
        <el-form-item>
          <el-button style = "width:100%" type="primary" @click="jump">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data(){
    return {
      form: {},
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirm: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ]

      }
    }
  },
  methods: {
    register(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.request.post("/user/register",this.form).then(res => {
            console.log(this.form)
            if(res.code == '0'){
              this.$message({
                type:"success",
                message:"注册成功!",
              })
              this.$router.push("/login")   //登录成功之后跳转
            }else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })
      if(this.form.password !== this.form.confirm){
        this.$message({
          type: "error",
          message: "两次密码不一致！"
        })
      }
    },
    jump(){
      this.$router.push("/login")
    }
  }
}
</script>

<style scoped>

</style>