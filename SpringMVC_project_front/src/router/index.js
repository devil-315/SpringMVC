import { createRouter, createWebHistory } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/dashble',
      name: 'index',
      meta: {
        needLogin: true
      },
      component: () => import("../views/index.vue")
    }, {
      path: '/',
      name: 'login',
      component: () => import("../views/Login.vue")
    }
  ]
})

//全局前置路由
//to:跳转到哪个页面
//from：从哪个页面来
//next：能不能放行
router.beforeEach((to,from,next) => {
  if(to.matched.length === 0){
    router.push("/")
    ElMessage.error("请先登录")
  }

const loginData = sessionStorage.getItem("userInfo")

  //通过 needLogin 这个属性 判断这个路由要不要登录验证
  //如果要登录验证，判断有没有session ，如果有 session，就允许进入主页面

  //只要路由中有neddLogin 这个属性，就回到登录页面
  if(to.meta.needLogin != null &&to.meta.needLogin && loginData == null){
    router.push("/")
    ElMessage.error("请先登录")
  }
  
  next();
})
export default router
