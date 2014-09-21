<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<Script language=JavaScript>
function frm_submit()
{
	 if (check_fee()){
      document.frmPost.submit();
   }
}
function back_submit()
{
	document.frmPost.action="service_account_resume_create.do";
	document.frmPost.submit();
}
</Script>

<form name="frmPost" method="post" action="service_account_resume_confirm.do" >     
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

      <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UR%>" acctshowFlag ="true"  confirmFlag="false" />		 		
          
      <tbl:hiddenparameters pass="txtCustomerID/txtServiceAccountID/txtCPIDs/txtAccount/txtSAID/txtSAName/txtSAC/txtActionType/txtCsiCreateReason" />
      <input type="hidden" name="txtDoPost" value="FALSE" >
      <input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >

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

