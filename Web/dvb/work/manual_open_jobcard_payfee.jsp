<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<script language=javascript>
<!--
function frm_next(){
    document.frmPost.action="manual_open_jobcard_confirm.do";
    document.frmPost.submit();
}
function back_submit() {  
	document.frmPost.action="manual_open_jobcard_calculatefee.screen";
	document.frmPost.submit();
}
-->
</script>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
		<table  border="0" align="center" cellpadding="0" cellspacing="10" >
		  <tr>
		    <td><img src="img/list_tit.gif" width="19" height="19"></td>
		    <td class="title">手工开单费用支付</td>
		  </tr>
		</table>
		<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
		  <tr>
		    <td><img src="img/mao.gif" width="1" height="1"></td>
		  </tr>
		</table>
		<br>
		<form name="frmPost"  method="post" action="">
		<input type="hidden" name="confirm_post" value="FALSE">
        <tbl:hiddenparameters pass="txtJobType/txtSubtype/txtAccount/txtManualCreateReason/txtAddPortNumber/txtServiceAccountID/txtComments/txtCustomizeFee/txtOldDistrictID/txtOldDetailAddress" />
        <tbl:hiddenparameters pass="txtCustomerID/txtCatvID/txtName/txtAddressID/txtDetailAddress/txtDistrictID/txtTelephone/txtTelephoneMobile/txtPreferedDate/txtPreferedTime" />
        <tbl:hiddenparameters pass="txtCustomerType/txtOpenSourceType/txtCreateJobForCust/txtScreenDirect" />
        <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
           <tr ><td> 
           	 <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.METHODOFPAYMENT_KHGD%>" acctshowFlag ="true"  confirmFlag="false"  />		 
           </td></tr> 
        </table>
	    </form> 
	    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
          <tr>
            <td><img src="img/mao.gif" width="1" height="1"></td>
          </tr>
        </table>
	    <table align="center" border="0" cellspacing="0" cellpadding="0">
            <BR>
            <tr>
               <td><img src="img/button2_r.gif" width="22" height="20"></td>
               <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
               <td><img src="img/button2_l.gif" width="11" height="20"></td>
               <td width="20" ></td>
               <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
               <td><input name="next" type="button" class="button" onClick="javascript:frm_next()" value="下一步"></td>
               <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
            </tr>
       </table>     
    </TD>
</TR>
</TABLE>