<template>
	<div>
		<h5>{{ condition.label }}</h5>
		<el-divider></el-divider>

		<el-button
			type="primary"
			plain
			v-for="(item, index) in condition.children"
			:key="index"
			@click="onClick(item)"
			>{{ item.label }}</el-button
		>
	</div>
</template>

<script>
export default {
	name: "Condition",
	props: ["condition"],
	methods: {
		onClick(item) {
			this.$store.commit("test/add", item);
		},
	},
};
</script>

<style scoped>
.el-tag {
	margin-right: 2%;
}
</style>
