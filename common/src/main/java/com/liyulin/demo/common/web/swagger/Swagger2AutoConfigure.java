package com.liyulin.demo.common.web.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import com.liyulin.demo.common.constants.CommonConstants;
import com.liyulin.demo.common.properties.SmartProperties;
import com.liyulin.demo.common.properties.SwaggerProperties;
import com.liyulin.demo.common.support.annotation.ConditionalOnPropertyBoolean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2配置
 *
 * @author liyulin
 * @date 2019年4月22日上午12:31:04
 */
@Configuration
@EnableSwagger2
@ConditionalOnPropertyBoolean(name="smart.swagger.enable")
public class Swagger2AutoConfigure {

	@Autowired
	private SmartProperties smartProperties;

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName(smartProperties.getSwagger().getGroupName())
				.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false)
				.forCodeGeneration(false).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage(CommonConstants.BASE_PACAKGE)).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		SwaggerProperties swagger = smartProperties.getSwagger();
		Contact contact = new Contact(swagger.getName(), swagger.getUrl(),
				swagger.getEmail());
		return new ApiInfoBuilder()
				.title(swagger.getTitle())
				.description(swagger.getDescription())
				.contact(contact)
				.version(smartProperties.getApi().getApiVersion())
				.build();
	}

}