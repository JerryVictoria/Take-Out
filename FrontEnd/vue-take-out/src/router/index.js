import Vue from 'vue'
import Router from 'vue-router'
import Login from '../pages/Login'
import Register from '../pages/Register'
import CustomerCenter from '../pages/CustomerCenter'
import Market from '../pages/Market'
import RestaurantInfoChange from '../pages/RestaurantInfoChange'
import ManagerCenter from '../pages/ManagerCenter'
import RestaurantInfoRelease from '../pages/RestaurantInfoRelease'
import MarketDetail from '../pages/MarketDetail'
import MarketOrderTrack from '../pages/MarketOrderTrack'
import RestaurantUntreated from '../pages/RestaurantUntreated'
import RestaurantRecordCheck from '../pages/RestaurantRecordCheck'
import RestaurantInfoCheck from '../pages/RestaurantInfoCheck'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/Register',
      name: 'Register',
      component: Register
    },
    {
      path: '/CustomerCenter',
      name: 'CustomerCenter',
      component: CustomerCenter,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/Market',
      name: 'Market',
      component: Market,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/RestaurantInfoChange',
      name: 'RestaurantInfoChange',
      component: RestaurantInfoChange,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/ManagerCenter',
      name: 'ManagerCenter',
      component: ManagerCenter,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/RestaurantInfoRelease',
      name: 'RestaurantInfoRelease',
      component: RestaurantInfoRelease,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/MarketDetail',
      name: 'MarketDetail',
      component: MarketDetail,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/MarketOrderTrack',
      name: 'MarketOrderTrack',
      component: MarketOrderTrack,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/RestaurantUntreated',
      name: 'RestaurantUntreated',
      component: RestaurantUntreated,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/RestaurantRecordCheck',
      name: 'RestaurantRecordCheck',
      component: RestaurantRecordCheck,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/RestaurantInfoCheck',
      name: 'RestaurantInfoCheck',
      component: RestaurantInfoCheck,
      meta: {
        requireAuth: true
      }
    }
  ]
})
