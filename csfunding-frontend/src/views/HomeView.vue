<template>
  <div class="Background">
    <el-menu :default-active="activeIndex"
             class="el-menu-demo"
             mode="horizontal"
             :ellipsis="false"
             background-color="grey"
             style="min-width: 100%">
      <el-menu-item index="0">南方科技大学财务管理系统</el-menu-item>
      <div class="flex-grow" />
      <el-menu-item index="1">帮助中心</el-menu-item>
      <el-sub-menu index="2">
        <template #title>退出登录</template>
        <el-menu-item index="2-1">
          <router-link to="login">
            返回登录页面
          </router-link>
        </el-menu-item>
        <el-menu-item index="2-2">item two</el-menu-item>
      </el-sub-menu>
    </el-menu>

    <el-container>

      <el-main>
        <div class="Main">

          <el-row>

            <el-col :span="7">
              <el-card class="box-card">
                <template #header>
                  <div class="card-header">
                    <span>用户信息</span>
                    <el-button class="button" text>编辑</el-button>
                  </div>
                </template>
                账号：{{username}} <br>
                姓名：{{name}} <br>
                联系电话：{{phoneNumber}} <br>
                账号类型：{{type}} <br>
              </el-card>
            </el-col>

            <el-col :span="1">
              <div class="box">
              </div>
            </el-col>

            <el-col :span="7">
              <el-container class="Group">
                <el-header>
                  我的课题组
                </el-header>
                <el-main>
                <el-table :data="ResearchGroup" style="width: 100%" class="GroupTable">
                  <el-table-column prop="name" label="课题组名称" width="95px"  />
                  <el-table-column prop="sum" label="经费总额" width="65px" />
                  <el-table-column prop="remain" label="经费剩余" width="65px"/>
                  <el-table-column label="操作" width="60px">
                    <el-button @click="FundByResearchGroup" type="primary" size="small" plain>查看</el-button>
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
              <el-container class="Notice">
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
                  <el-card class="box-card">
                    <template #header>
                      <div class="card-header">
                        <el-container>
                          <el-header>
                        <span>最近记录</span>
                        <el-button @click="AllApply" class="button" text>所有申请</el-button>
                          </el-header>
                          <el-main>
                        <el-table :data="RecentApply" style="width: 100%" >
                          <el-table-column prop="name" label="经费名称" />
                          <el-table-column prop="researchGroup" label="课题组名称" />
                          <el-table-column prop="applyPerson" label="申请人" />
                          <el-table-column prop="amount" label="申请金额" />
                          <el-table-column prop="state" label="申请状态" >
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
              <el-container class="Function">
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

        <el-footer>
          Foot
        </el-footer>

    </el-container>
  </div>
</template>


<style>
.Main {
  width: 1200px;
  margin: 0 auto;
}

.Group{
  height: 400px;
  background-color: #eaeaea;
  border: 1px solid orangered;
  line-height: 100px;
  font-size: 24px;
}
.box-card{
  height: 400px;
  background-color: #eaeaea;
  border: 1px solid orangered;
  line-height: 50px;
  font-size: 24px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.Notice{
  height: 400px;
  background-color: #eaeaea;
  border: 1px solid orangered;
  line-height: 100px;
  font-size: 24px;
}
.Record{
  height: 400px;
  background-color: #eaeaea;
  border: 1px solid orangered;
  line-height: 100px;
  font-size: 24px;
}
.Function{
  height: 400px;
  background-color: #eaeaea;
  border: 1px solid orangered;
  line-height: 100px;
  font-size: 24px;
}

.Background{
  width: 100%;
  height: 100%;
  background-color: lightgray;
  background-position: 0 0;
  background-repeat: no-repeat;
}
.flex-grow {
  flex-grow: 1;
}


</style>

<script>
import { ref } from 'vue'

const activeIndex = ref('1')

export default {
  name:'Home',
  data(){
    return {
      username: '',
      name: 'xxx',
      phoneNumber: 'xxxviii',
      type: '',
      ResearchGroup: [],
      RecentApply:[]
    }
  },
  created() {
    const query = this.$route.query;

    this.username = query.username
    this.name = query.name
    this.phoneNumber = query.phoneNumber

    if (!JSON.parse(query.isAdmin)){
      this.type = 'User'
    }
    else{
      this.type = 'Admin'
    }

    this.ResearchGroup = [
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
      },
      {
        name: '赵',
        sum: 30000,
        remain: 20000
      }
    ]

    this.RecentApply = [
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


  },
  methods:{
    AllApply(){
      this.$router.push({path: '/apply'})
    },
    FundByResearchGroup(){
      this.$router.push({path: '/fund'})
    }
  }
}
</script>

