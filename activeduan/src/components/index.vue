<script>


</script>

<template>
    <div class="body">
        <div class="neirong">
            <router-view></router-view>
        </div>
    </div>
  <van-tabbar route>
    <van-tabbar-item replace to="/zhuye" icon="home-o">校园</van-tabbar-item>
    <van-tabbar-item replace to="/conversation" icon="chat-o">消息</van-tabbar-item>
    <van-tabbar-item replace to="/friend" icon="friends-o">朋友</van-tabbar-item>
    <van-tabbar-item replace to="/users" icon="setting-o">我的设置</van-tabbar-item>
  </van-tabbar>
  
</template>

<style scoped>
.body{
    width: 100%;
    height: 100%;
}
.neirong{
    height: 46.875rem;
    width: 100%;
}
</style>
