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
		selectStatement.append(" order by a.devicemodel ");
	}
}
