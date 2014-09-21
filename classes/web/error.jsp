<%@ page import="java.lang.Throwable" %>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>

<%
    Throwable ex = (Throwable)request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE);
    if (ex!=null)
    {
%>
<BR>
	<table border=0 cellpadding=2 width="80%">
  	<caption><font color="red"><i>发生错误，错误信息如下:</i></font></caption>
  	        <tr><td align="center" colspan=2><font color="red"><%=ex.getMessage()%></font></td></tr>
  	</table>
<%  }
%>
