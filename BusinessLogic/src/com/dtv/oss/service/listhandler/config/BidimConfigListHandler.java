package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.BidimConfigSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.BidimConfigDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class BidimConfigListHandler extends ValueListHandler {
	private BidimConfigDAO dao = null;

	final private String tableName = "t_bidimconfigsetting t";

	private BidimConfigSettingDTO dto = null;

	public BidimConfigListHandler() {
		this.dao = new BidimConfigDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof BidimConfigSettingDTO)
			this.dto = (BidimConfigSettingDTO) dto1;
		else {
			LogUtility.log(getClass(), LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		// added
		// if (fillDTOWithPrivilege(dto) == false) return;
		// �����ѯ�ַ���
		constructSelectQueryString(dto);
		// ִ�����ݲ�ѯ
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(BidimConfigSettingDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		makeSQLByStringField("t.configType", dto.getConfigType(),
				selectStatement);
		makeSQLByStringField("t.configSubType", dto.getConfigSubType(),
				selectStatement);
		makeSQLByStringField("t.valueType", dto.getValueType(), selectStatement);
		makeSQLByStringField("t.status", dto.getStatus(), selectStatement);
		makeSQLByIntField("t.id", dto.getId(), selectStatement);

		selectStatement.append(" order by t.id desc");

		// ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		// ���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}