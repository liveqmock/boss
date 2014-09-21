<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="/WEB-INF/privilege.tld" prefix="pri" %>
<%@ page import="com.dtv.oss.util.Postern, java.util.* ,java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.util.TimestampUtility"%>

<%
    Timestamp createTime = TimestampUtility.getCurrentDateWithoutTime();
    java.sql.Date createDate = new java.sql.Date(createTime.getTime());
    int n = Postern.getValidTotalNum(WebUtil.StringToInt(request.getParameter("txtQueryID")));
    String type = request.getParameter("txtJobType");
    String name = "";
    String remark = "��ע";
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
    if("US".equals(type))
    	name = "����ͣ��";
    if("UR".equals(type))
    	name = "��������";
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
    		
    String str1 = "����";
    String str2 = "����";
%>

<SCRIPT language="JavaScript">
function check_form ()
{
    if(document.frmPost.txtBatchJobAmount.value == 0)
    {
    	alert("������ѯ��Ч��¼����Ϊ�㣬���ܴ������������񵥣�");
    	return false;
    }
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
    if (!check_TenDate(document.frmPost.txtScheduleTimeDatePart, true, "�趨ִ��ʱ��")) 
			return false;
	
	<%
      		//���������������Ϣ�����ʼ�
      		if("SM".equals(type) || "SO".equals(type) ){
    %>
    if(document.frmPost.txtScheduleType.value == "S")
    {	
    	if(!checkPlainNum(document.frmPost.txtScheduleSendNumber,true,3,"�趨���ʹ���")){
			document.frmPost.txtScheduleSendNumber.focus();
			return false;
		}
		if(!checkPlainNum(document.frmPost.txtScheduleSendTimeInterval,true,3,"�趨����ʱ����")){
			document.frmPost.txtScheduleSendTimeInterval.focus();
			return false;
		}
		if(document.frmPost.txtScheduleSendNumber.value != ""){
			if(check_Blank(document.frmPost.txtScheduleSendTimeInterval, true, "�趨����ʱ����")){
				document.frmPost.txtScheduleSendTimeInterval.focus();
				return false;
			}
		}
		if(document.frmPost.txtScheduleSendTimeInterval.value != ""){
			if(check_Blank(document.frmPost.txtScheduleSendNumber, true, "�趨���ʹ���")){
				document.frmPost.txtScheduleSendNumber.focus();
				return false;
			}
		}
    }
    <%}
    %>
    
    if(document.frmPost.txtJobType.value == "SO" && document.frmPost.txtBatchJobRemark.value == "")
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
	<%
      		//���������������Ϣ�����ʼ�
      		if("SM".equals(type) || "SO".equals(type) ){
    %>
		var trl = document.getElementById("trl");
		if(document.frmPost.txtScheduleType.value == "I"){  //����ִ��
			document.frmPost.txtScheduleSendNumber.value = "";
			document.frmPost.txtScheduleSendTimeInterval.value = "";
			trl.style.display = "none";
		}
		else if(document.frmPost.txtScheduleType.value == "S"){ //��ʱִ��
			document.frmPost.txtScheduleSendNumber.value = "";
			document.frmPost.txtScheduleSendTimeInterval.value = "";
			trl.style.display = "block";
		}
		else{
			document.frmPost.txtScheduleSendNumber.value = "";
			document.frmPost.txtScheduleSendTimeInterval.value = "";
			trl.style.display = "none";
		}
	<%}
    %>
}
function frm_submit()
{
    if (check_form())
    {
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
    	document.frmPost.txtAction.value = "create";
        document.frmPost.action = "batchJob_create_confirm.do";
        document.frmPost.submit();
    }
}
</SCRIPT>

<form name="frmPost" method="post">
<input type="hidden" name="txtJobType" size="20" value="<tbl:writeparam name="txtJobType"/>">
<input type="hidden" name="txtJobStatus" size="20" value="N">
<input type="hidden" name="txtJobReferType" size="20" value="Q">
<input type="hidden" name="txtQueryID" size="20" value="<tbl:writeparam name="txtQueryID"/>">
<input type="hidden" name="txtAction" size="20" value="">
<input type="hidden" name="func_flag" value="1101">
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
<br>
<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">��������*</td>
		<td class="list_bg1" width="33%"><input name="txtBatchJobName" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtBatchJobName" />"></td>
		<td class="list_bg2" align="right" width="17%">��������</td>
		<td class="list_bg1" width="33%"><input name="txtBatchJobType" type="text" class="textgray" maxlength="200" size="25" value="<d:getcmnname typeName="SET_B_BATCHJOBTYPE" match="txtJobType"/>" readonly></td>
 	</tr>
	<tr>
		<td class="list_bg2" align="right">����״̬</td>
		<td class="list_bg1"><input name="txtBatchJobStatus" type="text" class="textgray" maxlength="200" size="25" value="<d:getcmnname typeName="SET_B_BATCHJOBSTATUS" match="N"/>" readonly></td>
   		<td class="list_bg2" align="right">����ʱ��</td>
   		<td class="list_bg1"><input name="txtCreateDate" type="text" class="textgray" maxlength="10" size="25" value="<%=createDate%>" readonly></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">������Դ</td>
		<td class="list_bg1"><input name="txtBatchJobReferType" type="text" class="textgray" maxlength="200" size="25" value="<d:getcmnname typeName="SET_B_BATCHJOBREFERTYPE" match="Q"/>" readonly></td>
		<td class="list_bg2" align="right">��ԴID</td>
		<td class="list_bg1"><input name="txtBatchJobReferID" type="text" class="textgray" maxlength="200" size="25" value="<tbl:writeparam name="txtQueryID"/>" readonly></td>
 	</tr>	
	<tr>
		<td class="list_bg2" align="right">ִ�з�ʽ*</td>
   		<td class="list_bg1"><d:selcmn name="txtScheduleType" mapName="SET_B_SCHEDULETYPE"  match="txtScheduleType"  width="23" onclick="javascript:getScheduleTime()" /></td>
   		<td class="list_bg2" align="right">�趨ִ��ʱ��</td>
   		<td class="list_bg1">
   		<!--
   		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtScheduleTime)" onblur="lostFocus(this)" name="txtScheduleTime" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtScheduleTime" />">   		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
   		-->
   		<font size="2">
   		<d:datetime name="txtScheduleTime" size="10" match="txtScheduleTime" includeHour="true" includeMinute="true" includeSecond="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTimeDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />  
	   </font>
   		</td>
	</tr>
	<!--
	<tr>
		<td class="list_bg2">��ע</td>
		<td class="list_bg1" colspan="3"><input name="txtBatchJobRemark" type="text" class="text" maxlength="200" size="83%" value="<tbl:writeparam name="txtBatchJobRemark" />"></td>
	</tr>
	-->
	<%
      		//���������������Ϣ�����ʼ�
      		if("SM".equals(type) || "SO".equals(type) ){
    %>
	<tr id="trl" style="display:none">
		<td class="list_bg2" align="right">�趨���ʹ���</td>
		<td class="list_bg1"><input name="txtScheduleSendNumber" type="text" maxlength="200" size="25" value="" ></td>
		<td class="list_bg2" align="right">�趨����ʱ����</td>
		<td class="list_bg1"><input name="txtScheduleSendTimeInterval" type="text" maxlength="200" size="25" value="" >����</td>
	</tr>
	<%}
    %>
	<tr>
		<td class="list_bg2" align="right">�ܼ�¼��</td>
		<td class="list_bg1"><input name="txtBatchJobAmount" type="text" class="textgray" maxlength="200" size="25" value="<%=n%>" readonly></td>
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
		  		<bk:canback url="ownFeeBatch_query_result.do">
				<td width="20" ></td>  
				<td><img src="img/button2_r.gif" width="22" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="<bk:backurl property="ownFeeBatch_query_result.do"/>" class="btn12">��һ��</a></td>
				<td><img src="img/button2_l.gif" width="13" height="20"></td>
				</bk:canback>
				<td width="20" ></td>
			<pri:authorized name="batchJob_create_common.do">		
		    	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    	<td><input name="Submit" type="button" class="button" value="<%=name%>" onclick="javascript:frm_submit()"></td>
		    	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		    	</pri:authorized>
		  	</tr>
	  	</table></td>
	</tr>
</table>
</form>