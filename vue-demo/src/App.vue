<template>
	<el-container id="main-container">
		<el-header id="main-header">
			<el-menu
				:default-active="activeIndex"
				id="el-menu-1"
				mode="horizontal"
				@select="handleSelect"
				router="true"
			>
				<el-menu-item index="1" route="/Home" class="el-menu-item-1"> Home</el-menu-item>
				<el-menu-item index="4" route="/demo" class="el-menu-item-1"> Demo</el-menu-item>
				<el-submenu index="5" route="/about" id="user">
					<template slot="title"
						><el-avatar
							src="https://images.unsplash.com/photo-1477414348463-c0eb7f1359b6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2550&q=80"
						></el-avatar
					></template>
					<el-menu-item index="2-4-1" class="el-menu-item-1">选项1</el-menu-item>
					<el-menu-item index="2-4-2" class="el-menu-item-1">选项2</el-menu-item>
					<el-menu-item index="2-4-3" class="el-menu-item-1">选项3</el-menu-item>
				</el-submenu>
				<el-menu-item index="6" route="/admin" id="admin" class="el-menu-item-1">
					Admin</el-menu-item
				>
			</el-menu>

			<el-menu id="el-menu-2" mode="horizontal">
				<el-menu-item class="el-menu-item-right el-menu-item-1">
					注册
				</el-menu-item>
				<el-menu-item class="el-menu-item-right el-menu-item-1" @click="showLoginDialog">
					登陆</el-menu-item
				>
			</el-menu>
			<div style="clear:both"></div>
			<el-divider id="el-divider-1"></el-divider>
		</el-header>

		<el-main>
			<router-view />
		</el-main>
		<!-- <el-footer id="main-footer">
			©Copyright 2019
		</el-footer> -->

		<el-dialog
			title="提示"
			:visible.sync="loginDialogVisible"
			width="30%"
			center
			@close="close"
		>
			<span>需要注意的是内容是默认不居中的</span>
			<span slot="footer" class="dialog-footer">
				<el-button @click="centerDialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
			</span>
		</el-dialog>

		{{ hasLoggedIn }}
		<el-button style="primary" @click="test">test</el-button>
	</el-container>
</template>

<script>
// import { mapGetters, mapActions } from "vuex";

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

		//在页面刷新时将vuex里的信息保存到sessionStorage里
		window.addEventListener("beforeunload", () => {
			console.log(this.activeIndex);
			sessionStorage.setItem("activeIndex", this.activeIndex);
		});
	},
	computed: {
		// ...mapGetters("people", ["getPeople", "getPeopleByAge"]),
		// ...mapGetters("user", ["isLogined"]),
		loginDialogVisible() {
			return this.$store.state.user.loginDialogVisible;
		},
		// 方式2
		hasLoggedIn() {
			return this.$store.getters["user/hasLoggedIn"];
		},
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
		close() {
			console.log("close");
		},
		showLoginDialog() {
			this.$store.commit("user/setLoginDialogVisible", true);
		},
	},

	props: {
		msg: String,
	},
};
</script>

<style lang="scss" scoped>
.el-menu-item-1 {
	font-size: 20px;
}
#el-menu-1 {
	width: 80%;
	float: left;
	border: none;
}
#el-menu-2 {
	width: 20%;
	float: right;
	border: none;
	.el-menu-item-right {
		float: right;
		border: none;
	}
}

#el-divider-1 {
	margin-top: 0%;
	margin-bottom: 0%;
}

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
