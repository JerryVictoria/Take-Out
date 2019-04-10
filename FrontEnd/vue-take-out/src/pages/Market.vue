<template>
    <div>
      <customerTopBar :activeIndex = "'1'"></customerTopBar>
      <div class="yummy-market">
        <label style="font-size: 36px">点餐大厅</label>
        <hr/>
        <label>注：如果您选择的送餐地址和餐厅非同一市将无法送达！</label>
        <br/><br/>
        <div>
          <el-table
            :data="restaurants"
            style="width: 100%">
            <el-table-column
              prop="name"
              label="餐厅名称"
              width="200">
            </el-table-column>
            <el-table-column
              prop="type"
              label="餐厅类型"
              :filters="[{text: '中餐', value: '中餐'}, {text: '西餐', value: '西餐'}, {text: '休闲餐', value: '休闲餐'}]"
              :filter-method="filterHandler"
              width="200">
            </el-table-column>
            <el-table-column
              prop="address"
              label="餐厅地址"
              width="200">
            </el-table-column>
            <el-table-column
              prop="phone"
              label="餐厅电话"
              width="200">
            </el-table-column>
            <el-table-column
              label="操作"
              width="200">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" plain @click="checkDetail(scope.$index)">查看详情</el-button>
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
  name: 'Market',
  components: {customerTopBar},
  data () {
    return {
      restaurants: []
    }
  },
  mounted: function () {
    this.getRestaurantList()
  },
  methods: {
    getRestaurantList () {
      this.restaurants = []
      var _this = this
      this.$axios.get('/GetRestaurants', {
        params: {}
      }).then(function (response) {
        var data = response.data
        for (var i = 0; i < data.length; i++) {
          _this.restaurants.push({rid: data[i].rid, name: data[i].name, phone: data[i].phone, address: data[i].address, type: data[i].type})
        }
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    filterHandler (value, row, column) {
      const property = column['property']
      return row[property] === value
    },
    checkDetail (index) {
      this.$router.push({name: 'MarketDetail',
        params: {rid: this.restaurants[index].rid,
          address: this.restaurants[index].address,
          name: this.restaurants[index].name,
          phone: this.restaurants[index].phone}})
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
</style>
