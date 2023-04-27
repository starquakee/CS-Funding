import {createApp} from 'vue'
import {createPinia, storeToRefs} from 'pinia'

import App from './App.vue'
import router from './router'
import VueCookies from 'vue-cookies'

import './assets/main.css'
import {useTokenStore} from "@/stores/token";

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(VueCookies)
app.mount('#app')

const store = useTokenStore()
router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth){
        if (store.token){
            next()
        } else {
            next({
                path: '/login',
                query: { redirect: to.fullPath }
            })
        }
    }
    next()
})

export {app};

