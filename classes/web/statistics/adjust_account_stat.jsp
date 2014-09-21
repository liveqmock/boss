<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.service.util.BusinessUtility" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.stat.CommonStatDTO,com.dtv.oss.dto.OperatorDTO" %>
<%@ page import="com.dtv.oss.web.util.CurrentOperator,com.dtv.oss.web.util.LogonWebCurrentOperator" %>
<%
String selStatType = request.getParameter("selStatType");
%>
<script language=javascript>
<!--
function query_submit(){
	document.frmPost.action="adjust_account_stat.do";
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


	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"结束日期必须大于等于开始日期")){
		
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
<div id="updselwaitlayer" style="position:absolute; left:700px; top:104px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr><td width="100%"><div align="center"><font size="2">正在获取内容。。。</font></td></tr>
    </table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0" ></iframe>

<form name="frmPost" method="post" action="adjust_account_stat.do">
<input type="hidden" name="excelTable" value="" >
<input type="hidden" name="excelFileName" value="" >
<!--input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10"-->

<input type="hidden" name="txtActionType" size="20" value="all">
<table width="822">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">调帐统计</td>
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
   <td class="list_bg2" align="right">所属组织</td>
   		 <td class="list_bg1">
	    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
			<input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtOrgDesc" />" class="text">
			<input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,O,S,D,P','txtOrgID','txtOrgDesc')">
	   </td>	
  </tr>
    
  <tr>	 
    <td class="list_bg2"><div align="right">所在区域</div></td>
    <td class="list_bg1">
    <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtCountyDesc" />" class="text">
    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
    </td>
    <td class="list_bg2"><div align="right">客户类型</div></td>
    <td class="list_bg1"><d:selcmn  name="txtCustType" mapName="SET_C_CUSTOMERTYPE" match="txtCustType" width="23" /> </td>
  </tr>
  
   <tr>	 
   	<td class="list_bg2"><div align="right">调帐方式</div></td>
    <td class="list_bg1"><d:selcmn  name="txtAdjustType" mapName="SET_F_ACCOUNTADJUSTMENTTYPE" match="txtAdjustType" width="23" /> </td>
    <td class="list_bg2"><div align="right">帐户类型</div></td>
    <td class="list_bg1"><d:selcmn  name="txtAcctType" mapName="SET_F_ACCOUNTTYPE" match="txtAcctType" width="23" />
    </td>
  </tr>
  
  <tr>
  	<td class="list_bg2"><div align="right">调帐原因</div></td>
    <td class="list_bg1"><d:selcmn  name="txtAdjustReason" mapName="SET_F_ACCOUNTADJUSTMENTREASON" match="txtAdjustReason" width="23" /> </td>
    
    <td class="list_bg2"><div align="right">创建时间</div></td>
    <td class="list_bg1">
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
			
			<pri:authorized name="adjust_account_stat_excel.do" >
			<td width="22" height="20">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="保存结果" onclick="javascript:save_query_result('调帐统计',true)"></td>
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
<%
 String tableTitle = "组织/区域";
 String id = "0";
 //String selStatType = request.getParameter("selStatType");
 if("O".equals(selStatType))
 {
 	String orgId = request.getParameter("txtOrgID");
 	if((orgId!=null) && (!"".equals(orgId)))
 		id = orgId;
 	if (!BusinessUtility.hasSon(id, "org"))tableTitle="操作员";
	else tableTitle="组织";
 }
else if("D".equals(selStatType))
	{
	 String distId = request.getParameter("txtDistrictID");
		if((distId!=null) && (!"".equals(distId)) )
	 		id = distId;
		if (!BusinessUtility.hasSon(id, "dist"))tableTitle="操作员";
		else tableTitle="区域";
	}

%>
<div id="fileContent">
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="inTable">
	<tr class="list_head">
		<td class="list_head" rowspan="2" nowrap><div align="center"><%=tableTitle%></div></td>
<%
	
	 //Map mapShow = Postern.getHashKeyValueByName("SET_F_ACCOUNTADJUSTMENTDIRECTION");
	 Map mapShow = Postern.getHashKeyValueByName("SET_F_ADJUSTMENTREFERRECORDTYPE");
	
	double allValue[];
	long allCount[];
	int colCount = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		colCount = mapShow.size();
		allValue = new double[colCount];
		allCount = new long[colCount];
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
%>
			<td class="list_head" colspan="2" nowrap><div align="center"><%=mapShow.get(strKey)%></div></td>
<%
		}
	}
	else
		{
		allValue = new double[1];
		allCount = new long[1];
		}
%>
		</tr>
		<tr>
<%
	if (mapShow != null)
	{
		for (int i=0;i<colCount;i++)
		{
%>
			<td class="list_head"><div align="center">笔数</div></td>
			<td class="list_head"><div align="center">金额</div></td>
			
<%}}%>
		</tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	<td align="center" class="t12" nowrap><tbl:write name="oneline" property="name" /></td>
<%
	CommonStatDTO dto = (CommonStatDTO)pageContext.getAttribute("oneline");
	
	HashMap mapValue = dto.getKeyValue();
	double dAllValue = 0;
	long dAllCount = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
				double dValue = 0;
				long dCount = 0;
				if((mapValue.get(strKey) != null))
				{
					Map temMap = (Map)mapValue.get(strKey);
					dValue = Double.parseDouble((String)(temMap.get("amount")));
					dCount = Math.round(Integer.parseInt((String)(temMap.get("batchnumber")))*10)/10;
				}
				allValue[i] = allValue[i]+ dValue;
				allCount[i] = allCount[i]+ dCount;
				dAllValue = dAllValue + dValue;
				dAllCount = dAllCount + dCount;
%>
		<td align="center" class="t12"><%=dCount%></td>
		<td align="center" class="t12" nowrap> <%=Math.round(dValue*100)/100.0%> </td>
		
<%
		}
	}
%>
  </tbl:printcsstr>
</lgc:bloop>  
	<tr class="trline" > 
      	<td align="center" class="t12" nowrap>总计</td>
<%
	double dSumAll = 0;
	long dSumAllCount = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<mapShow.size();i++)
		{
			dSumAll = dSumAll + allValue[i];
			dSumAllCount = dSumAllCount + allCount[i];
%>
		<td align="center" class="t12"> <%=allCount[i]%> </td>
		<td align="center" class="t12" nowrap> <%=Math.round(allValue[i]*100)/100.0%> </td>
<%
		}
	}
%>	
  </tr>
    <tr>
    <td colspan="<%=colCount*2+1*2%>" class="list_foot"></td>
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

      