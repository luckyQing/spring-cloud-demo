package com.liyulin.demo.rpc.product.response.base;

import java.math.BigInteger;

import com.liyulin.demo.common.business.dto.BaseEntityRespBody;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@ApiModel(description = "商品信息响应信息")
public class ProductInfoRespBody extends BaseEntityRespBody {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品名称")
	private String name;

	@ApiModelProperty(value = "销售价格（单位：万分之一元）")
	private BigInteger sellPrice;

	@ApiModelProperty(value = "库存")
	private BigInteger stock;

}