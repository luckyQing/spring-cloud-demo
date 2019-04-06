package com.liyulin.demo.product.rpc.request.oms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.liyulin.demo.common.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "商品新增请求参数")
public class ProductInsertReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品名称", required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(value = "销售价格（单位：万分之一元）", required = true)
	@Min(100)
	@NotNull
	private Long sellPrice;

	@ApiModelProperty(value = "库存", required = true)
	@Min(1)
	@NotNull
	private Long stock;

}