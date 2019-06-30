package com.liyulin.demo.mall.user.test.data;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.liyulin.demo.mall.user.entity.base.UserInfoEntity;
import com.liyulin.demo.mall.user.mapper.base.UserInfoBaseMapper;
import com.liyulin.demo.mybatis.common.mapper.enums.DelStateEnum;
import com.liyulin.demo.rpc.enums.user.ChannelEnum;
import com.liyulin.demo.rpc.enums.user.SexEnum;

@Component
public class UserInfoData {

	@Autowired
	private UserInfoBaseMapper userInfoBaseMapper;

	public void insertTestData(Long id) {
		UserInfoEntity entity = new UserInfoEntity();
		entity.setId(id);
		entity.setMobile("18720912981");
		entity.setChannel(ChannelEnum.APP.getValue());
		entity.setSex(SexEnum.FEMALE.getValue());
		entity.setAddTime(new Date());
		entity.setDelState(DelStateEnum.NORMAL.getDelState());
		userInfoBaseMapper.insertSelective(entity);
	}

}