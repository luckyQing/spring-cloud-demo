package com.liyulin.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import com.liyulin.demo.biz.oms.ProductInfoOmsBiz;
import com.liyulin.demo.common.annotation.MainService;
import com.liyulin.demo.rpc.product.request.oms.ProductInsertReqBody;

@MainService
public class Application {

	@Autowired
	private ProductInfoOmsBiz productOmsBiz;

//	@PostConstruct
//	public void test() {
//		ProductInsertReqBody reqBody = new ProductInsertReqBody();
//		reqBody.setName("手机x");
//		reqBody.setSellPrice(1231L);
//		reqBody.setStock(10000L);
//		productOmsBiz.insert(reqBody);
//	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}