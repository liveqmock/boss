<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.web.util.CommonKeys"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%
    String txtCustomerID = request.getParameter( "txtCustomerID");
    String txtServiceAccountID=request.getParameter( "txtServiceAccountID");
    String txtCusPhoneNO = request.getParameter( "txtCusPhoneNO");
%>
<Script language=JavaScript>

function check_frm(){
	if(document.frmPost.txtPhoneNo.value=="")
	{
		alert("请填写号码！");
		return false;
	}
	return true;
}

function frm_submit() {
   if (check_frm()) {	   
	document.frmPost.submit();
   }
}
function frm_back(){
	document.frmPost.action= "query_friend_phoneno.do";
	document.frmPost.submit();
}
</script>
		
<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"> <div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>

  <form name="frmPost" method="post" action="friend_phoneno_creat.do" >
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">亲情号码----增加</td>
  </tr>
</table>
  <br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
   </table>
   <br>
   <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width="17%"><div align="right">客户ID</div></td>
    <td class="list_bg1" width="33%"><input type="text" name="txtCustomerID" size="25"  value="<%=txtCustomerID%>"  class="textgray" readonly ></td>
    <td class="list_bg2" width="17%"><div align="right">业务帐户ID</div></td>
    <td class="list_bg1" width="33%"><input type="text" name="txtServiceAccountID" size="25" maxlength="10" value="<%=txtServiceAccountID%>" class="textgray" readonly  ></td>
  </tr>
  <tr>
    <td class="list_bg2" width="17%"><div align="right">用户电话号码</div></td>
    <td class="list_bg1" width="33%"><input type="text" name="txtCusPhoneNO" size="25"  value="<%=txtCusPhoneNO%>"  class="textgray" readonly ></td>
    <td class="list_bg2" width="17%"><div align="right">国际代码</div></td>
    <td class="list_bg1" width="33%"><input type="text" name="txtCountryCode" size="25" maxlength="20" ></td>
  </tr>
  <tr>
    <td class="list_bg2" width="17%"><div align="right">地区代码</div></td>
    <td class="list_bg1" width="33%"><input type="text" name="txtAreaCode" size="25"  maxlength="20"  ></td>
    <td class="list_bg2" width="17%"><div align="right">号       码*</div></td>
    <td class="list_bg1" width="33%"><input type="text" name="txtPhoneNo" size="25" maxlength="20" ></td>
  </tr>
 </table>
<br>
<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td><img src="img/button2_r.gif" width="22" height="20"></td>  
       <td background="img/button_bg.gif">
	   <a href="<bk:backurl property="query_friend_phoneno.do" />" class="btn12">返  回</a></td>
	   <td><img src="img/button2_l.gif" width="13" height="20"></td>
	   
	   <td width="20" >&nbsp;</td> 
	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td height="20">
		 <input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确  认">
	   </td>
	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>

	</tr>
</table>
<input type="hidden" name="txtActionType" value="<%=CommonKeys.FRIEND_PHONENO_CREATE%>" />
<input type="hidden" name="func_flag" value="118">
</form>
