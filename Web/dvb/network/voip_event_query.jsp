<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.wrap.VOIPEventWrap"%>
<%@ page import="com.dtv.oss.dto.SystemEventDTO"%>

<SCRIPT language="JavaScript">
function checkdate(){
	if(!compareDate(document.frmPost.txtStartTime,document.frmPost.txtEndTime,"�������ڱ�����ڵ��ڿ�ʼ����")){
        	
        	return false;
       } 
   
       return true;
}

function query_submit()
{
   if(checkdate()){
   		document.frmPost.submit();
   }
    
}

function event_detail()
{
	location.href = "voip_event_detail.do";
}
function back_submit(){
	url="voip_info_index.screen";
    document.location.href=url;
}
</SCRIPT>

<form name="frmPost" method="post" action="voip_event_query.do">
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >
<INPUT TYPE="hidden" name="queryFlag" value="event">
<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19px" height="19px"></td>
		<td class="title">�����ӿ��¼���ѯ</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<br>
<%
Map mapSystemEvent=Postern.getAllVOIPEvent();
pageContext.setAttribute("events",mapSystemEvent);
%>

 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr>
		<td class="list_bg2" align="right" width="17%">�¼�����</td>
		<td class="list_bg1" width="33%"><font size="2"><tbl:select name="txtEventClass" set="events" width="23" match="txtEventClass"/></font></td>
		<td class="list_bg2" align="right" width="17%">�¼�����ʼ</td>
		<td class="list_bg1" width="33%"><input name="txtStartNo"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtStartNo" />"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">�¼��Ž�ֹ</td>
		<td class="list_bg1"><input name="txtEndNo"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtEndNo" />"></td>
		<td class="list_bg2" align="right">����ʱ����ʼ</td>
		<td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartTime)" onblur="lostFocus(this)" name="txtStartTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writeparam name="txtStartTime" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartTime,'Y')" src="img/calendar.gif" style="CURSOR: hand" border="0"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">����ʱ���ֹ</td>
		<td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndTime)" onblur="lostFocus(this)" name="txtEndTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writeparam name="txtEndTime" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndTime,'Y')" src="img/calendar.gif" style="CURSOR: hand" border="0"></td>
		<td class="list_bg2" align="right">�ͻ�֤��</td>
		<td class="list_bg1"><input name="txtCustmerID"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtCustmerID" />"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">ҵ���ʻ�ID</td>
		<td class="list_bg1"><input name="txtServiceAccountID"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtServiceAccountID" />"></td>
		<td class="list_bg2" align="right">������</td>
		<td class="list_bg1"><input name="txtCsiID"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtCsiID" />"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">�������</td>
		<td class="list_bg1"><input name="txtServiceCode"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtServiceCode" />"></td>
		<td class="list_bg1" align="right"></td>
		<td class="list_bg1"></td>
	</tr>
 </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22px" height="20px"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11px" height="20px"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11px" height="20px"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
            <td><img src="img/button_r.gif" width="22px" height="20px"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

<rs:hasresultset>
	<table width="98%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
			<td class="list_head" ><P align="center">�¼�ID</P></td>
			<td class="list_head" ><P align="center">�¼�����</P></td>
			<td class="list_head" ><P align="center">������</P></td>
			<td class="list_head" ><P align="center">�ͻ�֤��</P></td>
			<td class="list_head" ><P align="center">ҵ���ʻ�</P></td>
			<td class="list_head" ><P align="center">��Ʒ</P></td>
			<td class="list_head" ><P align="center">ִ��ʱ��</P></td>
			<td class="list_head" ><P align="center">�����ѯ</P></td>
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
			<%
				VOIPEventWrap wrap=(VOIPEventWrap)pageContext.getAttribute("oneline");
				SystemEventDTO sysEventDto = wrap.getSysEventDTO();
				pageContext.setAttribute("sysEvent",sysEventDto);
				String eventName=(String)mapSystemEvent.get(sysEventDto.getEventClass()+"");
				if(eventName==null)eventName="";
				
				String proName=Postern.getProductNameByProductID(sysEventDto.getProductID());
				String eventtime=Postern.getSysEventTimeByEventID(sysEventDto.getSequenceNo());
				if(eventtime==null||eventtime.equals("null"))eventtime="";
				
			%>
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				<td align="center"><tbl:write name="sysEvent" property="sequenceNo" /></td>
				<td align="center"><%=eventName%></td>
				<td align="center"><tbl:write name="sysEvent" property="csiID"/></td>
				<td align="center"><tbl:write name="sysEvent" property="customerID"/></td>
				<td align="center"><tbl:write name="sysEvent" property="serviceAccountID"/></td>
				<td align="center"><%=proName%></td>
				<td align="center"><%=eventtime%></td>
				<td align="center"><a href="voip_eventcmdmap_query.do?sID=<tbl:write name="sysEvent" property="sequenceNo"/>">����鿴</a></td>
			</tbl:printcsstr>
		</lgc:bloop>
		<tr>
			<td colspan="8" class="list_foot"></td>
		</tr>
	</table>
	<table border="0" align="right" cellpadding="0" cellspacing="8">
		<tr>
			<td>��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ<span
				class="en_8pt">/</span>��<span class="en_8pt"><rs:prop
				property="pageamount" /><span>ҳ</td>
			<td>��<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼</td>
			<rs:notfirst>
				<td align="right"><img src="img/dot_top.gif" width="17px" height="11px"></td>
				<td><a
					href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)"
					class="link12">��ҳ</a></td>
				<td align="right"><img src="img/dot_pre.gif" width="6px" height="11px"></td>
				<td><a
					href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)"
					class="link12">��һҳ</a></td>
			</rs:notfirst>
			<rs:notlast>
				<td align="right"><img src="img/dot_next.gif" width="6px" height="11px"></td>
				<td><a
					href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)"
					class="link12">��һҳ</a></td>
				<td align="right"><img src="img/dot_end.gif" width="17px" height="11px"></td>
				<td><a
					href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)"
					class="link12">ĩҳ</a></td>
			</rs:notlast>
			<td align="right">��ת��</td>
			<td><input name="txtPage" type="text" class="page_txt"></td>
			<td>ҳ</td>
			<td><input name="imageField" type="image" src="img/button_go.gif"
				width="28px" height="15px" border="0"
				onclick="javascript:return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
		</tr>
	</table>
</rs:hasresultset> <br>
</form>