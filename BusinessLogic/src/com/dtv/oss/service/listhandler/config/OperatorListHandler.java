/*
 * Created on 2006-4-29 by Stone Liang
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.OperatorDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class OperatorListHandler extends ValueListHandler {
	private OperatorDAO dao = null;

	private String selectCriteria = "";

	final private String tableName = "t_operator";

	/**
	 * use DTO as a template to determine
	 * search criteria
	 */
	private OperatorDTO dto = null;

	public OperatorListHandler() {
		this.dao = new OperatorDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * @return
	 */
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(OperatorListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof OperatorDTO)
			this.dto = (OperatorDTO) dto1;
		else {
			LogUtility.log(OperatorListHandler.class, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		//构造查询字符串
		constructSelectQueryString(dto);
		//执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(OperatorDTO dto) {
//		LogUtility.log(DistrictSettingListHandler.class, LogLevel.DEBUG,"dto.getBelongTo():\n"+dto.getBelongTo());
//		LogUtility.log(DistrictSettingListHandler.class, LogLevel.DEBUG,"dto.getId():\n"+dto.getId());
		StringBuffer begin = new StringBuffer();
		begin.append("select *  from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		 
	    makeSQLByIntField("orgid", dto.getOrgID(), selectStatement);
	 
	    makeSQLByIntField("operatorid", dto.getOperatorID(), selectStatement);
	    makeSQLByStringField("loginID", dto.getLoginID(), selectStatement,"like");
	    makeSQLByStringField("operatorname", dto.getOperatorName(), selectStatement,"like");
 
	    makeSQLByStringField("status", dto.getStatus(), selectStatement);
 
		appendOrderByString(selectStatement);

		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by operatorid desc");
	}
}
