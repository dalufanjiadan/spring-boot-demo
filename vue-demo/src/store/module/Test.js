const test = {
	namespaced: true,
	//变量data
	state: {
		selected: [1, 2, 3],
	},
	//类似于计算属性computed
	getters: {
		getSelected: (state, getters, rootState) => {
			return state.selected;
		},
	},
	//方法methods
	mutations: {
		add1(state, item) {
			console.log("--");
			console.log(item);
			state.selected.push(item);

			console.log("--2");
		},
	},
	//异步操作
	actions: {},
};

export default test;
