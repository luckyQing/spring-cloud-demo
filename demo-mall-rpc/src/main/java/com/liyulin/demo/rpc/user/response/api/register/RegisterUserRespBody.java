package com.liyulin.demo.rpc.user.response.api.register;

import com.liyulin.demo.rpc.user.response.api.login.LoginRespBody;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@ApiModel(description = "用户注册响应信息")
public class RegisterUserRespBody extends LoginRespBody {

	private static final long serialVersionUID = 1L;

	public RegisterUserRespBody(LoginRespBody loginRespBody) {
		super(loginRespBody.getUserId());
	}

}