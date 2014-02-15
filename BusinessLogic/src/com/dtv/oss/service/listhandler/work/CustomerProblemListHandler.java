/*
 * Created on 2005-11-1
 *  
 */
package com.dtv.oss.service.listhandler.work;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.dao.work.CustomerProblemDAO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
 
public class CustomerProblemListHandler extends ValueListHandler {
	private CustomerProblemDAO dao = null;
	private String selectCriteria = "";
	final private String tableName = "T_CustomerProblem cp";

	/**
	 * use DTO as a template to determine search criteria
	 */
	private CommonQueryConditionDTO dto = null;

	public CustomerProblemListHandler() {
		this.dao = new CustomerProblemDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * 
	 * @return
	 */
	 
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(CustomerProblemListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(CustomerProblemListHandler.class, LogLevel.DEBUG,
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
	   * Use fillSqlWithPrivilege() method to filter by operator's privilege
	   * @return

	  private boolean fillDTOWithPrivilege(CommonQueryConditionDTO dto) throws ListHandlerException {
	  
	  	
	  	switch(getBelongTo()) {
	  	  case OPERATOR_BELONG_TO_GENERAL_COMPANY:
	  	  	//总公司可以看所有受理单
	  		return true;
	  	  case OPERATOR_BELONG_TO_SUBCOMPANY:
	  	  	//分公司只能看所有该分公司下的客户的，以及该分公司做过操作的报修单	  	
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
	private void constructSelectQueryString(CommonQueryConditionDTO dto)throws ListHandlerException {
		 
		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 ");
		 
		/*
		 * Search Condition:
		 * 
		 */
		makeSQLByStringField("cp.id", dto.getNo(), selectStatement);
		 
                //cretetime
		if(dto.getBeginTime()!=	null)
			 
			selectStatement.append(" and cp.createdate>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		if(dto.getEndTime()!=null)
			 
			selectStatement.append(" and cp.createdate<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

//		status
		if (dto.getSpareStr1() != null) {
			selectStatement.append(" and cp.status in('").append(dto.getSpareStr1().replaceAll(";", "','")).append("')");
		}
		 
		//spareStr13:	 报修类别
		if (dto.getSpareStr13() != null)
			selectStatement.append(" and cp.ProblemCategory = '").append(dto.getSpareStr13()).append("'");
//		add by chenjiang 2005/03/17 spareStr14:	 收费类别
		if (dto.getSpareStr14() != null)
			selectStatement.append(" and cp.feeClass = '").append(dto.getSpareStr14()).append("'"); 
		
		appendPrivilege(selectStatement,dto.getSpareStr15());
		appendOrderByString(selectStatement);
		//appendOrderByString(end);
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
		System.out.println(begin.toString());
	}

	  
	 
	private void appendOrderByString(StringBuffer selectStatement) {				
		String orderByAscend = (dto.getIsAsc()? " asc":" desc");
		
		if ((dto.getOrderField() == null) ||
				dto.getOrderField().trim().equals(""))
			selectStatement.append(" order by cp.id desc");
		else {
			selectStatement.append(" order by cp." + dto.getOrderField() + orderByAscend);
		}
		
		orderByAscend = null;
		
	}
	private void appendPrivilege(StringBuffer selectStatement,String querytype)throws ListHandlerException {
		String orginfo = BusinessUtility.getOrgInfoByOrgID(getOperator().getOrgID());
		String orgtype = orginfo.substring(0,1);
		int orgID = getOperator().getOrgID();
		int parentorgid = BusinessUtility.getParentHasCustomerOrgID(orgID);
		//if(CommonConstDefinition.ORGANIZATIONORGTYPE_DEPARTMENT.equals(orgtype)||CommonConstDefinition.ORGANIZATIONORGTYPE_PARTNER.equals(orgtype))
			orgID = parentorgid;
			
			//分公司只能看所有该分公司下的客户的，以及该分公司做过操作的报修单			  	
			String tvalue = BusinessUtility.getSystemsettingValueByName("SET_W_PRECISEWORK");
			if(tvalue != null && ("Y").equals(tvalue) && (querytype != null&&("queryPart").equals(querytype))){
					selectStatement.append(" and cp.processorgid="+getOperator().getOrgID());					
			}else if(querytype != null && ("queryAll").equals(querytype)&&(tvalue != null && ("Y").equals(tvalue))){
					selectStatement.append(" and ( cp.processorgid="+getOperator().getOrgID());
					selectStatement.append(" or cp.addressid in (select addressid from t_address addr where 1=1");		
					selectStatement.append(" and addr.districtid in (select id from t_districtsetting connect by prior id=belongto start with id in(select districtid from t_orggoverneddistrict where orgid=" + orgID);
					selectStatement.append("))))");				
			}else {
				selectStatement.append(" and cp.addressid in (select addressid from t_address addr where 1=1");
				selectStatement.append(" and addr.districtid in (select id from t_districtsetting connect by prior id=belongto start with id in(select districtid from t_orggoverneddistrict where orgid=" + orgID);
				selectStatement.append(")))");
			}			
		  /*case OPERATOR_BELONG_TO_STREET_STATION:
			dto.setStreet(getOperator().getOrgID());
			break;*/  		 
	  	 
	}
}
