/*
 * Created on 2005-1-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.util;


/**
 * @author 220226
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommonUtility {
	public static String getSelectExpression4ServiceAccount(String prefix) {
		String strSelect =
			"sa#ServiceAccountID sa_ServiceAccountID, sa#ServiceID sa_ServiceID, "
				+ "sa#CustomerID sa_CustomerID, sa#UserID sa_UserID, sa#SubscriberID sa_SubscriberID, "
				+ "sa#CreateTime sa_CreateTime, sa#Status sa_Status, sa#user_Type sa_user_Type,sa#Description sa_Description, "
				+ "sa#Dt_Create sa_Dt_Create, sa#Dt_Lastmod sa_Dt_Lastmod, sa#ServiceCode sa_ServiceCode, sa#ServiceAccountName sa_ServiceAccountName";
		return strSelect.replaceAll("sa#", prefix);
	}
	public static String getSelectExpression4Address(String prefix) {
		String strSelect =
			"addr#Postcode addr_Postcode, addr#DistrictID addr_DistrictID,"
				+ "addr#DetailAddress addr_DetailAddress,"
				+ "addr#AddressID addr_AddressID,addr#Dt_Create addr_Dt_Create,addr#Dt_Lastmod addr_Dt_Lastmod";
		return strSelect.replaceAll("addr#", prefix);
	}
	public static String getSelectExpression4JobCardProcess(String prefix) {
		String strSelect =
			"jcp#Action jcp_Action,jcp#Memo jcp_Memo,jcp#CurrentOrgId jcp_CurrentOrgId,"
				+ "jcp#SeqNo jcp_SeqNo,jcp#JcId jcp_JcId,jcp#CurrentOperatorId jcp_CurrentOperatorId,"
				+ "jcp#ProcessMan jcp_ProcessMan,jcp#ProcessOrgId jcp_ProcessOrgId,jcp#WorkResult jcp_WorkResult,"
				+ "jcp#WorkResultReason jcp_WorkResultReason,jcp#WorkDate jcp_WorkDate,jcp#WorkTime jcp_WorkTime,"
				+ "jcp#OutOfDateReason jcp_OutOfDateReason,jcp#NextOrgId jcp_NextOrgId,"
				+ "jcp#ActionTime jcp_ActionTime,jcp#Dt_Create jcp_Dt_Create,jcp#Dt_Lastmod jcp_Dt_Lastmod";
		return strSelect.replaceAll("jcp#", prefix);
	}
	
	
	public static String getSelectExpression4Address2(String prefix) {
		String strSelect =
			"addr#Postcode addr2_Postcode, addr#DistrictID addr2_DistrictID,"
				+ "addr#DetailAddress addr2_DetailAddress,"
				+ "addr#AddressID addr2_AddressID,addr#Dt_Create addr2_Dt_Create,addr#Dt_Lastmod addr2_Dt_Lastmod";
		return strSelect.replaceAll("addr#", prefix);
	}
	
	public static String getSelectExpression4NewCustomerInfo(String prefix) {
		String strSelect =
			"nci#Name nci_Name,nci#Id nci_Id,nci#CsiID nci_CsiID,nci#CustID nci_CustID,"
				+ "nci#CatvID nci_CatvID,nci#DigitalCatvID nci_DigitalCatvID,nci#Type nci_Type,"
				+ "nci#Nationality nci_Nationality,nci#Gender nci_Gender,nci#CardType nci_CardType,"
				+ "nci#MARKETSEGMENTID nci_MARKETSEGMENTID,nci#CardID nci_CardID,nci#Birthday nci_Birthday,"
				+ "nci#OpenSourceType nci_OpenSourceType,nci#OpenSourceID nci_OpenSourceID,"
				+ "nci#Telephone nci_Telephone,nci#MobileNumber nci_MobileNumber,nci#FaxNumber nci_FaxNumber,nci#Email nci_Email,"
				+ "nci#GroupBargainID nci_GroupBargainID,nci#ContractNo nci_ContractNo,nci#TimeRangeStart nci_TimeRangeStart,"
				+ "nci#TimeRangeEnd nci_TimeRangeEnd,nci#FromAddressID nci_FromAddressID,nci#ToAddressID nci_ToAddressID,"
				+ "nci#Dt_Create nci_Dt_Create,nci#Dt_Lastmod nci_Dt_Lastmod,nci#SocialSecCardID nci_SocialSecCardID,nci#custstyle nci_custstyle,"
				+ "nci#groupcustID nci_groupcustID,nci#agentName nci_agentName,"
				+ "nci#loginID nci_loginID,nci#loginPWD nci_loginPWD,nci#comments nci_comments,nci#customerSerialNo nci_customerSerialNo";

		return strSelect.replaceAll("nci#", prefix);
	}
	public static String getSelectExpression4CustProblemProcess(String prefix) {
	    String strSelect = 
	        "cpp#ID cpp_ID,cpp#CUSTPROBLEMID cpp_CUSTPROBLEMID,cpp#CURRENTORGID cpp_CURRENTORGID,"
	        +"cpp#CURRENTOPERATORID cpp_CURRENTOPERATORID,cpp#ACTION cpp_ACTION,cpp#CREATEDATE cpp_CREATEDATE,"
	        +"cpp#WORKRESULT cpp_WORKRESULT,cpp#WORKRESULTREASON cpp_WORKRESULTREASON,cpp#NEXTORGID cpp_NEXTORGID,"
	        +"cpp#DT_CREATE cpp_DT_CREATE,cpp#DT_LASTMOD cpp_DT_LASTMOD";
	    return strSelect.replaceAll("cpp#", prefix);
	}

	
	public static String getSelectExpression4NewCustAccountInfo(String prefix) {
		String strSelect =
			"ncai#Id ncai_Id,ncai#CsiID ncai_CsiID,ncai#AccountID ncai_AccountID,ncai#AccountName ncai_AccountName,ncai#ACCOUNTTYPE ncai_AccountType,"
				+ "ncai#Description ncai_Description,ncai#MopID ncai_MopID,ncai#BankAccountName ncai_BankAccountName,"
				+ "ncai#BankAccount ncai_BankAccount,ncai#AddressID ncai_AddressID,ncai#AddressFlag ncai_AddressFlag,"
				+ "ncai#Dt_Create ncai_Dt_Create,ncai#Dt_Lastmod ncai_Dt_Lastmod,ncai#comments ncai_comments";
		return strSelect.replaceAll("ncai#", prefix);
	}
	public static String getSelectExpress4CustomerInfo(String prefix){
		String strSelect="cust#CUSTOMERID cust_CUSTOMERID,"
			+"cust#CUSTOMERSTYLE cust_CUSTOMERSTYLE,"
			+"cust#CUSTOMERTYPE cust_CUSTOMERTYPE,"
			+"cust#GENDER cust_GENDER,"
			+"cust#TITLE cust_TITLE,"
			+"cust#NAME cust_NAME,"
			+"cust#BIRTHDAY cust_BIRTHDAY,"
			+"cust#NATIONALITY cust_NATIONALITY,"
			+"cust#CARDTYPE cust_CARDTYPE,"
			+"cust#CARDID cust_CARDID,"
			+"cust#SOCIALSECCARDID cust_SOCIALSECCARDID,"
			+"cust#LOGINID cust_LOGINID,"
			+"cust#LOGINPWD cust_LOGINPWD,"
			+"cust#TELEPHONE cust_TELEPHONE,"
			+"cust#TELEPHONEMOBILE cust_TELEPHONEMOBILE,"
			+"cust#FAX cust_FAX,"
			+"cust#EMAIL cust_EMAIL,"
			+"cust#ORGID cust_ORGID,"
			+"cust#OPENSOURCETYPE cust_OPENSOURCETYPE,"
			+"cust#OPENSOURCETYPEID cust_OPENSOURCETYPEID,"
			+"cust#GROUPBARGAINID cust_GROUPBARGAINID,"
			+"cust#MARKETSEGMENTID cust_MARKETSEGMENTID,"
			+"cust#ADDRESSID cust_ADDRESSID,"
			+"cust#CATVID cust_CATVID,"
			+"cust#CURACCUMULATEPOINT cust_CURACCUMULATEPOINT,"
			+"cust#TOTALACCUMULATEPOINT cust_TOTALACCUMULATEPOINT,"
			+"cust#CREATETIME cust_CREATETIME,"
			+"cust#STATUS cust_STATUS,"
			+"cust#DT_CREATE cust_DT_CREATE,"
			+"cust#DT_LASTMOD cust_DT_LASTMOD,"
			+"cust#GROUPCUSTID cust_GROUPCUSTID,"
			+"cust#AGENTNAME cust_AGENTNAME,"
			+"cust#comments cust_comments,"
			+"cust#customerSerialNo cust_customerSerialNo,"
			+"cust#CONTRACTNO cust_CONTRACTNO";  
		return strSelect.replaceAll("cust#", prefix);
	}

	public static String getSelectExpress4DistrictInfo(String prefix){
		String strSelect=
			"dist#ID dist_ID,"
			+"dist#DISTRICTCODE dist_DISTRICTCODE,"
			+"dist#NAME dist_NAME,"
			+"dist#TYPE dist_TYPE,"
			+"dist#BELONGTO dist_BELONGTO,"
			+"dist#STATUS dist_STATUS,"
			+"dist#DT_CREATE dist_DT_CREATE,"
			+"dist#DT_LASTMOD dist_DT_LASTMOD";
		return strSelect.replaceAll("dist#", prefix);
	}
	public static String getSelectExpress4CommonSettingDataInfo(String prefix){
		String strSelect=
			"comm#NAME comm_NAME,"
			+"comm#KEY comm_KEY,"
			+"comm#VALUE comm_VALUE,"
			+"comm#DESCRIPTION comm_DESCRIPTION,"
			+"comm#STATUS comm_STATUS,"
			+"comm#DT_CREATE comm_DT_CREATE,"
			+"comm#DT_LASTMOD comm_DT_LASTMOD,"
			+"comm#PRIORITY comm_PRIORITY,"
			+"comm#DEFAULTORNOT comm_DEFAULTORNOT,"
			+"comm#GRADE comm_GRADE";
		return strSelect.replaceAll("comm#", prefix);
	}
	public static String getSelectExpress4AccountInfo(String prefix) {
		String strSelect ="acct#ACCOUNTID acct_ACCOUNTID,"
			+"acct#CUSTOMERID acct_CUSTOMERID,"
			+"acct#ACCOUNTNAME acct_ACCOUNTNAME,"
			+"acct#ACCOUNTCLASS acct_ACCOUNTCLASS,"
			+"acct#ACCOUNTTYPE acct_ACCOUNTTYPE,"
			+"acct#INVOICELAYOUTFORMAT acct_INVOICELAYOUTFORMAT,"
			+"acct#MOPID acct_MOPID,"
			+"acct#BANKACCOUNT acct_BANKACCOUNT,"
			+"acct#CARDTYPE acct_CARDTYPE,"
			+"acct#CARDID acct_CARDID,"
			+"acct#BANKACCOUNTSTATUS acct_BANKACCOUNTSTATUS,"
			+"acct#ADDRESSID acct_ADDRESSID,"
			+"acct#BILLADDRESSFLAG acct_BILLADDRESSFLAG,"
			+"acct#INVALIDADDRESSREASON acct_INVALIDADDRESSREASON,"
			+"acct#CURRENCY acct_CURRENCY,"
			+"acct#REDIRECTACCOUNTID acct_REDIRECTACCOUNTID,"
			+"acct#BALANCE acct_BALANCE,"
			+"acct#BBF acct_BBF,"
			+"acct#SMALLCHANGE acct_SMALLCHANGE,"
			+"acct#CASHDEPOSIT acct_CASHDEPOSIT,"
			+"acct#TOKENDEPOSIT acct_TOKENDEPOSIT,"
			+"acct#BILLINGCYCLETYPEID acct_BILLINGCYCLETYPEID,"
			+"acct#NEXTINVOICEDATE acct_NEXTINVOICEDATE,"
			+"acct#CREATETIME acct_CREATETIME,"
			+"acct#STATUS acct_STATUS,"
			+"acct#DT_CREATE acct_DT_CREATE,"
			+"acct#DT_LASTMOD acct_DT_LASTMOD,"
			+"acct#BANKACCOUNTNAME acct_BANKACCOUNTNAME,"
			+"acct#COMMENTS acct_COMMENTS,"
			+"acct#REDIRECTTYPE acct_REDIRECTTYPE";
		return strSelect.replaceAll("acct#", prefix);
	}
	
	public static String getSelectExpress4BatchJobItemInfo(String prefix){
		String strSelect ="item#ITEMNO  ITEM_ITEMNO,"
			             +"item#BATCHID  item_BATCHID,"
			             +"item#CUSTOMERID  item_CUSTOMERID, "
		                 +"item#ACCOUNTID  item_ACCOUNTID," 
		                 +"item#USERID  item_USERID,"
		                 +"item#CUSTPACKAGEID  item_CUSTPACKAGEID,"
		                 +"item#PSID  item_PSID," 
		                 +"item#CCID  item_CCID,"
		                 +"item#RCDDATA  item_RCDDATA ,"
		                 +"item#STATUS  item_STATUS,"
		                 +"item#STATUSTIME  item_STATUSTIME ,"
		                 +"item#DT_CREATE  item_DT_CREATE ,"
		                 +"item#DT_LASTMOD  item_DT_LASTMOD";
		return strSelect.replaceAll("item#", prefix);
	}
	//为取结果而拼凑的字段(可能拼出全是空的字段) 开户回访[预约增机和门店增机用] begin
	public static String getSelectNullAddress2() {
		String strSelect =
			"'' addr2_Postcode, 0 addr2_DistrictID,"
				+ "'' addr2_DetailAddress,"
				+ "0 addr2_AddressID,systimestamp addr2_Dt_Create,systimestamp addr2_Dt_Lastmod";
		return strSelect;
	}
	public static String getSelectExpression4Customer(String prefix) {
		String strSelect =
			"nci#Name nci_Name,nci#CustomerID nci_Id,0 nci_CsiID,nci#CustomerID nci_CustID,"
				+ "nci#CatvID nci_CatvID,'' nci_DigitalCatvID,nci#CustomerType nci_Type,"
				+ "nci#Nationality nci_Nationality,nci#Gender nci_Gender,nci#CardType nci_CardType,"
				+ "nci#MARKETSEGMENTID nci_MARKETSEGMENTID,nci#CardID nci_CardID,nci#Birthday nci_Birthday,"
				+ "nci#OpenSourceType nci_OpenSourceType,0 nci_OpenSourceID,"
				+ "nci#Telephone nci_Telephone,nci#TelephoneMobile nci_MobileNumber,nci#Fax nci_FaxNumber,nci#Email nci_Email,"
				+ "nci#GroupBargainID nci_GroupBargainID,nci#ContractNo nci_ContractNo,sysdate nci_TimeRangeStart,"
				+ "sysdate nci_TimeRangeEnd,nci#AddressID nci_FromAddressID,nci#AddressID nci_ToAddressID,"
				+ "nci#Dt_Create nci_Dt_Create,nci#Dt_Lastmod nci_Dt_Lastmod,nci#SocialSecCardID nci_SocialSecCardID,nci#CUSTOMERSTYLE nci_custstyle,"
				+ "nci#groupcustID nci_groupcustID,nci#agentName nci_agentName,"
				+ "nci#loginID nci_loginID,nci#loginPWD nci_loginPWD,nci#comments nci_comments,nci#customerSerialNo nci_customerSerialNo";

		return strSelect.replaceAll("nci#", prefix);
	}
	public static String getSelectNullCustAccountInfo() {
		String strSelect =
			"0 ncai_Id,0 ncai_CsiID,0 ncai_AccountID,'' ncai_AccountName,'' ncai_AccountType,"
				+ "'' ncai_Description,0 ncai_MopID,'' ncai_BankAccountName,"
				+ "'' ncai_BankAccount,0 ncai_AddressID,'' ncai_AddressFlag,"
				+ "systimestamp ncai_Dt_Create,systimestamp ncai_Dt_Lastmod,'' ncai_comments";
		return strSelect;
	}
	//为取结果而拼凑的字段(可能拼出全是空的字段) 开户回访[预约增机和门店增机用] end
}
