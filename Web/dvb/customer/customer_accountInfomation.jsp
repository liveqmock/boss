<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO"%>

<%
	//付费方式
	Map mapBankMop = Postern.getAllMethodOfPayments();
	MethodOfPaymentDTO dtoMOP = (MethodOfPaymentDTO) mapBankMop
			.get(request.getParameter("txtMopID"));
	String strMopName = null;
	if (dtoMOP != null)
		strMopName = dtoMOP.getName();
	if (strMopName == null)
		strMopName = "";
%>

<table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
		<td colspan="4" class="import_tit" align="center">
			<font size="3">帐户基本信息(帐单地址如不填写则默认为用户地址)</font>
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			帐户类型
		</td>
		<td class="list_bg1">
			<font size="2"> <d:getcmnname typeName="SET_F_ACCOUNTTYPE" match="txtAccountType" /> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right">
			帐户名
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtAccountName" /> </font>
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			付费类型
		</td>
		<td class="list_bg1">
			<font size="2"> <%=strMopName%> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right">
			银行帐户
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtBankAccount" /> </font>
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			银行帐户名
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtBankAccountName" /> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right">
			帐单所在区
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:WriteDistrictInfo property="txtbillCounty" /> </font>
		</td>
	</tr>	
	<tr>
		<td valign="middle" class="list_bg2" align="right" width="17%">
			帐单寄送地址
		</td>
		<td class="list_bg1" width="33%">
			<font size="2"> <tbl:writeparam name="txtBillDetailAddress" /> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right" width="17%">
			帐单寄送邮编
		</td>
		<td class="list_bg1" width="33%">
			<font size="2"> <tbl:writeparam name="txtBillPostcode" /> </font>
		</td>
	</tr>
</table>
