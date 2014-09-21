<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk"%>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.CASentDTO"%>

<SCRIPT language="JavaScript">
function back_submit(){
	url="<bk:backurl property='ca_event_detail.do' />";
    document.location.href= url;

}
</SCRIPT>

<form name="frmPost" method="post" action="ca_event_query.do"><input
	type="hidden" name="txtFrom" size="20" value="1"> <input type="hidden"
	name="txtTo" size="20" value="10"> <INPUT TYPE="hidden"
	name="queryFlag" value="sent">
<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">CAS应答详细信息</td>
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
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
		<table width="100%" align="center" border="0" cellspacing="1"
			cellpadding="3">
			<tr>
				<td class="list_bg2" align="right" width="17%">命令流水号</td>
				<td class="list_bg1" width="33%"><tbl:write name="oneline" property="queueID" /></td>
				<td class="list_bg2" align="right">错误码</td>
				<td class="list_bg1" style="word-break:break-all"><tbl:write name="oneline" property="errorCode" /></td> 
			</tr>
			<tr>
				<td class="list_bg2" align="right" width="17%">事件号</td>
				<td class="list_bg1" width="33%"><tbl:write name="oneline" property="eventID" /></td>
				<td class="list_bg2" align="right" width="17%">通信ID</td>
				<td class="list_bg1" width="33%"><tbl:write name="oneline" property="transID" /></td>
			</tr>
			<tr>
				<td class="list_bg2" align="right">记录创建时间戳</td>
				<td class="list_bg1"><tbl:writedate name="oneline" property="dtCreate" includeTime="true"/></td>
				<td class="list_bg2" align="right">记录更新时间戳</td>
				<td class="list_bg1"><tbl:writedate name="oneline" property="dtLastmod" includeTime="true"/></td>
			</tr>
			 
			 
			<tr>
				<td class="list_bg2" align="right" style="word-break:break-all">接收的二进制数据</td>
				<td class="list_bg1" colspan="3" style="word-break:break-all"><tbl:write name="oneline" property="recvData" /></td>
			</tr>
			<tr>
				<td class="list_bg2" align="right" style="word-break:break-all">接收的文本数据</td>
				<td class="list_bg1" colspan="3" style="word-break:break-all"><tbl:write name="oneline" property="recvString" /></td>
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
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
        </tr>
      </table>	
	   </td>
	</tr>
 </table>    
</form>

