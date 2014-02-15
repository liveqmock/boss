package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.BillBoardDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.BillBoardDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class BillBoardListHandler extends ValueListHandler {
    private BillBoardDAO dao = null;
    final private String tableName = "t_billboard t";


	private BillBoardDTO dto = null;

	public BillBoardListHandler() {
	  	this.dao = new BillBoardDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(BillBoardListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof BillBoardDTO) 
       	 this.dto = (BillBoardDTO)dto1;
       else {
       	LogUtility.log(BillBoardListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //构造查询字符串
       constructSelectQueryString(dto);
       //执行数据查询
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(BillBoardDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	//公告名称
    	makeSQLByStringField("t.name", dto.getName(), selectStatement,"like");
    	//发布人
    	makeSQLByStringField("t.PublishPerson", dto.getPublishPerson(), selectStatement);
    	//发布原因
    	makeSQLByStringField("t.PublishReason", dto.getPublishReason(), selectStatement);
    	//状态
    	makeSQLByStringField("t.status", dto.getStatus(), selectStatement);
    	//优先级
    	makeSQLByStringField("t.grade", dto.getGrade(), selectStatement);
    	//seqno
    	makeSQLByIntField("t.seqno", dto.getSeqNo(), selectStatement);
    	
    	//dateFrom存的是查询页面中发布日期的开始时间
		if (dto.getDateFrom()!=null) 
		        selectStatement.append("and t.publishDate>=to_timestamp('").append(dto.getDateFrom().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')"); 
//		dateFrom存的是查询页面中发布日期的结束时间
		if (dto.getDateTo()!=null) 
			   selectStatement.append("and t.publishDate<=to_timestamp('").append(dto.getDateTo().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')"); 
    	
		selectStatement.append(" order by t.seqno desc");     
       
		//设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}