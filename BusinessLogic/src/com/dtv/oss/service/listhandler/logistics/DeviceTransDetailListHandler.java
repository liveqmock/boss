package com.dtv.oss.service.listhandler.logistics;

import com.dtv.oss.dto.DeviceTransitionDetailDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.logistics.DeviceTransDetailDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * <p>Title: BOSS_P5 for SCN</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: SHDV</p>
 * @author chenjiang
 * @version 1.0
 */

public class DeviceTransDetailListHandler extends ValueListHandler {
  /**
 * 构造函数，设定DAO对象为DeviceDAO
 */
public DeviceTransDetailListHandler() {
  this.dao = new DeviceTransDetailDAO();
}

/**
 * 设备DTO
 */
private DeviceTransitionDetailDTO dto;

/**
 * 查询使用的DAO对象
 */
private DeviceTransDetailDAO dao = null;

/**
 * 查询条件
 */
private String selectCriteria = "";

/**
 * 查询的表名
 * @param dto
 * @throws com.dtv.oss.service.listhandler.ListHandlerException
 */
String tableName = "T_DEVICETRANSITIONDETAIL a";

/**
* 设置设备查询条件
* @param dto
* @throws com.dtv.oss.service.listhandler.ListHandlerException
*/
public void setCriteria(Object dto1) throws ListHandlerException {
	LogUtility.log(DeviceTransDetailListHandler.class, LogLevel.DEBUG,
			"in setCriteria begin setCriteria...");
	if (dto1 instanceof DeviceTransitionDetailDTO)
		this.dto = (DeviceTransitionDetailDTO) dto1;
	else {
		LogUtility.log(DeviceTransDetailListHandler.class, LogLevel.DEBUG,
				"in setCriteria method the dto type is not proper...");
		throw new ListHandlerException("the dto type is not proper...");
	}
	 
	 
	//构造查询字符串
	constructSelectQueryString(dto);
	//执行数据查询
	executeSearch(dao, true, true);
}

 
/**
 * 检查查询条件dto，包装成sql语句
 * @param dto
 * @return
 */
private void constructSelectQueryString(DeviceTransitionDetailDTO dto) {
	
StringBuffer begin = new StringBuffer();
begin.append("select a.* from " + tableName);
StringBuffer selectStatement = new StringBuffer(128);
boolean bHasPrio = false;
  // 1.有BatchID吗？
  if (dto.getBatchID() != 0) {
      selectStatement.append(" where a.BATCHID=" + dto.getBatchID());
      bHasPrio = true;
  }

  LogUtility.log(DeviceListHandler.class, LogLevel.DEBUG, selectStatement.toString());
  appendOrderByString(selectStatement);
//  appendOrderByString(end);
//  设置构造取得当前查询总纪录数的sql
  setRecordCountQueryTable(tableName);
  setRecordCountSuffixBuffer(selectStatement);
//  设置当前数据查询sql
  setRecordDataQueryBuffer(begin.append(selectStatement));
  }
  private void appendOrderByString(StringBuffer selectStatement) {
  	 
   
  		selectStatement.append("order by a.seqno");
  	 
   
  }
}