package com.liyulin.demo.mall.user.biz.api;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.liyulin.demo.common.util.CollectionUtil;
import com.liyulin.demo.mall.user.dto.login.LoginInfoInsertBizDto;
import com.liyulin.demo.mall.user.entity.base.LoginInfoEntity;
import com.liyulin.demo.mall.user.mapper.base.LoginInfoBaseMapper;
import com.liyulin.demo.mybatis.common.biz.BaseBiz;
import com.liyulin.demo.rpc.enums.user.UserStateEnum;
import com.liyulin.demo.rpc.user.request.api.login.LoginReqBody;

import tk.mybatis.mapper.entity.Example;

@Repository
public class LoginInfoApiBiz extends BaseBiz<LoginInfoEntity> {

	@Autowired
	private LoginInfoBaseMapper loginInfoBaseMapper;

	/**
	 * 插入登陆信息
	 * 
	 * @param dto
	 * @return
	 */
	public LoginInfoEntity insert(LoginInfoInsertBizDto dto) {
		LoginInfoEntity entity = create();
		entity.setUserId(dto.getUserId());
		entity.setUsername(dto.getUsername());
		entity.setSalt(dto.getSalt());
		entity.setPassword(dto.getPassword());
		entity.setPwdState(dto.getPwdState());
		entity.setLastLoginTime(new Date());
		entity.setUserState(UserStateEnum.ENABLE.getValue());

		loginInfoBaseMapper.insertSelective(entity);
		return entity;
	}

	/**
	 * 根据用户名查询登陆信息
	 * 
	 * @param username
	 * @return
	 */
	public LoginInfoEntity queryByUsername(String username) {
		Example example = new Example(LoginInfoEntity.class, true, true);
		example.createCriteria().andEqualTo(LoginInfoEntity.Columns.USERNAME.getProperty(), username);
		List<LoginInfoEntity> list = loginInfoBaseMapper.selectByExampleAndRowBounds(example, new RowBounds(0, 1));
		if (CollectionUtil.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 判断该用户名是否已存在
	 * 
	 * @param username
	 * @return
	 */
	public boolean existByUsername(String username) {
		Example example = new Example(LoginInfoEntity.class, true, true);
		example.createCriteria().andEqualTo(LoginInfoEntity.Columns.USERNAME.getProperty(), username);
		return loginInfoBaseMapper.selectCountByExample(example) > 0;
	}

}