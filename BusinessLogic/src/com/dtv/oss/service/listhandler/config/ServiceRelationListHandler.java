package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.ServiceDependencyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.ServiceRelationDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class ServiceRelationListHandler extends ValueListHandler {

    private ServiceRelationDAO dao =null;
    private final String  tableName = "T_SERVICEDEPENDENCY a "; 
	private ServiceDependencyDTO dto =null;
	
	public ServiceRelationListHandler(){
		this.dao =new ServiceRelationDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		LogUtility.log(ServiceRelationListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
	    if (dto1 instanceof ServiceDependencyDTO) 
	       this.dto = (ServiceDependencyDTO)dto1;
	    else {
	        LogUtility.log(ServiceRelationListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
	    }

	    //构造查询字符串
	    constructSelectQueryString(dto);
	    //执行数据查询
	    executeSearch(dao,true,true);
	}
	
	private void constructSelectQueryString(ServiceDependencyDTO dto) {
	     StringBuffer begin = new StringBuffer();
	     begin.append("select a.* ");
	     begin.append(" from " + tableName);
	    	
	     StringBuffer selectStatement = new StringBuffer();
	     selectStatement.append(" where 1=1 ");
	     makeSQLByIntField("a.serviceid", dto.getServiceId(), selectStatement);
	     selectStatement.append(" order by a.referserviceid ");     
	       
		 //设置构造取得当前查询总纪录数的sql
		 setRecordCountQueryTable(tableName);
		 setRecordCountSuffixBuffer(selectStatement);
		 //设置当前数据查询sql
		 setRecordDataQueryBuffer(begin.append(selectStatement));
    }
}
