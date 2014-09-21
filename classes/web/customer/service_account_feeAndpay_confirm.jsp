<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%

String strActionType = request.getParameter("txtActionType");

int iActionType = 0;

%>
<Script language=JavaScript>
<!--  
function check_frm(){
	return true;
	
}

function frm_submit(){
	if (check_frm()) document.frmPost.submit();
}
function back_submit(){
	if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>'){
		document.frmPost.action="service_account_fee_view.do";
	}else{
		document.frmPost.action="service_account_feeAndpay.do";
	}
//	document.frmPost.action="service_account_feeAndpay.do";
	document.frmPost.txtDoPost.value="FALSE";
	document.frmPost.submit();
}
function frm_submit_1(){
	 if (check_frm()){
		  document.frmPost.action ="service_account_op_jobcard.do";
		  document.frmPost.submit();
	 }
}
//-->
</Script>
<form name="frmPost" method="post" action="service_account_op.do" >     
 <table  border="0" align="center" cellpadding="0" cellspacing="5">
    <tr>
      <td><img src="img/list_tit.gif" width="19" height="19"></td>
      <td class="title">确认信息</td>
    </tr>
  </table>
  <br>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
   </table>
   <br>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2"><div align="right">业务帐户ID</div></td>
    <td class="list_bg1"><input type="text" name="txtSAID" size="25"  value="<tbl:writeparam name="txtSAID"/>" class="textgray" readonly ></td>
    <td class="list_bg2"><div align="right">业务名称</div></td>
    <td class="list_bg1"><input type="text" name="txtSAName" size="25"  value="<tbl:writeparam name="txtSAName"/>" class="textgray" readonly ></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">一次性费帐户</div></td>
    <td class="list_bg1"><input type="text" name="txtAccount" size="25"  value="<tbl:writeparam name="txtAccount"/>" class="textgray" readonly ></td>
    <td class="list_bg2"><div align="right">创建日期</div></td>
    <td class="list_bg1"><input type="text" name="txtSAC" size="25"  value="<tbl:writeparam name="txtSAC"/>" class="textgray" readonly ></td>
  </tr>
 </table>
 
  <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UO%>" acctshowFlag ="true" confirmFlag="true" />		        
  <tbl:hiddenparameters pass="txtCustomerID/txtServiceAccountID/txtActionType/func_flag/txtIsInstall/txtPreferedDate/txtPreferedTime/txtSAID/txtSAName/txtSAC/txtIsInstallName/txtPreferedTimeName/mopid/tickettype/paymentType/txtTransferType/txtCsiCreateReason" />
  <tbl:hiddenparameters pass="txtIsSendBack/txtDeviceFee/txtComments" />

<input type="hidden" name="txtDoPost" value="TRUE" >
<input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >  
<tbl:generatetoken />

<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr>
   <td><img src="img/button2_r.gif" width="22" height="20"></td>
   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td>
   <td width="20" ></td>
   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确 认"></td>
   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
   <%
      int saId =(request.getParameter("txtSAID")==null) ? 0 :Integer.parseInt(request.getParameter("txtSAID"));
      int serviceId =Postern.getServiceIDByAcctServiceID(saId);
      if (serviceId ==6){
   %>
   <pri:authorized name="service_account_op_jobcard.do" >
   <td width="20" ></td>
   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit_1()" value="确认且开工单"></td>
   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
   </pri:authorized>
   <%
     }
   %>
</tr>
</table>  
</form> 

