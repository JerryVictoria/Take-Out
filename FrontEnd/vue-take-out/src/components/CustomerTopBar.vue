<template>
  <div>
    <el-menu :default-active="activeIndex" background-color="#409EFF" text-color="#FFF" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="0"><img src="../assets/logo.png" style="width: auto;height: auto;max-width: 150px;"></el-menu-item>
      <el-menu-item class="top-bar-style" index="1">Yummy!点餐</el-menu-item>
      <el-menu-item class="top-bar-style" index="2">跟踪订单</el-menu-item>
      <el-menu-item class="top-bar-style" index="3">个人中心</el-menu-item>
      <el-menu-item class="top-bar-style" index="4">登出</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'CustomerTopBar',
  props: ['activeIndex'],
  data () {
    return {
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      if (key === '1') {
        this.$router.push({name: 'Market'})
      } else if (key === '2') {
        this.$router.push({name: 'MarketOrderTrack'})
      } else if (key === '3') {
        this.$router.push({name: 'CustomerCenter'})
      } else if (key === '4') {
        var storage = window.localStorage
        storage.clear()
        this.$router.push({name: 'Login'})
      } else {
        this.$router.push({name: 'Market'})
      }
    }
  }
}
</script>

<style scoped>
.top-bar-style{
  font-size: 22px;
}
</style>
