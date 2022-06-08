<template>
  <div>

    <div className="infoRing">
      <div className="ring">
        <canvas id="tutorial" width="150" height="150"></canvas>
        <span className="fraction">XXX <span className="small">分</span> </span>
        <span className="title">获取的信息</span>
      </div>

      <div className="ring">
        <canvas id="tutorial1" width="150" height="150"></canvas>
        <span className="fraction">XXX <span className="small">分</span> </span>
        <span className="title">获取的信息</span>
      </div>
    </div>


    <div id="main" style="width: 500px; height: 400px; display: inline-block; margin: 25px 25px;"></div>
    <div id="pie" style="width: 500px; height: 400px; display: inline-block; margin: 25px 25px;"></div>
  </div>


</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "CycleInfo",
  data() {
    return {
      timeData: [],
      temData: [],
      squData: [],
    }
  },
  created() {
    this.flushTime()
  },
  methods: {
    createCav(id) {
      let radius = 75;
      let thickness = 10;
      let innerRadius = radius - thickness;
      let x = 75;
      let y = 75;
      var canvas = document.getElementById(id);
      var ctx = canvas.getContext("2d");

      ctx.beginPath();
      ctx.fillStyle = "#e87c7c"; //修改颜色
      ctx.arc(x, y, radius, Math.PI * 1.5, Math.PI);
      ctx.quadraticCurveTo(
          x - innerRadius - thickness / 2,
          y - thickness,
          x - innerRadius,
          y
      );
      ctx.arc(x, y, innerRadius, Math.PI, Math.PI * 1.5, true);
      ctx.quadraticCurveTo(
          y - thickness,
          x - innerRadius - thickness / 2,
          x,
          y - innerRadius - thickness
      );
      ctx.fill();
    },

    flushTime() {
      this.request.get("/echarts/getDate7?type=循环水站").then(res => {
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
    this.createCav("tutorial");
    this.createCav("tutorial1");
    var chartDom = document.getElementById('main');
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

    var chartDom2 = document.getElementById('pie');
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
              return (params.value) + "C"
            }
          }
        },
        {
          data: this.squData,
          type: 'bar'
        }
      ]
    };
    let flushData = () => {
      this.request.get("/echarts/getTemperature7?type=循环水站").then(res => {
        if (res === null) {
          console.log("温度信息为空");
        } else {
          console.log(res.data)
          this.temData = res.bvvvvv
          option.xAxis.data = this.timeData;
          option.series[0].data = res.data
          option.series[1].data = res.data
          myChart.setOption(option)
        }
      })
      this.request.get("/echarts/getSquare7?type=循环水站").then(res => {
        if (res === null) {
          console.log("湿度信息为空");
        } else {
          console.log(res.data)
          this.squData = res.data;
          option2.xAxis.data = this.timeData;
          option2.series[0].data = res.data
          option2.series[1].data = res.data
          myChart2.setOption(option2)
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
.ring {
  width: 150px;
  height: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  position: relative;
}

#tutorial {
  transform: rotate(-135deg);
  width: 150px;
  height: 150px;
}

#tutorial1 {
  transform: rotate(-135deg);
  width: 150px;
  height: 150px;
}

.fraction {
  position: absolute;
  font-size: 30px;
  font-weight: bold;
  color: red;
}

.small {
  font-size: 12px;
  font-weight: lighter;
}

.title {
  color: red;
  bottom: 0;
  position: absolute;
}
</style>