<template>
    <div class="Background">
      <el-menu :default-active="activeIndex"
               class="el-menu-demo"
               mode="horizontal"
               :ellipsis="false"
               style="min-width: 100%">
        <el-menu-item index="0">
          <font style="color: white">南方科技大学财务管理系统</font>
        </el-menu-item>
        <div class="flex-grow"/>
        <el-menu-item index="1">
          <font style="color: white">您好 xxx</font>
        </el-menu-item>
        <el-sub-menu index="2">
          <template #title>
            <font style="color: white">注销</font>
          </template>
          <el-menu-item index="2-1">
            <router-link to="login">
              <font style="color: white">返回登录页面</font>
            </router-link>
          </el-menu-item>
          <el-menu-item index="2-2">
            <font style="color: white">返回官网</font>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>

        <el-container>

            <el-main>
                <div class="Main">
                    <el-row>

                        <el-col :span="7">
                            <el-card class="info">

                                    <div class="info-header" style="height: 40px">
                                        <span>用户信息</span>
                                        <el-button class="button" text>编辑</el-button>
                                    </div>

                                    <div style="height: 20px">

                                    </div>

                              <div class="divide">

                                <div>
                                  <img src="@/img/user.png" width="85" height="150">
                                </div>

                                <div style="font-size: 16px">
                                  账号：{{ userData.username }} <br>
                                  姓名：{{ userData.name }} <br>
                                  联系电话：{{ userData.phoneNumber }} <br>
                                  账号类型：{{ userData.type }} <br>
                                </div>
                              </div>
                            </el-card>
                        </el-col>

                        <el-col :span="1">
                            <div class="box">
                            </div>
                        </el-col>

                        <el-col :span="7">
                            <el-container class="group">
                                <el-header>
                                    我的课题组
                                </el-header>
                                <el-main>
                                    <el-table :data="ResearchGroup" style="width: 95%" class="GroupTable">
                                        <el-table-column prop="name" label="课题组名称" align="center"/>
                                        <el-table-column prop="sum" label="经费总额" align="center"/>
                                        <el-table-column label="操作" align="center">
                                            <el-button @click="FundByResearchGroup" type="primary" size="small" plain>
                                                查看
                                            </el-button>
                                        </el-table-column>
                                    </el-table>
                                </el-main>
                            </el-container>
                        </el-col>

                        <el-col :span="1">
                            <div class="box">
                            </div>
                        </el-col>

                        <el-col :span="8">
                            <el-container class="Notice group">
                                <el-header>
                                    通知
                                </el-header>
                                <el-main>

                                </el-main>

                            </el-container>
                        </el-col>

                    </el-row>

                    <el-row style="height: 40px">

                    </el-row>

                    <el-row>

                        <el-col :span="17">
                            <el-card class="info">
                                <template #header>
                                    <div class="info-header">
                                        <el-container>
                                            <el-header>
                                                <span>最近记录</span>
                                                <el-button @click="AllApply" class="button" text>所有申请</el-button>
                                            </el-header>
                                            <el-main>
                                                <el-table :data="RecentApply" style="width: 100%">
                                                    <el-table-column prop="name" align="center" label="经费名称"/>
                                                    <el-table-column prop="researchGroup" align="center" label="课题组名称"/>
                                                    <el-table-column prop="applyPerson" align="center" label="申请人"/>
                                                    <el-table-column prop="amount" align="center" label="申请金额"/>
                                                    <el-table-column prop="state" align="center" label="申请状态">
                                                    </el-table-column>
                                                </el-table>
                                            </el-main>
                                        </el-container>
                                    </div>
                                </template>

                            </el-card>
                        </el-col>

                        <el-col :span="1">
                            <div class="box">
                            </div>
                        </el-col>

                        <el-col :span="6">
                            <el-container class="Function group">
                                <el-header>
                                    所有功能
                                </el-header>
                                <el-main>

                                </el-main>
                            </el-container>
                        </el-col>

                    </el-row>

                </div>
            </el-main>

          <div class="Bottom">
            <div style="height: 5px"></div>
            <el-button style="background-color: #8f000b; border: #8f000b">
              <font style="color: white">主页</font>
            </el-button>
          </div>

        </el-container>
    </div>
</template>


<style>
.Main {
    width: 1200px;
    margin: 0 auto;
}

.group {
    height: 300px;
    background-color: white;
    line-height: 75px;
    font-size: 24px;
    border-radius: 5px;
    box-shadow: 0 0 12px rgba(0, 0, 0, 0.12)
}

.info {
    height: 300px;
    background-color: white;
    line-height: 40px;
    font-size: 24px;
}

.info-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.Notice {
    height: 300px;
    background-color: white;
    /*border: 1px solid orangered;*/
    line-height: 100px;
    font-size: 24px;
}

.Function {
    height: 400px;
    background-color: white;
    /*border: 1px solid orangered;*/
    line-height: 100px;
    font-size: 24px;
}

.Background {
    width: 100%;
    height: 100%;
    background-color: whitesmoke;
    background-position: 0 0;
    background-repeat: no-repeat;
}

.flex-grow {
    flex-grow: 1;
}

.divide {
  width: 100%;
  height: 22px;
  display: flex;
  flex-direction: row;
}
.divide > div {
  height: 22px;
  flex: 1;
}


</style>

<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue'
import router from "@/router";
import request from "@/util/request";


const activeIndex = ref('1')
const userData = reactive({
    username: '',
    name: '',
    phoneNumber: '',
    type: ''
})

onMounted(() => {
    request({
        url: '/current-user',
        method: 'get'
    }).then(res => {
        console.log(res);
        let ud = res.data.data;
        userData.username = ud.id;
        userData.name = ud.name;
        userData.phoneNumber = ud.phoneNum;
        userData.type = ud.isAdmin ? '管理员' : '用户';
    })
})

  const ResearchGroup = [
    {
      name: '王',
      sum: 10000,
      remain: 10000
    },
    {
      name: '李',
      sum: 20000,
      remain: 10000
    },
    {
      name: '孙',
      sum: 20000,
      remain: 20000
    }
  ]

  const RecentApply = [
    {
      name: '经费1',
      researchGroup: '王',
      applyPerson: 'xxx',
      amount: 500,
      state: 'pass'
    },
    {
      name: '经费2',
      researchGroup: '李',
      applyPerson: 'xxx',
      amount: 2000,
      state: 'pass'
    },
    {
      name: '经费3',
      researchGroup: '李',
      applyPerson: 'xxx',
      amount: 3000,
      state: 'submit'
    }

  ]
// created() {
//   const query = this.$route.query;
//
//   this.username = query.username
//   this.name = query.name
//   this.phoneNumber = query.phoneNumber
//
//   if (!JSON.parse(query.isAdmin)){
//     this.type = 'User'
//   }
//   else{
//     this.type = 'Admin'
//   }
//
//
//
//
// },
function AllApply() {
    router.push({path: '/apply'})
}

function FundByResearchGroup() {
    router.push({path: '/fund'})
}
</script>

