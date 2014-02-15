<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.dto.QueryDTO"%>
<%@ page import="com.dtv.oss.dto.QueryResultItemDTO"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<%
    QueryDTO query = Postern.getQueryDTOByQueryID(WebUtil.StringToInt(request.getParameter("txtQueryID")));
    pageContext.setAttribute("query", query);
    String strOpName="";
    strOpName=Postern.getOperatorNameByID(query.getCreateOperatorId());
    if(strOpName==null)
    	strOpName="";
    int n = Postern.getValidTotalNum(WebUtil.StringToInt(request.getParameter("txtQueryID")));
%>

<script language=javascript>
<!--
 function process_log_submit(queryID){	
   window.location.href="batch_job_process_log.do?txtBatchID=" + queryID;
}
//-->
</script>

<form name="frmPost" method="post" action="" >
<input type="hidden" name="txtQueryType" size="20" value="<tbl:writeparam name="txtQueryType" />">
<input type="hidden" name="txtStatus" size="20" value="S">
<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
<input type="hidden" name="txtActionType" size="20" value="result">
<input type="hidden" name="txtQueryID" t value="<tbl:writeparam name="txtQueryID" />">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  	<tr>
    	<td><img src="img/list_tit.gif" width="19" height="19"></td>
    	<td class="title">批量查询操作记录明细</td>
  	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
	<tr>
	        <td colspan="4" class="import_tit" align="center"><font size="2">批量查询操作头记录</font></td>
        </tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">批量查询操作记录号</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtQueryID" /></td>
		<td class="list_bg2" align="right" width="17%">批量查询操作名称</td>
		<td class="list_bg1" width="33%"><tbl:write name="query" property="QueryName"/></td>
 	</tr>
	<tr>
   		<td class="list_bg2" align="right">批量查询操作创建时间</td>
   		<td class="list_bg1"><tbl:writedate name="query" property="CreateTime" includeTime="false"/></td>
		<td class="list_bg2" align="right">批量查询操作创建人</td>
		<td class="list_bg1"><%=strOpName%></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">批量查询操作执行方式</td>
   		<td class="list_bg1"><d:getcmnname typeName="SET_B_SCHEDULETYPE" match="query:ScheduleType" /></td>
   		<td class="list_bg2" align="right">批量查询操作排程执行时间</td>
   		<td class="list_bg1"><tbl:writedate name="query" property="ScheduleTime" includeTime="false"/></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">批量查询操作实际执行时间</td>
   		<td class="list_bg1"><tbl:writedate name="query" property="PerformTime" includeTime="false"/></td>
   		<td class="list_bg2" align="right">批量查询操作有效记录总数</td>
		<td class="list_bg1"><%=n%></td>
	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<rs:hasresultset>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  	<tr> 
	   <td colspan="9" class="import_tit" align="center"><font size="3">批量查询操作明细</font></td>
  	</tr>
  	<tr class="list_head">
  	    <td class="list_head">明细号</td>
    	    <td class="list_head">用户证号</td>
    	    <td class="list_head">客户姓名</td>
	    <td class="list_head">客户帐号</td>
	    <td class="list_head">业务账户</td>
	    <td class="list_head">产品包</td>
	    <td class="list_head">客户产品</td>
	    <td class="list_head">促销活动</td>
	    <td class="list_head">状态</td>
  	</tr>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
  	<%
	    QueryResultItemDTO dto = (QueryResultItemDTO)pageContext.getAttribute("oneline");
	    
	    pageContext.setAttribute("QueryResultItemDTO",dto);
	    
	    String customerName="";
	    String userName="";
	    String packageName="";
	    String productName="";
	    String ccName="";
	    customerName=Postern.getCustomerNameByID(dto.getCustomerId());
	    userName=Postern.getServiceNameByID(Postern.getServiceIDByAcctServiceID(dto.getUserId()));
	    packageName=Postern.getPackagenameByID(dto.getPackageId());
	    productName=Postern.getProductNameByID(dto.getProductId());
	    ccName=Postern.getCampaignNameByID(dto.getCcId());
	    if(customerName==null)
	    	customerName="";
		if(userName==null)
	    	userName="";
	    if(productName==null)
	    	productName="";
	    if(packageName==null)
	    	packageName="";
	    if(ccName==null)
	    	ccName="";
	%>
  	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	    <td><tbl:write name="QueryResultItemDTO" property="itemNO"/></td>
	    <td><tbl:write name="QueryResultItemDTO" property="customerId"/></td>
	    <td><%=customerName%></td>
	    <td><tbl:write name="QueryResultItemDTO" property="accountId"/></td>
	    <td><%=userName %></td>
	    <td><%=packageName %></td>
	    <td><tbl:write name="QueryResultItemDTO" property="psId"/></td>
	    <td><%=ccName %></td>
	    <td><d:getcmnname typeName="SET_G_GENERALSTATUS" match="QueryResultItemDTO:status" /></td>
	</tbl:printcsstr>
	</lgc:bloop>
	<tr>
    	<td colspan="9" class="list_foot"></td>
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
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<bk:canback url="ownFeeBatch_query_result.do">
		<td width="20" ></td>  
		<td><img src="img/button2_r.gif" width="22" height="20"></td>
		<td background="img/button_bg.gif"  height="20" >
		<a href="<bk:backurl property="ownFeeBatch_query_result.do"/>" class="btn12">返&nbsp;回</a></td>
		<td><img src="img/button2_l.gif" width="13" height="20"></td>
		</bk:canback>
				
		<td width="20" ></td>  
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td background="img/button_bg.gif"  height="20" >
		<a href="javascript:process_log_submit('<tbl:writeparam name="txtQueryID"/>');" class="btn12">查看处理日志</a></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	</tr>
</table>
	 
</form>