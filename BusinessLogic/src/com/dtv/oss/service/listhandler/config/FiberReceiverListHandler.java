package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.FiberReceiverDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.FiberReceiverDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class FiberReceiverListHandler extends ValueListHandler {
    private FiberReceiverDAO dao = null;
    final private String tableName = "t_FiberReceiver t";


	private FiberReceiverDTO dto = null;

	public FiberReceiverListHandler() {
	  	this.dao = new FiberReceiverDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(FiberReceiverListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof FiberReceiverDTO) 
       	 this.dto = (FiberReceiverDTO)dto1;
       else {
       	LogUtility.log(FiberReceiverListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //�����ѯ�ַ���
       constructSelectQueryString(dto);
       //ִ�����ݲ�ѯ
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(FiberReceiverDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	makeSQLByIntField("t.id",dto.getId(),selectStatement);
        makeSQLByIntField("t.FiberTransmitterId",dto.getFiberTransmitterId(),selectStatement);
        makeSQLByStringField("t.FiberReceivercode",dto.getFiberReceiverCode(),selectStatement);
        if(dto.getDistrictId()>0)
          appendStringWithDistrictID("t.districtid", dto.getDistrictId(),
				selectStatement);
       // makeSQLByIntField("t.districtid",dto.getDistrictId(),selectStatement);
        makeSQLByStringField("t.DetailAddress",dto.getDetailAddress(),selectStatement,"like");
		 	
		
		selectStatement.append(" order by t.id desc");     
       
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}