<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO"%>

<%
	//���ѷ�ʽ
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
			<font size="3">�ʻ�������Ϣ(�ʵ���ַ�粻��д��Ĭ��Ϊ�û���ַ)</font>
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			�ʻ�����
		</td>
		<td class="list_bg1">
			<font size="2"> <d:getcmnname typeName="SET_F_ACCOUNTTYPE" match="txtAccountType" /> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right">
			�ʻ���
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtAccountName" /> </font>
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			��������
		</td>
		<td class="list_bg1">
			<font size="2"> <%=strMopName%> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right">
			�����ʻ�
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtBankAccount" /> </font>
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			�����ʻ���
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtBankAccountName" /> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right">
			�ʵ�������
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:WriteDistrictInfo property="txtbillCounty" /> </font>
		</td>
	</tr>	
	<tr>
		<td valign="middle" class="list_bg2" align="right" width="17%">
			�ʵ����͵�ַ
		</td>
		<td class="list_bg1" width="33%">
			<font size="2"> <tbl:writeparam name="txtBillDetailAddress" /> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right" width="17%">
			�ʵ������ʱ�
		</td>
		<td class="list_bg1" width="33%">
			<font size="2"> <tbl:writeparam name="txtBillPostcode" /> </font>
		</td>
	</tr>
</table>
