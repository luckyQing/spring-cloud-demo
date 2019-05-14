package com.liyulin.demo.mall.product.constants;

import com.liyulin.demo.mybatis.autoconfigure.MultipleDataSourceAutoConfiguration;

import lombok.experimental.UtilityClass;

/**
 * 商品服务常量
 *
 * @author liyulin
 * @date 2019年4月27日上午12:30:02
 */
@UtilityClass
public class ProductConstants {

	/** {@code @Transactional}属性value的值（“product”与“application-db-product.yml”中配的数据源key名称一致）*/
	public static final String TRANSACTION_NAME = "product" + MultipleDataSourceAutoConfiguration.TRANSACTION_MANAGER_NAME;

}