<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
                 
<%@ page import="import com.dtv.oss.dto.stat.BookInteractionSumStatDTO" %>

<script language=javascript>
<!--
function query_submit(){
	document.frmPost.action="book_interaction_sum_stat.do";
	if(checkDate()){  
		document.frmPost.submit();
	}
}
function checkDate(){
	if (document.frmPost.txtStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtStartDate, true, "创建开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtEndDate, true, "创建结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtStartDate,document.frmPost.txtEndDate,"结束日期必须大于等于开始日期"))
		return false;
	
	if (document.frmPost.txtStartDate1.value != ''){
		if (!check_TenDate(document.frmPost.txtStartDate1, true, "预约开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtEndDate1.value != ''){
		if (!check_TenDate(document.frmPost.txtEndDate1, true, "预约结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtStartDate1,document.frmPost.txtEndDate1,"结束日期必须大于等于开始日期"))
		return false;
	if (check_Blank(document.frmPost.txtOrgID, true, "受理组织"))
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
<form name="frmPost" method="post" action="book_interaction_sum_stat.do">
<input type="hidden" name="excelTable" value="" >
<input type="hidden" name="excelFileName" value="" >
<table width="822">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">预约受理量统计</td>
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
		
		<td class="list_bg2" align="right">受理组织*</td>
    <td class="list_bg1">
        <input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
        <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtOrgDesc" />" class="text">
	<input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,O,S,D,P','txtOrgID','txtOrgDesc')">
    </td>
	
  	<td class="list_bg2"><div align="right">来源渠道</div></td>
    <td class="list_bg1"><d:selcmn name="txtOpenSourceType" match="txtOpenSourceType" mapName="SET_C_CUSTOPENSOURCETYPE" width="23" /></td>
    </tr>
  	<tr>
    <td class="list_bg2"><div align="right">创建时间</div></td>
    <td class="list_bg1">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartDate)" onblur="lostFocus(this)" name="txtStartDate" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtStartDate" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndDate)" onblur="lostFocus(this)" name="txtEndDate" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtEndDate" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td> 
    <td class="list_bg2"><div align="right">预约时间</div></td>
    <td class="list_bg1">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartDate1)" onblur="lostFocus(this)" name="txtStartDate1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtStartDate1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartDate1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndDate1)" onblur="lostFocus(this)" name="txtEndDate1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtEndDate1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndDate1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
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
			
			<pri:authorized name="book_interaction_sum_stat_excel.do" >
			<td width="22" height="20">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="保存结果" onclick="javascript:save_query_result('预约受理量统计',true)"></td>
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
	int iAllTotalNum = 0;
	int iAllInstallNum = 0;
	int iAllSuccessNum = 0;
	int iAllC1Num = 0;
	int iAllC2Num = 0;
	int iAllWaitNum = 0;
	int iAllProcessNum = 0;
	int iAllFailNum = 0;

	int iAllNum = 0;
	int iAveNum = 0;
		
	double rateFailure = 0;
	double rateSuccess = 0;
	double rateBookingCancel = 0;
	double rateInstallCancel = 0;
	double rateProcess = 0;
	double rateWait = 0;
	double rateAllFailure = 0;
	double rateAllSuccess = 0;
	double rateAllBookingCancel = 0;
	double rateAllInstallCancel = 0;
	double rateAllProcess = 0;
	double rateAllWait = 0;	
	
	int iColSize=0;
%>
<div id="fileContent">
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="inTable">
	<tr class="list_head">
		<td class="list_head" nowrap><div align="center">姓名</div></td>
		<td class="list_head" nowrap><div align="center">受理量</div></td>
		<td class="list_head" nowrap><div align="center">上门量</div></td>
		<td class="list_head" colspan="2" nowrap><div align="center">受理成功量/比率</div></td>
		<td class="list_head" colspan="2" nowrap><div align="center">受理失败量/比率</div></td>
		<td class="list_head" colspan="2" nowrap><div align="center">受理预约取消量/比率</div></td>
		<td class="list_head" colspan="2" nowrap><div align="center">安装预约取消量/比率</div></td>
		<td class="list_head" colspan="2" nowrap><div align="center">处理中量/比率</div></td>
		<td class="list_head" colspan="2" nowrap><div align="center">待处理量/比率</div></td>
	</tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
	BookInteractionSumStatDTO dto = (BookInteractionSumStatDTO)pageContext.getAttribute("oneline");
	iAllTotalNum = iAllTotalNum + dto.getTotalNum();
	iAllInstallNum = iAllInstallNum + dto.getInstallNum();
	iAllSuccessNum = iAllSuccessNum + dto.getSuccessNum();
	iAllFailNum = iAllFailNum + dto.getFailNum();
	iAllC1Num = iAllC1Num + dto.getC1Num();
	iAllC2Num = iAllC2Num + dto.getC2Num();
	iAllProcessNum = iAllProcessNum + dto.getProcessNum();
	iAllWaitNum = iAllWaitNum + dto.getWaitNum();
	
	iColSize++;
	
	if(dto.getTotalNum()!=0)
	{
		rateSuccess = Math.round((dto.getSuccessNum()*1.0)/(dto.getTotalNum()*1.0)*10000)/100.0;
		rateFailure=Math.round((dto.getFailNum()*1.0)/(dto.getTotalNum()*1.0)*10000)/100.0;
		rateBookingCancel = Math.round((dto.getC1Num()*1.0)/(dto.getTotalNum()*1.0)*10000)/100.0;
		rateInstallCancel = Math.round((dto.getC2Num()*1.0)/(dto.getTotalNum()*1.0)*10000)/100.0;
		rateProcess = Math.round((dto.getProcessNum()*1.0)/(dto.getTotalNum()*1.0)*10000)/100.0;;
		rateWait = Math.round((dto.getWaitNum()*1.0)/(dto.getTotalNum()*1.0)*10000)/100.0;
	}
%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="Name" /></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="TotalNum" /></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="InstallNum" /></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="SuccessNum"/></td>
		<td align="center" class="t12" nowrap><%=rateSuccess%>%</td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="FailNum"/></td>
		<td align="center" class="t12" nowrap><%=rateFailure%>%</td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="C1Num"/></td>
		<td align="center" class="t12" nowrap><%=rateBookingCancel%>%</td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="C2Num"/></td>
		<td align="center" class="t12" nowrap><%=rateInstallCancel%>%</td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="ProcessNum"/></td>
		<td align="center" class="t12" nowrap><%=rateProcess%>%</td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="WaitNum"/></td>
		<td align="center" class="t12" nowrap><%=rateWait%>%</td>
  	</tbl:printcsstr>
</lgc:bloop>
<%
	//050311 by Liang
	if(iAllTotalNum!=0)
	{
		rateAllSuccess = Math.round((iAllSuccessNum*1.0)/(iAllTotalNum*1.0)*10000)/100.0;
		rateAllFailure=Math.round((iAllFailNum*1.0)/(iAllTotalNum*1.0)*10000)/100.0;
		rateAllBookingCancel = Math.round((iAllC1Num*1.0)/(iAllTotalNum*1.0)*10000)/100.0;
		rateAllInstallCancel = Math.round((iAllC2Num*1.0)/(iAllTotalNum*1.0)*10000)/100.0;
		rateAllProcess = Math.round((iAllProcessNum*1.0)/(iAllTotalNum*1.0)*10000)/100.0;;
		rateAllWait = Math.round((iAllWaitNum*1.0)/(iAllTotalNum*1.0)*10000)/100.0;
	}
	
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
	<tr class="<%=strStyle1%>"> 
      		<td align="center" class="t12" nowrap>总计</td>
		<td align="center" class="t12" nowrap><%=iAllTotalNum%></td>	
		<td align="center" class="t12" nowrap><%=iAllInstallNum%></td>
		<td align="center" class="t12" nowrap><%=iAllSuccessNum%></td>
		<td align="center" class="t12" nowrap><%=rateAllSuccess%>%</td>
		<td align="center" class="t12" nowrap><%=iAllFailNum%></td>
		<td align="center" class="t12" nowrap><%=rateAllFailure%>%</td>
		<td align="center" class="t12" nowrap><%=iAllC1Num%></td>
		<td align="center" class="t12" nowrap><%=rateAllBookingCancel%>%</td>
		<td align="center" class="t12" nowrap><%=iAllC2Num%></td>
		<td align="center" class="t12" nowrap><%=rateAllInstallCancel%>%</td>
		<td align="center" class="t12" nowrap><%=iAllProcessNum%></td>
		<td align="center" class="t12" nowrap><%=rateAllProcess%>%</td>
		<td align="center" class="t12" nowrap><%=iAllWaitNum%></td>
		<td align="center" class="t12" nowrap><%=rateAllWait%>%</td>
 	</tr>
<%
	if(iColSize==0)
		iColSize=1;
	double fAveTotalNum = (double)iAllTotalNum/iColSize;
	double fAveInstallNum = (double)iAllInstallNum/iColSize;
	double fAveSuccessNum = (double)iAllSuccessNum/iColSize;
	double fAveFailNumNum = (double)iAllFailNum/iColSize;
	double fAveC1Num = (double)iAllC1Num/iColSize;
	double fAveC2Num = (double)iAllC2Num/iColSize;
	double fAveProcessNum = (double)iAllProcessNum/iColSize;
	double fAveWaitNum = (double)iAllWaitNum/iColSize;

%>
    	<tr class="<%=strStyle2%>"> 
      		<td align="center" class="t12" nowrap>平均</td>
		<td align="center" class="t12" nowrap><%=fAveTotalNum%></td>	
		<td align="center" class="t12" nowrap><%=fAveInstallNum%></td>
		<td align="center" class="t12" colspan="2" nowrap><%=fAveSuccessNum%></td>
		<td align="center" class="t12" colspan="2" nowrap><%=fAveFailNumNum%></td>
		<td align="center" class="t12" colspan="2" nowrap><%=fAveC1Num%></td>
		<td align="center" class="t12" colspan="2" nowrap><%=fAveC2Num%></td>
		<td align="center" class="t12" colspan="2" nowrap><%=fAveProcessNum%></td>
		<td align="center" class="t12" colspan="2" nowrap><%=fAveWaitNum%></td>
 	</tr>
 	<tr>
    		<td colspan="15" class="list_foot"></td>
  	</tr>
</table>
</div>	
</rs:hasresultset>
</form>    