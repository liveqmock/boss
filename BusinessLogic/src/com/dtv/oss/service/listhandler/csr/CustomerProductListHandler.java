package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.service.dao.csr.CustomerProductDAO;
import com.dtv.oss.dto.CustomerProductDTO;

/**
 * @author 250803y david.Yang
 */
public class CustomerProductListHandler extends ValueListHandler {
    private GenericDAO dao = null;
    final private String tableName = "T_CustomerProduct a";
    private CustomerProductDTO dto = null;

	public CustomerProductListHandler() {
	  	this.dao = new CustomerProductDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
  	    LogUtility.log(CustomerProductListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
        if (dto1 instanceof CustomerProductDTO) 
      	    this.dto = (CustomerProductDTO)dto1;
        else {
      	    LogUtility.log(CsiProcessLogListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
        
        constructSelectQueryString(dto);
        //执行数据查询
        executeSearch(dao,true,true);
    }

	private void constructSelectQueryString(CustomerProductDTO dto) {
	  	StringBuffer begin = new StringBuffer();
    	begin.append("select a.* ");
    	begin.append(" from " + tableName);
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");

		makeSQLByIntField("a.psid", dto.getPsID(), selectStatement);
		makeSQLByIntField("a.customerid", dto.getCustomerID(), selectStatement);
		makeSQLByIntField("a.AccountID", dto.getAccountID(), selectStatement);
		makeSQLByIntField("a.ServiceAccountID", dto.getServiceAccountID(), selectStatement);
		makeSQLByIntField("a.ProductID", dto.getProductID(), selectStatement);
//		appendString("a.status <> '" + CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL + "'", selectStatement);
		
 		selectStatement.append(" order by a.psid desc");
		
		//appendOrderByString(end);
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));

      }
}