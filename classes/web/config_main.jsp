<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator" %>
<%@ page import="com.dtv.oss.dto.OperatorDTO" %>
<%@ page import="com.dtv.oss.util.TimestampUtility" %>
<%@ page import="java.sql.Timestamp" %>
<br>
<br>
<br>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">��ӭ���������������ۺ�ҵ����Ӫ֧��ϵͳ</td>
      </tr>
</table>
<br>
<br>
<table width="50%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
	<br>
<table  border="0" cellspacing="10" cellpadding="0" >
	<tr align="center">
	  <td width="182"><img src="img/welcome.jpg" ></td>
	  <td ><font size="3" >��ǰ��ʱ���ǣ� <tbl:writedate certainDateFlag="now" isChinese="true" includeTime="true" /> 11ʱ31��50��<br><br>����ǰ��λ���ǿͻ�����ϵͳ����С��ʹ�ñ�ϵͳ��<%
	OperatorDTO operDto = CurrentLogonOperator.getOperator(request);

	if (operDto!=null)
	{
	Timestamp dtUpd = operDto.getDtLastmod();
	Timestamp dtNow = new Timestamp(System.currentTimeMillis());
	if (dtUpd.before(TimestampUtility.TimestampMove(dtNow,0,-3,-1)))
	{

	%>

	<br><br>�����ϴ��޸������Ѿ�����3���£�ϣ�������޸�һ�����롣

	<%
	}
	}
	%>   
	</font></td>
	</tr>
</table>
<br>
<table width="50%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
