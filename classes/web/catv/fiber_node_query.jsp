<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO,
                 com.dtv.oss.dto.DeviceModelDTO,
                 com.dtv.oss.web.util.WebUtil" %>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td>
     <%
	 	//String txtDeviceModel =request.getParameter("txtDevicemodel");
    	//String txtDeviceClass =request.getParameter("txtDeviceClass");
    	//String txtDeviceClass =request.getParameter("txtDeviceClass");
    	//String txtDeviceClass =request.getParameter("txtDeviceClass");
    %>
      
      <iframe name=head width=100% height=640 frameborder=0 scrolling=no src="fiber_node_query_list.do" ></iframe>
    </td>
  </tr>
</table>