<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<Script language=JavaScript>
<!--  
function check_frm()
{
	return true;
	
}

function frm_submit()
{
	if (check_frm()) document.frmPost.submit();
}
function back_submit()
{
	if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>'){
		document.frmPost.action="service_account_transfer_create.do";
	}else{
		document.frmPost.action="service_account_transfer_fee.do";
	}	
//	document.frmPost.action="service_account_transfer_fee.do";
	document.frmPost.txtDoPost.value="FALSE";
	document.frmPost.submit();
}

//-->
</Script>
<form name="frmPost" method="post" action="service_account_transfer_op.do" >     
<input type="hidden" name="txtDoPost" value="TRUE" >

<tbl:hiddenparameters pass="txtCustomerID/txtServiceAccountID/txtAccount/txtActionType/txtTransferType/txtSAID/txtSAName/txtSAC/txtNewCustomerID/txtNewAccountID" />

 <table  border="0" align="center" cellpadding="0" cellspacing="5">
    <tr>
      <td><img src="img/list_tit.gif" width="19" height="19"></td>
      <td class="title">确认信息</td>
    </tr>
  </table>
  <br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
   </table>
   <br>
 <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width="17%"><div align="right">业务帐户ID</div></td>
    <td class="list_bg1" width="33%"><tbl:writeparam name="txtSAID"/></td>
    <td class="list_bg2" width="17%"><div align="right">业务名称</div></td>
    <td class="list_bg1" width="33%"><tbl:writeparam name="txtSAName"/></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">一次性费帐户</div></td>
    <td class="list_bg1"><tbl:writeparam name="txtAccount"/></td>
    <td class="list_bg2"><div align="right">创建日期</div></td>
    <td class="list_bg1"><tbl:writeparam name="txtSAC"/></td>
  </tr>
  <tr>
  	<tbl:csiReason csiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UT%>" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" forceDisplayTD="true" tdControlColspan="3" showType="label"  tdWordStyle="list_bg2" tdControlStyle="list_bg1" />
  </tr>
 </table>
<%
	if(CommonKeys.SA_TRANFER_TYPE_O.equals(request.getParameter("txtTransferType"))){
%>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
      <td>
			<jsp:include page="open_create_confirm_sa_tranfer.jsp" />
      </td>
    </tr>
   </table>	
	<!--系统外过户要的参数-->
<tbl:hiddenparameters pass="txtName/txtGender/txtCustType/txtTelephone/txtMobileNumber/txtBirthday/txtDetailAddress/txtCatvID/txtOpenSourceType/txtOpenSourceID/txtGroupBargainDetailNo/txtCounty/txtCardType/txtCardID/txtForceDepostMoney/txtSocialSecCardID/txtEmail/txtNationality/txtFaxNumber/txtPostcode/txtComments/txtBillDetailAddress/txtBillPostcode/txtbillCounty/txtMopID/txtBankAccount/txtBankAccountName/txtAccountType/txtAccountName" />

<% 	} else{ %>

 <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr><td class="import_tit" align="center" colspan="5"><font size="3">新客户信息</font></td></tr>
    <tr>
    <td class="list_bg2" width="17%"><div align="right">客户证号</div></td>
    <td class="list_bg1" width="33%"><input type="text" name="txtNewCustomerID" size="25"  value="<tbl:writeparam name="txtNewCustomerID"/>" class="textgray" readonly ></td>
    <td class="list_bg2" width="17%"><div align="right">帐户号</div></td>
    <td class="list_bg1" width="33%"><input type="text" name="txtNewAccountID" size="25"  value="<tbl:writeparam name="txtNewAccountID"/>" class="textgray" readonly ></td>

</table>
<%}%>     

  <tbl:hiddenparameters pass="txtContractNo/txtCsiCreateReason" />
  <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UT%>" acctshowFlag ="true"  confirmFlag="true" />		 		
  <input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >
  <tbl:generatetoken />

<br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
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
   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确  定"></td>
   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
</tr>
</table>  
</form> 

