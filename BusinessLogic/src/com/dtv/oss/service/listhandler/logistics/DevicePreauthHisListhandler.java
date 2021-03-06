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

	// 查询条件的dto
	private CommonQueryConditionDTO dto = null;

	public DevicePreauthHisListhandler() {
		dao = new DevicePreAuthDetailDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(DevicePreauthHisListhandler.class, LogLevel.DEBUG,
				"智能卡批量预授权历史记录查询...");
		if (dto instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto;
		else {
			LogUtility.log(DevicePreauthHisListhandler.class, LogLevel.DEBUG,
					"传入的查找参数有误...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		// 构造查询字符串
		constructSelectQueryString(this.dto);
		// 执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select * from t_devicepreauthrecord");

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");

		// 有设备ID，加入查询条件
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr1())) {
			int deviceid = Integer.valueOf(dto.getSpareStr1()).intValue();
			selectStatement.append(" and deviceid=" + deviceid);
		}

		appendOrderByString(selectStatement);

		System.out.println(selectStatement);

		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);

		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	private void appendOrderByString(StringBuffer selectStatement) {

			selectStatement.append(" order by seqno desc");

	}

}
