<%@ page import="java.util.List,
                 com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.dto.DeviceModelDTO"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%
    String checkFlags = request.getParameter("checkFlags");
    if (checkFlags == null)
        checkFlags = "";
    List deviceList = Postern.getDeviceModelDTOByProductID(WebUtil.StringToInt(request.getParameter("txtProductID")));
%>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td width="5%"><div align="center"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.all,'Flags', this.checked)"></div></td>
          <td class="list_head">代码</td>
          <td class="list_head">名称</td>
          <td class="list_head">提供商</td>
          <td class="list_head">类型</td>
        </tr>
<%
    int i = 0;
    String check = null;
    if (deviceList != null) {
        for (int k = 0; k < deviceList.size(); k++) {
            DeviceModelDTO dto = (DeviceModelDTO) deviceList.get(k);
            if (checkFlags.indexOf(";"+i+";") != -1) {
                check = " ckecked ";
            } else {
                check = "";
            }
%>
        <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	 <td align="center"><input type="checkbox" name="Flags" <%=check%> value="<%=i%>"></div></td>
         <td align="center"><input type="text" name="txtDeviceModel"  value="<%=dto.getDeviceModel()%>" class="textgray" readonly/></td>
         <td align="center"><input type="text" name="txtDescription"  value="<%=dto.getDescription()%>" class="textgray" readonly/></td>
         <td align="center"><input type="text" name="txtProviderID"  value="<%=Postern.getOrgNameByOrgID(dto.getProviderID())%>" class="textgray" readonly/></td>
         <td align="center"><input type="text" name="txtDeviceClass"  value="<%=dto.getDeviceClass()%>" class="textgray" readonly/></td>
        </tr>
<%
            check = null;
        }
    }
    i++;%>
</table>