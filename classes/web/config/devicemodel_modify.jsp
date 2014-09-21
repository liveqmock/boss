<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.DeviceModelDTO"%>
<%
    String s=request.getParameter("txtOPDeviceModel");
    
%>
<SCRIPT language="JavaScript">
function check_form ()
{
    if (check_Blank(document.frmPost.txtDeviceModelName, true, "设备型号名称"))
	    return false;
   if (check_Blank(document.frmPost.txtDeviceModelStatus, true, "设备状态"))
	    return false;
  if (check_Blank(document.frmPost.txtManagerDeviceFlag, true, "是否管理具体设备标志"))
	    return false;
    if (!check_Num(document.frmPost.txtSerialLength, true, "序列号长度"))
	    return false;
	   if (check_Blank(document.frmPost.txtViewInCdtFlag, true, "是否在客户信息树中显示"))
	    return false;
if (document.frmPost.txtViewInCdtFlag.value =="Y") {
 if (check_Blank(document.frmPost.txtKeySerialNoFrom, true, "序列号显示的起始位置"))
	    return false;
   if (check_Blank(document.frmPost.txtKeySerialNoTo, true, "序列号显示的截止位置"))
	    return false;
		 if (!check_Num(document.frmPost.txtKeySerialNoFrom, true, "序列号显示的起始位置"))
	    return false;
 if (!check_Num(document.frmPost.txtKeySerialNoTo, true, "序列号显示的截止位置"))
	    return false;
	if(parseInt(document.frmPost.txtKeySerialNoTo.value)<parseInt(document.frmPost.txtKeySerialNoFrom.value))
			{
				  alert("序列号显示的起始位置不能小于序列号显示的截止位置");
 document.frmPost.txtKeySerialNoFrom.focus();
			return false;
			}

	}
	return true;
}

function devicemodel_modify()
{
    if (check_form())
    {
    	
        document.frmPost.submit();
    }
}
function changePrefed(){
   if (document.frmPost.txtViewInCdtFlag.value =="N"){
      document.all("prefered").style.display ="none";
   } else{
      document.all("prefered").style.display ="";
   }
 
}
 
</SCRIPT>

<form name="frmPost" method="post" action="devicemodel_modify.do">
	<!-- 定义当前操作 -->
	<input type="hidden" name="txtActionType" size="20" value="MODIFY">
	<input type="hidden" name="txtModel" size="20" value="">
	
	 
	 
	
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">设备型号修改</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
  
 
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
				<%
				
				     
					Map mapDeviceClasses = Postern.getAllDeviceClasses();
					pageContext.setAttribute("AllDeviceClasses",mapDeviceClasses);

					Map mapAllProvider = Postern.getAllProvider();
					pageContext.setAttribute("AllProvider",mapAllProvider);

				 

				        DeviceModelDTO dto = (DeviceModelDTO) pageContext
							.getAttribute("oneline");
					String strDeviceModelStatus=dto.getStatus();
					String strDeviceClass=dto.getDeviceClass();
					String deviceName="";
					if(strDeviceClass!=null)
				        deviceName = Postern.getClassNameByDeviceClass(strDeviceClass);
						 String strViewInCdtFlag=dto.getViewInCdtFlag();
					String strProvider=dto.getProviderID()+"";
					%>

<table width="810" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="18%"></td>
		<td class="list_bg1" width="32%"></td>
		<td class="list_bg2" align="right" width="17%"></td>
		<td class="list_bg1" width="33%"></td>
	</tr>
	<input type="hidden" name="txtDtLastmod" size="20" value="<tbl:write name="oneline" property="dtLastmod"/>">
	<tr>
		<td class="list_bg2" align="right">设备型号名称</td>
		<td class="list_bg1"><input name="txtDeviceModelName"
			type="text" maxlength="200" size="25" readonly  class="textgray" 
			value="<tbl:write name="oneline" property="deviceModel"/>"></td>
		<td class="list_bg2" align="right">设备类型</td>
		<td class="list_bg1"><input name="txtDeviceName"
			type="text" maxlength="200" size="25" readonly  class="textgray" 
			value="<%=deviceName%>">
		<!-- <tbl:select name="txtDeviceClass" set="AllDeviceClasses" match="txtDeviceClass" width="23" defaultValue="<%=strDeviceClass%>"/> --></td>
	</tr>
	<input type="hidden" name="txtDeviceClass" size="20" value="<tbl:write name="oneline" property="deviceClass"/>">
	<tr>
		<td class="list_bg2" align="right">状态*</td>
		<td class="list_bg1"><font size="2"><d:selcmn name="txtDeviceModelStatus"
			mapName="SET_D_DEVICEMODELSTATUS" match="txtDeviceModelStatus" width="23" defaultValue="<%=strDeviceModelStatus%>"/> </font></td>
		<td class="list_bg2" align="right">设备供应商</td>
		<td class="list_bg1"><tbl:select name="txtProvider" set="AllProvider" match="txtProvider" width="23" defaultValue="<%=strProvider%>"/></td>	
		
	</tr>
	<tr>
	      <td class="list_bg2" align="right">序列号长度</td>
		<td class="list_bg1"><font size="2"><input name="txtSerialLength"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:write name="oneline" property="serialLength"/>"></font></td>
	       <td class="list_bg2" align="right">是否管理具体设备标志*</td>
		<td class="list_bg1"><font size="2">
		<d:selcmn name="txtManagerDeviceFlag" mapName="SET_G_YESNOFLAG" match="oneline:manageDeviceFlag" width="23" /> </font></td>
		 			
	</tr>
	<tr>
	       <td class="list_bg2" align="right">是否在客户信息树中显示*</td>
		<td class="list_bg1" ><font size="2">
		<d:selcmn name="txtViewInCdtFlag" mapName="SET_G_YESNOFLAG" match="oneline:viewInCdtFlag" width="23" onchange="changePrefed()" /> </font></td>
        <td class="list_bg2" align="right">设备型号分类</td>
		<td class="list_bg1" ><font size="2">
		<d:selcmn name="txtBusinessType" mapName="SET_V_DEVICEMODEL_BUSINESSTYPE" match="oneline:businessType" width="23" /> </font></td>		 			
	</tr>
	<tr id="prefered"  <%if ((strViewInCdtFlag.equals("N")) || (strViewInCdtFlag.equals(""))){%>
				style="display:none" <%} %>>
	      <td class="list_bg2" align="right">序列号显示的起始位置*</td>
		<td class="list_bg1"><font size="2"><input name="txtKeySerialNoFrom"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:write name="oneline" property="keySerialNoFrom"/>"></font></td>
	       <td class="list_bg2" align="right">序列号显示的截止位置*</td>
		<td class="list_bg1"><font size="2"><input name="txtKeySerialNoTo"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:write name="oneline" property="keySerialNoTo"/>"></font></td>	
	</tr>
	<tr>
		<td class="list_bg2" align="right">设备型号描述</td>
		<td class="list_bg1" colspan="3"><font size="2"><input name="txtDeviceModelDesc"
			type="text" class="text" maxlength="200" size="83"
			value="<tbl:write name="oneline" property="description"/>"></font></td>
	</tr>
	<tr>
		
	</tr>
</table>
	</lgc:bloop>
 
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td width="11" height="20"><img src="img/button2_r.gif"  height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="devicemodel_query.do" />" class="btn12">返&nbsp;回</a></td>
            <td width="22" height="20"><img src="img/button2_l.gif"  height="20"></td>     	
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
	    value="保&nbsp;存" onclick="javascript:devicemodel_modify()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

</form>
