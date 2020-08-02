<template>
	<el-main id="main">
		<ul
			id="ul-1"
			class="infinite-list"
			infinite-scroll-disabled="disabled"
			v-infinite-scroll="load"
			style="overflow:auto"
		>
			<li v-for="(line, i) in data" class="infinite-list-item" :key="i">
				<el-tag size="medium" style="width: 1rem; text-align:center"> {{ i + 1 }}</el-tag>
				{{ line }}
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
			return this.data.length >= 200;
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
	// height: 700px;
	padding-inline-start: 0%;
	list-style: none;
	overflow: auto;
	li {
		display: inline-block;
		font-size: 18px;
		width: max-content;
	}
}
</style>
