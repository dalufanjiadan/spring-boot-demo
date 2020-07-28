import request from "@/util/request";

const OAUTH2_REDIRECT_URI = "http://localhost:3000/#/home";
// const GITHUB_AUTH_URL = "/oauth2/authorize/github?redirect_uri=" + OAUTH2_REDIRECT_URI;

export function getOauth2Uri(thirdParty) {
	return (
		process.env.VUE_APP_BASE_API +
		`oauth2/authorize/${thirdParty}?redirect_uri=` +
		OAUTH2_REDIRECT_URI
	);
}

export function getUserInfo() {
	return request({
		url: "/api/v1/user/me",
		method: "get",
	});
}

export const api = {
	getOauth2Uri,
	getUserInfo,
};
