package com.dtv.oss.service.listhandler.logistics;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.TerminateDeviceDAO;
import com.dtv.oss.service.dao.logistics.DevicePreAuthDAO;
import com.dtv.oss.service.dao.logistics.DevicePreAuthDetailDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HelperCommonUtil;

public class DevicePreAuthDetailListhandler extends ValueListHandler {

	private DevicePreAuthDetailDAO dao = null;

	private String tableName = "t_devicepreauthrecord";

	// ��ѯ������dto
	private CommonQueryConditionDTO dto = null;

	public DevicePreAuthDetailListhandler() {
		dao = new DevicePreAuthDetailDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(DevicePreAuthDetailListhandler.class, LogLevel.DEBUG,
				"���ܿ�����Ԥ��Ȩ��¼��ѯ...");
		if (dto instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto;
		else {
			LogUtility.log(DevicePreAuthDetailListhandler.class,
					LogLevel.DEBUG, "����Ĳ��Ҳ�������...");
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

		// �м�¼ID�������ѯ����
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr1())) {
			int batchId = Integer.valueOf(dto.getSpareStr1()).intValue();
			selectStatement.append(" and batchid=" + batchId);
		}
		
		selectStatement.append(" order by seqno desc"); 
		System.out.println(selectStatement);

		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);

		// ���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}
