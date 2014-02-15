<%--Copyright 2003 Digivision, Inc. All rights reserved.--%>
<%--$Id: manual_grant_campaign_fee.jsp,v 1.1.1.1 2010/01/25 09:11:16 yangyong Exp $--%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>

<Script language=JavaScript>

<!--
	function back_submit(){
		document.frmPost.action="manual_grant_campaign.screen";
		document.frmPost.submit();
	}
	
	function frm_next(csiPayOption){
	   document.frmPost.txtcsiPayOption.value =csiPayOption;
	   if (check_fee()){
	      document.frmPost.action="manual_grant_campaign_pay.screen";
	   }else{
	   		document.frmPost.txtConfirmBackFlag.value="caculatefee";
				document.frmPost.action="manual_grant_campaign_confirm.screen";
	   }
	   document.frmPost.submit();
  }
  
  function frm_finish(csiPayOption){
	   document.frmPost.txtcsiPayOption.value =csiPayOption;
	   document.frmPost.txtConfirmBackFlag.value="caculatefee";
	   document.frmPost.action="manual_grant_campaign_confirm.screen";
	   document.frmPost.submit();
  }	
-->
</Script>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">手工授予促销活动-费用信息</td>
	</tr>
</table>

<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>

<form name="frmPost" method="post"
	action="manual_grant_campaign_pay.screen">
	<input type="hidden" name="txtCampaignID" value="<tbl:writeparam name="txtCampaignID" />"> 
	<input type="hidden" name="txtConfirmBackFlag" value="<tbl:writeparam name="txtConfirmBackFlag" />"> 
		
	<input type="hidden" name="txtActionType" value="manualGrantCampaign"> 
	<input type="hidden" name="func_flag" value="668"> 
	<input type="hidden" name="txtDoPost" value="false"> 
	<input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >
	<tbl:hiddenparameters pass="txtCustomerID" /> 
	<%
 	String manualAction = request.getParameter("txtManualActionType");
 	if ("A".equals(manualAction)) {
 		manualAction = "帐户";
 	} else {
 		manualAction = "业务帐户";
 	}
 	String acctName = Postern.getAccountNameByID(request
 			.getParameter("txtAccountID"));
 	int campaignid = Integer.parseInt(request
 			.getParameter("txtCampaignID"));
 	String campaignName = Postern.getCampaignNameByID(campaignid);
 	pageContext.setAttribute("ACCOUNTMAP", Postern
 			.getAccountIDMapByCustID(WebUtil.StringToInt(request
 			.getParameter("txtCustomerID"))));
 	pageContext.setAttribute("SAMAP",
 			Postern
 			.getNormalServiceAccountMapByCustID(WebUtil
 			.StringToInt(request
 					.getParameter("txtCustomerID"))));
 %>
<table align="center" width="100%" border="0" cellspacing="1"
	cellpadding="3" class="list_bg">
	<tr>
		<td class="list_bg2" width="17%" align="right">客户ID</td>
		<td class="list_bg1" width="33%" align="left"><font size="2">
		<input type="text" name="txtCustID" size="25"
			value="<tbl:writeparam name="txtCustomerID" />" class="textgray"
			readonly> </font></td>
		<td class="list_bg2" width="17%" align="right">授予对象</td>
		<td class="list_bg1" width="33%" align="left"><font size="2">
		<input type="text" name="txtManualAction" size="25"
			value="<%=manualAction %>" class="textgray" readonly> <input
			type="hidden" name="txtManualActionType" size="25"
			value="<tbl:writeparam name="txtManualActionType" />"> </font></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">账户</td>
		<td class="list_bg1" align="left"><font size="2"> <input
			type="text" name="txtAccountName" size="25" value="<%=acctName %>"
			class="textgray" readonly> <input type="hidden"
			name="txtAccountID" size="25"
			value="<tbl:writeparam name="txtAccountID" />"> </font></td>
		<td class="list_bg2" align="right">业务账户ID</td>
		<td class="list_bg1" align="left"><font size="2"> <input
			type="text" name="txtServiceAccountID" size="25"
			value="<tbl:writeparam name="txtServiceAccountID" />"
			class="textgray" readonly> </font></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">促销开始日期</td>
		<td class="list_bg1" align="left" colspan="3"><input type="text"
			name="txtDateFrom" size="25"
			value="<tbl:writeparam name="txtDateFrom" />" class="textgray"
			readonly></td>
	</tr>
	<tr>
		<td class="list_bg2">
		<div align="right">备注信息</div>
		</td>
		<td class="list_bg1" colspan="3"><input type="text"
			name="txtComments" size="83"
			value="<tbl:writeparam name="txtComments" />" class="textgray"
			readonly></td>
	</tr>
	<tr>
		<td class="list_bg2" height=="20px">
		<div align="right">优惠活动信息</div>
		</td>
		<td class="list_bg1" colspan="3">
			<input type="text" name="txtCampaignName" size="83" value="<%=campaignName %>"
			class="textgray" readonly>
			</td>
	</tr>
</table>
<BR>
<lgc:hasnoterror>
	<tbl:CommonFeeAndPaymentInfo includeParameter="Fee"
		payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_MAC%>"
		acctshowFlag="true" />
</lgc:hasnoterror>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="22" height="20"><img src="img/button2_r.gif" width="22"
			height="20"></td>
		<td><input name="Submit" type="button" class="button" value="上一步"
			onclick="javascript:back_submit()"></td>
		<td width="11" height="20"><img src="img/button2_l.gif" width="11"
			height="20"></td>
		<jsp:include page="/fee/common_control.jsp" />
	</tr>
</table>
</form>
