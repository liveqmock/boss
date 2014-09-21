<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.stat.CommonStatDTO"%>
<%
String selStatType = request.getParameter("selStatType");
%>
<script language=javascript>
function query_submit(){
	document.frmPost.action="other_csi_stat.do";
	if(checkDate()){  
		document.frmPost.submit();
	}
}
function checkDate(){
	if (document.frmPost.txtStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtStartDate, true, "开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtEndDate, true, "结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtStartDate,document.frmPost.txtEndDate,"结束日期必须大于等于开始日期")){
		
		return false;
	}
  	if (check_Blank(document.frmPost.selStatType, true, "统计方式"))
		return false;
	
	if(document.frmPost.selStatType.value=="O" && document.frmPost.txtOrgID.value==""){	
		if (check_Blank(document.frmPost.txtOrgID, true, "所属组织"))
		return false;
	}
	else if(document.frmPost.selStatType.value=="D" && document.frmPost.txtDistrictID.value==""){
		if (check_Blank(document.frmPost.txtDistrictID, true, "区域"))
		return false;
	}
	
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
	document.frmPost.excelTable.value=fc.innerHTML;
	intable.border=0;
	document.frmPost.excelFileName.value=fname;
	document.frmPost.action="excel_download.screen";
	document.frmPost.submit();
	}
}
//-->
</SCRIPT>
<form name="frmPost" method="post" action="other_csi_stat.do">
<input type="hidden" name="excelTable" value="" >
<input type="hidden" name="excelFileName" value="" >
<!--input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10"-->
<input type="hidden" name="txtActionType" size="20" value="all">

<table width="822">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">其他受理统计</td>
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

    <td class="list_bg2"><div align="right">统计方式*</div></td>
    <td class="list_bg1">
    <%
    Map mapType = new HashMap();
    mapType.put("O", "按操作机构统计");
    mapType.put("D", "按客户所在区域统计");
    pageContext.setAttribute("StatTypeList", mapType);
%>        
  <tbl:select name="selStatType" set="StatTypeList" match="selStatType" width="23" onchange="change_show(document.frmPost.selStatType.value)"/></td>
<td class="list_bg2"><div align="right">客户类型</div></td>
    <td class="list_bg1"><d:selcmn  name="txtCustType" mapName="SET_C_CUSTOMERTYPE" match="txtCustType" width="23" /></td>
  </tr>
  <tr>
  	<td class="list_bg2"><div align="right">所属组织</div></td>
    <td class="list_bg1">
    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
    	<input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtOrgDesc" />" class="text">
	<input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,O,S,D','txtOrgID','txtOrgDesc')">
    </td>
    <td class="list_bg2"><div align="right">区域</div></td>
    <td class="list_bg1">
    
    <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtCountyDesc" />" class="text">
    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
   
    </td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">创建时间</div></td>
    <td class="list_bg1">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartDate)" onblur="lostFocus(this)" name="txtStartDate" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtStartDate" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndDate)" onblur="lostFocus(this)" name="txtEndDate" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtEndDate" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
    <td class="list_bg2"><div align="right"></div></td>
    <td class="list_bg1"></td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查&nbsp;询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
			<pri:authorized name="other_csi_stat_excel.do" >
			<td width="22" height="20">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="保存结果" onclick="javascript:save_query_result('其他受理统计',true)"></td>
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
			<td class="list_head"><div align="center">组织/区域</div></td>
<%
	Map mapShow = Postern.getHashKeyValueByName("SET_V_CUSTSERVICEINTERACTIONTYPE");
	mapShow.remove("OS");
	
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
			<td class="list_head"><div align="center"><%=mapShow.get(strKey)%></div></td>
<%
		}
	}
	else
		allValue = new long[1];
%>
			<td class="list_head"><div align="center">总计</div></td>
		</tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	<td align="center" class="t12"><tbl:write name="oneline" property="name" /></td>
<%
	CommonStatDTO dto = (CommonStatDTO)pageContext.getAttribute("oneline");
	HashMap mapValue = dto.getKeyValue();
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
		<td align="center" class="t12" nowrap><%=dValue%></td>
<%
		}
	}
%>
		<td align="center" class="t12" nowrap><%=dAllValue%></td>
  </tbl:printcsstr>
</lgc:bloop>  
	<tr class="trline" > 
      	<td align="center" class="t12" nowrap>总计</td>
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
		<td align="center" class="t12" nowrap> <%=dValue%> </td>
<%
		}
	}
%>					
	<td align="center" class="t12" nowrap> <%=dSumAll%> </td>
  </tr>
    <tr>
    <td colspan="<%=colCount+2%>" class="list_foot"></td>
  </tr>
</table>		
</div>
</rs:hasresultset>
</form>  
<script language=javascript>
<!--
change_show("<%=selStatType%>");
function change_show(txtSelStatType)
{
	if("O"==txtSelStatType)//按操作机构统计
	{
	  document.frmPost.txtDistrictID.value="";
	  document.frmPost.txtCountyDesc.value="";
		document.frmPost.txtCountyDesc.readOnly=true;
		document.all["selDistButton"].style.display="none";
		
		document.frmPost.txtOrgDesc.readOnly=false;
		document.all["selOrgButton"].style.display="";
	}
	if("D"==txtSelStatType)//按客户所在区域统计
	{	
	  document.frmPost.txtOrgID.value="";
	  document.frmPost.txtOrgDesc.value="";
		document.frmPost.txtOrgDesc.readOnly=true;
		document.all["selOrgButton"].style.display="none";
		
		document.frmPost.txtCountyDesc.readOnly=false;
		document.all["selDistButton"].style.display="";
	}
}
//-->
</script>        

      