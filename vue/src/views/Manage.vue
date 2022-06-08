<template>
  <el-container style="height:100vh">

<!--    侧边栏-->
    <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246);">
      <Aside :is-collapse="isCollapse"/>
    </el-aside>

    <el-container>

<!--      导航栏-->
      <el-header style="border-bottom:1px solid #ccc;">
        <Header :collapse-btn-class="collapseBtnClass" :collapse="collapse"/>
      </el-header>

<!--      主体部分-->
      <el-main style="padding:0; margin: 0;">
        <router-view/>
      </el-main>

    </el-container>
  </el-container>
</template>

<script>
// @ is an alias to /src
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'HomeView',
  components: {
    Header,
    Aside
  },
  data() {
    return {
      isCollapse:false,
      sideWidth:200,
      collapseBtnClass:'el-icon-s-fold',
      // headerbg: 'bg'
    }
  },
  created() {
    // this.currentPathName = localStorage.getItem("currentPathName")
  },
  methods:{

    collapse(){
      this.isCollapse = !this.isCollapse;
      if(this.isCollapse){
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
      }else{
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
      }
    }
  }

}
</script>
<style>
  /*.bg{*/
  /*  background: #42b983 !important;*/
  /*}*/
</style>