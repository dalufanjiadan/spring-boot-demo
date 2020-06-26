<template>
	<div>
		<h1>Home</h1>
		<el-button type="primary" v-on:click="hello">test button</el-button>
		<div id="char1" style="width: 600px;height:400px;"></div>
	</div>
</template>

<script>
export default {
	name: "Home",
	data() {
		return {
			activeIndex: "1",
			activeIndex2: "1",
		};
	},
	methods: {
		handleSelect(key, keyPath) {
			console.log(key, keyPath);
		},
		hello() {
			console.log("hello world11");
			let data = [1, 2, 3, 4, 5];
			this.$http.get("/api/v1/test/hello").then((res) => {
				console.log(res);
				console.log(res.data);
			});

			let myChart = this.$echarts.init(document.getElementById("char1"));

			// 指定图表的配置项和数据
			var option = {
				title: {
					text: "ECharts 入门示例",
				},
				tooltip: {},
				legend: {
					data: ["销量"],
				},
				xAxis: {
					data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"],
				},
				yAxis: {},
				series: [
					{
						name: "销量",
						type: "bar",
						data: [5, 20, 36, 10, 10, 20],
					},
				],
			};

			// 使用刚指定的配置项和数据显示图表。
			myChart.setOption(option);

			option = {
				xAxis: {
					type: "category",
					data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
				},
				yAxis: {
					type: "value",
				},
				series: [
					{
						data: [820, 932, 901, 934, 1290, 1330, 1320],
						type: "line",
					},
				],
			};
			myChart.setOption(option);

			option = {
				xAxis: {
					type: "category",
					data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
				},
				yAxis: {
					type: "value",
				},
				series: [
					{
						data: data,
						type: "line",
					},
				],
			};
			myChart.setOption(option);

			option = {
				tooltip: {
					trigger: "item",
					formatter: "{a} <br/>{b}: {c} ({d}%)",
				},
				legend: {
					orient: "vertical",
					left: 10,
					data: ["直接访问", "邮件营销", "联盟广告", "视频广告", "搜索引擎"],
				},
				series: [
					{
						name: "访问来源",
						type: "pie",
						radius: ["50%", "70%"],
						avoidLabelOverlap: false,
						label: {
							show: false,
							position: "center",
						},
						emphasis: {
							label: {
								show: true,
								fontSize: "30",
								fontWeight: "bold",
							},
						},
						labelLine: {
							show: false,
						},
						data: [
							{ value: 335, name: "直接访问" },
							{ value: 310, name: "邮件营销" },
							{ value: 234, name: "联盟广告" },
							{ value: 135, name: "视频广告" },
							{ value: 1548, name: "搜索引擎" },
						],
					},
				],
			};

			myChart.setOption(option);
		},
	},
	props: {
		msg: String,
	},
};
</script>

<style scoped></style>
