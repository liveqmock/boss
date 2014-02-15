package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.MarketSegmentToDistrictDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.MarketSegmentToDistrictDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class MarketSegmentToDistrictListHandler extends ValueListHandler {
    private MarketSegmentToDistrictDAO dao = null;
    final private String tableName = "t_marketsegmenttodistrict t";


	private MarketSegmentToDistrictDTO dto = null;

	public MarketSegmentToDistrictListHandler() {
	  	this.dao = new MarketSegmentToDistrictDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(MarketSegmentToDistrictListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof MarketSegmentToDistrictDTO) 
       	 this.dto = (MarketSegmentToDistrictDTO)dto1;
       else {
       	LogUtility.log(MarketSegmentToDistrictListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //�����ѯ�ַ���
       constructSelectQueryString(dto);
       //ִ�����ݲ�ѯ
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(MarketSegmentToDistrictDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	makeSQLByIntField("t.MARKETSEGMENTID",dto.getMarketSegmentId(),selectStatement);
        makeSQLByIntField("t.DISTRICTID",dto.getDistrictId(),selectStatement);
		 	
		
		selectStatement.append(" order by t.DISTRICTID desc");     
       
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}