package com.dtv.oss.service.dao.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.QueryDTO;
import com.dtv.oss.service.dao.GenericDAO;

public class BatchDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		QueryDTO dto=null;
		
		while(rs.next()){
			dto=new QueryDTO();
			
			dto.setAccountAddress(rs.getString("ACCOUNTADDRESS"));
			dto.setAccountCashBalance1(rs.getDouble("AccountCashBalance1"));
			dto.setAccountCashBalance2(rs.getDouble("AccountCashBalance2"));
			dto.setAccountCreateTime1(rs.getTimestamp("AccountCreateTime1"));
			dto.setAccountCreateTime2(rs.getTimestamp("AccountCreateTime2"));
			dto.setAccountDistrictIdList(rs.getString("AccountDistirctIDList"));
			dto.setAccountInvoiceCreateTime1(rs.getTimestamp("AccountInvoiceCreateTime1"));
			dto.setAccountInvoiceCreateTime2(rs.getTimestamp("AccountInvoiceCreateTime2"));
			dto.setAccountInvoiceStatusList(rs.getString("AccountInvoiceStatusList"));
			dto.setAccountMopIdList(rs.getString("AccountMOPIDList"));
			dto.setAccountStatusList(rs.getString("AccountStatusList"));
			dto.setAccountTokenBalance1(rs.getDouble("AccountTokenBalance1"));
			dto.setAccountTokenBalance2(rs.getDouble("AccountTokenBalance2"));
			dto.setAccountTypeList(rs.getString("AccountTypeList"));
			dto.setCpCampaignEndTime1(rs.getTimestamp("CPCampaignEndTime1"));
			dto.setCpCampaignEndTime2(rs.getTimestamp("CPCampaignEndTime2"));
			dto.setCpCampaignIdList(rs.getString("CPCampaignIDList"));
			dto.setCpCampaignStartTime1(rs.getTimestamp("CPCampaignStartTime1"));
			dto.setCpCampaignStartTime2(rs.getTimestamp("CPCampaignStartTime2"));
			dto.setCpCreateTime1(rs.getTimestamp("CPCreateTime1"));
			dto.setCpCreateTime2(rs.getTimestamp("CPCreateTime2"));
			dto.setCpProductIdList(rs.getString("CPProductIDList"));
			dto.setCpStatusList(rs.getString("CPStatusList"));
			dto.setCreateOperatorId(rs.getInt("CreateOperatorID"));
			dto.setCreateTime(rs.getTimestamp("CreateTime"));
			dto.setCustAddress(rs.getString("CustAddress"));
			dto.setCustCreateTime1(rs.getTimestamp("CustCreateTime1"));
			dto.setCustCreateTime2(rs.getTimestamp("CustCreateTime2"));
			dto.setCustCurrentPoints1(rs.getInt("CustCurrentPoints1"));
			dto.setCustCurrentPoints2(rs.getInt("CustCurrentPoints2"));
			dto.setCustDistrictIdList(rs.getString("CustDistrictIDList"));
			dto.setCustName(rs.getString("CustName"));
			dto.setCustomerId(rs.getInt("CustomerID"));
			dto.setCustOpenSourceIdList(rs.getString("CustOpenSourceIDList"));
			dto.setCustOpenSourceTypeList(rs.getString("CustOpenSourceTypeList"));
			dto.setCustStatusList(rs.getString("CustStatusList"));
			dto.setCustTotalPoints1(rs.getInt("CustTotalPoints1"));
			dto.setCustTotalPoints2(rs.getInt("CustTotalPoints2"));
			dto.setCustTypeList(rs.getString("CustTypeList"));
			dto.setPerformTime(rs.getTimestamp("PerformTime"));
			dto.setScheduleType(rs.getString("ScheduleType"));
			dto.setStatus(rs.getString("Status"));
			dto.setQueryDesc(rs.getString("QueryDesc"));
			dto.setQueryId(rs.getInt("QueryID"));
			dto.setQueryName(rs.getString("QueryName"));
			dto.setQueryType(rs.getString("QueryType"));
			dto.setScheduleTime(rs.getTimestamp("ScheduleTime"));
			dto.setDtCreate(rs.getTimestamp("dt_create"));
			dto.setDtLastmod(rs.getTimestamp("dt_lastmod"));
			
			dto.setProductClassList(rs.getString("PRODUCTCLASSLIST"));
			dto.setBankAccountStatusList(rs.getString("BANKACCOUNTSTATUSLIST"));
			dto.setTemplateFlag(rs.getString("TemplateFlag"));		
			list.add(dto);
		}
		
		return list;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
