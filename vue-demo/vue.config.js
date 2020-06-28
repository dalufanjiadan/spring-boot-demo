const path = require("path");

const port = 3000; // dev port

module.exports = {
	outputDir: path.resolve(__dirname, "../src/main/resources/static"),
	devServer: {
		port: port,
	},
	configureWebpack: {
		devtool: "source-map",
	},
};
