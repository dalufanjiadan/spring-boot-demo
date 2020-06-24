package com.justdoit.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.justdoit.demo.mapper.ProductMapper;
import com.justdoit.demo.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	@Autowired
	private ProductMapper productMapper;

	@GetMapping
	public List<Product> getAll() {

		System.out.println(Thread.currentThread().toString()+LocalDateTime.now());
		
		return productMapper.findAll();
	}

}