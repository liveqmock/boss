<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.ServiceDTO" %>

<script language="javascript" >
function add_submit(){
    document.frmPost.action ="config_serviceBaseInfo_add.screen";
    document.frmPost.submit();
}
</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td align="center"><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">业务基本信息管理</td>
  </tr>
</table>
<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<br>
<form name="frmPost" method="post" action="config_serviceInfo_query.do" >
    <input type="hidden" name="txtFrom"  value="1">
    <input type="hidden" name="txtTo"  value="10">
    <input type="hidden" name="func_flag" value="20001" >
    <input type="hidden" name="ActionFlag" value="del_service" >
    <!--<input type="hidden" name="txtServiceIds" value="" >-->
   
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr class="list_head">
        <!--<td class="list_head" width="5%"><div align="center"><input type="checkbox" name="allchoose" value="" onclick="AllChoose()"></div></td>-->
        <td class="list_head" width="19%">业务ID</td>
        <td class="list_head" width="19%">业务名称</td>
        <td class="list_head" width="19%">业务状态</td>
        <td class="list_head" width="19%">有效起始时间</td>
        <td class="list_head" width="19%">有效结束时间</td>
      </tr>
      <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
        <%
          ServiceDTO dto =(ServiceDTO)pageContext.getAttribute("oneline");
          String strUrl ="config_serviceInfo_prepare.do?txtServiceID="+dto.getServiceID();
        %>
         <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
            <td align="center" ><a href ="<%=strUrl%>"  class="link12"><tbl:writenumber name="oneline" property="serviceID" digit="7"/></a>
            </td>
            <td align="center" ><tbl:write name="oneline" property="serviceName" /></td>
            <td align="center" ><d:getcmnname typeName="SET_P_SERVICESTATUS" match="oneline:status" /></td>
            <td align="center" ><tbl:writedate name="oneline" property="dateFrom"/></td>
            <td align="center" ><tbl:writedate name="oneline" property="dateTo"/></td>
         </tbl:printcsstr>
      </lgc:bloop>
	<tr>
	    <td colspan="8" class="list_foot"></td>
	</tr>
     <tr class="trline" >
         <td colspan="6">
         <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
          <tr>
            <td><img src="img/mao.gif" width="1" height="1"></td>
          </tr>
         </table>
       </td></tr>
      <rs:hasresultset>
        <tr class="trline" >
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
        
       </rs:hasresultset>             
       
      </table> 
      <BR>  
      <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
           <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td><input name="Submit" type="button" class="button" value="新 增" onclick="javascript:add_submit()"></td>
	    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
            
            <!--
            <td width="20" ></td>  
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="next" type="button" class="button" onClick="del_submit()" value="删  除"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td> 
            -->
        </tr>
      </table>	
      <input type="hidden" name="confirm_post"  value="true" >
      <tbl:generatetoken /> 
</form>
