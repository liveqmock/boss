package com.dtv.oss.service.listhandler.groupcustomer;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.groupcustomer.ContractDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

/**
 * @author Chen Jiang 
 */

public class ContractListHandler extends ValueListHandler {
    private ContractDAO dao = null;
    final private String tableName = "t_contract a";


	private CommonQueryConditionDTO dto = null;

	public ContractListHandler() {
	  	this.dao = new ContractDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(ContractListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof CommonQueryConditionDTO) 
       	 this.dto = (CommonQueryConditionDTO)dto1;
        else {
       	LogUtility.log(ContractListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //构造查询字符串
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
        /*
         * search condition:
         * invoiceno, BarCode, acctid, custid, InvoiceSourceType, status
         */
        //合同号
        makeSQLByStringField("a.CONTRACTNO", dto.getSpareStr1(), selectStatement);
        //合同名
        makeSQLByStringField("a.name", dto.getSpareStr2(), selectStatement,"like");
        /**update chaoqiu 2007-5-11 begin
        	该查询被三个功能点共用：1子客户开户使用新合同的 新合同查询 2合同管理-->合同查询 3集团客户开户合同查询
        	“dto.getCustomerID() > 0”只可能是1子客户开户使用新合同的 新合同查询
        	
        */
        if(dto.getCustomerID() > 0){
        	/**   取消掉合同状态条件，增加有效期 和 开户数量判断（若开户数已满则不查询出该合同）
	        selectStatement.append(" and ((");
	        //合同状态
	        selectStatement.append(" a.status = '"+ CommonConstDefinition.CONTRACTSTATUS_OPEN+"'");
	  
	        //集团客户号
	        makeSQLByIntField(" a.usedcustomerid ",dto.getCustomerID(),selectStatement);
	        selectStatement.append(" ) or ");
	        selectStatement.append(" a.status = '"+ CommonConstDefinition.CONTRACTSTATUS_EFFECT+"'");
	        //super.makeSQLByStringFieldIn("a.status", CommonConstDefinition.CONTRACTSTATUS_EFFECT, selectStatement);
	        selectStatement.append(" )");
	        */
        	selectStatement.append(" and sysdate>=a.dateFrom and sysdate<=a.dateTo ");
        	selectStatement.append(" and a.usedCount<userCount ");
        	
        }
        //update chaoqiu 2007-5-11 end
        
        
        //开户截止日期的过滤,针对集团客户开户合同查询.getSpareTime1的值在webaction中设置好了
        if(dto.getSpareTime1() != null)
			selectStatement.append(" and a.normaldate >=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

        if(dto.getBeginTime()!=	null)
			selectStatement.append(" and a.dateFrom>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		if(dto.getEndTime()!= null)
			selectStatement.append(" and a.dateFrom<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		 
		if (dto.getSpareBeginTime() != null)
		   	 selectStatement.append(" and a.dateTo>=to_timestamp('").append(dto.getSpareBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		     
		if (dto.getSpareEndTime() != null)
	         selectStatement.append(" and a.dateTo<=to_timestamp('").append(dto.getSpareEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		//all deleted records cann't return to user 
	//	appendString("a.status <> '" + CommonConstDefinition.INVOICE_STATUS_CANCEL + "'", selectStatement);		
		
		selectStatement.append(" order by a.dt_create desc");  
		
		
	
       
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}