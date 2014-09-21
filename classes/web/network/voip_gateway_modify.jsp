<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.wrap.VOIPEventWrap"%>
<%@ page import="com.dtv.oss.dto.SystemEventDTO"%>

<SCRIPT language="JavaScript">
function modify_submit()
{
	if(check_frm())
		document.frmPost.submit();
}
function back_submit(){
	document.frmPost.action="voip_gateway_list.do?txtQueryType=G";
    	document.frmPost.submit();
}
function check_frm(){
	if(document.frmPost.txtdeviceModel.value==""){
		alert("没有选择设备类型！");
		return false;
	}
	if(document.frmPost.txtdevsType.value==""){
		alert("没有填写网关型号标识！");
		return false;
	}
	if (document.frmPost.txtdevsType.value.length>5){
  		alert('网关型号标识长度不能超过5');
  		return false;
  } 
	return true;
}
</SCRIPT>

<form name="frmPost" method="post" action="voip_gateway_modify.do">
<INPUT TYPE="hidden" name="actionFlag" value="modify">
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >


<INPUT TYPE="hidden" name="prevDevsType" value="<tbl:writeparam name="txtDevsType"/>">
<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19px" height="19px"></td>
		<td class="title">网关型号修改</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<br>
<%Map mapDevices=Postern.getAllDeviceClasses();
pageContext.setAttribute("devices",mapDevices);
%>
<table width="100%" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">设备型号</td>
		<td class="list_bg1" width="33%"><input name="txtdeviceModel" type="text" class="textgray" maxlength="200" size="25"
			value="<tbl:writeparam name="txtDeviceModel"  />" readonly ></td>
		<td class="list_bg2" align="right" width="17%">网关型号标识*</td>
		<td class="list_bg1" width="33%"><input name="txtdevsType"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtDevsType"  />" ></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">描述</td>
		<td class="list_bg1" colspan="3"><input name="txtdescription"
			type="text" class="text" maxlength="1200" size="83"
			value="<tbl:writeparam name="txtdescription"/>"></td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<br>
<table align="center"  border="0" cellspacing="0" cellpadding="0">
          <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="保&nbsp;存" onclick="javascript:modify_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
     
   
	   </td>
	</tr>
    </table>    
 <br>
 <INPUT TYPE="hidden" name="func_flag" value="2003"> 
</form>
