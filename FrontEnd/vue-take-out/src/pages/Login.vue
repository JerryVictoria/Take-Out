<template>
  <div>
    <div style="width: 700px;margin: auto">
      <img src="../assets/logo.png" style="width: auto;height: auto;max-width: 100%;">
    </div>
    <el-card style="width: 500px; margin:10px auto;">
      <div slot="header">
        <span>Welcome to Yummy！</span>
      </div>
      <div>
        <el-form :model="login_form" :rules="login_form_rules" ref="login_form">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="login_form.username" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="login_form.password" placeholder="密码"></el-input>
          </el-form-item>
          <el-form-item label="角色">
            <el-radio-group v-model="login_form.role">
              <el-radio label="普通用户"></el-radio>
              <el-radio label="餐厅"></el-radio>
              <el-radio label="管理员"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('login_form')">登录</el-button>
            <el-button  @click="getAccount">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      login_form: {
        username: '',
        password: '',
        role: '普通用户'
      },
      login_form_rules: {
        username: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    onSubmit: function (formName) {
      if (this.login_form.role === '餐厅' && this.login_form.username.length !== 7) {
        this.$message({
          message: '餐厅用户名为七位数字标识！',
          type: 'error'
        })
      } else {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log('submit!')
            var _this = this
            this.$axios.post('/Login', {
              username: _this.login_form.username,
              password: _this.login_form.password,
              role: _this.login_form.role
            }).then(function (response) {
              var data = response.data
              console.log(data)
              if (data.success === true) {
                console.log('success!')
                localStorage.username = _this.login_form.username
                localStorage.roles = _this.login_form.role
                if (localStorage.roles === '普通用户') {
                  localStorage.level = data.level
                  console.log(localStorage.level)
                  _this.$router.push({name: 'Market'})
                } else if (localStorage.roles === '餐厅') {
                  _this.$router.push({name: 'RestaurantInfoChange'})
                } else {
                  _this.$router.push({name: 'ManagerCenter'})
                }
              } else {
                _this.$message({
                  message: '用户名或密码错误！',
                  type: 'error'
                })
              }
            }).catch(function (error) {
              console.log('login error')
              console.log(error)
            })
          } else {
            this.$message({
              message: '用户名和密码不能为空！',
              type: 'error'
            })
          }
        })
      }
    },
    getAccount () {
      console.log('get a new account!')
      this.$router.push({name: 'Register'})
    }
  }
}
</script>

<style scoped>

</style>
