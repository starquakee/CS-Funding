<template>
    <div class="login-wrap">
        <el-form class="login-container">
            <h1 class="title">用户登陆</h1>
            <h1 class="title">{{ token }}</h1>
            <el-form-item>
                <el-input type="text" v-model="form.name" placeholder="用户账号" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input type="password" v-model="form.key" placeholder="用户密码" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="doLogin" style="width: 100%;">登陆</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="setToken" style="width: 100%;">Set Token</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>


<script setup lang="ts">
import axios from 'axios';
import {reactive} from "vue";
import router from "@/router";
import {useTokenStore} from "@/stores/token";
import {storeToRefs} from "pinia";

const store = useTokenStore()
const {token} = storeToRefs(store)
const name = 'Login';
const form = reactive({name: "", key: ""});
let code;
let person;
let phoneNum;
let isAdmin;

function doLogin() {
    let url = "http://localhost:8081/api/login";
    router.push('/home')
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
    if (token.value) {
        token.value = '';
    } else {
        token.value = 'tester';
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