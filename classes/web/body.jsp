<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator" %>
<%@ page import="com.dtv.oss.dto.OperatorDTO" %>
<%@ page import="com.dtv.oss.util.TimestampUtility" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.util.Postern" %>
 
<br>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">欢迎您使用<d:config prefix="" showName="MSOSYSTEMNAME" suffix="" /></td>
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
	  <td ><font size="3" >当前的时间是： <tbl:writedate certainDateFlag="now" isChinese="true" includeTime="true" /><br><br>您当前的位置是客户服务系统，请小心使用本系统！<%
	OperatorDTO operDto = CurrentLogonOperator.getOperator(request);

	if (operDto!=null)
	{
	Timestamp dtUpd = operDto.getDtLastmod();
	Timestamp dtNow = new Timestamp(System.currentTimeMillis());
	if (dtUpd.before(TimestampUtility.TimestampMove(dtNow,0,-3,-1)))
	{

	%>

	<br><br>距您上次修改密码已经超过3个月，希望您能修改一下密码。

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
<lgc:displayonfirestload name="BILL_BORD">
<%boolean isExistMS=Postern.getBillBoardCount();
  if(isExistMS){%>
<script language="JavaScript" type="text/JavaScript">
window.open('hint.do','','toolbar=no,menubar=no,scrollbars=yes,location=no,height=280,width=450,left=150,top=50');
</script>
<%}%>
</lgc:displayonfirestload>