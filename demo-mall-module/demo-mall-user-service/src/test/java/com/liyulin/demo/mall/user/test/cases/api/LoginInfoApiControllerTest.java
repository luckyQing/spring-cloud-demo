package com.liyulin.demo.mall.user.test.cases.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.LoginCache;
import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.signature.LoginRedisConfig;
import com.liyulin.demo.common.business.signature.util.ReqHttpHeadersUtil;
import com.liyulin.demo.common.business.test.AbstractIntegrationTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.common.redis.RedisWrapper;
import com.liyulin.demo.mall.user.config.UserRedisConfig;
import com.liyulin.demo.rpc.user.request.api.login.CacheDesKeyReqBody;
import com.liyulin.demo.rpc.user.request.api.login.LoginReqBody;
import com.liyulin.demo.rpc.user.request.api.user.QueryUserInfoByIdReqBody;
import com.liyulin.demo.rpc.user.response.api.login.LoginRespBody;
import com.liyulin.demo.rpc.user.response.base.UserInfoBaseRespBody;

public class LoginInfoApiControllerTest extends AbstractIntegrationTest {

	@Autowired
	private RedisWrapper redisWrapper;
	
	@Test
	public void testGetRsaKey() throws Exception {
		// 构造请求参数
		QueryUserInfoByIdReqBody reqBody = new QueryUserInfoByIdReqBody();

		Resp<UserInfoBaseRespBody> result = super.postWithNoHeaders("/api/open/user/loginInfo/getRsaKey", ReqUtil.build(reqBody),
				new TypeReference<Resp<UserInfoBaseRespBody>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
	}

	@Test
	public void testCacheDesKey() throws Exception {
		LoginCache loginCache = new LoginCache();
		String token = ReqHttpHeadersUtil.generateToken();
		loginCache.setToken(token);
		
		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);
		redisWrapper.setObject(tokenRedisKey, loginCache, UserRedisConfig.NON_LOGIN_TOKEN_EXPIRE_MILLIS);
		
		// 构造请求参数
		CacheDesKeyReqBody reqBody = new CacheDesKeyReqBody();
		reqBody.setKey("123456");

		Resp<BaseDto> result = super.postWithHeaders("/api/sign/user/loginInfo/cacheDesKey", ReqUtil.build(reqBody),
				token, new TypeReference<Resp<BaseDto>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}
	
	@Test
	public void testLogin() throws Exception {
		LoginCache loginCache = new LoginCache();
		String token = ReqHttpHeadersUtil.generateToken();
		loginCache.setToken(token);
		
		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);
		redisWrapper.setObject(tokenRedisKey, loginCache, UserRedisConfig.NON_LOGIN_TOKEN_EXPIRE_MILLIS);
		
		// 构造请求参数
		LoginReqBody reqBody = new LoginReqBody();
		reqBody.setUsername("zhangsan");
		reqBody.setPassword("123456");

		Resp<LoginRespBody> result = super.postWithHeaders("/api/sign/user/loginInfo/login", ReqUtil.build(reqBody),
				token, new TypeReference<Resp<LoginRespBody>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

}