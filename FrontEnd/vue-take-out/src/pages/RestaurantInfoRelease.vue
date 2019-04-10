<template>
    <div>
      <restaurantTopBar :activeIndex="'2'"></restaurantTopBar>
      <div style="display: flex">
        <div id="release-menu">
          <el-menu :default-active="activeIndex" style="float:left;min-height:700px;padding-top:50px;width:200px;" @select="handleSelect">
            <el-menu-item class="menu-style" index="1">
              <i class="el-icon-edit"></i>
              <span slot="title">单一餐品</span>
            </el-menu-item>
            <el-menu-item class="menu-style" index="2">
              <i class="el-icon-document"></i>
              <span slot="title">组合套餐</span>
            </el-menu-item>
            <el-menu-item class="menu-style" index="3">
              <i class="el-icon-edit"></i>
              <span slot="title">满减优惠</span>
            </el-menu-item>
          </el-menu>
        </div>
        <div class = "release-info">
          <label style="font-size: 36px">发布消息</label>
          <hr/>
          <br/>
          <div v-if="activeIndex === '1'" style="display: flex">
            <div style="padding-right: 100px">
              <label style="font-size: 20px">目前已有餐品</label>
              <div style="width: 200px;padding: 10px 0px">
                <hr/>
              </div>
              <el-table
                key = "first"
                :data="product_info"
                style="width: 100%">
                <el-table-column
                  prop="name"
                  label="餐品名称"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="quantity"
                  label="餐品库存"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="price"
                  label="餐品价格"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="type"
                  label="餐品类型"
                  width="100">
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
              </el-table>
            </div>
            <div>
              <label style="font-size: 20px">发布餐品消息</label>
              <div style="width: 200px;padding: 10px 0px">
                <hr/>
              </div>
              <el-form :model="release_info" :rules="release_info_rule" label-width="130px" ref="release_info_form">
                <el-form-item label="开始时间" prop="start_time">
                  <el-date-picker value-format="yyyy-MM-dd" v-model="release_info.start_time" type="date" placeholder="开始时间" :picker-options="pickerOptions" class="input-style">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间" prop="end_time">
                  <el-date-picker value-format="yyyy-MM-dd" v-model="release_info.end_time" type="date" placeholder="结束时间" :picker-options="pickerOptions" class="input-style">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="餐品名称" prop="product_name">
                  <el-input class="input-style" v-model="release_info.product_name"></el-input>
                </el-form-item>
                <el-form-item label="餐品类型" prop="type">
                  <el-input class="input-style" v-model="release_info.type"></el-input>
                </el-form-item>
                <el-form-item label="餐品单价" prop="price_front">
                  <div style="display: flex">
                    <el-form-item prop="price_front">
                      <el-input style="width: 200px;" v-model.number="release_info.price_front" placeholder="输入整数部分"></el-input>
                    </el-form-item>
                    <label>.</label>
                    <el-form-item prop="price_back">
                      <el-input style="width: 200px;" v-model.number="release_info.price_back"  placeholder="输入小数部分"></el-input>
                    </el-form-item>
                  </div>
                </el-form-item>
                <el-form-item label="餐品数量" prop="quantity">
                  <el-input class="input-style" v-model.number="release_info.quantity"></el-input>
                </el-form-item>
                <el-form-item label="餐品描述" prop="description">
                  <el-input class="input-style" v-model="release_info.description" type="textarea"></el-input>
                </el-form-item>
                <el-form-item style="padding-left: 400px">
                  <el-button type="primary" @click="onSubmitProductInfo('release_info_form')">确认发布</el-button>
                </el-form-item>
              </el-form>
            </div>
          </div>
          <div v-if="activeIndex === '2'" style="display: flex">
            <div style="padding-right: 100px">
              <label style="font-size: 20px">目前已有餐品</label>
              <div style="width: 200px;padding: 10px 0px">
                <hr/>
              </div>
              <el-table
                key = "second"
                :data="product_info"
                style="width: 100%">
                <el-table-column
                  prop="name"
                  label="餐品名称"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="quantity"
                  label="餐品库存"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="price"
                  label="餐品价格"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="type"
                  label="餐品类型"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="description"
                  label="餐品描述"
                  width="160">
                </el-table-column>
                <el-table-column
                  label="操作"
                  width="100">
                  <template slot-scope="scope">
                    <el-button type="success" size="mini" @click="addProduct(scope.$index)">加入套餐</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div style="width: 500px">
              <label style="font-size: 20px">套餐详情</label>
              <div style="width: 200px;padding: 10px 0px">
                <hr/>
              </div>
              <el-table
                key = "third"
                :data="order_info"
                style="width: 100%">
                <el-table-column
                  prop="name"
                  label="餐品名称"
                  width="125">
                </el-table-column>
                <el-table-column
                  prop="quantity"
                  label="数量"
                  width="125">
                </el-table-column>
                <el-table-column
                  prop="price"
                  label="总价"
                  width="125">
                </el-table-column>
                <el-table-column
                  label="操作"
                  width="125">
                  <template slot-scope="scope">
                    <el-button type="danger" size="mini" @click="deleteProduct(scope.$index)">移除</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <label>总计：{{primitive_price}}</label>
              <br/><br/>
              <el-form :model="set_meal" :rules="set_meal_rule" label-width="80px" ref="set_meal_form">
                <el-form-item label="开始时间" prop="start_time">
                  <el-date-picker value-format="yyyy-MM-dd" v-model="set_meal.start_time" type="date" placeholder="开始时间" :picker-options="pickerOptions" class="input-style">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间" prop="end_time">
                  <el-date-picker value-format="yyyy-MM-dd" v-model="set_meal.end_time" type="date" placeholder="结束时间" :picker-options="pickerOptions" class="input-style">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="餐品名称" prop="product_name">
                  <el-input class="input-style" v-model="set_meal.product_name"></el-input>
                </el-form-item>
                <el-form-item label="餐品类型" prop="type">
                  <el-input class="input-style" v-model="set_meal.type"></el-input>
                </el-form-item>
                <el-form-item label="餐品单价" prop="price_front">
                  <div style="display: flex">
                    <el-form-item prop="price_front">
                      <el-input style="width: 200px;" v-model.number="set_meal.price_front" placeholder="输入整数部分"></el-input>
                    </el-form-item>
                    <label>.</label>
                    <el-form-item prop="price_back">
                      <el-input style="width: 200px;" v-model.number="set_meal.price_back"  placeholder="输入小数部分"></el-input>
                    </el-form-item>
                  </div>
                </el-form-item>
                <el-form-item label="餐品数量" prop="quantity">
                  <el-input class="input-style" v-model.number="set_meal.quantity"></el-input>
                </el-form-item>
                <el-form-item label="餐品描述" prop="description">
                  <el-input class="input-style" v-model="set_meal.description" type="textarea"></el-input>
                </el-form-item>
                <el-form-item style="padding-left: 300px">
                  <el-button type="primary" @click="onSubmitSetMealInfo('set_meal_form')">确认发布</el-button>
                </el-form-item>
              </el-form>
            </div>
          </div>
          <div v-if="activeIndex === '3'" style="display: flex;">
            <div style="padding-right: 100px">
              <label style="font-size: 20px">目前已有优惠</label>
              <div style="width: 200px;padding: 10px 0px">
                <hr/>
              </div>
              <el-table
                :data="discount_exist"
                style="width: 100%">
                <el-table-column
                  prop="upper"
                  label="满"
                  width="150">
                </el-table-column>
                <el-table-column
                  prop="discount"
                  label="减"
                  width="150">
                </el-table-column>
              </el-table>
            </div>
            <div>
              <label style="font-size: 20px">发布优惠消息</label>
              <div style="width: 200px;padding: 10px 0px">
                <hr/>
              </div>
              <el-form :model="discount_info" :rules="discount_info_rule" label-width="130px" ref="discount_info_form">
                <el-form-item label="开始时间" prop="start_time">
                  <el-date-picker value-format="yyyy-MM-dd" v-model="discount_info.start_time" type="date" placeholder="开始时间" :picker-options="pickerOptions" class="input-style">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间" prop="end_time">
                  <el-date-picker value-format="yyyy-MM-dd" v-model="discount_info.end_time" type="date" placeholder="结束时间" :picker-options="pickerOptions" class="input-style">
                  </el-date-picker>
                </el-form-item>
                <div style="display: flex;">
                  <el-form-item label="满" prop="upper">
                    <el-input style="width: 135px;" v-model.number="discount_info.upper"></el-input>
                  </el-form-item>
                  <el-form-item label="减" prop="discount">
                    <el-input style="width: 135px;" v-model.number="discount_info.discount"></el-input>
                  </el-form-item>
                </div>
                <el-form-item style="padding-left: 400px">
                  <el-button type="primary" @click="onSubmitDiscountInfo('discount_info_form')">确认发布</el-button>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import restaurantTopBar from '../components/RestaurantTopBar'
export default {
  name: 'RestaurantInfoRelease',
  components: {restaurantTopBar},
  data () {
    return {
      activeIndex: '1',
      product_info: [],
      order_info: [],
      primitive_price: 0,
      discount_exist: [],
      release_info: {
        start_time: '',
        end_time: '',
        product_name: '',
        type: '',
        price_front: '',
        price_back: '',
        price: 0.0,
        quantity: '',
        description: ''
      },
      release_info_rule: {
        start_time: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        end_time: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        product_name: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        type: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        price_front: [
          {required: true, type: 'number', message: '必须为数字值'}
        ],
        price_back: [
          {required: true, max: 99, type: 'number', message: '必须为数字值，最大99'}
        ],
        quantity: [
          {required: true, min: 1, type: 'number', message: '必须为数字值'}
        ],
        description: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ]
      },
      set_meal: {
        start_time: '',
        end_time: '',
        product_name: '',
        type: '',
        price_front: '',
        price_back: '',
        price: 0.0,
        quantity: '',
        description: ''
      },
      set_meal_rule: {
        start_time: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        end_time: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        product_name: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        type: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        price_front: [
          {required: true, type: 'number', message: '必须为数字值'}
        ],
        price_back: [
          {required: true, max: 99, type: 'number', message: '必须为数字值，最大99'}
        ],
        quantity: [
          {required: true, min: 1, type: 'number', message: '必须为数字值'}
        ],
        description: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ]
      },
      discount_info: {
        start_time: '',
        end_time: '',
        upper: '',
        discount: ''
      },
      discount_info_rule: {
        start_time: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        end_time: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        upper: [
          {required: true, type: 'number', message: '必须为数字值'}
        ],
        discount: [
          {required: true, type: 'number', message: '必须为数字值'}
        ]
      },
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      }
    }
  },
  mounted: function () {
    this.getRestaurantDetail()
    this.getRestaurantDiscount()
  },
  methods: {
    deleteProduct (index) {
      this.order_info.splice(index, 1)
      this.calculatePrice()
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
    calculatePrice () {
      this.primitive_price = 0.0
      for (var i = 0; i < this.order_info.length; i++) {
        this.primitive_price += this.order_info[i].price
      }
    },
    getRestaurantDiscount () {
      var _this = this
      this.discount_exist = []
      this.$axios.get('/GetAllDiscount', {
        params: {
          rid: localStorage.username
        }
      }).then(function (response) {
        var discounts = response.data
        for (var j = 0; j < discounts.length; j++) {
          _this.discount_exist.push({upper: discounts[j].upper, discount: discounts[j].discount})
        }
      }).catch(function (error) {
        console.log(error)
      })
    },
    getRestaurantDetail () {
      var _this = this
      this.product_info = []
      this.$axios.get('/GetAllProduct', {
        params: {
          rid: localStorage.username
        }
      }).then(function (response) {
        var products = response.data
        console.log(products)
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
      }).catch(function (error) {
        console.log(error)
      })
    },
    onSubmitProductInfo (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.release_info.price = parseFloat((this.release_info.price_front + '.' + this.release_info.price_back))
          var _this = this
          this.$axios.post('/ReleaseProductInfo', {
            rid: parseInt(localStorage.username),
            startTime: this.release_info.start_time,
            endTime: this.release_info.end_time,
            productName: _this.release_info.product_name,
            type: _this.release_info.type,
            price: _this.release_info.price,
            quantity: _this.release_info.quantity,
            description: _this.release_info.description
          }).then(function (response) {
            _this.$message({
              message: '发布成功！',
              type: 'success'
            })
            _this.$refs[formName].resetFields()
            _this.getRestaurantDetail()
          }).catch(function (error) {
            console.log('error:' + error)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    onSubmitSetMealInfo (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.set_meal.price = parseFloat((this.set_meal.price_front + '.' + this.set_meal.price_back))
          var _this = this
          this.$axios.post('/ReleaseSetMealInfo', {
            orderBaseInfoList: _this.order_info,
            rid: localStorage.username,
            startTime: this.set_meal.start_time,
            endTime: this.set_meal.end_time,
            setMealName: _this.set_meal.product_name,
            type: _this.set_meal.type,
            price: _this.set_meal.price,
            quantity: _this.set_meal.quantity,
            description: _this.set_meal.description
          }).then(function (response) {
            _this.$message({
              message: '发布成功！',
              type: 'success'
            })
            _this.order_info = []
            _this.$refs[formName].resetFields()
            _this.getRestaurantDetail()
          }).catch(function (error) {
            console.log('error:' + error)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    onSubmitDiscountInfo (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var _this = this
          this.$axios.post('/ReleaseDiscountInfo', {
            rid: parseInt(localStorage.username),
            startTime: _this.discount_info.start_time,
            endTime: _this.discount_info.end_time,
            upper: _this.discount_info.upper,
            discount: _this.discount_info.discount
          }).then(function (response) {
            _this.$message({
              message: '发布成功！',
              type: 'success'
            })
            _this.$refs[formName].resetFields()
            _this.getRestaurantDiscount()
          }).catch(function (error) {
            console.log('error:' + error)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    handleSelect (key, keyPath) {
      this.activeIndex = key
    }
  }
}
</script>

<style scoped>
  .exist-info{
    width: 650px;
    padding-top: 50px;
    padding-left: 50px;
    padding-right: 150px;
    text-align: left;
    margin: 30px auto;
  }
  .release-info{
    width: 1500px;
    padding-top: 50px;
    padding-left: 150px;
    text-align: left;
  }
  .input-style{
    width: 400px;
  }
</style>
