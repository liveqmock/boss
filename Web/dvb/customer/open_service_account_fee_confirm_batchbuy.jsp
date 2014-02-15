<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO" %>

<form name="frmPost" method="post" action="open_service_account_confirm_batchbuy.do" > 
<input type="hidden" name="transferType" value="<tbl:writeparam name="transferType"/>">     
     <table  border="0" align="center" cellpadding="0" cellspacing="5">
        <tr>
          <td><img src="img/list_tit.gif" width="19" height="19"></td>
          <td class="title">收费信息确认</td>
        </tr>
      </table>
      <br>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
       </table>
       <br>
  
		<jsp:include page="/batchbuy/buyinfo_product_confirm.jsp" />  
                 
    <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UO%>" acctshowFlag ="true"  confirmFlag="true" />		

     <%
        String systemSettingPrecise = Postern.getSystemsettingValueByName("SET_W_PRECISEWORK");
     	if((CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_INSTALL).equals(request.getParameter("txtIsInstall"))&&(systemSettingPrecise!=null&&("Y").equals(systemSettingPrecise))){
        String systemSettingValue = Postern.getSystemsettingValueByName("SET_W_AUTOTRANSFER");
	if(systemSettingValue == null || ("N").equals(systemSettingValue)){%>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr> 
			    <td colspan="2" class="import_tit" align="center"><font size="3">工单手工流转</font></td>
			    <input type="hidden" name="selType" value="manual"  >
		        </tr>
			<tr> 
			    <td colspan="2" class="list_bg1" align="center"></td>
		        </tr>    
			<tr>
			    <td class="list_bg2"><div align="right">流转部门*</td>
			    <td class="list_bg1" > 
				 <input type="hidden" name="txtNextOrgID" value="<tbl:writeparam name="txtNextOrgID" />">
			    	 <input type="text" name="txtOrgDesc" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtNextOrgID" />" class="text">
			    	 <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D,S,O','txtNextOrgID','txtOrgDesc')">
			    </td>		
			</tr>		
	</table> 
    <%}else if(systemSettingValue != null && ("Y").equals(systemSettingValue)){
       String custid = (request.getParameter("txtCustomerID") == null ? "0":request.getParameter("txtCustomerID"));
       int autoOrgid=Postern.getAutoNextOrgID("I",null,null,null,null,Postern.getDistrictIDByCustomerID(custid),0);%>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr> 
			    <td colspan="2" class="import_tit" align="center"><font size="3">工单自动流转</font></td>
			    <input type="hidden" name="selType" value="auto"  >
		        </tr>
			<tr>
			    <td class="list_bg2"><div align="right">流转部门*</td>
			    <td class="list_bg1" > 
			 	<input type="hidden" name="txtAutoNextOrgID" value="<%=autoOrgid%>">
		    		<input type="text" name="txtNextOrgName" size="22" maxlength="50" readonly value="<%=Postern.getOrganizationDesc(autoOrgid)%>" class="text">
		    		<input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:mod_organization()">
			    </td>		
			</tr>		
	</table>
    <%}}%>  
      
    <tbl:hiddenparameters pass="ProductList/CampaignList/DeviceProductIds/DeviceClassList/TerminalDeviceList/sspanList/txtCustomerID/txtIsInstall/txtAccount/txtPreferedTime/txtPreferedDate/phoneNo/itemID" />
    <tbl:hiddenparameters pass="txtContactPhone/txtContactPerson/txtComments/txtForceDepostMoney/txtCustType/txtOpenSourceType/txtCsiCreateReason/txtUsedMonth/txtBuyNum" />
    <tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" /> 
    <input type="hidden" name="txtActionType" value="<%=CommonKeys.OPEN_BATCHBUY_SERVICE_ACCOUNT%>" />
    <input type="hidden" name="func_flag" value="1" />
    <input type="hidden" name="confirm_post" value="false" >
    <input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >   
      
    <tbl:generatetoken />
</form> 
    <br>
    <table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input id="prev" name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	   <td width="20" ></td>
	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	   <td><input id="next" name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确 认"></td>
	   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
 </table>   
   
<Script language=JavaScript>
<!--  
function check_transfertype(){
		var str=document.frmPost.selType!=null?document.frmPost.selType.value:""; 
		if(  'auto'==str && (''==document.frmPost.txtAutoNextOrgID.value||0==document.frmPost.txtAutoNextOrgID.value)){
			alert("无法匹配合适的处理部门，请手工流转并指定流转部门！");
			return false;
		}
		if('manual'==str){
			if(''==document.frmPost.txtNextOrgID.value){
			       	alert("手工流转需指定流转部门！");
			       	return false;
		       	}
		}
	document.frmPost.transferType.value=str;
	return true;
}
function mod_organization(){
	var strFeatures = "width=480px,height=240px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
	window.open("transfer_org_list.do?strRole=I","流转部门",strFeatures);
} 
function frm_submit()
{
	if(check_transfertype()){
	  document.frmPost.confirm_post.value="true"
	  document.frmPost.submit();
	  document.getElementById('prev').disabled=true;
		document.getElementById('next').disabled=true;
	}
}
function back_submit(){
	if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>'){
		document.frmPost.action="open_service_account_fee_batchbuy.screen";
	}else{
		document.frmPost.action="open_service_account_pay_batchbuy.screen";
	}
	document.frmPost.submit();
}
//-->
</Script>      