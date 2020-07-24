package com.justdoit.demo.service;

import java.io.IOException;

import com.justdoit.demo.mapper.UploadDownloadMapper;
import com.justdoit.demo.model.DBFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadDownloadService {

	@Autowired
	private UploadDownloadMapper mapper;

	public DBFile storeFile(MultipartFile file) throws Exception {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		System.out.println(fileName);

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
			}

			DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

			System.out.println(dbFile);

			mapper.insertFile(dbFile);
		} catch (IOException ex) {
			throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
		}

		return null;
	}

	// public DBFile getFile(String fileId) {
	// return dbFileRepository.findById(fileId)
	// .orElseThrow(() -> new MyFileNotFoundException("File not found with id " +
	// fileId));
	// }
}