<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<form name="frmLogin" method="post" action="signon.do" > 
<script language="JavaScript" type="text/JavaScript">
<%@ page import="com.dtv.oss.util.Postern"%>
<!--
function OnEnter(){
        if (check_Blank(document.frmLogin.txtLoginID, true, "用户名"))
        	return false;
        if (check_Blank(document.frmLogin.txtLoginPwd, true, "密码"))
        	return false;
        document.frmLogin.submit();
}

function NameReturnClick(){        
        if (event.keyCode==13) document.frmLogin.txtLoginPwd.focus();         
}

function ReturnClick(){
        
        if (event.keyCode==13) OnEnter();
         
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
    </object>
    </td>
   <td width="586" height="125" valign="top" bgcolor="#005FBD"><table width="100%"  border="0" cellpadding="0" cellspacing="0" background="img/login_bg3.gif">
      <tr>
        <td width="1" bgcolor="#E7E7E7"><img src="img/mao.gif" width="1" height="1"></td>
        <td width="484" height="125">
    	<!--<img src="img/<d:config prefix="" showName="SYSTEMSYMBOLNAME" suffix="/" />title.gif" width="484" height="125" border="0"> -->
		<img src="img_download.screen?action=imgTitle" width="484" height="125" border="0">    
<!--
    //这里的flash去掉，现在每个系统都有对应的内容
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
        <td width="160" align="center"><br>          <br>          <img src="img/login_pic.gif" width="88" height="98"><br>
          <br>
          <br></td>
        <td valign="bottom"><form name="form1" method="post" action="">
        <table width="100%"  border="0" cellspacing="10" cellpadding="0">
          <tr>
            <td width="50">用户名</td>
            <td><input name="txtLoginID" type="text" class="text" value="" style="width:150px;" onkeydown="NameReturnClick()"></td>
          </tr>
          <tr>
            <td>密&nbsp;&nbsp;码</td>
            <td><input name="txtLoginPwd" type="password" class="text" value="" style="width:150px;" onkeydown="ReturnClick()"></td>
          </tr>
        </table>
          <table  border="0" cellspacing="10" cellpadding="0">
            <tr align="center">
              <td><table  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                  <td><input type="button" class="button" onClick="javascript:OnEnter()" value="登 录"></td>
                  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                </tr>
              </table></td>
              <td><table  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                  <td><input type="reset" class="button" value="重 置"></td>
                  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                </tr>
              </table></td>
            </tr>
          </table>
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
document.frmLogin.txtLoginID.focus();

//window.open('hint.htm','','toolbar=no,menubar=no,location=no,height=280,width=450,left=150,top=50');
//-->
</script>
</form>