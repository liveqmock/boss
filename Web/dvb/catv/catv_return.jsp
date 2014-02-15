<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>


<BR>
<%String sFlag = request.getParameter("txtAction");
String hiddenValues=request.getParameter("txtHiddenValues");
			String strErrBackUrl = "";
			String strErrButtonValue = "";
			String strBackUrl = "construction_batch_import.do";
			String strButtonValue = "返回房产信息录入";
			String strActionUrl = "construction_batch_import.do";


			if (sFlag != null) {
				if (sFlag.equalsIgnoreCase("construction_batch_import")) {
					strBackUrl = "construction_batch_import.do";
					strButtonValue = "返回房产信息录入";
					strErrBackUrl = "construction_batch_import_ipnut.do?txtActionType=query&txtFrom=1&txtTo=10";
					strErrButtonValue = "返回批量导入房产信息浏览";
				} else {
				}
			}
			System.out.println("sFlag=================" + sFlag);
			System.out.println("strErrBackUrl=========" + strErrBackUrl);
			System.out.println("strErrButtonValue=====" + strErrButtonValue);
			System.out.println("strBackUrl============" + strBackUrl);
			System.out.println("strButtonValue========" + strButtonValue);
			System.out.println("strActionUrl==========" + strActionUrl);

			%>
<script language="JavaScript" type="text/JavaScript">
<!--
function frmSubmit(){
	var url="";
	url="<bk:backurl property='<%=strBackUrl%>' />";
	if(url==null||url=="")
		url='<%=strBackUrl%>';
	if(url!="")
		document.frmPost.action = url;
	document.frmPost.submit();
}
function errorBack(){
	var url="";
	<%if(strErrBackUrl.endsWith("screen")){%>
		url="<%=strErrBackUrl%>";
	<%}else{%>
		url="<bk:backurl property='<%=strErrBackUrl%>' />";
	<%}%>
	if(url==null||url=="")
		url="<%=strErrBackUrl%>";
	if(url!="")
		document.frmPost.action = url;
        document.frmPost.submit();
}

//-->
</script>
<TABLE border="0" align="center" cellspacing="0" cellpadding="0"
	width="820">
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
		</lgc:haserror> 
		<lgc:hasnoterror>
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
		<form name="frmPost" method="post" action="<%=strActionUrl%>">
		<tbl:hiddenparameters pass="<%=hiddenValues%>" />
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="10">
			<tr align="center">
				<td height="37">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<!-- 有错要显示的按钮 -->
						<lgc:haserror>
							<%if(strErrButtonValue!=null&&!strErrButtonValue.equals("")){%>
							<td width="22" height="20"><img src="img/button2_r.gif" width="22"
								height="20"></td>
							<td><input name="Submit22" type="button" class="button"
								onClick="javascript:errorBack()"
								value="<%=strErrButtonValue%>"></td>
							<td width="11" height="20"><img src="img/button2_l.gif" width="11"
								height="20"></td>
							<%}%>
						</lgc:haserror>
						<!-- 正常显示的按钮 -->
						<lgc:hasnoterror>
							<td width="20">&nbsp;&nbsp;</td>
							<%if(strButtonValue!=null&&!strButtonValue.equals("")){%>
							<td width="11" height="20"><img src="img/button_l.gif" width="11"
								height="20"></td>
							<td><input name="Submit22" type="button" class="button"
								onClick="javascript:frmSubmit()"
								value="<%=strButtonValue%>"></td>
							<td width="22" height="20"><img src="img/button_r.gif" width="22"
								height="20"></td>
							<%}%>
						</lgc:hasnoterror>
					</tr>
				</table>
				</td>
				<td width="20%">&nbsp;</td>
			</tr>
		</table>
		</form>
</table>
