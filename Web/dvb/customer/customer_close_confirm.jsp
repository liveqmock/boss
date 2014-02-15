<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.service.component.ImmediateCountFeeInfo" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys,com.dtv.oss.web.util.WebKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.PaymentRecordDTO" %>
<%@ page import="com.dtv.oss.dto.AccountItemDTO" %>
<%@ page import="com.dtv.oss.dto.AcctItemTypeDTO" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="java.util.*" %>

<script language=javascript>
<!--
function back_submit() {
	<%
  	  String requestAttribute =(request.getAttribute(WebKeys.REQUEST_ATTRIBUTE) ==null) ? "0" : "1";
  %>
   request_attr ="<%=requestAttribute%>";
   if (request_attr =="0"){
      document.frmPost.action ="customer_close_fee_step2.screen";
   } else{
      document.frmPost.txtDoPost.value ="false";
      document.frmPost.action ="customer_close_fee_wrap_step1data.do";
   }
   document.frmPost.submit();
}

function frm_submit(){
	 document.frmPost.submit();
}

//-->
</SCRIPT>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户销户--信息确认</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<form name="frmPost" method="post" action="customer_close_op.do">
<table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
            <td class="list_bg2"><div align="right">客户证号</div></td>
            <td class="list_bg1"><input type="text" name="txtCustomerID" size="25"  value="<tbl:writeparam name="txtCustomerID"/>" class="textgray" readonly ></td>
             <td class="list_bg2"><div align="right">姓名</div></td>
            <td class="list_bg1"><input type="text" name="txtName" size="25"  value="<tbl:writeparam name="txtName"/>" class="textgray" readonly ></td>
        </tr>
        <tr> 
        	<td class="list_bg2"><div align="right">帐户</div></td>
           <td class="list_bg1"><input type="text" name="txtAccountName" size="25"  value="<tbl:writeparam name="txtAccountName"/>" class="textgray" readonly ></td>  
           	<!--     
           	<tbl:csiReason csiType="CC" name="txtCloseReasonName" action="N" showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" forceDisplayTD="true" match="txtCloseReason" />
           	-->
           <%
           	if(request.getParameter("txtCloseReasonName") != null && !"".equals(request.getParameter("txtCloseReasonName")))
           	{
           %>
           <td class="list_bg2"><div align="right">销户原因</div></td>
           <td class="list_bg1"><input type="text" name="txtCloseReasonName" size="25"  value="<tbl:writeparam name="txtCloseReasonName"/>" class="textgray" readonly ></td>
           <%
           }
          else
          	{%>
          	<td class="list_bg2"><div align="right"></div></td>
          	<td class="list_bg1"><div align="right"></div></td>
          	<%}%>
        </tr> 
</table>
<table align="center" width="810" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
   <tr ><td>       
     <tbl:CommonFeeAndPaymentInfo includeParameter="CloseAcctFee_step3" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_CC%>"  />		 		
   </td></tr>
</table>      	
 <tbl:hiddenparameters pass="txtCustomerID/txtName/txtCloseReason/txtAccount" />
<input type="hidden" name="txtDoPost" value="TRUE">
<input type="hidden" name="func_flag" value="3" >
<tbl:generatetoken />
<br>
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



