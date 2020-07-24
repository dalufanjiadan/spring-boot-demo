package com.justdoit.demo.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

		System.out.println(fileName);

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
			}

			DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
			mapper.insertFile(dbFile);
			System.out.println(dbFile);

			String fileDownloadUri = ServletUriComponentsBuilder//
					.fromCurrentContextPath()//
					.path("/api/v1/download-file/")//
					.path(dbFile.getId())//
					.toUriString();

			Map<String, Object> result = new HashMap<>();
			result.put("name", dbFile.getFileName());
			result.put("size", file.getSize());
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
}