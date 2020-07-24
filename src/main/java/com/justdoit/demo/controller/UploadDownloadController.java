package com.justdoit.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import com.justdoit.demo.service.UploadDownloadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

		service.storeFile(file);

		// FileUtil.readLines(file, charset)

		return null;

		// DBFile dbFile = dbFileStorageService.storeFile(file);

		// String fileDownloadUri =
		// ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
		// .path(dbFile.getId()).toUriString();

		// return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
		// file.getContentType(), file.getSize());
	}

	// @PostMapping("/uploadMultipleFiles")
	// public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files")
	// MultipartFile[] files) {
	// return Arrays.asList(files).stream().map(file ->
	// uploadFile(file)).collect(Collectors.toList());
	// }

	// @GetMapping("/downloadFile/{fileId}")
	// public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
	// // Load file from database
	// DBFile dbFile = dbFileStorageService.getFile(fileId);

	// return
	// ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType()))
	// .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
	// dbFile.getFileName() + "\"")
	// .body(new ByteArrayResource(dbFile.getData()));
	// }

}