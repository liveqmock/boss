<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="/WEB-INF/privilege.tld" prefix="pri" %>                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.dto.BatchJobDTO"%>

<SCRIPT language="JavaScript">
function check_form ()
{
    if (document.frmPost.txtCreateStartDate.value != '')
    {
		if (!check_TenDate(document.frmPost.txtCreateStartDate, true, "创建时间开始日期"))
			return false;
	}
	if (document.frmPost.txtCreateEndDate.value != '')
	{
		if (!check_TenDate(document.frmPost.txtCreateEndDate, true, "创建时间结束日期"))
			return false;
	}
	if(!compareDate(document.frmPost.txtCreateStartDate,document.frmPost.txtCreateEndDate,"创建时间结束日期必须大于等于创建时间开始日期"))
		return false;

	if (document.frmPost.txtActionStartDate.value != '')
	{
		if (!check_TenDate(document.frmPost.txtActionStartDate, true, "执行时间开始日期"))
			return false;
	}
	if (document.frmPost.txtActionEndDate.value != '')
	{
		if (!check_TenDate(document.frmPost.txtActionEndDate, true, "执行时间结束日期"))
			return false;
	}
	if(!compareDate(document.frmPost.txtActionStartDate,document.frmPost.txtActionEndDate,"执行时间结束日期必须大于等于执行时间开始日期"))
		return false;
	//if(document.frmPost.txtCreateName.value != '')
	//{
	//	if (!check_Num(document.all("txtCreateName"), true, "创建人编号"))
	//		return false;
	//}
	return true;
}

function back_submit()
{
	document.frmPost.action = "batchJob_create_query.do";
	document.frmPost.submit();
}

function query_submit()
{
    if (check_form())
    {
    	document.frmPost.txtBatchJobName.value = document.frmPost.txtBatchName.value;
    	document.frmPost.txtScheduleType.value = document.frmPost.txtScheduleJobType.value;
    	document.frmPost.txtActionType.value = "batch";
        document.frmPost.action = "batchJob_query_result.do";
        document.frmPost.submit();
    }
}

function view_batchjob_detail(strID)
{
	document.frmPost.txtBatchID.value = strID;
	document.frmPost.txtActionType.value = "result";
    document.frmPost.action = "batchJob_query_detail.do";
    document.frmPost.submit();
}

function ownFee_batchjob_update(strID)
{
	var type = document.frmPost.txtJobType.value;
	location.href = "batchJob_update.do?txtBatchID="+strID+"&txtJobType="+type;
}
</SCRIPT>

<form name="frmPost" method="post" action="batchJob_query_result.do" >
<input type="hidden" name="txtActionType" size="20" value="batch">
<input type="hidden" name="txtJobType" size="20" value="<tbl:writeparam name="txtJobType"/>">
<input type="hidden" name="txtQueryID" size="20" value="<tbl:writeparam name="txtQueryID"/>">
<input type="hidden" name="txtBatchJobName" size="20" value="<tbl:writeparam name="txtBatchName"/>">
<input type="hidden" name="txtScheduleType" size="20" value="<tbl:writeparam name="txtScheduleJobType"/>">
<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  	<tr>
    	<td><img src="img/list_tit.gif" width="19" height="19"></td>
    	<td class="title">批量任务查询</td>
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
		<td class="list_bg2" align="right" width="17%">任务编号</td>
		<td class="list_bg1" width="33%"><input name="txtBatchID" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtBatchID" />"></td>
		<td class="list_bg2" align="right" width="17%">任务名称</td>
		<td class="list_bg1" width="33%"><input name="txtBatchName" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtBatchName" />"></td>
 	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">任务状态</td>
		<td class="list_bg1" width="33%"><d:selcmn name="txtBatchJobStatus" mapName="SET_B_BATCHJOBSTATUS"  match="txtBatchJobStatus"  width="23" /></td>
		<td class="list_bg2" align="right" width="17%">任务类型</td>
		<td class="list_bg1"  width="33%"><input name="txtBatchJobType" type="text" class="textgray" maxlength="200" size="25" value="<d:getcmnname typeName="SET_B_BATCHJOBTYPE" match="txtJobType"/>" readonly ></td>
	</tr>
	<tr>
   		<td class="list_bg2" align="right" width="17%">设定执行时间</td>
   		<td class="list_bg1"><font size="2">
   		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtActionStartDate)" onblur="lostFocus(this)" name="txtActionStartDate" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtActionStartDate" />">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtActionStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
     	-
     	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtActionEndDate)" onblur="lostFocus(this)" name="txtActionEndDate" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtActionEndDate" />">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtActionEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	</font></td>
   		<td class="list_bg2" align="right" width="17%">设定执行方式</td>
   		<td class="list_bg1"><d:selcmn name="txtScheduleJobType" mapName="SET_B_SCHEDULETYPE"  match="txtScheduleJobType"  width="23" /></td>
	</tr>
	<tr>
   		<td class="list_bg2" align="right" width="17%">创建时间</td>
   		<td class="list_bg1"><font size="2">
     	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateStartDate)" onblur="lostFocus(this)" name="txtCreateStartDate" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateStartDate" />">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
     	-
     	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateEndDate)" onblur="lostFocus(this)" name="txtCreateEndDate" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateEndDate" />">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	</font></td>
    	<td class="list_bg2" align="right" width="17%">操作员LoginID</td>
		<td class="list_bg1"><input name="txtCreateName" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtCreateName" />"></td>
	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  	<td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  	<tr>
		  	<pri:authorized name="batchJob_query.do"> 
		  		<td width="20" ></td>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="查&nbsp;询" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
				<td width="20" ></td>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="新&nbsp;建" onclick="javascript:back_submit()"></td>
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
    	    <td class="list_head">任务编号</td>
	    <td class="list_head">任务名称</td>
	    <td class="list_head">创建时间</td>
	    <td class="list_head">创建人姓名</td>
	    <td class="list_head">执行方式</td>
	    <td class="list_head">任务状态</td>
	    <td class="list_head">设定执行时间</td>
	    <td class="list_head">实际执行时间</td>
	    <td class="list_head">操 作</td>
  	</tr>
  	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
  	<%
	    BatchJobDTO dto = (BatchJobDTO)pageContext.getAttribute("oneline");
	    
	    pageContext.setAttribute("BatchJobDTO",dto);
	    
	    String strOpName="";
	    strOpName=Postern.getOperatorNameByID(dto.getCreateOpId());
	    if(strOpName==null)
	    	strOpName="";
	    
	%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	    <td><a href="javascript:view_batchjob_detail('<tbl:write name="BatchJobDTO" property="batchId"/>')" class="link12" ><tbl:write name="BatchJobDTO" property="batchId"/></a></td>
	    <td><tbl:write name="BatchJobDTO" property="Name"/></td>  
	    <td><tbl:writedate name="BatchJobDTO" property="createTime" /></td>
	    <td><%=strOpName %></td>
	    <td><d:getcmnname typeName="SET_B_SCHEDULETYPE" match="BatchJobDTO:ScheduleType" /></td>
	    <td><d:getcmnname typeName="SET_B_BATCHJOBSTATUS" match="BatchJobDTO:status" /></td>
	    <td><tbl:writedate name="BatchJobDTO" property="scheduleTime"/><br><tbl:writedate name="BatchJobDTO" property="scheduleTime" onlyTime="true"/></td>
	    <td><tbl:writedate name="BatchJobDTO" property="ExecuteStartTime"/><br><tbl:writedate name="BatchJobDTO" property="ExecuteStartTime" onlyTime="true"/></td>
	    <% if("N".equals(dto.getStatus())){%>
	    <td><a href="javascript:ownFee_batchjob_update('<tbl:write name="BatchJobDTO" property="batchId"/>')" class="link12" >编辑任务</a></td>
	    <%}
	       else{
	    %>
	    <td>不能操作</td>
	    <%}%>
	</tbl:printcsstr>
	</lgc:bloop>
	<tr>
    	<td colspan="9" class="list_foot"></td>
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