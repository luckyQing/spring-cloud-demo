package com.liyulin.demo.common.web.autoconfigure;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.liyulin.demo.common.web.interceptor.RepeatSubmitCheckInterceptor;

@Configuration
public class SmartWebMvcConfigurer implements WebMvcConfigurer {

	@Autowired(required=false)
	private RepeatSubmitCheckInterceptor repeatSubmitCheckInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (repeatSubmitCheckInterceptor != null) {
			registry.addInterceptor(repeatSubmitCheckInterceptor);
		}
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(buildJsonHttpMessageConverter());
	}

	private FastJsonHttpMessageConverter buildJsonHttpMessageConverter() {
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteBigDecimalAsPlain,
				SerializerFeature.WriteEnumUsingToString,
				// 禁用“循环引用检测”
				SerializerFeature.DisableCircularReferenceDetect);

		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		fastJsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		return fastJsonHttpMessageConverter;
	}

}