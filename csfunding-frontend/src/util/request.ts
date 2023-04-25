import axios from 'axios'
import router from "@/router";
import {useTokenStore} from "@/stores/token";

const service = axios.create({
    baseURL: "http://localhost:8081/"
})

service.interceptors.request.use(
    config => {
        const store = useTokenStore();
        if (store.token) {
            config.headers['authorization'] = store.token
        }
        return config
    },
    error => {
        // do something with request error
        console.log(error) // for debug
        return Promise.reject(error)
    }
)

service.interceptors.response.use(response => {
        let res = response.data;
        let code = res.code;

        if (code === 200) {
            return response
        }
        if (code === 401){
            const store = useTokenStore();
            store.clearToken()
            router.push({
                path: '/login',
                query: {redirect: router.currentRoute.value.fullPath}
            })
        }
        return Promise.reject(response.data.msg)
    },
    error => {
        const store = useTokenStore();
        console.log(error)
        if (error.response.data) {
            error.message = error.response.data.msg
        }

        if (error.response.status === 401) {
            store.clearToken()
            router.push({
                path: '/login',
                query: {redirect: router.currentRoute.value.fullPath}
            })
        }
        return Promise.reject(error)
    }
)

export default service