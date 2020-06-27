<template>
	<div>
		<el-row :gutter="20">
			<el-col :span="11"><span>todo</span></el-col>
			<el-col :span="2"><el-divider direction="vertical"></el-divider></el-col>

			<el-col :span="11"><span>finish</span></el-col>
		</el-row>
		<TaskItem
			v-for="taskItem in taskItemList"
			:taskItem="taskItem"
			:key="taskItem.createAt"
		></TaskItem>
	</div>
</template>

<script>
import TaskItem from "./TaskItem.vue";

export default {
	name: "Todo",
	components: {
		TaskItem,
	},
	data() {
		return {
			taskItemList: [],
		};
	},
	created() {
		console.log("created");
		for (let i = 0; i < 10; i++) {
			let taskItem = {
				text: i,
				createAt: i,
				finishAt: i,
				deleteAt: i,
			};
			this.taskItemList.push(taskItem);
		}
	},
};
</script>

<style></style>
