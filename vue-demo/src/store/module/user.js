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
		isAdmin: true,
		loginDialogVisible: false,
	},
	//类似于计算属性computed
	getters: {
		hasSignedIn: (state, getters, rootState) => {
			return state.username !== "anonymous";
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
