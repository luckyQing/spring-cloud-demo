package com.liyulin.demo.mall.user.service.api;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.common.business.LoginCache;
import com.liyulin.demo.common.business.dto.BaseDto;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.ServerException;
import com.liyulin.demo.common.business.signature.LoginRedisConfig;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.common.redis.RedisWrapper;
import com.liyulin.demo.common.util.RandomUtil;
import com.liyulin.demo.common.util.SnowFlakeIdUtil;
import com.liyulin.demo.common.util.security.RsaUtil;
import com.liyulin.demo.mall.user.biz.api.LoginInfoApiBiz;
import com.liyulin.demo.mall.user.config.UserRedisConfig;
import com.liyulin.demo.mall.user.entity.base.LoginInfoEntity;
import com.liyulin.demo.mall.user.enums.UserReturnCodeEnum;
import com.liyulin.demo.mybatis.common.mapper.enums.DelStateEnum;
import com.liyulin.demo.rpc.enums.user.UserStateEnum;
import com.liyulin.demo.rpc.user.request.api.login.CacheDesKeyReqBody;
import com.liyulin.demo.rpc.user.request.api.login.LoginReqBody;
import com.liyulin.demo.rpc.user.response.api.login.GetRsaKeyRespBody;
import com.liyulin.demo.rpc.user.response.api.login.LoginRespBody;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginInfoApiService {

	@Autowired
	private LoginInfoApiBiz loginInfoApiBiz;
	@Autowired
	private RedisWrapper redisWrapper;

	public GetRsaKeyRespBody getRsaKey(){
		KeyPair keyPair = null;
		try {
			keyPair = RsaUtil.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			throw new ServerException(UserReturnCodeEnum.GENERATE_RSAKEY_FAIL);
		}
		
		GetRsaKeyRespBody getRsaKeyRespBody = new GetRsaKeyRespBody();
		getRsaKeyRespBody.setRsaModulus(RsaUtil.getModulus(keyPair));
		getRsaKeyRespBody.setRsaPubKey(RsaUtil.getPublicExponent(keyPair));
		String token = generateToken();
		getRsaKeyRespBody.setToken(token);
		
		cacheRsaKey(token, keyPair);
		return getRsaKeyRespBody;
	}
	
	public Resp<BaseDto> cacheDesKey(CacheDesKeyReqBody req){
		
	}
	
	/**
	 * 缓存rsa key相关信息
	 * 
	 * @param rsaKey
	 */
	private void cacheRsaKey(String token, KeyPair keyPair) {
		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);
		
		LoginCache loginCache = new LoginCache();
		loginCache.setToken(token);
		loginCache.setRsaPrivateKey((RSAPrivateKey) keyPair.getPrivate());
		loginCache.setRsaPublicKey((RSAPublicKey) keyPair.getPublic());
		redisWrapper.setObject(tokenRedisKey, loginCache, UserRedisConfig.APP_LOGINED_TOKEN_EXPIRE_MILLIS);
	}

	private String generateToken() {
		// 产生规则：16进制（雪花算法）+2位随机字符混淆
		return Long.toHexString(SnowFlakeIdUtil.getInstance().nextId()) + RandomUtil.createRandom(false, 2);
	}
	
	/**
	 * 登陆
	 * 
	 * @param req
	 * @return
	 */
	public Resp<LoginRespBody> login(LoginReqBody req) {
		LoginInfoEntity entity = loginInfoApiBiz.queryByUsernameAndPwd(req);
		if (Objects.isNull(entity)) {
			return RespUtil.error(UserReturnCodeEnum.ACCOUNT_NOT_EXIST);
		}
		if (entity.getUserState() == UserStateEnum.UNENABLE.getValue()) {
			return RespUtil.error(UserReturnCodeEnum.USER_UNENABLE);
		}
		if (entity.getDelState() == DelStateEnum.DELETED.getDelState()) {
			return RespUtil.error(UserReturnCodeEnum.USER_DELETED);
		}

		return RespUtil.success(new LoginRespBody(entity.getId()));
	}

}