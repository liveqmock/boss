<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.stat.CommonStatDTO"%>

<script language=javascript>
function query_submit(){
	document.frmPost.action="prepay_deduction_stat.do";
	if(checkData())
		document.frmPost.submit();
}

function checkData(){
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
	
	if (check_Blank(document.frmPost.txtDistrictID, true, "所在区域"))
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
<form name="frmPost" method="post" action="prepay_deduction_stat.do">
<input type="hidden" name="excelTable" value="" >
<input type="hidden" name="excelFileName" value="" >
<!--input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10"-->

<input type="hidden" name="txtActionType" size="20" value="all">

<table width="822">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">预存抵扣统计</td>
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
    <td class="list_bg2"><div align="right">付费类型</div></td>
    <%
    pageContext.setAttribute("MOPList", Postern.getOpeningMop());
    %>
    <td class="list_bg1">
    	<tbl:select name="txtMOP" set="MOPList" match="txtMOP" width="23" />
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
			
			<pri:authorized name="prepay_deduction_stat_excel.do" >
			<td width="22" height="20">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="保存结果" onclick="javascript:save_query_result('预存抵扣统计',true)"></td>
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
		<td class="list_head" rowspan="2" nowrap><div align="center">区域</div></td>
<%
	
Map mapShow = Postern.getHashKeyValueByName("SET_F_PDR_REFERRECORDTYPE");
Map mapSecSub = Postern.getHashKeyValueByName("SET_F_PREPAYMENTTYPE");

	
	double allValue[][] = null;
	Object mapShowKey[] = null;
	List secSubKeyList = new ArrayList();
	int colCount = 1;
	int secSubCount = 1;
	if(mapSecSub != null)
	{
		Object [] secSubKey = mapSecSub.keySet().toArray();
		secSubCount = secSubKey.length;
		for(int n=0;n<secSubCount;n++)
		{
			secSubKeyList.add(secSubKey[n]);
		}
		//Collections.sort(secSubKeyList);
		Collections.reverse(secSubKeyList);
	}
	if (mapShow != null)
	{
		mapShowKey= mapShow.keySet().toArray();
		colCount = mapShowKey.length;
		for (int i=0;i<colCount;i++)
		{
%>
			<td class="list_head" colspan="<%=mapSecSub.size()%>" nowrap><%=mapShow.get(mapShowKey[i])%></td>
<%
		}
	}
%>
			<td class="list_head" rowspan="2" nowrap><div align="center">总计</div></td>
		</tr>
		<tr class="list_head">
			<%
			for(int k=0;k<colCount;k++){
				if(mapSecSub != null)
				{
					for(int n=0;n<secSubCount;n++){
						%><td class="list_head" nowrap><div align="center"><%=mapSecSub.get(secSubKeyList.get(n))%></div></td><%
				}
			}
		}
				allValue = new double[colCount][secSubCount];
			%>
		<tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	<td align="center" class="t12" nowrap><tbl:write name="oneline" property="name" /></td>
<%
	CommonStatDTO dto = (CommonStatDTO)pageContext.getAttribute("oneline");
	
	
	HashMap mapValue = dto.getKeyValue();
	double dAllValue = 0;
	if (mapShow != null)
	{
		for (int i=0;i<colCount;i++)
		{
				HashMap mapValueMulti = (HashMap)mapValue.get(mapShowKey[i]);
				for(int j=0;j<secSubCount;j++)
				{
					double dValue = 0;
					if(mapValueMulti != null)
					{
					  if(mapValueMulti.get(secSubKeyList.get(j))!=null && !"".equals(mapValueMulti.get(secSubKeyList.get(j))))
					  {
							dValue = Double.parseDouble((String)mapValueMulti.get(secSubKeyList.get(j)));
							
						}
					}
					allValue[i][j] = allValue[i][j]+ dValue;
					dAllValue = dAllValue + dValue;
					%>
					<td align="center" class="t12" nowrap><%=Math.round(dValue*100)/100.0%></td>
					<%
				} 
		}
	}
%>
		<td align="center" class="t12" nowrap><%=Math.round(dAllValue*100)/100.0%></td>
  </tbl:printcsstr>
</lgc:bloop>  
	<tr class="trline" > 
      	<td align="center" class="t12" nowrap>总计</td>
<%
	double dSumAll = 0;
	if (mapShow != null)
	{
		for (int i=0;i<colCount;i++)
		{
			for(int j=0;j<secSubCount;j++)
			{
				dSumAll = dSumAll + allValue[i][j];
				%>
				<td align="center" class="t12" nowrap> <%=Math.round(allValue[i][j]*100)/100.0%> </td>
				<%
			}
		}
	}
%>					
	<td align="center" class="t12" nowrap> <%=Math.round(dSumAll*100)/100.0%> </td>
  </tr>
    <tr>
    <td colspan="<%=colCount*secSubCount+2%>" class="list_foot"></td>
  </tr>
</table>		
</div>
 </rs:hasresultset>                 
<BR>

</form>  
         

      