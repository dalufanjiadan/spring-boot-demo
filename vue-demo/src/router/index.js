import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
	{
		path: "/home",
		name: "home",
		component: () => import("../view/Home.vue"),
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
			}
		],
	},
];

const router = new VueRouter({
	// mode: 'history',
	routes,
});

export default router;
