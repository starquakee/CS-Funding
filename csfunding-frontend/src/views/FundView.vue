<template>
    <div>

        <div class="SelectForm">
            <div style="height: 10px"></div>
            <el-form :inline="true" :model="SearchForm" class="demo-form-inline">
                <el-form-item label="编号">
                    <el-input v-model="SearchForm.FundNumber" placeholder="经费编号"/>
                </el-form-item>
                <el-form-item label="名称">
                    <el-input v-model="SearchForm.FundName" placeholder="经费名称"/>
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
                    <div style="display: flex;height: 200px; background-color: whitesmoke">
                        <div style="flex: 1; text-align: center;display: flex; justify-content: center; align-items: center;">
                            <h4 style="margin-right: 10px;">详细信息：</h4>
                            <el-card style="width: 30%;" shadow="never">
                                <h4>已使用经费: {{ props.row.sum - props.row.remain }}</h4>
                                <h4>当前执行率: {{ props.row.rate }}</h4>
                                <h4>剩余时间: {{ props.row.remainDay }}</h4>
                            </el-card>
                        </div>

                        <div style="flex: 1; text-align: center;display: flex; justify-content: center; align-items: center;">
                            <img src="@/img/user.png" width="580" height="120">
                        </div>
                    </div>

<!--                    <div style="display: flex; justify-content: center; align-items: center; background-color: whitesmoke">-->
<!--                        <h4 style="margin-right: 10px;">最近申请记录：</h4>-->
<!--                        <el-table :data="props.row.applies" border class="ChildTable"-->
<!--                                  style="width: 65%; margin-bottom: 20px">-->
<!--                            <el-table-column prop="name" label="经费名称" align="center"/>-->
<!--                            <el-table-column prop="researchGroup" label="课题组名称" align="center"/>-->
<!--                            <el-table-column prop="applyPerson" label="申请人" align="center"/>-->
<!--                            <el-table-column prop="amount" label="申请金额" align="center"/>-->
<!--                            <el-table-column prop="state" label="申请状态" align="center">-->
<!--                                <template #default="props">-->

<!--                                    <el-tag type="error" size="small" plain v-if="props.row.state === 'fail'"-->
<!--                                            effect="dark">-->
<!--                                        申请失败-->
<!--                                    </el-tag>-->

<!--                                    <el-tag type="warning" size="small" plain v-if="props.row.state === 'submit'"-->
<!--                                            effect="dark">-->
<!--                                        未审核-->
<!--                                    </el-tag>-->

<!--                                    <el-tag type="success" size="small" plain v-if="props.row.state === 'pass'"-->
<!--                                            effect="dark">-->
<!--                                        申请通过-->
<!--                                    </el-tag>-->

<!--                                </template>-->
<!--                            </el-table-column>-->
<!--                        </el-table>-->
<!--                    </div>-->
                </template>
            </el-table-column>

            <el-table-column prop="id" label="经费编号" align="center"/>
            <el-table-column prop="name" label="经费名称" align="center"/>
            <el-table-column prop="sum" label="经费总额" align="center"/>
            <el-table-column prop="remain" label="经费剩余" align="center"/>
            <el-table-column prop="start" label="开始时间" align="center"/>
            <el-table-column prop="end" label="结束时间" align="center"/>
            <el-table-column prop="state" label="是否达标" align="center">
                <template #default="props">
                    <el-tag type="error" size="small" plain v-if="props.row.remainDay < 60
                                                 && props.row.remain/props.row.sum > 0.2" effect="dark">
                        不合格
                    </el-tag>

                    <el-tag type="success" size="small" plain v-if="!(props.row.remainDay < 60
                                                 && props.row.remain/props.row.sum > 0.2)" effect="dark">
                        合格
                    </el-tag>

                </template>
            </el-table-column>
        </el-table>

        <div class="Pagination">
            <el-pagination background layout="prev, pager, next" :total="1000"/>
        </div>


        <div class="Bottom">
            <div style="height: 5px"></div>
            <el-button style="background-color: #8f000b; border: #8f000b">
                <el-icon style="vertical-align: middle;" size="25px">
                    <Back/>
                </el-icon>
                <font style="color: white">返回主页</font>
            </el-button>
        </div>

    </div>
</template>

<script lang="ts" setup>

import {onMounted, reactive} from "vue";
import type {TableColumnCtx} from 'element-plus'
import {
    Delete, Edit, Search, Share, Upload, Bell, Back,
    UploadFilled, Document, View, Coin, Notification, HomeFilled
} from '@element-plus/icons-vue'
import request from "@/util/request";
import {useRoute} from "vue-router";
import moment from "moment"

const route = useRoute()

// const tableData : fund[] = [
//   {
//     id: 'xxx',
//     name: '经费1',
//     sum: 1000,
//     remain: 500,
//     start: 'xxxx-yy-dd',
//     end: 'xxxx-yy-dd',
//     remainDay: 5,
//     applies: [
//       {
//         name: '经费1',
//         researchGroup: '王',
//         applyPerson: 'xxx',
//         amount: 500,
//         state: 'pass'
//       },
//       {
//         name: '经费1',
//         researchGroup: '王',
//         applyPerson: 'xxx',
//         amount: 500,
//         state: 'pass'
//       }
//     ]
//   },
//   {
//     id: 'xxx',
//     name: '经费2',
//     sum: 1000,
//     remain: 0,
//     start: 'xxxx-yy-dd',
//     end: 'xxxx-yy-dd',
//     remainDay: 5,
//     applies:[]
//   },
//   {
//     id: 'xxx',
//     name: '经费3',
//     sum: 1000,
//     remain: 1000,
//     start: 'xxxx-yy-dd',
//     end: 'xxxx-yy-dd',
//     remainDay: 5,
//     applies:[]
//   }
// ]

const tableData = reactive<fund[]>([])

function getFundData(fid: any) {
    request({
        url: `/get-fund?uuid=${fid}`,
        method: 'get',
    }).then(res => {
        let rd = res.data.data;
        let fd: fund = {
            id: rd.uuid,
            name: rd.fundName,
            sum: rd.sum,
            remain: rd.balance,
            start: moment.unix(rd.startTime / 1000).format('YYYY-MM-DD'),
            end: moment.unix(rd.endTime / 1000).format('YYYY-MM-DD'),
            remainDay: rd.remainDays,
            applies: []
        };
        tableData.push(fd)
    })
}

function getTableData(gid: any) {
    request({
        url: `/fund-research-group?gid=${gid}`,
        method: 'get'
    }).then(res => {
        let groups = res.data.data;
        groups.forEach((item: any) => {
            getFundData(item)
        })
    })
}

onMounted(() => {
    getTableData(route.query.gid);
    // getFundData(route.query.gid);
})

const SearchForm = reactive({
    FundNumber: '',
    FundName: '',
})

interface fund {
    id: string,
    name: string,
    sum: number,
    remain: number,
    start: string,
    end: string,
    remainDay: number,
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
        {
            row: fund
            rowIndex: number
        }) => {
    if (row.remainDay < 60 && row.remain / row.sum > 0.2) {
        return 'warning-row'
    }
    return 'success-row'
}

interface SummaryMethodProps<T = fund> {
    columns: TableColumnCtx<T>[]
    data: T[]
}

const getSummaries = (param: SummaryMethodProps) => {
    const {columns, data} = param
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


</script>

<style>
.ChildTable {

}
</style>