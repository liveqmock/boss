<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
 
<%@ page import="com.dtv.oss.dto.LdapConditionDTO"%>
 

<SCRIPT language="JavaScript">
function checkFrm(){
	 
	if (!check_Num(document.frmPost.txtCondID, true, "条件ID"))
		return false;	
	 
   
       return true;
}

function query_submit()
{
   if(checkFrm()){
   		document.frmPost.submit();
   }
    
}
function create_submit() {
    
   document.frmPost.action="ldap_cond_create.screen";
   document.frmPost.submit();
}
 
function back_submit(){
	url="ldap_interface_index.screen";
    document.location.href= url;
}
function view_detail_click(aiNO) {
    self.location.href = "ldap_cond_detail.do?txtCondID="+aiNO;
 }
</SCRIPT>

<form name="frmPost" method="post" action="ldap_cond_query.do">
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >
 
<br>
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr>
		 
		<td>
		<table  border="0" align="center" cellpadding="0" cellspacing="10">
  				<tr>
    				<td><img src="img/list_tit.gif" width="19" height="19"></td>
    				<td  class="title">LDAP条件查询</td>
  				</tr>
  			</table>
			<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  				<tr>
    				<td><img src="img/mao.gif" width="1" height="1"></td>
  				</tr>
			</table>
 
<br>
<%
 
Map allLDAPHost = Postern.getAllLdapHostNameID();
pageContext.setAttribute("AllLDAPHost",allLDAPHost);

%>

<table width="98%" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">条件ID</td>
		<td class="list_bg1"><input name="txtCondID"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:writeparam name="txtCondID" />"></td>
		 <td class="list_bg2" align="right" >条件名称</td>
		<td class="list_bg1"><input name="txtCondName"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:writeparam name="txtCondName" />"></td>
	</tr>
	<tr>    
	        <td class="list_bg2" align="right" >LDAP主机名称</td>
		<td class="list_bg1" align="left" colspan="3"><tbl:select name="txtHostID" set="AllLDAPHost" match="txtHostID" width="23" /></td>
		 
		
	</tr>
	 
	 
	  
	
	
	 
</table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="查&nbsp;询" onclick="javascript:query_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>   
             <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="新&nbsp;增" onclick="javascript:create_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td> 
	
                     
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

<rs:hasresultset>
	<table width="98%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
		        <td class="list_head">条件ID</td> 
		        
		        <td class="list_head">条件名称</td>
		        <td class="list_head">主机名称</td>
		        
			 
			 
			
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
			<%
				LdapConditionDTO dto=(LdapConditionDTO)pageContext.getAttribute("oneline");
				 
				String hostName=(String)allLDAPHost.get(dto.getHostId()+"");
				if(hostName==null)hostName="";
				 
			%>
			<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
			    <td align="center" ><a href="javascript:view_detail_click('<tbl:write name="oneline" property="condId"/>')" class="link12">
                            <div align="center">
                            <tbl:writenumber name="oneline" property="condId" digit="9" />
                         </div>     
                           </td>
				 
				<td align="center"><tbl:write name="oneline" property="condName"/></td>
				 
				<td align="center"><%=hostName%></td>
				 
				 
				 
				 
				
			</tbl:printcsstr>
		</lgc:bloop>
		<tr>
        <td colspan="9" class="list_foot"></td>
       </tr>
 
            <tr>
              <td align="right" class="t12" colspan="9" >
                 第<span class="en_8pt"><rs:prop property="curpageno" /></span>页
                 <span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" />页
                 共有<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录&nbsp;&nbsp;&nbsp;&nbsp;             
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >首页 </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >上一页</a>
                 </rs:notfirst>
          
                <rs:notlast>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >下一页</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >末页</a>
                </rs:notlast>
                &nbsp;
                转到
               <input type="text" name="txtPage" class="page_txt">页 
               <a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
	</table>
</rs:hasresultset> <br>
</form>
