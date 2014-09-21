<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.*" %>
<%
		String open_flag = (request.getParameter("OpenFlag") == null) ? "" : request.getParameter("OpenFlag");
		String csiType =(request.getParameter("txtCsiType") == null) ? "" : request.getParameter("txtCsiType");
%>

<table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
		<td colspan="4" class="import_tit" align="center">
			<font size="3">�ͻ���Ϣ</font>
		</td>
	</tr>
	<%
		if (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)
			|| open_flag.equals(CommonKeys.ACTION_OF_SHOPACCOUNT)) {
	%>
	<lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTSERIALNO" >
    <tr>
		<td valign="middle" class="list_bg2" align="right" width="17%">
			�ͻ�������
		</td>
		<td colspan="3" class="list_bg1">
			<tbl:writeparam name="txtCustomerSerialNo" />
		</td>
	</tr>
	</lgc:showcontentbysetting>
	<%
	}
	%>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			�ͻ�����*
		</td>
		<td class="list_bg1">
			<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="txtCustType" />
		</td>
		<td valign="middle" class="list_bg2" align="right" width="17%">
			����*
		</td>
		<td width="33%" class="list_bg1">
			<tbl:writeparam name="txtName" />
		</td>
	</tr>
	<%
				if (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)
				|| open_flag.equals(CommonKeys.ACTION_OF_SHOPACCOUNT)||open_flag.equals(CommonKeys.ACTION_OF_BOOK_DIRECTLY)) {
	%>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			֤������
		</td>
		<td class="list_bg1">
			<d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="txtCardType" />
		</td>
		<td valign="middle" class="list_bg2" align="right">
			֤����
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtCardID" />
		</td>
	</tr>
	<%
	}
	%>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			�̶��绰
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtTelephone" /> 
		</td>
		<td valign="middle" class="list_bg2" align="right">
			�ƶ��绰
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtMobileNumber" />
		</td>
	</tr>
	<%if(request.getParameter("txtAgentName")!=null&&!"".equals(request.getParameter("txtAgentName"))){%>
	<tr>
		<td valign="middle" class="list_bg2" width="17%" align="right">
			����������
		</td>
		<td width="33%" class="list_bg1">
			<tbl:writeparam name="txtAgentName" />
		</td>
		<td valign="middle" class="list_bg2" align="right">
			��������ϵ�绰
		</td>
		<td width="33%" class="list_bg1">
			<tbl:writeparam name="txtAgentTelephone" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" width="17%" align="right">
			������֤������
		</td>
		<td width="33%" class="list_bg1">
			<d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="txtAgentCardType" />
		</td>
		<td valign="middle" class="list_bg2" align="right">
			������֤����
		</td>
		<td width="33%" class="list_bg1">
			<tbl:writeparam name="txtAgentCardId" />
		</td>
	</tr>
	<%}%>
	<tr>
		<td valign="middle" class="list_bg2" width="17%" align="right">
			������
		</td>
		<td width="33%" class="list_bg1">
			<tbl:WriteDistrictInfo property="txtCounty" />
		</td>
		<td valign="middle" class="list_bg2" align="right">
			��ϸ��ַ*
		</td>
		<td width="33%" class="list_bg1">
			<tbl:writeparam name="txtDetailAddress" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2">
			<div align="right">
				�ʱ�
			</div>
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtPostcode" />
		</td>
		<td valign="middle" class="list_bg2" align="right">
				�Ƿ����Ű�װ
		</td>
		<td class="list_bg1">
			<d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONINSTYPE" match="txtIsInstall" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			ԤԼ��������
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtPreferedDate" /> 
		</td>
		<td class="list_bg2" align="right">
			ԤԼ����ʱ���
		</td>
		<td class="list_bg1" cospan="3">
			<d:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="txtPreferedTime" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			��Դ����
		</td>
		<td class="list_bg1">
			<d:getcmnname typeName="SET_C_CUSTOPENSOURCETYPE" match="txtOpenSourceType" /> 
		</td>
		<td valign="middle" class="list_bg2" align="right">
			��Դ����ID
		</td>
		<td class="list_bg1">
			<d:getorgname match="txtOpenSourceID" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
				Э����
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtContractNo" />
		</td>
		
		<tbl:csiReason csiType="<%=csiType%>" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" forceDisplayTD="true" showType="label"  tdWordStyle="list_bg2" tdControlStyle="list_bg1" />
		
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			&nbsp;<%=Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID")%>
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtCatvID" />
		</td>
		<td valign="middle" class="list_bg2" align="right">
			�Ź�ȯ��
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtGroupBargainDetailNo" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			����
		</td>
		<td class="list_bg1">
			<font size="2"> <d:getcmnname typeName="SET_C_NATIONALITY" match="txtNationality" /> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right" width="17%">
			�Ա�
		</td>
		<td width="33%" class="list_bg1">
			<d:getcmnname typeName="SET_C_CUSTOMERGENDER" match="txtGender" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			�籣�����
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtSocialSecCardID" />
		</td>
		<td valign="middle" class="list_bg2" align="right">
			��������
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtBirthday" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			EMAIL��ַ
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtEmail" /> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right">
			����
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtFaxNumber" /> </font>
		</td>
	</tr>
		<tr>
		<td valign="middle" class="list_bg2" align="right">
			��½ID
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtLoginID" /> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right">
			��½����
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtLoginPwd" /> </font>
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			��ע
		</td>
		<td class="list_bg1" colspan="3">
			<tbl:writeparam name="txtComments" />
		</td>
	</tr>
	<tbl:dynamicservey serveyType="M" showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="23" />
</table>
