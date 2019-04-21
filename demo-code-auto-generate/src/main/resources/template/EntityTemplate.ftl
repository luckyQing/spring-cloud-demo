package ${package};

import javax.persistence.*;
import lombok.*;
<#list vo.attrFullNames as attrFullName>
 import ${attrFullName};
</#list>

/**
 * @Desc  ${vo.table.tableComment}
 * @author ${generate.author}
 * @date ${generate.createDate}
 * @Copyright (c) ${generate.config.company}
 */
 @Getter
 @Setter
@Table(name = "${vo.table.name}")
public class ${vo.name} extends BaseEntity {
	
	private static final long serialVersionUID = ${generate.serialVersionUID};
	
<#list vo.attributes as attribute>
	<#if attribute.comment??>
    /**
     * ${attribute.comment}
     */
	</#if>
	 <#if attribute.column.primaryKey>
    @Id
	 </#if>
    @Column(name = "${attribute.column.columnName}")
	protected ${attribute.attributeType} ${attribute.attributeName};
	
</#list>


	/**
	 * 实体对象的属性字段
	 */
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Columns {

    	<#list vo.attributes as attribute>
    	<#if attribute.comment??>
        /**
         * ${attribute.comment}
         */
    	</#if>
    	${attribute.attributeEnumName}("${attribute.attributeName}","${attribute.column.columnName}"),
    	</#list>
    	;

        private String property;
        private String column;
    }
}

