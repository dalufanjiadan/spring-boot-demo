<template>
	<div>
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
