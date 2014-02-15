<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.CAEventCmdMapDTO"%>

<SCRIPT language="JavaScript">
function ca_eventcmdmap_create()
{
	location.href = "ca_eventcmdmap_create.screen";
}

function back_submit(){
	url="ca_info_index.screen";
    document.location.href= url;

}
</SCRIPT>
<!-- ?queryFlag=caeventcmdmap -->
<form name="frmPost" method="post" action="ca_eventcmdmap_query.do"><input
	type="hidden" name="txtFrom" value="1"> <input type="hidden"
	name="txtTo" value="10">

	<input type="hidden" name="queryFlag" size="20" value="caeventcmdmap">
<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">CA事件命令映射列表</td>
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
Map mapCACondition=Postern.getAllCaConditions();
Map mapCACommand=Postern.getAllCaCommands();
%>

	<table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
			<td class="list_head">对应ID</td>
			<td class="list_head">事件ID</td>
			<td class="list_head">条件ID</td>
			<td class="list_head">命令ID</td>
			<td class="list_head">优先级</td>
			<td class="list_head">状态</td>
			<td class="list_head">描述</td>
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				<%
				CAEventCmdMapDTO dto=(CAEventCmdMapDTO)pageContext.getAttribute("oneline");
				String eventName=(String)mapSystemEvent.get(dto.getMapEventID()+"");
				if(eventName==null)eventName="";
				String conditionName=(String)mapCACondition.get(dto.getMapConditionID()+"");
				if(conditionName==null)conditionName="";
				String commandName=(String)mapCACommand.get(dto.getMapCmdID()+"");
				if(commandName==null)commandName="";
				%>
				<td align="center"><a href="ca_eventcmdmap_detail.do?queryFlag=caeventcmdmap&OPMapID=<tbl:write name="oneline" property="mapID"/>"><tbl:writenumber name="oneline" property="mapID" digit="9"/></a></td>
				<td align="center"><tbl:write name="oneline" property="mapEventID" /></td>
				<td align="center"><tbl:write name="oneline" property="mapConditionID" /></td> 
				<td align="center"><tbl:write name="oneline" property="mapCmdID" /></td>  
				 
				<td align="center"><tbl:write name="oneline" property="priority" /></td>
				<td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS"
					match="oneline:status" /></td>
					<td align="center"><tbl:write name="oneline" property="description" /></td>
			</tbl:printcsstr>
		</lgc:bloop>
	<tr>
        <td colspan="7" class="list_foot"></td>
        </tr>
 
            <tr>
              <td align="right" class="t12" colspan="7" >
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
	<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
</rs:hasresultset>
<!-- 	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1"> -->

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="新&nbsp;增" onclick="javascript:ca_eventcmdmap_create()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>      
        </tr>
      </table>	
<!-- 	   </td>
	</tr>
    </table>   -->  
</form>
