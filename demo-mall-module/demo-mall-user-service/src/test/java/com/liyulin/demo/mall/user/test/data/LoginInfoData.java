package com.liyulin.demo.mall.user.test.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liyulin.demo.mall.user.dto.login.LoginInfoInsertServiceDto;
import com.liyulin.demo.mall.user.entity.base.LoginInfoEntity;
import com.liyulin.demo.mall.user.service.api.LoginInfoApiService;
import com.liyulin.demo.rpc.enums.user.PwdStateEnum;

@Component
public class LoginInfoData {

	@Autowired
	private LoginInfoApiService loginInfoApiService;
	
	public LoginInfoEntity insert(String username, String password) {
		LoginInfoInsertServiceDto dto = new LoginInfoInsertServiceDto();
		dto.setUsername(username);
		dto.setPassword(password);
		dto.setUserId(1000L);
		dto.setPwdState(PwdStateEnum.DONE_SETTING.getValue());
		return loginInfoApiService.insert(dto);
	}
	
}