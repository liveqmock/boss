<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<script language=javascript>
  function view_detail_click(strId,jobType)
 {
		self.location.href="job_card_view.do?txtJobCardID="+strId;
 }
 
 
</script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">工单查询</td>
  </tr>
</table>
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost"  method="post" >   

 
 
	  <input type="hidden" name="txtFrom" size="22" value="1">
          <input type="hidden" name="txtTo" size="22" value="10">
           
         

  
<rs:hasresultset>
 
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">工单编号</td>
    <td class="list_head">工单类型</td>
    <td class="list_head">工单子类型</td>
    <td class="list_head">用户证号</td>
    <td class="list_head">联系人</td>
    <td class="list_head">联系电话</td>
    <td class="list_head">联系地址</td>
    <td class="list_head">所属区域</td>
    <td class="list_head">状态</td>
  </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
     com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap wrap = (com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
     JobCardDTO dto=wrap.getJobCardDto();
     pageContext.setAttribute("oneline", wrap.getJobCardDto());
      pageContext.setAttribute("process", wrap.getJobCardProcessDto());
     
     String strAddress = Postern.getAddressById(dto.getAddressId());
     AddressDTO addrDto = Postern.getAddressDtoByID(dto.getAddressId());
     pageContext.setAttribute("custaddr", addrDto);
    if (strAddress == null) strAddress="";
	 
%>
 

 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
    <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="JobCardId" />','<tbl:write name="oneline" property="jobType" />')" class="link12" ><tbl:write name="oneline" property="JobCardId" /></a></td>
     <td align="center"><d:getcmnname typeName="SET_W_JOBCARDTYPE" match="oneline:jobType" /></td>
     <td align="center"><d:getcmnname typeName="SET_W_JOBCARDSUBTYPE" match="oneline:subType" /></td>
    <td align="center"><tbl:writenumber name="oneline" property="CustId" digit="8" hideZero="true"/></td>
    <td align="center"><tbl:write name="oneline" property="ContactPerson"/></td>
    <td align="center"><tbl:write name="oneline" property="ContactPhone"/></td>  
    <td align="center"><%=strAddress%></td>
    <td align="center"><d:getdistrictname match="custaddr:districtID" /></td>
    <td align="center"><d:getcmnname typeName="SET_W_JOBCARDSTATUS" match="oneline:status" /></td>
  </tr>
 

</lgc:bloop>
<tbl:generatetoken />

  <tr>
					<td colspan="11" class="list_foot"></td>
			</tr>
        	<tr>
              <td align="right" class="t12" colspan="11" >
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
</TD>
</TR>
</TABLE>
</form> 