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
			<font size="3">客户信息</font>
		</td>
	</tr>
	<%
		if (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)
			|| open_flag.equals(CommonKeys.ACTION_OF_SHOPACCOUNT)) {
	%>
	<lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTSERIALNO" >
    <tr>
		<td valign="middle" class="list_bg2" align="right" width="17%">
			客户条形码
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
			客户类型*
		</td>
		<td class="list_bg1">
			<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="txtCustType" />
		</td>
		<td valign="middle" class="list_bg2" align="right" width="17%">
			姓名*
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
			证件类型
		</td>
		<td class="list_bg1">
			<d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="txtCardType" />
		</td>
		<td valign="middle" class="list_bg2" align="right">
			证件号
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
			固定电话
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtTelephone" /> 
		</td>
		<td valign="middle" class="list_bg2" align="right">
			移动电话
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtMobileNumber" />
		</td>
	</tr>
	<%if(request.getParameter("txtAgentName")!=null&&!"".equals(request.getParameter("txtAgentName"))){%>
	<tr>
		<td valign="middle" class="list_bg2" width="17%" align="right">
			代理人姓名
		</td>
		<td width="33%" class="list_bg1">
			<tbl:writeparam name="txtAgentName" />
		</td>
		<td valign="middle" class="list_bg2" align="right">
			代理人联系电话
		</td>
		<td width="33%" class="list_bg1">
			<tbl:writeparam name="txtAgentTelephone" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" width="17%" align="right">
			代理人证件类型
		</td>
		<td width="33%" class="list_bg1">
			<d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="txtAgentCardType" />
		</td>
		<td valign="middle" class="list_bg2" align="right">
			代理人证件号
		</td>
		<td width="33%" class="list_bg1">
			<tbl:writeparam name="txtAgentCardId" />
		</td>
	</tr>
	<%}%>
	<tr>
		<td valign="middle" class="list_bg2" width="17%" align="right">
			所在区
		</td>
		<td width="33%" class="list_bg1">
			<tbl:WriteDistrictInfo property="txtCounty" />
		</td>
		<td valign="middle" class="list_bg2" align="right">
			详细地址*
		</td>
		<td width="33%" class="list_bg1">
			<tbl:writeparam name="txtDetailAddress" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2">
			<div align="right">
				邮编
			</div>
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtPostcode" />
		</td>
		<td valign="middle" class="list_bg2" align="right">
				是否上门安装
		</td>
		<td class="list_bg1">
			<d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONINSTYPE" match="txtIsInstall" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			预约上门日期
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtPreferedDate" /> 
		</td>
		<td class="list_bg2" align="right">
			预约上门时间段
		</td>
		<td class="list_bg1" cospan="3">
			<d:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="txtPreferedTime" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			来源渠道
		</td>
		<td class="list_bg1">
			<d:getcmnname typeName="SET_C_CUSTOPENSOURCETYPE" match="txtOpenSourceType" /> 
		</td>
		<td valign="middle" class="list_bg2" align="right">
			来源渠道ID
		</td>
		<td class="list_bg1">
			<d:getorgname match="txtOpenSourceID" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
				协议编号
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
			团购券号
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtGroupBargainDetailNo" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			国籍
		</td>
		<td class="list_bg1">
			<font size="2"> <d:getcmnname typeName="SET_C_NATIONALITY" match="txtNationality" /> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right" width="17%">
			性别
		</td>
		<td width="33%" class="list_bg1">
			<d:getcmnname typeName="SET_C_CUSTOMERGENDER" match="txtGender" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			社保卡编号
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtSocialSecCardID" />
		</td>
		<td valign="middle" class="list_bg2" align="right">
			出生年月
		</td>
		<td class="list_bg1">
			<tbl:writeparam name="txtBirthday" />
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			EMAIL地址
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtEmail" /> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right">
			传真
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtFaxNumber" /> </font>
		</td>
	</tr>
		<tr>
		<td valign="middle" class="list_bg2" align="right">
			登陆ID
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtLoginID" /> </font>
		</td>
		<td valign="middle" class="list_bg2" align="right">
			登陆密码
		</td>
		<td class="list_bg1">
			<font size="2"> <tbl:writeparam name="txtLoginPwd" /> </font>
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2" align="right">
			备注
		</td>
		<td class="list_bg1" colspan="3">
			<tbl:writeparam name="txtComments" />
		</td>
	</tr>
	<tbl:dynamicservey serveyType="M" showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="23" />
</table>
