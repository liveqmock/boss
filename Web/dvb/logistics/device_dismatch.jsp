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
	if (check_Blank(document.frmPost.txtSCDeviceInput, true, "���ܿ���"))
		return false;
	document.frmPost.txtSCSerialNoCollection.value = document.frmPost.txtSCSerialNoCollection.value + document.frmPost.txtSCDeviceInput.value + "\r\n";

	document.frmPost.txtSCDeviceInput.value="";
	document.frmPost.txtSCDeviceInput.focus();
  }  
}
function check_frm()
{
	if (check_Blank(document.frmPost.txtBatchID, true, "��������"))
		return false;
	if (check_Blank(document.frmPost.txtSCSerialNoCollection, true, "���ܿ����к��б�"))
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
    <td class="title"><font size="3">���������</font></td>
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
            <td class="list_bg2" align="right" >��������*</td>
            <td class="list_bg1" width="70%">
                <input type="text" name="txtBatchID" size="20"  value="<tbl:writeparam name="txtBatchID" />" >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right" ></td>
            <td class="list_bg1" width="70%">ɨ��󰴻س�</td>
        </tr>
        <tr>
            <td class="list_bg2" align="right" >���ܿ�ɨ�������</td>
	    <td class="list_bg1" width="70%">
        	<input type="text" name="txtSCDeviceInput" size="20"  value="<tbl:writeparam name="txtSCDeviceInput" />" onkeypress="return   text2_onkeypress()" >
		<input name="selbutton" type="button" class="button" value="ѡ��" onClick="javascript:sel_sc(defaultDevice.value,'txtSCSerialNoCollection','','','','DeviceIDList')"> 
            </td>
        </tr>
	<tr>
            <td class="list_bg2" align="right" ></td>
            <td class="list_bg1" width="70%">���ܿ�����ԡ��س�������</td>
        </tr>
	<tr>
	    <td class="list_bg2" align="right" >���ܿ����*</td>    
	    <td class="list_bg1" >
	    <textarea name="txtSCSerialNoCollection"  length="5" cols=75 rows=9><tbl:writeSpeCharParam name="txtSCSerialNoCollection" /></textarea>
	    </td>		
	</tr>
<tr>
	    <td class="list_bg2" align="right" >��ע</td>    
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
        	<!-- ���ص��豸��ϸ��Ϣҳ�� -->
        	<%if(request.getParameter("txtDeviceID")!=null && !"".equals(request.getParameter("txtDeviceID"))){ %>
        	<bk:canback url="dev_detail.do" >
          	<td width="20" ></td>  
           	<td><img src="img/button2_r.gif" width="22" height="20"></td>      
           	<td background="img/button_bg.gif"  height="20" >
           	<a href="<bk:backurl property="dev_detail.do" />" class="btn12">��&nbsp;��</a></td>
           	<td><img src="img/button2_l.gif" width="13" height="20"></td>
       		</bk:canback>
       		<%}%>
       		<td width="20" ></td>
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:next_step()" class="btn12">��һ��</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
        </tr>
        </table>
	</td>
    </tr>
    </table>
<tbl:hiddenparameters pass="txtDeviceID" />     <!-- ���豸��ϸ��Ϣҳ�洫�����Ĳ��� -->    
<input type="hidden" name="defaultDevice" value="">
<input type="hidden" name="DeviceIDList" value="">
</form>