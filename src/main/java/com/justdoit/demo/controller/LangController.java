package com.justdoit.demo.controller;

import java.util.Locale;

import com.justdoit.demo.model.RestResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1/lang")
@Api(tags = "国际化11", description = "国际化")
public class LangController {

	@ApiOperation("切换语言")
	@GetMapping("/change")
	@ApiParam(name = "lang", value = "语言", required = true)
	public Object changeLanguage(@RequestParam(name = "lang") String lang) {
		// 在LocaleChangeInterceptor中已切换，此处无需处理
		return getLanguage();
	}

	@ApiOperation("获得当前语言")
	@GetMapping
	public Object getLanguage() {
		Locale locale = LocaleContextHolder.getLocale();
		return RestResponse.ok(locale.toString());
	}
}