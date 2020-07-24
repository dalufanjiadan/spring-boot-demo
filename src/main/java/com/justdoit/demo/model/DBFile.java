package com.justdoit.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DBFile {

	private String id;
	private String fileName;
	private String fileType;
	private byte[] data;

	public DBFile(String fileName, String fileType, byte[] data) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}

}