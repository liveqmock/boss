<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.ServiceResourceDTO" %>

<script language="javascript" >
function view_edit(resourceName){
	document.frmPost.action="config_service_resource_editing.do?editing_type=view_edit&txtResourceName="+resourceName;
	document.frmPost.submit();
}

function add_submit(){
	document.frmPost.action="config_service_resource_editing.do?editing_type=new";
	document.frmPost.submit();	
}
</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td align="center"><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">业务资源基本信息管理</td>
  </tr>
</table>
<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="config_serviceResource_query.do" >
    <input type="hidden" name="txtFrom"  value="1">
    <input type="hidden" name="txtTo"  value="10">   
       <rs:hasresultset>
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr class="list_head">
        <td class="list_head" width="25%">资源名称</td>
        <td class="list_head" width="20%">资源类型</td>
        <td class="list_head" width="20%">资源状态</td>
        <td class="list_head" width="35%">资源描述</td>
      </tr>
      <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
        <%
          ServiceResourceDTO dto =(ServiceResourceDTO)pageContext.getAttribute("oneline");
          //String strUrl ="config_resource_prepare.do?txtResourceName="+dto.getResourceName();
        %>
         <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >             
            <td align="center" ><a href ="javascript:view_edit('<tbl:write name="oneline" property="resourceName" />')"  class="link12"><tbl:write name="oneline" property="resourceName" /></a></td>
            <td align="center" ><d:getcmnname typeName="SET_R_RESOURCETYPE" match="oneline:resourceType"/></td>
            <td align="center" ><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status"/></td>
            <td align="center" ><tbl:write name="oneline" property="resourceDesc" /></td>
         </tbl:printcsstr>
      </lgc:bloop>
     <tr>
    <td colspan="10" class="list_foot"></td>
  </tr>

 <tr>
   <td align="right" class="t12" colspan="10" >
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
       </rs:hasresultset>             

     <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
     </table>

      <BR>  
      <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="next" type="button" class="button" onClick="javascript:add_submit()" value="新  增"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
      <input type="hidden" name="confirm_post"  value="true" >
      <tbl:generatetoken /> 
</form>
