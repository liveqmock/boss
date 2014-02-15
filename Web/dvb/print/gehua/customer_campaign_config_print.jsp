<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>有线数字电视整体转换受理登记表</title>
<style type="text/css">
<!--
.STYLE2 {FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 14pt;font-size: 16px; }
-->
</style>
</head>

<%@ page import="com.dtv.oss.web.util.WebKeys" %>

<%
	String CONTENT = (String)request.getAttribute(WebKeys.DOWNLOADWEBACTION_FILECONTENT);
	
	//System.out.println("==================================================="+CONTENT);
%>



<%=CONTENT%>

</body>
</html>
<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>