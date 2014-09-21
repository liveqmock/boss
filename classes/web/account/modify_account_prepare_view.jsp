<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="osstags" prefix="o" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.Map,
                 java.util.HashMap,
                 java.util.Iterator,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>

<form name="frmPost" method="post" action="">
<%
   int accountSize =0;
%>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
<%
    accountSize =accountSize+1;
    
    com.dtv.oss.dto.wrap.customer.Account2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Account2AddressWrap)pageContext.getAttribute("oneline");
   
    String currentStatus=Postern.getCustomerStatusByID(wrap.getAcctDto().getCustomerID());
    String currentAccountStatus=wrap.getAcctDto().getStatus();
    if (currentAccountStatus==null) currentAccountStatus="";
    boolean bIsNormalCust = false;
    boolean isNotCancelCust = true;
    if ((currentStatus!=null) && currentStatus.equals("C"))
        isNotCancelCust = false;
    if ((currentStatus!=null) && currentStatus.equals("N"))
        bIsNormalCust = true;
    
    String billingcyclename = Postern.getBillingCycleTypeNameByID(wrap.getAcctDto().getBillingCycleTypeID());
    if (billingcyclename == null) billingcyclename = "";
    pageContext.setAttribute("oneline", wrap.getAcctDto());
    pageContext.setAttribute("acctaddr", wrap.getAddrDto());
  
%>  
	<input type="hidden" name="txtAccountType"   value="<tbl:write name="oneline" property="accountType" />"  >
	
    <input type="hidden" name="txtAccountTypeShow"   value="<o:getcmnname typeName="SET_F_ACCOUNTTYPE" match="oneline:accountType" />"   >

    <input type="hidden" name="txtCreateTime"   value="<tbl:write name="oneline" property="CreateTime" />"   >
		
    <input type="hidden" name="txtStatusShow"  value="<o:getcmnname typeName="SET_F_ACCOUNTSTATUS" match="oneline:status" />"    >
		
    <input type="hidden" name="txtPostcode"  value="<tbl:write name="acctaddr" property="postcode" />"  >
		
    <input type="hidden" name="txtBBF"  value="<tbl:write name="oneline" property="Bbf" />"   >
    
    <input type="hidden" name="txtCashDeposit" value="<tbl:write name="oneline" property="CashDeposit" />" >
				
    <input type="hidden" name="txtTokenDeposit"  value="<tbl:write name="oneline" property="TokenDeposit" />"  >
    			
    <input type="hidden" name="txtBillingCycleName"  value="<%=billingcyclename%>" />
		
    <input type="hidden" name="txtBankAccountStatus"  value="<o:getcmnname typeName="SET_F_ACCOUNTBANKACCOUNTSTATUS" match="oneline:BankAccountStatus" />"  >
		 
    <input type="hidden" name="txtBankAccount" value="<tbl:write name="oneline" property="bankAccount" />"   >
		
    <input type="hidden" name="txtBankAccountName"  value="<tbl:write name="oneline" property="bankAccountName" />"  >
		
    <input type="hidden" name="txtAccountName"  value="<tbl:write name="oneline" property="accountName" />"  >
	      
    <input type="hidden" name="txtMopID" value="<tbl:write name="oneline" property="mopID" />" >
    		
    <input type="hidden" name="txtbillCounty" value="<tbl:write name="acctaddr"  property="districtID" />">
             
    <input type="hidden" name="txtDetailAddress"  value="<tbl:write name="acctaddr" property="detailAddress" />"  >

    <input type="hidden" name="txtAccountDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />"    >
	       
    <input type="hidden" name="txtAddressDtLastmod"   value="<tbl:write name="acctaddr" property="DtLastmod" />"    >
    
    <input type="hidden" name="txtAddressID"   value="<tbl:write name="acctaddr" property="addressID" />"    >    
    
    <input type="hidden" name="txtAccountID" value="<tbl:write name="oneline" property="accountID" />"  >

<BR>

 </lgc:bloop>     
 <input type="hidden" name="txtCustomerID"    value="<tbl:writeparam name="txtCustomerID" />" >
 <input type="hidden" name="txtCsiID"   value="<tbl:writeparam name="txtCsiID" />">
 <input type="hidden" name="txtAccountSize"  value="<%=accountSize%>" >
 </form>
</td></tr></table>
<script>
     document.frmPost.action="modify_account_view.do";
     document.frmPost.submit();
</script>
