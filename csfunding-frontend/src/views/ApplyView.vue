<template>

  <div class="SelectForm">
    <div style="height: 10px"></div>
    <el-form :inline="true" :model="searchForm" class="demo-form-inline">
      <el-form-item label="经费">
        <el-input v-model="searchForm.fundName" placeholder="经费名"/>
      </el-form-item>
      <el-form-item label="课题组">
        <el-input v-model="searchForm.researchGroup" placeholder="课题组名"/>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="searchForm.state" placeholder="申请状态">
          <el-option label="全部" value=""/>
          <el-option label="未审核" value="submit"/>
          <el-option label="已通过" value="pass"/>
          <el-option label="未通过" value="fail"/>
          <el-option label="重提交" value="resubmitted"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onQuery">查询</el-button>
      </el-form-item>
    </el-form>
  </div>

  <div style="height: 50px" v-if="!isAdmin">
    <div style="height: 8px"></div>
    <el-button type="primary" @click="ClickOnSubmit(null)" style="margin-left: 5px">
      新增申请
    </el-button>
  </div>

  <div>
    <el-table :data="tableData" border :row-class-name="tableRowClassName" style="width: 100%">
      <el-table-column prop="fundName" label="经费名称" align="center"/>
      <el-table-column prop="researchGroup" label="课题组名称" align="center"/>
      <el-table-column prop="applyPerson" label="申请人" align="center"/>
      <el-table-column prop="money" label="申请金额" align="center"/>
      <el-table-column label="申请状态" align="center">
        <template #default="props">

          <el-tag type="error" size="small" plain v-if="props.row.state === 'fail'" effect="dark">
            申请失败
          </el-tag>

          <el-tag type="primary" size="small" plain v-if="props.row.state === 'resubmitted'" effect="dark">
            重新提交
          </el-tag>

          <el-tag type="warning" size="small" plain v-if="props.row.state === 'submit'" effect="dark">
            未审核
          </el-tag>

          <el-tag type="success" size="small" plain v-if="props.row.state === 'pass'" effect="dark">
            申请通过
          </el-tag>

        </template>
      </el-table-column>

      <el-table-column label="操作" align="center">

        <template #default="props">

          <el-button @click="ClickOnView(); Type1 = props.row.type1; Type2 = props.row.type2;
                    FundNumber = props.row.fundNumber; FundName = props.row.fundName;
                    Summary = props.row.summary;
                    ViewDialogVisible = true" type="primary" size="small" plain>
            查看
          </el-button>
          <el-button @click="ClickOnCheck(props.row);
                  CheckDialogVisible = true" type="primary" size="small" plain
                     v-if="props.row.state === 'submit' && isAdmin">
            审核
          </el-button>

          <el-button @click="ClickOnSubmit(props.row)"
                     type="warning" size="small" plain
                     v-if="props.row.state === 'fail' && !isAdmin">
            重新提交
          </el-button>
        </template>

      </el-table-column>
    </el-table>
  </div>

  <el-dialog v-model="ApplyDialogVisible" title="提交申请" width="30%" draggable class="ApplyDialog">

    <el-form :model="ApplyForm">
      <el-form-item label="经费名称">
        <el-cascader
            v-model="ApplyForm.fundName"
            :options="groupOption"
            placeholder="请选择经费"
            @change="onFundChange"
        />
      </el-form-item>
      <el-form-item label="申请种类">
        <el-col>
          <el-cascader
              v-model=ApplyForm.type
              :options="typeOption"
              placeholder="请选择申请种类"
          />
        </el-col>
      </el-form-item>
      <el-form-item label="金额">
        <el-input-number v-model="ApplyForm.money" :placeholder="leftMoneyText" :controls="false" :max="balance" :min=0 />
      </el-form-item>

      <el-form-item label="说明">
        <el-input v-model="ApplyForm.summary" type="textarea"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="success" @click="onApplySubmit">提交</el-button>
        <el-button type="danger" @click="ApplyDialogVisible = false">
          取消
        </el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog v-model="ViewDialogVisible" title="查看申请" width="30%" draggable class="ApplyDialog">

      <div>经费信息：{{ FundNumber }}-{{ FundName }}</div>
      <br>
      <div>申请类型：{{ Type1 }}-{{ Type2 }}</div>
      <br>
      <div>说明：{{ Summary }}</div>

    <template #footer>
      <span class="dialog-footer" style="height: 20px">
        <el-button type="danger" @click="ViewDialogVisible = false;">
          取消
        </el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog v-model="CheckDialogVisible" title="审核申请" width="30%" draggable>

    <el-form :model="CheckForm" label-width="120px">
      <el-form-item label="是否通过">
        <el-radio-group v-model="CheckForm.state">
          <el-radio label="通过"/>
          <el-radio label="不通过"/>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="评语">
        <el-input v-model="CheckForm.comment" type="textarea"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="success" @click="onCheckSubmit()">确认</el-button>
        <el-button type="danger" @click="CheckDialogVisible = false">
          取消
        </el-button>
      </span>
    </template>
  </el-dialog>



</template>

<script lang="ts" setup>
import {onMounted, reactive, toRaw} from 'vue'
import {ref} from 'vue'
import {storeToRefs} from "pinia";
import {useUserStore} from "@/stores/user";
import {
  Delete, Edit, Search, Share, Upload, Bell, Back,
  UploadFilled, Document, View, Coin, Notification, HomeFilled
} from '@element-plus/icons-vue'
import request from "@/util/request";

const ViewDialogVisible = ref(false)
const ApplyDialogVisible = ref(false)
const CheckDialogVisible = ref(false)

const FundNumber = ref("")
const FundName = ref("")
const Type1 = ref("")
const Type2 = ref("")
const Summary = ref("")
const leftMoneyText = ref("剩余经费0")
const balance = ref(0)

const {isAdmin, userName} = storeToRefs(useUserStore());

const groupOption = reactive([])

let inspectId = -1;

function getApplies() {
  // console.log(isAdmin.value)
  tableData.splice(0);
  // console.log(searchForm);
  let search = toRaw(searchForm);
  if (search.state == '')
    search.state = null
  if (!isAdmin.value) {
    request({
      url: '/search-my-apply',
      method: 'post',
      data: search
    }).then(res => {
      let rd = res.data.data;
      rd.forEach((item: any) => {
        // console.log(item)
        tableData.push(item);
      })
    })
  } else {
    request({
      url: '/search-all-apply',
      method: 'post',
      data: search
    }).then(res => {
      let rd = res.data.data;
      rd.forEach((item: any) => {
        // console.log(item)
        tableData.push(item);
      })
    })
  }
}

function getGroups() {
  request({
    url: '/current-group-fund-data',
    method: "GET"
  }).then(res => {
    // console.log(res.data.data[0].children.length)
    res.data.data.forEach((item: any) => {
      if (!item.children.length) {
        item.disabled = true;
      }
      groupOption.push(item)
    })
    // console.log(groupOption)
    // let rd = res.data.data;
    // rd.forEach((item: any) => {
    //     let group: Group = {
    //         name: item.teacher,
    //         gid: item.uuid,
    //     }
    //     groupOption.push(group)
    // })
  })
}

onMounted(async () => {
  while (!userName.value) {
    await new Promise(f => setTimeout(f, 100))
  }
  getApplies();
  getGroups();
})

const searchForm = reactive({
  researchGroup: '',
  fundName: '',
  state: ''
})

const ApplyForm = reactive({
  fundName: [],
  type: [],
  money: null,
  summary: '',
  resubmitId: -1
})

const CheckForm = reactive({
  state: '',
  comment: ''
})

const onQuery = () => {
  getApplies()
}

function onApplySubmit() {
  console.log(ApplyForm)
  let apply = {
    name: 'NoName',
    money: ApplyForm.money,
    type1: ApplyForm.type[0],
    type2: ApplyForm.type[1],
    fundID: ApplyForm.fundName[1].uuid,
    researchGroupID: ApplyForm.fundName[0],
    userID: -1,
    state: null,
    contentSummary: ApplyForm.summary,
    remark: null,
    apply: ApplyForm.resubmitId,
  }
  console.log(apply)
  request({
    url: '/register/apply',
    method: 'POST',
    data: apply
  }).then(res => {
    ApplyDialogVisible.value = false
    getApplies()
  })
}

function onCheckSubmit() {
  console.log(CheckForm);
  let inspect = {
    aid: inspectId,
    remark: CheckForm.comment,
    pass: CheckForm.state == '通过'
  }
  request({
    url: '/inspect-apply',
    method: "post",
    data: inspect
  }).then(res => {
    console.log(res);
    getApplies();
  })
  CheckDialogVisible.value = false;
}

function onFundChange() {
  let fund = ApplyForm.fundName;
  leftMoneyText.value = `剩余经费${fund[1].balance}`;
  balance.value = fund[1].balance;
}

function ClickOnView() {

}

function ClickOnSubmit(row: any) {
  ApplyDialogVisible.value = true;
  if (row == null){
    ApplyForm.money = null;
    ApplyForm.type = [];
    ApplyForm.fundName = [];
    ApplyForm.summary = '';
    ApplyForm.resubmitId = -1;
  } else {
    console.log(row);
    ApplyForm.fundName = [row.researchGroupId, {balance: row.balance, uuid: row.fundNumber}];
    onFundChange()
    ApplyForm.money = row.money;
    ApplyForm.type = [row.type1, row.type2];
    ApplyForm.summary = row.summary;
    ApplyForm.resubmitId = row.applyId;
  }
  console.log(ApplyForm);
}

function ClickOnCheck(row: any) {
  console.log(row)
  inspectId = row.applyId;
  if (row.state == 'pass')
    CheckForm.state = '通过';
  else if (row.state == 'fail')
    CheckForm.state = '不通过';
  else
    CheckForm.state = '';
  CheckForm.comment = row.remark;
}

interface Apply {
  fundName: string
  researchGroup: string
  applyPerson: string
  money: number
  state: string
  type1: string
  type2: string
  summary: string
  remark: string
  fundNumber: string
}

const typeOption = reactive([
  {
    value: '大类1',
    label: '大类1',
    children: [
      {value: '小类1-1', label: '小类1-1'},
      {value: '小类1-2', label: '小类1-2'},
      {value: '小类1-3', label: '小类1-3'},
    ]
  }, {
    value: '大类2',
    label: '大类2',
    children: [
      {value: '小类2-1', label: '小类2-1'},
      {value: '小类2-2', label: '小类2-2'},
      {value: '小类2-3', label: '小类2-3'},
      {value: '小类2-4', label: '小类2-4'},
    ]
  },
  {
    value: '大类3',
    label: '大类3',
    children: [
      {value: '小类3-1', label: '小类3-1'},
      {value: '小类3-2', label: '小类3-2'},
    ]
  },
])

const tableRowClassName = (
    {row, rowIndex,}:
        {
          row: Apply
          rowIndex: number
        }) => {
  if (row.state == 'submit') {
    return 'warning-row'
  } else if (row.state == 'pass') {
    return 'success-row'
  }
  return 'error-row'
}

const tableData = reactive([])

// const tableData : Apply[] = [
//   {
//     fundName: '经费1',
//     researchGroup: '王',
//     applyPerson: 'xxx',
//     money: 1000,
//     state: 'pass',
//     type1: '1',
//     type2: '2',
//     summary: '',
//     remark: '',
//     fundNumber: 'F1'
//
//   },
//   {
//     fundName: '经费2',
//     researchGroup: '李',
//     applyPerson: 'xxx',
//     money: 2000,
//     state: 'fail',
//     type1: '2',
//     type2: '2',
//     summary: '',
//     remark: '',
//     fundNumber: 'F2'
//
//   },
//   {
//     fundName: '经费3',
//     researchGroup: '李',
//     applyPerson: 'xxx',
//     money: 3000,
//     state: 'submit',
//     type1: '3',
//     type2: '2',
//     summary: '',
//     remark: '',
//     fundNumber: 'F3'
//   }
// ]
</script>

<style>

.SelectForm {
  background: none !important;
  border: 1px solid #ddd;
  padding: 0 10px;
  height: 55px;
}

.el-menu-demo {
  background-color: #8f000b;
}

.Bottom {
  position: fixed;
  width: 100%;
  top: 743px;
  height: 50px;
  background-color: #8f000b;
}

.Pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  width: 100%;
  top: 658px;
  height: 40px;
}


.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}

.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}

.el-table .error-row {
  --el-table-tr-bg-color: var(--el-color-error-light-9);
}
</style>