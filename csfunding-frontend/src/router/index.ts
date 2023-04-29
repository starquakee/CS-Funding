import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import ApplyView from "@/views/ApplyView.vue";
import FundView from '@/views/FundView.vue';
import CheckView from '@/views/CheckView.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/home',
            name: 'home',
            component: HomeView,
            meta: {
                requireAuth: true
            },
        },
        {
            path: '/login',
            name: 'login',
            component: LoginView
        },
        {
            path: '/',
            // component: LoginView,
            redirect: '/login',
        },
        {
            path: '/apply',
            name: 'apply',
            component: ApplyView
        },
        {
            path: '/fund',
            name: 'fund',
            component: FundView
        },
        {
            path: '/check',
            name: 'check',
            component: CheckView
        }

    ]
})

// const store = useTokenStore()
// router.beforeEach((to, from, next) => {
//         //路由需要认证
//         // let {token} = storeToRefs(store)
//         console.log(store.token)
//         if (to.meta.requireAuth) {
//             //判断store里是否有token
//             if (store.token) {
//                 console.log(store.token)
//                 next()
//             } else {
//                 next({
//                     path: 'login',
//                     query: {redirect: to.fullPath}
//                 })
//             }
//         } else {
//             next()
//         }
//         // next()
//     }
// )

function requireAuth(to: any, from: any, next: () => void) {

    next()
}

export default router
