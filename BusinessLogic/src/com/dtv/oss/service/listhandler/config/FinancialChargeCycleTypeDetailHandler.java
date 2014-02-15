package com.dtv.oss.service.listhandler.config;

import java.text.SimpleDateFormat;

import com.dtv.oss.dto.BillingCycleTypeDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.FinancialBillingCycleTypeDetailDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class FinancialChargeCycleTypeDetailHandler extends ValueListHandler {
	private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";

	private FinancialBillingCycleTypeDetailDAO dao = null;

	private SimpleDateFormat dateFormat = null;

	public FinancialChargeCycleTypeDetailHandler() {
		this.dao = new FinancialBillingCycleTypeDetailDAO();
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
		executeSearch(dao, false, false);
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

		// ID 1 1 N NUMBER (10) NULL
		// 名称 NAME 2 Y VARCHAR2 (50) NULL
		// 状态 STATUS 20 Y VARCHAR2 (5) Null
		// 描述 DESCRIPTION 4 Y VARCHAR2 (200) NULL
		// 是否允许预计费 ALLOWPREBILLINGFLAG 8 N VARCHAR2 (5) NULL
		// 开始计费标志 STARTBILLINGFLAG 9 N VARCHAR2 (5) NULL
		// 统一折算标志 UNIFIEDCYCLEFLAG 5 Y VARCHAR2 (5) NULL
		// 第一个计费的周期 FIRSTVALIDINVOICECYCLEID 12 Y NUMBER (10) NULL
		// 折算模式 RENTDISCTMODE 15 Y VARCHAR2 (5) NULL
		// 折算日 RENTDIVIDINGDATE 16 Y DATE NULL
		// 统一周期标志 UNIFIEDDISCTFLAG 14 N VARCHAR2 (5) NULL
		// 其他费计费起始日 OTHERCYCLEBDATE 7 Y DATE NULL
		// 租费计费起始日 RENTCYCLEBDATE 6 N DATE NULL
		StringBuffer where = new StringBuffer(" where 1=1 ");
		makeSQLByStringField("CTYPE", "B", where);
		if (dto != null) {
			makeSQLByIntField("ID", dto.getId(), where);

			makeSQLByStringField("NAME", dto.getName(), where);
			makeSQLByStringField("DESCRIPTION", dto.getDescription(), where);
			makeSQLByStringField("STATUS", dto.getStatus(), where);
			makeSQLByStringField("ALLOWPREBILLINGFLAG", dto
					.getAllowPrebillingFlag(), where);
			makeSQLByStringField("STARTBILLINGFLAG", dto.getStartBillingFlag(),
					where);
			makeSQLByStringField("UNIFIEDCYCLEFLAG", dto.getUnifiedCycleFlag(),
					where);
			makeSQLByIntField("FIRSTVALIDINVOICECYCLEID", dto
					.getFirstValidInvoiceCycleId(), where);
			makeSQLByStringField("RENTDISCTMODE", dto.getRentDisctMode(), where);

			if (dto.getRentDividingDate() != null) {
				String strDate = getDateFormat()
						.format(dto.getRentCyclebDate());
				where.append(" and to_char(RENTDIVIDINGDATE,'").append(
						DATE_FORMAT_STRING).append("')=").append(strDate);
			}
			makeSQLByStringField("UNIFIEDDISCTFLAG", dto.getUnifiedDisctFlag(),
					where);
			if (dto.getEndInvoicingDate() != null) {
				String strDate = getDateFormat().format(
						dto.getEndInvoicingDate());
				where.append(" and to_char(ENDINVOICINGDATE,'").append(
						DATE_FORMAT_STRING).append("')=").append(strDate);
			}
			if (dto.getRentCyclebDate() != null) {
				String strDate = getDateFormat()
						.format(dto.getRentCyclebDate());
				where.append(" and to_char(RENTCYCLEBDATE,'").append(
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
