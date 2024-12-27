import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },

  server: {
    //项目启动的端口号
    port: 3001,

    hrm: true,
    
    proxy: {
      '/api': {
        //目标代理的是哪个请求， 后端url
        target: "http://192.168.33.164:9091",

        //请求头中的host值
        changeOrigin: true,

        //过滤掉请求前缀，代理会把 URL 中的 /api 替换掉 /api
        //在这个列子中，实际上并没有发生改变
        pathRewrite: {
          "^api": "/api"
        }
      }
    }
  }
})
