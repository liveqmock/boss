<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="java.lang.Throwable" %>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>
<Script language=JavaScript>
<!--
<%
    Throwable ex = (Throwable)request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE);
    if (ex!=null)
    {
%>
var ErrorMsg='<table border=0 cellpadding=2 width="80%"><caption><font color="red"><i>��<tbl:writeparam name="buyIndex"/>�鹺�������󣬴�����Ϣ����:</i></font></caption><tr><td align="center" colspan=2><font color="red"><%=ex.getMessage()%></font></td></tr></table>';
//parent.document.getElementById('errorDiv').innerHTML=ErrorMsg;
parent.errorProcess(<tbl:writeparam name="buyIndex"/>,'<%=ex.getMessage()%>');
<%  }%>
function check_buyInfo()
{
	
}
-->
</Script>
<form name="frmPost" method="post" action="" >     
     <tbl:hiddenparameters pass="txtOpenSourceType/txtCsiType/txtCustType/txtDistrictID/txtBuyContent/buyIndex" />
</form> 
