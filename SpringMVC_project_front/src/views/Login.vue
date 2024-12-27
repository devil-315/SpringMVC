<template>
    <div class="login-page">
        <el-form
        class="box"
            :model="loginData">
        <h1>login</h1>
        <input type="text" 
        name="phone"
         placeholder="请输入用户名" 
         v-model.trim="loginData.phone" 
         @blur="isEmpty"/>

        <input 
        type="password" 
        name="password" 
        placeholder="请输入密码" 
        v-model.trim="loginData.password" 
        @keyup.enter="submitForm"
        @blur="isEmpty"/>

        <el-button type="primary" class="login-button" @click="submitForm">登录</el-button>
        </el-form>
    </div>
</template>
<script setup>
    import { ref } from 'vue';
    import { ElMessage } from 'element-plus'
    import axios from 'axios';
    import router from '@/router';



    const loginData = ref({})


    const isEmpty = () => {
        if(!loginData.value.phone || !loginData.value.password) {
                ElMessage.error("手机号或者密码不能为空")
                return
            }
        }
    const submitForm = async() => {
        if(!loginData.value.phone || !loginData.value.password){
            ElMessage.error("手机号或者密码不能为空")
            return
        }
        let params = {}
        Object.assign(params,loginData.value)

        let result = await axios.post("/api/userInfo/login",params,{
            headers: {
                "Content-Type" : 'multipart/form-data'
            }
        })

        const data = result.data
        if(data.status != "success"){
            ElMessage.error("登录失败")
            return  
        }
        
        //跳转到主页
        sessionStorage.setItem("userInfo",data)
        router.push("/dashble")
        ElMessage.success("登录成功")

    }


</script>
<style>
    .login-page {
        /* 使其有窗口的高度 */
        min-height: 100vh;
        margin: 0;
        padding: 0;
        font-family: sans-serif;
        width: 100%;
        height: 100%;
        background: #34495e;
    }
    
    .box {
        width: 300px;
        padding: 40px;
        position: absolute;
        background: #191919;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
        border-radius: 20px;
        opacity: 0.8;
        color: white;
        text-align: center;
        /* 强制英文大写 */
        text-transform: uppercase;
        font-weight: 800;
        box-shadow: 10px 10px 20px black
    }

    .box input[type="text"],
    .box input[type="password"]{
        /* 独占一行 */
        display: block;
        background: none;
        margin: 20px auto;
        border: 2px solid #3498db;
        padding: 14px 10px;
        border-radius: 25px;
        text-align: center;
        color: white;
        width: 200px;
        outline: none;
        transition: 0.2s;
    }

    /* 聚焦状态 */
    .box input[type="text"]:focus {
        width: 280px;
        border-color: #2ecc71;
    }

    .box input[type="password"]:focus {
        width: 280px;
        border-color: #2ecc71;
    }

    .login-button {
        padding: 20px 40px;
        text-align: center;
        border-radius: 24px;
    }
</style>
