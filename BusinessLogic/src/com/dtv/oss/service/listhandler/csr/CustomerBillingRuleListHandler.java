/*
 * Created on 2007-6-25
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.CustomerBillingRuleDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CustomerBillingRuleDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author 260904l
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CustomerBillingRuleListHandler extends ValueListHandler{
	private CustomerBillingRuleDAO dao = null;
	private final String tableName = "t_customerbillingrule a";
	private CustomerBillingRuleDTO dto = null;
	
	public CustomerBillingRuleListHandler(){
		dao = new CustomerBillingRuleDAO();
	}
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(AccountListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CustomerBillingRuleDTO)
			this.dto = (CustomerBillingRuleDTO) dto1;
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
	/**
	 * @param dto2
	 */
	private void constructSelectQueryString(CustomerBillingRuleDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select a.*" + " from " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 ");
		makeSQLByIntField("a.psid",dto.getPsID(),selectStatement);
		appendOrderByString(selectStatement);
		//appendOrderByString(end);
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	/**
	 * @param selectStatement
	 */
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by a.id desc");
		
	}

}
