import { createStore } from 'vuex'
/**
 * 创建仓库和导出
 */
export default createStore({
    actions:{},
    mutations:{
        username(state,vaule){
            state.userAccount=vaule
        },
        userid(state,vaule){
            state.id=vaule
        },
    },
    state: {
        userAccount: '',
        id:'',
    },
    
})