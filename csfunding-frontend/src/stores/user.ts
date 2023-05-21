import {ref, computed, reactive} from 'vue'
import {defineStore} from 'pinia'
import request from "@/util/request";

export const useUserStore = defineStore('user', () => {
    const isAdmin = ref(false)
    const userName = ref('')

    function getUserData() {
        request({
            url: '/current-user',
            method: 'get'
        }).then(res => {
            // console.log(res);
            let ud = res.data.data;
            isAdmin.value = ud.isAdmin;
            userName.value = ud.name;
        })
    }

    return {isAdmin, userName, getUserData}
})

