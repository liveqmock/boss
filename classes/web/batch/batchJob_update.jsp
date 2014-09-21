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
    String remark = "��ע";
    description=description.replaceAll("\r","\\\\r");
    description=description.replaceAll("\n","\\\\n");
    
    if("AL1".equals(type))
    	name = "һ��Ƿ������";
    if("AL2".equals(type))
    	name = "����Ƿ������";
    if("AS".equals(type))
    	name = "����Ƿ����ͣ";
    if("PS".equals(type))
    	name = "������ͣ";
    if("PC".equals(type))
    	name = "����ȡ��";
    if("SM".equals(type))
    {
    	name = "���������ʼ�";
    	remark = "��������";
    }
    if("SO".equals(type))
    {
    	name = "��������OSD";
    	remark = "��������";
    }
    if("PR".equals(type))
    	name = "�����ָ�";

    String str1 = "ά��";
    String str2 = "����";
%>

<SCRIPT language="JavaScript">
function cancel_submit()
{
	if(document.frmPost.txtJobStatus.value != "N")
	{
		alert("ֻ�еȴ�ִ�е����񵥿��Ա�ȡ����");
		return false;
	}
	if(!confirm('��ȷ��Ҫȡ��������������?')){
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
		alert("ֻ�еȴ�ִ�е����񵥿��Ա��޸ģ�");
		return false;
	}
    if (check_form())
    {
    if(!confirm('��ȷ��Ҫ�޸ĸ�����������?')){
			return; 
		} 
      //���ִ��ʱ��
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
    	alert("�������������ƣ�");
    	document.frmPost.txtBatchJobName.focus();
    	return false;
    }
    if(document.frmPost.txtScheduleType.value == "")
    {
    	alert("�������趨ִ�з�ʽ��");
    	document.frmPost.txtScheduleType.focus();
    	return false;
    }
    if(document.frmPost.txtScheduleType.value == "S" && document.frmPost.txtScheduleTimeDatePart.value == "")
    {
    	alert("�������趨ִ��ʱ�䣡");
    	document.frmPost.txtScheduleTimeDatePart.focus();
    	return false;
    }
     if(document.frmPost.txtScheduleType.value == "I" && document.frmPost.txtScheduleTimeDatePart.value != "")
    {
    	alert("�������Ͳ���Ҫ�����趨ִ��ʱ�䣡");
    	document.frmPost.txtScheduleTimeDatePart.focus();
    	return false;
    }
     if (!check_TenDate(document.frmPost.txtScheduleTimeDatePart, true, "Ԥ��ִ��ʱ��")) 
			return false;
			
    if(document.frmPost.txtJobType.value == "S0" && document.frmPost.txtBatchJobRemark.value == "")
    {
    	alert("����д�������ݣ�");
    	document.frmPost.txtBatchJobRemark.focus();
    	return false;
    }
    if(document.frmPost.txtJobType.value == "SO" && document.frmPost.txtBatchJobRemark.value.length>90){
    	if(!confirm("������ķ������ݹ������������ݿ��ܻᶪʧ����ȷ��Ҫ�ύ��"))
    		return false;
    }
    if(document.frmPost.txtJobType.value == "SM" && document.frmPost.txtBatchJobRemark.value == "")
    {
    	alert("����д�������ݣ�");
    	document.frmPost.txtBatchJobRemark.focus();
    	return false;
    }
    if(document.frmPost.txtJobType.value == "SM" && document.frmPost.txtBatchJobRemark.value.length>95){
    	if(!confirm("��������ʼ����ݹ������������ݿ��ܻᶪʧ����ȷ��Ҫ�ύ��"))
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
		<td class="list_bg2" align="left" width="17%">��������</td>
		<td class="list_bg1" width="33%"><input name="txtBatchJobName" type="text" class="text" maxlength="200" size="25" value="<tbl:write name="query" property="Name"/>"></td>
		<td class="list_bg2" align="left" width="17%">��������</td>
		<td class="list_bg1" width="33%"><input name="txtBatchJobType" type="text" class="textgray" maxlength="200" size="25" value="<d:getcmnname typeName="SET_B_BATCHJOBTYPE" match="txtJobType"/>" readonly></td>
 	</tr>
	<tr>
		<td class="list_bg2">����״̬</td>
		<td class="list_bg1"><input name="txtBatchJobStatus" type="text" class="textgray" maxlength="200" size="25" value="<d:getcmnname typeName="SET_B_BATCHJOBSTATUS" match="query:status"/>" readonly></td>
   		<td class="list_bg2">����ʱ��</td>
   		<td class="list_bg1"><input name="txtCreateDate" type="text" class="textgray" maxlength="10" size="25" value="<tbl:writedate name="query" property="CreateTime" includeTime="false"/>" readonly></td>
	</tr>
	<tr>
		<td class="list_bg2">������Դ</td>
		<td class="list_bg1"><input name="txtBatchJobReferType" type="text" class="textgray" maxlength="200" size="25" value="<d:getcmnname typeName="SET_B_BATCHJOBREFERTYPE" match="query:referType"/>" readonly></td>
		<td class="list_bg2">������Դ��</td>
		<td class="list_bg1"><input name="txtBatchJobReferID" type="textgray" class="textgray" maxlength="200" size="25" value="<tbl:write name="query" property="referId"/>" readonly></td>
 	</tr>
	<tr>
		<td class="list_bg2">�趨ִ�з�ʽ</td>
   		<td class="list_bg1"><d:selcmn name="txtScheduleType" mapName="SET_B_SCHEDULETYPE"  match="query:ScheduleType"  width="23" onclick="javascript:getScheduleTime()" /></td>
   		<td class="list_bg2">�趨ִ��ʱ��</td>
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
		<td class="list_bg2">��ע</td>
		<td class="list_bg1" colspan="3"><input name="txtBatchJobRemark" type="text" class="text" maxlength="200" size="83%" value="<tbl:write name="query" property="description"/>"></td>
	</tr>
	-->
	<tr>
		<td class="list_bg2">�������¼����</td>
		<td class="list_bg1"><input name="txtBatchJobAmount" type="text" class="textgray" maxlength="200" size="25" value="<tbl:write name="query" property="totoalRecordNo"/>" readonly></td>
		<td class="list_bg1" width="50%" colspan="2"></td>
	</tr>
	
	<%
      		//���������������Ϣ�����ʼ�
      		if("SM".equals(type) || "SO".equals(type) ){
      	%>
	<tr>
      		<td class="list_bg2" align="right"><%=remark%></td>
      		<td class="list_bg1" colspan=3>
      		<font color="red">
      			���Ʒ��ͣ�{$CustomerName}-�ͻ�������{$AccountBalance}-�ʻ���{$InvoiceAmount}-��Ƿ�ѽ�
      			
      			<%
      				if("SM".equals(type)){
      			%>
      			<br>�ʼ�����������ûس�������������󳤶�Ϊ15���ַ���������󳤶�Ϊ80���ַ���
      			<%
      				}
      				else{
      			%>
      			<br>����������󳤶�Ϊ90���ַ���
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
				<a href="<bk:backurl property="batchJob_query_result.do"/>" class="btn12">��һ��</a></td>
				<td><img src="img/button2_l.gif" width="13" height="20"></td>
				</bk:canback>
				<td width="20" ></td>
			<pri:authorized name="batchJob_update_confirm.do">		
		    	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    	<td><input name="Submit" type="button" class="button" value="�޸�" onclick="javascript:update_submit()"></td>
		    	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		    	<td width="20" ></td>	
		    	</pri:authorized>	
		    	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    	<td><input name="Submit" type="button" class="button" value="ȡ��" onclick="javascript:cancel_submit()"></td>
		    	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  	</tr>
	  	</table></td>
	</tr>
</table>
</form>
</body>