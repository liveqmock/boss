<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.MarketSegmentDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>


<script language=javascript>
 
</script>

  <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
  
      <tr class="list_head">
        <td  class="list_head">&nbsp;</td> 
        <td  class="list_head" align="center">市场分区ID</td> 
        <td  class="list_head" align="center">区域名称</td> 
         
       </tr>
        <%  
            int packageID = WebUtil.StringToInt(request.getParameter("txtPackageId")); 
            String checkedMarket=Postern.getCheckedMarketIDList(packageID);
            
       %>
      <input type="hidden" name="checkedMarket" value="<%=checkedMarket%>">  
       <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
            <%
              
              MarketSegmentDTO msDto =(MarketSegmentDTO)pageContext.getAttribute("oneline");
              MarketSegmentDTO dto =new MarketSegmentDTO();
              if (checkedMarket.indexOf(String.valueOf(msDto.getId()))>=0){
                   dto.setId(msDto.getId());
                   
              }else{
                  dto.setId(msDto.getId());
              }
              pageContext.setAttribute("onebox",dto);
                 
            %>
           
            <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
                  
                 <td align="center" ><tbl:checkbox name="listID"  value="onebox:id" match="<%=checkedMarket%>" multipleMatchFlag="true" /></td>  
                 <td align="center" ><tbl:write name="oneline" property="id" /></td>  
                 <td align="center" ><tbl:write name="oneline" property="name" /></td> 
           </tbl:printcsstr>
        </lgc:bloop>                     
  </table>
 
       

