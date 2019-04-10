<template>
  <div>
    <customerTopBar :activeIndex = "'3'"></customerTopBar>
    <div style="display: flex">
      <div id="menu">
        <el-menu :default-active="activeIndex" style="float:left;height:700px;padding-top:50px" @select="handleSelect">
          <el-menu-item class="menu-style" index="1">
            <i class="el-icon-delete"></i>
            <span slot="title">注销会员</span>
          </el-menu-item>
          <el-menu-item class="menu-style" index="2">
            <i class="el-icon-edit"></i>
            <span slot="title">修改会员信息</span>
          </el-menu-item>
          <el-menu-item class="menu-style" index="3">
            <i class="el-icon-document"></i>
            <span slot="title">查看本人统计信息</span>
          </el-menu-item>
        </el-menu>
      </div>
      <div id="deleteUser" v-if="activeIndex==='1'" class = "right-part">
        <label style="font-size: 36px">注销</label>
        <hr/>
        <br/>
        <label>注销账号后，不可恢复账号，请慎重选择！</label>
        <div style="display: flex;padding-top: 40px;">
          <label>用户名：{{username}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
          <label>角色：{{role}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
          <div style="padding-left: 700px;float: right">
            <el-button type="primary" @click="writtenOff">注销</el-button>
          </div>
        </div>
      </div>
      <div id="changeInformation" v-if="activeIndex==='2'" class="right-part">
        <label style="font-size: 36px">修改会员信息</label>
        <hr/>
        <el-form :model="customer_info" :rules="customer_info_rule" label-width="80px" style="padding-top:30px" ref="customer_info">
          <el-form-item label="用户名">
            <el-input class="input-style" v-model="customer_info.username" disabled></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input class="input-style" v-model="customer_info.email" disabled></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input class="input-style" v-model="customer_info.name"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input class="input-style" v-model="customer_info.phone"></el-input>
            <div style="padding-left: 20px;display: inline"></div>
            <el-button type="primary" @click="onSubmit('customer_info')">提交修改</el-button>
          </el-form-item>
          <el-form-item label="等级">
            <el-input class="input-style" v-model="customer_info.level" disabled></el-input>
          </el-form-item>
          <el-form-item label="账户余额">
            <el-input class="input-style" v-model="customer_info.money" disabled></el-input>
          </el-form-item>
          <el-form-item label="已有地址">
            <li class="input-style" v-for="item in address" :key="item.key"  disabled>
              地址{{item.key}}：{{item.addr}}
            </li>
          </el-form-item>
          <el-form-item label="新增地址">
            <address-choice @refreshInfo="getCustomerInfo"></address-choice>
          </el-form-item>
        </el-form>
      </div>
      <div id="checkInformation" v-if="activeIndex==='3'" class="right-part">
        <label style="font-size: 36px">个人统计信息</label>
        <hr/>
        <br/>
        <div style="padding-bottom: 30px">
          <el-form :inline="true">
            <el-form-item label="开始时间" style="padding:5px 20px 5px 20px;">
              <el-date-picker value-format="yyyy-MM-dd" v-model="searchOption.startTime" type="date" placeholder="请输入查询内容" class="date-style">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="结束时间" style="padding:5px 20px 5px 20px;">
              <el-date-picker value-format="yyyy-MM-dd" v-model="searchOption.endTime" type="date" placeholder="请输入查询内容" class="date-style">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="最低金额" style="padding:5px 20px 5px 20px;">
              <el-input placeholder="请输入查询内容" clearable v-model="searchOption.minMoney"></el-input>
            </el-form-item>
            <el-form-item label="最高金额" style="padding:5px 20px 5px 20px;">
              <el-input placeholder="请输入查询内容"  clearable v-model="searchOption.maxMoney"></el-input>
            </el-form-item>
            <el-form-item label="餐厅名称" style="padding:5px 20px 5px 20px;">
              <el-input placeholder="请输入查询内容" clearable v-model="searchOption.restaurant_name"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div class="table-div">
          <el-table
            :data="order_list"
            style="width: 100%">
            <el-table-column
              prop="name"
              label="餐厅名称"
              width="150">
            </el-table-column>
            <el-table-column
              prop="type"
              label="餐厅类型"
              :filters="[{text: '中餐', value: '中餐'}, {text: '西餐', value: '西餐'}, {text: '休闲餐', value: '休闲餐'}]"
              :filter-method="filterHandler"
              width="150">
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
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import customerTopBar from '../components/CustomerTopBar'
import addressChoice from '../components/AddressChoice'
export default {
  name: 'CustomerCenter',
  components: {customerTopBar, addressChoice},
  data () {
    return {
      activeIndex: '2',
      username: localStorage.username,
      role: localStorage.roles,
      customer_info: {
        username: localStorage.username,
        email: '',
        phone: '',
        name: '',
        level: '',
        money: 0.0
      },
      customer_info_rule: {
        phone: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ]
      },
      address: [],
      order_list: [],
      save_order_list: [],
      searchOption: {
        restaurant_name: '',
        startTime: '',
        endTime: '',
        minMoney: '',
        maxMoney: ''
      }
    }
  },
  mounted: function () {
    this.getCustomerInfo()
  },
  watch: {
    searchOption: {
      deep: true,
      handler: function (val, oldVal) {
        this.order_list = this.searchNewList(val.restaurant_name, val.startTime, val.endTime, val.minMoney, val.maxMoney)
      }
    }
  },
  methods: {
    searchNewList (name, startTime, endTime, minMoney, maxMoney) {
      // console.log('start time : ' + startTime)
      let checkedOrder = []
      for (var i = 0; i < this.save_order_list.length; i++) {
        if (this.save_order_list[i].name.indexOf(name) >= 0 &&
          (startTime === null || startTime === '' || startTime <= this.save_order_list[i].time) &&
          (endTime === null || endTime === '' || endTime >= this.save_order_list[i].time) &&
          (minMoney === '' || parseInt(minMoney) <= this.save_order_list[i].total_price) &&
          (maxMoney === '' || parseInt(maxMoney) >= this.save_order_list[i].total_price)) {
          checkedOrder.push(this.save_order_list[i])
        }
      }
      return checkedOrder
    },
    handleSelect (key, keyPath) {
      this.activeIndex = key
      if (key === '3') {
        console.log('key=3')
        this.checkSelfDetail()
      }
    },
    // 得到用户信息
    getCustomerInfo () {
      this.address = []
      var _this = this
      this.$axios.get('/GetCustomerInfo', {
        params: {
          username: localStorage.username
        }
      }).then(function (response) {
        var data = response.data
        console.log(data)
        _this.customer_info.email = data.email
        _this.customer_info.phone = data.phone
        _this.customer_info.name = data.name
        _this.customer_info.level = data.level
        _this.customer_info.money = data.money
        var addrs = data.address
        console.log(addrs)
        for (var i = 0; i < addrs.length; i++) {
          _this.address.push({addr: addrs[i], key: (i + 1)})
        }
        console.log('address:')
        console.log(_this.address)
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    // 用户注销
    writtenOff () {
      var _this = this
      this.$axios.get('/DeleteCustomer', {
        params: {
          username: localStorage.username
        }
      }).then(function (response) {
        _this.$message({
          message: '用户已注销！',
          type: 'success'
        })
        _this.$router.push({name: 'Login'})
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    // 提交基本信息修改
    onSubmit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var _this = this
          this.$axios.get('/ChangeBaseCustomerInfo', {
            params: {
              username: localStorage.username,
              name: _this.customer_info.name,
              phone: _this.customer_info.phone
            }
          }).then(function (response) {
            _this.$message({
              message: '修改成功！',
              type: 'success'
            })
            _this.getCustomerInfo()
          }).catch(function (error) {
            console.log('error:' + error)
          })
        }
      })
    },
    filterHandler (value, row, column) {
      const property = column['property']
      return row[property] === value
    },
    // 查看统计
    checkSelfDetail () {
      this.order_list = []
      var _this = this
      this.$axios.get('/CheckCustomerDetail', {
        params: {
          username: localStorage.username
        }
      }).then(function (response) {
        var data = response.data
        console.log(data)
        for (var i = 0; i < data.length; i++) {
          _this.order_list.push({name: data[i].restaurantName,
            type: data[i].type,
            total_price: data[i].totalPrice,
            isCancelled: data[i].cancelled,
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
.menu-style{
  font-size: 18px;
  width: 250px;
  text-align: left;
  padding-bottom: 60px;
}
.right-part{
  padding-top: 100px;
  padding-left: 200px;
  font-size: 18px;
  min-width: 1100px;
  text-align: left;
}
.input-style{
  width: 470px;
}
.date-style{
  width: 225px;
}
  .table-div{
    width: 1100px;
  }
</style>
