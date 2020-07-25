import request from "@/util/request";

const url = "/api/v1/demo/files";

export function deleteFile(id) {
	return request({
		url: `${url}/${id}`,
		method: "delete",
	});
}

export function getFiles(params) {
	return request({
		url: url,
		method: "get",
		params,
	});
}

export function downloadFile(id) {
	return request({
		url: `${url}/${id}/download`,
		method: "get",
	});
}

export const api = {
	getFiles,
	downloadFile,
	deleteFile,
};
