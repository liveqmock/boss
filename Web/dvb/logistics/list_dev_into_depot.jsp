<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ page import="com.dtv.oss.dto.VodstbDeviceImportDetailDTO" %>
<%@ page contentType="text/html; charset=GBK"%>
<Script language=JavaScript>
</Script>
<form name="formPost" method="post" action="list_dev_into_depot.do" >
<input type="hidden" name="txtFrom" value="1">
<input type="hidden" name="txtTo" value="10">
<%String no=null; %>
<rs:hasresultset>
<table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
  	<td class="list_head" ><div align="center">设备序列号</div></td>
      <td class="list_head" ><div align="center">CM_MAC</div></td>
      <td class="list_head" ><div align="center">STB_MAC</div></td>
      <td class="list_head" ><div align="center">状态</div></td>  
      <td class="list_head" ><div align="center">描述信息</div></td>
  </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%VodstbDeviceImportDetailDTO vdto = (VodstbDeviceImportDetailDTO)pageContext.getAttribute("oneline");
no=vdto.getBatchID()+"";%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <td><div align="center"><tbl:write name="oneline" property="serialNo"/></div></td>
      <td><div align="center"><tbl:write name="oneline" property="macaddress"/></div></td>
      <td><div align="center"><tbl:write name="oneline" property="intermacaddress"/></div></td>
      <td><div align="center"><d:getcmnname typeName="SET_D_STBDEVICEIMPORTDETAILSTATUS" match="oneline:status" /></div></td>
      <td><div align="center"><tbl:write name="oneline" property="description"/></div></td>
</tbl:printcsstr>
</lgc:bloop>
 <tr>
      <td colspan="5" class="list_foot"></td>
    </tr>
    <tr class="trline" >
       <td align="right" class="t12" colspan="5" >
          第<span class="en_8pt"><rs:prop property="curpageno" /></span>页
          <span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" />页
          共有<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录&nbsp;&nbsp;&nbsp;&nbsp;             
          <rs:notfirst>
          <img src="img/dot_top.gif" width="17" height="11">
          <a href="javascript:firstPage_onClick(document.formPost, <rs:prop property="firstto" />)" >首页 </a>
          <img src="img/dot_pre.gif" width="6" height="11">
          <a href="javascript:previous_onClick(document.formPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >上一页</a>
          </rs:notfirst>
          
          <rs:notlast>
          &nbsp;
          <img src="img/dot_next.gif" width="6" height="11">
          <a href="javascript:next_onClick(document.formPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >下一页</a>
          <img src="img/dot_end.gif" width="17" height="11">
          <a href="javascript:lastPage_onClick(document.formPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >末页</a>
          </rs:notlast>
          &nbsp;
          转到
          <input type="text" name="txtPage" class="page_txt">页 
          <a href="javascript:goto_onClick(document.formPost, <rs:prop property="pageamount" />)" >
               <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
          </a>
        </td>
     </tr>    
</table>   
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
</rs:hasresultset>  
<input type="hidden" name="txtBatchID" value="<%=no %>">
</form>