import Vue from "vue";
import Vuex from "vuex";
import people from "./module/people";

Vue.use(Vuex);

const store = new Vuex.Store({
	//变量data
	state: {
		count: 100,
	},
	//类似于计算属性computed
	getters: {
		getPeople: (state) => {
			return state.count;
		},
	},
	//方法methods
	mutations: {
		add(state) {
			state.count++;
		},
		sub(state) {
			state.count--;
		},
		mul(state, payload) {
			state.count *= 10;
		},
		div(state, payload) {
			state.count /= 10;
		},
	},
	//异步操作
	actions: {
		div(context) {
			context.commit({
				type: "div",
				amount: 10,
			});
		},
	},
	//模块
	modules: {
		people,
	},
});

export default store;
