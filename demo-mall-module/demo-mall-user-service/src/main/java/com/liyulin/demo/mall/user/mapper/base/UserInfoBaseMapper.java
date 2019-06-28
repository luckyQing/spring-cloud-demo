package com.liyulin.demo.mall.user.mapper.base;

import com.liyulin.demo.mall.user.entity.base.UserInfoEntity;
import com.liyulin.demo.mybatis.common.mapper.ext.ExtMapper;
import com.liyulin.demo.rpc.user.response.base.UserInfoBaseRespBody;

public interface UserInfoBaseMapper extends ExtMapper<UserInfoEntity, UserInfoBaseRespBody, Long> {

}