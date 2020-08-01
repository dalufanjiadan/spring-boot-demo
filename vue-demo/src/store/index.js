import Vue from "vue";
import Vuex from "vuex";
import user from "./module/user";

Vue.use(Vuex);

const store = new Vuex.Store({
	//变量data
	state: {},
	//类似于计算属性computed
	getters: {},
	//方法methods
	mutations: {},
	//异步操作
	actions: {},
	//模块
	modules: {
		user,
	},
});

export default store;
