/*
 * Created on 2005-12-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.system;

import com.dtv.oss.dto.SystemLogDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.system.LogDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author 241115c
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LogListHandler extends ValueListHandler {

	private LogDAO dao = null;
	final private String tableName = "t_systemlog a ";
	private SystemLogDTO dto = null;

	public LogListHandler() {
		this.dao = new LogDAO();
	}
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(LogListHandler.class, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof SystemLogDTO)
			this.dto = (SystemLogDTO) dto1;
		else {
			LogUtility.log(LogListHandler.class, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		// 构造查询字符串
		constructSelectQueryString(dto);
		// 执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(SystemLogDTO dto) {
		//由于tableName临时会变动，所以添加这个变量
		String strTableName=tableName;
		
		StringBuffer begin = new StringBuffer();
		begin.append("select a.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		makeSQLByIntField("a.SEQUENCENO", dto.getSequenceNo(), selectStatement);
		makeSQLByStringField("a.OPERATION", dto.getOperation(), selectStatement);
		makeSQLByStringField("a.MACHINENAME", dto.getMachineName(),selectStatement);
		makeSQLByStringField("a.LOGCLASS", dto.getLogClass(), selectStatement);
		makeSQLByIntField("a.OPERATORID", dto.getOperatorID(), selectStatement);
		
		/**
		if (dto.getCustomerID() != 0) {
			strTableName=strTableName +",t_customer cust ";
			begin.append(",t_customer cust ");
			selectStatement.append(" and a.customerid=cust.customerid ");
			selectStatement.append(" and ( cust.customerid=" + dto.getCustomerID() +" or cust.groupcustid=" + dto.getCustomerID() +") ");
			//appendStringWithGroupCustomerID("a.CUSTOMERID", dto.getCustomerID() + "", selectStatement);
		}
		**/
		if (dto.getCustomerID() != 0) {
			selectStatement.append(" and a.customerid="+dto.getCustomerID() );
		}
		
		makeSQLByStringField("a.LOGINID", dto.getLoginID(), selectStatement);
		makeSQLByStringField("a.CUSTOMERNAME", dto.getCustomerName(),selectStatement);
		makeSQLByStringField("a.MODULENAME", dto.getModuleName(),selectStatement);

		if (dto.getDtCreate() != null)
			selectStatement.append(" and a.CREATEDATETIME>=to_timestamp('").append(dto.getDtCreate().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getDtLastmod() != null)
			selectStatement.append(" and a.CREATEDATETIME<=to_timestamp('").append(dto.getDtLastmod().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		//侯添加,集团客户日志查询时,按客户ID排序.
		/**
		if (dto.getCustomerID() != 0) 
			selectStatement.append(" order by a.CUSTOMERID, a.SEQUENCENO desc");
		else
		**/
			selectStatement.append(" order by  a.SEQUENCENO desc");

		// 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(strTableName);
		setRecordCountSuffixBuffer(selectStatement);
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}
