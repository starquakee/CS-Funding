import axios from 'axios'
import router from "@/router";
import {useTokenStore} from "@/stores/token";

const service = axios.create({
    baseURL: "http://localhost:8081/"
})

const store = useTokenStore();
const token = store.token;

service.interceptors.request.use(
    config => {
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

axios.interceptors.response.use(response => {
        let res = response.data;
        console.log(res)
        if (res.code === 200) {
            return response
        } else {
            return Promise.reject(response.data.msg)
        }
    },
    error => {
        console.log(error)
        if (error.response.data) {
            error.message = error.response.data.msg
        }

        if (error.response.status === 401) {
            router.push("/login")
        }
        return Promise.reject(error)
    }
)

export default service