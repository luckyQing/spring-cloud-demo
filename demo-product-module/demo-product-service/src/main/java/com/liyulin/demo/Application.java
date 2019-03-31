package com.liyulin.demo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import com.alibaba.fastjson.JSON;
import com.liyulin.demo.common.annotation.MainService;
import com.liyulin.demo.common.dto.BasePageResp;
import com.liyulin.demo.mybatis.mapper.entity.BaseEntity;
import com.liyulin.demo.mybatis.mapper.enums.DelStateEnum;
import com.liyulin.demo.product.base.domain.entity.ProductInfoEntity;
import com.liyulin.demo.product.base.domain.mapper.ProductInfoBaseMapper;
import com.liyulin.demo.product.rpc.response.ProductInfoRespBody;

import tk.mybatis.mapper.entity.Example;

@MainService
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
		for (int i = 23; i < 26; i++) {
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

	private void update() {
		ProductInfoEntity entity = new ProductInfoEntity();
		entity.setId(BigInteger.valueOf(70));
		entity.setName("手机111");
		entity.setSellPrice(BigInteger.valueOf(120));
		entity.setStock(BigInteger.valueOf(10));
		entity.setAddTime(new Date());
		entity.setDelState(DelStateEnum.DELETED.getDelState());
		productInfoBaseMapper.updateByPrimaryKeySelective(entity);
	}
	
	private void page() {
//		Example example = new Example(ProductInfoEntity.class);
//		example.createCriteria().andEqualTo(BaseEntity.Columns.DEL_STATE.getProperty(), DelStateEnum.DELETED.getDelState());
//		example.orderBy(BaseEntity.Columns.ID.getProperty()).desc();
//		
//		PageInfo<ProductInfoEntity> pageInfo = productInfoBaseMapper.pageByExample(example, 1, 10);
//		LogUtil.info("count=>{};list=>{} ", pageInfo.getTotal(), JSON.toJSONString(pageInfo.getList()));
	}

	public void test1() {
		Example example = new Example(ProductInfoEntity.class);
		example.createCriteria().andEqualTo(BaseEntity.Columns.DEL_STATE.getProperty(), DelStateEnum.DELETED.getDelState());
		example.orderBy(BaseEntity.Columns.ID.getProperty()).desc();
		BasePageResp<ProductInfoRespBody> entitydatas = productInfoBaseMapper.pageRespByExample(example, 1, 10);
		System.err.println(JSON.toJSONString(entitydatas));
	}
	
	private void delete() {
		productInfoBaseMapper.logicDeleteByPrimaryKeys(Arrays.asList(BigInteger.valueOf(1), BigInteger.valueOf(2)),
				BigInteger.valueOf(29), new Date());
	}

	@PostConstruct
	public void test() {
		test1();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}