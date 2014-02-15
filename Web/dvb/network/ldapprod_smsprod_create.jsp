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
	
	
    if (check_Blank(document.frmPost.txtSmsProductId, true, "SMS产品名称"))
	    return false;
    
    
   if (check_Blank(document.frmPost.txtLdapProductName, true, "LDAP产品名称"))
		return false;
		
    if (!check_Num(document.frmPost.txtPriority, true, "优先级"))
		return false;
		
  

	return true;
} 
 
function create_submit() {
    if(check_frm()){
   document.frmPost.action="ldap_sms_create.do";
   document.frmPost.Action.value="create";
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
    <td  class="title">新增LDAP产品/SMS产品对应关系</td>
  </tr>
  </table>
  <table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>

 
<form name="frmPost" method="post" >
<table width="100%" align="center" border="0" cellspacing="1" cellpadding="3">
	<input type="hidden" name="func_flag" value="230" > 
	<input type="hidden" name="Action" value="" > 
	<%
	 
	 pageContext.setAttribute("allProductName",Postern.getAllProductName());
	 
	pageContext.setAttribute("allLdapProductName",Postern.getAllLdapProductName());
%> 
 
	<tr>
	        <td  class="list_bg2"><div align="right">SMS产品名称*</div></td>         
                 <td class="list_bg1" align="left">
                  <tbl:select name="txtSmsProductId" set="allProductName" match="txtSmsProductId" width="23" />
                 </td>  
                 <td class="list_bg2"><div align="right">LDAP产品名称*</div></td>
                 <td class="list_bg1" align="left">
                 <tbl:select name="txtLdapProductName" set="allLdapProductName" match="txtLdapProductName" width="23" /> 
		 
                </td> 
                
       </tr>
       <tr>
	        <td  class="list_bg2"><div align="right">优先级</div></td>         
                 <td class="list_bg1" align="left" colspan="3">
                   <input type="text" name="txtPriority" maxlength="10" size="25" value="<tbl:writeparam name="txtPriority" />" >
                 </td>  
                
       </tr>
</table>
 <table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
     <tr>
     
          <td><img src="img/button2_r.gif" width="22" height="20"></td>  
            <td background="img/button_bg.gif"><a href="<bk:backurl property="ldap_prod_to_smsprod.do" />" class="btn12">返&nbsp;回</a></td>
            <td><img src="img/button2_l.gif" width="13" height="20"></td>
            <td width="20" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:create_submit()" class="btn12">保&nbsp;存</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
     
           
            
         </tr>
        
   </table>   
<br>
 
 
	 
    </td>
  </tr>
</form>  
</table>  
 
