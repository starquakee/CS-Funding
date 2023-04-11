<template>
  <el-button @click="resetDateFilter">搜索</el-button>

  <el-table :data="tableData" style="width: 100%" >
    <el-table-column type="expand">
      <template #default="props">
        <div >
          <h3>申请记录</h3>
          <el-table :data="tableApply" :border="childBorder">
            <el-table-column prop="name" label="经费名称" />
            <el-table-column prop="researchGroup" label="课题组名称" />
            <el-table-column prop="applyPerson" label="申请人" />
            <el-table-column prop="amount" label="申请金额" />
            <el-table-column prop="state" label="申请状态" >
            </el-table-column>
          </el-table>
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="id" label="经费编号" />
    <el-table-column prop="name" label="经费名称" />
    <el-table-column prop="sum" label="经费总额" />
    <el-table-column prop="remain" label="经费剩余" />
    <el-table-column prop="start" label="开始时间" />
    <el-table-column prop="end" label="结束时间" >

    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: "FundView",
  data(){
    return {
      tableData : [
        {
          id: 'xxx',
          name: '经费1',
          sum: 1000,
          remain: 500,
          start: 'xxxx-yy-dd',
          end: 'xxxx-yy-dd'
        },
        {
          id: 'xxx',
          name: '经费2',
          sum: 1000,
          remain: 500,
          start: 'xxxx-yy-dd',
          end: 'xxxx-yy-dd'
        },
        {
          id: 'xxx',
          name: '经费3',
          sum: 1000,
          remain: 1000,
          start: 'xxxx-yy-dd',
          end: 'xxxx-yy-dd'
        }
      ],
      tableApply:[
        {
          name: '经费1',
          researchGroup: '王',
          applyPerson: 'xxx',
          amount: 1000,
          state: 'pass'
        }
      ]
    }

  }
}
</script>

<style scoped>

</style>