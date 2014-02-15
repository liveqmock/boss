<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="/WEB-INF/operator.tld" prefix="oper" %>

<script language="javascript">
function check()
{
        if (document.frmPost.newpwd.value == '' || document.frmPost.newpwd2.value == '') {
            alert("密码不允许为空");
            if (document.frmPost.newpwd.value == '') {
                document.frmPost.newpwd.focus();
            } else {
                document.frmPost.newpwd2.focus();
            }
            return false;
        }
        if (document.frmPost.newpwd2.value != document.frmPost.newpwd.value)
        {
            alert("两次密码输入不一致，请确认");
            document.frmPost.newpwd2.focus();
            return false;
        }
        return true;
}

function frm_submit()
{
	if (check()) frmPost.submit();
}
</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
<tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">修改操作员密码</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>


<form name="frmPost" action="change_pwd_return.do" method="post" >
     <input type="hidden" name="loginID" value="<oper:curoper property="LoginID" />"/>
     
     <table align="center" width="810" border="0" cellspacing="1" cellpadding="3"  class="list_bg">
        <tr> 
	  <td valign="middle" class="list_bg2" width="40%" align="right">当前操作员</td> 
          <td valign="middle" class="list_bg1" align="left">
            <input type="text" name="curroperator" size="25" maxlength="50" class="textgray" readonly value="<oper:curoper property="OperatorName" />">
          </td>
        </tr>
        <tr>
	  <td valign="middle" class="list_bg2" align="right">请输入原密码*</td>
          <td valign="middle" class="list_bg1" align="left">
            <input type="password" name="oldpwd" size="25" maxlength="50">
          </td>
        </tr>
        <tr>
	  <td valign="middle" class="list_bg2" align="right">请输入新密码*</div></td>
          <td valign="middle" class="list_bg1" align="left">
            <input type="password" name="newpwd" size="25" maxlength="50">
          </td>
        </tr>
        <tr>
	  <td valign="middle" class="list_bg2" align="right">请再输入新密码*</div></td>
          <td valign="middle" class="list_bg1" align="left">
             <input type="password" name="newpwd2" size="25" maxlength="50">
          </td>
        </tr>
      </table>
      <br>
      <table align="center" width="50" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="20" ></td>  
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="next" type="button" class="button" onClick="frm_submit()" value="修  改"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>     
          </tr>
      </table>
      <input type="hidden" name="func_flag" value="2001"/>  
</form>