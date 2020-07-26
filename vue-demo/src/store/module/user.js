import { getToken, setToken, removeToken } from "@/util/auth";

const user = {
	namespaced: true,
	//变量data
	state: {
		username: "anonymous",
		avatar: "",
		introduction: "",
		roles: [],
		token: getToken(),
		idAdmin: false,
		loginDialogVisible: false,
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
	mutations: {
		setLoginDialogVisible(state, loginDialogVisible) {
			state.loginDialogVisible = loginDialogVisible;
		},
	},
	//异步操作
	actions: {},
};

export default user;
