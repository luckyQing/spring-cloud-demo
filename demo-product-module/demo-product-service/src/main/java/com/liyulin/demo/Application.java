package com.liyulin.demo;

import java.math.BigInteger;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.liyulin.demo.product.base.domain.entity.ProductInfoEntity;
import com.liyulin.demo.product.base.domain.mapper.ProductInfoMapper;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableTransactionManagement
//@MapperScan(basePackages = "com.liyulin.demo.**.mapper")
public class Application {
	
	@Autowired
	private ProductInfoMapper productInfoMapper;
	
	@PostConstruct
	public void test() {
		ProductInfoEntity entity = new ProductInfoEntity();
		entity.setId(BigInteger.valueOf(2));
		entity.setName("手机");
		entity.setSellPrice(BigInteger.valueOf(12800));
		entity.setStock(BigInteger.valueOf(100));
		entity.setAddTime(new Date());
		entity.setDelState(1);
		
		productInfoMapper.insert(entity);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}