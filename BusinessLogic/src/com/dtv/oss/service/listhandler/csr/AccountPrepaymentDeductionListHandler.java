package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.dao.csr.AccoutPrepaymentDeductionDao;
import com.dtv.oss.service.util.HelperCommonUtil;

public class AccountPrepaymentDeductionListHandler extends ValueListHandler {
     
	private AccoutPrepaymentDeductionDao dao=null;
	
	final private String tableName ="t_prepaymentdeductionrecord a ";
	
	private CommonQueryConditionDTO dto = null;
	
	public AccountPrepaymentDeductionListHandler() {
		this.dao = new AccoutPrepaymentDeductionDao();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
	    LogUtility.log(CsiProcessLogListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
	    if (dto1 instanceof CommonQueryConditionDTO) 
	        this.dto = (CommonQueryConditionDTO)dto1;
	    else {
	        LogUtility.log(CsiProcessLogListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
		    throw new ListHandlerException("the dto type is not proper...");
	    }
	        
	    constructSelectQueryString(dto);
	    //执行数据查询
	    executeSearch(dao,true,true);
	}
	
	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
	  	StringBuffer begin = new StringBuffer();
	 	begin.append("select a.* ");
    	begin.append(" from " + tableName);
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 "); 
    	makeSQLByIntField(" CUSTID" ,HelperCommonUtil.String2Int(dto.getSpareStr8()) ,selectStatement);
    	makeSQLByIntField(" ACCTID" ,HelperCommonUtil.String2Int(dto.getSpareStr9()) ,selectStatement);
    	
    	if (dto.getBeginTime()!=null)
			selectStatement.append(" and a.createtime>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getEndTime() != null)
			selectStatement.append(" and a.createtime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");  
	
		makeSQLByStringField(" INVOICEDFLAG" , dto.getSpareStr1() , selectStatement);
		makeSQLByStringField(" REFERRECORDTYPE" , dto.getSpareStr2() ,selectStatement);
		makeSQLByIntField(" REFERRECORDID" ,HelperCommonUtil.String2Int(dto.getSpareStr3()) ,selectStatement);
		makeSQLByStringField(" PREPAYMENTTYPE" , dto.getSpareStr4() ,selectStatement);
		makeSQLByStringField(" INVOICENO",dto.getSpareStr10(),selectStatement);

		appendOrderByString(selectStatement);
		//appendOrderByString(end);
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {		
		String orderByString = dto.getOrderField();
		String orderByAscend = (dto.getIsAsc()? " asc":" desc");
		
		if ((orderByString == null) ||
				orderByString.trim().equals(""))
			selectStatement.append(" order by a.seqno desc");
		else {
			selectStatement.append(" order by a." + orderByString + orderByAscend);
		}
		
	}
}
