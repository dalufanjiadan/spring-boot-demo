package com.justdoit.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import com.justdoit.demo.model.DBFile;
import com.justdoit.demo.model.RestResponse;
import com.justdoit.demo.service.UploadDownloadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "upload/download")
public class UploadDownloadController {

	@Autowired
	private UploadDownloadService service;

	@PostMapping("/upload-file")
	public Object uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Map<String, Object> params)
			throws Exception {

		System.out.println(file.getOriginalFilename());
		Path path = Paths.get("/Users/gecheng/temp", "test.txt");

		System.out.println(params);

		Stream<String> lines = Files.lines(path);

		lines.forEach(System.out::println);

		return RestResponse.ok(service.storeFile(file));
	}

	// @PostMapping("/uploadMultipleFiles")
	// public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files")
	// MultipartFile[] files) {
	// return Arrays.asList(files).stream().map(file ->
	// uploadFile(file)).collect(Collectors.toList());
	// }

	@GetMapping("/download-file/{fileId}")
	public Object downloadFile(@PathVariable long fileId) throws Exception {
		// Load file from database
		DBFile dbFile = service.getFile(fileId);

		return ResponseEntity.ok()//
				.contentType(MediaType.parseMediaType(dbFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
				.body(new ByteArrayResource(dbFile.getData()));
	}

}