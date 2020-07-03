<template>
	<div>
		<el-button type="primary" @click="testClick">test</el-button>

		<el-divider></el-divider>

		<el-select v-model="accountRoleDevice" placeholder="请选择" @change="onSelectChange">
			<el-option
				v-for="item in options"
				:key="item.value"
				:label="item.label"
				:value="item.value"
			>
			</el-option>
		</el-select>

		<el-divider></el-divider>

		<Condition v-for="(item, index) in conditions" :key="index" :condition="item"></Condition>

		<el-divider></el-divider>

		<el-tag> {{ getSelected }}</el-tag>

		{{ this.$store.state.test.selected }}
		{{ this.$store.getters["test/selected"] }}
	</div>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import Condition from "./Condition";

export default {
	name: "Test",
	components: {
		Condition,
	},
	data() {
		return {
			accountRoleDevice: 1,
			options: [
				{
					label: "账号",
					value: 1,
				},
				{
					label: "角色",
					value: 2,
				},
				{
					label: "设备",
					value: 3,
				},
			],
			conditions: [
				{
					label: "登录类",
					children: [
						{
							label: "注册用户",
							conditions: [
								{
									label: "登陆过的用户",
									input: {
										dateStart: null,
										dateEnd: null,
									},
								},
								{
									label: "登陆过的用户,并且最高等级在a至b级的用户",
									input: {
										dateStart: null,
										dateEnd: null,
										a: null,
										b: null,
									},
								},
							],
						},
						{
							label: "新增用户",
						},
						{
							label: "活跃用户",
						},
					],
				},
				{
					label: "付费类",
				},
				{
					label: "用户属性",
				},
			],
		};
	},
	computed: {
		...mapGetters("test", ["getSelected"]),
	},
	methods: {
		...mapMutations("test", ["add1"]),
		testClick() {
			this.conditions.forEach((element) => {
				// console.log(element);
			});

			console.log(this.$store.state.test.selected);

			// this.$store.state.test.selected.push(4)

			this.$store.commit("test/add1",5);
			// this.add1(5);
		},
		onSelectChange() {
			console.log(this.accountRoleDevice);
		},
	},
};
</script>

<style></style>
