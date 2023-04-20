import { createApp } from 'vue'
import App from './App.vue'
import Router from './router'
import store from './store'
import {VueMasonryPlugin} from 'vue-masonry';

const app = createApp(App)
app.use(Router)
app.use(store)
app.use(VueMasonryPlugin)
app.mount('#app')
