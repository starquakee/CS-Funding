import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import ApplyView from "@/views/ApplyView.vue";
import FundView from '@/views/FundView.vue';
import DefaultRedirect from "@/views/DefaultRedirect.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/home',
            name: 'home',
            component: HomeView,
            meta: {
                requireAuth: true
            }
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
        {   path: '/fund',
            name: 'fund',
            component: FundView

        }
    ]
})

export default router
