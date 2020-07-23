<template>
	<div>
		<el-button
			type="primary"
			icon="el-icon-plus"
			circle
			@click="createTaskItemFormVisible = true"
			id="createTodoButton"
		></el-button>
		<el-tabs :tab-position="'right'" @tab-click="tabClick()" v-model="status">
			<el-tab-pane label="未完成" name="0">
				<div v-for="(todo, index) in todos" :todo="todo" :key="index">
					<el-row :gutter="20">
						<el-col :span="20">
							{{ todo.id + todo.description }}
						</el-col>
						<el-col :span="2">
							<el-button
								type="success"
								icon="el-icon-success"
								circle
								@click="finishTask(index)"
								style="float:right"
								size="medium"
							></el-button>
						</el-col>
						<el-col :span="2">
							<el-button
								type="danger"
								icon="el-icon-delete"
								circle
								@click="deleteTask(taskItemListNotFinished, index)"
								size="medium"
								style="float:right"
							></el-button>
						</el-col>
					</el-row>
					<el-divider content-position="right"></el-divider>
				</div>
			</el-tab-pane>
			<el-tab-pane label="已完成" name="1">
				<div v-for="(todo, index) in todos" :todo="todo" :key="index">
					<el-row :gutter="20">
						<el-col :span="22">
							{{ todo.id + todo.description }}
						</el-col>

						<el-col :span="2">
							<el-button
								type="danger"
								icon="el-icon-delete"
								circle
								@click="deleteTask(taskItemListFinished, index)"
								size="medium"
								style="float:right"
							></el-button>
						</el-col>
					</el-row>

					<el-divider content-position="right"></el-divider>
				</div>
			</el-tab-pane>
			<el-tab-pane label="已删除" name="2">
				<div v-for="(todo, index) in todos" :todo="todo" :key="index">
					<el-row :gutter="20">
						<el-col :span="22">
							{{ todo.id + todo.description }}
						</el-col>

						<el-col :span="2">
							<el-button
								type="danger"
								icon="el-icon-error"
								circle
								@click="removeTask(index)"
								size="medium"
								style="float:right"
							></el-button>
						</el-col>
					</el-row>
					<el-divider content-position="right"></el-divider>
				</div>
			</el-tab-pane>
		</el-tabs>

		<el-pagination
			:page-size="pageSize"
			layout="prev, pager, next"
			:total="total"
			hide-on-single-page="true"
			@current-change="pageChange"
			:current-page.sync="currentPage"
		>
		</el-pagination>

		<!-- <el-dialog title="新建todo" :visible.sync="createTaskItemFormVisible">
			<el-form :model="createTaskItemform">
				<el-form-item label="描述" :label-width="formLabelWidth">
					<el-input v-model="createTaskItemform.text" autocomplete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="createTaskItemFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="createTaskItem">确 定</el-button>
			</div>
		</el-dialog> -->
	</div>
</template>

<script>
import { fetchTodos } from "@/api/todo";

export default {
	name: "Todo",
	components: {},
	data() {
		return {
			todos: [],
			total: 0,
			currentPage: 1,
			pageSize: 8,
			status: 0,
			username: "gech",
		};
	},
	created() {
		this.getTodos();
	},
	methods: {
		pageChange() {
			this.getTodos();
		},
		tabClick() {
			this.getTodos();
		},
		getTodos() {
			let params = {
				username: this.username,
				status: this.status,
				currentPage: this.currentPage,
				pageSize: this.pageSize,
			};
			fetchTodos(params).then((res) => {
				this.total = res.data.total;
				this.todos = res.data.todos;
			});
		},
	},
	computed: {},
};
</script>

<style>
#createTodoButton {
	position: fixed;
	margin-top: 20%;
	right: 13%;
}
</style>
