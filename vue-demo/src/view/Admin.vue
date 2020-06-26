<template>
	<el-container>
		<el-aside width="200px" style="background-color: rgb(238, 241, 246)">
			<el-menu :default-openeds="['1', '3']" router="true">
				<el-submenu index="1">
					<template slot="title"><i class="el-icon-message"></i>用户管理</template>
					<el-menu-item-group>
						<template slot="title">分组一</template>
						<el-menu-item index="1-1" route="/admin-user-create">增</el-menu-item>
						<el-menu-item index="1-2" route="/admin-user-delete">删</el-menu-item>
						<el-menu-item index="1-3" route="/admin-user-update">改</el-menu-item>
						<el-menu-item index="1-4" route="/admin-user-find">查</el-menu-item>
					</el-menu-item-group>
					<el-menu-item-group title="分组2">
						<el-menu-item index="1-3">选项3</el-menu-item>
					</el-menu-item-group>
					<el-submenu index="1-4">
						<template slot="title">选项4</template>
						<el-menu-item index="1-4-1">选项4-1</el-menu-item>
					</el-submenu>
				</el-submenu>
				<el-submenu index="2">
					<template slot="title"><i class="el-icon-menu"></i>角色管理</template>
					<el-menu-item-group>
						<template slot="title">分组一</template>
						<el-menu-item index="2-1">选项1</el-menu-item>
						<el-menu-item index="2-2">选项2</el-menu-item>
					</el-menu-item-group>
					<el-menu-item-group title="分组2">
						<el-menu-item index="2-3">选项3</el-menu-item>
					</el-menu-item-group>
					<el-submenu index="2-4">
						<template slot="title">选项4</template>
						<el-menu-item index="2-4-1">选项4-1</el-menu-item>
					</el-submenu>
				</el-submenu>
			</el-menu>
		</el-aside>

		<el-container>
			<router-view></router-view>
		</el-container>
	</el-container>
</template>

<style>

</style>

<script>
export default {
	data() {
		const item = {
			date: "2016-05-02",
			name: "王小虎",
			address: "上海市普陀区金沙江路 1518 弄",
		};
		return {
			tableData: Array(20).fill(item),
		};
	},
};
</script>
