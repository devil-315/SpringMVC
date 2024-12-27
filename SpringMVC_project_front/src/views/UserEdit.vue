<template>
    <div>
        <!--  v-model="dialogVisible" 控制 弹窗显示与隐藏 -->
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

          <el-form-item label="用户名" prop="userName">
            <el-input
            clearable
            placeholder="请输入用户名"
            v-model.trim="FormData.userName" />
          </el-form-item>

          <el-form-item label="手机号" prop="phone">
            <el-input
            clearable
            placeholder="请输入手机号"
            v-model.trim="FormData.phone" />
          </el-form-item>
            <!-- v-if 会将不满足条件的样式直接删掉 -->
             <!-- v-show不会将不满足的样式直接删除，而是隐藏， display:none -->
          <template v-if="!FormData.userId">
            <el-form-item label="密码" prop="password">
            <el-input
            clearable
            placeholder="请输入密码"
            v-model.trim="FormData.password" 
            type="password"
            show-password/>
          </el-form-item>

          <el-form-item label="重复密码" prop="repassword">
            <el-input
            clearable
            placeholder="请输入密码"
            v-model.trim="FormData.repassword" 
            type="password"
            show-password/>
          </el-form-item>
          </template>

          <el-form-item label="出生年月" prop="birthday">
            <el-date-picker
                v-model="FormData.birthday"
                type="date"
                placeholder="请选择出生年月"
                style="width: 100%"
              />
          </el-form-item>

          <el-form-item label="性别" prop="sex">
            <el-radio-group v-model="FormData.sex">
                <el-radio :label="item.value" :key="item.value" v-for="item in sexArray">
                  {{ item.text }}
                </el-radio>
              </el-radio-group>
          </el-form-item>

          <el-form-item label="职位" prop="position">
            <el-radio-group v-model="FormData.position">
              <el-radio :label="item.value" :key="item.value" v-for="item in positionArray">
                  {{ item.text }}
                </el-radio>
              </el-radio-group>
          </el-form-item>

          <el-form-item label="角色" prop="roles">
              <el-checkbox-group v-model="FormData.roles">
                <el-checkbox :label="item.value + ''" :key="item.value" v-for="item in rolesArray">
                  {{ item.text }}
                </el-checkbox>
              </el-checkbox-group>
          </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </span>
    </template>
  </el-dialog>
    </div>
</template>
<script setup>
    import {ref,nextTick} from "vue"

    import { SEX_MAP,POSITION_MAP,ROLES_MAP } from '@/utils/Constancs.js';

    import { ElMessage } from 'element-plus'

    import axios from 'axios';

    const dialogVisible = ref(false)

    const title = ref("新增")


    // map ==> 数组
    const converMappToArray = (dataMap) => {
      //定义一个数组等会做接收
      const array = []
      for (let item in dataMap) {
        array.push({
          value: Number.parseInt(item),
          text: dataMap[item]
        })
      }
      return array;
    }

    const sexArray = ref(converMappToArray(SEX_MAP));
    const positionArray = ref(converMappToArray(POSITION_MAP));
    const rolesArray = ref(converMappToArray(ROLES_MAP));

    const FormData = ref([])
    const FormDataRef = ref();

     //手机号的正则
  const regs = {
    //13012345678
    phone: /^(13[0-9]|14[5-9]|15[0-3,5-9]|16[6]|17[0-8]|18[0-9]|19[8-9])\d{8}$/
  };

  const checkPhone = (rule, value, callback) => {
    //如果值为空
    if (!value) {
          return callback(new Error(rule.message))
        }else {
          if(regs.phone.test(value)) {
            callback();
          } else {
            callback(new Error(rule.message))
          }
        }
  }

  const checkRePassword = (rule, value, callback) => {
    if(value && FormData.value.password !== value) {
      callback(new Error(rule.message))
    }else {
      callback()
    }
  }
    const rules = {
    userName: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      { min: 3, max: 11, message: '长度至少为3个 最多为11个', trigger: 'blur' },
    ],
    phone: [{ required: true, message: '请输入手机号', trigger: 'blur' },
      {
        validator: checkPhone,
        message: "请输入正确的手机号"
      }
    ],
    password: [{ required: true, message: '请输入密码', trigger: 'blur' },],
    repassword: [{ required: true, message: '请再次输入密码', trigger: 'blur' },
      {
        validator: checkRePassword,
        message: "两次密码不一致"
      }
    ],
    birthday: [{ required: true, message: '请选项出生年月日', trigger: 'blur' },],
    sex: [{ required: true, message: '请选择性别', trigger: 'blur' },],
    position: [{ required: true, message: '请选择职位', trigger: 'blur' },],
    roles: [{ required: true, message: '请选择角色', trigger: 'blur' },]
  }

  //获取 父组件传递过来的方法或者事件
  const emit = defineEmits(["reload"])

    //提交
    const submitForm = ()=> {
      FormDataRef.value.validate(async(valid) => {
        if(!valid){
          return
        }

        let params = {};
        Object.assign(params,FormData.value);
        params.roles = params.roles.join(",");

        let result = await axios.post("/api/userInfo/saveUser", params)

        const data = result.data
        
        if(data.status != "success"){
          ElMessage.error(data.message)
          return
        }
        ElMessage.success(data.message)

        //关闭弹框
        dialogVisible.value = false

        //触发事件，方法
        emit("reload")
      });    
    }

    const show = (data) => {
      dialogVisible.value = true

      //判断是新增还是修改， data为空时 新增 否则是 修改
      title.value = data ? "修改" : "新增"

      //有可能出现 页面还没加载好，就开始执行下面的逻辑
      //正常逻辑： 页面加载好之后，再去 执行逻辑
      nextTick(() => {
         //每次点开都重置表单
        FormDataRef.value.resetFields();

        //数据显示
        if(data){
          data.roles = data.roles.split(",")
        }

        //如果有数据就将数据遍历到 FormData 中，没有就是空
        FormData.value = Object.assign({},data || {})
      });
    }

    //将方法暴露出去
    defineExpose({
      show,
    })
</script>
<style lang="scss" scoped>

</style>
