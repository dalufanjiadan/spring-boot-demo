package com.justdoit.demo.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.justdoit.demo.model.DBFile;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UploadDownloadMapper {

	int insertFile(DBFile dbFile);

	Optional<DBFile> findById(long fileId);

	@Delete("DELETE FROM file WHERE id = #{fileId}")
	int deleteFile(long fileId);

	List<Map<String, Object>> findAllFiles();
}