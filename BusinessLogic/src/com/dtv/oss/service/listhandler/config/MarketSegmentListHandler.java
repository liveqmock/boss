package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.MarketSegmentDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.MarketSegmentDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

/**
 * @author Chen jiang
 */

public class MarketSegmentListHandler extends ValueListHandler {
    private MarketSegmentDAO dao = null;
    final private String tableName = "t_marketsegment t";


	private MarketSegmentDTO dto = null;

	public MarketSegmentListHandler() {
	  	this.dao = new MarketSegmentDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(MarketSegmentListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof MarketSegmentDTO) 
       	 this.dto = (MarketSegmentDTO)dto1;
       else {
       	LogUtility.log(MarketSegmentListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //�����ѯ�ַ���
       constructSelectQueryString(dto);
       //ִ�����ݲ�ѯ
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(MarketSegmentDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	 
        makeSQLByIntField("t.id",dto.getId(),selectStatement);
        makeSQLByStringField("t.name",dto.getName(),selectStatement,"like");
		//appendString("t.status <> '" + CommonConstDefinition.GENERALSTATUS_INVALIDATE + "'", selectStatement);		
		
		selectStatement.append(" order by t.id desc");     
       
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}