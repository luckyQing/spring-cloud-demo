package com.liyulin.demo.mall.user.test.integration.cases.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractIntegrationTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.mall.user.service.api.LoginInfoApiService;
import com.liyulin.demo.mall.user.service.api.RegisterApiService;
import com.liyulin.demo.rpc.enums.user.ChannelEnum;
import com.liyulin.demo.rpc.enums.user.PwdStateEnum;
import com.liyulin.demo.rpc.enums.user.SexEnum;
import com.liyulin.demo.rpc.user.request.api.login.LoginInfoInsertReqBody;
import com.liyulin.demo.rpc.user.request.api.register.RegisterUserReqBody;
import com.liyulin.demo.rpc.user.request.api.user.UserInfoInsertReqBody;
import com.liyulin.demo.rpc.user.response.api.register.RegisterUserRespBody;

public class RegisterApiControllerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void testRegister() throws Exception {
		// mock
		LoginInfoApiService loginInfoApiService = Mockito.mock(LoginInfoApiService.class);
		RegisterApiService registerApiService = applicationContext.getBean(RegisterApiService.class);
		setMockAttribute(registerApiService, loginInfoApiService);
		Mockito.doNothing().when(loginInfoApiService).cacheLoginAfterLoginSuccess(Mockito.any());
		
		// 构造请求参数
		UserInfoInsertReqBody userInfo = new UserInfoInsertReqBody();
		userInfo.setMobile("18720912981");
		userInfo.setChannel(ChannelEnum.APP.getValue());
		userInfo.setSex(SexEnum.FEMALE.getValue());
		
		LoginInfoInsertReqBody loginInfo = new LoginInfoInsertReqBody();
		loginInfo.setUsername("test");
		loginInfo.setPwdState(PwdStateEnum.DONE_SETTING.getValue());
		loginInfo.setPassword("123456");
		
		RegisterUserReqBody registerUserReqBody = new RegisterUserReqBody();
		registerUserReqBody.setUserInfo(userInfo);
		registerUserReqBody.setLoginInfo(loginInfo);
		
		Resp<RegisterUserRespBody> result = super.postWithNoHeaders("/api/sign/user/register",
				ReqUtil.build(registerUserReqBody), new TypeReference<Resp<RegisterUserRespBody>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
	}
	
}