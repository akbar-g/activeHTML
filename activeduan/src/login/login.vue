<template>

	<div class="loding" v-show="false">
		<section>
		<div class="loader loader-17">
		  <div class="css-square square1"></div>
		  <div class="css-square square2"></div>
		  <div class="css-square square3"></div>
		  <div class="css-square square4"></div>
		  <div class="css-square square5"></div>
		  <div class="css-square square6"></div>
		  <div class="css-square square7"></div>
		  <div class="css-square square8"></div>
		</div>
	  </section>
	</div>
  <div class="login">
	<h2>用户登录</h2>
	<hr style="border: 0.125rem black solid;">
	<van-form @submit="onSubmit">
  <van-cell-group inset>
    <van-field
	  style="border-bottom: 0.0625rem black solid;"
      v-model="username"
      name="userAccount"
      label="用户名"
      placeholder="用户名"
      :rules="[{ required: true, message: '请填写用户名' }]"
    />
    <van-field
	  style="border-bottom: 0.0625rem black solid;"
      v-model="password"
      type="password"
      name="userPassword"
      label="密码"
      placeholder="密码"
      :rules="[{ required: true, message: '请填写密码' }]"
    />
  </van-cell-group>
  <div style="margin: 16px;">
    <van-button round block color="black" plain native-type="submit">
      提交
    </van-button>
  </div>
	</van-form>
	<router-link to="/zhuce" ><van-tag plain color="black">没有账号，注册一个</van-tag></router-link>
  </div>

</template>

<script>
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from "vue-router"  // 引入userRouter

export default {
  setup() {
    const username = ref('');
    const password = ref('');
    const router = useRouter();
    const onSubmit = (values) => {
      axios.post('http://localhost:8082/api/user/login',values).then(
          response =>{
            if (response.status==200){
              sessionStorage.setItem('login',response.data.message);
              sessionStorage.setItem('users',JSON.stringify({'username':response.data.data.userAccount,'userid':response.data.data.id}));
              router.push( '/zhuye' );
            }else{
              alert("登录失败")
            }
          },
          error => {
            console.log('提交失败，错误是：',error);
          }
        )
    };
    return {
      username,
      password,
      onSubmit,
    };
  },
};

</script>

<style>
h2{
	color: rgba(49, 50, 50, 0.612);
	text-align: center;
}
.login{
	width: 80%;
	height: auto;
	border: 0.125rem black solid;
	margin: auto;
	text-align: center;
	padding-bottom: .625rem;
}
*,
*:before,
*:after {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}
section {
    width: 8.125rem;
	height: auto;
    display: inline-block;
    text-align: center;
    min-height: 50px;
    vertical-align: top;
    margin: 1%;
    background: #2d2d2e;
    border-radius: 5px;
    box-shadow: 0px 0px 30px 1px #103136 inset;
	opacity: 0.6;
}

@media only screen and (max-width: 600px) {
section {
min-width: 1.25rem;
}
}

.loader {
    position: relative;
    width: 1.25rem;
    height: 1.25rem;
    border-radius: 50%;
    margin: .625rem;
    display: inline-block;
    vertical-align: middle;
}

.loader-star {
    position: absolute;
    top: calc(50% - 12px);
}
/*LOADER-17*/

.loader-17 .css-square { 
  position: absolute;
  top: 50%;
  width: 25px; height: 7px;
background: white;
box-shadow: 2px 2px 3px 0px black;
}

.loader-17 .square8 {
    left: 1.875rem;
    -webkit-animation: dominos 1s 0.125s ease infinite;
    animation: dominos 1s 0.9s ease infinite;
}
.loader-17 .square1 {
    left: 1.25rem;
    -webkit-animation: dominos 1s 0.3s ease infinite;
    animation: dominos 1s 0.125s ease infinite;
}

.loader-17 .square2 {
    left: .625rem;
    -webkit-animation: dominos 1s 0.425s ease infinite;
    animation: dominos 1s 0.3s ease infinite;
}

.loader-17 .square3 {
    left: 0rem;
    -webkit-animation: dominos 1s 0.540s ease infinite;
    animation: dominos 1s 0.425s ease infinite;
}

.loader-17 .square4 {
    left: -0.625rem;
    -webkit-animation: dominos 1s 0.665s ease infinite;
    animation: dominos 1s 0.540s ease infinite;
}

.loader-17 .square5 {
    left: -1.25rem;
    -webkit-animation: dominos 1s 0.79s ease infinite;
    animation: dominos 1s 0.665s ease infinite;
}

.loader-17 .square6 {
    left: -1.875rem;
    -webkit-animation: dominos 1s 0.9s ease infinite;
    animation: dominos 1s 0.79s ease infinite;
}

.loader-17 .square7 {
    left: -2.5rem;
    -webkit-animation: dominos 1s 1.1s ease infinite;
    animation: dominos 1s 0.9s ease infinite;
}
/* ----------------     KEYFRAMES    ----------------- */

@-webkit-keyframes dominos {
50% { opacity: 0.7; }
75% { -webkit-transform: rotate(90deg); transform: rotate(90deg); }
80% { opacity: 1; } 
}


@keyframes dominos {
50% { opacity: 0.7; }
75% { -webkit-transform: rotate(90deg); transform: rotate(90deg); }
80% { opacity: 1; } 
}
</style>