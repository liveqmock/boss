package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.UserPointsExchangeActivityDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.PointsActivityDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

/**
 * @author Chen jiang
 */

public class PointsActivityListHandler extends ValueListHandler {
    private PointsActivityDAO dao = null;
    final private String tableName = "t_userpointsexchangeactivity t";


	private UserPointsExchangeActivityDTO dto = null;

	public PointsActivityListHandler() {
	  	this.dao = new PointsActivityDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(PointsActivityListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof UserPointsExchangeActivityDTO) 
       	 this.dto = (UserPointsExchangeActivityDTO)dto1;
       else {
       	LogUtility.log(PointsActivityListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //构造查询字符串
       constructSelectQueryString(dto);
       //执行数据查询
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(UserPointsExchangeActivityDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	//活动ID
    	if(dto.getId()!=null)
    	makeSQLByIntField("t.ID",dto.getId().intValue(),selectStatement);
    	//活动名称
    	makeSQLByStringField("t.name", dto.getName(), selectStatement, "like");
    	
    	if(dto.getDateStart()!=null){
         	selectStatement.append(" and t.DATESTART>=to_timestamp('").append(dto.getDateStart().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
            }
        
         
          if(dto.getDateEnd()!=null){
          	selectStatement.append(" and t.DATEEND<=to_timestamp('").append(dto.getDateEnd().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
          
          }
         // appendString("t.status <> '" + CommonConstDefinition.GENERALSTATUS_INVALIDATE + "'", selectStatement);	
        
	 
		selectStatement.append(" order by t.id desc");     
       
		//设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}