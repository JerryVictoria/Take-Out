<template>
  <div>
    <customerTopBar :activeIndex = "'1'"></customerTopBar>
    <div style="display: flex">
      <div class="yummy-market">
        <label style="font-size: 36px;">餐厅详情</label>
        <label style="color: dodgerblue">&nbsp;&nbsp;{{name}}&nbsp;&nbsp;&nbsp;&nbsp;地址：{{address}}&nbsp;&nbsp;&nbsp;&nbsp;联系电话：{{phone}}</label>
        <hr/>
        <div class="discount-div">
          <label>优惠：</label>
          <li v-for="item in discount_info" :key="item.key"  disabled>
            满{{item.upper}}减{{item.discount}}&nbsp;&nbsp;
          </li>
        </div>
        <div class="product-div">
          <el-table
            :data="product_info"
            style="width: 100%">
            <el-table-column
              prop="name"
              label="餐品名称"
              width="160">
            </el-table-column>
            <el-table-column
              prop="quantity"
              label="餐品库存"
              width="160">
            </el-table-column>
            <el-table-column
              prop="price"
              label="餐品价格"
              width="160">
            </el-table-column>
            <el-table-column
              prop="type"
              label="餐品类型"
              width="160">
            </el-table-column>
            <el-table-column
              label="餐品描述"
              width="160">
              <template slot-scope="scope">
                <label>{{product_info[scope.$index].description}}</label>
                <li v-for="item in product_info[scope.$index].meals" :key="item.key">
              <span class="order-detail">
                {{item.name}}
                共{{item.quantity}}份
                （原价：￥{{item.price}}/份）
              </span>
                </li>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="200">
              <template slot-scope="scope">
                <el-button type="success" size="mini" @click="addProduct(scope.$index)">加入购物车</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="shopping-cart">
        <label style="font-size: 36px">您的购物车</label>
        <hr/>
        <el-table
          :data="order_info"
          style="width: 100%">
          <el-table-column
            prop="name"
            label="餐品名称"
            width="100">
          </el-table-column>
          <el-table-column
            prop="quantity"
            label="数量"
            width="100">
          </el-table-column>
          <el-table-column
            prop="price"
            label="总价"
            width="100">
          </el-table-column>
          <el-table-column
            label="操作"
            width="100">
            <template slot-scope="scope">
              <el-button type="danger" size="mini" @click="deleteProduct(scope.$index)">移除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <br/><br/>
        <hr/>
        <div style="margin-bottom: 50px">
          <label>总计：{{primitive_price.toFixed(2)}}</label>
          <br/><br/>
          <label>折后价：{{total_price.toFixed(2)}}</label>
          <br/><br/>
          <label>会员等级优惠：1级9.9折，2级9.7折（累计消费满1000），3级9.5折（累计消费满5000）</label>
          <br/><br/>
          <label>您现在的等级是：{{user_level}}</label>
          <br/><br/>
          <div>
            <div style="display: flex;padding-bottom: 10px">
              <label>选择送达地址:（同一地区预计40分钟送达，不同地区预计80分钟送达）&nbsp;&nbsp;&nbsp;&nbsp;</label>
              <el-button size="mini" @click="goToCustomerCenter">新增地址</el-button>
            </div>
            <el-radio-group v-model="order_address">
              <li v-for="item in customer_address" :key="item.key">
                <el-radio :label="item.address"></el-radio>
              </li>
            </el-radio-group>
          </div>
        </div>
        <div style="float: right">
          <el-button type="primary" @click="submitOrder">提交订单</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import customerTopBar from '../components/CustomerTopBar'
export default {
  name: 'MarketDetail',
  components: {customerTopBar},
  data () {
    return {
      product_info: [],
      discount_info: [],
      order_info: [],
      primitive_price: 0.0,
      total_price: 0.0,
      user_level: localStorage.level,
      address: '',
      customer_address: [],
      order_address: '',
      rid: 0,
      name: '',
      phone: ''
    }
  },
  mounted: function () {
    // console.log(this.$route.params.rid)
    this.getRestaurantDetail(this.$route.params.rid)
    this.getCustomerAddress()
    this.address = this.$route.params.address
    this.rid = this.$route.params.rid
    this.name = this.$route.params.name
    this.phone = this.$route.params.phone
  },
  methods: {
    getRestaurantDetail (rid) {
      var _this = this
      this.$axios.get('/GetRestaurantDetail', {
        params: {
          rid: parseInt(rid)
        }
      }).then(function (response) {
        var data = response.data
        var products = data.productInfoList
        var discounts = data.discountInfoList
        for (var i = 0; i < products.length; i++) {
          _this.product_info.push({pid: products[i].pid,
            name: products[i].productName,
            quantity: products[i].quantity,
            price: products[i].price,
            type: products[i].type,
            description: products[i].description,
            isSetMeal: products[i].setMeal,
            meals: products[i].orderBaseInfoList})
        }
        for (var j = 0; j < discounts.length; j++) {
          _this.discount_info.push({upper: discounts[j].upper, discount: discounts[j].discount})
        }
      }).catch(function (error) {
        console.log(error)
      })
    },
    getCustomerAddress () {
      var _this = this
      this.$axios.get('/GetAllAddress', {
        params: {
          username: localStorage.username
        }
      }).then(function (response) {
        var addr = response.data
        for (var i = 0; i < addr.length; i++) {
          _this.customer_address.push({address: addr[i]})
        }
      }).catch(function (error) {
        console.log(error)
      })
    },
    addProduct (index) {
      var existed = -1
      for (var i = 0; i < this.order_info.length; i++) {
        if (this.product_info[index].pid === this.order_info[i].pid) {
          existed = i
          break
        }
      }
      if (existed >= 0) {
        var newq = this.order_info[i].quantity + 1
        this.order_info[i].quantity = newq
        this.order_info[i].price = (this.product_info[index].price * newq)
      } else {
        this.order_info.push({ pid: this.product_info[index].pid, name: this.product_info[index].name, quantity: 1, price: this.product_info[index].price })
      }
      this.calculatePrice()
    },
    deleteProduct (index) {
      this.order_info.splice(index, 1)
      this.calculatePrice()
    },
    calculatePrice () {
      this.primitive_price = 0.0
      this.total_price = 0.0
      for (var i = 0; i < this.order_info.length; i++) {
        this.primitive_price += this.order_info[i].price
      }
      this.total_price = this.primitive_price
      for (var j = this.discount_info.length - 1; j >= 0; j--) {
        if (this.primitive_price >= this.discount_info[j].upper) {
          this.total_price -= this.discount_info[j].discount
          break
        }
      }
      switch (this.user_level) {
        case '1': this.total_price = this.total_price * 0.99; break
        case '2': this.total_price = this.total_price * 0.97; break
        case '3': this.total_price = this.total_price * 0.95; break
        default: console.log('bug:' + this.user_level)
      }
    },
    submitOrder () {
      var city = this.address.substring(0, this.address.indexOf('市') + 1)
      if (this.order_info.length === 0) {
        this.$message({
          message: '购物车为空！',
          type: 'error'
        })
      } else if (this.order_address.length === 0) {
        this.$message({
          message: '请选择送达地址！',
          type: 'error'
        })
      } else if (this.order_address.indexOf(city) < 0) {
        this.$message({
          message: '餐厅地址与您非同市不可达！',
          type: 'error'
        })
      } else {
        var _this = this
        this.$axios.post('/SubmitOrder', {
          orderBaseInfoList: _this.order_info,
          username: localStorage.username,
          rid: _this.rid,
          totalPrice: _this.total_price,
          address: _this.order_address
        }).then(function (response) {
          var data = response.data
          if (data.success === true) {
            _this.order_info = []
            _this.$message({
              message: data.message,
              type: 'success'
            })
            _this.$router.push({name: 'MarketOrderTrack'})
          } else {
            _this.$message({
              message: data.message,
              type: 'error'
            })
          }
        }).catch(function (error) {
          console.log('order error')
          console.log(error)
        })
      }
    },
    goToCustomerCenter () {
      this.$router.push({name: 'CustomerCenter'})
    }
  }
}
</script>

<style scoped>
  .yummy-market{
    width: 1000px;
    margin: 40px auto;
    text-align: left;
  }
  .discount-div{
    display: flex;
    color: orangered;
    padding: 10px 10px 30px 10px;
  }
  .shopping-cart{
    width: 400px;
    margin: 40px auto;
    text-align: left;
  }
</style>
