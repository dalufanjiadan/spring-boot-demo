const path = require("path");

module.exports = {
	outputDir: path.resolve(__dirname, "../src/main/resources/static"),
	configureWebpack: {
		devtool: "source-map",
	},
	css: {
		// css预设器配置项
		// loaderOptions: {
		// 	postcss: {
		// 		plugins: [
		// 			require("postcss-px2rem")({
		// 				remUnit: 100,
		// 			}),
		// 		],
		// 	},
		// },
	},
};
