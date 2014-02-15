<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.AccountItemDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>

<Script language=javascript>
function frm_submit()
{
	document.frmPost.txtActionType.value = "confiscate";
	document.frmPost.action="customer_deposit_confiscate_submit.do";
	document.frmPost.submit();
}
</SCRIPT>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">确认押金没收信息</td>
	</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<form name="frmPost" method="post">
<input type="hidden" name="confirm_post" value="true">
<input type="hidden" name="txtActionType" value="">
<input type="hidden" name="func_flag" value="1106">
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
	AccountItemDTO dto = (AccountItemDTO)pageContext.getAttribute("oneline");

	pageContext.setAttribute("AccountItemDTO",dto);

	String acctName="";
	String csiidName="";
	acctName = Postern.getAcctNameById(dto.getAcctID());
	csiidName = Postern.getCSITypeByID(dto.getReferID());
	
	CustomerDTO custDto = (CustomerDTO)Postern.getCustomerByID(dto.getCustID());
	pageContext.setAttribute("CustomerDTO",custDto);
	if(acctName==null)
		acctName="";
	if(csiidName==null)
		csiidName="";
%>
<table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
	<tr>
		<td class="list_bg2"><div align="right">客户证号</div></td>
		<td class="list_bg1"><input type="text" name="txtCustomerID" size="25"  value="<tbl:write name="AccountItemDTO" property="custID" />" class="textgray" readonly ></td>
		<td class="list_bg2"><div align="right">客户姓名</div></td>
		<td class="list_bg1"><input type="text" name="txtName" size="25"  value="<tbl:write name="CustomerDTO" property="name" />" class="textgray" readonly ></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">客户类型</div></td>
		<td class="list_bg1"><input type="text" name="txtCustomerTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="CustomerDTO:customerType" />" class="textgray" readonly ></td>
		<td class="list_bg2"><div align="right">创建日期</div></td>
		<td class="list_bg1"><input type="text" name="txtCreateTime" size="25"  value="<tbl:write name="AccountItemDTO" property="createTime" />" class="textgray" readonly ></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">账户号</div></td>
		<td class="list_bg1"><input type="text" name="txtAcctID" size="25"  value="<tbl:write name="AccountItemDTO" property="acctID" />" class="textgray" readonly ></td>
		<td class="list_bg2"><div align="right">帐户名称</div></td>
		<td class="list_bg1"><input type="text" name="txtAcctName" size="25"  value="<%=acctName %>" class="textgray" readonly ></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">受理单号</div></td>
		<td class="list_bg1"><input type="text" name="txtCSIID" size="25"  value="<tbl:write name="AccountItemDTO" property="referID" />" class="textgray" readonly ></td>
		<td class="list_bg2"><div align="right">受理类型</div></td>
		<td class="list_bg1"><input type="text" name="txtCSIName" size="25"  value="<d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="<%=csiidName %>" />" class="textgray" readonly ></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">所收押金金额</div></td>
		<td class="list_bg1" colspan="3"><input type="text" name="txtSetoffAmount" size="25"  value="<tbl:write name="AccountItemDTO" property="setOffAmount" />" class="textgray" readonly ></td>
	</tr>
</table>
<input type="hidden" name="txtAiNO" value="<tbl:write name="AccountItemDTO" property="aiNO" />">
</lgc:bloop>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<bk:canback url="customer_deposit_query_result.do">
		<td width="20" ></td>  
		<td><img src="img/button2_r.gif" width="22" height="20"></td>
		<td background="img/button_bg.gif"  height="20" >
		<a href="<bk:backurl property="customer_deposit_query_result.do"/>" class="btn12">上一步</a></td>
		<td><img src="img/button2_l.gif" width="11" height="20"></td>
		</bk:canback>
		<td width="20" ></td>
		<td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
		<td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确认"></td>
		<td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table>
</form>