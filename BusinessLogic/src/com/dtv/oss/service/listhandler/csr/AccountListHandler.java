/*
 * Created on 2004-8-9
 *
 */
package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.AccountDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.work.JobCardListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.CommonUtility;

public class AccountListHandler extends ValueListHandler {
    private AccountDAO dao = null;
    private String selectCriteria = "";
    final private String tableName = "T_Account a, t_address addr";

    /**
     * use DTO as a template to determine
     * search criteria
     */
	private AccountDTO dto = null;

	public AccountListHandler() {
	  	this.dao = new AccountDAO();
	}

      /**
       * Use setCriteria() method to check whether or not the QUERY is the same
       * @return
       */
	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * 
	 * @return
	 */
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(AccountListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof AccountDTO)
			this.dto = (AccountDTO) dto1;
		else {
			LogUtility.log(AccountListHandler.class, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		 
		//构造查询字符串
		constructSelectQueryString(dto);
		//执行数据查询
		executeSearch(dao, true, true);
	}
	 
    private void constructSelectQueryString(AccountDTO dto) {
		boolean none = false;
		StringBuffer begin = new StringBuffer();
		begin.append("select a.*, " + CommonUtility.getSelectExpression4Address("addr.") + " from " + tableName);
	    StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 ");
		/*
		 * Search Condition:
		 * 
		 */
		makeSQLByIntField("a.AccountID", dto.getAccountID(), selectStatement);
	    makeSQLByIntField("a.CustomerID", dto.getCustomerID(), selectStatement);
	    makeSQLByStringFieldIn("a.status", dto.getStatus(), selectStatement); 
//	  all deleted records cann't return to user 
//	    appendString("a.status not in ('" + CommonConstDefinition.ACCOUNT_STATUS_CLOSE + "')", selectStatement); 
	    appendString("a.addressid = addr.addressid", selectStatement);
	    appendOrderByString(selectStatement);
		//appendOrderByString(end);
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
		 
	}
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by a.accountid desc");
}

      
}
