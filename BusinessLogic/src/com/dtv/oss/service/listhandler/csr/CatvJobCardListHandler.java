/*
 * Created on 2005-9-28
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.dao.work.JobCardDAO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import java.text.SimpleDateFormat;

public class CatvJobCardListHandler extends ValueListHandler {
	private JobCardDAO dao = null;
	private CommonQueryConditionDTO dto = null;
	final private String tableName = " t_jobcard jc left join t_customer cust on jc.custid = cust.customerid left join t_address on t_address.addressid = cust.addressid ";

	public CatvJobCardListHandler() {
		this.dao = new JobCardDAO();
	}
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(CatvJobCardListHandler.class, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(CatvJobCardListHandler.class, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		//构造查询字符串
		constructSelectQueryString(dto);
		//执行数据查询
		executeSearch(dao, true, true);
	}

	/**
	 * 
	 */
	 
	private void constructSelectQueryString(CommonQueryConditionDTO dto) throws ListHandlerException{
		LogUtility.log(CatvJobCardListHandler.class,LogLevel.DEBUG,"模拟工单查询..constructSelectQueryString."); 
        StringBuffer begin = new StringBuffer();
       	begin.append("select jc.* "  + " from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		String tvalue = BusinessUtility.getSystemsettingValueByName("SET_W_PRECISEWORK");
		String orginfo = BusinessUtility.getOrgInfoByOrgID(getOperator().getOrgID());
		String orgtype = orginfo.substring(0,1);
		int orgID = getOperator().getOrgID();		
		if(("Y").equals(tvalue)){
					if((dto.getSpareStr19()!=null && ("queryPart").equals(dto.getSpareStr19())))
						selectStatement.append(" and jc.processorgid = " + orgID);
		}
		 /*
		 * Search Condition:
		 * ID,CatvID,County
		 * DetailedAddress, status, statusreason
		 */
		if(dto.getNo()!=null)
		makeSQLByIntField("jc.JOBCARDID", Integer.parseInt(dto.getNo()), selectStatement);
		if(dto.getCustomerID() != 0)
		makeSQLByIntField("jc.CUSTID", dto.getCustomerID(), selectStatement);
		makeSQLByStringField("jc.SUBTYPE", dto.getSpareStr1(), selectStatement);
		makeSQLByStringField("jc.STATUS", dto.getStatus(), selectStatement);
		makeSQLByStringField("cust.customertype", dto.getSpareStr2(), selectStatement);
		//makeSQLByStringField("t_address.districtid", dto.getSpareStr3(), selectStatement);
		
		if(dto.getSpareStr3()!=null){
			appendString(" jc.addressID in (select addressid from t_address where districtId in (select ID from t_districtsetting WHERE STATUS='V' connect by prior ID=BELONGTO start with ID="+dto.getSpareStr3()+")) ",selectStatement);
		}
		if(dto.getBeginTime()!=null){
			appendString( " jc.DT_CREATE >= to_timestamp('"+dto.getBeginTime()+"','YYYY-MM-DD-HH24:MI:SSxff') ", selectStatement);
		}	
		if(dto.getEndTime()!=null){
			appendString( " jc.DT_CREATE <= to_timestamp('"+dto.getEndTime()+"','YYYY-MM-DD-HH24:MI:SSxff') ", selectStatement);
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		if(dto.getSpareBeginTime()!=null){
			appendString( " jc.PREFEREDDATE >= to_date('"+format.format(dto.getSpareBeginTime())+"','YYYY-MM-DD') ", selectStatement);
		}
		if(dto.getSpareEndTime()!=null){
			appendString( " jc.PREFEREDDATE <= to_date('"+format.format(dto.getSpareEndTime())+"','YYYY-MM-DD') ", selectStatement);
		}
		
		if(dto.getSpareTime1()!=null){
			appendString( " jc.workdate >= to_date('"+format.format(dto.getSpareTime1())+"','YYYY-MM-DD') ", selectStatement);
		}
		if(dto.getSpareTime2()!=null){
			appendString( " jc.workdate <= to_date('"+format.format(dto.getSpareTime2())+"','YYYY-MM-DD') ", selectStatement);
		}
		/*if(((dto.getSpareStr19()!=null && ("queryAll").equals(dto.getSpareStr19()))||(tvalue == null||("N").equals(tvalue)))){
					selectStatement.append(" and (jc.processorgid="+orgID);
					selectStatement.append(" or ");
		}else if(dto.getSpareStr19() != null ){
					selectStatement.append(" and");
		}
		if((dto.getSpareStr19() != null )
						|| (((dto.getSpareStr19()!=null && ("queryAll").equals(dto.getSpareStr19()))||(tvalue == null||("N").equals(tvalue))))){
			int parentorgid = Integer.parseInt(orginfo.substring(1));		
			if(CommonConstDefinition.ORGANIZATIONORGTYPE_DEPARTMENT.equals(orgtype)||CommonConstDefinition.ORGANIZATIONORGTYPE_PARTNER.equals(orgtype)){
				orgID = parentorgid;			
			}
					selectStatement.append(" jc.addressid in(select addressid from t_address addr where 1=1");
					selectStatement.append(" and  exists (select id from t_districtsetting  where addr.districtid =id connect by prior id=belongto start with id in(select districtid from t_orggoverneddistrict where orgid=" + orgID);
					selectStatement.append(")))");			
		}
		if(((dto.getSpareStr19()!=null && ("queryAll").equals(dto.getSpareStr19()))||(tvalue == null||("N").equals(tvalue))))
					selectStatement.append(" )");*/
		makeSQLByStringField("jc.WorkResultReason", dto.getSpareStr4(), selectStatement);
		makeSQLByStringField("jc.JobType", dto.getSpareStr5(), selectStatement); //工单类型：模拟
		makeSQLByStringField("jc.JobType", "C", selectStatement); //工单类型：模拟
		if(dto.getSpareStr22()!=null){
			appendString( " exists (select * from T_JobCardProcess  where jc.JobCardID = JCID  and ProcessMan='"+dto.getSpareStr22()+"')", selectStatement);
		}
		
        //--add by david.Yang
        //联系人
		if (dto.getSpareStr23() !=null)
			selectStatement.append(" and jc.contactperson ='").append(dto.getSpareStr23()).append("'")  ;
		//联系电话
		if (dto.getSpareStr24() !=null)
			selectStatement.append(" and jc.contactphone like '%").append(dto.getSpareStr24()).append("%'")  ;
				
		appendPrivilege(selectStatement,dto.getSpareStr19());
		appendOrderByString(selectStatement);
        // 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement.toString()));
	}

	 
	private void appendOrderByString(StringBuffer selectStatement) {
		//selectStatement.append(" order by jc.JOBCARDID desc");
		if ((dto.getOrderField() == null)
				|| dto.getOrderField().trim().equals(""))
			selectStatement.append(" order by jc.JobCardID desc");
		else {
			selectStatement.append(" order by ((select (select dis1.name from t_districtsetting dis1 where dis1.id =dis.belongto) || '-->' || dis.name from t_districtsetting dis where dis.id = t_address.districtid) || '-->' || t_address.detailaddress) desc " );
		}
	}
	private void appendPrivilege(StringBuffer selectStatement,String querytype)throws ListHandlerException {
			String orginfo = BusinessUtility.getOrgInfoByOrgID(getOperator().getOrgID());
			String orgtype = orginfo.substring(0,1);
			int orgID = getOperator().getOrgID();
			int parentorgid = BusinessUtility.getParentHasCustomerOrgID(orgID);
			//if(CommonConstDefinition.ORGANIZATIONORGTYPE_DEPARTMENT.equals(orgtype)||CommonConstDefinition.ORGANIZATIONORGTYPE_PARTNER.equals(orgtype))
				orgID = parentorgid;
			String tvalue = BusinessUtility.getSystemsettingValueByName("SET_W_PRECISEWORK");
		
			if((tvalue!=null && ("Y").equals(tvalue)) && (querytype!=null && ("queryPart".equals(querytype))) ){  	
			//只能查询到可以操作的报修，即流转到本部门的
				selectStatement.append(" and  jc.processorgid=" + getOperator().getOrgID());
				//selectStatement.append(" and id in (select max(id) from t_custproblemprocess group by custproblemid))");
			}else if(tvalue!=null&&("Y").equals(tvalue)&&(querytype!=null&&("queryAll").equals(querytype))) {
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