<template>
  <div class="login-wrap">
    <el-form class="login-container">
      <h1 class="title">用户登陆</h1>
      <el-form-item >
        <el-input type="text" v-model="form.name"  placeholder="用户账号" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item >
        <el-input type="password" v-model="form.key" placeholder="用户密码" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="doLogin" style="width: 100%;">登陆</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<script>
import axios from 'axios'
export default{
  name:'Login',
  data:function(){
    return {
      form:{}
    }
  },
  methods:{
    doLogin:function(){
      let url="http://localhost:8081/api/login";
      axios.post(url,this.form).then(resp=> {

        this.code = resp.data.code
        this.person = resp.data.data.name
        this.phoneNumber = resp.data.data.phoneNum
        this.isAdmin = resp.data.data.isAdmin

        console.log(this.code, this.person)

        if (this.code === 200) {
          this.$router.push({path: '/home', query: {
              username: this.form.name,
              name: this.person,
              phoneNumber: this.phoneNumber,
              isAdmin: this.isAdmin
            }});
        }
      })



    }
  }
}
</script>


<style scoped>

.login-wrap {
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  padding-top: 10%;
  background-repeat: no-repeat;
  background-position: center right;
  background-size: 100%;
}

.login-container {
  border-radius: 10px;
  margin: 0 auto;
  width: 350px;
  padding: 30px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  text-align: left;
  box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
}

.title {
  margin: 0 auto 40px auto;
  text-align: center;
  color: #505458;
}


</style>