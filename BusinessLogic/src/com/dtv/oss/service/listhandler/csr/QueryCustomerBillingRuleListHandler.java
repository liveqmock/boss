package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.service.dao.config.CustomerBillingRuleDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

/**
 * @author 250803y david.Yang
 */
public class QueryCustomerBillingRuleListHandler extends ValueListHandler {
    private GenericDAO dao = null;
    final private String tableName = " t_customerbillingrule custbillrule,t_customerproduct custpro ";
    private CommonQueryConditionDTO dto = null;

	public QueryCustomerBillingRuleListHandler() {
	  	this.dao = new CustomerBillingRuleDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
  	    LogUtility.log(CustomerProductListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
        if (dto1 instanceof CommonQueryConditionDTO) 
      	    this.dto = (CommonQueryConditionDTO)dto1;
        else {
      	    LogUtility.log(CsiProcessLogListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
        
        constructSelectQueryString(dto);
        //ִ�����ݲ�ѯ
        executeSearch(dao,true,true);
    }

	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
	  	StringBuffer begin = new StringBuffer();
    	begin.append("select custbillrule.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where custbillrule.psid=custpro.psid ");
		makeSQLByIntField("custpro.customerid",dto.getCustomerID(), selectStatement);
		makeSQLByStringField("custpro.ServiceAccountID",dto.getSpareStr1() , selectStatement);
		makeSQLByStringField("custbillrule.psid",dto.getSpareStr2() , selectStatement);
		makeSQLByStringField("custbillrule.psid",dto.getSpareStr3() , selectStatement);
	
		
		selectStatement.append(" and custbillrule.status <> '" + CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL + "'");
		//�ͻ���Ʒ����ʱ��
		//if (dto.getStartTime() != null)
		//	selectStatement.append(" and a.CreateTime>=to_timestamp('").append(dto.getStartTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		//if (dto.getEndTime() != null)
		//	selectStatement.append(" and a.CreateTime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
 		selectStatement.append(" order by custbillrule.id desc");
		
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));

      }
}