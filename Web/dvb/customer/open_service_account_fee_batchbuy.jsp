<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys,
                 com.dtv.oss.web.util.WebKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO,
                 com.dtv.oss.dto.DeviceModelDTO,
                 com.dtv.oss.web.util.WebUtil" %>

<form name="frmPost" method="post" action="" >     
     <table  border="0" align="center" cellpadding="0" cellspacing="5">
        <tr>
          <td><img src="img/list_tit.gif" width="19" height="19"></td>
          <td class="title">收费信息</td>
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

		<%
		String packageList=request.getParameter("txtAllPackageList");
		System.out.println("packageList>>>>>>>>>>"+packageList);
		%>
     <tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UO%>" acctshowFlag ="true" packageList="<%=packageList %>" />  
     
     <tbl:hiddenparameters pass="ProductList/CampaignList/DeviceProductIds/DeviceClassList/TerminalDeviceList/sspanList/txtCustomerID/txtIsInstall/txtAccount/txtPreferedTime/txtPreferedDate/phoneNo/itemID" />
     <tbl:hiddenparameters pass="txtContactPhone/txtContactPerson/txtComments/txtForceDepostMoney/txtCustType/txtOpenSourceType/txtCsiCreateReason/txtUsedMonth/txtBuyNum" />
     <input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_BATCHBUY_PRODUCTINFO%>" />
     <input type="hidden" name="txtConfirmBackFlag" value="" />
     <input type="hidden" name="confirm_post" value="false" >
     <input type="hidden" name="txtcsiPayOption" >  
     <tbl:generatetoken />
</form> 

<table align="center" border="0" cellspacing="0" cellpadding="0">
   <tr>
      <td><img src="img/button2_r.gif" width="22" height="20"></td>
      <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
      <td><img src="img/button2_l.gif" width="11" height="20"></td>
	 
      <jsp:include page = "/fee/common_control.jsp"/>
   </tr>
</table>   
   
<Script language=JavaScript>
<!--  

function back_submit(){
   document.frmPost.action="open_service_account_product_batchbuy.do";
	document.frmPost.submit();
}

function frm_next(csiPayOption){
	    document.frmPost.txtcsiPayOption.value =csiPayOption;
	    if (check_fee()){
	       document.frmPost.action="open_service_account_pay_batchbuy.screen";
	    }else{
	 	     document.frmPost.action="open_service_account_fee_confirm_batchbuy.screen";
	    	 document.frmPost.confirm_post.value="false";
	    	 document.frmPost.txtConfirmBackFlag.value="caculatefee";
	    }
	    document.frmPost.submit();
}

function frm_finish(csiPayOption){
	    document.frmPost.txtcsiPayOption.value =csiPayOption;
	    document.frmPost.action="open_service_account_fee_confirm_batchbuy.screen";
	    document.frmPost.confirm_post.value="false";
	    document.frmPost.txtConfirmBackFlag.value="caculatefee";
	    document.frmPost.submit();
}
//-->
</Script>      