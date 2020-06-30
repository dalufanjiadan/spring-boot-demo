import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
	{
		path: "/",
		redirect: "/home",
		name: "home",
		component: () => import("../view/Home.vue"),
	},
	{
		path: "/home",
		name: "home",
		component: () => import("../view/Home.vue"),
	},
	{
		path: "/todo",
		name: "todo",
		component: () => import("../view/todo/Todo.vue"),
	},
	{
		path: "/chat",
		name: "chat",
		component: () => import("../view/chat/Chat.vue"),
	},
	{
		path: "/test",
		name: "test",
		component: () => import("../view/test/Test.vue"),
	},
	{
		path: "/about",
		name: "about",
		component: () => import("../view/About.vue"),
	},
	{
		path: "/admin",
		name: "admin",
		component: () => import("../view/Admin.vue"),
		children: [
			{
				path: "/admin-user-create",
				name: "createUser",
				component: () => import("../view/user/CreateUser.vue"),
			},
			{
				path: "/admin-user-delete",
				name: "deleteUser",
				component: () => import("../view/user/DeleteUser.vue"),
			},
			{
				path: "/admin-user-update",
				name: "deleteUser",
				component: () => import("../view/user/UpdateUser.vue"),
			},
			{
				path: "/admin-user-find",
				name: "deleteUser",
				component: () => import("../view/user/FindUser.vue"),
			},
		],
	},
];

const router = new VueRouter({
	// mode: 'history',
	routes,
});

export default router;
