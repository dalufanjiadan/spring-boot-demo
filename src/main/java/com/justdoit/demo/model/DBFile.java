package com.justdoit.demo.model;

import lombok.Data;

@Data
public class DBFile {

	private String id;
	private String fileName;
	private String fileType;
	private byte[] data;
	private Long size;

	public DBFile(String fileName, String fileType, byte[] data, Long size) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
		this.size = size;
	}

}