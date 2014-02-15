<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>             
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.dto.QueryDTO"%>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>


<script language=javascript>
function batch_query_model_submit(){
      document.frmPost.action="batch_query_model_query.do";
      document.frmPost.submit();
}

function view_batch_detail(batchid){
	var templageFlag="<%=CommonConstDefinition.YESNOFLAG_YES%>";
	var strURL="batch_query_show_detail.do?txtQueryID="+batchid+"&txtActionType=batch&txtShowFlag=Y&txtTemplateFlag=" + templageFlag;
	self.location.href=strURL;
}

function batch_query_model_create_submit(){
	document.frmPost.action="batch_query_create.screen";
	document.frmPost.submit();
}

function batch_query_basemodel_create_submit(queryid){
	document.frmPost.templateFlag.value="<%=CommonConstDefinition.YESNOFLAG_NO%>";
	document.frmPost.action="batch_module_query_create.do?txtQueryID="+queryid;
	document.frmPost.submit();
}


</script>

<form name="frmPost" method="post" action="batch_query_model_query.do">

<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">

<input type="hidden" name="txtActionType" size="20" value="batch">
<input type="hidden" name="txtTemplateFlag" size="20" value="<%=CommonConstDefinition.YESNOFLAG_YES%>">
<input type="hidden" name="templateFlag" size="20" value="<%=CommonConstDefinition.YESNOFLAG_YES%>">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">批量查询模板----查询</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	
  <tr>
    <td class="list_bg2"><div align="right">模板编号</div></td>
    <td class="list_bg1"><input name="txtQueryID" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtQueryID" />"></td>
    <td class="list_bg2"><div align="right">模板名称</div></td>
    <td class="list_bg1"><input name="txtQueryName" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtQueryName" />"></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">结果集类型</div></td>
    <td class="list_bg1"><d:selcmn name="txtQueryType" mapName="SET_B_QUERYTYPE"  match="txtQueryType"  width="23" /></td>
    <td class="list_bg2"><div align="right">执行方式</div></td>
    <td class="list_bg1"><d:selcmn name="txtScheduleType" mapName="SET_B_SCHEDULETYPE"  match="txtScheduleType"  width="23" /></td>
  </tr> 
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查&nbsp;询" onclick="javascript:batch_query_model_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="创建批量查询模板" onclick="javascript:batch_query_model_create_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
		  </tr>
	  </table></td>
	</tr>
</table> 

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   <tr class="list_head">
    <td class="list_head">模板编号</td>
    <td class="list_head">模板名称</td>
    <td class="list_head">结果集类型</td>
    <td class="list_head">执行方式</td>
    <td class="list_head">状态</td>
    <td class="list_head">操作</td>
  </tr> 
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    QueryDTO dto = (QueryDTO)pageContext.getAttribute("oneline");
    String status = dto.getStatus();
    
    pageContext.setAttribute("QueryDTO",dto);
    
    String strOpName="";
    strOpName=Postern.getOperatorNameByID(dto.getCreateOperatorId());
    if(strOpName==null)
    	strOpName="";
    
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <td align="center" class="t12"><a href="javascript:view_batch_detail('<tbl:write name="QueryDTO" property="queryId"/>')" class="link12" ><tbl:write name="QueryDTO" property="queryId"/></a></td>
    <td align="left" class="t12"><tbl:write name="QueryDTO" property="queryName"/></td>  
    <td align="center" class="t12"><d:getcmnname typeName="SET_B_QUERYTYPE" match="QueryDTO:queryType" /></td>
    <td align="center" class="t12"><d:getcmnname typeName="SET_B_SCHEDULETYPE" match="QueryDTO:scheduleType" /></td>
    <td align="center"><d:getcmnname typeName="SET_B_QUERYSTATUS" match="QueryDTO:status" /></td>
    
    <td align="center" class="t12">
    <%if(!CommonConstDefinition.QUERY_STATUS_CANCEL.equals(status))
    {%>
    	<a href="javascript:batch_query_basemodel_create_submit('<tbl:write name="QueryDTO" property="queryId"/>')" class="link12" >创建批量查询操作</a>
    <%}%>	
    </td>
</tbl:printcsstr>
</lgc:bloop>
<tr>
    <td colspan="10" class="list_foot"></td>
</tr>
</table> 
 
<table  border="0" align="center" cellpadding="0" cellspacing="8">
  <tr>
    <td>第<span class="en_8pt"><rs:prop property="curpageno" /></span>页<span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" /><span>页</td>
    <td>共<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">首页</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">上一页</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">下一页</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">末页</a></td>
</rs:notlast>
    <td align="right">跳转到</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>页</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
  </tr>
  </table>
 </rs:hasresultset> 
 <br>
</form>  
         

      