package com.liyulin.demo.code.auto.generate.handler;

import com.liyulin.demo.code.auto.generate.config.ConfigFactory;
import com.liyulin.demo.code.auto.generate.config.TableConfig;
import com.liyulin.demo.code.auto.generate.model.*;
import com.liyulin.demo.code.auto.generate.utils.JavaTypeResolverUtils;
import com.liyulin.demo.code.auto.generate.utils.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class EntityVoHandler {

	private static TableConfig tableConfig = ConfigFactory.config.getTable();

	public static EntityVo createVo(SqlResultSet resultSet) {
		String baseName = resultSet.getName();
		if (StringUtils.isNotBlank(tableConfig.getSymbol())) {
			baseName = resultSet.getName().replace(tableConfig.getSymbol(), "");
		}
		if (StringUtils.isNotBlank(tableConfig.getTablePri())) {
			String pri = tableConfig.getTablePri() + "_";
			if (baseName.indexOf(pri) != -1) {
				baseName = baseName.substring(baseName.indexOf(pri) + pri.length());
			}
		}
		baseName = StringUtils.upperCaseFirstChar(StringUtils.underlineToCamel(baseName));

		EntityVo vo = new EntityVo();
		vo.setName(baseName);
		for (Column column : resultSet.getColumns()) {
			EntityAttributeVo attribute = convertColumn(column);
			vo.getAttributes().add(attribute);
		}
		vo.setTable(resultSet);
		return vo;
	}

	public static EntityVo createVo(Table table) {
		String baseName = table.getName();
		if (StringUtils.isNotBlank(tableConfig.getSymbol())) {
			baseName = table.getName().replace(tableConfig.getSymbol(), "");
		}
		if (tableConfig.getTablePris() != null) {
			for (String str : tableConfig.getTablePris()) {
				if (baseName.indexOf(str + "_") == 0) {
					baseName = baseName.substring(str.length());
					break;
				}
			}
		}

		baseName = StringUtils
				.upperCaseFirstChar(StringUtils.underlineToCamel(baseName.toLowerCase()));
		EntityVo vo = new EntityVo();
		vo.setName(baseName);
		for (Column column : table.getColumns()) {
			EntityAttributeVo attribute = convertColumn(column);
			attribute.setColumn(column);
			vo.getAttributes().add(attribute);
		}
		if (CollectionUtils.isEmpty(table.getPrimaryKeys())) {
			throw new RuntimeException("表" + table.getName() + "无主键");
		}
		List<EntityAttributeVo> primaryKeys = new ArrayList<>();
		for (Column column : table.getPrimaryKeys()) {
			EntityAttributeVo primaryKey = convertColumn(column);
			primaryKeys.add(primaryKey);
		}
		vo.setPrimaryKeys(primaryKeys);
		vo.setTable(table);
		return vo;
	}

	private static EntityAttributeVo convertColumn(Column column) {
		String name = column.getColumnName();
		if (tableConfig.getColumnPris() != null) {
			for (String str : tableConfig.getColumnPris()) {
				if (name.indexOf(str + "_") == 0) {
					name = name.substring((str+"_").length());
					break;
				}
			}
		}
		String attributeEnumName = name.toUpperCase();
		String attributeShortName = StringUtils.underlineToCamel(name);
		name = StringUtils.lowerCaseFirstChar(attributeShortName);
		JavaTypeResolverUtils.JdbcTypeInformation type = column.getType();
		String comment = column.getColumnComment();
		EntityAttributeVo vo = new EntityAttributeVo();
		vo.setAttributeEnumName(attributeEnumName);
		String packagePath = type.getJavaPackage();
		String className = type.getJavaShortName();
		String fullName = type.getJavaFullName() == null ? "" : type.getJavaFullName();
		vo.setAttributeName(name);
		vo.setAttributeType(className);
		vo.setFullName(fullName);
		vo.setColumn(column);
		return vo;
	}

}
