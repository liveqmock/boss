<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="import com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>

<script language=javascript>
<!--
function check_form(){
   if(!check_csiReason())
	 {
		 return false;
	 }
   if (check_Blank(document.frmPost.txtAccount, true, "帐户"))
  			 return false; 
 	return true;
}
function frm_submit(){
	if (check_form()) {
	    document.frmPost.txtAccountName.value = document.frmPost.txtAccount.options[document.frmPost.txtAccount.selectedIndex].text;
	    if(document.frmPost.txtCloseReason != null)
	    {
	   	 document.frmPost.txtCloseReasonName.value = document.frmPost.txtCloseReason.options[document.frmPost.txtCloseReason.selectedIndex].text;
	  	}
	    document.frmPost.submit();
	}    
}

//-->
</SCRIPT>
<table border="0" cellspacing="0" cellpadding="0" width="810" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">录入销户信息</td>
</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="customer_close_fee_step1.do">	 
    <input type="hidden" name="func_flag" value="3"> 
    <input type="hidden" name="txtDoPost" value="FALSE"> 
   <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
            <td class="list_bg2"><div align="right">客户证号</div></td>
            <td class="list_bg1"><input type="text" name="txtCustomerID" size="25"  value="<tbl:writeparam name="txtCustomerID"/>" class="textgray" readonly ></td>
             <td class="list_bg2"><div align="right">姓名</div></td>
            <td class="list_bg1"><input type="text" name="txtName" size="25"  value="<tbl:writeparam name="txtName"/>" class="textgray" readonly ></td>
        </tr>
        <tr>        
          <!--td class="list_bg2"><div align="right">销户原因*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selcmn name="txtCloseReason" mapName="SET_V_CC_CUSTSERVICEINTERACTIONSTATUSREASON" match="txtCloseReason"   width="23" />
           </font> </td-->
           <td class="list_bg2"><div align="right">为一次性费指定帐户*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" style="width:186" />
           </font> </td>
           
           <tbl:csiReason csiType="CC" name="txtCloseReason" action="N" showType="text"  checkScricptName="check_csiReason()" tdWordStyle="list_bg2" tdControlStyle="list_bg1" forceDisplayTD="true" match="txtCloseReason"  controlSize="25" />
           
        </tr> 
        </table>

<%
    String customerID = request.getParameter("txtCustomerID");
    //检查是否有未付的帐单
    int iUnpaidInvoiceAccount = Postern.getUnpaidInvoiceCountByCustID(WebUtil.StringToInt(customerID));
    if (iUnpaidInvoiceAccount>0){
%>
  <table align="center" border="0" cellspacing="0" cellpadding="0">	
  	<tr>
  		<td align="right">
  		<font size="3" color="red" >
		该账户有<%=iUnpaidInvoiceAccount%>张未付帐单!
		</font>
		</td>
  	</tr>
  		
  </table>
<% 
}  
%>  
 <BR>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
     	<td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
    	<td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
    </tr>
 </table>
<input type="hidden" name="txtCloseReasonName" value="">
<input type="hidden" name="txtAccountName" value="">
</form> 
 </td></tr></table>