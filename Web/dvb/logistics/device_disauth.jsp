<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%
//解析上传文件要用到，在webaction中使用过后remove掉
session.setAttribute(CommonKeys.FILE_UPLOAD_PAGE_CONTEXT,pageContext);
%>
<script language=javascript>
function check_frm(){
	if(document.frmPost.txtFileName.value==null||document.frmPost.txtFileName.value==""){
	   alert("没有有效的数据文件！");
	   return false;
	}
	return true;
}
function next_step(){
  if(check_frm()){
    document.frmPost.action ="device_disauth_check.do";
    document.frmPost.submit();    
  }
}
</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><font size="3">解预授权</font></td>
  </tr>
</table>

<form name="frmPost" method="post" method="post" enctype="multipart/form-data">
<input type="hidden" name="confirm_post" value="false" >
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tr>
       <td class="list_bg2" align="right" >设备文件*</td>
       <td class="list_bg1" width="75%">
        	<input type="file" name="txtFileName" size="50"  maxlength="200"  onkeypress='return false;' onpaste="return false;"/>
       </td>
	  </tr>
</table>

<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tr align="center">
	    <td class="list_bg1">   
        <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
        	<td width="20" ></td>
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:next_step()" class="btn12">下一步</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
        </tr>
        </table>
	     </td>
    </tr>
</table>
</form>