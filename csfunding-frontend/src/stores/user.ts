import {ref, computed, reactive} from 'vue'
import {defineStore} from 'pinia'

export const useUserStore = defineStore('user', () => {
    const isAdmin = ref(false)
    const userName = ref('')

    return {isAdmin, userName}
})
