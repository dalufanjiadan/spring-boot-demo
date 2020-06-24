package com.justdoit.demo.mapper;

import java.util.List;
import java.util.Map;

import com.justdoit.demo.model.Product;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductMapper {

	int insertProduct(Product product);

	@Select("select * from product")
	List<Product> findAll();
}