package com.dtv.oss.service.listhandler.csr;
/**
 * @author 250803y david.Yang
 */

import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.PaymentRecordDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class PaymentRecordListHandler extends ValueListHandler {
	private PaymentRecordDAO dao =null;
	final private String tableName = "t_PaymentRecord a ";

	private PaymentRecordDTO dto=null;
	
	public PaymentRecordListHandler(){
		this.dao =new PaymentRecordDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		LogUtility.log(PaymentRecordListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
	    if (dto1 instanceof PaymentRecordDTO) 
	       this.dto = (PaymentRecordDTO)dto1;
	    else {
	        LogUtility.log(PaymentRecordListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
	    }

	    //构造查询字符串
	    constructSelectQueryString(dto);
	     //执行数据查询
	     executeSearch(dao,true,true);
	 }

     private void constructSelectQueryString(PaymentRecordDTO dto) {
	     StringBuffer begin = new StringBuffer();
	     begin.append("select a.* ");
	     begin.append(" from " + tableName);
	    	
	     StringBuffer selectStatement = new StringBuffer();
	     selectStatement.append(" where 1=1 ");
	     
	     makeSQLByIntField("a.invoiceno", dto.getInvoiceNo(), selectStatement);
	     makeSQLByIntField("a.AcctID", dto.getAcctID(), selectStatement);
	     makeSQLByIntField("a.CustID", dto.getCustID(), selectStatement);
	     makeSQLByIntField("a.SeqNo", dto.getSeqNo(),selectStatement);
	     makeSQLByIntField("a.SourceRecordID",dto.getSourceRecordID(),selectStatement);
	     makeSQLByIntField("a.referID",dto.getReferID(),selectStatement);
	     makeSQLByStringField("a.SourceType",dto.getSourceType(),selectStatement);
	     makeSQLByStringField("a.referType",dto.getReferType(),selectStatement);
	     
	     
	     if (dto.getDtCreate() != null)
	    	 selectStatement.append(" and a.createtime>=to_timestamp('").append(dto.getDtCreate().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
	     
	     if (dto.getDtLastmod() != null)
             selectStatement.append(" and a.createtime<=to_timestamp('").append(dto.getDtLastmod().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
    	 
	     selectStatement.append(" order by a.seqno desc");     
	       
		 //设置构造取得当前查询总纪录数的sql
		 setRecordCountQueryTable(tableName);
		 setRecordCountSuffixBuffer(selectStatement);
		 //设置当前数据查询sql
		 setRecordDataQueryBuffer(begin.append(selectStatement));
    }

}
