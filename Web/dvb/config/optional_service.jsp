<%@ page import="java.util.List,
                  com.dtv.oss.util.Postern,
                  com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.dto.ServiceDTO"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%
    String checkFlags = request.getParameter("checkFlags");
    if (checkFlags == null)
        checkFlags = "";
    
    int size =0;
    int productId = WebUtil.StringToInt(request.getParameter("txtProductID"));
    List serviceList = Postern.getOptionServiceByProduct(productId);
    if(serviceList!=null) 
      size = serviceList.size();
       
%>

<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
       
          <td width="5%"><div align="center"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.all,'Flags', this.checked)"></div></td>
          <td class="list_head" width="10%"><div align="center">业务ID</div></td>
          <td class="list_head" width="40%"><div align="center">业务名称</div></td>
          <td class="list_head" width="45%"><div align="center">描述</div></td>
        </tr>
<%
    String check = null;
    if (size > 0)
    for (int i = 0; i < size; i++) {
   
        ServiceDTO dto = (ServiceDTO) serviceList.get(i);
        if (checkFlags.indexOf(";"+i+";") != -1) {
            check = " ckecked ";
        } else {
            check = "";
        }
        String descrption="";
        if(dto.getDescription()!=null)
        descrption=dto.getDescription();
        
        
%>
        <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	  <td align="center"><input type="checkbox" name="Flags" <%=check%> value="<%=i%>"></td>
          <td align="center"><input type="text" name="txtServiceID" size="8" value="<%=dto.getServiceID()%>" class="textgray" readonly/> </td>
         <td align="center"><input type="text" name="txtServiceName" size="17" value="<%=dto.getServiceName()%>" class="textgray" readonly/> </td>
          <td align="center"><input type="text" name="txtDescription" size="17" value="<%=descrption%>" class="textgray" readonly/> </td> 
        </tr>
<%    check = null;
 
    }%>
</table>