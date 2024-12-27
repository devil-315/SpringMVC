import './assets/base.css'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//国际化
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(ElementPlus, {
    locale: zhCn,
  })
  
app.use(router)

app.mount('#app')
