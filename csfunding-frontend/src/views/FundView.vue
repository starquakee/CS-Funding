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
      <el-form-item label="编号">
        <el-input v-model="formInline.user" placeholder="经费编号" />
      </el-form-item>
      <el-form-item label="名称">
        <el-input v-model="formInline.user" placeholder="经费名称" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="formInline.region" placeholder="经费状态">
          <el-option label="合格" value="pass" />
          <el-option label="不合格" value="fail" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
  </div>


  <el-table :data="tableData" style="width: 100%" border class="ParentTable" :row-class-name="tableRowClassName"
            :summary-method="getSummaries" show-summary>

    <el-table-column type="expand">

      <template #default="props">
         <h3>{{props.row.name}}</h3>
          <h4>已使用经费: {{props.row.sum - props.row.remain}}</h4>
          <h4>当前执行率: {{props.row.rate}}</h4>
          <h4>剩余时间: {{props.row.remainDay}}</h4>

          <h4>最近申请记录</h4>
          <el-table :data="props.row.applies" border class="ChildTable">
            <el-table-column prop="name" label="经费名称" align="center"/>
            <el-table-column prop="researchGroup" label="课题组名称" align="center"/>
            <el-table-column prop="applyPerson" label="申请人" align="center"/>
            <el-table-column prop="amount" label="申请金额" align="center"/>
            <el-table-column prop="state" label="申请状态" align="center">
            </el-table-column>
          </el-table>

      </template>
    </el-table-column>

    <el-table-column prop="id" label="经费编号" align="center"/>
    <el-table-column prop="name" label="经费名称" align="center"/>
    <el-table-column prop="sum" label="经费总额" align="center"/>
    <el-table-column prop="remain" label="经费剩余" align="center"/>
    <el-table-column prop="start" label="开始时间" align="center"/>
    <el-table-column prop="end" label="结束时间" align="center"/>
    <el-table-column prop="state" label="是否达标" align="center">

    </el-table-column>
  </el-table>

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

import {reactive} from "vue";
import type { TableColumnCtx } from 'element-plus'

const formInline = reactive({
  user: '',
  region: '',
})

interface fund{
  id: string,
  name: string,
  sum: number,
  remain: number,
  state: string,
  start: string,
  end: string,
  remainDay: number,
  rate: number,
  applies: apply[]
}
interface apply {
  name: string
  researchGroup: string
  applyPerson: string
  amount: number
  state: string
}

const tableRowClassName = (
    {row, rowIndex,}:
        { row: fund
          rowIndex: number
        }) => {
  if (row.state != 'P') {
    return 'warning-row'
  }
  return 'success-row'
}

interface SummaryMethodProps<T = fund> {
  columns: TableColumnCtx<T>[]
  data: T[]
}

const getSummaries = (param: SummaryMethodProps) => {
  const { columns, data } = param
  const sums: string[] = []
  columns.forEach((column, index) => {
    if (index === 0) {
      sums[index] = '总'
      return
    }
    const values = data.map((item) => Number(item[column.property]))
    if (!values.every((value) => Number.isNaN(value))) {
      sums[index] = ` ${values.reduce((prev, curr) => {
        const value = Number(curr)
        if (!Number.isNaN(value)) {
          return prev + curr
        } else {
          return prev
        }
      }, 0)}`
    } else {
      sums[index] = '/'
    }
  })

  return sums
}

const tableData : fund[] = [
  {
    id: 'xxx',
    name: '经费1',
    sum: 1000,
    remain: 500,
    state: 'P',
    start: 'xxxx-yy-dd',
    end: 'xxxx-yy-dd',
    remainDay: 5,
    rate: 0,
    applies: [
      {
        name: '经费1',
        researchGroup: '王',
        applyPerson: 'xxx',
        amount: 500,
        state: 'pass'
      },
      {
        name: '经费1',
        researchGroup: '王',
        applyPerson: 'xxx',
        amount: 500,
        state: 'pass'
      }
    ]
  },
  {
    id: 'xxx',
    name: '经费2',
    sum: 1000,
    remain: 500,
    state: 'P',
    start: 'xxxx-yy-dd',
    end: 'xxxx-yy-dd',
    remainDay: 5,
    rate: 0,
    applies:[]
  },
  {
    id: 'xxx',
    name: '经费3',
    sum: 1000,
    remain: 1000,
    state: 'P',
    start: 'xxxx-yy-dd',
    end: 'xxxx-yy-dd',
    remainDay: 5,
    rate: 0,
    applies:[]
  }
]


</script>

<style>
.ChildTable{
  margin-top: 20px;
  margin-left: 20px;
  margin-right: 20px;
}
.ParentTable{

}
</style>