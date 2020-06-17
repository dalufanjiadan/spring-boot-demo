package com.justdoit.demo.filter;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justdoit.demo.config.ClientConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SecurityFilter implements Filter {

	@Autowired
	private ObjectMapper om;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		Map<String, String[]> parameterMap = req.getParameterMap();
		req.getParameterNames();

		String[] clientId = parameterMap.getOrDefault("clientId", null);
		String timeStamp = parameterMap.get("timeStamp")[0];
		long now = System.currentTimeMillis();

		int flag = 0;

		if (clientId == null) {
			flag = 1;
		} else if (now - Long.parseLong(timeStamp) > 10000) {
			flag = 2;
		} else {
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			map.put("url", req.getRequestURI());
			map.put("timeStamp", timeStamp);
			map.put("clientId", clientId[0]);
			map.put("secret", ClientConfig.clients.get(clientId[0]));

			String sign = generateSign(map);
			if (!sign.equals(parameterMap.get("sign")[0])) {
				flag = 3;
			}
		}

		Map<String, String> result = new HashMap<>();
		switch (flag) {
			case 0:
				chain.doFilter(request, response);
				break;
			case 1:
				result.put("msg", "没有权限");
				result.put("code", "-10001");
				break;
			case 2:
				result.put("msg", "时间戳过期");
				result.put("code", "-10002");
				break;
			case 3:
				result.put("msg", "签名错误");
				result.put("code", "-10003");
				break;

			default:
				break;
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(om.writeValueAsString(result));
	}

	private String generateSign(LinkedHashMap<String, String> map) {

		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			sb.append(key);
			sb.append("-");
			sb.append(map.get(key));
		}

		return getMd5(sb.toString());
	}

	private String getMd5(String input) {
		try {
			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes());
			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);
			// Convert message digest into hex value
			String hashText = no.toString(16);
			while (hashText.length() < 32) {
				hashText = "0" + hashText;
			}
			return hashText;
		}
		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}