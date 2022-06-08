<template>
  <el-menu :default-openeds="['1', '3']" style="min-height:100%; overflow-x:hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
           @select="handleSelect">
    <div style="height: 60px; line-height: 60px; text-align: center">
      <i class="el-icon-s-data" style="margin-right: 3px; color: white; font-size: 18px; font-weight: bold; position:relative; top: 1px;"></i>
      <b style="color: white" v-show="!isCollapse">数据展示平台</b>
    </div>
    <el-menu-item index="/">
      <template slot="title">
        <i class="el-icon-message"></i>
        <span slot="title">主页</span>
      </template>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">数据</span>
      </template>
      <el-menu-item index="/data">
        <i class="el-icon-s-custom"/>
        <span slot="title">实时数据</span>
      </el-menu-item>
      <el-menu-item index="/alldata">
          <i class="el-icon-s-custom"/>
          <span slot="title">历史数据</span>
        </el-menu-item>
    </el-submenu>
<!--    <el-submenu index="3">-->
<!--      <template slot="title">-->
<!--        <i class="el-icon-menu"></i>-->
<!--        <span slot="title">用户2</span>-->
<!--      </template>-->
<!--    </el-submenu>-->
    <el-menu-item index="/user">
      <template slot="title">
        <i class="el-icon-s-custom"></i>
        <span slot="title">用户</span>
      </template>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse: Boolean
  },
  methods:{
    handleSelect(index){
      // console.log(index)
      // alert(this.$route.name)
    }
  }
}
</script>

<style scoped>

</style>