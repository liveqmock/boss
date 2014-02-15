package com.dtv.oss.service.listhandler.logistics;

import com.dtv.oss.dto.VodstbDeviceImportDetailDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.logistics.VodstbDeviceImportDetailDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class VodstbDeviceImportDetailListHandler extends ValueListHandler {
	  /**
	 * ���캯�����趨DAO����ΪVodstbDeviceImportDetailDAO
	 */
	public VodstbDeviceImportDetailListHandler() {
	  this.dao = new VodstbDeviceImportDetailDAO();
	}

	/**
	 * �豸DTO
	 */
	private VodstbDeviceImportDetailDTO dto;

	/**
	 * ��ѯʹ�õ�DAO����
	 */
	private VodstbDeviceImportDetailDAO dao = null;

	/**
	 * ��ѯ����
	 */
	private String selectCriteria = "";

	/**
	 * ��ѯ�ı���
	 * @param dto
	 * @throws com.dtv.oss.service.listhandler.ListHandlerException
	 */
	String tableName = "vod_stbdevice_import_detail";

	/**
	* �����豸��ѯ����
	* @param dto
	* @throws com.dtv.oss.service.listhandler.ListHandlerException
	*/
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(VodstbDeviceImportDetailListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof VodstbDeviceImportDetailDTO)
			this.dto = (VodstbDeviceImportDetailDTO) dto1;
		else {
			LogUtility.log(VodstbDeviceImportDetailListHandler.class, LogLevel.DEBUG,
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
	private void constructSelectQueryString(VodstbDeviceImportDetailDTO dto) {
		
	StringBuffer begin = new StringBuffer();
	begin.append("select * from " + tableName);
	StringBuffer selectStatement = new StringBuffer();
	  // 1.��BatchID��
	  if (dto.getBatchID() != 0) {
	      selectStatement.append(" where batchid=" + dto.getBatchID());
	  }

	  LogUtility.log(VodstbDeviceImportDetailListHandler.class, LogLevel.DEBUG, selectStatement.toString());
	  appendOrderByString(selectStatement);
	//  appendOrderByString(end);
	//  ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
	  setRecordCountQueryTable(tableName);
	  setRecordCountSuffixBuffer(selectStatement);
	//  ���õ�ǰ���ݲ�ѯsql
	  setRecordDataQueryBuffer(begin.append(selectStatement));
	  }
	  private void appendOrderByString(StringBuffer selectStatement) {
	  	 
	   
	  		selectStatement.append("order by serialno desc");
	  	 
	   
	  }
	}