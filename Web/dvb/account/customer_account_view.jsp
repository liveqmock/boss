<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.Map,
                 java.util.HashMap,
                 java.util.Iterator,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition,
                 com.dtv.oss.web.util.WebOperationUtil,
                 com.dtv.oss.util.TimestampUtility" %>

<SCRIPT language="JAVASCRIPT">
<%
    StringBuffer lstBankMop = Postern.getBankMopBuffer();
    
    pageContext.setAttribute("AllMOPList" , Postern.getOpeningMop());
    
    int needRecoverCount = 0;
%>
var listBankMop=["9#9"<%=lstBankMop%>];
  
function IndexOfBankMop(str)
{
    for (var i=0; i < listBankMop.length; i++)
    {
        if (listBankMop[i] == str) return i;      
    }

    return -1;
}

function check_txtMopID(){
		if (document.frmPost.txtMopID.value=='')
        {
        	alert("����ѡ�񸶷�����");
        	document.frmPost.txtMopID.focus();
        	return false;
        }
        if (IndexOfBankMop(document.frmPost.txtMopID.value)>=0){
        if(document.frmPost.txtBankAccount!=null&&document.frmPost.txtBankAccountName!=null){
           if (trim(document.frmPost.txtBankAccount.value) =='' || trim(document.frmPost.txtBankAccountName.value) ==''){
               alert("���ָ������ͱ������������ʻ��������ʻ���");
               if (trim(document.frmPost.txtBankAccount.value) ==''){
                  document.frmPost.txtBankAccount.focus();
                  return false;
               }
               if (trim(document.frmPost.txtBankAccountName.value) ==''){
               	  document.frmPost.txtBankAccountName.focus();
               	  return false;
               }
           } 
        }
        } else {
        if(document.frmPost.txtBankAccount!=null&&document.frmPost.txtBankAccountName!=null){
            if (trim(document.frmPost.txtBankAccount.value) != '' || trim(document.frmPost.txtBankAccountName.value) != '') {
                alert("���ָ������Ͳ��������������ʻ�!");
                return false;
            }
        }
        }
		return true;
}  
function check_txtAccountTypeShow(){
	if (check_Blank(document.frmPost.txtAccountTypeShow, true, "�ʻ�����"))
	  	return false;  	
	return true;  	
}
function check_txtAccountName(){
	if (check_Blank(document.frmPost.txtAccountName, true, "�ʻ�����"))
		  return false; 	
	return true;		  	  
}
function check_txtPostcode(){
	if (check_Blank(document.frmPost.txtPostcode,true,"�ʵ������ʱ�"))
	    return false;
	return true;	    
}
function check_txtDetailAddress(){
	if (check_Blank(document.frmPost.txtDetailAddress, true, "�ʵ���ַ"))
		  return false;
	return true;		  
}
function check_txtBillAddressFlag(){
	if(document.frmPost.txtBillAddressFlag.value=='Y'){
     if (check_Blank(document.frmPost.txtInvalidAddressReason, true, "�ʵ���ַ����ԭ��"))
		  return false;	      
 	 }
	return true;
}
function check_txtbillCounty(){
	if (check_Blank(document.frmPost.txtbillCounty, true, "�ʵ����͵�ַ������"))
		  return false;
	return true;		  
}

function edit_submit(){
	
	 if (check_form()){
        if (window.confirm('��ȷ��Ҫ�޸ĸ��ʻ���Ϣ��?')){
           document.frmPost.action="account_modify.do";
        	 document.frmPost.actionType.value="update";
        	 document.frmPost.submit();
     }
   }
}

function close_account(accid) {
    if (confirm("��ȷ��Ҫȡ�����ʻ���")) {
        location.href="account_cancel.do?txtAccountID="+accid + "&txtCustomerID="+document.frmPost.txtCustomerID.value
                        +"&forwardFlag=1";
    }
}
function  billaddress_onchange(){
	if(document.frmPost.txtBillAddressFlag.value=='N'){
		document.frmPost.txtInvalidAddressReason.value="";
		document.frmPost.txtInvalidAddressReason.disabled="true";
	}else if(document.frmPost.txtBillAddressFlag.value=='Y'){
		document.frmPost.txtInvalidAddressReason.disabled="";
	}else{
		document.frmPost.txtInvalidAddressReason.value="";
		document.frmPost.txtInvalidAddressReason.disabled="true";
	}
}

function account_adjust_submit(){
	document.frmPost.action="account_adjust.do";
	document.frmPost.submit();
}

function account_manual_fee(){
	document.frmPost.action="menu_account_manual_fee.do";
	document.frmPost.submit();
}
function customer_info_print(customerID,acctID){
		document.frmForPrintPost.target="_blank";
		document.frmForPrintPost.action="customer_info_print.do?txtCustomerID="+customerID+"&txtAccountID="+acctID;
		document.frmForPrintPost.submit();
		document.frmForPrintPost.target="_self";
}

function customer_identity_print(customerID,acctID){
		document.frmForPrintPost.target="_blank";
		document.frmForPrintPost.action="customer_identity_print.do?txtCustomerID="+customerID+"&txtAccountID="+acctID;
		document.frmForPrintPost.submit();
		document.frmForPrintPost.target="_self";
}
function account_feepay_print(acctID){
	 document.frmForPrintPost.action="account_feepay_query.do";
	 document.frmForPrintPost.submit();
}
</SCRIPT>

<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top">
	<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">�鿴�ʻ�����</td>
      </tr>
	</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
         <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
</table>
<br>

<form name="frmPost" method="post" action="">
<input type="hidden" name="txtSetOffFlag" value ="" >


<%
   int accountID=0;
%>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
<%
    com.dtv.oss.dto.wrap.customer.Account2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Account2AddressWrap)pageContext.getAttribute("oneline");
    String billingcyclename = Postern.getBillingCycleTypeNameByID(wrap.getAcctDto().getBillingCycleTypeID());
    
    /**�õ��ʻ������**************start*********/
    accountID = wrap.getAcctDto().getAccountID();
    String balance = Postern.getBalanceByAcctId(accountID);
    /**�õ��ʻ������**************end*********/
    		
    if (billingcyclename == null) billingcyclename = "";
    pageContext.setAttribute("oneline", wrap.getAcctDto());
    pageContext.setAttribute("acctaddr", wrap.getAddrDto());
    
    needRecoverCount=Postern.getSaCountByAcctIDAndStatusAndServiceID(wrap.getAcctDto().getAccountID(),"'H','S'",6);
%>   	     
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="5" class="fulltable" >   
<d:DynamicForm formName="AccountView" functionName="check_form()" column="4">
   

	<d:DynamicFormElement elementName="txtAccountID" >	
  		<td class="list_bg2"><div align="right">�ʻ���</div></td>
	  	<td class="list_bg1">
			  <input type="text" name="txtAccountID" size="25"  value="<tbl:write name="oneline" property="accountID" />" class="textgray" readonly   >
		  </td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="txtAccountType" >
		  <td class="list_bg2"><div align="right">�ʻ�����*</div></td>
		  <td class="list_bg1">
			  <input type="text" name="txtAccountTypeShow" size="25"  value="<o:getcmnname typeName="SET_F_ACCOUNTTYPE" match="oneline:accountType" />" class="textgray" readonly   >
			  <input type="hidden" name="txtAccountType" value ="<tbl:write name="oneline" property="accountType" />" >
		  </td>
	</d:DynamicFormElement>	  

    <d:DynamicFormElement elementName="txtAccountName" functionName="check_txtAccountName()">
      <td class="list_bg2"><div align="right">�ʻ�����*</div></td>
	    <td class="list_bg1">
		    <input type="text" name="txtAccountName" size="25" maxlength="25" value="<tbl:write name="oneline" property="accountName" />" class="text" >
	    </td> 
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="txtCreateTime" >		    
		  <td class="list_bg2"><div align="right">����ʱ��</div></td>
		  <td class="list_bg1">
			 <input type="text" name="txtCreateTime" size="25"  value="<tbl:writedate name="oneline" property="CreateTime" />"  class="textgray" readonly >
		  </font></td>
	</d:DynamicFormElement> 

	<d:DynamicFormElement elementName="txtBillingCycleTypeShow" >
       <td class="list_bg2"><div align="right">������������</div></td>
		   <td class="list_bg1">
          <input type="hidden" name="txtBillingCycleTypeShow" value="<tbl:write name="oneline" property="BillingCycleTypeID" />"/>
			   <input type="text" name="txtBillingCycleName" size="25" value="<%=billingcyclename%>" class="textgray" readonly/>
		   </td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="txtMopID"  functionName="check_txtMopID()">		 
       <td class="list_bg2"><div align="right">��������*</div></td>
	     <td class="list_bg1">
		        <tbl:select name="txtMopID" set="AllMOPList" match="oneline:mopID" width="23" />
	      </td>
	</d:DynamicFormElement>      
	<d:DynamicFormElement elementName="txtBankAccount" >
			 <td class="list_bg2"><div align="right">�����ʺ�</div></td>
			 <td class="list_bg1">
				<input type="text" name="txtBankAccount" size="25" maxlength="25" value="<tbl:write name="oneline" property="bankAccount"/>"   class="text">
			 </td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="txtBankAccountName" >
			 <td class="list_bg2"><div align="right">�����ʻ���</div></td>
		    <td class="list_bg1">
			   <input type="text" name="txtBankAccountName" size="25" maxlength="25" value="<tbl:write name="oneline" property="bankAccountName"/>" class="text" >
			</td>
	</d:DynamicFormElement>

	<d:DynamicFormElement elementName="txtBankAccountStatus" >
      	         <td class="list_bg2"><div align="right">����״̬</div></td>
		<td class="list_bg1">
			<input type="text" name="txtBankAccountStatus" size="25" class="textgray" readonly value="<o:getcmnname typeName="SET_F_ACCOUNTBANKACCOUNTSTATUS" match="oneline:BankAccountStatus" />"  >
		 </td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="txtBBF" >
		 <td class="list_bg2" align="right">�ʻ������</td>
	   <td class="list_bg1">
	   <input type="text" name="txtBBF" size="25" class="textgray" readonly value="<%=balance%>"  >
	    </td>
	</d:DynamicFormElement>

	<d:DynamicFormElement elementName="txtCashDeposit" >
		<td class="list_bg2"><div align="right">Ԥ���ֽ�</div></td>
		<td class="list_bg1">
			<input type="text" name="txtCashDeposit" size="25"  value="<tbl:write name="oneline" property="CashDeposit" />"  class="textgray" readonly >
		 </td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="txtTokenDeposit" >
		<td class="list_bg2"><div align="right">Ԥ���������</div></td>
		<td class="list_bg1">
			<input type="text" name="txtTokenDeposit" size="25"  value="<tbl:write name="oneline" property="TokenDeposit" />"  class="textgray" readonly >
		</td>
	</d:DynamicFormElement>

	<d:DynamicFormElement elementName="txtbillCounty"  functionName="check_txtbillCounty()">
               <td class="list_bg2"><div align="right">�ʵ����͵�ַ������</div></td>
               <td class="list_bg1">
                        <input type="hidden" name="txtbillCounty" value="<tbl:write name="acctaddr"  property="districtID" />">
	                <input type="text" name="txtbillCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo name="acctaddr" property="districtID" />" class="text">
                        <input name="selDistButton1" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all;leaf','txtbillCounty','txtbillCountyDesc')">
               </td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="txtDetailAddress" functionName="check_txtDetailAddress()">
	       <td class="list_bg2"><div align="right">�ʵ�������ϸ��ַ*</div></td>
		<td class="list_bg1">
			<input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<tbl:write name="acctaddr" property="detailAddress" />" class="text" >
		 </td>
	</d:DynamicFormElement>

	<d:DynamicFormElement elementName="txtPostcode" functionName="check_txtPostcode()">
	        <td class="list_bg2"><div align="right">�ʵ������ʱ�*</div></td>
		<td class="list_bg1" >
			<input type="text" name="txtPostcode" size="25" maxlength="6" value="<tbl:write name="acctaddr" property="postcode" />"  class="text">
		</td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="txtBillAddressFlag" >
		<td class="list_bg2"><div align="right">�ʵ���ַ�Ƿ����</div></td>
		<td class="list_bg1" >
		        <o:selcmn name="txtBillAddressFlag" mapName="SET_G_YESNOFLAG" match="<%=wrap.getAcctDto().getBillAddressFlag()%>" width="23" onchange="javascript:billaddress_onchange();"  />
		</td>
	</d:DynamicFormElement>	

	<d:DynamicFormElement elementName="txtInvalidAddressReason" functionName="check_txtBillAddressFlag()">
		   <td class="list_bg2"><div align="right">�ʵ���ַ����ԭ��</div></td>
		   <td class="list_bg1" >
		        <o:selcmn name="txtInvalidAddressReason"  mapName="SET_F_INVALIDADDRESSREASON" match="oneline:InvalidAddressReason" width="23"/>
		   </td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="txtRedirectType" >
		   <td class="list_bg2"><div align="right">�ʻ��ض�������</div></td>
		   <td class="list_bg1">
			   <input type="text" name="txtRedirectType" size="25"  value="<o:getcmnname typeName="SET_F_ACCOUNTDIRECTTYPE" match="oneline:redirectType" />" class="textgray" readonly   >
		   </td>
	</d:DynamicFormElement>

	<d:DynamicFormElement elementName="txtStatusShow" >
       <td class="list_bg2"><div align="right">�ʻ�״̬</div></td>
		   <td class="list_bg1">
			   <input type="text" name="txtStatusShow" size="25"  value="<o:getcmnname typeName="SET_F_ACCOUNTSTATUS" match="oneline:status" />" class="textgray" readonly   >
		   </td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="txtRedirectAccountID" >
		   <td class="list_bg2"><div align="right">�ʻ��ض������</div></td>
		   <td class="list_bg1">
			    <input type="text" name="txtRedirectAccountID" size="25"  value="<tbl:write name="oneline" property="redirectAccountID" />" class="textgray" readonly   >
		   </td>
	</d:DynamicFormElement>

	<d:DynamicFormElement elementName="txtDtLastmod">
        <td class="list_bg2"><div align="right">�ʻ�������ʱ��</div></td>
		    <td class="list_bg1" >
			   <input type="text" name="txtDtLastmod" size="25"  value="<tbl:writedate name="oneline" property="dtLastmod" includeTime="true"/>" class="textgray" readonly   >
		    </td>
	</d:DynamicFormElement>
	
	<d:DynamicFormElement elementName="txtNbrDate">
      	<td class="list_bg2"><div align="right">ģ��ɷѽ�������</div></td>
		    <td class="list_bg1">
		    <%
		      String overTime ="";
          int singalsServiceCount = Postern.getSaCountByAcctIDAndStatusAndServiceID(wrap.getAcctDto().getAccountID(),"'N','I'",6);
          if (singalsServiceCount >0){
		          overTime =WebOperationUtil.changeMoneyToDate(wrap.getAcctDto().getAccountID());
		      }else{
		      	  overTime ="��ͣ״̬����ģ��ҵ����ο��ʻ���";
		      }
		    %>
			   <input type="text" name="txtNbrDate" size="25"  value="<%=overTime%>" class="textgray" readonly   >
		    </td>
	</d:DynamicFormElement>

  <d:DynamicFormElement elementName="txtComments" column="4">
       <td class="list_bg2"><div align="right">��ע��Ϣ</div></td>
		   <td class="list_bg1" colspan="3">
		        <input type="text" name="txtComments"  class="text"   size="86" value="<tbl:write  name="oneline" property="comments" />" >
		   </td>
	</d:DynamicFormElement>

</d:DynamicForm>
   </table>
      
      <input type="hidden" name="txtCustomerID"   value="<tbl:write name="oneline" property="customerID" />"    >
      <input type="hidden" name="txtAccStatus"   value="<tbl:write name="oneline" property="status" />"    >
      <input type="hidden" name="txtAccountDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />"    >
      
      <input type="hidden" name="txtAddressID"   value="<tbl:write name="acctaddr" property="addressID" />"    >    
      <input type="hidden" name="txtAddressDtLastmod"   value="<tbl:write name="acctaddr" property="DtLastmod" />"    >
            
      <input type="hidden" name="txtInvoiceBatchPayAndPrey" value="">
      <input type="hidden" name="actionType" value="update">
      <input type="hidden" name="func_flag" value="1">
      
<BR>
  
 <%
    
    //����Ƿ���δ�����ʵ�
    int iUnpaidInvoiceAccount = Postern.getUnpaidInvoiceCountByAcctID(wrap.getAcctDto().getAccountID());
     
    if (iUnpaidInvoiceAccount>0){
    
%>
<table align="center" border="0" cellspacing="0" cellpadding="0">	
  	<tr>
  		<td align="right">
  		<font size="3" color="red" >
		���˻���<%=iUnpaidInvoiceAccount%>��δ���ʵ�!
		</font>
		</td>
  	</tr>
  		
  </table>
<%    
            
    }  
%>  

</table>   
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>    
<table align="center" border="0" cellspacing="0" cellpadding="0">
    <td >
		 <table align="center" border="0" cellspacing="0" cellpadding="0">	
        <tr>            
       	<d:displayControl id="button_account_view_modify" bean="oneline">
       		 <td width="20" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:edit_submit()" class="btn12">��&nbsp;��</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
				</d:displayControl>
       	<d:displayControl id="button_account_view_close_account" bean="oneline">
       	<pri:authorized name="account_cancel.do">
           <td width="20" ></td>
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:close_account('<tbl:write name="oneline" property="accountID" />')" class="btn12">ȡ���ʻ�</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
        </pri:authorized>
	</d:displayControl>
       	<d:displayControl id="button_account_view_pre_pay" bean="oneline">
       	<pri:authorized name="pre_pay_submit.do">
           <td width="20" ></td>              
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:pre_pay_submit()" class="btn12">Ԥ����</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
        </pri:authorized>
	</d:displayControl>
       	<d:displayControl id="button_account_view_adjust" bean="oneline">
       	<pri:authorized name="account_adjust.do"> 
           <td width="20" ></td>           
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:account_adjust_submit()" class="btn12">�ʻ�����</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
        </pri:authorized>
				</d:displayControl>
	 
	<d:displayControl id="button_customer_account_manual_fee" bean="oneline">
	<pri:authorized name="menu_account_manual_fee.do">			
	   <td width="20" ></td>           
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:account_manual_fee()" class="btn12">�ֹ��շ�</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
           </pri:authorized>
        </d:displayControl>   
   <%
	  if (iUnpaidInvoiceAccount>0){
	%>
		 <pri:authorized name="bill_batch_query_for_singleAccount.do">
					 <td width="20" ></td>           
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:bill_batch_pay_submit()" align="center" class="btn12">����֧���ʵ�</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
		</pri:authorized>
	<%
	  }
	%>         
    </tr>
    </table>
		<tr>
				<td>&nbsp;</td>
		</tr>
    <tr>
			<td>
			<table align="center" border="0" cellspacing="0" cellpadding="0">	
				<tr>
          <pri:authorized name="customer_info_print.do">   
          <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="Submit" type="button" class="button" value="�û��ǼǱ��ӡ" onclick="javascript:customer_info_print(<%=wrap.getAcctDto().getCustomerID()%>,<tbl:write name="oneline" property="accountID" />)"></td>
   		    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
   		    </pri:authorized>
   		    <td width="20" ></td>
   		    <pri:authorized name="customer_identity_print.do">  
          <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="Submit" type="button" class="button" value="�û��ɷѿ���ӡ" onclick="javascript:customer_identity_print(<%=wrap.getAcctDto().getCustomerID()%>,<tbl:write name="oneline" property="accountID" />)"></td>
   		    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>   		  
          </pri:authorized>
          <td width="20" ></td>
          <pri:authorized name="account_feepay_query.do">   
          <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="Submit" type="button" class="button" value="�û��ɷ��嵥��ӡ" onclick="javascript:account_feepay_print(<tbl:write name="oneline" property="accountID" />)"></td>
   		    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
   		    </pri:authorized>
        </tr>
      </table>
     </td>
   </tr>
</table>

<%if("Y".equals(wrap.getAcctDto().getBillAddressFlag())){%>
<SCRIPT language="JAVASCRIPT">
	document.frmPost.txtInvalidAddressReason.disabled="";
</SCRIPT>
<%}else{%>
<SCRIPT language="JAVASCRIPT">
	document.frmPost.txtInvalidAddressReason.disabled="true";
</SCRIPT>
<%}%>

</lgc:bloop>      

</form>
<form name="frmForPrintPost" method="post" >
	<input type="hidden" name="txtCreateStartDate" value ="<%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM-dd")%>">
  <input type="hidden" name="txtCreateEndDate" value ="<%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM-dd")%>">
  <input type="hidden" name="txtacctID"   value="<%=accountID%>" >
</form>
<SCRIPT language="JAVASCRIPT">
function pre_pay_submit(){
	 var comfirm_flag =false;
	 var signalsRecoverCount =<%=needRecoverCount%>;
   if(signalsRecoverCount >0){
      if (window.confirm("��ʾ�����˻��ж�Ӧ��ͣ��ģ���շ��նˣ�����ɻָ�������,�Ƿ������")){
      	 comfirm_flag =true;
      }
   }else{
   	  comfirm_flag =true;
   }
   if (comfirm_flag){
      document.frmPost.action="pre_pay_submit.do";
      document.frmPost.submit();
   }
}
function bill_batch_pay_submit(){
	 var comfirm_flag =false;
	 var signalsRecoverCount =<%=needRecoverCount%>;
   if(signalsRecoverCount >0){
      if (window.confirm("��ʾ�����˻��ж�Ӧ��ͣ��ģ���շ��նˣ�����ɻָ�������,�Ƿ������")){
      	 comfirm_flag =true;
      }
   }else{
   	  comfirm_flag =true;
   }
   if (comfirm_flag){ 
	   document.frmPost.action="bill_batch_query_for_singleAccount.do";
	   document.frmPost.submit();
	 }
}
</SCRIPT>

