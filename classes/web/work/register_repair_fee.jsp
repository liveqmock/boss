<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.CommonKeys" %>

<script language=javascript>
 
function back_submit(){
	document.frmPost.action="register_repair_info_view.do";
	document.frmPost.submit();
}
 
function check_frm(){
  if (check_fee()) 
      return true;
      
   return false;
} 
 
 function frm_submit(){
   if (check_frm()) 
      document.frmPost.submit();
}

function frm_next(csiPayOption){
		document.frmPost.txtcsiPayOption.value =csiPayOption;
	    if (check_fee()){
	    	  document.frmPost.txtDoPost.value="false";
	    }else{
	// 	  document.frmPost.action="register_repair_op.do";
	 	  document.frmPost.txtDoPost.value="true";
	   }
	  document.frmPost.action="register_repair_pay.screen";
	  document.frmPost.submit();
}

function frm_finish(csiPayOption){
	 document.frmPost.txtcsiPayOption.value =csiPayOption;
	 document.frmPost.action="register_repair_op.do";
	 document.frmPost.txtDoPost.value="true";
	 document.frmPost.submit();
}
 
</script>
 <table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top">
   <table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">维修费用支付信息</td>
      </tr>
    </table>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
     </table>
     <br>
 

 
<form name="frmPost" method="post" action="register_repair_fee_confirm.screen" >     
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
        <td class="list_bg2"><div align="right">工单编号</div></td>
        <td class="list_bg1">
	<input type="text" name="txtJobCardID" size="25"  value="<tbl:writeparam name="txtJobCardID"/>" class="textgray" readonly   >
	</td>
	<td class="list_bg2"><div align="right">详细地址</div></td>
	<td class="list_bg1">
       <input type="text" name="txtAddressID" size="25"  value="<tbl:writeparam name="txtAddressID"/>" class="textgray" readonly   >
	</td>
	</tr>
	 <tr>
	     <td class="list_bg2"><div align="right">联系人</div></td>
	     <td class="list_bg1">
		 <input type="text" name="txtContactPerson" size="25" value="<tbl:writeparam name="txtContactPerson"/>" class="textgray" readonly >
	    </td>
	    <td class="list_bg2"><div align="right">维修人员</div></td>
	    <td class="list_bg1"> 
		<input type="text" name="txtProcessMan" size="25"  value="<tbl:writeparam name="txtProcessMan"/>" class="textgray" readonly >
	   </td> 
	</tr> 
	 <tr>
	         
		<td class="list_bg2" ><div align="right">故障类型</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtTroubleType1" size="25"  value="<o:getcmnname  typeName="SET_W_JOBCARDTROUBLETYPE"  match="txtTroubleType"/>" class="textgray" readonly >
		</td>
		
		<td class="list_bg2" ><div align="right">故障原因</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtTroubleSubType1" size="25"  value="<o:getcmnname  typeName="SET_W_JOBCARDTROUBLESUBTYPE"  match="txtTroubleSubType"/>" class="textgray" readonly >
		</td>
	 </tr>
	 <tr>
		<td class="list_bg2"><div align="right">解决手段</div></td>
		<td class="list_bg1" colspan="3"> 
		<input type="text" name="txtResolutionType1" size="25"  value="<o:getcmnname  typeName="SET_W_JOBCARDRESOLUTIONTYPE"  match="txtResolutionType"/>" class="textgray" readonly >
		 
		</td>
		 
        </tr>	
	<tr>
	   <td class="list_bg2" ><div align="right">维修成功描述</div></td>
	   <td class="list_bg1" colspan="3"> 
			<input type="text" name="txtSucessComment" size="80" maxlength="200" value="<tbl:writeparam name="txtSucessComment"/>" class="textgray" readonly >
	  </td>
	</tr>	 
</table>
<tbl:hiddenparameters pass="isSuccess/txtDtLastmod/txtTroubleType/txtCustomerID/txtAccountID/txtWorkDate/txtWorkTime/txtWorkResultReason/txtSucessComment/txtFaildComment/txtReferSheetId/txtTroubleSubType/txtResolutionType/txtOutofDateReason/func_flag/txtCustServiceAccountID" />

<tbl:dynamicservey serveyName="txtMaterialName" serveyType="U" serveySubType="RU" showType="hide" />
<input type="hidden" name="txtcsiPayOption" >
<input type="hidden" name="txtDoPost" value="false" >
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
   <tr ><td> 
   	 <tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=CommonKeys.METHODOFPAYMENT_KHBX%>" acctshowFlag ="true"  />		 
   </td></tr> 
</table>
<br> 
<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr> 
   <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
   <td height="20"> 
	    <input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步">
   </td>
   <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
   
   <jsp:include page = "/fee/common_control.jsp"/>
</tr>
</table>    

</form>  

 
 

   

 