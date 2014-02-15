<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
                 
<%@ page import="com.dtv.oss.dto.stat.CustomerProblemSumStatDTO" %>

<script language=javascript>
<!--
function query_submit(){
	document.frmPost.action="customer_problem_sum_stat.do";
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
	if(!compareDate(document.frmPost.txtStartDate,document.frmPost.txtEndDate,"结束日期必须大于等于开始日期"))
		return false;
	if (check_Blank(document.frmPost.txtOrgID, true, "所属组织"))
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
<form name="frmPost" method="post" action="customer_problem_sum_stat.do">
<input type="hidden" name="excelTable" value="" >
<input type="hidden" name="excelFileName" value="" >
<table width="822">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">报修量统计</td>
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
    <td class="list_bg2"><div align="right">时间</div></td>
    <td class="list_bg1">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartDate)" onblur="lostFocus(this)" name="txtStartDate" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtStartDate" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndDate)" onblur="lostFocus(this)" name="txtEndDate" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtEndDate" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
    <td class="list_bg2"><div align="right">操作员LoginID</div></td>
    <td class="list_bg1"><input type="text" name="txtOperatorID" maxlength="10" size="25" value="<tbl:writeparam name="txtOperatorID" />" ></td>
  </tr>
  <tr>
  	<td class="list_bg2" align="right">组织机构*</td>
    <td class="list_bg1">
	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
	<input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtOrgDesc" />" class="text">
	<input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,O,S,D','txtOrgID','txtOrgDesc')">
	<td class="list_bg2" align="right"></td>
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
			
			<pri:authorized name="customer_problem_sum_stat_excel.do" >
			<td width="22" height="20">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="保存结果" onclick="javascript:save_query_result('报修量统计',true)"></td>
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
	int iAllCatNormalNum = 0;
	int iAllCatSelfInstallNum = 0;
	int iAllCatInOneWeekNum = 0;
	int iAllOrgNum = 0;
	int iAllNum = 0;
	
	int iColSize=0;
%>
<div id="fileContent">
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="inTable">
	<tr class="list_head">
		<td class="list_head" nowrap><div align="center">姓名</div></td>
		<td class="list_head" nowrap><div align="center">普通报修</div></td>
		<td class="list_head" nowrap><div align="center">自安装失败</div></td>
		<td class="list_head" nowrap><div align="center">上门安装一周内</div></td>
		<td class="list_head" nowrap><div align="center">总计报修量</div></td>
	</tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
	CustomerProblemSumStatDTO dto = (CustomerProblemSumStatDTO)pageContext.getAttribute("oneline");
	iAllCatNormalNum = iAllCatNormalNum + dto.getCatNormalNum();
	iAllCatSelfInstallNum = iAllCatSelfInstallNum + dto.getCatSelfInstallNum();
	iAllCatInOneWeekNum = iAllCatInOneWeekNum + dto.getCatInOneWeekNum();

	iColSize++;
	
	iAllOrgNum = dto.getCatNormalNum() + dto.getCatSelfInstallNum() + dto.getCatInOneWeekNum();
	iAllNum = iAllNum + iAllOrgNum;
%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="Name" /></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="CatNormalNum" /></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="CatSelfInstallNum"/></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="CatInOneWeekNum" /></td>
		<td align="center" class="t12" nowrap><%=iAllOrgNum%></td>
  	</tbl:printcsstr>	
</lgc:bloop>  

<%
	String strStyle1="";
	String strStyle2="";
	if(iColSize%2==0){
		strStyle1="list_bg1";
		strStyle2="list_bg2";
	}
	else{
		strStyle1="list_bg2";
		strStyle2="list_bg1";
	}
%>
          <tr  class="<%=strStyle1 %>" >  
          	<td align="center"  class="t12" nowrap>总计</td>
          	<td align="center"  class="t12" nowrap><%=iAllCatNormalNum%></td>  
          	<td align="center"  class="t12" nowrap><%=iAllCatSelfInstallNum%></td>  
          	<td align="center"  class="t12" nowrap><%=iAllCatInOneWeekNum%></td>
          	<td align="center"  class="t12" nowrap><%=iAllNum%></td>   
          </tr>   
<%   
	if(iColSize  ==  0)  
		iColSize   =  1;  
	double  fAveCatNormalNum   =(double)iAllCatNormalNum/iColSize; 
	double fAveCatSelfInstallNum  =(double)iAllCatSelfInstallNum/iColSize; 
	double fAveCatInOneWeekNum=   (double)iAllCatInOneWeekNum/iColSize;   
	double   fAveAllNum  =(double)iAllNum/iColSize;   
%>    
	<tr class="<%=strStyle2 %>">
		<td align="center" class="t12" nowrap>平均</td>
		<td align="center" class="t12" nowrap><%=fAveCatNormalNum%></td>
		<td align="center" class="t12" nowrap><%=fAveCatSelfInstallNum%></td>
		<td align="center" class="t12" nowrap><%=fAveCatInOneWeekNum%></td>  
		<td align="center" class="t12" nowrap><%=fAveAllNum%></td>
	</tr>  	   
	<tr>   
		<td colspan="5" class="list_foot"></td>
	</tr> 
</table>
</div>
</rs:hasresultset> 
</form>  