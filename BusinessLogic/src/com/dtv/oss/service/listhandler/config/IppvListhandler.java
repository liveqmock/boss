package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.CAWalletDefineDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.dao.config.IppvDAO;

public class IppvListhandler extends ValueListHandler {

	private IppvDAO dao = null;

	final private String tableName = "t_cawalletdefine";

	public IppvListhandler() {
		this.dao = new IppvDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {

		LogUtility.log(IppvListhandler.class, LogLevel.DEBUG, "IPPVǮ������...");

		CAWalletDefineDTO dto1 = (CAWalletDefineDTO) dto;
		// �����ѯ�ַ���
		constructSelectQueryString(dto1);
		// ִ�����ݲ�ѯ
		executeSearch(dao, false, false);

	}

	private void constructSelectQueryString(CAWalletDefineDTO dto) {

		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");

		makeSQLByStringField("cawalletcode", dto.getCaWalletCode(),
				selectStatement);
		makeSQLByStringField("cawalletname", dto.getCaWalletName(),
				selectStatement, "like");
		makeSQLByStringField("devicemodellist", dto.getDeviceModelList(),
				selectStatement);
		makeSQLByStringField("required", dto.getRequired(), selectStatement);

		appendOrderByString(selectStatement);

		// ���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));

	}

	/**
	 * @param selectStatement
	 */
	private void appendOrderByString(StringBuffer selectStatement) {

		selectStatement.append(" order by caWalletCode desc");
	}
}
