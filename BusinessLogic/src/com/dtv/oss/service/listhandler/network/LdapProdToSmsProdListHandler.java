package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.LdapProdToSmsProdDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.LdapProdToSmsProdDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class LdapProdToSmsProdListHandler extends ValueListHandler {
    private LdapProdToSmsProdDAO dao = null;
    final private String tableName = "t_ldapprodtosmsprod t";


	private LdapProdToSmsProdDTO dto = null;

	public LdapProdToSmsProdListHandler() {
	  	this.dao = new LdapProdToSmsProdDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(LdapProdToSmsProdListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof LdapProdToSmsProdDTO) 
       	 this.dto = (LdapProdToSmsProdDTO)dto1;
       else {
       	LogUtility.log(LdapProdToSmsProdListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //构造查询字符串
       constructSelectQueryString(dto);
       //执行数据查询
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(LdapProdToSmsProdDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	 
    	 makeSQLByIntField("t.smsproductid",dto.getSmsProductId(),selectStatement); 
		
		selectStatement.append(" order by t.smsproductid asc");     
       
		//设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}