<template>
	<el-container id="main-container">
		<el-header id="main-header">
			<el-menu
				:default-active="activeIndex"
				class="el-menu-demo"
				mode="horizontal"
				@select="handleSelect"
				router="true"
			>
				<el-menu-item index="1" route="/Home"> Home</el-menu-item>
				<el-menu-item index="2" route="/Todo"> Todo</el-menu-item>
				<el-menu-item index="3" route="/Test"> Test</el-menu-item>
				<el-menu-item index="4" route="/demo"> Demo</el-menu-item>
				<el-submenu index="5" route="/about" id="user">
					<template slot="title"
						><el-avatar
							src="https://images.unsplash.com/photo-1477414348463-c0eb7f1359b6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2550&q=80"
						></el-avatar
					></template>
					<el-menu-item index="2-4-1">选项1</el-menu-item>
					<el-menu-item index="2-4-2">选项2</el-menu-item>
					<el-menu-item index="2-4-3">选项3</el-menu-item>
				</el-submenu>
				<el-menu-item index="6" route="/admin" id="admin"> Admin</el-menu-item>
			</el-menu>
		</el-header>
		<el-main>
			<router-view />
		</el-main>
		<!-- <el-footer id="main-footer">
			©Copyright 2019
		</el-footer> -->
	</el-container>
</template>

<script>
import { fetchHello } from "@/api/todo";

export default {
	name: "App",
	data() {
		return {
			activeIndex: "1",
		};
	},
	created() {
		//在页面加载时读取sessionStorage里的状态信息
		if (sessionStorage.getItem("activeIndex")) {
			this.activeIndex = sessionStorage.getItem("activeIndex");
		}

		console.log(this.activeIndex);

		//在页面刷新时将vuex里的信息保存到sessionStorage里
		window.addEventListener("beforeunload", () => {
			console.log(this.activeIndex);
			sessionStorage.setItem("activeIndex", this.activeIndex);
		});
	},
	methods: {
		handleSelect(key, keyPath) {
			this.activeIndex = keyPath[0];
		},
		hello() {
			console.log("hello world11");
			this.$http.get("/api/v1/test/hello").then((res) => {
				console.log(res);
				console.log(res.data);
			});
		},
	},
	props: {
		msg: String,
	},
};
</script>

<style scoped>
.el-menu-item,
.el-submenu__title,
#main-template {
	font-size: 0.8rem;
	font: bold;
}
#main-container {
	width: 80%;
	margin: 0 auto;
}
#main-footer {
	margin: 0 auto;
}
#admin,
#user {
	float: right;
}
</style>
