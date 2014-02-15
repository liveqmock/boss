<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<script language=javascript>

function back_submit()
{
	document.frmPost.action='<bk:backurl property="devicestatus_choice_info.do" />';
	document.frmPost.submit();
}

function next_submit()
{
	document.frmPost.action="devicestatus_op.do";
	document.frmPost.submit();
}

</script>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">设备状态——修改信息确认</td>
	</tr>
</table>
<table width="98%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<form name="frmPost" method="post" action="devicestatus_op.do">
<tbl:hiddenparameters
	pass="txtFromID/txtFromDeviceStatus/txtToDeviceStatus/DeviceList" />  <tbl:hiddenparameters
	pass="txtFrom/txtTo/txtStatus/txtDepotID/txtDeviceClass/txtDeviceModel" />

<table width="98%" border="0" align="center" cellpadding="5"
	cellspacing="1" class="list_bg">
	<tr>
		<td class="list_bg2">
		<div align="right">修改单编号</div>
		</td>
		<td class="list_bg1"><input type="text" name="txtOperationNo" size="25"
			value="<tbl:writeparam name="txtOperationNo" />" class="textgray"
			readonly></td>
		<td class="list_bg2">
		<div align="right">描述</div>
		</td>
		<td class="list_bg1"><input type="text" name="txtDescription"
			size="25" value="<tbl:writeparam name="txtDescription" />"
			class="textgray" readonly></td>
	</tr>
	<%Map mapDepots = Postern.getAllDepot();

			%>
	<tr>
		<td class="list_bg2">
		<div align="right">设备原状态</div>
		</td>
		<td class="list_bg1"><input type="text" name="txtFromDeviceStatusShow"
			size="25"
			value="<d:getcmnname typeName="SET_D_DEVICESTATUS" match="txtFromDeviceStatus"/>"
			class="textgray" readonly></td>
		<td class="list_bg2">
		<div align="right">设备目标状态</div>
		</td>
		<td class="list_bg1" colspan="3"><input type="text"
			name="txtToDeviceStatusShow" size="25"
			value="<d:getcmnname typeName="SET_D_DEVICESTATUS" match="txtToDeviceStatus"/>"
			class="textgray" readonly></td>
	</tr>
</table>




<table width="98%" border="0" align="center" cellpadding="5"
	cellspacing="1" class="list_bg">
	<tr class="list_head">
		<td colspan="3" align="center" class="import_tit"><font size="3">已选中的设备</font></td>
	</tr>
	<tr class="list_head">
		<td align="center" class="list_head"><font size="2">ID</font></td>
		<td align="center" class="list_head"><font size="2">序列号</font></td>
		<td align="center" class="list_head"><font size="2">状态</font></td>
	</tr>
	<%String[] aDeviceID = request.getParameterValues("DeviceList");

			if ((aDeviceID != null) && (aDeviceID.length > 0)) {
				for (int i = 0; i < aDeviceID.length; i++) {
					TerminalDeviceDTO dto = Postern
							.getTerminalDeviceByID(WebUtil
									.StringToInt(aDeviceID[i]));
					pageContext.setAttribute("oneline", dto);

					%>
	<tr class="list_bg1" onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg1'">
		<td align="center" ><font size="2"><%=aDeviceID[i]%></font></td>
		<td align="center" ><font size="2"><tbl:write
			name="oneline" property="SerialNo" /></font></td>
		<td align="center" ><font size="2"><d:getcmnname
			typeName="SET_D_DEVICESTATUS" match="oneline:Status" /></font></td>
	</tr>
	<%}
			}

		%>
				<tr>
					<td colspan="3" class="list_foot"></td>
				</tr>		
</table>
		<table width="98%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">
			<tr align="center">
				<td class="list_bg1">
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><img src="img/button2_r.gif" border="0"></td>
		<td background="img/button_bg.gif"><a href="javascript:back_submit()"
			class="btn12">上一步</a></td>
		<td><img src="img/button2_l.gif" border="0"></td>
		<td width="20"></td>
		<td><img src="img/button_l.gif" border="0"></td>
		<td background="img/button_bg.gif"><a href="javascript:next_submit()"
			class="btn12">确&nbsp;认</a></td>
		<td><img src="img/button_r.gif" border="0"></td>
	</tr>
</table>
				</td>
			</tr>
		</table>
<tbl:generatetoken /> <input type="hidden" name="func_flag" value="109">
<!-- <input type="hidden" name="txtFromType" value="D">为什么要固定一个值-->
</form>
