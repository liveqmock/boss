<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>

<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>

<BR>
<%
		String strProductID=request.getParameter("txtProductID");

			%>
<script language="JavaScript" type="text/JavaScript">
<!--
	function return_click(){
		document.frmPost.action ="cpconfigedproperty_view.screen";
		document.frmPost.submit();
	}
//-->
</script>
<TABLE  width="820" border="0" align="center" cellspacing="0" cellpadding="0">
	<TR>
		<TD align="center">
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="10">
			<tr>
				<td class="title" align="center" valign="middle"><img
					src="img/list_tit.gif" width="19" height="19">&nbsp;&nbsp;提示信息</td>
			</tr>
		</table>
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>
		<br>
		<lgc:haserror>
			<table width="50%" border="0" cellspacing="10" cellpadding="0">
				<tr align="center">
					<td width="182"><img src="img/icon_error.gif" width="182"
						height="182"></td>
					<td class="ok"><font color="red"><i>操作不成功，错误信息如下:</i></font>
					<br>
					<tbl:errmsg /></td>
				</tr>
			</table>
		</lgc:haserror> <lgc:hasnoterror>
			<table width="50%" border="0" cellspacing="10" cellpadding="0">
				<tr align="center">
					<td width="182"><img src="img/icon_ok.gif" width="182" height="182"></td>
					<td class="ok">操作成功。</td>
				</tr>
			</table>
		</lgc:hasnoterror> <br>
		<br>
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>
		<form name="frmPost" method="post" action="cpconfigedproperty_view.screen">
		<tbl:hiddenparameters pass="txtServiceAccountID/txtAccountID/txtCustomerID/txtProductID/txtPsID/txtSerialNo/txtDeviceID/txtStatus" />

		<!-- <INPUT TYPE="hidden" name="txtProductID" value="<tbl:writeparam name="txtProductID"/>">
		<INPUT TYPE="hidden" name="txtPsID" value="<tbl:writeparam name="txtPsID"/>"> -->

		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="10">
			<tr align="center">
				<td height="37">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="img/button2_r.gif" border="0" width="22" height="20" ></td>
					   <td background="img/button_bg.gif"  ><a href="<bk:backurl property="customer_product_view.do" />" class="btn12">返回产品信息</a></td>
					   <td><img src="img/button2_l.gif" border="0" width="11" height="20" ></td>
					</tr>
				</table>
				</td>
				<td width="20%">&nbsp;</td>
			</tr>
		</table>
		</form>
</table>
