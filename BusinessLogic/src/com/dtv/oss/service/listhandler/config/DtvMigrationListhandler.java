package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.DtvMigrationAreaDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.DtvMigrationDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.HelperCommonUtil;

public class DtvMigrationListhandler extends ValueListHandler {

	private DtvMigrationDAO dao = null;

	final private String tableName = "t_dtvmigrationarea";

	public DtvMigrationListhandler() {
		this.dao = new DtvMigrationDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {

		LogUtility.log(DtvMigrationListhandler.class, LogLevel.DEBUG,
				"ƽ��С������...");

		DtvMigrationAreaDTO dto1 = (DtvMigrationAreaDTO) dto;
		// �����ѯ�ַ���
		constructSelectQueryString(dto1);
		// ִ�����ݲ�ѯ
		executeSearch(dao, false, false);

	}

	private void constructSelectQueryString(DtvMigrationAreaDTO dto) {

		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");

		makeSQLByIntField("id", dto.getId(), selectStatement);
		makeSQLByStringField("areaCode", dto.getAreaCode(), selectStatement);
		makeSQLByStringField("areaName", dto.getAreaName(), selectStatement);
		
		
		
		// ��������
		if (dto.getRegionalAreaId()!=0)
			appendString("regionalAreaId in " +
					"(select Id from T_DISTRICTSETTING connect by prior ID=BELONGTO  start with ID="+dto.getRegionalAreaId()+")", selectStatement);
		
		makeSQLByStringField("migrationTeamName", dto.getMigrationTeamName(),
				selectStatement);
		makeSQLByStringField("status", dto.getStatus(), selectStatement);
		
		if (dto.getCreateDate() != null) {
			selectStatement.append(" and createDate=to_timestamp('")
					.append(dto.getCreateDate().toString()).append(
							"', 'YYYY-MM-DD-HH24:MI:SSxff')");

		}

		appendOrderByString(selectStatement);

		// ���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));

	}

	/**
	 * @param selectStatement
	 */
	private void appendOrderByString(StringBuffer selectStatement) {

		selectStatement.append(" order by id desc");
	}
}
