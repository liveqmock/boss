<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<Script language=javascript>
function frm_submit()
{
	document.frmPost.txtActionType.value = "return";
	document.frmPost.action="customer_deposit_return_submit.do";
	document.frmPost.submit();
}
</SCRIPT>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">ȷ��Ѻ���˻���Ϣ</td>
	</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<form name="frmPost" method="post">
<input type="hidden" name="txtAiNO" value="<tbl:writeparam name="txtAiNO" />">
<input type="hidden" name="confirm_post" value="true">
<input type="hidden" name="txtActionType" value="">
<input type="hidden" name="func_flag" value="1106">
<table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
	<tr>
		<td class="list_bg2"><div align="right">�ͻ�֤��</div></td>
		<td class="list_bg1"><input type="text" name="txtCustomerID" size="25"  value="<tbl:writeparam name="txtCustomerID"/>" class="textgray" readonly ></td>
		<td class="list_bg2"><div align="right">�ͻ�����</div></td>
		<td class="list_bg1"><input type="text" name="txtName" size="25"  value="<tbl:writeparam name="txtName"/>" class="textgray" readonly ></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">�ͻ�����</div></td>
		<td class="list_bg1"><input type="text" name="txtCustomerTypeShow" size="25"  value="<tbl:writeparam name="txtCustomerTypeShow"/>" class="textgray" readonly ></td>
		<td class="list_bg2"><div align="right">��������</div></td>
		<td class="list_bg1"><input type="text" name="txtCreateTime" size="25"  value="<tbl:writeparam name="txtCreateTime"/>" class="textgray" readonly ></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">�˻���</div></td>
		<td class="list_bg1"><input type="text" name="txtAcctID" size="25"  value="<tbl:writeparam name="txtAcctID"/>" class="textgray" readonly ></td>
		<td class="list_bg2"><div align="right">�ʻ�����</div></td>
		<td class="list_bg1"><input type="text" name="txtAcctName" size="25"  value="<tbl:writeparam name="txtAcctName"/>" class="textgray" readonly ></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">������</div></td>
		<td class="list_bg1"><input type="text" name="txtCSIID" size="25"  value="<tbl:writeparam name="txtCSIID"/>" class="textgray" readonly ></td>
		<td class="list_bg2"><div align="right">��������</div></td>
		<td class="list_bg1"><input type="text" name="txtCSIName" size="25"  value="<tbl:writeparam name="txtCSIName"/>" class="textgray" readonly ></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">����Ѻ����</div></td>
		<td class="list_bg1" colspan="3"><input type="text" name="txtSetoffAmount" size="25"  value="<tbl:writeparam name="txtSetoffAmount"/>" class="textgray" readonly ></td>
	</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<%
String payType = (String)request.getAttribute("tickettype");
%>
<table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg">
	<tr> 
		<td class="import_tit" align="center" colspan="3"><font size="3">�˻�Ѻ��</font></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="35%">�˻���ʽ��<input type="text" name="MOP" size="18"  value="<d:getcmnname typeName="SET_F_MOPPAYTYPE" match="<%=payType%>"/>" class="textgray" readonly ></td>
		<td class="list_bg2" align="left" width="30%">�˻���<input name="txtPay" type="text" size="12" value="<tbl:writeparam name="txtPay"/>" class="textgray" readonly></td>
		<td class="list_bg2" align="left" width="35%">Ʊ�ݱ�ţ�<input name="billNo" type="text" size="18" value="<tbl:writeparam name="billNo"/>" class="textgray" readonly></td>
	</tr>
</table>
<tbl:hiddenparameters pass="mopid/tickettype" />
<BR>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<bk:canback url="customer_deposit_return.do">
		<td width="20" ></td>  
		<td><img src="img/button2_r.gif" width="22" height="20"></td>
		<td background="img/button_bg.gif"  height="20" >
		<a href="<bk:backurl property="customer_deposit_return.do"/>" class="btn12">��һ��</a></td>
		<td><img src="img/button2_l.gif" width="11" height="20"></td>
		</bk:canback>
		<td width="20" ></td>
		<td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
		<td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="ȷ��"></td>
		<td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table>
</form>