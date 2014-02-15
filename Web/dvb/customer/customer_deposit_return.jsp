<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.AccountItemDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<SCRIPT language="JavaScript">
function check_form()
{
    if(document.frmPost.MOP.value == "")
	{	
		alert("��ѡ���˻���ʽ��");
		return false;
	}
	var mop = document.frmPost.MOP.value;
	document.frmPost.mopid.value = mop.substring(0,mop.indexOf("-"));
	document.frmPost.tickettype.value = mop.substring(mop.indexOf("-")+1,mop.indexOf("_"));
	if (document.frmPost.tickettype.value == document.frmPost.mopPayType.value)
	{
	   if (check_Blank(document.frmPost.billNo, true, "Ʊ�ݺ�"))
	       return false;
	}
	if(document.frmPost.totalFee.value < document.frmPost.txtPay.value*1.0
		|| document.frmPost.txtPay.value*1.0 == 0)
 	{
 		alert("�˻�����ȷ��");
 		return false;
	}
	if(document.frmPost.txtPay.value*1.0 < 0)
 	{
 		alert("�˻�����С�� 0 !");
 		return false;
	}
 	return true;
}

function frm_submit()
{
    if(check_form())
    {
    	document.frmPost.txtActionType.value = "return";
    	document.frmPost.action = "customer_deposit_return_confirm.do";
    	document.frmPost.submit();
    }
}
</SCRIPT>

<form name="frmPost" method="post" action="customer_deposit_return_confirm.do">
<input type="hidden" name="mopPayType" value="<%=CommonKeys.MOPPAYTYPE_DK%>">
<input type="hidden" name="mopid" value="">
<input type="hidden" name="tickettype" value="">
<input type="hidden" name="txtActionType" value="">
<input type="hidden" name="confirm_post" value="false">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">�ͻ�Ѻ���˻�</td>
	</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
	<tr> 
		<td class="import_tit" align="center" colspan="5"><font size="3">����Ѻ��</font></td>
	</tr>
</table>
<%
double totalFee = 0;
%>
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
	totalFee += dto.getValue();
%>
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >	
	<tr class='list_head'> 
		<td class='list_head' align="center" >��ˮ��</td>
		<td class='list_head' align="center" >�˻���</td>
		<td class='list_head' align="center" >�˻�����</td>
		<td class='list_head' align="center" >������</td>
		<td class='list_head' align="center" >��������</td>
		<td class='list_head' align="center" >��������</td>
		<td class='list_head' align="center" >Ѻ����</td>
		<td class='list_head' align="center" >���ʽ��</td>
		<td class='list_head' align="center" >״̬</td>
	</tr>
	<tr>
		<td class='list_bg1' align="center" ><tbl:write name="AccountItemDTO" property="aiNO" /></td>
		<td class='list_bg1' align="center" ><tbl:write name="AccountItemDTO" property="acctID" /></td>
		<td class='list_bg1' align="center" ><%=acctName %></td>
		<td class='list_bg1' align="center" ><tbl:write name="AccountItemDTO" property="referID" /></td>
		<td class='list_bg1' align="center" ><d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="<%=csiidName %>" /></td>
		<td class='list_bg1' align="center" ><tbl:write name="AccountItemDTO" property="createTime" /></td>
		<td class='list_bg1' align="center" ><tbl:write name="AccountItemDTO" property="value" /></td>
		<td class='list_bg1' align="center" ><tbl:write name="AccountItemDTO" property="setOffAmount" /></td>
		<td class='list_bg1' align="center" ><d:getcmnname typeName="SET_F_FTSTATUS" match="AccountItemDTO:status" /></td>
	</tr>
</table>
<input type="hidden" name="txtAiNO" value="<tbl:write name="AccountItemDTO" property="aiNO" />">
<input type="hidden" name="txtAcctID" value="<tbl:write name="AccountItemDTO" property="acctID" />">
<input type="hidden" name="txtAcctName" value="<%=acctName %>">
<input type="hidden" name="txtCSIID" value="<tbl:write name="AccountItemDTO" property="referID" />">
<input type="hidden" name="txtCSIName" value="<d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="<%=csiidName %>" />">
<input type="hidden" name="txtCreateTime" value="<tbl:write name="AccountItemDTO" property="createTime" />">
<input type="hidden" name="txtSetoffAmount" value="<tbl:write name="AccountItemDTO" property="setOffAmount" />">
<input type="hidden" name="txtCustomerID" size="20" value="<tbl:write name="AccountItemDTO" property="custID" />">
<input type="hidden" name="txtName" size="20" value="<tbl:write name="CustomerDTO" property="name" />">
<input type="hidden" name="txtCustomerTypeShow" size="20" value="<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="CustomerDTO:customerType" />">
</lgc:bloop> 
<%
pageContext.setAttribute("AllMOPList" , Postern.getOpeningPaymentMop(CommonKeys.CUSTSERVICEINTERACTIONTYPE_FR));
%>
<table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg">
	<tr> 
		<td class="import_tit" align="center" colspan="3"><font size="3">�˻�Ѻ��</font></td>
	</tr>
	<tr>
		<td class="list_bg2" align="center" width="35%">*�˻���ʽ��<tbl:select name="MOP" set="AllMOPList" match="MOP" width="18" /></td>
		<td class="list_bg2" align="center" width="30%">*�˻���<input name="txtPay" type="text" size="12" class="text" value=""></td>
		<td class="list_bg2" align="center" width="35%">Ʊ�ݱ�ţ�<input name="billNo" type="text" size="18" class="text" value=""></td>
	</tr>
</table>
<input type="hidden" name="totalFee" value="<%=WebUtil.bigDecimalFormat(totalFee)%>">
<BR>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<bk:canback url="customer_deposit_query_result.do">
		<td width="20" ></td>  
		<td><img src="img/button2_r.gif" width="22" height="20"></td>
		<td background="img/button_bg.gif"  height="20" >
		<a href="<bk:backurl property="customer_deposit_query_result.do"/>" class="btn12">��һ��</a></td>
		<td><img src="img/button2_l.gif" width="11" height="20"></td>
		</bk:canback>
		<td width="20" ></td>
		<td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
		<td><input name="Submit" type="button" class="button" onclick="javascript:frm_submit()" value="�˻�Ѻ��"></td>
		<td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table>
</form>