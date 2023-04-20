<template>
    <van-image
  round
  width="10rem"
  height="10rem"
  fit="cover"
  position="center"
  src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
  style="display: block; margin: 1.875rem auto;"
/>
<van-divider style="font-size: 1.25rem; font-weight: 600;">{{ userName }}</van-divider>
<van-cell-group inset>
    <van-cell title="完善个人信息" is-link to="/users/finishusers" size="large" />
    <van-cell title="修改密码" is-link to="/users/updata" size="large" />
    <van-cell title="历史记录" is-link to="/users/history" size="large" />
    <van-cell title="用户注销" size="large" @click="showPopup" style="color:#D2691E;"/>  
    <van-button round type="success" color="red" @click="exituser" style=" color:white; font-size: 15px; font-weight: 600;width: 70%;margin: 15%;">账号退出</van-button>


    <!-- 圆角弹窗（居中） -->
    <van-popup v-model:show="showCenter" round :style="{ padding: '64px' }">
      <h3 style="color:red;">您确定要注销账号{{ userName }}吗?</h3>
      <van-button round block color="black" plain style="margin-top: 1.25rem;" @click="onDel">
        <van-icon name="bulb-o" />
          提交
      </van-button>
    </van-popup>
</van-cell-group>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from "vue-router"  // 引入userRouter

export default {
  setup() {
    const showCenter = ref(false);
    let userName = JSON.parse(sessionStorage.getItem('users')).username
    const router = useRouter()
    const onDel = () => {
      axios.post('http://localhost:8082/api/user/delete',String( JSON.parse(sessionStorage.getItem('users')).userid )).then(
          response =>{
            if (response.status==200){
              console.log(response);
              alert('注销成功!')
              router.push( '/zhuye' )
            }else{
              alert("注销失败，请重试。")
            }
          },
          error => {
            console.log('提交失败，错误是：',error);
          }
        )
    }
    const showPopup = () => {
      showCenter.value = true;
    };
    const exituser = () => {
      axios.get('http://localhost:8082/api/user/logout').then(
          response =>{
            if (response.status==200){
              console.log(response);
              alert('退出成功!')
              router.push( '/zhuye' )
            }else{
              alert("注销失败，请重试。")
            }
          },
          error => {
            console.log('提交失败，错误是：',error);
          }
        )
    };
    return {
      showCenter,
      userName,
      exituser,
      showPopup,
      onDel,
    };
  },
};

</script>

<style>

</style>