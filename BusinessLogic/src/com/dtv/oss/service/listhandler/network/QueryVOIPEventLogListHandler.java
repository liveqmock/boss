package com.dtv.oss.service.listhandler.network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dtv.oss.dto.SystemEventDTO;
import com.dtv.oss.dto.wrap.CAEventWrap;
import com.dtv.oss.dto.wrap.VOIPEventWrap;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.VOIPEventLogDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class QueryVOIPEventLogListHandler extends ValueListHandler {
	private VOIPEventLogDAO dao = null;

	private final String tableName = " t_systemevent ";

	private VOIPEventWrap dto = null;

	public QueryVOIPEventLogListHandler() {
		this.dao = new VOIPEventLogDAO();
	} 

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof VOIPEventWrap)
			this.dto = (VOIPEventWrap) dto1;
		else {
			LogUtility.log(this.getClass(), LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		//构造查询字符串
		constructSelectQueryString(dto);
		//执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(VOIPEventWrap wrap) {
		
		SystemEventDTO sysEventDto = wrap.getSysEventDTO();
		StringBuffer begin = new StringBuffer();
		begin.append("select *  from " + tableName);
		StringBuffer selectStatement = new StringBuffer();
	  	selectStatement.append(" where 1=1 and eventclass in( select distinct(eventclass) from t_ssif_eventcmdmap) ");
	  	this.makeSQLByIntField("eventclass",sysEventDto.getEventClass(),selectStatement);
	  	if(wrap.getStartNO()>0){
		  	selectStatement.append(" and sequenceno >= ");
		  	selectStatement.append(wrap.getStartNO());
	  	}
	  	if(wrap.getEndNO()>0){
		  	selectStatement.append(" and sequenceno <=");
		  	selectStatement.append(wrap.getEndNO());
	  	}
		if (wrap.getStartTime()!=null)
			selectStatement.append(" and dt_create>=to_timestamp('")
			.append(wrap.getStartTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		if (wrap.getEndTime()!=null)
			selectStatement.append(" and dt_create<=to_timestamp('")
			.append(wrap.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		//this.makeSQLByStringField("c.servicecode",wrap.getServiceCode(),selectStatement);
	  	this.makeSQLByIntField("customerid",sysEventDto.getCustomerID(),selectStatement);
	  	this.makeSQLByIntField("serviceaccountid",sysEventDto.getServiceAccountID(),selectStatement);
	  	this.makeSQLByIntField("csiid",sysEventDto.getCsiID(),selectStatement);
	  	if(wrap.getServiceCode()!=null && !"".equals(wrap.getServiceCode())){
	  		selectStatement.append(" And serviceaccountid in( select distinct(serviceaccountid)  from t_serviceaccount  where servicecode='"+wrap.getServiceCode()+"')");
	  	}
	  	appendOrderByString(selectStatement);
	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by sequenceno desc");
	}
	
	
}
