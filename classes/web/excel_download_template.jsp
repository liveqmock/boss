<%@ page import="import java.util.GregorianCalendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=GBK"%>

<% 
	SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	GregorianCalendar cal = new GregorianCalendar();
	System.out.println("System Date: " + bartDateFormat.format(cal.getTime()));
	
	String excelFileName = request.getParameter("excelFileName"); 
	excelFileName = "attachment;filename="+excelFileName+"_"+bartDateFormat.format(cal.getTime())+".xls";
%>

<% response.setHeader("Content-disposition", new String(excelFileName.getBytes("GB2312"),"iso8859-1") ); %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
</head>

<body>
<% 
	String excelTable = request.getParameter("excelTable"); 
%>
<%=excelTable%>


</body>
</html>	