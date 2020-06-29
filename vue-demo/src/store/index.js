import Vue from "vue";
import Vuex from "vuex";
import test from "./module/test";

Vue.use(Vuex);

const store = new Vuex.Store({
	//变量data
	state: {
		count: 100,
		people: [
			{ name: "小A", age: 8, address: "北京" },
			{ name: "小B", age: 18, address: "上海" },
			{ name: "小C", age: 28, address: "广州" },
		],
	},
	//类似于计算属性computed
	getters: {
		getPeople: (state) => {
			return state.people.filter((n) => n.age > 10);
		},
		etPeopleLength: (state, getters) => {
			//直接调用上面的getPeople方法并取得其长度
			return getters.getPeople.length;
		},
		getPeopleByAge: (state) => (age) => {
			return state.people.find((n) => n.age === age);
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
		test,
	},
});

export default store;
