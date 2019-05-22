package com.liyulin.demo.mall.product;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.liyulin.demo.common.support.annotation.DefaultSmartSpringCloudApplication;
import com.liyulin.demo.common.util.SpringUtil;

import io.swagger.models.Swagger;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.web.Swagger2Controller;

@DefaultSmartSpringCloudApplication
@RestController
public class Application {
	
//	@Autowired
//	private DocumentationCache documentationCache;
	@Value("${spring.application.name}")
	private String appName;
	
	@Bean
	@ConditionalOnMissingBean
	public Swagger2Controller initSwagger2Controller(Environment environment, DocumentationCache documentationCache,
			ServiceModelToSwagger2Mapper mapper, JsonSerializer jsonSerializer) {
		return new Swagger2Controller(environment, documentationCache, mapper, jsonSerializer);
	}

	@GetMapping("xxx")
	public ResponseEntity<Json> getDocumentation(
			HttpServletRequest servletRequest) {
		Swagger2Controller swagger2Controller = SpringUtil.getBean(Swagger2Controller.class);
		ResponseEntity<Json> responseEntity = swagger2Controller.getDocumentation(appName, servletRequest);
		Json json = responseEntity.getBody();
		Swagger swagger = JSONObject.parseObject(json.value(), Swagger.class);
		return responseEntity;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}