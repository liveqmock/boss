package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.UserPointsCumulatedRuleDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.PointsCumulateRuleDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class PointsCumulateRuleListHandler extends ValueListHandler {
    private PointsCumulateRuleDAO dao = null;
    final private String tableName = "t_userpointscumulatedrule t";


	private UserPointsCumulatedRuleDTO dto = null;

	public PointsCumulateRuleListHandler() {
	  	this.dao = new PointsCumulateRuleDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(PointsCumulateRuleListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof UserPointsCumulatedRuleDTO) 
       	 this.dto = (UserPointsCumulatedRuleDTO)dto1;
       else {
       	LogUtility.log(PointsCumulateRuleListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //构造查询字符串
       constructSelectQueryString(dto);
       //执行数据查询
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(UserPointsCumulatedRuleDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	 
    	makeSQLByIntField("t.ID",dto.getId(),selectStatement);
    	 
    	makeSQLByIntField("t.condevent", dto.getCondEvent(), selectStatement);
    	makeSQLByIntField("t.productid", dto.getProductID(), selectStatement);
    	makeSQLByStringField("t.status", dto.getStatus(), selectStatement); 	
        
	 
		selectStatement.append(" order by t.id desc");     
       
		//设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}