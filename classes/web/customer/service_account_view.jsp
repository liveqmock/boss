<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>


<%
String strAlert = "";
String txtActionType = request.getParameter("txtActionType");
String strActionType = "";
String strActionDo="";
String csiType="";
int iActionType=0;

    //业务账户过户类型,默认为系统内客户
    Map transferTypeMap=new HashMap();
    transferTypeMap.put("I","系统内客户");
    transferTypeMap.put("O","系统外客户");
    pageContext.setAttribute("TRANSFER_TYPE" ,transferTypeMap);
    
if ((txtActionType!=null)&&(txtActionType.compareTo("")!=0))
{
   try
   {
	   iActionType=Integer.valueOf(txtActionType).intValue();
   }
   catch (Exception ex)
   {
   }
}

switch(iActionType)
{
	case CommonKeys.SERVICE_ACCOUNT_PAUSE:
		strActionType = "停 机";
		strAlert = "您确认要暂停该业务帐户吗?";
		strActionDo="service_account_fee_view.do";
		csiType="UP";
		break;
	case CommonKeys.SERVICE_ACCOUNT_CLOSE:
		strActionType = "销 户";
		strAlert = "您确认要关闭该业务帐户吗?";
		strActionDo="service_account_fee_view.do";
		csiType="UC";
		break;
	case CommonKeys.SERVICE_ACCOUNT_BEFOREHAND_CLOSE:
		strActionType = "预退户";
		strAlert = "您确认要预退户吗?";
		strActionDo="service_account_fee_view.do";
		csiType="SP";
		break;
	case CommonKeys.SERVICE_ACCOUNT_REAL_CLOSE:
		strActionType = "实退户";
		strAlert = "您确认要实退户吗?";
		strActionDo="service_account_real_close_op.do";
		csiType="RC";
		break;
	case CommonKeys.SERVICE_ACCOUNT_TRANSFER:
		strActionType = "过&nbsp;户";
		strAlert = "您确认要过户吗?";
		strActionDo="service_account_transfer_input.do";
		csiType="UT";
		break;
	case CommonKeys.SERVICE_ACCOUNT_RENT:
		strActionType = "设备转租";
		strAlert = "您确认设备要转租吗?";
		strActionDo="service_account_fee_view.do";
		csiType="RT";
		break;
}
String txtDeviceFee = request.getParameter("txtDeviceFee");
	if(txtDeviceFee==null ||"".equals(txtDeviceFee))
		txtDeviceFee="0";
%>
<Script language=JavaScript>
<!--
function frm_check(){
	if(document.frmPost.txtCsiCreateReason!=null&&!check_csiReason()){
		return false;
	}
	if("UC"=="<%=csiType%>"||"SP"=="<%=csiType%>")
	{
		var isSendback=document.frmPost.txtIsSendBack;
		if(check_Blank(isSendback, true, "是否退还设备"))
		{
				return false;
		}
	 if (isSendback!=null&&isSendback.value=='<%=com.dtv.oss.web.util.CommonKeys.FORCEDDEPOSITFLAG_Y%>')
   {
		if(check_Blank(document.frmPost.txtDeviceFee, true, "设备费")||!check_Float(document.frmPost.txtDeviceFee,true,"设备费")){
			return false;
		}	
   }else{
   	document.frmPost.txtDeviceFee.value="";
   }
	}
	else if("RC"=="<%=csiType%>"){
		document.frmPost.txtDoPost.value="TRUE";
	}
	return true;
}

function frm_submit(strAlert){
  if (frm_check()&&window.confirm(strAlert)){
	    document.frmPost.action="<%=strActionDo %>";
		  document.frmPost.submit();
	}
}
-->
</Script>
<form name="frmPost" method="post">
	
	
<jsp:include page="sa_view.jsp"/>	


<TABLE width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" >
 <%
 		//业务帐户销户/业务账户预退户
   if(iActionType==CommonKeys.SERVICE_ACCOUNT_CLOSE||iActionType==CommonKeys.SERVICE_ACCOUNT_BEFOREHAND_CLOSE) {
 %>
  <TR>
	   <td  width="17%" class="list_bg2" align="right" >请选择有效付费帐户*  </td>
	   <td  width="33%" class="list_bg1" align="left" ><d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" style="width:expression((this.offsetWidth>187)?'auto':187)" /></td>
 		<tbl:csiReason csiType="<%=csiType%>" tdWordStyle="list_bg2" tdControlStyle="list_bg1" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" showType="text" checkScricptName="check_csiReason()" controlSize="25" forceDisplayTD="true"/>
  </TR>
  <tr>
    <td class="list_bg2" width="17%" align="right">退还设备*</td>
    <td class="list_bg1" width="33%">
    	<d:selcmn name="txtIsSendBack" mapName="SET_G_YESNOFLAG"  match="txtIsSendBack"  width="23" />
    </td>
    <td class="list_bg2" width="17%" align="right">设备费*</td>
    <td class="list_bg1" width="33%">
    	<input name="txtDeviceFee" type="text" maxlength="8" size="25" value="<%=txtDeviceFee%>">
    </td>
  </tr> 
  <tr>
  	<td class="list_bg2" ><div align="right">备注</div></td>
  	<td class="list_bg1" colspan="3" >
     <input type="text" name="txtComments"  size="83" maxlength="250" value="<tbl:writeparam name="txtComments" />" >
    </td>
  </tr>
  
  
	<%} else if(iActionType==CommonKeys.SERVICE_ACCOUNT_PAUSE){%>
	<TR>
	   <td  width="17%" align="right" >请选择有效付费帐户*  </td>
	   <td  width="33%" align="left" ><d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" style="width:expression((this.offsetWidth>187)?'auto':187)" /></td>
 		<tbl:csiReason csiType="<%=csiType%>" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" showType="text" checkScricptName="check_csiReason()" controlSize="25" tdControlColspan="3" forceDisplayTD="true"/>
  </TR>
	<%}else if(iActionType==CommonKeys.SERVICE_ACCOUNT_TRANSFER){%>
	 <TR>
	 	  <td  width="25%" align="right" >请选择有效付费帐户*  </td>
	    <td  width="25%" align="left"  ><d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" style="width:expression((this.offsetWidth>187)?'auto':187)" /></td>
	    <td  width="25%" align="right" >过户类型*  </td>
	    <td  width="25%" align="left"  ><tbl:select name="txtTransferType" set="TRANSFER_TYPE" match="txtTransferType" width="23" empty="false" /></TD>
   </TR>
	<%}else if(iActionType==CommonKeys.SERVICE_ACCOUNT_RENT){%>
	<TR>
	   <td  width="17%" align="right" >请选择有效付费帐户*  </td>
	   <td  width="33%" align="left" ><d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" style="width:expression((this.offsetWidth>187)?'auto':187)" /></td>
 		<tbl:csiReason csiType="<%=csiType%>" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" showType="text" checkScricptName="check_csiReason()" controlSize="25" tdControlColspan="3" forceDisplayTD="true"/>
  </TR>
	<%}%>
</TABLE> 
<br>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table border="0" cellspacing="0" cellpadding="0" align="center">
    <tr align="center">
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit" type="button" class="button" value="<%=strActionType%>" onclick="javascript:frm_submit('<%=strAlert%>')"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
   </tr>
</table>
     
<input type="hidden" name="txtActionType" value="<%=txtActionType%>" />
<input type="hidden" name="txtDoPost" value="FALSE" >
<input type="hidden" name="func_flag" value="2" >
<tbl:generatetoken />
</form>