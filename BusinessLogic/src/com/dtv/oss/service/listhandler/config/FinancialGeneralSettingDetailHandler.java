package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.FinancialSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.service.dao.config.FinancialGeneralSettingDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class FinancialGeneralSettingDetailHandler extends ValueListHandler {
	private FinancialGeneralSettingDAO dao = null;

	public FinancialGeneralSettingDetailHandler() {
		this.dao = new FinancialGeneralSettingDAO();
	}

	protected void executeSearch(GenericDAO dao, boolean isWrap, boolean isCount)
			throws ListHandlerException {
		super.executeSearch(dao, isWrap, isCount);
		if (list != null && list.size() > 1) {
			throw new ListHandlerException("�Ʒ�����ͨ�ò������ü�¼������1");
		}
	}

	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto == null || (!(dto instanceof FinancialSettingDTO))) {
			LogUtility.log(getClass(), LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		constructSelectQueryString((FinancialSettingDTO) dto);
		// ִ�����ݲ�ѯ
		executeSearch(dao, false, false);
	}

	private void constructSelectQueryString(FinancialSettingDTO dto) {
		StringBuffer buffer = new StringBuffer("select ");
		// t_financialsetting
		// ����NAME
		// ʹ��ȫ��ͳһ��������UNIFIEDCYCLEFLAG
		// �Ƿ�������ɽ�CALCULATEPUNISHMENTFLAG
		// ���ɽ�ʼ��������PUNISHMENTSTARTDATE
		// ���ɽ���Ŀ����PUNISHMENTACCTITEMTYPEID
		// �����ɽ����PUNISHMENTRATE

		// �ճ��ʵ���������INVOICEDUEDATE
		// Ƿ�Ѹ����Զ��ָ���־AUTORESUMECPFLAG
		// ��ͷ����ģʽSMAILLCHANGEPROCESSMODE
		// Ԥ��ֿ۷�ʽPREPAYMENTDEDUCTIONMODE
		// �ʵ��ۼ�ģʽ INVOICEACCUMULATEMODE
		buffer.append("AUTORESUMECPFLAG,CALCULATEPUNISHMENTFLAG,");
		buffer.append("INVOICEACCUMULATEMODE,INVOICEDUEDATE,NAME,");
		buffer.append("PREPAYMENTDEDUCTIONMODE,DT_LASTMOD,DT_CREATE,");
		buffer
				.append("PUNISHMENTACCTITEMTYPEID,PUNISHMENTRATE,PUNISHMENTSTARTDATE,");
		buffer.append("SMALLCHANGEPROCESSMODE,UNIFIEDCYCLEFLAG,");

		/* no usage *** */
		buffer.append("BILLINGSTARTDATE,DELAYONEMONTHINARREARFLAG,");
		buffer.append("FORCEDDEPOSITACCTITEMTYPEID,CALCULATEPUNISHMENTFLAG,");
		buffer.append("RETURNDEVICEACCTITEMTYPEID,SETOFFLEVEL ");
		buffer.append(" from t_financialsetting ");/* no usage *** */

		// AUTORESUMECPFLAG 12 Y VARCHAR2 (5) NULL
		// *BILLINGSTARTDATE 3 Y DATE NULL
		// CALCULATEPUNISHMENTFLAG 5 Y VARCHAR2 (5) NULL
		// *DELAYONEMONTHINARREARFLAG 13 Y VARCHAR2 (5) NULL
		// *DT_CREATE 14 Y TIMESTAMP(6) NULL
		// *DT_LASTMOD 15 Y TIMESTAMP(6) NULL
		// *FORCEDDEPOSITACCTITEMTYPEID 17 Y VARCHAR2 (20) null
		// INVOICEACCUMULATEMODE 10 Y VARCHAR2 (5) NULL
		// INVOICEDUEDATE 4 Y NUMBER (10) NULL
		// NAME 1 1 N VARCHAR2 (50) NULL
		// PREPAYMENTDEDUCTIONMODE 9 Y VARCHAR2 (5) NULL
		// PUNISHMENTACCTITEMTYPEID 16 Y VARCHAR2 (20)
		// PUNISHMENTRATE 7 Y NUMBER (10,3) 0
		// PUNISHMENTSTARTDATE 6 Y NUMBER (10) NULL
		// *RETURNDEVICEACCTITEMTYPEID 18 Y VARCHAR2 (20) null
		// *SETOFFLEVEL 11 Y VARCHAR2 (5) NULL
		// SMALLCHANGEPROCESSMODE 8 Y VARCHAR2 (5) NULL
		// UNIFIEDCYCLEFLAG 2 Y VARCHAR2 (5) NULL

		// buffer.append(" where 1=1 ");
		// makeSQLByStringField("AUTORESUMECPFLAG", dto.getName(), buffer);
		// makeSQLByStringField("CALCULATEPUNISHMENTFLAG", dto.getName(),
		// buffer);
		// makeSQLByStringField("INVOICEACCUMULATEMODE", dto.getName(), buffer);
		// makeSQLByStringField("INVOICEDUEDATE", dto.getName(), buffer);
		// makeSQLByStringField("NAME", dto.getName(), buffer);
		// makeSQLByStringField("PREPAYMENTDEDUCTIONMODE", dto.getName(),
		// buffer);
		// makeSQLByStringField("PUNISHMENTACCTITEMTYPEID", dto.getName(),
		// buffer);
		// makeSQLByStringField("PUNISHMENTRATE", dto.getName(), buffer);
		// makeSQLByStringField("PUNISHMENTSTARTDATE", dto.getName(), buffer);
		// makeSQLByStringField("SMALLCHANGEPROCESSMODE", dto.getName(),
		// buffer);
		// makeSQLByStringField("UNIFIEDCYCLEFLAG", dto.getName(), buffer);
		// ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable("t_financialsetting");
		if (dto != null && dto.getName() != null) {
			setRecordCountSuffixBuffer(new StringBuffer(" where name='")
					.append(dto.getName()).append("' "));
			buffer.append(" where name='").append(dto.getName()).append("' ");
		} else {
			setRecordCountSuffixBuffer(new StringBuffer("  "));
		}
		// ���õ�ǰ���ݲ�ѯsql

		setRecordDataQueryBuffer(buffer);
	}
}
