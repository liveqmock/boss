<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.dto.AccountItemDTO"%>

<SCRIPT language="JavaScript">
function check_form ()
{
    if (document.frmPost.txtCreateTime1.value != '')
    {
		if (!check_TenDate(document.frmPost.txtCreateTime1, true, "创建日期开始日期"))
			return false;
	}
	if (document.frmPost.txtCreateTime2.value != '')
	{
		if (!check_TenDate(document.frmPost.txtCreateTime2, true, "创建日期结束日期"))
			return false;
	}
	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"创建日期结束日期必须大于等于创建日期开始日期"))
		return false;

	return true;
}

function query_submit()
{
    if (check_form())
        document.frmPost.submit();
}

function depositReturn(strID)
{
    location.href = "customer_deposit_return.do?txtAiNo="+strID;
}

function depositConfiscate(strID)
{
    location.href = "customer_deposit_confiscate_confirm.do?txtAiNo="+strID;
}
</SCRIPT>

<form name="frmPost" method="post" action="customer_deposit_query_result.do" >
<input type="hidden" name="txtCustomerID" size="20" value="<tbl:writeparam name="txtCustomerID" />">
<input type="hidden" name="txtAiNo" size="20" value="">
<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  	<tr>
    	<td><img src="img/list_tit.gif" width="19" height="19"></td>
    	<td class="title">客户押金查询</td>
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
		<td class="list_bg2" align="left" width="17%">账户号</td>
		<td class="list_bg1" width="33%"><input name="txtAcctID" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtAcctID" />"></td>
		<td class="list_bg2" align="left" width="17%">受理单号</td>
		<td class="list_bg1" width="33%"><input name="txtCSIID" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtCSIID" />"></td>
 	</tr>
	<tr>
   		<td class="list_bg2">创建日期</td>
   		<td class="list_bg1"><font size="2">
     	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" name="txtCreateTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime1" />">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
     	-
     	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" name="txtCreateTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime2" />">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	</font></td>
    	<td class="list_bg1" colspan="3"><table  border="0" cellspacing="0" cellpadding="0">
		  	<tr>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="查&nbsp;询" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  	</tr>
	  	</table></td>
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
  	<tr class="list_head">
    	<td class="list_head">流水号</td>
	    <td class="list_head">账户号</td>
	    <td class="list_head">帐户名称</td>
	    <td class="list_head">受理单号</td>
	    <td class="list_head">受理类型</td>
	    <td class="list_head">创建日期</td>
	    <td class="list_head">押金金额</td>
	    <td class="list_head">销帐金额</td>
	    <td class="list_head">状态</td>
	    <td class="list_head">退还押金</td>
	    <td class="list_head">没收押金</td>
  	</tr>
  	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
  	<%
	    AccountItemDTO dto = (AccountItemDTO)pageContext.getAttribute("oneline");
	    
	    pageContext.setAttribute("AccountItemDTO",dto);
	    
	    String acctName="";
	    String csiidName="";
	    acctName = Postern.getAcctNameById(dto.getAcctID());
	    csiidName = Postern.getCSITypeByID(dto.getReferID());
	    if(acctName==null)
	    	acctName="";
	    if(csiidName==null)
	    	csiidName="";
	    String status=null;
	    status = dto.getStatus();
	    	
	%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	    <td><tbl:write name="AccountItemDTO" property="aiNO"/></td>
	    <td><tbl:write name="AccountItemDTO" property="acctID"/></td>  
	    <td><%=acctName %></td>
	    <td><tbl:write name="AccountItemDTO" property="referID"/></td>
	    <td><d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="<%=csiidName %>" /></td>
	    <td><tbl:write name="AccountItemDTO" property="createTime"/></td>
	    <td><tbl:write name="AccountItemDTO" property="value"/></td>
	    <td><tbl:write name="AccountItemDTO" property="setOffAmount"/></td>
	    <td><d:getcmnname typeName="SET_F_FTSTATUS" match="AccountItemDTO:status" /></td>
	    
	    <td><% if ("V".equals(status)) {%>
	    <a href="javascript:depositReturn('<tbl:write name="AccountItemDTO" property="aiNO"/>')" class="link12" >退还押金</a><%}%></td>
	    
	    <td><% if ("V".equals(status)) {%>
	    <a href="javascript:depositConfiscate('<tbl:write name="AccountItemDTO" property="aiNO"/>')" class="link12" >没收押金</a><%}%></td>
	</tbl:printcsstr>
	</lgc:bloop>
	<tr>
    	<td colspan="11" class="list_foot"></td>
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