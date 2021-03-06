import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus';
import 'element-plus/lib/theme-chalk/index.css';
import './assets/css/global.css'
import 'dayjs/locale/zh-cn'
import locale from 'element-plus/lib/locale/lang/zh-cn'

import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'

createApp(App).use(store).use(ElementPlus, { locale }).use(router).use(codemirror).mount('#app')
