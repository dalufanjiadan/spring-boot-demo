import request from "@/util/request";

export function getFiles(params) {
	return request({
		url: "/api/v1/upload-files",
		method: "get",
		params,
	});
}

export function downloadFile(id) {
	return request({
		url: `/api/v1/download-file/${id}`,
		method: "get",
	});
}

export const api = {
	getFiles,
	downloadFile,
};
