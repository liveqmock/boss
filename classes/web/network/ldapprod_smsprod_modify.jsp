<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                
                 com.dtv.oss.dto.LdapProdToSmsProdDTO,
                 java.util.*"%>
 

<SCRIPT language="JavaScript">
function check_frm ()
{
    if (check_Blank(document.frmPost.txtLdapProductName, true, "LDAP产品名称"))
	    return false;
    if (!check_Num(document.frmPost.txtPriority, true, "优先级"))
		return false;
		
	    
	return true;
}

function edit_submit(){
 if (window.confirm('您确认要修改LDAP产品/SMS产品对应关系吗?')){
  if (check_frm()){
	    document.frmPost.action="ldap_sms_edit.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
}
}
 
 function back_submit(){
	url="ldap_prod_to_smsprod.do";
        document.location.href= url;

} 
 

</SCRIPT>

<form name="frmPost" method="post">
	 
	
	<input type="hidden" name="Action"  value="">
	<input type="hidden" name="func_flag" value="230" >
	 
	<!-- 定义当前操作 -->
	 
    <table border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
    <td colspan="2" height="8"></td>
  </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">LDAP产品/SMS产品对应关系维护</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br> 

<table width="100%" align="center" border="0" cellspacing="1" cellpadding="3">
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
 <%
	 LdapProdToSmsProdDTO dto = (LdapProdToSmsProdDTO)pageContext.getAttribute("oneline"); 
	Map nameMap = Postern.getAllProductName();
	
	 String smsProdName=(String)nameMap.get(""+dto.getSmsProductId());
	  
	 
	 
	pageContext.setAttribute("allLdapProductName",Postern.getAllLdapProductName());
	 %>
 
	<tr>
	          <td  class="list_bg2"><div align="right">SMS产品名称</div></td>         
                  <td class="list_bg1" align="left">
                   <input type="text" name="txtSmsProductName" size="25" maxlength="50" value="<%=smsProdName%>" class="textgray" readonly >
                  
                    
                  </td>     
                   <input type="hidden" name="txtSmsProductId"   value="<tbl:write name="oneline" property="smsProductId" />">  
		 <td class="list_bg2"><div align="right">LDAP产品名称*</div></td>
                 <td class="list_bg1" align="left">
                   <tbl:select name="txtLdapProductName" set="allLdapProductName" match="oneline:ldapProductName" width="23" /> 
                  </td>       
                  
	</tr> 
	<tr>
	        <td  class="list_bg2"><div align="right">优先级</div></td>         
                 <td class="list_bg1" align="left" colspan="3">
                   <input type="text" name="txtPriority" maxlength="10" size="25" value="<tbl:write name="oneline" property="priority" />">  
                 </td>  
                
       </tr> 
       
	 
	  </lgc:bloop>  
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
	   <tr>
					<td><img src="img/button2_r.gif" width="22" height="20"></td>
					<td><input name="Submit" type="button" class="button"
						value="返&nbsp;回" onclick="javascript:back_submit()"></td>
					<td><img src="img/button2_l.gif" width="11" height="20"></td>

					<td width="20"></td>
					 
						<td><img src="img/button_l.gif" width="11" height="20"></td>
						<td><input name="Submit" type="button" class="button"
							value="保&nbsp;存" onclick="javascript:edit_submit()"></td>
						<td><img src="img/button_r.gif" width="22" height="20"></td>
					 
				</tr>
					 
          
      </table>	

</form>
