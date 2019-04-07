package com.liyulin.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import com.liyulin.demo.common.annotation.MainService;
import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.ReqObjectBody;
import com.liyulin.demo.mybatis.common.mapper.entity.BaseEntity;
import com.liyulin.demo.mybatis.common.mapper.enums.DelStateEnum;
import com.liyulin.demo.product.base.entity.ProductInfoEntity;
import com.liyulin.demo.product.base.mapper.ProductInfoBaseMapper;
import com.liyulin.demo.product.service.rpc.ProductInfoRpcService;
import com.liyulin.demo.rpc.product.request.rpc.UpdateStockReqBody;

import tk.mybatis.mapper.entity.Example;

@MainService
public class Application {

	@Autowired
	private ProductInfoBaseMapper productInfoBaseMapper;
	@Autowired
	private ProductInfoRpcService productInfoRpcService;

	@PostConstruct
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
	
	public void test2() {
		ProductInfoEntity record1 = new ProductInfoEntity();
		record1.setName("手机33333");
		record1.setSellPrice(33L);
		record1.setStock(333L);
		
		ProductInfoEntity record2 = new ProductInfoEntity();
		record2.setName("手机44444");
		record2.setSellPrice(4L);
		record2.setStock(44L);

		Example example1 = new Example(ProductInfoEntity.class);
		example1.createCriteria().andEqualTo(BaseEntity.Columns.ID.getProperty(), 3)
				.andNotEqualTo(BaseEntity.Columns.DEL_STATE.getProperty(), DelStateEnum.DELETED.getDelState());

		Example example2 = new Example(ProductInfoEntity.class);
		example2.createCriteria().andEqualTo(BaseEntity.Columns.ID.getProperty(), 4)
				.andNotEqualTo(BaseEntity.Columns.DEL_STATE.getProperty(), DelStateEnum.DELETED.getDelState());

		productInfoBaseMapper.updateListByExamplesSelective(Arrays.asList(record1, record2), Arrays.asList(example1, example2));
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}