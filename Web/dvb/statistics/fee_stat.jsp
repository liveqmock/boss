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

<script language=javascript>
function query_submit(){
	document.frmPost.action="fee_stat.do";
	if(checkDate()){  
		document.frmPost.submit();
	}
}
function checkDate(){
	if (document.frmPost.txtCreateTime1.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime1, true, "创建起始时间")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTime2.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime2, true, "创建结束时间")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"创建结束时间必须大于等于创建起始时间")){
		
		return false;
	}
	
	if (check_Blank(document.frmPost.txtDistrictID, true, "区域"))
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
	document.frmPost.excelTable.value=fc.innerHTML;
	intable.border=0;
	document.frmPost.excelFileName.value=fname;
	document.frmPost.action="excel_download.screen";
	document.frmPost.submit();
	}
}
//-->
</SCRIPT>
<form name="frmPost" method="post" action="fee_stat.do">
<input type="hidden" name="excelTable" value="" >
<input type="hidden" name="excelFileName" value="" >
<!--input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10"-->

<input type="hidden" name="txtActionType" size="20" value="all">
<table width="822">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">费用统计</td>
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
    <td class="list_bg2"><div align="right">客户类型</div></td>
    <td class="list_bg1"><d:selcmn  name="txtCustType" mapName="SET_C_CUSTOMERTYPE" match="txtCustType" width="23" /> 
    </td>
    
    <td class="list_bg2"><div align="right">所在区域*</div></td>
    <td class="list_bg1">
    <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtCountyDesc" />" class="text">
    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
    </td>
  </tr>
  <tr>
  	<td class="list_bg2"><div align="right">帐户类型</div></td>
    <td class="list_bg1"><d:selcmn  name="txtAcctType" mapName="SET_F_ACCOUNTTYPE" match="txtAcctType" width="23" />
    </td>
    <td class="list_bg2"><div align="right">费用类型</div></td>
    <td class="list_bg1"><d:selcmn  name="txtFeeType" mapName="SET_F_BRFEETYPE" match="txtFeeType" width="23" />
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
			
			<pri:authorized name="fee_stat_excel.do" >
			<td width="22" height="20">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="保存结果" onclick="javascript:save_query_result('费用统计',true)"></td>
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
			<td class="list_head" nowrap><div align="center">区域</div></td>
<%
 String txtFeeType = request.getParameter("txtFeeType");
 Map mapShow = null;
 if(txtFeeType == null ||"".equals(txtFeeType))
 {
 	mapShow = Postern.getHashKeyValueByName("SET_F_BRFEETYPE");
 }
else
	mapShow = Postern.gettAcctitemtypeByFeeType(txtFeeType);//key, value
	double allValue[];
	int colCount = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		colCount = mapShow.size();
		allValue = new double[colCount];
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
%>
			<td class="list_head" nowrap><div align="center"><%=mapShow.get(strKey)%></div></td>
<%
		}
	}
	else
		allValue = new double[1];
%>
			<td class="list_head" nowrap><div align="center">总计</div></td>
		</tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	<td align="center" class="t12" nowrap><tbl:write name="oneline" property="name" /></td>
<%
	CommonStatDTO dto = (CommonStatDTO)pageContext.getAttribute("oneline");
	HashMap mapValue = dto.getKeyValue();
	double dAllValue = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
				double dValue = 0;
				if((mapValue.get(strKey) != null) && (mapValue.get(strKey) != ""))
				{
					dValue = Double.parseDouble((String)mapValue.get(strKey));
				}
				allValue[i] = allValue[i]+ dValue;
				dAllValue = dAllValue + dValue;
%>
		<td align="center" class="t12" nowrap> <%=Math.round(dValue*100)/100.0%> </td>
<%
		}
	}
%>
		<td align="center" class="t12" nowrap> <%=Math.round(dAllValue*100)/100.0%> </td>
  </tbl:printcsstr>
</lgc:bloop>  
	<tr class="trline" > 
      	<td align="center" class="t12" nowrap>总计</td>
<%
	double dSumAll = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<mapShow.size();i++)
		{
			//double dValue = Math.round(allValue[i]*100)/100.0;
			dSumAll = dSumAll + allValue[i];
%>
		<td align="center" class="t12" nowrap> <%=Math.round(allValue[i]*100)/100.0%> </td>
<%
		}
	}
%>					
	<td align="center" class="t12" nowrap> <%=Math.round(dSumAll*100)/100.0%> </td>
  </tr>
    <tr>
    <td colspan="<%=colCount+2%>" class="list_foot"></td>
  </tr>
</table>		
</div>
</rs:hasresultset>
</form>  
         

      