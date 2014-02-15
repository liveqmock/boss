/*
 * Created on 2005-9-28
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.work;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.CommonUtility;
import com.dtv.oss.service.dao.work.JobCardAndJobCardProcessDAO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;

public class JobCardListHandler extends ValueListHandler {
	private JobCardAndJobCardProcessDAO dao = null;
	private boolean withPrivilege = false;
	 

	final private String tableName = "T_JobCard jc,T_jobCardProcess jcp";

	/**
	 * use DTO as a template to determine search criteria
	 */
	private CommonQueryConditionDTO dto = null;

	public JobCardListHandler() {
		this.dao = new JobCardAndJobCardProcessDAO();
	}
	
	public JobCardListHandler(boolean withPrivilege){
		if(withPrivilege)
			this.withPrivilege = true;
		this.dao = new JobCardAndJobCardProcessDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * 
	 * @return
	 */
	public void setCriteria(Object dto1) throws ListHandlerException {
		
		
		LogUtility.log(JobCardListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(JobCardListHandler.class, LogLevel.DEBUG,
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
	 
	private void constructSelectQueryString(CommonQueryConditionDTO dto) throws ListHandlerException{
		 
		StringBuffer begin = new StringBuffer();
		begin.append("select jc.*," + CommonUtility.getSelectExpression4JobCardProcess("jcp.") + " from " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 ");
		selectStatement.append(" and jc.jobcardid = jcp.jcid");
		String tvalue = BusinessUtility.getSystemsettingValueByName("SET_W_PRECISEWORK");
		String orginfo = BusinessUtility.getOrgInfoByOrgID(getOperator().getOrgID());
		String orgtype = orginfo.substring(0,1);
		int orgID = getOperator().getOrgID();		
		/*else{
			if(withPrivilege)
				selectStatement.append(" and jcp.nextorgid = " + getOperator().getOrgID());
		}*/
		/*
		 * Search Condition:
		 * 
		 */
		if(dto.getNo()!=null&&dto.getNo().indexOf(";") > -1){			
		    makeSQLByIntFieldIn("jc.jobCardId", dto.getNo(), selectStatement);		    
		}else{
			makeSQLByStringField("jc.jobCardId", dto.getNo(), selectStatement);
		}
		 
		makeSQLByStringField("jc.jobtype", dto.getType(), selectStatement);  
		if(("I").equals(dto.getType())){
			if(dto.getSpareStr17()!=null&&dto.getSpareStr18()!=null){
				 selectStatement.append(" and jc.jobcardid in(select csi.referjobcardid from t_custserviceinteraction csi,t_csicustproductinfo csiprod,t_terminaldevice device where csi.id=csiprod.csiid and csiprod.referdeviceid=device.deviceid and device.devicemodel='")
				 .append(dto.getSpareStr17()).append("'").append(" and device.serialno='").append(dto.getSpareStr18()).append("')");
			 }else if(dto.getSpareStr17()!=null){
				 selectStatement.append(" and jc.jobcardid in(select csi.referjobcardid from t_custserviceinteraction csi,t_csicustproductinfo csiprod,t_terminaldevice device where csi.id=csiprod.csiid and csiprod.referdeviceid=device.deviceid and device.devicemodel='")
				 .append(dto.getSpareStr17()).append("')");
			 }else if(dto.getSpareStr18()!=null){
				 selectStatement.append(" and jc.jobcardid in(select csi.referjobcardid from t_custserviceinteraction csi,t_csicustproductinfo csiprod,t_terminaldevice device where csi.id=csiprod.csiid and csiprod.referdeviceid=device.deviceid and device.serialno='")
				 .append(dto.getSpareStr18()).append("')");
			 }
			if(dto.getCustomerID() != 0){
				selectStatement.append(" and (jc.custid=" + dto.getCustomerID() +" or jc.refersheetid in (select id from t_custserviceinteraction where customerid=" + dto.getCustomerID() );
				selectStatement.append(" and type<>'" + CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK + "'))");
				//selectStatement.append(" or")
			}
			//makeSQLByIntField("jc.custid", dto.getCustomerID(), selectStatement);
		}else if(("R").equals(dto.getType())){
			if(dto.getSpareStr17()!=null&&dto.getSpareStr18()!=null){
				 selectStatement.append(" and jc.jobcardid in(select referjobcardid from t_customerproblem where custserviceaccountid in(select serviceaccountid from t_customerproduct custprod, t_terminaldevice device where custprod.deviceid <>0  and custprod.deviceid=device.deviceid and device.devicemodel='")
				 .append(dto.getSpareStr17()).append("'").append(" and device.serialno='").append(dto.getSpareStr18()).append("'))");
			 }else if(dto.getSpareStr17()!=null){
				 selectStatement.append(" and jc.jobcardid in(select referjobcardid from t_customerproblem where custserviceaccountid in(select serviceaccountid from t_customerproduct custprod, t_terminaldevice device where custprod.deviceid <>0  and custprod.deviceid=device.deviceid and device.devicemodel='")
				 .append(dto.getSpareStr17()).append("'))");
			 }else if(dto.getSpareStr18()!=null){
				 selectStatement.append(" and jc.jobcardid in(select referjobcardid from t_customerproblem where custserviceaccountid in(select serviceaccountid from t_customerproduct custprod, t_terminaldevice device where custprod.deviceid <>0  and custprod.deviceid=device.deviceid and device.serialno='")
				 .append(dto.getSpareStr18()).append("'))");
			 }
			makeSQLByIntField("jc.custid", dto.getCustomerID(), selectStatement);
			if(dto.getSpareStr20()!=null)
				makeSQLByIntField("jc.refersheetid",Integer.parseInt(dto.getSpareStr20()),selectStatement);
		}else {
			makeSQLByIntField("jc.custid", dto.getCustomerID(),selectStatement);
		}
		  
		
		/**
		 * 默认条件,取得当前操作员所属组织,再取得该组织可管理区域,则该操作员只能查询该管理区域内的客户
		 */
		/**-------20070122 杨晨   start-------------*/
		// 所属组织
		
		/*if((tvalue!=null&&("Y").equals(tvalue))||(dto.getSpareStr19()!=null && ("queryPart").equals(dto.getSpareStr19()))){
					selectStatement.append(" and jc.processorgid = " + orgID);
		}
		if((!withPrivilege)&&((dto.getSpareStr19()!=null && ("queryAll").equals(dto.getSpareStr19())||(tvalue == null||("N").equals(tvalue))))){
			selectStatement.append(" and (jc.processorgid="+orgID);
			selectStatement.append(" or ");
		}else if(dto.getSpareStr19() != null ){
			selectStatement.append(" and");
		}
		if((dto.getSpareStr19() != null )
				|| ((!withPrivilege)&&((dto.getSpareStr19()!=null && ("queryAll").equals(dto.getSpareStr19()))||(tvalue == null||("N").equals(tvalue))))){
			int parentorgid = Integer.parseInt(orginfo.substring(1));		
			if(CommonConstDefinition.ORGANIZATIONORGTYPE_DEPARTMENT.equals(orgtype)||CommonConstDefinition.ORGANIZATIONORGTYPE_PARTNER.equals(orgtype)){
				orgID = parentorgid;			
			}
			selectStatement.append(" jc.addressid in(select addressid from t_address addr where 1=1");
			selectStatement.append(" and  exists (select id from t_districtsetting  where addr.districtid =id connect by prior id=belongto start with id in(select districtid from t_orggoverneddistrict where orgid=" + orgID);
			selectStatement.append(")))");			
		}
		if((!withPrivilege)&&((dto.getSpareStr19()!=null && ("queryAll").equals(dto.getSpareStr19()))||(tvalue == null||("N").equals(tvalue))))
			selectStatement.append(" )");*/
		//appendStringWithOrgGovernedDistrict("jc.addressid in(Select addressid From t_address where districtid", 0,selectStatement);
		//下面这个括号是必要的，用来和上面的那个括号对应
		//selectStatement.append(")");
		//---20060904 liyanchun add-------*/
		if(dto.getSpareStr9() != null)
			selectStatement.append(" and jc.troubletype='").append(dto.getSpareStr9()).append("'");
		if(dto.getSpareStr8() != null)
			selectStatement.append(" and jc.troublesubtype='").append(dto.getSpareStr8()).append("'");
		if(dto.getSpareStr11() != null)
			selectStatement.append(" and jc.resolutiontype='").append(dto.getSpareStr11()).append("'");
		if(dto.getSpareStr2() != null)
			selectStatement.append(" and jc.workresultreason='").append(dto.getSpareStr2()).append("'");
		/**--------20070122 杨晨   end------------*/
		//创建时间，根据t_jobcard.jodcardId=t_jobcardProcess.jcid 关联
		if(dto.getBeginTime()!=	null)
			selectStatement.append(" and jc.dt_create>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		if(dto.getEndTime()!= null)
			selectStatement.append(" and jc.dt_create<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		 
		//预约上门时间
		if (dto.getSpareBeginTime()!=null) 
		        selectStatement.append("and jc.preferedDate>=to_timestamp('").append(dto.getSpareBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')"); 
		
		if (dto.getSpareEndTime()!=null) 
			   selectStatement.append("and jc.preferedDate<=to_timestamp('").append(dto.getSpareEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')"); 
//		在录入安装信息的时候过滤掉custid不等于0的情况
		if(dto.getSpareStr3()!=null)	{
			 selectStatement.append(" and jc.custid <>0 ");
		}
		//完成时间
		if(dto.getSpareTime1()!=null)
			selectStatement.append(" and jc.dt_lastmod>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		if(dto.getSpareTime2()!= null)
			selectStatement.append(" and jc.dt_lastmod<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getSpareStr13()!= null) {
			 
           selectStatement.append(" and jc.refersheetid in (select nci.csiid from t_newcustomerinfo nci where nci.opensourcetype='").append(dto.getSpareStr13()).append("')");
         }
		 if(dto.getSpareStr5()!= null) {
		 	makeSQLByIntField("jc.refersheetid", Integer.valueOf(dto.getSpareStr5()).intValue(), selectStatement);   
		 }
		 if(dto.getSpareStr21()!=null) {
		 	makeSQLByStringField("jc.subtype",dto.getSpareStr21(),selectStatement);
		 }
//		status
		makeSQLByStringFieldIn("jc.status", dto.getStatus(), selectStatement);
  
 
/**
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr3()) ) {
			selectStatement.append(" and jc.jobCardId in ( select jc.jobcardid from t_jobcard jc where jc.custid in" +
					" (select customerid from t_customer c where c.addressid  in " +
					"(select addressid from t_address a where a.districtid= '")
			        .append(dto.getSpareStr3()).append("')))");
		}	
		*/
		
//		 所在区域
		if (dto.getDistrict() != 0) {
		    selectStatement.append(" and jc.addressID in (select addressid from t_address where districtId in " +
		    		"( select ds.id from t_districtsetting ds connect by prior ds.id=belongto start with id =").append(dto.getDistrict()).append("))");
		}
	 //用户类型
		if("I".equals(dto.getType())&&dto.getSpareStr15()!=null){
			selectStatement.append(" and jc.custid in (select customerid from t_customer where customertype ='").append(dto.getSpareStr15()).append("'");
			selectStatement.append(" union select custid from t_newcustomerinfo where type='").append(dto.getSpareStr15()).append("')");			
		}else if(dto.getSpareStr15()!=null){
				selectStatement.append(" and jc.custid in (select customerid from t_customer where customertype ='").append(dto.getSpareStr15()).append("')");
		}
		//--add by david.Yang
		//联系人
		if (dto.getSpareStr23() !=null)
			selectStatement.append(" and jc.contactperson ='").append(dto.getSpareStr23()).append("'")  ;
		//联系电话
		if (dto.getSpareStr24() !=null)
			selectStatement.append(" and jc.contactphone like '%").append(dto.getSpareStr24()).append("%'")  ;
				
		selectStatement.append(" and jcp.seqno in (select max(seqno) from t_jobcardprocess group by jcid)");
		//String conditionForOnlyOneRecord = "jcp.seqno in (select max(jcp.seqno) from T_JobCard jc,T_JobCardProcess jcp "+selectStatement.toString()+" group by jc.jobcardid)";
		//安装人员或者维修人员姓名
		makeSQLByStringField("jcp.ProcessMan", dto.getSpareStr22(), selectStatement);
		
		//appendString(conditionForOnlyOneRecord, selectStatement);
		appendPrivilege(selectStatement,dto.getSpareStr19());
		appendOrderByString(selectStatement);
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

	/*private void appendPrivilege(StringBuffer selectStatement)throws ListHandlerException {
	  	//只能查询到可以操作的报修，即流转到本部门的	  	
	  	selectStatement.append(" and nextorgid="+getOperator().getOrgID());
	  	selectStatement.append(")");			
}*/
	 
	private void appendOrderByString(StringBuffer selectStatement) {
		//String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");

		if ((dto.getOrderField() == null)
				|| dto.getOrderField().trim().equals(""))
			selectStatement.append(" order by jc.JobCardID desc");
		else {
			selectStatement.append(" order by (select (select (select dis1.name from t_districtsetting dis1 where dis1.id =dis.belongto) || '-->' || dis.name from t_districtsetting dis where dis.id = t.districtid) || '-->' || t.detailaddress from t_address t where t.addressid =jc.addressid) desc " );
				//	+ orderByAscend);
		}

		//orderByAscend = null;

	}
	private void appendPrivilege(StringBuffer selectStatement,String querytype)throws ListHandlerException {
		String orginfo = BusinessUtility.getOrgInfoByOrgID(getOperator().getOrgID());
		String orgtype = orginfo.substring(0,1);
		int orgID = getOperator().getOrgID();
		int parentorgid = BusinessUtility.getParentHasCustomerOrgID(orgID);
		//if(CommonConstDefinition.ORGANIZATIONORGTYPE_DEPARTMENT.equals(orgtype)||CommonConstDefinition.ORGANIZATIONORGTYPE_PARTNER.equals(orgtype))
			orgID = parentorgid;
		String tvalue = BusinessUtility.getSystemsettingValueByName("SET_W_PRECISEWORK");
		
		if((tvalue!=null && ("Y").equals(tvalue)) && (withPrivilege||(!withPrivilege && (querytype!=null && ("queryPart".equals(querytype))))) ){  	
		//只能查询到可以操作的报修，即流转到本部门的
			selectStatement.append(" and  jc.processorgid=" + getOperator().getOrgID());
			//selectStatement.append(" and id in (select max(id) from t_custproblemprocess group by custproblemid))");
		}else if(tvalue!=null&&("Y").equals(tvalue)&&(querytype!=null&&("queryAll").equals(querytype)) && !withPrivilege) {
			selectStatement.append(" and (jc.processorgid=" + getOperator().getOrgID());
			selectStatement.append(" or jc.addressid in(select addressid from t_address addr where 1=1");
			selectStatement.append(" and addr.districtid in (select id from t_districtsetting connect by prior id=belongto start with id in(select districtid from t_orggoverneddistrict where orgid=" + orgID);
			selectStatement.append("))))");
		}else if(tvalue == null || ("N").equals(tvalue)){
			selectStatement.append(" and jc.addressid in(select addressid from t_address addr where 1=1");
			selectStatement.append(" and addr.districtid in (select id from t_districtsetting connect by prior id=belongto start with id in(select districtid from t_orggoverneddistrict where orgid=" + orgID);
			selectStatement.append(")))");
		}
	}
}