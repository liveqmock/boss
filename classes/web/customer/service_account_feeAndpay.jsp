<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<form name="frmPost" method="post" action="service_account_feeAndpay_confirm.do" >     
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
     <table border="0" cellspacing="0" cellpadding="0" width="820">
        <tr><td><font color="red">&nbsp;&nbsp;说明：费用为负表示应退给客户</font></td></tr>
     </table>        
     <tbl:hiddenparameters pass="txtCustomerID/txtServiceAccountID/txtAccount/txtActionType/func_flag/txtSAID/txtSAName/txtSAC/txtIsInstallName/txtPreferedTimeName/txtTransferType/txtCsiCreateReason" />
     <tbl:hiddenparameters pass="txtIsSendBack/txtDeviceFee/txtComments" />

     <input type="hidden" name="txtDoPost" value="FALSE" >
     <input type="hidden" name="txtInstallationType" value="<tbl:writeparam name="txtIsInstall" />" >
     <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UO%>" acctshowFlag ="true"  confirmFlag="false" checkMoneyFlag="2" />		 
     
     <input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >	  
<br>         
<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr>
	 <td><img src="img/button2_r.gif" width="22" height="20"></td>
   <td><input name="prev" type="button" class="button" onclick="javascript:javascript:prev_submit()" value="上一步"></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td>
   <td width="20" ></td>
   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
</tr>
</table>
</form> 

<Script language=JavaScript>
<!--  

function frm_submit(){
	 if (check_fee()){
	    document.frmPost.submit();
	 }
}
function prev_submit(){
		document.frmPost.action = "service_account_fee_view.do";
    document.frmPost.submit();
}

//-->
</Script>
      