<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.WebUtil,
                 
                 java.util.*"%>
 

<SCRIPT language="JavaScript">

function check_frm(){
	 
	if (check_Blank(document.frmPost.txtCondName, true, "条件名称"))
		return false;	
	 
   
       return true;
}

 
function ldap_cond_create_submit(){
 
 
   if (check_frm()){
	    document.frmPost.action="ldap_cond_create.do";
	    document.frmPost.Action.value="create";
	    document.frmPost.submit();
	   
   
  }
}
 
 function back_submit(){
	url="<bk:backurl property='ldap_cond_query.do'/>";
	if(url=="")
		url="ldap_condition_query.screen";
    document.location.href=url;
}
  
 

</SCRIPT>

<form name="frmPost" method="post">
	 
	
	<input type="hidden" name="Action"  value="">
	<input type="hidden" name="func_flag" value="227" >
	 
	<!-- 定义当前操作 -->
	 
    <table border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
    <td colspan="2" height="8"></td>
  </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">LDAP条件创建</td>
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
 
 <%
	 Map allLDAPHost = Postern.getAllLdapHostNameID();
         pageContext.setAttribute("AllLDAPHost",allLDAPHost);
%>
 
	<tr>
	          
		 <td class="list_bg2"><div align="right">条件名称*</div></td>
                 <td class="list_bg1" align="left">
                   <input type="text" name="txtCondName" size="25"  maxlength="25" value="<tbl:writeparam name="txtCondName" />"> 
                  </td>       
                  
	 
	        <td class="list_bg2" align="right" >LDAP主机名称</td>
		<td class="list_bg1" align="left"><tbl:select name="txtHostID" set="AllLDAPHost" match="txtHostID" width="23" /></td>
		 
		
	</tr>
	  
	 <tr>
		<td  class="list_bg2"><div align="right">条件字符串</div></td>         
                <td class="list_bg1" align="left" colspan="3">
                <input type="text" name="txtCondString" size="83" maxlength="100" value="<tbl:writeparam name="txtCondString" />"> 
                 
                </td> 
               
       </tr>
       <tr>
		<td  class="list_bg2"><div align="right">描述</div></td>         
                <td class="list_bg1" align="left" colspan="3">
                <input type="text" name="txtDesption" size="83"  maxlength="100" value="<tbl:writeparam name="txtDesption" />"> 
                 
                </td> 
               
       </tr>
       
	  
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
            <td width="20" ></td>  
            <td><img src="img/button_l.gif" width="11px" height="20px"></td>
            <td><input name="Submit" type="button" class="button"
	    value="保&nbsp;存" onclick="javascript:ldap_cond_create_submit()"></td>
            <td><img src="img/button_r.gif" width="22px" height="20px"></td>    
             
                  
        </tr>
      </table>	

</form>
