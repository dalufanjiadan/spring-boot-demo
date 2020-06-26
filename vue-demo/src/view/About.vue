<template>
	<div>
		<h1>about</h1>
	</div>
</template>

<script>
export default {
	name: "About",
	data() {
		return {
			activeIndex: "1",
			activeIndex2: "1",
		};
	},
	methods: {
		handleSelect(key, keyPath) {
			console.log(key, keyPath);
		},
		hello() {
			console.log("hello world11");
			this.$http.get("/api/v1/test/hello").then((res) => {
				console.log(res);
				console.log(res.data);
			});
		},
	},
	props: {
		msg: String,
	},
};
</script>

<style scoped></style>
