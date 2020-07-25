<template>
	<div id="container">
		<h2>spring boot upload / download rest api example</h2>
		<el-divider></el-divider>
		<h3>upload single file</h3>

		<el-upload
			id="el-upload-1"
			class="upload-demo"
			drag
			action="http://localhost:8000/api/v1/upload-file"
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
			action="http://localhost:8000/api/v1/upload-multiple-files"
			:limit="3"
			multiple
			show-file-list="true"
			:data="data"
			auto-upload="false"
			name="files"
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

export default {
	data() {
		return {
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
			console.log(response.data.downloadUri);
		},
		getTableData() {
			let params = {
				currentPage: this.pagination.currentPage,
				pageSize: this.pagination.pageSize,
			};
			api.getFiles(params).then((res) => {
				console.log(res);
				this.tableData = res.data.data;
				this.pagination.total = res.data.total;
			});
		},
		pageChange() {
			this.getTableData();
		},
		downloadFile(index, row) {
			console.log(row.id);
			console.log(row.fileName);
			api.downloadFile(row.id).then((res) => {
				const url = window.URL.createObjectURL(new Blob([res.data]));
				const link = document.createElement("a");
				link.href = url;
				link.setAttribute("download", row.fileName); //or any other extension
				document.body.appendChild(link);
				link.click();
			});
		},
		deleteFile(index, row) {
			console.log(index);
			console.log(row);
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
