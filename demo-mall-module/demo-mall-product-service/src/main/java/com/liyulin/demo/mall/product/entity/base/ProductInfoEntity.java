package com.liyulin.demo.mall.product.entity.base;

import javax.persistence.Column;
import javax.persistence.Table;

import com.liyulin.demo.mybatis.common.mapper.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 商品信息
 *
 * @author liyulin
 * @date 2019年6月28日下午3:54:39
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_product_info")
public class ProductInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 商品名称 */
	@Column(name = "f_name")
	private String name;

	/** 销售价格（单位：万分之一元） */
	@Column(name = "f_sell_price")
	private Long sellPrice;

	/** 库存 */
	@Column(name = "f_stock")
	private Long stock;

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public enum Columns {
		/** 商品名称 */
		NAME("name"),
		/** 销售价格 */
		SELL_PRICE("sellPrice"),
		/** 库存 */
		STOCK("stock");

		private String property;
	}

}