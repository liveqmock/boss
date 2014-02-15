package com.dtv.oss.service.listhandler.logistics;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.logistics.VendorDeviceDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * 查找供应商设备信息
 * add by jason.zhou 2007-3-13
 */
public class VendorDeviceListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private VendorDeviceDAO dao = null;
	String tableName = "T_VendorDeviceDepot a";
	
	public VendorDeviceListHandler() {
		this.dao = new VendorDeviceDAO();
	}

	
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(VendorDeviceListHandler.class, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(VendorDeviceListHandler.class, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		// 构造查询字符串
		constructSelectQueryString(dto);
		// 执行数据查询
		executeSearch(dao, false, false);
	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto) {

		StringBuffer begin = new StringBuffer();
		begin.append("select a.* from " + tableName);
		
		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 ");

		//dto.setSpareStr1:DeviceClass
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			makeSQLByStringField("a.DeviceClass", dto.getSpareStr1(), selectStatement);
		//dto.setSpareStr2:DeviceModel
		if(!(dto.getSpareStr2()==null || "".equals(dto.getSpareStr2())))
			makeSQLByStringField("a.DeviceModel", dto.getSpareStr2(), selectStatement);
		//dto.setSpareStr3:SerialNo
		if(!(dto.getSpareStr3()==null || "".equals(dto.getSpareStr3())))
			makeSQLByStringField("a.SerialNo", dto.getSpareStr3(), selectStatement);

		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
}