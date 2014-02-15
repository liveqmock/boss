package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.LdapAttrConfigDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.LdapAttrConfigDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class LdapAttrConfigListHandler extends ValueListHandler {
    private LdapAttrConfigDAO dao = null;
    final private String tableName = "t_ldapattrconfig t";


	private LdapAttrConfigDTO dto = null;

	public LdapAttrConfigListHandler() {
	  	this.dao = new LdapAttrConfigDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(LdapAttrConfigListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof LdapAttrConfigDTO) 
       	 this.dto = (LdapAttrConfigDTO)dto1;
       else {
       	LogUtility.log(LdapAttrConfigListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //�����ѯ�ַ���
       constructSelectQueryString(dto);
       //ִ�����ݲ�ѯ
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(LdapAttrConfigDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	 
    	 makeSQLByStringField("t.attrname", dto.getAttrName(), selectStatement);
		
		selectStatement.append(" order by t.attrname asc");     
       
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}