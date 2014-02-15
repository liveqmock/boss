package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.OpGroupDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.OpGroupDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class OpGroupListHandler extends ValueListHandler {
    private OpGroupDAO dao = null;
    final private String tableName = "t_opgroup t";


	private OpGroupDTO dto = null;

	public OpGroupListHandler() {
	  	this.dao = new OpGroupDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(OpGroupListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof OpGroupDTO) 
       	 this.dto = (OpGroupDTO)dto1;
       else {
       	LogUtility.log(OpGroupListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //�����ѯ�ַ���
       constructSelectQueryString(dto);
       //ִ�����ݲ�ѯ
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(OpGroupDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
     
    	makeSQLByIntField("t.opGroupID",dto.getOpGroupID(),selectStatement);
    	 
    	makeSQLByStringField("t.opgroupname", dto.getOpGroupName(), selectStatement, "like");
    	
    	 
         
        
	 
		selectStatement.append(" order by t.opGroupID desc");     
       
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}