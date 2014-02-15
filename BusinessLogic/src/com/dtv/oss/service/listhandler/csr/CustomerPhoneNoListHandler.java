package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.dao.csr.CustomerPhoneNoDAO;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;

public class CustomerPhoneNoListHandler extends ValueListHandler {
    private CustomerPhoneNoDAO dao = null;
    final private String tableName = "t_customer c, t_serviceaccount s";

    /**
     * use DTO as a template to determine
     * search criteria
     */
	private CommonQueryConditionDTO dto = null;

	public CustomerPhoneNoListHandler() {
	  	this.dao = new CustomerPhoneNoDAO();
	}

      /**
       * Use setCriteria() method to check whether or not the QUERY is the same
       * @return
       */
      public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(CustomerPhoneNoListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
          if (dto1 instanceof CommonQueryConditionDTO) 
          	this.dto = (CommonQueryConditionDTO)dto1;
          else {
          	LogUtility.log(CustomerPhoneNoListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
           }
          // added by Horace 2004-11-3
        //  if (fillDTOWithPrivilege(dto) == false) return;
          //构造查询字符串
          constructSelectQueryString(dto);
          //执行数据查询
          executeSearch(dao,true,true);
      }

	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
        StringBuffer begin = new StringBuffer();
         
    	begin.append("select c.customerid,name,serviceaccountid,s.status,servicecode " + " from " + tableName);
        
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	/*
		 * Search Condition:
		 * servicecode
		 */
		makeSQLByStringField("servicecode", dto.getSpareStr1(), selectStatement);
		appendString("s.status <> '" + CommonConstDefinition.SA_STATUS_CANCEL + "'", selectStatement);
		appendString("c.customerid = s.customerid", selectStatement);
		
 		appendOrderByString(selectStatement);
		//appendOrderByString(end);
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	private void appendOrderByString(StringBuffer selectStatement) {		
	}
}