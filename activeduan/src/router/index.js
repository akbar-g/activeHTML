import { createRouter, createWebHistory } from 'vue-router'
import login from '../login/login.vue';
import zhuce from "../login/ZhuCe.vue";
import index from '../components/index.vue';
import zhuye from '../components/zhuye.vue';
import users from '../components/users/users.vue';
import updatausers from '../components/users/updatausers.vue';
import usersinfo from '../components/users/usersInfo.vue';
import finishusers from '../components/users/finishusers.vue';
import Histroys from '../components/users/userHistory.vue';
import friends from '../components/friend/friends.vue';
import conversation from '../components/conversation/conversation.vue';
import talk from '../components/conversation/talk.vue';

const routes = [
    {
        path:'/login',
        component:login,
    },
    {
        path:'/zhuce',
        component:zhuce,
    },
    {
        path:'/',
        component:index,
        children:[
            {
                path:'zhuye',
                component:zhuye,
            },
            {
                path:'users',
                component:users,
                meta:{isUser:true},
                beforeEnter: (to, from, next) => {
                    if (to.meta.isUser) {
                        if (sessionStorage.getItem('login')==="ok") {
                            next()
                        } else {
                            if (confirm('去登录吗？')) {
                                next('/login')
                            }
                        }
                    }else{
                        next()
                    }
                },
                children:[
                    {
                        path:'',
                        component:usersinfo,
                    },
                    {
                        path:'finishusers',
                        component:finishusers,
                    },
                    {
                        path:'updata',
                        component:updatausers,
                    },
                    {
                        path:'history',
                        component:Histroys,
                    },
                ]
            },
            {
                path:'friend',
                component:friends,
            },
            {
                path:'conversation',
                component:conversation,
            },
        ]
    },
    {
        path:'/talk',
        component:talk,
    },
]

const c =  createRouter({
    mode:'history',
    history: createWebHistory(),
    routes,
})
export default c