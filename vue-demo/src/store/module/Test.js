const test = {
	namespaced: true,
	//变量data
	state: {
		selected: [],
	},
	//类似于计算属性computed
	getters: {
		getSelected: (state, getters, rootState) => {
			return state.selected;
		},
	},
	//方法methods
	mutations: {
		add(state, item) {
			state.selected.push(item);
		},
	},
	//异步操作
	actions: {},
};

export default test;
