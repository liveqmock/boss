package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.LdapCommandDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.LdapCommandDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class LDAPCommandListHandler extends ValueListHandler {
    private LdapCommandDAO dao = null;
    final private String tableName = "t_ldapcommand t";


	private LdapCommandDTO dto = null;

	public LDAPCommandListHandler() {
	  	this.dao = new LdapCommandDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(LDAPCommandListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof LdapCommandDTO) 
       	 this.dto = (LdapCommandDTO)dto1;
       else {
       	LogUtility.log(LDAPCommandListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //�����ѯ�ַ���
       constructSelectQueryString(dto);
       //ִ�����ݲ�ѯ
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(LdapCommandDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	 
    	 
		
		selectStatement.append(" order by t.commandname asc");     
       
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}