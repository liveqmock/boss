<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>

<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>

<BR>
<BR>
<%
			String sFlag = request.getParameter("Action");
			System.out.println("�����������ķ���ҳ��,�����Ĳ�����־=====" + sFlag);
			int iFlag = 0;

			if (sFlag != null) {
				if (sFlag.equalsIgnoreCase("add"))
					iFlag = 101;
				else if (sFlag.equalsIgnoreCase("update"))
					iFlag = 102;
				else
					iFlag = 0;
			}
			System.out.println("iFlag=====" + iFlag);

			%>
<script language="JavaScript" type="text/JavaScript">
	function frmSubmit(url){
		if(url!="")
			document.frmPost.action = url;
			document.frmPost.submit();
	}
</script>

<TABLE border="0" align="center" cellspacing="0" cellpadding="0"
	width="100%">
	<TR>
		<TD align="center">

		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="10">
			<tr>
				<td class="title" align="center" valign="middle"><img
					src="img/list_tit.gif" width="19" height="19">&nbsp;&nbsp;��ʾ��Ϣ</td>
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
					<td class="ok"><font color="red"><i>�������ɹ���������Ϣ����:</i></font>
					<br>
					<tbl:errmsg /></td>
				</tr>
			</table>
		</lgc:haserror> <lgc:hasnoterror>
			<table width="50%" border="0" cellspacing="10" cellpadding="0">
				<tr align="center">
					<td width="182"><img src="img/icon_ok.gif" width="182" height="182"></td>
					<td class="ok">�����ɹ���</td>
				</tr>
			</table>

			<!-- �����ɹ�,ˢһˢ��,ˢ����,ͷһ�����,�ڶ��鿪 <bk:backurl property='pallet_query.do'/>-->
			<script language="JavaScript" type="text/JavaScript">
				parent.leftFrame.location.href="treeAction.do?key=org_<tbl:writeparam name="txtQryBelongTo"/>";
				parent.leftFrame.location.href="treeAction.do?key=org_<tbl:writeparam name="txtQryBelongTo"/>";
			</script>

		</lgc:hasnoterror> <br>
		<br>
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>
		<form name="frmPost" method="post" action="">
			<tbl:hiddenparameters pass="txtQryBelongTo/txtOrgType/txtOrgName/txtOrgSubType/txtRank/txtRegisterNo/txtHasCustomerFlag/txtStatus" />

		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="10">
			<tr align="center">
				<td height="37">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<%//-------�д�Ҫ��ʾ�İ�ť-------

			%>
						<lgc:haserror>
							<%switch (iFlag) {
				case 101:

					%>
							<td width="11" height="20"><img src="img/button_l.gif" width="11"
								height="20"></td>
							<td><input name="Submit22" type="button" class="button"
								onClick="javascript:frmSubmit('organization_create.screen')"
								value="�����½�ҳ��"></td>
							<td width="22" height="20"><img src="img/button_r.gif" width="22"
								height="20"></td>
							<%break;
				case 102:

					%>
							<td width="11" height="20"><img src="img/button_l.gif" width="11"
								height="20"></td>
							<td><input name="Submit22" type="button" class="button"
								onClick="javascript:frmSubmit('<bk:backurl property='organization_query_detail.do' />')"
								value="�����޸�ҳ��"></td>
							<td width="22" height="20"><img src="img/button_r.gif" width="22"
								height="20"></td>

							<%break;
				}

			%>
						</lgc:haserror>
						<td width="30"></td>
						<td width="11" height="20"><img src="img/button_l.gif" width="11"
							height="20"></td>
						<td><input name="Submit22" type="button" class="button"
							onClick="javascript:frmSubmit('<bk:backurl property='organization_query_result.do' />')"
							value="����"></td>
						<td width="22" height="20"><img src="img/button_r.gif" width="22"
							height="20"></td>
							<!-- ������,������ -->
					</tr>
				</table>
				</td>
				<td width="20%">&nbsp;</td>
			</tr>
		</table>
		</form>
</table>
