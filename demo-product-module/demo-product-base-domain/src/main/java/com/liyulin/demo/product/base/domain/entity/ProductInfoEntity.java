package com.liyulin.demo.product.base.domain.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Table;

import com.liyulin.demo.mybatis.mapper.entity.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
	private BigInteger sellPrice;

	/** 库存 */
	@Column(name = "f_stock")
	private BigInteger stock;

}