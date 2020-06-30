<template>
	<div id="message">
		<div v-if="message.self">
			<p class="message-name">
				{{ message.name }}
			</p>
			<p class="message-text">
				{{ message.message }}
			</p>
		</div>
		<!-- <div v-if="message.self === false">
			<p class="message-name">
				{{ message.name }}
			</p>
			<p class="message-text">
				{{ message.message }}
			</p>
		</div> -->
	</div>
</template>

<script>
export default {
	name: "message",
	props: ["message"],
};
</script>

<style scoped>
.message {
	border-radius: 30px;
	clear: both;
	border-radius: 20px;
	box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.message-name {
	font-size: 18px;
	margin-bottom: 0;
}
.message-text {
	font-size: 20px;
	margin-top: 0;
}
</style>
