package com.justdoit.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
	private final Auth auth = new Auth();
	private final OAuth2 oauth2 = new OAuth2();

	@Data
	public static final class Auth {
		private String tokenSecret;
		private long tokenExpirationMsec;
	}

	@Data
	public static final class OAuth2 {
		private List<String> authorizedRedirectUris = new ArrayList<>();
	}
}