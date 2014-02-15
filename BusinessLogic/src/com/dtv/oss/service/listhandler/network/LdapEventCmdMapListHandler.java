package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.LdapEventCmdMapDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.LdapEventCmdMapDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class LdapEventCmdMapListHandler extends ValueListHandler {
    private LdapEventCmdMapDAO dao = null;
    final private String tableName = "t_LdapEventCmdMap t";


	private LdapEventCmdMapDTO dto = null;

	public LdapEventCmdMapListHandler() {
	  	this.dao = new LdapEventCmdMapDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(LdapEventCmdMapListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof LdapEventCmdMapDTO) 
       	 this.dto = (LdapEventCmdMapDTO)dto1;
       else {
       	LogUtility.log(LdapEventCmdMapListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //�����ѯ�ַ���
       constructSelectQueryString(dto);
       //ִ�����ݲ�ѯ
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(LdapEventCmdMapDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	 
    	 makeSQLByIntField("t.mapid",dto.getMapID(),selectStatement); 
		
		selectStatement.append(" order by t.mapid asc");     
       
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}