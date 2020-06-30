<template>
	<div>
		<p>{{ message.name }}</p>
		<p>{{ message.message }}</p>
	</div>
</template>

<script>
export default {
	name: "message",
	props: ["message"],
};
</script>

<style></style>
