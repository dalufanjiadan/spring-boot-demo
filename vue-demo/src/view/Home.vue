<template>
	<div>
		<h1>Home</h1>
	</div>
</template>

<script>
// import { mapGetters, mapActions } from "vuex";
export default {
	name: "Home",
	data() {
		return {};
	},
	created() {},
	computed: {
		// ...mapGetters("people", ["getPeople", "getPeopleByAge"]),
		count() {
			return this.$store.state.count;
		},
	},
	methods: {},
	props: {
		msg: String,
	},
};
</script>

<style scoped></style>
