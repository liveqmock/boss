package com.dtv.oss.service.listhandler.logistics;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.TerminateDeviceDAO;
import com.dtv.oss.service.dao.logistics.DevicePreAuthDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HelperCommonUtil;

public class DevicePreAuthListhandler extends ValueListHandler {

	private DevicePreAuthDAO dao = null;

	private String tableName = "t_devicebatchpreauth";

	// 查询条件的dto
	private CommonQueryConditionDTO dto = null;

	public DevicePreAuthListhandler() {
		dao = new DevicePreAuthDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(DevicePreAuthListhandler.class, LogLevel.DEBUG,
				"智能卡批量预授权记录查询...");
		if (dto instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto;
		else {
			LogUtility.log(DevicePreAuthListhandler.class, LogLevel.DEBUG,
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
		begin.append("select * from t_devicebatchpreauth");

		StringBuffer selectStatement = new StringBuffer();
		selectStatement
				.append(" where 1=1 and operationtype in ('AP','DP','DA') ");

		//有记录ID，加入查询条件
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr1())) {
			int batchId = Integer.valueOf(dto.getSpareStr1()).intValue();
			selectStatement.append(" and batchid=" + batchId);
		}
		//有设备ID，加入查询条件
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr2())) {
			int deviceId = Integer.valueOf(dto.getSpareStr2()).intValue();
			selectStatement
					.append(" and batchid in (select batchid from t_devicepreauthrecord where deviceId="
							+ deviceId + ") ");
		}
		//有序列号，加入查询条件
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr3())) {
			String referSheetSerialNO = dto.getSpareStr3();
			selectStatement
					.append(" and batchId in (select batchid from t_devicepreauthrecord where serialno='"
							+ referSheetSerialNO + "') ");
		}
		//有操作类型，加入查询条件
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr4())) {
			String operationtype = dto.getSpareStr4();
			selectStatement.append(" and operationtype='" + operationtype
					+ "'");
		}
		//有授权产品，加入查询条件
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr5())) {
			String productIdList = dto.getSpareStr5();
			productIdList=productIdList.replaceAll(";", ",");
			selectStatement
					.append(" and batchId in (select batchId from t_devicepreauthrecord where productid in ("
							+ productIdList + ")) ");
		}
		
		//有起始时间，加入查询条件
		if (dto.getBeginTime() != null) {
					selectStatement.append(" and CREATETIME >= to_timestamp('"
							+ dto.getBeginTime() + "','YYYY-MM-DD-HH24:MI:SSxff')");
		}

		//有终止时间，加入查询条件
		if (dto.getEndTime() != null) {
					selectStatement.append(" and CREATETIME <= to_timestamp('"
							+ dto.getEndTime() + "','YYYY-MM-DD-HH24:MI:SSxff')");
		}
		
		selectStatement.append(" order by batchid desc"); 
		
		System.out.println(selectStatement);

		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}
