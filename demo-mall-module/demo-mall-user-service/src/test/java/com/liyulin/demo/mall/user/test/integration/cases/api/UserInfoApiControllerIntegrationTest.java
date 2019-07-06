package com.liyulin.demo.mall.user.test.integration.cases.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.LoginCache;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.security.LoginRedisConfig;
import com.liyulin.demo.common.business.security.util.ReqHttpHeadersUtil;
import com.liyulin.demo.common.business.test.AbstractIntegrationTest;
import com.liyulin.demo.common.redis.RedisComponent;
import com.liyulin.demo.mall.user.config.UserRedisConfig;
import com.liyulin.demo.mall.user.test.data.UserInfoData;
import com.liyulin.demo.rpc.user.response.base.UserInfoBaseRespBody;

@Rollback
@Transactional
public class UserInfoApiControllerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private UserInfoData userInfoData;
	@Autowired
	private RedisComponent redisComponent;

	@Test
	public void testQuery() throws Exception {
		Long userId = 1L;
		userInfoData.insertTestData(userId);
		
		// 构造请求参数
		LoginCache loginCache = new LoginCache();
		String token = ReqHttpHeadersUtil.generateToken();
		loginCache.setToken(token);
		loginCache.setUserId(userId);

		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);
		redisComponent.setObject(tokenRedisKey, loginCache, UserRedisConfig.NON_LOGIN_TOKEN_EXPIRE_MILLIS);
		

		Resp<UserInfoBaseRespBody> result = super.getWithHeaders("/api/identity/user/userInfo/query", null, token,
				new TypeReference<Resp<UserInfoBaseRespBody>>() {
				});
		
		redisComponent.delete(tokenRedisKey);

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
	}

}