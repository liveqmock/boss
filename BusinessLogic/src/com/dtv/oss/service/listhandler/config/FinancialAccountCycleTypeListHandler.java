package com.dtv.oss.service.listhandler.config;

import java.text.SimpleDateFormat;

import com.dtv.oss.dto.BillingCycleTypeDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.FinancialBillingCycleTypeBriefDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class FinancialAccountCycleTypeListHandler extends ValueListHandler {
	private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";

	private FinancialBillingCycleTypeBriefDAO dao = null;

	private SimpleDateFormat dateFormat = null;

	public FinancialAccountCycleTypeListHandler() {
		this.dao = new FinancialBillingCycleTypeBriefDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto == null || !(dto instanceof BillingCycleTypeDTO)) {

			LogUtility.log(getClass(), LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		constructSelectQueryString((BillingCycleTypeDTO) dto);
		// 执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(BillingCycleTypeDTO dto) {
		String tableName = "T_BILLINGCYCLETYPE";

		StringBuffer select = new StringBuffer("select ");
		// all fields
		select.append("ID,NAME,CTYPE,DESCRIPTION,UNIFIEDCYCLEFLAG,");
		select.append("RENTCYCLEBDATE,OTHERCYCLEBDATE,ALLOWPREBILLINGFLAG,");
		select.append("STARTBILLINGFLAG,INVOICEDUEDATE,BILLINGCYCLETYPEID,");
		select.append("FIRSTVALIDINVOICECYCLEID,CYCLECOUNT,UNIFIEDDISCTFLAG,");
		select.append("RENTDISCTMODE,RENTDIVIDINGDATE,");
		select.append("DT_CREATE,DT_LASTMOD,ENDINVOICINGDATE,STATUS ");

		// 序号ID 1 1 N NUMBER (10) NULL
		// CTYPE 3 N VARCHAR2 (5) NULL
		// 名称 NAME 2 Y VARCHAR2 (50) NULL
		// 描述 DESCRIPTION 4 Y VARCHAR2 (200) NULL
		// 状态 STATUS 20 Y VARCHAR2 (5) Null
		// 对应计费周期类型 BILLINGCYCLETYPEID 11 Y NUMBER (10) NULL
		// 第一个有效周期 FIRSTVALIDINVOICECYCLEID 12 Y NUMBER (10) NULL
		// 时间长度 CYCLECOUNT 13 Y NUMBER (10) NULL
		//
		// 租费开始时间段 RENTCYCLEBDATE 6 N DATE NULL
		// 使用费用开始时间 OTHERCYCLEBDATE 7 Y DATE NULL
		// 帐单生成截止日 ENDINVOICINGDATE 19 Y DATE
		// 帐单过期日期 INVOICEDUEDATE 10 Y DATE NULL
		StringBuffer where = new StringBuffer(" where 1=1 ");
		makeSQLByStringField("CTYPE", "I", where);
		if (dto != null) {
			makeSQLByIntField("ID", dto.getId(), where);

			makeSQLByStringField("NAME", dto.getName(), where);
			makeSQLByStringField("DESCRIPTION", dto.getDescription(), where);
			makeSQLByStringField("STATUS", dto.getStatus(), where);
			makeSQLByIntField("BILLINGCYCLETYPEID",
					dto.getBillingCycleTypeId(), where);
			makeSQLByIntField("FIRSTVALIDINVOICECYCLEID", dto
					.getFirstValidInvoiceCycleId(), where);
			makeSQLByIntField("CYCLECOUNT", dto.getCycleCount(), where);

			if (dto.getRentCyclebDate() != null) {
				String strDate = getDateFormat()
						.format(dto.getRentCyclebDate());
				where.append(" and to_char(RENTCYCLEBDATE,'").append(
						DATE_FORMAT_STRING).append("')=").append(strDate);
			}
			if (dto.getOtherCycleBDate() != null) {
				String strDate = getDateFormat().format(
						dto.getOtherCycleBDate());
				where.append(" and to_char(OTHERCYCLEBDATE,'").append(
						DATE_FORMAT_STRING).append("')=").append(strDate);
			}
			if (dto.getEndInvoicingDate() != null) {
				String strDate = getDateFormat().format(
						dto.getEndInvoicingDate());
				where.append(" and to_char(ENDINVOICINGDATE,'").append(
						DATE_FORMAT_STRING).append("')=").append(strDate);
			}
			if (dto.getInvoiceDueDate() != null) {
				String strDate = getDateFormat()
						.format(dto.getInvoiceDueDate());
				where.append(" and to_char(INVOICEDUEDATE,'").append(
						DATE_FORMAT_STRING).append("')=").append(strDate);
			}
		}
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(where);
		// 设置当前数据查询sql
		select.append(" from ").append(tableName).append(where).append(
				" order by id desc ");
		setRecordDataQueryBuffer(select);
	}

	private SimpleDateFormat getDateFormat() {
		dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
		return dateFormat;
	}

}
