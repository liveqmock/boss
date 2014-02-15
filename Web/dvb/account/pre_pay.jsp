<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.web.util.WebUtil,com.dtv.oss.dto.CustomerDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<Script language=JavaScript>

<!--
function pre_pay_submit(){
	   if (check_pay()){
        document.frmPost.action="pre_pay.do";
        document.frmPost.submit();
     }
}
function back_submit(){
	document.frmPost.action="account_view.do";
	document.frmPost.submit();
}
//-->
</SCRIPT>


<%
	CustomerDTO customerDTO=Postern.getCustomerByID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
%>

<table width="810" align="center" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="center" valign="top">
			<table  border="0" align="center" cellpadding="0" cellspacing="10">
        <tr>
          <td><img src="img/list_tit.gif" width="19" height="19"></td>
          <td class="title">预付费</td>
       </tr>
      </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
     </table>
     <br>

<form name="frmPost" method="post" action="">
	 <table align="center" width="98%" border="0" cellspacing="1" cellpadding="3" class="fulltable" >      
       <tr>
		     <td class="list_bg2"  align="right" width="35%">帐 号</td>
		     <td class="list_bg1">
		      	<input type="text" name="txtAccountID" size="25"  value="<tbl:writeparam name="txtAccountID" />" class="textgray" readonly   >
		     </td>
      </tr>
      <tr>
		     <td class="list_bg2" align="right">用户姓名</td>
		     <td class="list_bg1">
			     <input type="text" name="txtCustomerName" size="25"  value="<%=customerDTO.getName()%>"  class="textgray" readonly >
		     </td>
	    </tr>
	    <tr>
	    	<td colspan="2">
	    		<tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Deposite" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_AD%>" acctshowFlag ="false" confirmFlag="false" />
	      </td>
	    </tr>
   </table>
   
	 <input type="hidden" name="txtCustomerID" value="<%=customerDTO.getCustomerID()%>">
	 <input type="hidden" name="txtDoPost" value="TRUE">
   <input type="hidden" name="func_flag" value="6001">
<BR>
 <%
    
    //检查是否有未付的帐单
    int iUnpaidInvoiceAccount = Postern.getUnpaidInvoiceCountByAcctID(WebUtil.StringToInt(request.getParameter("txtAccountID")));
     
    if (iUnpaidInvoiceAccount>0){
    
%>
  <table align="center" border="0" cellspacing="0" cellpadding="0">	
  	<tr>
  		<td align="right">
  		<font size="3" color="red" >
		该账户有<%=iUnpaidInvoiceAccount%>张未付帐单,请支付帐单后预存！  
		</font>
		</td>
		
		
		<td width="20" ></td>
            	<td><img src="img/button2_r.gif" border="0" ></td>
            	<td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button2_l.gif" border="0" ></td>
  	</tr>
  		
  </table>
<%    
   }        
   else {
%>  
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>  
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button2_r.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button2_l.gif" border="0" ></td>
          
           <td width="20" ></td>
            <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:pre_pay_submit()" class="btn12">确&nbsp;定</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
 
    </tr>

</table>   
<% } %>
<tbl:generatetoken />
</form>
</td></tr></table>