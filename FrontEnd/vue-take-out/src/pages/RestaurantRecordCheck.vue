<template>
  <div>
    <restaurantTopBar :activeIndex="'3'"></restaurantTopBar>
    <div class="bottom-part">
      <label style="font-size: 36px">送餐记录</label>
      <hr/>
      <br/>
      <el-table
        :data="order_info"
        style="width: 100%">
        <el-table-column
          prop="time"
          label="时间"
          width="150">
        </el-table-column>
        <el-table-column
          prop="username"
          label="客户用户名"
          width="150">
        </el-table-column>
        <el-table-column
          prop="phone"
          label="客户电话"
          width="150">
        </el-table-column>
        <el-table-column
          label="订单详情"
          width="250">
          <template slot-scope="scope">
            <li v-for="item in order_info[scope.$index].order_base_info_list" :key="item.key">
              <span class="order-detail">
                名称：{{item.name}}
                数量：{{item.quantity}}
                价格：{{item.price}}
              </span>
            </li>
          </template>
        </el-table-column>
        <el-table-column
          label="是否退货"
          width="150">
          <template slot-scope="scope">
            <div v-if="order_info[scope.$index].isCancelled">
              退货
            </div>
            <div v-else>
              未退货
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="total_price"
          label="总价"
          width="150">
        </el-table-column>
        <el-table-column
          prop="profit"
          label="收益"
          width="150">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import restaurantTopBar from '../components/RestaurantTopBar'
export default {
  name: 'RestaurantRecordCheck',
  components: {restaurantTopBar},
  data () {
    return {
      order_info: []
    }
  },
  mounted: function () {
    this.getOrderRecord()
  },
  methods: {
    getOrderRecord () {
      this.order_info = []
      var _this = this
      this.$axios.get('/GetRecord', {
        params: {
          rid: parseInt(localStorage.username)
        }
      }).then(function (response) {
        var data = response.data
        for (var i = 0; i < data.length; i++) {
          _this.order_info.push({oid: data[i].oid,
            total_price: data[i].totalPrice,
            isCancelled: data[i].cancelled,
            order_base_info_list: data[i].orderBaseInfoList,
            time: (data[i].time + '').substring(0, 10) + ' ' + (data[i].time + '').substring(11),
            username: data[i].username,
            phone: data[i].phone,
            profit: data[i].restaurantProfit})
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
