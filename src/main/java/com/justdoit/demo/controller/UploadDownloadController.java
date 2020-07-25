package com.justdoit.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.pagehelper.PageHelper;
import com.justdoit.demo.model.DBFile;
import com.justdoit.demo.model.RestResponse;
import com.justdoit.demo.service.UploadDownloadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/v1/demo/files")
@Api(tags = "demo-files")
public class UploadDownloadController {

	@Autowired
	private UploadDownloadService service;

	/**
	 * 文件存到数据库
	 * 
	 * 还是存到磁盘吧 数据库存个路径
	 */
	public Object uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Map<String, Object> params)
			throws Exception {

		return RestResponse.ok(service.storeFile(file));
	}

	@PostMapping
	public Object uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		List<Map<String, Object>> result = Arrays.asList(files).stream().map(file -> {
			try {
				return service.storeFile(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());

		return RestResponse.ok(result);
	}

	@DeleteMapping("/{fileId}")
	public Object deleteFile(@PathVariable long fileId) {

		return RestResponse.ok(service.deleteFile(fileId));
	}

	@GetMapping
	public RestResponse<Object> getFiles(@RequestParam int currentPage, @RequestParam int pageSize) {

		return RestResponse.ok(service.getFiles(currentPage, pageSize));
	}

	@GetMapping("/{fileId}/download")
	public Object downloadFile(@PathVariable long fileId) throws Exception {
		// Load file from database
		DBFile dbFile = service.getFile(fileId);

		

		return ResponseEntity.ok()//
				.contentType(MediaType.parseMediaType(dbFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
				.body(new ByteArrayResource(dbFile.getData()));
	}
}