package com.liyulin.demo.rpc.user.request.api.login;

import javax.validation.constraints.NotBlank;

import com.liyulin.demo.common.business.dto.BaseDto;

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
@ApiModel(description = "登陆请求参数")
public class LoginReqBody extends BaseDto {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户名", required = true)
	@NotBlank
	private String username;

	@ApiModelProperty(value = "密码", required = true)
	@NotBlank
	private String password;

}