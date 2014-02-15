package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.SwapDeviceReason2StatusDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.SwapDeviceReason2StatusDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class SwapDeviceReason2StatusListHandler extends ValueListHandler {
    private SwapDeviceReason2StatusDAO dao = null;
    final private String tableName = "t_swapdevicereason2status t";


	private SwapDeviceReason2StatusDTO dto = null;

	public SwapDeviceReason2StatusListHandler() {
	  	this.dao = new SwapDeviceReason2StatusDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(SwapDeviceReason2StatusListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof SwapDeviceReason2StatusDTO) 
       	 this.dto = (SwapDeviceReason2StatusDTO)dto1;
       else {
       	LogUtility.log(CsiTypeReason2DeviceListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //�����ѯ�ַ���
       constructSelectQueryString(dto);
       //ִ�����ݲ�ѯ
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(SwapDeviceReason2StatusDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	 
        makeSQLByIntField("t.seqno",dto.getSeqNo(),selectStatement);
       
	 	
		
		selectStatement.append(" order by t.seqno desc");     
       
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}