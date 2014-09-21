<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="/WEB-INF/privilege.tld" prefix="pri" %>                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.dto.QueryDTO"%>

<%
	String type = request.getParameter("txtJobType");
%>
<SCRIPT language="JavaScript">
function check_form ()
{
    if (document.frmPost.txtCreateTime1.value != '')
    {
		if (!check_TenDate(document.frmPost.txtCreateTime1, true, "创建时间开始日期"))
			return false;
	}
	if (document.frmPost.txtCreateTime2.value != '')
	{
		if (!check_TenDate(document.frmPost.txtCreateTime2, true, "创建时间结束日期"))
			return false;
	}
	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"创建时间结束日期必须大于等于创建时间开始日期"))
		return false;

	if (document.frmPost.txtScheduleTime1.value != '')
	{
		if (!check_TenDate(document.frmPost.txtScheduleTime1, true, "执行时间开始日期"))
			return false;
	}
	if (document.frmPost.txtScheduleTime2.value != '')
	{
		if (!check_TenDate(document.frmPost.txtScheduleTime2, true, "执行时间结束日期"))
			return false;
	}
	if(!compareDate(document.frmPost.txtScheduleTime1,document.frmPost.txtScheduleTime2,"执行时间结束日期必须大于等于执行时间开始日期"))
		return false;

	if (document.frmPost.txtPerformTime1.value != '')
	{
		if (!check_TenDate(document.frmPost.txtPerformTime1, true, "实际时间开始日期"))
			return false;
	}
	if (document.frmPost.txtPerformTime2.value != '')
	{
		if (!check_TenDate(document.frmPost.txtPerformTime2, true, "实际时间结束日期"))
			return false;
	}
	if(!compareDate(document.frmPost.txtPerformTime1,document.frmPost.txtPerformTime2,"实际时间结束日期必须大于等于实际时间开始日期"))
		return false;
	
	return true;
}

function query_submit()
{
    if (check_form())
    {
    	if(document.frmPost.txtJobType.value == "AL1" || document.frmPost.txtJobType.value == "AL2")
    		document.frmPost.txtQueryType.value = "A"
    	document.frmPost.txtActionType.value = "batch";
        document.frmPost.submit();
    }
}

function view_batch_detail(strID)
{
	document.frmPost.txtQueryID.value = strID;
	document.frmPost.txtActionType.value = "result";
    	document.frmPost.action = "ownFeeBatch_query_detail.do";
    	//document.frmPost.action = "batchJob_query_detail.do";
    	document.frmPost.submit();
}

function ownFee_batch_create(strID)
{
	location.href = "batchJob_create_common.do?txtQueryID="+strID+"&txtJobType=<%=type%>";
}

function update_submit()
{
    document.frmPost.action = "batchJob_query.do";
    document.frmPost.submit();
}
</SCRIPT>

<form name="frmPost" method="post" action="ownFeeBatch_query_result.do" >
<input type="hidden" name="txtQueryType" size="20" value="P">
<input type="hidden" name="txtStatus" size="20" value="S">
<!--
<input type="hidden" name="txtStatus1" size="20" value="I">
-->
<input type="hidden" name="txtActionType" size="20" value="batch">
<input type="hidden" name="txtJobType" size="20" value="<%=type%>">
<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  	<tr>
    	<td><img src="img/list_tit.gif" width="19" height="19"></td>
    	<td class="title">批量查询操作记录查询</td>
  	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">批量查询操作记录号</td>
		<td class="list_bg1" width="33%"><input name="txtQueryID" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtQueryID" />"></td>
		<td class="list_bg2" align="right" width="17%">批量查询操作名称</td>
		<td class="list_bg1" width="33%"><input name="txtQueryName" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtQueryName" />"></td>
 	</tr>
	<tr>
   		<td class="list_bg2" align="right">批量查询操作创建时间</td>
   		<td class="list_bg1"><font size="2">
     	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" name="txtCreateTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime1" />">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
     	-
     	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" name="txtCreateTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime2" />">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	</font></td>
   		<td class="list_bg2" align="right">批量查询操作执行方式</td>
   		<td class="list_bg1"><d:selcmn name="txtScheduleType" mapName="SET_B_SCHEDULETYPE"  match="txtScheduleType"  width="23" /></td>
	</tr>
	<tr>
   		<td class="list_bg2" align="right">批量查询操作排程执行时间</td>
   		<td class="list_bg1"><font size="2">
   		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtScheduleTime1)" onblur="lostFocus(this)" name="txtScheduleTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtScheduleTime1" />">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
     	-
     	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtScheduleTime2)" onblur="lostFocus(this)" name="txtScheduleTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtScheduleTime2" />">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	</font></td>
   		<td class="list_bg2" align="right">批量查询操作实际执行时间</td>
   		<td class="list_bg1"><font size="2">
   		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPerformTime1)" onblur="lostFocus(this)" name="txtPerformTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtPerformTime1" />">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPerformTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
     	-
     	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPerformTime2)" onblur="lostFocus(this)" name="txtPerformTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtPerformTime2" />">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPerformTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	</font></td>
	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  	<td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  	<tr>
		  		<td width="22" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
		    	<td><input name="Submit" type="button" class="button" value="返&nbsp;回" onclick="javascript:update_submit()"></td>
		    	<td width="11" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
		  		<td width="20" ></td>
		  		<pri:authorized name="batchJob_query.do">
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="查&nbsp;询" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				</pri:authorized>
		  	</tr>
	  	</table></td>
	</tr>
</table> 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<rs:hasresultset>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  	<tr class="list_head">
    	<td class="list_head">批量查询操作记录号</td>
	    <td class="list_head">批量查询操作名称</td>
	    <td class="list_head">创建时间</td>
	    <td class="list_head">创建人姓名</td>
	    <td class="list_head">结果集类型</td>
	    <td class="list_head">批量查询操作状态</td>
	    <td class="list_head">操 作</td>
  	</tr>
  	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
  	<%
	    QueryDTO dto = (QueryDTO)pageContext.getAttribute("oneline");
	    
	    pageContext.setAttribute("QueryDTO",dto);
	    
	    String strOpName="";
	    strOpName=Postern.getOperatorNameByID(dto.getCreateOperatorId());
	    if(strOpName==null)
	    	strOpName="";
	    
	%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	    <td><a href="javascript:view_batch_detail('<tbl:write name="QueryDTO" property="queryId"/>')" class="link12" ><tbl:write name="QueryDTO" property="queryId"/></a></td>
	    <td><tbl:write name="QueryDTO" property="queryName"/></td>  
	    <td><tbl:writedate name="QueryDTO" property="createTime" /></td>
	    <td><%=strOpName %></td>
	    <td><d:getcmnname typeName="SET_B_QUERYTYPE" match="QueryDTO:queryType" /></td>
	    <td><d:getcmnname typeName="SET_B_QUERYSTATUS" match="QueryDTO:status" /></td>
	    <td>
	    <pri:authorized name="batchJob_create_common.do">
	    <a href="javascript:ownFee_batch_create('<tbl:write name="QueryDTO" property="queryId"/>')" class="link12" >创建任务单</a>
	    </pri:authorized>
	    </td>
	</tbl:printcsstr>
	</lgc:bloop>
	<tr>
    	<td colspan="7" class="list_foot"></td>
  	</tr>
</table>
<table  border="0" align="center" cellpadding="0" cellspacing="8">
  	<tr>
    	<td>第<span class="en_8pt"><rs:prop property="curpageno" /></span>页<span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" /><span>页</td>
    	<td>共<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录</td>
	<rs:notfirst>
	    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
	    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">首页</a></td>
	    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
	    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">上一页</a></td>
	</rs:notfirst>
	<rs:notlast>
	    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
	    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">下一页</a></td>
	    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
	    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">末页</a></td>
	</rs:notlast>
	    <td align="right">跳转到</td>
	    <td><input name="txtPage" type="text" class="page_txt"></td>
	    <td>页</td>
	    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
  	</tr>
</table>
</rs:hasresultset>
<br>
</form>