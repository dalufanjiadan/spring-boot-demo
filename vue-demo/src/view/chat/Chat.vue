<template>
	<div id="chat">
		<div id="content">
			<div>
				<el-form :label-position="labelPosition" label-width="auto" :model="formLabelAlign">
					<el-form-item label="昵称">
						<el-input v-model="name"></el-input>
					</el-form-item>
				</el-form>
				<!-- <el-row :gutter="0" type="flex" justify="space-between">
					<el-col :span="18">
						<el-input v-model="name"></el-input>
					</el-col>
					<el-col :span="6">
						<el-button @click.prevent="removeDomain(domain)" id="sendBtn"
							>发送</el-button
						>
					</el-col>
				</el-row> -->
			</div>
			<el-divider></el-divider>
			<Message v-for="(message, index) in messages" :key="index" :message="message"></Message>
		</div>
		<div id="footer">
			<el-divider></el-divider>
			<el-row :gutter="0">
				<el-col :span="19">
					<el-input v-model="name"></el-input>
				</el-col>
				<el-col :span="5">
					<el-button @click.prevent="removeDomain(domain)" style="float:right"
						>发送</el-button
					>
				</el-col>
			</el-row>
		</div>
	</div>
</template>

<script>
import Message from "./Message";

export default {
	name: "Chart",
	components: {
		Message,
	},
	data() {
		return {
			name: "nufasdfall",
			messages: [
				{
					name: "aa",
					message: "haha",
					self: false,
				},
				{
					name: "aa",
					message: "haha",
					self: true,
				},
			],
		};
	},
};
</script>

<style scoped>
#chat {
	width: 50%;
	margin: auto auto;
	padding: 2%;
	border-radius: 20px;
	box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
	overflow: auto;
}
#content {
	height: 600px;
}
</style>
