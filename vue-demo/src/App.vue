<template>
	<el-container id="main-container">
		<el-header id="main-header">
			<el-menu
				id="el-menu-1"
				:default-active="activeIndex1"
				@select="handleSelect1"
				router="true"
				mode="horizontal"
			>
				<el-menu-item index="home" route="/Home" class="el-menu-item-1"> Home</el-menu-item>
				<el-menu-item index="demo" route="/demo" class="el-menu-item-1"> Demo</el-menu-item>
			</el-menu>

			<el-menu
				id="el-menu-2"
				:default-active="activeIndex2"
				@select="handleSelect2"
				router="true"
				mode="horizontal"
			>
				<el-menu-item
					v-if="hasSignedIn === false"
					index="signup"
					class="el-menu-item-right el-menu-item-1"
				>
					注册
				</el-menu-item>
				<el-menu-item
					v-if="hasSignedIn === false"
					index="signin"
					class="el-menu-item-right el-menu-item-1"
					@click="showLoginDialog"
				>
					登陆</el-menu-item
				>
				<el-submenu v-if="hasSignedIn" index="user" class="el-menu-item-right el-submenu-1">
					<template slot="title">
						<el-avatar
							src="https://images.unsplash.com/photo-1477414348463-c0eb7f1359b6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2550&q=80"
						>
						</el-avatar>
					</template>
					<el-menu-item index="user-1" class="el-menu-item-1">选项1</el-menu-item>
					<el-menu-item index="user-2" class="el-menu-item-1">选项2</el-menu-item>

					<el-divider v-if="isAdmin"></el-divider>
					<el-menu-item
						index="user-3"
						v-if="isAdmin"
						route="/admin"
						class="el-menu-item-1"
					>
						admin
					</el-menu-item>
				</el-submenu>
			</el-menu>
			<div style="clear:both"></div>
			<el-divider id="el-divider-1"></el-divider>
		</el-header>

		<el-main>
			<router-view />
		</el-main>

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
	</el-container>
</template>

<script>
// import { mapGetters, mapActions } from "vuex";

export default {
	name: "App",
	data() {
		return {
			activeIndex1: "",
			activeIndex2: "",
		};
	},
	created() {
		//在页面加载时读取sessionStorage里的状态信息
		if (sessionStorage.getItem("activeIndex1")) {
			this.activeIndex1 = sessionStorage.getItem("activeIndex1");
		}
		if (sessionStorage.getItem("activeIndex2")) {
			this.activeIndex2 = sessionStorage.getItem("activeIndex2");
		}

		//在页面刷新时将vuex里的信息保存到sessionStorage里
		window.addEventListener("beforeunload", () => {
			sessionStorage.setItem("activeIndex1", this.activeIndex1);
			sessionStorage.setItem("activeIndex2", this.activeIndex2);
		});

		if (this.activeIndex1 === "" && this.activeIndex2 === "") {
			this.activeIndex1 = 1;
		}
	},
	computed: {
		// ...mapGetters("people", ["getPeople", "getPeopleByAge"]),
		// ...mapGetters("user", ["isLogined"]),
		loginDialogVisible() {
			return this.$store.state.user.loginDialogVisible;
		},
		// 方式2
		hasSignedIn() {
			return this.$store.getters["user/hasSignedIn"];
		},
		isAdmin() {
			return this.$store.state.user.isAdmin;
		},
	},
	methods: {
		handleSelect1(key, keyPath) {
			this.activeIndex1 = keyPath[keyPath.length - 1];
			this.activeIndex2 = "";
		},
		handleSelect2(key, keyPath) {
			if (keyPath[0] === "user") {
				this.activeIndex2 = keyPath[keyPath.length - 1];
				this.activeIndex1 = "";
			}
		},
		close() {
			this.$store.commit("user/setLoginDialogVisible", false);
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
	font-size: 23px;
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
	.el-submenu-1 {
		float: right;
		border: none;
		.el-menu-item {
			border: none;
		}
	}
}

#el-divider-1,
#el-divider-2 {
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
