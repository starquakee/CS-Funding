<template>
    <h1>
        {{ name }}
    </h1>
    <el-form :model="loginForm" label-width="120px">
        <el-form-item label="User Name">
            <el-input v-model="loginForm.name"/>
        </el-form-item>
        <el-form-item label="Password">
            <el-input v-model="loginForm.password" type="password" show-password/>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">Login</el-button>
        </el-form-item>
    </el-form>
</template>

<script setup>
import {reactive} from "vue";

const name = "LoginView"
const loginForm = reactive({
    name: "",
    password: ""
})

function onSubmit() {
    console.log(loginForm)
}
</script>

<style scoped>

</style>