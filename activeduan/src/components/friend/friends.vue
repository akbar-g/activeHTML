<template>
    <van-nav-bar title="我的朋友们"/>
<van-index-bar :index-list="indexList">
  <van-index-anchor index="A" />
  <van-swipe-cell style="margin-bottom: .625rem;margin-left: .625rem;" v-for="i in frinds" :key="i">
    <van-image
    round
    width="2.5rem"
    height="2.5rem"
    fit="cover"
    position="center"
    src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
    style="vertical-align: middle;"
    />
    <h4 style="display:inline-block;width: 80%;padding-right: 60%;color: black;margin-left: .625rem;">{{ i }}</h4>
    <template #right>
    <van-button square type="danger" text="删除" @click="friendsDel('删除')"/>
    <van-button square type="primary" text="拉黑" @click="friendsNone('拉黑')"/>
  </template>
  </van-swipe-cell>
  <van-swipe-cell style="margin-bottom: .625rem;margin-left: .625rem;">
    <van-image
    round
    width="2.5rem"
    height="2.5rem"
    fit="cover"
    position="center"
    src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
    style="vertical-align: middle;"
    />
    <h4 style="display:inline-block;width: 80%;padding-right: 60%;color: black;margin-left: .625rem;">A朋友</h4>
    <template #right>
    <van-button square type="danger" text="删除" @click="friendsDel('删除')"/>
    <van-button square type="primary" text="拉黑" @click="friendsNone('拉黑')"/>
  </template>
  </van-swipe-cell>
  <van-swipe-cell style="margin-bottom: .625rem;margin-left: .625rem;">
    <van-image
    round
    width="2.5rem"
    height="2.5rem"
    fit="cover"
    position="center"
    src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
    style="vertical-align: middle;"
    />
    <h4 style="display:inline-block;width: 80%;padding-right: 60%;color: black;margin-left: .625rem;">A朋友</h4>
    <template #right>
    <van-button square type="danger" text="删除" @click="friendsDel('删除')"/>
    <van-button square type="primary" text="拉黑" @click="friendsNone('拉黑')"/>
  </template>
  </van-swipe-cell>

  <van-index-anchor index="B" />
  <van-swipe-cell style="margin-bottom: .625rem;margin-left: .625rem;">
    <van-image
    round
    width="2.5rem"
    height="2.5rem"
    fit="cover"
    position="center"
    src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
    style="vertical-align: middle;"
    />
    <h4 style="display:inline-block;width: 80%;padding-right: 60%;color: black;margin-left: .625rem;">A朋友</h4>
    <template #right>
    <van-button square type="danger" text="删除" @click="friendsDel('删除')"/>
    <van-button square type="primary" text="拉黑" @click="friendsNone('拉黑')"/>
  </template>
  </van-swipe-cell>
  <van-swipe-cell style="margin-bottom: .625rem;margin-left: .625rem;">
    <van-image
    round
    width="2.5rem"
    height="2.5rem"
    fit="cover"
    position="center"
    src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
    style="vertical-align: middle;"
    />
    <h4 style="display:inline-block;width: 80%;padding-right: 60%;color: black;margin-left: .625rem;">A朋友</h4>
    <template #right>
    <van-button square type="danger" text="删除" @click="friendsDel('删除')"/>
    <van-button square type="primary" text="拉黑" @click="friendsNone('拉黑')"/>
  </template>
  </van-swipe-cell>
  <van-swipe-cell style="margin-bottom: .625rem;margin-left: .625rem;">
    <van-image
    round
    width="2.5rem"
    height="2.5rem"
    fit="cover"
    position="center"
    src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
    style="vertical-align: middle;"
    />
    <h4 style="display:inline-block;width: 80%;padding-right: 60%;color: black;margin-left: .625rem;">A朋友</h4>
    <template #right>
    <van-button square type="danger" text="删除" @click="friendsDel('删除')"/>
    <van-button square type="primary" text="拉黑" @click="friendsNone('拉黑')"/>
  </template>
  </van-swipe-cell>

</van-index-bar>

</template>

<script setup>
import axios from 'axios'
import cnchar from 'cnchar'
import { ref,onMounted } from 'vue'

const friends = ref('')

const friendsLife = (value) => {
    console.log(value);
      axios.get('#').then(
    response => {
      if (response.status==200){
              console.log(response);
              alert('修改成功！')
              friends = response.data
              router.push( '/users' )
            }else{
              alert("请求报错")
            }
          },
          error => {
            console.log('提交数据失败，错误是：',error);
          }
        ),
  error => {
    console.log('提交失败，错误是：',error);
  }
}
const friendsDel = (value) => {
    console.log(value);
      axios.get('').then(
    response => {

    }
  ),
  error => {
    console.log('提交失败，错误是：',error);
  }
}
const friendsNone= (value) => {
    console.log(value);
      axios.get('').then(
    response => {

    }
  ),
  error => {
    console.log('提交失败，错误是：',error);
  }
}
onMounted(() => {
  axios.get('').then(
    response => {

    }
  ),
  error => {
    console.log('提交失败，错误是：',error);
  }
})
let indexList =  ref([])  //字母列表
let data = ref(['是','的','我','啊','吧','从','额','给'])  //接收的后台数据
let x =ref([]) 
let y = ref(0)
let o = ref(0)

//x[y]同一种字母的有多少种

// const list = data.concat([]).sort((a,b)=>a.localeCompare(b))  //遍历list
// for (i=0;i<= list.length;i++){
//     for (j=0;j<= indexList.length;j++){
//         if(indexList[j] != cnchar.spell(list[i])[0]){
//             indexList.push(cnchar.spell(list[i])[0])
//             x[y][o].push(cnchar.spell(list[i]))
//             y += 1
//             o += 1
//         }else{
//             x[y][o].push(cnchar.spell(list[i]))
//             o += 1
//         }
//     }
// }
</script>

<style>

</style>