<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>


<script language=javascript>
function frmPost_close(){
	var conditionString=eval('window.opener.frmPost.txtConditionString');
	var productNameList=eval('window.opener.frmPost.txtProductNameList');
	l_value=document.getElementsByName("listID") ;
	l_name =document.getElementsByName("listName");
	var str;
	conditionString.value="";
	productNameList.value=""; 
	if (l_value){
	  if (l_value.length){
	    for(i=0;i<l_value.length;i++){  
		if(l_value[i].checked){
	           conditionString.value=conditionString.value+"`"+l_value[i].value;
	           productNameList.value =productNameList.value+l_name[i].value+" ";
		  
		  
		} 
	    }
	  }else {
	     if(l_value.checked){
	        conditionString.value="`"+l_value.value;
		productNameList.value =l_name.value; 
	     }
	  }	
	}
	if(conditionString.value!=""){
		conditionString.value="P"+conditionString.value;
	}
		
	window.close();
} 
</script>
<form name="frmPost" method="post" action="" >
  <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
  
      <tr class="list_head">
        <td width="15%" class="list_head">&nbsp;</td> 
        <td width="10%" class="list_head" align="center">产品ID</td> 
        <td width="40%" class="list_head" align="center">产品名称</td> 
       </tr>
        <% 
           String checkedProduct=Postern.getCheckedProductIDList();        
           String  productList = Postern.getProductIDList();
           String ccStr=request.getParameter("txtConditionString");
          
           String productNameList="";
           if(!("".equals(ccStr)||ccStr==null)){
           ccStr=ccStr.substring(2);
           String[] ccStrlist=ccStr.split("`");
           
           for(int i=0;i<ccStrlist.length;i++)
           	checkedProduct=checkedProduct+";"+ccStrlist[i];
           	}
       %>
      <input type="hidden" name="checkedProduct" value="<%=checkedProduct%>">  
       <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
            <%
              ProductDTO productDto =(ProductDTO)pageContext.getAttribute("oneline");
              ProductDTO dto =new ProductDTO();
              if (checkedProduct.indexOf(String.valueOf(productDto.getProductID()))>=0){
                   dto.setProductID(productDto.getProductID());
                   productNameList=productNameList+" "+Postern.getProductNameByID(productDto.getProductID());
                   System.out.println("productDto.getProductID()======"+productDto.getProductID());
              }else{
                  dto.setProductID(productDto.getProductID());
              }
              pageContext.setAttribute("onebox",dto);
              
                 
            %>
      
            <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
                  
                 <td align="center" >
                    <tbl:checkbox name="listID"  value="onebox:productID" match="<%=checkedProduct%>" multipleMatchFlag="true" />
                    <input type="hidden" name="listName" value="<tbl:write name="oneline" property="productName" />" >
                 </td>  
                 <td align="center" ><tbl:write name="oneline" property="productID" /></td>  
                 <td align="center" ><tbl:write name="oneline" property="productName" /></td> 
       	
             </tbl:printcsstr>
        </lgc:bloop>                   
  </table>
  <br>
  <table align="center" border="0" cellspacing="0" cellpadding="0" >
	<tr >
	   <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onClick="javascript:frmPost_close()" value="确 定"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	</tr>
    </table>
</form>
       

