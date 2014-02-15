package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.ServiceDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.ServiceInfoDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class ServiceInfoListHandler extends ValueListHandler {
    private ServiceInfoDAO dao =null;
    private final String  tableName = "T_SERVICE a "; 
	private ServiceDTO dto =null;
	
	public ServiceInfoListHandler(){
		this.dao =new ServiceInfoDAO();
	}
	
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		LogUtility.log(ServiceInfoListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
	    if (dto1 instanceof ServiceDTO) 
	       this.dto = (ServiceDTO)dto1;
	    else {
	        LogUtility.log(ServiceInfoListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
	    }

	    //构造查询字符串
	    constructSelectQueryString(dto);
	     //执行数据查询
	     executeSearch(dao,true,true);
	}
	
	 private void constructSelectQueryString(ServiceDTO dto) {
	     StringBuffer begin = new StringBuffer();
	     begin.append("select a.* ");
	     begin.append(" from " + tableName);
	    	
	     StringBuffer selectStatement = new StringBuffer();
	     selectStatement.append(" where 1=1 ");
	     makeSQLByIntField("a.serviceid", dto.getServiceID(), selectStatement);
	     selectStatement.append(" order by a.serviceid desc");     
	       
		 //设置构造取得当前查询总纪录数的sql
		 setRecordCountQueryTable(tableName);
		 setRecordCountSuffixBuffer(selectStatement);
		 //设置当前数据查询sql
		 setRecordDataQueryBuffer(begin.append(selectStatement));
    }

}
