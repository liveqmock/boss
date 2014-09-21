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
String custName = customerDTO.getName();              //联系人
String custTelephone = customerDTO.getTelephone();    //联系电话

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
    <td class="title">创建业务帐户</td>
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
				<td width="100%" class="import_tit" align="center">其它受理信息</td>
			</tr>
		</table>	
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >

  <tr>
	  <td width="17%" align="right" class="list_bg2">押金*</td>
	  <%
	     Collection protocolList =Postern.getProtocolDTOCol(Integer.parseInt(txtCustomerID));
	     if (protocolList ==null || protocolList.isEmpty()){
	  %>
       <td colspan="3" align="left" class="list_bg1"><input type="text" size="25" name="txtForceDepostMoney" value="<%=txtForceDepostMoney%>" class="text">
	  <%
	     }else{
	  %>
	    <td align="left" class="list_bg1"><input type="text" size="25" name="txtForceDepostMoney" value="<%=txtForceDepostMoney%>" class="text">
	    <td width="17%" align="right" class="list_bg2">使用月数*</td>
	    <td align="left" class="list_bg1"><input type="text" size="25" name="txtUsedMonth" value="0" class="text">
	  <%
	     }
	  %>
	</tr>
  <tr>     
     <td width="17%" align="right" class="list_bg2">联系电话*</td>
     <td width="33%" align="left" class="list_bg1"><input type="text" size="25"  name="txtContactPhone" value="<%=custTelephone%>" class="text"></td>
     <td width="17%" align="right" class="list_bg2">联系人*</td>
     <td width="33%" align="left" class="list_bg1"><input type="text" size="25" name="txtContactPerson" value="<%=custName%>" class="text"></td>
   </tr>
  
  <TR>
	   <TD width="17%" align="right" class="list_bg2" >是否上门安装</TD>
	   <TD width="33%" class="list_bg1" ><d:selcmn name="txtIsInstall" mapName="SET_V_CUSTSERVICEINTERACTIONINSTYPE" match="txtIsInstall" width="25"  defaultFlag ="true" showAllFlag="false"  empty="false" onchange="changePrefed()"/></TD>
     <%
       if ("gehua".equalsIgnoreCase(Postern.getSystemSymbolName())){
     %>
     <TD width="17%" align="right" class="list_bg2" >备注</TD>
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
		<td  width="17%" valign="middle" class="list_bg2"  align="right" width="17%" >预约上门日期*</div></td>
		<td  width="33%"  class="list_bg1"><font size="2">
		<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPreferedDate)" onblur="lostFocus(this)" type="text" name="txtPreferedDate" size="25"  value="<tbl:writeparam name="txtPreferedDate" />" class="text">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
		<td  width="17%" class="list_bg2" align="right">预约上门时间段*</td>
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
		 <input name="prev" type="button" class="button" onClick="javascript:frm_back()" value="上一步">
	   </td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td> 
	   <td width="20" >&nbsp;</td> 
	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td height="20">
		 <input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步">
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

	if (check_Blank(document.frmPost.txtContactPhone, true, "联系电话"))
          return false;
	if (check_Blank(document.frmPost.txtContactPerson, true, "联系人"))
          return false; 
	if (check_Blank(document.frmPost.txtForceDepostMoney, true, "押金"))
	        return false;
  if (!check_Float(document.frmPost.txtForceDepostMoney,true,"押金"))
          return false;
  var usedMonth =document.frmPost.txtUsedMonth;
  if (usedMonth !=null){
  	 if (check_Blank(document.frmPost.txtUsedMonth, true, "使用月数"))
	       return false;
	   if (!check_Num(document.frmPost.txtUsedMonth, true, "使用月数"))
	       return false;
	   if (document.frmPost.txtUsedMonth.value*1.0 <=0){
	   	   alert("使用月数不能小于等于0");
	   	   return false;
	   }
  }

	return true;
}

function frm_submit(){
    if (document.all("prefered").style.display=="") {
       if (check_Blank(document.frmPost.txtPreferedDate, true, "预约上门日期"))
	         return false;
       if (check_Blank(document.frmPost.txtPreferedTime, true, "预约上门时间段"))
	         return false;
       if (!check_TenDate(document.frmPost.txtPreferedDate, true, "预约上门日期")) 
           return false;	
       if(!compareDateAndToday(getCurrentDate(),document.frmPost.txtPreferedDate,"预约上门日期必须在今天以后"))
			return false;   
           
    }
        //检查所有的设备是否填写完整

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