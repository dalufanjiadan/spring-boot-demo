<template>
	<div id="chat">
		<div id="content">
			<div>
				<el-form :label-position="labelPosition" label-width="auto" :model="formLabelAlign">
					<el-form-item label="昵称">
						<el-input v-model="name"></el-input>
					</el-form-item>
				</el-form>
			</div>
			<el-divider></el-divider>
			<Message v-for="(message, index) in messages" :key="index" :message="message">
			</Message>
		</div>
		<div id="footer">
			<el-divider></el-divider>
			<el-row :gutter="0">
				<el-col :span="19">
					<el-input v-model="messageText"></el-input>
				</el-col>
				<el-col :span="5">
					<el-button @click="sendMessage()" style="float:right">发送</el-button>
				</el-col>
			</el-row>
		</div>
	</div>
</template>
<script src="./js/sockjs-0.3.4.js"></script>
<script src="./js/stomp.js"></script>
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
			messageText: null,
			stompClient: null,
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
	created() {
		this.connect();
	},
	methods: {
		sendMessage() {
			let message = {
				from: this.name,
				text: this.messageText,
				self: true,
			};

			// post

			this.messages.push(message);

			console.log(this.messages);

			stompClient.send("/app/chat", {}, JSON.stringify(message));
		},
		setConnected(connected) {
			document.getElementById("connect").disabled = connected;
			document.getElementById("disconnect").disabled = !connected;
			document.getElementById("conversationDiv").style.visibility = connected
				? "visible"
				: "hidden";
			document.getElementById("response").innerHTML = "";
		},
		connect() {
			console.log("-=-=");
			let socket = new SockJS("/chat");
			this.stompClient = Stomp.over(socket);
			this.stompClient.connect({}, function(frame) {
				// setConnected(true);
				console.log("Connected: " + frame);
				stompClient.subscribe("/topic/messages", function(messageOutput) {
					showMessageOutput(JSON.parse(messageOutput.body));
				});
			});

			console.log(this.stompClient);
			console.log("-==12");
		},
		disconnect() {
			if (stompClient != null) {
				stompClient.disconnect();
			}
			setConnected(false);
			console.log("Disconnected");
		},

		showMessageOutput(messageOutput) {
			var response = document.getElementById("response");
			var p = document.createElement("p");
			p.style.wordWrap = "break-word";
			p.appendChild(
				document.createTextNode(
					messageOutput.from + ": " + messageOutput.text + " (" + messageOutput.time + ")"
				)
			);
			response.appendChild(p);
		},
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
	overflow: auto;
}
</style>
