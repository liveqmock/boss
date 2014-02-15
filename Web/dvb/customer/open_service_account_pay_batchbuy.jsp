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


     <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UO%>" acctshowFlag ="true" confirmFlag="false" />		 

      <tbl:hiddenparameters pass="ProductList/CampaignList/DeviceProductIds/DeviceClassList/TerminalDeviceList/sspanList/txtCustomerID/txtIsInstall/txtAccount/txtPreferedTime/txtPreferedDate/phoneNo/itemID" />
      <tbl:hiddenparameters pass="txtContactPhone/txtContactPerson/txtComments/txtForceDepostMoney/txtCustType/txtOpenSourceType/txtCsiCreateReason/txtUsedMonth/txtBuyNum" />
       
      <tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" /> 
      <input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >   
</form> 
    <br>
    <table align="center" border="0" cellspacing="0" cellpadding="0">
   	   <tr>
	       <td><img src="img/button2_r.gif" width="22" height="20"></td>
	       <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
	       <td><img src="img/button2_l.gif" width="11" height="20"></td>
	       <td width="20" ></td>
	       <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	       <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
	       <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	     </tr>
    </table>   
   
<Script language=JavaScript>
<!--  
function check_frm(){
	if (!check_fee()) return false;
	return true;
   
}

function frm_submit(){
	if (check_frm()) {
		 document.frmPost.action ="open_service_account_fee_confirm_batchbuy.screen";
	   document.frmPost.submit();
	}
}
function back_submit(){
	 document.frmPost.action ="open_service_account_fee_batchbuy.screen";
	 document.frmPost.submit();
}
//-->
</Script>      