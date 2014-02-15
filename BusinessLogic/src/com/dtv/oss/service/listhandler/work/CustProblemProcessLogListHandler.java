/*
 * Created on 2006-1-4
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.work;

import com.dtv.oss.dto.CustProblemProcessDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.work.CustProblemProcessLogDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
 

public class CustProblemProcessLogListHandler extends ValueListHandler {
	
	private CustProblemProcessLogDAO dao = null;

	final private String tableName1 = "t_custproblemprocess t ,t_commonsettingdata com ";
	final private String tableName2 = "t_jobcardprocess j ,t_commonsettingdata com ";

	/**
	 * use DTO as a template to determine search criteria
	 */
	private CustProblemProcessDTO dto = null;

	public CustProblemProcessLogListHandler() {
		this.dao = new CustProblemProcessLogDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * 
	 * @return
	 */
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(CustProblemProcessLogListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CustProblemProcessDTO)
			this.dto = (CustProblemProcessDTO) dto1;
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
		executeSearch(dao, false, false);
	}

	/**
	 * 构造查询条件 1. 工单号 jobcardid 2.用户类型 CustomerType 3.用户证号 custid
	 *  4.状态 status 5.创建时间 actionTime 6.所在区域 District
	 */
	 
	private void constructSelectQueryString(CustProblemProcessDTO dto) {
		 
		StringBuffer begin = new StringBuffer();
		begin.append("Select  com.Value  action ,t.Id,CustProblemID,t.CurrentOrgID,t.CreateDate,t.CurrentOperatorID,t.workresult,t.memo,t.nextorgid " +
				"from " + tableName1+
				"Where t.custproblemid=" + dto.getCustProblemId()+" And com.Name='SET_C_CPPROCESSACTION' And com.Key = t.action " +
				"Union All select com.Value  action ,SeqNo,JCID,CurrentOrgID,ActionTime,CurrentOperatorID,workresult,memo,nextorgid  " +
				"from "+ tableName2+" Where j.jcid In " +
				"( Select  referjobcardid  From  t_customerproblem Where Id = "+dto.getCustProblemId()+")"+" And com.Name='SET_W_JOBCARDLOGTYPE' And com.Key = j.action "
				);
		StringBuffer selectStatement = new StringBuffer(128);
	//	selectStatement.append(" where 1=1 ");
		 
		
		 
		/*
		 * Search Condition:
		 * 
		 */
	//	makeSQLByIntField("t.custproblemid", dto.getCustProblemId(), selectStatement); 
		 
	 
		selectStatement.append(" Order By createdate Desc");   
		//appendOrderByString(end);
		//设置构造取得当前查询总纪录数的sql
		// setRecordCountQueryTable(tableName);
      //  System.out.println("**********the sql string is "+begin.append(selectStatement));
	   //	setRecordCountSuffixBuffer(selectStatement);
		  
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