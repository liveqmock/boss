package com.dtv.oss.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.dtv.oss.dto.*;

public class DtoFiller implements java.io.Serializable {
	public static CustomerDTO fillCustomerDTO(ResultSet rs) throws SQLException {
		return fillCustomerDTO(rs, null);
	}

	public static CustomerDTO fillCustomerDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		CustomerDTO dto = new CustomerDTO();
		dto.setCustomerID(rs.getInt(prex + "CustomerID"));

		dto.setCustomerStyle(rs.getString(prex + "CustomerStyle"));
		dto.setCustomerType(rs.getString(prex + "CustomerType"));
		dto.setMarketSegmentID(rs.getInt(prex + "MarketSegmentID"));
		dto.setGender(rs.getString(prex + "Gender"));
		dto.setTitle(rs.getString(prex + "Title"));
		dto.setName(rs.getString(prex + "Name"));
		dto.setBirthday(rs.getTimestamp(prex + "Birthday"));
		dto.setNationality(rs.getString(prex + "Nationality"));
		dto.setCardType(rs.getString(prex + "CardType"));
		dto.setContractNo(rs.getString(prex + "ContractNo"));
		dto.setCardID(rs.getString(prex + "CardID"));
		dto.setLoginID(rs.getString(prex + "LoginID"));
		dto.setLoginPwd(rs.getString(prex + "LoginPwd"));
		dto.setTelephone(rs.getString(prex + "Telephone"));
		dto.setTelephoneMobile(rs.getString(prex + "TelephoneMobile"));
		dto.setFax(rs.getString(prex + "Fax"));
		dto.setEmail(rs.getString(prex + "Email"));
		dto.setOrgID(rs.getInt(prex + "OrgID"));
		dto.setOpenSourceType(rs.getString(prex + "OpenSourceType"));
		dto.setOpenSourceTypeID(rs.getInt(prex + "OpenSourceTypeID"));
		dto.setGroupBargainID(rs.getInt(prex + "GroupBargainID"));
		dto.setAgentName(rs.getString(prex + "agentName"));
		dto.setGroupCustID(rs.getInt(prex + "groupCustID"));
		dto.setAddressID(rs.getInt(prex + "AddressID"));
		dto.setCatvID(rs.getString(prex + "CatvID"));
		dto.setCurrentAccumulatePoint(rs.getInt(prex + "CurAccumulatePoint"));
		dto.setTotalAccumulatePoint(rs.getInt(prex + "TotalAccumulatePoint"));
		dto.setStatus(rs.getString(prex + "Status"));
		dto.setComments(rs.getString(prex + "comments"));
		dto.setCustomerSerialNo(rs.getString(prex + "customerSerialNo"));
		dto.setDtCreate(rs.getTimestamp(prex + "Dt_Create"));
		dto.setDtLastmod(rs.getTimestamp(prex + "Dt_Lastmod"));
		dto.setCreateTime(rs.getTimestamp(prex + "CreateTime"));
		dto.setSocialSecCardID(rs.getString(prex + "SocialSecCardID"));
		return dto;
	}

	public static AddressDTO fillAddressDTO(ResultSet rs) throws SQLException {
		return fillAddressDTO(rs, null);
	}

	public static AddressDTO fillAddressDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		AddressDTO dto = new AddressDTO();
		dto.setAddressID(rs.getInt(prex + "ADDRESSID"));
		dto.setPostcode(rs.getString(prex + "POSTCODE"));
		dto.setDetailAddress(rs.getString(prex + "DETAILADDRESS"));
		dto.setDistrictID(rs.getInt(prex + "DISTRICTID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static CustProblemProcessDTO fillCustProblemProcessDTO(ResultSet rs)
	throws SQLException {
return fillCustProblemProcessDTO(rs, null);
}

public static CustProblemProcessDTO fillCustProblemProcessDTO(ResultSet rs,
	String prex) throws

SQLException {
	if (prex == null)
		prex = "";
	prex = prex.trim();
	
	CustProblemProcessDTO dto = new CustProblemProcessDTO();
	dto.setId(rs.getInt(prex + "ID"));
	dto.setCustProblemId(rs.getInt(prex + "CustProblemID"));
	dto.setCurrentorgId(rs.getInt(prex + "CurrentOrgID"));
	dto.setCurrentOperatorId(rs.getInt(prex + "CurrentOperatorID"));
	// dto.setAction(rs.getString(prex + "action"));
	dto.setAction(rs.getString(prex + "Action"));
	dto.setCreateDate(rs.getTimestamp(prex + "CREATEDATE"));
	dto.setWorkResult(rs.getString(prex + "WorkResult"));
	dto.setWorkResultReason(rs.getString(prex + "WorkResultReason"));
	dto.setNextOrgId(rs.getInt(prex + "NextOrgID"));
	dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
	dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
	return dto;
}

public static CustProblemProcessDTO fillCustProblemProcessDTOAndOther(
		ResultSet rs) throws SQLException {
	return fillCustProblemProcessDTOAndOther(rs, null);
}

public static CustProblemProcessDTO fillCustProblemProcessDTOAndOther(
		ResultSet rs, String prex) throws

SQLException {
	if (prex == null)
		prex = "";
	prex = prex.trim();

	CustProblemProcessDTO dto = new CustProblemProcessDTO();
	dto.setId(rs.getInt(prex + "ID"));
	dto.setCustProblemId(rs.getInt(prex + "CustProblemID"));
	dto.setCurrentorgId(rs.getInt(prex + "CurrentOrgID"));
	dto.setCurrentOperatorId(rs.getInt(prex + "CurrentOperatorID"));
	// dto.setAction(rs.getString(prex + "action"));
	dto.setAction(rs.getString(prex + "Action"));
	dto.setCreateDate(rs.getTimestamp(prex + "CREATEDATE"));
	dto.setWorkResult(rs.getString(prex + "WorkResult"));
	dto.setMemo(rs.getString(prex + "Memo"));
	//dto.setWorkResultReason(rs.getString(prex + "WorkResultReason"));
	dto.setNextOrgId(rs.getInt(prex + "NextOrgID"));
	// dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
	//dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
	return dto;
}

	public static AccountDTO fillAccountDTO(ResultSet rs) throws SQLException {
		return fillAccountDTO(rs, null);
	}

	public static AccountDTO fillAccountDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		AccountDTO dto = new AccountDTO();

		dto.setAccountID(rs.getInt(prex + "ACCOUNTID"));
		dto.setCustomerID(rs.getInt(prex + "CUSTOMERID"));
		dto.setAccountName(rs.getString(prex + "ACCOUNTNAME"));
		dto.setAccountClass(rs.getString(prex + "ACCOUNTCLASS"));
		dto.setAccountType(rs.getString(prex + "ACCOUNTTYPE"));
		dto.setInvoiceLayoutFormat(rs.getString(prex + "INVOICELAYOUTFORMAT"));
		dto.setMopID(rs.getInt(prex + "MOPID"));
		dto.setBankAccount(rs.getString(prex + "BANKACCOUNT"));

		dto.setCardType(rs.getString(prex + "CARDTYPE"));
		dto.setCardID(rs.getString(prex + "CARDID"));
		dto.setBankAccountStatus(rs.getString(prex + "BANKACCOUNTSTATUS"));
		dto.setAddressID(rs.getInt(prex + "ADDRESSID"));
		dto.setBillAddressFlag(rs.getString(prex + "BILLADDRESSFLAG"));
		dto.setCurrency(rs.getString(prex + "CURRENCY"));
		dto.setRedirectAccountID(rs.getInt(prex + "REDIRECTACCOUNTID"));
		dto.setBalance(rs.getDouble(prex + "BALANCE"));
		dto.setBbf(rs.getDouble(prex + "BBF"));
		dto.setSmallChange(rs.getDouble(prex + "SMALLCHANGE"));
		dto.setCashDeposit(rs.getDouble(prex + "CASHDEPOSIT"));
		dto.setTokenDeposit(rs.getDouble(prex + "TOKENDEPOSIT"));
		dto.setBillingCycleTypeID(rs.getInt(prex + "BILLINGCYCLETYPEID"));
		dto.setNextInvoiceDate(rs.getTimestamp(prex + "NEXTINVOICEDATE"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setRedirectType(rs.getString(prex + "REDIRECTTYPE"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setBankAccountName(rs.getString(prex + "BANKACCOUNTNAME"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setInvalidAddressReason(rs.getString(prex
						+ "INVALIDADDRESSREASON"));
		dto.setComments(rs.getString(prex+"COMMENTS"));
		return dto;
	}

	public static ServiceAccountDTO fillServiceAccountDTO(ResultSet rs)
			throws SQLException {
		return fillServiceAccountDTO(rs, null);
	}

	public static ServiceAccountDTO fillServiceAccountDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		ServiceAccountDTO dto = new ServiceAccountDTO();
		dto.setServiceAccountID(rs.getInt(prex + "SERVICEACCOUNTID"));
		dto.setServiceID(rs.getInt(prex + "SERVICEID"));
		dto.setCustomerID(rs.getInt(prex + "CUSTOMERID"));
		dto.setUserID(rs.getInt(prex + "USERID"));
		dto.setUserType(rs.getString(prex + "USER_TYPE"));
		dto.setSubscriberID(rs.getInt(prex + "SUBSCRIBERID"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setServiceCode(rs.getString(prex + "SERVICECODE"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setServiceAccountName(rs.getString(prex + "SERVICEACCOUNTNAME"));
		return dto;
	}

	public static OrganizationDTO fillOrganizationDTO(ResultSet rs)
			throws SQLException {
		return fillOrganizationDTO(rs, null);
	}

	public static OrganizationDTO fillOrganizationDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		OrganizationDTO dto = new OrganizationDTO();
		dto.setOrgID(rs.getInt(prex + "ORGID"));
		dto.setOrgName(rs.getString(prex + "ORGNAME"));
		dto.setOrgType(rs.getString(prex + "ORGTYPE"));
		dto.setOrgCode(rs.getString(prex + "ORGCODE"));
		dto.setParentOrgID(rs.getInt(prex + "PARENTORGID"));
		dto.setOrgDesc(rs.getString(prex + "ORGDESC"));
		dto.setRank(rs.getString(prex + "RANK"));
		dto.setRegisterNo(rs.getString(prex + "REGISTERNO"));
		dto.setHasCustomerFlag(rs.getString(prex + "HASCUSTOMERFLAG"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setOrgSubType(rs.getString(prex + "ORGSUBTYPE"));
		return dto;
	}

	public static OpGroupDTO fillOpGroupDTO(ResultSet rs) throws SQLException {
		return fillOpGroupDTO(rs, null);
	}

	public static OpGroupDTO fillOpGroupDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		OpGroupDTO dto = new OpGroupDTO();
		dto.setOpGroupID(rs.getInt(prex + "OPGROUPID"));
		dto.setOpGroupName(rs.getString(prex + "OPGROUPNAME"));
		dto.setOpGroupDesc(rs.getString(prex + "OPGROUPDESC"));
		dto.setOpGroupLevel(rs.getString(prex + "OPGROUPLEVEL"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setSystemFlag(rs.getString(prex + "SystemFlag"));
		return dto;
	}

	public static OrganizationDTO fillOrganizationDTO(ResultSet rs,
			OrganizationDTO dto, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		dto.setOrgID(rs.getInt(prex + "ORGID"));
		dto.setOrgName(rs.getString(prex + "ORGNAME"));
		dto.setOrgType(rs.getString(prex + "ORGTYPE"));
		dto.setOrgCode(rs.getString(prex + "ORGCODE"));
		dto.setParentOrgID(rs.getInt(prex + "PARENTORGID"));
		dto.setOrgDesc(rs.getString(prex + "ORGDESC"));
		dto.setRank(rs.getString(prex + "RANK"));
		dto.setRegisterNo(rs.getString(prex + "REGISTERNO"));
		dto.setHasCustomerFlag(rs.getString(prex + "HASCUSTOMERFLAG"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setOrgSubType(rs.getString(prex + "ORGSUBTYPE"));
		return dto;
	}

	public static AccountAdjustmentDTO fillAccountAdjustmentDTO(ResultSet rs)
			throws SQLException {
		return fillAccountAdjustmentDTO(rs, null);
	}

	public static AccountAdjustmentDTO fillAccountAdjustmentDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		AccountAdjustmentDTO dto = new AccountAdjustmentDTO();

		dto.setAcctID(rs.getInt(prex + "ACCTID"));
		dto.setAdjustmentType(rs.getString(prex + "ADJUSTMENTTYPE"));
		dto.setComments(rs.getString(prex + "COMMENTS"));
		dto.setCreateOpID(rs.getInt(prex + "CREATEOPID"));
		dto.setCreateOrgID(rs.getInt(prex + "CREATEORGID"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setCustID(rs.getInt(prex + "CUSTID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setAdjustmentDate(rs.getTimestamp(prex + "adjustmentDate"));
		dto.setReason(rs.getString(prex + "REASON"));
		dto.setReferRecordID(rs.getInt(prex + "REFERRECORDID"));
		dto.setReferRecordType(rs.getString(prex + "ReferRecordType"));
		dto.setSeqNo(rs.getInt(prex + "SEQNO"));
		dto.setStatus(rs.getString(prex + "STATUS"));

		return dto;
	}

	public static AcctItemTypeDTO fillAcctItemTypeDTO(ResultSet rs)
			throws SQLException {
		return fillAcctItemTypeDTO(rs, null);
	}

	public static AcctItemTypeDTO fillAcctItemTypeDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		AcctItemTypeDTO dto = new AcctItemTypeDTO();

		dto.setAcctItemTypeID(rs.getString(prex + "ACCTITEMTYPEID"));
		dto.setAcctItemTypeName(rs.getString(prex + "ACCTITEMTYPENAME"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setFeeType(rs.getString(prex + "FEETYPE"));
		dto.setShowLevel(rs.getString(prex + "SHOWLEVEL"));
		dto.setSpecialSetOffFlag(rs.getString(prex + "SPECIALSETOFFFLAG"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setSystemFlag(rs.getString(prex + "SystemFlag"));
		dto.setSummaryAiFlag(rs.getString(prex + "SUMMARYAIFLAG"));
		dto.setSummaryTo(rs.getString(prex + "SUMMARYTO"));
		return dto;
	}

	public static BackgroundJobDTO fillBackgroundJobDTO(ResultSet rs)
			throws SQLException {
		return fillBackgroundJobDTO(rs, null);
	}

	public static BackgroundJobDTO fillBackgroundJobDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		BackgroundJobDTO dto = new BackgroundJobDTO();

		dto.setAction(rs.getInt(prex + "ACTION"));
		dto.setActionType(rs.getInt(prex + "ACTIONTYPE"));
		dto.setBeginTime(rs.getTimestamp(prex + "BEGINTIME"));
		dto.setComments(rs.getString(prex + "COMMENTS"));
		dto.setCreateOperatorID(rs.getInt(prex + "CREATEOPERATORID"));
		dto.setDescr(rs.getString(prex + "DESCR"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setEndTime(rs.getTimestamp(prex + "ENDTIME"));
		dto.setEvent(rs.getInt(prex + "EVENT"));
		dto.setId(rs.getInt(prex + "ID"));
		dto.setJobClassName(rs.getString(prex + "JOBCLASSNAME"));
		dto.setJobGroup(rs.getString(prex + "JOBGROUP"));
		dto.setJobName(rs.getString(prex + "JOBNAME"));
		dto.setQueryCriteriaID(rs.getInt(prex + "QUERYCRITERIAID"));
		dto.setRepeatTime(rs.getInt(prex + "REPEATTIME"));
		dto.setSpanTime(rs.getLong(prex + "SPANTIME"));
		dto.setStartTime(rs.getTimestamp(prex + "STARTTIME"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		return dto;
	}

	public static CampaignDTO fillCampaignDTO(ResultSet rs) throws SQLException {
		return fillCampaignDTO(rs, null);
	}

	public static CampaignDTO fillCampaignDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		CampaignDTO dto = new CampaignDTO();

		dto.setAllowAlter(rs.getString(prex + "ALLOWALTER"));
		dto.setAllowClose(rs.getString(prex + "ALLOWCLOSE"));
		dto.setAllowPause(rs.getString(prex + "ALLOWPAUSE"));
		dto.setAllowTransfer(rs.getString(prex + "ALLOWTRANSFER"));
		dto.setAllowTransition(rs.getString(prex + "ALLOWTRANSITION"));
		dto.setCampaignID(rs.getInt(prex + "CAMPAIGNID"));
		dto.setCampaignName(rs.getString(prex + "CAMPAIGNNAME"));
		dto.setCampaignType(rs.getString(prex + "CAMPAIGNTYPE"));
		dto.setCustTypeList(rs.getString(prex + "CUSTTYPELIST"));
		dto.setDateFrom(rs.getTimestamp(prex + "DATEFROM"));
		dto.setDateTo(rs.getTimestamp(prex + "DATETO"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setKeepBilling(rs.getString(prex + "KEEPBILLING"));
		dto.setObligationFlag(rs.getString(prex + "ObligationFlag"));
		dto.setOpenSourceTypeList(rs.getString(prex + "OPENSOURCETYPELIST"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setTimeLengthUnitNumber(rs.getInt(prex + "TIMELENGTHUNITNUMBER"));
		dto.setTimeLengthUnitType(rs.getString(prex + "TIMELENGTHUNITTYPE"));
		dto.setCsiTypeList(rs.getString(prex + "CsiTypeList"));
		dto.setRfBillingFlag(rs.getString(prex + "RfBillingFlag"));
		dto.setRfBillingCycleFlag(rs.getString(prex + "RfBillingCycleFlag"));
		dto.setGroupBargainFlag(rs.getString(prex + "GroupBargainFlag"));
		dto.setAutoExtendFlag(rs.getString(prex + "AutoExtendFlag"));
		dto.setBundlePrePaymentFlag(rs.getString(prex + "BundlePrePaymentFlag"));
		dto.setPaymentAwardFlag(rs.getString(prex + "PaymentAwardFlag"));
		dto.setMultiCheckFlag(rs.getString(prex + "multiCheckFlag"));
		dto.setCampainpriority(rs.getInt(prex+"campainpriority"));
		return dto;
	}

	public static CatvTerminalDTO fillCatvTerminalDTO(ResultSet rs)
			throws SQLException {
		return fillCatvTerminalDTO(rs, null);
	}

	public static CatvTerminalDTO fillCatvTerminalDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		CatvTerminalDTO dto = new CatvTerminalDTO();
		dto.setActiveDate(rs.getTimestamp(prex + "ACTIVEDATE"));
		dto.setBiDirectionFlag(rs.getString(prex + "BIDIRECTIONFLAG"));
		dto.setCableType(rs.getString(prex + "CABLETYPE"));
		//dto.setCatvCode(rs.getString(prex + "CATVCODE"));
		dto.setConBatchID(rs.getInt(prex + "CONBATCHID"));
		dto.setDistrictID(rs.getInt(prex + "districtID"));
		dto.setCreateDate(rs.getTimestamp(prex + "CREATEDATE"));
		dto.setDetailedAddress(rs.getString(prex + "DETAILADDRESS"));
		dto.setCatvTermType(rs.getString(prex + "catvTermType"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setFiberNodeID(rs.getInt(prex + "FIBERNODEID"));
		dto.setId(rs.getString(prex + "ID"));

		dto.setPauseDate(rs.getTimestamp(prex + "PAUSEDATE"));
		dto.setCancelDate(rs.getTimestamp(prex + "CancelDate"));
		dto.setPortNo(rs.getInt(prex + "PORTNO"));
		dto.setPostcode(rs.getString(prex + "POSTCODE"));
		dto.setRecordSource(rs.getString(prex + "RECORDSOURCE"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setStatusReason(rs.getString(prex + "STATUSREASON"));
		 
		dto.setComments(rs.getString(prex + "comments")); 
		 
		return dto;
	}

	public static CPCampaignMapDTO fillCPCampaignMapDTO(ResultSet rs)
			throws SQLException {
		return fillCPCampaignMapDTO(rs, null);
	}

	public static CPCampaignMapDTO fillCPCampaignMapDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		CPCampaignMapDTO dto = new CPCampaignMapDTO();

		dto.setCcId(rs.getInt(prex + "CCID"));

		dto.setCustProductID(rs.getInt(prex + "CUSTPRODUCTID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));

		dto.setId(rs.getInt(prex + "ID"));
		dto.setStatus(rs.getString(prex + "STATUS"));

		return dto;
	}

	public static CustServiceInteractionDTO fillCustServiceInteractionDTO(
			ResultSet rs) throws SQLException {
		return fillCustServiceInteractionDTO(rs, null);
	}

	public static CustServiceInteractionDTO fillCustServiceInteractionDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		CustServiceInteractionDTO dto = new CustServiceInteractionDTO();
		dto.setAccountID(rs.getInt(prex + "ACCOUNTID"));
		dto.setAgentCardID(rs.getString(prex + "AGENTCARDID"));
		dto.setAgentCardType(rs.getString(prex + "AGENTCARDTYPE"));
		dto.setAgentName(rs.getString(prex + "AGENTNAME"));
		dto.setCallBackFlag(rs.getString(prex + "CALLBACKFLAG"));
		dto.setComments(rs.getString(prex + "COMMENTS"));
		dto.setContactPerson(rs.getString(prex + "CONTACTPERSON"));
		dto.setContactPhone(rs.getString(prex + "CONTACTPHONE"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setCustomerID(rs.getInt(prex + "CUSTOMERID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setGroupCampaignID(rs.getInt(prex + "GROUPCAMPAIGNID"));
		dto.setId(rs.getInt(prex + "ID"));
		dto.setInstallationType(rs.getString(prex + "INSTALLATIONTYPE"));
		dto.setPaymentStatus(rs.getString(prex + "PAYMENTSTATUS"));
		dto.setPreferedDate(rs.getTimestamp(prex + "PREFEREDDATE"));
		dto.setPreferedTime(rs.getString(prex + "PREFEREDTIME"));
		dto.setReferBookingSheetID(rs.getInt(prex + "REFERBOOKINGSHEETID"));
		dto.setReferSheetID(rs.getString(prex + "REFERSHEETID"));
		dto.setReferJobCardID(rs.getInt(prex + "REFERJOBCARDID"));
		dto.setScheduleAction(rs.getString(prex + "SCHEDULEACTION"));
		dto.setScheduleTime(rs.getTimestamp(prex + "SCHEDULETIME"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setStatusReason(rs.getString(prex + "STATUSREASON"));
		dto.setCreateOperatorId(rs.getInt(prex + "CreateOperatorId"));
		dto.setCallBackDate(rs.getTimestamp(prex + "CALLBACKDATE"));
		dto.setType(rs.getString(prex + "TYPE"));
		dto.setServiceCodeList(rs.getString(prex + "ServiceCodeList"));
		dto.setBillCollectionMethod(rs.getString(prex + "BillCollectionMethod"));
		dto.setCreateReason(rs.getString(prex+"CREATEREASON"));
		dto.setWalletId(rs.getInt(prex+"walletId"));
		dto.setPoint(rs.getInt(prex+"point"));
		dto.setValue(rs.getBigDecimal(prex+"value"));
		dto.setAgentTelephone(rs.getString(prex+"agentTelephone"));
		dto.setWorkDate(rs.getTimestamp(prex+"workDate"));
		return dto;
	}

	public static JobCardDTO fillJobCardDTO(ResultSet rs) throws SQLException {
		return fillJobCardDTO(rs, null);
	}

	public static JobCardDTO fillJobCardDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		JobCardDTO dto = new JobCardDTO();
		dto.setAddressId(rs.getInt(prex + "ADDRESSID"));
		dto.setContactPerson(rs.getString(prex + "CONTACTPERSON"));
		dto.setContactPhone(rs.getString(prex + "CONTACTPHONE"));
		dto.setCustId(rs.getInt(prex + "CUSTID"));
		dto.setCustServiceAccountId(rs.getInt(prex + "CUSTSERVICEACCOUNTID"));
		dto.setProcessOrgId(rs.getInt(prex + "PROCESSORGID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setDueDate(rs.getTimestamp(prex + "DUEDATE"));
		dto.setFirstWorkDate(rs.getTimestamp(prex + "FIRSTWORKDATE"));
		dto.setTroubleType(rs.getString(prex + "TROUBLETYPE"));
		dto.setTroubleSubType(rs.getString(prex + "TROUBLESUBTYPE"));
		dto.setResolutionType(rs.getString(prex + "RESOLUTIONTYPE"));
		dto.setJobCardId(rs.getInt(prex + "JOBCARDID"));
		dto.setJobType(rs.getString(prex + "JOBTYPE"));
		dto.setReferSheetId(rs.getInt(prex + "REFERSHEETID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setWorkCount(rs.getInt(prex + "WORKCOUNT"));
		dto.setPreferedDate(rs.getTimestamp(prex + "PREFEREDDATE"));
		dto.setPreferedTime(rs.getString(prex + "PREFEREDTIME"));
		dto.setWorkDate(rs.getTimestamp(prex + "WORKDATE"));
		dto.setWorkResult(rs.getString(prex + "WORKRESULT"));
		dto.setWorkTime(rs.getString(prex + "WORKTIME"));
		dto.setOutOfDateReason(rs.getString(prex + "OUTOFDATEREASON"));
		dto.setWorkResultReason(rs.getString(prex + "WORKRESULTREASON"));
		dto.setAddPortNumber(rs.getInt(prex + "ADDPORTNUMBER"));
		dto.setOldPortNumber(rs.getInt(prex + "OLDPORTNUMBER"));
		dto.setAccountID(rs.getInt(prex + "ACCOUNTID"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setSubType(rs.getString(prex + "SUBTYPE"));
		dto.setCatvID(rs.getString(prex + "CATVID"));
		dto.setReferType(rs.getString(prex + "REFERTYPE"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setCreateOpID(rs.getInt(prex + "CREATEOPID"));
		dto.setCreateOrgID(rs.getInt(prex + "CreateOrgID"));
		dto.setCreateMethod(rs.getString(prex + "CREATEMETHOD"));
		dto.setManualCreateReason(rs.getString(prex + "MANUALCREATEREASON"));
		dto.setPayStatus(rs.getString(prex + "PAYSTATUS"));
		dto.setPayDate(rs.getTimestamp(prex + "PAYDATE"));
		dto.setStatusReasonDate(rs.getTimestamp(prex + "STATUSREASONDATE"));
		dto.setOldAddressId(rs.getInt(prex + "OLDADDRESSID"));
		
		return dto;
	}

	public static PaymentRecordDTO fillPaymentRecordDTO(ResultSet rs)
			throws SQLException {
		return fillPaymentRecordDTO(rs, null);
	}

	public static PaymentRecordDTO fillPaymentRecordDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		PaymentRecordDTO dto = new PaymentRecordDTO();
		dto.setAcctID(rs.getInt(prex + "ACCTID"));
		dto.setAmount(rs.getDouble(prex + "AMOUNT"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setCustID(rs.getInt(prex + "CUSTID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setInvoicedFlag(rs.getString(prex + "INVOICEDFLAG"));
		dto.setInvoiceNo(rs.getInt(prex + "INVOICENO"));
		dto.setMopID(rs.getInt(prex + "MOPID"));
		dto.setOpID(rs.getInt(prex + "OPID"));
		dto.setOrgID(rs.getInt(prex + "ORGID"));
		dto.setPaidTo(rs.getInt(prex + "PAIDTO"));
		dto.setPaymentTime(rs.getTimestamp(prex + "PAYMENTTIME"));
		dto.setPayType(rs.getString(prex + "PAYTYPE"));
		dto.setPrepaymentType(rs.getString(prex + "PREPAYMENTTYPE"));
		dto.setSeqNo(rs.getInt(prex + "SEQNO"));
		dto.setSourceRecordID(rs.getInt(prex + "SOURCERECORDID"));
		dto.setSourceType(rs.getString(prex + "SOURCETYPE"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setTicketNo(rs.getString(prex + "TICKETNO"));
		dto.setTicketType(rs.getString(prex + "TICKETTYPE"));
		dto.setReferID(rs.getInt(prex + "REFERID"));
		dto.setReferType(rs.getString(prex + "REFERTYPE"));
		dto.setComments(rs.getString(prex + "Comments"));
		dto.setAdjustmentFlag(rs.getString(prex + "AdjustmentFlag"));
		dto.setAdjustmentNo(rs.getInt(prex + "AdjustmentNo"));
		dto.setCreatingMethod(rs.getString(prex + "CreatingMethod"));
		dto.setFaPiaoNo(rs.getString(prex + "FaPiaoNo"));
		return dto;
	}

	public static AccountItemDTO fillAccountItemDTO(ResultSet rs)
			throws SQLException {
		return fillAccountItemDTO(rs, null);
	}

	public static AccountItemDTO fillAccountItemDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		AccountItemDTO dto = new AccountItemDTO();
		dto.setAcctID(rs.getInt(prex + "ACCTID"));
		dto.setAcctItemTypeID(rs.getString(prex + "ACCTITEMTYPEID"));
		dto.setAiNO(rs.getInt(prex + "AI_NO"));
		dto.setBatchNO(rs.getInt(prex + "BATCHNO"));
		dto.setBillingCycleID(rs.getInt(prex + "BILLINGCYCLEID"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setCustID(rs.getInt(prex + "CUSTID"));
		dto.setDate1(rs.getTimestamp(prex + "DATE1"));
		dto.setDate2(rs.getTimestamp(prex + "DATE2"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setForcedDepositFlag(rs.getString(prex + "FORCEDDEPOSITFLAG"));
		dto.setInvoiceFlag(rs.getString(prex + "INVOICEDFLAG"));
		dto.setInvoiceNO(rs.getInt(prex + "INVOICENO"));
		dto.setOrgID(rs.getInt(prex + "ORGID"));
		dto.setOperatorID(rs.getInt(prex + "OpID"));
		dto.setPsID(rs.getInt(prex + "PSID"));
		dto.setBrID(rs.getInt(prex + "BRID"));
		dto.setReferID(rs.getInt(prex + "REFERID"));
		dto.setReferType(rs.getString(prex + "REFERTYPE"));
		dto.setServiceAccountID(rs.getInt(prex + "SERVICEACCOUNTID"));
		dto.setSetOffAmount(rs.getDouble(prex + "SETOFFAMOUNT"));
		dto.setSetOffFlag(rs.getString(prex + "SETOFFFLAG"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setFeeType(rs.getString(prex + "FEETYPE"));
		dto.setValue(rs.getDouble(prex + "VALUE"));
		dto.setSourceRecordID(rs.getInt(prex+"SOURCERECORDID"));
		dto.setCreatingMethod(rs.getString(prex + "CREATINGMETHOD"));
		dto.setAdjustmentNo(rs.getInt(prex+"AdjustmentNo"));
		dto.setAdjustmentFlag(rs.getString(prex + "AdjustmentFlag"));
		dto.setComments(rs.getString(prex+"COMMENTS"));
		dto.setCcID(rs.getInt(prex+"CcID"));
		dto.setFeeSplitPlanID(rs.getInt(prex+"FeeSplitPlanID"));
		dto.setRfBillingCycleFlag(rs.getString(prex+"RfBillingCycleFlag"));
        //20140222 fapiao
        dto.setFapiaoSerialNo(rs.getString(prex+"FapiaoSerialNo"));
        dto.setFapiaoHaoma(rs.getString(prex+"FapiaoHaoma"));
		return dto;
	}

	public static CustomerBillingRuleDTO fillCustomerBillingRuleDTO(ResultSet rs)
			throws SQLException {
		return fillCustomerBillingRuleDTO(rs, null);
	}

	public static CustomerBillingRuleDTO fillCustomerBillingRuleDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		CustomerBillingRuleDTO dto = new CustomerBillingRuleDTO();
		dto.setAcctItemTypeID(rs.getString(prex + "ACCTITEMTYPEID"));
		dto.setContractNo(rs.getString(prex + "CONTRACTNO"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setEventClass(rs.getInt(prex + "EVENTCLASS"));
		dto.setEventReason(rs.getString(prex + "EVENTREASON"));
		dto.setId(rs.getInt(prex + "ID"));
		dto.setPsID(rs.getInt(prex + "PSID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setStartDate(rs.getTimestamp(prex + "STARTDATE"));
		dto.setEndDate(rs.getTimestamp(prex + "ENDDATE"));
		dto.setValue(rs.getDouble(prex + "VALUE"));
		dto.setBrRateType(rs.getString(prex + "brRateType"));
		dto.setCcID(rs.getInt(prex + "ccID"));
		dto.setReferID(rs.getInt(prex + "referID"));
		dto.setCustPackageID(rs.getInt(prex + "custPackageID"));
		dto.setReferType(rs.getString(prex + "referType"));
		dto.setRfBillingCycleFlag(rs.getString(prex + "rfBillingCycleFlag")); 
		dto.setFeeType(rs.getString(prex + "FEETYPE"));
		dto.setComments(rs.getString(prex + "comments"));
		return dto;
	}

	public static BillingRuleItemDTO fillBillingRuleItemDTO(ResultSet rs)
			throws SQLException {
		return fillBillingRuleItemDTO(rs, null);
	}

	public static BillingRuleItemDTO fillBillingRuleItemDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		BillingRuleItemDTO dto = new BillingRuleItemDTO();
		dto.setFeeType(rs.getString(prex + "FEETYPE"));
		dto.setAcctItemTypeId(rs.getString(prex + "ACCTITEMTYPEID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setEventClass(rs.getInt(prex + "EVENTCLASS"));
		dto.setEventReason(rs.getString(prex + "EVENTREASON"));
		dto.setId(rs.getInt(prex + "ID"));
		dto.setFormulaId(rs.getInt(prex + "FORMULAID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setValidFrom(rs.getTimestamp(prex + "VALIDFROM"));
		dto.setValidTo(rs.getTimestamp(prex + "VALIDTO"));
		dto.setValue(rs.getInt(prex + "VALUE"));
		dto.setCustType(rs.getString(prex + "CUSTTYPE"));
		dto.setUseFormulaFlag(rs.getString(prex + "USERFORMULAFLAG"));
		dto.setPackageId(rs.getInt(prex + "PACKAGEID"));
		dto.setCampaignId(rs.getInt(prex + "CAMPAIGNID"));
		dto.setBrId(rs.getInt(prex + "BRID"));
		dto.setProductId(rs.getInt(prex + "PRODUCTID"));
		dto.setDestProductId(rs.getInt(prex + "DESTPRODUCTID"));
		dto.setMarketSegmentID(rs.getInt(prex + "MARKETSEGMENTID"));
		dto.setUserType(rs.getInt(prex + "USERTYPE"));
		dto.setPriority(rs.getInt(prex + "PRIORITY"));
		dto.setBrRateType(rs.getString(prex + "BRRATETYPE"));
		dto.setFeeSplitPlanID(rs.getInt(prex+"FeeSplitPlanID"));
		dto.setAcctType(rs.getString(prex + "ACCTTYPE"));
		dto.setCatvTermType(rs.getString(prex + "catvTermType"));
		dto.setCableType(rs.getString(prex + "cableType"));
		dto.setBiDrectionFlag(rs.getString(prex + "biDrectionFlag"));
		dto.setOldPortNo(rs.getInt(prex + "oldPortNo"));
		dto.setNewPortNo(rs.getInt(prex + "newPortNo"));
		return dto;
	}

	public static CustomerProductDTO fillCustomerProductDTO(ResultSet rs)
			throws SQLException {
		return fillCustomerProductDTO(rs, null);
	}

	public static CustomerProductDTO fillCustomerProductDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		CustomerProductDTO dto = new CustomerProductDTO();
		dto.setAccountID(rs.getInt(prex + "ACCOUNTID"));
		dto.setActivateTime(rs.getTimestamp(prex + "ACTIVATETIME"));
		dto.setCancelTime(rs.getTimestamp(prex + "CANCELTIME"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setCustomerID(rs.getInt(prex + "CUSTOMERID"));
		dto.setDeviceID(rs.getInt(prex + "DEVICEID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setPauseTime(rs.getTimestamp(prex + "PAUSETIME"));
		dto.setProductID(rs.getInt(prex + "PRODUCTID"));
		dto.setPsID(rs.getInt(prex + "PSID"));
		dto.setReferPackageID(rs.getInt(prex + "REFERPACKAGEID"));
		dto.setServiceAccountID(rs.getInt(prex + "SERVICEACCOUNTID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setStatusReason(rs.getString(prex + "STATUSREASON"));
		dto.setLinkToDevice1(rs.getInt(prex + "LINKTODEVICE1"));
		dto.setLinkToDevice2(rs.getInt(prex + "LINKTODEVICE2"));

		dto.setFinanceOption(rs.getString(prex + "FinanceOption"));
		dto.setStartTime(rs.getTimestamp(prex + "StartTime"));
		dto.setEndTime(rs.getTimestamp(prex + "EndTime"));
		dto.setDeviceProvideFlag(rs.getString(prex + "DeviceProvideFlag"));
		return dto;
	}

	public static NewCustomerInfoDTO fillNewCustomerInfoDTO(ResultSet rs)
			throws SQLException {
		return fillNewCustomerInfoDTO(rs, null);
	}

	public static NewCustomerInfoDTO fillNewCustomerInfoDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		NewCustomerInfoDTO dto = new NewCustomerInfoDTO();
		dto.setCardID(rs.getString(prex + "CARDID"));
		dto.setCardType(rs.getString(prex + "CARDTYPE"));
		dto.setCatvID(rs.getString(prex + "CATVID"));
		dto.setContractNo(rs.getString(prex + "CONTRACTNO"));
		dto.setCsiID(rs.getInt(prex + "CSIID"));
		dto.setCustID(rs.getInt(prex + "CUSTID"));
		dto.setDigitalCatvID(rs.getString(prex + "DIGITALCATVID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setEmail(rs.getString(prex + "EMAIL"));
		dto.setFaxNumber(rs.getString(prex + "FAXNUMBER"));
		dto.setMarketSegmentID(rs.getInt(prex + "MARKETSEGMENTID"));
		dto.setFromAddressID(rs.getInt(prex + "FROMADDRESSID"));
		dto.setGender(rs.getString(prex + "GENDER"));
		dto.setGroupBargainID(rs.getInt(prex + "GROUPBARGAINID"));
		dto.setId(rs.getInt(prex + "ID"));
		dto.setMobileNumber(rs.getString(prex + "MOBILENUMBER"));
		dto.setName(rs.getString(prex + "NAME"));
		dto.setNationality(rs.getString(prex + "NATIONALITY"));
		dto.setOpenSourceID(rs.getInt(prex + "OPENSOURCEID"));
		dto.setOpenSourceType(rs.getString(prex + "OPENSOURCETYPE"));
		dto.setTimeRangeStart(rs.getTimestamp(prex + "TIMERANGESTART"));
		dto.setSocialSecCardID(rs.getString(prex + "SOCIALSECCARDID"));
		dto.setTelephone(rs.getString(prex + "TELEPHONE"));
		dto.setTimeRangeEnd(rs.getTimestamp(prex + "TIMERANGEEND"));
		dto.setToAddressID(rs.getInt(prex + "TOADDRESSID"));
		dto.setType(rs.getString(prex + "TYPE"));
		dto.setBirthday(rs.getTimestamp(prex + "BIRTHDAY"));
		dto.setAgentName(rs.getString(prex + "agentName"));
		dto.setGroupCustID(rs.getInt(prex + "groupCustID"));
		dto.setCustStyle(rs.getString(prex + "custStyle"));
		dto.setLoginID(rs.getString(prex + "loginID"));
		dto.setComments(rs.getString(prex + "Comments"));
		dto.setCustomerSerialNo(rs.getString(prex + "customerSerialNo"));
		dto.setLoginPWD(rs.getString(prex + "LoginPWD"));
		return dto;
	}

	public static OldCustomerInfoDTO fillOldCustomerInfoDTO(ResultSet rs)
	throws SQLException {
       return fillOldCustomerInfoDTO(rs, null);
    }

    public static OldCustomerInfoDTO fillOldCustomerInfoDTO(ResultSet rs,String prex)
    throws SQLException {
        if (prex == null)
	    prex = "";
        prex = prex.trim();

        OldCustomerInfoDTO dto = new OldCustomerInfoDTO();
        dto.setCardID(rs.getString(prex + "CARDID"));
        dto.setCardType(rs.getString(prex + "CARDTYPE"));
        dto.setCatvID(rs.getString(prex + "CATVID"));
        dto.setContractNo(rs.getString(prex + "CONTRACTNO"));
        dto.setCsiID(rs.getInt(prex + "CSIID"));
        dto.setCustID(rs.getInt(prex + "CUSTID"));
        dto.setDigitalCatvID(rs.getString(prex + "DIGITALCATVID"));
        dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
        dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
        dto.setEmail(rs.getString(prex + "EMAIL"));
        dto.setFaxNumber(rs.getString(prex + "FAXNUMBER"));
        dto.setAddressID(rs.getInt(prex + "ADDRESSID"));
        dto.setGender(rs.getString(prex + "GENDER"));
        dto.setGroupBargainID(rs.getInt(prex + "GROUPBARGAINID"));
        dto.setId(rs.getInt(prex + "ID"));
        dto.setMobileNumber(rs.getString(prex + "MOBILENUMBER"));
        dto.setName(rs.getString(prex + "NAME"));
        dto.setNationality(rs.getString(prex + "NATIONALITY"));
        dto.setOpenSourceID(rs.getInt(prex + "OPENSOURCEID"));
        dto.setOpenSourceType(rs.getString(prex + "OPENSOURCETYPE"));
        dto.setTimeRangeStart(rs.getTimestamp(prex + "TIMERANGESTART"));
        dto.setSocialSeccardID(rs.getString(prex + "SOCIALSECCARDID"));
        dto.setTelephone(rs.getString(prex + "TELEPHONE"));
        dto.setTimeRangeEnd(rs.getTimestamp(prex + "TIMERANGEEND"));
        dto.setType(rs.getString(prex + "TYPE"));
        dto.setCustomerSerialNo(rs.getString(prex + "customerSerialNo"));
        dto.setComments(rs.getString(prex + "Comments"));
        dto.setBirthday(rs.getTimestamp(prex + "birthday"));
        dto.setLoginID(rs.getString(prex + "loginID"));
        dto.setLoginPwd(rs.getString(prex + "loginPwd"));
        return dto;
}


	public static NewCustAccountInfoDTO fillNewCustAccountInfoDTO(ResultSet rs)
		throws SQLException {
		return fillNewCustAccountInfoDTO(rs, null);
	}

	public static NewCustAccountInfoDTO fillNewCustAccountInfoDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		NewCustAccountInfoDTO dto = new NewCustAccountInfoDTO();
		dto.setAccountID(rs.getInt(prex + "ACCOUNTID"));
		dto.setAccountType(rs.getString(prex + "ACCOUNTTYPE"));
		dto.setAddressFlag(rs.getString(prex + "ADDRESSFLAG"));
		dto.setAddressID(rs.getInt(prex + "ADDRESSID"));
		dto.setBankAccount(rs.getString(prex + "BANKACCOUNT"));
		dto.setBankAccountName(rs.getString(prex + "BANKACCOUNTNAME"));
		dto.setCsiID(rs.getInt(prex + "CSIID"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setId(rs.getInt(prex + "ID"));
		dto.setMopID(rs.getInt(prex + "MOPID"));
		dto.setAccountName(rs.getString(prex + "ACCOUNTNAME"));
		dto.setComments(rs.getString("comments"));

		return dto;
	}

    public static OldCustAccountInfoDTO fillOldCustAccountInfoDTO(ResultSet rs)
	   throws SQLException {
       return fillOldCustAccountInfoDTO(rs, null);
    }

    public static OldCustAccountInfoDTO fillOldCustAccountInfoDTO(ResultSet rs,
	    String prex) throws SQLException {
        if (prex == null)
	    prex = "";
        prex = prex.trim();

        OldCustAccountInfoDTO dto = new OldCustAccountInfoDTO();
        dto.setAccountID(rs.getInt(prex + "ACCOUNTID"));
        dto.setAccountType(rs.getString(prex + "ACCOUNTTYPE"));
        dto.setAddressFlag(rs.getString(prex + "ADDRESSFLAG"));
        dto.setAddressID(rs.getInt(prex + "ADDRESSID"));
        dto.setBankAccount(rs.getString(prex + "BANKACCOUNT"));
        dto.setBankAccountName(rs.getString(prex + "BANKACCOUNTNAME"));
        dto.setCsiID(rs.getInt(prex + "CSIID"));
        dto.setDescription(rs.getString(prex + "DESCRIPTION"));
        dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
        dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
        dto.setID(rs.getInt(prex + "ID"));
        dto.setMopID(rs.getInt(prex + "MOPID"));
        dto.setAccountName(rs.getString(prex + "ACCOUNTNAME"));
        dto.setComments(rs.getString("comments"));

        return dto;
    }

	public static ForcedDepositDTO fillForcedDepositDTO(ResultSet rs)
			throws SQLException {
		return fillForcedDepositDTO(rs, null);
	}

	public static ForcedDepositDTO fillForcedDepositDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		ForcedDepositDTO dto = new ForcedDepositDTO();
		dto.setCreateOperator(rs.getInt(prex + "CREATEOPERATOR"));
		dto.setCreateorgID(rs.getInt(prex + "CREATEORGID"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setCsiID(rs.getInt(prex + "CSIID"));
		dto.setCustomerID(rs.getInt(prex + "CUSTOMERID"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setFromDate(rs.getTimestamp(prex + "FROMDATE"));
		dto.setMoney(rs.getDouble(prex + "MONEY"));
		dto.setProcessOperatorID(rs.getInt(prex + "PROCESSOPERATORID"));
		dto.setProcessOrgID(rs.getInt(prex + "PROCESSORGID"));
		dto.setProcessTime(rs.getTimestamp(prex + "PROCESSTIME"));
		dto.setRefersheetID(rs.getString(prex + "REFERSHEETID"));
		dto.setSeizureMoney(rs.getDouble(prex + "SEIZUREMONEY"));
		// dto.setSeizureorgID(rs.getInt(prex +""));
		dto.setSeqNo(rs.getInt(prex + "SEQNO"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setToDate(rs.getTimestamp(prex + "TODATE"));
		dto.setUserID(rs.getInt(prex + "USERID"));
		dto.setWithdrawMoney(rs.getDouble(prex + "WITHDRAWMONEY"));
		return dto;
	}

	public static FinanceSetOffMapDTO fillFinanceSetOffMapDTO(ResultSet rs)
			throws SQLException {
		return fillFinanceSetOffMapDTO(rs, null);
	}

	public static FinanceSetOffMapDTO fillFinanceSetOffMapDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		FinanceSetOffMapDTO dto = new FinanceSetOffMapDTO();
		dto.setAcctId(rs.getInt(prex + "ACCTID"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setCustId(rs.getInt(prex + "CUSTID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setMinusReferId(rs.getInt(prex + "MINUSREFERID"));
		dto.setMinusReferType(rs.getString(prex + "MINUSREFERTYPE"));
		dto.setOpId(rs.getInt(prex + "OPID"));
		dto.setOrgId(rs.getInt(prex + "ORGID"));
		dto.setPlusReferId(rs.getInt(prex + "PLUSREFERID"));
		dto.setPlusReferType(rs.getString(prex + "PLUSREFERTYPE"));
		dto.setSeqNo(rs.getInt(prex + "SEQNO"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setComments(rs.getString(prex + "Comments"));
		dto.setSType(rs.getString(prex + "STYPE"));
		dto.setValue(rs.getDouble(prex + "VALUE"));

		return dto;
	}

	public static InvoiceDTO fillInvoiceDTO(ResultSet rs) throws SQLException {
		return fillInvoiceDTO(rs, null);
	}

	public static InvoiceDTO fillInvoiceDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		InvoiceDTO dto = new InvoiceDTO();
		dto.setAcctID(rs.getInt(prex + "ACCTID"));
		dto.setAmount(rs.getDouble(prex + "AMOUNT"));
		dto.setBarCode(rs.getString(prex + "BARCODE"));
		dto.setBcf(rs.getDouble(prex + "BCF"));
		dto.setComments(rs.getString(prex + "Comments"));
		dto.setCreateDate(rs.getTimestamp(prex + "CREATEDATE"));
		dto.setCreateOpId(rs.getInt(prex + "CREATEOPID"));
		dto.setCustID(rs.getInt(prex + "CUSTID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setDueDate(rs.getTimestamp(prex + "DUEDATE"));
		dto.setInvoiceCycleId(rs.getInt(prex + "INVOICECYCLEID"));
		dto.setInvoiceNo(rs.getInt(prex + "INVOICENO"));
		dto.setInvoiceSourceType(rs.getString(prex + "INVOICESOURCETYPE"));

		dto.setPayAmount(rs.getDouble(prex + "PAYAMOUNT"));
		dto.setPayDate(rs.getTimestamp(prex + "PAYDATE"));
		dto.setPayOpId(rs.getInt(prex + "PAYOPID"));
		dto.setPayOrgId(rs.getInt(prex + "PAYORGID"));

		dto.setPunishment(rs.getDouble(prex + "PUNISHMENT"));
		dto.setReferenceNo(rs.getInt(prex + "REFERENCENO"));
		dto.setSmallChange(rs.getDouble(prex + "SMALLCHANGE"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setCreateOrgId(rs.getInt(prex + "CREATEORGID"));
		dto.setBatchNo(rs.getInt(prex + "BATCHNO"));
		dto.setFeeTotal(rs.getDouble(prex + "FEETOTAL"));
		dto.setPaymentTotal(rs.getDouble(prex + "PAYMENTTOTAL"));
		dto.setPrepaymentTotal(rs.getDouble(prex + "PREPAYMENTTOTAL"));
		dto.setPrepaymentDeductionTotal(rs.getDouble(prex
				+ "PREPAYMENTDEDUCTIONTOTAL"));
		return dto;
	}

	public static ProductDTO fillProductDTO(ResultSet rs) throws SQLException {
		return fillProductDTO(rs, null);
	}

	public static ProductDTO fillProductDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		ProductDTO dto = new ProductDTO();

		dto.setDateFrom(rs.getTimestamp(prex + "DATEFROM"));
		dto.setDateTo(rs.getTimestamp(prex + "DATETO"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setNewsaFlag(rs.getString(prex + "NEWSAFLAG"));
		dto.setProductClass(rs.getString(prex + "PRODUCTCLASS"));
		dto.setProductID(rs.getInt(prex + "PRODUCTID"));
		dto.setProductName(rs.getString(prex + "PRODUCTNAME"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		return dto;
	}

	// add by zhouxushun for PackageLine ,DATE: 2005-10-10 15:48
	public static PackageLineDTO fillPackageLineDTO(ResultSet rs)
			throws SQLException {
		return fillPackageLineDTO(rs, null);
	}

	public static PackageLineDTO fillPackageLineDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		PackageLineDTO dto = new PackageLineDTO();
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setPackageId(rs.getInt(prex + "PACKAGEID"));
		dto.setProductId(rs.getInt(prex + "PRODUCTID"));
		dto.setProductNum(rs.getInt(prex + "PRODUCTNUM"));
		dto.setOptionFlag(rs.getString(prex + "OPTIONFLAG"));
		return dto;
	}

	public static ProductPackageDTO fillProductPackageDTO(ResultSet rs)
			throws SQLException {
		return fillProductPackageDTO(rs, null);
	}

	public static ProductPackageDTO fillProductPackageDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		ProductPackageDTO dto = new ProductPackageDTO();
		dto.setDateFrom(rs.getTimestamp(prex + "DATEFROM"));
		dto.setDateTo(rs.getTimestamp(prex + "DATETO"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setPackageID(rs.getInt(prex + "PACKAGEID"));
		dto.setPackagePriority(rs.getInt(prex + "PACKAGEPRIORITY"));
		dto.setPackageName(rs.getString(prex + "PACKAGENAME"));
		dto.setPackageClass(rs.getString(prex + "PACKAGECLASS"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setMaxSelProductNum(rs.getInt(prex + "MAXSELPRODUCTNUM"));
		dto.setMinSelProductNum(rs.getInt(prex + "MINSELPRODUCTNUM"));
		dto.setGrade(rs.getInt(prex + "GRADE"));
		dto.setHasOptionProductFlag(rs.getString(prex
						+ "HASOPTIONPRODUCTFLAG"));
		dto.setCaptureFlag(rs.getString(prex + "CaptureFlag"));
		dto.setCsiTypeList(rs.getString(prex + "CsiTypeList"));
		dto.setCustTypeList(rs.getString(prex+"CustTypeList"));
		return dto;
	}

	public static ContractToPackageDTO fillContractToPackageDTO(ResultSet rs)
			throws SQLException {
		return fillContractToPackageDTO(rs, null);
	}

	public static ContractToPackageDTO fillContractToPackageDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		ContractToPackageDTO dto = new ContractToPackageDTO();

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setProductPackageID(rs.getInt(prex + "ProductPackageID"));

		dto.setContractNo(rs.getString(prex + "ContractNo"));

		return dto;
	}

	public static ValidDateChangeHistDTO fillValidDateChangeHistDTO(ResultSet rs)
			throws SQLException {
		return fillValidDateChangeHistDTO(rs, null);
	}

	public static ValidDateChangeHistDTO fillValidDateChangeHistDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		ValidDateChangeHistDTO dto = new ValidDateChangeHistDTO();
		dto.setCreateDate(rs.getTimestamp(prex + "CREATEDATE"));
		dto.setCustAdditionInfoId(rs.getInt(prex + "CUSTADDITIONINFOID"));
		dto.setCustomerId(rs.getInt(prex + "CUSTOMERID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setOperatorId(rs.getInt(prex + "OPERATORID"));
		dto.setSequenceNo(new Integer(rs.getInt(prex + "SEQUENCENO")));
		dto.setValidBeginDate(rs.getTimestamp(prex + "VALIDBEGINDATE"));
		dto.setValidEndDate(rs.getTimestamp(prex + "VALIDENDDATE"));
		return dto;
	}

	public static TerminalDeviceDTO fillTerminalDeviceDTO(ResultSet rs)
			throws SQLException {
		return fillTerminalDeviceDTO(rs, null);
	}

	public static TerminalDeviceDTO fillTerminalDeviceDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		TerminalDeviceDTO dto = new TerminalDeviceDTO();
		dto.setAddressID(rs.getInt(prex + "ADDRESSID"));
		dto.setAddressType(rs.getString(prex + "ADDRESSTYPE"));
		dto.setBatchID(rs.getInt(prex + "BATCHID"));
		dto.setCaSetDate(rs.getTimestamp(prex + "CASETDATE"));
		dto.setCaSetFlag(rs.getString(prex + "CASETFLAG"));
		dto.setDateFrom(rs.getTimestamp(prex + "DATEFROM"));
		dto.setDateTo(rs.getTimestamp(prex + "DATETO"));
		dto.setDepotID(rs.getInt(prex + "DEPOTID"));
		dto.setDeviceClass(rs.getString(prex + "DEVICECLASS"));
		dto.setDeviceID(rs.getInt(prex + "DEVICEID"));
		dto.setDeviceModel(rs.getString(prex + "DEVICEMODEL"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setGuaranteeLength(rs.getInt(prex + "GUARANTEELENGTH"));
		dto.setInterMACAddress(rs.getString(prex + "INTERMACADDRESS"));
		dto.setLeaseBuy(rs.getString(prex + "LEASEBUY"));
		dto.setMACAddress(rs.getString(prex + "MACADDRESS"));
		dto.setMatchDeviceID(rs.getInt(prex + "MATCHDEVICEID"));
		dto.setMatchFlag(rs.getString(prex + "MATCHFLAG"));

		dto.setPalletID(rs.getInt(prex + "PALLETID"));
		dto.setSerialNo(rs.getString(prex + "SERIALNO"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setUseFlag(rs.getString(prex + "USEFLAG"));
		dto.setPreAuthorization(rs.getString(prex + "PREAUTHORIZATION"));
		
		dto.setOwnerType(rs.getString(prex + "OWNERTYPE"));
		dto.setOwnerID(rs.getInt(prex + "OWNERID"));
		dto.setPurposeStrList(rs.getString(prex + "PURPOSESTRLIST"));
		dto.setOkNumber(rs.getString(prex + "OKNUMBER"));
		dto.setComments(rs.getString(prex + "COMMENTS"));
		return dto;
	}

	public static DeviceTransitionDTO fillDeviceTransitionDTO(ResultSet rs)
			throws SQLException {
		return fillDeviceTransitionDTO(rs, null);
	}

	public static DeviceTransitionDTO fillDeviceTransitionDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		DeviceTransitionDTO dto = new DeviceTransitionDTO();
		dto.setAction(rs.getString(prex + "ACTION"));
		dto.setBatchID(rs.getInt(prex + "BATCHID"));
		dto.setBatchNo(rs.getString(prex + "BATCHNO"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setDataFileName(rs.getString(prex + "DATAFILENAME"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setDeviceNumber(rs.getInt(prex + "DEVICENUMBER"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setFromID(rs.getInt(prex + "FROMID"));
		dto.setFromType(rs.getString(prex + "FROMTYPE"));
		dto.setOperatorID(rs.getInt(prex + "OPERATORID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setStatusReason(rs.getString(prex + "STATUSREASON"));
		dto.setToID(rs.getInt(prex + "TOID"));
		dto.setToType(rs.getString(prex + "TOTYPE"));
		return dto;
	}

	public static DeviceTransitionDetailDTO fillDeviceTransitionDetailDTO(
			ResultSet rs) throws SQLException {
		return fillDeviceTransitionDetailDTO(rs, null);
	}

	public static DeviceTransitionDetailDTO fillDeviceTransitionDetailDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		DeviceTransitionDetailDTO dto = new DeviceTransitionDetailDTO();
		dto.setBatchID(rs.getInt(prex + "BATCHID"));
		dto.setDeviceID(rs.getInt(prex + "DEVICEID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setFromDeviceStatus(rs.getString(prex + "FROMDEVICESTATUS"));
		dto.setFromID(rs.getInt(prex + "FROMID"));
		dto.setFromType(rs.getString(prex + "FROMTYPE"));
		dto.setSeqNo(rs.getInt(prex + "SEQNO"));
		dto.setSerialNo(rs.getString(prex + "SERIALNO"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setToDeviceStatus(rs.getString(prex + "TODEVICESTATUS"));
		dto.setToID(rs.getInt(prex + "TOID"));
		dto.setToType(rs.getString(prex + "TOTYPE"));
		return dto;
	}

	public static GroupBargainDTO fillGroupBargainDTO(ResultSet rs)
			throws SQLException {
		return fillGroupBargainDTO(rs, null);
	}

	public static GroupBargainDTO fillGroupBargainDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		GroupBargainDTO dto = new GroupBargainDTO();
		dto.setAbortSheets(rs.getInt(prex + "ABORTSHEETS"));
		dto.setAgentId(rs.getInt(prex + "AGENTID"));
		dto.setBargainNo(rs.getString(prex + "BARGAINNO"));
		dto.setCampaignId(rs.getInt(prex + "CAMPAIGNID"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setDataFrom(rs.getTimestamp(prex + "DATAFROM"));
		dto.setDateTo(rs.getTimestamp(prex + "DATETO"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		// dto.setGroupBargainClassID();
		dto.setId(rs.getInt(prex + "ID"));
		dto.setIsCampaign(rs.getString(prex + "ISCAMPAIGN"));
		// dto.setMonthFee();
		// dto.setMonthFeeDiscount();
		// dto.setNewProductPackage1();
		// dto.setNewProductPackage2();
		// dto.setNewProductPackage3();
		// dto.setNewProductPackage4();
		// dto.setNewProductPackage5();
		dto.setNormalTimeTo(rs.getTimestamp(prex + "NORMALTIMETO"));
		// dto.setOperatorID();
		dto.setOrgId(rs.getInt(prex + "ORGID"));
		dto.setPrepayImmediateFee(rs.getDouble(prex + "PREPAYIMMEDIATEFEE"));
		dto.setPrepayDepositFee(rs.getDouble(prex + "PREPAYDEPOSITFEE"));
		// dto.setPrepayAmountDiscount();
		// dto.setServiceFee();
		// dto.setServiceFeeDiscount();
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setMopId(rs.getInt(prex + "MOPID"));
		// dto.setTimeLengthUnitNumber();
		// dto.setTimeLengthUnitType();
		// dto.setTotalFee();
		// dto.setTotalFeeDiscount();
		dto.setTotalSheets(rs.getInt(prex + "TOTALSHEETS"));
		dto.setUsedSheets(rs.getInt(prex + "USEDSHEETS"));
		return dto;
	}

	public static GroupBargainDetailDTO fillGroupBargainDetailDTO(ResultSet rs)
			throws SQLException {
		return fillGroupBargainDetailDTO(rs, null);
	}

	public static GroupBargainDetailDTO fillGroupBargainDetailDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		GroupBargainDetailDTO dto = new GroupBargainDetailDTO();
		dto.setDetailNo(rs.getString(prex + "DETAILNO"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setGroupBargainID(rs.getInt(prex + "GROUPBARGAINID"));
		dto.setId(rs.getInt(prex + "ID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setUsedDate(rs.getTimestamp(prex + "USEDDATE"));
		dto.setUserID(rs.getInt(prex + "USERID"));
		return dto;
	}

	public static SystemLogDTO fillSystemLogDTO(ResultSet rs)
			throws SQLException {
		return fillSystemLogDTO(rs, null);
	}

	public static SystemLogDTO fillSystemLogDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		SystemLogDTO dto = new SystemLogDTO();
		dto.setAccountID(rs.getInt(prex + "ACCOUNTID"));
		dto.setCreateDateTime(rs.getTimestamp(prex + "CREATEDATETIME"));
		dto.setCustomerID(rs.getInt(prex + "CUSTOMERID"));
		dto.setCustomerName(rs.getString(prex + "CUSTOMERNAME"));
		dto.setOperatorName(rs.getString(prex + "OPERATORNAME"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setLogClass(rs.getString(prex + "LOGCLASS"));
		dto.setLogDesc(rs.getString(prex + "LOGDESC"));
		dto.setLoginID(rs.getString(prex + "LOGINID"));
		dto.setLogType(rs.getString(prex + "LOGTYPE"));
		dto.setMachineName(rs.getString(prex + "MACHINENAME"));
		dto.setModuleName(rs.getString(prex + "MODULENAME"));
		dto.setOperation(rs.getString(prex + "OPERATION"));
		dto.setOperatorID(rs.getInt(prex + "OPERATORID"));
	 	dto.setPsID(rs.getInt(prex + "PSID"));
		dto.setSequenceNo(rs.getInt(prex + "SEQUENCENO"));
		dto.setOrgID(rs.getInt(prex + "OrgID"));
		dto.setServiceAccountID(rs.getInt(prex + "SERVICEACCOUNTID"));
		return dto;
	}
	
	public static CsiProcessLogDTO fillCsiProcessLogDTO(ResultSet rs)
			throws SQLException {
		return fillCsiProcessLogDTO(rs, null);
	}

	public static CsiProcessLogDTO fillCsiProcessLogDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		CsiProcessLogDTO dto = new CsiProcessLogDTO();
		dto.setAction(rs.getString(prex + "ACTION"));
		dto.setActionTime(rs.getTimestamp(prex + "ACTIONTIME"));
		dto.setCsiID(rs.getInt(prex + "CSIID"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setId(rs.getInt(prex + "ID"));
		dto.setInvoiceNo(rs.getInt(prex + "INVOICENO"));
		dto.setOperatorID(rs.getInt(prex + "OPERATORID"));
		dto.setOrgID(rs.getInt(prex + "ORGID"));
		dto.setWorkResult(rs.getString(prex + "WORKRESULT"));
		dto.setWorkResultReason(rs.getString(prex + "WORKRESULTREASON"));
		return dto;
	}

	public static CustomerProblemDTO fillCustomerProblemDTO(ResultSet rs)
	throws SQLException {
return fillCustomerProblemDTO(rs, null);
}

public static CustomerProblemDTO fillCustomerProblemDTO(ResultSet rs,
	String prex) throws SQLException {
	if (prex == null)
		prex = "";
	prex = prex.trim();
	
	CustomerProblemDTO dto = new CustomerProblemDTO();
	dto.setId(rs.getInt(prex + "ID"));
	dto.setProblemLevel(rs.getString(prex + "PROBLEMLEVEL"));
	dto.setProblemCategory(rs.getString(prex + "PROBLEMCATEGORY"));		
	// dto.setProblemType();
	// dto.setProblemType();
	// dto.setReferCustomerProblem();
	dto.setReferJobCardID(rs.getInt(prex + "REFERJOBCARDID"));
	dto.setCustID(rs.getInt(prex + "CUSTID"));
	// dto.setCustOrgID();
	dto.setCustServiceAccountID(rs.getInt(prex + "CUSTSERVICEACCOUNTID"));
	dto.setCustAccountID(rs.getInt(prex + "CUSTACCOUNTID"));
	dto.setContactPerson(rs.getString(prex + "CONTACTPERSON"));
	dto.setContactphone(rs.getString(prex + "CONTACTPHONE"));
	dto.setAddressID(rs.getInt(prex + "ADDRESSID"));
	dto.setProblemDesc(rs.getString(prex + "PROBLEMDESC"));
	dto.setCreateDate(rs.getTimestamp(prex + "CREATEDATE"));
	dto.setDueDate(rs.getTimestamp(prex + "DUEDATE"));
	dto.setFeeClass(rs.getString(prex + "FEECLASS"));
	dto.setCallBackFlag(rs.getString(prex + "CALLBACKFLAG"));
	// dto.setCloseDate();
	dto.setCallBackDate(rs.getTimestamp(prex + "CALLBACKDATE"));
	dto.setProcessOrgId(rs.getInt(prex + "PROCESSORGID"));
	// dto.setCurrentDepartmentID();
	// dto.setCurrentOrgID();
	// dto.setCurrentProcessDate();
	dto.setStatus(rs.getString(prex + "STATUS"));
	dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
	dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));		
	dto.setCreateOpId(rs.getInt(prex + "CreateOpId"));
	dto.setCreateOrgID(rs.getInt(prex + "CreateOrgID"));
	dto.setIsManualTransfer(rs.getString(prex + "ISMANUALTRANSFER"));
	dto.setManualTransferFromOrgID(rs.getInt(prex + "MANUALTRANSFERFROMORGID"));
	return dto;
}

	public static JobCardProcessDTO fillJobCardProcessDTO(ResultSet rs)
	throws SQLException {
return fillJobCardProcessDTO(rs, null);
}

public static JobCardProcessDTO fillJobCardProcessDTO(ResultSet rs,
	String prex) throws SQLException {
	if (prex == null)
		prex = "";
	prex = prex.trim();
	JobCardProcessDTO dto = new JobCardProcessDTO();
	dto.setAction(rs.getString(prex + "ACTION"));
	dto.setActionTime(rs.getTimestamp(prex + "ACTIONTIME"));
	dto.setCurrentOperatorId(rs.getInt(prex + "CURRENTOPERATORID"));
	dto.setCurrentOrgId(rs.getInt(prex + "CURRENTORGID"));
	dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
	dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
	dto.setJcId(rs.getInt(prex + "JCID"));
	dto.setMemo(rs.getString(prex + "MEMO"));
	dto.setNextOrgId(rs.getInt(prex + "NEXTORGID"));
	dto.setOutOfDateReason(rs.getString(prex + "OUTOFDATEREASON"));
	dto.setProcessMan(rs.getString(prex + "PROCESSMAN"));
	dto.setProcessOrgId(rs.getString(prex + "PROCESSORGID"));
	dto.setSeqNo(rs.getInt(prex + "SEQNO"));
	dto.setWorkDate(rs.getTimestamp(prex + "WORKDATE"));
	dto.setWorkResult(rs.getString(prex + "WORKRESULT"));
	dto.setWorkResultReason(rs.getString(prex + "WORKRESULTREASON"));
	dto.setWorkTime(rs.getString(prex + "WORKTIME"));
	return dto;
}

	public static NewCustomerMarketInfoDTO fillNewCustomerMarketInfoDTO(
			ResultSet rs) throws SQLException {
		return fillNewCustomerMarketInfoDTO(rs, null);
	}

	public static NewCustomerMarketInfoDTO fillNewCustomerMarketInfoDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		NewCustomerMarketInfoDTO dto = new NewCustomerMarketInfoDTO();
		dto.setDtCreate(rs.getTimestamp("DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp("DT_LASTMOD"));
		dto.setInfoSettingId(rs.getInt("INFOSETTINGID"));

		dto.setInfoValueId(rs.getInt("INFOVALUEID"));
		dto.setID(rs.getInt("ID"));
		dto.setCsiID(rs.getInt("CSIID"));
		dto.setMemo(rs.getString("MEMO"));

		dto.setNewCustomerId(rs.getInt("NEWCUSTOMERID"));

		return dto;
	}

	public static AccountItemDTO fillAccountItemNotIncludeAiNoDTO(ResultSet rs)
			throws SQLException {
		return fillAccountItemNotIncludeAiNoDTO(rs, null);
	}

	/**
	 * 用来取得立即计算信息费的费用明细，但是不取得主健值
	 *
	 * @param rs
	 * @param prex
	 * @return
	 * @throws SQLException
	 */
	public static AccountItemDTO fillAccountItemNotIncludeAiNoDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		AccountItemDTO dto = new AccountItemDTO();
		dto.setAcctID(rs.getInt(prex + "ACCTID"));
		dto.setAcctItemTypeID(rs.getString(prex + "ACCTITEMTYPEID"));
		// 在这里不需要主健值，所以不用设置
		// dto.setAiNO(rs.getInt(prex+"AI_NO"));
		dto.setBatchNO(rs.getInt(prex + "BATCHNO"));
		dto.setBillingCycleID(rs.getInt(prex + "BILLINGCYCLEID"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setCustID(rs.getInt(prex + "CUSTID"));
		dto.setDate1(rs.getTimestamp(prex + "DATE1"));
		dto.setDate2(rs.getTimestamp(prex + "DATE2"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setForcedDepositFlag(rs.getString(prex + "FORCEDDEPOSITFLAG"));
		dto.setInvoiceFlag(rs.getString(prex + "INVOICEDFLAG"));
		dto.setInvoiceNO(rs.getInt(prex + "INVOICENO"));
		dto.setOrgID(rs.getInt(prex + "ORGID"));
		dto.setPsID(rs.getInt(prex + "PSID"));
		dto.setReferID(rs.getInt(prex + "REFERID"));
		dto.setReferType(rs.getString(prex + "REFERTYPE"));
		dto.setServiceAccountID(rs.getInt(prex + "SERVICEACCOUNTID"));
		dto.setProductID(rs.getInt(prex + "ProductID"));
		dto.setSetOffAmount(rs.getDouble(prex + "SETOFFAMOUNT"));
		dto.setSetOffFlag(rs.getString(prex + "SETOFFFLAG"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setValue(rs.getDouble(prex + "VALUE"));
		dto.setOperatorID(rs.getInt("OPID"));
		dto.setCreatingMethod(rs.getString("CREATINGMETHOD"));
		dto.setFeeType(rs.getString("FEETYPE"));
		dto.setBrID(rs.getInt("BRID"));
		dto.setAdjustmentNo(rs.getInt("ADJUSTMENTNO"));
		dto.setAdjustmentFlag(rs.getString("ADJUSTMENTFLAG"));
		dto.setComments(rs.getString("COMMENTS"));
		dto.setRfBillingCycleFlag(rs.getString("RFBILLINGCYCLEFLAG"));
		dto.setFeeSplitPlanID(rs.getInt("FEESPLITPLANID"));
		dto.setCcID(rs.getInt("CCID"));
		dto.setSourceRecordID(rs.getInt(prex+"SOURCERECORDID"));

		return dto;
	}

	public static UserPointsExchangeActivityDTO fillUserPointsExchangeActivityDTO(
			ResultSet rs) throws SQLException {
		return fillUserPointsExchangeActivityDTO(rs, null);
	}

	public static UserPointsExchangeActivityDTO fillUserPointsExchangeActivityDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		UserPointsExchangeActivityDTO dto = new UserPointsExchangeActivityDTO();
		dto.setId(new Integer(rs.getInt(prex + "ID")));
		dto.setName(rs.getString(prex + "NAME"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDescr(rs.getString(prex + "DESCR"));
		dto.setDateStart(rs.getTimestamp(prex + "DATESTART"));
		dto.setDateEnd(rs.getTimestamp(prex + "DATEEND"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));

		return dto;
	}

	public static UserPointsExchangeCondDTO fillUserPointsExchangeCondDTO(
			ResultSet rs) throws SQLException {
		return fillUserPointsExchangeCondDTO(rs, null);
	}

	public static UserPointsExchangeCondDTO fillUserPointsExchangeCondDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		UserPointsExchangeCondDTO dto = new UserPointsExchangeCondDTO();
		dto.setCondId(rs.getInt(prex + "CONDID"));
		dto.setActivityId(rs.getInt(prex + "ACTIVITYID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setCustTypeList(rs.getString(prex + "CUSTTYPELIST"));
		dto.setAccountClassList(rs.getString(prex + "ACCOUNTCLASSLIST"));
		dto.setExchangeGoodsTypeID(rs.getInt(prex + "EXCHANGEGOODSTYPEID"));
		dto.setMopIdList(rs.getString(prex + "MOPIDLIST"));
		dto.setPointRange1(rs.getInt(prex + "POINTRANGE1"));
		dto.setPointRange2(rs.getInt(prex + "POINTRANGE2"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));

		return dto;
	}

	public static UserPointsExchangeGoodsDTO fillUserPointsExchangeGoodsDTO(
			ResultSet rs) throws SQLException {
		return fillUserPointsExchangeGoodsDTO(rs, null);
	}

	public static UserPointsExchangeGoodsDTO fillUserPointsExchangeGoodsDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		UserPointsExchangeGoodsDTO dto = new UserPointsExchangeGoodsDTO();
		dto.setId(rs.getInt(prex + "ID"));
		dto.setActivityId(rs.getInt(prex + "ACTIVITYID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setName(rs.getString(prex + "NAME"));
		dto.setAmount(rs.getInt(prex + "AMOUNT"));
		dto.setDescr(rs.getString(prex + "DESCR"));

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));

		return dto;
	}

	public static BrconditionDTO fillBrconditionDTO(ResultSet rs)
			throws SQLException {
		return fillBrconditionDTO(rs, null);
	}

	public static BrconditionDTO fillBrconditionDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		BrconditionDTO dto = new BrconditionDTO();
		dto.setBrcntID(rs.getInt(prex + "BRCNTID"));
		dto.setCntName(rs.getString(prex + "CNTNAME"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setCntType(rs.getString(prex + "CNTTYPE"));
		dto.setCntValueStr(rs.getString(prex + "CNTVALUESTR"));

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));

		return dto;
	}

	public static BillingRuleDTO fillBillingRuleDTO(ResultSet rs)
			throws SQLException {
		return fillBillingRuleDTO(rs, null);
	}

	public static BillingRuleDTO fillBillingRuleDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		BillingRuleDTO dto = new BillingRuleDTO();
		dto.setId(rs.getInt(prex + "ID"));
		dto.setEventClass(rs.getInt(prex + "EVENTCLASS"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setEventReason(rs.getString(prex + "EVENTREASON"));
		dto.setProductId(rs.getInt(prex + "PRODUCTID"));
		dto.setDestProductId(rs.getInt(prex + "DESTPRODUCTID"));
		dto.setDestPackageId(rs.getInt(prex + "destPackageId"));
		dto.setBrcntIdCustType(rs.getInt(prex + "BRCNTID_CUSTTYPE"));
		dto.setBrcntIdAcctType(rs.getInt(prex + "BRCNTID_ACCTTYPE"));
		dto.setBrcntIdCampaign(rs.getInt(prex + "BRCNTID_CAMPAIGN"));
		dto.setPackageId(rs.getInt(prex + "PACKAGEID"));
		dto.setUseFormulaFlag(rs.getString(prex + "USEFORMULAFLAG"));
		dto.setFormulaId(rs.getInt(prex + "FORMULAID"));
		dto.setAcctItemTypeId(rs.getString(prex + "ACCTITEMTYPEID"));
		dto.setValue(rs.getDouble(prex + "VALUE"));
		dto.setValidFrom(rs.getTimestamp(prex + "VALIDFROM"));
		dto.setValidTo(rs.getTimestamp(prex + "VALIDTO"));
		dto.setBrDesc(rs.getString(prex + "BRDESC"));
		dto.setFeeSplitPlanID(rs.getInt(prex+"FeeSplitPlanID"));
		dto.setBrName(rs.getString(prex + "BRNAME"));
		dto.setAllowReturn(rs.getString(prex + "ALLOWRETURN"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setBrCntIDMarketSegmentID(rs.getInt(prex
				+ "BrCntID_MarketSegmentID"));
		dto.setBrCntIDUserType(rs.getInt(prex + "BrCntID_UserType"));
		dto.setPriority(rs.getInt(prex + "Priority"));
		dto.setBrCntIDBiDrectionFlag(rs.getInt(prex + "BRCNTID_BIDRECTIONFLAG"));
		dto.setBrCntIDCATVTermType(rs.getInt(prex + "BRCNTID_CATVTERMTYPE"));
		dto.setBrCntIDCableType(rs.getInt(prex + "BRCNTID_CABLETYPE"));
		dto.setOldPortNo(rs.getInt(prex + "oldPortNo"));
		dto.setNewPortNo(rs.getInt(prex + "newPortNo"));
		dto.setBrRateType(rs.getString(prex + "BrRateType"));
		dto.setRfBillingCycleFlag(rs.getString(prex + "RfBillingCycleFlag"));

		return dto;
	}

	public static MethodOfPaymentDTO fillMethodOfPaymentDTO(ResultSet rs)
			throws SQLException {
		return fillMethodOfPaymentDTO(rs, null);
	}

	public static MethodOfPaymentDTO fillMethodOfPaymentDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		MethodOfPaymentDTO dto = new MethodOfPaymentDTO();
		dto.setMopID(rs.getInt(prex + "MOPID"));
		dto.setAccountflag(rs.getString(prex + "ACCOUNTFLAG"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setName(rs.getString(prex + "NAME"));
		dto.setCashFlag(rs.getString(prex + "CASHFLAG"));
		dto.setPartnerID(rs.getInt(prex + "PARTNERID"));
		dto.setPaymentflag(rs.getString(prex + "PAYMENTFLAG"));
		dto.setPayType(rs.getString(prex + "PAYTYPE"));
		dto.setCsiTypeList(rs.getString(prex + "CsiTypeList"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));

		return dto;
	}

	public static UserPointsExchangeRuleDTO fillUserPointsExchangeRuleDTO(
			ResultSet rs) throws SQLException {
		return fillUserPointsExchangeRuleDTO(rs, null);
	}

	public static UserPointsExchangeRuleDTO fillUserPointsExchangeRuleDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		UserPointsExchangeRuleDTO dto = new UserPointsExchangeRuleDTO();
		dto.setId(rs.getInt(prex + "ID"));
		dto.setActivityId(rs.getInt(prex + "ACTIVITYID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setCustTypeList(rs.getString(prex + "CUSTTYPELIST"));
		dto.setExchangeGoodsAmount(rs.getInt(prex + "EXCHANGEGOODSAMOUNT"));
		dto.setExchangePointsValue(rs.getInt(prex + "EXCHANGEPOINTSVALUE"));
		dto.setExchangeGoodsTypeId(rs.getInt(prex + "EXCHANGEGOODSTYPEID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));

		return dto;
	}

	public static UserPointsExchangerCdDTO fillUserPointsExchangerCdDTO(
			ResultSet rs) throws SQLException {
		return fillUserPointsExchangerCdDTO(rs, null);
	}

	public static UserPointsExchangerCdDTO fillUserPointsExchangerCdDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		UserPointsExchangerCdDTO dto = new UserPointsExchangerCdDTO();
		dto.setId(rs.getInt(prex + "ID"));
		dto.setActivityId(rs.getInt(prex + "ACTIVITYID"));
		dto.setAccountId(rs.getInt(prex + "ACCOUNTID"));
		dto.setCreateOperatorId(rs.getInt(prex + "CREATEOPERATORID"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setExchangeGoodsAmount(rs.getInt(prex + "EXCHANGEGOODSAMOUNT"));
		dto.setExchangeGoodsTypeId(rs.getInt(prex + "EXCHANGEGOODSTYPEID"));
		dto.setExchangePoints(rs.getInt(prex + "EXCHANGEPOINTS"));
		dto.setUserPointPost(rs.getInt(prex + "USERPOINTPOST"));
		dto.setUserPointsBefore(rs.getInt(prex + "USERPOINTSBEFORE"));
		dto.setUserId(rs.getInt(prex + "USERID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));

		return dto;
	}

	public static SystemEventDTO fillSystemEventDTO(ResultSet rs)
			throws SQLException {
		return fillSystemEventDTO(rs, null);
	}

	public static SystemEventDTO fillSystemEventDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		SystemEventDTO dto = new SystemEventDTO();
		dto.setSequenceNo(rs.getInt(prex + "SEQUENCENO"));
		dto.setEventClass(rs.getInt(prex + "EVENTCLASS"));
		dto.setCustomerID(rs.getInt(prex + "CUSTOMERID"));
		dto.setAccountID(rs.getInt(prex + "ACCOUNTID"));
		dto.setServiceAccountID(rs.getInt(prex + "SERVICEACCOUNTID"));
		dto.setProductID(rs.getInt(prex + "PRODUCTID"));
		dto.setAmount(rs.getDouble(prex + "AMOUNT"));
		dto.setCsiID(rs.getInt(prex + "CSIID"));
		dto.setPsID(rs.getInt(prex + "PSID"));
		dto.setScDeviceID(rs.getInt(prex + "SCDEVICEID"));
		dto.setScSerialNo(rs.getString(prex + "SCSERIALNO"));
		dto.setStbDeviceID(rs.getInt(prex + "STBDEVICEID"));
		dto.setStbSerialNo(rs.getString(prex + "STBSERIALNO"));
		dto.setOldScDeviceID(rs.getInt(prex + "OLDSCDEVICEID"));
		dto.setOldScSerialNo(rs.getString(prex + "OLDSCSERIALNO"));
		dto.setOldStbDviceID(rs.getInt(prex + "OLDSTBDEVICEID"));
		dto.setOldStbSerialNo(rs.getString(prex + "OLDSTBSERIALNO"));
		dto.setOldProductID(rs.getInt(prex + "OLDPRODUCTID"));
		dto.setCommandID(rs.getInt(prex + "COMMANDID"));
		dto.setOperatorID(rs.getInt(prex + "OPERATORID"));
		dto.setInvoiceNo(rs.getInt(prex + "INVOICENO"));
		dto.setEventReason(rs.getString(prex + "EVENTREASON"));
		dto
				.setOldCustProductStatus(rs.getString(prex
						+ "OLDCUSTPRODUCTSTATUS"));
		dto.setRecordData(rs.getString(prex + "RECORDDATA"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setCaWalletCode(rs.getString(prex + "CAWALLETCODE"));
		dto.setServiceCode(rs.getString(prex + "SERVICECODE"));
		dto.setOldServiceCode(rs.getString(prex + "OLDSERVICECODE"));
		return dto;
	}

	public static PrepaymentDeductionRecordDTO fillPrepaymentDeductionRecordDTO(
			ResultSet rs) throws SQLException {
		return fillPrepaymentDeductionRecordDTO(rs, null);
	}

	public static PrepaymentDeductionRecordDTO fillPrepaymentDeductionRecordDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		PrepaymentDeductionRecordDTO dto = new PrepaymentDeductionRecordDTO();
		dto.setAcctId(rs.getInt(prex + "ACCTID"));
		dto.setAmount(rs.getDouble(prex + "AMOUNT"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setCreatingMethod(rs.getString(prex + "CREATINGMETHOD"));
		dto.setCustId(rs.getInt(prex + "CUSTID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setInvoicedFlag(rs.getString(prex + "INVOICEDFLAG"));
		dto.setInvoiceNo(rs.getInt(prex + "INVOICENO"));
		dto.setOpId(rs.getInt(prex + "OPID"));
		dto.setOrgId(rs.getInt(prex + "ORGID"));
		dto.setPrepaymentType(rs.getString(prex + "PREPAYMENTTYPE"));
		dto.setReferRecordId(rs.getInt(prex + "REFERRECORDID"));
		dto.setReferRecordType(rs.getString(prex + "REFERRECORDTYPE"));
		dto.setSeqNo(rs.getInt(prex + "SEQNO"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setComments(rs.getString(prex + "Comments"));
		dto.setReferSheetID(rs.getInt(prex + "ReferSheetID"));
		dto.setReferRecordType(rs.getString(prex + "ReferRecordType"));
		dto.setAdjustmentNo(rs.getInt(prex + "AdjustmentNo"));
		dto.setAdjustmentFlag(rs.getString(prex + "AdjustmentFlag"));

		return dto;
	}

	public static MarketSegmentDTO fillMarketSegmentDTO(ResultSet rs)
			throws SQLException {
		return fillMarketSegmentDTO(rs, null);
	}

	public static MarketSegmentDTO fillMarketSegmentDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		MarketSegmentDTO dto = new MarketSegmentDTO();
		dto.setId(rs.getInt(prex + "ID"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setName(rs.getString(prex + "NAME"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));

		return dto;
	}

	public static MarketSegmentToDistrictDTO fillMarketSegmentToDistrictDTO(
			ResultSet rs) throws SQLException {
		return fillMarketSegmentToDistrictDTO(rs, null);
	}

	public static MarketSegmentToDistrictDTO fillMarketSegmentToDistrictDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		MarketSegmentToDistrictDTO dto = new MarketSegmentToDistrictDTO();
		dto.setDistrictId(rs.getInt(prex + "DISTRICTID"));
		dto.setMarketSegmentId(rs.getInt(prex + "MARKETSEGMENTID"));

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));

		return dto;
	}

	public static ServiceDTO fillServiceDTO(ResultSet rs) throws SQLException {
		return fillServiceDTO(rs, null);
	}

	public static ServiceDTO fillServiceDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		ServiceDTO dto = new ServiceDTO();
		dto.setDateFrom(rs.getTimestamp(prex + "DATEFROM"));
		dto.setDateTo(rs.getTimestamp(prex + "DATETO"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setServiceID(rs.getInt(prex + "SERVICEID"));
		dto.setServiceName(rs.getString(prex + "SERVICENAME"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		return dto;
	}

	public static BossConfigurationDTO fillBossConfigurationDTO(ResultSet rs)
			throws SQLException {
		return fillBossConfigurationDTO(rs, null);
	}

	public static BossConfigurationDTO fillBossConfigurationDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		BossConfigurationDTO dto = new BossConfigurationDTO();
		dto.setSoftwareName(rs.getString(prex + "SOFTWARENAME"));
		dto.setSoftwarEversion(rs.getString(prex + "SOFTWAREVERSION"));
		dto.setDatabaseVersion(rs.getString(prex + "DATABASEVERSION"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setDeveloper(rs.getString(prex + "DEVELOPER"));
		dto.setMsoName(rs.getString(prex + "MSONAME"));
		dto.setMsoSystemName(rs.getString(prex + "MSOSYSTEMNAME"));
		dto.setLicensedMaxUser(rs.getInt(prex + "LICENSEDMAXUSER"));
		dto.setLicenseValidFrom(rs.getString(prex + "LICENSEVALIDFROM"));
		dto.setLicenseValidTo(rs.getString(prex + "LICENSEVALIDTO"));
		dto.setInstallTime(rs.getTimestamp(prex + "INSTALLTIME"));
		dto.setSystemSymbolName(rs.getString(prex + "SYSTEMSYMBOLNAME"));
		dto.setSystemName(rs.getString(prex + "SYSTEMNAME"));
		
		return dto;
	}

	public static ServiceDependencyDTO fillServiceDependencyDTO(ResultSet rs)
			throws SQLException {
		return fillServiceDependencyDTO(rs, null);
	}

	public static ServiceDependencyDTO fillServiceDependencyDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		ServiceDependencyDTO dto = new ServiceDependencyDTO();
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setReferServiceId(rs.getInt(prex + "REFERSERVICEID"));
		dto.setServiceId(rs.getInt(prex + "SERVICEID"));
		dto.setType(rs.getString(prex + "TYPE"));
		return dto;
	}

	public static OperatorDTO fillOperatorDTO(ResultSet rs) throws SQLException {
		return fillOperatorDTO(rs, null);
	}

	public static OperatorDTO fillOperatorDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		OperatorDTO dto = new OperatorDTO();
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setOperatorID(rs.getInt(prex + "OPERATORID"));
		dto.setLoginPwd(rs.getString(prex + "LOGINPWD"));
		dto.setLevelID(rs.getInt(prex + "LEVELID"));
		dto.setLoginID(rs.getString(prex + "LOGINID"));
		dto.setOperatorDesc(rs.getString(prex + "OPERATORDESC"));
		dto.setOperatorName(rs.getString(prex + "OPERATORNAME"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setInternalUserFlag(rs.getString(prex + "InternalUserFlag"));
		dto.setOrgID(rs.getInt(prex + "ORGID"));
		return dto;
	}

	public static ServiceResourceDTO fillServiceResourceDTO(ResultSet rs)
			throws SQLException {
		return fillServiceResourceDTO(rs, null);
	}

	public static ServiceResourceDTO fillServiceResourceDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		ServiceResourceDTO dto = new ServiceResourceDTO();

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setResourceDesc(rs.getString(prex + "RESOURCEDESC"));
		dto.setResourceName(rs.getString(prex + "RESOURCENAME"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setResourceType(rs.getString(prex + "RESOURCETYPE"));
		return dto;
	}

	public static SystemPrivilegeDTO fillSystemPrivilegeDTO(ResultSet rs)
			throws SQLException {
		return fillSystemPrivilegeDTO(rs, null);
	}

	public static SystemPrivilegeDTO fillSystemPrivilegeDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		SystemPrivilegeDTO dto = new SystemPrivilegeDTO();
		dto.setLevelID(rs.getInt(prex + "LEVELID"));
		dto.setModuleName(rs.getString(prex + "MODULENAME"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setPrivDesc(rs.getString(prex + "PRIVDESC"));
		dto.setPrivID(rs.getInt(prex + "PRIVID"));
		dto.setPrivName(rs.getString(prex + "PRIVNAME"));
		return dto;
	}

	public static ProductDependencyDTO fillProductDependencyDTO(ResultSet rs)
			throws SQLException {
		return fillProductDependencyDTO(rs, null);
	}

	public static ProductDependencyDTO fillProductDependencyDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		ProductDependencyDTO dto = new ProductDependencyDTO();
		dto.setDtCreate(rs.getTimestamp(prex + "dt_create"));
		dto.setDtLastmod(rs.getTimestamp(prex + "dt_lastmod"));
		dto.setProductId(rs.getInt(prex + "ProductID"));
		dto.setReferAllFlag(rs.getString(prex + "ReferAllFlag"));
		dto.setReferProductIDList(rs.getString(prex + "ReferProductIDList"));
		dto.setReferProductNum(rs.getInt(prex + "ReferProductNum"));
		dto.setType(rs.getString(prex + "Type"));
		dto.setSeqNo(rs.getInt(prex + "SeqNo"));

		return dto;
	}

	public static ProductPropertyDTO fillProductPropertyDTO(ResultSet rs)
			throws SQLException {
		return fillProductPropertyDTO(rs, null);
	}

	public static ProductPropertyDTO fillProductPropertyDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		ProductPropertyDTO dto = new ProductPropertyDTO();
		dto.setDescription(rs.getString(prex + "Description"));
		dto.setDtCreate(rs.getTimestamp(prex + "dt_create"));
		dto.setDtLastmod(rs.getTimestamp(prex + "dt_lastmod"));
		dto.setProductId(rs.getInt(prex + "ProductID"));
		dto.setPropertyMode(rs.getString(prex + "PropertyMode"));
		dto.setPropertyName(rs.getString(prex + "PropertyName"));
		dto.setResourceName(rs.getString(prex + "ResourceName"));
		dto.setPropertyCode(rs.getString(prex + "PropertyCode"));
		dto.setPropertyType(rs.getString(prex + "PropertyType"));
		dto.setPropertyValue(rs.getString(prex + "PropertyValue"));
		return dto;
	}

	public static BidimConfigSettingDTO fillBidimConfigSettingDTO(ResultSet rs)
			throws SQLException {
		return fillBidimConfigSettingDTO(rs, null);
	}

	public static BidimConfigSettingDTO fillBidimConfigSettingDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		BidimConfigSettingDTO dto = new BidimConfigSettingDTO();
		dto.setDescription(rs.getString(prex + "Description"));
		dto.setDtCreate(rs.getTimestamp(prex + "dt_create"));
		dto.setDtLastmod(rs.getTimestamp(prex + "dt_lastmod"));
		dto.setId(rs.getInt(prex + "ID"));
		dto.setName(rs.getString(prex + "NAME"));
		dto.setConfigType(rs.getString(prex + "CONFIGTYPE"));
		dto.setConfigSubType(rs.getString(prex + "CONFIGSUBTYPE"));
		dto.setAllowNull(rs.getString(prex + "ALLOWNULL"));
		dto.setServiceId(rs.getInt(prex + "SERVICEID"));
		dto.setStatus(rs.getString(prex + "Status"));
		dto.setValueType(rs.getString(prex + "ValueType"));

		return dto;
	}

	public static BidimConfigSettingValueDTO fillBidimConfigSettingValueDTO(
			ResultSet rs) throws SQLException {
		return fillBidimConfigSettingValueDTO(rs, null);
	}

	public static BidimConfigSettingValueDTO fillBidimConfigSettingValueDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		BidimConfigSettingValueDTO dto = new BidimConfigSettingValueDTO();
		dto.setDescription(rs.getString(prex + "Description"));
		dto.setDtCreate(rs.getTimestamp(prex + "dt_create"));
		dto.setDtLastmod(rs.getTimestamp(prex + "dt_lastmod"));
		dto.setId(rs.getInt(prex + "ID"));
		dto.setCode(rs.getString(prex + "code"));
		dto.setSettingId(rs.getInt(prex + "settingID"));
		dto.setDefaultOrNot(rs.getString(prex + "DEFAULTORNOT"));
		dto.setPriority(rs.getInt(prex + "PRIORITY"));
		dto.setStatus(rs.getString(prex + "Status"));
		return dto;
	}

	public static UserPointsCumulatedRuleDTO fillUserPointsCumulatedRuleDTO(
			ResultSet rs) throws SQLException {
		return fillUserPointsCumulatedRuleDTO(rs, null);
	}

	public static UserPointsCumulatedRuleDTO fillUserPointsCumulatedRuleDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		UserPointsCumulatedRuleDTO dto = new UserPointsCumulatedRuleDTO();
		dto.setId(rs.getInt(prex + "ID"));
		dto.setProductID(rs.getInt(prex + "PRODUCTID"));
		dto.setCondEvent(rs.getInt(prex + "CONDEVENT"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setCondValue1(rs.getDouble(prex + "CONDVALUE1"));
		dto.setCondValue2(rs.getInt(prex + "CONDVALUE2"));
		dto.setAddedPoints(rs.getInt(prex + "ADDEDPOINTS"));
		dto.setDescr(rs.getString(prex + "DESCR"));
		dto.setCustTypeList(rs.getString(prex + "CUSTTYPELIST"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));

		return dto;
	}

	public static DepotDTO fillDepotDTO(ResultSet rs) throws SQLException {
		return fillDepotDTO(rs, null);
	}

	public static DepotDTO fillDepotDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		DepotDTO dto = new DepotDTO();
		dto.setDepotID(rs.getInt(prex + "DEPOTID"));
		dto.setDepotName(rs.getString(prex + "DEPOTNAME"));
		dto.setDetailAddress(rs.getString(prex + "DETAILADDRESS"));
		dto.setOwnerID(rs.getInt(prex + "OWNERID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static PalletDTO fillPalletDTO(ResultSet rs) throws SQLException {
		return fillPalletDTO(rs, null);
	}

	public static PalletDTO fillPalletDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		PalletDTO dto = new PalletDTO();
		dto.setPalletID(rs.getInt(prex + "PALLETID"));
		dto.setPalletName(rs.getString(prex + "PALLETNAME"));
		dto.setPalletDesc(rs.getString(prex + "PALLETDESC"));
		dto.setMaxNumberAllowed(rs.getInt(prex + "MAXNUMBERALLOWED"));
		dto.setDepotID(rs.getInt(prex + "DEPOTID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static DeviceModelDTO fillDeviceModelDTO(ResultSet rs)
			throws SQLException {
		return fillDeviceModelDTO(rs, null);
	}

	public static DeviceModelDTO fillDeviceModelDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		DeviceModelDTO dto = new DeviceModelDTO();
		dto.setDeviceModel(rs.getString(prex + "DEVICEMODEL"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setProviderID(rs.getInt(prex + "PROVIDERID"));
		dto.setDeviceClass(rs.getString(prex + "DEVICECLASS"));
		dto.setSerialLength(rs.getInt(prex + "SERIALLENGTH"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setManageDeviceFlag(rs.getString(prex + "MANAGEDEVICEFLAG"));
		dto.setViewInCdtFlag(rs.getString(prex + "VIEWINCDTFLAG"));
		dto.setBusinessType(rs.getString(prex + "BUSINESSTYPE"));
		dto.setKeySerialNoFrom(rs.getInt(prex + "KEYSERIALNOFROM"));
		dto.setKeySerialNoTo(rs.getInt(prex + "KEYSERIALNOTO"));
		return dto;
	}

	public static LogisticsSettingDTO fillLogisticsSettingDTO(ResultSet rs)
			throws SQLException {
		return fillLogisticsSettingDTO(rs, null);
	}

	public static LogisticsSettingDTO fillLogisticsSettingDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		LogisticsSettingDTO dto = new LogisticsSettingDTO();
		dto.setSeqNo(rs.getInt(prex + "SEQNO"));
		dto.setInAndOut(rs.getString(prex + "INANDOUT"));
		dto.setOutOrgnization(rs.getInt(prex + "OUTORGNIZATION"));
		dto.setMatchAndPreauth(rs.getString(prex + "MATCHANDPREAUTH"));
		dto.setPreauthProductid1(rs.getInt(prex + "PREAUTHPRODUCTID1"));
		dto.setPreauthProductid2(rs.getInt(prex + "PREAUTHPRODUCTID2"));
		dto.setPreauthProductid3(rs.getInt(prex + "PREAUTHPRODUCTID3"));
		dto.setPreauthProductid4(rs.getInt(prex + "PREAUTHPRODUCTID4"));
		dto.setPreauthProductid5(rs.getInt(prex + "PREAUTHPRODUCTID5"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static CAHostDTO fillCaHostDTO(ResultSet rs) throws SQLException {
		return fillCaHostDTO(rs, null);
	}

	public static CAHostDTO fillCaHostDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		CAHostDTO dto = new CAHostDTO();
		dto.setHostID(rs.getInt(prex + "HOSTID"));
		dto.setHostName(rs.getString(prex + "HOSTNAME"));
		dto.setCaType(rs.getString(prex + "CATYPE"));
		dto.setIp(rs.getString(prex + "IP"));
		dto.setPort(rs.getInt(prex + "PORT"));
		dto.setIpBack(rs.getString(prex + "IPBACK"));
		dto.setPortBack(rs.getInt(prex + "PORTBACK"));
		dto.setProtocolType(rs.getString(prex + "PROTOCOLTYPE"));
		dto.setLoopSize(rs.getInt(prex + "LOOPSIZE"));
		dto.setLoopInterval(rs.getInt(prex + "LOOPINTERVAL"));
		dto.setTryTime(rs.getInt(prex + "TRYTIME"));
		dto.setValidStartTime(rs.getTimestamp(prex + "VALIDSTARTTIME"));
		dto.setValidEndTime(rs.getTimestamp(prex + "VALIDENDTIME"));
		dto.setLastRunTime(rs.getTimestamp(prex + "LASTRUNTIME"));
		dto.setLastStopTime(rs.getTimestamp(prex + "LASTSTOPTIME"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setOperatorID(rs.getInt(prex + "OPERATORID"));
		dto.setMd5key(rs.getString(prex + "MD5KEY"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static ResourcePhoneNoDTO fillResourcePhoneNoDTO(ResultSet rs)
			throws SQLException {
		return fillResourcePhoneNoDTO(rs, null);
	}

	public static ResourcePhoneNoDTO fillResourcePhoneNoDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		ResourcePhoneNoDTO dto = new ResourcePhoneNoDTO();
		dto.setItemID(rs.getInt(prex + "ITEMID"));
		dto.setDistrictId(rs.getInt(prex + "DISTRICTID"));
		dto.setGrade(rs.getString(prex + "grade"));
		dto.setChooseNoFee(rs.getDouble(prex + "chooseNoFee"));
		dto.setResourceName(rs.getString(prex + "RESOURCENAME"));
		dto.setPhoneNo(rs.getString(prex + "PHONENO"));
		dto.setAreaCode(rs.getString(prex + "AREACODE"));
		dto.setCountryCode(rs.getString(prex + "COUNTRYCODE"));
		dto.setComments(rs.getString(prex + "COMMENTS"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setStatusTime(rs.getTimestamp(prex + "STATUSTIME"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static DistrictSettingDTO fillDistrictSettingDTO(ResultSet rs)
			throws SQLException {
		return fillDistrictSettingDTO(rs, null);
	}

	public static DistrictSettingDTO fillDistrictSettingDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		DistrictSettingDTO dto = new DistrictSettingDTO();
		dto.setId(rs.getInt(prex + "ID"));
		dto.setDistrictCode(rs.getString(prex + "DISTRICTCODE"));
		dto.setName(rs.getString(prex + "NAME"));
		dto.setType(rs.getString(prex + "TYPE"));
		dto.setBelongTo(rs.getInt(prex + "BELONGTO"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static BillingCycleTypeDTO fillBillingCycleTypeDTO(ResultSet rs)
			throws SQLException {
		return fillBillingCycleTypeDTO(rs, null);
	}

	public static BillingCycleTypeDTO fillBillingCycleTypeDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		BillingCycleTypeDTO dto = new BillingCycleTypeDTO();

		// ID 1 1 N NUMBER (10) NULL
		// NAME 2 Y VARCHAR2 (50) NULL
		// CTYPE 3 N VARCHAR2 (5) NULL
		// DESCRIPTION 4 Y VARCHAR2 (200) NULL
		// UNIFIEDCYCLEFLAG 5 Y VARCHAR2 (5) NULL
		// RENTCYCLEBDATE 6 N DATE NULL
		// OTHERCYCLEBDATE 7 Y DATE NULL
		// ALLOWPREBILLINGFLAG 8 N VARCHAR2 (5) NULL
		// STARTBILLINGFLAG 9 N VARCHAR2 (5) NULL
		// INVOICEDUEDATE 10 Y DATE NULL
		// BILLINGCYCLETYPEID 11 Y NUMBER (10) NULL
		// FIRSTVALIDINVOICECYCLEID 12 Y NUMBER (10) NULL
		// CYCLECOUNT 13 Y NUMBER (10) NULL
		// UNIFIEDDISCTFLAG 14 N VARCHAR2 (5) NULL
		// RENTDISCTMODE 15 Y VARCHAR2 (5) NULL
		// RENTDIVIDINGDATE 16 Y DATE NULL
		// DT_CREATE 17 Y TIMESTAMP(6) NULL
		// DT_LASTMOD 18 Y TIMESTAMP(6) NULL
		// ENDINVOICINGDATE 19 Y DATE

		dto.setId(rs.getInt(prex + "ID"));
		dto.setName(rs.getString(prex + "NAME"));
		dto.setCType(rs.getString(prex + "CTYPE"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setUnifiedCycleFlag(rs.getString(prex + "UNIFIEDCYCLEFLAG"));
		dto.setRentCyclebDate(rs.getTimestamp(prex + "RENTCYCLEBDATE"));
		dto.setOtherCycleBDate(rs.getTimestamp(prex + "OTHERCYCLEBDATE"));
		dto.setAllowPrebillingFlag(rs.getString(prex + "ALLOWPREBILLINGFLAG"));
		dto.setStartBillingFlag(rs.getString(prex + "STARTBILLINGFLAG"));
		dto.setInvoiceDueDate(rs.getTimestamp(prex + "INVOICEDUEDATE"));
		dto.setBillingCycleTypeId(rs.getInt(prex + "BILLINGCYCLETYPEID"));
		dto.setFirstValidInvoiceCycleId(rs.getInt(prex
				+ "FIRSTVALIDINVOICECYCLEID"));
		dto.setCycleCount(rs.getInt(prex + "CYCLECOUNT"));
		dto.setUnifiedDisctFlag(rs.getString(prex + "UNIFIEDDISCTFLAG"));
		dto.setRentDisctMode(rs.getString(prex + "RENTDISCTMODE"));
		dto.setRentDividingDate(rs.getTimestamp(prex + "RENTDIVIDINGDATE"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setEndInvoicingDate(rs.getTimestamp(prex + "ENDINVOICINGDATE"));
		dto.setStatus(rs.getString(prex + "STATUS"));

		return dto;
	}

	public static CustAcctCycleCfgDTO fillCustAcctCycleCfgDTO(ResultSet rs)
			throws SQLException {
		return fillCustAcctCycleCfgDTO(rs, null);
	}

	public static CustAcctCycleCfgDTO fillCustAcctCycleCfgDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		CustAcctCycleCfgDTO dto = new CustAcctCycleCfgDTO();

		// SEQNO BILLINGCYCLETYPEID CUSTOMERTYPE ACCOUNTTYPE DT_CREATE
		// DT_LASTMOD
		dto.setAccountType(rs.getString(prex + "ACCOUNTTYPE"));
		dto.setBillingCycleTypeId(rs.getInt(prex + "BILLINGCYCLETYPEID"));
		dto.setCustomerType(rs.getString(prex + "CUSTOMERTYPE"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setSeqNo(rs.getInt(prex + "SEQNO"));

		return dto;
	}

	/**
	 * added by wanghao
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static FinancialSettingDTO fillFinancialSettingDTO(ResultSet rs)
			throws SQLException {
		return fillFinancialSettingDTO(rs, null);
	}

	public static FinancialSettingDTO fillFinancialSettingDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		FinancialSettingDTO dto = new FinancialSettingDTO();

		// T_FinancialSetting
		// NAME UNIFIEDCYCLEFLAG BILLINGSTARTDATE INVOICEDUEDATE
		// CALCULATEPUNISHMENTFLAG
		// PUNISHMENTSTARTDATE PUNISHMENTRATE SMALLCHANGEPROCESSMODE
		// PREPAYMENTDEDUCTIONMODE INVOICEACCUMULATEMODE SETOFFLEVEL
		// AUTORESUMECPFLAG DELAYONEMONTHINARREARFLAG DT_CREATE DT_LASTMOD
		// PUNISHMENTACCTITEMTYPEID FORCEDDEPOSITACCTITEMTYPEID
		// RETURNDEVICEACCTITEMTYPEID

		dto.setAutoResumeCpFlag(rs.getString(prex + "AUTORESUMECPFLAG"));
		dto.setBillingStartDate(rs.getTimestamp(prex + "BILLINGSTARTDATE"));
		dto.setCalculatePunishmentFlag(rs.getString(prex
				+ "CALCULATEPUNISHMENTFLAG"));
		dto.setDelayOneMonthInarrearFlag(rs.getString(prex
				+ "DELAYONEMONTHINARREARFLAG"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setForcedDepositAcctItemTypeID(rs.getString(prex
				+ "FORCEDDEPOSITACCTITEMTYPEID"));
		dto.setInvoiceAccumulateMode(rs.getString(prex
				+ "INVOICEACCUMULATEMODE"));
		dto.setInvoiceDueDate(rs.getInt(prex + "INVOICEDUEDATE"));
		dto.setName(rs.getString(prex + "NAME"));
		dto.setPrepaymentDeductionMode(rs.getString(prex
				+ "PREPAYMENTDEDUCTIONMODE"));
		dto.setPunishmentAcctItemTypeID(rs.getString(prex
				+ "PUNISHMENTACCTITEMTYPEID"));
		dto.setPunishmenTrate(rs.getDouble(prex + "PUNISHMENTRATE"));
		dto.setPunishmentStartDate(rs.getInt(prex + "PUNISHMENTSTARTDATE"));
		dto.setReturnDeviceAcctItemTypeID(rs.getString(prex
				+ "RETURNDEVICEACCTITEMTYPEID"));
		dto.setSetOffLevel(rs.getString(prex + "SETOFFLEVEL"));
		dto.setSmallchangeProcessMode(rs.getString(prex
				+ "SMALLCHANGEPROCESSMODE"));
		dto.setUnifiedCycleFlag(rs.getString(prex + "UNIFIEDCYCLEFLAG"));

		return dto;
	}

	public static LdapProcessLogDTO fillLdapProcessLogDTO(ResultSet rs)
			throws SQLException {
		return fillLdapProcessLogDTO(rs, null);
	}

	public static LdapProcessLogDTO fillLdapProcessLogDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		LdapProcessLogDTO dto = new LdapProcessLogDTO();
		dto.setAccountID(rs.getInt(prex + "AccountID"));
		dto.setCommandName(rs.getString(prex + "CommandName"));
		dto.setCreateTime(rs.getTimestamp(prex + "CreateTime"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setCustomerID(rs.getInt(prex + "CustomerID"));
		dto.setDeviceID(rs.getInt(prex + "DeviceID"));
		dto.setSeqno(rs.getInt(prex + "Seqno"));
		dto.setProcessResult(rs.getString(prex + "ProcessResult"));
		dto.setEventID(rs.getInt(prex + "EventID"));
		dto.setPsID(rs.getInt(prex + "PsID"));
		dto.setServiceAccountID(rs.getInt(prex + "ServiceAccountID"));
		dto.setDescription(rs.getString(prex + "Description"));
		dto.setStatus(rs.getString(prex + "Status"));
		dto.setQueueID(rs.getInt(prex + "queueID"));
		return dto;
	}

	public static LdapHostDTO fillLdapHostDTO(ResultSet rs) throws SQLException {
		return fillLdapHostDTO(rs, null);
	}

	public static LdapHostDTO fillLdapHostDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		LdapHostDTO dto = new LdapHostDTO();
		dto.setCmentrydir(rs.getString(prex + "Cmentrydir"));
		dto.setHostID(rs.getInt(prex + "HostID"));

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setHostName(rs.getString(prex + "HostName"));
		dto.setIpAddress(rs.getString(prex + "IpAddress"));
		dto.setLoginID(rs.getString(prex + "LoginID"));
		dto.setLoginPWD(rs.getString(prex + "LoginPWD"));
		dto.setPort(rs.getInt(prex + "port"));
		dto.setProtocolType(rs.getString(prex + "ProtocolType"));

		dto.setStatus(rs.getString(prex + "Status"));
		return dto;
	}

	public static LdapProdToSmsProdDTO fillfillLdapProdToSmsProdDTO(ResultSet rs)
			throws SQLException {
		return fillLdapProdToSmsProdDTO(rs, null);
	}

	public static LdapProdToSmsProdDTO fillLdapProdToSmsProdDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		LdapProdToSmsProdDTO dto = new LdapProdToSmsProdDTO();
		dto.setSmsProductId(rs.getInt(prex + "smsproductid"));
		dto.setLdapProductName(rs.getString(prex + "ldapproductname"));
		dto.setPriority(rs.getInt(prex + "priority"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));

		return dto;
	}

	/**
	 * added by wanghao
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static VODInterfaceHostDTO fillVODInterfaceHostDTO(ResultSet rs)
			throws SQLException {
		return fillVODInterfaceHostDTO(rs, null);
	}

	/**
	 * added by wanghao
	 *
	 * @param rs
	 * @param prex
	 * @return
	 * @throws SQLException
	 */
	public static VODInterfaceHostDTO fillVODInterfaceHostDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		VODInterfaceHostDTO dto = new VODInterfaceHostDTO();
		// HOSTID, HOSTNAME, VODTYPE, IP, PORT, IPBACK, PORTBACK, PROTOCOLTYPE,
		// LOOPSIZE, LOOPINTERVAL, TRYTIME, VALIDSTARTTIME, VALIDENDTIME,
		// LASTRUNTIME, LASTSTOPTIME, STATUS, DT_CREATE, DT_LASTMOD
		dto.setHostID(rs.getInt(prex + "HOSTID"));
		dto.setHostName(rs.getString(prex + "HOSTNAME"));
		dto.setVodType(rs.getString(prex + "VODTYPE"));
		dto.setIp(rs.getString(prex + "IP"));
		dto.setPort(rs.getString(prex + "PORT"));
		dto.setIpBack(rs.getString(prex + "IPBACK"));
		dto.setPortBack(rs.getString(prex + "PORTBACK"));
		dto.setProtocolType(rs.getString(prex + "PROTOCOLTYPE"));
		dto.setLoopSize(rs.getInt(prex + "LOOPSIZE"));
		dto.setLoopInterval(rs.getInt(prex + "LOOPINTERVAL"));
		dto.setTryTime(rs.getInt(prex + "TRYTIME"));
		dto.setValidStartTime(rs.getTimestamp(prex + "VALIDSTARTTIME"));
		dto.setValidEndTime(rs.getTimestamp(prex + "VALIDENDTIME"));
		dto.setLastRunTime(rs.getTimestamp(prex + "LASTRUNTIME"));
		dto.setLastStopTime(rs.getTimestamp(prex + "LASTSTOPTIME"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastMod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	/**
	 * added by wanghao
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static VODInterfaceProductDTO fillVODInterfaceProductDTO(ResultSet rs)
			throws SQLException {
		return fillVODInterfaceProductDTO(rs, null);
	}

	/**
	 * added by wanghao
	 *
	 * @param rs
	 * @param prex
	 * @return
	 * @throws SQLException
	 */
	public static VODInterfaceProductDTO fillVODInterfaceProductDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		VODInterfaceProductDTO dto = new VODInterfaceProductDTO();
		// SMSPRODUCTID, VODPRODUCTIDLIST, VODSERVICEIDLIST, BILLINGCODELIST,
		// NEWSAFLAG, STATUS, DT_CREATE, DT_LASTMOD, ACCTITEMTYPEID
		dto.setSmsProductID(rs.getInt(prex + "SMSPRODUCTID"));
		dto.setVodProductIDList(rs.getString(prex + "VODPRODUCTIDLIST"));
		dto.setVodServiceIDList(rs.getString(prex + "VODSERVICEIDLIST"));
		dto.setBillingCodeList(rs.getString(prex + "BILLINGCODELIST"));
		dto.setNewsaflag(rs.getString(prex + "NEWSAFLAG"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastMod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setAcctItemTypeID(rs.getString(prex + "ACCTITEMTYPEID"));
		return dto;
	}

	/**
	 * added by wanghao
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static VODInterfaceCustomerOrderedDTO fillVODInterfaceCustomerOrderedDTO(
			ResultSet rs) throws SQLException {
		return fillVODInterfaceCustomerOrderedDTO(rs, null);
	}

	/**
	 * added by wanghao
	 *
	 * @param rs
	 * @param prex
	 * @return
	 * @throws SQLException
	 */
	public static VODInterfaceCustomerOrderedDTO fillVODInterfaceCustomerOrderedDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		VODInterfaceCustomerOrderedDTO dto = new VODInterfaceCustomerOrderedDTO();
		// SEQNO, HOSTID, CREATETIME, VODRECORDID, VODTYPE, DEVICEMACADDR,
		// MOVIEID,
		// MOVIENAME, MOIVECATALOG, MOVIEGRADE, VENDERID, VENDERNAME,
		// PLAYSTATUS,
		// PLAYSTARTTIME, PLAYENDTIME, RECORDSTATUS, RATINGSTATUS, RATINGTIME,
		// ACCTITEMTYPEID, VALUE, CUSTID, ACCTID, USERID, PSID, ACCOUNTEDFLAG,
		// ACCOUNTEDTIME, DT_CREATE, DT_LASTMOD
		dto.setSeqNO(rs.getInt(prex + "SEQNO"));
		dto.setHostID(rs.getInt(prex + "HOSTID"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setVodRecordID(rs.getInt(prex + "VODRECORDID"));
		dto.setVodType(rs.getString(prex + "VODTYPE"));
		dto.setDeviceMacAddr(rs.getString(prex + "DEVICEMACADDR"));
		dto.setMovieID(rs.getString(prex + "MOVIEID"));
		dto.setMovieName(rs.getString(prex + "MOVIENAME"));
		dto.setMovieCataLog(rs.getString(prex + "MOIVECATALOG"));
		dto.setMovieGrade(rs.getString(prex + "MOVIEGRADE"));
		dto.setVenderID(rs.getInt(prex + "VENDERID"));
		dto.setVenderName(rs.getString(prex + "VENDERNAME"));
		dto.setPlayStatus(rs.getString(prex + "PLAYSTATUS"));
		dto.setPlayStartTime(rs.getTimestamp(prex + "PLAYSTARTTIME"));
		dto.setPlayEndTime(rs.getTimestamp(prex + "PLAYENDTIME"));
		dto.setRecordStatus(rs.getString(prex + "RECORDSTATUS"));
		dto.setRatingStatus(rs.getString(prex + "RATINGSTATUS"));
		dto.setRatingTime(rs.getTimestamp(prex + "RATINGTIME"));
		dto.setAcctItemTypeID(rs.getString(prex + "ACCTITEMTYPEID"));
		dto.setValue(rs.getDouble(prex + "VALUE"));
		dto.setChargeAmount(rs.getDouble(prex + "CHARGEAMOUNT"));
		dto.setCustID(rs.getInt(prex + "CUSTID"));
		dto.setAcctID(rs.getInt(prex + "ACCTID"));
		dto.setUserID(rs.getInt(prex + "USERID"));
		dto.setPsID(rs.getInt(prex + "PSID"));
		dto.setAccountedFlag(rs.getString(prex + "ACCOUNTEDFLAG"));
		dto.setAccountedTime(rs.getTimestamp(prex + "ACCOUNTEDTIME"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastMod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	/**
	 * added by wanghao
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static VODInterfaceLogDTO fillVODInterfaceLogDTO(ResultSet rs)
			throws SQLException {
		return fillVODInterfaceLogDTO(rs, null);
	}

	/**
	 * added by wanghao
	 *
	 * @param rs
	 * @param prex
	 * @return
	 * @throws SQLException
	 */
	public static VODInterfaceLogDTO fillVODInterfaceLogDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		VODInterfaceLogDTO dto = new VODInterfaceLogDTO();

		dto.setSeqNO(rs.getInt(prex + "SEQNO"));
		dto.setTransactionID(rs.getInt(prex + "TRANSACTIONID"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setType(rs.getString(prex + "TYPE"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setProcessResult(rs.getString(prex + "PROCESSRESULT"));
		dto.setProcessInformation(rs.getString(prex + "PROCESSINFORMATION"));
		dto.setCustomerID(rs.getInt(prex + "CUSTOMERID"));
		dto.setAccountID(rs.getInt(prex + "ACCOUNTID"));
		dto.setServiceAccountID(rs.getInt(prex + "SERVICEACCOUNTID"));
		dto.setPsID(rs.getInt(prex + "PSID"));
		dto.setDeviceNO(rs.getString(prex + "DEVICENO"));
		dto.setSmsProductID(rs.getInt(prex + "SMSPRODUCTID"));
		dto.setVodProductID(rs.getString(prex + "VODPRODUCTID"));
		dto.setVodServiceID(rs.getString(prex + "VODSERVICEID"));
		dto.setRecuringFlag(rs.getString(prex + "RECURINGFLAG"));
		dto.setMovieID(rs.getString(prex + "MOVIEID"));
		dto.setMovieName(rs.getString(prex + "MOVIENAME"));
		dto.setMovieGrade(rs.getString(prex + "MOVIEGRADE"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastMod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setQueueID(rs.getInt(prex + "queueID"));
		dto.setVodDataRecordID(rs.getInt(prex + "vodDataRecordID"));
		return dto;
	}

	public static CAProductDTO fillCAProductDTO(ResultSet rs)
			throws SQLException {
		return fillCAProductDTO(rs, null);
	}

	public static CAProductDTO fillCAProductDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		CAProductDTO dto = new CAProductDTO();
		dto.setProductId(rs.getInt(prex + "PRODUCTID"));
		dto.setConditionId(rs.getInt(prex + "CONDITIONID"));
		dto.setEntitlement(rs.getString(prex + "ENTITLEMENT"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setOpiID(rs.getInt(prex + "OPI_ID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	public static CAProductDefDTO fillCAProductDefDTO(ResultSet rs)
	   throws SQLException {
         return fillCAProductDefDTO(rs, null);
    }

    public static CAProductDefDTO fillCAProductDefDTO(ResultSet rs, String prex)
	  throws SQLException {
      if (prex == null)
	  prex = "";
      prex = prex.trim();
      CAProductDefDTO dto = new CAProductDefDTO();
      dto.setHostID(rs.getInt(prex + "HostID"));
      dto.setProductName(rs.getString(prex + "CAProductName"));
      dto.setCaProductID(rs.getString(prex + "CaProductID"));
      dto.setOpiID(rs.getInt(prex + "OPI_ID"));
      dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
       dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
     return dto;
}


	public static ContractDTO fillContractDTO(ResultSet rs) throws SQLException {
		return fillContractDTO(rs, null);
	}

	public static ContractDTO fillContractDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		ContractDTO dto = new ContractDTO();
		dto.setContractNo(rs.getString(prex + "CONTRACTNO"));
		dto.setName(rs.getString(prex + "NAME"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setDatefrom(rs.getTimestamp(prex + "DATEFROM"));
		dto.setDateto(rs.getTimestamp(prex + "DATETO"));
		dto.setNormaldate(rs.getTimestamp(prex + "NORMALDATE"));
		dto.setRentFeeTotal(rs.getDouble(prex + "RENTFEETOTAL"));
		dto.setOneOffFeeTotal(rs.getDouble(prex + "ONEOFFFEETOTAL"));
		dto.setPrepayAmount(rs.getDouble(prex + "PREPAYAMOUNT"));
		dto.setPrepayMopID(rs.getInt(prex + "PREPAYMOPID"));
		dto.setUsedDate(rs.getTimestamp(prex + "USEDDATE"));
		dto.setUsedCustomerID(rs.getInt(prex + "USEDCUSTOMERID"));
		dto.setSheetNo(rs.getString(prex + "SHEETNO"));
		dto.setUserCount(rs.getInt(prex + "USERCOUNT"));
		dto.setUsedCount(rs.getInt(prex + "USEDCOUNT"));
		dto.setSourceOrg(rs.getInt(prex + "SOURCEORG"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static ContractProcessLogDTO fillContractProcessLogDTO(ResultSet rs)
			throws SQLException {
		return fillContractProcessLogDTO(rs, null);
	}

	public static ContractProcessLogDTO fillContractProcessLogDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		ContractProcessLogDTO dto = new ContractProcessLogDTO();
		dto.setSeqNo(rs.getInt(prex + "SEQNO"));
		dto.setContractNo(rs.getString(prex + "CONTRACTNO"));
		dto.setAction(rs.getString(prex + "ACTION"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setActionTime(rs.getTimestamp(prex + "ACTIONTIME"));

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	public static CommonSettingDataDTO fillCommonSettingDataDTO(ResultSet rs)
	   throws SQLException {
       return fillCommonSettingDataDTO(rs, null);
    }

    public static CommonSettingDataDTO fillCommonSettingDataDTO(ResultSet rs,
	        String prex) throws SQLException {
    if (prex == null)
	   prex = "";
       prex = prex.trim();

      CommonSettingDataDTO dto = new CommonSettingDataDTO();
      dto.setName(rs.getString(prex + "NAME"));
      dto.setKey(rs.getString(prex + "KEY"));
      dto.setValue(rs.getString(prex + "VALUE"));
      dto.setDescription(rs.getString(prex + "DESCRIPTION"));
      dto.setStatus(rs.getString(prex + "STATUS"));
      dto.setPriority(rs.getInt(prex + "PRIORITY"));
      dto.setDefaultOrNot(rs.getString(prex + "DEFAULTORNOT"));
      dto.setGrade(rs.getInt(prex + "GRADE"));
      dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
      dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
      return dto;
    }

	public static CACommandDTO fillCACommandDTO(ResultSet rs)
			throws SQLException {
		return fillCACommandDTO(rs, null);
	}

	public static CACommandDTO fillCACommandDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		CACommandDTO dto = new CACommandDTO();
		dto.setCommandID(rs.getInt(prex + "COMMANDID"));
		dto.setCommandName(rs.getString(prex + "COMMANDNAME"));
		dto.setCommandString(rs.getString(prex + "COMMANDSTRING"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static CAParameterDTO fillCAParameterDTO(ResultSet rs)
			throws SQLException {
		return fillCAParameterDTO(rs, null);
	}

	public static CAParameterDTO fillCAParameterDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		CAParameterDTO dto = new CAParameterDTO();
		dto.setPmName(rs.getString(prex + "PMNAME"));
		dto.setPmType(rs.getString(prex + "PMTYPE"));
		dto.setPmSize(rs.getInt(prex + "PMSIZE"));
		dto.setPmHasDefault(rs.getInt(prex + "PMHASDEFAULT"));
		dto.setPmDefault(rs.getString(prex + "PMDEFAULT"));
		dto.setPmDesc(rs.getString(prex + "PMDESC"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static CAConditionDTO fillCAConditionDTO(ResultSet rs)
			throws SQLException {
		return fillCAConditionDTO(rs, null);
	}

	public static CAConditionDTO fillCAConditionDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		CAConditionDTO dto = new CAConditionDTO();
		dto.setCondID(rs.getInt(prex + "CONDID"));
		dto.setCondName(rs.getString(prex + "CONDNAME"));
		dto.setHostID(rs.getInt(prex + "HOSTID"));
		dto.setCondString(rs.getString(prex + "CONDSTRING"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static LdapProductDTO fillLdapProductDTO(ResultSet rs)
			throws SQLException {
		return fillLdapProductDTO(rs, null);
	}

	public static LdapProductDTO fillLdapProductDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		LdapProductDTO dto = new LdapProductDTO();
		dto.setProductName(rs.getString(prex + "productname"));
		dto.setStatus(rs.getString(prex + "status"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static LdapAttrConfigDTO fillLdapAttrConfigDTO(ResultSet rs)
			throws SQLException {
		return fillLdapAttrConfigDTO(rs, null);
	}

	public static LdapAttrConfigDTO fillLdapAttrConfigDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		LdapAttrConfigDTO dto = new LdapAttrConfigDTO();
		dto.setAttrName(rs.getString(prex + "attrname"));
		dto.setStatus(rs.getString(prex + "status"));
		dto.setFixedFlag(rs.getString(prex + "fixedflag"));
		dto.setFixedValue(rs.getString(prex + "fixedvalue"));
		dto.setLength(rs.getInt(prex + "length"));
		dto.setPrefix(rs.getString(prex + "prefix"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static CAEventCmdMapDTO fillCAEventCmdMapDTO(ResultSet rs)
			throws SQLException {
		return fillCAEventCmdMapDTO(rs, null);
	}

	public static CAEventCmdMapDTO fillCAEventCmdMapDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		CAEventCmdMapDTO dto = new CAEventCmdMapDTO();
		dto.setMapID(rs.getInt(prex + "MAPID"));
		dto.setMapCmdID(rs.getInt(prex + "MAPCMDID"));
		dto.setMapEventID(rs.getInt(prex + "MAPEVENTID"));
		dto.setMapConditionID(rs.getInt(prex + "MAPCONDITIONID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setPriority(rs.getInt(prex + "PRIORITY"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
/**
	public static CAQueueDTO fillCAProcessEventDTO(ResultSet rs)
			throws SQLException {
		return fillCAProcessEventDTO(rs, null);
	}

	public static CAQueueDTO fillCAProcessEventDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		CAQueueDTO dto = new CAQueueDTO();
		dto.setQueueID(rs.getInt(prex + "QUEUEID"));
		dto.setEventID(rs.getInt(prex + "EVENTID"));
		dto.setHostID(rs.getInt(prex + "HOSTID"));
		dto.setCondID(rs.getInt(prex + "CondID"));
		dto.setOpiID(rs.getInt(prex + "OPI_ID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setCommandID(rs.getInt(prex + "COMMANDID"));
		dto.setEventClass(rs.getInt(prex + "EventClass"));
		dto.setCustomerID(rs.getInt(prex + "customerID"));
		dto.setScnr(rs.getString(prex + "Scnr"));
		dto.setStbnr(rs.getString(prex + "Stbnr"));
		dto.setProductID(rs.getInt(prex + "ProductID"));
		dto.setOldProductId(rs.getInt(prex + "OldProductID"));
		dto.setOldScnr(rs.getString(prex + "OldScnr"));
		dto.setOldStbnr(rs.getString(prex + "OldStbnr"));
		dto.setCreateTime(rs.getTimestamp(prex + "CreateTime"));
		dto.setDoneTime(rs.getTimestamp(prex + "DoneTime"));
		dto.setEntitlement(rs.getString(prex + "Entitlement"));
		return dto;
	}
	**/

	public static CARecvDTO fillCARecvDTO(ResultSet rs) throws SQLException {
		return fillCARecvDTO(rs, null);
	}

	public static CARecvDTO fillCARecvDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		CARecvDTO dto = new CARecvDTO();
		dto.setQueueID(rs.getInt(prex + "QUEUEID"));
		dto.setEventID(rs.getInt(prex + "EVENTID"));
		dto.setTransID(rs.getInt(prex + "TRANSID"));
		dto.setRecvData(rs.getString(prex + "RECVDATA"));
		dto.setRecvString(rs.getString(prex + "RECVSTRING"));
		dto.setErrorCode(rs.getString(prex + "ERRORCODE"));

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static CASentDTO fillCASentDTO(ResultSet rs) throws SQLException {
		return fillCASentDTO(rs, null);
	}

	public static CASentDTO fillCASentDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		CASentDTO dto = new CASentDTO();
		dto.setQueueID(rs.getInt(prex + "QUEUEID"));
		dto.setEventID(rs.getInt(prex + "EVENTID"));
		dto.setTransID(rs.getInt(prex + "TRANSID"));
		dto.setSentData(rs.getString(prex + "SENTDATA"));
		dto.setSendString(rs.getString(prex + "SENDSTRING"));
		dto.setSentTime(rs.getTimestamp(prex + "SENTTIME"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setHostID(rs.getInt(prex + "HOSTID"));
		dto.setErrorCode(rs.getString(prex + "ERRORCODE"));

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static CAQueueDTO fillCAQueueDTO(ResultSet rs) throws SQLException {
		return fillCAQueueDTO(rs, null);
	}

	public static CAQueueDTO fillCAQueueDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		CAQueueDTO dto = new CAQueueDTO();
		dto.setQueueID(rs.getInt(prex + "QUEUEID"));
		dto.setEventID(rs.getInt(prex + "EVENTID"));
		dto.setHostID(rs.getInt(prex + "HOSTID"));
		dto.setCondID(rs.getInt(prex + "CondID"));
		dto.setOpiID(rs.getInt(prex + "OPI_ID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setCommandID(rs.getInt(prex + "COMMANDID"));
		dto.setEventClass(rs.getInt(prex + "EventClass"));
		dto.setCustomerID(rs.getInt(prex + "customerID"));
		dto.setScnr(rs.getString(prex + "Scnr"));
		dto.setStbnr(rs.getString(prex + "Stbnr"));
		dto.setProductID(rs.getInt(prex + "ProductID"));
		dto.setOldProductId(rs.getInt(prex + "OldProductID"));
		dto.setOldScnr(rs.getString(prex + "OldScnr"));
		dto.setOldStbnr(rs.getString(prex + "OldStbnr"));
		dto.setCreateTime(rs.getTimestamp(prex + "CreateTime"));
		dto.setDoneTime(rs.getTimestamp(prex + "DoneTime"));
		dto.setEntitlement(rs.getString(prex + "Entitlement"));
		return dto;
	}

	public static LdapCommandDTO fillLdapCommandDTO(ResultSet rs)
			throws SQLException {
		return fillLdapCommandDTO(rs, null);
	}

	public static LdapCommandDTO fillLdapCommandDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		LdapCommandDTO dto = new LdapCommandDTO();
		dto.setCommandName(rs.getString(prex + "commandName"));
		dto.setCommandString(rs.getString(prex + "commandString"));
		dto.setDescription(rs.getString(prex + "description"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static LdapEventCmdMapDTO fillLdapEventCmdMapDTO(ResultSet rs)
			throws SQLException {
		return fillLdapEventCmdMapDTO(rs, null);
	}

	public static LdapEventCmdMapDTO fillLdapEventCmdMapDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		LdapEventCmdMapDTO dto = new LdapEventCmdMapDTO();
		dto.setCommandName(rs.getString(prex + "commandName"));
		dto.setEventClassID(rs.getInt(prex + "eventClassID"));
		dto.setPriority(rs.getInt(prex + "priority"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setMapID(rs.getInt(prex + "mapid"));
		dto.setConditionID(rs.getInt(prex + "conditionID"));

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static CustomerCampaignDTO fillCustomerCampaignDTO(ResultSet rs)
			throws SQLException {
		return fillCustomerCampaignDTO(rs, null);
	}

	public static CustomerCampaignDTO fillCustomerCampaignDTO(ResultSet rs,
			String prex) throws SQLException {
		CustomerCampaignDTO dto = new CustomerCampaignDTO();
		if (prex == null)
			prex = "";
		prex = prex.trim();
		dto.setCampaignID(rs.getInt(prex + "CAMPAIGNID"));
		dto.setCcID(rs.getInt(prex + "CCID"));
		dto.setCsiID(rs.getInt(prex + "CSIID"));
		dto.setGroupBargainID(rs.getInt(prex + "GROUPBARGAINID"));
		dto.setContactNo(rs.getString(prex + "CONTRACTNO"));
		dto.setCustomerID(rs.getInt(prex + "CUSTOMERID"));
		dto.setAccountID(rs.getInt(prex + "ACCOUNTID"));
		dto.setServiceAccountID(rs.getInt(prex + "SERVICEACCOUNTID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDateFrom(rs.getTimestamp(prex + "DATEFROM"));
		dto.setDateTo(rs.getTimestamp(prex + "DATETO"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setAutoExtendFlag(rs.getString(prex + "AUTOEXTENDFLAG"));
		dto.setComments(rs.getString(prex + "COMMENTS"));
		dto.setCreatreOpID(rs.getInt(prex + "CREATREOPID"));
		dto.setCreateOrgID(rs.getInt(prex + "CREATEORGID"));
		dto.setPrePaidTo(rs.getTimestamp(prex + "PREPAIDTO"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		dto.setAllowAlter(rs.getString(prex + "ALLOWALTER"));
		dto.setAllowClose(rs.getString(prex + "ALLOWCLOSE"));
		dto.setAllowPause(rs.getString(prex + "ALLOWPAUSE"));
		dto.setAllowTransfer(rs.getString(prex + "ALLOWTRANSFER"));
		dto.setAllowTransition(rs.getString(prex + "ALLOWTRANSITION"));
		dto.setNbrDate(rs.getTimestamp(prex + "NBRDATE"));
		return dto;
	}

	public static CasubentitlementDTO fillCasubentitlementDTO(ResultSet rs)
			throws SQLException {
		return fillCasubentitlementDTO(rs, null);
	}

	public static CasubentitlementDTO fillCasubentitlementDTO(ResultSet rs,
			String prex) throws SQLException {
		CasubentitlementDTO dto = new CasubentitlementDTO();
		if (prex == null)
			prex = "";
		prex = prex.trim();
		dto.setCardsn(rs.getString(prex + "CARDSN"));
		dto.setHostId(rs.getInt(prex + "HOSTID"));
		dto.setOpiID(rs.getInt(prex + "OPI_ID"));
		dto.setCaproductid(rs.getString(prex + "CAPRODUCTID"));
		dto.setSubscriberId(rs.getInt(prex + "SUBSCRIBERID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static VOIPEventCmdDTO fillVOIPEventCmdMapDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		VOIPEventCmdDTO dto = new VOIPEventCmdDTO();
		dto.setQueueID(rs.getInt(prex + "queueid"));
		dto.setIfID(rs.getInt(prex + "ifid"));
		dto.setCmdID(rs.getInt(prex + "cmdid"));
		dto.setEventID(rs.getInt(prex + "eventid"));

		dto.setCreateTime(rs.getTimestamp(prex + "createtime"));
		dto.setDoneTime(rs.getTimestamp(prex + "donetime"));
		dto.setStatus(rs.getString(prex + "status"));
		dto.setIfName(rs.getString(prex + "ifname"));
		dto.setCmdName(rs.getString(prex + "commandname"));
		return dto;
	}

	public static VOIPEventCmdDTO fillVOIPEventCmdMapDTO(ResultSet rs)
			throws SQLException {
		return fillVOIPEventCmdMapDTO(rs, null);
	}

	public static VOIPCmdProDTO fillVOIPCmdListDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		VOIPCmdProDTO dto = new VOIPCmdProDTO();
		dto.setSeqNo(rs.getInt(prex + "seqno"));
		dto.setIfID(rs.getInt(prex + "ifid"));
		dto.setIfName(rs.getString(prex + "ifname"));
		dto.setQueueID(rs.getInt(prex + "queueid"));
		dto.setEventID(rs.getInt(prex + "eventid"));
		dto.setCmdID(rs.getInt(prex + "cmdid"));
		dto.setPhoneNo(rs.getInt(prex + "phoneno") + "");
		dto.setCardNo(rs.getInt(prex + "cardno") + "");
		dto.setCmdCode(rs.getString(prex + "cmdcode"));
		dto.setTranID(rs.getInt(prex + "tranid"));
		dto.setSendDate(rs.getDate(prex + "senddata"));
		dto.setSendTime(rs.getTimestamp(prex + "sendtime"));
		dto.setRevDate(rs.getDate(prex + "revdata"));
		dto.setRevTime(rs.getTimestamp(prex + "revtime"));
		dto.setProcessResult(rs.getString(prex + "processresult"));
		dto.setDt_Create(rs.getTimestamp(prex + "dt_create"));
		dto.setDt_Lastmod(rs.getTimestamp(prex + "dt_lastmod"));

		dto.setCmdName(rs.getString(prex + "commandname"));

		return dto;
	}

	public static VOIPCmdProDTO fillVOIPCmdListDTO(ResultSet rs)
			throws SQLException {
		return fillVOIPCmdListDTO(rs, null);
	}

	public static VOIPCmdProDTO fillVOIPCmdHisDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		VOIPCmdProDTO dto = new VOIPCmdProDTO();
		dto.setSeqNo(rs.getInt(prex + "SEQNO"));
		dto.setIfID(rs.getInt(prex + "IFID"));
		dto.setIfName(rs.getString(prex + "IFNAME"));
		dto.setQueueID(rs.getInt(prex + "QUEUEID"));
		dto.setEventID(rs.getInt(prex + "EVENTID"));
		dto.setCmdID(rs.getInt(prex + "CMDID"));
		dto.setPhoneNo(rs.getInt(prex + "PHONENO") + "");
		dto.setCardNo(rs.getInt(prex + "CARDNO") + "");
		dto.setCmdCode(rs.getString(prex + "CMDCODE"));
		dto.setTranID(rs.getInt(prex + "TRANID"));
		dto.setSendDate(rs.getDate(prex + "SENDDATA"));
		dto.setSendTime(rs.getTimestamp(prex + "SENDTIME"));
		dto.setRevDate(rs.getDate(prex + "REVDATA"));
		dto.setRevTime(rs.getTimestamp(prex + "REVTIME"));
		dto.setProcessResult(rs.getString(prex + "PROCESSRESULT"));
		dto.setDt_Create(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDt_Lastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setCmdName(rs.getString(prex + "commandname"));
		return dto;
	}

	public static VOIPCmdProDTO fillVOIPCmdHisDTO(ResultSet rs)
			throws SQLException {
		return fillVOIPCmdHisDTO(rs, null);
	}

	public static ServiceAccountDeviceDTO fillServiceAccountDeviceDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		ServiceAccountDeviceDTO dto = new ServiceAccountDeviceDTO();
		dto.setServiceAccountID(rs.getInt(prex + "serviceaccountid"));
		dto.setServiceID(rs.getInt(prex + "serviceid"));
		dto.setCustomerID(rs.getInt(prex + "customerid"));
		dto.setServiceCode(rs.getString(prex + "servicecode"));
		dto.setCreateTime(rs.getTimestamp(prex + "createtime"));
		dto.setStatus(rs.getString(prex + "status"));
		dto.setDescription(rs.getString(prex + "description"));
		dto.setDt_create(rs.getTimestamp(prex + "dt_create"));
		dto.setDt_lastmod(rs.getTimestamp(prex + "dt_lastmod"));
		return dto;
	}

	public static ServiceAccountDeviceDTO fillServiceAccountDeviceDTO(
			ResultSet rs) throws SQLException {
		return fillServiceAccountDeviceDTO(rs, null);
	}

	public static VoiceFriendPhoneNoDTO fillVoiceFriendPhoneNoDTO(ResultSet rs)
			throws SQLException {
		return fillVoiceFriendPhoneNoDTO(rs, null);
	}

	public static VoiceFriendPhoneNoDTO fillVoiceFriendPhoneNoDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		VoiceFriendPhoneNoDTO dto = new VoiceFriendPhoneNoDTO();
		dto.setSeqno(rs.getInt(prex + "SEQNO"));
		dto.setServiceAccountID(rs.getInt(prex + "SERVICEACCOUNTID"));
		dto.setCustomerID(rs.getInt(prex + "CUSTOMERID"));
		dto.setCountryCode(rs.getString(prex + "COUNTRYCODE"));
		dto.setAreaCode(rs.getString(prex + "AREACODE"));
		dto.setResourceItemID(rs.getInt(prex + "RESOURCEITEMID"));
		dto.setPhoneNo(rs.getString(prex + "PHONENO"));
		dto.setDisctplan(rs.getInt(prex + "DISCTPLAN"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static VOIPProductDTO fillVOIPProductDTO(ResultSet rs)
			throws SQLException {
		// TODO Auto-generated method stub
		return fillVOIPProductDTO(rs, null);
	}

	public static VOIPProductDTO fillVOIPProductDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		VOIPProductDTO dto = new VOIPProductDTO();
		dto.setProductID(rs.getInt("PRODUCTID"));
		dto.setPropertyName(rs.getString("PROPERTYNAME"));
		dto.setSssrvCode(rs.getString("SSSRVCODE"));
		dto.setSssrvType(rs.getString("SSSRVTYPE"));
		dto.setDescription(rs.getString("DESCRIPTION"));
		return dto;
	}

	public static VOIPConditionDTO fillVOIPConditionDTO(ResultSet rs)
			throws SQLException {
		// TODO Auto-generated method stub
		return fillVOIPConditionDTO(rs, null);
	}

	public static VOIPConditionDTO fillVOIPConditionDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		VOIPConditionDTO dto = new VOIPConditionDTO();
		dto.setConditionID(rs.getInt("CONDITIONID"));
		dto.setConditionName(rs.getString("CONDITIONNAME"));
		dto.setConditionString(rs.getString("CONDITIONSTRING"));
		dto.setDescription(rs.getString("DESCRIPTION"));
		return dto;
	}

	public static VOIPGatewayDTO fillVOIPGatewayDTO(ResultSet rs)
			throws SQLException {
		// TODO Auto-generated method stub
		return fillVOIPGatewayDTO(rs, null);
	}

	public static VOIPGatewayDTO fillVOIPGatewayDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		VOIPGatewayDTO dto = new VOIPGatewayDTO();
		dto.setDeviceModel(rs.getString("DEVICEMODEL"));
		dto.setDevsType(rs.getString("DEVSTYPE"));
		dto.setDescription(rs.getString("DESCRIPTION"));
		return dto;
	}




	public static VOIPRecordDTO fillVOIPRecordDTO(ResultSet rs)
			throws SQLException {
		return fillVOIPRecordDTO(rs, null);
	}

	public static VOIPRecordDTO fillVOIPRecordDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		VOIPRecordDTO dto = new VOIPRecordDTO();
		dto.setAa_no(rs.getInt(prex + "AA_NO"));

		dto.setDate1(rs.getTimestamp(prex + "DATE1"));
		dto.setDate2(rs.getTimestamp(prex + "DATE2"));

		dto.setRealValue(rs.getDouble(prex + "VALUE"));

		dto.setPointOrigin(rs.getString(prex + "POINT_ORIGIN"));
		dto.setPointTarget(rs.getString(prex + "POINT_TARGET"));
		dto.setUnits(rs.getInt(prex + "UNITS"));
		dto.setRateAmount(rs.getDouble(prex + "RATE_AMOUNT"));
		dto.setDiscount(rs.getDouble(prex + "DISCOUNT"));
		dto.setUnitsCredited(rs.getInt(prex + "UNIT_CREDITED"));
		dto.setAcctItemTypeID(rs.getString(prex + "AcctItemTypeID"));
		return dto;
	}

	public static CustomerComplainDTO fillCustomerComplainDTO(ResultSet rs)
			throws SQLException {
		return fillCustomerComplainDTO(rs, null);
	}

	public static CustomerComplainDTO fillCustomerComplainDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		CustomerComplainDTO dto = new CustomerComplainDTO();
		dto.setCustComplainId(rs.getInt(prex + "CUSTCOMPLAINID"));
		dto.setCustomerId(rs.getInt(prex + "CUSTOMERID"));
		dto.setType(rs.getString(prex + "TYPE"));
		dto.setContent(rs.getString(prex + "CONTENT"));
		dto.setRequest(rs.getString(prex + "REQUEST"));
		dto.setComplainedOrgId(rs.getInt(prex + "COMPLAINEDORGID"));
		dto.setComplainedPerson(rs.getString(prex + "COMPLAINEDPERSON"));
		dto.setContactPerson(rs.getString(prex + "CONTACTPERSON"));
		dto.setContactPhone(rs.getString(prex + "CONTACTPHONE"));
		dto.setCallBackFlag(rs.getString(prex + "CALLBACKFLAG"));
		dto.setCallBackDate(rs.getTimestamp(prex + "CALLBACKDATE"));
		dto.setCurrentWorkOrgID(rs.getInt(prex + "CURRENTWORKORGID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static CustComplainProcessDTO fillCustComplainProcessDTO(ResultSet rs)
			throws SQLException {
		return fillCustComplainProcessDTO(rs, null);
	}

	public static CustComplainProcessDTO fillCustComplainProcessDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		CustComplainProcessDTO dto = new CustComplainProcessDTO();
		dto.setId(rs.getInt(prex + "ID"));
		dto.setCustComplainId(rs.getInt(prex + "CUSTCOMPLAINID"));
		dto.setCurrentOrgId(rs.getInt(prex + "CURRENTORGID"));
		dto.setCurrentOperatorId(rs.getInt(prex + "CURRENTOPERATORID"));
		dto.setNextOrgId(rs.getInt(prex + "NEXTORGID"));
		dto.setAction(rs.getString(prex + "ACTION"));
		dto.setCreateDate(rs.getTimestamp(prex + "CREATEDATE"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	public static DeviceMatchToProductDTO fillDeviceMatchToProductDTO(ResultSet rs)
	throws SQLException {
		return fillDeviceMatchToProductDTO(rs, null);
	}

	public static DeviceMatchToProductDTO fillDeviceMatchToProductDTO(
		ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		DeviceMatchToProductDTO dto = new DeviceMatchToProductDTO();
		dto.setProductId(rs.getInt(prex + "ProductID"));
		dto.setDeviceModel(rs.getString(prex + "DeviceModel"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static RoleOrganizationDTO fillRoleOrganizationDTO(ResultSet rs)
	throws SQLException {
		return fillRoleOrganizationDTO(rs, null);
	}

	public static RoleOrganizationDTO fillRoleOrganizationDTO(
		ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		RoleOrganizationDTO dto = new RoleOrganizationDTO();
		dto.setId(rs.getInt(prex + "ID"));
		dto.setDistrictId(rs.getInt(prex + "DistrictId"));
		dto.setServiceOrgId(rs.getInt(prex + "SERVICEORGID"));
		dto.setSaProductId(rs.getInt(prex + "SaProductId"));
		dto.setOrgRole(rs.getString(prex + "ORGROLE"));
		dto.setTroubleSubType(rs.getString(prex + "troubleSubType"));
		dto.setTroubleType(rs.getString(prex + "troubleType"));
		dto.setDiagnosisResult(rs.getString(prex + "diagnosisResult"));
		dto.setIsFirst(rs.getString(prex + "ISFIRST"));
		 
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}

	public static DisplayControlDTO fillDisplayControlDTO(ResultSet rs)
	throws SQLException {
		return fillDisplayControlDTO(rs, null);
	}

	public static DisplayControlDTO fillDisplayControlDTO(
		ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		DisplayControlDTO dto = new DisplayControlDTO();
		dto.setSeqno(rs.getInt(prex+"seqno"));
		dto.setControlID(rs.getString(prex+"controlID"));
		dto.setDescription(rs.getString(prex+"description"));
		dto.setIsVisible(rs.getString(prex+"isVisible"));
		dto.setReferto(rs.getString(prex+"referto"));
		dto.setDisposeOrder(rs.getString(prex+"disposeOrder"));
		dto.setPostfix(rs.getString(prex+"postfix"));
		dto.setClassName(rs.getString(prex+"className"));
		dto.setFieldName(rs.getString(prex+"fieldName"));
		dto.setOperate(rs.getString(prex+"operate"));
		dto.setValue(rs.getString(prex+"value"));
		dto.setDt_Create(rs.getTimestamp(prex+"dt_create"));
		dto.setDt_Lastmod(rs.getTimestamp(prex+"dt_lastmod"));
		return dto;
	}
	public static MenuConfigurationDTO fillMenuConfigurationDTO(ResultSet rs)
	throws SQLException {
		return fillMenuConfigurationDTO(rs, null);
	}

	public static MenuConfigurationDTO fillMenuConfigurationDTO(
		ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		MenuConfigurationDTO dto = new MenuConfigurationDTO();
		dto.setMenuName(rs.getString(prex+"MENUNAME"));
		dto.setMenuAction(rs.getString(prex+"MENUACTION"));
		dto.setMenuWidth(rs.getInt(prex+"MENUWIDTH"));
		dto.setMenuHeigth(rs.getInt(prex+"MENUHEIGTH"));
		dto.setParameterList(rs.getString(prex+"PARAMETERLIST"));
		dto.setMenuKey(rs.getString(prex+"MENUKEY"));
		dto.setParentMenu(rs.getString(prex+"PARENTMENU"));
		dto.setMenuType(rs.getString(prex+"MENUTYPE"));
		dto.setPriority(rs.getInt(prex+"PRIORITY"));
		dto.setSubMenuWidth(rs.getInt(prex+"SubMenuWidth"));
/*		下面这些字段是扩展用的，目前暂时不设置值，以后需要再放开
		dto.setMenuDescription(rs.getString(prex+"MENUDESCRIPTION"));

		dto.setStatus(rs.getString(prex+"STATUS"));
*/
		return dto;
	}

	public static DynamicFormSettingDTO fillDynamicFormSettingDTO(ResultSet rs)
	throws SQLException {
		return fillDynamicFormSettingDTO(rs, null);
	}

	public static DynamicFormSettingDTO fillDynamicFormSettingDTO(
		ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		DynamicFormSettingDTO dto = new DynamicFormSettingDTO();
		dto.setFormName(rs.getString(prex+"formName"));
		dto.setElementName(rs.getString(prex+"elementName"));
		dto.setDescription(rs.getString(prex+"description"));
		dto.setIsVisible(rs.getString(prex+"isVisible"));
		dto.setArrangeOrder(rs.getInt(prex+"arrangeOrder"));
		dto.setInfoBlock(rs.getString(prex+"infoBlock"));
		dto.setDt_create(rs.getTimestamp(prex+"dt_create"));
		dto.setDt_lastmod(rs.getTimestamp(prex+"dt_lastmod"));
		return dto;
	}
	public static FutureRightDTO fillFutureRightDTO(ResultSet rs)
	throws SQLException {
		return fillMFutureRightDTO(rs, null);
	}

	public static FutureRightDTO fillMFutureRightDTO(
		ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		FutureRightDTO dto =new  FutureRightDTO();
		 dto.setCancelDate(rs.getTimestamp("CANCELDATE"));
		 dto.setCancelOpID(rs.getInt("CANCELOPID"));
		 dto.setCancelOrgID(rs.getInt("CANCELORGID"));
		 dto.setCreateDate(rs.getTimestamp("CREATEDATE"));
		 dto.setCreateOpID(rs.getInt("CREATEOPID"));
		 dto.setCreateOrgID(rs.getInt("CREATEORGID"));
		 dto.setCsiID(rs.getInt("CSIID"));
		 dto.setCustomerID(rs.getInt("CUSTOMERID"));
		 dto.setDescription(rs.getString("DESCRIPTION"));
		 dto.setDtCreate(rs.getTimestamp("DT_CREATE"));
		 dto.setDtLastmod(rs.getTimestamp("DT_LASTMOD"));
		 dto.setExcuteDate(rs.getTimestamp("EXCUTEDATE"));
		 dto.setExcuteOpID(rs.getInt("EXCUTEOPID"));
		 dto.setExcuteOrgID(rs.getInt("EXCUTEORGID"));
		 dto.setLockToDate(rs.getTimestamp("LOCKTODATE"));
		 dto.setReferSheetID(rs.getString("REFERSHEETID"));
		 dto.setSeqNo(rs.getInt("SEQNO"));
		 dto.setStatus(rs.getString("STATUS"));
		 dto.setAccountID(rs.getInt("ACCOUNTID"));
		 dto.setValue(rs.getDouble("VALUE"));
		return dto;
	}

	public static BillBoardDTO fillBillBoardDTO(
			ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return fillBillBoardDTO(rs, null);
	}

	public static BillBoardDTO fillBillBoardDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		BillBoardDTO dto = new BillBoardDTO();
		dto.setSeqNo(rs.getInt("SEQNO"));
		dto.setDescription(rs.getString("description"));
		dto.setName(rs.getString("name"));
		dto.setStatus(rs.getString("status"));
		dto.setDateFrom(rs.getTimestamp("DateFrom"));
		dto.setPublishPerson(rs.getString("PublishPerson"));
		dto.setPublishReason(rs.getString("PublishReason"));
		dto.setGrade(rs.getString("grade"));
		dto.setPublishDate(rs.getTimestamp("PublishDate"));
		dto.setDateTo(rs.getTimestamp("DateTo"));
		dto.setDtCreate(rs.getTimestamp("DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp("DT_LASTMOD"));
		return dto;
	}

	public static DynamicShowAttributesDTO fillDynamicShowAttributesDTO(ResultSet rs)
	throws SQLException {
		return fillDynamicShowAttributesDTO(rs, null);
	}
	public static DynamicShowAttributesDTO fillDynamicShowAttributesDTO(ResultSet rs,String prex)
	throws SQLException {
		if (prex == null) prex = "";
		DynamicShowAttributesDTO dto =new DynamicShowAttributesDTO();
		dto.setDtoName(rs.getString(prex+"DTONAME"));
		dto.setLabelCols(rs.getInt(prex+"LABELCOLS"));
		dto.setLabelName(rs.getString(prex+"LABELNAME"));
		dto.setLabelOrder(rs.getInt(prex+"LABELORDER"));
		dto.setTextName(rs.getString(prex+"TEXTNAME"));
		dto.setTextValue(rs.getString(prex+"TEXTVALUE"));
		dto.setValueSourceType(rs.getString(prex+"VALUESOURCETYPE"));
		dto.setValueTerm(rs.getString(prex+"VALUETERM"));
		return dto;
	}
	public static SystemSettingDTO fillSystemSettingDTO(ResultSet rs)
	throws SQLException {
		return fillSystemSettingDTO(rs, null);
	}
	public static SystemSettingDTO fillSystemSettingDTO(ResultSet rs,String prex)
	throws SQLException {
		if (prex == null) prex = "";
		SystemSettingDTO dto =new SystemSettingDTO();
		dto.setDescription(rs.getString(prex+"Description"));
		dto.setName(rs.getString(prex+"Name"));
		dto.setStatus(rs.getString(prex+"Status"));
		dto.setValue(rs.getString(prex+"Value"));
		dto.setValueType(rs.getString(prex+"ValueType"));

		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		return dto;
	}


	public static CsiCustProductInfoDTO fillCsiCustProductInfoDTO(ResultSet rs)
	throws SQLException {
		return fillCsiCustProductInfoDTO(rs,null);
	}
	public static CsiCustProductInfoDTO fillCsiCustProductInfoDTO(ResultSet rs,String prex)
	throws SQLException {
		if (prex ==null) prex ="";
		CsiCustProductInfoDTO dto =new CsiCustProductInfoDTO();
		dto.setAction(rs.getString(prex+"ACTION"));
		dto.setCsiID(rs.getInt(prex+"CSIID"));
		dto.setCustProductID(rs.getInt(prex+"CUSTPRODUCTID"));
		dto.setDestProductID(rs.getInt(prex+"DESTPRODUCTID"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		dto.setId(rs.getInt(prex+"ID"));
		dto.setProductID(rs.getInt(prex+"PRODUCTID"));
		dto.setReferAccountID(rs.getInt(prex+"REFERACCOUNTID"));
		dto.setReferCampaignID(rs.getInt(prex+"REFERCAMPAIGNID"));
		dto.setReferCCID(rs.getInt(prex+"REFERCCID"));
		dto.setReferContractNo(rs.getString(prex+"REFERCONTRACTNO"));
		dto.setReferDestDeviceID(rs.getInt(prex+"REFERDESTDEVICEID"));
		dto.setReferDeviceID(rs.getInt(prex+"REFERDEVICEID"));
		dto.setReferGroupBargainID(rs.getInt(prex+"REFERGROUPBARGAINID"));
		dto.setReferPackageID(rs.getInt(prex+"REFERPACKAGEID"));
		dto.setReferServiceAccountID(rs.getInt(prex+"REFERSERVICEACCOUNTID"));
		dto.setStatus(rs.getString(prex+"STATUS"));
		return dto;
	}
	public static CustServiceInteractionDTO fillCSIDTO(ResultSet rs) throws SQLException{
		return fillCSIDTO(rs,null);
	}
	public static CustServiceInteractionDTO fillCSIDTO(ResultSet rs,String prex)throws SQLException{
		if(prex == null)
			prex="";
		CustServiceInteractionDTO dto=new CustServiceInteractionDTO();
		dto.setId(rs.getInt(prex+"id"));
		dto.setReferSheetID(rs.getString(prex+"refersheetid"));
		dto.setType(rs.getString(prex+"type"));
		dto.setCreateTime(rs.getTimestamp(prex+"createtime"));
		dto.setCustomerID(rs.getInt(prex+"customerid"));
		dto.setAccountID(rs.getInt(prex+"accountid"));
		dto.setAgentName(rs.getString(prex+"agentname"));
		dto.setAgentCardType(rs.getString(prex+"agentcardtype"));
		dto.setAgentCardID(rs.getString(prex+"agentcardid"));
		dto.setGroupCampaignID(rs.getInt(prex+"groupcampaignid"));
		dto.setInstallationType(rs.getString(prex+"installationtype"));
		dto.setReferJobCardID(rs.getInt(prex+"referjobcardid"));
		dto.setReferBookingSheetID(rs.getInt(prex+"referbookingsheetid"));
		dto.setContactPerson(rs.getString(prex+"contactperson"));
		dto.setContactPhone(rs.getString(prex+"contactphone"));
		dto.setPreferedDate(rs.getTimestamp(prex+"prefereddate"));
		dto.setPreferedTime(rs.getString(prex+"preferedtime"));
		dto.setPaymentStatus(rs.getString(prex+"paymentstatus"));
		dto.setScheduleAction(rs.getString(prex+"scheduleaction"));
		dto.setScheduleTime(rs.getTimestamp(prex+"scheduletime"));
		dto.setCallBackFlag(rs.getString(prex+"callbackflag"));
		dto.setCallBackDate(rs.getTimestamp(prex+"callbackdate"));
		dto.setStatusReason(rs.getString(prex+"statusreason"));
		dto.setStatus(rs.getString(prex+"status"));
		dto.setComments(rs.getString(prex+"comments"));
		dto.setDtCreate(rs.getTimestamp(prex+"dt_create"));
		dto.setDtLastmod(rs.getTimestamp(prex+"dt_lastmod"));
		dto.setCreateORGId(rs.getInt(prex+"createorgid"));
		dto.setCreateOperatorId(rs.getInt(prex+"createoperatorid"));
		return dto;
	}

	public static BatchJobDTO fillBatchJobDTO(ResultSet rs) throws SQLException{
		return fillBatchJobDTO(rs,null);
	}
	public static BatchJobDTO fillBatchJobDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		BatchJobDTO dto =new BatchJobDTO();
		dto.setBatchId(rs.getInt(prex+"BATCHID"));
		dto.setCreateOpId(rs.getInt(prex+"CREATEOPID"));
		dto.setCreateTime(rs.getTimestamp(prex+"CREATETIME"));
		dto.setDescription(rs.getString(prex+"DESCRIPTION"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		dto.setExecuteEndTime(rs.getTimestamp(prex+"EXECUTEENDTIME"));
		dto.setExecuteStartTime(rs.getTimestamp(prex+"EXECUTESTARTTIME"));
		dto.setFailureRecordNo(rs.getInt(prex+"FAILURERECORDNO"));
		dto.setJobType(rs.getString(prex+"JOBTYPE"));
		dto.setName(rs.getString(prex+"NAME"));
		dto.setReasonCode(rs.getString(prex+"REASONCODE"));
		dto.setReferId(rs.getInt(prex+"REFERID"));
		dto.setReferType(rs.getString(prex+"REFERTYPE"));
		dto.setScheduleTime(rs.getTimestamp(prex+"SCHEDULETIME"));
		dto.setScheduleType(rs.getString(prex+"SCHEDULETYPE"));
		dto.setStatus(rs.getString(prex+"STATUS"));
		dto.setSuccessRecordNo(rs.getInt(prex+"SUCCESSRECORDNO"));
		dto.setTotoalRecordNo(rs.getInt(prex+"TOTOALRECORDNO"));
		return dto;
	}

	public static BatchJobItemDTO fillBatchJobItemDTO(ResultSet rs) throws SQLException{
		return fillBatchJobItemDTO(rs,null);
	}

	public static BatchJobItemDTO fillBatchJobItemDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		BatchJobItemDTO dto =new BatchJobItemDTO();
		dto.setAccountId(rs.getInt(prex+"ACCOUNTID"));
		dto.setBatchId(rs.getInt(prex+"BATCHID"));
		dto.setCcId(rs.getInt(prex+"CCID"));
		dto.setCustomerId(rs.getInt(prex+"CUSTOMERID"));
		dto.setCustPackageId(rs.getInt(prex+"CUSTPACKAGEID"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		dto.setItemNO(rs.getInt(prex+"ITEMNO"));
		dto.setPsId(rs.getInt(prex+"PSID"));
		dto.setRcdData(rs.getString(prex+"RCDDATA"));
		dto.setStatus(rs.getString(prex+"STATUS"));
		dto.setStatusTime(rs.getTimestamp(prex+"STATUSTIME"));
		dto.setUserId(rs.getInt(prex+"USERID"));
		return dto;
	}

	public static CampaignCondPackageDTO fillCampaignCondPackageDTO(ResultSet rs) throws SQLException{
		return fillCampaignCondPackageDTO(rs,null);
	}

	public static CampaignCondPackageDTO fillCampaignCondPackageDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		CampaignCondPackageDTO dto =new CampaignCondPackageDTO();
		dto.setCampaignID(rs.getInt(prex+"CampaignID"));
		dto.setDtCreate(rs.getTimestamp(prex+"dt_create"));
		dto.setDtLastmod(rs.getTimestamp(prex+"dt_lastmod"));
		dto.setExistingFlag(rs.getString(prex+"ExistingFlag"));
		dto.setHasAllFlag(rs.getString(prex+"HasAllFlag"));
		dto.setNewPurchaseFlag(rs.getString(prex+"NewPurchaseFlag"));
		dto.setPackageIdList(rs.getString(prex+"PackageIDList"));
		dto.setPackageNum(rs.getInt(prex+"PackageNum"));
		dto.setSeqNo(rs.getInt(prex+"SeqNo"));
		return dto;
	}

	public static CampaignCondProductDTO fillCampaignCondProductDTO(ResultSet rs) throws SQLException{
		return fillCampaignCondProductDTO(rs,null);
	}

	public static CampaignCondProductDTO fillCampaignCondProductDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		CampaignCondProductDTO dto =new CampaignCondProductDTO();
		dto.setCampaignID(rs.getInt(prex+"CampaignID"));
		dto.setDtCreate(rs.getTimestamp(prex+"dt_create"));
		dto.setDtLastmod(rs.getTimestamp(prex+"dt_lastmod"));
		dto.setExistingFlag(rs.getString(prex+"ExistingFlag"));
		dto.setHasAllFlag(rs.getString(prex+"HasAllFlag"));
		dto.setNewCaptureFlag(rs.getString(prex+"NewCaptureFlag"));
		dto.setProductIdList(rs.getString(prex+"ProductIDList"));
		dto.setProductNum(rs.getInt(prex+"ProductNum"));
		dto.setSeqNo(rs.getInt(prex+"SeqNo"));
		return dto;
	}

	public static CampaignCondCampaignDTO fillCampaignCondCampaignDTO(ResultSet rs) throws SQLException{
		return fillCampaignCondCampaignDTO(rs,null);
	}

	public static CampaignCondCampaignDTO fillCampaignCondCampaignDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		CampaignCondCampaignDTO dto =new CampaignCondCampaignDTO();
		dto.setCampaignID(rs.getInt(prex +"CampaignID"));
		dto.setCampaignIDList(rs.getString(prex +"CampaignIDList"));
		dto.setCampaignNum(rs.getInt(prex +"CampaignNum"));
		dto.setDtCreate(rs.getTimestamp(prex +"Dt_Create"));
		dto.setDtLastmod(rs.getTimestamp(prex +"Dt_Lastmod"));
		dto.setExistingFlag(rs.getString(prex +"ExistingFlag"));
		dto.setHasAllFlag(rs.getString(prex +"HasAllFlag"));
		dto.setNewCaptureFlag(rs.getString(prex +"NewCaptureFlag"));
		dto.setSeqNo(rs.getInt(prex +"SeqNo"));
		return dto;
	}
	public static BundlePrepaymentDTO fillBundlePrepaymentDTO(ResultSet rs) throws SQLException{
		return fillBundlePrepaymentDTO(rs,null);
	}

	public static BundlePrepaymentDTO fillBundlePrepaymentDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		BundlePrepaymentDTO dto =new BundlePrepaymentDTO();
		dto.setCampaignId(rs.getInt(prex+"CampaignID"));
		dto.setDtCreate(rs.getTimestamp(prex+"dt_create"));
		dto.setDtLastmod(rs.getTimestamp(prex+"dt_lastmod"));
		dto.setBundlePrepaymentPlanId(rs.getInt(prex+"BundlePrepaymentPlanID"));
		dto.setAmount(rs.getDouble(prex+"Amount"));
		dto.setAcctItemTypeId(rs.getString(prex+"AcctItemTypeID"));
		dto.setFeeType(rs.getString(prex+"FeeType"));
		dto.setTargetBundleId(rs.getInt(prex+"TargetBundleID"));
		dto.setTimeUnitLengthNumber(rs.getInt(prex+"TimeUnitLengthNumber"));
		dto.setTimeUnitLengthType(rs.getString(prex+"TimeUnitLengthType"));
		return dto;
	}
	public static CampaignAgmtCampaignDTO fillCampaignAgmtCampaignDTO(ResultSet rs) throws SQLException{
		return fillCampaignAgmtCampaignDTO(rs,null);
	}

	public static CampaignAgmtCampaignDTO fillCampaignAgmtCampaignDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		CampaignAgmtCampaignDTO dto =new CampaignAgmtCampaignDTO();
		dto.setCampaignId(rs.getInt(prex+"CampaignID"));
		dto.setDtCreate(rs.getTimestamp(prex+"dt_create"));
		dto.setDtLastmod(rs.getTimestamp(prex+"dt_lastmod"));
		 
		dto.setTargetBundleId(rs.getInt(prex+"TargetBundleID"));
		dto.setTimeLengthUnitNumber(rs.getInt(prex+"TimeLengthUnitNumber"));
		dto.setTimeLengthUnitType(rs.getString(prex+"timeLengthUnitType"));
		return dto;
	}
	public static BundlePaymentMethodDTO fillBundlePaymentMethodDTO(ResultSet rs) throws SQLException{
		return fillBundlePaymentMethodDTO(rs,null);
	}

	public static BundlePaymentMethodDTO fillBundlePaymentMethodDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		BundlePaymentMethodDTO dto =new BundlePaymentMethodDTO();
		dto.setBundleID(rs.getInt(prex+"BundleID"));
		dto.setDtCreate(rs.getTimestamp(prex+"dt_create"));
		dto.setDtLastmod(rs.getTimestamp(prex+"dt_lastmod"));
		dto.setBundlePaymentName(rs.getString(prex+"BundlePaymentName"));
		dto.setRfBillingCycleFlag(rs.getString(prex+"RfBillingCycleFlag"));

		dto.setTimeUnitLengthNumber(rs.getInt(prex+"TimeUnitLengthNumber"));
		dto.setTimeUnitLengthType(rs.getString(prex+"TimeUnitLengthType"));
		return dto;
	}
	public static VODQueueDTO fillVODQueueDTO(ResultSet rs) throws SQLException {
		return fillVODQueueDTO(rs, null);
	}

	public static VODQueueDTO fillVODQueueDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		VODQueueDTO dto = new VODQueueDTO();
		dto.setQueueID(rs.getInt(prex + "QUEUEID"));
		dto.setEventID(rs.getInt(prex + "EVENTID"));
		dto.setTransactionSentList(rs.getString(prex + "TransactionSentList"));

		dto.setDoneTime(rs.getTimestamp(prex + "DoneTime"));
		dto.setCreateTime(rs.getTimestamp(prex + "CreateTime"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setHostID(rs.getInt(prex + "HOSTID"));

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	public static LdapQueueDTO fillLdapQueueDTO(ResultSet rs) throws SQLException {
		return fillLdapQueueDTO(rs, null);
	}

	public static LdapQueueDTO fillLdapQueueDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		LdapQueueDTO dto = new LdapQueueDTO();
		dto.setQueueID(rs.getInt(prex + "QUEUEID"));
		dto.setEventID(rs.getInt(prex + "EVENTID"));
        dto.setEventClass(rs.getInt(prex + "eventClass")); 
        dto.setPriority(rs.getInt(prex + "priority")); 
        dto.setCommandName(rs.getString(prex + "commandName")); 
        dto.setLdapProduct(rs.getString(prex + "ldapProduct")); 
        dto.setMacAddress(rs.getString(prex + "macAddress")); 
        dto.setInterMacAddress(rs.getString(prex + "interMacAddress")); 
		dto.setDoneTime(rs.getTimestamp(prex + "DoneTime"));
		dto.setCreateTime(rs.getTimestamp(prex + "CreateTime"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setHostID(rs.getInt(prex + "HOSTID"));
		dto.setServiceAccountID(rs.getInt(prex + "serviceAccountID"));
		dto.setDeviceID(rs.getInt(prex + "deviceID"));
		 
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	public static DeviceClassDTO fillDeviceClassDTO(ResultSet rs) throws SQLException {
		return fillDeviceClassDTO(rs, null);
	}

	public static DeviceClassDTO fillDeviceClassDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		DeviceClassDTO dto = new DeviceClassDTO();
		dto.setDeviceClass(rs.getString("DEVICECLASS"));
		dto.setDescription(rs.getString("DESCRIPTION"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	public static FeeSplitPlanDTO fillFeeSplitPlanDTO(ResultSet rs) throws SQLException {
		return fillFeeSplitPlanDTO(rs, null);
	}

	public static FeeSplitPlanDTO fillFeeSplitPlanDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		FeeSplitPlanDTO dto = new FeeSplitPlanDTO();
		dto.setFeeSplitPlanID(new Integer(rs.getInt("feeSplitPlanID")));
		dto.setPlanName(rs.getString("planName"));
		dto.setTotalTimeCycleNo(rs.getInt("TotalTimeCycleNo"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	public static FeeSplitPlanItemDTO fillFeeSplitPlanItemDTO(ResultSet rs) throws SQLException {
		return fillFeeSplitPlanItemDTO(rs, null);
	}

	public static FeeSplitPlanItemDTO fillFeeSplitPlanItemDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		FeeSplitPlanItemDTO dto = new FeeSplitPlanItemDTO();
		dto.setFeeSplitPlanID(rs.getInt("feeSplitPlanID"));
		dto.setSeqNo(new Integer(rs.getInt("seqNo")));
		dto.setValue(rs.getDouble("value"));
		dto.setTimeCycleNO(rs.getInt("timeCycleNO"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	public static ManualTransferSettingDTO fillManualTransferSettingDTO(ResultSet rs) throws SQLException {
		return fillManualTransferSettingDTO(rs, null);
	}

	public static ManualTransferSettingDTO fillManualTransferSettingDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		ManualTransferSettingDTO dto = new ManualTransferSettingDTO();
		dto.setSeqNo(rs.getInt("seqNo"));
		dto.setFromOrgId(rs.getInt("fromOrgId"));
		dto.setToOrgId(rs.getInt("toOrgId"));
		dto.setPriority(rs.getInt("priority"));
		dto.setSheetType(rs.getString("sheetType"));
		dto.setOrgSubRole(rs.getString("orgSubRole"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	public static Bundle2CampaignDTO fillBundle2CampaignDTO(ResultSet rs) throws SQLException{
		return fillBundle2CampaignDTO(rs,null);
	}

	public static Bundle2CampaignDTO fillBundle2CampaignDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		Bundle2CampaignDTO dto =new Bundle2CampaignDTO();
		dto.setPackageID(rs.getInt(prex+"packageID"));
		dto.setDtCreate(rs.getTimestamp(prex+"dt_create"));
		dto.setDtLastmod(rs.getTimestamp(prex+"dt_lastmod"));
		dto.setCampaignID(rs.getInt(prex+"campaignID"));
		 
		return dto;
	}
	
	public static CsiActionReasonSettingDTO fillCsiActionReasonSettingDTO(ResultSet rs) throws SQLException{
		return fillCsiActionReasonSettingDTO(rs,null);
	}

	public static CsiActionReasonSettingDTO fillCsiActionReasonSettingDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		CsiActionReasonSettingDTO dto =new CsiActionReasonSettingDTO();
		dto.setAction(rs.getString(prex+"ACTION"));
		dto.setCanEmptyFlag(rs.getString(prex+"CANEMPTYFLAG"));
		dto.setCsiType(rs.getString(prex+"CSITYPE"));
		dto.setStatus(rs.getString(prex+"status"));
		dto.setDisplayName(rs.getString(prex+"DISPLAYNAME"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		dto.setSeqNo(rs.getInt(prex+"SEQNO"));
		return dto;
	}
	
	public static CsiActionReasonDetailDTO fillCsiActionReasonDetailDTO(ResultSet rs) throws SQLException{
		return fillCsiActionReasonDetailDTO(rs,null);
	}
	public static CsiActionReasonDetailDTO fillCsiActionReasonDetailDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		CsiActionReasonDetailDTO dto =new CsiActionReasonDetailDTO();
		dto.setDefaultValueFlag(rs.getString(prex+"DEFAULTVALUEFLAG"));
		dto.setKey(rs.getString(prex+"KEY"));
		dto.setPriority(rs.getInt(prex+"PRIORITY"));
		dto.setReferSeqNo(rs.getInt(prex+"REFERSEQNO"));
		dto.setSeqNo(rs.getInt(prex+"SEQNO"));
		dto.setValue(rs.getString(prex+"VALUE"));
		dto.setStatus(rs.getString(prex+"status"));
		return dto;
	}
	public static CsiTypeReason2DeviceDTO fillCsiTypeReason2DeviceDTO(ResultSet rs) throws SQLException{
		return fillCsiTypeReason2DeviceDTO(rs,null);
	}
	public static CsiTypeReason2DeviceDTO fillCsiTypeReason2DeviceDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		CsiTypeReason2DeviceDTO dto =new CsiTypeReason2DeviceDTO();
		dto.setComments(rs.getString(prex+"comments"));
		dto.setCsiCreateReason(rs.getString(prex+"csiCreateReason"));
		dto.setCsiType(rs.getString(prex+"csiType"));
		dto.setReferDeviceModel(rs.getString(prex+"referDeviceModel"));
		dto.setSeqNo(rs.getInt(prex+"SEQNO"));
		dto.setReferPackageId(rs.getInt(prex+"referPackageId"));
		dto.setReferProductId(rs.getInt(prex+"referProductId")); 
		dto.setReferPurpose(rs.getString(prex+"referPurpose")); 
		dto.setStatus(rs.getString(prex+"status"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		return dto;
	}
	public static LdapConditionDTO fillLdapConditionDTO(ResultSet rs) throws SQLException{
		return fillLdapConditionDTO(rs,null);
	}
	public static LdapConditionDTO fillLdapConditionDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		LdapConditionDTO dto =new LdapConditionDTO();
		dto.setCondId(rs.getInt(prex+"condId"));
		dto.setCondName(rs.getString(prex+"condName"));
		dto.setDescription(rs.getString(prex+"description"));
		dto.setCondString(rs.getString(prex+"condString"));
		dto.setHostId(rs.getInt(prex+"hostId"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		return dto;
	}
	public static SwapDeviceReason2StatusDTO fillSwapDeviceReason2StatusDTO(ResultSet rs) throws SQLException{
		return fillSwapDeviceReason2StatusDTO(rs,null);
	}
	public static SwapDeviceReason2StatusDTO fillSwapDeviceReason2StatusDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		SwapDeviceReason2StatusDTO dto =new SwapDeviceReason2StatusDTO();
		dto.setReasonStrList(rs.getString(prex+"reasonStrList"));
		dto.setSeqNo(rs.getInt(prex+"seqNo"));
		dto.setStatus(rs.getString(prex+"status"));
		dto.setToStatus(rs.getString(prex+"toStatus"));
		 
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		return dto;
	}

	public static SpOperationCacheDTO fillSpOperationCacheDTO(ResultSet rs)throws SQLException{
		SpOperationCacheDTO dto = new SpOperationCacheDTO();
		dto.setSeqNo(rs.getInt("SeqNo"));
		dto.setBatchId(rs.getInt("BatchID"));
		dto.setCreateTime(rs.getTimestamp("createTime"));
		dto.setOperationType(rs.getString("OperationType"));
		dto.setIssqlStmtFlag(rs.getString("IssqlStmtFlag"));
		dto.setReferRecordId(rs.getInt("ReferRecordId"));
		dto.setReferParam(rs.getString("ReferParam"));
		dto.setSqlStmt(rs.getString("SqlStmt"));
		dto.setProcessStatus(rs.getString("ProcessStatus"));
		dto.setDtCreate(rs.getTimestamp("Dt_Create"));
		dto.setDtLastmod(rs.getTimestamp("Dt_LastMod"));
		return dto;
	}
	public static FiberNodeDTO fillFiberNodeDTO(ResultSet rs) throws SQLException{
		return fillFiberNodeDTO(rs,null);
	}
	public static FiberNodeDTO fillFiberNodeDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		FiberNodeDTO dto =new FiberNodeDTO();
		dto.setId(rs.getInt(prex+"id"));
		dto.setDistrictId(rs.getInt(prex+"districtId"));
		dto.setFiberNodeCode(rs.getString(prex+"fiberNodeCode"));
		dto.setDescription(rs.getString(prex+"description"));
		dto.setDetailAddress(rs.getString(prex+"detailAddress"));
		dto.setFiberReceiverId(rs.getInt(prex+"fiberReceiverId"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		return dto;
	}
	public static FiberReceiverDTO fillFiberReceiverDTO(ResultSet rs) throws SQLException{
		return fillFiberReceiverDTO(rs,null);
	}
	public static FiberReceiverDTO fillFiberReceiverDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		FiberReceiverDTO dto =new FiberReceiverDTO();
		dto.setId(rs.getInt(prex+"id"));
		dto.setDistrictId(rs.getInt(prex+"districtId"));
		dto.setFiberTransmitterId(rs.getInt(prex+"fiberTransmitterId"));
		dto.setDescription(rs.getString(prex+"description"));
		dto.setDetailAddress(rs.getString(prex+"detailAddress"));
		dto.setFiberReceiverCode(rs.getString(prex+"FiberReceiverCode"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		return dto;
	}
	public static FiberTransmitterDTO fillFiberTransmitterDTO(ResultSet rs) throws SQLException{
		return fillFiberTransmitterDTO(rs,null);
	}
	public static FiberTransmitterDTO fillFiberTransmitterDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		FiberTransmitterDTO dto =new FiberTransmitterDTO();
		dto.setId(rs.getInt(prex+"id"));
		dto.setDistrictId(rs.getInt(prex+"districtId"));
		dto.setMachineRoomId(rs.getInt(prex+"machineRoomId"));
		dto.setDescription(rs.getString(prex+"description"));
		dto.setDetailAddress(rs.getString(prex+"detailAddress"));
		dto.setFiberTransmitterCode(rs.getString(prex+"fiberTransmitterCode"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		return dto;
	}
	public static MachineRoomDTO fillMachineRoomDTO(ResultSet rs) throws SQLException{
		return fillMachineRoomDTO(rs,null);
	}
	public static MachineRoomDTO fillMachineRoomDTO(ResultSet rs,String prex) throws SQLException{
		if (prex ==null) prex ="";
		MachineRoomDTO dto =new MachineRoomDTO();
		dto.setId(rs.getInt(prex+"id"));
		dto.setDistrictId(rs.getInt(prex+"districtId"));
		dto.setMachineRoomCode(rs.getString(prex+"machineRoomCode"));
		dto.setDescription(rs.getString(prex+"description"));
		dto.setDetailAddress(rs.getString(prex+"detailAddress"));
		dto.setMachineRoomName(rs.getString(prex+"machineRoomName"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		return dto;
	}
	public static CampaignPaymentAwardDTO fillCampaignPaymentAwardDTO(ResultSet rs) throws SQLException{
		return fillCampaignPaymentAwardDTO(rs,null);
	}
	public static CampaignPaymentAwardDTO fillCampaignPaymentAwardDTO(ResultSet rs,String prex) throws SQLException {
		if (prex ==null) prex ="";
		CampaignPaymentAwardDTO dto =new CampaignPaymentAwardDTO();
		dto.setCampaignID(rs.getInt(prex+"campaignID"));
		dto.setIsPrepaymentFlag(rs.getString(prex+"isPrepaymentFlag"));
		dto.setMopID(rs.getInt(prex+"mopID"));
		dto.setSeqNo(rs.getInt(prex+"seqNo"));
		dto.setValue(rs.getDouble(prex+"value"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		return dto;
	}
	public static BankDeductionHeaderDTO fillBankDeductionHeaderDTO(ResultSet rs) throws SQLException{
		return fillBankDeductionHeaderDTO(rs,null);
	}
	public static BankDeductionHeaderDTO fillBankDeductionHeaderDTO(ResultSet rs,String prex) throws SQLException {
		if (prex ==null) prex ="";
		BankDeductionHeaderDTO dto =new BankDeductionHeaderDTO();
		dto.setDescription(rs.getString(prex+"description"));
		dto.setMopId(rs.getInt(prex+"mopID"));
		dto.setInterfaceId(rs.getInt(prex+"interfaceId"));
		dto.setBatchNo(rs.getInt(prex+"batchNo"));
		dto.setBillingCycle(rs.getInt(prex+"billingCycle"));
		dto.setBillingCycleType(rs.getInt(prex+"billingCycleType"));
		dto.setIncludeBefore(rs.getString(prex+"includeBefore"));
		dto.setCreatOpId(rs.getInt(prex+"creatOpId"));
		dto.setCreateOrgId(rs.getInt(prex+"createOrgId"));
		dto.setStartTime(rs.getTimestamp(prex+"startTime"));
		dto.setEndTime(rs.getTimestamp(prex+"endTime"));
		dto.setTotalRecordNo(rs.getInt(prex+"totalRecordNo"));
		dto.setTotalAmount(rs.getBigDecimal(prex+"totalAmount"));
		dto.setBankDealDate(rs.getTimestamp(prex+"bankDealDate"));
		dto.setSucessRecordNo(rs.getInt(prex+"sucessRecordNo"));
		dto.setSucessAmount(rs.getBigDecimal(prex+"sucessAmount"));
		dto.setFailedRecordNo(rs.getInt(prex+"failedRecordNo"));
		dto.setFailedAmount(rs.getBigDecimal(prex+"failedAmount"));
		dto.setInvalidRecordNo(rs.getInt(prex+"invalidRecordNo"));
		dto.setProcessStatus(rs.getString(prex+"processStatus"));
		dto.setExportFileName(rs.getString(prex+"exportFileName"));
		dto.setSucessFileName(rs.getString(prex+"sucessFileName"));
		dto.setFailedFileName(rs.getString(prex+"failedFileName"));
		dto.setReturnBackFileName(rs.getString(prex+"returnBackFileName"));
		dto.setCzTotalCount(rs.getInt(prex+"czTotalCount"));
		dto.setCzTotalValue(rs.getBigDecimal(prex+"czTotalValue"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		dto.setExchangeId(rs.getString(prex+"exchangeId"));
		return dto;
	}
	public static BankDeductionDetailDTO fillBankDeductionDetailDTO(ResultSet rs) throws SQLException{
		return fillBankDeductionDetailDTO(rs,null);
	}
	public static BankDeductionDetailDTO fillBankDeductionDetailDTO(ResultSet rs,String prex) throws SQLException {
		if (prex ==null) prex ="";
		BankDeductionDetailDTO dto =new BankDeductionDetailDTO();
		dto.setAmount(rs.getBigDecimal(prex+"amount"));
		dto.setSeqNo(rs.getInt(prex+"seqNo"));
		dto.setBatchNo(rs.getInt(prex+"batchNo"));
		dto.setCreateTime(rs.getTimestamp(prex+"createTime"));
		dto.setReturnTime(rs.getTimestamp(prex+"returnTime"));
		dto.setReferInvoiceNo(rs.getInt(prex+"referInvoiceNo"));
		dto.setReturnStatus(rs.getString(prex+"returnStatus"));
		dto.setReturnReasonCode(rs.getString(prex+"returnReasonCode"));
		dto.setCustomerId(rs.getInt(prex+"customerId"));
		dto.setBankAccountName(rs.getString(prex+"bankAccountName"));
		dto.setBankAccountId(rs.getString(prex+"bankAccountId"));
		dto.setStatus(rs.getString(prex+"status"));
		dto.setAccountID(rs.getInt(prex+"accountID"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		return dto;
	}
	
	public static CAWalletDTO fillCAWalletDTO(ResultSet rs) throws SQLException{
		return fillCAWalletDTO(rs,null);
	}
	public static CAWalletDTO fillCAWalletDTO(ResultSet rs,String prex) throws SQLException {
		if (prex ==null) prex ="";
		CAWalletDTO dto =new CAWalletDTO();
		dto.setWalletId(rs.getInt(prex+"walletId"));
		dto.setCustomerId(rs.getInt(prex+"customerId"));
		dto.setServiceAccountId(rs.getInt(prex+"serviceAccountId"));
		dto.setScSerialNo(rs.getString(prex+"scSerialNo"));
		dto.setStatus(rs.getString(prex+"status"));
		dto.setTotalPoint(rs.getInt(prex+"totalPoint"));
		dto.setCaWalletCode(rs.getString(prex+"caWalletCode"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		return dto;
	}
	
	public static CAWalletChargeRecordDTO fillCAWalletChargeRecordDTO(ResultSet rs) throws SQLException{
		return fillCAWalletChargeRecordDTO(rs,null);
	}
	public static CAWalletChargeRecordDTO fillCAWalletChargeRecordDTO(ResultSet rs,String prex) throws SQLException {
		if (prex ==null) prex ="";
		CAWalletChargeRecordDTO dto =new CAWalletChargeRecordDTO();
		dto.setSeqNo(rs.getInt(prex+"seqNo"));
		dto.setWalletId(rs.getInt(prex+"walletId"));
		dto.setCustomerId(rs.getInt(prex+"customerId"));
		dto.setServiceAccountId(rs.getInt(prex+"serviceAccountId"));
		dto.setScSerialNo(rs.getString(prex+"scSerialNo"));
		dto.setCreateTime(rs.getTimestamp(prex+"createTime"));
		dto.setOpId(rs.getInt(prex+"opId"));
		dto.setOrgId(rs.getInt(prex+"orgId"));
		dto.setMopId(rs.getInt(prex+"mopId"));
		dto.setPoint(rs.getInt(prex+"point"));
		dto.setValue(rs.getBigDecimal(prex+"value"));
		dto.setStatus(rs.getString(prex+"status"));
		dto.setCaWalletCode(rs.getString(prex+"caWalletCode"));
		dto.setCsiId(rs.getInt(prex+"csiId"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		return dto;
	}
	
	public static CAWalletDefineDTO fillCAWalletDefineDTO(ResultSet rs) throws SQLException{
		return fillCAWalletDefineDTO(rs,null);
	}
	public static CAWalletDefineDTO fillCAWalletDefineDTO(ResultSet rs,String prex) throws SQLException {
		if (prex ==null) prex ="";
		CAWalletDefineDTO dto =new CAWalletDefineDTO();
		dto.setCaWalletCode(rs.getString(prex+"caWalletCode"));
		dto.setCaWalletName(rs.getString(prex+"caWalletName"));
		dto.setRate(rs.getBigDecimal(prex+"rate"));
		dto.setRequired(rs.getString(prex+"required"));
		dto.setStatus(rs.getString(prex+"status"));
		dto.setDtCreate(rs.getTimestamp(prex+"DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex+"DT_LASTMOD"));
		dto.setDeviceModelList(rs.getString(prex+"deviceModelList"));
		return dto;
	}
	
	public static CustServiceInteractionDTO fillCustServiceInteractionViewDTO(
			ResultSet rs) throws SQLException {
		return fillCustServiceInteractionViewDTO(rs, null);
	}
	
	public static CustServiceInteractionDTO fillCustServiceInteractionViewDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		CustServiceInteractionDTO dto = new CustServiceInteractionDTO();

		dto.setId(rs.getInt(prex + "ID"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setType(rs.getString(prex + "TYPE"));
		dto.setCustomerID(rs.getInt(prex + "CUSTOMERID"));
		dto.setCreateTime(rs.getTimestamp(prex + "CREATETIME"));
		return dto;
	}

	public static ExportDataColumnDefineDTO fillExportDataColumnDefineDTO(
			ResultSet rs) throws SQLException {
		return fillExportDataColumnDefineDTO(rs, null);
	}
	
	public static ExportDataColumnDefineDTO fillExportDataColumnDefineDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		ExportDataColumnDefineDTO dto = new ExportDataColumnDefineDTO();

		dto.setId(rs.getInt(prex + "id"));
		dto.setRefId(rs.getInt(prex + "refid"));
		dto.setCaptionname(rs.getString(prex + "CAPTIONNAME"));
		dto.setCaptiontype(rs.getString(prex + "CAPTIONTYPE"));
		dto.setCaptionbgcolor(rs.getString(prex + "CAPTIONBGCOLOR"));
		dto.setAlign(rs.getString(prex + "ALIGN"));
		dto.setArrangeorder(rs.getInt(prex + "ARRANGEORDER"));
		dto.setValue(rs.getString(prex + "VALUE"));
		dto.setFormat(rs.getString(prex + "FORMAT"));
		dto.setValuebgcolor(rs.getString(prex + "VALUEBGCOLOR"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDt_lastmod(rs.getDate(prex + "DT_LASTMOD"));
		dto.setDt_create(rs.getDate(prex + "DT_CREATE"));
		return dto;
	}
	public static ExportDataHeadDefineDTO fillExportDataHeadDefineDTO(
			ResultSet rs) throws SQLException {
		return fillExportDataHeadDefineDTO(rs, null);
	}
	
	public static ExportDataHeadDefineDTO fillExportDataHeadDefineDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		ExportDataHeadDefineDTO dto = new ExportDataHeadDefineDTO();

		dto.setId(rs.getInt(prex + "id"));
		dto.setAction(rs.getString(prex + "action"));
		dto.setSubtype(rs.getString(prex + "subtype"));
		dto.setSqldescription(rs.getString(prex + "sqldescription"));
		dto.setCustomsql(rs.getString(prex + "customsql"));
		dto.setDescription(rs.getString(prex + "description"));
		dto.setExportfilename(rs.getString(prex + "exportfilename"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDt_lastmod(rs.getDate(prex + "DT_LASTMOD"));
		dto.setDt_create(rs.getDate(prex + "DT_CREATE"));
		return dto;
	}
	
	public static OssAuthorizationDTO fillOssAuthorizationDTO(
			ResultSet rs) throws SQLException {
		return fillOssAuthorizationDTO(rs, null);
	}
	
	public static OssAuthorizationDTO fillOssAuthorizationDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		OssAuthorizationDTO dto = new OssAuthorizationDTO();

		dto.setDeviceID(rs.getInt(prex + "deviceID"));
		dto.setDeviceSerialNo(rs.getString(prex + "deviceSerialNo"));
		dto.setProductID(rs.getInt(prex + "productID"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		return dto;
	}
	
	public static DeviceBatchPreauthDTO fillDeviceBatchPreauthDTO(
			ResultSet rs) throws SQLException {
		return fillDeviceBatchPreauthDTO(rs, null);
	}
	
	public static DeviceBatchPreauthDTO fillDeviceBatchPreauthDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		DeviceBatchPreauthDTO dto = new DeviceBatchPreauthDTO();

		dto.setBatchId(rs.getInt(prex + "batchId"));
		dto.setReferSheetSerialNO(rs.getString(prex + "referSheetSerialNO"));
		dto.setOperationType(rs.getString(prex + "operationType"));
		dto.setCreateTime(rs.getTimestamp(prex + "createTime"));
		dto.setOpId(rs.getInt(prex + "opId"));
		dto.setOrgId(rs.getInt(prex + "orgId"));
		dto.setTotalDeviceNum(rs.getInt(prex + "totalDeviceNum"));
		dto.setFileName(rs.getString(prex + "fileName"));
		dto.setDescription(rs.getString(prex + "description"));
		dto.setStatus(rs.getString(prex + "status"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		return dto;
	}
	
	public static DevicePreauthRecordDTO fillDevicePreauthRecordDTO(
			ResultSet rs) throws SQLException {
		return fillDevicePreauthRecordDTO(rs, null);
	}
	
	public static DevicePreauthRecordDTO fillDevicePreauthRecordDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		DevicePreauthRecordDTO dto = new DevicePreauthRecordDTO();

		dto.setSeqNo(rs.getInt(prex + "seqNo"));
		dto.setBatchId(rs.getInt(prex + "batchId"));
		dto.setOperationType(rs.getString(prex + "operationType"));
		dto.setCreateTime(rs.getTimestamp(prex + "createTime"));
		dto.setOpId(rs.getInt(prex + "opId"));
		dto.setOrgId(rs.getInt(prex + "orgId"));
		dto.setDeviceID(rs.getInt(prex + "deviceID"));
		dto.setDeviceModel(rs.getString(prex + "deviceModel"));
		dto.setSerialNo(rs.getString(prex + "serialNo"));
		dto.setProductId(rs.getInt(prex + "productId"));
		dto.setStatus(rs.getString(prex + "status"));
		dto.setDescription(rs.getString(prex + "description"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		return dto;
	}
	
	public static PrintBlockDTO fillPrintBlockDTO(
			ResultSet rs) throws SQLException {
		return fillPrintBlockDTO(rs, null);
	}
	
	public static PrintBlockDTO fillPrintBlockDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		PrintBlockDTO dto = new PrintBlockDTO();

		dto.setId(rs.getInt(prex + "id"));
		dto.setRefConfigId(rs.getInt(prex + "refConfigId"));
		dto.setCustomsql(rs.getString(prex + "customsql"));
		dto.setDescription(rs.getString(prex + "description"));
		dto.setMarginLeft(rs.getInt(prex + "marginLeft"));
		dto.setMarginTop(rs.getInt(prex + "marginTop"));
		dto.setTextSize(rs.getInt(prex + "TEXTSIZE"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDt_lastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setDt_create(rs.getTimestamp(prex + "DT_CREATE"));
		return dto;
	}
	
	public static PrintBlockDetailDTO fillPrintBlockDetailDTO(
			ResultSet rs) throws SQLException {
		return fillPrintBlockDetailDTO(rs, null);
	}
	
	public static PrintBlockDetailDTO fillPrintBlockDetailDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		PrintBlockDetailDTO dto = new PrintBlockDetailDTO();

		dto.setId(rs.getInt(prex + "id"));
		dto.setRefBlockId(rs.getInt(prex + "refBlockId"));
		dto.setName(rs.getString(prex + "name"));
		dto.setValue(rs.getString(prex + "value"));
		dto.setFormat(rs.getString(prex + "format"));
		dto.setType(rs.getString(prex + "type"));
		dto.setLeft(rs.getInt(prex + "left"));
		dto.setTop(rs.getInt(prex + "top"));
		dto.setMigrationRow(rs.getInt(prex + "migrationRow"));
		dto.setWidth(rs.getInt(prex + "width"));
		dto.setAlign(rs.getString(prex + "align"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setDt_lastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setDt_create(rs.getTimestamp(prex + "DT_CREATE"));
		return dto;
	}
	
	public static PrintConfigDTO fillPrintConfigDTO(
			ResultSet rs) throws SQLException {
		return fillPrintConfigDTO(rs, null);
	}
	
	public static PrintConfigDTO fillPrintConfigDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		PrintConfigDTO dto = new PrintConfigDTO();

		dto.setId(rs.getInt(prex + "id"));
		dto.setPrintSheetType(rs.getString(prex + "printSheetType"));
		dto.setSheetSubType(rs.getString(prex + "sheetSubType"));
		dto.setCsiReason(rs.getString(prex + "csiReason"));
		dto.setPrintReason(rs.getString(prex + "printReason"));
		dto.setRowHeight(rs.getInt(prex + "rowHeight"));
		dto.setPageBreakCount(rs.getInt(prex + "pageBreakCount"));
		dto.setDescription(rs.getString(prex + "description"));
		dto.setLeft(rs.getInt(prex + "Left"));
		dto.setTop(rs.getInt(prex + "Top"));
		dto.setBackImg(rs.getBlob(prex + "backImg"));
		dto.setStatus(rs.getString(prex + "STATUS"));
		dto.setPageHeight(rs.getInt(prex + "pageHeight"));
		dto.setPageWeight(rs.getInt(prex + "pageWeight"));
		dto.setDt_lastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setDt_create(rs.getTimestamp(prex + "DT_CREATE"));
		return dto;
	}
	
	public static DevicePrematchRecordDTO fillDevicePrematchRecordDTO(
			ResultSet rs) throws SQLException {
		return fillDevicePrematchRecordDTO(rs, null);
	}
	public static DevicePrematchRecordDTO fillDevicePrematchRecordDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();

		DevicePrematchRecordDTO dto = new DevicePrematchRecordDTO();

		dto.setSeqNo(rs.getInt(prex + "seqNo"));
		dto.setBatchId(rs.getInt(prex + "batchId"));
		dto.setOperationtype(rs.getString(prex + "operationType"));
		dto.setCreateTime(rs.getTimestamp(prex + "createTime"));
		dto.setOpId(rs.getInt(prex + "opId"));
		dto.setOrgId(rs.getInt(prex + "orgId"));
		dto.setStbDeviceID(rs.getInt(prex + "stbdeviceid"));
		dto.setStbDeviceModel(rs.getString(prex + "stbdevicemodel"));
		dto.setStbSerialNO(rs.getString(prex + "stbserialno"));
		
		dto.setScDeviceID(rs.getInt(prex + "scdeviceid"));
		dto.setScdeviceModel(rs.getString(prex + "scdevicemodel"));
		dto.setScSerialNo(rs.getString(prex + "scserialno"));		
		
		dto.setStatus(rs.getString(prex + "status"));
		dto.setDescription(rs.getString(prex + "description"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		return dto;
	}
	public static DtvMigrationAreaDTO fillDtvMigrationAreaDTO(ResultSet rs) throws SQLException {
		return fillDtvMigrationAreaDTO(rs, null);
	}

	public static DtvMigrationAreaDTO fillDtvMigrationAreaDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		DtvMigrationAreaDTO dto = new DtvMigrationAreaDTO();

		dto.setId(rs.getInt(prex + "ID"));
		dto.setCreateDate(rs.getTimestamp(prex + "CREATEDATE"));
		dto.setCreateOpId(new Integer(rs.getInt(prex + "CREATEOPID")));
		dto.setAreaCode(rs.getString(prex + "AREACODE"));
		dto.setAreaName(rs.getString(prex + "AREANAME"));
		dto.setDescription(rs.getString(prex + "DESCRIPTION"));
		dto.setRegionalAreaId(rs.getInt(prex + "REGIONALAREAID"));
		dto.setMigrationTeamName(rs.getString(prex + "MIGRATIONTEAMNAME"));
		dto.setBatchStartDate(rs.getTimestamp(prex + "BATCHSTARTDATE"));
		dto.setBatchEndDate(rs.getTimestamp(prex + "BATCHENDDATE"));
		dto.setStatus(rs.getString(prex + "STATUS"));			
		dto.setStatusDate(rs.getTimestamp(prex + "STATUSDATE"));
		dto.setPlanMigrationRoomNum(rs.getInt(prex + "PLANMIGRATIONROOMNUM"));
		
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	
	public static FaPiaoDTO fillFaPiaoDTO(ResultSet rs) throws SQLException {
		return fillFaPiaoDTO(rs, null);
	}

	public static FaPiaoDTO fillFaPiaoDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		FaPiaoDTO dto = new FaPiaoDTO();
		
		dto.setSeqNo(rs.getInt(prex + "seqNo"));
		
		dto.setType(rs.getString(prex + "Type"));
		dto.setModuleName(rs.getString(prex + "ModuleName"));
		dto.setSerailNo(rs.getString(prex + "SerailNo"));
		dto.setSysSerialNo(rs.getString(prex + "SysSerialNo"));
		dto.setVolumnSeqno(rs.getInt(prex + "VolumnSeqno"));

		dto.setCreatorOpID(rs.getInt(prex + "CreatorOpID"));
		dto.setCreatorOrgID(rs.getInt(prex + "CreatorOrgID"));
		dto.setRecipientOpID(rs.getInt(prex + "RecipientOpID"));
		dto.setRecipientOrgID(rs.getInt(prex + "RecipientOrgID"));
		dto.setDrawOpID(rs.getInt(prex + "DrawOpID"));

		dto.setDrawOrgID(rs.getInt(prex + "DrawOrgID"));
		dto.setTitle(rs.getString(prex + "Title"));
		dto.setAccountID(rs.getInt(prex + "AccountID"));
		dto.setSumAmount(rs.getInt(prex + "SumAmount"));
		dto.setIsAmtFixed(rs.getString(prex + "IsAmtFixed"));

		dto.setAuthBy(rs.getString(prex + "AuthBy"));
		dto.setAddressType(rs.getString(prex + "AddressType"));
		dto.setAddressID(rs.getInt(prex + "AddressID"));
		dto.setStatus(rs.getString(prex + "Status"));
		dto.setStatusTime(rs.getTimestamp(prex + "StatusTime"));

		dto.setDescription(rs.getString(prex + "Description"));
		dto.setCreateTime(rs.getTimestamp(prex + "CreateTime"));
		dto.setDiscardReason(rs.getString(prex + "DiscardReason"));
		dto.setPrintTimes(rs.getInt(prex + "PrintTimes"));
		dto.setLastPrintDate(rs.getTimestamp(prex + "LastPrintDate"));

		dto.setPrintCode(rs.getString(prex + "PrintCode"));
		dto.setMachineCode(rs.getString(prex + "MachineCode"));
        dto.setPrintNumber(rs.getString(prex + "PrintNumber"));
        dto.setTaxControlCode(rs.getString(prex + "TaxControlCode"));
        dto.setMajorTaxAgentCode(rs.getString(prex + "MajorTaxAgentCode"));

        dto.setIsZongBaoRen(rs.getString(prex + "IsZongBaoRen"));
        dto.setIsFenBaoRen(rs.getString(prex + "IsFenBaoRen"));
        dto.setPayerName(rs.getString(prex + "PayerName"));
        dto.setPayerType(rs.getString(prex + "PayerType"));
        dto.setPayerID(rs.getString(prex + "PayerID"));

        dto.setPayeeName(rs.getString(prex + "PayeeName"));
        dto.setPayeeType(rs.getString(prex + "PayeeType"));
        dto.setPayeeID(rs.getString(prex + "PayeeID"));
        dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		
		dto.setRecipientTime(rs.getTimestamp(prex + "RecipientTime"));
		dto.setDrawTime(rs.getTimestamp(prex + "DrawTime"));
		dto.setIsPatchPrint(rs.getString(prex + "IsPatchPrint"));
		dto.setSourceType(rs.getString(prex + "SourceType"));
        dto.setSourceID(rs.getInt(prex + "SourceID"));
        dto.setInvoiceCycleID(rs.getInt(prex + "InvoiceCycleID"));
        
		return dto;
	}
	
	
	public static FapiaoDetailDTO fillFapiaoDetailDTO(ResultSet rs) throws SQLException {
		return fillFapiaoDetailDTO(rs, null);
	}

	public static FapiaoDetailDTO fillFapiaoDetailDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		FapiaoDetailDTO dto = new FapiaoDetailDTO();
		
		dto.setSeqNo(rs.getInt(prex + "seqNo"));
		dto.setUnit(rs.getString(prex + "Unit"));
		dto.setAmount(rs.getDouble(prex + "Amount"));
		dto.setCompleteTaxTag(rs.getString(prex + "CompleteTaxTag"));
		dto.setIsFixed(rs.getString(prex + "IsFixed"));
		dto.setAuthBy(rs.getString(prex + "AuthBy"));
		
		dto.setStatus(rs.getString(prex + "Status"));
		dto.setCreateTime(rs.getTimestamp(prex + "CreateTime"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		dto.setFapiaoSeqNo(rs.getInt(prex + "FapiaoSeqNo"));
		dto.setQuantity(rs.getInt(prex + "Quantity"));
		return dto;
	}
	
	public static Fapiao2PaymentDTO fillFapiao2PaymentDTO(ResultSet rs) throws SQLException {
		return fillFapiao2PaymentDTO(rs, null);
	}

	public static Fapiao2PaymentDTO fillFapiao2PaymentDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		Fapiao2PaymentDTO dto = new Fapiao2PaymentDTO();
		
		dto.setSeqNo(rs.getInt(prex + "seqNo"));
		dto.setStatus(rs.getString(prex + "Status"));
		dto.setFapiaoSeqNo(rs.getInt(prex + "FapiaoSeqNo"));
		dto.setSourceType(rs.getString(prex + "SourceType"));
		dto.setSourceID(rs.getInt(prex + "SourceID"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	
	public static FapiaoTransitionDTO fillFapiaoTransitionDTO(ResultSet rs) throws SQLException {
		return fillFapiaoTransitionDTO(rs, null);
	}

	public static FapiaoTransitionDTO fillFapiaoTransitionDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		FapiaoTransitionDTO dto = new FapiaoTransitionDTO();
		
		dto.setSeqNo(rs.getInt(prex + "seqNo"));
		
		dto.setAction(rs.getString(prex + "Action"));
		dto.setFileName(rs.getString(prex + "FileName"));
		dto.setStatus(rs.getString(prex + "Status"));
		dto.setCreateTime(rs.getTimestamp(prex + "CreateTime"));
		dto.setPrintCode(rs.getString(prex + "PrintCode"));
        
		dto.setPrintNumber(rs.getString(prex + "PrintNumber"));
		dto.setTaxControlCode(rs.getString(prex + "TaxControlCode"));
		dto.setVolumnSeqNo(rs.getInt(prex + "VolumnSeqNo"));

		dto.setTotal(rs.getInt(prex + "Total"));
		dto.setOpID(rs.getInt(prex + "OpID"));
		dto.setOrgID(rs.getInt(prex + "OrgID"));
        

		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	
	public static FapiaoTransitionDetailDTO fillFapiaoTransitionDetailDTO(ResultSet rs) throws SQLException {
		return fillFapiaoTransitionDetailDTO(rs, null);
	}

	public static FapiaoTransitionDetailDTO fillFapiaoTransitionDetailDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		FapiaoTransitionDetailDTO dto = new FapiaoTransitionDetailDTO();
		
		dto.setSeqNo(rs.getInt(prex + "seqNo"));
		
		dto.setAction(rs.getString(prex + "Action"));
		dto.setFromStatus(rs.getString(prex + "FromStatus"));
		dto.setToStatus(rs.getString(prex + "ToStatus"));
		dto.setFromAddressType(rs.getString(prex + "FromAddressType"));
		dto.setFromAddressID(rs.getInt(prex + "FromAddressID"));

		dto.setToAddressType(rs.getString(prex + "ToAddressType"));
		dto.setDtCreate(rs.getTimestamp(prex + "DtCreate"));
		dto.setFapiaoTransSeqNo(rs.getInt(prex + "FapiaoTransSeqNo"));

		dto.setVolumnSeqNo(rs.getString(prex + "VolumnSeqNo"));
		dto.setFapiaoSeqNo(rs.getInt(prex + "FapiaoSeqNo"));
		dto.setOpID(rs.getInt(prex + "OpID"));
		dto.setOrgID(rs.getInt(prex + "OrgID"));
        
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	
	public static FapiaoVolumnDTO fillFapiaoVolumnDTO(ResultSet rs) throws SQLException {
		return fillFapiaoVolumnDTO(rs, null);
	}

	public static FapiaoVolumnDTO fillFapiaoVolumnDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		FapiaoVolumnDTO dto = new FapiaoVolumnDTO();
		
		dto.setSeqNo(rs.getInt(prex + "seqNo"));
		
		dto.setType(rs.getString(prex + "Type"));
		dto.setCreatorOpID(rs.getInt(prex + "CreatorOrgID"));
		dto.setCreatorOrgID(rs.getInt(prex + "CreatorOrgID"));
		dto.setRecepientOpID(rs.getInt(prex + "RecepientOpID"));
		dto.setRecipientOrgID(rs.getInt(prex + "RecipientOrgID"));

		dto.setStatus(rs.getString(prex + "Status"));
		dto.setStatusTime(rs.getTimestamp(prex + "StatusTime"));
		dto.setAddressType(rs.getString(prex + "AddressType"));
		dto.setAddressID(rs.getInt(prex + "AddressID"));
    	dto.setVolumnSn(rs.getString(prex + "VolumnSn"));
        
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	
	public static FapiaoModuleDTO fillFapiaoModuleDTO(ResultSet rs) throws SQLException {
		return fillFapiaoModuleDTO(rs, null);
	}

	public static FapiaoModuleDTO fillFapiaoModuleDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		FapiaoModuleDTO dto = new FapiaoModuleDTO();
		
		dto.setSeqNo(rs.getInt(prex + "seqNo"));
		dto.setType(rs.getString(prex + "Type"));
		dto.setModuleName(rs.getString(prex + "ModuleName"));
		dto.setFieldName(rs.getString(prex + "FieldName"));
        
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
	
	
	public static BBIProcessLogDTO fillBBIProcessLogDTO(ResultSet rs) throws SQLException {
		return fillBBIProcessLogDTO(rs, null);
	}

	public static BBIProcessLogDTO fillBBIProcessLogDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		BBIProcessLogDTO dto = new BBIProcessLogDTO();
		
		dto.setSeqNo(rs.getInt(prex + "seqNo"));
		dto.setEventID(rs.getInt(prex + "eventID"));
		dto.setCommandName(rs.getString(prex + "commandName"));
		dto.setDescription(rs.getString(prex + "description"));
		dto.setServiceAccountID(rs.getInt(prex + "serviceAccountID"));
		dto.setStatus(rs.getString(prex + "status"));
		dto.setDtCreate(rs.getTimestamp(prex + "DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp(prex + "DT_LASTMOD"));
		return dto;
	}
/*
 * wangfang 2008.05.22 发票流转查询
 */
	public static void fillFapiaoTransitionDetailDTO(FapiaoTransitionDetailDTO dto,java.sql.ResultSet rs) throws java.sql.SQLException {
		
	  dto.setSeqNo(rs.getInt("SEQNO"));
	  dto.setFapiaoTransSeqNo(rs.getInt("FAPIAOTRANSSEQNO"));
	  dto.setFapiaoSeqNo(rs.getInt("FAPIAOSEQNO"));
	  dto.setVolumnSeqNo(String.valueOf(rs.getInt("VOLUMNSEQNO")));
	  dto.setOpID(rs.getInt("OPID"));
	  dto.setOrgID(rs.getInt("ORGID"));
	  dto.setAction(rs.getString("ACTION"));
	  dto.setFromStatus(rs.getString("FROMSTATUS"));
	  dto.setToStatus(rs.getString("TOSTATUS"));
	  dto.setFromAddressID(rs.getInt("FROMADDRESSID"));
	  dto.setFromAddressType(rs.getString("FROMADDRESSTYPE"));
	  dto.setToAddressID(rs.getInt("TOADDRESSID"));
	  dto.setToAddressType(rs.getString("TOADDRESSTYPE"));
	  dto.setDtCreate(rs.getTimestamp("DT_CREATE"));
	  dto.setDtLastmod(rs.getTimestamp("DT_LASTMOD"));  

  }
	public static VodstbDeviceImportDetailDTO fillVodstbDeviceImportDetailDTO(ResultSet rs) throws SQLException {
		return fillVodstbDeviceImportDetailDTO(rs, null);
	}

	public static VodstbDeviceImportDetailDTO fillVodstbDeviceImportDetailDTO(ResultSet rs, String prex)
			throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		VodstbDeviceImportDetailDTO dto = new VodstbDeviceImportDetailDTO();
		dto.setBatchID(rs.getInt(prex + "batchid"));
		dto.setSerialNo(rs.getString(prex + "serialno"));
		dto.setMacaddress(rs.getString(prex + "macaddress"));
		dto.setIntermacaddress(rs.getString(prex + "intermacaddress"));
		dto.setStatus(rs.getString(prex + "status"));
		dto.setDescription(rs.getString(prex + "description"));
		return dto;
	}
	public static VodstbDeviceImportHeadDTO fillVodstbDeviceImportHeadDTO(ResultSet rs)
			throws SQLException {
		return fillVodstbDeviceImportHeadDTO(rs, null);
		}
		
		public static VodstbDeviceImportHeadDTO fillVodstbDeviceImportHeadDTO(ResultSet rs,
			String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		
		VodstbDeviceImportHeadDTO dto = new VodstbDeviceImportHeadDTO();
		dto.setBatchID(rs.getInt(prex + "batchID"));
		dto.setBatchNo(rs.getString(prex + "batchNo"));
		dto.setComments(rs.getString(prex + "comments"));
		dto.setDepotID(rs.getInt(prex + "depotID"));
		dto.setDeviceClass(rs.getString(prex + "deviceClass"));
		dto.setDeviceModel(rs.getString(prex + "deviceModel"));
		dto.setGuaranteeLength(rs.getInt(prex + "guaranteeLength"));
		dto.setInAndOut(rs.getString(prex + "inAndOut"));
		dto.setOperatorID(rs.getInt(prex + "operatorID"));
		dto.setOutOrgID(rs.getInt(prex + "outOrgID"));
		dto.setProviderID(rs.getInt(prex + "providerID"));
		dto.setPurposestrList(rs.getString(prex + "purposestrList"));
		dto.setPurposeStrListValue(rs.getString(prex + "purposeStrListValue"));
		dto.setStatus(rs.getString(prex + "status"));
		dto.setCreatetime(rs.getTimestamp(prex + "createtime"));
		return dto;
		}
		
		
		
	public static ProtocolDTO fillProtocolDTO(ResultSet rs)
		throws SQLException {
		return fillProtocolDTO(rs, null);
	}
	
	public static ProtocolDTO fillProtocolDTO(ResultSet rs,
		String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		ProtocolDTO dto = new ProtocolDTO();
		dto.setCustomerID(rs.getInt(prex + "customerID"));
		dto.setProductPackageID(rs.getInt(prex + "productPackageID"));
		dto.setAcctitemTypeID(rs.getString(prex + "acctitemTypeID"));
		dto.setSinglePrice(rs.getDouble(prex + "singlePrice"));
		dto.setFeeType(rs.getString(prex + "feeType"));
		dto.setStatus(rs.getString(prex + "status"));
		dto.setDateFrom(rs.getTimestamp(prex + "dateFrom"));
		dto.setDateTo(rs.getTimestamp(prex + "dateTo"));
		dto.setUsedCount(rs.getInt(prex+"usedCount"));
		dto.setDtCreate(rs.getTimestamp("DT_CREATE"));
		dto.setDtLastmod(rs.getTimestamp("DT_LASTMOD"));  
		return dto;
	}
	
	public static QueryDTO fillQueryDTO(ResultSet rs) throws SQLException{
		return fillQueryDTO(rs,null);
	}
	
	public static QueryDTO fillQueryDTO(ResultSet rs,String prex) throws SQLException{
		if (prex == null)
			prex = "";
		prex = prex.trim();
		QueryDTO dto = new QueryDTO();
		dto.setAccountAddress(rs.getString("CustAddress"));
		dto.setAccountCashBalance1(rs.getDouble("AccountCashBalance1"));
		dto.setAccountCashBalance2(rs.getDouble("AccountCashBalance2"));
		dto
				.setAccountCreateTime1(rs
						.getTimestamp("AccountCreateTime1"));
		dto
				.setAccountCreateTime2(rs
						.getTimestamp("AccountCreateTime2"));
		dto.setAccountDistrictIdList(rs
				.getString("AccountDistirctIDList"));
		dto.setAccountInvoiceCreateTime1(rs
				.getTimestamp("AccountInvoiceCreateTime1"));
		dto.setAccountInvoiceCreateTime2(rs
				.getTimestamp("AccountInvoiceCreateTime2"));
		dto.setAccountInvoiceStatusList(rs
				.getString("AccountInvoiceStatusList"));
		dto.setAccountMopIdList(rs.getString("AccountMOPIDList"));
		dto.setAccountStatusList(rs.getString("AccountStatusList"));
		dto.setAccountTokenBalance1(rs
				.getDouble("AccountTokenBalance1"));
		dto.setAccountTokenBalance2(rs
				.getDouble("AccountTokenBalance2"));
		dto.setAccountTypeList(rs.getString("AccountTypeList"));
		dto
				.setCpCampaignEndTime1(rs
						.getTimestamp("CPCampaignEndTime1"));
		dto
				.setCpCampaignEndTime2(rs
						.getTimestamp("CPCampaignEndTime2"));
		dto.setCpCampaignIdList(rs.getString("CPCampaignIDList"));
		dto.setCpCampaignStartTime1(rs
				.getTimestamp("CPCampaignStartTime1"));
		dto.setCpCampaignStartTime2(rs
				.getTimestamp("CPCampaignStartTime2"));
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
		dto.setCustOpenSourceIdList(rs
				.getString("CustOpenSourceIDList"));
		dto.setCustOpenSourceTypeList(rs
				.getString("CustOpenSourceTypeList"));
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
		return dto;
	}
	
	public static FoundCustomerDetaiDTO fillFoundCustomerDetaiDTO(
			ResultSet rs) throws SQLException {
		return fillFoundCustomerDetaiDTO(rs, null);
	}
	
	public static FoundCustomerDetaiDTO fillFoundCustomerDetaiDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		
		FoundCustomerDetaiDTO dto =new FoundCustomerDetaiDTO();
		dto.setBatchNo(rs.getInt(prex + "batchNo"));
		dto.setCardId(rs.getString(prex + "cardId"));
		dto.setComments(rs.getString(prex + "comments"));
		dto.setCreateTime(rs.getTimestamp(prex +"createTime"));
		dto.setCustomerId(rs.getInt(prex +"customerId"));
		dto.setCustomerType(rs.getString(prex +"customerType"));
		dto.setDetailAddress(rs.getString(prex +"detailAddress"));
		dto.setDistrinctId(rs.getInt(prex +"distrinctId"));
		dto.setDt_createTime(rs.getTimestamp(prex +"dt_createTime"));
		dto.setInstallTime(rs.getTimestamp(prex+"installTime"));
		dto.setName(rs.getString(prex +"name"));
		dto.setOncepaymoney(rs.getFloat(prex +"oncepaymoney"));
		dto.setPosterCode(rs.getString(prex+"posterCode"));
		dto.setPrePaymoney(rs.getFloat(prex+"prePaymoney"));
		dto.setProductId(rs.getInt(prex+"productId"));
		dto.setServiceAccountCount(rs.getInt(prex+"serviceAccountCount"));
		dto.setStatus(rs.getString(prex+"status"));
		dto.setTel(rs.getString(prex+"tel"));
		dto.setDt_lastMod(rs.getTimestamp(prex+"dt_lastMod"));
		return dto;
	}

	public static ExportCustomerDetailDTO fillExportCustomerDetaiDTO(
			ResultSet rs) throws SQLException {
		return fillExportCustomerDetaiDTO(rs, null);
	}
	
	public static ExportCustomerDetailDTO fillExportCustomerDetaiDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		
		ExportCustomerDetailDTO dto =new ExportCustomerDetailDTO();
		dto.setBatchNo(rs.getInt(prex+"BATCHNO"));
		dto.setCardID(rs.getString(prex+"CARDID"));
		dto.setCatvid(rs.getString(prex+"CATVID"));
		dto.setComments(rs.getString(prex+"COMMENTS"));
		dto.setCustomerid(rs.getInt(prex+"CUSTOMERID"));
		dto.setCustomerType(rs.getString(prex+"CUSTOMERTYPE"));
		dto.setDetailAddress(rs.getString(prex+"DETAILADDRESS"));
		dto.setDistrinctId(rs.getInt(prex+"DISTRINCTID"));
		dto.setDt_create(rs.getTimestamp(prex+"DT_CREATETIME"));
		dto.setFfcreateTime(rs.getTimestamp(prex+"FFCREATETIME"));
		dto.setName(rs.getString(prex+"NAME"));
		dto.setSerialNo(rs.getString(prex+"SERIALNO"));
		dto.setTel(rs.getString(prex+"TEL"));
		return dto;
	}
	
	public static JqPrintConfigDTO fillJqPrintConfigDTO(
			ResultSet rs) throws SQLException {
		return fillJqPrintConfigDTO(rs, null);
	}
	public static JqPrintConfigDTO fillJqPrintConfigDTO(
			ResultSet rs, String prex) throws SQLException {
		if (prex == null)
			prex = "";
		prex = prex.trim();
		
		JqPrintConfigDTO dto =new JqPrintConfigDTO();
		dto.setBelongto(rs.getInt(prex+"belongto"));
		dto.setParamtype(rs.getString(prex+"paramtype"));
		dto.setParamname(rs.getString(prex+"paramname"));
		dto.setPosition_height(rs.getInt(prex+"position_height"));
		dto.setPosition_width(rs.getInt(prex+"position_width"));
		dto.setPrintFlag(rs.getString(prex+"printFlag"));
		dto.setPrintName(rs.getString(prex+"printName"));
		dto.setPrintName_Blank(rs.getInt(prex+"printName_Blank"));
		dto.setPrintContext(rs.getString(prex+"printContext"));
		dto.setPrintContext_Blank(rs.getInt(prex+"printContext_blank"));
		dto.setPosition_prior(rs.getInt(prex+"position_prior"));
		return dto;
	}
}
