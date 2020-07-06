<template>
	<div  id="aaa">
		{{ index }}

		<el-tag>{{ condition.label }}</el-tag>
		<el-form ref="form" :model="form">
			<el-form-item v-for="(item, index) in condition.conditions" :key="index">
				{{ item.label }}
				<el-divider></el-divider>
				<el-input
					v-for="(param, index) in item.input"
					:key="index"
					:placeholder="param.label"
				>
					{{ param }}
				</el-input>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
export default {
	name: "SetCondition",
	props: ["condition", "index"],
};
</script>

<style scoped>
#aaa{
	box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
	padding: 5%;
}
</style>
