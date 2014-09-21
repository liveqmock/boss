<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>

<%
    
        String title ="业务帐户过户-新客户信息";
%>

<!--
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
    </table>
    <br>
-->
  <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0" class="list_bg" >
        <tr ><td>
          <jsp:include page = "customer_basicInfomation.jsp"/>
        </td></tr>
        <tr ><td> 
          <jsp:include page="customer_accountInfomation.jsp"/>
        </td></tr>      
    </table>

         
