<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>                
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>

<Script language=JavaScript>
<!--

//参数说明：type为类型，typeName为名字（不含value名字），subTypeValue为该参数的值
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
    if (check_Blank(document.frmPost.txtQueryType, true, "结果集类型"))
	return false;

    if (check_Blank(document.frmPost.txtQueryName, true, "查询操作名称"))
	return false;

    if (check_Blank(document.frmPost.txtScheduleType, true, "执行方式"))
	return false;	
     
     if(document.frmPost.txtScheduleType.options[document.frmPost.txtScheduleType.selectedIndex].text=="定时执行" &&
     	document.frmPost.txtScheduleTimeDatePart.value==""){
     	alert("该种类型需要输入执行时间！");
     	return false;
     }
     	
     if(document.frmPost.txtScheduleType.options[document.frmPost.txtScheduleType.selectedIndex].text!="定时执行" &&
     	document.frmPost.txtScheduleTimeDatePart.value!=""){
     	alert("该种类型不需要输入执行时间！");
     	return false;
     }
     if (!check_TenDate(document.frmPost.txtScheduleTimeDatePart, true, "设定执行时间")) 
			return false;
     return true;
}

function back_submit()
{
  var strUrl='<bk:backurl property="batch_query_module_query.do" />';
		 if(strUrl=='')
		   strUrl="batch_query_select_module.screen";
	   document.frmPost.action=strUrl;
	   document.frmPost.submit();
}

function frm_submit()
{
  
  if (check_frm()&&check_frm_batch_create_common()){
  	
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


function ChangeOpenSourceType()
{
     document.FrameUS.submit_update_select('osttoid', document.frmPost.txtOpenSourceType.value, 'txtOpenSourceID', '');

}
//-->
</Script>
<%
String title = "批量查询操作创建----录入信息";
if(CommonConstDefinition.YESNOFLAG_YES.equals(request.getParameter("templateFlag")))
	title = "批量查询模板操作创建----录入信息";
%>

<form name="frmPost" method="post" action="batch_create_confirm.do">
<input name="txtTemplateFlag" type="hidden" value="<tbl:writeparam name="txtTemplateFlag" />">
<input name="templateFlag" type="hidden" value="<tbl:writeparam name="templateFlag" />">
<input name="txtScheduleTime" type="hidden" value="">

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
	  <td colspan="4" class="import_tit" align="center"><font size="2">批量查询操作基本信息</font></td>
      </tr>
     
      <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >结果集类型*</td>
	   <td width="33%" class="list_bg1"><font size="2"><d:selcmn name="txtQueryType" mapName="SET_B_QUERYTYPE"  match="txtQueryType"  width="23" /> </font></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >查询操作名称*</td>
	   <td width="33%" class="list_bg1"><font size="2"><input name="txtQueryName" type="text" class="text" maxlength="100" size="25" value="<tbl:writeparam name="txtQueryName" />"></font></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2"  ><div align="right">查询操作描述</div></td>
	   <td class="list_bg1"  colspan="3"><font size="2"><input name="txtQueryDesc" type="text" class="text" maxlength="200" size="81" value="<tbl:writeparam name="txtQueryDesc" />"></font></td>
	</tr>
	
	<tr>
	   <td valign="middle" class="list_bg2" align="right" >执行方式*</td>
	   <td class="list_bg1"><font size="2">
		<d:selcmn name="txtScheduleType" mapName="SET_B_SCHEDULETYPE"  match="txtScheduleType"  width="23" />
	   </font></td>
	   <td valign="middle" class="list_bg2" align="right" >设定执行时间</td>
	   <td class="list_bg1"><font size="2">
	   <!--
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtScheduleTime)" onblur="lostFocus(this)" name="txtScheduleTime" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtScheduleTime" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	    -->
    	    
    	    <d:datetime name="txtScheduleTime" size="10" match="txtScheduleTime" includeHour="true" includeMinute="true" includeSecond="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTimeDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />  
    	    
	   </font></td>
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
			    
			    <bk:canback url="menu_batch_query_query.do/batch_query_query.do/menu_batch_query_model_query.do/batch_query_model_query.do">
			    <td width="20" ></td>		
			    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			    <td background="img/button_bg.gif"  height="20">
			    	<a href="<bk:backurl property="menu_batch_query_query.do/batch_query_query.do/menu_batch_query_model_query.do/batch_query_model_query.do"/>" class="btn12">&nbsp;上一步&nbsp;</a></td>
			    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			    </bk:canback>
			    
			    <td width="20" ></td>		
			    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			    <td><input name="Submit" type="button" class="button" value="下一步" onclick="javascript:frm_submit()"></td>
			    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			    
			</tr>
			</table>     
 		</td>
  </tr>
</table> 

</form>