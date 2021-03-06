import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
	{
		path: "/",
		name: "main",
		component: () => import("../view/main.vue"),
		// redirect: "/home",
		children: [
			{
				path: "/home",
				name: "home",
				component: () => import("../view/home.vue"),
			},
			{
				path: "/demo",
				name: "demo",
				component: () => import("../view/demo/Demo.vue"),
				redirect: "/demo-todo",
				children: [
					{
						path: "/demo-todo",
						name: "demo-todo",
						component: () => import("../view/todo/Todo.vue"),
					},
					{
						path: "/demo-upload-download",
						name: "demo-upload-down",
						component: () => import("../view/upload-download/UploadDownload.vue"),
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
				],
			},
			{
				path: "/admin",
				name: "admin",
				component: () => import("../view/Admin.vue"),
				redirect: "/admin-user-find",
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
					{
						path: "/admin-log",
						name: "log",
						component: () => import("../view/admin/Log.vue"),
					},
					{
						path: "/admin-log2",
						name: "log2",
						component: () => import("../view/admin/Log2.vue"),
					},
				],
			},
		],
	},

	{
		path: "/503",
		name: "maintain",
		component: () => import("../view/error/503.vue"),
	},
];

const router = new VueRouter({
	// mode: 'history',
	routes,
});

export default router;
