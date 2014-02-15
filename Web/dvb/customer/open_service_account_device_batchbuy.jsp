<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="operator" prefix="op" %>

<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>

<%

String txtCustomerID = request.getParameter("txtCustomerID");
CustomerDTO customerDTO = Postern.getCustomerByID(Integer.parseInt(txtCustomerID));
String custName = customerDTO.getName();              //��ϵ��
String custTelephone = customerDTO.getTelephone();    //��ϵ�绰

String install = "";
if(request.getParameter("txtIsInstall") != null)
	install = request.getParameter("txtIsInstall");
else
	install = CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL;

float txtForceDepostMoney =(float) ((request.getParameter("txtForceDepostMoney")==null) ? 0.0 :Float.parseFloat(request.getParameter("txtForceDepostMoney")));    

%>

<form name="frmPost" method="post" action="open_service_account_device_batchbuy.do" >
	<input type="hidden" name="checkSelect" value="1" >
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">����ҵ���ʻ�</td>
  </tr>
</table>
  <br>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
   </table>
   <br>
<jsp:include page="/batchbuy/buyinfo_device_choose.jsp" />
	
		<table width="100%" border="0" align="center" cellpadding="4"
			cellspacing="1" class="list_bg">
			<tr>
				<td width="100%" class="import_tit" align="center">����������Ϣ</td>
			</tr>
		</table>	
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >

  <tr>
	  <td width="17%" align="right" class="list_bg2">Ѻ��*</td>
	  <%
	     Collection protocolList =Postern.getProtocolDTOCol(Integer.parseInt(txtCustomerID));
	     if (protocolList ==null || protocolList.isEmpty()){
	  %>
       <td colspan="3" align="left" class="list_bg1"><input type="text" size="25" name="txtForceDepostMoney" value="<%=txtForceDepostMoney%>" class="text">
	  <%
	     }else{
	  %>
	    <td align="left" class="list_bg1"><input type="text" size="25" name="txtForceDepostMoney" value="<%=txtForceDepostMoney%>" class="text">
	    <td width="17%" align="right" class="list_bg2">ʹ������*</td>
	    <td align="left" class="list_bg1"><input type="text" size="25" name="txtUsedMonth" value="0" class="text">
	  <%
	     }
	  %>
	</tr>
  <tr>     
     <td width="17%" align="right" class="list_bg2">��ϵ�绰*</td>
     <td width="33%" align="left" class="list_bg1"><input type="text" size="25"  name="txtContactPhone" value="<%=custTelephone%>" class="text"></td>
     <td width="17%" align="right" class="list_bg2">��ϵ��*</td>
     <td width="33%" align="left" class="list_bg1"><input type="text" size="25" name="txtContactPerson" value="<%=custName%>" class="text"></td>
   </tr>
  
  <TR>
	   <TD width="17%" align="right" class="list_bg2" >�Ƿ����Ű�װ</TD>
	   <TD width="33%" class="list_bg1" ><d:selcmn name="txtIsInstall" mapName="SET_V_CUSTSERVICEINTERACTIONINSTYPE" match="txtIsInstall" width="25"  defaultFlag ="true" showAllFlag="false"  empty="false" onchange="changePrefed()"/></TD>
     <%
       if ("gehua".equalsIgnoreCase(Postern.getSystemSymbolName())){
     %>
     <TD width="17%" align="right" class="list_bg2" >��ע</TD>
     <TD width="33%" class="list_bg1" ><input type="text" size="25" name="txtComments" value ="<tbl:writeparam name="txtComments" />" class="text"></TD>
     <%
       } else{
     %>
     <TD width="17%" align="right" class="list_bg2" >&nbsp;</TD>
     <TD width="33%" class="list_bg1" >&nbsp;</TD>  
     <%
       }
     %>
  </TR>
	<tr id ="prefered" style="display:<%if(CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL.equals(install)){%>none<%}else{}%>">
		<td  width="17%" valign="middle" class="list_bg2"  align="right" width="17%" >ԤԼ��������*</div></td>
		<td  width="33%"  class="list_bg1"><font size="2">
		<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPreferedDate)" onblur="lostFocus(this)" type="text" name="txtPreferedDate" size="25"  value="<tbl:writeparam name="txtPreferedDate" />" class="text">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
		<td  width="17%" class="list_bg2" align="right">ԤԼ����ʱ���*</td>
		<td  width="33%" class="list_bg1"  ><font size="2">
		 <d:selcmn name="txtPreferedTime" mapName="SET_C_CSIPREFEREDTIME" match="txtPreferedTime" width="23" defaultFlag ="true" showAllFlag="false"/>
		</font></td>
	</tr>
</TABLE>     


<br>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
   </table>
<br>
<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td width="20" ></td>
	   <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td height="20">
		 <input name="prev" type="button" class="button" onClick="javascript:frm_back()" value="��һ��">
	   </td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td> 
	   <td width="20" >&nbsp;</td> 
	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td height="20">
		 <input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="��һ��">
	   </td>
	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>

	</tr>
</table>
<tbl:hiddenparameters pass="txtDistrictID/txtCustomerID/txtAccount/txtCustType/txtOpenSourceType/txtCsiCreateReason/txtBuyNum" />
<input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_BATCHBUY_PRODUCTINFO%>" />
<input type="hidden" name="confirm_post" value="false" >
<input type="hidden" name="txtHiddenMacInmac" value="" >

</form>
<Script language=JavaScript>
 <!--
 var selfInstall ="<%=CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL%>" ;  
function check_frm(){
	if(!checkDeviceInfo()){
		return false;
	}	

	if (check_Blank(document.frmPost.txtContactPhone, true, "��ϵ�绰"))
          return false;
	if (check_Blank(document.frmPost.txtContactPerson, true, "��ϵ��"))
          return false; 
	if (check_Blank(document.frmPost.txtForceDepostMoney, true, "Ѻ��"))
	        return false;
  if (!check_Float(document.frmPost.txtForceDepostMoney,true,"Ѻ��"))
          return false;
  var usedMonth =document.frmPost.txtUsedMonth;
  if (usedMonth !=null){
  	 if (check_Blank(document.frmPost.txtUsedMonth, true, "ʹ������"))
	       return false;
	   if (!check_Num(document.frmPost.txtUsedMonth, true, "ʹ������"))
	       return false;
	   if (document.frmPost.txtUsedMonth.value*1.0 <=0){
	   	   alert("ʹ����������С�ڵ���0");
	   	   return false;
	   }
  }

	return true;
}

function frm_submit(){
    if (document.all("prefered").style.display=="") {
       if (check_Blank(document.frmPost.txtPreferedDate, true, "ԤԼ��������"))
	         return false;
       if (check_Blank(document.frmPost.txtPreferedTime, true, "ԤԼ����ʱ���"))
	         return false;
       if (!check_TenDate(document.frmPost.txtPreferedDate, true, "ԤԼ��������")) 
           return false;	
       if(!compareDateAndToday(getCurrentDate(),document.frmPost.txtPreferedDate,"ԤԼ�������ڱ����ڽ����Ժ�"))
			return false;   
           
    }
        //������е��豸�Ƿ���д����

	  if (check_frm()) {
		   if (document.frmPost.txtIsInstall!=null && (document.frmPost.txtIsInstall.value ==selfInstall
  	                                              ||document.frmPost.txtIsInstall.value =="")){
  	      document.frmPost.txtPreferedDate.value ="";
			    document.frmPost.txtPreferedTime.value ="";
  	   }
	     document.frmPost.submit();
	  }
}
function frm_back(){
   document.frmPost.txtActionType.value ="";
	 document.frmPost.action="menu_open_service_account_batchbuy.do?txtCsiType=UO";
	 document.frmPost.submit();
}

function changePrefed(){
   if (document.frmPost.txtIsInstall.value ==selfInstall){
      document.all("prefered").style.display ="none";
   } else{
      document.all("prefered").style.display ="";
   }
 
}


 function installPrepare(){
     if(document.frmPost.txtIsInstall!=null){
	   	if (document.frmPost.txtIsInstall.value ==selfInstall||document.frmPost.txtIsInstall.value ==""){
		      document.all("prefered").style.display ="none";
		 } else{
		      document.all("prefered").style.display ="";
		 }
	}
 }
  installPrepare();

function setSelectFalse()
{
	document.frmPost.checkSelect.value = "0";
}
 
-->
</Script>