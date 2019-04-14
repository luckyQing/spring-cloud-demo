package com.liyulin.demo.rpc.order.response.api;

import com.liyulin.demo.common.business.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "创建订单响应信息")
public class CreateOrderRespBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "订单id")
	private Long orderId;

	@ApiModelProperty(value = "是否面单")
	private boolean free;

}