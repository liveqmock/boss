<%@ page import="java.util.List,
                  com.dtv.oss.util.Postern,
                  com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.dto.OperatorDTO"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%
    String checkFlags = request.getParameter("checkFlags");
    if (checkFlags == null)
        checkFlags = "";
    List oprList = Postern.getNotCurrentHaveOperator(WebUtil.StringToInt(request.getParameter("txtOpGroupID")));
    int size = oprList.size();
%>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
       
          <td width="5%"><div align="center"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.all,'Flags', this.checked)"></div></td>
          <td class="list_head"><div align="center">操作员ID</div></td>
           <td class="list_head"><div align="center">操作员名称</div></td>
           
        </tr>
<%
    String check = null;
    if (size > 0)
    for (int i = 0; i < size; i++) {
        OperatorDTO dto = (OperatorDTO) oprList.get(i);
        if (checkFlags.indexOf(";"+i+";") != -1) {
            check = " ckecked ";
        } else {
            check = "";
        }
%>
        <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	  <td align="center"><input type="checkbox" name="Flags" <%=check%> value="<%=i%>"></td>
          <input type="hidden" name="txtDtLastmod" value="<%=dto.getDtLastmod()%>"/>
          <td align="center"><input type="text" name="txtOperatorID" size="8" value="<%=dto.getOperatorID()%>" class="textgray" readonly/> </td>
         <td align="center"><input type="text" name="txtOperatorName" size="17" value="<%=dto.getOperatorName()%>" class="textgray" readonly/> </td>
         
        </tr>
<%    check = null;
    }%>
</table>