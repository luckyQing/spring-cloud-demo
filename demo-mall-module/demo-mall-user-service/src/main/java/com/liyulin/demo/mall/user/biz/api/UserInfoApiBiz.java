package com.liyulin.demo.mall.user.biz.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.liyulin.demo.mall.user.entity.base.UserInfoEntity;
import com.liyulin.demo.mall.user.mapper.base.UserInfoBaseMapper;
import com.liyulin.demo.mybatis.common.biz.BaseBiz;
import com.liyulin.demo.rpc.user.request.api.user.UserInfoInsertReqBody;

import lombok.Getter;
import tk.mybatis.mapper.entity.Example;

@Repository
public class UserInfoApiBiz extends BaseBiz<UserInfoEntity> {

	@Autowired
	@Getter
	private UserInfoBaseMapper userInfoBaseMapper;

	/**
	 * 插入用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public UserInfoEntity insert(UserInfoInsertReqBody userInfo) {
		UserInfoEntity record = create();
		record.setMobile(userInfo.getMobile());
		record.setNickname(userInfo.getNickname());
		record.setRealname(userInfo.getRealname());
		record.setSex(userInfo.getSex());
		record.setBirthday(userInfo.getBirthday());
		record.setProfileImage(userInfo.getProfileImage());
		record.setChannel(userInfo.getChannel());

		userInfoBaseMapper.insertSelective(record);
		return record;
	}

	/**
	 * 判断改手机号是否已存在
	 * 
	 * @param mobile
	 * @return
	 */
	public boolean existByMobile(String mobile) {
		Example example = new Example(UserInfoEntity.class, true, true);
		example.createCriteria().andEqualTo(UserInfoEntity.Columns.MOBILE.getProperty(), mobile);
		return userInfoBaseMapper.selectCountByExample(example) > 0;
	}

}