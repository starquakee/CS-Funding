<template>

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

  <div class = "SelectForm" >
    <div style="height: 10px"></div>
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="申请人">
        <el-input v-model="formInline.user" placeholder="申请人姓名" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="formInline.region" placeholder="申请状态">
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
    <el-button type="primary" @click="SubmitApply; ApplyDialogVisible=true">
      新增申请
    </el-button>
  </div>

  <div>
    <el-table :data="tableData" border :row-class-name="tableRowClassName" style="width: 100%" >

<!--      <template #default="props">-->

      <el-table-column prop="name" label="经费名称" align="center"/>
      <el-table-column prop="researchGroup" label="课题组名称" align="center"/>
      <el-table-column prop="applyPerson" label="申请人" align="center"/>
      <el-table-column prop="amount" label="申请金额" align="center"/>
      <el-table-column prop="state" label="申请状态" align="center"/>
      <el-table-column label="操作" align="center">

        <template #default="props">

        <el-button @click="ClickOnCheck; CheckDialogVisible = true" type="primary" size="small" plain  >
          操作{{props.row.state}}
        </el-button>
        <el-button @click="ClickOnCheck; CheckDialogVisible = true" type="error" size="small" plain v-if="props.row.state === 'pass'">
          操作
        </el-button>
        <el-button @click="ClickOnCheck; CheckDialogVisible = true" type="primary" size="small" plain >
          按钮
        </el-button>
        </template>

      </el-table-column>
<!--      </template>-->
    </el-table>
  </div>

  <el-dialog v-model="CheckDialogVisible" title="审核申请" width="30%" draggable>

    <el-form :model="form" label-width="120px">
      <el-form-item label="是否通过">
        <el-radio-group v-model="form.resource">
          <el-radio label="通过" />
          <el-radio label="不通过" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="原因">
        <el-select v-model="form.region" placeholder="please select your zone">
          <el-option label="Zone one" value="shanghai" />
          <el-option label="Zone two" value="beijing" />
        </el-select>
      </el-form-item>
      <el-form-item label="评语">
        <el-input v-model="form.desc" type="textarea" />
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

  <el-dialog v-model="ApplyDialogVisible" title="提交申请" width="30%" draggable class="ApplyDialog">

    <el-form :model="form" label-width="120px">
      <el-form-item label="课题组名称">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="经费名称">
        <el-select v-model="form.region" placeholder="经费名称">
          <el-option label="Zone one" value="shanghai" />
          <el-option label="Zone two" value="beijing" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请种类">
        <el-col :span="11">
          <el-select v-model="form.region" placeholder="大类">
            <el-option label="Zone one" value="shanghai" />
            <el-option label="Zone two" value="beijing" />
          </el-select>
        </el-col>
        <el-col :span="2" class="text-center">
        </el-col>
        <el-col :span="11">
          <el-select v-model="form.region" placeholder="小类">
            <el-option label="Zone one" value="shanghai" />
            <el-option label="Zone two" value="beijing" />
          </el-select>
        </el-col>
      </el-form-item>
      <el-form-item label="评语">
        <el-input v-model="form.desc" type="textarea" />
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


  <div class="Pagination">
    <el-pagination background layout="prev, pager, next" :total="1000" />
  </div>


  <div class="Bottom">
    <div style="height: 5px"></div>
    <el-button style="background-color: #8f000b; border: #8f000b">
      <font style="color: white">返回主页</font>
    </el-button>
  </div>



</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import { ref } from 'vue'

let CheckDialogVisible = ref(false)
let ApplyDialogVisible = ref(false)

const formInline = reactive({
  user: '',
  region: '',
})

const onSubmit = () => {
  console.log('submit!')
}

function ClickOnCheck(){

}

function SubmitApply(){

}

interface Apply {
  name: string
  researchGroup: string
  applyPerson: string
  amount: number
  state: string
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

const form = reactive({
  name: '',
  region: '',
  date1: '',
  date2: '',
  type: [],
  resource: '',
  desc: '',
})

const buttonName = (
    {row, rowIndex,}:
        { row: Apply
          rowIndex: number
        }) => {
  if (row.state == 'submit') {
    return '审核'
  } else if (row.state == 'pass') {
    return '查看'
  }
  return '修改'
}

const tableData : Apply[] = [
  {
    name: '经费1',
    researchGroup: '王',
    applyPerson: 'xxx',
    amount: 1000,
    state: 'pass'
  },
  {
    name: '经费2',
    researchGroup: '李',
    applyPerson: 'xxx',
    amount: 2000,
    state: 'fail'
  },
  {
    name: '经费3',
    researchGroup: '李',
    applyPerson: 'xxx',
    amount: 3000,
    state: 'submit'
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
  top: 708px;
  height: 40px;
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

.ApplyDialog{
  display: flex;
  justify-content: center;
  align-items: center;
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