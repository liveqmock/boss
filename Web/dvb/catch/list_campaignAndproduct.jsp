<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%
    String openCampaignFlag = (request.getAttribute("openCampaignFlag")==null) ? "true" : request.getAttribute("openCampaignFlag").toString();

%>
<style type="text/css">
<!--
A {
	COLOR: #111177
}
-->
</style>
<table width="780" border="0" align="center" cellpadding="0" cellspacing="0">
		
   <tr>
   <% if (openCampaignFlag.equals("true")) { %>
      	<td align="right" width="1%"><img src="" id=opencampaign0 height="20"></td> 
      	<td height="20" id=opencampaign1 align="center"  width="23%">
      	   <a href="javascript:changestyle(1)" id=opencampaign2 style="text-decoration:none; color: #111177">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开户套餐活动&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
      	</td>
      	<td width="1%"><img src="" id=opencampaign3 height="20"></td>
   <% } %>
   
      <td align="right" width="1%"><img src="" id="mutiproduct0" height="20"></td> 
      <td height="20" id="mutiproduct1" align="center" width="23%" >
      	 <a href="javascript:changestyle(2)" id="mutiproduct2" style="text-decoration:none;color: #111177">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;多产品的产品包&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
      </td>
      <td width="1%"><img src="" id="mutiproduct3" height="20"></td>
    		  
      <td align="right" width="1%"><img src="img/catch/btn_nav_l.gif" id="singleproduct0" height="20"></td> 
		  <td height="20" width="23%" id="singleproduct1" background="img/catch/btn_nav_body.gif" align="center">
		  	 <a href="javascript:changestyle(3)" id="singleproduct2" style="text-decoration:none; color: #111177">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单产品的产品包&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
		  </td>
		 	<td width="1%"><img src="img/catch/btn_nav_r.gif" id="singleproduct3" height="20"></td>
		 		  
		 	<td align="right" width="1%"><img src="img/catch/btn_nav_l.gif" id="generalcampaign0" height="20"></td> 
      <td height="20" width="23%" id="generalcampaign1" background="img/catch/btn_nav_body.gif" align="center">
      	<a href="javascript:changestyle(4)" id="generalcampaign2" style="text-decoration:none; color: #111177">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;优&nbsp;惠&nbsp;活&nbsp;动&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
      </td>
      <td width="1%"><img src="img/catch/btn_nav_r.gif" id="generalcampaign3" height="20"></td>
      
   <% if (openCampaignFlag.equals("false")) { %>
       <td align="right" width="1%">&nbsp;</td> 
       <td height="20" align="center"  width="23%">&nbsp;</td>
       <td width="1%">&nbsp;</td>
   <% } %>  
    
    </tr>
    
</table>
<br>
<table width="780" valign="top"  border="0" align="center" cellpadding="0" cellspacing="0">
  <%
     if (openCampaignFlag.equals("true")) { 
  %>
  	<tr id="prefered1">
  		 <td>
          <jsp:include page="list_opencampaign.jsp" />
       </td>
    </tr>
   <% } %>
  
  <% if (openCampaignFlag.equals("false")){  %>
    <tr id="prefered2" >
  <% } else { %>
    <tr id="prefered2" style="display:none">
  <% } %>
    	 <td>
         <jsp:include page="list_mutiproduct.jsp" />
       </td>
    </tr>
    <tr id ="prefered3" style="display:none">
    	 <td>
         <jsp:include page="list_singleproduct.jsp" />
       </td>
    </tr>
    <tr id ="prefered4" style="display:none">
    	 <td>
         <jsp:include page="list_generalcampaign.jsp" />
       </td>
    </tr>
</table>
<input type="hidden" name="OpenCampaignList" value="<tbl:writeparam name="OpenCampaignList" />">
<input type="hidden" name="MutiProductList" value="<tbl:writeparam name="MutiProductList" />">
<input type="hidden" name="SingleProductList" value="<tbl:writeparam name="SingleProductList" />">
<input type="hidden" name="GeneralCampaignList" value="<tbl:writeparam name="GeneralCampaignList" />">

<script language="Javascript">
	var openCampaignFlag ='<%=openCampaignFlag%>';
  function changestyle(styleflag){
     if (styleflag ==1){
     	  document.all("prefered1").style.display ="";
     	  document.all("prefered2").style.display ="none";
     	  document.all("prefered3").style.display ="none";
     	  document.all("prefered4").style.display ="none";    
     	  fill_picture('opencampaign','true');
     	  fill_picture('mutiproduct','false');
     	  fill_picture('singleproduct','false');
     	  fill_picture('generalcampaign','false');
	
     }else if (styleflag ==2){
     	  if (openCampaignFlag =='true'){  
     	      document.all("prefered1").style.display ="none";
     	      fill_picture('opencampaign','false');
     	  }
     	  document.all("prefered2").style.display ="";
     	  document.all("prefered3").style.display ="none";
     	  document.all("prefered4").style.display ="none";    	      
     	  fill_picture('mutiproduct','true');
     	  fill_picture('singleproduct','false');
     	  fill_picture('generalcampaign','false');
     }else if (styleflag ==3){
     	  if (openCampaignFlag =='true'){  
     	     document.all("prefered1").style.display ="none";
     	     fill_picture('opencampaign','false');
     	  }
     	  document.all("prefered2").style.display ="none";
     	  document.all("prefered3").style.display ="";
     	  document.all("prefered4").style.display ="none";    	     
     	  fill_picture('mutiproduct','false');
     	  fill_picture('singleproduct','true');
     	  fill_picture('generalcampaign','false');
     }else if (styleflag ==4){
     	  if (openCampaignFlag =='true') {
     	     document.all("prefered1").style.display ="none";
     	     fill_picture('opencampaign','false');
     	  }
     	  document.all("prefered2").style.display ="none";
     	  document.all("prefered3").style.display ="none";
     	  document.all("prefered4").style.display ="";
     	  fill_picture('mutiproduct','false');
     	  fill_picture('singleproduct','false');
     	  fill_picture('generalcampaign','true');
     }
  }
  
  function add_openCampaign(){
  	 document.all("OpenCampaignList").value ="";
  	 campaignCount =0;
  	 if (document.all("listID1")){
  	    if (document.all("listID1").length > 1) {
	        for (i=0;i<document.all("listID1").length;i++)
		        if (document.all("listID1")[i].checked){
		        	 campaignCount =campaignCount+1;
		           document.all("OpenCampaignList").value=document.all("OpenCampaignList").value + document.all("listID1")[i].value+";";
		       }
        } else {
          if (document.all("listID1").checked) {
              document.all("OpenCampaignList").value=document.all("listID1").value + ";";
          } else {
             document.all("OpenCampaignList").value = '';
         }
      }
    }
  
   return true;
  /*
     if (campaignCount >1){
    	  alert("只能选一个套餐！");
    	  return false;
     }else{
    	  return true;
     }
   */
  }
  
  function add_productAndGeneralCampaign (){
  	 document.all("MutiProductList").value ="";
  	 if (document.all("listID2")){
  	    if (document.all("listID2").length > 1) {
	         for (i=0;i<document.all("listID2").length;i++)
		         if (document.all("listID2")[i].checked){
			          document.all("MutiProductList").value=document.all("MutiProductList").value + document.all("listID2")[i].value + ";";
		         }
       } else {
            if (document.all("listID2").checked) {
               document.all("MutiProductList").value=document.all("listID2").value + ";";
            } else {
              document.all("MutiProductList").value = '';
            }
       }
     }
    
     document.all("SingleProductList").value ="";
     if(document.all("listID3")){
       if (document.all("listID3").length > 1) {
	        for (i=0;i<document.all("listID3").length;i++)
		         if (document.all("listID3")[i].checked){
			          document.all("SingleProductList").value=document.all("SingleProductList").value + document.all("listID3")[i].value + ";";
		         }
       } else {
         if (document.all("listID3").checked) {
             document.all("SingleProductList").value=document.all("listID3").value + ";";
         } else {
            document.all("SingleProductList").value = '';
         }
       }
     }
     
     document.all("GeneralCampaignList").value="";
     if (document.all("listID4")){
       if (document.all("listID4").length > 1) {
	        for (i=0;i< document.all("listID4").length;i++)
		       if (document.all("listID4")[i].checked){
		          document.all("GeneralCampaignList").value=document.all("GeneralCampaignList").value + document.all("listID4")[i].value + ";";
		       }
       } else {
           if (document.all("listID4").checked) {
              document.all("GeneralCampaignList").value=document.all("listID4").value + ";";
           } else {
              document.all("GeneralCampaignList").value = '';
           }
      }
    }
  }
  
 function clearchooseproduct(clearPackageIds){
    clearPackageId =clearPackageIds.split(",");
  	for (i=0; i< clearPackageId.length; i++){
  	   if (document.all("listID2")){
  	 	    if (document.all("listID2").length > 1) {
	           for (j=0;j< document.all("listID2").length;j++){
	                if (clearPackageId[i] ==document.all("listID2")[j].value)  
	                    document.all("listID2")[j].checked=false;
	             }
	        }
	        else {
	      	   if (clearPackageId[i] ==document.all("listID2").value)
	      	      document.all("listID2").checked=false;
	        }
	     }
  	 
  	  if (document.all("listID3")){
  	 	   if (document.all("listID3").length >1) {
       	    for (p=0;p< document.all("listID3").length; p++){
       	       if (clearPackageId[i] ==document.all("listID3")[p].value)  
       	          document.all("listID3")[p].checked=false;
       	    }
       	 } else {
       		   document.all("listID3").checked=false;
       	 }
  	  }
    }
  }
  
  function campaignchoose(clearPackageIds){
    /*	 
     clearchooseproduct(clearPackageIds);
  	 strnewpackageIds =getpackageIds();
     newpackageId =strnewpackageIds.split(",");
     for (i=0; i< newpackageId.length; i++){
    	 //checked muti product
    	 if (document.all("listID2")){
    	    if (document.all("listID2").length > 1) {
	           for (j=0;j< document.all("listID2").length;j++){
	        	    if (newpackageId[i] ==document.all("listID2")[j].value) document.all("listID2")[j].checked=true;  	
	           }
          } else{
       	     if (newpackageId[i] ==document.all("listID2").value) document.all("listID2").checked=true;
          }
       }
       
       //checked Single product
       if (document.all("listID3")){
          if (document.all("listID3").length >1) {
       	     for (p=0;p< document.all("listID3").length; p++) {
       	  	    if (newpackageId[i] ==document.all("listID3")[p].value) document.all("listID3")[p].checked=true;
       	     }
          } else{
       	     if (newpackageId[i] ==document.all("listID3").value) document.all("listID3").checked=true;
          }
       }
    } 
    */
  }
  
  function getpackageIds(){
  	 var strnewpackageIds ='';
  	 if (document.all("listID1")){
    	    if (document.all("listID1").length > 1) {
	           for (m=0;m< document.all("listID1").length;m++){
	           	   if (document.all("listID1")[m].checked){
	           	    	  if (strnewpackageIds =='')  strnewpackageIds =document.all("sub_listID1")[m].value;
	           	        else strnewpackageIds =strnewpackageIds +","+document.all("sub_listID1")[m].value ;
	           	   }
	           }
  	      } else {
  	      	 if (document.all("listID1").checked) strnewpackageIds =document.all("sub_listID1").value;
  	      }
  	  }
  	  
  	  if (document.all("listID4")){
    	    if (document.all("listID4").length > 1) {
	           for (n=0;n< document.all("listID4").length;n++){
	           	   if (document.all("listID4")[n].checked){
	           	    	  if (strnewpackageIds =='')  strnewpackageIds =document.all("sub_listID4")[n].value;
	           	        else strnewpackageIds =strnewpackageIds +","+document.all("sub_listID4")[n].value ;
	           	   }
	           }
  	      } else {
  	      	 if (document.all("listID4").checked) strnewpackageIds =document.all("sub_listID4").value;
  	      }
  	  }
  	  return strnewpackageIds;
  }
  
  function view_package_detail(strID,strName){
    window.open('list_product_package_detail.do?txtPackageID='+strID+'&txtPackageName='+strName,'','width=300,height=250,resizable=no,toolbar=no,scrollbars=yes');
  }
 
  if (openCampaignFlag =='true'){
 	     fill_picture('opencampaign','true');
 	     fill_picture('mutiproduct','false');
  }else {
       fill_picture('mutiproduct','true');
  }
  
  
  function fill_picture(idName,chooseFlag){
  	 if (chooseFlag =='true'){
  	 	 // alert(document.getElementById(idName+"1").srcElement.style);
  	    document.getElementById(idName+"0").src='img/catch/btn_nav1_l.gif';
  	    document.getElementById(idName+"1").style.backgroundImage='url(img/catch/btn_nav1_body.gif)';
  	    document.getElementById(idName+"2").style.color ='#FFFFFF';
  	    document.getElementById(idName+"3").src='img/catch/btn_nav1_r.gif';
  	 }else{
  	 	  document.getElementById(idName+"0").src='img/catch/btn_nav_l.gif';
  	    document.getElementById(idName+"1").style.backgroundImage='url(img/catch/btn_nav_body.gif)';
  	    document.getElementById(idName+"2").style.color ='#111177';
  	    document.getElementById(idName+"3").src='img/catch/btn_nav_r.gif';
  	 }
  	
  }
 
</script>
   

