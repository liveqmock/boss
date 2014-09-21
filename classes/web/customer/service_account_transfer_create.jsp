<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>

   <%
    if(CommonKeys.SA_TRANFER_TYPE_O.equals(request.getParameter("txtTransferType"))){  
     //获得用户详细地址校验信息
    java.util.List checkDetailAddressList = (java.util.List)Postern.getCheckDetailAddress(request.getParameter("txtDetailAddress"));
   %>
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
      <%
         if(checkDetailAddressList != null){
      %> 
        <tr> 
            <td colspan="3" class="import_tit" align="left"><font size="3">该详细地址曾用于以下用户：</font></td>
        </tr>
        <tr class="list_head"> 
            <td width="33%" align="center" class="list_head">用户证号</td>
            <td width="33%" align="center" class="list_head">用户类型</td>
            <td width="33%" align="center" class="list_head">用户状态</td>
        </tr>
       <%
        for(int i=0;i<checkDetailAddressList.size();i++){
            pageContext.setAttribute("customerMSG", (CustomerDTO)checkDetailAddressList.get(i));
       %>
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
          <td width="33%" align="center"><font size="2">
              <tbl:write name="customerMSG" property="CustomerID"/>
          </font></td>
          <td width="33%" align="center"><font size="2">
              <d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="customerMSG:customerType" />
          </font></td>
          <td width="33%" align="center"><font size="2">
              <d:getcmnname typeName="SET_C_CUSTOMERSTATUS" match="customerMSG:status" />
          </font></td>
        </tbl:printcsstr>
		<tr>
			<td colspan="3" class="list_foot"></td>
		</tr>
    <%
        }
     } 
     }
    %>
    </table>

<form name="frmPost" method="post" action="service_account_transfer_confirm.screen" >     

      <br>
      <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
       </table>
       <br>
      <table border="0" cellspacing="0" cellpadding="0" width="820">
         <tr><td><font color="red">&nbsp;&nbsp;说明：费用为负表示应退给客户</font></td></tr>
      </table>        

      <tbl:CommonFeeAndPaymentInfo includeParameter="Fee"  payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UT%>" acctshowFlag ="true"  /> 
      <!--系统内过户要的参数-->
      <tbl:hiddenparameters pass="txtCustomerID/txtServiceAccountID/txtAccount/txtActionType/txtTransferType/txtSAID/txtSAName/txtSAC/txtNewCustomerID/txtNewAccountID" />
      <!--系统外过户要的参数-->
      <tbl:hiddenparameters pass="txtName/txtGender/txtCustType/txtTelephone/txtMobileNumber/txtBirthday/txtDetailAddress/txtCatvID/txtOpenSourceType/txtOpenSourceID" />
      <tbl:hiddenparameters pass="txtGroupBargainDetailNo/txtCounty/txtCardType/txtCardID/txtForceDepostMoney/txtSocialSecCardID/txtEmail/txtNationality/txtFaxNumber" />
      <tbl:hiddenparameters pass="txtPostcode/txtComments/txtBillDetailAddress/txtBillPostcode/txtbillCounty/txtMopID/txtBankAccount/txtBankAccountName/txtAccountType/txtAccountName" />
      <tbl:hiddenparameters pass="txtContractNo/txtCsiCreateReason" />
      <tbl:dynamicservey serveyType="M"  showType="hide" controlSize="23"/>
      <input type="hidden" name="txtDoPost" value="FALSE" >
      <input type="hidden" name="txtcsiPayOption" >
      <input type="hidden" name="txtConfirmBackFlag" value="" >
      <tbl:generatetoken /> 

      <br>
      <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
       </table>
       <br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr>
   <td><img src="img/button2_r.gif" width="22" height="20"></td>
   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td>
   
   <jsp:include page = "/fee/common_control.jsp"/>
</tr>
</table>
</form> 

<Script language=JavaScript>
<!--  

function frm_next(csiPayOption){
	 document.frmPost.txtcsiPayOption.value =csiPayOption;
	 if (check_fee()){
	    document.frmPost.action="service_account_transfer_fee.do";
	 }else{
		 document.frmPost.action="service_account_transfer_confirm.do";
		 document.frmPost.txtConfirmBackFlag.value="caculatefee"
//	 	  document.frmPost.action="service_account_transfer_op.do";
//	 	  document.frmPost.txtDoPost.value="true";
	 }
	 document.frmPost.submit();
}
function frm_finish(csiPayOption){
	 document.frmPost.txtcsiPayOption.value =csiPayOption;
	 document.frmPost.action="service_account_transfer_confirm.do";
	 document.frmPost.txtConfirmBackFlag.value="caculatefee"
//	 document.frmPost.action="service_account_transfer_op.do";
//	 document.frmPost.txtDoPost.value="true";
	 document.frmPost.submit();
}
function back_submit()
{
	document.frmPost.action="service_account_transfer_input.screen";
	document.frmPost.submit();
}

//-->
</Script>
      