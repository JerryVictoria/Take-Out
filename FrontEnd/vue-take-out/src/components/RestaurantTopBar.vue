<template>
  <div>
    <el-menu :default-active="activeIndex" background-color="#409EFF" text-color="#FFF" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="0"><img src="../assets/logo.png" style="width: auto;height: auto;max-width: 150px;"></el-menu-item>
      <el-menu-item class="top-bar-style" index="1">修改餐厅信息</el-menu-item>
      <el-menu-item class="top-bar-style" index="2">发布餐厅消息</el-menu-item>
      <el-menu-item class="top-bar-style" index="3">查看送餐记录</el-menu-item>
      <el-menu-item class="top-bar-style" index="4">查看统计信息</el-menu-item>
      <el-menu-item class="top-bar-style" index="5">查看未处理订单</el-menu-item>
      <el-menu-item class="top-bar-style" index="6">登出</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'RestaurantTopBar',
  props: ['activeIndex'],
  data () {
    return {
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      if (key === '1') {
        this.$router.push({name: 'RestaurantInfoChange'})
      } else if (key === '2') {
        this.waitToApprove()
      } else if (key === '3') {
        this.$router.push({name: 'RestaurantRecordCheck'})
      } else if (key === '4') {
        this.$router.push({name: 'RestaurantInfoCheck'})
      } else if (key === '5') {
        this.$router.push({name: 'RestaurantUntreated'})
      } else if (key === '6') {
        var storage = window.localStorage
        storage.clear()
        this.$router.push({name: 'Login'})
      } else {
        this.$router.push({name: 'RestaurantInfoChange'})
      }
    },
    waitToApprove () {
      var _this = this
      this.$axios.get('/WaitToApprove', {
        params: {
          rid: localStorage.username
        }
      }).then(function (response) {
        var data = response.data
        console.log('wait to approve:' + data)
        if (data) {
          _this.$message({
            message: '您有修改信息等待审核！',
            type: 'error'
          })
        } else {
          _this.$router.push({name: 'RestaurantInfoRelease'})
        }
      }).catch(function (error) {
        console.log('error:' + error)
      })
    }
  }
}
</script>

<style scoped>
  .top-bar-style{
    font-size: 22px;
  }
</style>
