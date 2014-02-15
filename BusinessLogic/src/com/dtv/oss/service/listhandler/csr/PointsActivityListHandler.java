package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
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


	private CommonQueryConditionDTO dto = null;

	public PointsActivityListHandler() {
	  	this.dao = new PointsActivityDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(PointsActivityListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof CommonQueryConditionDTO) 
       	 this.dto = (CommonQueryConditionDTO)dto1;
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

    private void constructSelectQueryString(CommonQueryConditionDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	//活动ID
    	makeSQLByIntField("t.ID",dto.getStreet(),selectStatement);
        /*
         * search condition:
         * 用customerId来存积分这个值
         */
        if(dto.getCustomerID()>0)
        	selectStatement.append(" and t.id in (select t.activityid from t_userpointsexchangecond t " +
        			"where "+dto.getCustomerID()+">=nvl(t.pointrange1,"+dto.getCustomerID()+") and "+dto.getCustomerID()+"<=nvl(t.pointrange2,"+dto.getCustomerID()+"))");
        
        if(dto.getSpareTime1()!=null)
			selectStatement.append(" and t.datestart<=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
            selectStatement.append(" and t.dateend>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//all deleted records cann't return to user 
		appendString("t.status <> '" + CommonConstDefinition.GENERALSTATUS_INVALIDATE + "'", selectStatement);		
		
		selectStatement.append(" order by t.id desc");     
       
		//设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}