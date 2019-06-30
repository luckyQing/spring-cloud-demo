package com.liyulin.demo.mall.user.test.cases.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.enums.ReturnCodeEnum;
import com.liyulin.demo.common.business.test.AbstractIntegrationTest;
import com.liyulin.demo.common.business.util.ReqUtil;
import com.liyulin.demo.mall.user.test.data.UserInfoData;
import com.liyulin.demo.rpc.user.request.api.user.QueryUserInfoByIdReqBody;
import com.liyulin.demo.rpc.user.response.base.UserInfoBaseRespBody;

public class UserInfoApiControllerTest extends AbstractIntegrationTest {

	@Autowired
	private UserInfoData userInfoData;
	
	@Test
	public void testQueryById() throws Exception {
		Long userId = 1L;
		userInfoData.insertTestData(userId);		
		// 构造请求参数
		QueryUserInfoByIdReqBody reqBody = new QueryUserInfoByIdReqBody();
		reqBody.setUserId(userId);
		
		Resp<UserInfoBaseRespBody> result = super.postJson("/api/identity/user/userInfo/queryById",
				ReqUtil.build(reqBody), new TypeReference<Resp<UserInfoBaseRespBody>>() {
				});

		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getHead()).isNotNull();
		Assertions.assertThat(result.getHead().getCode()).isEqualTo(ReturnCodeEnum.SUCCESS.getCode());
		Assertions.assertThat(result.getBody()).isNotNull();
	}
	
}