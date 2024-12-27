<template>
    <div>
      <!-- v-model = dialogVisible 控制 弹框显示与隐藏-->
      <el-dialog
        v-model="dialogVisible"
        :title="title"
        width="30%"
        :close-on-click-modal="false"
      >
        <el-form
          :model="FormData"
          :rules="rules"
          ref="FormDataRef"
          label-width="80px"
        >
          <el-form-item label="密码" prop="password">
          <el-input
          clearable
          placeholder="请输入密码" 
          v-model.trim="FormData.password"
          type="password"
          show-password
          />
          </el-form-item>
  
          <el-form-item label="重复密码" prop="rePassword">
            <el-input
            clearable
            placeholder="请输入密码" 
            v-model.trim="FormData.rePassword"
            type="password"
            show-password
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitForm">确定</el-button>
          </div>
        </template>
      </el-dialog>
    </div>
  </template>
  <script setup>
  import { ref, nextTick } from "vue"
  import { ElMessage } from 'element-plus'
  import axios from 'axios'
  
  const dialogVisible = ref(false)
  const title = ref("修改密码")
  
  const FormData = ref([])
  const FormDataRef = ref();
  
  const checkRePassword = (rule, value, callback) => {
    if(value && FormData.value.password !== value) {
      callback(new Error(rule.message))
    }else {
      callback()
    }
  }
  
  const rules = {
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
    rePassword: [
      { required: true, message: '请重新输入密码', trigger: 'blur' },
      {
        validator: checkRePassword,
        message: "两次输入的密码不一致"
      }
    ]
  }
  
  // 提交
  const submitForm = () => {
    FormDataRef.value.validate(async(valid) => {
      if(!valid) {
        return
      }
      let params = {};
      console.log(FormData.value);
      
      Object.assign(params, FormData.value);

      //params.roles = params.roles.join(",");
  
      let result = await axios.post("/api/userInfo/updatePassword", params, {
        headers: {
          "Content-Type" : 'application/x-www-form-urlencoded'
        }
      })
  
      const data = result.data 
  
      if(data.status != "success") {
        ElMessage.error(data.message)
        return
      }
      ElMessage.success(data.message)
  
      //关闭弹框
      dialogVisible.value = false
    });
  }
  
  const show = (userId) => {
  
    dialogVisible.value = true
  
    // 有可能出现 页面还没加载好，就开始执行下面的逻辑
    // 正常逻辑：  页面加载好之后， 再去 执行逻辑
    nextTick(() => {
      //每次点开都重置表单
      FormDataRef.value.resetFields();
  
      //接收传递过来的userId
      FormData.value = {
        userId
      }
    });
  
  }
  
  //将方法暴露出去
  defineExpose ({
    show,
  })
  
  </script>
  <style lang="scss" scoped>
  
  
  </style>
  