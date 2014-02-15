<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
                 
<%@ page import="com.dtv.oss.util.Postern,java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.stat.DeviceSwapStatDTO"%>
<%@ page import="com.dtv.oss.web.taglib.util.QueryPageProp"%>

<script language=javascript>

function query_submit(){
	document.frmPost.action="device_sales_stat.do";
	if(checkDate()){  
		document.frmPost.submit();
	}
}
function checkDate(){
	if (document.frmPost.txtCreateTime1.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime1, true, "��ʼʱ��")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTime2.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime2, true, "����ʱ��")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"����ʱ�������ڵ�����ʼʱ��")){
		
		return false;
	}
	
	if (check_Blank(document.frmPost.txtOrgID, true, "��/վ"))
		return false;
	
	return true;
}




</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function save_query_result(fname,isOpen){
	var intable=document.getElementById("inTable");
	if(intable!=null){
	intable.border=1;
	var fc=document.getElementById("fileContent");
	
	var dc=document.getElementById("txtCustType");
	//alert(dc.options[dc.selectedIndex].innerText)
	var htmlhead =  "<table border=1 align=center cellpadding=5 cellspacing=1>";
		htmlhead = htmlhead +"<tr><td align=center colspan=4 >���ֵ����豸����ͳ�Ʊ�</td></tr>";
		if(dc.options[dc.selectedIndex].innerText == "-----------------------")
		{
			htmlhead = htmlhead +"<tr><td>�û�����:</td>";
		}
		else
		{
			htmlhead = htmlhead +"<tr><td>�û�����:"+dc.options[dc.selectedIndex].innerText+"</td>";
		}
		
		htmlhead = htmlhead +"<td>�豸����:"+document.frmPost.txtDeviceModel.value+"</td>";
		htmlhead = htmlhead +"<td colspan=2>����:"+document.frmPost.txtOrgDesc.value+"</td></tr>";
		htmlhead = htmlhead +"<tr><td>ͳ��ʱ��:</td>";
		htmlhead = htmlhead +"<td>��"+document.frmPost.txtCreateTime1.value+"</td>";
		htmlhead = htmlhead +"<td colspan=2>��"+document.frmPost.txtCreateTime2.value+"</td>";
		htmlhead = htmlhead +"</tr>";
		htmlhead = htmlhead +"<tr></tr>";
		htmlhead = htmlhead +"</table>";
		
	var htmlfoot = "<table border=1 align=center cellpadding=5 cellspacing=1>";
		htmlfoot = htmlfoot +"<tr></tr>";
		htmlfoot = htmlfoot +"<tr><td>����:</td><td>�Ʊ���:</td><td></td><td>�����:</td></tr>";
		htmlfoot = htmlfoot +"<tr><td></td><td></td><td></td><td></td></tr>";
		htmlfoot = htmlfoot +"<tr><td></td><td></td><td colspan=2>����ʱ��:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;��</td></tr>";
		htmlfoot = htmlfoot +"</table>";
		
	document.frmPost.excelTable.value=htmlhead + fc.innerHTML + htmlfoot;
	intable.border=0;
	document.frmPost.excelFileName.value=fname;
	document.frmPost.action="excel_download.screen";
	document.frmPost.submit();
	document.frmPost.action="device_sales_stat.do";
	}
}
//-->
</SCRIPT>

<%
	Map deviceClassMap=Postern.getAllDeviceModels();
	if(deviceClassMap == null) deviceClassMap = new HashMap();
	//�γ�һ�� key-key��ɵ�map
	Map deviceModelMap =  new HashMap();
	Iterator temIt = deviceClassMap.keySet().iterator();
	while(temIt.hasNext())
	{
		Object temValue = temIt.next();
		deviceModelMap.put(temValue,temValue);
	}
	//pageContext.setAttribute("DEVICECLASSES", deviceClassMap);
	pageContext.setAttribute("deviceModelMap", deviceModelMap);
	
%>

<form name="frmPost" method="post" action="device_sales_stat.do">
<input type="hidden" name="excelTable" value="" >
<input type="hidden" name="excelFileName" value="" >
<!--
<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
-->

<input type="hidden" name="txtActionType" size="20" value="all">

<table width="822">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�豸����ͳ��</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">

   <tr>
    <td class="list_bg2" align="right">��/վ*</td>
	<td class="list_bg1">
		<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
		<input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtOrgDesc" />" class="text">
		<input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,O,S,D,P','txtOrgID','txtOrgDesc')">
	</td>
    <td class="list_bg2"><div align="right">ͳ��ʱ��</div></td>
    <td class="list_bg1">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" name="txtCreateTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" name="txtCreateTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
  </tr>
  
  
   <tr>
    <td class="list_bg2" ><div align="right">�û�����</div></td>
    <td class="list_bg1"><d:selcmn  name="txtCustType" mapName="SET_C_CUSTOMERTYPE" match="txtCustType" width="23" /> </td>
    <td class="list_bg2"><div align="right">�豸����</div></td>
    <td class="list_bg1"><d:selcmn  name="txtDeviceModel" set="deviceModelMap" match="txtDeviceModel" width="23" /> 
    </td>		 
   </tr>
  
</table>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			

			<td width="22" height="20">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="������" onclick="javascript:save_query_result('�豸����ͳ��',true)"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
		  </tr>
	  </table></td>
	</tr>
</table> 

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
</table>

<div id="fileContent">
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="inTable">
		<tr class="list_head">
			<td class="list_head" nowrap><div align="center">��&nbsp;&nbsp;��</div></td>
			<td class="list_head" nowrap><div align="center">�豸����</div></td>
			<td class="list_head" nowrap><div align="center">&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;</div></td>
			<td class="list_head" nowrap><div align="center">&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
		</tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="orgname" /></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="devicemodel" /></td>
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="amount" /></td>
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="value" /></td>
	</tbl:printcsstr>
</lgc:bloop>
</table>
</div>


</form>

         

      