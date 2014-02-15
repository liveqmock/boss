<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ page import="com.dtv.oss.dto.wrap.customer.Account2AddressWrap" %>


<table width="98%" border="0" cellspacing="5" cellpadding="5">
<tr> 
  <td width="100%"><div align="center"> 
      <p align="center" class="title1">正在获取预约信息，请稍候。。。</strong></p>
    </div></td>
  
</tr>
</table>
      
<%
   String forwardFlag =(request.getParameter("forwardFlag")==null) ? "" :request.getParameter("forwardFlag");
%>      

<form name="frmPost" method="post" action="" >
  <input type="hidden" name="forwardFlag" value="<%=forwardFlag%>" >
  <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true">      
  <%
     String txtCustomerID =request.getParameter("txtCustomerID");
     Account2AddressWrap wrap = (Account2AddressWrap)pageContext.getAttribute("oneline");
     pageContext.setAttribute("oneline", wrap.getAcctDto());
     pageContext.setAttribute("addr", wrap.getAddrDto());
   
  %>   
   <input type="hidden" name="txtCustomerID" value="<%=txtCustomerID%>" >
   <tbl:hidden name="txtAcctID" value="oneline:AccountID" />
   <tbl:hidden name="txtStatus" value="oneline:Status" />
   <tbl:hidden name="txtAccountTypeShow" value="oneline:accountType" />
   <tbl:hidden name="txtDetailAddress" value="addr:DetailAddress" />
   <tbl:hidden name="txtPostcode" value="addr:Postcode" />
   <tbl:hidden name="txtCounty" value="addr:districtID" />
   <tbl:hidden name="txtBalance" value="oneline:Balance" />
   <tbl:hidden name="txtBBF" value="oneline:BbF" />
   <tbl:hidden name="txtCashDeposit" value="oneline:CashDeposit" />
   <tbl:hidden name="txtTokenDeposit" value="oneline:TokenDeposit" />
   <tbl:hidden name="txtBillingCycleTypeShow" value="oneline:BillingCycleTypeID" />
   <tbl:hidden name="txtBankAccountStatus" value="oneline:BankAccountStatus" />
   <tbl:hidden name="txtBankAccount" value="oneline:bankAccount" />
   <tbl:hidden name="txtCreateTime" value="oneline:CreateTime" />
   <tbl:hidden name="txtMopID" value="oneline:mopID" />
   <tbl:hidden name="txtAccountName" value="oneline:accountName" />
   <tbl:hidden name="txtDetailAddress" value="addr:detailAddress" />
   <tbl:hidden name="txtAddressId" value="addr:addressID" />
   <input type="hidden" name="txtAccountDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />"    >
   <input type="hidden" name="txtAddressDtLastmod"   value="<tbl:write name="addr" property="DtLastmod" />"    >
  </lgc:bloop>
</form>

<Script language=Javascript>
  var forward_flag ="<%=forwardFlag%>";
  if (forward_flag ==""){
      alert("没有传入的参数，系统不知道是页面转向何出！");
      window.close();
  }

  if (forward_flag =="1"){
    document.frmPost.action ="account_cancel.screen" ;
    document.frmPost.submit();
  }
  if (forward_flag =="2") {
  	document.frmPost.action ="group_account_cancel.screen" ;
  	document.frmPost.submit(); 
  }
     
</Script>