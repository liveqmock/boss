package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.SystemSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.SystemSettingDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class SystemSettingListHandler extends ValueListHandler {
    private SystemSettingDAO dao = null;
    final private String tableName = "t_SystemSetting t";


	private SystemSettingDTO dto = null;

	public SystemSettingListHandler() {
	  	this.dao = new SystemSettingDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(SystemSettingListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof SystemSettingDTO) 
       	 this.dto = (SystemSettingDTO)dto1;
       else {
       	LogUtility.log(SystemSettingListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //�����ѯ�ַ���
       constructSelectQueryString(dto);
       //ִ�����ݲ�ѯ
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(SystemSettingDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	 
    	 makeSQLByStringField("t.name", dto.getName(), selectStatement);
    	 
    	 makeSQLByStringField("t.value", dto.getValue(), selectStatement);
    	 
    	 makeSQLByStringField("t.valuetype", dto.getValueType(), selectStatement);
    	 
    	 makeSQLByStringField("t.status", dto.getStatus(), selectStatement);
		
		 selectStatement.append(" order by t.name asc");     
       
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}