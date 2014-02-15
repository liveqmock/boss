package com.dtv.oss.service.listhandler.logistics;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.logistics.DevicePreAuthDetailDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.HelperCommonUtil;

public class DevicePreauthHisListhandler extends ValueListHandler {

	private DevicePreAuthDetailDAO dao = null;

	private String tableName = "t_devicepreauthrecord";

	// ��ѯ������dto
	private CommonQueryConditionDTO dto = null;

	public DevicePreauthHisListhandler() {
		dao = new DevicePreAuthDetailDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(DevicePreauthHisListhandler.class, LogLevel.DEBUG,
				"���ܿ�����Ԥ��Ȩ��ʷ��¼��ѯ...");
		if (dto instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto;
		else {
			LogUtility.log(DevicePreauthHisListhandler.class, LogLevel.DEBUG,
					"����Ĳ��Ҳ�������...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		// �����ѯ�ַ���
		constructSelectQueryString(this.dto);
		// ִ�����ݲ�ѯ
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select * from t_devicepreauthrecord");

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");

		// ���豸ID�������ѯ����
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr1())) {
			int deviceid = Integer.valueOf(dto.getSpareStr1()).intValue();
			selectStatement.append(" and deviceid=" + deviceid);
		}

		appendOrderByString(selectStatement);

		System.out.println(selectStatement);

		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);

		// ���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	private void appendOrderByString(StringBuffer selectStatement) {

			selectStatement.append(" order by seqno desc");

	}

}
