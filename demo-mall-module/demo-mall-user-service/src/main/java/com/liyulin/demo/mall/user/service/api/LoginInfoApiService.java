package com.liyulin.demo.mall.user.service.api;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.common.business.LoginCache;
import com.liyulin.demo.common.business.ReqContextHolder;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.ServerException;
import com.liyulin.demo.common.business.signature.LoginRedisConfig;
import com.liyulin.demo.common.business.signature.util.ReqHttpHeadersUtil;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.common.redis.RedisWrapper;
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

	/**
	 * 生成rsa公钥、私钥、token
	 * 
	 * @return
	 */
	public GetRsaKeyRespBody generateRsaKey() {
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
		String token = ReqHttpHeadersUtil.generateToken();
		getRsaKeyRespBody.setToken(token);

		cacheRsaKey(token, keyPair);
		return getRsaKeyRespBody;
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
		redisWrapper.setObject(tokenRedisKey, loginCache, UserRedisConfig.NON_LOGIN_TOKEN_EXPIRE_MILLIS);
	}

	public void cacheDesKey(CacheDesKeyReqBody req) {
		LoginCache loginCache = ReqContextHolder.getLoginCache();
		loginCache.setAesKey(req.getKey());

		String token = loginCache.getToken();
		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);
		redisWrapper.setObject(tokenRedisKey, loginCache, UserRedisConfig.NON_LOGIN_TOKEN_EXPIRE_MILLIS);
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
		
		Long userId = entity.getId();
		cacheLoginAfterLoginSuccess(userId);

		return RespUtil.success(new LoginRespBody(userId));
	}
	
	public void cacheLoginAfterLoginSuccess(Long userId) {
		// 1、更新当前登陆缓存
		LoginCache loginCache = ReqContextHolder.getLoginCache();
		loginCache.setUserId(userId);

		String token = loginCache.getToken();
		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);
		redisWrapper.setObject(tokenRedisKey, loginCache, UserRedisConfig.APP_LOGINED_TOKEN_EXPIRE_MILLIS);
		
		// 2、删除上次登陆的缓存
		String userIdRedisKey = LoginRedisConfig.getUserIdRedisKey(userId);
		// 上一次登陆成功保存的token
		String oldToken = redisWrapper.getString(userIdRedisKey);
		if(StringUtils.isNotBlank(oldToken)) {
			String oldTokenRedisKey = LoginRedisConfig.getTokenRedisKey(oldToken);
			redisWrapper.delete(oldTokenRedisKey);
		}
		
		// 3、保存当前登陆的“userId: token”对，以便于下次登陆成功后删除上一次的
		redisWrapper.setString(userIdRedisKey, token, UserRedisConfig.APP_LOGINED_TOKEN_EXPIRE_MILLIS);
	}

}