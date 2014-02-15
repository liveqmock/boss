package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;

import com.dtv.oss.service.dao.config.ProductPropertyDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class ProductPropertyListHandler extends ValueListHandler {
	private ProductPropertyDAO dao = null;

	private final String tableName = " T_ProductProperty ";

	private ProductPropertyDTO dto = null;

	private static Class clazz = ProductPropertyListHandler.class;

	public ProductPropertyListHandler() {
		this.dao = new ProductPropertyDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof ProductPropertyDTO)
			this.dto = (ProductPropertyDTO) dto1;
		else {
			LogUtility.log(clazz, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		//构造查询字符串
		constructSelectQueryString(dto);
		//执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(ProductPropertyDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select *  from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");

		makeSQLByIntField("ProductID", dto.getProductId(), selectStatement);
		//属性名称
		makeSQLByStringField("PropertyName", dto.getPropertyName(),
				selectStatement);
		//属性代码
		makeSQLByStringField("PropertyCode", dto.getPropertyCode(),
				selectStatement);
		//取值方式
		makeSQLByStringField("PropertyMode", dto.getPropertyMode(),
				selectStatement);
		//取值来源类型
		makeSQLByStringField("PropertyValue", dto.getPropertyValue(), selectStatement);
		//取值来源名称
		makeSQLByStringField("ResourceName", dto.getResourceName(),
				selectStatement, "like");
		//取值类型
		makeSQLByStringField("PropertyType", dto.getPropertyType(),
				selectStatement);

		//		if(!(dto.getResourceName()==null || "".equals(dto.getResourceName())))
		//			selectStatement.append(" and ResourceName like '%" + dto.getResourceName() +"%' ");

		appendOrderByString(selectStatement);

		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by ProductID desc");
	}
}
