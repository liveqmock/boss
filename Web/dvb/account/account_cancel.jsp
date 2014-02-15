<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<Script language=JavaScript>
  function check_frm(){
       return true;
  }
  function cancel_next() {
     if (check_frm()){
           document.frmPost.action ="account_cancel_step1.do";
           document.frmPost.submit();
        }
  }
  
  function back_submit(){
      var txtID;
      txtID = document.frmPost.txtAccountID.value;     
	    document.frmPost.action="account_view.do?"+txtID;
	    document.frmPost.submit();
  }
</Script>
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
  <input type="hidden" name="confirm_post"  value="false" >
  <tbl:hiddenparameters pass="forwardFlag/txtCustomerID/txtAcctID/txtBillingCycleTypeShow/txtMopID/txtAddressId/txtAccountDtLastmod/txtAddressDtLastmod" />
  <table align="center" width="810" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
     <tr ><td>
        <jsp:include page = "account_view.jsp"/>
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
           <td><input name="next" type="button" class="button" onClick="javascript:cancel_next()" value="下一步"></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>     
        
    </tr>
   </table>
   <tbl:generatetoken />
</form>