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
        <van-button size="large" type="primary" style="width: 150%;">发送</van-button>
      </template>
    </van-field>
</van-cell-group>

  </div>

</template>

<script setup>
import axios from "axios";
import { onMounted,ref } from "vue";

let userName = ref(JSON.parse(sessionStorage.getItem('users')).username)
let toName = ref('')
const showChat = (name) => {
        toName = name;
        //现在聊天框
	      main = document.querySelector('#message')
	      main.style.color = "#ccc"
        //从sessionStorage中获取历史信息
        console.log(userName, toName)
        axios.post('http://localhost:8082/userMessage/getMessage',{userName,toName}).then(
        response => {
        if (response.status==200){
                console.log(response)
                let list = ress[0].concat(ress[1])

                function sortUpdateTime(a, b) {
                    return new Date(a.updateTime).getTime() - new Date(b.updateTime).getTime()
                }

                list = list.sort(sortUpdateTime)
                list.forEach(function (item) {
                    if (item.fromName == userName) {
                        var strr = "<div class='put'><span>" + item.message + "</span></div></br>";
                        main.append(strr);
                    } else {
                        if (item.toName == toName) {
                            var str = "<div class='pull'><span>" + item.message + "</span></div></br>";
                            main.append(str);
                        }
                    }
                })
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
  toName = localStorage.getItem("toname")
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
background-color: rgb(0, 102, 255);
background-image: url(../../../public/qpk.png);
}
.pull{
float: left;
background-image: url(../../../public/qpk.png);
}
</style>