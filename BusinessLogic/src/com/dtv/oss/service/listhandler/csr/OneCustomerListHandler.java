package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.dao.csr.OneCustomerDAO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.util.*;

public class OneCustomerListHandler extends ValueListHandler {
    private OneCustomerDAO dao = null;
    final private String tableName = "t_customer cust, t_address addr, t_account acct ";

    /**
     * use DTO as a template to determine
     * search criteria
     */
	private CommonQueryConditionDTO dto = null;

	public OneCustomerListHandler() {
	  	this.dao = new OneCustomerDAO();
	}

      /**
       * Use setCriteria() method to check whether or not the QUERY is the same
       * @return
       */
      public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(CustomerListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
          if (dto1 instanceof CommonQueryConditionDTO) 
          	this.dto = (CommonQueryConditionDTO)dto1;
          else {
          	LogUtility.log(CustomerListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
           }
          //构造查询字符串
          constructSelectQueryString(dto);
          //执行数据查询
          executeSearch(dao,true,true);
      }

	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
        StringBuffer begin = new StringBuffer();
    	begin.append("select cust.*, " + CommonUtility.getSelectExpression4Address("addr.") + ", acct.accountid, acct.accountname" + " from " + tableName);
        
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	/*
		 * Search Condition:
		 * CustomerID,Name,CustomerStyle, CustomerType
		 * district, orgid, streetstationid, detailaddress,userid[t_serviceaccount],
		 * status
		 */
     	makeSQLByIntField("cust.CustomerID", dto.getCustomerID() , selectStatement);
		appendString("cust.addressid = addr.addressid", selectStatement);
		appendString("cust.customerid = acct.customerid", selectStatement);
		makeSQLByStringFieldIn("cust.status",CommonConstDefinition.CUSTOMER_STATUS_NORMAL + ";" + CommonConstDefinition.CUSTOMER_STATUS_POTENTIAL , selectStatement);
		makeSQLByStringField("acct.status",CommonConstDefinition.ACCOUNT_STATUS_NORMAL , selectStatement);
		
		selectStatement.append(" order by acct.AccountID asc ");

		//appendOrderByString(end);
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
}