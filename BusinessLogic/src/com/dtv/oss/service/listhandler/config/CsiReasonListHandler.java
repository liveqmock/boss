package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.CsiActionReasonSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.CsiActionReasonSettingDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class CsiReasonListHandler extends ValueListHandler {
    private CsiActionReasonSettingDAO dao = null;
    final private String tableName = "t_csiactionreasonsetting t";


	private CsiActionReasonSettingDTO dto = null;

	public CsiReasonListHandler() {
	  	this.dao = new CsiActionReasonSettingDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(CsiReasonListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof CsiActionReasonSettingDTO) 
       	 this.dto = (CsiActionReasonSettingDTO)dto1;
       else {
       	LogUtility.log(CsiReasonListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //构造查询字符串
       constructSelectQueryString(dto);
       //执行数据查询
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(CsiActionReasonSettingDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	 
        makeSQLByIntField("t.seqno",dto.getSeqNo(),selectStatement);
        makeSQLByStringField("t.action",dto.getAction(),selectStatement);
        makeSQLByStringField("t.canemptyflag",dto.getCanEmptyFlag(),selectStatement);
        makeSQLByStringField("t.csitype",dto.getCsiType(),selectStatement);
        makeSQLByStringField("t.status",dto.getStatus(),selectStatement);
        makeSQLByStringField("t.displayname",dto.getDisplayName(),selectStatement,"like");
	 	
		
		selectStatement.append(" order by t.seqNo desc");     
       
		//设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}