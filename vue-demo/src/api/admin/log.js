import request from "@/util/request";

export function getLog() {
	return request({
		url: "/test/hello",
		method: "get",
	});
}

export const api = {
	getLog,
};
