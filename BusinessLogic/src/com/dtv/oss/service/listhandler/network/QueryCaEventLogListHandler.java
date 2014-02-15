/*
 * Created on 2007-1-5
 *  
 */
package com.dtv.oss.service.listhandler.network;

import java.util.List;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.dto.wrap.CAEventWrap;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.CaEventLogDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
 
public class QueryCaEventLogListHandler extends ValueListHandler {
	private CaEventLogDAO dao = null;
	private String selectCriteria = "";
	final private String tableName = "t_caqueue c";

	/**
	 * use DTO as a template to determine search criteria
	 */
//	private CommonQueryConditionDTO dto = null;
	private CAEventWrap dto = null;

	public QueryCaEventLogListHandler() {
		this.dao = new CaEventLogDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * 
	 * @return
	 */
	 
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(QueryCaEventLogListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CAEventWrap)
			this.dto = (CAEventWrap) dto1;
		else {
			LogUtility.log(QueryCaEventLogListHandler.class, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
	 
		constructSelectQueryString(dto);
	 
		//执行数据查询
		executeSearch(dao, true, true);
		List logList=this.getList();
		CAEventWrap wrap=new CAEventWrap();
		/**
		if(logList.size()==1){
			wrap=(CAEventWrap) logList.get(0);
			String queueID=wrap.getCaEventDTO().getSpareStr1();
			int queueId = Integer.valueOf(queueID).intValue();
			ArrayList recvList=BusinessUtility.getCARecvList(queueId);
			wrap.setCaRecvList(recvList);
			ArrayList sentList=BusinessUtility.getCASentList(queueId);
			wrap.setCaSentList(sentList);
			ArrayList queueList=BusinessUtility.getCAQueueList(queueId);
			wrap.setCaQueueList(queueList);			
		}
		**/
	}
	 
	private void constructSelectQueryString(CAEventWrap wrap) {
		 
		CommonQueryConditionDTO caEventDto = wrap.getCaEventDTO();
		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 ");
		 
		/*
		 * Search Condition:
		 * 
		 */
		makeSQLByStringField("c.queueid", caEventDto.getSpareStr1(), selectStatement);
		makeSQLByStringField("c.SCNR", caEventDto.getSpareStr2(), selectStatement);
		makeSQLByStringField("c.StbNR", caEventDto.getSpareStr3(), selectStatement);
		makeSQLByStringField("c.EventClass", caEventDto.getSpareStr4(), selectStatement);
		makeSQLByStringField("c.status", caEventDto.getSpareStr5(), selectStatement);
		makeSQLByIntField("c.CustomerID", caEventDto.getCustomerID(), selectStatement);
		if(caEventDto.getDistrict()>0){
		  	selectStatement.append(" and c.eventid >=");
		  	selectStatement.append(caEventDto.getDistrict());
	  	}
	  	if(caEventDto.getStreet()>0){
		  	selectStatement.append(" and c.eventid<=");
		  	selectStatement.append(caEventDto.getStreet());
	  	}
		if (caEventDto.getBeginTime()!=null)
			selectStatement.append(" and c.dt_create>=to_timestamp('")
			.append(caEventDto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		if (caEventDto.getEndTime()!=null)
			selectStatement.append(" and c.dt_create<=to_timestamp('")
			.append(caEventDto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		 
        

 
		 
	 

		appendOrderByString(selectStatement);
		//appendOrderByString(end);
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	  
	 
	private void appendOrderByString(StringBuffer selectStatement) {				
	 
			selectStatement.append(" order by c.queueid  desc");
		 
		
		}
}
