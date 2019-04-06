package com.liyulin.demo.common.business.dto;

import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "响应对象")
public class Resp<T extends BaseDto> extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "响应头部")
	private RespHead head = null;

	@ApiModelProperty(value = "响应体")
	private T body;

	public Resp(RespHead head) {
		this.head = head;
	}

	public Resp(T body) {
		this.head = new RespHead(ReturnCodeEnum.SUCCESS);
		this.body = body;
	}

}