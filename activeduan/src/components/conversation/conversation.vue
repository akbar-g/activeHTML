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
                src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
                style="vertical-align: middle;"
                />
                <h4 style="display:inline-block;width: 40%;padding-right: 60%;color: black;margin-left: .625rem;">{{ i }}</h4>
                <template #right>
                <van-button square type="danger" style="width:4rem; height: 4rem;" text="删除" @click="friendsDel()"/>
                <van-button square type="primary" style="width:4rem; height: 4rem;" text="未读" @click="friendsNone('未读')"/>
            </template>
        </van-badge>
  </van-swipe-cell>
</template>

<script setup>
import axios from 'axios'
import { useRouter } from "vue-router"
import { onMounted,onUpdated,ref } from 'vue'
    let conversations = ref([1,2,3,4,5,6,7,8,9])
    let router = useRouter()
    const friendsDel = () => {
        //清除本地储存，并把后端数据库记载清除 #代表后端的访问路径
    axios.get('#').then(
        response => {
        if (response.status==200){
                console.log(response)
                console.log('修改成功！')
                }else{
                console.log("请求有点问题",response)
                }
            },
            error => {
                console.log('提交数据失败，错误是：',error);
            }
            )}
            //vuex 接收数据，之后传入对话框的组件中
    const activeF = (value) => {
        console.log(value);
        localStorage.setItem("toname",value)
        router.push( '/talk' )
    }

    const initializing = () => {
        conversations = localStorage.getItem("conversations")
    }

            //该组件挂载之后调用，并把后端传回的数据存入本地储存 #代表后端的访问路径
    onMounted(() => {
        axios.get('#').then(
        response => {
        if (response.status==200){
                console.log(response)
                console.log('修改成功！')
                localStorage.setItem("conversations",response.data)
                }else{
                console.log("请求有点问题",response)
                }
            },
            error => {
                console.log('提交数据失败，错误是：',error);
            }
            )
        initializing()
    })
    onUpdated(() => {
        console.log("数据改变了");
    })
</script>

<style>

</style>