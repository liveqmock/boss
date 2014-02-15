package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.ServiceAccountDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.CommonUtility;


public class ServiceAccountListHandler extends ValueListHandler {
    private ServiceAccountDAO dao = null;
    private String selectCriteria = "";
    final private String tableName = "T_CustomerProduct cp, T_ServiceAccount sa,T_Product pd";

    /**
     * use DTO as a template to determine
     * search criteria
     */
	private CustomerProductDTO dto = null;

	public ServiceAccountListHandler() {
	  	this.dao = new ServiceAccountDAO();
	}

      /**
       * Use setCriteria() method to check whether or not the QUERY is the same
       * @return
       */
      public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(ServiceAccountListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
          if (dto1 instanceof CustomerProductDTO) 
          	this.dto = (CustomerProductDTO)dto1;
          else {
          	LogUtility.log(ServiceAccountListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
           }
          //构造查询字符串
          constructSelectQueryString(this.dto);
          //执行数据查询
          executeSearch(dao,true,true);
      }

  	private void constructSelectQueryString(CustomerProductDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select cp.*, " + CommonUtility.getSelectExpression4ServiceAccount("sa.") + " from " + tableName);
	    
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where cp.serviceaccountid = sa.SERVICEACCOUNTID");
		selectStatement.append(" and cp.ProductID=pd.ProductID ");
		String saStatus=BusinessUtility.getServiceAccountStatusBySAID(dto.getServiceAccountID());
		if(!saStatus.equals(CommonConstDefinition.SERVICEACCOUNT_STATUS_CANCEL)){
			selectStatement.append(" and cp.status<>'" + CommonConstDefinition.SERVICEACCOUNT_STATUS_CANCEL + "'");
		}
	   	makeSQLByIntField("sa.CustomerID", dto.getCustomerID() , selectStatement);
	   	makeSQLByIntField("sa.ServiceAccountID", dto.getServiceAccountID() , selectStatement);
	   	selectStatement.append(" order by pd.ProductClass,cp.PSID"); 
	   	
	   	//
	   	
	  // 	selectStatement.append(" order by sa.serviceaccountid desc"); 
	   	//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);

		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
}
