<template>
  <div>

    <!--    <div class="main">-->
    <!--      <div class="beauty"><span class="ziti">实时数据</span></div>-->
    <!--      <div class="ring quater-div">-->
    <!--        <canvas id="tutorial" width="150" height="150"></canvas>-->
    <!--        <span class="fraction">XXX <span class="small">分</span> </span>-->
    <!--        <span class="title weizhi">获取的信息</span>-->
    <!--      </div>-->
    <!--      <div class="ring quater-div">-->
    <!--        <canvas id="tutorial1" width="150" height="150"></canvas>-->
    <!--        <span class="fraction">XXX <span class="small">分</span> </span>-->
    <!--        <span class="title weizhi">获取的信息</span>-->
    <!--      </div>-->
    <!--      <div class="ring quater-div">-->
    <!--        <canvas id="tutorial2" width="150" height="150"></canvas>-->
    <!--        <span class="fraction">XXX <span class="small">分</span> </span>-->
    <!--        <span class="title weizhi">获取的信息</span>-->
    <!--      </div>-->
    <!--      <div class="ring quater-div">-->
    <!--        <canvas id="tutorial3" width="150" height="150"></canvas>-->
    <!--        <span class="fraction">XXX <span class="small">分</span> </span>-->
    <!--        <span class="title weizhi">获取的信息</span>-->
    <!--      </div>-->
    <!--    </div>-->

    <!--    <div>{{user.nickname}}</div>-->
    <div className="echarts" style="width: 100%;">
      <div style="width: 50%; display: inline-block; padding-left: 100px;">
        <div id="temperature" style="width: 400px; height: 300px; display: inline-block; margin: 25px 50px;"></div>
      </div>
      <div style="width: 50%; display: inline-block;">
        <div id="humidity" style="width: 400px; height: 300px; display: inline-block; margin: 25px 50px;"></div>
      </div>
      <div style="width: 50%; display: inline-block; padding-left: 100px;">
        <div id="kurtosis" style="width: 400px; height: 300px; display: inline-block; margin: 25px 50px;"></div>
      </div>
      <div style="width: 50%; display: inline-block">
        <div id="frequency" style="width: 400px; height: 300px; display: inline-block; margin: 25px 50px;"></div>
      </div>
    </div>
  </div>


</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "SmashInfo",
  data() {
    return {
      timeData: [],
      temData: [],
      squData: [],
      kurData: [],
      freData: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.flushTime()
  },
  methods: {
    //这是创建这个图形的方法，每创建一个都要运行
    // createCav(id) {
    //   let radius = 75;
    //   let thickness = 10;
    //   let innerRadius = radius - thickness;
    //   let x = 75;
    //   let y = 75;
    //   var canvas = document.getElementById(id);
    //   var ctx = canvas.getContext("2d");
    //
    //   ctx.beginPath();
    //   ctx.fillStyle = "#e87c7c"; //修改颜色
    //   ctx.arc(x, y, radius, Math.PI * 1.5, Math.PI);
    //   ctx.quadraticCurveTo(
    //       x - innerRadius - thickness / 2,
    //       y - thickness,
    //       x - innerRadius,
    //       y
    //   );
    //   ctx.arc(x, y, innerRadius, Math.PI, Math.PI * 1.5, true);
    //   ctx.quadraticCurveTo(
    //       y - thickness,
    //       x - innerRadius - thickness / 2,
    //       x,
    //       y - innerRadius - thickness
    //   );
    //   ctx.fill();
    // },

    flushTime() {
      this.request.get("/echarts/getDate7?type=脱胚粉碎").then(res => {
        if (res === null) {
          console.log("时间信息为空")
        } else {
          this.timeData = res.data;
          console.log(this.timeData)
        }
      })
    },
  },
  mounted() {  // 页面元素渲染好之后才会触发
    // this.createCav("tutorial");
    // this.createCav("tutorial1");
    // this.createCav("tutorial2");
    // this.createCav("tutorial3");
    var chartDom = document.getElementById('temperature');
    var myChart = echarts.init(chartDom);
    var option = {
      title: {
        text: '温度历史记录'
      },
      xAxis: {
        type: 'category',
        axisLabel: {interval: 0, rotate: 40},
        data: this.timeData
      },
      // dataZoom:[
      //   {
      //     show: true,
      //     start: 0,
      //     end: 50
      //   }
      // ],
      yAxis: {
        type: 'value',
        axisLabel: {formatter: '{value} ℃'},
      },
      series: [
        {
          data: this.temData,
          type: 'line',
          label: {
            show: true,
            position: 'top',
            textStyle: {
              color: 'black',
              fontSize: 16
            },
            formatter: function (params) {
              return (params.value) + "℃"
            }
          }
        },
        {
          data: this.temData,
          type: 'bar',
        }
      ]
    };

    var chartDom2 = document.getElementById('humidity');
    var myChart2 = echarts.init(chartDom2);
    var option2 = {
      title: {
        text: '湿度历史记录'
      },
      xAxis: {
        type: 'category',
        axisLabel: {interval: 0, rotate: 40},
        data: this.timeData,
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: this.squData,
          type: 'line',
          label: {
            show: true,
            position: 'top',
            textStyle: {
              color: 'black',
              fontSize: 16
            },
            formatter: function (params) {
              return (params.value)
            }
          }
        },
        {
          data: this.squData,
          type: 'bar'
        }
      ]
    };
    var chartDom3 = document.getElementById('kurtosis');
    var myChart3 = echarts.init(chartDom3);
    var option3 = {
      title: {
        text: '峭度值历史记录'
      },
      xAxis: {
        type: 'category',
        axisLabel: {interval: 0, rotate: 40},
        data: this.timeData,
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: this.kurData,
          type: 'line',
          label: {
            show: true,
            position: 'top',
            textStyle: {
              color: 'black',
              fontSize: 16
            },
            formatter: function (params) {
              return (params.value)
            }
          }
        },
        {
          data: this.kurData,
          type: 'bar'
        }
      ]
    };
    var chartDom4 = document.getElementById('frequency');
    var myChart4 = echarts.init(chartDom4);
    var option4 = {
      title: {
        text: '重心频率历史记录'
      },
      xAxis: {
        type: 'category',
        axisLabel: {interval: 0, rotate: 40},
        data: this.timeData,
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: this.freData,
          type: 'line',
          label: {
            show: true,
            position: 'top',
            textStyle: {
              color: 'black',
              fontSize: 16
            },
            formatter: function (params) {
              return (params.value)
            }
          }
        },
        {
          data: this.freData,
          type: 'bar'
        }
      ]
    };
    let flushData = () => {
      this.request.get("/echarts/getTemperature7?type=脱胚粉碎").then(res => {
        if (res === null) {
          console.log("温度信息为空");
        } else {
          console.log(res.data)
          // this.temData = res.data
          option.xAxis.data = this.timeData;
          option.series[0].data = res.data
          option.series[1].data = res.data
          myChart.setOption(option)
        }
      })
      this.request.get("/echarts/getSquare7?type=脱胚粉碎").then(res => {
        if (res === null) {
          console.log("湿度信息为空");
        } else {
          console.log(res.data)
          // this.squData = res.data;
          option2.xAxis.data = this.timeData;
          option2.series[0].data = res.data
          option2.series[1].data = res.data
          myChart2.setOption(option2)
        }
      })
      this.request.get("/echarts/getKurtosis7?type=脱胚粉碎").then(res => {
        if (res === null) {
          console.log("峭度值信息为空");
        } else {
          console.log(res.data)
          // this.kurData = res.data;
          option3.xAxis.data = this.timeData;
          option3.series[0].data = res.data
          option3.series[1].data = res.data
          myChart3.setOption(option3)
        }
      })
      this.request.get("/echarts/getFrequency7?type=脱胚粉碎").then(res => {
        if (res === null) {
          console.log("峭度值信息为空");
        } else {
          console.log(res.data)
          // this.freData = res.data;
          option4.xAxis.data = this.timeData;
          option4.series[0].data = res.data
          option4.series[1].data = res.data
          myChart4.setOption(option4)
        }
      })
    }
    flushData()
    this.timed = window.setInterval(() => {
      setTimeout(() => {
        this.flushTime()
        flushData();
      }, 0)
    }, 100000)

  }

}
</script>

<style scoped>
/** {*/
/*  margin: 0;*/
/*  padding: 0;*/
/*}*/

/*.beauty {*/
/*  margin-left: calc(100vw / 15);*/
/*  padding: 10px;*/
/*}*/

/*.ziti {*/
/*  font-size: 22px;*/
/*}*/
/*.main {*/
/*  width: 100%;*/
/*  height: 100%;*/
/*  position: absolute;*/
/*}*/
/*.quater-div {*/
/*  width: calc(100vw / 5);*/
/*  height: 200px;*/
/*  float: left;*/
/*}*/
/*.ring {*/
/*  display: flex;*/
/*  align-items: center;*/
/*  justify-content: center;*/
/*  flex-direction: column;*/
/*  position: relative;*/
/*}*/

/*.weizhi {*/
/*  position: absolute;*/
/*  top: 180px;*/
/*}*/

/*#tutorial {*/
/*  transform: rotate(-135deg);*/
/*  width: 150px;*/
/*  height: 150px;*/
/*}*/

/*#tutorial1 {*/
/*  transform: rotate(-135deg);*/
/*  width: 150px;*/
/*  height: 150px;*/
/*}*/

/*#tutorial2 {*/
/*  transform: rotate(-135deg);*/
/*  width: 150px;*/
/*  height: 150px;*/
/*}*/

/*#tutorial3 {*/
/*  transform: rotate(-135deg);*/
/*  width: 150px;*/
/*  height: 150px;*/
/*}*/

/*.fraction {*/
/*  position: absolute;*/
/*  font-size: 30px;*/
/*  font-weight: bold;*/
/*  color: red;*/
/*}*/

/*.small {*/
/*  font-size: 12px;*/
/*  font-weight: lighter;*/
/*}*/

/*.title {*/
/*  color: red;*/
/*  bottom: 0;*/
/*  position: absolute;*/
/*}*/
</style>