package com.liyulin.demo.common.web.validation.valueextraction;

import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

import com.liyulin.demo.common.business.dto.Req;

/**
 * {@link Req}泛型参数T校验生效
 *
 * @author liyulin
 * @date 2019年3月29日下午11:13:23
 */
public class ReqExtractor implements ValueExtractor<Req<@ExtractedValue ?>> {

	public static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ReqExtractor());

	private ReqExtractor() {
	}

	@Override
	public void extractValues(Req<?> originalValue, ValueReceiver receiver) {
		receiver.value(null, originalValue.getBody());
	}

}