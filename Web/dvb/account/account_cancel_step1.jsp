<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<Script language=JavaScript>
  function check_frm(){
      if (check_fee()) return true;
      return false;
  }
  function cancel_next() {
     if (check_frm()){
         document.frmPost.action ="account_cancel_step2.do";
         document.frmPost.submit();
     }
  }
   
  function back_submit(){
	   document.frmPost.action="account_cancel.screen";
	   document.frmPost.submit();
  }
</Script>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">取消帐户--费用支付</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="" >
  <input type="hidden" name="func_flag" value="1">
  <input type="hidden" name="acceptPayFlag" value="false" >
  <input type="hidden" name="readyForeturnFeeFlag" value="true"> 
  <tbl:hiddenparameters pass="forwardFlag/txtCustomerID/txtAcctID/txtMopID/txtAddressId/txtAccountDtLastmod/txtBillingCycleTypeShow/txtAddressDtLastmod" />   
  <table align="center" width="810" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
     <tr ><td>
        <jsp:include page = "account_view.jsp"/>
     </td></tr>
     <tr ><td>
       <tbl:CommonFeeAndPaymentInfo includeParameter="CloseAcctFee_step1" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_OC%>" checkMoneyFlag="2"/>		 
     </td></tr>
  </table>
  <BR>  
  <table align="center"  border="0" cellspacing="0" cellpadding="0">
    <tr>       
      
         <td><img src="img/button2_r.gif" width="22" height="20"></td>
         <td ><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
           <td width="20" ></td> 
       <td><img src="img/button_l.gif" width="11" height="20"></td>
       <td><input name="next" type="button" class="button" onClick="cancel_next()" value="下一步"></td>
      <td><img src="img/button_r.gif" width="22" height="20"></td>  
    </tr>
   </table>
</form>