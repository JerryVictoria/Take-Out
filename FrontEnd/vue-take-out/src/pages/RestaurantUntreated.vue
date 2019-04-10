<template>
  <div>
    <restaurantTopBar :activeIndex="'5'"></restaurantTopBar>
    <div class="bottom-part">
      <label style="font-size: 36px">未处理订单</label>
      <hr/>
      <label style="font-size: 16px">接受退订要求时，请选择目前商品是否制作，以退还不同价格。</label>
      <label  style="font-size: 16px">已经制作只退半数，未制作退九成</label>
      <br/><br/>
      <br/>
      <el-table
        :data="order_info"
        style="width: 100%">
        <el-table-column
          prop="total_price"
          label="总价"
          width="110">
        </el-table-column>
        <el-table-column
          prop="time"
          label="预计送达时间"
          width="150">
        </el-table-column>
        <el-table-column
          prop="username"
          label="客户用户名"
          width="110">
        </el-table-column>
        <el-table-column
          prop="phone"
          label="客户电话"
          width="110">
        </el-table-column>
        <el-table-column
          prop="address"
          label="客户地址"
          width="120">
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
          label="客户是否退货"
          width="120">
          <template slot-scope="scope">
            <div v-if="order_info[scope.$index].isCancelled">
              请求退货
            </div>
            <div v-else>
              未退货
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="180">
          <template slot-scope="scope">
            <div v-if="order_info[scope.$index].isCancelled">
              <div style="padding-bottom: 5px">
                <el-button type="primary" size="mini" @click="takeCancelledOrder(scope.$index,true)">商品已制作，退单</el-button>
              </div>
              <div>
                <el-button type="warning" size="mini" @click="takeCancelledOrder(scope.$index,false)">商品未制作，退单</el-button>
              </div>
            </div>
            <div v-else>
              <el-button type="success" size="mini" @click="takeOrder(scope.$index)">接单</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import restaurantTopBar from '../components/RestaurantTopBar'
export default {
  name: 'RestaurantUntreated',
  components: {restaurantTopBar},
  data () {
    return {
      order_info: []
    }
  },
  mounted: function () {
    this.getUntreatedOrder()
  },
  methods: {
    getUntreatedOrder () {
      this.order_info = []
      var _this = this
      this.$axios.get('/GetUntreatedOrder', {
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
            address: data[i].address})
        }
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    takeOrder (index) {
      var oid = this.order_info[index].oid
      var _this = this
      this.$axios.get('/TakeOrder', {
        params: {
          oid: oid
        }
      }).then(function (response) {
        _this.$message({
          message: '已接单！',
          type: 'success'
        })
        _this.getUntreatedOrder()
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    takeCancelledOrder (index, choice) {
      var oid = this.order_info[index].oid
      var _this = this
      this.$axios.get('/TakeCancelledOrder', {
        params: {
          oid: oid,
          isMade: choice
        }
      }).then(function (response) {
        _this.$message({
          message: '确认退订！',
          type: 'success'
        })
        _this.getUntreatedOrder()
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
