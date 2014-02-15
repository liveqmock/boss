/*
 * Created on 2004-8-9
 *  
 */
package com.dtv.oss.service.listhandler.system;

import java.util.List;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.dao.system.CustCampaignMapDAO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;

public class CustCampaignMapListHandler extends ValueListHandler {
	private CustCampaignMapDAO dao = null;
	final private String tableName = " T_CPCampaignMap ";
    private CommonQueryConditionDTO dto = null;

	public CustCampaignMapListHandler() {
		this.dao = new CustCampaignMapDAO();
	}

	public void setCriteria(Object dto1)  throws ListHandlerException{
		LogUtility.log(CustCampaignMapListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
	    if (dto1 instanceof CommonQueryConditionDTO) 
	 	     this.dto = (CommonQueryConditionDTO)dto1;
	    else {
	 	  LogUtility.log(CustCampaignMapListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
		  throw new ListHandlerException("the dto type is not proper...");
	    }
	  
	    //构造查询字符串
	    constructSelectQueryString(dto);
	    //执行数据查询
	    executeSearch(dao,true,true);
	}
	
	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select * ");
		begin.append(" from " + tableName);
			
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		if (!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			makeSQLByIntField("CCID",Integer.parseInt(dto.getSpareStr1()),selectStatement);		

		if (!(dto.getSpareStr6()==null || "".equals(dto.getSpareStr6())))
			makeSQLByIntField("ID",Integer.parseInt(dto.getSpareStr6()),selectStatement);		
		
		selectStatement.append(" order by ID desc ");
		
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}
