<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.CommonKeys" %>

<script language=javascript>
<!-- 
function back_submit() {
   document.frmPost.txtDoPost.value ="false";
	 document.frmPost.action="register_repair_pay.screen";
	 document.frmPost.submit();
}

function frm_submit(){
    document.frmPost.submit();   
}
 //-->
 
</script>
 <table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top"><table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">维修费用支付信息确认</td>
      </tr>
    </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
         <br>
 

 
<form name="frmPost" method="post" action="register_repair_op.do" >  
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
        <td class="list_bg2"><div align="right">工单编号</div></td>
        <td class="list_bg1">
	<input type="text" name="txtJobCardID" size="25"  value="<tbl:writeparam name="txtJobCardID"/>" class="textgray" readonly   >
	</td>
	<td class="list_bg2"><div align="right">详细地址</div></td>
	<td class="list_bg1">
       <input type="text" name="txtDetailAddress" size="25"  value="<tbl:writeparam name="txtDetailAddress"/>" class="textgray" readonly   >
	</td>
	</tr>
	 <tr>
	     <td class="list_bg2"><div align="right">联系人</div></td>
	     <td class="list_bg1">
		 <input type="text" name="txtContactPerson" size="25" value="<tbl:writeparam name="txtContactPerson"/>" class="textgray" readonly >
	    </td>
	    <td class="list_bg2" ><div align="right">维修人员</div></td>
	    <td class="list_bg1" colspan="3" > 
		<input type="text" name="txtProcessMan" size="25"  value="<tbl:writeparam name="txtProcessMan"/>" class="textgray" readonly >
	   </td> 
	</tr> 
	 
	 <tr>
	 
		<td class="list_bg2"><div align="right">故障类型</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtTroubleType1" size="25" value="<o:getcmnname  typeName="SET_W_JOBCARDTROUBLETYPE"  match="txtTroubleType"/>" class="textgray" readonly >
		</td>
		 
		<td class="list_bg2" ><div align="right">故障原因</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtTroubleSubType1" size="25"  value="<o:getcmnname  typeName="SET_W_JOBCARDTROUBLESUBTYPE"  match="txtTroubleSubType"/>" class="textgray" readonly >
		</td>
	</tr>
	<tr>
		<td class="list_bg2" ><div align="right">解决手段</div></td>
		<td class="list_bg1" colspan="3"> 
		<input type="text" name="txtResolutionType1" size="25"  value="<o:getcmnname  typeName="SET_W_JOBCARDRESOLUTIONTYPE"  match="txtResolutionType"/>" class="textgray" readonly >
		 
		</td>
		 
        </td>	
	<tr>
	   <td class="list_bg2" ><div align="right">维修成功描述</div></td>
	   <td class="list_bg1" colspan="3"> 
			<input type="text" name="txtSucessComment" size="77" maxlength="200" value="<tbl:writeparam name="txtSucessComment"/>" class="textgray" readonly >
	  </td>
	</tr>	 
</table>
 <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
     <tr ><td> 
        <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.METHODOFPAYMENT_KHBX%>" acctshowFlag ="true" confirmFlag="true" />		 
     </td></tr> 
  </table>
  <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
  	 <tr> 
	      <td colspan="4" class="import_tit" align="center"><font size="3">物料使用情况</font></td>
     </tr>
     <tr ><td> 
       <tbl:dynamicservey serveyName="txtMaterialName" serveyType="U" serveySubType="RU" showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="23" tdWidth1="17%" tdWidth2="33%" tdHeight="30" /> 
     </td></tr>
  </table>

<input type="hidden" name="txtDoPost" size="22" value="TRUE">
<input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >
   
<tbl:hiddenparameters pass="isSuccess/txtDtLastmod/txtWorkResultReason/txtTroubleType/txtSucessComment/txtFaildComment/txtReferSheetId/txtCustomerID/txtAccountID/txtTroubleSubType/txtResolutionType/txtWorkDate/txtWorkTime/txtOutofDateReason/txtDiaName/func_flag" />
<tbl:generatetoken />
 <table align="center" border="0" cellspacing="0" cellpadding="0">
<tr>
   <td><img src="img/button2_r.gif" width="22" height="20"></td>
   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td>
   <td width="20" ></td>
   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确 认"></td>
   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
</tr>
</table>   
</form>  

 
 

   

 