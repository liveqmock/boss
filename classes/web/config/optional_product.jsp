<%@ page import="java.util.List,
                  com.dtv.oss.util.Postern,
                  com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.dto.ProductDTO"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%
    String checkFlags = request.getParameter("checkFlags");
    if (checkFlags == null)
        checkFlags = "";
    String selectFlags = request.getParameter("selectFlags");
    if (selectFlags == null)
        selectFlags = "";
    List oprList = Postern.getCurrentNotHaveProduct(WebUtil.StringToInt(request.getParameter("txtPackageID")));
    int size = oprList.size();
%>

<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
       
          <td width="5%"><div align="center"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.all,'Flags', this.checked)"></div></td>
          <td  width="5%" class="list_head"><div align="center">ID</div></td>
           <td class="list_head" width="50%"><div align="center">产品名称</div></td>
           <td class="list_head" width="40%"><div align="center">可选否</div></td>
        </tr>
<%
    String check = null;
    String check1 = null;
    if (size > 0)
    for (int i = 0; i < size; i++) {
        ProductDTO dto = (ProductDTO) oprList.get(i);
        if (checkFlags.indexOf(";"+i+";") != -1) {
            check = " ckecked ";
        } else {
            check = "";
        }
         if (selectFlags.indexOf(";"+i+";") != -1) {
            check1 = " ckecked ";
        } else {
            check1 = "";
        }
        int productId = dto.getProductID();
        String productName = dto.getProductName();
        
%>
        <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	  <td align="center"><input type="checkbox" name="Flags" <%=check%> value="<%=i%>"></td>
          <input type="hidden" name="txtDtLastmod" value="<%=dto.getDtLastmod()%>"/>
          <td align="center"><input type="text" name="txtProductID" size="8" value="<%=productId%>" class="textgray" readonly/> </td>
         <td align="center"><input type="text" name="txtOperatorName" size="17" value="<%=productName%>" class="textgray" readonly/> </td>
          <td align="center"><input type="checkbox" name="selectFlags" <%=check1%> value="<%=i%>"></td>
        </tr>
<%    check = null;
 check1 = null;
    }%>
</table>