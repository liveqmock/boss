<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�������ֵ�������ת������ǼǱ�</title>
<style type="text/css">
<!--
.STYLE2 {FONT-FAMILY: ����_GB2312; mso-bidi-font-size: 14pt;font-size: 16px; }
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