<template>
  <van-nav-bar title="辽宁科技学院">
    <template #right>
      <van-icon name="search" size="18"  @click="showsearchs = !showsearchs"/>
    </template>
  </van-nav-bar>
  <van-search
    v-model="IMtext"
    input-align="center"
    shape="round"
    background="white"
    placeholder="请输入搜索关键词"
    @blur="showsearchs = !showsearchs"
    @search="searchTag"
    v-show="showsearchs"
  />
<van-notice-bar
  left-icon="volume-o"
  text="无论我们能活多久，我们能够享受的只有无法分割的此刻，此外别无其他。"
/>
<!-- 轮播图 -->
<van-swipe :autoplay="3000" style="width: 100%; height: 18.75rem;" lazy-render  v-show="!showsearchs">
  <van-swipe-item v-for="image in images" :key="image">
    <img style="width: 100%; height: 18.75rem;" :src="image" />
  </van-swipe-item>
</van-swipe>
<!-- 学校应用分栏 -->
<van-sticky :offset-top="0" v-show="!showsearchs">
    <van-grid>
  <van-grid-item icon="photo-o" text="最新通知" />
  <van-grid-item icon="photo-o" text="校园活动" />
  <van-grid-item icon="photo-o" text="校园表白墙" />
  <van-grid-item icon="photo-o" text="校园伙伴匹配" />
</van-grid>
</van-sticky>
<!-- 学生动态分栏 -->
<van-pull-refresh v-model="refreshing" @refresh="onRefresh">
  <van-grid direction="horizontal" :column-num="3">
  <van-grid-item text="最新" @click="changes('zx')"/>
  <van-grid-item text="最热" @click="changes('zr')"/>
  <van-grid-item text="推荐" @click="changes('tj')"/>
  </van-grid>

  <van-list
    v-model:loading="loading"
    :finished="finished"
    finished-text="没有更多了"
    @load="onLoad"
  >
  <div v-masonry  transition-duration="0.3s" i tem-selector=".item">
    <div v-masonry-tile class="item" v-for="item in list" :key="item">
      <img src="../../public/kk.jpg">
      <div>
        <span>
          123456789
        </span>
      </div>
    </div>
</div>
  </van-list>
</van-pull-refresh>

<div style="height: 50px;"></div>
<!-- <van-back-top /> -->
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';
import { onMounted } from 'vue';

export default {
  setup() {
    const images = [
      'https://fastly.jsdelivr.net/npm/@vant/assets/apple-1.jpeg',
      'https://fastly.jsdelivr.net/npm/@vant/assets/apple-2.jpeg',
      'https://fastly.jsdelivr.net/npm/@vant/assets/apple-1.jpeg',
      'https://fastly.jsdelivr.net/npm/@vant/assets/apple-2.jpeg',
    ];
    let list = ref([]);
    const loading = ref(false);
    const finished = ref(false);
    const refreshing = ref(false);
    const IMtext = ref('');
    let showsearchs = ref(false);
//懒加载帖子
    const onLoad = () => {
      setTimeout(() => {
        if (refreshing.value) {
          list.value = [];
          refreshing.value = false;
        }

        for (let i = 0; i < 3; i++) {
          list.value.push(list.value.length + 1);
        }
        loading.value = false;

        if (list.value.length >= 18) {
          finished.value = true;
        }
      }, 1000);
    };
    const onRefresh = () => {
      // 清空列表数据
      finished.value = false;

      // 重新加载数据
      // 将 loading 设置为 true，表示处于加载状态
      loading.value = true;
      onLoad();
    };
// 按照分类进行后台查询
    const changes = (value) => {
      axios.post('#',value).then(
          response =>{
            if (response.status==200){
              console.log(response);
              alert('修改成功！')
              lsit = response.data
              router.push( '/users' )
            }else{
              alert("请求报错")
            }
          },
          error => {
            console.log('提交数据失败，错误是：',error);
          }
        )
    };
// 搜索栏搜索查询
    const searchTag = (value) => {
      axios.post('#',value).then(
          response =>{
            if (response.status==200){
              console.log(response);
              alert('修改成功！')
              lsit = response.data
              router.push( '/users' )
            }else{
              alert("请求报错")
            }
          },
          error => {
            console.log('提交数据失败，错误是：',error);
          }
        )
    }
    onMounted(() => {
      // 调用cookie查询方法
      console.log("cookie是否存在");
      //查询用户是否登录(获取当前登录用户)
      axios.get("http://localhost:8082/api/user/get/login").then(response => {
        console.log(response);
      },
      error => {
        console.log('请求错误',error);
      })
      //获取帖子
      
    })
    
    return {
      list,
      images,
      onLoad,
      loading,
      finished,
      onRefresh,
      refreshing,
      changes,
      IMtext,
      showsearchs,
      searchTag,
      onMounted,
    };
  },
};

</script>

<style scoped>
.item {
  width: 46%;
  margin-bottom: 10px;
  margin: .3125rem .3125rem;
  display: inline-block;
  padding-top: .625rem;
  border: 1px rgb(111, 111, 111) solid;
}
 
.item img {
   width: 100%;
   height: 100%;
}
</style>