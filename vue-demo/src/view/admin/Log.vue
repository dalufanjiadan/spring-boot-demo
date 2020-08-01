<template>
	<el-main id="main">
		<ul
			id="ul-1"
			class="infinite-list"
			infinite-scroll-disabled="disabled"
			v-infinite-scroll="load"
			style="overflow:auto"
		>
			<li v-for="(line, index) in data" class="infinite-list-item" :key="index">
				{{ index + "==" + line }}
			</li>
		</ul>
		<p v-if="loading">加载中...</p>
		<p v-if="noMore">没有更多了</p>
	</el-main>
</template>

<script>
import { api } from "@/api/admin/log";
import { MessageBox, Message } from "element-ui";
export default {
	data() {
		return {
			count: 0,
			data: [],
			loading: false,
		};
	},
	computed: {
		noMore() {
			return this.data.length >= 50;
		},
		disabled() {
			return this.loading || this.noMore;
		},
	},
	methods: {
		load() {
			this.loading = true;
			setTimeout(() => {
				this.loading = false;
				api.getLog().then((res) => {
					// Array(20).fill({ a: 1 })
					this.data = this.data.concat(res.data);
					// this.message(this.data);
				});
			}, 500);
		},

		message(data) {
			Message({
				message: data,
				type: "info",
				duration: 5 * 1000,
			});
		},
	},
};
</script>

<style lang="scss" scoped>
* {
	padding: 0%;
	margin: 0%;
}

#main {
	padding: 2%;
	height: 800px;
}

#ul-1 {
	height: 700px;
	padding-inline-start: 0%;
	list-style: none;
	// width: 90%;
	overflow: auto;
	// width: 5000px; //设置足够的宽度
	li {
		// display: inline-block;
		// background-color: wheat;
		width: 3000px;
		margin-bottom: 5px;
		font-size: 18px;
	}
}
</style>
