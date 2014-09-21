<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>


<script language=javascript>
 
</script>

  <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
  
      <tr class="list_head">
        <td width="15%" class="list_head">&nbsp;</td> 
        <td width="10%" class="list_head" align="center">产品ID</td> 
        <td width="40%" class="list_head" align="center">产品名称</td> 
         <td width="25%" class="list_head" align="center">产品状态</td> 
       </tr>
        <%  
             
 			String checkedProduct=Postern.getCheckedProductIDList();
			String strProductIDList=request.getParameter("txtProductIDList");
            if(strProductIDList!=null && !"".equals(strProductIDList))
            	checkedProduct = strProductIDList;
			String  productList = Postern.getProductIDList();
         
           
           
       %>
      <input type="hidden" name="checkedProduct" value="<%=checkedProduct%>">  
       <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
            <%
              ProductDTO productDto =(ProductDTO)pageContext.getAttribute("oneline");
              ProductDTO dto =new ProductDTO();
              if (checkedProduct.indexOf(String.valueOf(productDto.getProductID()))>=0){
                   dto.setProductID(productDto.getProductID());
                   System.out.println("productDto.getProductID()======"+productDto.getProductID());
              }else{
                  dto.setProductID(productDto.getProductID());
              }
              pageContext.setAttribute("onebox",dto);
                 
            %>
      
            <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
                  
                 <td align="center" ><tbl:checkbox name="listID"  value="onebox:productID" match="<%=checkedProduct%>" multipleMatchFlag="true" /></td>  
                 <td align="center" ><tbl:write name="oneline" property="productID" /></td>  
                 <td align="center" ><tbl:write name="oneline" property="productName" /></td> 
                 <input type="hidden" name="productName" value="<tbl:write name="oneline" property="productName" />">
                 <td align="center" ><d:getcmnname typeName="SET_P_PRODUCTSTATUS" match="oneline:status" /></td>               
             </tbl:printcsstr>
        </lgc:bloop>                     
  </table>
 
       

