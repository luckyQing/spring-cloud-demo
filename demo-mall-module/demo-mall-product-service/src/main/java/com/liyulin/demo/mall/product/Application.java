package com.liyulin.demo.mall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;

import com.liyulin.demo.common.support.annotation.DefaultSmartSpringCloudApplication;

@DefaultSmartSpringCloudApplication
@RestController
public class Application {
	
//	@Autowired
//	private DocumentationCache documentationCache;
//	@Value("${spring.application.name}")
//	private String appName;
//	
//	@Bean
//	@ConditionalOnMissingBean
//	public Swagger2Controller initSwagger2Controller(Environment environment, DocumentationCache documentationCache,
//			ServiceModelToSwagger2Mapper mapper, JsonSerializer jsonSerializer) {
//		return new Swagger2Controller(environment, documentationCache, mapper, jsonSerializer);
//	}
//
//	@GetMapping("xxx")
//	public ResponseEntity<Json> getDocumentation(
//			HttpServletRequest servletRequest) {
//		Swagger2Controller swagger2Controller = SpringUtil.getBean(Swagger2Controller.class);
//		ResponseEntity<Json> responseEntity = swagger2Controller.getDocumentation(appName, servletRequest);
//		Json json = responseEntity.getBody();
//		Swagger swagger = JSONObject.parseObject(json.value(), Swagger.class);
//		return responseEntity;
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}