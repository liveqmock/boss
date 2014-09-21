<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys,com.dtv.oss.web.util.WebKeys" %>
<Script language=JavaScript>
  function check_frm(){
      return true;
  }
  function cancel_account() {
     if (check_frm()){
        if (confirm("您确定要取消该帐户吗？")) {
           document.frmPost.action ="account_cancel_op.do";
           document.frmPost.submit();
        }
     }
  }
  function cancel_prev(){
  	<%
  	  String requestAttribute =(request.getAttribute(WebKeys.REQUEST_ATTRIBUTE) ==null) ? "0" : "1";
  	%>
  	 request_attr ="<%=requestAttribute%>";
     if (request_attr =="0"){
        document.frmPost.action ="account_cancel_step2.screen";
     } else{
        document.frmPost.confirm_post.value ="false";
        document.frmPost.action ="account_cancel_wrap_step1data.do";
     }
     document.frmPost.submit(); 
  }
</Script>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">取消帐户--信息确认</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="" >
  <input type="hidden" name="func_flag" value="1">
  <input type="hidden" name="confirm_post"  value="true" >
  <tbl:hiddenparameters pass="txtMopID/forwardFlag/txtCustomerID/txtAcctID/txtAddressId/txtAccountDtLastmod/txtAddressDtLastmod" />
  <table align="center" width="810" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
     <tr ><td>
        <jsp:include page = "account_view.jsp"/>
     </td></tr>
     <tr ><td>
       <tbl:CommonFeeAndPaymentInfo includeParameter="CloseAcctFee_step3" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_OC%>"  />	
     </td></tr>
  </table>
  <BR>  
  <table align="center"  border="0" cellspacing="0" cellpadding="0">
    <tr>      
      <td><img src="img/button_l.gif" width="11" height="20"></td>
      <td><input name="next" type="button" class="button" onClick="cancel_prev()" value="上一步"></td>
      <td><img src="img/button_r.gif" width="22" height="20"></td>  
      <td width="20">&nbsp;</td>
      <td><img src="img/button_l.gif" width="11" height="20"></td>
      <td><input name="next" type="button" class="button" onClick="cancel_account()" value="确 认"></td>
      <td><img src="img/button_r.gif" width="22" height="20"></td>  
    </tr>
   </table>
   <tbl:generatetoken />
</form>