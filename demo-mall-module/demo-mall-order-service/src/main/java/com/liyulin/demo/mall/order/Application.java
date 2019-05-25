package com.liyulin.demo.mall.order;

import org.springframework.boot.SpringApplication;

import com.liyulin.demo.common.support.annotation.DefaultSmartSpringCloudApplication;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;

@DefaultSmartSpringCloudApplication
@EncryptablePropertySource(name = "orderEncryptedProperties", value = "classpath*:application-db-order.yml")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}