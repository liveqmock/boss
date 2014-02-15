package com.dtv.oss.service.listhandler.network;

import java.util.List;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.BandProcessLogDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class QueryBandLogListHandler extends ValueListHandler {
	private BandProcessLogDAO dao = null;
	private String selectCriteria = "";
	final private String tableName = " t_bbiqueue queue left join t_bbiprocesslog process on queue.queueid=process.queueid ";

	/**
	 * use DTO as a template to determine search criteria
	 */
//	private CommonQueryConditionDTO dto = null;
	private CommonQueryConditionDTO dto = null;

	public QueryBandLogListHandler() {
		this.dao = new BandProcessLogDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * 
	 * @return
	 */
	 
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(QueryCaEventLogListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(QueryCaEventLogListHandler.class, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		
		constructSelectQueryString(dto);
		
		//执行数据查询
		executeSearch(dao, true, true);
		
	}
	 
	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
		 
		StringBuffer begin = new StringBuffer();
		begin.append("select queue.queueid seqNo,"+
					"queue.eventid eventid,"+
					"queue.commandname commandname,"+
					"queue.serviceaccountid serviceaccountid,"+
					"queue.status status,"+
					"process.description description,"+
					"process.dt_create dt_create,"+
					"process.dt_lastmod dt_lastmod "+
					"from " + tableName);
		
		StringBuffer selectStatement = new StringBuffer(128);
		if(dto.getSpareStr4() != null || dto.getSpareStr3() != null){
		  	selectStatement.append(" left join t_serviceaccount sa on sa.serviceaccountid=queue.serviceaccountid left join t_customer cust on cust.customerid=sa.customerid ");
	  	}
		if(dto.getSpareStr5() != null){
		  	selectStatement.append(" left join t_customerproduct cp on cp.serviceaccountid=queue.serviceaccountid left join t_cpconfigedproperty pro on pro.psid=cp.psid ");
	  	}
		
		selectStatement.append(" where 1=1 ");
		
		if(dto.getSpareStr1() != null){
		  	selectStatement.append(" and queue.commandname='"+dto.getSpareStr1()+"' ");
	  	}
	  	if(dto.getSpareStr2() != null){
		  	selectStatement.append(" and queue.status='"+dto.getSpareStr2()+"' ");
	  	}
	  	if(dto.getSpareStr3() != null){
		  	selectStatement.append(" and cust.customerid="+dto.getSpareStr3()+" ");
	  	}
	  	if(dto.getSpareStr4() != null){
		  	selectStatement.append(" and cust.name ='"+dto.getSpareStr4()+"' ");
	  	}
	  	if(dto.getSpareStr5() != null){
		  	selectStatement.append(" and pro.propertycode='30002' and pro.propertyvalue='"+dto.getSpareStr5()+"' ");
	  	}
	  	
		if (dto.getBeginTime()!=null)
			selectStatement.append(" and process.dt_create>=to_timestamp('")
			.append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		if (dto.getEndTime()!=null)
			selectStatement.append(" and process.dt_create<=to_timestamp('")
			.append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		appendOrderByString(selectStatement);
		//appendOrderByString(end);
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	  
	 
	private void appendOrderByString(StringBuffer selectStatement) {				

			selectStatement.append(" order by seqNo desc");
		
		}
}
