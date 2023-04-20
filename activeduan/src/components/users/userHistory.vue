<template>
  <van-nav-bar
  title="历史记录"
  left-text="返回"
  left-arrow
  @click-left="onClickLeft"
/>
<van-card
  desc="描述信息"
  title="标题"
  thumb="https://fastly.jsdelivr.net/npm/@vant/assets/apple-1.jpeg"
>
  <template #tags>
    <van-tag plain type="primary">标签</van-tag>
    <van-tag plain type="primary">标签</van-tag>
  </template>
  <template #bottom>
    <h5>作者</h5>
  </template>
</van-card>

</template>

<script setup>
// import axios from 'axios';
// import { onMounted } from 'vue';

// const historys = JSON.parse(sessionStorage.getItem('author'))


const onClickLeft = () => history.back();
// onMounted(() => {
//     axios.post('#').then(
//           response =>{
            
//           },
//           error => {
//             console.log('提交数据失败，错误是：',error);
//           }
//         )
    
//     })

</script>

<style>

</style>