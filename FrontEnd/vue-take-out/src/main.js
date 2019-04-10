// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import router from './router'
import axios from './httpConfig/url_config'
import Vuex from 'vuex'
import store from './vuex/store'
import Progress from 'vue-multiple-progress'
import qs from 'qs'
import echarts from 'echarts'

Vue.use(Progress)
Vue.prototype.url_config = 'localhost'

Vue.use(ElementUI)
Vue.use(Vuex)

Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.$qs = qs
Vue.prototype.$echarts = echarts

router.beforeEach((to, from, next) => {
  // 可以在路由元信息指定哪些页面需要登录权限
  var isLogin = true
  if (localStorage.username === '' || localStorage.username === null || localStorage.username === undefined) {
    isLogin = false
  }
  // console.log(localStorage.username)
  console.log(to.meta.requireAuth)
  if (to.meta.requireAuth && !isLogin) { // 需要登录授权，这里还需要判断是否登录
    next({path: '/'})
    return
  }
  next()
})
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store, // 使用store
  components: { App },
  template: '<App/>'
})
