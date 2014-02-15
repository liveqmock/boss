<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>

<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>

<BR>
<%String sFlag = request.getParameter("txtActionType");
			System.out.println("sFlag=====" + sFlag);
			int iFlag = 0;

			if (sFlag != null) {
				if (sFlag.equalsIgnoreCase("CREATE"))
					iFlag = 101;
				else if (sFlag.equalsIgnoreCase("MODIFY"))
					iFlag = 102;
				else
					iFlag = 0;
			}
			System.out.println("iFlag=====" + iFlag);

			%>
<script language="JavaScript" type="text/JavaScript">
<!--
function frmSubmit(url){
	if(url!="")
		document.frmPost.action = url;
        document.frmPost.submit();
}

//-->
</script>
<TABLE border="0" align="center" cellspacing="0" cellpadding="0"
	width="100%">
	<TR>
		<TD align="center">
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="10">
			<tr>
				<td class="title" align="center" valign="middle"><img
					src="img/list_tit.gif" width="19" height="19">&nbsp;&nbsp;提示信息</td>
			</tr>
		</table>
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>
		<br>
		<lgc:haserror>
			<table width="50%" border="0" cellspacing="10" cellpadding="0">
				<tr align="center">
					<td width="182"><img src="img/icon_error.gif" width="182"
						height="182"></td>
					<td class="ok"><font color="red"><i>操作不成功，错误信息如下:</i></font>
					<br>
					<tbl:errmsg /></td>
				</tr>
			</table>
		</lgc:haserror> <lgc:hasnoterror>
			<table width="50%" border="0" cellspacing="10" cellpadding="0">
				<tr align="center">
					<td width="182"><img src="img/icon_ok.gif" width="182" height="182"></td>
					<td class="ok">操作成功。</td>
				</tr>
			</table>
		</lgc:hasnoterror> <br>
		<br>
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>
		<form name="frmPost" method="post" action="depot_query.screen">
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="10">
			<tr align="center">
				<td height="37">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<%//-------有错要显示的按钮-------

			%>
						<lgc:haserror>
							<%switch (iFlag) {
				case 101: //仓库创建

					%>
							<td width="11" height="20"><img src="img/button_l.gif" width="11"
								height="20"></td>
							<td><input name="Submit22" type="button" class="button"
								onClick="javascript:frmSubmit('<bk:backurl property='depot_create.do' />')"
								value="返回"></td>
							<td width="22" height="20"><img src="img/button_r.gif" width="22"
								height="20"></td>
							<%break;
				case 102: //仓库修改

					%>
							<td width="11" height="20"><img src="img/button_l.gif" width="11"
								height="20"></td>
							<td><input name="Submit22" type="button" class="button"
								onClick="javascript:frmSubmit('<bk:backurl property='depot_modify.do' />')"
								value="返回"></td>
							<td width="22" height="20"><img src="img/button_r.gif" width="22"
								height="20"></td>

							<%break;
				}

			%>
						</lgc:haserror>
						<td width="11" height="20"><img src="img/button_l.gif" width="11"
							height="20"></td>
						<td><input name="Submit22" type="button" class="button"
							onClick="javascript:frmSubmit('<bk:backurl property='depot_query.do' />')"
							value="返回到仓库查询"></td>
						<td width="22" height="20"><img src="img/button_r.gif" width="22"
							height="20"></td>
					</tr>
				</table>
				</td>
				<td width="20%">&nbsp;</td>
			</tr>
		</table>
		</form>
</table>
