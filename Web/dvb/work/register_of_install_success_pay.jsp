<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys" %>

<Script language=JavaScript> 
   function frm_submit(){
	   if (check_fee()){
	   	  document.frmPost.action ="register_of_install_success_confirm.screen";
	      document.frmPost.submit();
	   }
   }
   
   function back_submit(){
	    document.frmPost.action="register_of_install_success_fee.screen";
	    document.frmPost.submit();
   }

</Script>

<form name="frmPost" method="post" action="" >     
    <table  border="0" align="center" cellpadding="0" cellspacing="5">
       <tr>
         <td><img src="img/list_tit.gif" width="19" height="19"></td>
         <td class="title">录入安装反馈信息--费用支付信息</td>
       </tr>
    </table>
    <br>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
    </table>
    <%
       String csiType = request.getParameter("txtCsiType");
    %>
     
    <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=csiType%>" acctshowFlag ="true"  confirmFlag="false" /> 
    <tbl:hiddenparameters pass="txtJobCardID/txtReferSheetID/txtPreferedDate/txtPreferedTime/txtWorkDate/txtWorkTime/txtProcessMan/txtMemo/txtIsSuccess/txtDtLastmod" />
    <tbl:hiddenparameters pass="txtcsiPayOption/txtCustomerID/txtAccountID/txtServiceID" />
    <input type="hidden" name="txtCsiType" value="<%=csiType%>" >
    <tbl:dynamicservey serveyName="txtDynamicServey" serveyType="U" serveySubType="IU" showType="hide" />
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

