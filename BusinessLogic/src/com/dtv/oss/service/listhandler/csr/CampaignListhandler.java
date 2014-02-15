package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CampaignDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.util.TimestampUtility;

public class CampaignListhandler extends ValueListHandler {

	private CampaignDAO dao = null;

	final private String tableName = " T_CAMPAIGN ";

	public CampaignListhandler() {
		this.dao = new CampaignDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {

		LogUtility.log(CampaignListhandler.class, LogLevel.DEBUG, "优惠查找...");

		CampaignDTO dto1 = (CampaignDTO) dto;
		// 构造查询字符串
		constructSelectQueryString(dto1);
		// 执行数据查询
		executeSearch(dao, false, false);

	}

	private void constructSelectQueryString(CampaignDTO dto) {

		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");

		makeSQLByStringField("STATUS", "V", selectStatement);
		//这个条件专用于客户树上手工授予促销时过滤掉开户/团购促销,及没有定义NEWProduct的促销.DTO上没有可用字段,先占用着NAME
		if (dto.getCampaignName() != null && dto.getCampaignName().equals("oldProduct"))
					appendString(
							"((OBLIGATIONFLAG='N' AND campaignid in (select campaignid from t_campaignoldproductcond)) Or OBLIGATIONFLAG='Y')",
							selectStatement);
		if (dto.getCampaignName() != null && dto.getCampaignName().equals("seviceProduct"))
			appendString(
					"((OBLIGATIONFLAG='N' AND campaignid in (select campaignid from t_campaignoldproductcond)) ) ",
					selectStatement);
		if (dto.getCampaignName() != null && dto.getCampaignName().equals("accountProduct"))
			appendString(
					"((OBLIGATIONFLAG='Y' )) ",
					selectStatement);
		
		
		makeSQLByIntField("CAMPAIGNID", dto.getCampaignID(), selectStatement);

		
		makeSQLByStringFieldIn("CAMPAIGNTYPE", dto.getCampaignType(),
				selectStatement);
		
		if(dto.getCustTypeList()!=null){
		makeSQLByStringField("custtypelist", dto.getCustTypeList(),
				selectStatement, "like");
		}
		if(dto.getOpenSourceTypeList()!=null){
		makeSQLByStringField("opensourcetypelist", dto.getOpenSourceTypeList(),
				selectStatement, "like");
		}
		makeSQLByStringField("ObligationFlag", dto.getObligationFlag(),
				selectStatement);
		
		selectStatement.append(" and DATEFROM<=to_date('").append(
				TimestampUtility.TimestampToDateString(
						TimestampUtility.getCurrentDate()).toString()).append(
				"', 'YYYY-MM-DD')");

		selectStatement.append(" and DATETO>=to_date('").append(
				TimestampUtility.TimestampToDateString(
						TimestampUtility.getCurrentDate()).toString()).append(
				"', 'YYYY-MM-DD')");

		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
}
