<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<script language=javascript>
<!--
function sel_sc(defDev,typeName,parentType,parentTypeName,parentTypeValue,hiddenvar)
{
	var param="sc_select.screen?";
	param=param + "typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	param=param + "&defaultDevice="+defDev;
	param=param + "&deviceIDList="+hiddenvar;
	param=param + "&processFlag=dismatch";

	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;

	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=600,height=550,screenX=600,screenY=400";
	window.open(param,"",windowFeatures);
}
function   text2_onkeypress()   
{
  if(event.keyCode==13)
  {
	if (check_Blank(document.frmPost.txtSCDeviceInput, true, "智能卡号"))
		return false;
	document.frmPost.txtSCSerialNoCollection.value = document.frmPost.txtSCSerialNoCollection.value + document.frmPost.txtSCDeviceInput.value + "\r\n";

	document.frmPost.txtSCDeviceInput.value="";
	document.frmPost.txtSCDeviceInput.focus();
  }  
}
function check_frm()
{
	if (check_Blank(document.frmPost.txtBatchID, true, "操作批号"))
		return false;
	if (check_Blank(document.frmPost.txtSCSerialNoCollection, true, "智能卡序列号列表"))
	{
		document.frmPost.txtSCDeviceInput.focus();
		return false;
	}
	return true;
}
function next_step()
{
    if(check_frm()){
        document.frmPost.action ="device_dismatch_check.do";
    document.frmPost.submit();    
  }
}
//-->
</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><font size="3">机卡解配对</font></td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<br>

<form name="frmPost" method="post" >
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr> 
            <td class="list_bg2" align="right" >操作批号*</td>
            <td class="list_bg1" width="70%">
                <input type="text" name="txtBatchID" size="20"  value="<tbl:writeparam name="txtBatchID" />" >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right" ></td>
            <td class="list_bg1" width="70%">扫描后按回车</td>
        </tr>
        <tr>
            <td class="list_bg2" align="right" >智能卡扫描输入框</td>
	    <td class="list_bg1" width="70%">
        	<input type="text" name="txtSCDeviceInput" size="20"  value="<tbl:writeparam name="txtSCDeviceInput" />" onkeypress="return   text2_onkeypress()" >
		<input name="selbutton" type="button" class="button" value="选择" onClick="javascript:sel_sc(defaultDevice.value,'txtSCSerialNoCollection','','','','DeviceIDList')"> 
            </td>
        </tr>
	<tr>
            <td class="list_bg2" align="right" ></td>
            <td class="list_bg1" width="70%">智能卡编号以“回车”隔开</td>
        </tr>
	<tr>
	    <td class="list_bg2" align="right" >智能卡编号*</td>    
	    <td class="list_bg1" >
	    <textarea name="txtSCSerialNoCollection"  length="5" cols=75 rows=9><tbl:writeSpeCharParam name="txtSCSerialNoCollection" /></textarea>
	    </td>		
	</tr>
<tr>
	    <td class="list_bg2" align="right" >备注</td>    
	    <td class="list_bg1" >
	    <textarea name="txtDesc"  length="5" cols=75 rows=4><tbl:writeSpeCharParam name="txtDesc" /></textarea>
	    </td>		
	</tr>
    </table>
    <table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tr align="center">
	<td class="list_bg1">   
        <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
        	<!-- 返回到设备详细信息页面 -->
        	<%if(request.getParameter("txtDeviceID")!=null && !"".equals(request.getParameter("txtDeviceID"))){ %>
        	<bk:canback url="dev_detail.do" >
          	<td width="20" ></td>  
           	<td><img src="img/button2_r.gif" width="22" height="20"></td>      
           	<td background="img/button_bg.gif"  height="20" >
           	<a href="<bk:backurl property="dev_detail.do" />" class="btn12">返&nbsp;回</a></td>
           	<td><img src="img/button2_l.gif" width="13" height="20"></td>
       		</bk:canback>
       		<%}%>
       		<td width="20" ></td>
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:next_step()" class="btn12">下一步</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
        </tr>
        </table>
	</td>
    </tr>
    </table>
<tbl:hiddenparameters pass="txtDeviceID" />     <!-- 从设备详细信息页面传过来的参数 -->    
<input type="hidden" name="defaultDevice" value="">
<input type="hidden" name="DeviceIDList" value="">
</form>