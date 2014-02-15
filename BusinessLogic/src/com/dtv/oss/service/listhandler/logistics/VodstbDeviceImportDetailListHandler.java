package com.dtv.oss.service.listhandler.logistics;

import com.dtv.oss.dto.VodstbDeviceImportDetailDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.logistics.VodstbDeviceImportDetailDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class VodstbDeviceImportDetailListHandler extends ValueListHandler {
	  /**
	 * 构造函数，设定DAO对象为VodstbDeviceImportDetailDAO
	 */
	public VodstbDeviceImportDetailListHandler() {
	  this.dao = new VodstbDeviceImportDetailDAO();
	}

	/**
	 * 设备DTO
	 */
	private VodstbDeviceImportDetailDTO dto;

	/**
	 * 查询使用的DAO对象
	 */
	private VodstbDeviceImportDetailDAO dao = null;

	/**
	 * 查询条件
	 */
	private String selectCriteria = "";

	/**
	 * 查询的表名
	 * @param dto
	 * @throws com.dtv.oss.service.listhandler.ListHandlerException
	 */
	String tableName = "vod_stbdevice_import_detail";

	/**
	* 设置设备查询条件
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
	private void constructSelectQueryString(VodstbDeviceImportDetailDTO dto) {
		
	StringBuffer begin = new StringBuffer();
	begin.append("select * from " + tableName);
	StringBuffer selectStatement = new StringBuffer();
	  // 1.有BatchID吗？
	  if (dto.getBatchID() != 0) {
	      selectStatement.append(" where batchid=" + dto.getBatchID());
	  }

	  LogUtility.log(VodstbDeviceImportDetailListHandler.class, LogLevel.DEBUG, selectStatement.toString());
	  appendOrderByString(selectStatement);
	//  appendOrderByString(end);
	//  设置构造取得当前查询总纪录数的sql
	  setRecordCountQueryTable(tableName);
	  setRecordCountSuffixBuffer(selectStatement);
	//  设置当前数据查询sql
	  setRecordDataQueryBuffer(begin.append(selectStatement));
	  }
	  private void appendOrderByString(StringBuffer selectStatement) {
	  	 
	   
	  		selectStatement.append("order by serialno desc");
	  	 
	   
	  }
	}