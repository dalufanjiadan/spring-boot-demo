import Vue from "vue";
import App from "./App.vue";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import { get, post } from "./util/httpUtil";

Vue.use(ElementUI);
Vue.prototype.$http = { get, post };

Vue.config.productionTip = false;

new Vue({
	render: (h) => h(App),
}).$mount("#app");
