package com.liyulin.demo.mall.product;

import org.springframework.boot.SpringApplication;

import com.liyulin.demo.common.support.annotation.DefaultSmartSpringCloudApplication;

//import io.swagger.models.Swagger;
//import io.swagger.parser.SwaggerParser;

@DefaultSmartSpringCloudApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
//		Swagger swagger = new SwaggerParser().read("http://localhost:20021/v2/api-docs?group=demoMallProductService");
//		System.err.println(JSON.toJSONString(swagger));
	}

}