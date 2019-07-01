package com.liyulin.demo.mall.user.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.mall.user.dto.login.LoginInfoInsertServiceDto;
import com.liyulin.demo.mall.user.entity.base.UserInfoEntity;
import com.liyulin.demo.rpc.user.request.api.login.LoginInfoInsertReqBody;
import com.liyulin.demo.rpc.user.request.api.register.RegisterUserReqBody;
import com.liyulin.demo.rpc.user.response.api.register.RegisterUserRespBody;

/**
 * 注册
 *
 * @author liyulin
 * @date 2019年6月29日下午3:23:59
 */
@Service
public class RegisterApiService {

	@Autowired
	private UserInfoApiService userInfoApiService;
	@Autowired
	private LoginInfoApiService loginInfoApiService;
	
	/**
	 * 注册
	 * 
	 * @param req
	 * @return
	 */
	@Transactional
	public Resp<RegisterUserRespBody> register(RegisterUserReqBody req){
		// 用户信息
		UserInfoEntity userInfoEntity = userInfoApiService.insert(req.getUserInfo());
		
		// 登陆信息
		LoginInfoInsertReqBody loginInfo = req.getLoginInfo();
		
		LoginInfoInsertServiceDto loginInfoInsertDto = LoginInfoInsertServiceDto.builder()
				.userId(userInfoEntity.getId())
				.username(loginInfo.getUsername())
				.password(loginInfo.getPassword())
				.pwdState(loginInfo.getPwdState())
				.build();
		loginInfoApiService.insert(loginInfoInsertDto);
		
		// 注册成功，则缓存
		Long userId = userInfoEntity.getId();
		loginInfoApiService.cacheLoginAfterLoginSuccess(userId);
		
		RegisterUserRespBody registerUserRespBody = new RegisterUserRespBody();
		registerUserRespBody.setUserId(userId);
		return RespUtil.success(registerUserRespBody);
	}
	
}