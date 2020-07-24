import request from "@/util/request";

export function getTodos(query) {
	return request({
		url: "/api/v1/todos",
		method: "get",
		params: query,
	});
}

export function createTodo(data) {
	return request({
		url: "/api/v1/todos",
		method: "post",
		data,
	});
}

export function deleteTodo(id) {
	return request({
		url: "/api/v1/todos/" + id,
		method: "delete",
	});
}

export function updateTodo(id, data) {
	return request({
		url: "/api/v1/todos/" + id,
		method: "put",
		data,
	});
}

export function fetchList(query) {
	return request({
		url: "/vue-element-admin/article/list",
		method: "get",
		params: query,
	});
}

export function fetchArticle(id) {
	return request({
		url: "/vue-element-admin/article/detail",
		method: "get",
		params: { id },
	});
}

export function fetchPv(pv) {
	return request({
		url: "/vue-element-admin/article/pv",
		method: "get",
		params: { pv },
	});
}

export function createArticle(data) {
	return request({
		url: "/vue-element-admin/article/create",
		method: "post",
		data,
	});
}

export function updateArticle(data) {
	return request({
		url: "/vue-element-admin/article/update",
		method: "post",
		data,
	});
}

export const api = {
	getTodos,
	createTodo,
	updateTodo,
	deleteTodo,
};
