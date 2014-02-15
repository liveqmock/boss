package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CampaignDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class CampaignListhandler extends ValueListHandler {

	private CampaignDAO dao = null;
    final private String tableName = " T_CAMPAIGN";
    
    public CampaignListhandler(){
    	this.dao=new CampaignDAO();
    }
	public void setCriteria(Object dto) throws ListHandlerException {
		
		LogUtility.log(CampaignListhandler.class,LogLevel.DEBUG,"优惠查找...");
        
		CampaignDTO dto1=(CampaignDTO)dto;
        //构造查询字符串
        constructSelectQueryString(dto1);
        //执行数据查询
        executeSearch(dao,false,false);

	}
	private void constructSelectQueryString(CampaignDTO dto) {
		
		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);
		
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");

		makeSQLByStringField("STATUS",dto.getStatus(),selectStatement);
		makeSQLByIntField("CAMPAIGNID",dto.getCampaignID(),selectStatement);
		makeSQLByStringFieldIn("CAMPAIGNTYPE",dto.getCampaignType(),selectStatement);
		makeSQLByStringField("CampaignName",dto.getCampaignName(),selectStatement,"like");
		if (dto.getDateFrom() != null) {
			selectStatement.append(" and datefrom>=to_timestamp('")
					.append(dto.getDateFrom().toString()).append(
							"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
	 
		if (dto.getDateTo() != null) {
			selectStatement.append(" and dateto<=to_timestamp('")
					.append(dto.getDateTo().toString()).append(
							"', 'YYYY-MM-DD-HH24:MI:SSxff')");

		}
		
		
		appendOrderByString(selectStatement);
		 
        
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
		
	}
	/**
	 * @param selectStatement
	 */
	private void appendOrderByString(StringBuffer selectStatement) {
		 
		selectStatement.append(" order by campainpriority " );
	}
}
