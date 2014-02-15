<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<%
    String title ="模拟电视开户--确认信息";
    String funcFlag ="106";    
    String phoneNo = request.getParameter("phoneNo");
    boolean hasPhoneNo = true;
    if(phoneNo == null || "".equals(phoneNo) || "null".equals(phoneNo))
    	 hasPhoneNo = false;
%>

<Script language=JavaScript>
function back_submit(){
	if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>')
		document.frmPost.action="open_catv_create_product_fee.screen";
	else
		document.frmPost.action="open_catv_create_product_pay.screen";
	document.frmPost.submit(); 
}  
function check_frm(){
	if(document.frmPost.func_flag.value == "106"){
		if(check_transfertype())
		   	return true;		   	
	   	else
	   		return false;
	}
	return false;
}
function frm_submit(){
	document.frmPost.confirm_post.value="true";
	document.frmPost.action="open_catv_submit.do";
	if (check_frm()){
		document.frmPost.submit();
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
      	String catvFieldName =Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID");
      %>
      <tr ><td>
      	 <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr> 
            <td colspan="4" class="import_tit" align="center"><font size="3">终端信息</font></td>
         </tr>
         <tr> 
            <td  align="right" class="list_bg2" width="17%"><font size="2"><%=catvFieldName%></font></td> 
            <td  class="list_bg1" width="33%"><font size="2"><tbl:writeparam name="txtCatvID" /></font></td>
            <td  align="right" class="list_bg2" width="17%"><font size="2">新增端口数</font></td> 
            <td  class="list_bg1" width="33%"><font size="2"><tbl:writeparam name="txtAddPortNum" /></font></td>
        </tr>
        </table>
        </td></tr>

        <tr><td> 
          <jsp:include page="customer_buyProductPropertyInfo.jsp"/>
        </td></tr>
        <tr ><td> 
          <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_OS%>" acctshowFlag ="false"  confirmFlag="true" />
        </td></tr>
    
    </table>
     <%String strIsinstall=(request.getParameter("txtIsInstall")==null?"":request.getParameter("txtIsInstall"));
     String systemSettingPrecise = Postern.getSystemsettingValueByName("SET_W_PRECISEWORK");
     if(systemSettingPrecise!=null&&("Y").equals(systemSettingPrecise)){
     	if ("C".equals(strIsinstall)){ 
		String systemSettingValue = Postern.getSystemsettingValueByName("SET_W_AUTOTRANSFER");
		if(systemSettingValue == null || ("N").equals(systemSettingValue)){%>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
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
       String strRole=null;
       String subRole=null;
       if(Postern.IsExistCatvID(request.getParameter("txtCatvID"))){
       		strRole="M";
       		if(WebUtil.StringToInt(request.getParameter("txtAddPortNum"))>0)
       			subRole="Z";
       		else
       			subRole="H";
       }
       else{
       		strRole="I";
       }
       int autoOrgid=Postern.getAutoNextOrgID(strRole,subRole,null,null,null,WebUtil.StringToInt(districtID),9999);%>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr> 
			    <td colspan="2" class="import_tit" align="center"><font size="3">工单自动流转</font></td>
			    <input type="hidden" name="selType" value="auto">
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
    <%}}}%>   
      <BR>
      <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
           <td width="20" ></td>
           <td><img src="img/button_l.gif" width="11" height="20"></td>
	   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确 认"></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>
        </tr>
      </table>
      

         
      <tbl:hiddenparameters pass="txtCatvID/txtGroupBargainDetailNo/txtReferBookingSheetID/txtCsiCreateReason/txtCsiType" />
      <tbl:hiddenparameters pass="txtCustType/txtGender/txtName/txtBirthday/txtCardType/txtCardID/txtTelephone/txtMobileNumber/txtEmail" />
      <tbl:hiddenparameters pass="txtContractNo/txtCounty/txtDetailAddress/txtPostcode/txtOpenSourceType/txtOpenSourceID/txtComments/txtLoginID/txtLoginPwd" />
      <tbl:hiddenparameters pass="txtPreferedTime/txtPreferedDate/txtSocialSecCardID/txtNationality/txtFaxNumber" />
      <tbl:hiddenparameters pass="txtBillDetailAddress/txtBillPostcode/txtbillCounty/txtMopID/txtBankAccount/txtBankAccountName/txtAccountType/txtAccountName" />
      <tbl:hiddenparameters pass="txtBuyMode/OpenFlag/txtIsInstall/txtForceDepostMoney/sspanList" />
      <tbl:hiddenparameters pass="ProductList/CampaignList" />
      <tbl:hiddenparameters pass="DeviceProductIds/DeviceClassList/TerminalDeviceList/phoneNo/itemID/txtGrade/txtcsiPayOption/txtServiceCodeList" />
      
      <tbl:hiddenparameters pass="txtAddPortNum/txtCatvTermType/txtFiberNodeID/txtCableType/txtBiDirectionFlag/txtFiberNode" />
           
      <input type="hidden" name="confirm_post"  value="true" >
      <input type="hidden" name="func_flag" value="<%=funcFlag%>">
      <input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_HARDPRODUCTINFO%>" />
   <tbl:generatetoken />   
</form>