package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.AcctItemTypeDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.FinancialAccountTypeBriefDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class FinancialAccountTypeListHandler extends ValueListHandler {
	private FinancialAccountTypeBriefDAO dao = null;

	public FinancialAccountTypeListHandler() {
		this.dao = new FinancialAccountTypeBriefDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto == null || !(dto instanceof AcctItemTypeDTO)) {

			LogUtility.log(getClass(), LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		constructSelectQueryString((AcctItemTypeDTO) dto);
		// 执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(AcctItemTypeDTO dto) {
		// T_AcctItemType
		// 帐目类型标识 ACCTITEMTYPEID 1 1 N VARCHAR2 (20) NULL
		// 帐目类型名称 ACCTITEMTYPENAME 2 Y VARCHAR2 (50) NULL
		// 费用类型 FEETYPE 3 Y VARCHAR2 (5) NULL
		// 归类帐目标志 SUMMARYAIFLAG 4 Y VARCHAR2 (5) NULL
		// 归类到 SUMMARYTO 5 Y VARCHAR2 (20) NULL
		// 特殊销帐标识 SPECIALSETOFFFLAG 6 Y VARCHAR2 (5) NULL
		// 显示级别 SHOWLEVEL 7 Y VARCHAR2 (5) NULL
		// 状态 STATUS 8 Y VARCHAR2 (5) NULL
		// DT_CREATE 9 Y TIMESTAMP(6) NULL
		// DT_LASTMOD 10 Y TIMESTAMP(6) NULL
		StringBuffer select = new StringBuffer("select  ");
		select
				.append("ACCTITEMTYPEID,ACCTITEMTYPENAME,FEETYPE,SUMMARYAIFLAG,SUMMARYTO,");
		select
				.append("SPECIALSETOFFFLAG,SHOWLEVEL,STATUS,SYSTEMFLAG,DT_CREATE,DT_LASTMOD");
		select.append(" from T_AcctItemType ");
		StringBuffer where = new StringBuffer(" where 1=1 ");
		if (dto != null) {
			if (dto.getAcctItemTypeID() != null
					&& dto.getAcctItemTypeID().trim().length() > 0) {
				where.append(" and ACCTITEMTYPEID like'%").append(
						dto.getAcctItemTypeID().trim()).append("%' ");
			}
			if (dto.getAcctItemTypeName() != null
					&& dto.getAcctItemTypeName().trim().length() > 0) {
				where.append(" and ACCTITEMTYPENAME like'%").append(
						dto.getAcctItemTypeName().trim()).append("%' ");
			}
			makeSQLByStringField("FEETYPE", dto.getFeeType(), where);
			makeSQLByStringField("SUMMARYAIFLAG", dto.getSummaryAiFlag(), where);
			makeSQLByStringField("SUMMARYTO", dto.getSummaryTo(), where);
			makeSQLByStringField("SPECIALSETOFFFLAG", dto
					.getSpecialSetOffFlag(), where);
			makeSQLByStringField("SHOWLEVEL", dto.getShowLevel(), where);
			makeSQLByStringField("STATUS", dto.getStatus(), where);
		}
		setRecordCountQueryTable("T_AcctItemType");
		setRecordCountSuffixBuffer(where);
		// 设置当前数据查询sql
		select.append(where);
		//select.append(" order by SummaryAiFlag, FeeType, AcctItemTypeID Asc ");
		select.append(" order by AcctItemTypeID desc ");
		setRecordDataQueryBuffer(select);
	}
}
