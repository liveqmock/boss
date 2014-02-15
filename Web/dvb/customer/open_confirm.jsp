<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%

    String open_flag =(request.getParameter("OpenFlag")==null) ? "" : request.getParameter("OpenFlag");
    String title ="";
    String funcFlag ="0";
    if  (open_flag.equals(CommonKeys.ACTION_OF_BOOKING)) {
        title ="预约--确认信息";
        funcFlag ="101";
    }
    if  (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)) {
        title ="预约开户--确认信息";
        funcFlag ="102";
    }
    if  (open_flag.equals(CommonKeys.ACTION_OF_SHOPACCOUNT)) {
        title ="门店开户--确认信息";
        funcFlag ="103";
    }
    if  (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGAGENT)) {
        title ="代理商预约--确认信息";
        funcFlag ="104";
    }
    
    String phoneNo = request.getParameter("phoneNo");
    boolean hasPhoneNo = true;
    if(phoneNo == null || "".equals(phoneNo) || "null".equals(phoneNo))
    	 hasPhoneNo = false;
%>

<Script language=JavaScript>
   function back_submit(){
	   var booking =<%=CommonKeys.ACTION_OF_BOOKING%>;
	   var bookingAccount =<%=CommonKeys.ACTION_OF_BOOKINGACCOUNT%>;
	   var shopAccount =<%=CommonKeys.ACTION_OF_SHOPACCOUNT%>;
	   var bookingAgent =<%=CommonKeys.ACTION_OF_BOOKINGAGENT%>;
	   document.frmPost.confirm_post.value="false";
	   var open_flag =<%=open_flag%>;
	   if (open_flag ==booking || open_flag ==bookingAgent) {
	        document.frmPost.txtActionType.value="<%=CommonKeys.CHECK_CUSTOMERINFO%>";
	        if(<%=hasPhoneNo%>)
	        {
	        	document.frmPost.action="book_select_phone_number.screen";
	        }
	        else
	        {
						document.frmPost.action="check_address.do";
					}
	   } else{
	   		if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>'){
	   			 document.frmPost.action="open_create_product_fee.screen";
	   		}else{
	   			 document.frmPost.action="open_create_product_pay.screen";
	   		}
	   }
	   document.frmPost.submit();
	}  
	function check_frm(){
		if(document.frmPost.func_flag.value == "101" || document.frmPost.func_flag.value == "103"){
			if(check_transfertype())
				return true;		   	
		}else{
			return true;
}
return false;
	}
	function frm_submit(){
	   document.frmPost.confirm_post.value="true";
	   document.frmPost.action="open_submit.do";
	   if (check_frm()){
    	document.frmPost.submit();
	   	document.getElementById('prev').disabled=true;
	   	document.getElementById('next').disabled=true;
	  }
	}
	function mod_organization(){
		var strFeatures = "width=480px,height=240px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
	  	window.open("transfer_org_list.do?strRole=I","流转部门",strFeatures);
	}
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
</Script>
    
<form name="frmPost" method="post" action="" > 
<input type="hidden" name="transferType" value="<tbl:writeparam name="transferType"/>"> 
    <table  border="0" align="center" cellpadding="0" cellspacing="5">
       <tr>
         <td><img src="img/list_tit.gif" width="19" height="19"></td>
         <td class="title"><%=title%></td>
       </tr>
    </table> 
    <br>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
    </table>
    <br>

  <table align="center" width="820" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
        <tr ><td>
          <jsp:include page = "customer_basicInfomation.jsp"/>
        </td></tr>
        <tr ><td> 
          <jsp:include page="customer_accountInfomation.jsp"/>
        </td></tr>
       <% 
         if (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT) || open_flag.equals(CommonKeys.ACTION_OF_SHOPACCOUNT)) {   
       %>
        <tr ><td> 
          <jsp:include page="customer_buyProductPropertyInfo.jsp"/>
        </td></tr>
        <pri:authorized name="manual_product_agreement_info.do" >
        <tr><td> 
           <jsp:include page="./agreement_info.jsp?showAgreementType=label" flush="true" />       
        </td></tr> 
        </pri:authorized>
        <tr ><td> 
        	<% 
        	   if (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)){
        	%>
              <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_OB%>" acctshowFlag ="false"  confirmFlag="true" />
          <%
             } else {
          %>
              <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_OS%>" acctshowFlag ="false"  confirmFlag="true" />
          <%
             }
          %>
        </td></tr>
       <% 
         }else {
       %>
        <tr ><td> 
          <jsp:include page="customer_buyProductInfomation.jsp"/>        
        </td></tr> 
        <tr ><td> 
          <tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BK%>" acctshowFlag ="false" />       
        </td></tr>       
      <%}%>
      
    </table>
     <%String strIsinstall=(request.getParameter("txtIsInstall")==null?"":request.getParameter("txtIsInstall"));
     String systemSettingPrecise = Postern.getSystemsettingValueByName("SET_W_PRECISEWORK");
     if ((systemSettingPrecise!=null&&("Y").equals(systemSettingPrecise))&&(open_flag.equals(CommonKeys.ACTION_OF_BOOKING) || (open_flag.equals(CommonKeys.ACTION_OF_SHOPACCOUNT)&&("C".equals(strIsinstall))))) { 
	String systemSettingValue = Postern.getSystemsettingValueByName("SET_W_AUTOTRANSFER");
	if(systemSettingValue == null || ("N").equals(systemSettingValue)){%>
	<table width="820"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr> 
			    <td colspan="2" class="import_tit" align="center"><font size="3">工单手工流转</font></td>
			    <input type="hidden" name="selType" value="manual"  >
		        </tr>
			<tr>
			    <td class="list_bg2"><div align="right">流转部门*</td>
			    <td class="list_bg1" > 
				 <input type="hidden" name="txtNextOrgID" value="<tbl:writeparam name="txtNextOrgID" />">
			    	 <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtNextOrgID" />" class="text">
			    	 <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D,S,O','txtNextOrgID','txtOrgDesc')">
			    </td>		
			</tr>		
	</table> 
    <%}else if(systemSettingValue != null && ("Y").equals(systemSettingValue)){
       String districtID = (request.getParameter("txtCounty") == null ? "0":request.getParameter("txtCounty"));
       int autoOrgid=Postern.getAutoNextOrgID("I",null,null,null,null,WebUtil.StringToInt(districtID),0);%>
	<table width="820"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr> 
			    <td colspan="2" class="import_tit" align="center"><font size="3">工单自动流转</font></td>
			    <input type="hidden" name="selType" value="auto"  >
		        </tr>
			<tr>
			    <td class="list_bg2"><div align="right">流转部门*</td>
			    <td class="list_bg1" > 
			 	<input type="hidden" name="txtAutoNextOrgID" value="<%=autoOrgid%>">
		    		<input type="text" name="txtNextOrgName" size="25" maxlength="50" readonly value="<%=Postern.getOrganizationDesc(autoOrgid)%>" class="text">
		    		<input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:mod_organization()">
			    </td>		
			</tr>		
	</table>
    <%}}%>   
      <BR>
      <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input id="prev" name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
           <td width="20" ></td>
           <td><img src="img/button_l.gif" width="11" height="20"></td>
	   <td><input id="next" name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确 认"></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>
        </tr>
      </table>
      

         
      <tbl:hiddenparameters pass="txtCatvID/txtGroupBargainDetailNo/txtReferBookingSheetID/txtCsiCreateReason/txtCsiType" />
      <tbl:hiddenparameters pass="txtCustomerSerialNo/txtCustType/txtGender/txtName/txtBirthday/txtCardType/txtCardID/txtTelephone/txtMobileNumber/txtEmail" />
      <tbl:hiddenparameters pass="txtContractNo/txtCounty/txtDetailAddress/txtPostcode/txtOpenSourceType/txtOpenSourceID/txtComments/txtLoginID/txtLoginPwd" />
      <tbl:hiddenparameters pass="txtPreferedTime/txtPreferedDate/txtSocialSecCardID/txtNationality/txtFaxNumber" />
      <tbl:hiddenparameters pass="txtBillDetailAddress/txtBillPostcode/txtbillCounty/txtMopID/txtBankAccount/txtBankAccountName/txtAccountType/txtAccountName" />
      <tbl:hiddenparameters pass="txtBuyMode/OpenFlag/txtIsInstall/txtForceDepostMoney/sspanList" />
      <tbl:hiddenparameters pass="ProductList/CampaignList" />
      <tbl:hiddenparameters pass="DeviceProductIds/DeviceClassList/TerminalDeviceList/phoneNo/itemID/txtGrade/txtcsiPayOption/txtServiceCodeList" />
      <tbl:hiddenparameters pass="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>"/>
			<tbl:hiddenparameters pass="txtAgentName/txtAgentTelephone/txtAgentCardType/txtAgentCardId" />
				      	
      <tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" />   
           
      <input type="hidden" name="confirm_post"  value="true" >
      <input type="hidden" name="func_flag" value="<%=funcFlag%>">
      <input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_HARDPRODUCTINFO%>" />
     
</form>