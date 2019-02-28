<template>
  <div class="login_page fillcontain" v-loading="loading">
    <transition name="form-fade" mode="in-out">
      <section class="form_contianer" v-show="showLogin">
        <div class="manage_tip">
          <p>elm后台管理系统</p>
        </div>
        <el-form :model="loginForm" ref="loginForm">
          <el-form-item prop="username">
            <el-input v-model="loginForm.loginName" placeholder="用户名"><span>dsfsf</span></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password" placeholder="密码" v-model="loginForm.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm()" class="submit_btn">登陆</el-button>
          </el-form-item>
        </el-form>
        <p class="tip">温馨提示：</p>
        <!--<p class="tip">未登录过的新用户，自动注册</p>-->
        <!--<p class="tip">注册过的用户可凭账号密码登录</p>-->
      </section>
    </transition>
  </div>
</template>

<script>

  export default {
    data(){
      return {
        loginForm: {
          loginName: 'admin',
          password: '1234',
        },
        showLogin: false,
        loading: false,
      }
    },
    mounted(){
      this.showLogin = true;
    },
    methods: {
      async submitForm() {
        if (this.loginForm.loginName == undefined ||  this.loginForm.loginName.length == 0) {
          this.$message({
            type:'error',
            message:'用户不能为空'
          });
          return;
        }
        if (this.loginForm.password == undefined ||  this.loginForm.password.length == 0) {
          this.$message({
            type:'error',
            message:'密码不能为空'
          });
          return;
        }
        this.loading = true;
        this.$https.post('/web/login', this.loginForm, this).then((data) => {
          console.log(data);
          this.$router.push('manage')
        },(data)=>{
          this.params.nonce = data.data?data.data:null;
          console.log(data,'登录');
        }).finally((data) => {
            this.showLogin = true;
            this.loading = false;
          }
        )


        // this.$refs[formName].validate(async (valid) => {
        //   if (valid) {
        //     const res = await login({loginName: this.loginForm.username, password: this.loginForm.password})
        //     if (res.code == 0) {
        //       this.$message({
        //         type: 'success',
        //         message: '登录成功'
        //       });
        //       this.$router.push('manage')
        //     }else{
        //       this.$message({
        //         type: 'error',
        //         message: res.message
        //       });
        //     }
        //   } else {
        //     this.$notify.error({
        //       title: '错误',
        //       message: '请输入正确的用户名密码',
        //       offset: 100
        //     });
        //     return false;
        //   }
        // });
      },
    },
    watch: {
      adminInfo: function (newValue){
        if (newValue.id) {
          this.$message({
            type: 'success',
            message: '检测到您之前登录过，将自动登录'
          });
          this.$router.push('manage')
        }
      }
    }
  }
</script>

<style lang="less" scoped>
  @import '../style/mixin';
  .login_page{
    background-color: #324057;
  }
  .manage_tip{
    position: absolute;
    width: 100%;
    top: -100px;
    left: 0;
    p{
      font-size: 34px;
      color: #fff;
    }
  }
  .form_contianer{
    .wh(320px, 210px);
    .ctp(320px, 210px);
    padding: 25px;
    border-radius: 5px;
    text-align: center;
    background-color: #fff;
    .submit_btn{
      width: 100%;
      font-size: 16px;
    }
  }
  .tip{
    font-size: 12px;
    color: red;
  }
  .form-fade-enter-active, .form-fade-leave-active {
    transition: all 1s;
  }
  .form-fade-enter, .form-fade-leave-active {
    transform: translate3d(0, -50px, 0);
    opacity: 0;
  }
</style>
