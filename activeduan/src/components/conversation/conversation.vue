<template>
    <van-nav-bar title="消息"/>
    <van-swipe-cell style="margin-bottom: .625rem;margin-left: .625rem; padding-top: .5rem;" v-for="i in conversations" :key="i">
        <van-badge :content="i" style="width: 95%; height: 95%;" @click="activeF(i)">
            <van-image
                round
                width="4rem"
                height="4rem"
                fit="cover"
                position="center"
                src="../../../public/kk.jpg"
                style="vertical-align: middle;"
                />
                <h4 style="display:inline-block;width: 40%;padding-right: 60%;color: black;margin-left: .625rem;">{{ i }}</h4>
        </van-badge>
        <template #right>
                <van-button square type="danger" style="width:4rem; height: 4rem;" text="删除" @click="friendsDel()"/>
                <van-button square type="primary" style="width:4rem; height: 4rem;" text="未读" @click="friendsNone('未读')"/>
        </template>
  </van-swipe-cell>
</template>

<script setup>
import axios from 'axios'
import { useRouter } from "vue-router"
import { onMounted,onUpdated,ref } from 'vue'
    let conversations = ref([1,2,3,4,5,6,7,8,9])  //对话列表
    let router = useRouter()
    const friendsDel = () => {
        //清除本地储存
    }
    //vuex 接收数据，之后传入对话框的组件中
    const activeF = (value) => {
        console.log(value);
        sessionStorage.setItem("toname",value)
        router.push( '/talk' )
    }
    //获取对话列表
    const initializing = () => {
        conversations = sessionStorage.getItem("conversations")
    }

    //  //向后端索取最近几个对话朋友列表吗，并把后端传回的数据存入本地储存 #代表后端的访问路径
    // onMounted(() => {
    //     axios.get('#').then(
    //     response => {
    //     if (response.status==200){
    //             console.log(response)
    //             console.log('修改成功！')
    //             sessionStorage.setItem("conversations",response.data)
    //             }else{
    //             console.log("请求有点问题",response)
    //             }
    //         },
    //         error => {
    //             console.log('提交数据失败，错误是：',error);
    //         }
    //         )
    //     initializing()
    // })
    onUpdated(() => {
        console.log("数据改变了");
    })
</script>

<style>

</style>