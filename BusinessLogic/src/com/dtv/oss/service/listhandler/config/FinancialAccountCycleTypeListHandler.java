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
		// ִ�����ݲ�ѯ
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

		// ���ID 1 1 N NUMBER (10) NULL
		// CTYPE 3 N VARCHAR2 (5) NULL
		// ���� NAME 2 Y VARCHAR2 (50) NULL
		// ���� DESCRIPTION 4 Y VARCHAR2 (200) NULL
		// ״̬ STATUS 20 Y VARCHAR2 (5) Null
		// ��Ӧ�Ʒ��������� BILLINGCYCLETYPEID 11 Y NUMBER (10) NULL
		// ��һ����Ч���� FIRSTVALIDINVOICECYCLEID 12 Y NUMBER (10) NULL
		// ʱ�䳤�� CYCLECOUNT 13 Y NUMBER (10) NULL
		//
		// ��ѿ�ʼʱ��� RENTCYCLEBDATE 6 N DATE NULL
		// ʹ�÷��ÿ�ʼʱ�� OTHERCYCLEBDATE 7 Y DATE NULL
		// �ʵ����ɽ�ֹ�� ENDINVOICINGDATE 19 Y DATE
		// �ʵ��������� INVOICEDUEDATE 10 Y DATE NULL
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
		// ���õ�ǰ���ݲ�ѯsql
		select.append(" from ").append(tableName).append(where).append(
				" order by id desc ");
		setRecordDataQueryBuffer(select);
	}

	private SimpleDateFormat getDateFormat() {
		dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
		return dateFormat;
	}

}
