<template>
  <div style="padding: 20px">
    <dv-border-box-2>
      <dv-water-level-pond :config="config" style="width:150px;height:200px; padding-top: 20px" />
      <div id="equipment" style="width: 1200px; height: 400px;"></div>
    </dv-border-box-2>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import * as dataV from "@jiaminghi/data-view";
const option = {
  tooltip: {
    trigger: 'axis',
    position: function (pt) {
      return [pt[0], '10%'];
    }
  },
  title: {
    left: 'center',
    text:''
  },
  toolbox: {
    feature: {
      dataZoom: {
        yAxisIndex: 'none'
      },
      restore: {},
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    // data: date
    data:[]
  },
  yAxis: {
    type: 'value',
    boundaryGap: [0, '100%']
  },
  dataZoom: [
    {
      type: 'inside',
      start: 0,
      end: 100
    },
    {
      start: 0,
      end: 10
    }
  ],
  series: [
    {
      name: 'Fake Data',
      type: 'line',
      symbol: 'none',
      sampling: 'lttb',
      itemStyle: {
        color: 'rgb(255, 70, 131)'
      },
      areaStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          {
            offset: 0,
            color: 'rgb(255, 158, 68)'
          },
          {
            offset: 1,
            color: 'rgb(255, 70, 131)'
          }
        ])
      },
      // data: data
      data:[]
    }
  ]
};
export default {
  name: "EquipmentInfo",
  data() {
    return {
      config:{
        data:[66],
        shape: 'round',
        colors:['#fc5531','#1acac8']
      },
      s_Data:[],
      s_Time:[],
      title:'',
    }
  },
    async mounted() {
    // this.request.get("/echarts/getTestData").then(res => {
    //   if(res === null){
    //     console.log("empty")
    //   }else {
    //     this.s_Data = res.data
    //     console.log("s_Data:"+this.s_Data)
    //   }
    // })
    // this.request.get("/echarts/getTestTime").then(res => {
    //   if(res === null){
    //     console.log("empty")
    //   }else {
    //     this.s_Time = res.data
    //     console.log("s_Time"+this.s_Time)
    //   }
    // })
      const s_Data = await this.request.get("/echarts/getTestData")
      const s_Time = await this.request.get("/echarts/getTestTime")
      this.s_Data = s_Data || this.s_Data
      this.s_Time = s_Time  || this.s_Time
      var chartDom = document.getElementById('equipment');
      console.log('chartDom' , chartDom)
      var myChart = echarts.init(chartDom);
      option.title.text = '近七天数据('+ this.title + ")"
      option.xAxis.data = s_Time.data
      option.series[0].data = s_Data.data
      console.log('option' , option)
      option && myChart.setOption(option);
  }
}
</script>

<style scoped>
</style>