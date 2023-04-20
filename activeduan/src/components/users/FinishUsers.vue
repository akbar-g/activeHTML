<template>
<van-nav-bar
  title="完善个人信息"
  left-text="返回"
  left-arrow
  @click-left="onClickLeft"
/>
  <van-form  @submit="onSubmit1">
  <van-cell-group inset>
    <!-- 通过 validator 进行函数校验 -->
    <van-field
      size="large"
      v-model="username"
      name="username"
      placeholder="昵称"
      :rules="[{  required: true, message: '请输入正确的昵称！' }]"
    />

      <van-field
      size="large"
      v-model="userAccount"
      name="userAccount"
      placeholder="用户名"
      readonly
     />

    <van-field name="avatarUrl" label="头像上传">
      <template #input>
        <van-uploader v-model="avatarUrl" />
      </template>
    </van-field>


    <van-field
      size="large"
    v-model="gender"
      name="gender"
      placeholder="性别"
      :rules="[{ validator: genderjy, message: '请输入正确的性别！' }]"
    />

    <van-field
      size="large"
      v-model="phone"
      name="phone"
      placeholder="电话号码"
      :rules="[{ validator: phonejy, message: '请输入正确的电话号码！' }]"
    />

    <van-field
      size="large"
    v-model="email"
      name="email"
      placeholder="邮箱"
      :rules="[{ validator:emailjy, message: '请输入正确的邮箱格式！' }]"
    />

    <van-field
      size="large"
      v-model="tages"
      name="tages"
      placeholder="标签"
      :rules="[{ required: true, message: '请输入正确的标签！' }]"
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


export default {
  setup() {
    const phone = ref('');
    const username = ref('');
    const userAccount = ref(JSON.parse(sessionStorage.getItem('users')).username);
    const gender = ref('');
    const email = ref('');
    const tages = ref('');


    const avatarUrl = ref(
      [{ url: 'https://fastly.jsdelivr.net/npm/@vant/assets/leaf.jpeg' },]
    );

    // 电话号码校验函数返回 true 表示校验通过，false 表示不通过

    const genderjy = (val) =>/^(男|女){1}$/.test(val);

    const phonejy = (val) =>/^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$/.test(val);
    
    const emailjy = (val) =>/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/.test(val);

    
    const onFailed = (errorInfo) => {
      console.log('failed', errorInfo);
    };

    const onSubmit1 = (values) => {
      if (values.gender == '男') {
        values.gender = 0
      }else{
        values.gender = 1
      }
      values.avatarUrl = values.avatarUrl[0].url
      console.log(values);
      axios.post('http://localhost:8082/api/user/update',values).then(
          response =>{
            if (response.status==200){
              console.log(response);
              alert('修改成功！')
              router.push( '/users' )
            }else{
              alert("写入数据失败")
            }
          },
          error => {
            console.log('提交数据失败，错误是：',error);
          }
        )
    };
    const onClickLeft = () => history.back();
    return {
      avatarUrl,
      phone,
      email,
      gender,
      tages,
      username,
      userAccount,
      onFailed,
      genderjy,
      phonejy,
      emailjy,
      onSubmit1,
      onClickLeft,
    };
  },
};

</script>

<style>

</style>