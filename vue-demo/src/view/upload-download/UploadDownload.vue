<template>
	<div id="container">
		<h2>spring boot upload / download rest api example</h2>
		<el-divider></el-divider>
		<h3>upload single file</h3>

		<el-upload
			id="el-upload-1"
			class="upload-demo"
			drag
			:action="uploadUrl"
			name="files"
			:limit="1"
			multiple="false"
			show-file-list="true"
			:data="data"
			:on-success="uploadSuccess"
		>
			<i class="el-icon-upload"></i>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
			<!-- <div class="el-upload__tip" slot="tip"></div> -->
		</el-upload>

		<el-divider></el-divider>
		<h3>upload multiple files</h3>
		<el-upload
			id="el-upload-2"
			class="upload-demo"
			drag
			:action="uploadUrl"
			:limit="3"
			multiple
			show-file-list="true"
			:data="data"
			auto-upload="false"
			name="files"
			:on-success="uploadSuccess"
		>
			<i class="el-icon-upload"></i>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
		</el-upload>

		<el-divider></el-divider>
		<h3>all files</h3>
		<el-table :data="tableData" style="width: 100%">
			<el-table-column prop="createdAt" label="日期" width="180"> </el-table-column>
			<el-table-column prop="fileName" label="名称" width="180"> </el-table-column>
			<el-table-column prop="fileType" label="类型"> </el-table-column>
			<el-table-column prop="size" label="大小"> </el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button
						type="info"
						icon="el-icon-download"
						circle
						@click="downloadFile(scope.$index, scope.row)"
					></el-button>
					<el-button
						type="danger"
						icon="el-icon-delete"
						circle
						@click="deleteFile(scope.$index, scope.row)"
					></el-button>
				</template>
			</el-table-column>
		</el-table>

		<div class="block" style="text-align:center">
			<el-pagination
				layout="prev, pager, next"
				:total="pagination.total"
				:current-page.sync="pagination.currentPage"
				:page-size="pagination.pageSize"
				hide-on-single-page="false"
				@current-change="pageChange"
			>
			</el-pagination>
		</div>
	</div>
</template>

<script>
import { api } from "@/api/uploadDownload";
const baseUrl = process.env.VUE_APP_BASE_API;
export default {
	data() {
		return {
			uploadUrl: baseUrl + "/api/v1/demo/files",
			data: {
				username: "sanhaoxuesheng",
				type: 0,
			},
			tableData: [],
			// 分页
			pagination: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
			},
		};
	},
	created() {
		this.getTableData();
	},
	methods: {
		uploadSuccess(response, file, fileList) {
			this.getTableData();
		},
		getTableData() {
			let params = {
				currentPage: this.pagination.currentPage,
				pageSize: this.pagination.pageSize,
			};
			api.getFiles(params).then((res) => {
				this.tableData = res.data.data;
				for (const element of this.tableData) {
					element.size = this.convertFileSize(element.size);
				}
				this.pagination.total = res.data.total;
			});
		},
		pageChange() {
			this.getTableData();
		},
		downloadFile(index, row) {
			let a = document.createElement("a");
			let url = `${baseUrl}/api/v1/demo/files/${row.id}/download`;
			let filename = row.filename;
			a.href = url;
			a.download = filename;
			a.click();
		},
		deleteFile(index, row) {
			api.deleteFile(row.id).then((res) => {
				console.log(res);
				this.getTableData();
			});
		},

		convertFileSize(fileSize) {
			let result = "";
			if (fileSize >= 1024 * 1024 * 1024) {
				// B => GB
				result = (fileSize / (1024 * 1024 * 1024)).toFixed(2) + "G";
			} else if (fileSize >= 1048576) {
				// B => MB
				result = (fileSize / (1024 * 1024)).toFixed(2) + "MB";
			} else if (fileSize >= 1024) {
				// B => KB
				result = (fileSize / 1024).toFixed(2) + "KB";
			} else {
				result = fileSize + "B";
			}
			return result;
		},
	},
};
</script>

<style>
#container {
	width: 80%;
	margin: 0% auto;
}
#el-upload-1,
#el-upload-2 {
	text-align: center;
}
</style>
