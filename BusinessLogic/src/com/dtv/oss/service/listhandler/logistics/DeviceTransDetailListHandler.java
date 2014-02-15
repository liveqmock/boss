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
 * ���캯�����趨DAO����ΪDeviceDAO
 */
public DeviceTransDetailListHandler() {
  this.dao = new DeviceTransDetailDAO();
}

/**
 * �豸DTO
 */
private DeviceTransitionDetailDTO dto;

/**
 * ��ѯʹ�õ�DAO����
 */
private DeviceTransDetailDAO dao = null;

/**
 * ��ѯ����
 */
private String selectCriteria = "";

/**
 * ��ѯ�ı���
 * @param dto
 * @throws com.dtv.oss.service.listhandler.ListHandlerException
 */
String tableName = "T_DEVICETRANSITIONDETAIL a";

/**
* �����豸��ѯ����
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
	 
	 
	//�����ѯ�ַ���
	constructSelectQueryString(dto);
	//ִ�����ݲ�ѯ
	executeSearch(dao, true, true);
}

 
/**
 * ����ѯ����dto����װ��sql���
 * @param dto
 * @return
 */
private void constructSelectQueryString(DeviceTransitionDetailDTO dto) {
	
StringBuffer begin = new StringBuffer();
begin.append("select a.* from " + tableName);
StringBuffer selectStatement = new StringBuffer(128);
boolean bHasPrio = false;
  // 1.��BatchID��
  if (dto.getBatchID() != 0) {
      selectStatement.append(" where a.BATCHID=" + dto.getBatchID());
      bHasPrio = true;
  }

  LogUtility.log(DeviceListHandler.class, LogLevel.DEBUG, selectStatement.toString());
  appendOrderByString(selectStatement);
//  appendOrderByString(end);
//  ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
  setRecordCountQueryTable(tableName);
  setRecordCountSuffixBuffer(selectStatement);
//  ���õ�ǰ���ݲ�ѯsql
  setRecordDataQueryBuffer(begin.append(selectStatement));
  }
  private void appendOrderByString(StringBuffer selectStatement) {
  	 
   
  		selectStatement.append("order by a.seqno");
  	 
   
  }
}