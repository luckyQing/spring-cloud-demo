package com.liyulin.demo.rpc.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RpcConstants {
	
	public static final class Order {
		public static final String SERVICE_NAME = "demo-mall-order-service";
		public static final String FEIGN_CLIENT_NAME = "${demo_mall_order_service:demo-mall-order-service}";
	}
	
	public static final class Product {
		public static final String SERVICE_NAME = "demo-mall-product-service";
		public static final String FEIGN_CLIENT_NAME = "${demo_mall_product_service:demo-mall-product-service}";
	}

}