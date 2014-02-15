package com.dtv.oss.service.listhandler.logistics;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.logistics.DevicePreMatchDetailDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.HelperCommonUtil;

public class DevicePrematchHisListhandler extends ValueListHandler {

	private DevicePreMatchDetailDAO dao = null;

	private String tableName = "t_deviceprematchrecord";

	// ��ѯ������dto
	private CommonQueryConditionDTO dto = null;

	public DevicePrematchHisListhandler() {
		dao = new DevicePreMatchDetailDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(DevicePrematchHisListhandler.class, LogLevel.DEBUG,
				"���ܿ��䌦��¼��ѯ...");
		if (dto instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto;
		else {
			LogUtility.log(DevicePrematchHisListhandler.class, LogLevel.DEBUG,
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
		begin.append("select * from t_deviceprematchrecord");

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");

		// �м�¼ID�������ѯ����
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr1())) {
			int deviceid = Integer.valueOf(dto.getSpareStr1()).intValue();
			selectStatement.append(" and stbdeviceid=" + deviceid
					+ " or scdeviceid=" + deviceid);
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
