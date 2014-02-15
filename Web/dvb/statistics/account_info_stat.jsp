<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.stat.CommonStatDTO"%>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>

<script language=javascript>
function query_submit(){
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
	if (check_Blank(document.frmPost.txtDistrictID, true, "区域"))
		return false;
	
	return true;
}
</script>

<%!
	public CommonStatDTO getCommonStatDTOByDTOID(String dtoID,Collection col){
		if(dtoID==null || "".equals(dtoID) || col==null)
			return null;
		Iterator itCol=col.iterator();
		while(itCol.hasNext()){
			CommonStatDTO dto=(CommonStatDTO)itCol.next();
			if(dtoID.equals(dto.getId()))
				return dto;
		}
		return null;
	}

%>
<form name="frmPost" method="post" action="account_info_stat.do">

<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">

<input type="hidden" name="txtActionType" size="20" value="all">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">帐户情况统计</td>
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
    <td class="list_bg2"><div align="right">区域*</div></td>
    <td class="list_bg1">
    <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtCountyDesc" />" class="text">
    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
   
    </td>
    <td class="list_bg2"><div align="right">客户类型</div></td>
    <td class="list_bg1"><d:selcmn  name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="23" /></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">帐户状态</div></td>
    <td class="list_bg1"><d:selcmn  name="txtStatus" mapName="SET_F_ACCOUNTSTATUS" match="txtStatus" width="23" /> </td>
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
<rs:hasresultset>


<%
	QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)request.getAttribute("ResponseQueryResult");
   	Collection amountCommonStatDTOList =(Collection)RepCmd.getExtraPayLoad();
%>

<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr class="list_head">
		<td class="list_head"><div align="center">付费类型</div></td>
<%
	
	Map mapShow = new HashMap();
	//添置类型
	mapShow.put("T","二次欠费（数量/金额）");
	mapShow.put("O","一次欠费（数量/金额）");
	mapShow.put("N","正常（数量/金额）");
	mapShow.put("B","串配状态");
	
	long allValue[];
	double allCount[];
	
	int colCount = 0;
	if (!mapShow.isEmpty())
	{
		Iterator itKey = mapShow.keySet().iterator();
		colCount = mapShow.size();
		allValue = new long[colCount];
		allCount = new double[colCount]; 
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
%>
			<td class="list_head" colspan="2"><div align="center"><%=mapShow.get(strKey)%></div></td>
<%
		}
	}
	else{
		allValue = new long[1];
		allCount = new double[1];
	}
%>
			<td class="list_head" colspan="2" ><div align="center">总计(数量/金额)</div></td>
		</tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	<td align="center" class="t12"><tbl:write name="oneline" property="name" /></td>
<%
	CommonStatDTO dto = (CommonStatDTO)pageContext.getAttribute("oneline");
	CommonStatDTO amountDTO=getCommonStatDTOByDTOID(dto.getId(),amountCommonStatDTOList);
	
	if(dto.getId().endsWith("C"))
		out.write("<td align=\"center\" class=\"t12\">串配取消</td>");
	else if(dto.getId().endsWith("S"))
		out.write("<td align=\"center\" class=\"t12\">串配成功</td>");
	else if(dto.getId().endsWith("U"))
		out.write("<td align=\"center\" class=\"t12\">串配不成功</td>");
	else if(dto.getId().endsWith("W"))
		out.write("<td align=\"center\" class=\"t12\">待串配</td>");	
	else
		out.write("<td align=\"center\" class=\"t12\"></td>");	
	
	HashMap mapValue = dto.getKeyValue();
	HashMap mapCount=null;
	
	if(amountDTO!=null)
		mapCount=amountDTO.getKeyValue();
		
	long dAllValue = 0;
	
	double dAllCount =0;
	
	if (!mapShow.isEmpty())
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
				long dValue = 0;
				double dCount = 0;
				if((mapValue.get(strKey) != null) && (mapValue.get(strKey) != ""))
				{
					dValue = Math.round(Double.parseDouble((String)mapValue.get(strKey))*10)/10;
				}
				if(mapCount!=null && mapCount.get(strKey)!=null && mapCount.get(strKey) !="")
					dAllCount = Math.round(Double.parseDouble((String)mapCount.get(strKey))*10)/10;
					
				allValue[i] = allValue[i]+ dValue;
				dAllValue = dAllValue + dValue;
				
				allCount[i] = allCount[i]+ dCount;
				dAllCount = dAllCount + dCount;
%>
		<td align="center" class="t12"><%=dValue%></td>
		<td align="center" class="t12"><%=dCount%></td>
<%
		}
	}
%>
		<td align="center" class="t12"><%=dAllValue%></td>
		<td align="center" class="t12"><%=dAllCount%></td>
  </tbl:printcsstr>
</lgc:bloop>  
	<tr class="trline" > 
      	<td align="center" class="t12">总计(数量/金额)</td>
<%
	long dSumAll = 0;
	double dSumCount=0;
	
	if (!mapShow.isEmpty())
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<mapShow.size();i++)
		{
			long dValue = Math.round(allValue[i]*100)/100;
			dSumAll = dSumAll + dValue;
			
			double dCount = Math.round(allCount[i]*100)/100;
			dSumCount = dSumCount + dCount;
			
			if(i==3){
%>
		<td align="center" class="t12">  </td>
		<td align="center" class="t12">  </td>
<%
		}
		else {
%>
		<td align="center" class="t12"> <%=dValue%> </td>
		<td align="center" class="t12"> <%=dCount%> </td>
<%
		}
	    }
	}
%>					
	<td align="center" class="t12"> <%=dSumAll%> </td>
	<td align="center" class="t12"> <%=dSumCount%> </td>
  </tr>
    <tr>
    <td colspan="<%=colCount*2+3%>" class="list_foot"></td>
  </tr>
</table>		
</rs:hasresultset>
</form>  
         

      