package com.liyulin.demo.common.business.mock.strategy;

import java.lang.annotation.Annotation;
import java.util.List;

import com.liyulin.demo.common.util.RandomUtil;

import uk.co.jemos.podam.common.AttributeStrategy;

/**
 * 手机号码mock生成策略
 *
 * @author liyulin
 * @date 2019年4月17日下午10:37:33
 */
public class MobileAttributeStrategy implements AttributeStrategy<String> {

	@Override
	public String getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
		return "1" + RandomUtil.generateRangeRandom(3, 8) + RandomUtil.generateRandom(true, 9);
	}

}