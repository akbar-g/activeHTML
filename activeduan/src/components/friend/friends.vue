<template>
    <van-nav-bar title="我的朋友们"/>
<van-index-bar>
  <van-index-anchor index="A" />
  <van-swipe-cell style="margin-bottom: .625rem;margin-left: .625rem;" v-for="i in frinds" :key="i">
    <van-image
    round
    width="2.5rem"
    height="2.5rem"
    fit="cover"
    position="center"
    src="../../../public/kk.jpg"
    style="vertical-align: middle;"
    />
    <h4 style="display:inline-block;width: 80%;padding-right: 60%;color: black;margin-left: .625rem;">{{ i }}</h4>
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
import { ref,onBeforeMount } from 'vue'

const frinds = ref([1,2,3,4])

const friendsLife = () => {
      axios.get('http://localhost:8082/api/friend/friendlist').then(
    response => {
      if (response.status==200){
              console.log(response)
              frinds = response.data
              alert('修改成功！')
            }else{
              alert("请求报错")
            }
          },
          error => {
            console.log('提交数据失败，错误是：',error);
          }
        )
  }

  onBeforeMount(() => {
    console.log(1)
    friendsLife()
  })
// const friendsDel = (value) => {
//     console.log(value);
//       axios.get('').then(
//     response => {
//
//     }
//   ),
//   error => {
//     console.log('提交失败，错误是：',error);
//   }
// }
// const friendsNone= (value) => {
//     console.log(value);
//       axios.get('').then(
//     response => {
//
//     }
//   ),
//   error => {
//     console.log('提交失败，错误是：',error);
//   }
// }
//   error => {
//     console.log('提交失败，错误是：',error);
//   }
// })
// let indexList =  ref([])  //字母列表
// let data = ref(['是','的','我','啊','吧','从','额','给'])  //接收的后台数据
// let x =ref([])
// let y = ref(0)
// let o = ref(0)

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