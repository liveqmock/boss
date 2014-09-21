<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk"%>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.wrap.CAEventWrap"%>
<%@ page import="com.dtv.oss.dto.CASentDTO"%>
<%@ page import="com.dtv.oss.dto.CARecvDTO"%>
<%@ page import="com.dtv.oss.dto.SystemEventDTO"%>
<%@ page import="com.dtv.oss.dto.CAQueueDTO"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<SCRIPT language="JavaScript">
function query_submit()
{
    document.frmPost.submit();
}

function recv_detail(quereid,eventID,transID)
{
	location.href = "ca_recv_query.do?queryFlag=recv&txtQuereID="+quereid+"&txtEventID="+eventID+"&txtTransID="+transID;
}
function sent_detail(quereid,eventID,transID)
{
	location.href = "ca_sent_query.do?queryFlag=sent&txtQuereID="+quereid+"&txtEventID="+eventID+"&txtTransID="+transID;
}
function back_submit(){
	url="<bk:backurl property='ca_event_query.do' />";
        document.location.href= url;

}
</SCRIPT>
<br>
<table border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td width="820">

<form name="frmPost" method="post" action="ca_event_query.do"><input
	type="hidden" name="txtFrom" size="20" value="1"> <input type="hidden"
	name="txtTo" size="20" value="10"> <INPUT TYPE="hidden"
	name="queryFlag" value="event">

<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">CA�ӿ��������ϸ��Ϣ</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<%Map mapSystemEvent = Postern.getAllSystemEvent();
			pageContext.setAttribute("events", mapSystemEvent);
			Map mapCAHost = Postern.getAllCAHost();
			pageContext.setAttribute("hosts", mapCAHost);
			String queueID=request.getParameter("OPEventID");
			%> 
<rs:hasresultset>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
	<%
	                CAEventWrap wrap=(CAEventWrap)pageContext.getAttribute("oneline");
				CAQueueDTO caEventDto=wrap.getQueueDTO();
			 
			 
			pageContext.setAttribute("caEvent",caEventDto);
			String eventName=(String)mapSystemEvent.get(caEventDto.getEventClass()+"");
			if(eventName==null)eventName="";
			String productName=Postern.getProductNameByID(caEventDto.getProductID());
			if(productName==null)productName="";
			String oldProductName=Postern.getProductNameByID(caEventDto.getOldProductId());
			if(oldProductName==null)oldProductName="";
			int commandID = caEventDto.getCommandID();
				String commandName = Postern.getCommandNameByID(commandID);
				if(commandName==null)
				commandName="";
			String hostName=(String)mapCAHost.get(caEventDto.getHostID()+"");
				if(hostName==null)hostName="";	
	%>
		<table width="100%" align="center" border="0" cellspacing="1"
			cellpadding="3">
			<tr>
				<td class="list_bg2" align="right" width="17%">��ˮ��</td>
				<td class="list_bg1" width="33%"><tbl:write name="caEvent" property="queueID" /></td>
				<td class="list_bg2" align="right" width="17%">�¼���</td>
				<td class="list_bg1" width="33%"><tbl:write name="caEvent" property="eventID" /></td>
			</tr>
			<tr>
				<td class="list_bg2" align="right">�ͻ�ID</td>
				<td class="list_bg1"><tbl:write name="caEvent" property="customerID" /></td>
				<td class="list_bg2" align="right" width="17%">�¼�����</td>
				<td class="list_bg1" width="33%"><%=eventName%></td>
			</tr>
			<tr>
				<td class="list_bg2" align="right">��Ʒ����</td>
				<td class="list_bg1"><%=productName%></td>
				<td class="list_bg2" align="right">CA����ID</td>
				<td class="list_bg1""><tbl:write name="caEvent" property="commandID" /></td>
				
			</tr>
			<tr>
				<td class="list_bg2" align="right">���ܿ����к�</td>
				<td class="list_bg1"><tbl:write name="caEvent" property="scnr" /></td>
				<td class="list_bg2" align="right">CA��������</td>
				<td class="list_bg1"><%=commandName%></td>
			</tr>
			<tr>
				<td class="list_bg2" align="right">���������к�</td>
				<td class="list_bg1""><tbl:write name="caEvent" property="stbnr"/></td>
				<td class="list_bg2" align="right">CA����ID</td>
				<td class="list_bg1"><tbl:write name="caEvent" property="condID"/></td>
			</tr>
			<tr>
				<td class="list_bg2" align="right">�����ܿ����к�</td>
				<td class="list_bg1"><tbl:write name="caEvent" property="oldScnr" /></td>
				<td class="list_bg2" align="right">CA��������</td>
				<td class="list_bg1"><%=hostName%></td>
			</tr>
			<tr>    
			        <td class="list_bg2" align="right">�ϻ��������к�</td>
				<td class="list_bg1"><tbl:write name="caEvent" property="oldStbnr" /></td>
				 <td class="list_bg2" align="right">CA��Ȩ��ʶ</td>
				<td class="list_bg1"><tbl:write name="caEvent" property="entitlement" /></td>
			</tr>	
			<tr>
				<td class="list_bg2" align="right">����Ӫ�̲�Ʒ����</td>
				<td class="list_bg1"><%=oldProductName%></td>
				<td class="list_bg2" align="right">OPI_ID</td>
				<td class="list_bg1"><tbl:write name="caEvent" property="opiID" /></td>
			</tr>
			
			<tr>
				<td class="list_bg2" align="right">�����״̬</td>
				<td class="list_bg1""><d:getcmnname typeName="SET_N_QUEUESTATUS" match="caEvent:status" /></td>
				<td class="list_bg2" align="right">�����ʱ��</td>
				<td class="list_bg1"><tbl:writedate name="caEvent" property="createTime" includeTime="true"/></td>
			</tr>
			<tr>
				<td class="list_bg2" align="right"></td>
				<td class="list_bg1""></td>
				<td class="list_bg2" align="right">�������ʱ��</td>
				<td class="list_bg1"><tbl:writedate name="caEvent" property="doneTime"  includeTime="true" /></td>
			</tr>
			 <tr> 
                <td colspan="4" class="import_tit" align="center"><font size="3">CA�ӿ��������Ϣ</font></td>
                </tr>
			
			
		 

		<table width="100%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">
			<tr class="list_head">
				<td class="list_head" width="10%">������ˮ��</td>
				<td class="list_head" width="10%">ͨ��ID</td>
				<td class="list_head" width="10%">�¼���</td>
				<td class="list_head" width="30%">����ʱ��</td>
				<td class="list_head" width="10%">״̬</td>
				<td class="list_head" width="30%">������</td>
			</tr>
			<%
			              
			                int queueId = WebUtil.StringToInt(queueID);
			               
			                ArrayList caSentDtoList=Postern.getCASentList(queueId);
			               
					ArrayList caRecvDtoList=Postern.getCARecvList(queueId);
					 
					if(caSentDtoList.size()>0)  { 
					  for(int i=0;i<caSentDtoList.size();i++){
					  CASentDTO caSentDto = (CASentDTO)caSentDtoList.get(i);
					  pageContext.setAttribute("caSentDto", caSentDto);
					
					%>
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				<td align="center"><a class="link12" 
					href="javascript:sent_detail('<tbl:write name="caSentDto" property="queueID"/>','<tbl:write name="caSentDto" property="eventID"/>','<tbl:write name="caSentDto" property="transID"/>')"><tbl:write name="caSentDto" property="queueID" /></a></td>
				<td align="center"><tbl:write name="caSentDto" property="transID" /></td>
				<td align="center"><tbl:write name="caSentDto" property="eventID" /></td>
				<td align="center"><tbl:writedate name="caSentDto" property="sentTime" includeTime="true"/></td>
				<td align="center"><d:getcmnname typeName="SET_N_QUEUESTATUS" match="caSentDto:status" /></td> 	
				 
			<!--	<td style="word-break:break-all"><tbl:write name="caSentDto" property="sentData" /></td>
				<td style="word-break:break-all"><tbl:write name="caSentDto" property="sendString" /></td>
			-->	
				
				<td style="word-break:break-all"><tbl:write name="caSentDto" property="errorCode" /></td>
			</tbl:printcsstr>
				<%}} %>
				 
				<tr>
				 <td colspan="6" class="import_tit" align="center"><font size="3">CA�ӿ�CASӦ����Ϣ</font></td>
			    </tr>
				 
				
		</table>

  
<table width="100%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">
			<tr class="list_head">
			
			         <td class="list_head" width="10%">������ˮ��</td>
				<td class="list_head" width="10%">ͨ��ID</td>
				<td class="list_head" width="10%">�¼���</td>
				<td class="list_head" width="30%">����ʱ��</td>
				<td class="list_head" width="40%">������</td>
			</tr>
			<%	
			if(caRecvDtoList.size()>0)  { 
			    
			     for(int i=0;i<caRecvDtoList.size();i++){
				 CARecvDTO caRecvDto = (CARecvDTO)caRecvDtoList.get(i);
				  
					pageContext.setAttribute("caRecvDto", caRecvDto);
					%>
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				<td align="center"><a class="link12" 
					href="javascript:recv_detail('<tbl:write name="caRecvDto" property="queueID"/>','<tbl:write name="caRecvDto" property="eventID"/>','<tbl:write name="caRecvDto" property="transID"/>')"><tbl:write name="caRecvDto" property="queueID" /></a></td>
				<td align="center"><tbl:write name="caRecvDto" property="transID" /></td>
				<td align="center"><tbl:write name="caRecvDto" property="eventID" /></td>
				<td align="center"><tbl:writedate name="caRecvDto" property="dtCreate" includeTime="true"/></td>
				<td style="word-break:break-all"><tbl:write name="caRecvDto" property="errorCode" /></td>

			</tbl:printcsstr>
			<%}} %>
			<tr>
				<td colspan="5" class="list_foot"></td>
			</tr>
		</table>
	</lgc:bloop>
</rs:hasresultset>
 <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
      <td class="list_bg1">
	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
        </tr>
      </table>	
	   </td>
	</tr>
 </table>    
</form>
	   </td>
	</tr>
 </table>    
