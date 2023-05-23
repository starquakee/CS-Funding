<template>
    <div class="Background">


        <el-container>

            <el-main>
                <div class="Main">
                    <el-row>

                        <el-col :span="7">
                            <el-card class="info">

                                <div class="info-header" style="height: 40px">
                                    <span>用户信息</span>
                                    <el-button type="primary" :icon="Edit"
                                    @click="EditKeyVisible=true">修改密码</el-button>

                                </div>
                                <div style="height: 20px">
                                </div>

                                <div class="info-divide">

                                    <div style="margin-top: 15px; margin-left: 15px">
                                        <img src="@/img/user.png" width="80" height="100" v-if="userData.type==='用户'">
                                        <img src="@/img/admin.png" width="80" height="100"
                                             v-if="userData.type!=='用户'">
                                    </div>

                                    <div style="font-size: 16px">
                                        账号：{{ userData.username }} <br>
                                        姓名：{{ userData.name }} <br>
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
                                    <div class="info-divide">
                                        <div style="font-size: 24px;">
                                            课题组
                                        </div>
                                        <div>
                                            <el-form :inline="true" :model="ResearchGroupForm" class="demo-form-inline"
                                                     style="margin-top: 10px">

                                                <el-form-item label-width="1px">
                                                    <el-input v-model="ResearchGroupForm.ResearchGroupName"
                                                              placeholder="组名"/>
                                                </el-form-item>

                                            </el-form>


                                        </div>
                                        <el-button type="primary" style="margin-top: 25px">查询</el-button>
                                    </div>

                                </el-header>
                                <el-main>
                                    <el-table :data="researchGroup" style="width: 95%" class="GroupTable" >
                                        <el-table-column prop="name" label="课题组名称" align="center"/>
                                        <el-table-column label="操作" align="center">
                                            <template #default="props">
                                                <el-button @click="FundByResearchGroup(props.row)" type="primary" size="small" plain>
                                                    查看
                                                </el-button>
                                                <el-button type="danger" size="small" plain>
                                                    删除
                                                </el-button>
                                            </template>
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
                                    <el-button @click="NoticeDialogVisible=true" type="primary" :icon="Edit" style="margin-left: 220px"
                                               v-if="userData.type!=='用户'" >添加
                                    </el-button>
                                </el-header>
                                <el-main>
                                    <el-table :data="Notices" style="width: 95%" class="GroupTable">
                                        <el-table-column prop="notice" label="通知内容" width="210px"/>
                                        <el-table-column v-if="userData.type!=='用户'">
                                            <el-button type="warning" size="small" plain>
                                                修改
                                            </el-button>
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
                                    <el-button type="primary" :icon="Search" style="margin-left: 588px"
                                               @click="AllApply">查看全部
                                    </el-button>
                                    <el-table :data="RecentApply" style="width: 100%">
                                        <el-table-column prop="name" align="center" label="经费名称"/>
                                        <el-table-column prop="researchGroup" align="center" label="课题组名称"/>
                                        <el-table-column prop="applyPerson" align="center" label="申请人"/>
                                        <el-table-column prop="amount" align="center" label="申请金额"/>
                                        <el-table-column align="center" label="申请状态">
                                            <template #default="props">

                                                <el-tag type="error" size="small" plain
                                                        v-if="props.row.state === 'fail'" effect="dark">
                                                    申请失败
                                                </el-tag>

                                                <el-tag type="warning" size="small" plain
                                                        v-if="props.row.state === 'submit'" effect="dark">
                                                    未审核
                                                </el-tag>

                                                <el-tag type="success" size="small" plain
                                                        v-if="props.row.state === 'pass'" effect="dark">
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
                                            <el-icon style="vertical-align: middle;" size="25px">
                                                <UploadFilled/>
                                            </el-icon>
                                        </el-button>
                                        <br>
                                        我的申请
                                    </div>

                                    <div class="function-button" v-if="userData.type!=='用户'" @click="AllApply">
                                        <el-button type="primary" style="height: 50px; width: 50px" circle>
                                            <el-icon style="vertical-align: middle;" size="25px">
                                                <View/>
                                            </el-icon>
                                        </el-button>
                                        <br>
                                        审核申请
                                    </div>

                                    <div class="function-button">
                                        <el-button type="primary" style="height: 50px; width: 50px"
                                                   @click="ClickOnOutput; OutputDialogVisible=true;"
                                                   circle>
                                            <el-icon style="vertical-align: middle;" size="25px">
                                                <Document/>
                                            </el-icon>
                                        </el-button>
                                        <br>
                                        导出文件

                                    </div>

                                </div>
                                <div class="info-divide" style="height: 70px">
                                    <div class="function-button" v-if="userData.type==='用户'">
                                        <el-button type="primary" style="height: 50px; width: 50px" circle>
                                            <el-icon style="vertical-align: middle;" size="25px">
                                                <Edit/>
                                            </el-icon>
                                        </el-button>
                                        <br>
                                        编辑信息
                                    </div>

                                    <div class="function-button" v-if="userData.type==='用户'">
                                    </div>

                                    <div class="function-button" v-if="userData.type!=='用户'">
                                        <el-button type="primary" style="height: 50px; width: 50px" circle
                                                   @click="FundDialogVisible=true">
                                            <el-icon style="vertical-align: middle;" size="25px">
                                                <Coin/>
                                            </el-icon>
                                        </el-button>
                                        <br>
                                        增添经费
                                    </div>

                                    <div class="function-button" v-if="userData.type!=='用户'">
                                        <el-button type="primary" style="height: 50px; width: 50px" circle
                                                   @click="EditResearchGroupVisible=true">
                                            <el-icon style="vertical-align: middle;" size="25px">
                                                <Edit/>
                                            </el-icon>
                                        </el-button>
                                        <br>
                                        增加/编辑课题组
                                    </div>
                                </div>
                            </el-card>
                        </el-col>

                    </el-row>

                </div>
            </el-main>

            <el-dialog v-model="OutputDialogVisible" title="导出数据" width="30%" draggable>

                <el-form :model="OutputForm" label-width="120px">
                    <el-form-item label="课题组名称">
                        <el-select v-model="OutputForm.researchGroup" placeholder="经费名称">
                            <el-option label="王" value="王"/>
                            <el-option label="李" value="李"/>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="经费名称">
                        <el-select v-model="OutputForm.fundName" placeholder="经费名称">
                            <el-option label="经费1" value="经费1"/>
                            <el-option label="经费2" value="经费2"/>
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
                            <el-option label="王" value="王"/>
                            <el-option label="李" value="李"/>
                        </el-select>

                    </el-form-item>


                    <el-form-item label="经费编号">
                        <el-input v-model="FundForm.fundNumber"/>
                    </el-form-item>

                    <el-form-item label="经费名称">
                        <el-input v-model="FundForm.fundName"/>
                    </el-form-item>


                    <el-form-item label="总金额">
                        <el-input v-model="FundForm.sum"/>
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
                    <el-button type="danger" @click="FundDialogVisible = false; ">
                      取消
                    </el-button>
                  </span>
                </template>
            </el-dialog>

            <el-dialog v-model="NoticeDialogVisible" title="发布通知" width="30%" draggable>

                <el-form :model="NoticeForm" label-width="40px" label-position="top">
                    <el-form-item label="不达标课题组/经费">
                        <el-col :span="11">
                            <el-select v-model="NoticeForm.researchGroup" placeholder="课题组名">
                                <el-option label="Zone one" value="shanghai"/>
                                <el-option label="Zone two" value="beijing"/>
                            </el-select>
                        </el-col>
                        <el-col :span="11">
                            <el-select v-model="NoticeForm.fund" placeholder="经费名">
                                <el-option label="Zone one" value="shanghai"/>
                                <el-option label="Zone two" value="beijing"/>
                            </el-select>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="备注">
                        <el-input v-model="NoticeForm.notice" type="textarea"/>
                    </el-form-item>
                </el-form>
                <template #footer>
                  <span class="dialog-footer">
                    <el-button type="primary" @click="NoticeDialogVisible = false">一键发送</el-button>
                    <el-button type="success" @click="NoticeDialogVisible = false">发布通知</el-button>
                    <el-button type="danger" @click="NoticeDialogVisible = false">取消</el-button>
                  </span>
                </template>
            </el-dialog>

          <el-dialog v-model="EditResearchGroupVisible" title="增加/编辑课题组" width="30%"
                      draggable>

            <el-form :model="EditResearchGroupForm" label-width="40px"
                     label-position="top" rules="EditResearchGroupFormRule">
              <el-form-item label="操作类型">
                <el-select v-model="EditResearchGroupForm.Operation1" placeholder="经费名" >
                <el-option label="增加课题组" value="add"/>
                <el-option label="编辑课题组" value="edit"/>
              </el-select>
              </el-form-item>
              <el-form-item label="选择课题组" v-if="EditResearchGroupForm.Operation1==='edit'" >
              <el-select v-model="EditResearchGroupForm.ResearchGroupName" placeholder="">
                <el-option label="增加课题组" value="add"/>
                <el-option label="编辑课题组" value="edit"/>
              </el-select>
              </el-form-item>
              <el-form-item label="新建课题组" v-if="EditResearchGroupForm.Operation1==='add'">
                <el-input v-model="EditResearchGroupForm.ResearchGroupName"/>
              </el-form-item>
              <el-form-item label="操作类型" v-if="EditResearchGroupForm.Operation1==='edit'">
                <el-select v-model="EditResearchGroupForm.Operation2" >
                  <el-option label="增加用户" value="add"/>
                  <el-option label="删除用户" value="delete"/>
                </el-select>
              </el-form-item>
              <el-form-item label="用户" v-if="EditResearchGroupForm.Operation1==='edit'">
                <el-select v-model="EditResearchGroupForm.Operation2">
                  <el-option label="如果Operation2为add,在不负责管理这个课题组的users里选" value="add"/>
                  <el-option label="如果Operation2为delete,在负责管理这个课题组的user里选" value="delete"/>
                </el-select>
              </el-form-item>


            </el-form>
            <template #footer>
                  <span class="dialog-footer">
                    <el-button type="success" @click="EditResearchGroupVisible= false">确定</el-button>
                    <el-button type="danger" @click="EditResearchGroupVisible = false">取消</el-button>
                  </span>
            </template>
          </el-dialog>

          <el-dialog v-model="EditKeyVisible" title="修改密码" width="25%" draggable>

            <el-form
                ref="ruleFormRef"
                :model="editKeyForm"
                status-icon
                :rules="rules"
                label-width="120px"
                class="demo-ruleForm"
            >
              <el-form-item label="原密码" prop="initialPass">
                <el-input v-model.number="editKeyForm.initialPass" />
              </el-form-item>
              <el-form-item label="新密码" prop="pass">
                <el-input v-model="editKeyForm.editPass" type="password" autocomplete="off" />
              </el-form-item>
              <el-form-item label="请重复" prop="checkPass">
                <el-input
                    v-model="editKeyForm.checkPass"
                    type="password"
                    autocomplete="off"
                />
              </el-form-item>

              <el-form-item>
                <el-button type="primary" @click="submitForm(ruleFormRef);"
                >提交</el-button>
                <el-button @click="resetForm(ruleFormRef)">重置</el-button>
              </el-form-item>
            </el-form>

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
    line-height: 50px;
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

.RecentApply {
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

.function-button {
    line-height: 25px;
    align-items: center;
    text-align: center;
}


</style>

<script setup lang="ts">
import {storeToRefs} from "pinia";
import {useUserStore} from "@/stores/user";
import {onMounted, reactive, ref} from 'vue'
import router from "@/router";
import request from "@/util/request";
import {
    Delete, Edit, Search, Share, Upload, Bell, Back,
    UploadFilled, Document, View, Coin, Notification, HomeFilled
} from '@element-plus/icons-vue'

import {type FormInstance, type FormRules, ElMessage, ElNotification  } from 'element-plus'

const ruleFormRef = ref<FormInstance>()

const checkInitialPass = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('请输入原密码'))
  }
  setTimeout(() => {
    if (value != userData.key) {
      callback(new Error('密码不正确'))
    } else {
      callback()
    }
  }, 1000)
}

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('新输入新密码'))
  } else {
    if (editKeyForm.checkPass !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('checkPass', () => null)
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入新密码'))
  } else if (value !== editKeyForm.editPass) {
    callback(new Error("两次密码不一致，请检查!"))
  } else {
    callback()
  }
}

const editKeyForm = reactive({
  editPass: '',
  checkPass: '',
  initialPass: '',
})

const rules = reactive<FormRules>({
  pass: [{ validator: validatePass, trigger: 'blur' }],
  checkPass: [{ validator: validatePass2, trigger: 'blur' }],
  initialPass: [{ validator: checkInitialPass, trigger: 'blur' }],
})

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      SuccessMessage()
      EditKeyVisible.value = false
      console.log('submit!')
    } else {
      FailMessage()
      console.log('error submit!')
      return false
    }
  })
}

const SuccessMessage = () => {
  ElMessage.success('提交成功')
}

const FailMessage = () => {
  ElMessage.error('提交失败')
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
  editKeyForm.editPass = ''
}

const {isAdmin, userName} = storeToRefs(useUserStore());

const userData = reactive({
    username: '',
    name: '',
    phoneNumber: '',
    type: '',
    key: ''
})

const OutputForm = reactive({
    researchGroup: '',
    fundName: ''
})

const NoticeForm = reactive({
    notice: '',
    researchGroup: '',
    fund: ''
})

const FundForm = reactive({
    researchGroup: '',
    fundName: '',
    fundNumber: '',
    start: Date,
    end: Date,
    sum: 0
})

const ResearchGroupForm = reactive({
    ResearchGroupName: ''
})

const EditResearchGroupForm = reactive({
  Operation1: '',
  ResearchGroupName: '',
  Operation2: ''
})

const EditResearchGroupFormRule = reactive({
  Operation1: [
    {
      required: true,
      message: '请选择一种操作',
      trigger: 'change',
    },
  ],
  Operation2: [
    {
      required: true,
      message: '请选择一种操作',
      trigger: 'change',
    },
  ],
  ResearchGroupName: [
    {
      required: true,
      message: '请选择一个课题组',
      trigger: 'change',
    },
  ]
})


let OutputDialogVisible = ref(false)
let FundDialogVisible = ref(false)
let NoticeDialogVisible = ref(false)
let EditKeyVisible = ref(false)
let EditResearchGroupVisible = ref(false)

function getResearchGroup() {
    request({
        url: '/get-all-research-groups',
        method: 'get'
    }).then(r => {
        r.data.data.forEach((val: any, index: any, arr: any) => {
            // console.log(val)
            let add = {
                name: val.teacher,
                sum: val.allFund,
                uuid: val.uuid
            }
            researchGroup.push(add)
        })
        // console.log(researchGroup)
    })
}

function getUserData(){
    request({
        url: '/current-user',
        method: 'get'
    }).then(res => {
        // console.log(res);
        let ud = res.data.data;
        userData.username = ud.id;
        userData.name = ud.name;
        userData.key = ud.key;
        userData.phoneNumber = ud.phoneNum;
        userData.type = ud.isAdmin ? '管理员' : '用户';
    })
}

onMounted(() => {
    getUserData();
    getResearchGroup();
})

const researchGroup = reactive([])

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

function FundByResearchGroup(group: any) {
    router.push({path: '/fund', query:{gid: group.uuid}})
    // console.log(group.uuid)
}

function ClickOnOutput() {

}


</script>

