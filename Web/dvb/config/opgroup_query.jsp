<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern,
               com.dtv.oss.dto.OpGroupDTO,
               java.util.*" %>
 

<script language=javascript>

function check_frm()
{
	
 	
    if (!checkPlainNum(document.frmPost.txtOpGroupID, true,9, "操作员组ID"))
	    return false;
    
	    
	    
     return true;
}
 
function query_submit()
{
	     if(check_frm())
		document.frmPost.submit();
	 
}

function view_detail_click(strId)
{
	self.location.href="op_group_detail.do?txtOpGroupID="+strId;
} 
 
 
 
function create_opgroup_submit() {
    
   document.frmPost.action="create_opgroup_create.screen";
   document.frmPost.submit();
}

</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">操作员组查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" action="opgroup_query.do" >
 
 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      
        <tr> 
          <td  class="list_bg2"><div align="right">操作员组ID</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtOpGroupID" maxlength="10" size="22"  value="<tbl:writeparam name="txtOpGroupID" />" >
           </td>      
             <td  class="list_bg2"><div align="right">操作员组名称</div></td>         
             <td class="list_bg1" align="left">
              <input type="text" name="txtOpGroupName" maxlength="25" size="22"  value="<tbl:writeparam name="txtOpGroupName" />" >
             </td>      
           
        </tr>
         
     
       <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1">
	         <table  border="0" cellspacing="0" cellpadding="0">							
		  <tr>
		       <td><img src="img/button_l.gif" border="0" ></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"/></td>
			<td><img src="img/button_r.gif" border="0" ></td>
		  	 
			<td width=20 ></td>
			<td><img src="img/button_l.gif" border="0" ></td>
			<td><input name="Submit" type="button" class="button" value="新 增" onclick="javascript:create_opgroup_submit()"/></td>
			<td><img src="img/button_r.gif" border="0" ></td>
		     </tr>
	  	</table> 
	 </td>
  </tr>
  </table>
    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
     
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
 
  
  <rs:hasresultset>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
            <td class="list_head">操作员组ID</td>
            <td class="list_head">操作员组名称</td>
            <td class="list_head">操作员组级别</td>
             <td class="list_head">系统内部组标志</td>
            <td class="list_head">操作员组权限</td>
            <td class="list_head">操作员组成员</td>
          </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
 <%
        OpGroupDTO opGroupDto = (OpGroupDTO)pageContext.getAttribute("oneline");
        
 %>
 
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="opGroupID"/>')" class="link12" ><tbl:writenumber name="oneline" property="opGroupID" digit="7"/></a></td>
      	     <td align="center"><tbl:write name="oneline" property="opGroupName"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_S_OPGROUPLEVEL" match="oneline:opGroupLevel"/></td> 
      	     <td align="center"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:systemFlag"/></td> 
      	     <td align="center">
      	      <a href="operator_group_privilege.screen?txtOpGroupID=<tbl:write name="oneline" property="opGroupID"/>
&txtOpGroupName=<tbl:write name="oneline" property="opGroupName"/>&txtSystemFlag=<tbl:write name="oneline" property="systemFlag"/>&txtOpGroupDesc=<tbl:write name="oneline" property="opGroupDesc"/>"/>拥有的权限</a>
      	     </td>
      	     <td align="center">
      	      <a href="operator_group_member.screen?txtOpGroupID=<tbl:write name="oneline" property="opGroupID"/>
&txtOpGroupName=<tbl:write name="oneline" property="opGroupName"/>&txtSystemFlag=<tbl:write name="oneline" property="systemFlag"/>&txtOpGroupDesc=<tbl:write name="oneline" property="opGroupDesc"/>">组成员</a>
      	     </td>
      	   <tr>
 </lgc:bloop>  
    <td colspan="8" class="list_foot"></td>
  </tr>
 
            <tr>
              <td align="right" class="t12" colspan="8" >
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
	 
    </td>
  </tr>
</form>  
</table>  
 
