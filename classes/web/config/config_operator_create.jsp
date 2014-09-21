<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
     com.dtv.oss.dto.* "%>
 

<script language=javascript>
 

 
function check_frm()
{
	
 	
    if (check_Blank(document.frmPost.txtOperatorName, true, "操作员姓名"))
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
     document.frmPost.txtLoginPwd.focus();
     return false;
     }
}
 
function operator_create_submit(){
  if (check_frm() && confirm_password()){
	    document.frmPost.action="create_all_operator.do";
	    document.frmPost.Action.value="CREATE";
	    document.frmPost.submit();
	  }
}

</script>
<br>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">操作员创建</td>
    
  </tr>
   
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 <form name="frmPost" method="post" >
<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
	<input type="hidden" name="func_flag" value="198" > 
	<input type="hidden" name="Action" value="" > 
	<tr> 
          <td  class="list_bg2"><div align="right">操作员姓名*</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtOperatorName" size="25" value="<tbl:writeparam name="txtOperatorName" />" >
           </td>  
           <td class="list_bg2"><div align="right">登录帐号</div></td>
           <td class="list_bg1" align="left">
          <input type="text" name="txtLoginID"  size="25"   value="<tbl:writeparam name="txtLoginID"/>">
          </td>
       </tr>
      
       <tr>
         <td class="list_bg2"><div align="right">密&nbsp;&nbsp;码*</div></td>
           <td class="list_bg1" align="left">
           <input name="txtLoginPwd" type="password" size="25" class="text" value="<tbl:writeparam name="txtLoginPwd"/>" style="width:150px;"></td>
           
          </td>
          <td class="list_bg2"><div align="right">密码确认</div></td>
           <td class="list_bg1" align="left">
           <input name="txtSecondLoginPwd" type="password"  size="25" class="text" value="<tbl:writeparam name="txtSecondLoginPwd"/>" style="width:150px;"></td>
           </td>
         
      </tr>
      
<%
	 
	 
	 
	if(Postern.getAllOrg()!=null)
	pageContext.setAttribute("allOrg",Postern.getAllOrg());
	
	else 
	pageContext.setAttribute("allOrg","");
	
%>
      <tr>
         <td class="list_bg2"><div align="right">所属组织</div></td>
           <td class="list_bg1" align="left">
           <tbl:select name="txtOrgID" set="allOrg" match="txtOrgID" width="25" />
          
          </td>
          <td class="list_bg2"><div align="right">状态</div></td>
           <td class="list_bg1" align="left">
           <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="25" /></td>
         
      </tr>
       <tr>
       <td class="list_bg2"><div align="right">操作员描述 </div></td>
           <td class="list_bg1" align="left"  colspan="3">
          <input type="text" name="txtOperatorDesc"  size="83"   value="<tbl:writeparam name="txtOperatorDesc"/>">
          </td>    
      </tr>
</table>
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
     <tr>
          <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="all_operator_query_result.do/menu_operator_query.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:operator_create_submit()" class="btn12">保&nbsp;存</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
            
         </tr>
        
   </table>   
<br>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
 
   
 
	 
    </td>
  </tr>
</form>  
</table>  
 
