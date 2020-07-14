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

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
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

		Map response = doPost(url, null);

		return ((Map) response.get("data")).get("task_id").toString();
	}

	/**
	 * 根据task_id 查询结束后存到临时表
	 */
	public static Object saveToTable(String taskId) {
		// 根据task_id 查询任务状态
		String uri = "/task/getTaskBySerialNo";
		int count = 10;

		System.out.println(taskId);

		TreeMap<String, String> map = new TreeMap<>();
		map.put("_key", KEY);
		map.put("client_id", CLIENT_ID);
		map.put("serial_no", taskId);

		while (count > 0) {
			map.remove("_sign");
			String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
			map.put("timestamp", timestamp);
			map.put("_sign", md5(mapToString(map)));
			String url = URL + uri + "?" + mapToString(map);
			Map response = doPost(url, null);
			String s = ((Map) ((Map) response.get("data")).get("jobs")).get("status").toString();

			if ("成功".equals(s)) {
				// bl = true;
				System.out.println(s);
				break;
			} else {
				count--;
				System.out.println(s);
				try {
					Thread.sleep(2 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// break;
		}
		System.out.println("over");

		// // 状态完成生成临时表
		// uri = "/hive/uploadTmpTable";
		// map.clear();

		String dbName = "db_temp";
		String tableName = taskId.replaceAll("-", "_");

		String hive_sql = "CREATE EXTERNAL TABLE tableName(" + "`device_id` String COMMENT '设备id') "
				+ "ROW FORMAT DELIMITED " + "  FIELDS TERMINATED BY '\t' " + "  LINES TERMINATED BY '\n' "
				+ "STORED AS INPUTFORMAT " + "  'org.apache.hadoop.mapred.TextInputFormat' " + "OUTPUTFORMAT"
				+ "  'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat' " + "LOCATION "
				+ " 'hdfs:///user/hive/warehouse/dbName.db/tableName'";

		String sql = hive_sql.replaceAll("tableName", tableName);
		sql = hive_sql.replaceAll("dbName", dbName);

		map.clear();
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

		String url = URL + "/hive/uploadTmpTable" + "?" + mapToString(map);
		doPostRequest(url);

		return null;
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

			ObjectMapper om = new ObjectMapper();

			try {
				JsonNode json = om.readTree(response.toString());
				System.out.println(json);
				System.out.println(json.get("return_comment"));
			} catch (IOException e) {
				System.out.println(e);
			}

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