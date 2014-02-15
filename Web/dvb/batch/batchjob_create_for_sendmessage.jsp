<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="/WEB-INF/privilege.tld" prefix="pri" %>
<%@ page import="com.dtv.oss.util.Postern, java.util.* ,java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.util.TimestampUtility"%>
<%
String type = request.getParameter("txtJobType");
%>
<SCRIPT language="JavaScript">
<!--
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
    if (!check_TenDate(document.frmPost.txtScheduleTimeDatePart, true, "设定执行时间")) 
			return false;

    if(document.frmPost.txtScheduleType.value == "S")
    {	
    	if(!checkPlainNum(document.frmPost.txtScheduleSendNumber,true,3,"设定发送次数")){
			document.frmPost.txtScheduleSendNumber.focus();
			return false;
		}
		if(!checkPlainNum(document.frmPost.txtScheduleSendTimeInterval,true,3,"设定发送时间间隔")){
			document.frmPost.txtScheduleSendTimeInterval.focus();
			return false;
		}
		if(document.frmPost.txtScheduleSendNumber.value != ""){
			if(check_Blank(document.frmPost.txtScheduleSendTimeInterval, true, "设定发送时间间隔")){
				document.frmPost.txtScheduleSendTimeInterval.focus();
				return false;
			}
		}
		if(document.frmPost.txtScheduleSendTimeInterval.value != ""){
			if(check_Blank(document.frmPost.txtScheduleSendNumber, true, "设定发送次数")){
				document.frmPost.txtScheduleSendNumber.focus();
				return false;
			}
		}
    }
    
    if(document.frmPost.txtJobType.value == "SO" && document.frmPost.txtBatchJobRemark.value == "")
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
    /*
		var trl = document.getElementById("trl");
		if(document.frmPost.txtScheduleType.value == "I"){  //立即执行
			document.frmPost.txtScheduleSendNumber.value = "";
			document.frmPost.txtScheduleSendTimeInterval.value = "";
			trl.style.display = "none";
		}
		else if(document.frmPost.txtScheduleType.value == "S"){ //定时执行
			document.frmPost.txtScheduleSendNumber.value = "";
			document.frmPost.txtScheduleSendTimeInterval.value = "";
			trl.style.display = "block";
		}
		else{
			document.frmPost.txtScheduleSendNumber.value = "";
			document.frmPost.txtScheduleSendTimeInterval.value = "";
			trl.style.display = "none";
		}
	*/
}
function frm_submit()
{
    if (check_form())
    {
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
        document.frmPost.submit();
    }
}
//-->
</SCRIPT>

<form name="frmPost" method="post" action="batchJob_for_send_message_create.do">
<input type="hidden" name="txtJobType" size="20" value="<tbl:writeparam name="txtJobType" />">
<input type="hidden" name="txtJobStatus" size="20" value="N">
<input type="hidden" name="txtBatchJobReferID" size="20" value="<tbl:writeparam name="txtBatchNo" />">
<input type="hidden" name="txtJobReferType" size="20" value="U">
<input name="txtScheduleTime" type="hidden" value="">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  	<tr>
    	<td><img src="img/list_tit.gif" width="19" height="19"></td>
    	<td class="title">批量发送消息</td>
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
		<td class="list_bg2" align="right" width="17%">任务名称*</td>
		<td class="list_bg1" width="33%"><input name="txtBatchJobName" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtBatchJobName" />"></td>
		<td class="list_bg2" align="right" width="17%">导入文件批号</td>
		<td class="list_bg1" width="33%"><input name="txtBatchNo" type="text" class="textgray" maxlength="200" size="25" value="<tbl:writeparam name="txtBatchNo" />" readonly></td>
 	</tr>
	<tr>
		<td class="list_bg2" align="right">执行方式*</td>
   		<td class="list_bg1"><d:selcmn name="txtScheduleType" mapName="SET_B_SCHEDULETYPE"  match="txtScheduleType"  width="23" onclick="javascript:getScheduleTime()" /></td>
   		<td class="list_bg2" align="right">设定执行时间</td>
   		<td class="list_bg1">
   		<font size="2">
   		<d:datetime name="txtScheduleTime" size="10" match="txtScheduleTime" includeHour="true" includeMinute="true" includeSecond="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTimeDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />  
	   </font>
   		</td>
	</tr>
	<tr id="trl" style="display:none">
		<td class="list_bg2" align="right">设定发送次数</td>
		<td class="list_bg1"><input name="txtScheduleSendNumber" type="text" maxlength="200" size="25" value="" ></td>
		<td class="list_bg2" align="right">设定发送时间间隔</td>
		<td class="list_bg1"><input name="txtScheduleSendTimeInterval" type="text" maxlength="200" size="25" value="" >分钟</td>
	</tr>
	<tr>
      		<td class="list_bg2" align="right">发送内容</td>
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
    	
</table>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  	<td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  	<tr>
		  		<bk:canback url="batch_send_message_query.do">
				<td width="20" ></td>  
				<td><img src="img/button2_r.gif" width="22" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="<bk:backurl property="batch_send_message_query.do"/>" class="btn12">上一步</a></td>
				<td><img src="img/button2_l.gif" width="13" height="20"></td>
				</bk:canback>
				<td width="20" ></td>
			    <pri:authorized name="batchJob_for_send_message_create.do">		
		    	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    	<td><input name="Submit" type="button" class="button" value="发送" onclick="javascript:frm_submit()"></td>
		    	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		    	</pri:authorized>
		  	</tr>
	  	</table></td>
	</tr>
</table>
</form>
