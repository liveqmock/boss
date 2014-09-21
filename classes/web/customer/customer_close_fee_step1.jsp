<%@ page import="java.lang.Throwable" %>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<script language=javascript>
<!--
function back_submit() {
	document.frmPost.action="customer_close_create.do";
	document.frmPost.submit();
}

function frm_submit(){
   if (check_fee()){
   	  document.frmPost.action="customer_close_fee_step2.do";
	    document.frmPost.submit();
	 }
}

//-->
</SCRIPT>

<table border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户销户--费用支付</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<form name="frmPost" method="post" action="">
 <tbl:hiddenparameters pass="txtCustomerID/txtName/txtCloseReason/txtAccount/txtCloseReasonName/txtAccountName" />
 <input type="hidden" name="acceptPayFlag" value="false" >
 <input type="hidden" name="readyForeturnFeeFlag" value="true"> 
  
 <table align="center" width="810" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
    <tr ><td>
      <tbl:CommonFeeAndPaymentInfo includeParameter="CloseAcctFee_step1" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_CC%>" checkMoneyFlag="2" />		 		
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



