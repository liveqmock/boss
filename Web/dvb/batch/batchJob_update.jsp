<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="/WEB-INF/privilege.tld" prefix="pri" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.dto.BatchJobDTO"%>

<%
    BatchJobDTO dto = Postern.getBatchJobDTOByBatchID(WebUtil.StringToInt(request.getParameter("txtBatchID")));
    pageContext.setAttribute("query", dto);
    if( dto.getScheduleTime()!=null)
    	pageContext.setAttribute("txtScheduleTime", dto.getScheduleTime());
    String description = dto.getDescription();
    if(description == null)
    	description = "";
    String batchid = request.getParameter("txtBatchID");
    String type = request.getParameter("txtJobType");
    String name = "";
    String remark = "备注";
    description=description.replaceAll("\r","\\\\r");
    description=description.replaceAll("\n","\\\\n");
    
    if("AL1".equals(type))
    	name = "一次欠费升级";
    if("AL2".equals(type))
    	name = "二次欠费升级";
    if("AS".equals(type))
    	name = "批量欠费暂停";
    if("PS".equals(type))
    	name = "批量暂停";
    if("PC".equals(type))
    	name = "批量取消";
    if("SM".equals(type))
    {
    	name = "批量发送邮件";
    	remark = "发送内容";
    }
    if("SO".equals(type))
    {
    	name = "批量发送OSD";
    	remark = "发送内容";
    }
    if("PR".equals(type))
    	name = "批量恢复";

    String str1 = "维护";
    String str2 = "任务单";
%>

<SCRIPT language="JavaScript">
function cancel_submit()
{
	if(document.frmPost.txtJobStatus.value != "N")
	{
		alert("只有等待执行的任务单可以被取消！");
		return false;
	}
	if(!confirm('你确认要取消该批量任务单吗?')){
			return; 
		}
    document.frmPost.txtAction.value = "cancel";
    document.frmPost.action = "batchJob_update_confirm.do";
    document.frmPost.submit();
}
function update_submit()
{
 
	if(document.frmPost.txtJobStatus.value != "N")
	{
		alert("只有等待执行的任务单可以被修改！");
		return false;
	}
    if (check_form())
    {
    if(!confirm('你确认要修改该批量任务单吗?')){
			return; 
		} 
      //填充执行时间
      document.frmPost.txtScheduleTime.value="";
  	if(document.frmPost.txtScheduleTimeDatePart.value!=""){
  		document.frmPost.txtScheduleTime.value=document.frmPost.txtScheduleTimeDatePart.value;
  		if(document.frmPost.txtScheduleTimeHourPart.value!="")
  			document.frmPost.txtScheduleTime.value=document.frmPost.txtScheduleTime.value + " " + document.frmPost.txtScheduleTimeHourPart.value;
  		else
  			document.frmPost.txtScheduleTime.value=document.frmPost.txtScheduleTime.value + " 00";
  		if(document.frmPost.txtScheduleTimeMinutePart.value!="")
  			document.frmPost.txtScheduleTime.value=document.frmPost.txtScheduleTime.value + ":" + document.frmPost.txtScheduleTimeMinutePart.value;
  		else
  			document.frmPost.txtScheduleTime.value=document.frmPost.txtScheduleTime.value + ":00";
  		if(document.frmPost.txtScheduleTimeSecondPart.value!="")
  			document.frmPost.txtScheduleTime.value=document.frmPost.txtScheduleTime.value + ":" + document.frmPost.txtScheduleTimeSecondPart.value;
  		else
  			document.frmPost.txtScheduleTime.value=document.frmPost.txtScheduleTime.value + ":00";
  	}
    	document.frmPost.txtAction.value = "modify";
        document.frmPost.action = "batchJob_update_confirm.do";
        document.frmPost.submit();
    }
}
function check_form ()
{
    if(document.frmPost.txtBatchJobName.value == "")
    {
    	alert("请输入任务名称！");
    	document.frmPost.txtBatchJobName.focus();
    	return false;
    }
    if(document.frmPost.txtScheduleType.value == "")
    {
    	alert("请输入设定执行方式！");
    	document.frmPost.txtScheduleType.focus();
    	return false;
    }
    if(document.frmPost.txtScheduleType.value == "S" && document.frmPost.txtScheduleTimeDatePart.value == "")
    {
    	alert("请输入设定执行时间！");
    	document.frmPost.txtScheduleTimeDatePart.focus();
    	return false;
    }
     if(document.frmPost.txtScheduleType.value == "I" && document.frmPost.txtScheduleTimeDatePart.value != "")
    {
    	alert("该种类型不需要输入设定执行时间！");
    	document.frmPost.txtScheduleTimeDatePart.focus();
    	return false;
    }
     if (!check_TenDate(document.frmPost.txtScheduleTimeDatePart, true, "预定执行时间")) 
			return false;
			
    if(document.frmPost.txtJobType.value == "S0" && document.frmPost.txtBatchJobRemark.value == "")
    {
    	alert("请填写发送内容！");
    	document.frmPost.txtBatchJobRemark.focus();
    	return false;
    }
    if(document.frmPost.txtJobType.value == "SO" && document.frmPost.txtBatchJobRemark.value.length>90){
    	if(!confirm("您输入的发送内容过长，部分数据可能会丢失，请确信要提交吗？"))
    		return false;
    }
    if(document.frmPost.txtJobType.value == "SM" && document.frmPost.txtBatchJobRemark.value == "")
    {
    	alert("请填写发送内容！");
    	document.frmPost.txtBatchJobRemark.focus();
    	return false;
    }
    if(document.frmPost.txtJobType.value == "SM" && document.frmPost.txtBatchJobRemark.value.length>95){
    	if(!confirm("您输入的邮件内容过长，部分数据可能会丢失，请确信要提交吗？"))
    		return false;
    }
    
    return true;
}
function getScheduleTime()
{
	if(document.frmPost.txtScheduleType.value == "I")
		document.frmPost.txtScheduleTime.value = "";
}
function initlocal()
{
	document.frmPost.txtBatchJobRemark.value = "<%=description%>";
}
</SCRIPT>

<body onload="initlocal()">
<form name="frmPost" method="post" action="">
<input type="hidden" name="txtAction" size="20" value="">
<input type="hidden" name="txtBatchID" size="20" value="<%=batchid%>">
<input type="hidden" name="txtJobStatus" size="20" value="<tbl:write name="query" property="status"/>">
<input type="hidden" name="txtJobReferType" size="20" value="<tbl:write name="query" property="referType"/>">
<input type="hidden" name="txtJobType" size="20" value="<tbl:writeparam name="txtJobType"/>">
<input type="hidden" name="txtDtLastmod" size="20" value="<tbl:write name="query" property="dtLastmod"/>">
<input type="hidden" name="func_flag" value="1102">
<input name="txtScheduleTime" type="hidden" value="">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  	<tr>
    	<td><img src="img/list_tit.gif" width="19" height="19"></td>
    	<td class="title"><%=str1+name+str2%></td>
  	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
	<tr>
		<td class="list_bg2" align="left" width="17%">任务名称</td>
		<td class="list_bg1" width="33%"><input name="txtBatchJobName" type="text" class="text" maxlength="200" size="25" value="<tbl:write name="query" property="Name"/>"></td>
		<td class="list_bg2" align="left" width="17%">任务类型</td>
		<td class="list_bg1" width="33%"><input name="txtBatchJobType" type="text" class="textgray" maxlength="200" size="25" value="<d:getcmnname typeName="SET_B_BATCHJOBTYPE" match="txtJobType"/>" readonly></td>
 	</tr>
	<tr>
		<td class="list_bg2">任务状态</td>
		<td class="list_bg1"><input name="txtBatchJobStatus" type="text" class="textgray" maxlength="200" size="25" value="<d:getcmnname typeName="SET_B_BATCHJOBSTATUS" match="query:status"/>" readonly></td>
   		<td class="list_bg2">创建时间</td>
   		<td class="list_bg1"><input name="txtCreateDate" type="text" class="textgray" maxlength="10" size="25" value="<tbl:writedate name="query" property="CreateTime" includeTime="false"/>" readonly></td>
	</tr>
	<tr>
		<td class="list_bg2">创建来源</td>
		<td class="list_bg1"><input name="txtBatchJobReferType" type="text" class="textgray" maxlength="200" size="25" value="<d:getcmnname typeName="SET_B_BATCHJOBREFERTYPE" match="query:referType"/>" readonly></td>
		<td class="list_bg2">创建来源号</td>
		<td class="list_bg1"><input name="txtBatchJobReferID" type="textgray" class="textgray" maxlength="200" size="25" value="<tbl:write name="query" property="referId"/>" readonly></td>
 	</tr>
	<tr>
		<td class="list_bg2">设定执行方式</td>
   		<td class="list_bg1"><d:selcmn name="txtScheduleType" mapName="SET_B_SCHEDULETYPE"  match="query:ScheduleType"  width="23" onclick="javascript:getScheduleTime()" /></td>
   		<td class="list_bg2">设定执行时间</td>
   		<td class="list_bg1">
   		<!--
   		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtScheduleTime)" onblur="lostFocus(this)" name="txtScheduleTime" type="text" class="text" maxlength="10" size="25" value="<tbl:writedate name="query" property="ScheduleTime" includeTime="false"/>">   		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
   		-->
   		<font size="2">
   		<d:datetime name="txtScheduleTime" size="10" match="txtScheduleTime" includeHour="true" includeMinute="true" includeSecond="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTimeDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />  
	   </font>
   		</td>
	</tr>
	<!--
	<tr>
		<td class="list_bg2">备注</td>
		<td class="list_bg1" colspan="3"><input name="txtBatchJobRemark" type="text" class="text" maxlength="200" size="83%" value="<tbl:write name="query" property="description"/>"></td>
	</tr>
	-->
	<tr>
		<td class="list_bg2">批处理记录总数</td>
		<td class="list_bg1"><input name="txtBatchJobAmount" type="text" class="textgray" maxlength="200" size="25" value="<tbl:write name="query" property="totoalRecordNo"/>" readonly></td>
		<td class="list_bg1" width="50%" colspan="2"></td>
	</tr>
	
	<%
      		//如果是批量发送消息或者邮件
      		if("SM".equals(type) || "SO".equals(type) ){
      	%>
	<tr>
      		<td class="list_bg2" align="right"><%=remark%></td>
      		<td class="list_bg1" colspan=3>
      		<font color="red">
      			定制发送：{$CustomerName}-客户姓名；{$AccountBalance}-帐户余额；{$InvoiceAmount}-总欠费金额。
      			
      			<%
      				if("SM".equals(type)){
      			%>
      			<br>邮件主题和正文用回车隔开，主题最大长度为15个字符，正文最大长度为80个字符。
      			<%
      				}
      				else{
      			%>
      			<br>发送内容最大长度为90个字符。
      			<%	}
      			%>
      		</font><br>
      		<textarea id=content name="txtBatchJobRemark" cols="81" rows="9" value="<tbl:writeparam name="txtBatchJobRemark" />"></textarea>
      		</td>
    	</tr>
    	<%	}
    		else{
    	%>
    	<tr>
      		<td class="list_bg2" align="right"><%=remark%></td>
      		<td class="list_bg1" colspan=3>
      		<textarea id=content name="txtBatchJobRemark" cols="81" rows="2" value="<tbl:writeparam name="txtBatchJobRemark" />"></textarea>
      		</td>
    	</tr>
    	<%
    		}
    	%>
    	
</table>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  	<td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  	<tr>
		  		<bk:canback url="batchJob_query_result.do">
				<td width="20" ></td>  
				<td><img src="img/button2_r.gif" width="22" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="<bk:backurl property="batchJob_query_result.do"/>" class="btn12">上一步</a></td>
				<td><img src="img/button2_l.gif" width="13" height="20"></td>
				</bk:canback>
				<td width="20" ></td>
			<pri:authorized name="batchJob_update_confirm.do">		
		    	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    	<td><input name="Submit" type="button" class="button" value="修改" onclick="javascript:update_submit()"></td>
		    	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		    	<td width="20" ></td>	
		    	</pri:authorized>	
		    	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    	<td><input name="Submit" type="button" class="button" value="取消" onclick="javascript:cancel_submit()"></td>
		    	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  	</tr>
	  	</table></td>
	</tr>
</table>
</form>
</body>