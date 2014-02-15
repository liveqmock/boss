<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="osstags" prefix="d"%>

<script language=javascript>

</script>
<style type="text/css">
.link12
{
	text-justify: distribute;
	font-size: 18px;
}

</style>
<SCRIPT language="JavaScript">

 
function click2_submit()
{
	document.frmPost.action="voip_condition_list.do?txtQueryType=C";
	document.frmPost.submit();
}
function click3_submit()
{
	document.frmPost.action="voip_gateway_list.do?txtQueryType=G";
	document.frmPost.submit();
}
</SCRIPT>

<form name="frmPost" method="post" action=""> 
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >
 
<br>
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td align="center">
		<br>
<table width="100%" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td align="center">
		<table border="0" align="center" cellpadding="0" cellspacing="10">
			<tr>
				<td><img src="img/list_tit.gif" width="19" height="19"></td>
				<td class="title">语音接口管理功能</td>
			</tr>
		</table>
		<table width="98%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>
		 
		 
		<br>
		<table width="40%">
			 
			<tr><td>&nbsp;</td></tr>
			<tr class="list_bg1" onmouseover="this.className='list_over'"
				onmouseout="this.className='list_bg1' ">
				<td align="center" class="link12"><a class="link12" href="javascript:click2_submit()"><font size=4 >语音条件配置信息维护</font></a></td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr class="list_bg1" onmouseover="this.className='list_over'"
				onmouseout="this.className='list_bg1' ">
				<td align="center" class="link12"><a class="link12" href="javascript:click3_submit()"><font size=4 >网关型号配置信息维护</font></a></td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr class="list_bg1" onmouseover="this.className='list_over'"
				onmouseout="this.className='list_bg1' ">
				<td align="center" class="link12"><a class="link12" href="voip_event_query.screen"><font size=4 >语音接口事件命令查询</font></a></td>
			</tr>
			<tr><td>&nbsp;</td></tr>
		</table>
		<br>
		<br>
		 
		<table width="98%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>

		</td>
	</tr>
</table>

		</td>
	</tr>
</table>
</form>
