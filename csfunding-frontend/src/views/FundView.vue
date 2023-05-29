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
          <el-button type="primary" @click="onQuery">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="PieChartVisible=!PieChartVisible">{{ PieChartVisible ? '隐藏' : '显示' }}
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="openFolderDialog">导出表格</el-button>
        </el-form-item>
      </el-form>
    </div>


    <div class="PieChart" ref="chartDom" :style="{ width: '800px', height: '400px', name,
                                display: PieChartVisible ? 'block' : 'none'}">
    </div>


    <el-table :data="tableData" style="width: 100%" border class="ParentTable" :row-class-name="tableRowClassName"
              :summary-method="getSummaries" show-summary>

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
      <el-table-column prop="operation" label="操作" align="center">
        <template #default="props">
          <el-button @click="onBarChartClick(props.row)" type="primary" size="small" plain>
            {{ BarChartVisible ? '返回' : '月支出' }}
          </el-button>
          <el-button  type="primary" size="small" plain @click="EndDay = props.row.endTimeStamp;
                                                                StartDay = props.row.startTimeStamp;
                                                                  Rate = 1 - props.row.remain/props.row.sum;
                                                              UsedAmount = props.row.sum -
                                                              props.row.remain;InfoVisible = true">
            详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="BarChart"
         v-show="BarChartVisible">
      <div style="height: 10%; width: 100%; text-align: right">
        <el-icon size="50" style="padding: 5px" @click="BarChartVisible=false"><Close /></el-icon>
      </div>
      <div style="height: 55vh; width: 60vw" ref="chartContainer">

      </div>
    </div>
    <el-dialog v-model="InfoVisible" title="详细信息" width="30%" draggable>
      <h4>已使用经费: {{UsedAmount}}</h4>
      <h4>当前执行率: {{Rate}}</h4>
      <h4>剩余时间: {{ ((EndDay - now) - (EndDay - now) % (3600 * 24)) / (3600 * 24) }}天</h4>
      <h4>进行时间: {{ ((now - StartDay) - (now - StartDay) % (3600 * 24)) / (3600 * 24) }}天</h4>
      <template #footer>
    <span class="dialog-footer" style="height: 20px">
      <el-button type="danger" @click="InfoVisible = false;">
        取消
      </el-button>
    </span>
      </template>
    </el-dialog>

  </div>
</template>

<script lang="ts" setup>

import {onMounted, reactive, ref, toRaw} from "vue";
import type {TableColumnCtx} from 'element-plus'
import request from "@/util/request";
import {useRoute} from "vue-router";
import moment from "moment"
import * as echarts from 'echarts';
import {Close} from '@element-plus/icons-vue'

const PieChartVisible = ref(false)
const chartDom = ref<HTMLElement | null>(null);

function initRoseChart() {
  if (chartDom.value) {
    const myChart = echarts.init(chartDom.value);
    let option;

    option = {
      title: {
        text: '课题组中经费使用情况',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        left: 'center',
        top: 'bottom',
        data: [
          'rose1',
          'rose2',
          'rose3',
          'rose4',
          'rose5',
          'rose6',
          'rose7',
          'rose8'
        ]
      },
      toolbox: {
        show: true,
        feature: {
          mark: {show: true},
          dataView: {show: true, readOnly: false},
          restore: {show: true},
          saveAsImage: {show: true}
        }
      },
      series: [
        {
          name: '经费总额',
          type: 'pie',
          radius: [20, 140],
          center: ['20%', '50%'],
          roseType: 'radius',
          itemStyle: {
            borderRadius: 5
          },
          label: {
            show: true
          },
          emphasis: {
            label: {
              show: true
            }
          },
          data: [
            {value: 40, name: 'rose 1'},
            {value: 33, name: 'rose 2'},
            {value: 28, name: 'rose 3'},
            {value: 22, name: 'rose 4'},
            {value: 20, name: 'rose 5'},
            {value: 15, name: 'rose 6'},
            {value: 12, name: 'rose 7'},
            {value: 10, name: 'rose 8'}
          ]
        },
        {
          name: '剩余经费',
          type: 'pie',
          radius: [20, 140],
          center: ['75%', '50%'],
          roseType: 'area',
          itemStyle: {
            borderRadius: 5
          },
          data: [
            {value: 30, name: 'rose 1'},
            {value: 28, name: 'rose 2'},
            {value: 26, name: 'rose 3'},
            {value: 24, name: 'rose 4'},
            {value: 22, name: 'rose 5'},
            {value: 20, name: 'rose 6'},
            {value: 18, name: 'rose 7'},
            {value: 16, name: 'rose 8'}
          ]
        },

      ]
    };

    option && myChart.setOption(option);
  }
}


const chartContainer = ref<HTMLElement>();

let BarChartVisible = ref(false)
let InfoVisible = ref(false)
let RemainDay = ref(0)
let Rate = ref(0)
let UsedAmount = ref(0)
let chart: echarts.ECharts
let EndDay = ref(0)
let StartDay = ref(0)

function onBarChartClick(row: any) {
  request({
    url: '/get-12month-cost',
    method: "GET",
    params: {
      uuid: row.uuid
    }
  }).then(r => {
    let rd = r.data.data;
    let ss = chart.getOption().series;
    ss[0].data = rd.map((data) => data.value);
    chart.setOption({
      xAxis: {
        type: 'category',
        data: rd.map((data) => data.name),
      },
      series: ss
    })


  })
  BarChartVisible.value = !BarChartVisible.value;
}


function initBarChart() {
  if (chartContainer.value) {
    chart = echarts.init(chartContainer.value);
    // Prepare data for the chart

    // Configure the chart options
    const options = {
      title: {
        text: '各月支出',
        left: 'center'
      },
      tooltip: {},
      xAxis: {
        type: 'category',
        // data: chartData.value.map((data) => data.name),
        data: []
      },
      yAxis: {
        type: 'value',
      },
      series: [
        {
          type: 'bar',
          // data: chartData.value.map((data) => data.value),
          data: []
        },
      ],
    };
    // Set the options and render the chart
    chart.setOption(options);
  }
}

defineExpose({
  chartContainer,
});

const route = useRoute()


const tableData = reactive<fund[]>([])

function getFundData(fid: any) {
  request({
    url: `/get-fund?uuid=${fid}`,
    method: 'get',
  }).then(res => {
    let rd = res.data.data;
    let fd: fund = {
      uuid: rd.uuid,
      id: rd.fundNumber,
      name: rd.fundName,
      sum: rd.sum,
      remain: rd.balance,
      startTimeStamp: rd.startTime / 1000,
      endTimeStamp: rd.endTime / 1000,
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

function onQuery() {
  // console.log(isAdmin.value)
  tableData.splice(0);
  // console.log(searchForm);
  let search = toRaw(SearchForm);
  request({
    url: '/get-fund-vague',
    method: 'get',
    params: search,
  }).then(res => {
    let rd = res.data.data;
    rd.forEach((item: any) => {
      console.log(item)
      let rd = item
      let fd: fund = {
        uuid: rd.uuid,
        id: rd.fundNumber,
        name: rd.fundName,
        sum: rd.sum,
        remain: rd.balance,
        startTimeStamp: rd.startTime / 1000,
        endTimeStamp: rd.endTime / 1000,
        start: moment.unix(rd.startTime / 1000).format('YYYY-MM-DD'),
        end: moment.unix(rd.endTime / 1000).format('YYYY-MM-DD'),
        remainDay: rd.remainDays,
        applies: []
      };
      tableData.push(fd)
    })
  })
}

const selectedPath = ref('');

const openFolderDialog = async () => {
  try {
    const folderHandle = await window.showDirectoryPicker();
    selectedPath.value = folderHandle.name;
  } catch (error) {
    console.error('Error opening folder dialog:', error);
  } finally {
    request({
      url: '/get-excel',
      method: 'Get',
      params: {
        path: selectedPath.value
      },
    }).then(res => {
    })

  }
};


onMounted(() => {
  onQuery();
  initBarChart();
  initRoseChart();
})

const SearchForm = reactive({
  FundNumber: '',
  FundName: '',
  researchGroupId: route.query.gid,
})

const now = new Date().getTime() / 1000

interface fund {
  uuid: number,
  id: string,
  name: string,
  sum: number,
  remain: number,
  startTimeStamp: number,
  endTimeStamp: number,
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


.BarChart {
  position: absolute;
  /* 水平垂直居中 */
  top: 30%;
  left: 20%;
  width: 60vw;
  height: 60vh;
  background-color: white;
  z-index: 5;
  border-radius: 10px;
  box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
  //border: 1px solid black;
}

.PieChart {
  left: 23%;
}
</style>