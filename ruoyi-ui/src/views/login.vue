<template>
  <div class="login platform-login">
    <section class="login-visual">
      <div class="visual-grid"></div>
      <div class="brand-panel">
        <div class="platform-badge">司法行政 · 名册监管 · 证照预警</div>
        <h1>司法鉴定平台</h1>
        <p class="system-name">司法鉴定机构与鉴定人管理系统</p>
        <div class="feature-strip">
          <div>
            <strong>机构档案</strong>
            <span>统一登记</span>
          </div>
          <div>
            <strong>鉴定人名册</strong>
            <span>动态维护</span>
          </div>
          <div>
            <strong>证照预警</strong>
            <span>到期提醒</span>
          </div>
        </div>
      </div>
    </section>

    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form platform-card">
      <div class="form-header">
        <h2>{{ title }}</h2>
        <p>请输入账号信息完成登录</p>
      </div>
      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          type="text"
          auto-complete="off"
          placeholder="账号"
        >
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="验证码"
          class="code-input"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img" />
        </div>
      </el-form-item>
      <div class="form-options">
        <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
        <router-link v-if="register" class="link-type" :to="'/register'">立即注册</router-link>
      </div>
      <el-form-item class="login-action">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登录</span>
          <span v-else>登录中...</span>
        </el-button>
      </el-form-item>
    </el-form>

    <div class="el-login-footer">
      <span>{{ footerContent }}</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login"
import Cookies from "js-cookie"
import { encrypt, decrypt } from '@/utils/jsencrypt'
import defaultSettings from '@/settings'

export default {
  name: "Login",
  data() {
    return {
      title: "司法鉴定平台",
      footerContent: defaultSettings.footerContent,
      codeUrl: "",
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      captchaEnabled: true,
      register: true,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.getCode()
    this.getCookie()
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img
          this.loginForm.uuid = res.uuid
        }
      })
    },
    getCookie() {
      const username = Cookies.get("username")
      const password = Cookies.get("password")
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
        code: this.loginForm.code,
        uuid: this.loginForm.uuid
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 })
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 })
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 })
          } else {
            Cookies.remove("username")
            Cookies.remove("password")
            Cookies.remove('rememberMe')
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{})
          }).catch(() => {
            this.loading = false
            if (this.captchaEnabled) {
              this.getCode()
            }
          })
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.platform-login {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100%;
  padding: 42px 48px 62px;
  overflow: hidden;
  background:
    linear-gradient(135deg, rgba(8, 38, 82, 0.96), rgba(14, 88, 122, 0.92)),
    linear-gradient(90deg, rgba(255, 255, 255, 0.08) 1px, transparent 1px),
    linear-gradient(0deg, rgba(255, 255, 255, 0.08) 1px, transparent 1px);
  background-size: auto, 46px 46px, 46px 46px;
}

.login-visual {
  position: relative;
  flex: 1;
  max-width: 760px;
  min-height: 520px;
  margin-right: 54px;
  border-radius: 8px;
  overflow: hidden;
  background:
    linear-gradient(145deg, rgba(255, 255, 255, 0.16), rgba(255, 255, 255, 0.04)),
    linear-gradient(90deg, rgba(92, 196, 168, 0.14), rgba(255, 255, 255, 0));
  border: 1px solid rgba(255, 255, 255, 0.18);
  box-shadow: 0 26px 70px rgba(0, 23, 54, 0.32);
}

.visual-grid {
  position: absolute;
  inset: 0;
  background:
    linear-gradient(90deg, rgba(255, 255, 255, 0.08) 1px, transparent 1px),
    linear-gradient(0deg, rgba(255, 255, 255, 0.08) 1px, transparent 1px),
    linear-gradient(135deg, transparent 0 50%, rgba(255, 255, 255, 0.08) 50% 51%, transparent 51%);
  background-size: 56px 56px, 56px 56px, 140px 140px;
}

.brand-panel {
  position: relative;
  z-index: 1;
  height: 100%;
  padding: 70px 64px;
  color: #ffffff;
}

.platform-badge {
  display: inline-flex;
  align-items: center;
  height: 34px;
  padding: 0 16px;
  border-radius: 4px;
  background: rgba(255, 255, 255, 0.14);
  border: 1px solid rgba(255, 255, 255, 0.22);
  font-size: 14px;
}

.brand-panel h1 {
  margin: 34px 0 14px;
  font-size: 44px;
  line-height: 1.18;
  font-weight: 700;
}

.system-name {
  margin: 0;
  font-size: 22px;
  line-height: 1.5;
  color: rgba(255, 255, 255, 0.86);
}

.student-info {
  margin-top: 28px;
  display: grid;
  gap: 8px;
  color: rgba(255, 255, 255, 0.82);
}

.student-info span {
  font-size: 17px;
}

.student-info strong {
  font-size: 15px;
  font-weight: 500;
}

.feature-strip {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 14px;
  margin-top: 72px;
}

.feature-strip div {
  min-height: 92px;
  padding: 18px;
  border-radius: 8px;
  background: rgba(255, 255, 255, 0.12);
  border: 1px solid rgba(255, 255, 255, 0.18);
}

.feature-strip strong,
.feature-strip span {
  display: block;
}

.feature-strip strong {
  font-size: 18px;
  margin-bottom: 10px;
}

.feature-strip span {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.76);
}

.platform-card {
  position: relative;
  z-index: 1;
  width: 404px;
  flex-shrink: 0;
  padding: 34px 30px 20px;
  border-radius: 8px;
  background: rgba(255, 255, 255, 0.96);
  box-shadow: 0 24px 70px rgba(0, 23, 54, 0.34);
}

.form-header {
  margin-bottom: 28px;
  text-align: left;
}

.form-header h2 {
  margin: 0 0 8px;
  color: #10243f;
  font-size: 24px;
  line-height: 1.3;
}

.form-header p {
  margin: 0;
  color: #6b778c;
  font-size: 14px;
}

.login-form {
  .el-input {
    height: 40px;
    input {
      height: 40px;
      border-radius: 4px;
    }
  }
  .input-icon {
    height: 40px;
    width: 14px;
    margin-left: 2px;
  }
}

.code-input {
  width: 62%;
}

.login-code {
  width: 34%;
  height: 40px;
  float: right;
  border-radius: 4px;
  overflow: hidden;
  border: 1px solid #dcdfe6;
  background: #f7f9fc;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.login-code-img {
  width: 100%;
  height: 38px;
}

.form-options {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 2px 0 22px;
}

.login-action {
  width: 100%;
  margin-bottom: 0;
  .el-button {
    width: 100%;
    height: 42px;
    border-radius: 4px;
    background: #0f5f8f;
    border-color: #0f5f8f;
    font-size: 15px;
  }
}

.el-login-footer {
  position: fixed;
  left: 0;
  right: 0;
  bottom: 14px;
  text-align: center;
  color: rgba(255, 255, 255, 0.78);
  font-family: Arial, "Microsoft YaHei", sans-serif;
  font-size: 12px;
  letter-spacing: 0;
}

@media (max-width: 1100px) {
  .platform-login {
    flex-direction: column;
    padding: 28px 20px 62px;
  }

  .login-visual {
    width: 100%;
    max-width: 760px;
    min-height: 360px;
    margin: 0 0 22px;
  }

  .brand-panel {
    padding: 42px 36px;
  }

  .brand-panel h1 {
    font-size: 34px;
  }

  .feature-strip {
    margin-top: 34px;
  }
}

@media (max-width: 640px) {
  .login-visual {
    min-height: auto;
  }

  .brand-panel {
    padding: 30px 22px;
  }

  .brand-panel h1 {
    font-size: 28px;
  }

  .system-name {
    font-size: 17px;
  }

  .feature-strip {
    grid-template-columns: 1fr;
    gap: 10px;
  }

  .feature-strip div {
    min-height: 74px;
  }

  .platform-card {
    width: 100%;
    max-width: 404px;
  }
}
</style>
