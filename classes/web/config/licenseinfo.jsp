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

<!-- \\\\\\\\\\\\\\CMS V5.0��ϵͳ�汾����Build 20060630�����Build��Ϣ��
��Ȩ���У��Ϻ�ȫ�����ּ�����˾ 2002��2008  -->
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
					�汾��<B>5.9.00</B><br><br>
					��Ȩ����(C)�� 2002��2012���Ϻ�ȫ�����ּ�����˾<br><br><br>
					�÷��������Ȩ��<B><tbl:write name="oneline" property="msoName" /></B>ʹ��<br><br>
				��Ȩ��Ч�ڣ���<I><tbl:write name="oneline" property="licenseValidFrom" /></I>��<I><tbl:write name="oneline" property="licenseValidTo" /></I>ֹ<br><br>
				��Ȩ�û�����<B><tbl:write name="oneline" property="licensedMaxUser" /></B><br><br>
				��ǰ�û�����<B><%=userCount%></B>
	</font></td>
	</tr>
</table>
<br>
<table width="50%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
