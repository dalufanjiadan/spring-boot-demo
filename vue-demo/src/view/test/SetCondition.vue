<template>
	<div style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)">
		<el-tag>{{ condition.label }}</el-tag>
		<!-- {{ condition.conditions }} -->

		<el-form ref="form" :model="form" label-width="80px">
			<el-form-item
				:label="item.label"
				v-for="(item, index) in condition.conditions"
				:key="index"
			>
				{{ item }}
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
export default {
	name: "SetCondition",
	props: ["condition"],
};
</script>

<style></style>
