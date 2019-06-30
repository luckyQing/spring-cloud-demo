package com.liyulin.demo.mall.user.test.cases.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractIntegrationTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.rpc.user.request.api.login.CacheDesKeyReqBody;
import com.liyulin.demo.rpc.user.request.api.user.QueryUserInfoByIdReqBody;
import com.liyulin.demo.rpc.user.response.base.UserInfoBaseRespBody;

public class LoginInfoApiControllerTest extends AbstractIntegrationTest {
	
	@Test
	public void testGetRsaKey() throws Exception {
		// 构造请求参数
		QueryUserInfoByIdReqBody reqBody = new QueryUserInfoByIdReqBody();
		
		Resp<UserInfoBaseRespBody> result = super.postJson("/api/open/user/loginInfo/getRsaKey",
				ReqUtil.build(reqBody), new TypeReference<Resp<UserInfoBaseRespBody>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
	}
	
	@Test
	public void testCacheDesKey() throws Exception {
		// 构造请求参数
		CacheDesKeyReqBody reqBody = new CacheDesKeyReqBody();
		reqBody.setKey("123456");
		
		Resp<BaseDto> result = super.postJson("/api/sign/user/loginInfo/cacheDesKey",
				ReqUtil.build(reqBody), new TypeReference<Resp<BaseDto>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}
	
}