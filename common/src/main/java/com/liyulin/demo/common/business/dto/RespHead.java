package com.liyulin.demo.common.business.dto;

import com.liyulin.demo.common.business.exception.enums.IBaseReturnCode;
import com.liyulin.demo.common.business.mock.strategy.RespHeadCodeAttributeStrategy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Getter
@Setter
@SuperBuilder
@ApiModel(description = "响应头部")
public class RespHead extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "全局唯一交易流水号", example = "eb9f81e7cee1c000")
	private String transactionId;

	@ApiModelProperty(value = "响应状态码", example = "100500")
	@PodamStrategyValue(RespHeadCodeAttributeStrategy.class)
	private String code;

	@ApiModelProperty(value = "提示信息", example = "服务器异常")
	private String msg;

	@ApiModelProperty(value = "错误详情", example = "错误详细信息")
	private String error;

	@ApiModelProperty(value = "响应时间戳", example = "1554551377629")
	private long timestamp;

	public RespHead(IBaseReturnCode returnCode) {
		setReturnCode(returnCode);
	}

	public RespHead(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public void setReturnCode(IBaseReturnCode returnCode) {
		this.code = returnCode.getCode();
		this.msg = returnCode.getMsg();
	}

}