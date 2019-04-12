package com.liyulin.demo.rpc.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RpcConstants {
	
	public static final class Order {
		public static final String SERVICE_NAME = "demo_mall_order_service";
		public static final String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
	}
	
	public static final class Product {
		public static final String SERVICE_NAME = "demo_mall_product_service";
		public static final String FEIGN_CLIENT_NAME = "${" + SERVICE_NAME + ":" + SERVICE_NAME + "}";
	}

}