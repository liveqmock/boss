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
    if (check_Blank(document.frmPost.txtDeviceModelName, true, "�豸�ͺ�����"))
	    return false;
   if (check_Blank(document.frmPost.txtDeviceModelStatus, true, "�豸״̬"))
	    return false;
  if (check_Blank(document.frmPost.txtManagerDeviceFlag, true, "�Ƿ��������豸��־"))
	    return false;
    if (!check_Num(document.frmPost.txtSerialLength, true, "���кų���"))
	    return false;
	   if (check_Blank(document.frmPost.txtViewInCdtFlag, true, "�Ƿ��ڿͻ���Ϣ������ʾ"))
	    return false;
if (document.frmPost.txtViewInCdtFlag.value =="Y") {
 if (check_Blank(document.frmPost.txtKeySerialNoFrom, true, "���к���ʾ����ʼλ��"))
	    return false;
   if (check_Blank(document.frmPost.txtKeySerialNoTo, true, "���к���ʾ�Ľ�ֹλ��"))
	    return false;
		 if (!check_Num(document.frmPost.txtKeySerialNoFrom, true, "���к���ʾ����ʼλ��"))
	    return false;
 if (!check_Num(document.frmPost.txtKeySerialNoTo, true, "���к���ʾ�Ľ�ֹλ��"))
	    return false;
	if(parseInt(document.frmPost.txtKeySerialNoTo.value)<parseInt(document.frmPost.txtKeySerialNoFrom.value))
			{
				  alert("���к���ʾ����ʼλ�ò���С�����к���ʾ�Ľ�ֹλ��");
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
	<!-- ���嵱ǰ���� -->
	<input type="hidden" name="txtActionType" size="20" value="MODIFY">
	<input type="hidden" name="txtModel" size="20" value="">
	
	 
	 
	
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">�豸�ͺ��޸�</td>
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
		<td class="list_bg2" align="right">�豸�ͺ�����</td>
		<td class="list_bg1"><input name="txtDeviceModelName"
			type="text" maxlength="200" size="25" readonly  class="textgray" 
			value="<tbl:write name="oneline" property="deviceModel"/>"></td>
		<td class="list_bg2" align="right">�豸����</td>
		<td class="list_bg1"><input name="txtDeviceName"
			type="text" maxlength="200" size="25" readonly  class="textgray" 
			value="<%=deviceName%>">
		<!-- <tbl:select name="txtDeviceClass" set="AllDeviceClasses" match="txtDeviceClass" width="23" defaultValue="<%=strDeviceClass%>"/> --></td>
	</tr>
	<input type="hidden" name="txtDeviceClass" size="20" value="<tbl:write name="oneline" property="deviceClass"/>">
	<tr>
		<td class="list_bg2" align="right">״̬*</td>
		<td class="list_bg1"><font size="2"><d:selcmn name="txtDeviceModelStatus"
			mapName="SET_D_DEVICEMODELSTATUS" match="txtDeviceModelStatus" width="23" defaultValue="<%=strDeviceModelStatus%>"/> </font></td>
		<td class="list_bg2" align="right">�豸��Ӧ��</td>
		<td class="list_bg1"><tbl:select name="txtProvider" set="AllProvider" match="txtProvider" width="23" defaultValue="<%=strProvider%>"/></td>	
		
	</tr>
	<tr>
	      <td class="list_bg2" align="right">���кų���</td>
		<td class="list_bg1"><font size="2"><input name="txtSerialLength"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:write name="oneline" property="serialLength"/>"></font></td>
	       <td class="list_bg2" align="right">�Ƿ��������豸��־*</td>
		<td class="list_bg1"><font size="2">
		<d:selcmn name="txtManagerDeviceFlag" mapName="SET_G_YESNOFLAG" match="oneline:manageDeviceFlag" width="23" /> </font></td>
		 			
	</tr>
	<tr>
	       <td class="list_bg2" align="right">�Ƿ��ڿͻ���Ϣ������ʾ*</td>
		<td class="list_bg1" ><font size="2">
		<d:selcmn name="txtViewInCdtFlag" mapName="SET_G_YESNOFLAG" match="oneline:viewInCdtFlag" width="23" onchange="changePrefed()" /> </font></td>
        <td class="list_bg2" align="right">�豸�ͺŷ���</td>
		<td class="list_bg1" ><font size="2">
		<d:selcmn name="txtBusinessType" mapName="SET_V_DEVICEMODEL_BUSINESSTYPE" match="oneline:businessType" width="23" /> </font></td>		 			
	</tr>
	<tr id="prefered"  <%if ((strViewInCdtFlag.equals("N")) || (strViewInCdtFlag.equals(""))){%>
				style="display:none" <%} %>>
	      <td class="list_bg2" align="right">���к���ʾ����ʼλ��*</td>
		<td class="list_bg1"><font size="2"><input name="txtKeySerialNoFrom"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:write name="oneline" property="keySerialNoFrom"/>"></font></td>
	       <td class="list_bg2" align="right">���к���ʾ�Ľ�ֹλ��*</td>
		<td class="list_bg1"><font size="2"><input name="txtKeySerialNoTo"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:write name="oneline" property="keySerialNoTo"/>"></font></td>	
	</tr>
	<tr>
		<td class="list_bg2" align="right">�豸�ͺ�����</td>
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
            <td background="img/button_bg.gif"><a href="<bk:backurl property="devicemodel_query.do" />" class="btn12">��&nbsp;��</a></td>
            <td width="22" height="20"><img src="img/button2_l.gif"  height="20"></td>     	
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
	    value="��&nbsp;��" onclick="javascript:devicemodel_modify()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

</form>
