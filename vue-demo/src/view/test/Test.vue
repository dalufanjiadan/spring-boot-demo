<template>
	<div>
		<el-button type="primary" @click="testClick">test</el-button>
	</div>
</template>

<script>
export default {
	name: "Test",
	data() {
		return {};
	},
	methods: {
		testClick() {
			let map = new Map();
			map.set("hello", "world");
			map.set("user", "hah");


			console.log(map.keys);
		},
	},
};
</script>

<style></style>
