package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.VOIPGatewayDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class QueryVOIPGatewayListHandler extends ValueListHandler {
	private VOIPGatewayDAO dao=null;
	private final String tableName=" t_ssif_dvesmap a";
	public QueryVOIPGatewayListHandler(){
		this.dao=new VOIPGatewayDAO();
	}
	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
		"in setCriteria begin setCriteria...");
		//构造查询字符串
		constructSelectQueryString();
		//执行数据查询
		executeSearch(dao, true, true);
		
	}
	private void constructSelectQueryString() {
		StringBuffer begin = new StringBuffer();
		begin.append("select *  from " + tableName);
		StringBuffer selectStatement = new StringBuffer();
	  	selectStatement.append(" where 1=1 ");
	  	appendOrderByString(selectStatement);
	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by a.devicemodel ");
	}
}
