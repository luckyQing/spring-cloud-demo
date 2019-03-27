package com.liyulin.demo.common.dto;

import com.liyulin.demo.common.exception.enums.IBaseReturnCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ApiModel(description = "响应头部")
public class RespHead extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "全局唯一交易流水号")
	private String transactionId;

	@ApiModelProperty(value = "响应状态码")
	private String code;

	@ApiModelProperty(value = "提示信息")
	private String msg;

	@ApiModelProperty(value = "错误详情")
	private String error;

	public RespHead(IBaseReturnCode returnCode) {
		this.code = returnCode.getCode();
		this.msg = returnCode.getMsg();
	}

}