package com.liyulin.demo.common.business.mock.strategy;

import java.lang.annotation.Annotation;
import java.util.List;

import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;

import uk.co.jemos.podam.common.AttributeStrategy;

public class RespHeadCodeAttributeStrategy implements AttributeStrategy<String> {

	@Override
	public String getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
		return ReturnCodeEnum.SUCCESS.getCode();
	}

}