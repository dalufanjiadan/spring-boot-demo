package com.justdoit.demo.mapper;

import java.util.Optional;

import com.justdoit.demo.model.DBFile;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UploadDownloadMapper {

	int insertFile(DBFile dbFile);

	Optional<DBFile> findById(long fileId);
}