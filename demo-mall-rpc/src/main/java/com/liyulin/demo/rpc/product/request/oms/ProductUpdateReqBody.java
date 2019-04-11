package com.liyulin.demo.rpc.product.request.oms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.liyulin.demo.common.business.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "商品修改请求参数")
public class ProductUpdateReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品id", required = true)
	@NotNull
	@Min(1)
	private Long id;

	@ApiModelProperty(value = "商品名称", required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(value = "销售价格（单位：万分之一元）")
	@Min(100)
	private Long sellPrice;

	@ApiModelProperty(value = "库存")
	@Min(1)
	private Long stock;

}