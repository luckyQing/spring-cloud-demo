package com.liyulin.demo.common.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Charsets;

import lombok.experimental.UtilityClass;

/**
 * http工具类
 *
 * @author liyulin
 * @date 2019年5月3日下午5:56:28
 */
@UtilityClass
public class HttpUtil {
	
	/** 默认编码 */
	private static final String DEFAULT_CHARSET = Charsets.UTF_8.name();
	/** 默认从服务器获取响应数据的超时时间（单位毫秒，默认10秒） */
	private static final int DEFAULT_SOCKET_TIMEOUT = 10000;
	/** 默认socket的连接超时时间（单位毫秒，默认10秒） */
	private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
	/** 从连接池中获取连接的超时时间（单位毫秒，默认2秒） */
	private static final int CONNECTION_REQUEST_TIMEOUT = 2000;

	/**
	 * post方式请求（编码为UTF-8，超时时间10秒）
	 * 
	 * @param url
	 * @param stringEntity 请求体中的数据
	 * @return
	 * @throws IOException
	 */
	public static String postWithRaw(String url, String stringEntity) throws IOException {
		return postWithRaw(url, stringEntity, DEFAULT_CHARSET, DEFAULT_SOCKET_TIMEOUT, DEFAULT_CONNECT_TIMEOUT);
	}

	/**
	 * 以post方式请求（编码为UTF-8）
	 * 
	 * @param url
	 * @param stringEntity   请求体中的数据
	 * @param socketTimeout  从服务器获取响应数据的超时时间
	 * @param connectTimeout socket的连接超时时间，单位毫秒
	 * @return
	 * @throws IOException
	 */
	public static String postWithRaw(String url, String stringEntity, int socketTimeout, int connectTimeout)
			throws IOException {
		return postWithRaw(url, stringEntity, DEFAULT_CHARSET, socketTimeout, connectTimeout);
	}

	/**
	 * 以post方式请求
	 * 
	 * @param url
	 * @param stringEntity   请求体中的数据
	 * @param charset        编码方式
	 * @param socketTimeout  从服务器获取响应数据的超时时间，单位毫秒
	 * @param connectTimeout socket的连接超时时间，单位毫秒
	 * @return
	 * @throws IOException
	 */
	public static String postWithRaw(String url, String stringEntity, String charset, int socketTimeout,
			int connectTimeout) throws IOException {
		RequestConfig requestConfig = createRequestConfig(socketTimeout, connectTimeout);

		HttpPost post = new HttpPost(url);
		post.setConfig(requestConfig);
		post.setHeader("Content-Type", "application/json");
		post.setEntity(new StringEntity(stringEntity, charset));
		try (CloseableHttpClient client = HttpClientBuilder.create().build();) {
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, charset);
			}
		}

		return null;
	}

	/**
	 * 构建RequestConfig对象
	 * 
	 * @param socketTimeout  从服务器获取响应数据的超时时间，单位毫秒
	 * @param connectTimeout socket的连接超时时间，单位毫秒
	 * @return
	 */
	private static RequestConfig createRequestConfig(int socketTimeout, int connectTimeout) {
		return RequestConfig.custom()
				.setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
				.build();
	}

}