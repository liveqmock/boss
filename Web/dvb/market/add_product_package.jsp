<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.ProductPackageDTO" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>


<script language=javascript>
 
</script>
 <%  
    String checkedPackage="";
     if(request.getParameter("campaignID")!=null){
       int campaignId=WebUtil.StringToInt(request.getParameter("campaignID"));
       checkedPackage=Postern.getPackageIDByCampaignID(campaignId);
     }
     String contractNo = request.getParameter("contractNo");  
     if(contractNo!=null)
      checkedPackage = Postern.getPackageIDByContractNo(contractNo);
      
  //   int campaignId=WebUtil.StringToInt(request.getParameter("campaignID"));
            
 //     String checkedPackage=Postern.getPackageIDByCampaignID(campaignId);
            
   %>
    <input type="hidden" name="checkedPackage" value="<%=checkedPackage%>">  
           
  <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
      <tr class="list_head">
        <td width="10%" class="list_head">&nbsp;</td> 
        <td width="20%" class="list_head" align="center">产品包ID</td> 
        <td width="60%" class="list_head" align="center">产品包名称</td> 
       </tr>
 <%
              
             
              
               ArrayList  packageDtoList = Postern.getProductPackageDTO(null,-100);
               Iterator ite=packageDtoList.iterator();
               while(ite.hasNext()){
                ProductPackageDTO dto= (ProductPackageDTO)ite.next(); 
                ProductPackageDTO checkedDto= new ProductPackageDTO();
                if (checkedPackage.indexOf(String.valueOf(dto.getPackageID()))>=0){
                  checkedDto.setPackageID(dto.getPackageID());
                    
              }else{
                  checkedDto.setPackageID(dto.getPackageID());
              }
               pageContext.setAttribute("onebox",checkedDto);
               pageContext.setAttribute("oneline",dto);   
                
                 
            %>
       
            <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
                 <td align="center" ><tbl:checkbox name="ListID"  value="onebox:packageID" match="<%=checkedPackage%>" multipleMatchFlag="true" /></td>  
                
                 <td align="center" ><tbl:write name="oneline" property="packageID"/></td> 
                   <td align="center" ><tbl:write name="oneline" property="packageName"/></td> 
                
             </tbl:printcsstr>
             <%}%>
                  
  </table>
 
       

