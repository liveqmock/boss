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
       //�����ѯ�ַ���
       constructSelectQueryString(dto);
       //ִ�����ݲ�ѯ
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
        //��ͬ��
        makeSQLByStringField("a.CONTRACTNO", dto.getSpareStr1(), selectStatement);
        //��ͬ��
        makeSQLByStringField("a.name", dto.getSpareStr2(), selectStatement,"like");
        /**update chaoqiu 2007-5-11 begin
        	�ò�ѯ���������ܵ㹲�ã�1�ӿͻ�����ʹ���º�ͬ�� �º�ͬ��ѯ 2��ͬ����-->��ͬ��ѯ 3���ſͻ�������ͬ��ѯ
        	��dto.getCustomerID() > 0��ֻ������1�ӿͻ�����ʹ���º�ͬ�� �º�ͬ��ѯ
        	
        */
        if(dto.getCustomerID() > 0){
        	/**   ȡ������ͬ״̬������������Ч�� �� ���������жϣ��������������򲻲�ѯ���ú�ͬ��
	        selectStatement.append(" and ((");
	        //��ͬ״̬
	        selectStatement.append(" a.status = '"+ CommonConstDefinition.CONTRACTSTATUS_OPEN+"'");
	  
	        //���ſͻ���
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
        
        
        //������ֹ���ڵĹ���,��Լ��ſͻ�������ͬ��ѯ.getSpareTime1��ֵ��webaction�����ú���
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
		
		
	
       
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}