<%@ taglib uri="main" prefix="tbl" %>
<%@ taglib uri="cur" prefix="cust" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="operator" prefix="oper" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="com.dtv.oss.web.util.*" %>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<%
    String strCurBar = null;
    int customerAccountItemNo = 0;
    int serviceAccountItemNo =0;
    String customerAccountMenuId ="";
    String customerAccountTdname ="";
    String serviceAccountMenuId ="";
    String serviceAccountTdname ="";
    String currentOpenServiceAccountID="";
       
    String strSelBar=(String)request.getSession().getAttribute(WebKeys.SIDERBARTDID);
    if (strSelBar==null) strSelBar ="";
    
    String tableId=(String)request.getSession().getAttribute(WebKeys.SIDERBARMENUID);
    if (tableId ==null) tableId ="-1";
%>

<SCRIPT LANGUAGE="JavaScript">

//�ͻ�
function customer_view_submit(strSel)
{
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'customer_view.do';
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function open_account_submit(strSel){
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'account_create_prepare.do';
    document.frmTree.forwardFlag.value ="0";
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function open_service_account(strSel)
{
		var txtCsiType = "<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UO%>";
		var customerType = '<cust:curcust property="customerType" />';
		var openSourceType = '<cust:curcust property="openSourceType" />';
    document.frmTree.action = 'menu_open_service_account.do?txtCsiType='+txtCsiType+'&txtCustType='+customerType+'&txtOpenSourceType='+openSourceType;
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function open_service_account_batchbuy(strSel)
{
		var txtCsiType = "<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UO%>";
		var customerType = '<cust:curcust property="customerType" />';
		var openSourceType = '<cust:curcust property="openSourceType" />';
    document.frmTree.action = 'menu_open_service_account_batchbuy.do?txtCsiType='+txtCsiType+'&txtCustType='+customerType+'&txtOpenSourceType='+openSourceType;
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function open_book_account(strSel)
{
    var customerType = '<cust:curcust property="customerType" />';
		var openSourceType = '<cust:curcust property="openSourceType" />';
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'query_book_account.do?txtCsiType=BU&&txtCustomerID=<cust:curcust property="CustomerID" />&txtCustType='+customerType+'&txtOpenSourceType='+openSourceType+'&txtStatus=W;P';;
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function modify_account_submit(strSel){
        document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
        document.frmTree.forwardFlag.value = '5';
        document.frmTree.action = 'modify_account_prepare_view.do'; 
        document.frmTree.tableId.value ="1";
        document.frmTree.sel_bar.value = strSel;
        document.frmTree.submit();
}

function customer_move_submit(strSel)
{
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.forwardFlag.value = '1';
    document.frmTree.action = 'customer_move_info_prepare.do';
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function customer_transfer_submit(strSel){
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
		if(strSel=='mnu2151st'){
			//21Ϊ���ع���
    	document.frmTree.forwardFlag.value = '21';
    	document.frmTree.action = 'customer_vicinal_transfer_info_prepare.do';
  	}else{
			//22Ϊ��ع���
    	document.frmTree.forwardFlag.value = '22';
    	document.frmTree.action = 'customer_strange_transfer_info_prepare.do';
  	}
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function customer_withdraw_submit(strSel){
        document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
        document.frmTree.forwardFlag.value = '3';
        document.frmTree.action = 'customer_withdraw_info_prepare.do'; 
        document.frmTree.tableId.value ="1";
        document.frmTree.sel_bar.value = strSel;
        document.frmTree.submit();
}

function customer_close_submit(strSel){
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.forwardFlag.value = '4';
    document.frmTree.action = 'customer_close_info_prepare.do'; 
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function batchAddPackage_submit(strStel){
	  var customerType = '<cust:curcust property="customerType" />';
	  document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'batchAddPackage_chooseServiceAccount_Idle.do?txtCustType='+customerType;
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strStel;
    document.frmTree.submit();
}
function batchRenewProtocol_submit(strStel){
	  document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'batchRenewProtocol_Idle.do';
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strStel;
    document.frmTree.submit();
}

function query_serviceaccount_device(strSel)
{
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'query_service_account.do';
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function query_serviceaccount_device()
{
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'dev_query_result_ss.do';
    document.frmTree.tableId.value ="2";
    document.frmTree.submit();
}
function query_serviceaccount_all()
{
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'query_service_account.do';
    document.frmTree.tableId.value ="1";
    document.frmTree.submit();
}

function query_futureright(strSel)
{
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'futureRight_query_result.do';
    document.frmTree.tableId.value ="6";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}


// ��ͻ�����
function group_customer_view_submit(strSel){
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'groupcustomer_view.do';
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}

// ��ͻ����ӿͻ�����
function groupcustomer_open_subcustomer(strSel)
{
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'group_preparefor_contract_select.do?txtGroupCustID=<cust:curcust property="CustomerID" />';
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}


//�ӿͻ���ѯ
function group_leaf_customer_view(strSel){
	  var txtCustomerID = '<cust:curcust property="CustomerID" />';
	  document.frmTree.action = 'group_cust_query.do?txtGroupCustID='+'<cust:curcust property="CustomerID" />';
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function group_to_single_query(strSel){
	  var txtCustomerID = '<cust:curcust property="CustomerID" />';
	  document.frmTree.action = 'group_to_single_query.do?txtActionType=groupToSingle&txtGroupCustID='+txtCustomerID;
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}

//�ʻ�
function account_submit(strID,menuid,strSel)
{
        document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
        document.frmTree.txtAccountID.value = strID;
        document.frmTree.action = 'account_view.do';
        document.frmTree.tableId.value =menuid;
        document.frmTree.sel_bar.value = strSel;
	document.frmTree.submit();
}

function bill_query_result_submit(strID,menuid,strSel){
      document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
      document.frmTree.txtAccountID.value = strID;
      document.frmTree.action = 'bill_query_result_for_cust.do?txtCustomerID='+'<cust:curcust property="CustomerID" />'+'&txtAccountID='+strID;
      document.frmTree.tableId.value =menuid;
      document.frmTree.sel_bar.value = strSel;
      document.frmTree.submit();
}

function fee_record_conditionQuery_submit(strID,menuid,strSel){
      document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
      document.frmTree.txtAccountID.value = strID;
      document.frmTree.action = 'fee_detailrecord_conditionQuery.do?txtCustomerID='+'<cust:curcust property="CustomerID" />'+'&txtAccountID='+strID;
      document.frmTree.tableId.value =menuid;
      document.frmTree.sel_bar.value = strSel;
      document.frmTree.submit();
}


function payment_record_conditionQuery_submit(strID,menuid,strSel){
      document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
      document.frmTree.txtAccountID.value = strID;
      document.frmTree.action = 'payment_record_conditionQuery.do?txtCustomerID='+'<cust:curcust property="CustomerID" />'+'&txtAccountID='+strID;
      document.frmTree.tableId.value =menuid;
      document.frmTree.sel_bar.value = strSel;
      document.frmTree.submit();
}

function adjust_acct_submit(strID,menuid,strSel){
      var custID ='<cust:curcust property="CustomerID" />';
      document.frmTree.action = 'custAcct_adjust_account_query.do?txtCustID='+custID+'&txtAcctID='+strID;
      document.frmTree.tableId.value =menuid;
      document.frmTree.sel_bar.value = strSel;
      document.frmTree.submit();
}


function prepaymentdeduction_submit(strID,menuid,strSel){
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'account_prepaymentdeduction.do?txtCustomerID='+'<cust:curcust property="CustomerID" />'+'&txtAccountID='+strID;
    document.frmTree.txtAccountID.value = strID;
    document.frmTree.tableId.value =menuid;
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}

//ҵ���ʻ�
function view_service_account(strID,menuid,strSel)
{
    document.frmTree.action = 'service_account_query_result_by_sa.do';
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.txtServiceAccountID.value = strID;
    
    document.frmTree.txtOpenTreeServiceAccountID.value = strID;
    
    document.frmTree.tableId.value =menuid;
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.txtTo.value =500;
    document.frmTree.submit();
}

function add_product(strID,menuid,strSel)
{
		var txtCsiType = "<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_PN%>";
		var customerType = '<cust:curcust property="customerType" />';
		var openSourceType = '<cust:curcust property="openSourceType" />';
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.txtServiceAccountID.value = strID;
    
    document.frmTree.txtOpenTreeServiceAccountID.value = strID;
    
    document.frmTree.action = 'cp_add_product.do?txtCsiType='+txtCsiType+'&txtCustType='+customerType+'&txtOpenSourceType='+openSourceType;
    document.frmTree.tableId.value =menuid;
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function query_book_product(strSel)
{
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'menu_query_book_product.do';
    document.frmTree.tableId.value ="1";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function book_product(strID,menuid,strSel)
{
	  var txtCsiType = "<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BP%>";
	  var customerType = '<cust:curcust property="customerType" />';
		var openSourceType = '<cust:curcust property="openSourceType" />';
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.txtServiceAccountID.value = strID;
    
    document.frmTree.txtOpenTreeServiceAccountID.value = strID;
    
    document.frmTree.action = 'menu_book_product.do?txtCsiType='+txtCsiType+'&txtCustType='+customerType+'&txtOpenSourceType='+openSourceType;
    document.frmTree.tableId.value =menuid;
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function pause_service_account(strID,menuid,strSel)
{
    document.frmTree.action = 'service_account_pause_view.do?txtActionType=<%=CommonKeys.SERVICE_ACCOUNT_PAUSE%>';
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.txtServiceAccountID.value = strID;
    
    document.frmTree.txtOpenTreeServiceAccountID.value = strID;
    
    document.frmTree.tableId.value =menuid;
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.txtTo.value =500;
    document.frmTree.submit();
}
function rent_service_account(strID,menuid,strSel)
{
    document.frmTree.action = 'service_account_rent_view.do?txtActionType=<%=CommonKeys.SERVICE_ACCOUNT_RENT%>';
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.txtServiceAccountID.value = strID;
    
    document.frmTree.txtOpenTreeServiceAccountID.value = strID;
    
    document.frmTree.tableId.value =menuid;
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.txtTo.value =500;
    document.frmTree.submit();
}
function friend_phoneno_account(strID,menuid,strSel)
{
    document.frmTree.action = 'query_friend_phoneno.do?txtServiceAccountID='+strID;
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.txtServiceAccountID.value = strID;
    
    document.frmTree.txtOpenTreeServiceAccountID.value = strID;
    
    document.frmTree.tableId.value =menuid;
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function close_service_account(strID,menuid,strSel)
{
    document.frmTree.action = 'service_account_close_view.do?txtActionType=<%=CommonKeys.SERVICE_ACCOUNT_CLOSE%>';
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.txtServiceAccountID.value = strID;
    
    document.frmTree.txtOpenTreeServiceAccountID.value = strID;
    
    document.frmTree.tableId.value =menuid;
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.txtTo.value =500;
    document.frmTree.submit();
}
function beforehand_close_service_account(strID,menuid,strSel)
{
    document.frmTree.action = 'service_account_beforehand_close_view.do?txtActionType=<%=CommonKeys.SERVICE_ACCOUNT_BEFOREHAND_CLOSE%>';
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.txtServiceAccountID.value = strID;
    
    document.frmTree.txtOpenTreeServiceAccountID.value = strID;
    
    document.frmTree.tableId.value =menuid;
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.txtTo.value =500;
    document.frmTree.submit();
}
function real_close_service_account(strID,menuid,strSel)
{
    document.frmTree.action = 'service_account_real_close_view.do?txtActionType=<%=CommonKeys.SERVICE_ACCOUNT_REAL_CLOSE%>';
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.txtServiceAccountID.value = strID;
    
    document.frmTree.txtOpenTreeServiceAccountID.value = strID;
    
    document.frmTree.tableId.value =menuid;
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.txtTo.value =500;
    document.frmTree.submit();
}
function resume_service_account(strID,menuid,strSel)
{
    document.frmTree.action = 'service_account_resume_view.do?txtActionType=<%=CommonKeys.SERVICE_ACCOUNT_RESUME%>';
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.txtServiceAccountID.value = strID;
    
    document.frmTree.txtOpenTreeServiceAccountID.value = strID;
    
    document.frmTree.tableId.value =menuid;
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.txtTo.value =500;
    document.frmTree.submit();
}

function transfer_service_account(strID,menuid,strSel)
{
    document.frmTree.action = 'service_account_transfer_view.do?txtActionType=<%=CommonKeys.SERVICE_ACCOUNT_TRANSFER%>';
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.txtServiceAccountID.value = strID;
    
    document.frmTree.txtOpenTreeServiceAccountID.value = strID;
    
    document.frmTree.tableId.value =menuid;
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.txtTo.value =500;
    document.frmTree.submit();
}

function create_sechedule(strID,menuid,strSel){
   document.frmTree.action = 'show_sacp_for_schedule.do?txtActionType=showForSechedule';
   document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
   document.frmTree.txtServiceAccountID.value = strID;
   
   document.frmTree.txtOpenTreeServiceAccountID.value = strID;
   
   document.frmTree.tableId.value =menuid;
   document.frmTree.sel_bar.value = strSel;
   document.frmTree.txtTo.value =500;
   document.frmTree.submit();
}

//����
function customerServiceInteraction_submit(strSel){
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'customer_serviceInteraction_query.do'; 
    document.frmTree.tableId.value ="4";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}

 

 
function fd_submit() {
 
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'cust_forced_deposit_query_result.do';
    document.frmTree.tableId.value ="4";
  
    document.frmTree.submit();
}



 
function create_problem(strSel,strID)
{
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'query_service_account.do';
    document.frmTree.txtAccountID.value = strID;
    document.frmTree.tableId.value ="4";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function query_csi_submit(strSel)
{
    document.frmTree.action = 'service_interaction_view_for_link.do';
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.tableId.value ="4";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function query_cp_submit(strSel)
{
    document.frmTree.action = 'cp_query_result.do?txtQueryType=queryAll';
    document.frmTree.txtCustID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.tableId.value ="4";
    document.frmTree.txtFrom.value="1";
     document.frmTree.txtTo.value="10";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.submit();
}
function query_jc_submit(strSel)
{        
        document.frmTree.sel_bar.value = strSel;
        document.frmTree.tableId.value ="4";
        document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
        document.frmTree.action = 'jobcard_query.do';
	document.frmTree.submit();
}
function slide_cust_complain_query(strSel)
{        
        document.frmTree.sel_bar.value = strSel;
        document.frmTree.tableId.value ="4";
       // document.frmTree.txtCustomerId.value = '<cust:curcust property="CustomerID" />';
        document.frmTree.action = 'cust_complain_query.do?txtCustomerId=<cust:curcust property="CustomerID" />&actiontype=custQuery&txtQueryType=queryAll';
	document.frmTree.submit();
}
//�ͻ��ײ�
function bundle_campaign_submit(strSel)
{   
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'cust_bundle_query.do?txtCustomerID='+'<cust:curcust property="CustomerID" />'+'&txtActionType=bundle';
    document.frmTree.sel_bar.value = strSel;   
    document.frmTree.tableId.value ="8";
	document.frmTree.submit();
}

//����
function campaign_submit(strSel)
{   
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'cust_campaign_query.do?txtCustomerID='+'<cust:curcust property="CustomerID" />'+'&txtActionType=campaign';
    document.frmTree.sel_bar.value = strSel;   
    document.frmTree.tableId.value ="5";
	document.frmTree.submit();
}
function manual_grant_campaign(strSel){
	document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'manual_grant_campaign_screen.do';
    document.frmTree.sel_bar.value = strSel;   
    document.frmTree.tableId.value ="5";
	document.frmTree.submit();
}
function customer_deposit_query(strSel)
{
      document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
      document.frmTree.action = 'customer_deposit_query.do';
      document.frmTree.sel_bar.value = strSel;
      document.frmTree.tableId.value ="6";
      document.frmTree.txtFrom.value ="1";
      document.frmTree.txtTo.value ="10";
      document.frmTree.submit();
}

function querylog_submit(strSel) {
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.action = 'log_query_for_cust.do';
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.tableId.value ="7";
    document.frmTree.txtFrom.value ="1";
    document.frmTree.txtTo.value ="10";
    document.frmTree.submit();
}

function modify_sechedule(strSel){
   document.frmTree.action = 'schedule_show_tree.do?txtActionType=customer';
   document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
   document.frmTree.tableId.value ="7";
   document.frmTree.sel_bar.value = strSel;
   document.frmTree.txtTo.value =500;
   document.frmTree.submit();
}

//���Ի�����
function customer_billing_rule_query(strSel)
{
    document.frmTree.action = "customer_billing_rule_query.do";
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.tableId.value ="6";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.txtTo.value =500;
    document.frmTree.submit();
}
function customer_billing_ippv(strSel)
{
    document.frmTree.action = "ca_wallet_service_interaction_query.do";
    document.frmTree.txtCustomerID.value = '<cust:curcust property="CustomerID" />';
    document.frmTree.tableId.value ="6";
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.txtTo.value =500;
    document.frmTree.submit();
}

//�¿��ͻ�����
function query_customer_for_create_jobcard(strSel)
{        
    document.frmTree.sel_bar.value = strSel;
    document.frmTree.tableId.value ="4";
    document.frmTree.action = 'query_customer_for_create_jobcard.do?txtCustomerID=<cust:curcust property="CustomerID" />';
    document.frmTree.submit();
}

</script>

<table width="180"  height="600" border="0" cellspacing="0" cellpadding="0" background="img/left_bg.gif">
      <tr>
       <td align="center" valign="top" style="background:url(img/left_top.gif) no-repeat top center; "><img src="img/mao.gif" width="5" height="5">
       <table width="170"  border="0" cellpadding="0" cellspacing="0" bgcolor="#F1F1ED" style="border:1px solid #999999; ">
       <lgc:notempty name="<%=WebKeys.OPERATOR_SESSION_NAME%>" scope="session" >
              <tr>
              <td align="left">
                <table  border="0" cellspacing="5" cellpadding="0">
                    <tr>
                      <td><img src="img/ico_01.gif" width="13" height="12"></td>
                      <td>����Ա��</td> 
                      <td class="blue en_9pt"><oper:curoper property="OperatorName" /></td> 
                    </tr>
                    <!--
                    <tr>
                      <td class="blue en_9pt" colspan="2"><div align="center"><oper:curoper property="OperatorName" /></div></td>
                    </tr>
                    -->
                </table>
              </td>
              </tr>
              <tr>
                <td background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
              <tr>
                <td><table width="100%"  border="0" cellpadding="0" cellspacing="5">
                    <tr>
                      <td align="center" class="blue"><oper:curoper typeName="orgname" /></td>
                    </tr>
                </table></td>
              </tr>
       </lgc:notempty>
       <lgc:empty name="<%=WebKeys.OPERATOR_SESSION_NAME%>" scope="session" >
              <tr>
                <td><table width="100%"  border="0" cellpadding="0" cellspacing="5">
                    <tr>
                      <td align="center" class="blue">û�е�ǰ����Ա</td>
                    </tr>
                </table></td>
              </tr>
       </lgc:empty>
       </table>

       <lgc:notempty name="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>" scope="session" >
         <table width="170"  border="0" cellpadding="0" cellspacing="0" bgcolor="#F1F1ED" style="border:1px solid #999999; ">
           <tr> 
             <td align="center">
               <table  border="0" cellspacing="5" cellpadding="0">
                 <tr>
                   <td><cust:curcust property="Name" />��</td>
                   <td class="blue en_9pt" ><cust:curcust property="CustomerID" /></td>
                 </tr>
               </table>
            </td>
           </tr>
           <tr>
             <td background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
           </tr>
         </table>

       	<d:displayControl id="body_sidebar_single_customer" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
          <table width="170"  border="0" cellspacing="0" cellpadding="0">
              <tr  class="menu1">
                <td width="30" align="center" bgcolor="#005FA1"><A href="javascript:drawSubMenu2('1')"><img id="arr21" src="img/expand-0.gif" border="0"></a></td>
                <td><A class="menu2_" title="�ͻ�" href="javascript:drawSubMenu2('1')">�ͻ�</a></td>
              </tr>
              <tr bgcolor="004677">
                <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
              <tr bgcolor="52B8FF">
                <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
          </table>
          <table width="170" border="0" cellspacing="0" cellpadding="0" id="mnu21" class="menu2" style="<oper:tableDisplay sourceTableId='1' objectTableId='<%=tableId%>' />">
             	<d:displayControl id="link_sidebar_customer_info" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:customer_view_submit('mnu211st')">
                  <oper:tableDisplay sourceTd='mnu211st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ͻ�����</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
            	</d:displayControl>
             	<d:displayControl id="link_sidebar_open_account" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
	            <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:open_account_submit('mnu212st')">
                  <oper:tableDisplay sourceTd='mnu212st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�����ʻ�</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
            	</d:displayControl>
             	<d:displayControl id="link_sidebar_open_service_account" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:open_service_account('mnu213st')">
                  <oper:tableDisplay sourceTd='mnu213st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ŵ�����</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
            	</d:displayControl>
             	<d:displayControl id="link_sidebar_open_service_account_batchbuy" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:open_service_account_batchbuy('mnu2132st')">
                  <oper:tableDisplay sourceTd='mnu2132st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�����ŵ�����</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
            	</d:displayControl>
             	<d:displayControl id="link_sidebar_book_service_account" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:open_book_account('mnu218st')">
                  <oper:tableDisplay sourceTd='mnu218st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;ԤԼ����</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
            	</d:displayControl>
             	<d:displayControl id="link_sidebar_customer_move" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:customer_move_submit('mnu214st')">
                   <oper:tableDisplay sourceTd='mnu214st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ͻ�Ǩ��</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
            	</d:displayControl>
             	<d:displayControl id="link_sidebar_customer_vicinal_transfer" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:customer_transfer_submit('mnu2151st')">
                  <oper:tableDisplay sourceTd='mnu2151st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ͻ�ԭַ����</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
            	</d:displayControl>
             	<d:displayControl id="link_sidebar_customer_strange_transfer" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:customer_transfer_submit('mnu2152st')">
                  <oper:tableDisplay sourceTd='mnu2152st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ͻ���ع���</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
            	</d:displayControl>
             	<d:displayControl id="link_sidebar_customer_close" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:customer_close_submit('mnu216st')">
                  <oper:tableDisplay sourceTd='mnu216st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ͻ�����</oper:tableDisplay>
                </a></td>
              </tr>
               <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
            	</d:displayControl>
             	<d:displayControl id="link_sidebar_customer_withdraw" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:customer_withdraw_submit('mnu217st')">
                  <oper:tableDisplay sourceTd='mnu217st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ͻ��˻�</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
            	</d:displayControl>
            	<d:displayControl id="link_sidebar_batchAddPackage" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:batchAddPackage_submit('mnu2170st')">
                  <oper:tableDisplay sourceTd='mnu2170st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�������Ӳ�Ʒ��</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
            	</d:displayControl>
            	<d:displayControl id="link_sidebar_batchRenewProtocol" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:batchRenewProtocol_submit('mnu21702st')">
                  <oper:tableDisplay sourceTd='mnu21702st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;Э��ۿͻ���������</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
            	</d:displayControl>
            	
             	<d:displayControl id="link_sidebar_customer_query_serviceaccount" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
		          <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td class="middle"><a href="javascript:query_serviceaccount_device('mnu243st')">
                   <oper:tableDisplay sourceTd='mnu243st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;ҵ���ʻ���ѯ</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>  
            	</d:displayControl>
            	<d:displayControl id="link_sidebar_customer_query_bookproduct" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
		          <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td class="middle"><a href="javascript:query_book_product('mnu246st')">
                   <oper:tableDisplay sourceTd='mnu246st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;ԤԼ������Ʒ</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>  
            	</d:displayControl>
            </table>
         </d:displayControl>   
       	<d:displayControl id="body_sidebar_group_customer" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">

            <table width="170"  border="0" cellspacing="0" cellpadding="0">
              <tr  class="menu1">
                <td width="30" align="center" bgcolor="#005FA1"><A href="javascript:drawSubMenu2('1')"><img id="arr21" src="img/expand-0.gif" border="0"></a></td>
                <td><A class="menu2_" title="���ſͻ�" href="javascript:drawSubMenu2('1')">���ſͻ�</a></td>
              </tr>
              <tr bgcolor="004677">
                <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
              <tr bgcolor="52B8FF">
                <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
            </table>
            <table width="170" border="0" cellspacing="0" cellpadding="0" id="mnu21" class="menu2" style="<oper:tableDisplay sourceTableId='1' objectTableId='<%=tableId%>' />">
       	<d:displayControl id="link_sidebar_group_customer_info" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:group_customer_view_submit('mnu211st')">
                  <oper:tableDisplay sourceTd='mnu211st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ͻ�����</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
         </d:displayControl>   
       	<d:displayControl id="link_sidebar_group_customer_open_account" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
	            <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:open_account_submit('mnu212st')">
                  <oper:tableDisplay sourceTd='mnu212st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�����ʻ�</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
         </d:displayControl>   
       	<d:displayControl id="link_sidebar_group_customer_close" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:groupcustomer_close_submit('mnu213st')">
                  <oper:tableDisplay sourceTd='mnu213st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ͻ�����</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
         </d:displayControl>   
       	<d:displayControl id="link_sidebar_group_customer_open_subcustomer" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:groupcustomer_open_subcustomer('mnu214st')">
                  <oper:tableDisplay sourceTd='mnu214st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ӿͻ�����</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
         </d:displayControl>   
       	<d:displayControl id="link_sidebar_group_customer_subcustomer_query" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:group_leaf_customer_view('mnu216st')">
                   <oper:tableDisplay sourceTd='mnu216st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ӿͻ���ѯ</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
         </d:displayControl>   
       	<d:displayControl id="link_sidebar_group_customer_group_to_single" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
              <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:group_to_single_query('mnu221st')">
                   <oper:tableDisplay sourceTd='mnu221st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ӿͻ�ת���˿ͻ�</oper:tableDisplay>
                </a></td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
         </d:displayControl>   
            </table>
        </d:displayControl>  
        <cust:custloop collectionType="<%=String.valueOf(CurrentCustomer.COLLECTION_ACCOUNT)%>"  item="oneAcct">
         <table width="170"  border="0" cellspacing="0" cellpadding="0">
           
            <%
              customerAccountItemNo = customerAccountItemNo+1;
              customerAccountMenuId ="2"+customerAccountItemNo;
                            
              AcctInfoWrap accInfo = (AcctInfoWrap)pageContext.getAttribute("oneAcct");
              pageContext.setAttribute("oneAcct", accInfo.getAcctInfo());

              strCurBar="acct" + accInfo.getAcctInfo().getAccountID();

            %>


           <tr class="menu1">
             <td width="30" align="center" bgcolor="#005FA1"><A href="javascript:drawSubMenu2('2<%=customerAccountItemNo%>')"><img id="arr22<%=customerAccountItemNo%>" src="img/expand-0.gif" border="0"></a></td>
             <td>
             	<A class="menu2_" title="�ͻ�" href="javascript:drawSubMenu2('2<%=customerAccountItemNo%>')">
           			�ʻ�<font style="font-weight: normal">(<tbl:write name="oneAcct" property="AccountID"/>,<d:getcmnname typeName="SET_F_ACCOUNTSTATUS" match="oneAcct:status"/>)</font>
             	</a>
             </td>
           </tr>                                                                
           <tr bgcolor="004677">
             <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
           </tr>
           <tr bgcolor="52B8FF">
             <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
           </tr>
         </table>
         <table width="170" border="0" cellspacing="0" cellpadding="0" id="mnu22<%=customerAccountItemNo%>" class="menu2" style="<oper:tableDisplay sourceTableId='<%=customerAccountMenuId%>' objectTableId='<%=tableId%>' />">
       	<d:displayControl id="link_sidebar_account_info" bean="oneAcct">
             <tr>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <%
                   customerAccountTdname ="mnu2"+customerAccountMenuId+"1st";        
                %>
                <td valign="middle"><a href="javascript:account_submit('<tbl:write name="oneAcct" property="AccountID"/>','2<%=customerAccountItemNo%>','<%=customerAccountTdname%>')">
                   <oper:tableDisplay sourceTd='<%=customerAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ʻ�����</oper:tableDisplay></a></td>
             </tr>
             <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
             </tr>
				</d:displayControl>
       	<d:displayControl id="link_sidebar_account_bill" bean="oneAcct">
	           <tr>
	              <% customerAccountTdname ="mnu2"+customerAccountMenuId+"2st"; %>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:bill_query_result_submit('<tbl:write name="oneAcct" property="AccountID"/>','2<%=customerAccountItemNo%>','<%=customerAccountTdname%>')">
                  <oper:tableDisplay sourceTd='<%=customerAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ʵ���¼</oper:tableDisplay></a></td>
             </tr>
             <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
             </tr>
				</d:displayControl>
       	<d:displayControl id="link_sidebar_account_fee" bean="oneAcct">
             <tr>
                <% customerAccountTdname ="mnu2"+customerAccountMenuId+"3st"; %>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:fee_record_conditionQuery_submit('<tbl:write name="oneAcct" property="AccountID"/>','2<%=customerAccountItemNo%>','<%=customerAccountTdname%>')">
                  <oper:tableDisplay sourceTd='<%=customerAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;���ü�¼</oper:tableDisplay></a></td>
             </tr>
             <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
             </tr>
				</d:displayControl>
       	<d:displayControl id="link_sidebar_account_payment" bean="oneAcct">
             <tr>
                <% customerAccountTdname ="mnu2"+customerAccountMenuId+"4st"; %>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:payment_record_conditionQuery_submit('<tbl:write name="oneAcct" property="AccountID"/>','2<%=customerAccountItemNo%>','<%=customerAccountTdname%>')">
                  <oper:tableDisplay sourceTd='<%=customerAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;֧����¼</oper:tableDisplay></a></td>
             </tr>
             <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
             </tr>
				</d:displayControl>
       	<d:displayControl id="link_sidebar_account_adjust" bean="oneAcct">
             <tr>
               <% customerAccountTdname ="mnu2"+customerAccountMenuId+"5st"; %>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:adjust_acct_submit('<tbl:write name="oneAcct" property="AccountID"/>','2<%=customerAccountItemNo%>','<%=customerAccountTdname%>')">
                  <oper:tableDisplay sourceTd='<%=customerAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;���ʼ�¼</oper:tableDisplay></a></td>
             </tr> 
			       <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
             </tr>
				</d:displayControl>
       	<d:displayControl id="link_sidebar_account_prepaymentdeduction" bean="oneAcct">
             <tr>
                 <% customerAccountTdname ="mnu2"+customerAccountMenuId+"8st"; %>
                <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
                <td valign="middle"><a href="javascript:prepaymentdeduction_submit('<tbl:write name="oneAcct" property="AccountID"/>','2<%=customerAccountItemNo%>','<%=customerAccountTdname%>')">
                  <oper:tableDisplay sourceTd='<%=customerAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;Ԥ��ֿۼ�¼</oper:tableDisplay></a></td>
             </tr>
             <tr>
                <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
				</d:displayControl>
          </table>
        </cust:custloop>
        

<%
		  //�Ƿ��ڿͻ����ϲ�����ʾҵ���ʻ�
		  Boolean isShowNumberSA = Boolean.TRUE;
     	if(session.getAttribute(CommonKeys.CURRENT_CUSTOMER_ISSHOWALLSA_SESSION_NAME)!=null){
     		isShowNumberSA = (Boolean)session.getAttribute(CommonKeys.CURRENT_CUSTOMER_ISSHOWALLSA_SESSION_NAME);
     	}
%>
	<% if(!isShowNumberSA.booleanValue()) {  // start �ͻ�������ʾҵ���ʻ��б� start //��ʾҵ���ʻ��б�͵�ǰҵ���ʻ������û��ѡ��ҵ���ʻ�������ʾ���µ�ҵ���ʻ� %>
		<table width="170"  border="0" cellspacing="0" cellpadding="0">
            <tr class="menu1">
	             <td align="center" bgcolor="#005FA1"></td>
	             <td align="left"><A class="menu2_" title="ҵ���ʻ��б�" href="javascript:query_serviceaccount_device()">&nbsp;ҵ���ʻ��б�(<%=session.getAttribute(CommonKeys.CURRENT_CUSTOMER_SACOUNT_SESSION_NAME)%>)</a></td>
	          </tr>
	          <tr bgcolor="004677">
		          <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
	          </tr>
	          <tr bgcolor="52B8FF">
		          <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
	          </tr>
         </table>
	<%} // end �ͻ�������ʾҵ���ʻ��б� end /////////////////////////// %>     	
	
  <d:displayControl id="body_sidebar_single_customer" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
       <cust:custloop collectionType="<%=String.valueOf(CurrentCustomer.COLLECTION_SERVICEACCOUNT)%>" item="onesa">
        <%
            serviceAccountItemNo = serviceAccountItemNo+1;
            serviceAccountMenuId ="3"+serviceAccountItemNo;
        %>
         <table width="170"  border="0" cellspacing="0" cellpadding="0">
            <tr class="menu1">
	             <td width="30" align="center" valign="top" bgcolor="#005FA1"><A href="javascript:drawSubMenu2('3<%=serviceAccountItemNo%>')"><img id="arr23<%=serviceAccountItemNo%>" src="img/expand-0.gif" border="0"></a></td>
	             <td ><A class="menu2_" title="ҵ���ʻ�" href="javascript:drawSubMenu2('3<%=serviceAccountItemNo%>')">
	             <tbl:write name="onesa" property="description"/>
	             </A>
	             </td>
	          </tr>
	          <tr bgcolor="004677">
		          <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
	          </tr>
	          <tr bgcolor="52B8FF">
		          <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
	          </tr>
         </table>
       
        <!--���ֵ���ҵ���ʻ�����start-->
		<d:displayControl id="body_sidebar_digitalserviceaccount" bean="onesa"> 
         <table width="170" border="0" cellspacing="0" cellpadding="0" id="mnu23<%=serviceAccountItemNo%>" class="menu2" style="<oper:tableDisplay sourceTableId='<%=serviceAccountMenuId%>' objectTableId='<%=tableId%>' />">
       	<d:displayControl id="link_sidebar_serviceaccount_info" bean="onesa">
	         <tr>
	           <% serviceAccountTdname ="mnu2"+serviceAccountMenuId+"1st"; %>
		         <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
		         <td valign="middle"><a href="javascript:view_service_account('<tbl:write name="onesa" property="ServiceAccountID"/>','3<%=serviceAccountItemNo%>','<%=serviceAccountTdname%>')">
		         <oper:tableDisplay sourceTd='<%=serviceAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;ҵ���ʻ�����</oper:tableDisplay></a></td>
	         </tr>
	         <tr>
		        <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
	         </tr>
				</d:displayControl>
       	<d:displayControl id="link_sidebar_serviceaccount_add_product" bean="onesa">
	         <tr>
	           <% serviceAccountTdname ="mnu2"+serviceAccountMenuId+"2st"; %>
	           <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
	           <td valign="middle"><a href="javascript:add_product('<tbl:write name="onesa" property="ServiceAccountID"/>','3<%=serviceAccountItemNo%>','<%=serviceAccountTdname%>')">
	              <oper:tableDisplay sourceTd='<%=serviceAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;������Ʒ</oper:tableDisplay></a></td>
           </tr>
           <tr>
		        <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
		       </tr>
				</d:displayControl>
				<d:displayControl id="link_sidebar_serviceaccount_book_product" bean="onesa">
	         <tr>
	           <% serviceAccountTdname ="mnu2"+serviceAccountMenuId+"3st"; %>
	           <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
	           <td valign="middle"><a href="javascript:book_product('<tbl:write name="onesa" property="ServiceAccountID"/>','3<%=serviceAccountItemNo%>','<%=serviceAccountTdname%>')">
	              <oper:tableDisplay sourceTd='<%=serviceAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;ԤԼ������Ʒ</oper:tableDisplay></a></td>
           </tr>
           <tr>
		        <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
		       </tr>
	      </d:displayControl>
       	<d:displayControl id="link_sidebar_serviceaccount_friend_phoneno" bean="onesa">
		       <tr>
		       	<% serviceAccountTdname ="mnu2"+serviceAccountMenuId+"4st"; %>
	          <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
		        <td valign="middle"><a href="javascript:friend_phoneno_account('<tbl:write name="onesa" property="ServiceAccountID"/>','3<%=serviceAccountItemNo%>','<%=serviceAccountTdname%>')">
		          <oper:tableDisplay sourceTd='<%=serviceAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;����������</oper:tableDisplay></a></td>
		       </tr>
		       <tr>
		         <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
		       </tr>
				</d:displayControl>
       	<d:displayControl id="link_sidebar_serviceaccount_pause" bean="onesa">
	         <tr>
	          <% serviceAccountTdname ="mnu2"+serviceAccountMenuId+"5st"; %>
	           <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
		         <td valign="middle"><a href="javascript:pause_service_account('<tbl:write name="onesa" property="ServiceAccountID"/>','3<%=serviceAccountItemNo%>','<%=serviceAccountTdname%>')">
		          <oper:tableDisplay sourceTd='<%=serviceAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;ҵ���ʻ�ͣ��</oper:tableDisplay></a></td>
	         </tr>
		       <tr>
		         <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
		       </tr>
				</d:displayControl>
		    <d:displayControl id="link_sidebar_device_rent" bean="onesa">
	         <tr>
	          <% serviceAccountTdname ="mnu2"+serviceAccountMenuId+"10st"; %>
	           <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
		         <td valign="middle"><a href="javascript:rent_service_account('<tbl:write name="onesa" property="ServiceAccountID"/>','3<%=serviceAccountItemNo%>','<%=serviceAccountTdname%>')">
		          <oper:tableDisplay sourceTd='<%=serviceAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�豸ת��</oper:tableDisplay></a></td>
	         </tr>
		       <tr>
		         <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
		       </tr>
		    </d:displayControl>
       	<d:displayControl id="link_sidebar_serviceaccount_resume" bean="onesa">
		      <tr>
		        <% serviceAccountTdname ="mnu2"+serviceAccountMenuId+"6st"; %>
		        <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
		        <td valign="middle"><a href="javascript:resume_service_account('<tbl:write name="onesa" property="ServiceAccountID"/>','3<%=serviceAccountItemNo%>','<%=serviceAccountTdname%>')">
		          <oper:tableDisplay sourceTd='<%=serviceAccountTdname%>' objectTd='<%=strSelBar%>'>
		          &nbsp;&nbsp;ҵ���ʻ�����</oper:tableDisplay></a></td>
		      </tr> 
		      <tr>
		        <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
		      </tr>
				</d:displayControl>
       	<d:displayControl id="link_sidebar_serviceaccount_transfer" bean="onesa">
		    <tr>
		      <% serviceAccountTdname ="mnu2"+serviceAccountMenuId+"7st"; %>
		      <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
		      <td valign="middle"><a href="javascript:transfer_service_account('<tbl:write name="onesa" property="ServiceAccountID"/>','3<%=serviceAccountItemNo%>','<%=serviceAccountTdname%>')">
		      <oper:tableDisplay sourceTd='<%=serviceAccountTdname%>' objectTd='<%=strSelBar%>'>
		       &nbsp;&nbsp;ҵ���ʻ�����</oper:tableDisplay></a></td>
		    </tr>
		    <tr>
		      <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
		    </tr>
				</d:displayControl>
       	<d:displayControl id="link_sidebar_serviceaccount_close" bean="onesa">
		    <tr>
		      <% serviceAccountTdname ="mnu2"+serviceAccountMenuId+"8st"; %>
		      <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
		      <td valign="middle"><a href="javascript:close_service_account('<tbl:write name="onesa" property="ServiceAccountID"/>','3<%=serviceAccountItemNo%>','<%=serviceAccountTdname%>')">
		        <oper:tableDisplay sourceTd='<%=serviceAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;ҵ���ʻ�����</oper:tableDisplay></a></td>
		    </tr>
		   <tr>
		     <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
		   </tr>
				</d:displayControl>
	    	<d:displayControl id="link_sidebar_serviceaccount_prechargebacks" bean="onesa">
		    <tr>
		      <% serviceAccountTdname ="mnu2"+serviceAccountMenuId+"11st"; %>
		      <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
		      <td valign="middle"><a href="javascript:beforehand_close_service_account('<tbl:write name="onesa" property="ServiceAccountID"/>','3<%=serviceAccountItemNo%>','<%=serviceAccountTdname%>')">
		        <oper:tableDisplay sourceTd='<%=serviceAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;ҵ���ʻ�Ԥ�˻�</oper:tableDisplay></a></td>
		    </tr>
		    <tr>
		     <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
		   </tr>
		   </d:displayControl>
		   <d:displayControl id="link_sidebar_serviceaccount_real_close" bean="onesa">
		    <tr>
		      <% serviceAccountTdname ="mnu2"+serviceAccountMenuId+"12st"; %>
		      <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
		      <td valign="middle"><a href="javascript:real_close_service_account('<tbl:write name="onesa" property="ServiceAccountID"/>','3<%=serviceAccountItemNo%>','<%=serviceAccountTdname%>')">
		        <oper:tableDisplay sourceTd='<%=serviceAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;ҵ���ʻ�ʵ�˻�</oper:tableDisplay></a></td>
		    </tr>
		   <tr>
		     <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
		   </tr>
		   </d:displayControl>
       <d:displayControl id="link_sidebar_serviceaccount_create_sechedule" bean="onesa">
		   <tr>
		     <% serviceAccountTdname ="mnu2"+serviceAccountMenuId+"9st"; %>
         <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
         <td valign="middle"><a href="javascript:create_sechedule('<tbl:write name="onesa" property="ServiceAccountID"/>','3<%=serviceAccountItemNo%>','<%=serviceAccountTdname%>')">
           <oper:tableDisplay sourceTd='<%=serviceAccountTdname%>' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�����ų�����</oper:tableDisplay>
         </a></td>
       </tr>
       <tr>
         <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
       </tr>
			</d:displayControl>
       
	   </table>
		</d:displayControl>
    <!--���ֵ���ҵ���ʻ�����end-->
    </cust:custloop>
   </d:displayControl>
   
	<!--ҵ���ʻ���Ŀ����10�������-->
	
     <%
     	//if(session.getAttribute(CommonKeys.CURRENT_CUSTOMER_SESSION_NAME)!=null){
     		//CustInfoWrap currentCustomerOfSA=(CustInfoWrap)session.getAttribute(CommonKeys.CURRENT_CUSTOMER_SESSION_NAME);
     		//if(currentCustomerOfSA.isHasMoreSA()){
     			//int curOpSAID=WebUtil.StringToInt(request.getParameter("txtServiceAccountID"));
     			//if(curOpSAID>0 &&(currentOpenServiceAccountID==null || "".equals(currentOpenServiceAccountID)))
		    	//	currentOpenServiceAccountID="mnu23"+curOpSAID;
     %>
     <!--
        <d:displayControl id="link_sidebar_customer_query_serviceaccount" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
     	<table width="170"  border="0" cellspacing="0" cellpadding="0">
         <tr class="menu1">
            <td width="30" align="center" bgcolor="#005FA1"><img id="arr_none_24" src="img/expand-1.gif" border="0"></td>
            <td valign="middle"><a class="menu2_" title="�鿴ȫ��ҵ���ʻ�" href="javascript:query_serviceaccount_all()">�鿴ȫ��ҵ���ʻ�</a></td>
         </tr>
         <tr bgcolor="004677">
            <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
         </tr>
         <tr bgcolor="52B8FF">
            <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
         </tr>
      </table>
      </d:displayControl>
     -->
     <%
     		//}
     	//}
     %>
	<!--end -->     
	
	
	     
     <table width="170"  border="0" cellspacing="0" cellpadding="0">
         <tr class="menu1">
            <td width="30" align="center" bgcolor="#005FA1"><A href="javascript:drawSubMenu2('4')"><img id="arr24" src="img/expand-0.gif" border="0"></a></td>
            <td valign="middle"><A class="menu2_" title="������Ϣ" href="javascript:drawSubMenu2('4')">������Ϣ</a></td>
         </tr>
         <tr bgcolor="004677">
            <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
         </tr>
         <tr bgcolor="52B8FF">
            <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
         </tr>
      </table>

      <table width="170" border="0" cellspacing="0" cellpadding="0" id="mnu24" class="menu2" style="<oper:tableDisplay sourceTableId='4' objectTableId='<%=tableId%>' />">
         <tr>
             <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
             <td valign="middle"><a href="javascript:customerServiceInteraction_submit('mnu241st')"><oper:tableDisplay sourceTd='mnu241st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;����</oper:tableDisplay></a></td>
         </tr>
         <tr>
              <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
         </tr>
       	<d:displayControl id="body_sidebar_single_customer" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
 
	       <tr>
             <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
             <td valign="middle"><a href="javascript:query_cp_submit('mnu242st')"><oper:tableDisplay sourceTd='mnu242st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;���޵�</oper:tableDisplay></a></td>
         </tr>
         <tr>
             <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
         </tr>
         <tr>
             <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
             <td valign="middle"><a href="javascript:query_jc_submit('mnu244st')"><oper:tableDisplay sourceTd='mnu244st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;����</oper:tableDisplay></a></td> 
          </tr>
          <tr>
             <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
          </tr>
          <tr>
             <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
             <td valign="middle"><a href="javascript:slide_cust_complain_query('mnu245st')"><oper:tableDisplay sourceTd='mnu245st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;Ͷ��</oper:tableDisplay></a></td>
          </tr>
          <tr>
             <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
          </tr>
       	<d:displayControl id="link_sidebar_ServiceInteraction_create_problem" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
         <tr>
             <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
             <td class="middle">&nbsp;<a href="javascript:create_problem('mnu243st','<tbl:write name="oneAcct" property="AccountID"/>')"><oper:tableDisplay sourceTd='mnu243st' objectTd='<%=strSelBar%>'>��������</oper:tableDisplay></a></td>
         </tr>
         <tr>
             <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
          </tr>
		</d:displayControl>
		<d:displayControl id="link_sidebar_customer_for_create_jobcard" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
		<tr>
             <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
             <td valign="middle"><a href="javascript:query_customer_for_create_jobcard('mnu273st')"><oper:tableDisplay sourceTd='mnu273st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;ģ���¿�����</oper:tableDisplay></a></td>
        </tr>
        <tr>
            <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
        </d:displayControl>
    </d:displayControl>
       </table>
<!--�ͻ��ײ�start-->             
<d:displayControl id="body_sidebar_customer_bundle" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">       
       <table width="170"  border="0" cellspacing="0" cellpadding="0">
         <tr class="menu1">
            <td width="30" align="center" bgcolor="#005FA1"><A href="javascript:drawSubMenu2('8')"><img id="arr28" src="img/expand-0.gif" border="0"></a></td>
            <td valign="middle"><A class="menu2_" title="�ײ�" href="javascript:drawSubMenu2('8')">�ײ�</a></td>
         </tr>
         <tr bgcolor="004677">
            <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
         </tr>
         <tr bgcolor="52B8FF">
            <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
         </tr>
      </table>
      <table width="170" border="0" cellspacing="0" cellpadding="0" id="mnu28" class="menu2" style="<oper:tableDisplay sourceTableId='8' objectTableId='<%=tableId%>' />">
	       <tr>
	         	<td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
	         	<td valign="middle"><a href="javascript:bundle_campaign_submit('mnu281st')"><oper:tableDisplay sourceTd='mnu281st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ͻ��ײ�</oper:tableDisplay></a></td>
	       </tr>
	       <tr>
	         	<td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
	       </tr>
      </table>
</d:displayControl>      
<!--�ͻ��ײ�end-->      
    <d:displayControl id="body_sidebar_single_customer" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
    </d:displayControl>
<!--�ͻ�����start-->                     
<d:displayControl id="body_sidebar_customer_campaign" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">               
     <table width="170"  border="0" cellspacing="0" cellpadding="0" >
        <tr  class="menu1">
          <td width="30" align="center" bgcolor="#005FA1"><A href="javascript:drawSubMenu2('5')"><img id="arr25" src="img/expand-0.gif" border="0"></A></td>
          <td valign="middle"><A class="menu2_" title="����"  href="javascript:drawSubMenu2('5')" />����</a></td>
        </tr>
        <tr bgcolor="004677">
          <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
        <tr bgcolor="52B8FF">
          <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
               
      <table width="170" border="0" cellspacing="0" cellpadding="0" id="mnu25" class="menu2" style="<oper:tableDisplay sourceTableId='5' objectTableId='<%=tableId%>' />">
	       <tr>
	         	<td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
	         	<td valign="middle"><a href="javascript:campaign_submit('mnu251st')"><oper:tableDisplay sourceTd='mnu251st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ͻ������</oper:tableDisplay></a></td>
	       </tr>
	       <tr>
	         	<td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
	       </tr>
				<d:displayControl id="link_sidebar_grant_campaign" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
		     <tr>
	        	<td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
	         	<td valign="middle"><a href="javascript:manual_grant_campaign('mnu252st')"><oper:tableDisplay sourceTd='mnu252st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ֹ���������</oper:tableDisplay></a></td>
	       </tr>
				</d:displayControl>
      </table>
</d:displayControl> 

<!--�ͻ�����end-->
<d:displayControl id="body_sidebar_customer_finance" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">      
      <table width="170"  border="0" cellspacing="0" cellpadding="0" >
        <tr  class="menu1">
          <td width="30" align="center" bgcolor="#005FA1"><A href="javascript:drawSubMenu2('6')"><img id="arr26" src="img/expand-0.gif" border="0"></A></td>
          <td valign="middle"><A class="menu2_" title="���������"  href="javascript:drawSubMenu2('6')" />���������</a></td>
        </tr>
        <tr bgcolor="004677">
          <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
        <tr bgcolor="52B8FF">
          <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
               
      <table width="170" border="0" cellspacing="0" cellpadding="0" id="mnu26" class="menu2" style="<oper:tableDisplay sourceTableId='6' objectTableId='<%=tableId%>' />">
	      <d:displayControl id="link_sidebar_customer_futureright" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
	      <pri:authorized name="futureRight_query_result.do">
	       <tr>
	         	<td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
	         	<td valign="middle"><a href="javascript:query_futureright('mnu261st')"><oper:tableDisplay sourceTd='mnu261st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;��Ȩ</oper:tableDisplay></a></td>
	       </tr>
	       <tr>
	         	<td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
	       </tr>
	       </pri:authorized>
	       </d:displayControl>
				<d:displayControl id="link_sidebar_customer_foregift" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
				<pri:authorized name="customer_deposit_query.do">
		     <tr>
	        	<td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
	         	<td valign="middle"><a href="javascript:customer_deposit_query('mnu262st')"><oper:tableDisplay sourceTd='mnu262st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;Ѻ��</oper:tableDisplay></a></td>
	       </tr>
	       <tr>
	         	<td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
	       </tr>
	       			</pri:authorized>
				</d:displayControl>
		<d:displayControl id="link_sidebar_customer_billingrule" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
		<pri:authorized name="customer_billing_rule_create.screen/">
		     <tr>
	        	<td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
	         	<td valign="middle"><a href="javascript:customer_billing_rule_query('mnu263st')"><oper:tableDisplay sourceTd='mnu263st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;���Ի�����</oper:tableDisplay></a></td>
	       </tr>
	       <tr>
	         	<td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
	       </tr>
	       </pri:authorized>
	       </d:displayControl>
		<d:displayControl id="link_sidebar_customer_ippv" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">		
	       <pri:authorized name="ca_wallet_service_interaction_query.do">
	       <tr>
	        	<td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
	         	<td valign="middle"><a href="javascript:customer_billing_ippv('mnu264st')"><oper:tableDisplay sourceTd='mnu264st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;IPPVǮ��</oper:tableDisplay></a></td>
	       </tr>
	       <tr>
	         	<td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
	       </tr>
	       </pri:authorized>
	       </d:displayControl>
      </table>
	</d:displayControl>
	    <table width="170"  border="0" cellspacing="0" cellpadding="0" >
        <tr  class="menu1">
          <td width="30" align="center" bgcolor="#005FA1"><A href="javascript:drawSubMenu2('7')"><img id="arr27" src="img/expand-0.gif" border="0"></A></td>
          <td valign="middle"><A class="menu2_" title="ϵͳ"  href="javascript:drawSubMenu2('7')" />ϵͳ</a></td>
        </tr>
        <tr bgcolor="004677">
          <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
        <tr bgcolor="52B8FF">
          <td colspan="2"><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
      <table width="170" border="0" cellspacing="0" cellpadding="0" id="mnu27" class="menu2" style="<oper:tableDisplay sourceTableId='7' objectTableId='<%=tableId%>' />">
	       <tr>
	         	<td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
	         	<td valign="middle"><a href="javascript:querylog_submit('mnu271st')">
	         		 <oper:tableDisplay sourceTd='mnu271st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;��־</oper:tableDisplay>
	         	</a></td>
	       </tr>
	       <tr>
	         	<td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
	       </tr>
	       
	       <tr>
           <td width="30" height="28" class="submenu2_d"><img src="img/dot_01.gif" width="4" height="4"></td>
           <td valign="middle"><a href="javascript:modify_sechedule('mnu272st')">
             <oper:tableDisplay sourceTd='mnu272st' objectTd='<%=strSelBar%>'>&nbsp;&nbsp;�ų�����</oper:tableDisplay>
           </a></td>
         </tr>
         <tr>
           <td colspan="2" bgcolor="ECECEC" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
         </tr>

      </table>
      
   </lgc:notempty>
          <br>
         </td>
      </tr>
    </table>
<form name="frmTree" method="post">
<input type="hidden" name="sel_bar" value="">
<input type="hidden" name="txtCustomerID" value="">
<input type="hidden" name="txtCustID" value="">
<input type="hidden" name="txtAccountID" value="">
<input type="hidden" name="txtFrom" value="1">
<input type="hidden" name="txtTo" value="10">
<input type="hidden" name="txtServiceAccountID" value="">
<input type="hidden" name="forwardFlag" value="">
<input type="hidden" name="tableId" value="<%=tableId%>" >

<input type="hidden" name="txtOpenTreeServiceAccountID" value="" >

</form>
<%if(!"".equals(currentOpenServiceAccountID)){%>
<SCRIPT LANGUAGE="JavaScript">
    drawSubMenu2('<%=currentOpenServiceAccountID%>');
    showSubMenu2('<%=currentOpenServiceAccountID%>');
</script> 
<%}%>
