<template>
    <div>
        <!-- 搜索框 -->
        <div>
            <!-- @submit.prevent： 阻止表单自动提交 -->
        <el-form :model="searchFormData" label-width="80px" @submit.prevent>
            <el-row :gutter="5">
                <el-col :span="5">
                    <el-form-item label="手机号">
                        <el-input v-model.trim="searchFormData.phoneFuzzy" placeholder="支持模糊查询" clearable ></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="5">
                    <el-form-item label="用户名">
                        <el-input v-model="searchFormData.userNameFuzzy" placeholder="支持模糊查询" clearable></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="5">
                    <el-button type="primary" @click="loadDate()">搜索</el-button>
                    <el-button type="primary" @click="showEdit()">新增用户</el-button>
                    <el-button type="danger" @click="logout()">退出</el-button>
                </el-col>
            </el-row>
        </el-form>
        
        </div>
        <!-- 表格 -->
    <el-table :data="tableData" style="width: 100%">
        <el-table-column label="用户ID" prop="userId"></el-table-column>
        <el-table-column label="用户名" prop="userName"></el-table-column>
        <el-table-column label="手机号" prop="phone"></el-table-column>
        <el-table-column label="出生日期" prop="birthday"></el-table-column>
        <el-table-column label="性别" prop="sex">
            <template #default = "scope">
                 <!-- 性别  0:女 1:男 -->
           <!-- 获取行信息中的Sex -->
            <!-- {{ scope.row.sex == 0 ? "女" : "男" }} -->
              {{ SEX_MAP[scope.row.sex] }}
            </template>
        </el-table-column>
        <el-table-column label="职位" prop="position">
            <template #default = "scope">
                <!-- 职位 0:程序员 1：测试 2：管理员 可多选 -->
                {{ POSITION_MAP[scope.row.position] }}
            </template>
        </el-table-column>
        <el-table-column label="角色" prop="roles">
            <template #default = "scope">
                {{ formatRoles(scope.row.roles) }}
            </template>
        </el-table-column>
        <el-table-column label="操作">
        <template #default="scope">
          <el-button-group>
					<el-button
					 	type="primary"
					 	@click="showEdit(scope.row)
					 	">Edit
					</el-button>
                    <el-button
						type="primary"
						@click="updatePwd(scope.row.userId)"
						>修改密码
					</el-button>
					<el-button
						type="danger"
						@click="delUser(scope.row.userId)"
						>Delete
					</el-button>
				</el-button-group>
      </template>
      </el-table-column>
    </el-table>
    <!-- total: 绑定总数据数 -->
    <!-- page-size: 绑定每页大小 -->
     <!-- 根据总数据数 和 每页大小 自动计算总页数 -->
    <div class="pageStyle">
        <el-pagination 
        background 
        layout="prev, pager, next" 
        :total="totalCount" 
        :page-size="pageSize"
        @current-change="pageChange"/>
    </div>
    <!-- 引入组件 -->
     <!-- ref给元素或者 子组件注册引用信息 -->
          <!-- @绑定一个事件 -->
    <UserEdit ref="userEditRef" @reload="loadDate()"></UserEdit>
    <UpdatePassword ref="updatePasswordRef"></UpdatePassword>
    </div>
</template>

<script setup>
    import UserEdit from './UserEdit.vue';
    import UpdatePassword from './UpdatePassword.vue';
    import { anchorEmits, ElMessage } from 'element-plus'
    import axios from 'axios';
    import { ref, onMounted } from 'vue';

    import { SEX_MAP,POSITION_MAP,ROLES_MAP } from '@/utils/Constancs.js';
import router from '@/router';

    //后端 api 接口
    const api = {
        LoadDateList : "/api/userInfo/loadDataListOption",
        delUser: "/api/userInfo/delUser",
        logout: "/api/userInfo/logout"
    }

    //遍历角色， 并且转换(0) (0,1) (0,1,2) (0,1,2,3)
    const formatRoles = (roles) => {
        let rolesArray = roles.split(",")
        let result = []
        rolesArray.forEach(element => {
      result.push(ROLES_MAP[element])
    });

        return result.join(",");
    }

    //对象
    const searchFormData = ref({})

    //定义 tableData数组对象
    const tableData = ref([])

    //定义总条目数
    const totalCount = ref(0)

    //定义每页大小
    const pageSize = ref(0)

    //定义当前页 ， 默认是第一页
    const pageNo = ref(1)

    //loadDate 方法 请求后端，获取数据 ==》 axios
    const loadDate = async() => {
        //将所有可枚举的值从一个或多个源对象 source 复制到 目标对象
        //将 searchFormData 复制到 空对象 param 中， 到时候 param 对象就会拥有，
        //searchFormData 对象的所有属性，
        const param = Object.assign({},searchFormData.value)
        //searchFormData 对象中不包含 pageNo，所有加入 pageNo 属性
        param.pageNo = pageNo.value

        let result = await axios.post(api.LoadDateList,param)
        
        //console.log(result);
        const data = result.data
        
        //如果后端返回的 状态 status 不为 success ， 提示请求失败
        if(data.status != "success"){
            ElMessage.error(data.message)
            return
        }

        ElMessage.success(data.message)

        //数据回显
        tableData.value = data.data.list

        totalCount.value = data.data.totalCount

        pageSize.value = data.data.pageSize
    }
    
    //页面渲染的时候，就开始调用，请求数据
    onMounted(() => {
        loadDate();
    })

    //点击切换页面的时候，重新加载数据
    const pageChange = (e) => {
        // e 就是点击的页数
        pageNo.value = e

        //重新请求数据
        loadDate();
    }

    //userEditRef 指向 UserEdit 组件的实例
    const userEditRef = ref()

    const showEdit = (data) =>{
        let param = null;

        //修改，有数据的时候
        if(data){
            param = Object.assign({},data)
        }
        userEditRef.value.show(param)
    }

    const updatePasswordRef = ref()
    const updatePwd = (userId) => {
    updatePasswordRef.value.show(userId)
    }

    const delUser = async(userId) => {
        const delUserApi = api.delUser + '/' + userId
        let result = await axios.get(delUserApi)
        const data = result.data

        if(data.status != "success"){
            ElMessage.error(data.message)
            return
        }
        ElMessage.success(data.message)
        //删除成功，重新加载
        loadDate();
    }

    const logout = async() =>{
        let result = await axios.get(api.logout)

        const data = result.data

        ElMessage.success(data.message)

        router.push("/")
    }
</script>
<style>
    .pageStyle {
        margin-top: 20px;
}
</style>
