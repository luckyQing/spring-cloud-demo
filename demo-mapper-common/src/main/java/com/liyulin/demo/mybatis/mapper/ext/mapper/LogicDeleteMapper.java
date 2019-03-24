package com.liyulin.demo.mybatis.mapper.ext.mapper;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import com.liyulin.demo.mybatis.mapper.ext.provider.LogicDeleteProvider;

/**
 * 逻辑删除
 *
 * @author liyulin
 * @date 2019年3月24日下午8:38:56
 */
public interface LogicDeleteMapper<T> {

	/**
	 * 根据主键字段进行逻辑删除，方法参数必须包含完整的主键属性
	 *
	 * @param key
	 * @return
	 */
	@UpdateProvider(type = LogicDeleteProvider.class, method = "dynamicSQL")
	int logicDeleteByPrimaryKey(@Param("id") BigInteger id, @Param("delUser") BigInteger delUser,
			@Param("delTime") Date delTime);

	/**
	 * 根据主键字段进行批量逻辑删除，方法参数必须包含完整的主键属性
	 *
	 * @param key
	 * @return
	 */
	@UpdateProvider(type = LogicDeleteProvider.class, method = "dynamicSQL")
	int logicDeleteByPrimaryKeys(@Param("ids") List<BigInteger> ids, @Param("delUser") BigInteger delUser,
			@Param("delTime") Date delTime);

}