<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.VOIPCmdProDTO"%>

<SCRIPT language="JavaScript">

function back_submit(){
	url="<bk:backurl property='voip_eventcmdmap_query.do'/>";
    document.location.href=url;

}
</SCRIPT>
<!-- ?queryFlag=caeventcmdmap -->
<form name="frmPost" method="post" action="ca_eventcmdmap_query.do">
	<input	type="hidden" name="txtFrom" value="1"> 
	<input type="hidden" name="txtTo" value="10">
<br>	
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">命令处理历史记录查询</td>
  </tr>
  </table>
 
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

<rs:hasresultset>
<%
Map mapSystemEvent=Postern.getAllSystemEvent();
%>

	<table width="100%" border="0" align="center" cellpadding="10"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
			<td class="list_head" nowrap>序列号</td>
			<td class="list_head" nowrap>接口名称</td>
			<td class="list_head" nowrap>命令名称</td>
			<td class="list_head" nowrap>事件ID</td>
			<td class="list_head" nowrap>事件名称</td>
			<td class="list_head" nowrap>电话号码</td>
			<td class="list_head" nowrap>发送日期</td>
			<td class="list_head" nowrap>发送时间</td>
			<td class="list_head" nowrap>接收日期</td>
			<td class="list_head" nowrap>接收时间</td>
			
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				<%
				VOIPCmdProDTO dto=(VOIPCmdProDTO)pageContext.getAttribute("oneline");
				String eventName=Postern.getEventNameByEventID(dto.getEventID());
				if(eventName==null)eventName="";
				%>
				<td align="center"><a href="voip_cmdprocess_detail.do?sID=<tbl:write name="oneline" property="seqNo"/>"><tbl:write name="oneline" property="seqNo" /></a></td>
				<td align="center"><tbl:write name="oneline" property="ifName" /></td>
				<td align="center"><tbl:write name="oneline" property="cmdName" /></td>
				<td align="center"><tbl:write name="oneline" property="eventID" /></td>
				<td align="center"><%=eventName%></td>
				<td align="center"><tbl:write name="oneline" property="phoneNo" /></td>
				<td align="center"><tbl:write name="oneline" property="sendDate" /></td>
				<td align="center"><tbl:write name="oneline" property="sendTime" /></td>
				<td align="center"><tbl:write name="oneline" property="revDate" /></td>
				<td align="center"><tbl:write name="oneline" property="revTime" /></td>
			</tbl:printcsstr>
		</lgc:bloop>
		<tr>
			<td colspan="10" class="list_foot"></td>
		</tr>
	</table>
	<TABLE border="0" align="right" cellpadding="0" cellspacing="8">
		<TR>
			<TD>第<SPAN class="en_8pt"><rs:prop property="curpageno" /></SPAN>页<SPAN class="en_8pt">/</SPAN>共<SPAN class="en_8pt"><rs:prop property="pageamount" /><SPAN>页</SPAN></SPAN></TD>
			<TD>共<SPAN class="en_8pt"><rs:prop property="recordcount" /></SPAN>条记录</TD>
			<rs:notfirst>
				<TD align="right"><IMG src="img/dot_top.gif" width="17px" height="11px"></TD>
				<TD><A href='javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)' class="link12">首页</A></TD>
				<TD align="right"><IMG src="img/dot_pre.gif" width="6px" height="11px"></TD>
				<TD><A href='javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)' class="link12">上一页</A></TD>
			</rs:notfirst>
			<rs:notlast>
				<TD align="right"><IMG src="img/dot_next.gif" width="6px" height="11px"></TD>
				<TD><A href='javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)' class="link12">下一页</A></TD>
				<TD align="right"><IMG src="img/dot_end.gif" width="17px" height="11px"></TD>
				<TD><A href='javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)' class="link12">末页</A></TD>
			</rs:notlast>
			<TD align="right">跳转到</TD>
			<TD><INPUT name="txtPage" type="text" class="page_txt"></TD>
			<TD>页</TD>
			<TD><INPUT name="imageField" type="image" src="img/button_go.gif" width="28px" height="15px" border="0" onclick='javascript:return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)'></TD>
		</TR>
	</TABLE>
</rs:hasresultset>
<br>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="10" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td >
	<TABLE align="center" border="0"  cellpadding="0" cellspacing="0">
         <TR>  
            <TD><IMG src="img/button2_r.gif" width="22px" height="20px"></TD>
			<TD><INPUT name="Submit" type="button" class="button" value="返&nbsp;回" onClick="javascript:back_submit()"></TD>
            <TD><IMG src="img/button2_l.gif" width="11px" height="20px"></TD>      
        </TR>
	</TABLE>
	   </td>
	</tr>
    </table> 
</form>
