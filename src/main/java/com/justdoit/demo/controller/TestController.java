package com.justdoit.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import javax.validation.Valid;

import com.google.common.base.Splitter;
import com.justdoit.demo.kafka.KafKaProducerService;
import com.justdoit.demo.mapper.UserMapper;
import com.justdoit.demo.model.RestResponse;
import com.justdoit.demo.model.User;
import com.justdoit.demo.util.DbCloudUtil;
import com.opencsv.CSVWriter;

import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "test")
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

	// @Autowired
	// private KafKaProducerService kafKaProducerService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserMapper userMapper;

	@ApiOperation(value = "1 hello")
	@GetMapping("/hello")
	public Object hello() {

		String sql = "SELECT account from data_analyze_label.dm_label_gamelog_kpi_account_ds where game_id=360 limit 8";

		// sql="SELECT account FROM data_analyze_label.dm_label_gamelog_kpi_account_ds
		// WHERE game_id = 360 AND ds = '20200510' AND last_login_date > '2020-05-01'
		// UNION SELECT account FROM data_analyze_label.dm_label_gamelog_kpi_account_ds
		// WHERE game_id = 360 AND ds = '20200510' AND last_login_date > '2020-05-01'";
		sql = sql.replaceAll("\n", " ");
		sql = sql.replaceAll("\t", " ");

		System.out.println(sql);

		String taskId = DbCloudUtil.doQueryAsync(sql);

		System.out.println(taskId);

		return DbCloudUtil.saveToTable(taskId);

	}

	@GetMapping("/hello1")
	public RestResponse<Map<String, Object>> hello1(@RequestParam Map<String, Object> params) {

		System.out.println(params);

		return RestResponse.ok(params);
	}

	@ApiOperation(value = "2 lang")
	@GetMapping("/lang")
	public Object lang() {
		Locale locale = LocaleContextHolder.getLocale();
		System.out.println(messageSource.getMessage("hello", null, locale));

		return RestResponse.ok("hello");
	}

	@GetMapping("/mapper")
	public Object mapper() {

		System.out.println(userMapper.findAll());

		return RestResponse.ok(userMapper.findAll());
	}

	// 发送消息方法
	public void send() {

		// User user = new User(11L, "username", "password", "phone");

		// kafKaProducerService.sendMessage("message" + System.currentTimeMillis());
		// kafKaProducerService.saveCreateUserLog(user);

	}

	@PostMapping("/upload")
	public String txtToCsv(@RequestParam("file") MultipartFile file) {

		try {
			byte[] bytes = file.getBytes();

			String data = new String(bytes, StandardCharsets.UTF_8);
			System.out.println(data);


			Splitter splitter = Splitter.on(",").trimResults();
			List<String> ids = splitter.splitToList(data);

			
			
			

			String path = "./upload/demo1.csv";
			try (//
					Writer writer = Files.newBufferedWriter(Paths.get(path));
					CSVWriter csvWriter = new CSVWriter(writer, //
							CSVWriter.DEFAULT_SEPARATOR, //
							CSVWriter.NO_QUOTE_CHARACTER, //
							CSVWriter.DEFAULT_ESCAPE_CHARACTER, //
							CSVWriter.DEFAULT_LINE_END);//
			) {
				String[] headerRecord = { "id"};
				csvWriter.writeNext(headerRecord);

				for (int i = 0; i < ids.size(); i++) {
					
					csvWriter.writeNext(
							new String[] { ids.get(i) });
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "hello";
	}

	// @PostMapping("/upload")
	public String singleFileUpload(@RequestParam("file") MultipartFile file) {

		try {
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();

			System.out.println("===");

			

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "hello";
	}

	@GetMapping("/csv")
	void generatingCsv() throws IOException {

		Resource resource = new PathResource("./upload/test.txt");
		// new ClassPathResource("classpath:data.txt");
		InputStream inputStream = resource.getInputStream();
		try {
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			String data = new String(bdata, StandardCharsets.UTF_8);

			System.out.println(data);
		} catch (IOException e) {
		}

		String path = "./upload/demo.csv";
		try (//
				Writer writer = Files.newBufferedWriter(Paths.get(path));
				CSVWriter csvWriter = new CSVWriter(writer, //
						CSVWriter.DEFAULT_SEPARATOR, //
						CSVWriter.NO_QUOTE_CHARACTER, //
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, //
						CSVWriter.DEFAULT_LINE_END);//
		) {
			String[] headerRecord = { "Name", "Email", "Phone", "Country" };
			csvWriter.writeNext(headerRecord);
			csvWriter
					.writeNext(new String[] { "Sundar Pichai ♥", "sundar.pichai@gmail.com", "+1-1111111111", "India" });
			csvWriter
					.writeNext(new String[] { "Satya Nadella", "satya.nadella@outlook.com", "+1-1111111112", "India" });
		}
	}


	@GetMapping("/uploadToHive")
	public void uploadToHive() {
		DbCloudUtil.upload();
	}
}