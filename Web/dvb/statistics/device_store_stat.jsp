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
	pageContext.setAttribute("DEVICECLASSES", deviceClassMap);
%>
<script language=javascript>
<!--
function query_submit(){
	document.frmPost.action="device_store_stat.do";
	if(checkDate()){  
		document.frmPost.submit();
	}
}
function checkDate(){
	//if (document.frmPost.txtCreateTime1.value != ''){
	//	if (!check_TenDate(document.frmPost.txtCreateTime1, true, "开始日期")){
	//		return false;
	//	}
	//}
	//if (document.frmPost.txtCreateTime2.value != ''){
	//	if (!check_TenDate(document.frmPost.txtCreateTime2, true, "结束日期")){
	//		return false;
	//	}
	//}
	//if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"结束日期必须大于等于开始日期"))
	//	return false;
		
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
<form name="frmPost" method="post" action="device_store_stat.do">
<input type="hidden" name="excelTable" value="" >
<input type="hidden" name="excelFileName" value="" >
<!--
<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">


<input type="hidden" name="txtActionType" size="20" value="all">
-->
<table width="822">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">库存设备统计</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<!--
<br>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2"><div align="right">流转类型 SET_D_DTACTION</div></td> 
    </td>
    <td class="list_bg2"><div align="right">设备类型</div></td>
 
	    
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
		  </tr>
	  </table></td>
	</tr>
</table> 
-->
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr align="center">
  	<pri:authorized name="device_store_stat_excel.do" >
    <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="保存结果" onclick="javascript:save_query_result('库存设备统计',true)"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table></td>
	  </pri:authorized>
	</tr>
</table> 

<br>
</table>
<rs:hasresultset>
<div id="fileContent">
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="inTable">
	<tr class="list_head">
		<td class="list_head" nowrap><div align="center">仓库</div></td>
<%
	Map mapShow=deviceClassMap;
	
	long allValue[];
	int colCount = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		colCount = mapShow.size();
		allValue = new long[colCount];
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
%>
      <!--修改为 devicemodule 而不是description-->
			<td class="list_head" nowrap><div align="center">&nbsp;<%=strKey%>&nbsp;</div></td>
<%
		}
	}
	else
		allValue = new long[1];
%>
			<td class="list_head" nowrap><div align="center">总计</div></td>
		</tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	<td align="center" class="t12" nowrap><tbl:write name="oneline" property="name" /></td>
<%
	CommonStatDTO dto = (CommonStatDTO)pageContext.getAttribute("oneline");
	
	HashMap mapValue = dto.getKeyValue();
	if(mapValue == null) mapValue = new HashMap();
	long dAllValue = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
				long dValue = 0;
				if((mapValue.get(strKey) != null) && (mapValue.get(strKey) != ""))
				{
					dValue = Math.round(Double.parseDouble((String)mapValue.get(strKey))*10)/10;
				}
				allValue[i] = allValue[i]+ dValue;
				dAllValue = dAllValue + dValue;
%>
		<td align="center" class="t12">&nbsp;<%=dValue%>&nbsp;</td>
<%
		}
	}
%>
		<td align="center" class="t12" nowrap>&nbsp;<%=dAllValue%>&nbsp;</td>
  </tbl:printcsstr>
</lgc:bloop>  
	<tr class="trline" > 
      	<td align="center" class="t12">总计</td>
<%
	long dSumAll = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<mapShow.size();i++)
		{
			long dValue = Math.round(allValue[i]*100)/100;
			dSumAll = dSumAll + dValue;
%>
		<td align="center" class="t12">&nbsp;<%=dValue%>&nbsp;</td>
<%
		}
	}
%>					
	<td align="center" class="t12" nowrap>&nbsp;<%=dSumAll%>&nbsp;</td>
  </tr>
    <tr>
    <td colspan="<%=colCount+2%>" class="list_foot"></td>
  </tr>
</table>	
</div>	
</rs:hasresultset>
</form>  
         

      