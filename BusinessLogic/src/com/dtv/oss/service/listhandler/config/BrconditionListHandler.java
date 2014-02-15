package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.BrconditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.BrconditionDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class BrconditionListHandler extends ValueListHandler {
    private BrconditionDAO dao = null;
    final private String tableName = "T_BRCONDITION t";


	private BrconditionDTO dto = null;

	public BrconditionListHandler() {
	  	this.dao = new BrconditionDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(BrconditionListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof BrconditionDTO) 
       	 this.dto = (BrconditionDTO)dto1;
       else {
       	LogUtility.log(MarketSegmentListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //构造查询字符串
       constructSelectQueryString(dto);
       //执行数据查询
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(BrconditionDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	 
        makeSQLByIntField("t.brcntID",dto.getBrcntID(),selectStatement);
        makeSQLByStringField("t.cntType",dto.getCntType(),selectStatement);
        makeSQLByStringField("t.status",dto.getStatus(),selectStatement);
        makeSQLByStringField("t.cntname",dto.getCntName(),selectStatement,"like");
	 	
		
		selectStatement.append(" order by t.brcntID desc");     
       
		//设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}