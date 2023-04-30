<template>
    <div class="Background">
      <el-menu :default-active="activeIndex"
               class="el-menu-demo"
               mode="horizontal"
               :ellipsis="false"
               >
        <el-menu-item index="0">
          <font style="color: white">南方科技大学财务管理系统</font>
        </el-menu-item>
        <div class="flex-grow"/>
        <el-menu-item index="1">
          <font style="color: white">您好 {{userData.name}}!</font>
        </el-menu-item>
        <el-sub-menu index="2" >
          <template #title>
            <font style="color: white">注销</font>
          </template>
          <el-menu-item index="2-1">
            <router-link to="login">
              返回登录
            </router-link>
          </el-menu-item>
          <el-menu-item index="2-2">
              返回官网
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
                                      <el-button type="primary" :icon="Edit">编辑</el-button>
                                    </div>

                                    <div style="height: 20px">

                                    </div>

                              <div class="info-divide">

                                <div style="margin-top: 15px; margin-left: 15px">
                                  <img src="@/img/user.png" width="80" height="100" v-if="userData.type==='用户'">
                                  <img src="@/img/admin.png" width="80" height="100" v-if="userData.type!=='用户'">
                                </div>

                                <div style="font-size: 16px">
                                  账号：{{ userData.username }} <br>
                                  姓名：{{ userData.name }} <br>
                                  账号类型：{{ userData.type }} <br>
                                  联系方式：{{ userData.phoneNumber }} <br>
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
                                  <el-button type="primary" :icon="Edit" style="margin-left: 100px"
                                             v-if="userData.type!=='用户'">编辑</el-button>
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
                            <el-container class="Notice">
                                <el-header>
                                    通知
                                </el-header>
                                <el-main>
                                  <el-table :data="Notices" style="width: 95%" class="GroupTable">
                                    <el-table-column prop="notice" label="通知内容" />
                                    <el-table-column v-if="userData.type!=='用户'">
                                      <el-button type="danger" size="small" plain>
                                        删除
                                      </el-button>
                                    </el-table-column>
                                  </el-table>

                                </el-main>

                            </el-container>
                        </el-col>

                    </el-row>

                    <el-row style="height: 40px">

                    </el-row>

                    <el-row>

                        <el-col :span="17">
                            <el-card class="RecentApply">
                                    <div>

                                      <span>最近记录</span>
                                      <el-button type="primary" :icon="Search" style="margin-left: 588px" @click="AllApply">查看全部</el-button>
                                      <el-table :data="RecentApply" style="width: 100%">
                                                    <el-table-column prop="name" align="center" label="经费名称"/>
                                                    <el-table-column prop="researchGroup" align="center" label="课题组名称"/>
                                                    <el-table-column prop="applyPerson" align="center" label="申请人"/>
                                                    <el-table-column prop="amount" align="center" label="申请金额"/>
                                                    <el-table-column align="center" label="申请状态">
                                                      <template #default="props">

                                                        <el-tag type="error" size="small" plain v-if="props.row.state === 'fail'" effect="dark">
                                                          申请失败
                                                        </el-tag>

                                                        <el-tag type="warning" size="small" plain v-if="props.row.state === 'submit'" effect="dark">
                                                          未审核
                                                        </el-tag>

                                                        <el-tag type="success" size="small" plain v-if="props.row.state === 'pass'" effect="dark">
                                                          申请通过
                                                        </el-tag>

                                                      </template>
                                                    </el-table-column>
                                                </el-table>

                                    </div>
                            </el-card>
                        </el-col>

                        <el-col :span="1">
                            <div class="box">
                            </div>
                        </el-col>

                        <el-col :span="6">
                          <el-card class="Function">

                            <span>功能</span>

                            <div style="height: 15px"></div>

                            <div class="info-divide" style="height: 85px">

                              <div class="function-button" v-if="userData.type==='用户'">
                              <el-button type="primary" style="height: 50px; width: 50px" circle
                                         @click="AllApply">
                                <el-icon  style="vertical-align: middle;" size="25px">
                                  <UploadFilled />
                                </el-icon>
                              </el-button>
                                <br>
                                我的申请
                              </div>

                              <div class="function-button" v-if="userData.type!=='用户'" @click="AllApply">
                                <el-button type="primary" style="height: 50px; width: 50px" circle>
                                  <el-icon  style="vertical-align: middle;" size="25px">
                                    <View />
                                  </el-icon>
                                </el-button>
                                <br>
                                审核申请
                              </div>

                              <div class="function-button">
                                <el-button type="primary" style="height: 50px; width: 50px"
                                           @click="ClickOnOutput; OutputDialogVisible=true"
                                           circle>
                                  <el-icon  style="vertical-align: middle;" size="25px">
                                    <Document />
                                  </el-icon>
                                </el-button>
                                <br>
                                导出文件

                              </div>

                            </div>
                            <div class="info-divide" style="height: 70px" >
                              <div class="function-button" v-if="userData.type==='用户'">
                                <el-button type="primary" style="height: 50px; width: 50px" circle>
                                  <el-icon  style="vertical-align: middle;" size="25px">
                                    <Edit />
                                  </el-icon>
                                </el-button>
                                <br>
                                编辑信息
                              </div>

                              <div class="function-button" v-if="userData.type==='用户'">
                              </div>

                              <div class="function-button" v-if="userData.type!=='用户'">
                                <el-button type="primary" style="height: 50px; width: 50px" circle @click="FundDialogVisible=true">
                                  <el-icon  style="vertical-align: middle;" size="25px">
                                    <Coin />
                                  </el-icon>
                                </el-button>
                                <br>
                                增添经费
                              </div>

                              <div class="function-button" v-if="userData.type!=='用户'">
                                <el-button type="primary" style="height: 50px; width: 50px" circle @click="NoticeDialogVisible=true">
                                  <el-icon  style="vertical-align: middle;" size="25px">
                                    <Notification />
                                  </el-icon>
                                </el-button>
                                <br>
                                发布通知
                              </div>
                            </div>
                          </el-card>
                        </el-col>

                    </el-row>

                </div>
            </el-main>

          <div class="Bottom">
            <div style="height: 5px"></div>
            <el-button style="background-color: #8f000b; border: #8f000b">
              <el-icon  style="vertical-align: middle;" size="25px">
                <HomeFilled />
              </el-icon>
              <font style="color: white; margin-left: 5px; margin-top: 3px">主页</font>
            </el-button>
          </div>

          <el-dialog v-model="OutputDialogVisible" title="导出数据" width="30%" draggable>

            <el-form :model="OutputForm" label-width="120px">
              <el-form-item label="课题组名称">
                <el-select v-model="OutputForm.researchGroup" placeholder="经费名称">
                  <el-option label="王" value="王" />
                  <el-option label="李" value="李" />
                </el-select>
              </el-form-item>
              <el-form-item label="经费名称">
                <el-select v-model="OutputForm.fundName" placeholder="经费名称">
                  <el-option label="经费1" value="经费1" />
                  <el-option label="经费2" value="经费2" />
                </el-select>
              </el-form-item>
              <el-form-item label="生成Excel文件">
                <a href="output" download>点击下载文件</a>
              </el-form-item>

            </el-form>
            <template #footer>
      <span class="dialog-footer">
        <el-button type="success" @click="OutputDialogVisible = false">确认</el-button>
        <el-button type="danger" @click="OutputDialogVisible = false">
          取消
        </el-button>
      </span>
            </template>
          </el-dialog>

          <el-dialog v-model="FundDialogVisible" title="创建经费" width="30%" draggable>

            <el-form :model="FundForm" label-width="120px">
              <el-form-item label="课题组名称">
                <el-select v-model="FundForm.researchGroup" placeholder="经费名称">
                  <el-option label="王" value="王" />
                  <el-option label="李" value="李" />
                </el-select>

              </el-form-item>


              <el-form-item label="经费编号">
                <el-input v-model="FundForm.fundNumber" />
              </el-form-item>

              <el-form-item label="经费名称">
                <el-input v-model="FundForm.fundName" />
              </el-form-item>


              <el-form-item label="总金额">
                <el-input v-model="FundForm.sum" />
              </el-form-item>

              <el-form-item label="起止时间">
                <el-col :span="11">
                  <el-form-item prop="date1">
                    <el-date-picker
                        v-model="FundForm.start"
                        type="date"
                        label="Pick a date"
                        placeholder="Pick a date"
                        style="width: 100%"
                    />
                  </el-form-item>
                </el-col>
                <el-col class="text-center" :span="2">
                  <span class="text-gray-500"></span>
                </el-col>
                <el-col :span="11">
                  <el-form-item prop="date2">
                    <el-time-picker
                        v-model="FundForm.end"
                        type="date"
                        label="Pick a date"
                        placeholder="Pick a date"
                        style="width: 100%"
                    />
                  </el-form-item>
                </el-col>
              </el-form-item>

            </el-form>
            <template #footer>
      <span class="dialog-footer">
        <el-button type="success" @click="FundDialogVisible = false">确认</el-button>
        <el-button type="danger" @click="FundDialogVisible = false">
          取消
        </el-button>
      </span>
            </template>
          </el-dialog>

          <el-dialog v-model="NoticeDialogVisible" title="发布通知" width="30%" draggable>

            <el-form :model="NoticeForm" label-width="40px">
              <el-form-item label="通知">
                <el-input v-model="NoticeForm.notice" type="textarea" />
              </el-form-item>
            </el-form>
            <template #footer>
      <span class="dialog-footer">
        <el-button type="success" @click="NoticeDialogVisible = false">确认</el-button>
        <el-button type="danger" @click="NoticeDialogVisible = false">
          取消
        </el-button>
      </span>
            </template>
          </el-dialog>

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
    line-height: 75px;
    font-size: 24px;
    border-radius: 5px;
    box-shadow: 0 0 12px rgba(0, 0, 0, 0.12)

}

.RecentApply{
  height: 268px;
  background-color: white;
  line-height: 50px;
  font-size: 24px;
  border-radius: 5px;
  box-shadow: 0 0 12px rgba(0, 0, 0, 0.12)
}

.Function {
    height: 268px;
    background-color: white;
    line-height: 50px;
    font-size: 24px;
    border-radius: 5px;
    box-shadow: 0 0 12px rgba(0, 0, 0, 0.12)
}

.Background {
    width: 100%;
    height: 800px;
    background-color: whitesmoke;
    background-position: 0 0;
    background-repeat: no-repeat;
}

.flex-grow {
    flex-grow: 1;
}

.info-divide {
  width: 100%;
  height: 22px;
  display: flex;
  flex-direction: row;
  font-size: 14px;
}
.info-divide > div {
  height: 22px;
  flex: 1;
}

.function-button{
  line-height: 25px;
  align-items: center;
  text-align: center;
}



</style>

<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue'
import router from "@/router";
import request from "@/util/request";
import { Delete, Edit, Search, Share, Upload, UploadFilled, Document, View, Coin, Notification, HomeFilled} from '@element-plus/icons-vue'


const activeIndex = ref('1')
const userData = reactive({
    username: '',
    name: '',
    phoneNumber: '',
    type: ''
})

const OutputForm = reactive({
  researchGroup: '',
  fundName: ''
})

const NoticeForm = reactive({
  notice: ''
})

const FundForm = reactive({
  researchGroup: '',
  fundName: '',
  fundNumber: '',
  start: Date,
  end: Date,
  sum: 0
})

let OutputDialogVisible = ref(false)
let FundDialogVisible = ref(false)
let NoticeDialogVisible = ref(false)

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

  const Notices = [
    {
      notice: "系统已上线"
    },
    {
      notice: "已收到提交"
    },

  ]

function AllApply() {
    router.push({path: '/apply'})
}

function FundByResearchGroup() {
    router.push({path: '/fund'})
}
function ClickOnOutput(){

}


</script>

