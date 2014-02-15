/*
 * Created on 2004-8-9
 *  
 */
package com.dtv.oss.service.listhandler.system;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.dao.system.CustCampaignDAO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;

public class CustCampaignListHandler extends ValueListHandler {
	private CustCampaignDAO dao = null;
	final private String tableName = " T_CustomerCampaign cc, t_campaign c ";

	/**
	 * use DTO as a template to determine search criteria
	 */
	private CommonQueryConditionDTO dto = null;

	public CustCampaignListHandler() {
		this.dao = new CustCampaignDAO();
	}
	  
	public void setCriteria(Object dto1)  throws ListHandlerException{
		LogUtility.log(CustCampaignListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
	    if (dto1 instanceof CommonQueryConditionDTO) 
	 	     this.dto = (CommonQueryConditionDTO)dto1;
	    else {
	 	  LogUtility.log(CustCampaignListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
		  throw new ListHandlerException("the dto type is not proper...");
	    }
	  
	    //构造查询字符串
	    constructSelectQueryString(dto);
	    //执行数据查询
	    executeSearch(dao,true,true);
	}
	
	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select cc.* ");
		begin.append(" from " + tableName);
			
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where cc.campaignid = c.campaignid ");
		//区分套餐和促销
		makeSQLByStringField("c.campaigntype",dto.getSpareStr20(),selectStatement);
		
		if (!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			makeSQLByIntField("cc.CCID",Integer.parseInt(dto.getSpareStr1()),selectStatement);		
		
		if (!(dto.getSpareStr2()==null || "".equals(dto.getSpareStr2())))
			makeSQLByIntField("cc.CustomerID",Integer.parseInt(dto.getSpareStr2()),selectStatement);		
		
		if (!(dto.getSpareStr3()==null || "".equals(dto.getSpareStr3())))
			makeSQLByIntField("cc.CampaignID",Integer.parseInt(dto.getSpareStr3()),selectStatement);		
		
		if (!(dto.getSpareStr4()==null || "".equals(dto.getSpareStr4())))
			makeSQLByStringField("cc.Status",dto.getSpareStr4(),selectStatement);	
		
		if (!(dto.getSpareStr5()==null || "".equals(dto.getSpareStr5())))
			makeSQLByIntField("cc.accountID",Integer.parseInt(dto.getSpareStr5()),selectStatement);		
		
		if (!(dto.getSpareStr6()==null || "".equals(dto.getSpareStr6())))
			makeSQLByIntField("cc.serviceAccountID",Integer.parseInt(dto.getSpareStr6()),selectStatement);		
		
		if(dto.getSpareTime1()!=null)
			selectStatement.append(" and cc.DateFrom>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		if(dto.getSpareTime2()!=null)
			selectStatement.append(" and cc.DateFrom<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		if(dto.getSpareTime3()!=null)
			selectStatement.append(" and cc.DateTo>=to_timestamp('").append(dto.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		if(dto.getSpareTime4()!=null)
			selectStatement.append(" and cc.DateTo<=to_timestamp('").append(dto.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		selectStatement.append(" order by cc.CCID desc ");
		
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
}
