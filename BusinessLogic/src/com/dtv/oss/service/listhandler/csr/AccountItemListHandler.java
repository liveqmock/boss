package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.AccountItemDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author 250803y david.Yang
 */

public class AccountItemListHandler extends ValueListHandler {
	private AccountItemDAO dao =null;
	final private String tableName = "t_accountItem a ";
	
	private AccountItemDTO dto =null;
	
	public AccountItemListHandler() {
		this.dao =new AccountItemDAO();
	}
 
	public void setCriteria(Object dto1)  throws ListHandlerException{
		LogUtility.log(AccountItemListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
	    if (dto1 instanceof AccountItemDTO) 
	       this.dto = (AccountItemDTO)dto1;
	    else {
	        LogUtility.log(AccountItemListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
	    }

	    //构造查询字符串
	    constructSelectQueryString(dto);
	     //执行数据查询
	     executeSearch(dao,true,true);
	}
	
	  private void constructSelectQueryString(AccountItemDTO dto) {
		     StringBuffer begin = new StringBuffer();
		     begin.append("select a.* ");
		     begin.append(" from " + tableName);
		    	
		     StringBuffer selectStatement = new StringBuffer();
		     selectStatement.append(" where 1=1 ");
		     
		     makeSQLByIntField("a.invoiceno", dto.getInvoiceNO(), selectStatement);
		     makeSQLByIntField("a.AcctID", dto.getAcctID(), selectStatement);
		     makeSQLByIntField("a.CustID", dto.getCustID(), selectStatement);
		     makeSQLByIntField("a.AI_NO",dto.getAiNO(),selectStatement);
		     makeSQLByStringField("a.ReferType",dto.getReferType(),selectStatement);
		     makeSQLByIntField("a.ReferID",dto.getReferID(),selectStatement);
		     
		     if (dto.getDtCreate() != null)
		    	 selectStatement.append(" and a.createtime>=to_timestamp('").append(dto.getDtCreate().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		     
		     if (dto.getDtLastmod() != null)
	             selectStatement.append(" and a.createtime<=to_timestamp('").append(dto.getDtLastmod().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		     
		     selectStatement.append(" order by a.ai_no desc");     
		       
			 //设置构造取得当前查询总纪录数的sql
			 setRecordCountQueryTable(tableName);
			 setRecordCountSuffixBuffer(selectStatement);
			 //设置当前数据查询sql
			 setRecordDataQueryBuffer(begin.append(selectStatement));
	    }

}
