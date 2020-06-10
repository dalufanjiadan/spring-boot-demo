package com.justdoit.demo.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

	@Override
	public boolean isValid(String phone, ConstraintValidatorContext context) {
		// 校验手机格式
		return Pattern.matches("^1[3-9]\\d{9}", phone);
	}
}