const people = {
	namespaced: true,
	//变量data
	state: {
		people: [
			{ name: "小A", age: 8, address: "北京" },
			{ name: "小B", age: 18, address: "上海" },
			{ name: "小C", age: 28, address: "广州" },
		],
	},
	//类似于计算属性computed
	getters: {
		getPeople: (state, getters, rootState) => {
			return state.people.filter((n) => n.age > 10);
		},
		getPeopleByAge: (state) => (age) => {
			return state.people.find((n) => n.age === age);
		},
	},
	//方法methods
	mutations: {},
	//异步操作
	actions: {},
};

export default people;
