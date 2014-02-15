<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
                
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>
<%
  String txtTemplateFlag = request.getParameter("txtTemplateFlag");
  String title = "������ѯ������Ϣ";
  if(CommonConstDefinition.YESNOFLAG_YES.equals(txtTemplateFlag))
    title = "������ѯģ����Ϣ";
  else
  	txtTemplateFlag = CommonConstDefinition.YESNOFLAG_NO;
%>
<Script language=JavaScript>
<!--

//����˵����typeΪ���ͣ�typeNameΪ���֣�����value���֣���subTypeValueΪ�ò�����ֵ
function open_select(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;
		
	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250,screenX=340,screenY=270";

	window.open(param,"",windowFeatures);
}

function check_frm()
{
    if (check_Blank(document.frmPost.txtQueryType, true, "���������"))
	return false;

    if (check_Blank(document.frmPost.txtQueryName, true, "��ѯ��������"))
	return false;

    if (check_Blank(document.frmPost.txtScheduleType, true, "ִ�з�ʽ"))
	return false;	
     
     if(document.frmPost.txtScheduleType.options[document.frmPost.txtScheduleType.selectedIndex].text=="��ʱִ��" &&
     	document.frmPost.txtScheduleTimeDatePart.value==""){
     	alert("����������Ҫ����ִ��ʱ�䣡");
     	return false;
     }
     	
     if(document.frmPost.txtScheduleType.options[document.frmPost.txtScheduleType.selectedIndex].text!="��ʱִ��" &&
     	document.frmPost.txtScheduleTimeDatePart.value!=""){
     	alert("�������Ͳ���Ҫ����ִ��ʱ�䣡");
     	return false;
     }
     	if (!check_TenDate(document.frmPost.txtScheduleTimeDatePart, true, "�趨ִ��ʱ��")) 
			return false;
			//if (!check_TenDate(document.frmPost.txtPerformTime2, true, "ʵ��ִ�н�ֹʱ��")) 
			//return false;
     return true;
}

function frm_modify(){
	document.frmPost.txtActionType.value="modify";
	document.frmPost.action="batch_query_modify_confirm.do";
	
	if (check_frm()&&check_frm_batch_create_common()){
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
		document.frmPost.submit();
	}
}

function frm_cancel(){
	if(!confirm('��ȷ��Ҫȡ����������ѯ��?')){
	return; 
	} 
	document.frmPost.txtActionType.value="cancel";
	document.frmPost.txtQueryIDs.value=document.frmPost.txtQueryID.value;
	document.frmPost.action="batch_query_delete.do";
	document.frmPost.func_flag.value="8003";
	
	if (check_frm()&&check_frm_batch_create_common()) document.frmPost.submit();
}

function frm_excute(){
	document.frmPost.txtActionType.value="excute";
	document.frmPost.txtQueryIDs.value=document.frmPost.txtQueryID.value;
	document.frmPost.action="batch_query_excute.do";

	if (check_frm()) document.frmPost.submit();
}

function frm_result(){
	self.location.href="batch_query_show_result.do?txtQueryID=" + document.frmPost.txtQueryID.value;
}

//-->
</Script>


<form name="frmPost" method="post" action="batch_create_confirm.do">

<input type="hidden" name="txtStatus" value="<tbl:writeparam name="txtStatus" />">
<input type="hidden" name="txtCreateOperatorID" value="<tbl:writeparam name="txtCreateOperatorID" />">

<input type="hidden" name="txtDtLastmod" value="<tbl:writeparam name="txtDtLastmod" />">

<input type="hidden" name="txtActionType" value="">
<input type="hidden" name="txtQueryIDs" value="">
<input type="hidden" name="func_flag" value="">
<input type="hidden" name="txtTemplateFlag" value="<%=txtTemplateFlag%>">
<input name="txtScheduleTime" type="hidden" value="">

<tbl:generatetoken />

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><%=title%></td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>


 <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg" >
      <tr>
	  <td colspan="4" class="import_tit" align="center"><font size="2">������ѯ������Ϣ</font></td>
      </tr>
     
      <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >��ѯID</td>
	   <td width="33%" class="list_bg1"><input name="txtQueryID" type="text" class="textgray" maxlength="10" size="25" value="<tbl:writeparam name="txtQueryID" />" readonly ></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >���������</td>
	   <td width="33%" class="list_bg1"><font size="2"><d:selcmn name="txtQueryType" mapName="SET_B_QUERYTYPE"  match="txtQueryType"  width="23" /></font></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >��ѯ����</td>
	   <td width="33%" class="list_bg1"><input name="txtQueryName" type="text" class="textgray" maxlength="100" size="25" value="<tbl:writeparam name="txtQueryName" />" readonly ></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >״̬</td>
	   <td width="33%" class="list_bg1"><font size="2"><input name="txtStatusValue" type="text" class="textgray" maxlength="200" size="25" value="<d:getcmnname typeName="SET_B_QUERYSTATUS" match="txtStatus" />" readonly ></font></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2"  ><div align="right">������Ϣ</div></td>
	   <td class="list_bg1"  colspan="3"><font size="2"><input name="txtQueryDesc" type="text" class="text" maxlength="200" size="83" value="<tbl:writeparam name="txtQueryDesc" />"></font></td>
	</tr>
	
	<tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >������</td>
	   <td width="33%" class="list_bg1"><input name="txtCreateOperatorIDValue" type="text" class="textgray" maxlength="200" size="25" value="<%=Postern.getOperatorNameByID(Integer.valueOf(request.getParameter("txtCreateOperatorID")).intValue()) %>" readonly ></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >����ʱ��</td>
	   <td width="33%" class="list_bg1"><font size="2"><input name="txtCreateTime" type="text" class="textgray" maxlength="200" size="25" value="<tbl:writeparam name="txtCreateTime" />" readonly ></font></td>
       </tr>
       
	<tr>
	   <td valign="middle" class="list_bg2" align="right" >ִ�з�ʽ*</td>
	   <td class="list_bg1"><font size="2">
		<d:selcmn name="txtScheduleType" mapName="SET_B_SCHEDULETYPE"  match="txtScheduleType"  width="23" />
	   </font></td>
	   <td valign="middle" class="list_bg2" align="right" >�趨ִ��ʱ��</td>
	   <td class="list_bg1"><font size="2">
		<!--
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtScheduleTime)" onblur="lostFocus(this)" name="txtScheduleTime" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtScheduleTime" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    		-->
    		<d:datetime name="txtScheduleTime" size="10" match="txtScheduleTime" includeHour="true" includeMinute="true" includeSecond="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTimeDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />  
	   </font></td>
	 </tr>
	
	<tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >ʵ��ִ��ʱ��</td>
	   <td width="33%" class="list_bg1"><input name="txtPerformTime" class="textgray" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtPerformTime" />" readonly ></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >��ѯ������ʱ��</td>
	   <td width="33%" class="list_bg1"><input name="txtDtLastmod" class="textgray" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtDtLastmod" />" readonly ></td>
       </tr> 
	 
 </table>
 <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>



<jsp:include page="batch_create_common.jsp" />

<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1">
	<tr align="center">
	  <td>
				<table border="0" cellspacing="0" cellpadding="0">
				  <tr>
				  
				    <bk:canback url="batch_query_query.do/batch_query_model_query.do" >  
				            <td width="20" ></td>         
				            <td><img src="img/button2_r.gif" border="0" width="22" height="20" ></td>
				            <td background="img/button_bg.gif" ><a href="<bk:backurl property="batch_query_query.do/batch_query_model_query.do" />" class="btn12">��&nbsp;��</a></td>
				            <td><img src="img/button2_l.gif" border="0" width="11" height="20" ></td>
				    </bk:canback> 
				
				<%
				//�����ģ��
				if(CommonConstDefinition.YESNOFLAG_YES.equals(txtTemplateFlag)){
					//������½����������޸ġ�ȡ��
					if(CommonKeys.QUERY_STATUS_NEW.equals(request.getParameter("txtStatus")))
					{
				%>
				<td width="20" ></td>		
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				    <td><input name="Submit" type="button" class="button" value="�޸�" onclick="javascript:frm_modify()"></td>
				    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				    
				    <td width="20" ></td>		
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				    <td><input name="Submit" type="button" class="button" value="ȡ��" onclick="javascript:frm_cancel()"></td>
				    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				<%}
				//�����ȡ�����������޸�
			else if(CommonKeys.QUERY_STATUS_CANCEL.equals(request.getParameter("txtStatus")))
				{
				%>
				<td width="20" ></td>		
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				    <td><input name="Submit" type="button" class="button" value="�޸�" onclick="javascript:frm_modify()"></td>
				    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				    <%}
				    	//���ִ�з�ʽ����ִ�У��������޸ġ�ȡ����ִ��
				  	}else if(CommonKeys.QUERY_STATUS_NEW.equals(request.getParameter("txtStatus")) && 
				  		CommonKeys.SCHEDULE_TYPE_I.equals(request.getParameter("txtScheduleType"))){
				    %>
				    <td width="20" ></td>		
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				    <td><input name="Submit" type="button" class="button" value="�޸�" onclick="javascript:frm_modify()"></td>
				    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				    
				    <td width="20" ></td>		
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				    <td><input name="Submit" type="button" class="button" value="ȡ��" onclick="javascript:frm_cancel()"></td>
				    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				    
				    <td width="20" ></td>		
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				    <td><input name="Submit" type="button" class="button" value="ִ��������ѯ" onclick="javascript:frm_excute()"></td>
				    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				    <%
				    	}
				    	//������½����������޸ġ�ȡ����ִ��
				  	else if(CommonKeys.QUERY_STATUS_NEW.equals(request.getParameter("txtStatus"))){
				    %>
				    <td width="20" ></td>		
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				    <td><input name="Submit" type="button" class="button" value="�޸�" onclick="javascript:frm_modify()"></td>
				    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				    
				    <td width="20" ></td>		
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				    <td><input name="Submit" type="button" class="button" value="ȡ��" onclick="javascript:frm_cancel()"></td>
				    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				    
				    <%	
				    	}
				    	//�����ȡ����ʧ�ܣ��������޸�
				    	else if(CommonKeys.QUERY_STATUS_FAIL.equals(request.getParameter("txtStatus")) ||
				    		CommonKeys.QUERY_STATUS_CANCEL.equals(request.getParameter("txtStatus")) ){
				    %>
				    <td width="20" ></td>		
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				    <td><input name="Submit" type="button" class="button" value="�޸�" onclick="javascript:frm_modify()"></td>
				    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				    
				    <%	
				    	}
				    	//����Ǵ���ɹ����������޸ġ�����ɹ�
				    	else if(CommonKeys.QUERY_STATUS_SUCCESS.equals(request.getParameter("txtStatus"))){
				    %>
				    <td width="20" ></td>		
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				    <td><input name="Submit" type="button" class="button" value="�޸�" onclick="javascript:frm_modify()"></td>
				    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				    
				    <td width="20" ></td>		
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				    <td><input name="Submit" type="button" class="button" value="�鿴������ѯ���" onclick="javascript:frm_result()"></td>
				    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				    
				    <td width="20" ></td>		
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				    <td><input name="Submit" type="button" class="button" value="ȡ��" onclick="javascript:frm_cancel()"></td>
				    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				    
				    <%	
				    	}
				    	//����Ǳ����ã���ֻ����鿴�����
				    	else if(CommonKeys.QUERY_STATUS_REFERED.equals(request.getParameter("txtStatus"))){
				    %>
				    
				    <td width="20" ></td>		
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				    <td><input name="Submit" type="button" class="button" value="�鿴������ѯ���" onclick="javascript:frm_result()"></td>
				    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				    
				    <%
				    	}
				    %>
				</tr>
			</table>     
 		</td>
  </tr>
</table>  
 </form>