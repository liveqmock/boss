<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%

String strActionType = request.getParameter("txtActionType");
String strCsitype ="";
int iActionType = 0;
if ((strActionType!=null)&&(strActionType.compareTo("")!=0)){
   try
   {
	   iActionType=Integer.valueOf(strActionType).intValue();
   }
   catch (Exception ex)
   {
   }
   switch(iActionType){
	   case CommonKeys.SERVICE_ACCOUNT_PAUSE:
		   strCsitype =CommonKeys.CUSTSERVICEINTERACTIONTYPE_UP;
		   break;
	   case CommonKeys.SERVICE_ACCOUNT_CLOSE:
		   strCsitype =CommonKeys.CUSTSERVICEINTERACTIONTYPE_UC;
		   break;
	   case CommonKeys.SERVICE_ACCOUNT_RENT:
		   strCsitype =CommonKeys.CUSTSERVICEINTERACTIONTYPE_RT;
		   break;
	   case CommonKeys.SERVICE_ACCOUNT_BEFOREHAND_CLOSE:
		   strCsitype =CommonKeys.CUSTSERVICEINTERACTIONTYPE_SP;
		   break;
  }
}
%>

<form name="frmPost" method="post" action="service_account_pause_confirm.screen" >     
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

     <tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=strCsitype%>" acctshowFlag ="true"  />  
     <tbl:hiddenparameters pass="txtCustomerID/txtServiceAccountID/txtAccount/txtActionType/func_flag/txtSAID/txtSAName/txtSAC/txtIsInstallName/txtPreferedTimeName/txtCsiCreateReason" />
     <tbl:hiddenparameters pass="txtIsSendBack/txtDeviceFee/txtComments" />
     <input type="hidden" name="txtDoPost" value="FALSE" >
     <input type="hidden" name="txtConfirmBackFlag" value="" >
     <input type="hidden" name="txtInstallationType" value="<tbl:writeparam name="txtIsInstall" />" >
     <input type="hidden" name="txtcsiPayOption" >

    <br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
      <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>  
      <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>

      <jsp:include page = "/fee/common_control.jsp"/> 
    </tr>
 </table>
  <tbl:generatetoken />
</form> 

<Script language=JavaScript>
<!--  

function frm_next(csiPayOption){
	 document.frmPost.txtcsiPayOption.value =csiPayOption;
	 if (check_fee()){
	    document.frmPost.action="service_account_feeAndpay.do";
	 }else{
	 	  document.frmPost.action="service_account_feeAndpay_confirm.do";
			document.frmPost.txtConfirmBackFlag.value="caculatefee"
//	 	  document.frmPost.action="service_account_op.do";
//	 	  document.frmPost.txtDoPost.value="true";
	 }
	 document.frmPost.submit();
}

function frm_finish(csiPayOption){
	 document.frmPost.txtcsiPayOption.value =csiPayOption;
	 document.frmPost.action="service_account_feeAndpay_confirm.do";
	 document.frmPost.txtConfirmBackFlag.value="caculatefee"
//	 document.frmPost.action="service_account_op.do";
//	 document.frmPost.txtDoPost.value="true";
	 document.frmPost.submit();
}

function back_submit(){
	document.frmPost.action="service_account_pause_view.do";
	document.frmPost.submit();
}
//-->
</Script>
      