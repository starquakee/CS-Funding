<template>
  <div style="background-image: url('../img/background.jpg');">
    <div class="login-wrap">
      <el-form class="login-container demo-ruleForm" ref="ruleFormRef" status-icon
               :model="loginForm" v-if="!isRegister"
      >
        <h1 class="title">用户登陆</h1>
        <el-form-item label="登录失败" style="padding-left: 10px; border: 1px solid red; border-radius: 4px"
                      v-if="loginFail">
        </el-form-item>
        <el-form-item prop="name">
          <el-input type="text" v-model="loginForm.name" placeholder="用户账号" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="key">
          <el-input type="password" v-model="loginForm.key" placeholder="用户密码" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doLogin" style="width: 45%;">登陆</el-button>
          <el-button type="primary" @click="isRegister=true" style="width: 45%;">去注册</el-button>
        </el-form-item>
      </el-form>
      <el-form class="login-container demo-ruleForm" ref="ruleFormRef" status-icon :rules="rules"
               :model="registerForm" v-if="isRegister"
      >
        <h1 class="title">用户注册</h1>
        <el-form-item label="注册成功" style="padding-left: 10px; border: 1px solid green; border-radius: 4px"
                      v-if="registerSuccess">
        </el-form-item>
        <el-form-item prop="userId">
          <el-input type="text" v-model="registerForm.userId" placeholder="登录名称" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="name">
          <el-input type="text" v-model="registerForm.name" placeholder="用户名称" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="register_key">
          <el-input type="password" v-model="registerForm.register_key" placeholder="你的密码"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="register_check_key">
          <el-input type="password" v-model="registerForm.register_check_key" placeholder="重复密码"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="e_mail">
          <el-input type="text" v-model="registerForm.e_mail" placeholder="验证邮箱" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="sendEmail">{{ isSend ? ("已发送" + `(${sendTimeout})`) : "发送验证码" }}</el-button>
        </el-form-item>
        <el-form-item prop="valid_code">
          <el-input v-model="registerForm.valid_code" placeholder="验证码" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleFormRef)" style="width: 45%;">
            注册
          </el-button>
          <el-button type="primary" @click="isRegister=false; isSend=false; registerSuccess=false" style="width: 45%;">
            去登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script setup lang="ts">
import axios from 'axios';
import {onMounted, reactive, ref} from "vue";
import router from "@/router";
import {useRoute} from "vue-router";
import {useTokenStore} from "@/stores/token";
import {storeToRefs} from "pinia";
import {useUserStore} from "@/stores/user";
import type {FormInstance, FormRules} from 'element-plus'

const ruleFormRef = ref<FormInstance>()

const store = useTokenStore();
const route = useRoute();

const loginForm = reactive({
  name: "",
  key: "",
});

const registerForm = reactive({
  userId: '',
  name: "",
  key: "",
  e_mail: "",
  register_key: "",
  register_check_key: "",
  valid_code: ""
})
const loginFail = ref(false);
const isRegister = ref(false);
const registerSuccess = ref(false);
const isSend = ref(false);
const {isAdmin, userName} = storeToRefs(useUserStore());
const sendTimeout = ref(0);

function generateString(length: number) {
  let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  let result = ' ';
  const charactersLength = characters.length;
  for (let i = 0; i < length; i++) {
    result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }

  return result;
}

async function sendEmail() {
  if (!isSend.value && registerForm.e_mail.includes('sustech.edu')) {
    // send mail

    axios.post("http://localhost:8081/register/send-mail", {
      mail: registerForm.e_mail,
      reg: generateString(10)
    }).then(res => console.log(res));
    // isSend.value = true;
    // for (let i = 60; i > 0; i--) {
    //   sendTimeout.value = i;
    //   await new Promise(f => setTimeout(f, 1000));
    // }
    // isSend.value = false;
  }
}

const checkEMail = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('请填写南科大邮箱'))
  }
  setTimeout(() => {
    if (!value.includes('sustech.edu')) {
      callback(new Error('请填写南科大邮箱'))
    } else {
      callback()
    }
  }, 100)
}

const checkValid = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('请填写验证码'))
  }
  setTimeout(() => {

    if (value != "12345") {
      callback(new Error('验证码错误'))
    } else {
      callback()
    }
  }, 100)
}

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else {
    if (registerForm.register_key !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('register_key', () => null)
    }
    callback()
  }
}

const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== registerForm.register_key) {
    callback(new Error("两次密码不一致!"))
  } else {
    callback()
  }
}

const rules = reactive<FormRules>({
  e_mail: [{validator: checkEMail, trigger: 'blur'}],
  register_key: [{validator: validatePass, trigger: 'blur'}],
  register_check_key: [{validator: validatePass2, trigger: 'blur'}],
  valid_code: [{validator: checkValid, trigger: 'blur'}],
})

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      console.log('submit!')
      registerSuccess.value = true
    } else {
      console.log('error submit!')
      return false
    }
  })
}


onMounted(() => {
  if (route.query.logoff) {
    store.clearToken()
    userName.value = ""
    isAdmin.value = false
  }
})

function doLogin() {
  let url = "http://localhost:8081/api/login";
  axios.post(url, loginForm).then(res => {
    if (res.data.code === 200) {
      store.setToken(res.data.data.token);
      isAdmin.value = res.data.data.isAdmin;
      userName.value = res.data.userName;
      router.push('/home');
    } else {
      loginFail.value = true;
    }
  });
  // router.push('/home')
  // axios.post(url, form).then(resp => {
  //
  //     code = resp.data.code
  //     person = resp.data.data.name
  //     phoneNum = resp.data.data.phoneNum
  //     isAdmin = resp.data.data.isAdmin
  //
  //     console.log(code, person)
  //
  //     if (code === 200) {
  //         router.push({
  //             path: '/home', query: {
  //                 username: form.name,
  //                 name: person,
  //                 phoneNumber: phoneNum,
  //                 isAdmin: isAdmin
  //             }
  //         });
  //     }
  // })


}

function setToken() {
  console.log(router.currentRoute.value)
  if (!store.token) {
    store.setToken('tester')
  } else {
    store.clearToken()
  }
}
</script>


<style scoped>

.login-wrap {
  box-sizing: border-box;
  width: 100%;
  height: 781px;
  padding-top: 10%;
  background-repeat: no-repeat;
  background-position: center right;
  background-size: 100%;
  background-image: url('../img/background.jpg');

}

.login-container {
  border-radius: 10px;
  margin: 0 auto;
  width: 350px;
  padding: 30px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  text-align: left;
  box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
}

.title {
  margin: 0 auto 40px auto;
  text-align: center;
  color: #505458;
}


</style>