package com.liyulin.demo.code.auto.generate.model;

import com.liyulin.demo.mybatis.common.mapper.entity.BaseEntity;

import java.lang.reflect.Field;
import java.util.*;

public class EntityVo {

	/** serialVersionUID*/
	private static final long serialVersionUID = 5001950227468763406L;

	private String name;

	private Table table;

	private List<EntityAttributeVo> attributes = new ArrayList<EntityAttributeVo>();

	private List<EntityAttributeVo> primaryKeys;

	private List<String> attrFullNames = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EntityAttributeVo> getPrimaryKey() {
		return primaryKeys;
	}

	public void setPrimaryKeys(List<EntityAttributeVo> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public List<EntityAttributeVo> getAttributes() {
		removeSysAttribute();
	    return attributes;
	}

	public void setAttributes(List<EntityAttributeVo> attributes) {
		this.attributes = attributes;
	}

	public List<String> getAttrFullNames() {
		for (EntityAttributeVo attribute : attributes) {
			String strTemp = attribute.getFullName();
			if (strTemp != null && !"".equals(strTemp)) {
				boolean isExists = false;
				for (String str : attrFullNames) {
					if (str.equals(strTemp)) {
						isExists = true;
						break;
					}
				}
				if (!isExists) {
					attrFullNames.add(strTemp);
				}
			}
		}
		Collections.sort(attrFullNames);
		return attrFullNames;
	}

	public void setAttrFullNames(List<String> attrFullNames) {
		this.attrFullNames = attrFullNames;
	}

	/**
	 * 移除系统属性
	 */
	public void removeSysAttribute(){
		if (attributes == null || attributes.size() ==0){
			return;
		}
        BaseEntity.Columns[] set = BaseEntity.Columns.values();
        for (BaseEntity.Columns column : set) {
        	Iterator<EntityAttributeVo> iterator = attributes.iterator();
        	while (iterator.hasNext()){
        		EntityAttributeVo attributeVo = iterator.next();
				if (attributeVo.getColumn() != null
						&& column.getColumn().equals(attributeVo.getColumn().getColumnName())) {
					iterator.remove();
				}
			}
        }
	}
}
