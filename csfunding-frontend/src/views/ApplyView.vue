<template>

  <div class = "SelectForm" >
    <div style="height: 10px"></div>
    <el-form :inline="true" :model="SearchForm" class="demo-form-inline">
      <el-form-item label="课题组">
        <el-input v-model="SearchForm.ResearchGroup" placeholder="课题组名" />
      </el-form-item>
      <el-form-item label="经费">
        <el-input v-model="SearchForm.FundName" placeholder="经费名" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="SearchForm.State" placeholder="申请状态">
          <el-option label="未审核" value="submit" />
          <el-option label="已通过" value="pass" />
          <el-option label="未通过" value="fail" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
  </div>

  <div style="height: 50px">
    <div style="height: 8px"></div>
    <el-button type="primary" @click="ClickOnSubmit; ApplyDialogVisible=true
                            ApplyForm.money = 0; ApplyForm.type1 = null;
                            ApplyForm.type2 = null; ApplyForm.fundName = null
                            ApplyForm.researchGroup = null">
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
        <el-button @click="ClickOnCheck();
                  CheckDialogVisible = true" type="primary" size="small" plain
                   v-if="props.row.state === 'submit' && userType !=='用户'">
          审核
        </el-button>

        <el-button @click="ClickOnCheck();
                CheckDialogVisible = true" type="primary" size="small" plain
                   v-if="props.row.state !== 'submit' && userType !=='用户'">
          修改
        </el-button>

        <el-button @click="ClickOnSubmit; ApplyDialogVisible=true
                            ApplyForm.money = props.row.money; ApplyForm.type1 = props.row.type1;
                            ApplyForm.type2 = props.row.type2; ApplyForm.fundName = props.row.fundName
                            ApplyForm.researchGroup = props.row.researchGroup"
                   type="warning" size="small" plain v-if="props.row.state === 'fail' && userType ==='用户'">
          重新提交
        </el-button>
        </template>

      </el-table-column>
    </el-table>
  </div>

  <el-dialog v-model="ApplyDialogVisible" title="提交申请" width="30%" draggable class="ApplyDialog">

    <el-form :model="ApplyForm">
    <el-form-item label="课题组名称">
      <el-select v-model="ApplyForm.researchGroup" placeholder="经费名称">
        <el-option label="王" value="王" />
        <el-option label="李" value="李" />
      </el-select>
    </el-form-item>
      <el-form-item label="经费名称">
        <el-select v-model="ApplyForm.fundName" placeholder="经费名称">
          <el-option label="经费1" value="经费1" />
          <el-option label="经费2" value="经费2" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请种类">
        <el-col :span="11">
          <el-select v-model="ApplyForm.type1" placeholder="大类">
            <el-option label="1" value="1" />
            <el-option label="2" value="2" />
          </el-select>
        </el-col>
        <el-col :span="2" class="text-center">
        </el-col>
        <el-col :span="11">
          <el-select v-model="ApplyForm.type2" placeholder="小类">
            <el-option label="1" value="1" />
            <el-option label="2" value="2" />
          </el-select>
        </el-col>
      </el-form-item>
      <el-form-item label="金额">
        <el-input v-model="ApplyForm.money" />
      </el-form-item>

      <el-form-item label="说明">
        <el-input v-model="ApplyForm.summary" type="textarea" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="success" @click="ApplyDialogVisible = false">提交</el-button>
        <el-button type="danger" @click="ApplyDialogVisible = false">
          取消
        </el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog v-model="ViewDialogVisible" title="查看申请" width="30%" draggable class="ApplyDialog">
    <el-card>
      <div>经费信息：{{FundNumber}}-{{FundName}}</div> <br>
      <div>申请类型：{{Type1}}-{{Type2}}</div> <br>
      <div>说明：{{Summary}}</div>
    </el-card>
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
          <el-radio label="通过" />
          <el-radio label="不通过" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="评语">
        <el-input v-model="CheckForm.comment" type="textarea" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="success" @click="CheckDialogVisible = false">确认</el-button>
        <el-button type="danger" @click="CheckDialogVisible = false">
          取消
        </el-button>
      </span>
    </template>
  </el-dialog>


  <div class="Pagination">
    <el-pagination background layout="prev, pager, next" :total="1000" />
  </div>


  <div class="Bottom">
    <div style="height: 5px"></div>
    <el-button style="background-color: #8f000b; border: #8f000b">
      <el-icon  style="vertical-align: middle;" size="25px">
        <Back/>
      </el-icon>
      <font style="color: white">返回主页</font>
    </el-button>
  </div>



</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import { ref } from 'vue'
import {storeToRefs} from "pinia";
import {useUserStore} from "@/stores/user";
import { Delete, Edit, Search, Share, Upload, Bell, Back,
  UploadFilled, Document, View, Coin, Notification, HomeFilled} from '@element-plus/icons-vue'

let ViewDialogVisible = ref(false)
let ApplyDialogVisible = ref(false)
let CheckDialogVisible = ref(false)

let FundNumber = ref("")
let FundName = ref("")
let Type1 = ref("")
let Type2 = ref("")
let Summary = ref("")

let userType = '用户1';

const SearchForm = reactive({
  ResearchGroup: '',
  FundName: '',
  State: ''
})

const ApplyForm = reactive({
  researchGroup: '',
  fundName: '',
  type1: '',
  type2: '',
  money: 0,
  summary: ''
})

const CheckForm = reactive({
  state:'',
  comment:''
})

const onSubmit = () => {
  console.log('submit!')
}

function ClickOnView(){

}

function ClickOnSubmit(){

}

function ClickOnCheck(){

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

const tableRowClassName = (
    {row, rowIndex,}:
        { row: Apply
          rowIndex: number
        }) => {
  if (row.state == 'submit') {
    return 'warning-row'
  } else if (row.state == 'pass') {
    return 'success-row'
  }
  return 'error-row'
}

const tableData : Apply[] = [
  {
    fundName: '经费1',
    researchGroup: '王',
    applyPerson: 'xxx',
    money: 1000,
    state: 'pass',
    type1: '1',
    type2: '2',
    summary: '',
    remark: '',
    fundNumber: 'F1'

  },
  {
    fundName: '经费2',
    researchGroup: '李',
    applyPerson: 'xxx',
    money: 2000,
    state: 'fail',
    type1: '2',
    type2: '2',
    summary: '',
    remark: '',
    fundNumber: 'F2'

  },
  {
    fundName: '经费3',
    researchGroup: '李',
    applyPerson: 'xxx',
    money: 3000,
    state: 'submit',
    type1: '3',
    type2: '2',
    summary: '',
    remark: '',
    fundNumber: 'F3'
  }
]
</script>

<style>

.SelectForm{
  background: none!important;
  border: 1px solid #ddd;
  padding: 0 10px;
  height: 55px;
}
.el-menu-demo{
  background-color: #8f000b;
}

.Bottom{
  position: fixed;
  width: 100%;
  top: 743px;
  height: 50px;
  background-color: #8f000b;
}

.Pagination{
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