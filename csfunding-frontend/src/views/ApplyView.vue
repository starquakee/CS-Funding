<template>
  <el-button @click="resetDateFilter">搜索</el-button>

  <el-table :data="tableData" style="width: 100%" >
    <el-table-column prop="name" label="经费名称" />
    <el-table-column prop="researchGroup" label="课题组名称" />
    <el-table-column prop="applyPerson" label="申请人" />
    <el-table-column prop="amount" label="申请金额" />
    <el-table-column prop="state" label="申请状态" >
    </el-table-column>
  </el-table>

</template>

<script>
export default {
  name: "ApplyView",
  data(){
    return {
      tableData : [
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
    }

  }
}
</script>

<style scoped>

</style>