<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="com.dtv.oss.service.component.ImmediateCountFeeInfo" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">帐户--取消帐户</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="" >
  <input type="hidden" name="func_flag" value="1">
  <input type="hidden" name="txtAccountID" value="<tbl:writeparam name="txtAcctID" />">
  <tbl:hiddenparameters pass="txtMopID/forwardFlag/txtCustomerID/txtAcctID/txtAddressId/txtAccountDtLastmod/txtAddressDtLastmod" />
  <tbl:hiddenparameters pass="txtAccountTypeShow/txtPostcode/txtCreateTime/txtStatus/txtBalance/txtBBF/txtCashDeposit" />
  <tbl:hiddenparameters pass="txtTokenDeposit/txtBankAccountStatus/txtBankAccount/txtAccountName/txtDetailAddress" />
  <%
       CommandResponse RepCmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
       if (RepCmd ==null) RepCmd = (CommandResponse)(request.getSession().getAttribute(CommonKeys.FEE_SESSION_NAME));
       if (RepCmd ==null) RepCmd =new CommandResponse(new ArrayList());
       request.getSession().setAttribute(CommonKeys.FEE_SESSION_NAME,RepCmd);
       String[] wrap_pay_realMOP =request.getParameterValues("wrap_pay_realMOP");
		   String[] wrap_pay_realaddInfo =request.getParameterValues("wrap_pay_realaddInfo");
		   String[] wrap_pay_realPay =request.getParameterValues("wrap_pay_realPay");
		  
		  if (wrap_pay_realMOP !=null){
		     for (int i=0; i<wrap_pay_realMOP.length; i++){
  %>
         <input type="hidden" name="pay_realMOP"  value="<%=wrap_pay_realMOP[i]%>">
         <input type="hidden" name="pay_realaddInfo" value="<%=wrap_pay_realaddInfo[i]%>" >
         <input type="hidden" name="pay_realpay" value="<%=wrap_pay_realPay[i]%>" >
  <%
        }
      }
      
  %>
</form>
<Script language=JavaScript>
  
  function back_submit(){
      document.frmPost.action ="account_cancel_step1.screen";  
	    document.frmPost.submit();
  }
  back_submit();
</Script>