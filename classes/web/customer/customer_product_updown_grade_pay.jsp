<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<Script language=javascript>
function back_submit(){
	 document.frmPost.action="customer_product_updown_grade_fee.screen";
	 document.frmPost.submit();
}

function frm_next(){
	document.frmPost.action="customer_product_updown_grade_confirm.screen";
	document.frmPost.submit();
}
</SCRIPT>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">客户产品升降级</td>
	</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>

<br>
<form name="frmPost" method="post">
<input type="hidden" name="func_flag" value="5002">
<input type="hidden" name="txtServiceAccountID" value="<tbl:writeparam name="txtServiceAccountID"/>">
<input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID"/>">
<input type="hidden" name="txtAccount" value="<tbl:writeparam name="txtAccount"/>">
<input type="hidden" name="txtCustomerName" value="<tbl:writeparam name="txtCustomerName"/>">
<input type="hidden" name="txtProductName" value="<tbl:writeparam name="txtProductName"/>">
<input type="hidden" name="txtStatusShow" value="<tbl:writeparam name="txtStatusShow"/>">
<input type="hidden" name="txtPackageName" value="<tbl:writeparam name="txtPackageName"/>">
<input type="hidden" name="txtCreateTime" value="<tbl:writeparam name="txtCreateTime"/>">
<input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption"/>" >
<tbl:hiddenparameters pass="txtActionType/txtProdID/txtPsID/txtObjectProduct" />
<%
   String actionType = request.getParameter("txtActionType"); 
   String csiType ="";
   if (Integer.parseInt(actionType) == CommonKeys.CUSTOMER_PRODUCT_UPGRADE){
       csiType =CommonKeys.CUSTSERVICEINTERACTIONTYPE_PU;
   } else if (Integer.parseInt(actionType) == CommonKeys.CUSTOMER_PRODUCT_DOWNGRADE){
       csiType =CommonKeys.CUSTSERVICEINTERACTIONTYPE_PD;
   }
%>
	
<tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=csiType%>" acctshowFlag ="true" confirmFlag="false" />		 	
		
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="20" ></td>  
		<td><img src="img/button2_r.gif" width="22" height="20"></td>
		<td><input name="next" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
		<td><img src="img/button2_l.gif" width="11" height="20"></td>
		<td width="20" ></td>
		<td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
		<td><input name="next" type="button" class="button" onClick="javascript:frm_next()" value="下一步"></td>
		<td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table>
</form>