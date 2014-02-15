package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.ServiceAccountDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CustServiceAccountDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;


public class CustServiceAccountListHandler extends ValueListHandler {
    private CustServiceAccountDAO dao = null;
    private String selectCriteria = "";
    final private String tableName = "T_ServiceAccount sa";

    /**
     * use DTO as a template to determine
     * search criteria
     */
	private ServiceAccountDTO dto = null;

	public CustServiceAccountListHandler() {
	  	this.dao = new CustServiceAccountDAO();
	}

      /**
       * Use setCriteria() method to check whether or not the QUERY is the same
       * @return
       */
      public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(CustServiceAccountListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
          if (dto1 instanceof ServiceAccountDTO) 
          	this.dto = (ServiceAccountDTO)dto1;
          else {
          	LogUtility.log(CustServiceAccountListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
           }
          //构造查询字符串
          constructSelectQueryString(dto);
          //执行数据查询
          executeSearch(dao,true,true);
      }

  	private void constructSelectQueryString(ServiceAccountDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select sa.*  from " + tableName);
	    
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		/**
		if(dto.getDescription().equals("problem")) {
			selectStatement.append(" and sa.status ='" + CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT + "'");	
			} else
		selectStatement.append(" and sa.status ='" + CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL + "'");
		*/
		// modified by Chen jiang 
		if(dto.getDescription().equals("problem"))  
			selectStatement.append(" and sa.status <>'" + CommonConstDefinition.SERVICEACCOUNT_STATUS_CANCEL + "'");	
		else 
		  selectStatement.append(" and sa.status ='" + CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL + "'");	
	   	makeSQLByIntField("sa.CustomerID", dto.getCustomerID() , selectStatement);
	   	    	
	   	

	   	//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);

		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
}
