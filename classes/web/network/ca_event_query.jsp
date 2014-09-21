<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.dto.wrap.*,java.util.*"%>
 
<%@ page import="com.dtv.oss.dto.CAQueueDTO"%>
 

<SCRIPT language="JavaScript">
function checkdate(){
	if(!compareDate(document.frmPost.txtStartTime,document.frmPost.txtEndTime,"�������ڱ�����ڵ��ڿ�ʼ����")){
        	
        	return false;
        	
       } 
      if(!check_TenDate(document.frmPost.txtStartTime,true,"���ʼʱ��")){
		return false;
	}
	if(!check_TenDate(document.frmPost.txtEndTime,true,"�������ʱ��")){
		return false;
	}
        if (!check_Num(document.frmPost.txtCustmerID, true, "�ͻ�ID"))
            {
                
        	return false;	
            }
        if (!check_Num(document.frmPost.txtStartNo, true, "��ʼϵͳ�¼���"))
            {
                
        	return false;	
            }
             if (!check_Num(document.frmPost.txtEndNo, true, "����ϵͳ�¼���"))
            {
                
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

function event_detail(strID,strID1)
{
	location.href = "ca_event_detail.do?queryFlag=event&OPEventID="+strID+"&txtEventID="+strID1;
}
function back_submit(){
	url="ca_info_index.screen";
    document.location.href= url;
}
</SCRIPT>

<form name="frmPost" method="post" action="ca_event_query.do">
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >
<INPUT TYPE="hidden" name="queryFlag" value="event">
<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">��ѯCA�ӿ�������¼</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<%
Map mapSystemEvent=Postern.getAllSystemEvent();
pageContext.setAttribute("events",mapSystemEvent);
Map mapCAHost=Postern.getAllCAHost();
pageContext.setAttribute("hosts",mapCAHost);

%>

<table width="100%" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		
		<td class="list_bg2" align="right">���ܿ����к�</td>
		<td class="list_bg1"><input name="txtSCSerialNo"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:writeparam name="txtSCSerialNo" />"></td>
		<td class="list_bg2" align="right">������ʼʱ��</td>
		<td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartTime)" onblur="lostFocus(this)" name="txtStartTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writeparam name="txtStartTime" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>	
	</tr>
	<tr>
		<td class="list_bg2" align="right">���������к�</td>
		<td class="list_bg1"><input name="txtSTBSerialNo"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:writeparam name="txtSTBSerialNo" />"></td>
		<td class="list_bg2" align="right">�����ֹʱ��</td>
		<td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndTime)" onblur="lostFocus(this)" name="txtEndTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writeparam name="txtEndTime" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">�¼�����</td>
		<td class="list_bg1" width="33%"><font size="2"><tbl:select name="txtEventClass" set="events" width="23" match="txtEventClass"/></font></td>
		<td class="list_bg2" align="right" width="17%">��ʼϵͳ�¼���</td>
		<td class="list_bg1" width="33%"><input name="txtStartNo"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:writeparam name="txtStartNo" />"></td>
	</tr>
	<tr>    
	        <td class="list_bg2" align="right">�ͻ�ID</td>
		<td class="list_bg1"><input name="txtCustmerID"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:writeparam name="txtCustmerID" />"></td>
		<td class="list_bg2" align="right">����ϵͳ�¼���</td>
		<td class="list_bg1"><input name="txtEndNo"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:writeparam name="txtEndNo" />"></td>
		
	</tr>
	<tr>    
	       <td  class="list_bg2"><div align="right">״̬</div></td>
               <td class="list_bg1" colspan="3">
             <d:selcmn name="txtStatus" mapName="SET_N_QUEUESTATUS" match="txtStatus" width="23" /> 
            
         </td>      
	</tr>
	 
	 
</table>
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

<rs:hasresultset>
	<table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
		        <td class="list_head">��ˮ��</td> 
			<td class="list_head">��������</td>
			<td class="list_head">���ܿ����к�</td>
			<td class="list_head">��������</td>
			<td class="list_head">��Ʒ����</td>
			<td class="list_head">����״̬</td>
			<td class="list_head">����ʱ��</td>
			<td class="list_head">���ʱ��</td>
			 
			
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
			<%
				CAEventWrap wrap=(CAEventWrap)pageContext.getAttribute("oneline");
				CAQueueDTO caEventDto=wrap.getQueueDTO();
				int commandID = caEventDto.getCommandID();
				String commandName = Postern.getCommandNameByID(commandID);
				if(commandName==null)
				commandName="";
				int productID = caEventDto.getProductID();
				String productName = Postern.getProductNameByProductID(productID);
				if(productName==null)
				productName =String.valueOf(productID);
				 
				pageContext.setAttribute("caEvent",caEventDto);
				 
				String hostName=(String)mapCAHost.get(caEventDto.getHostID()+"");
				if(hostName==null)hostName="";
				 
			%>
			<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
				<td align="center"><a href="javascript:event_detail('<tbl:write name="caEvent" property="queueID"/>','<tbl:write name="caEvent" property="eventID"/>')" class="link12"><tbl:write name="caEvent" property="queueID"/></a></td>
				<td align="center"><%=hostName%></td>
				<td align="center"><tbl:write name="caEvent" property="scnr"/></td>
				<td align="center"><%=commandName%></td>
				<td align="center"><%=productName%></td>
				<td align="center"><d:getcmnname typeName="SET_N_QUEUESTATUS" match="caEvent:status" /></td> 
				 
				<td align="center"><tbl:writedate name="caEvent" property="createTime" includeTime="true"/></td>
				<td align="center"><tbl:writedate name="caEvent" property="doneTime" includeTime="true" /></td>
				 
				
			</tbl:printcsstr>
		</lgc:bloop>
		<tr>
        <td colspan="8" class="list_foot"></td>
       </tr>
 
            <tr>
              <td align="right" class="t12" colspan="8" >
                 ��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ
                 <span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" />ҳ
                 ����<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼&nbsp;&nbsp;&nbsp;&nbsp;             
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >��ҳ </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >��һҳ</a>
                 </rs:notfirst>
          
                <rs:notlast>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >��һҳ</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >ĩҳ</a>
                </rs:notlast>
                &nbsp;
                ת��
               <input type="text" name="txtPage" class="page_txt">ҳ 
               <a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
	</table>
</rs:hasresultset> <br>
</form>