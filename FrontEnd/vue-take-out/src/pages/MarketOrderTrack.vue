<template>
  <div>
    <customerTopBar :activeIndex = "'2'"></customerTopBar>
    <div class="bottom-part">
      <label style="font-size: 36px">我的订单</label>
      <hr/>
      <label  style="font-size: 16px">退订时，已经制作只退半数，未制作退九成。</label>
      <div class="track-order">
        <el-table
          :data="order_track_list"
          style="width: 100%">
          <el-table-column
            prop="total_price"
            label="总价"
            width="200">
          </el-table-column>
          <el-table-column
            prop="time"
            label="预计送达时间"
            width="200">
          </el-table-column>
          <el-table-column
          label="订单详情"
          width="400">
          <template slot-scope="scope">
            <li v-for="item in order_track_list[scope.$index].order_base_info_list" :key="item.key">
              <span class="order-detail">
                名称：{{item.name}}
                数量：{{item.quantity}}
                价格：￥{{item.price}}/份
              </span>
            </li>
          </template>
        </el-table-column>
          <el-table-column
            label="是否付款"
            width="150">
            <template slot-scope="scope">
              <div v-if="order_track_list[scope.$index].isPayed">已付款！</div>
              <div v-else>
                <el-button type="success" size="mini" @click="payOrder(scope.$index)">付款</el-button>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="是否收货"
            width="150">
            <template slot-scope="scope">
              <div v-if="order_track_list[scope.$index].isReceived">已收货！</div>
              <div v-else>
                <div v-if="order_track_list[scope.$index].isCancelled">已退货，不可收货！</div>
                <div v-else>
                  <el-button type="primary" size="mini" @click="receiveOrder(scope.$index)">收货</el-button>
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="是否退货"
            width="150">
            <template slot-scope="scope">
              <div v-if="order_track_list[scope.$index].isCancelled">已退货！</div>
              <div v-else>
                <div v-if="order_track_list[scope.$index].isPayed">
                  <div v-if="order_track_list[scope.$index].isReceived">已收货，不可退！</div>
                  <div v-else>
                    <el-button type="warning" size="mini" @click="cancelOrder(scope.$index)">退货</el-button>
                  </div>
                </div>
                <div v-else>
                  还未付款！
                </div>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import customerTopBar from '../components/CustomerTopBar'
export default {
  name: 'MarketOrderTrack',
  components: {customerTopBar},
  data () {
    return {
      order_track_list: []
    }
  },
  mounted: function () {
    this.getTrackOrder()
  },
  methods: {
    getTrackOrder () {
      this.order_track_list = []
      var _this = this
      this.$axios.get('/TrackOrder', {
        params: {
          username: localStorage.username
        }
      }).then(function (response) {
        var data = response.data
        console.log(data)
        for (var i = 0; i < data.length; i++) {
          _this.order_track_list.push({oid: data[i].oid,
            total_price: data[i].totalPrice,
            isPayed: data[i].payed,
            isCancelled: data[i].cancelled,
            order_base_info_list: data[i].orderBaseInfoList,
            isReceived: data[i].received,
            time: (data[i].time + '').substring(0, 10) + ' ' + (data[i].time + '').substring(11)})
        }
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    payOrder (index) {
      var oid = this.order_track_list[index].oid
      var _this = this
      this.$axios.get('/Pay', {
        params: {
          oid: oid,
          username: localStorage.username
        }
      }).then(function (response) {
        if (response.data) {
          _this.$message({
            message: '支付成功！',
            type: 'success'
          })
          _this.getTrackOrder()
        } else {
          _this.$message({
            message: '余额不足，支付失败！',
            type: 'error'
          })
        }
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    receiveOrder (index) {
      var oid = this.order_track_list[index].oid
      var _this = this
      this.$axios.get('/ReceiveOrder', {
        params: {
          oid: oid
        }
      }).then(function (response) {
        _this.$message({
          message: '收货成功！',
          type: 'success'
        })
        _this.getTrackOrder()
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    cancelOrder (index) {
      var oid = this.order_track_list[index].oid
      var _this = this
      this.$axios.get('/CancelOrder', {
        params: {
          oid: oid
        }
      }).then(function (response) {
        _this.$message({
          message: '退订成功，等待商家确认后，即可的到返还现金！',
          type: 'success'
        })
        _this.getTrackOrder()
      }).catch(function (error) {
        console.log('error:' + error)
      })
    }
  }
}
</script>

<style scoped>
  .track-order{
    width: 1250px;
    margin: 40px auto;
    text-align: left;
  }
  .order-detail{
    font-size: 16px;
    color: green;
  }
  .bottom-part{
    width: 1250px;
    height: 700px;
    margin: 100px auto;
    text-align: left;
  }
</style>
