package com.liyulin.demo;

import org.slf4j.MDC;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Application {
	
	public static void main(String[] args) {
//		MDC.put("appName", "nodeA");
		SpringApplication.run(Application.class, args);
	}
	
}