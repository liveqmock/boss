<!-- ?queryFlag=caeventcmdmap -->
<%@taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@taglib uri="logic" prefix="lgc"%>
<%@taglib uri="resultset" prefix="rs"%>
<%@taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@page import="com.dtv.oss.dto.VOIPEventCmdDTO"%>

<SCRIPT language="JavaScript">

function back_submit(){
	url="<bk:backurl property='voip_event_query.do'/>";
    document.location.href=url;
}
</SCRIPT>
 
<form name="frmPost" method="post" action="voip_eventcmdmap_query.do"><!-- 	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1"> --><!-- 	   </td>
	</tr>
    </table>   -->
 

	  
<INPUT type="hidden" name="txtFrom" value="1">
<INPUT type="hidden" name="txtTo" value="10">
<br>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">语音接口事件命令查询</td>
  </tr>
  </table>
 
 
<TABLE width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<TR>
		<TD><IMG src="img/mao.gif" width="1px" height="1px"></TD>
	</TR>
</TABLE>
<BR>

<rs:hasresultset>
<%
Map mapSystemEvent=Postern.getAllSystemEvent();
%>

	<TABLE width="100%" border="0" align="center" cellpadding="8" cellspacing="1" class="list_bg">
		<TR class="list_head">
			<TD class="list_head" nowrap>序列号</TD>
			<TD class="list_head" nowrap>接口名称</TD>
			<TD class="list_head" nowrap>命令名称</TD>
			<TD class="list_head" nowrap>事件ID</TD>
			<TD class="list_head" nowrap>事件名称</TD>
			<TD class="list_head" nowrap>创建时间</TD>
			<TD class="list_head" nowrap>完成时间</TD>
			<TD class="list_head" nowrap>状态</TD>
			
		</TR>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
			<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
				<%
				VOIPEventCmdDTO dto=(VOIPEventCmdDTO)pageContext.getAttribute("oneline");
				String eventName=Postern.getEventNameByEventID(dto.getEventID());
				if(eventName==null)eventName="";
				%>
				<TD align="center"><A href='voip_cmdprocess_query.do?qID=<tbl:write name="oneline" property="queueID"/>'><tbl:write name="oneline" property="queueID" /></A></TD>
				<TD align="center"><tbl:write name="oneline" property="ifName" /></TD>
				<TD align="center"><tbl:write name="oneline" property="cmdName" /></TD>
				<TD align="center"><tbl:write name="oneline" property="eventID" /></TD>
				<TD align="center"><%=eventName%></TD>
				<TD align="center"><tbl:write name="oneline" property="createTime" /></TD>
				<TD align="center"><tbl:write name="oneline" property="doneTime" /></TD>
				<TD align="center"><d:getcmnname typeName="SET_SSIF_QUEUESTATUS" match="oneline:status" /></TD>
				
			</tbl:printcsstr>
		</lgc:bloop>
		<TR>
			<TD colspan="8" class="list_foot"></TD>
		</TR>
	</TABLE>
	<TABLE  border="0" align="right" cellpadding="0" cellspacing="8">
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
			<TD><INPUT name="Submit" type="button" class="button" value="返&nbsp;回" onclick="javascript:back_submit()"></TD>
            <TD><IMG src="img/button2_l.gif" width="11px" height="20px"></TD>      
        </TR>
	</TABLE>
	   </td>
	</tr>
    </table> 
</form>
