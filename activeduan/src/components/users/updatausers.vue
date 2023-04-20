<template>
  <van-nav-bar
  title="修改密码"
  left-text="返回"
  left-arrow
  @click-left="onClickLeft"
/>
  <van-form @submit="onSubmit">
    <van-cell-group inset>
    <!-- 通过 validator 进行函数校验 -->
    <van-field
      size="large"
      v-model="userPassword"
      name="userPassword"
      type="password"
      placeholder="密码(至少包含一个小写字母和一个数字的六位密码)"
      :rules="[{ validator: userPasswordjy, message: '请输入正确的密码格式！' }]"
    />
    <van-field
      size="large"
    v-model="checkPassword"
      name="checkPassword"
      type="password"
      placeholder="密码(至少包含一个小写字母和一个数字的六位密码)"
      :rules="[{ validator: userPasswordjy, message: '请输入正确的密码格式！' }]"
    />
    </van-cell-group>
    <div style="margin: 16px;">
    <van-button round block type="primary" native-type="submit">
      提交
    </van-button>
  </div>
</van-form>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from "vue-router"  // 引入userRouter

export default {
  setup() {
    const userPasswordjy = (val) =>/^.*(?=.{6,})(?=.*\d)(?=.*[a-z]).*$/.test(val);
    const checkPassword = ref('');
    const userPassword = ref('');
    const router = useRouter();
    const onSubmit = (values) => {
      axios.post('http://localhost:8082/api/user/updateUserPasswordById',values).then(
          response =>{
            if (response.status==200){
              console.log(response);
              router.push( '/login' )
            }else{
              alert("修改密码失败")
            }
          },
          error => {
            console.log('提交失败，错误是：',error);
          }
        )
    console.log('submit', values);
    };

    const onClickLeft = () => history.back();

    return {
        userPassword,
        checkPassword,
        onSubmit,
        userPasswordjy,
        onClickLeft,
    };
  },
};

</script>

<style>

</style>