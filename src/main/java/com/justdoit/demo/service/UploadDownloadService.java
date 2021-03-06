package com.justdoit.demo.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.justdoit.demo.mapper.UploadDownloadMapper;
import com.justdoit.demo.model.DBFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class UploadDownloadService {

	@Autowired
	private UploadDownloadMapper mapper;

	public Map<String, Object> storeFile(MultipartFile file) throws Exception {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
			}

			DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes(), file.getSize());
			mapper.insertFile(dbFile);

			String fileDownloadUri = ServletUriComponentsBuilder//
					.fromCurrentContextPath()//
					.path("/api/v1/download-file/")//
					.path(dbFile.getId())//
					.toUriString();

			Map<String, Object> result = new HashMap<>();
			result.put("name", dbFile.getFileName());
			result.put("size", dbFile.getSize());
			result.put("type", dbFile.getFileType());
			result.put("downloadUri", fileDownloadUri);

			return result;

		} catch (IOException ex) {
			throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
		}

	}

	public DBFile getFile(long fileId) throws Exception {
		return mapper.findById(fileId).orElseThrow(() -> new Exception("File not found with id " + fileId));
	}

	public Object deleteFile(long fileId) {
		return mapper.deleteFile(fileId);
	}

	public Object getFiles(int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<Map<String, Object>> files = mapper.findAllFiles();
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(files);

		Map<String, Object> result = new HashMap<>();
		result.put("total", pageInfo.getTotal());
		result.put("data", files);

		return result;
	}
}