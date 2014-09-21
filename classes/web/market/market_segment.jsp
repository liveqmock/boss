<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.MarketSegmentDTO" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<script language=javascript>
  
</script>

  <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
  
      <tr class="list_head">
        <td width="15%" class="list_head">&nbsp;</td> 
        <td width="30%" class="list_head" align="center">市场分区ID</td> 
        <td width="55%" class="list_head" align="center">市场分区名称</td> 
          
       </tr>
       
        <%  
             
            int campaignId=WebUtil.StringToInt(request.getParameter("campaignID"));
          
            String checkedMarket=Postern.getMarketSegmentIDByCampaignID(campaignId);
            	
           
         %>
           <input type="hidden" name="checkedMarket" value="<%=checkedMarket%>">  
         <%   
            ArrayList  segmentDtoList = Postern.getMarketSegmentDTO();
            Iterator ite=segmentDtoList.iterator();
             while(ite.hasNext()){
                MarketSegmentDTO dto= (MarketSegmentDTO)ite.next(); 
                MarketSegmentDTO checkedDto= new MarketSegmentDTO();
                if (checkedMarket.indexOf(String.valueOf(dto.getId()))>=0){
                   checkedDto.setId(dto.getId());
                    
              }else{
                   checkedDto.setId(dto.getId());
              }
              pageContext.setAttribute("onebox",checkedDto);
               pageContext.setAttribute("oneline",dto);   
                
           
         %>
          
            <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
               <td align="center" ><tbl:checkbox name="ListID"  value="onebox:Id" match="<%=checkedMarket%>" multipleMatchFlag="true" /></td>  
                
                 <td align="center" ><tbl:write name="oneline" property="Id"/></td>  
                 <td align="center" ><tbl:write name="oneline" property="Name"/></td> 
                     
            </tbl:printcsstr>
             <%}%>      
  </table>
 
       

