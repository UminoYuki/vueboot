<template>
  <div style="font-size: 12px; line-height: 60px; display:flex">
    <div style="flex:1; font-size: 18px">
      <span :class="collapseBtnClass" style="cursor:pointer" @click="collapse" ></span>

      <el-breadcrumb separator="/" style="display: inline-block; text-align: center; line-height: 100%; margin-left: 15px">
        <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width:70px; cursor: pointer">
      <div>
        <span>{{ user.nickname }}</span>
        <i class="el-icon-arrow-down" style="margin-left: 5px;"></i>
      </div>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item style="font-size: 14px;">
          <router-link to="/userInfo" style="text-decoration: none; font-size: 14px;">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px;">
          <span style="text-decoration: none; font-size: 14px; padding: 5px 5px;" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass: String,
    collapse: Function,
  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName   //需要监听的数据
    }
  },
  data(){
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  methods: {
    logout() {
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功")
    }
  },
  watch:{ // 监听路由变化
    currentPathName(newVal, oldVal){
      console.log(newVal)
    }
  }
}
</script>

<style scoped>

</style>