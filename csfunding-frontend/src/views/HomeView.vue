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
                  <el-table-column prop="sum" label="经费总额" />
                  <el-table-column prop="remain" label="经费剩余" />
                  <el-table-column label="操作" width="50px">
                    <el-button type="primary" size="small" plain>查看</el-button>
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
                  <el-container class="Record">
                    <el-header>
                      最近记录
                    </el-header>
                  <el-main>

                  </el-main>
                </el-container>
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
      ResearchGroup: []
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


  }
}
</script>

