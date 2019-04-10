<template>
  <div>
    <div style="width: 500px;margin: auto">
      <img src="../assets/logo.png" style="width: auto;height: auto;max-width: 100%;">
    </div>
    <el-card style="width: 500px; margin:5px auto;">
      <div slot="header">
        <span>注册平台账号</span>
      </div>
      <el-form :model="register_form" :rules="register_form_rules" ref="register_form">
        <el-form-item label="角色">
          <el-radio-group v-model="register_form.role">
            <el-radio label="普通用户"></el-radio>
            <el-radio label="餐厅"></el-radio>
          </el-radio-group>
        </el-form-item>
        <div v-if="register_form.role==='普通用户'">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="register_form.username" placeholder="用户名"></el-input>
          </el-form-item>
        </div>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="register_form.password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="register_form.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <div style="padding: 12px" v-if="register_form.role==='餐厅'">
          <label style="font-size: 14px">说明：餐厅由系统分配用户名，可以注册成功后修改餐厅信息。</label>
        </div>
        <el-form-item>
          <el-button type="primary"  @click="doRegister('register_form')">注册</el-button>
          <el-button  @click="returnLogin">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data () {
    return {
      emails: [],
      register_form: {
        role: '普通用户',
        username: '',
        password: '',
        email: ''
      },
      register_form_rules: {
        username: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  mounted: function () {
    this.getCustomerEmails()
  },
  methods: {
    getCustomerEmails () {
      var _this = this
      this.$axios.get('/GetAllEmails', {
        params: {}
      }).then(function (response) {
        var data = response.data
        for (var i = 0; i < data.length; i++) {
          _this.emails.push(data[i])
        }
      }).catch(function (error) {
        console.log('error:' + error)
      })
    },
    doRegister (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var emailExisted = false
          if (this.register_form.role === '普通用户') {
            for (var i = 0; i < this.emails.length; i++) {
              if (this.emails[i] === this.register_form.email) {
                emailExisted = true
                this.$message({
                  message: '邮箱已经被注册！',
                  type: 'error'
                })
                break
              }
            }
          }
          if (!emailExisted) {
            var _this = this
            this.$axios.post('/Register', {
              username: _this.register_form.username,
              password: _this.register_form.password,
              role: _this.register_form.role,
              email: _this.register_form.email
            }).then(function (response) {
              var data = response.data
              console.log(data)
              if (data.indexOf('点击邮箱验证后即可登录') >= 0) {
                _this.$message({
                  message: data,
                  type: 'success'
                })
                _this.$router.push({name: 'Login'})
              } else {
                _this.$message({
                  message: data,
                  type: 'error'
                })
              }
            }).catch(function (error) {
              console.log('register error')
              console.log(error)
            })
          }
        }
      })
    },
    returnLogin () {
      this.$router.push({name: 'Login'})
    }
  }
}
</script>

<style scoped>

</style>
