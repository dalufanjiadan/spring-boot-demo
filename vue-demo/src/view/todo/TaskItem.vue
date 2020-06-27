<template>
	<div>
		<el-row :gutter="20">
			<el-col :span="12"><div class="grid-content bg-purple">{{taskItem.text}}</div></el-col>
			<el-col :span="12"><div class="grid-content bg-purple">{{taskItem.createAt}}</div></el-col>
		</el-row>
	</div>
</template>

<script>
export default {
	name: "TaskItem",
	data() {
		return {};
	},
	props: ["taskItem"],
};
</script>

<style></style>
