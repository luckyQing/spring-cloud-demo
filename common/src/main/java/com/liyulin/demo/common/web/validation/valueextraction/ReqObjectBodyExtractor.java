package com.liyulin.demo.common.web.validation.valueextraction;

import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;

import com.liyulin.demo.common.business.dto.ReqObjectBody;

public class ReqObjectBodyExtractor implements ValueExtractor<ReqObjectBody<@ExtractedValue ?>> {

	public static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(
			new ReqObjectBodyExtractor());

	private ReqObjectBodyExtractor() {
	}

	@Override
	public void extractValues(ReqObjectBody<@ExtractedValue ?> originalValue, ValueReceiver receiver) {
		receiver.value(null, originalValue.getObject());
	}

}