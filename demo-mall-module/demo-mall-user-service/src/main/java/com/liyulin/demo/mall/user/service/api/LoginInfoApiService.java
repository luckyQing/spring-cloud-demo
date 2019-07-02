package com.liyulin.demo.mall.user.service.api;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyulin.demo.common.business.LoginCache;
import com.liyulin.demo.common.business.ReqContextHolder;
import com.liyulin.demo.common.business.dto.Resp;
import com.liyulin.demo.common.business.exception.ParamValidateError;
import com.liyulin.demo.common.business.exception.ServerException;
import com.liyulin.demo.common.business.signature.LoginRedisConfig;
import com.liyulin.demo.common.business.signature.util.ReqHttpHeadersUtil;
import com.liyulin.demo.common.business.util.PasswordUtil;
import com.liyulin.demo.common.business.util.RespUtil;
import com.liyulin.demo.common.redis.RedisComponent;
import com.liyulin.demo.common.util.security.RsaUtil;
import com.liyulin.demo.mall.user.biz.api.LoginInfoApiBiz;
import com.liyulin.demo.mall.user.config.UserParamValidateMessage;
import com.liyulin.demo.mall.user.config.UserRedisConfig;
import com.liyulin.demo.mall.user.dto.login.LoginInfoInsertBizDto;
import com.liyulin.demo.mall.user.dto.login.LoginInfoInsertServiceDto;
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
	private RedisComponent redisComponent;

	/**
	 * 生成rsa公钥、私钥、token
	 * 
	 * @return
	 */
	public GetRsaKeyRespBody generateRsaKey() {
		// 客户端公钥（校验签名）、服务端私钥（签名）
		KeyPair clientPubServerPriKeyPair = null;
		// 客户端私钥（签名）、服务端公钥（校验签名）
		KeyPair clientPriServerPubkeyPair = null;
		try {
			clientPubServerPriKeyPair = RsaUtil.generateKeyPair();
			clientPriServerPubkeyPair = RsaUtil.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			throw new ServerException(UserReturnCodeEnum.GENERATE_RSAKEY_FAIL);
		}

		GetRsaKeyRespBody getRsaKeyRespBody = new GetRsaKeyRespBody();
		getRsaKeyRespBody.setCheckSignModulus(RsaUtil.getModulus(clientPubServerPriKeyPair));
		getRsaKeyRespBody.setCheckSignKey(RsaUtil.getPublicExponent(clientPubServerPriKeyPair));
		
		getRsaKeyRespBody.setSignModules(RsaUtil.getModulus(clientPriServerPubkeyPair));
		getRsaKeyRespBody.setSignKey(RsaUtil.getPrivateExponent(clientPriServerPubkeyPair));
		
		String token = ReqHttpHeadersUtil.generateToken();
		getRsaKeyRespBody.setToken(token);

		cacheRsaKey(token, clientPubServerPriKeyPair, clientPriServerPubkeyPair);
		return getRsaKeyRespBody;
	}

	/**
	 * 缓存rsa key相关信息
	 * 
	 * @param token
	 * @param clientPubServerPriKeyPair
	 * @param clientPriServerPubkeyPair
	 */
	private void cacheRsaKey(String token, KeyPair clientPubServerPriKeyPair, KeyPair clientPriServerPubkeyPair) {
		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);

		LoginCache loginCache = new LoginCache();
		loginCache.setToken(token);
		loginCache.setSignModules(RsaUtil.getModulus(clientPubServerPriKeyPair));
		loginCache.setSignKey(RsaUtil.getPrivateExponent(clientPubServerPriKeyPair));
		loginCache.setCheckSignModulus(RsaUtil.getModulus(clientPriServerPubkeyPair));
		loginCache.setCheckSignKey(RsaUtil.getPublicExponent(clientPriServerPubkeyPair));
		
		redisComponent.setObject(tokenRedisKey, loginCache, UserRedisConfig.NON_LOGIN_TOKEN_EXPIRE_MILLIS);
	}

	public void cacheDesKey(CacheDesKeyReqBody req) {
		LoginCache loginCache = ReqContextHolder.getLoginCache();
		loginCache.setAesKey(req.getKey());

		String token = loginCache.getToken();
		String tokenRedisKey = LoginRedisConfig.getTokenRedisKey(token);
		redisComponent.setObject(tokenRedisKey, loginCache, UserRedisConfig.NON_LOGIN_TOKEN_EXPIRE_MILLIS);
	}

	/**
	 * 登陆校验
	 * 
	 * @param req
	 * @return
	 */
	public Resp<LoginRespBody> login(LoginReqBody req) {
		LoginInfoEntity entity = loginInfoApiBiz.queryByUsername(req.getUsername());
		if (Objects.isNull(entity)) {
			return RespUtil.error(UserReturnCodeEnum.ACCOUNT_NOT_EXIST);
		}
		// 校验密码
		String salt = entity.getSalt();
		String securePassword = PasswordUtil.secure(req.getPassword(), salt);
		if (!Objects.equals(securePassword, entity.getPassword())) {
			return RespUtil.error(UserReturnCodeEnum.USERNAME_OR_PASSWORD_ERROR);
		}

		if (Objects.equals(entity.getUserState(), UserStateEnum.UNENABLE.getValue())) {
			return RespUtil.error(UserReturnCodeEnum.USER_UNENABLE);
		}
		if (Objects.equals(entity.getDelState(), DelStateEnum.DELETED.getDelState())) {
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
		redisComponent.setObject(tokenRedisKey, loginCache, UserRedisConfig.APP_LOGINED_TOKEN_EXPIRE_MILLIS);
		
		// 2、删除上次登陆的缓存
		String userIdRedisKey = LoginRedisConfig.getUserIdRedisKey(userId);
		// 上一次登陆成功保存的token
		String oldToken = redisComponent.getString(userIdRedisKey);
		if(StringUtils.isNotBlank(oldToken)) {
			String oldTokenRedisKey = LoginRedisConfig.getTokenRedisKey(oldToken);
			redisComponent.delete(oldTokenRedisKey);
		}
		
		// 3、保存当前登陆的“userId: token”对，以便于下次登陆成功后删除上一次的
		redisComponent.setString(userIdRedisKey, token, UserRedisConfig.APP_LOGINED_TOKEN_EXPIRE_MILLIS);
	}
	
	/**
	 * 插入登陆信息
	 * 
	 * @param dto
	 * @return
	 */
	public LoginInfoEntity insert(LoginInfoInsertServiceDto dto) {
		// 判断该用户名是否已存在
		boolean existUsername = loginInfoApiBiz.existByUsername(dto.getUsername());
		if(existUsername) {
			throw new ParamValidateError(UserParamValidateMessage.REGISTER_USERNAME_EXSITED);
		}
		
		String salt = generateRandomSalt();
		String securePassword = PasswordUtil.secure(dto.getPassword(), salt);
		
		LoginInfoInsertBizDto loginInfoInsertDto = LoginInfoInsertBizDto.builder()
				.userId(dto.getUserId())
				.username(dto.getUsername())
				.password(securePassword)
				.pwdState(dto.getPwdState())
				.salt(salt)
				.build();
		return loginInfoApiBiz.insert(loginInfoInsertDto);
	}
	
	
	/**
	 * 生成随机盐值
	 * 
	 * @return
	 */
	private String generateRandomSalt() {
		String salt = null;
		try {
			salt = PasswordUtil.generateRandomSalt();
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			throw new ServerException(UserReturnCodeEnum.GENERATE_SALT_FAIL);
		}
		return salt;
	}

}