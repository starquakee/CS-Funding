import {createApp} from 'vue'
import {createPinia, storeToRefs} from 'pinia'

import App from './App.vue'
import router from './router'
import VueCookies from 'vue-cookies'

import './assets/main.css'
import {useTokenStore} from "@/stores/token";
import {useUserStore} from "@/stores/user";

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(VueCookies)
app.mount('#app')

const userStore = useUserStore()
const store = useTokenStore()
const {isAdmin, userName} = storeToRefs(userStore);
router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth){
        if (store.token){
            next()
        } else {
            next({
                path: '/login',
                query: { redirect: to.fullPath }
            });
        }
        if (!userName.value){
            userStore.getUserData();
        }
    }
    next()
})

export {app};

