<%--Copyright 2003 Digivision, Inc. All rights reserved.--%>
<%--$Id: manual_grant_campaign.jsp,v 1.1.1.1 2010/01/25 09:11:16 yangyong Exp $--%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ page
	import="com.dtv.oss.util.Postern,
				 com.dtv.oss.dto.CustomerDTO,
                 java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%
	String customerID=request.getParameter("txtCustomerID");
%>
<Script language=JavaScript>

<!--

function check_frm()
{	
	if (check_Blank(document.frmPost.txtManualActionType, true, "�������"))
		return false;
	if (check_Blank(document.frmPost.txtAccountID, true, "�ʻ�"))
		return false;
	if (check_Blank(document.frmPost.txtDateFrom, true, "������ʼ����"))
		return false;
	if (!check_TenDate(document.frmPost.txtDateFrom, true, "������ʼ����"))
     	return false;	
	if(!compareDateAndToday(getCurrentDate(),document.frmPost.txtDateFrom,"������ʼ���ڱ����ڽ����Ժ�"))
	   return false;

	var num=0;
	document.frmPost.txtCampaignID.value="";
	if (document.frames.FrameCampaign.frmPost.listID!=null){
    if (document.frames.FrameCampaign.frmPost.listID.length > 1) {
	    for (j=0;j<document.frames.FrameCampaign.frmPost.listID.length;j++){
				if (document.frames.FrameCampaign.frmPost.listID[j].checked){
					document.frmPost.txtCampaignID.value=document.frames.FrameCampaign.frmPost.listID[j].value;
					num++;
				}
	    }
	  }else{
	    if (document.frames.FrameCampaign.frmPost.listID.checked){
	      document.frmPost.txtCampaignID.value=document.frames.FrameCampaign.frmPost.listID.value;
	      num++;
	    }
	  }
	}
	if(num>1){
		alert("��ѡ����Ż���Ŀ����1");
		return false;
	}

	if(document.frmPost.txtCampaignID.value==""){
		alert("��û��ѡ���Ż�");
		return false;
	}

	
	if(document.frmPost.txtAccountID.value=="" && document.frmPost.txtServiceAccountID.value==""){
		alert("�ʻ���ҵ���ʻ�����ͬʱΪ��");
		return false;
	}
	if(document.frmPost.txtManualActionType.value=='S'&&document.frmPost.txtServiceAccountID.value==""){
		alert("ҵ���ʻ�����Ϊ��");
		return false;
	}
	return true;
}

function frm_submit(){
	if (check_frm())  {   
	   document.frmPost.submit();
	}
}

function changeManualGrant(){
	var txtObligationFlag='';
	if(document.frmPost.txtManualActionType.options[document.frmPost.txtManualActionType.selectedIndex].text=="�ʻ�"){
  	document.frmPost.txtServiceAccountID.disabled=true;
    document.frmPost.txtAccountID.disabled=false;
    txtObligationFlag='Y';
  }
  else if(document.frmPost.txtManualActionType.options[document.frmPost.txtManualActionType.selectedIndex].text=="ҵ���ʻ�"){
  	document.frmPost.txtServiceAccountID.disabled=false;	
  	document.frmPost.txtAccountID.disabled=false;
  	txtObligationFlag='N';
  }else{
  	document.frmPost.txtServiceAccountID.disabled=false;	
  	document.frmPost.txtAccountID.disabled=false;
  }
  try{
	document.FrameCampaign.refreshFrame(txtObligationFlag);
	}catch(ex){}
}
-->
</Script>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">�ֹ���������</td>
	</tr>
</table>

<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>

<form name="frmPost" method="post" action="manual_grant_campaign_fee.do">
<input type="hidden" name="txtCampaignID" value="<tbl:writeparam name="txtCampaignID" />"> <input
	type="hidden" name="txtActionType" value="manualGrantCampaign">
<input type="hidden" name="func_flag" value="668"> <input
	type="hidden" name="txtDoPost" value="false"> <tbl:hiddenparameters
	pass="txtCustomerID" /> <%
      	Map actionTypeMap=new HashMap();
      	actionTypeMap.put("A","�ʻ�");
      	actionTypeMap.put("S","ҵ���ʻ�");
      	pageContext.setAttribute("ACTIONTYPEMAP",actionTypeMap);
      	
      	pageContext.setAttribute("ACCOUNTMAP",Postern.getAccountIDMapByCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID"))));
      	pageContext.setAttribute("SAMAP",Postern.getNormalServiceAccountMapByCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID"))));
      	      	
      %>
<table align="center" width="100%" border="0" cellspacing="1"
	cellpadding="3" class="list_bg">
	<tr>
		<td class="list_bg2" width="17%" align="right">�ͻ�ID</td>
		<td class="list_bg1" width="33%" align="left"><font size="2">
		<input type="text" name="txtCustID" size="25" maxlength="10"
			value="<tbl:writeparam name="txtCustomerID" />" class="textgray"
			readonly> </font></td>
		<td class="list_bg2" width="17%" align="right">�������*</td>
		<td class="list_bg1" width="33%" align="left"><font size="2">
		<tbl:select name="txtManualActionType" set="ACTIONTYPEMAP"
			match="txtManualActionType" width="23" defaultValue="S"
			onchange="changeManualGrant();" /> </font></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">�˻�*</td>
		<td class="list_bg1" align="left"><font size="2"> <d:selAccByCustId
			name="txtAccountID" mapName="self" match="txtAccountID" width="23"
			disabled="true" /> </font></td>
		<td class="list_bg2" align="right">ҵ���˻�ID</td>
		<td class="list_bg1" align="left"><font size="2"> <tbl:select
			name="txtServiceAccountID" set="SAMAP" match="txtServiceAccountID"
			width="23" /> </font></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">������ʼ����*</td>
		<td class="list_bg1" align="left" colspan="3"><input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text"			name="txtDateFrom" size="25"			value="<tbl:writeparam name="txtDateFrom" />">&nbsp;<IMG			onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')"			src="img/calendar.gif" style="cursor: hand" border="0"></td>
	</tr>
	<tr>
		<td class="list_bg2">
		<div align="right">��ע��Ϣ</div>
		</td>
		<td class="list_bg1" colspan="3"><input type="text"
			name="txtComments" size="80"
			value="<tbl:writeparam name="txtComments" />"></td>
	</tr>
	<tr>
		<td colspan="4" class="list_bg2" align="center"><font size="3">�Żݻ��Ϣ</font></td>
	</tr>
	<tr>
		<td class="list_bg1" colspan="4" align="center"><font size="2">
		<%String obligetionFlag="N";
          if("A".equals(request.getParameter("txtManualActionType"))){
        	  obligetionFlag="Y";
          }
          %> <iframe
			SRC="list_campaign.do?txtCampaignType=A&txtObligationFlag=<%=obligetionFlag %>&match=<tbl:writeparam name="txtCampaignID" />"
			name="FrameCampaign" width="480" height="260"> </iframe> </font></td>
	</tr>
</table>
<BR>

<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="11" height="20"><img src="img/button_l.gif" width="11"
			height="20"></td>
		<td><input name="Submit" type="button" class="button" value="��һ��"
			onclick="javascript:frm_submit()"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22"
			height="20"></td>
	</tr>
</table>
</form>
<Script language=JavaScript>
    changeManualGrant();
</Script>