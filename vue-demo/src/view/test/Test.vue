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
			let url = "http://localhost:8080/game/gameClickInfo?gameIdOrName=少年三国志";
			console.log(url);
			console.log(encodeURI(url));
			this.$http
				.get("/api/v1/test/hello1?a=10&b=aaa&c=少年三国志&url=" + encodeURI(url))
				.then((res) => {
					console.log(res);
					console.log(res.data);
				});

			// this.$http.get("http://localhost:8080/game/gameClickInfo?gameIdOrName=少年三国志").then((res) => {
			// 	console.log(res);
			// 	console.log(res.data);
			// });
		},
	},
};
</script>

<style></style>
