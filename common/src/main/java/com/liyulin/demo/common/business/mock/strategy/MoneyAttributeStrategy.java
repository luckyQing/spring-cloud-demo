package com.liyulin.demo.common.business.mock.strategy;

import java.lang.annotation.Annotation;
import java.util.List;

import com.liyulin.demo.common.util.RandomUtil;

import uk.co.jemos.podam.common.AttributeStrategy;

/**
 * 金额mock生成策略
 *
 * @author liyulin
 * @date 2019年4月17日下午10:37:39
 */
public class MoneyAttributeStrategy implements AttributeStrategy<Long> {

	@Override
	public Long getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
		return (long) RandomUtil.generateRangeRandom(100, 1000000);
	}

}