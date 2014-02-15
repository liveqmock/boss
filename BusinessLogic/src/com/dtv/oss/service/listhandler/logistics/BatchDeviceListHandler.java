/*
 * Created on 2005-11-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.logistics;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.logistics.BatchDeviceDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author 241115c
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BatchDeviceListHandler extends ValueListHandler {

	public BatchDeviceListHandler() {
		this.dao = new BatchDeviceDAO();
	}

	private CommonQueryConditionDTO dto = null;
	//请注意这个是为了满足歌华的需要做了一点特殊处理的，以后变更的时候要记得这个需要同步
	private BatchDeviceDAO dao = null;

	private String selectCriteria = "";

	String tableName = "GEHUA_DEVICESERIALNO_FOR_QUERY a Left Join t_terminaldevice b On  a.serialno=b.serialno";
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(DeviceListHandler.class, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(DeviceListHandler.class, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		// 构造查询字符串
		constructSelectQueryString(dto);
		// 执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto) {

		StringBuffer begin = new StringBuffer();
		begin.append("Select a.serialno showserialno,b.* From  " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);

		// append additional conditions to where clause
		selectStatement.append(" where 1=1 ");

		// 有批号
		if (dto.getSpareStr1() != null)
			selectStatement.append(" and a.batchno='" + dto.getSpareStr1()+"'");

		
		LogUtility.log(DeviceListHandler.class, LogLevel.DEBUG, selectStatement.toString());
		appendOrderByString(selectStatement);
		// 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by b.status");
	}
}