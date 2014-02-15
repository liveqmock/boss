package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.dto.CustomerComplainDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CampaignDAO;
import com.dtv.oss.service.dao.csr.CustomerComplainDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.TimestampUtility;

public class CustComplainListhandler extends ValueListHandler {

	private CustomerComplainDAO dao = null;

	final private String tableName = " T_CUSTOMERCOMPLAIN ";

	public CustComplainListhandler() {
		this.dao = new CustomerComplainDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {

		LogUtility.log(CustComplainListhandler.class, LogLevel.DEBUG, "投诉查找....");

		CommonQueryConditionDTO dto1 = (CommonQueryConditionDTO) dto;
		// 构造查询字符串
		constructSelectQueryString(dto1);
		// 执行数据查询
		executeSearch(dao, false, false);

	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto1) throws ListHandlerException {

		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
			
		if (!(dto1.getSpareStr2()==null || "".equals(dto1.getSpareStr2())))
			makeSQLByIntField("CUSTOMERID",Integer.parseInt(dto1.getSpareStr2()),selectStatement);	
		
		if (!(dto1.getSpareStr3()==null || "".equals(dto1.getSpareStr3())))
			selectStatement.append(" and contactperson like '%"+dto1.getSpareStr3()+"%'");
			//makeSQLByStringField("CONTACTPERSON",dto1.getSpareStr3(),selectStatement);	
		
		if (!(dto1.getSpareStr4()==null || "".equals(dto1.getSpareStr4())))
			selectStatement.append(" and contactphone like '%"+dto1.getSpareStr4()+"%'");
			//makeSQLByStringField("CONTACTPHONE",dto1.getSpareStr4(),selectStatement);	
		
		if (!(dto1.getSpareStr5()==null || "".equals(dto1.getSpareStr5())))
			makeSQLByIntField("COMPLAINEDORGID",Integer.parseInt(dto1.getSpareStr5()),selectStatement);	
		
		if (!(dto1.getSpareStr6()==null || "".equals(dto1.getSpareStr6())))
			makeSQLByStringField("COMPLAINEDPERSON",dto1.getSpareStr6(),selectStatement);	
		
		if (!(dto1.getSpareStr9()==null || "".equals(dto1.getSpareStr9())))
			makeSQLByStringField("TYPE",dto1.getSpareStr9(),selectStatement);	
		
		if (!(dto1.getSpareStr10()==null || "".equals(dto1.getSpareStr10())))
			makeSQLByStringField("STATUS",dto1.getSpareStr10(),selectStatement);	
		
		if (!(dto1.getSpareStr11()==null || "".equals(dto1.getSpareStr11())))
			makeSQLByIntField("CUSTCOMPLAINID",Integer.parseInt(dto1.getSpareStr11()),selectStatement);	
		
		if (!(dto1.getSpareStr12()==null || "".equals(dto1.getSpareStr12()))){
			if(dto1.getSpareStr12().equals("transfer")||dto1.getSpareStr12().equals("manualend")||dto1.getSpareStr12().equals("process"))
				makeSQLByStringField("STATUS",CommonConstDefinition.CUSTOMER_COMPLAIN_WAIT,selectStatement);
			
			if(dto1.getSpareStr12().equals("callback"))
				selectStatement.append(" and  STATUS <> '"+CommonConstDefinition.CUSTOMER_COMPLAIN_WAIT+"' and callbackflag <> '"+CommonConstDefinition.CCPCALLBACKFLAG_YES+"' ");
			
			/*if(dto1.getSpareStr12().equals("transfer")||dto1.getSpareStr12().equals("manualend")||dto1.getSpareStr12().equals("process")){				
				makeSQLByIntField("currentWorkOrgID",getOperator().getOrgID(),selectStatement);
			}	*/		
		}		
	
		if(!(dto1.getSpareStr7()==null || "".equals(dto1.getSpareStr7())))
			selectStatement.append(" and DT_CREATE>=to_timestamp('").append(dto1.getSpareStr7().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		if(!(dto1.getSpareStr8()==null || "".equals(dto1.getSpareStr8())))
			selectStatement.append(" and DT_CREATE<=to_timestamp('").append(dto1.getSpareStr8().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		if(dto1.getSpareStr2()==null&&dto1.getSpareStr1()!=null&&(!("".equals(dto1.getSpareStr1())))){
			//selectStatement.append(" and CUSTOMERID in (select customerid from t_customer tt where tt.name like '%"+dto1.getSpareStr1()+"%' )");
			selectStatement.append(" and CUSTOMERID in (select customerid from t_customer tt where tt.name = '"+dto1.getSpareStr1()+"' )");
		}
		
		 if("process".equals(dto1.getSpareStr12())||"transfer".equals(dto1.getSpareStr12())||"manualend".equals(dto1.getSpareStr12()))
			appendPrivilege(selectStatement,dto1.getSpareStr13(),true);
		 else
		 	appendPrivilege(selectStatement,dto1.getSpareStr13(),false);
		selectStatement.append(" order by dt_create desc ");
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendPrivilege(StringBuffer selectStatement,String querytype,boolean withPrivilege)throws ListHandlerException {
		String orginfo = BusinessUtility.getOrgInfoByOrgID(getOperator().getOrgID());
		String orgtype = orginfo.substring(0,1);
		int orgID = getOperator().getOrgID();
		int parentorgid = BusinessUtility.getParentHasCustomerOrgID(orgID);
		//if(CommonConstDefinition.ORGANIZATIONORGTYPE_DEPARTMENT.equals(orgtype)||CommonConstDefinition.ORGANIZATIONORGTYPE_PARTNER.equals(orgtype))
			orgID = parentorgid;
		String tvalue = BusinessUtility.getSystemsettingValueByName("SET_W_PRECISEWORK");
		if((querytype!=null && "queryPart".equals(querytype)||withPrivilege) && tvalue!=null && ("Y").equals(tvalue)){  	
		//只能查询到可以操作的报修，即流转到本部门的
				selectStatement.append(" and  currentworkorgid=" + getOperator().getOrgID());
		  	//selectStatement.append(" and id in (select max(id) from t_custproblemprocess group by custproblemid))");
		}else if(tvalue!=null && ("Y").equals(tvalue) && querytype!=null && "queryAll".equals(querytype)&&(!withPrivilege)) {
			selectStatement.append(" and (currentworkorgid=" + getOperator().getOrgID());
			selectStatement.append(" or customerid in (select customerid from t_customer where addressid in(select addressid from t_address addr where 1=1");
			selectStatement.append(" and addr.districtid in (select id from t_districtsetting connect by prior id=belongto start with id in(select districtid from t_orggoverneddistrict where orgid=" + orgID);
			selectStatement.append(")))))");
		}else if(tvalue == null || ("N").equals(tvalue)){
			selectStatement.append(" and customerid in (select customerid from t_customer where addressid in(select addressid from t_address addr where 1=1");
			selectStatement.append(" and addr.districtid in (select id from t_districtsetting connect by prior id=belongto start with id in(select districtid from t_orggoverneddistrict where orgid=" + orgID);
			selectStatement.append("))))");
		}
	}
}
