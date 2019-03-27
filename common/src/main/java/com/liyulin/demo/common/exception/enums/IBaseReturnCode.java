package com.liyulin.demo.common.exception.enums;

/**
 * 状态码格式接口类：XXXXXX（服务模块编码|类型）
 * 
 * <ul>
 * <li>XXX1XX：信息类
 * <li>XXX2XX：操作成功
 * <li>XXX3XX：重定向
 * <li>XXX4XX：客户端错误
 * <li>XXX5XX：服务器错误
 * </ul>
 *
 * @author liyulin
 * @date 2019年3月27日下午11:24:10
 */
public interface IBaseReturnCode {

	String getCode();

	String getMsg();
	
}