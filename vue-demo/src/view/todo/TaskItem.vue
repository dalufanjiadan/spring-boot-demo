<template>
	<div>
		<el-row :gutter="20">
			<el-col :span="12"
				><div class="grid-content bg-purple">{{ taskItem.text }}</div></el-col
			>
			<el-col :span="10">
				<el-button
					type="primary"
					icon="el-icon-plus"
					circle
					@click="taskItem.finished = true"
				></el-button>
			</el-col>
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
