package com.liyulin.demo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.liyulin.demo.mybatis.mapper.enums.DelStateEnum;
import com.liyulin.demo.product.base.domain.entity.ProductInfoEntity;
import com.liyulin.demo.product.base.domain.mapper.ProductInfoBaseMapper;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@MapperScan(basePackages = "com.liyulin.demo.**.domain.mapper")
public class Application {
	
	@Autowired
	private ProductInfoBaseMapper productInfoBaseMapper;
	
	
	private void insert() {
		ProductInfoEntity entity = new ProductInfoEntity();
		entity.setId(BigInteger.valueOf(70));
		entity.setName("手机");
		entity.setSellPrice(BigInteger.valueOf(12800));
		entity.setStock(BigInteger.valueOf(100));
		entity.setAddTime(new Date());
		entity.setDelState(DelStateEnum.NORMAL.getDelState());
		productInfoBaseMapper.insert(entity);
	}
	
	private void batchInsert() {
		List<ProductInfoEntity> list = new ArrayList<>();
		for(int i=23; i<26;i++) {
			ProductInfoEntity entity = new ProductInfoEntity();
			entity.setId(BigInteger.valueOf(i));
			entity.setName("手机11");
			entity.setSellPrice(BigInteger.valueOf(12800));
			entity.setStock(BigInteger.valueOf(100));
			entity.setAddTime(new Date());
			entity.setDelState(DelStateEnum.NORMAL.getDelState());
			list.add(entity);
		}
		
		productInfoBaseMapper.updateListByPrimaryKeySelective(list);
	}
	
	@PostConstruct
	public void test() {
		batchInsert();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}