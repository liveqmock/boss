<%@ page import="com.dtv.oss.util.Postern"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<script language="JavaScript" type="text/JavaScript">
<!--
function OnEnter(){
        document.frmLogin.submit();
}
//-->
</script>

<table width="100%"  border="0" cellpadding="0" cellspacing="0" bgcolor="#00439E">
  <tr>
    <!--<td width="770" background="img/login_bg1.gif"><img src="img/<d:config prefix="" showName="SYSTEMSYMBOLNAME" suffix="/" />login_logo.gif" width="90" height="58"></td>-->
    <td width="770" background="img/login_bg1.gif"><img src="img_download.screen?action=imgLoginLogo" width="90" height="58"></td>
    <td background="img/login_bg2.gif">&nbsp;</td>
  </tr>
</table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="770" bgcolor="#D8D8D8"><img src="img/mao.gif" width="10" height="10"></td>
    <td bgcolor="#E3E3E3"><img src="img/mao.gif" width="10" height="10"></td>
  </tr>
</table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="184" height="460" rowspan="3" valign="top" style="background:url(img/login_l.gif) no-repeat top center #0082C2;"><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="184" height="460">
      <param name="movie" value="img/left.swf">
      <param name="quality" value="high">
      <param name="wmode" value="transparent">
      <embed src="img/left.swf" width="184" height="460" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent"></embed>
    </object></td>
   <td width="586" height="125" valign="top" bgcolor="#005FBD"><table width="100%"  border="0" cellpadding="0" cellspacing="0" background="img/login_bg3.gif">
      <tr>
        <td width="1" bgcolor="#E7E7E7"><img src="img/mao.gif" width="1" height="1"></td>
        <td width="484" height="125">
    <!--<img src="img/<d:config prefix="" showName="SYSTEMSYMBOLNAME" suffix="/" />title.gif" width="484" height="125" border="0">    -->
    <img src="img_download.screen?action=imgTitle" width="484" height="125" border="0">    
<!--       
        <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="484" height="125">
          <param name="movie" value="img/title.swf">
          <param name="quality" value="high">
          <param name="wmode" value="transparent">
          <embed src="img/title.swf" width="484" height="125" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent"></embed>
        </object>  
-->      
        </td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td background="img/login_bg4.gif" bgcolor="#4787C9">&nbsp;</td>
  </tr>
  <tr>
    <td height="313" align="center" valign="top" bgcolor="#ECECEC" style="background:url(img/login_pic2.gif) bottom right no-repeat #ECECEC; "><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td colspan="2"><img src="img/mao.gif" width="25" height="25"></td>
      </tr>
      <tr>
        <td height="1" colspan="2" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
      <tr>
        <td width="5" align="center"><br>
          <br>
          <br></td>
        <td valign="bottom" ><form name="frmLogin" method="post" action="login.screen">
		<jsp:include page="/error.jsp"/>
         <TABLE width="100%"  border="0" cellspacing="10" cellpadding="0">
        <TR align="center">
        	<TD  class="title" colspan=3><BR>再见，欢迎您再次使用<d:config prefix="" showName="MSOSYSTEMNAME" suffix="" />！<BR><BR><BR></TD>
        </TR>
        <TR align="center">
		<td height="20"><TABLE border="0" cellspacing="0" cellpadding="0">
		<TR>
			<td><img src="img/button_l.gif" width="11" height="20"></td>
                  <td><input type="button" class="button" onClick="javascript:OnEnter()" value="重新登陆"></td>
                  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		</TR>
		</TABLE></td>
        </TR>
        </TABLE>
        </form></td>
      </tr>
      <tr>
        <td colspan="2" background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
    </table></td>
    <td height="313" bgcolor="#F1F1F1">&nbsp;</td>
  </tr>
  <tr>
    <td height="22" align="right" bgcolor="#E0E0E0" class="en_7pt">COPYRIGHT 2005-2006 CMS OF DIGIVISION ALL RIGHTS RESERVED. </td>
    <td height="22" bgcolor="#E9E9E9">&nbsp;</td>
  </tr>
</table>
<script language="JavaScript" type="text/JavaScript">
<!--
//document.frmLogin.txtLoginID.focus();

//window.open('hint.htm','','toolbar=no,menubar=no,location=no,height=280,width=450,left=150,top=50');
//-->
</script>