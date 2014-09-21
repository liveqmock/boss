<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<script language=javascript>
 
</script>
       <%  
             int campaignId=WebUtil.StringToInt(request.getParameter("campaignID"));
            
             String checkedProduct=Postern.getProductIDByCampaignID(campaignId);
         %>
      <input type="hidden" name="checkedProduct" value="<%=checkedProduct%>">  

 <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
        <tr class="list_head">
          <td width="10%" class="list_head">&nbsp;</td> 
          <td width="20%" class="list_head" align="center">产品ID</td> 
          <td width="40%" class="list_head" align="center">产品名称</td> 
          <td width="20%" class="list_head" align="center">产品状态</td> 
        </tr>
 <%
              
             
              
               ArrayList  productDtoList = Postern.getProductDTO();
              Iterator ite=productDtoList.iterator();
              while(ite.hasNext()){
                ProductDTO dto= (ProductDTO)ite.next(); 
                ProductDTO checkedDto= new ProductDTO();
                if (checkedProduct.indexOf(String.valueOf(dto.getProductID()))>=0){
                  checkedDto.setProductID(dto.getProductID());
                    
              }else{
                  checkedDto.setProductID(dto.getProductID());
              }
               pageContext.setAttribute("onebox",checkedDto);
               pageContext.setAttribute("oneline",dto);   
                
                 
            %>
      
     
      
              <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
                <td align="center" ><tbl:checkbox name="ListID"  value="onebox:productID" match="<%=checkedProduct%>" multipleMatchFlag="true" /></td>  
                <td align="center" ><tbl:write name="oneline" property="productID" /></td>  
                 <td align="center" ><tbl:write name="oneline" property="productName" /></td> 
		 <td align="center"><d:getcmnname typeName="SET_P_PACKAGESTATUS" match="oneline:status"/></td> 	 

                 </tbl:printcsstr>
 <%
        }
       
    
  %>
  </table>
 
       