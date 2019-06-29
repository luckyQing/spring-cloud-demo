package com.liyulin.demo.mall.user.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.mall.user.biz.api.UserInfoApiBiz;
import com.liyulin.demo.rpc.user.request.api.user.QueryUserInfoByIdReqBody;
import com.liyulin.demo.rpc.user.response.base.UserInfoBaseRespBody;

@Service
public class UserInfoApiService {

	@Autowired
	private UserInfoApiBiz userInfoApiBiz;

	/**
	 * 根据id查询用户信息
	 * 
	 * @param req
	 * @return
	 */
	public UserInfoBaseRespBody queryById(QueryUserInfoByIdReqBody req){
		return userInfoApiBiz.getUserInfoBaseMapper().selectRespById(req.getUserId());
	}
	
}