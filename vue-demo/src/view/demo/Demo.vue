<template>
	<el-container>
		<el-aside width="20%">
			<el-menu
				:default-active="activeIndex"
				class="el-menu-vertical-demo"
				:collapse="false"
				mode="vertical"
				@select="handleSelect"
				router="true"
				menu-trigger="hover"
			>
				<el-menu-item index="1" route="/demo-todo">
					<i class="el-icon-menu"></i>
					<span slot="title">TODO</span>
				</el-menu-item>
				<el-menu-item index="2" route="/demo-upload-download">
					<i class="el-icon-upload2"></i>
					<span slot="title">UPLOAD/DOWNLOAD</span>
				</el-menu-item>
			</el-menu>
		</el-aside>
		<el-main>
			<router-view />
		</el-main>
	</el-container>
</template>

<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
	width: 80%;
}
</style>

<script>
export default {
	data() {
		return {
			activeIndex: "1",
		};
	},
	created() {
		//在页面加载时读取sessionStorage里的状态信息
		if (sessionStorage.getItem("demo-activeIndex")) {
			this.activeIndex = sessionStorage.getItem("demo-activeIndex");
		}

		// console.log(this.activeIndex);

		//在页面刷新时将vuex里的信息保存到sessionStorage里
		window.addEventListener("beforeunload", () => {
			console.log(this.activeIndex);
			sessionStorage.setItem("demo-activeIndex", this.activeIndex);
		});
	},
	methods: {
		handleSelect(key, keyPath) {
			this.activeIndex = keyPath[0];
		},
	},
};
</script>
