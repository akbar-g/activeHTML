<template>
  <div style="width: 100%;height: 100%;">
    <van-nav-bar
      title="张三"
      left-text=""
      left-arrow
      @click-left="onClickLeft"
    />
    <div id="message">

    </div>
    <van-cell-group inset style="width: 100%;padding:0; margin-left: -0.625rem;">
    <van-field
      v-model="sms"
      center
      clearable
      placeholder="请输入消息"
    >
      <template #button>
        <van-button size="large" type="primary" style="width: 150%;" @click="putMessage()">发送</van-button>
      </template>
    </van-field>
</van-cell-group>

  </div>

</template>

<script setup>
import axios from "axios";
import { onMounted,ref } from "vue";

// let userName = ref(JSON.parse(sessionStorage.getItem('users')).username)
let toName = ref('')
let sms = ''
let m = ''
//发送消息
const putMessage = () =>{
  //静态实现发送消息
  // let main = document.querySelector('#message')
  // let strr = "<img class='imgright' src='https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg'><div class='put'><span>" + sms + "</span></div></br></br></br>"
  // m = m + strr
  // main.innerHTML = m
  axios.get(`'http://localhost:8082/api/userMessage/getMessage/?${userName}&${toName}'`).then(
        response => {
        if (response.status==200){
                console.log(response)
                let main = document.querySelector('#message')
                let strr = "<img class='imgright' src='https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg'><div class='put'><span>" + sms + "</span></div></br></br></br>"
                m = m + strr
                main.innerHTML = m 
        }else{
              console.log("请求有点问题",response)
              }
        },
        error => {
        console.log('提交数据失败，错误是：',error);
        })
}

const showChat = (name) => {
        toName = name;
        //从sessionStorage中获取历史信息
        console.log(userName, toName)
        axios.post('http://localhost:8082/userMessage/getMessage',{userName,toName}).then(
        response => {
        if (response.status==200){
                //现在聊天框
                main = document.querySelector('#message')
                console.log(response)
                let list = ress[0].concat(ress[1])

                function sortUpdateTime(a, b) {
                    return new Date(a.updateTime).getTime() - new Date(b.updateTime).getTime()
                }
                
                list = list.sort(sortUpdateTime)
                list.forEach(function (item) {
                    if (item.fromName == userName) {
                        let strr = "<img class='imgright' src='https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg'><div class='put'><span>" + item.message + "</span></div></br></br></br>"
                          m = m + strr
                    } else {
                        if (item.toName == toName) {
                            let strr = "<img class='imgleft' src='https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg'><div class='pull'><span>" + item.message + "</span></div></br></br></br>"
                          m = m + strr
                        }
                    }
                })
                main.innerHTML = m 
                }else{
                console.log("请求有点问题",response)
                }
            },
            error => {
                console.log('提交数据失败，错误是：',error);
            }
            )
    }

const onClickLeft = () => history.back();

onMounted(() => {
  toName = sessionStorage.getItem("toname")
  showChat(toName)
})
</script>

<style>
#message{
  width: 98%;
  height: 85%;
  border: 1px black solid;
}
.put{
float: right;
margin-top: 10px;
padding: 8px 15px;
background: url(../../../public/qpk.png) no-repeat ;
background-size:100% 100%;
display: inline-block;
}

.imgright{
width: 2.5rem;
height: 2.5rem;
border-radius: 50%;
margin-top: 10px;
margin-left: .625rem;
float: right;
display: block;
}
.imgleft{
width: 2.5rem;
height: 2.5rem;
border-radius: 50%;
margin-top: 10px;
margin-left: .625rem;
float: left;
transform: rotateY(180deg);
display: block;
}
.pull{
float: left;
margin-top: 10px;
padding: 8px 15px;
background: url(../../../public/qpk.png) no-repeat;
background-size:100% 100%;
display: inline-block;
}
</style>