package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.CustAcctCycleCfgDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.FinancialCustomerAccountCycleDetailDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class FinancialCustomerAccountCycleDetailHandler extends
		ValueListHandler {
	private FinancialCustomerAccountCycleDetailDAO dao = null;

	public FinancialCustomerAccountCycleDetailHandler() {
		this.dao = new FinancialCustomerAccountCycleDetailDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto == null || !(dto instanceof CustAcctCycleCfgDTO)) {
			LogUtility.log(getClass(), LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		constructSelectQueryString((CustAcctCycleCfgDTO) dto);
		// 执行数据查询
		executeSearch(dao, false, true);
	}

	private void constructSelectQueryString(CustAcctCycleCfgDTO dto) {
		String tableName = "T_CustAcctCycleCfg";

		StringBuffer select = new StringBuffer(" select ");
		StringBuffer where = new StringBuffer(" where 1=1 ");
		// T_CustAcctCycleCfg
		// SEQNO 1 1 N NUMBER (10) NULL
		// BILLINGCYCLETYPEID 2 N NUMBER (10) NULL
		// CUSTOMERTYPE 3 Y VARCHAR2 (5) NULL
		// ACCOUNTTYPE 4 Y VARCHAR2 (5) NULL
		// DT_CREATE 5 Y TIMESTAMP(6) NULL
		// DT_LASTMOD 6 Y TIMESTAMP(6) NULL
		select
				.append("SEQNO,BILLINGCYCLETYPEID,CUSTOMERTYPE,ACCOUNTTYPE,DT_CREATE,DT_LASTMOD ");
		if (dto != null) {
			makeSQLByIntField("SEQNO", dto.getSeqNo(), where);
			makeSQLByIntField("BILLINGCYCLETYPEID",
					dto.getBillingCycleTypeId(), where);
			makeSQLByStringField("CUSTOMERTYPE", dto.getCustomerType(), where);
			makeSQLByStringField("ACCOUNTTYPE", dto.getAccountType(), where);
		}
		// 设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(where);
		// 设置当前数据查询sql
		select.append(" from ").append(tableName).append(where).append(
				" order by BILLINGCYCLETYPEID desc ");
		setRecordDataQueryBuffer(select);
	}
}
