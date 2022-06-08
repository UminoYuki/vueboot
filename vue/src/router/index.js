import Vue from 'vue'
import VueRouter from 'vue-router'
import Manage from '../views/Manage.vue'
import Home from "@/components/Home";
import store from "@/store";
import UserInfo from "@/components/UserInfo";
import Role from "@/components/Role";
import Menu from "@/components/Menu";

import dataV from '@jiaminghi/data-view'
import User from "@/components/User";
import Data from "@/components/Data";
import AllData from "@/components/AllData";

Vue.use(dataV)
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'manage',
    component: Manage,
    redirect: "/home",    //重定向
    children:[
      {
        path: 'home',
        name: '首页',
        component: Home,
      },
      {
        path:'data',
        name:'实时数据',
        component: Data
      },
      {
        path:'alldata',
        name:'历史数据',
        component: AllData
      },
      {
        path:'user',
        name:'用户',
        component: User
      }
    ]
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Register.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach((to, from,next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前路由名称，在Header组件中使用
  store.commit("setPath")  // 触发store数据更新
  next()  // 放行路由
  // return false
})

export default router