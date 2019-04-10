<template>
  <div>
    <el-menu :default-active="activeIndex" background-color="#409EFF" text-color="#FFF" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="0"><img src="../assets/logo.png" style="width: auto;height: auto;max-width: 150px;"></el-menu-item>
      <el-menu-item class="top-bar-style" index="1">审核餐厅修改信息</el-menu-item>
      <el-menu-item class="top-bar-style" index="2">查看平台运行情况</el-menu-item>
      <el-menu-item class="top-bar-style" index="3">登出</el-menu-item>
    </el-menu>
    <div v-if="activeIndex === '1'" class="bottom-part">
      <el-table
        :data="tableData"
        style="width: 100%">
        <el-table-column
          prop="rid"
          label="餐厅ID"
          width="150">
        </el-table-column>
        <el-table-column
          prop="name"
          label="修改名称"
          width="150">
        </el-table-column>
        <el-table-column
          prop="phone"
          label="修改电话"
          width="150">
        </el-table-column>
        <el-table-column
          prop="address"
          label="修改地址"
          width="150">
        </el-table-column>
        <el-table-column
          prop="type"
          label="修改类型"
          width="150">
        </el-table-column>
        <el-table-column
          prop="id"
          label="操作"
          width="250">
          <template slot-scope="scope">
            <el-button type="success" size="mini" plain @click="agree(scope.$index)">通过</el-button>
            <el-button type="danger" size="mini" plain @click="disagree(scope.$index)">不通过</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-show="activeIndex === '2'" class="bottom-part">
      <div style="display: flex">
        <div id="customerChart" class="chart-style"></div>
        <div id="restaurantChart" class="chart-style"></div>
      </div>
      <div>
        <br/><br/><br/><br/>
        <label>选择您想要查看的年份：</label>
        <el-date-picker
          value-format="yyyy"
          v-model="year"
          type="year"
          placeholder="选择年">
        </el-date-picker>&nbsp;&nbsp;&nbsp;&nbsp;
        <el-button type="success" @click="getProfitData">确定</el-button>
        <br/><br/><br/><br/>
        <div id="profitChart" style="width: 1200px; height: 500px"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ManagerCenter',
  data () {
    return {
      activeIndex: '1',
      tableData: [],
      customerData: [5, 10, 15],
      restaurantData: [2, 4, 6],
      profitData: [118.9, 97.92, 83, 77.48, 65.78, 68.98, 97.65, 108.77, 145.9, 110, 191.1, 91.92],
      year: '2019'
    }
  },
  mounted: function () {
    this.getApprovalData()
  },
  methods: {
    // charts
    drawLine () {
      console.log('draw line')
      // 基于准备好的dom，初始化echarts实例
      let customerChart = this.$echarts.init(document.getElementById('customerChart'))
      // 绘制图表
      customerChart.setOption({
        title: { text: '会员统计（分等级）' },
        tooltip: {},
        xAxis: {
          data: ['一级会员', '二级会员', '三级会员']
        },
        yAxis: {},
        series: [{
          name: '总人数',
          type: 'bar',
          data: this.customerData
        }],
        color: ['green']
      })
      let restaurantChart = this.$echarts.init(document.getElementById('restaurantChart'))
      // 绘制图表
      restaurantChart.setOption({
        title: { text: '餐厅数量统计（分类别）' },
        tooltip: {},
        xAxis: {
          data: ['中餐', '西餐', '休闲餐']
        },
        yAxis: {},
        series: [{
          name: '总餐厅数',
          type: 'bar',
          data: this.restaurantData
        }],
        color: ['#6e7074']
      })
      let profitChart = this.$echarts.init(document.getElementById('profitChart'))
      // 绘制图表
      profitChart.setOption({
        title: { text: '每月平台利润统计' },
        tooltip: {},
        xAxis: {
          data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        },
        yAxis: {},
        series: [
          {name: '月利润',
            type: 'bar',
            data: this.profitData,
            color: '#ca8622'},
          {name: '月利润',
            type: 'line',
            data: this.profitData,
            color: 'grey'}]
      })
      console.log('draw end')
    },
    // top-bar choice
    handleSelect (key, keyPath) {
      if (key === '1' || key === '2') {
        this.activeIndex = key
      }
      if (key === '2') {
        this.getCustomerData()
        this.getRestaurantData()
        this.getProfitData()
      }
      if (key === '3') {
        var storage = window.localStorage
        storage.clear()
        this.$router.push({name: 'Login'})
      }
    },
    // approval table
    getApprovalData () {
      this.tableData = []
      var _this = this
      this.$axios.get('/GetApprovals', {
        params: {}
      }).then(function (response) {
        var data = response.data
        for (var i = 0; i < data.length; i++) {
          _this.tableData.push({rid: data[i].rid, id: data[i].id, name: data[i].name, phone: data[i].phone, address: data[i].address, type: data[i].type})
        }
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    agree (id) {
      console.log(id)
      var _this = this
      this.$axios.get('/AgreeApproval', {
        params: {
          id: _this.tableData[id].id
        }
      }).then(function (response) {
        _this.$message({
          message: '通过！',
          type: 'success'
        })
        _this.getApprovalData()
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    disagree (id) {
      console.log(id)
      var _this = this
      this.$axios.get('/DisagreeApproval', {
        params: {
          id: _this.tableData[id].id
        }
      }).then(function (response) {
        _this.$message({
          message: '驳回！',
          type: 'success'
        })
        _this.getApprovalData()
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    // 得到会员数据
    getCustomerData () {
      this.customerData = []
      var _this = this
      this.$axios.get('/GetCustomerData', {
        params: {}
      }).then(function (response) {
        var data = response.data
        console.log(data)
        _this.customerData = data
        _this.drawLine()
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    // 得到餐厅数据
    getRestaurantData () {
      this.restaurantData = []
      var _this = this
      this.$axios.get('/GetRestaurantData', {
        params: {}
      }).then(function (response) {
        var data = response.data
        console.log(data)
        _this.restaurantData = data
        _this.drawLine()
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    // 得到利润数据
    getProfitData () {
      this.profitData = []
      var _this = this
      this.$axios.get('/GetProfitData', {
        params: {
          year: _this.year
        }
      }).then(function (response) {
        var data = response.data
        console.log(data)
        _this.profitData = data
        _this.drawLine()
      }).catch(function (error) {
        console.log('error:' + error)
      })
    }
  }
}
</script>

<style scoped>
  .top-bar-style{
    font-size: 22px;
  }
  .bottom-part{
    width: 1000px;
    height: 700px;
    margin: 100px auto;
    text-align: left;
  }
  .chart-style{
    width: 600px;
    height: 400px;
  }
</style>
