<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>

<%@ page import="com.dtv.oss.dto.BBIProcessLogDTO"%>

<SCRIPT language="JavaScript">
function checkdate(){
	if(!compareDate(document.frmPost.txtStartTime,document.frmPost.txtEndTime,"结束日期必须大于等于开始日期")){ 		
		return false;
	} 
	if(!check_TenDate(document.frmPost.txtStartTime,true,"命令开始时间")){
		return false;
	}
	if(!check_TenDate(document.frmPost.txtEndTime,true,"命令结束时间")){
		return false;
	}
	if (!check_Num(document.frmPost.txtCustmerID, true, "客户ID")){  
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
	url="band_info_index.screen";
    document.location.href= url;
}
</SCRIPT>

<form name="frmPost" method="post" action="band_log_query.do">
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >
<INPUT TYPE="hidden" name="queryFlag" value="log">
<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">查询宽带接口命令处理记录</td>
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
Map mapAllBandCommand = Postern.getAllBandCommand();
pageContext.setAttribute("AllBandCommand",mapAllBandCommand);
Map mapProcessResult = new HashMap();
mapProcessResult.put("D","成功");
mapProcessResult.put("F","失败");
mapProcessResult.put("W","等待");
pageContext.setAttribute("AllProcessResult",mapProcessResult);

%>

<table width="100%" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		
		<td class="list_bg2" align="right">命令</td>
		<td class="list_bg1"><font size="2"><tbl:select name="txtCommandName" set="AllBandCommand" width="23" match="txtCommandName"/></font></td>
		<td class="list_bg2" align="right">命令起始时间</td>
		<td class="list_bg1">
			<input name="txtStartTime" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtStartTime" />">
			<IMG onclick="calendar(document.frmPost.txtStartTime)" src="img/calendar.gif" style=cursor:hand border="0">
		</td>     
	</tr>
	<tr>
		<td class="list_bg2" align="right">命令状态</td>
		<td class="list_bg1"><font size="2"><tbl:select name="txtProcessResult" set="AllProcessResult" width="23" match="txtProcessResult"/></font></td>
		<td class="list_bg2" align="right">命令截止时间</td>
		<td class="list_bg1">
			<input name="txtEndTime" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtEndTime" />">
			<IMG onclick="calendar(document.frmPost.txtEndTime)" src="img/calendar.gif" style=cursor:hand border="0">
		</td>
	</tr>
	<tr>    
		<td class="list_bg2" align="right">客户ID</td>
		<td class="list_bg1"><input name="txtCustmerID" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCustmerID" />"></td>
		<td class="list_bg2" align="right">宽带用户名</td>
		<td class="list_bg1"><input name="txtBandName" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtBandName" />"></td>
	</tr>
	<tr>    
		<td  class="list_bg2"><div align="right">客户姓名</div></td>
		<td class="list_bg1" colspan="3">
             <input name="txtCustmerName" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCustmerName" />"></td>
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
	<table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
		        <td class="list_head">流水号</td> 
			<td class="list_head">事件</td>
			<td class="list_head">命令</td>
			<td class="list_head">客户ID</td>
			<td class="list_head">客户姓名</td>
			<td class="list_head">业务帐户ID</td>
			<td class="list_head">处理结果</td>
			<td class="list_head">处理描述</td>
			<td class="list_head">开始时间</td>
			<td class="list_head">完成时间</td>
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
			<%
				BBIProcessLogDTO dto=(BBIProcessLogDTO)pageContext.getAttribute("oneline");
				int eventID = dto.getEventID();
				int saID = dto.getServiceAccountID();
				String customerID = Postern.getCustomerIDByAcctServiceID(saID);
				if(customerID == null) customerID = "";
				String customerName = "";
				if(customerID!=null && !"".equals(customerID))
					customerName = Postern.getCustomerNameByID(Integer.parseInt(customerID));
				if(customerName == null) customerName="";
				int eventClass = Postern.getEventClassByEventID(eventID);
				if(eventClass<0) eventClass=0;
			%>
			<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
				<td align="center"><tbl:write name="oneline" property="seqNo"/></td>
				<td align="center"><%=eventClass%></td>
				<td align="center"><tbl:write name="oneline" property="commandName"/></td>
				<td align="center"><%=customerID%></td>
				<td align="center"><%=customerName%></td>
				<td align="center"><tbl:write name="oneline" property="serviceAccountID"/></td>
				<td align="center"><d:getcmnname typeName="AllProcessResult" match="oneline:status" /></td>
				<td align="center"><tbl:write name="oneline" property="description"/></td>
				<td align="center"><tbl:writedate name="oneline" property="dtCreate" includeTime="true"/></td>
				<td align="center"><tbl:writedate name="oneline" property="dtLastmod" includeTime="true" /></td>
				
			</tbl:printcsstr>
		</lgc:bloop>
		<tr>
        <td colspan="12" class="list_foot"></td>
       </tr>
 
            <tr>
              <td align="right" class="t12" colspan="12" >
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