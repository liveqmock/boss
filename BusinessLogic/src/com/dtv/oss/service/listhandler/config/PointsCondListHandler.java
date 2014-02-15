package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.UserPointsExchangeCondDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.PointsCondDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

/**
 * @author Chen jiang
 */

public class PointsCondListHandler extends ValueListHandler {
    private PointsCondDAO dao = null;
    final private String tableName = "t_userpointsexchangecond t";


	private UserPointsExchangeCondDTO dto = null;

	public PointsCondListHandler() {
	  	this.dao = new PointsCondDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(PointsCondListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof UserPointsExchangeCondDTO) 
       	 this.dto = (UserPointsExchangeCondDTO)dto1;
       else {
       	LogUtility.log(PointsRuleListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //�����ѯ�ַ���
       constructSelectQueryString(dto);
       //ִ�����ݲ�ѯ
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(UserPointsExchangeCondDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	makeSQLByIntField("t.activityId",dto.getActivityId(),selectStatement);
        makeSQLByIntField("t.condid",dto.getCondId(),selectStatement);
		//appendString("t.status <> '" + CommonConstDefinition.GENERALSTATUS_INVALIDATE + "'", selectStatement);		
		
		selectStatement.append(" order by t.condid desc");     
       
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}