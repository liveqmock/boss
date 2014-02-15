package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.RoleOrganizationDTO;
import com.dtv.oss.dto.ServiceDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.RoleOrgDAO;
import com.dtv.oss.service.dao.config.ServiceInfoDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.HelperCommonUtil;

public class RoleOrgListHandler extends ValueListHandler {
    private RoleOrgDAO dao =null;
    private final String  tableName = "t_roleorganization "; 
	private RoleOrganizationDTO dto =null;
	
	public RoleOrgListHandler(){
		this.dao =new RoleOrgDAO(); 
	}
	
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		LogUtility.log(RoleOrgListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
	    if (dto1 instanceof RoleOrganizationDTO) 
	       this.dto = (RoleOrganizationDTO)dto1;
	    else {
	        LogUtility.log(RoleOrgListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
	    }

	    //构造查询字符串
	    constructSelectQueryString(dto);
	     //执行数据查询
	     executeSearch(dao,true,true);
	}
	
	 private void constructSelectQueryString(RoleOrganizationDTO roleOrgdto) {
	     StringBuffer begin = new StringBuffer();
	     begin.append("select * ");
	     begin.append(" from " + tableName);
	    	
	     StringBuffer selectStatement = new StringBuffer();
	     selectStatement.append(" where 1=1 ");
	     makeSQLByIntField(" ID ", roleOrgdto.getId(), selectStatement);
	    
	    if (dto.getDistrictId()!=0){
	    	appendStringWithDistrictID("districtid",
	    			roleOrgdto.getDistrictId(), selectStatement);
	    }
	    if(dto.getServiceOrgId()!=0)
	    	appendStringWithOrgID("SERVICEORGID",dto.getServiceOrgId(),selectStatement);
	  
	     
	     
	     if(roleOrgdto.getOrgRole()!=null)
	    	 makeSQLByStringField(" ORGROLE ", roleOrgdto.getOrgRole(), selectStatement);
	     if(roleOrgdto.getIsFirst()!=null)
	    	 makeSQLByStringField(" ISFIRST ", roleOrgdto.getIsFirst(), selectStatement);
	     
	     selectStatement.append(" order by ID desc"); 
	    	 
	        
	       
		 //设置构造取得当前查询总纪录数的sql
		 setRecordCountQueryTable(tableName);
		 setRecordCountSuffixBuffer(selectStatement);
		 //设置当前数据查询sql
		 setRecordDataQueryBuffer(begin.append(selectStatement));
    }

}
