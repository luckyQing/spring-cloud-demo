package com.liyulin.demo.mall.user.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.mall.user.biz.api.LoginInfoApiBiz;
import com.liyulin.demo.mall.user.biz.api.UserInfoApiBiz;
import com.liyulin.demo.mall.user.entity.base.UserInfoEntity;
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
	private UserInfoApiBiz userInfoApiBiz;
	@Autowired
	private LoginInfoApiBiz loginInfoApiBiz;
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
		UserInfoEntity userInfoEntity = userInfoApiBiz.insert(req.getUserInfo());
		loginInfoApiBiz.insert(req.getLoginInfo(), userInfoEntity.getId());
		
		Long userId = userInfoEntity.getId();
		loginInfoApiService.cacheLoginAfterLoginSuccess(userId);
		
		RegisterUserRespBody registerUserRespBody = new RegisterUserRespBody();
		registerUserRespBody.setUserId(userId);
		return RespUtil.success(registerUserRespBody);
	}
	
}