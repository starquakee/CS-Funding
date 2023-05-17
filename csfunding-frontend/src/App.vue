<script setup lang="ts">
import {RouterView, useRoute} from 'vue-router';
import {onMounted, ref} from "vue";
import {storeToRefs} from "pinia";
import {useUserStore} from "@/stores/user";
import {HomeFilled} from "@element-plus/icons-vue";

const activeIndex = ref('1');
const {isAdmin, userName} = storeToRefs(useUserStore());
const route = useRoute()
const bottom = ref('')

// let pageHeight = ''
function __onWindowResize() {
    bottom.value = `${document.body.clientHeight - 40}px`
}

window.onresize = __onWindowResize
__onWindowResize()
</script>

<template>
  <!--  <header>-->
  <!--    <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="125" height="125" />-->

  <!--    <div class="wrapper">-->
  <!--      <HelloWorld msg="You did it!" />-->

  <!--      <nav>-->
  <!--        <RouterLink to="/">Home</RouterLink>-->
  <!--        <RouterLink to="/about">About</RouterLink>-->
  <!--      </nav>-->
  <!--    </div>-->
  <!--  </header>-->
    <el-menu :default-active="activeIndex"
             class="el-menu-demo"
             mode="horizontal"
             :ellipsis="false"
             v-if="userName"
    >
        <el-menu-item index="0">
            <font style="color: white">南方科技大学财务管理系统</font>
        </el-menu-item>
        <div class="flex-grow"/>
        <el-menu-item index="1">
            <font style="color: white">您好 {{ userName }}!</font>
        </el-menu-item>
        <el-sub-menu index="2">
            <template #title>
                <font style="color: white">注销</font>
            </template>
            <el-menu-item index="2-1">
                <router-link to="login?logoff=1">
                    返回登录
                </router-link>
            </el-menu-item>
            <el-menu-item index="2-2">
                返回官网
            </el-menu-item>
        </el-sub-menu>
    </el-menu>

    <RouterView/>

    <div class="Bottom" v-if="userName">
        <div style="height: 5px"></div>
        <el-button style="background-color: #8f000b; border: #8f000b">
            <el-icon style="vertical-align: middle;" size="25px">
                <HomeFilled/>
            </el-icon>
            <font style="color: white; margin-left: 5px; margin-top: 3px">主页</font>
        </el-button>
    </div>

</template>

<style scoped>
.Bottom {
    position: fixed;
    width: 100%;
    top: v-bind(bottom);
    height: 50px;
    background-color: #8f000b;
    z-index: 3;
}

header {
    line-height: 1.5;
    max-height: 100vh;
}

.logo {
    display: block;
    margin: 0 auto 2rem;
}

nav {
    width: 100%;
    font-size: 12px;
    text-align: center;
    margin-top: 2rem;
}

nav a.router-link-exact-active {
    color: var(--color-text);
}

nav a.router-link-exact-active:hover {
    background-color: transparent;
}

nav a {
    display: inline-block;
    padding: 0 1rem;
    border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
    border: 0;
}

@media (min-width: 1024px) {
    header {
        display: flex;
        place-items: center;
        padding-right: calc(var(--section-gap) / 2);
    }

    .logo {
        margin: 0 2rem 0 0;
    }

    header .wrapper {
        display: flex;
        place-items: flex-start;
        flex-wrap: wrap;
    }

    nav {
        text-align: left;
        margin-left: -1rem;
        font-size: 1rem;

        padding: 1rem 0;
        margin-top: 1rem;
    }
}
</style>
