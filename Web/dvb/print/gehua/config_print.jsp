<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.FileOutputStream"%>
<%@ page import="java.io.File"%>
<%@ page import="com.dtv.oss.util.*"%>
<%@ page import="java.sql.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
</head>

<%
	String CONTENT = (String)request.getAttribute(WebKeys.DOWNLOADWEBACTION_FILECONTENT);
%>

<%=CONTENT%>

<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>