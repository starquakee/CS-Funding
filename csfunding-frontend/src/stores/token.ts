import {ref, computed} from 'vue'
import {defineStore} from 'pinia'
// @ts-ignore
import {useCookies} from "vue3-cookies";

const {cookies} = useCookies()

export const useTokenStore = defineStore('token', () => {
    let ck = cookies.get('user-session')
    const token = ref('')
    if (ck) token.value = ck

    function setToken(tk: string) {
        token.value = tk
        cookies.set('user-session', tk)
    }

    function clearToken() {
        token.value = ''
        cookies.remove('user-session')
    }

    return {token, setToken, clearToken}
})
