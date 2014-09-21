<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.stat.CommonStatDTO,com.dtv.oss.dto.OperatorDTO" %>
<%@ page import="com.dtv.oss.web.util.CurrentOperator,com.dtv.oss.web.util.LogonWebCurrentOperator" %>

<%
	Map deviceClassMap=Postern.getAllDeviceModels();
	if(deviceClassMap == null) deviceClassMap = new HashMap();
	//形成一个 key-key组成的map
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
<script language=javascript>
<!--
function query_submit(){
	document.frmPost.action="device_tran_stat.do";
	if(checkDate()){  
		document.frmPost.submit();
	}
}
function checkDate(){
	if (document.frmPost.txtCreateTime1.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime1, true, "开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTime2.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime2, true, "结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"结束日期必须大于等于开始日期"))
		return false;
		
	return true;
}
//-->
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function save_query_result(fname,isOpen){
	var intable=document.getElementById("inTable");
	if(intable!=null){
	intable.border=1;
	var fc=document.getElementById("fileContent");
	document.frmPost.excelTable.value=fc.innerHTML;
	intable.border=0;
	document.frmPost.excelFileName.value=fname;
	document.frmPost.action="excel_download.screen";
	document.frmPost.submit();
	}
}
//-->
</SCRIPT>
<form name="frmPost" method="post" action="device_tran_stat.do">
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
    <td class="title">设备流转统计</td>
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
    <td class="list_bg2"><div align="right">流转类型</div></td>
    <td class="list_bg1"><d:selcmn  name="txtAction" mapName="SET_D_DTACTION" match="txtAction" width="23" /> 
    </td>
    <td class="list_bg2"><div align="right">设备型号</div></td>
    <td class="list_bg1"><d:selcmn  name="txtDeviceModel" set="deviceModelMap" match="txtDeviceModel" width="23" /> 
	    
    </td>		 
  </tr>  
  <tr>
    <td class="list_bg2"><div align="right">创建时间</div></td>
    <td class="list_bg1" colspan="3">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" name="txtCreateTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" name="txtCreateTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
    
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查&nbsp;询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
			<pri:authorized name="device_tran_stat_excel.do" >
			<td width="22" height="20">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="保存结果" onclick="javascript:save_query_result('设备流转统计',true)"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			</pri:authorized>
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
<rs:hasresultset>
<div id="fileContent">
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="inTable">
	<tr class="list_head">
		<td class="list_head" rowspan="2"><div align="center">流转类型</div></td>
<%
	Map mapShow=null;
	if(request.getParameter("txtDeviceModel")==null || "".equals(request.getParameter("txtDeviceModel"))){
		mapShow=deviceModelMap;
	}
	else if(deviceModelMap!=null){
		mapShow=new HashMap();
		mapShow.put(request.getParameter("txtDeviceModel"),request.getParameter("txtDeviceModel"));
	}
	long allValue[];
	long allCount[];
	int colCount = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		colCount = mapShow.size();
		allValue = new long[colCount];
		allCount = new long[colCount];
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
%>
			<td class="list_head" colspan="2"><div align="center">&nbsp;<%=strKey%>&nbsp;</div></td>
<%
		}
	}
	else
		{
		allValue = new long[1];
		allCount = new long[1];
		}
%>
			<td class="list_head" colspan="2" nowrap><div align="center">总计</div></td>
		</tr>
		<tr>
<%
	if (mapShow != null)
	{
		for (int i=0;i<colCount;i++)
		{
%>
			<td class="list_head"><div align="center">笔数</div></td>
			<td class="list_head"><div align="center">数量</div></td>
			
<%}}%>
		<td class="list_head"><div align="center">笔数</div></td>
		<td class="list_head"><div align="center">数量</div></td>

		</tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	<td align="center" class="t12"><d:getcmnname typeName="SET_D_DTACTION" match="oneline:id" /></td>
<%
	CommonStatDTO dto = (CommonStatDTO)pageContext.getAttribute("oneline");
	
	HashMap mapValue = dto.getKeyValue();
	if(mapValue == null) mapValue = new HashMap();
	long dAllValue = 0;
	long dAllCount = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
				long dValue = 0;
				long dCount = 0;
				if((mapValue.get(strKey) != null))
				{
				  Map temMap = (Map)mapValue.get(strKey);
					dValue = Math.round(Double.parseDouble((String)(temMap.get("amount")))*10)/10;
					dCount = Math.round(Integer.parseInt((String)(temMap.get("batchnumber")))*10)/10;
				}
				allValue[i] = allValue[i]+ dValue;
				allCount[i] = allCount[i]+ dCount;
				
				dAllValue = dAllValue + dValue;
				dAllCount = dAllCount + dCount;
%>
		<td align="center" class="t12">&nbsp;<%=dCount%>&nbsp;</td>
		<td align="center" class="t12">&nbsp;<%=dValue%>&nbsp;</td>
		
<%
		}
	}
%>
		<td align="center" class="t12" nowrap>&nbsp;<%=dAllCount%>&nbsp;</td>
		<td align="center" class="t12" nowrap>&nbsp;<%=dAllValue%>&nbsp;</td>
		
  </tbl:printcsstr>
</lgc:bloop>  
	<tr class="trline" > 
      	<td align="center" class="t12">总计</td>
<%
	long dSumAll = 0;
	long dSumAllCount = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<mapShow.size();i++)
		{
			long dValue = Math.round(allValue[i]*100)/100;
			long dCount = Math.round(allCount[i]*100)/100;
			dSumAll = dSumAll + dValue;
			dSumAllCount = dSumAllCount + dCount;
%>
		<td align="center" class="t12">&nbsp;<%=dCount%>&nbsp;</td>
		<td align="center" class="t12">&nbsp;<%=dValue%>&nbsp;</td>
		
<%
		}
	}
%>
	<td align="center" class="t12 nowrap">&nbsp;<%=dSumAllCount%>&nbsp;</td>				
	<td align="center" class="t12 nowrap">&nbsp;<%=dSumAll%>&nbsp;</td>
	
  </tr>
    <tr>
    <td colspan="<%=colCount*2+2*2%>" class="list_foot"></td>
  </tr>
</table>		
</div>
</rs:hasresultset>
</form>  
         

      