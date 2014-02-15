package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.CsiTypeReason2DeviceDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.CsiTypeReason2DeviceDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class CsiTypeReason2DeviceListHandler extends ValueListHandler {
    private CsiTypeReason2DeviceDAO dao = null;
    final private String tableName = "t_csitypereason2device t";


	private CsiTypeReason2DeviceDTO dto = null;

	public CsiTypeReason2DeviceListHandler() {
	  	this.dao = new CsiTypeReason2DeviceDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(CsiTypeReason2DeviceListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof CsiTypeReason2DeviceDTO) 
       	 this.dto = (CsiTypeReason2DeviceDTO)dto1;
       else {
       	LogUtility.log(CsiTypeReason2DeviceListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //构造查询字符串
       constructSelectQueryString(dto);
       //执行数据查询
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(CsiTypeReason2DeviceDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	 
        makeSQLByStringField("t.csitype",dto.getCsiType(),selectStatement);
        makeSQLByStringField("t.CsiCreateReason",dto.getCsiCreateReason(),selectStatement);
        makeSQLByStringField("t.status",dto.getStatus(),selectStatement);
        makeSQLByStringField("t.referPurpose",dto.getReferPurpose(),selectStatement);
        makeSQLByIntField("t.seqno",dto.getSeqNo(),selectStatement);
		
		selectStatement.append(" order by t.seqno desc");     
       
		//设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}