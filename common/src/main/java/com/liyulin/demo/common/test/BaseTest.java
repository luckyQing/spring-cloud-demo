package com.liyulin.demo.common.test;

import org.junit.runner.RunWith;
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

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class BaseTest {

	@Autowired
	protected WebApplicationContext webApplicationContext;

	public <T> T postXml(String url, Object req, Class<T> beanClass) throws Exception {
		MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		String xml = JAXBUtil.beanToXml(req);
		log.info("requestBody={}", xml);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_XML)
				.content(xml).accept(MediaType.APPLICATION_XML)).andReturn();

		String content = result.getResponse().getContentAsString();
		log.info("content={}", content);

		return JAXBUtil.xmlToBean(content, beanClass);
	}

	public <T> T postJson(String url, Object req, TypeReference<T> typeReference) throws Exception {
		MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		String requestBody = JSON.toJSONString(req);
		log.info("requestBody={}", requestBody);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(requestBody).accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();

		String content = result.getResponse().getContentAsString();
		log.info("content={}", content);

		return JSON.parseObject(content, typeReference);
	}

}