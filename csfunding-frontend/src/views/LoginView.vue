<template>
    <div class="login-wrap">
        <el-form class="login-container">
            <h1 class="title">用户登陆</h1>
            <el-form-item label="登录失败" style="padding-left: 10px; border: 1px solid red; border-radius: 4px" v-if="loginFail">
            </el-form-item>
            <el-form-item>
                <el-input type="text" v-model="form.name" placeholder="用户账号" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input type="password" v-model="form.key" placeholder="用户密码" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="doLogin" style="width: 100%;">登陆</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>


<script setup lang="ts">
import axios from 'axios';
import {inject, reactive, ref} from "vue";
import router from "@/router";
import {useTokenStore} from "@/stores/token";
import {storeToRefs} from "pinia";


const store = useTokenStore();
const {token} = storeToRefs(store);
const form = reactive({name: "", key: ""});
const loginFail = ref(false)

function doLogin() {
    let url = "http://localhost:8081/api/login";
    axios.post(url, form).then(res => {
        if (res.data.code === 200){
            store.setToken(res.data.data);
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
    height: 100%;
    padding-top: 10%;
    background-repeat: no-repeat;
    background-position: center right;
    background-size: 100%;
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