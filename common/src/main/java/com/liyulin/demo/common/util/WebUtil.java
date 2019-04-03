package com.liyulin.demo.common.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * web相关工具类
 *
 * @author liyulin
 * @date 2019年4月3日下午7:57:28
 */
@UtilityClass
@Slf4j
public class WebUtil {

	/**
     * 获取请求的ip地址
     *
     * @param request http请求
     * @return ip地址
     */
    public static String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null && ip.contains(",")) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        return ip;
    }

    /**
     * ip地址是否可以ping通
     *
     * @param ipAddress ip地址
     * @param timeout   超时，单位毫秒
     * @return true | false
     */
    public static boolean ipCanPing(String ipAddress, int timeout) {
        if (StringUtils.isEmpty(ipAddress)) {
            return false;
        }

        boolean canPing = false;
        try {
            canPing = InetAddress.getByName(ipAddress).isReachable(timeout);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return canPing;
    }

    /**
     * 链接是否可以被请求
     *
     * @param url     地址
     * @param timeout 超时，单位毫秒
     * @return true | false
     */
    public static boolean AddrCanConnect(String url, int timeout) {
        if (StringUtils.isEmpty(url)) {
            return false;
        }

        boolean canConnect = false;
        try {
            URL urlConnection = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setUseCaches(false);
            connection.setConnectTimeout(timeout);
            int state = connection.getResponseCode();
            canConnect = (state == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }

        return canConnect;
    }
	
}