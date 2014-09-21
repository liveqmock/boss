<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<script language=javascript>
<!--
function back_submit() {
    document.frmPost.confirm_post.value="false";
    document.frmPost.forwardFlag.value = '1';
    document.frmPost.action="customer_move_create.screen";
    document.frmPost.submit();
}

function frm_next(csiPayOption){
		document.frmPost.txtcsiPayOption.value =csiPayOption;
		if (check_fee()){
	     document.frmPost.action="customer_move_pay.screen";
	  }else{
	 	  //document.frmPost.action="customer_move_create_commit.do";
	 	  //document.frmPost.confirm_post.value="true";
	 	  document.frmPost.action="customer_move_confirm.do";
	 	  document.frmPost.txtConfirmBackFlag.value="caculatefee";
	 	  document.frmPost.confirm_post.value="false";
	  }
	  document.frmPost.submit();
}

function frm_finish(csiPayOption){
	 document.frmPost.txtcsiPayOption.value =csiPayOption;
	 //document.frmPost.action="customer_move_create_commit.do";
	 //document.frmPost.confirm_post.value="true";
	 document.frmPost.action="customer_move_confirm.do";
	 document.frmPost.confirm_post.value="false";
	 document.frmPost.txtConfirmBackFlag.value="caculatefee";
	 document.frmPost.submit();
}

//-->
</SCRIPT>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户迁移--收费信息</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<form name="frmPost" method="post" action="">
	
<tbl:CommonFeeAndPaymentInfo includeParameter="Fee"  payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_MD%>" acctshowFlag ="true"  /> 

<input type="hidden" name="forwardFlag" value="">
<input type="hidden" name="txtcsiPayOption" >
<input type="hidden" name="confirm_post" value="false">
<input type="hidden" name="func_flag" value="3001">
<tbl:hiddenparameters pass="txtCustomerID/txtCustomerSerialNo/txtContractNo/txtOldCatvID/txtCustomerType/txtCustomerStatus/txtOpenSourceType" />  
<tbl:hiddenparameters pass="txtDtCreateShow/txtName/txtGender/txtCardType/txtCardID/txtBirthday/txtDistrict/txtOldDetailAddress" />
<tbl:hiddenparameters pass="txtPostcode/txtTelephone/txtTelephoneMobile/txtDetailAddress/txtNewDistrict/txtCatvID/txtCustAccount" />
<tbl:hiddenparameters pass="txtCustomerDTlastmod/txtAddressDTlastmod/txtAddressID" />
<input type="hidden" name="txtConfirmBackFlag" value="" />
<tbl:generatetoken />
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



