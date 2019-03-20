package com.liyulin.demo.product.base.domain.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.liyulin.demo.mybatis.mapper.ext.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Table(name = "t_product_info")
public class ProductInfoEntity extends BaseEntity {

	@Id
	@Column(name = "f_id")
	protected BigInteger id;

	/** 商品名称 */
	@Column(name = "f_name")
	protected String name;

	/** 销售价格（单位：万分之一元） */
	@Column(name = "f_sell_price")
	protected BigInteger sellPrice;

	/** 库存 */
	@Column(name = "f_stock")
	protected BigInteger stock;

}