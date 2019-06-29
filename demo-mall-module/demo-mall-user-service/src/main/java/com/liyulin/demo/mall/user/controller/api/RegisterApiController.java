package com.liyulin.demo.mall.user.controller.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyulin.demo.common.business.dto.Req;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.mall.user.service.api.RegisterApiService;
import com.liyulin.demo.rpc.user.request.api.register.RegisterUserReqBody;
import com.liyulin.demo.rpc.user.response.api.register.RegisterUserRespBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Validated
@RequestMapping("api/pass/user/register")
@Api(tags = "用户api接口")
public class RegisterApiController {

	@Autowired
	private RegisterApiService registerApiService;

	@PostMapping
	@ApiOperation("注册")
	public Resp<RegisterUserRespBody> register(@RequestBody @Valid Req<@NotNull RegisterUserReqBody> req) {
		return registerApiService.register(req.getBody());
	}

}