package com.liyulin.demo.common.business.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.business.security.enums.ReqHttpHeadersEnum;
import com.liyulin.demo.common.business.security.util.ReqHttpHeadersUtil;
import com.liyulin.demo.common.util.MockitoUtil;
import com.liyulin.demo.common.util.NonceUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * SpringBoot集成测试基类
 *
 * @author liyulin
 * @date 2019年4月22日上午12:25:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
// 单元测试中，通过事务回滚，还原对DB的修改
@Rollback
@Transactional
@Slf4j
public abstract class AbstractIntegrationTest {

	@Autowired
	protected WebApplicationContext applicationContext;
	protected static MockMvc mockMvc = null;
	/** 测试的默认sign */
	private static final String DEFAULT_TEST_SIGN = "test";
	
	static {
		// 单元测试环境下，关闭eureka
		System.setProperty("eureka.client.enabled", "false");
	}
	
	@Before
	public void initMock() {
		if (mockMvc == null) {
			mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
		}
	}
	
	@After
	public void after() {
		MockitoUtil.revertMockAttribute(applicationContext);
	}

	/**
	 * 设置对象属性为mock对象
	 * 
	 * @param targetObject
	 * @param mockObject
	 */
	protected static void setMockAttribute(Object targetObject, Object mockObject) {
		MockitoUtil.setMockAttribute(targetObject, mockObject, MockitoUtil.MockTypeEnum.MOCK_BORROW);
	}

	/**
	 * 归还mock对象为真实对象
	 * 
	 * @param targetObject
	 * @param realObject
	 */
	protected static void revertMockAttribute(Object targetObject, Object realObject) {
		MockitoUtil.setMockAttribute(targetObject, realObject, MockitoUtil.MockTypeEnum.MOCK_REVERT);
	}
	
	/**
	 * post请求（不带请求头部信息）
	 * 
	 * @param url           请求mapping的地址
	 * @param req           请求参数
	 * @param typeReference 返回对象类型
	 * @return
	 * @throws Exception
	 */
	protected <T> T postWithNoHeaders(String url, Object req, TypeReference<T> typeReference) throws Exception {
		String requestBody = JSON.toJSONString(req);
		log.info("test.requestBody={}", requestBody);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post(url)
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.content(requestBody)
				).andReturn();

		String content = result.getResponse().getContentAsString();
		log.info("test.result={}", content);

		return JSON.parseObject(content, typeReference);
	}
	
	/**
	 * post请求（请求头部带有token、nonce、timestamp、sign等）
	 * 
	 * @param url           请求mapping的地址
	 * @param req           请求参数
	 * @param typeReference 返回对象类型
	 * @return
	 * @throws Exception
	 */
	protected <T> T postWithHeaders(String url, Object req, TypeReference<T> typeReference)
			throws Exception {
		return postWithHeaders(url, req, null, typeReference);
	}
	
	/**
	 * post请求（请求头部带有token、nonce、timestamp、sign等）
	 * 
	 * @param url           请求mapping的地址
	 * @param req           请求参数
	 * @param token         请求token；不传时，默认生成一个
	 * @param typeReference 返回对象类型
	 * @return
	 * @throws Exception
	 */
	protected <T> T postWithHeaders(String url, Object req, String token, TypeReference<T> typeReference)
			throws Exception {
		String requestBody = JSON.toJSONString(req);
		HttpHeaders httpHeaders = generateHeaders(token);
		log.info("test.httpHeaders={}; requestBody={}", JSON.toJSONString(httpHeaders), requestBody);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post(url)
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.headers(httpHeaders)
					.content(requestBody)
				).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info("test.result={}", content);
		
		return JSON.parseObject(content, typeReference);
	}
	
	/**
	 * 生成默认的header信息
	 * 
	 * @param token 请求token；不传时，默认生成一个
	 * @return
	 */
	public <T> HttpHeaders generateHeaders(String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(ReqHttpHeadersEnum.SMART_TOKEN.getHeaderName(),
				StringUtils.isBlank(token) ? ReqHttpHeadersUtil.generateToken() : token);
		httpHeaders.add(ReqHttpHeadersEnum.SMART_NONCE.getHeaderName(), NonceUtil.getInstance().nextId());
		httpHeaders.add(ReqHttpHeadersEnum.SMART_TIMESTAMP.getHeaderName(), String.valueOf(System.currentTimeMillis()));
		httpHeaders.add(ReqHttpHeadersEnum.SMART_SIGN.getHeaderName(), DEFAULT_TEST_SIGN);

		return httpHeaders;
	}
	
}