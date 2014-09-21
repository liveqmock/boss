<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<Script language=JavaScript>
function frmSubmit(url){
	 document.frmPost.action = url;
   document.frmPost.submit();
}

function frm_finish(){
	 document.frmPost.submit();
}
</Script>
<form name="frmPost" method="post" action="customer_protocol_confirm.do" >
	   <input type="hidden" name="confirm_post" value="true" >  
	   <tbl:hiddenparameters pass="txtCustomerID" />  
     <table  border="0" align="center" cellpadding="0" cellspacing="5">
        <tr>
          <td><img src="img/list_tit.gif" width="19" height="19"></td>
          <td class="title">客户协议价维护</td>
        </tr>
      </table>
      <br>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
       </table>
       <br>
       <jsp:include page = "/fee/package_protocolPrice.jsp"/>
       <tbl:generatetoken />
</form> 

<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr> 
	 <bk:canback url="customer_view.do">
    <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
    <td height="20">
	    <input name="prev" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='customer_view.do' />')" value="返回">
    </td>
    <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
   </bk:canback>
     
    <td width="20" ></td>
    <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
    <td>
    	<input name="next" type="button" class="button" onClick="javascript:frm_finish()" value="确认">
    </td>
    <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
    
</tr>
</table>  