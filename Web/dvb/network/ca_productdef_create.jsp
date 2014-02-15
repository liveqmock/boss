<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.CAProductDTO"%>

<SCRIPT language="JavaScript">
function check_form ()
{
    if (check_Blank(document.frmPost.txtHostID, true, "主机名称"))
	    return false;
    if (!check_Num(document.frmPost.txtOPI_ID, true, "OPI_ID"))
	    return false;
    

	return true;
}

function ca_product_create()
{
	if(check_form()){
		document.frmPost.action = "ca_product_op.do";
		document.frmPost.submit();
	}
}

function back_submit(){
	url="<bk:backurl property='ca_productdef_query.do' />";
	if(url=="")
		url="ca_product_query.screen";
    document.location.href= url;
}
</SCRIPT>

<form name="frmPost" method="post" action="ca_product_op.do"><input
	<input type="hidden" name="txtActionType" size="20" value="CA_PRODUCTDEF_CREATE">
	 
	
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">新增CA产品定义</td>
	</tr>
</table>	
 
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<%
Map mapHost=Postern.getAllCAHost();
pageContext.setAttribute("HOSTNAME",mapHost);
%>
				<table width="100%" align="center" border="0" cellspacing="1"
					cellpadding="3">
					<tr>
						<td class="list_bg2" align="right" width="17%"></td>
						<td class="list_bg1" width="33%"></td>
						<td class="list_bg2" align="left" width="17%"></td>
						<td class="list_bg1" width="33%"></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">主机名称*</td>
						<td class="list_bg1"><tbl:select name="txtHostID" set="HOSTNAME" 
			width="23" match="txtHostID"/></td>
						<td class="list_bg2" align="right">OPI_ID</td>
						<td class="list_bg1"><input name="txtOPI_ID"
							type="text" class="text" maxlength="10" size="25"
							value="<tbl:writeparam name="txtOPI_ID" />"></font></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">CA产品ID</td>
						<td class="list_bg1" colspan="3"><font size="2"><input name="txtCAProductID"
							type="text" class="text" maxlength="20" size="25"
							value="<tbl:writeparam name="txtCAProductID" />"></font></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">CA产品名称</td>
						<td class="list_bg1" colspan="3"><input name="txtCaProductName"
							type="text" class="text" maxlength="200" size="83"
							value="<tbl:writeparam name="txtCaProductName" />"></td>
					</tr>
				</table>
	<table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr align="center">
			<td class="list_bg1">

			<table align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><img src="img/button2_r.gif" width="22" height="20"></td>
					<td><input name="Submit1" type="button" class="button"
						value="返&nbsp;回" onclick="javascript:back_submit()"></td>
					<td><img src="img/button2_l.gif" width="11" height="20"></td>

					<td width="20"></td>
						<td><img src="img/button_l.gif" width="11" height="20"></td>
						<td><input name="Submit2" type="button" class="button"
							value="保&nbsp;存" onclick="javascript:ca_product_create()"></td>
						<td><img src="img/button_r.gif" width="22" height="20"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</form>
