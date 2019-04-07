package com.liyulin.demo.order;

import org.springframework.boot.SpringApplication;

import com.liyulin.demo.common.annotation.MainService;

@MainService
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}