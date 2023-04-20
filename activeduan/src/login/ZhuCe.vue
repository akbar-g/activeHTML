<template>
<div class="box">
	<h2>用户注册</h2>
    <van-form @submit="onSubmit">
    <van-cell-group inset>
  <!-- 输入任意文本 -->
  <van-field 
    v-model="userAccount"
    name="userAccount"
    label="用户名"
    placeholder="请输入用户名"
    :rules="[{ required: true, message: '请填写用户名' }]" />
  <!-- 输入手机号，调起手机号键盘 -->
  <van-field v-model="phone" type="tel"
    label="手机号"
    name="phone"
    placeholder="请输入手机号"
    :rules="[{ validator, message: '请填写用户名' }]" />
  <!-- 输入密码 -->
  <van-field v-model="userPassword" name="userPassword" type="password" label="密码" :rules="[{  validator: userPasswordjy, message: '请输入正确的密码格式！(至少包含一个小写字母和一个数字的六位密码)' }]"/>
  <van-field v-model="checkPassword" name="checkPassword" type="password" label="再次密码" :rules="[{  validator: userPasswordjy, message: '请输入正确的密码格式！(至少包含一个小写字母和一个数字的六位密码)' }]"/>
    </van-cell-group>
    <div style="margin: 16px;">
    <van-button round block color="black" plain native-type="submit">
      提交
    </van-button>
  </div>
</van-form>
</div>

</template>

<script>
import { ref } from 'vue';
import { showNotify } from 'vant';
import axios from 'axios';
import { useRouter } from "vue-router"  // 引入userRouter

export default {
  setup() {
    const phone = ref('');
    const userAccount = ref('');
    const checkPassword = ref('');
    const userPassword = ref('');
    const router = useRouter();
    const validator = (val) => /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/.test(val);
    const userPasswordjy = (val) =>/^.*(?=.{6,})(?=.*\d)(?=.*[a-z]).*$/.test(val);
    const onSubmit = (values) => {
      if (values.password == values.password1) {
        console.log(values);
        axios.post('http://localhost:8082/api/user/register',values).then(
          response =>{
            console.log(response);
            if (response.status==200){
              console.log("去登陆！！！");
              router.push('/login')
            }else{
              alert("注册失败")
            }
          },
          error => {
            console.log('提交失败，错误是：',error);
          }
        )
      } else {
        showNotify({ type: 'danger', message: '两次密码输入不同，请重新输入' });
      }
     };
    return { phone, userAccount, userPassword, 
      validator,userPasswordjy,checkPassword,onSubmit};
  },
};

</script>

<style>
h2{
	color: rgba(49, 50, 50, 0.612);
	text-align: center;
}
.box{
    width: 80%;
	height: auto;
	border: 0.125rem black solid;
	margin: auto;
	text-align: center;
	padding-bottom: .625rem;
}
</style>