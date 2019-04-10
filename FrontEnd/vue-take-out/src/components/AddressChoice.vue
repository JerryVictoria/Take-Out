<template>
  <div style="display: flex;">
    <el-select v-model="city" placeholder="请选择所在市" style="padding-right:22px">
      <el-option
        v-for="item in citys"
        :key="item.city"
        :label="item.label"
        :value="item.city">
      </el-option>
    </el-select>
    <div v-if="city==='南京市'" placeholder="请选择所在区" style="padding-right:22px">
      <el-select v-model="district">
        <el-option
          v-for="item in nj_districts"
          :key="item.district"
          :label="item.label"
          :value="item.district">
        </el-option>
      </el-select>
    </div>
    <div v-else>
      <el-select  v-model="district" placeholder="请选择所在区" style="padding-right:20px">
        <el-option
          v-for="item in ha_districts"
          :key="item.district"
          :label="item.label"
          :value="item.district">
        </el-option>
      </el-select>
    </div>
    <div style="padding-right:20px">
      <el-input v-model="address" placeholder="请输入详细地址"></el-input>
    </div>
    <div v-if="role==='普通用户'">
      <el-button type="primary" @click="addCustomerAddress">确认增加</el-button>
    </div>
    <div v-else>
      <el-button type="primary" @click="changeRestaurantAddress">确认修改</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AddressChoice',
  data () {
    return {
      role: localStorage.roles,
      city: '',
      district: '',
      address: '',
      citys: [{
        city: '南京市',
        label: '南京市'
      }, {
        city: '淮安市',
        label: '淮安市'
      }],
      nj_districts: [{
        district: '鼓楼区',
        label: '鼓楼区'
      }, {
        district: '玄武区',
        label: '玄武区'
      }],
      ha_districts: [{
        district: '清浦区',
        label: '清浦区'
      }, {
        district: '清河区',
        label: '清河区'
      }]
    }
  },
  methods: {
    setDefaultAddress (city, district, address) {
      this.city = city
      this.district = district
      this.address = address
    },
    addCustomerAddress () {
      if (this.city === '') {
        this.$message({
          message: '市不能为空！',
          type: 'error'
        })
      } else if (this.district === '') {
        this.$message({
          message: '区不能为空！',
          type: 'error'
        })
      } else if (this.address === '') {
        this.$message({
          message: '详细地址不能为空！',
          type: 'error'
        })
      } else {
        var _this = this
        this.$axios.get('/AddCustomerAddress', {
          params: {
            username: localStorage.username,
            address: (_this.city + _this.district + _this.address)
          }
        }).then(function (response) {
          _this.$message({
            message: '增加成功！',
            type: 'success'
          })
          _this.$emit('refreshInfo')
        }).catch(function (error) {
          console.log('error:' + error)
        })
      }
    },
    changeRestaurantAddress () {
      var _this = this
      this.$axios.get('/ChangeRestaurantAddress', {
        params: {
          rid: parseInt(localStorage.username),
          address: (_this.city + _this.district + _this.address)
        }
      }).then(function (response) {
        _this.$message({
          message: '修改成功，等待审核！',
          type: 'success'
        })
        _this.$emit('refreshInfo')
      }).catch(function (error) {
        console.log('error:' + error)
      })
    }
  }
}
</script>

<style scoped>

</style>
