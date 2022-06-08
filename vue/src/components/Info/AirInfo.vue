<template>
  <div>
    <div id="main" style="width: 500px; height: 400px; display: inline-block; margin: 25px 25px;"></div>
    <div>hello</div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
export default {
  name: "FeedInfo",
  mounted() {

    function randomData() {
      now = new Date(+now + 60*1000); //下一个点获取时添加的时间设置
      value = value + Math.random() * 21 - 10;
      return {
        name: now.toString(),
        value: [now,Math.round(value)]
      }
    }
    var data = [];
    var now = new Date();
    var value = Math.random() * 1000;
    for (var i = 0; i < 60; i++) { //初始化有多少个点
      data.push(randomData());
    }
    var dom = document.getElementById("main");
    var myChart = echarts.init(dom);
    var option = {

      title: {
        text: '动态数据 + 时间坐标轴'
      },

      tooltip: {
        trigger: 'axis',
        formatter: function (params) {
          params = params[0];
          var date = new Date(params.name);
          return date.getHours() + ':'  + date.getMinutes() +" "+date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1]; //数据点现实的格式数据
        },
        axisPointer: {
          animation: false
        }
      },

      xAxis: {
        type: 'time',
        splitLine: {
          show: false
        }
      },

      yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
          show: false
        }
      },

      series: [{
        name: '模拟数据',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: data
      }]
    };

    setInterval(function () {
      data.shift(); //数据不积累，来一个点，则不现实最后一个点，注释则积累现实
      data.push(randomData());

      myChart.setOption({
        series: [{
          data: data
        }]
      });
    }, 100000);

    if (option && typeof option === "object") {
      myChart.setOption(option, true);
    }
  }
}
</script>


<style scoped>

</style>