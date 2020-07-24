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
			:limit="3"
			multiple="false"
			show-file-list="true"
			:data="data"
		>
			<i class="el-icon-upload"></i>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
			<div class="el-upload__tip" slot="tip">
				只能上传jpg/png文件，且不超过500kb
			</div>
		</el-upload>

		<el-divider></el-divider>
		<h3>upload multiple files</h3>
	</div>
</template>

<script>
export default {
	data() {
		return {
			data: {
				username: "sanhaoxuesheng",
				type: 0,
			},
		};
	},
	methods: {},
};
</script>

<style>
#container {
	width: 80%;
	margin: 0% auto;
}
#el-upload-1 {
	text-align: center;
}
</style>
