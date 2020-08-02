<template>
	<el-container direction="horizontal" id="main-container">
		<el-tree :props="props" :load="loadNode" lazy show-checkbox> </el-tree>
		<h1>hello</h1>
	</el-container>
</template>

<script>
export default {
	data() {
		return {
			props: {
				label: "name",
				children: "zones",
				isLeaf: "leaf",
			},
		};
	},
	methods: {
		loadNode(node, resolve) {
			if (node.level === 0) {
				return resolve([{ name: "region" }]);
			}
			if (node.level > 1) return resolve([]);

			setTimeout(() => {
				const data = [
					{
						name: "leaf",
						leaf: true,
					},
					{
						name: "zone",
					},
				];

				resolve(data);
			}, 500);
		},
	},
};
</script>

<style lang="scss" scoped>
#main-container {
	width: 100%;
	background-color: aquamarine;
}
</style>
