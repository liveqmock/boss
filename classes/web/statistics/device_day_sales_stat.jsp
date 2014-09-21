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
<%@ page import="com.dtv.oss.dto.stat.DeviceDaySalesStatDTO"%>
<%@ page import="com.dtv.oss.web.taglib.util.QueryPageProp"%>

<script language=javascript>

function query_submit(){
	document.frmPost.action="device_day_sales_stat.do";
	if(checkDate()){  
		document.frmPost.submit();
	}
}
function checkDate(){
	
	if(document.frmPost.txtFillDate.value==''){
 		alert("请选择日期");
 		document.frmPost.txtFillDate.focus();
 		return false;
 	}
 	
	if (document.frmPost.txtFillDate.value != ''){
		if (!check_TenDate(document.frmPost.txtFillDate, true, "日期")){
			return false;
		}
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
	document.frmPost.action="device_day_sales_stat.do";
	}
}
//-->
</SCRIPT>

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
    <td class="title">机顶盒销售统计日报表</td>
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
 
    <td class="list_bg2"><div align="right">日期  </div></td>
    <td class="list_bg1">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtFillDate)" onblur="lostFocus(this)" name="txtFillDate" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtFillDate" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtFillDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
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
			

			<td width="22" height="20">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="保存结果" onclick="javascript:save_query_result('机顶盒销售统计日报表',true)"></td>
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
			<td class="list_head" nowrap><div align="center">&nbsp;&nbsp;</div></td>
			
			<td class="list_head" nowrap><div align="center">松下高清2860（元）</div></td>
			<td class="list_head" nowrap><div align="center">长虹高清1888（元）</div></td>
			<td class="list_head" nowrap><div align="center">长虹高清2360（元）</div></td>
			<td class="list_head" nowrap><div align="center">长虹标清666（元）</div></td>
			<td class="list_head" nowrap><div align="center">长虹标清598（元）</div></td>
			
			<td class="list_head" nowrap><div align="center">同洲标清666(元）</div></td>
			<td class="list_head" nowrap><div align="center">同洲标清598（元）</div></td>
			<td class="list_head" nowrap><div align="center">华为标清666（元）</div></td>
			<td class="list_head" nowrap><div align="center">华为标清598（元）</div></td>
			<td class="list_head" nowrap><div align="center">智能卡补卡100(元）</div></td>
			
			<td class="list_head" nowrap><div align="center">直销配卡(标清)100(元)</div></td>
			<td class="list_head" nowrap><div align="center">直销配卡(高清)100(元)</div></td>
			<td class="list_head" nowrap><div align="center">营业额（元）</div></td>
		</tr>

<%
	long sxgq_2860 =0;
	long chgq_1888 =0;
	long chgq_2360 =0;
	long chbq_666 =0;
	long chbq_598 =0;
	
	long bzbq_666 =0;
	long bzbq_598 =0;
	long hwbq_666 =0;
	long hwbq_598 =0;
	long znkbk =0;
	
	long zxpkbq =0;
	long zxpkgq =0;
	long totalmoney =0;

%>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

<%
	DeviceDaySalesStatDTO dto = (DeviceDaySalesStatDTO)pageContext.getAttribute("oneline");
	sxgq_2860 = sxgq_2860 + dto.getSxgq_2860();
	chgq_1888 = chgq_1888 + dto.getChgq_1888();
	chgq_2360 = chgq_2360 + dto.getChgq_2360();
	chbq_666 = chbq_666 + dto.getChbq_666();
	chbq_598 = chbq_598 + dto.getChbq_598();
	
	bzbq_666 = bzbq_666 + dto.getBzbq_666();
	bzbq_598 = bzbq_598 + dto.getBzbq_598();
	hwbq_666 = hwbq_666 + dto.getHwbq_666();
	hwbq_598 = hwbq_598 + dto.getHwbq_598();
	znkbk = znkbk + dto.getZnkbk();
	
	zxpkbq = zxpkbq + dto.getZxpkbq();
	zxpkgq = zxpkgq + dto.getZxpkgq();
	totalmoney = totalmoney + dto.getTotalmoney();
	
%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="belongName" /></td>
		
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="sxgq_2860" /></td>
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="chgq_1888" /></td>
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="chgq_2360" /></td>
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="chbq_666" /></td>
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="chbq_598" /></td>
		
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="bzbq_666" /></td>
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="bzbq_598" /></td>
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="hwbq_666" /></td>
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="hwbq_598" /></td>
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="znkbk" /></td>
		
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="zxpkbq" /></td>
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="zxpkgq" /></td>
		<td align="right" class="t12" nowrap><tbl:write name="oneline" property="totalmoney" /></td>
	</tbl:printcsstr>
</lgc:bloop>

<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1' " > 
		
		<td align="center" class="t12" nowrap>&nbsp;总计&nbsp;</td>
		
		<td align="right" class="t12" nowrap><%=sxgq_2860%></td>
		<td align="right" class="t12" nowrap><%=chgq_1888%></td>
		<td align="right" class="t12" nowrap><%=chgq_2360%></td>
		<td align="right" class="t12" nowrap><%=chbq_666%></td>
		<td align="right" class="t12" nowrap><%=chbq_598%></td>
		
		<td align="right" class="t12" nowrap><%=bzbq_666%></td>
		<td align="right" class="t12" nowrap><%=bzbq_598%></td>
		<td align="right" class="t12" nowrap><%=hwbq_666%></td>
		<td align="right" class="t12" nowrap><%=hwbq_598%></td>
		<td align="right" class="t12" nowrap><%=znkbk%></td>
		
		<td align="right" class="t12" nowrap><%=zxpkbq%></td>
		<td align="right" class="t12" nowrap><%=zxpkgq%></td>
		<td align="right" class="t12" nowrap><%=totalmoney%></td>
		
</tr>
</table>
</div>


</form>

         

      