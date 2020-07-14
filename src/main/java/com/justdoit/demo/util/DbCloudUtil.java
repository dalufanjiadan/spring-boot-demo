package com.justdoit.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class DbCloudUtil {

	private final static String USERNAME = "dbcloud_ba01";
	private final static String URL = "http://dbcloud.youzu.com/dc-flash-server";
	private final static String KEY = "96a53de9775eba7ed0d9eff646ef472d";
	private final static String CLIENT_ID = "3";

	/**
	 * 异步查询
	 * 
	 * @return
	 */
	public static String doQueryAsync(String sql) {
		String uri = "/presto/submitExecuteJob";

		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

		TreeMap<String, String> map = new TreeMap<>();
		map.put("_key", KEY);
		map.put("client_id", CLIENT_ID);
		map.put("user_name", USERNAME);
		map.put("sql", sql);
		map.put("timestamp", timestamp);
		map.put("_sign", md5(mapToString(map)));

		try {
			sql = URLEncoder.encode(sql, "UTF-8");
			map.put("sql", sql);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		map.remove("_key");

		String url = URL + uri + "?" + mapToString(map);

		JsonNode response = doPost(url, null);

		return response.get("data").get("task_id").asText();
	}

	/**
	 * 根据task_id 查询结束后存到临时表
	 */
	public static Object saveToTable(String taskId) {
		// 根据task_id 查询任务状态
		String uri = "/task/getTaskBySerialNo";
		boolean status = false;

		System.out.println(taskId);

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("_key", KEY);
		map.put("client_id", CLIENT_ID);
		map.put("serial_no", taskId);

		while (status == false) {

			String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
			map.put("timestamp", timestamp);
			map.put("_sign", md5(mapToString(map)));
			String url = URL + uri + "?" + mapToString(map);

			System.out.println(url);

			JsonNode response = doPost(url, null);
			return response;
			// System.out.println("成功".equals(response.get("data").get("jobs").get("status")));

			// if (status == true) {

			// } else {
			// try {
			// Thread.currentThread().sleep(60 * 1000);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			// }

			// break;
		}

		// // 状态完成生成临时表
		// uri = "/hive/uploadTmpTable";
		// map.clear();

		// // client_idint是客户端编号，向数据中心申请分配
		// // db_namestring是数据库名称
		// // sqlstring是上创建表的sql语句
		// // task_idstring是被创建表查询的task_id
		// // timestamplong是当前时间戳
		// // _signstring是md5签名
		// // user_namestring是用户域账户

		return null;

	}

	/**
	 * db cloud post request
	 */
	private static JsonNode doPost(String url, Object body) {

		URI uri = null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e1) {
			System.out.println(e1);
		}

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

		ObjectMapper om = new ObjectMapper();

		try {
			return om.readTree(responseEntity.getBody());
		} catch (IOException e) {
			System.out.println(e);
		}

		return null;
	}

	private static void doPostRequest(String url) {
		URL obj;
		try {
			obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// 默认值GET
			con.setRequestMethod("POST");

			// 添加请求头
			con.setRequestProperty("Content-Type", "application/json");

			int responseCode = con.getResponseCode();
			System.out.println("Sending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// 打印结果
			System.out.println(response.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String mapToString(Map<String, String> map) {
		return map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining("&"));
	}

	private static String md5(String sourceStr) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException:" + e);
		}
		return result;
	}

}