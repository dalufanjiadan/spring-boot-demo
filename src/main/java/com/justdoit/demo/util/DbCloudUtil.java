package com.justdoit.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
import com.justdoit.demo.model.RestResponse;

import org.springframework.core.io.PathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class DbCloudUtil {

	private final static String USERNAME = "dbcloud_ba01";
	private final static String BASE_URL = "http://dbcloud.youzu.com/dc-flash-server";
	private final static String KEY = "96a53de9775eba7ed0d9eff646ef472d";
	private final static String CLIENT_ID = "3";

	/**
	 * 异步查询
	 * 
	 * @return
	 */
	public static String doQueryAsync(String sql) {

		TreeMap<String, String> map = new TreeMap<>();
		map.put("_key", KEY);
		map.put("client_id", CLIENT_ID);
		map.put("user_name", USERNAME);
		map.put("sql", sql);
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		map.put("timestamp", timestamp);
		map.put("_sign", md5(mapToString(map)));

		try {
			sql = URLEncoder.encode(sql, "UTF-8");
			map.put("sql", sql);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		map.remove("_key");

		String url = BASE_URL + "/presto/submitExecuteJob" + "?" + mapToString(map);
		JsonNode response = doPostRequest(url);

		System.out.println(response);

		return response.get("data").get("task_id").asText();
	}

	/**
	 * 根据task_id 查询结束后存到临时表
	 */
	public static Object saveToTable(String taskId) {
		// 最多睡眠十次
		int count = 10;
		TreeMap<String, String> map = new TreeMap<>();
		map.put("_key", KEY);
		map.put("client_id", CLIENT_ID);
		map.put("serial_no", taskId);

		while (count > 0) {
			map.remove("_sign");
			String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
			map.put("timestamp", timestamp);
			map.put("_sign", md5(mapToString(map)));
			// 查看task_id 的运行状态，完成后存到临时表
			String url = BASE_URL + "/task/getTaskBySerialNo" + "?" + mapToString(map);
			JsonNode response = doPostRequest(url);
			String status = response.get("data").get("jobs").get("status").asText();

			System.out.println(status);
			if ("成功".equals(status)) {
				break;
			} else {
				count--;
				try {
					Thread.sleep(5 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("over");

		uploadTmpTable(taskId);

		return RestResponse.ok();
	}

	private static void uploadTmpTable(String taskId) {

		String dbName = "db_temp";
		String tableName = "user_cluster_" + taskId.replaceAll("-", "_");

		String sql = "CREATE EXTERNAL TABLE tableName(" + "`device_id` String COMMENT '设备id') "
				+ "ROW FORMAT DELIMITED " + "  FIELDS TERMINATED BY '\t' " + "  LINES TERMINATED BY '\n' "
				+ "STORED AS INPUTFORMAT " + "  'org.apache.hadoop.mapred.TextInputFormat' " + "OUTPUTFORMAT"
				+ "  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat' " + "LOCATION "
				+ " 'hdfs:///user/hive/warehouse/dbName.db/tableName'";

		sql = sql.replaceAll("tableName", tableName);
		sql = sql.replaceAll("dbName", dbName);

		TreeMap<String, String> map = new TreeMap<>();
		map.put("_key", KEY);
		map.put("client_id", CLIENT_ID);
		map.put("sql", sql);
		map.put("task_id", taskId);
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		map.put("timestamp", timestamp);
		map.put("user_name", USERNAME);
		map.put("_sign", md5(mapToString(map)));

		try {
			sql = URLEncoder.encode(sql, "UTF-8");
			map.put("sql", sql);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String url = BASE_URL + "/hive/uploadTmpTable" + "?" + mapToString(map);
		doPostRequest(url);

		// 刷新
		resfreshTableMeta(dbName, tableName);
	}

	/**
	 * 刷新元数据
	 * 
	 * 建表不一定立即生效
	 */
	private static void resfreshTableMeta(String dbName, String tableName) {
		TreeMap<String, String> map = new TreeMap<>();
		map.clear();
		map.put("_key", KEY);
		map.put("client_id", CLIENT_ID);
		map.put("db_name", dbName);
		map.put("table_name", tableName);
		map.put("op", "create");
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		map.put("timestamp", timestamp);

		map.put("_sign", md5(mapToString(map)));

		String url = BASE_URL + "/hive/meta/resfreshTableMeta" + "?" + mapToString(map);
		doPostRequest(url);
	}

	private static JsonNode doPostRequest(String url) {
		URL obj;
		try {
			obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// 默认值GET
			con.setRequestMethod("POST");

			// 添加请求头
			con.setRequestProperty("Content-Type", "application/json");

			int responseCode = con.getResponseCode();
			// System.out.println("Sending 'POST' request to URL : " + url);
			// System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// 打印结果
			// System.out.println(response.toString());

			ObjectMapper om = new ObjectMapper();

			try {
				JsonNode json = om.readTree(response.toString());
				// System.out.println(json);
				return json;
			} catch (IOException e) {
				System.out.println(e);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
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

	/**
	 * db cloud post request
	 */
	private static Map doPost(String url, Object body) {

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
		ResponseEntity<Map> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, Map.class);

		// ObjectMapper om = new ObjectMapper();

		// try {
		// return om.readTree(responseEntity.getBody());
		// } catch (IOException e) {
		// System.out.println(e);
		// }

		return responseEntity.getBody();
	}

	public static void upload() {
		TreeMap<String, String> map = new TreeMap<>();
		map.put("_key", KEY);
		map.put("client_id", CLIENT_ID);
		map.put("db_name", "db_temp");
		// map.put("file", "demo1");
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		map.put("timestamp", timestamp);
		map.put("table_name", "user_cluster_cc3115bd_90f0_4bca_8e80_4df65abad0e4");
		map.put("user_name", USERNAME);
		map.put("_sign", md5(mapToString(map)));

		String url = BASE_URL + "/hive/uploadDataToTmpTable" + "?" + mapToString(map);
		doPostUploadRequest(url);
	}

	private static JsonNode doPostUploadRequest(String url) {

		URI uri = null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", new PathResource("./upload/demo1.csv"));
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity,
				String.class);
		// ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST,
		// entity, String.class);

		System.out.println(responseEntity);

		return null;
	}
}