<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>

<%
//解析上传文件要用到，在webaction中使用过后remove掉
session.setAttribute(CommonKeys.FILE_UPLOAD_PAGE_CONTEXT,pageContext);
%>
<script language=javascript>
<!--
function file_submit(){
	if(document.frmPost.txtFileName.value==null||document.frmPost.txtFileName.value==""){
	    alert("没有有效的数据文件！");
	    return false;
	}
  document.frmPost.submit();
}
//-->
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">建档客户文件导入</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="upload_for_foundCustomer.do" method="post" enctype="multipart/form-data">
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   <tr>
        <td class="list_bg2" align="right" >数据文件*</td>
        <td class="list_bg1" align="left">
        	<input type="file" name="txtFileName" size="50"  maxlength="200"  onkeypress='return false;' onpaste="return false;"/>
        </td>
	</tr>
	<tr>
    <td class="list_bg2" align="right" width="17%">
		导入说明
	</td>
	<td class="list_bg1" width="83%">
		<input type="text" name="txtComments" value="" size="50"  maxlength="400">
	</td>
   </tr>
</table>
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0" height="20">
	<tr height="20">
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td height="20"><input name="next" type="button" class="button" onClick="javascript:file_submit()" value="下一步"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	</tr>
</table>  
</form> 
