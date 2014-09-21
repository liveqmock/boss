<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>


<script language=javascript>
 
</script>

  <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
  
      <tr class="list_head">
        <td width="15%" class="list_head">&nbsp;</td> 
        <td width="10%" class="list_head" align="center">产品ID</td> 
        <td width="40%" class="list_head" align="center">产品名称</td> 
         
       </tr>
        <%  
            int packageID = WebUtil.StringToInt(request.getParameter("txtPackageId")); 
            String checkedProduct=Postern.getCheckedProductIDList(packageID);
            
            
         
           
           
       %>
      <input type="hidden" name="checkedProduct" value="<%=checkedProduct%>">  
       <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
            <%
              
              ProductDTO productDto =(ProductDTO)pageContext.getAttribute("oneline");
              ProductDTO dto =new ProductDTO();
              if (checkedProduct.indexOf(String.valueOf(productDto.getProductID()))>=0){
                   dto.setProductID(productDto.getProductID());
                   
              }else{
                  dto.setProductID(productDto.getProductID());
              }
              pageContext.setAttribute("onebox",dto);
                 
            %>
           
            <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
                  
                 <td align="center" ><tbl:checkbox name="listID"  value="onebox:productID" match="<%=checkedProduct%>" multipleMatchFlag="true" /></td>  
                 <td align="center" ><tbl:write name="oneline" property="productID" /></td>  
                 <td align="center" ><tbl:write name="oneline" property="productName" /></td> 
           </tbl:printcsstr>
        </lgc:bloop>                     
  </table>
 
       

