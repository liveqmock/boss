<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="java.util.List,
                  com.dtv.oss.util.Postern,
                  com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.dto.OperatorDTO"%>
 

<script language=javascript>

 function create_operator_submit() {
    
   document.frmPost.action="config_operator_create.screen";
   document.frmPost.submit();
} 
function view_detail_click(strId)
{
	self.location.href="config_operator_detail.do?txtOperatorID="+strId;
	 
}
function query_submit()
{
   if (check_form())
     
    	
        document.frmPost.submit();
   
}
function check_form ()
{

   if (!checkPlainNum(document.frmPost.txtOperatorID, true,9, "操作员ID"))
	    return false;
	return true;
}
  

</script>
<table width="98%" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">操作员查询</td>
  </tr>
  </table>
  
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>

 
<form name="frmPost" method="post"  action="all_operator_query_result.do">
    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
    <table width="98%" align="center" border="0" cellspacing="1" cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">操作员ID</td>
		<td class="list_bg1" width="33%"><input name="txtOperatorID" type="text"  maxlength="10"  size="25" value="<tbl:writeparam name="txtOperatorID" />"></td>
		<td class="list_bg2" align="right" width="17%">登录帐号</td>
		<td class="list_bg1" width="33%"><input name="txtLoginID" type="text"  maxlength="20"   size="25" value="<tbl:writeparam name="txtLoginID" />"></td>
		
	</tr>
	<%
	if(Postern.getAllOrg()!=null)
	pageContext.setAttribute("allOrg",Postern.getAllOrg());
	
	else 
	pageContext.setAttribute("allOrg","");
	
	%>
        <tr>
        <td class="list_bg2" align="right" width="17%">操作员姓名</td>
	<td class="list_bg1" width="33%"><input name="txtOperatorName" type="text"  maxlength="25"   size="25" value="<tbl:writeparam name="txtOperatorName" />"></td>
	<td class="list_bg2" align="right">所属组织</td>
     <td class="list_bg1">
    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
	     <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" >
	    <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,D,P,B,O,S','txtOrgID','txtOrgDesc')">
    </td>	
         
          </td>
            <tr>
		<td class="list_bg2" align="right">状态</td>
		<td class="list_bg1" colspan="3" ><font size="2"> <d:selcmn name="txtStatus"
			mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" /> </font></td>
		 
	</tr>
</table>
 
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table> 
	 </td>
  </tr>
  </table>
   
 
<table width="98%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>

 
  <rs:hasresultset>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
           <td class="list_head">操作员ID</td>
           <td class="list_head">操作员姓名</td>
           <td class="list_head">登录帐号</td>
           <td class="list_head">所属组织</td>
           <td class="list_head">授权级别</td>
          <td class="list_head">状态</td>
           
           
        </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
         <%
           OperatorDTO dto=(OperatorDTO)pageContext.getAttribute("oneline");
           int orgId = dto.getOrgID();
           String orgName = Postern.getOrgNameByID(orgId);
         %>
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	  <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="operatorID"/>')" class="link12" ><tbl:writenumber name="oneline" property="operatorID" digit="7"/></a></td>
	     
      	     <td align="center"><tbl:write name="oneline" property="operatorName"/></td>
      	     <td align="center"><tbl:write name="oneline" property="loginID"/></td>
      	     <td align="center"><tbl:WriteOrganizationInfo name="oneline" property="orgID"/></td> 
      	      <td align="center"><d:getcmnname typeName="SET_S_OPERATORLEVEL" match="oneline:levelID"/></td> 
      	     <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>   
      	      
             
    	 
</lgc:bloop>  

<tr>
    <td colspan="6" class="list_foot"></td>
  </tr>
 
            <tr>
              <td align="right" class="t12" colspan="6" >
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
               <a href="javascript:return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
</table>   
 
   
	 
</rs:hasresultset> 
  
	 
    </td>
  </tr>
</form>  
</table>  
 
