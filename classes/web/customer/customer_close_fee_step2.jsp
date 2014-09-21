<%@ page import="java.lang.Throwable" %>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<script language=javascript>
<!--
function frm_submit(){
	 if (check_fee()){
	 	   document.frmPost.action ="customer_close_confirm.screen";
	     document.frmPost.submit();
	 }
}

function back_submit() {
	 document.frmPost.action="customer_close_fee_wrap_step1data.do";
	 document.frmPost.submit();
}

//-->
</SCRIPT>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户销户--退费信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<form name="frmPost" method="post" action="close_customer_confirm.screen">
 <tbl:hiddenparameters pass="txtCustomerID/txtName/txtCloseReason/txtAccount/txtCloseReasonName/txtAccountName" />
 <table align="center" width="810" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
    <tr ><td>
     <%
        String acceptPayFlag =(request.getParameter("acceptPayFlag") ==null) ? "true" :request.getParameter("acceptPayFlag");
     %>
      <tbl:CommonFeeAndPaymentInfo includeParameter="CloseAcctFee_step2" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_CC%>" checkMoneyFlag="1" acceptPayFlag="<%=acceptPayFlag%>" />		 		
   </td></tr>
</table>
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr>
   <td><img src="img/button2_r.gif" width="22" height="20"></td>
   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td>
   <td width="20" ></td>
   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
</tr>
</table>  
</form>



