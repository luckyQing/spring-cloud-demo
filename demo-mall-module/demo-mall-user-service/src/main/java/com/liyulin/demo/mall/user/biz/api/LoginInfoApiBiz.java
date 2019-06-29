package com.liyulin.demo.mall.user.biz.api;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.liyulin.demo.common.util.CollectionUtil;
import com.liyulin.demo.mall.user.entity.base.LoginInfoEntity;
import com.liyulin.demo.mall.user.mapper.base.LoginInfoBaseMapper;
import com.liyulin.demo.mybatis.common.biz.BaseBiz;
import com.liyulin.demo.rpc.enums.user.UserStateEnum;
import com.liyulin.demo.rpc.user.request.api.login.LoginInfoInsertReqBody;
import com.liyulin.demo.rpc.user.request.api.login.LoginReqBody;

import tk.mybatis.mapper.entity.Example;

@Repository
public class LoginInfoApiBiz extends BaseBiz<LoginInfoEntity> {

	@Autowired
	private LoginInfoBaseMapper loginInfoBaseMapper;

	/**
	 * 插入登陆信息
	 * 
	 * @param loginInfo
	 * @param userId
	 * @return
	 */
	public LoginInfoEntity insert(LoginInfoInsertReqBody loginInfo, Long userId) {
		LoginInfoEntity entity = create();
		entity.setUserId(userId);
		entity.setUsername(loginInfo.getUsername());
		entity.setPassword(loginInfo.getPassword());
		entity.setPwdState(loginInfo.getPwdState());
		entity.setLastLoginTime(new Date());
		entity.setUserState(UserStateEnum.ENABLE.getValue());
		
		loginInfoBaseMapper.insertSelective(entity);
		return entity;
	}
	
	/**
	 * 根据用户名、密码查询登陆信息
	 * 
	 * @param LoginReqBody
	 * @return
	 */
	public LoginInfoEntity queryByUsernameAndPwd(LoginReqBody req) {
		Example example = new Example(LoginInfoEntity.class, true, true);
		example.createCriteria().andEqualTo(LoginInfoEntity.Columns.USERNAME.getProperty(), req.getUsername())
				.andEqualTo(LoginInfoEntity.Columns.PASSWORD.getProperty(), req.getPassword());
		List<LoginInfoEntity> list = loginInfoBaseMapper.selectByExampleAndRowBounds(example, new RowBounds(0, 1));
		if (CollectionUtil.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

}