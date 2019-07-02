package com.liyulin.demo.mall.user.test.integration.cases.api;

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
import com.liyulin.demo.common.redis.RedisComponent;
import com.liyulin.demo.common.util.RandomUtil;
import com.liyulin.demo.mall.user.config.UserRedisConfig;
import com.liyulin.demo.mall.user.test.data.LoginInfoData;
import com.liyulin.demo.rpc.user.request.api.login.CacheDesKeyReqBody;
import com.liyulin.demo.rpc.user.request.api.login.LoginReqBody;
import com.liyulin.demo.rpc.user.request.api.user.QueryUserInfoByIdReqBody;
import com.liyulin.demo.rpc.user.response.api.login.LoginRespBody;
import com.liyulin.demo.rpc.user.response.base.UserInfoBaseRespBody;

public class LoginInfoApiControllerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private RedisComponent redisComponent;
	@Autowired
	private LoginInfoData loginInfoData;

	@Test
	public void testGetRsaKey() throws Exception {
		// 构造请求参数
		QueryUserInfoByIdReqBody reqBody = new QueryUserInfoByIdReqBody();

		Resp<UserInfoBaseRespBody> result = super.postWithNoHeaders("/api/open/user/loginInfo/getRsaKey", reqBody,
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
		redisComponent.setObject(tokenRedisKey, loginCache, UserRedisConfig.NON_LOGIN_TOKEN_EXPIRE_MILLIS);

		// 构造请求参数
		CacheDesKeyReqBody reqBody = new CacheDesKeyReqBody();
		reqBody.setKey(RandomUtil.generateRandom(false, 10));

		Resp<BaseDto> result = super.postWithHeaders("/api/sign/user/loginInfo/cacheDesKey", reqBody, token,
				new TypeReference<Resp<BaseDto>>() {
				});

		redisComponent.delete(tokenRedisKey);

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

	@Test
	public void testLogin() throws Exception {
		// insert login info
		String username = "zhangsan";
		String password = "123456";
		loginInfoData.insert(username, password);

		// setting cache
		LoginCache loginCache = new LoginCache();
		String token = ReqHttpHeadersUtil.generateToken();
		loginCache.setToken(token);

		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);
		redisComponent.setObject(tokenRedisKey, loginCache, UserRedisConfig.NON_LOGIN_TOKEN_EXPIRE_MILLIS);

		// 构造请求参数
		LoginReqBody reqBody = new LoginReqBody();
		reqBody.setUsername(username);
		reqBody.setPassword(password);

		Resp<LoginRespBody> result = super.postWithHeaders("/api/sign/user/loginInfo/login", reqBody, token,
				new TypeReference<Resp<LoginRespBody>>() {
				});

		redisComponent.delete(tokenRedisKey);

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
	}

}