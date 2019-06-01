package com.liyulin.demo.common.business.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.liyulin.demo.common.util.JAXBUtil;
import com.liyulin.demo.common.util.UnitTestUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * SpringBoot单元测试基类
 *
 * @author liyulin
 * @date 2019年4月22日上午12:25:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public abstract class AbstractUnitTest {
	
	static {
		UnitTestUtil.setTest(true);
		// 单元测试环境下，关闭eureka
		System.setProperty("eureka.client.enabled", "false");
	}

	@Autowired
	protected WebApplicationContext applicationContext;
	protected MockMvc mockMvc;
	
	@Before
	public void initMock() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
	}
	
	protected <T> T postXml(String url, Object req, Class<T> beanClass) throws Exception {
		String xml = JAXBUtil.beanToXml(req);
		log.info("test.requestBody={}", xml);

		MvcResult result = mockMvc.perform(
					MockMvcRequestBuilders.post(url)
					.contentType(MediaType.APPLICATION_XML)
					.content(xml)
					.accept(MediaType.APPLICATION_XML)
				).andReturn();

		String content = result.getResponse().getContentAsString();
		log.info("test.result={}", content);

		return JAXBUtil.xmlToBean(content, beanClass);
	}

	protected <T> T postJson(String url, Object req, TypeReference<T> typeReference) throws Exception {
		String requestBody = JSON.toJSONString(req);
		log.info("test.requestBody={}", requestBody);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post(url)
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(requestBody)
					.accept(MediaType.APPLICATION_JSON_UTF8)
				).andReturn();

		String content = result.getResponse().getContentAsString();
		log.info("test.result={}", content);

		return JSON.parseObject(content, typeReference);
	}

}