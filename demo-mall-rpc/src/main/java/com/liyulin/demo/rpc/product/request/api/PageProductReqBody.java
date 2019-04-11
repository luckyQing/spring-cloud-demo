package com.liyulin.demo.rpc.product.request.api;

import com.liyulin.demo.common.business.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "分页查询商品信息请求参数")
public class PageProductReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "商品名称（模糊匹配）")
	private String name;

}