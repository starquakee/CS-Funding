<template>

</template>

<script setup>
import router from "@/router";
import {onMounted} from "vue";

onMounted(() => {
    router.push({name: 'login'})
    // ctx.$route.push({name:'login'})
})
</script>

<style scoped>

</style>