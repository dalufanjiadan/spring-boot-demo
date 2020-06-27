<template>
	<div>
		<el-button
			type="primary"
			icon="el-icon-plus"
			circle
			@click="createTaskItemFormVisible = true"
			id="createTaskItemButton"
		></el-button>

		<el-tabs :tab-position="'right'">
			<el-tab-pane label="未完成">
				<div
					v-for="(taskItem, index) in getNotFinished()"
					:taskItem="taskItem"
					:key="index"
				>
					<el-row :gutter="20">
						<el-col :span="20">
							{{ taskItem.text }}
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
				<el-pagination
					page-size="8"
					layout="prev, pager, next"
					:total="taskItemListNotFinished.length"
					hide-on-single-page="true"
					@current-change="pageChange"
					:current-page.sync="currentPage"
				>
				</el-pagination>
			</el-tab-pane>
			<el-tab-pane label="已完成">
				<div
					v-for="(taskItem, index) in taskItemListFinished"
					:taskItem="taskItem"
					:key="index"
				>
					<el-row :gutter="20">
						<el-col :span="22">
							{{ taskItem.text }}
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
			<el-tab-pane label="已删除">
				<div
					v-for="taskItem in taskItemListDeleted"
					:taskItem="taskItem"
					:key="taskItem.createAt"
				>
					<el-row :gutter="20">
						<el-col :span="22">
							{{ taskItem.text }}
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

		<el-dialog title="新建todo" :visible.sync="createTaskItemFormVisible">
			<el-form :model="createTaskItemform">
				<el-form-item label="描述" :label-width="formLabelWidth">
					<el-input v-model="createTaskItemform.text" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="描述" :label-width="formLabelWidth">
					<el-input v-model="createTaskItemform.text" autocomplete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="createTaskItemFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="createTaskItem">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
import TaskItem from "./TaskItem.vue";

export default {
	name: "Todo",
	components: {
		// TaskItem,
	},
	data() {
		return {
			taskItemListNotFinished: [],
			taskItemListFinished: [],
			taskItemListDeleted: [],
			createTaskItemFormVisible: false,
			createTaskItemform: {
				text: "",
				finished: false,
			},
			// formLabelWidth: "120px",
			currentPage: 1,
			pageSize: 8,
		};
	},
	created() {
		console.log("created");
		for (let i = 0; i < 10; i++) {
			let taskItem = {
				text: i,
				finished: false,
			};
			this.taskItemListNotFinished.push(taskItem);
		}
		console.log(this.taskItemListNotFinished);
		console.log(this.taskItemListNotFinished.length);
	},
	methods: {
		createTaskItem() {
			let taskItem = {
				text: this.createTaskItemform.text,
				finished: false,
			};
			this.taskItemListNotFinished.push(taskItem);
		},
		finishTask(index) {
			let item = this.taskItemListNotFinished.splice(index, 1)[0];
			this.taskItemListFinished.push(item);
		},
		deleteTask(list, index) {
			let item = list.splice(index, 1)[0];
			this.taskItemListDeleted.push(item);
		},
		removeTask(index) {
			this.taskItemListDeleted.splice(index, 1);
		},
		getNotFinished() {
			let start = (this.currentPage - 1) * this.pageSize;
			let end = start + this.pageSize;

			let result = [];

			for (let i = start; i < end && i < this.taskItemListNotFinished.length; i++) {
				const element = this.taskItemListNotFinished[i];
				result.push(this.taskItemListNotFinished[i]);
			}
			return result;
		},
		pageChange() {
			console.log("--=====----");
			console.log(this.currentPage);
		},
	},
};
</script>

<style>
#createTaskItemButton {
	position: fixed;
	margin-top: 20%;
	right: 13%;
}
</style>
