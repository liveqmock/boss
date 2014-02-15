<%@ page import="java.util.List,
                 com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.dto.OpGroupDTO"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%
    String checkFlags = request.getParameter("checkFlags");
    if (checkFlags == null)
        checkFlags = "";
    List priList = Postern.getNotCurrentHaveOpGroup(WebUtil.StringToInt(request.getParameter("txtOperatorID")));
%>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td width="5%"><div align="center"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.all,'Flags', this.checked)"></div></td>
          <td class="list_head">操作组ID</td>
          <td class="list_head">操作组名称</td>
         
          
        </tr>
<%
    int i = 0;
    String check = null;
    if (priList != null) {
        for (int k = 0; k < priList.size(); k++) {
            OpGroupDTO dto = (OpGroupDTO) priList.get(k);
            if (checkFlags.indexOf(";"+i+";") != -1) {
                check = " ckecked ";
            } else {
                check = "";
            }
%>
        <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	 <td align="center"><input type="checkbox" name="Flags" <%=check%> value="<%=i%>"></div></td>
         <td align="center"><input type="text" name="txtOpGroupID" size="8" value="<%=dto.getOpGroupID()%>" class="textgray" readonly/></td>
         <td align="center"><input type="text" name="txtOpGroupName" size="17" value="<%=dto.getOpGroupName()%>" class="textgray" readonly/></td>
         
          
        </tr>
<%
            check = null;
        }
    }
    i++;%>
</table>