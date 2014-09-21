<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="java.util.List,
                  com.dtv.oss.util.Postern,
                  com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.dto.OperatorDTO"%>
 <%@ taglib uri="bookmark" prefix="bk" %>

<script language=javascript>

 function create_operator_submit() {
    
   document.frmPost.action="operator_create.screen";
   document.frmPost.submit();
} 
function view_detail_click(strId)
{
	self.location.href="operator_detail.do?txtOperatorID="+strId;
	 
}
//function view_detail_click(strId)
//{
//	self.location.href="organization_query_result.do?txtOperatorID="+strId;
	 
//}
  

</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">操作员列表</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" action="operator_query.do">
    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
     <input type="hidden" name="txtOrgID" size="20" value="<tbl:writeparam name="txtOrgID"/>">
     
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
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
          
           <td class="list_head" colspan="2">所属操作员组</td>
           
        </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
         <%
           OperatorDTO dto=(OperatorDTO)pageContext.getAttribute("oneline");
           int orgId = dto.getOrgID();
           
           String orgName = Postern.getOrgNameByOrgID(orgId);
         %>
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="operatorID"/>')" class="link12" ><tbl:writenumber name="oneline" property="operatorID" digit="7"/></a></td>
      	     <td align="center"><tbl:write name="oneline" property="operatorName"/></td>
      	     <td align="center"><tbl:write name="oneline" property="loginID"/></td>
      	     <td align="center"><%=orgName%></td>
      	      <td align="center"><d:getcmnname typeName="SET_S_OPERATORLEVEL" match="oneline:levelID"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>   
      	     <td align="center"> 
      	      <a href="operator_to_group_config.screen?txtOrgName=<%=orgName%>&txtOperatorID=<tbl:write name="oneline" property="operatorID"/>&txtOperatorName=<tbl:write name="oneline" property="operatorName"/>&txtOperatorDesc=<tbl:write name="oneline" property="operatorDesc"/>"/>所属操作员组</a>
            </td>
             
    	 
</lgc:bloop>  

<tr>
    <td colspan="7" class="list_foot"></td>
  </tr>
<tr>
   <td align="right" class="t12" colspan="7" >
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
 
   
 
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
       <td><img src="img/button2_r.gif" border="0" ></td>
	<td background="img/button_bg.gif"><a href="<bk:backurl property="organization_query_result.do"/>" class="btn12">返&nbsp;回</a></td>
	 <td><img src="img/button2_l.gif" border="0" ></td>
           <td width="20" ></td>  
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:create_operator_submit()" class="btn12">创&nbsp;建</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
         
          
         
	
        </tr>
      </table>    
	 
    </td>
  </tr>
</form>  
</table>  
 
