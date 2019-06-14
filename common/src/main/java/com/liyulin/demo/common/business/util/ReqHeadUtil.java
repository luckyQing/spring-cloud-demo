package com.liyulin.demo.common.business.util;

import org.springframework.beans.BeansException;

import com.liyulin.demo.common.business.dto.ReqHead;
import com.liyulin.demo.common.properties.SmartProperties;
import com.liyulin.demo.common.util.LogUtil;
import com.liyulin.demo.common.util.SpringUtil;
import com.liyulin.demo.common.util.TransactionIdUtil;

import lombok.experimental.UtilityClass;

/**
 * {@link ReqHead}工具类
 *
 * @author liyulin
 * @date 2019年4月22日上午12:26:07
 */
@UtilityClass
public class ReqHeadUtil {

	public static ReqHead of() {
		SmartProperties smartProperties = null;
		try {
			smartProperties = SpringUtil.getBean(SmartProperties.class);
		} catch (BeansException e) {
			LogUtil.warn("获取bean[SmartProperties]失败", e);
		}
		String apiVersion = null;
		if (smartProperties == null) {
			apiVersion = System.getProperty(SmartProperties.PropertiesName.API_VERSION);
		} else {
			apiVersion = smartProperties.getApiVersion();
		}

		return of(null, apiVersion);
	}

	public static ReqHead of(String token, String apiVersion) {
		ReqHead reqHead = new ReqHead();
		reqHead.setTransactionId(TransactionIdUtil.getInstance().nextId());
		reqHead.setTimestamp(System.currentTimeMillis());
		reqHead.setToken(token);
		reqHead.setApiVersion(apiVersion);
		return reqHead;
	}

}