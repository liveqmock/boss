<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<script language=javascript>
function check_form(){
	if (check_Blank(document.frmPost.txtBarCode, true, "帐单条形码"))
		return false;
	return true;
}

function next_submit()
{
        if (check_form()) document.frmPost.submit();
}
</script>

<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">帐单支付</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<font color="red" size="2">&nbsp;&nbsp;注：请输入帐单条形码</font>
<form name="frmPost" action="bill_pay_op.do" method="post" >    
<input type="hidden" name="bill_op" value ="check" >
<input type="hidden" name="returnFlag" value="true" >
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2"><div align="right">帐单条形码*</div></td>
    <td class="list_bg1"><font size="2">
            <input type="text" name="txtBarCode" size="48"  value="<tbl:writeparam name="txtBarCode" />" ></font></td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="下一步" onclick="javascript:next_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table></td>
	</tr>
</table>      
</form> 
