<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>

<script language=javascript>
function check_frm()
{
	 
 	
    if (check_Blank(document.frmPost.txtOperatorName, true, "操作员姓名"))
	    return false;
    if (check_Blank(document.frmPost.txtLevel, true, "授权级别"))
	    return false;
    if (check_Blank(document.frmPost.txtLoginID, true, "登录帐号"))
	    return false;
    if (check_Blank(document.frmPost.txtLoginPwd, true, "密码"))
	    return false;
	    
     return true;
}
function confirm_password()
{
	
 	
    if (document.frmPost.txtLoginPwd.value == document.frmPost.txtSecondLoginPwd.value)
	    return true;
    else {
     alert("密码不一致,请重新输入");
     document.frmPost.txtSecondLoginPwd.focus();
     return false;
     }
}
 
function operator_create_submit(){
  if (check_frm() && confirm_password()){
	    document.frmPost.action="create_operator.do";
	    document.frmPost.Action.value="CREATE";
	    document.frmPost.submit();
	  }
}
 
function back_submit(){

    document.frmPost.action= "operator_query.do?txtOrgID="+document.frmPost.txtOrgID.value;
    document.frmPost.submit();
} 

 
</script>
 
 
 
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">操作员创建</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
 <%
    int orgId =WebUtil.StringToInt(request.getParameter("txtOrgID"));
    
    String orgName = Postern.getOrgNameByOrgID(orgId);
   
    
 %>
  
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr> 
          <td  class="list_bg2"><div align="right">操作员姓名*</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtOperatorName" size="25" maxlength="20" value="<tbl:writeparam name="txtOperatorName" />" >
           </td>  
           <td class="list_bg2"><div align="right">操作员描述 </div></td>
           <td class="list_bg1" align="left">
          <input type="text" name="txtOperatorDesc" maxlength="100" size="25"   value="<tbl:writeparam name="txtOperatorDesc"/>">
          </td>    
       </tr>
       <tr>
         <td class="list_bg2"><div align="right">登录帐号*</div></td>
           <td class="list_bg1" align="left" >
          <input type="text" name="txtLoginID"  size="25"  maxlength="50" value="<tbl:writeparam name="txtLoginID"/>">
          </td>
         <td class="list_bg2"><div align="right">授权级别*</div></td>
           <td class="list_bg1" align="left" >
           <d:selcmn name="txtLevel" mapName="SET_S_OPERATORLEVEL" match="txtLevel" width="23" /></td>
          </td>
          
      </tr>
       <tr>
         <td class="list_bg2"><div align="right">密&nbsp;&nbsp;码*</div></td>
           <td class="list_bg1" align="left">
           <input name="txtLoginPwd" type="password" class="text"  maxlength="50" value="<tbl:writeparam name="txtLoginPwd"/>" style="width:187px;"></td>
           
          </td>
          <td class="list_bg2"><div align="right">密码确认</div></td>
           <td class="list_bg1" align="left">
           <input name="txtSecondLoginPwd" type="password"  maxlength="50" class="text" value="<tbl:writeparam name="txtSecondLoginPwd"/>" style="width:187px;"></td>
           </td>
         
      </tr>
      <tr>
         <td class="list_bg2"><div align="right">所属组织</div></td>
           <td class="list_bg1" align="left">
          <input type="text" name="txtOrgName"  maxlength="22" size="25"   value="<%=orgName%>"  class="textgray" readonly>
          </td>
          <td class="list_bg2"><div align="right">状态</div></td>
           <td class="list_bg1" align="left">
           <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" /></td>
         
      </tr>
       
 </table>
 <input type="hidden" name="func_flag" value="198" >
  <input type="hidden" name="Action" value=""/>
  <input type="hidden" name="txtOrgID" value="<%=orgId%>" >
   <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
     <input type="hidden" name="txtInternalUserFlag" size="20" value="N">
  
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
             <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            
           <td width="20" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:operator_create_submit()" class="btn12">添 加</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
	
        </tr>
      </table>   
  
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
       <br>      
   
</form>
