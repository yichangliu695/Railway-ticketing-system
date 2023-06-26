import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store/'
import '../theme/theme/index.css'
import ElementUI from 'element-ui'
/*
import axios from 'axios'
import VueAxios from 'vue-axios'
*/
/*import axios from 'axios'*/




Vue.config.productionTip = false;
/*Vue.use(VueAxios,axios);*/
/*Vue.prototype.$axios = axios;  //写成原型属性，*/
Vue.use(ElementUI);

new Vue({
	el: '#app',
	router,
	store,
	template: '<App/>',
	components: { App }
})
