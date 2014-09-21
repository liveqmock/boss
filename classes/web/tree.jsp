<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="operator" prefix="oper"%>

<%@ page import="com.dtv.oss.web.util.*"%>
<STYLE TYPE="text/css">
	A:link {
		  FONT-SIZE:9pt; COLOR:#FFFFFF; TEXT-DECORATION:None
	}
	A:visited {
		  FONT-SIZE:9pt; COLOR:#FFFF80; TEXT-DECORATION:None
	}
	A:active {
		  FONT-SIZE:9pt; COLOR:#0000FF; TEXT-DECORATION:None
	}
	A:hover {
		  FONT-SIZE:9pt; COLOR:#0099FF; TEXT-DECORATION:None
	}
</STYLE>

<form name="frmPost" method="post" action="treeAction.do"><!-- 专放背景图片的 -->
<table id="backtable" width="98%" border="0" cellspacing="0"
	cellpadding="0" style="background:url(img/left_bg.gif);background-repeat:repeat;height:expression(document.body.clientHeight);">
	<tr><!-- height="600"  style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='img/left_bg.gif', sizingMethod='scale')" 该样式会平铺背景,但会导致点击无效 -->
		<td id="backtd" valign="top" style="background:url(img/left_top.gif) no-repeat top left; "><!-- 专放背景图片的 -->
		<table width="98%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="2">
					<img src="img/mao.gif" width="5" height="5">
				</td>
			</tr>
			<tr><!--  -->
				<td align="left" valign="top" width="3px"><img src="img/mao.gif">&nbsp;&nbsp;
				</td><td align="left" valign="top">
				<table width="160" border="0" cellpadding="0" cellspacing="0"
					bgcolor="#F1F1ED" style="border:1px solid #999999; ">
					<lgc:notempty name="<%=WebKeys.OPERATOR_SESSION_NAME%>"
						scope="session">
						<tr>
							<td align="left">
							<table border="0" cellspacing="5" cellpadding="0">
								<tr>
								      
									<td><img src="img/ico_01.gif" width="13" height="12"></td>
									<td>操作员：</td>
									<td class="blue en_9pt"><oper:curoper property="OperatorName" /></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td background="img/line_01.gif"><img src="img/mao.gif" width="1"
								height="1"></td>
						</tr>
						<tr>
							<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="5">
								<tr>
									<td align="center" class="blue"><oper:curoper
										typeName="orgname" /></td>
								</tr>
							</table>
							</td>
						</tr>
					</lgc:notempty>
					<lgc:empty name="<%=WebKeys.OPERATOR_SESSION_NAME%>"
						scope="session">
						<tr>
							<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="5">
								<tr>
									<td align="center" class="blue">没有当前操作员</td>
								</tr>
							</table>
							</td>
						</tr>
					</lgc:empty>
				</table>

				</td>
			</tr>
			<tr>
				<td align="left" valign="top" colspan="2">
				<table class="tree">
					<tbl:TreeWrite />
				</table>
				</td>
			</tr>
		</table>
		<!-- 专放背景图片的 -->
		</td>
	</tr>
</table>

<!-- 专放背景图片的 --></form>
