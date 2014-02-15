package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.CARecvDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.CaRecvDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
/**
 * CA
 * @author 260327h
 *
 */
public class QueryCaEventRecvListHandler extends ValueListHandler {
	private CaRecvDAO dao = null;

	private final String tableName = " t_carecv t ";

	private CARecvDTO dto = null;

	public QueryCaEventRecvListHandler() {
		this.dao = new CaRecvDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CARecvDTO)
			this.dto = (CARecvDTO) dto1;
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

	private void constructSelectQueryString(CARecvDTO dto) {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"caRecvDto info\n"+dto.toString());
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
				
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

	  	selectStatement.append(" where 1=1 ");
	  	this.makeSQLByIntField("QUEUEID",dto.getQueueID(),selectStatement);
	  	this.makeSQLByIntField("EVENTID",dto.getEventID(),selectStatement);
	  	this.makeSQLByIntField("TRANSID",dto.getTransID(),selectStatement);
	  	
		appendOrderByString(selectStatement);

	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by TRANSID");
	}

}
