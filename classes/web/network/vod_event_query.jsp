<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.dto.wrap.*,java.util.*"%>
 
<%@ page import="com.dtv.oss.dto.VODQueueDTO"%>
 

<SCRIPT language="JavaScript">
function checkdate(){
	if(!compareDate(document.frmPost.txtStartTime,document.frmPost.txtEndTime,"命令结束时间必须大于等于命令开始时间")){
        	
        	return false;
       } 
       if(!check_TenDate(document.frmPost.txtStartTime,true,"命令开始时间")){
		return false;
	}
	if(!check_TenDate(document.frmPost.txtEndTime,true,"命令结束时间")){
		return false;
	}
        if (!check_Num(document.frmPost.txtCustmerID, true, "客户ID"))
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

 
function back_submit(){
	url="vod_interface_index.screen";
    document.location.href= url;
}
</SCRIPT>

<form name="frmPost" method="post" action="vod_event_query.do">
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >
 
<br>
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr>
		 
		<td>
		<table  border="0" align="center" cellpadding="0" cellspacing="10">
  				<tr>
    				<td><img src="img/list_tit.gif" width="19" height="19"></td>
    				<td  class="title">查询VOD接口命令处理记录</td>
  				</tr>
  			</table>
			<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  				<tr>
    				<td><img src="img/mao.gif" width="1" height="1"></td>
  				</tr>
			</table>
 
<br>
<%
Map mapSystemEvent=Postern.getAllSystemEvent();
pageContext.setAttribute("events",mapSystemEvent);
Map allVODHost = Postern.getAllVODHostNameID();
pageContext.setAttribute("AllVODHost",allVODHost);

%>

<table width="98%" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">事件类型</td>
		<td class="list_bg1" width="33%"><font size="2"><tbl:select name="txtEventClass" set="events" width="23" match="txtEventClass"/></font></td>
		 <td class="list_bg2" align="right" >VOD主机名称</td>
		<td class="list_bg1" align="left" ><tbl:select name="txtHostID" set="AllVODHost" match="txtHostID" width="23" /></td>
	</tr>
	<tr>    
	        <td class="list_bg2" align="right">客户ID</td>
		<td class="list_bg1"><input name="txtCustmerID"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:writeparam name="txtCustmerID" />"></td>
		<td class="list_bg2" align="right">设备序列号</td>
		<td class="list_bg1"><input name="txtSerialNo"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:writeparam name="txtSerialNo" />"></td>
		
	</tr>
	 
	<tr>
		<td class="list_bg2" align="right">命令开始时间</td>
		<td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartTime)" onblur="lostFocus(this)" name="txtStartTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writeparam name="txtStartTime" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>	
		<td class="list_bg2" align="right">命令结束时间</td>
		<td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndTime)" onblur="lostFocus(this)" name="txtEndTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writeparam name="txtEndTime" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
	</tr>
	 <tr>  
          <td class="list_bg2"><div align="right">处理状态</div></td>
           <td class="list_bg1" align="left" colspan="3">
             <d:selcmn name="txtStatus" mapName="SET_N_QUEUESTATUS" match="txtStatus" width="23" />
     	  </td> 
        
    </tr>
	
	
	 
</table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="查&nbsp;询" onclick="javascript:query_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

<rs:hasresultset>
	<table width="98%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
		        <td class="list_head" width="8%">流水号</td> 
		        <td class="list_head" width="8%">事件号</td>
		        <td class="list_head" width="15%">事件类型</td>
		        <td class="list_head" width="8%">客户ID</td>
			<td class="list_head" width="15%">主机名称</td>
			<td class="list_head" width="8%">处理状态</td>
			<td class="list_head" width="12%">创建时间</td>
			<td class="list_head" width="12%">完成时间</td>
			<td class="list_head" width="20%">命令描述</td> 
			
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
			<%
				VODQueueDTO dto=(VODQueueDTO)pageContext.getAttribute("oneline");
				int eventID = dto.getEventID();
				String eventName="";
				int custID=0;
				if(eventID>0){
				  eventName = Postern.getEventNameByEventID(eventID);
				if(eventName==null)
				  eventName="";
				  custID=Postern.getCustIDByEventID(eventID);
				} 
				String hostName=(String)allVODHost.get(dto.getHostID()+"");
				if(hostName==null)hostName="";
				 
			%>
			<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
				<td align="center"><tbl:writenumber name="oneline" digit="9" property="queueID"/></td>
				<td align="center"><tbl:write name="oneline" property="eventID"/></td>
				<td align="center"><%=eventName%></td>
				<td align="center"><%=custID%></td>
				<td align="center"><%=hostName%></td>
				<td align="center"><d:getcmnname typeName="SET_N_QUEUESTATUS" match="oneline:status" /></td> 
				<td align="center"><tbl:writedate name="oneline" property="createTime" includeTime="true"/></td>
				<td align="center"><tbl:writedate name="oneline" property="doneTime" includeTime="true" /></td>
				<td align="center"><tbl:write name="oneline" property="transactionSentList"/></td>
				 
				
			</tbl:printcsstr>
		</lgc:bloop>
		<tr>
        <td colspan="9" class="list_foot"></td>
       </tr>
 
            <tr>
              <td align="right" class="t12" colspan="9" >
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
</rs:hasresultset> <br>
</form>