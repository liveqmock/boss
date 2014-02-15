package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.VOIPConditionDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class QueryVOIPConditionListHandler extends ValueListHandler {
	private VOIPConditionDAO dao=null;
	
	private final String tableName = " t_ssif_condition a ";
	public QueryVOIPConditionListHandler(){
		this.dao=new VOIPConditionDAO();
	}
	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
		"in setCriteria begin setCriteria...");
		//�����ѯ�ַ���
		constructSelectQueryString();
		//ִ�����ݲ�ѯ
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
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by a.conditionid desc");
	}
}
