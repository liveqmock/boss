/*
 * Created on 2005-9-28
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.work;

import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.work.JobCardProcessLogDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
 

public class JobCardProcessLogListHandler extends ValueListHandler {
	private JobCardProcessLogDAO dao = null;

	 

	final private String tableName = "T_jobCardProcess jcp";

	/**
	 * use DTO as a template to determine search criteria
	 */
	private JobCardProcessDTO dto = null;

	public JobCardProcessLogListHandler() {
		this.dao = new JobCardProcessLogDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * 
	 * @return
	 */
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(JobCardProcessLogListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof JobCardProcessDTO)
			this.dto = (JobCardProcessDTO) dto1;
		else {
			LogUtility.log(JobCardProcessLogListHandler.class, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		// added by Horace 2004-11-3
	//	if (fillDTOWithPrivilege(dto) == false)
	//		return;
		//构造查询字符串
		constructSelectQueryString(dto);
		//执行数据查询
		executeSearch(dao, true, true);
	}

	/**
	 * 构造查询条件 1. 工单号 jobcardid 2.用户类型 CustomerType 3.用户证号 custid
	 *  4.状态 status 5.创建时间 actionTime 6.所在区域 District
	 */
	 
	private void constructSelectQueryString(JobCardProcessDTO dto) {
		 
		StringBuffer begin = new StringBuffer();
		begin.append("select jcp.* from " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 ");
		 
		
		 
		/*
		 * Search Condition:
		 * 
		 */
		makeSQLByIntField("jcp.jcid", dto.getJcId(), selectStatement); 
		 

		selectStatement.append(" order by jcp.seqno desc");   
		//appendOrderByString(end);
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		  
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	/**
	 * Use fillDTOWithPrivilege() method to filter by operator's privilege
	 * 
	 * @return
	
	private boolean fillDTOWithPrivilege(CommonQueryConditionDTO dto)
			throws ListHandlerException {

		switch (getBelongTo()) {
		case OPERATOR_BELONG_TO_GENERAL_COMPANY:
			return true;
		case OPERATOR_BELONG_TO_SUBCOMPANY:
			dto.setFiliale(getOperator().getOrgID());
			return true;
		case OPERATOR_BELONG_TO_STREET_STATION:
			dto.setStreet(getOperator().getOrgID());
			return true;
		default:
			return false;
		}
	}
	 */

	
	 
	 
}