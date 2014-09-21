<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ page
	import="com.dtv.oss.util.Postern,com.dtv.oss.dto.BossConfigurationDTO,java.util.*"%>


		<%BossConfigurationDTO dto = Postern.getBossConfigDto();

			pageContext.setAttribute("oneline", dto);
			String userCount=Postern.getCurrentUserCount();
			if(userCount==null)userCount="";
		%>

<!-- \\\\\\\\\\\\\\CMS V5.0（系统版本），Build 20060630（软件Build信息）
版权所有，上海全景数字技术公司 2002～2008  -->
<br>
<br>
<br>

<br>
<br>
<table width="50%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
	<br><!--  -->
<table  border="0" cellspacing="10" cellpadding="0" >
	<tr align="left">
	  <td width="182"><img src="img/logo.gif" ></td>
	  <td ><font size="3" ><B><tbl:write name="oneline" property="softwareName" /></B><br><br>
					版本：<B>5.9.00</B><br><br>
					版权所有(C)： 2002～2012，上海全景数字技术公司<br><br><br>
					该份软件被授权给<B><tbl:write name="oneline" property="msoName" /></B>使用<br><br>
				授权有效期：从<I><tbl:write name="oneline" property="licenseValidFrom" /></I>到<I><tbl:write name="oneline" property="licenseValidTo" /></I>止<br><br>
				授权用户数：<B><tbl:write name="oneline" property="licensedMaxUser" /></B><br><br>
				当前用户数：<B><%=userCount%></B>
	</font></td>
	</tr>
</table>
<br>
<table width="50%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
