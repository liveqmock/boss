<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<script language=javascript>
<!--
function back_submit() {
    document.frmPost.confirm_post.value="false";
    document.frmPost.forwardFlag.value = '2';
    if(document.frmPost.transferType.value=='strange'){
    	document.frmPost.action="customer_strange_transfer_fee.screen";
  	}else{
    	document.frmPost.action="customer_vicinal_transfer_fee.screen";
 		}
    document.frmPost.submit();
}
function check_form(){
 	return true;
}
function frm_submit()
{
	if (check_form()) {
	    if(check_fee()){
	     	if(document.frmPost.transferType.value=='strange'){
    			document.frmPost.action="customer_strange_transfer_confirm.screen";
  			}else{
    			document.frmPost.action="customer_vicinal_transfer_confirm.screen";
 				}
	    	document.frmPost.submit();
	    }
	}    
}

//-->
</SCRIPT>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户过户--收费信息</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<form name="frmPost" method="post" action="customer_vicinal_transfer_create_confirm.screen">
  <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UT%>" acctshowFlag ="true"  confirmFlag="false" />		 
  <input type="hidden" name="forwardFlag" value="">
  <input type="hidden" name="confirm_post" value="false">

  <tbl:hiddenparameters pass="transferType/txtcsiPayOption" />
  <tbl:hiddenparameters pass="txtCustomerSerialNo/txtContractNo/txtSocialSecCardID/txtEmail/txtNationality/txtFaxNumber"/>
  <tbl:hiddenparameters pass="forwardFlag/mopid/tickettype/txtCustomerDTlastmod/txtAddressDTlastmod/txtCustomerID/txtAddressID/txtInstallBill/txtNewName/txtOldDetailAddress/txtCustomerStatusName/txtCustomerStatus/txtNewGender/txtNewDistrict/txtNewDistrictDesc/txtDetailAddress/txtNewPostcode/txtCatvID/txtNewBirthday/txtCustAccount/txtNewCardType/txtNewCardID/txtNewTelephone/txtNewTelephoneMobile/txtName/txtCustomerTypeShow/txtCustomerType/txtDistrict/txtDistrictName/txtPostcode/txtIsInstall/txtIsInstallName/txtPreferedDate/txtPreferedTime/txtPreferedTimeName/txtLoginID/txtLoginPwd/txtOpenSourceType" />

  <tbl:dynamicservey serveyType="M"  showType="hide" tdWordStyle="list_bg2" tdControlStyle="list_bg1"  controlSize="23"/>

  <br>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	  <tr>
	    <td><img src="img/mao.gif" width="1" height="1"></td>
	  </tr>
	</table>
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

</form>



