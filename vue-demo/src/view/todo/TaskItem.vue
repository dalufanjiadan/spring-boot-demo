<template>
	<div>
		<h3>{{ taskItem.createAt }}</h3>
		<h3>{{ taskItem.text }}</h3>
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
