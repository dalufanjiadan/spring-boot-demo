package com.justdoit.demo.mapper;

import com.justdoit.demo.model.DBFile;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UploadDownloadMapper {

	int insertFile(DBFile dbFile);
}