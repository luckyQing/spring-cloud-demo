package com.liyulin.demo.mall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.liyulin.demo.common.support.annotation.MainService;
import com.liyulin.demo.mybatis.autoconfigure.MultipleDataSourceAutoConfiguration;

@MainService
@Import({MultipleDataSourceAutoConfiguration.class})
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}