package com.dtv.oss.service.listhandler.csr;


import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.GroupCustomerDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class GroupCustListhandler extends ValueListHandler {

	private GroupCustomerDAO dao = null;

	final private String tableName = " t_customer cust";

	public GroupCustListhandler() {
		this.dao = new GroupCustomerDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {

		LogUtility.log(GroupCustListhandler.class, LogLevel.DEBUG, "集团子客户查找....");

		CommonQueryConditionDTO dto1 = (CommonQueryConditionDTO) dto;
		// 构造查询字符串
		constructSelectQueryString(dto1);
		// 执行数据查询
		executeSearch(dao, false, false);

	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto1) {

		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where  cust.customerstyle= '"+CommonConstDefinition.CUSTOMERSTYLE_GROUP+"'  and  cust.GROUPCUSTID <>0  ");
		
		if (!(dto1.getSpareStr1()==null || "".equals(dto1.getSpareStr1())))
			makeSQLByIntField("cust.GROUPCUSTID",Integer.parseInt(dto1.getSpareStr1()),selectStatement);	
		
		if (!(dto1.getSpareStr3()==null || "".equals(dto1.getSpareStr3())))
			makeSQLByIntField("cust.CUSTOMERID",Integer.parseInt(dto1.getSpareStr3()),selectStatement);	
		
		if (!(dto1.getSpareStr4()==null || "".equals(dto1.getSpareStr4())))
			makeSQLByStringField("cust.NAME", dto1.getSpareStr4(),
					selectStatement, "like");
			
		if(dto1.getSpareStr1()==null&&dto1.getSpareStr2()!=null&&(!("".equals(dto1.getSpareStr2())))){
			selectStatement.append(" and cust.GROUPCUSTID in " +
					"(select customerid from t_customer tt where tt.name like '%"+dto1.getSpareStr2()+"%' and tt.customerstyle = '"+CommonConstDefinition.CUSTOMERSTYLE_GROUP+"' and tt.GROUPCUSTID = 0 ) ");
		}
		if (!(dto1.getSpareStr5()==null || "".equals(dto1.getSpareStr5()))){
			selectStatement.append(" and cust.customerid in " +
					"(select customerid from t_customercampaign tt where tt.contractno = '"+dto1.getSpareStr5()+"')" );
		}
		selectStatement.append(" order by cust.customerid desc");   
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
}
