<template>
  <div>
    <restaurantTopBar :activeIndex="'4'"></restaurantTopBar>
    <div class="bottom-part">
      <label style="font-size: 36px">餐厅统计信息</label>
      <hr/>
      <br/>
      <div style="padding-bottom: 30px">
        <el-form :inline="true">
          <el-form-item label="开始时间" style="padding:5px 20px 5px 20px;">
            <el-date-picker value-format="yyyy-MM-dd" v-model="searchOption.startTime" type="date" placeholder="请输入查询内容" class="input-style">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间" style="padding:5px 20px 5px 20px;">
            <el-date-picker value-format="yyyy-MM-dd" v-model="searchOption.endTime" type="date" placeholder="请输入查询内容" class="input-style">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="最低金额" style="padding:5px 20px 5px 20px;">
            <el-input placeholder="请输入查询内容" clearable v-model.number="searchOption.minMoney"></el-input>
          </el-form-item>
          <el-form-item label="最高金额" style="padding:5px 20px 5px 20px;">
            <el-input placeholder="请输入查询内容" clearable v-model.number="searchOption.maxMoney"></el-input>
          </el-form-item>
          <el-form-item label="用户名称" style="padding:5px 20px 5px 20px;">
            <el-input placeholder="请输入查询内容" clearable v-model="searchOption.username"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="table-div">
        <el-table
          :data="order_list"
          style="width: 100%">
          <el-table-column
            prop="name"
            label="用户名称"
            width="150">
          </el-table-column>
          <el-table-column
            prop="level"
            label="用户等级"
            :filters="[{text: '1', value: '1'}, {text: '2', value: '2'}, {text: '3', value: '3'}]"
            :filter-method="filterHandler"
            width="100">
          </el-table-column>
          <el-table-column
            prop="time"
            label="时间"
            width="200">
          </el-table-column>
          <el-table-column
            label="订单详情"
            width="400">
            <template slot-scope="scope">
              <li v-for="item in order_list[scope.$index].order_base_info_list" :key="item.key">
              <span class="order-detail">
                名称：{{item.name}}
                数量：{{item.quantity}}
                价格：{{item.price}}
              </span>
              </li>
            </template>
          </el-table-column>
          <el-table-column
            prop="total_price"
            label="总价"
            width="100">
          </el-table-column>
          <el-table-column
            label="是否退货"
            width="100">
            <template slot-scope="scope">
              <div v-if="order_list[scope.$index].isCancelled">
                退货
              </div>
              <div v-else>
                未退货
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="profit"
            label="利润"
            width="100">
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import restaurantTopBar from '../components/RestaurantTopBar'
export default {
  name: 'RestaurantInfoCheck',
  components: {restaurantTopBar},
  data () {
    return {
      order_list: [],
      save_order_list: [],
      searchOption: {
        username: '',
        startTime: '',
        endTime: '',
        minMoney: '',
        maxMoney: ''
      }
    }
  },
  mounted: function () {
    this.checkDetail()
  },
  watch: {
    searchOption: {
      deep: true,
      handler: function (val, oldVal) {
        this.order_list = this.searchNewList(val.username, val.startTime, val.endTime, val.minMoney, val.maxMoney)
      }
    }
  },
  methods: {
    searchNewList (username, startTime, endTime, minMoney, maxMoney) {
      let checkedOrder = []
      for (var i = 0; i < this.save_order_list.length; i++) {
        if (this.save_order_list[i].name.indexOf(username) >= 0 &&
          (startTime === null || startTime === '' || startTime <= this.save_order_list[i].time) &&
          (endTime === null || endTime === '' || endTime >= this.save_order_list[i].time) &&
          (minMoney === '' || parseInt(minMoney) <= this.save_order_list[i].total_price) &&
          (maxMoney === '' || parseInt(maxMoney) >= this.save_order_list[i].total_price)) {
          checkedOrder.push(this.save_order_list[i])
        }
      }
      return checkedOrder
    },
    filterHandler (value, row, column) {
      const property = column['property']
      return row[property] === value
    },
    checkDetail () {
      this.order_list = []
      var _this = this
      this.$axios.get('/CheckOrder', {
        params: {
          rid: localStorage.username
        }
      }).then(function (response) {
        var data = response.data
        console.log(data)
        for (var i = 0; i < data.length; i++) {
          _this.order_list.push({name: data[i].username,
            level: data[i].level,
            total_price: data[i].totalPrice,
            isCancelled: data[i].cancelled,
            profit: data[i].restaurantProfit,
            order_base_info_list: data[i].orderBaseInfoList,
            time: (data[i].time + '').substring(0, 10) + ' ' + (data[i].time + '').substring(11)})
          _this.save_order_list.push(_this.order_list[i])
        }
      }).catch(function (error) {
        console.log('error:' + error)
      })
    }
  }
}
</script>

<style scoped>
  .bottom-part{
    width: 1150px;
    height: 700px;
    margin: 100px auto;
    text-align: left;
  }
</style>
