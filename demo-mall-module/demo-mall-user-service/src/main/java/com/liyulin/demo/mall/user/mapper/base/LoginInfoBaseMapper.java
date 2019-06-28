package com.liyulin.demo.mall.user.mapper.base;

import com.liyulin.demo.mall.user.entity.base.LoginInfoEntity;
import com.liyulin.demo.mybatis.common.mapper.ext.ExtMapper;
import com.liyulin.demo.rpc.user.response.base.LoginInfoBaseRespBody;

public interface LoginInfoBaseMapper extends ExtMapper<LoginInfoEntity, LoginInfoBaseRespBody, Long> {

}