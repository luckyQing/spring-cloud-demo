package com.liyulin.demo.common.business.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "请求对象")
public class Req<T extends BaseDto> extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "请求头部", required = true)
	@NotNull
	@Valid
	private ReqHead head;

	@ApiModelProperty(value = "请求体")
	@Valid
	private T body;

	@ApiModelProperty(value = "签名", required = true)
	//@NotBlank
	private String sign;

	public Req(ReqHead head) {
		this.head = head;
	}

	public Req(T body) {
		this.body = body;
	}

	public Req(ReqHead head, T body) {
		this.head = head;
		this.body = body;
	}

}