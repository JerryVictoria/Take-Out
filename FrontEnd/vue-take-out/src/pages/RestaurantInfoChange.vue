<template>
  <div>
    <restaurantTopBar :activeIndex="'1'"></restaurantTopBar>
    <div class="bottom-part">
      <label style="font-size: 36px">修改餐厅信息</label>
      <hr/>
      <label>注：管理员审核后，信息才会变动！</label>
      <br/>
      <el-form :model="restaurant_info" :rules="restaurant_info_rule" label-width="80px" style="padding-top:30px" ref="restaurant_form">
        <el-form-item label="用户名">
          <el-input class="input-style" v-model="restaurant_info.rid" disabled></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input class="input-style" v-model="restaurant_info.email" disabled></el-input>
        </el-form-item>
        <el-form-item label="收益">
          <el-input class="input-style" v-model="restaurant_info.money" disabled></el-input>
        </el-form-item>
        <el-form-item label="餐厅名称" prop="name">
          <el-input class="input-style" v-model="restaurant_info.name"></el-input>
          <div style="padding-left: 16px;display: inline"></div>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input class="input-style" v-model="restaurant_info.phone"></el-input>
          <div style="padding-left: 16px;display: inline"></div>
        </el-form-item>
        <el-form-item label="餐厅类型">
          <el-select v-model="restaurant_info.type" placeholder="请选择餐厅类型" class="input-style">
            <el-option
              v-for="item in types"
              :key="item.type"
              :label="item.label"
              :value="item.type">
            </el-option>
          </el-select>
          <div style="padding-left: 16px;display: inline"></div>
          <el-button type="primary" @click="onSubmit('restaurant_form')">确认修改</el-button>
        </el-form-item>
        <el-form-item label="餐厅地址">
          <addressChoice ref="address_choice_child" @refreshInfo="getRestaurantInfo"></addressChoice>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import restaurantTopBar from '../components/RestaurantTopBar'
import addressChoice from '../components/AddressChoice'
export default {
  name: 'RestaurantInfoChange',
  components: {restaurantTopBar, addressChoice},
  data () {
    return {
      types: [
        {label: '中餐', type: '中餐'},
        {label: '西餐', type: '西餐'},
        {label: '休闲餐', type: '休闲餐'}
      ],
      restaurant_info: {
        rid: localStorage.username,
        name: '',
        phone: '',
        email: '',
        address: '',
        type: '',
        money: 0.0,
        city: '',
        district: '',
        address_detail: ''
      },
      restaurant_info_rule: {
        phone: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  mounted: function () {
    this.getRestaurantInfo()
  },
  methods: {
    getRestaurantInfo () {
      var _this = this
      this.$axios.get('/GetRestaurantInfo', {
        params: {
          rid: parseInt(localStorage.username)
        }
      }).then(function (response) {
        var data = response.data
        console.log(data)
        _this.restaurant_info.name = data.name
        _this.restaurant_info.phone = data.phone
        _this.restaurant_info.email = data.email
        _this.restaurant_info.address = data.address
        _this.restaurant_info.type = data.type
        _this.restaurant_info.money = data.money
        var addr = data.address
        if (addr.indexOf('市') >= 0) {
          _this.restaurant_info.city = addr.substring(0, addr.indexOf('市') + 1)
          _this.restaurant_info.district = addr.substring(addr.indexOf('市') + 1, addr.indexOf('区') + 1)
          _this.restaurant_info.address_detail = addr.substring(addr.indexOf('区') + 1)
        }
        _this.$refs.address_choice_child.setDefaultAddress(_this.restaurant_info.city, _this.restaurant_info.district, _this.restaurant_info.address_detail)
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    onSubmit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var _this = this
          this.$axios.get('/ChangeRestaurantBaseInfo', {
            params: {
              rid: localStorage.username,
              name: _this.restaurant_info.name,
              phone: _this.restaurant_info.phone,
              type: _this.restaurant_info.type
            }
          }).then(function (response) {
            _this.$message({
              message: '修改成功，等待审核！',
              type: 'success'
            })
            _this.getRestaurantInfo()
          }).catch(function (error) {
            console.log('error:' + error)
          })
        } else {
          console.log('error')
        }
      })
    }
  }
}
</script>

<style scoped>
  .bottom-part{
    width: 800px;
    height: 700px;
    margin: 100px auto;
    text-align: left;
  }
  .input-style{
    width: 470px;
  }
</style>
