<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="java.lang.Throwable" %>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>

<%
    Throwable ex = (Throwable)request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE);
    if (ex!=null)
    {
%>
<BR>
	 <table width="50%" border="0" cellspacing="10" cellpadding="0">
  	<caption><font color="red"><i>发生错误，错误信息如下:</i></font></caption>
  	        <tr><td align="center" colspan=2><font color="red"><%=ex.getMessage()%></font></td></tr>
  	</table>
<%
    }
%>
<br>
<form name="frmPost" method="post" action="">
    <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif">
		   <a href="<bk:backurl property="group_bargain_query_detail.do/campaign_query_detail.do" />" class="btn12">返回</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
		</tr>
      </table>

</form>

<SCRIPT LANGUAGE="JAVASCRIPT">
<!--
function frm_submit() {
    document.frmPost.submit();
}
//-->
</SCRIPT>