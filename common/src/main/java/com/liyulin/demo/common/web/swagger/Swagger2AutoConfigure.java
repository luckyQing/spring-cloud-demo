package com.liyulin.demo.common.web.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import com.liyulin.demo.common.constants.CommonConstants;
import com.liyulin.demo.common.properties.SmartProperties;

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
@ConditionalOnProperty(prefix = CommonConstants.SMART_PROPERTIES_PREFIX, name = SmartProperties.PropertiesName.SWAGGER, havingValue = "true", matchIfMissing = false)
public class Swagger2AutoConfigure {

	@Value("${spring.application.name}")
	private String groupName;
	@Autowired
	private SmartProperties smartProperties;

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName(groupName)
				.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false)
				.forCodeGeneration(false).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage(CommonConstants.BASE_PACAKGE)).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("cc", "https://swagger.io", "1634753825@qq.com");
		return new ApiInfoBuilder().title("API接口文档").description("swagger2").contact(contact).version(smartProperties.getApiVersion()).build();
	}

}