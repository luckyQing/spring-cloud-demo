package com.liyulin.demo.mall.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.liyulin.demo.common.annotation.MainService;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.ReqObjectBody;
import com.liyulin.demo.mall.product.mapper.base.ProductInfoBaseMapper;
import com.liyulin.demo.mall.product.service.rpc.ProductInfoRpcService;
import com.liyulin.demo.rpc.product.request.rpc.UpdateStockReqBody;

@MainService
public class Application {

	@Autowired
	private ProductInfoBaseMapper productInfoBaseMapper;
	@Autowired
	private ProductInfoRpcService productInfoRpcService;


	public void test() {
		List<UpdateStockReqBody> list = new ArrayList<>();
		
		UpdateStockReqBody record1 = new UpdateStockReqBody();
		record1.setId(3);
		record1.setCount(1);

		UpdateStockReqBody record2 = new UpdateStockReqBody();
		record2.setId(4);
		record2.setCount(1);
		
		list.add(record1);
		list.add(record2);

		Req<ReqObjectBody<List<UpdateStockReqBody>>> req = new Req<>(new ReqObjectBody<>(list));
		productInfoRpcService.updateStock(req);
	}

	@PostConstruct
	public void test2() {
		System.err.println("productInfoRpcService!=null===>"+(productInfoRpcService!=null));
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//		String[] names = context.getBeanDefinitionNames();
//		for(String name:names) {
//			System.err.println(name);
//		}
	}

}