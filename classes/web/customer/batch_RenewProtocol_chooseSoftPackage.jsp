<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.CommonKeys"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import ="java.util.*" %>
<%@ page import="com.dtv.oss.dto.ProtocolDTO,
                 com.dtv.oss.dto.ProductPackageDTO,
                 com.dtv.oss.dto.ProductDTO" %>

<Script language=JavaScript>
function add_productAndGeneralCampaign (){
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
}

function check_frm(){
	if(document.frm_Post.txtAccount.value=="") {
		 alert("请选择有效帐户！");
		 return false;
	}
		 	 
  if (check_Blank(document.frm_Post.txtUsedMonth, true, "使用月数"))
	    return false;
	if (!check_Num(document.frm_Post.txtUsedMonth, true, "使用月数"))
	    return false;
	if (document.frm_Post.txtUsedMonth.value*1.0 <=0){
	    alert("使用月数不能小于等于0");
	    return false;
	}
	
	 add_productAndGeneralCampaign();
	 document.frm_Post.ProductList.value=document.frm_Post.SingleProductList.value; 
   return true;
}

function back_submit(){
	 document.frm_Post.action="batchRenewProtocol_chooseServiceAccount.do";
	 document.frm_Post.submit();
}

function frm_submit() {
   if (check_frm()) {
   	  if (document.frm_Post.ProductList.value ==''){
   	     alert("请选择要增加的产品包!");
   	  }else{
	       document.frm_Post.submit();
	    }
   }
}

function view_package_detail(strID,strName){
    window.open('list_product_package_detail.do?txtPackageID='+strID+'&txtPackageName='+strName,'','width=300,height=250,resizable=no,toolbar=no,scrollbars=yes');
  }
</script>

<form name="frm_Post" method="post" action="batchRenewProtocol_fee.do" >
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">协议客户批量续费--->选择产品包</td>
  </tr>
</table>
  <br>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
  <br>
<table align="center" width="800" border="0" cellspacing="1" cellpadding="3" class="list_bg">
    <tr class="list_head">
        <td width="10%" class="list_head">&nbsp;</td> 
        <td width="23%" class="list_head" align="center">产品包名称</td> 
	      <td width="10%" class="list_head">&nbsp;</td> 
        <td width="23%" class="list_head" align="center">产品包名称</td> 
        <td width="10%" class="list_head">&nbsp;</td> 
        <td width="23%" class="list_head" align="center">产品包名称</td> 
    </tr> 
<%  
    String txtCustomerID =request.getParameter("txtCustomerID");
    Collection packageCol  =new ArrayList();
    Collection protocolCol =Postern.getProtocolDTOCol(Integer.parseInt(txtCustomerID));
    Iterator   protocolItr =protocolCol.iterator();
    while (protocolItr.hasNext()){
        boolean checkHardFlag =false;
        ProtocolDTO protocolDto =(ProtocolDTO)protocolItr.next();
        ProductPackageDTO productPackageDto =Postern.getProductPackageByPackageID(protocolDto.getProductPackageID());
        ArrayList  pdDtoList =Postern.getProductListByProductPackageID(productPackageDto.getPackageID());
        Iterator pdDtoItr =pdDtoList.iterator();
        while (pdDtoItr.hasNext()) {
           ProductDTO pdDto =(ProductDTO)pdDtoItr.next();
           if ("H".equals(pdDto.getProductClass())){
                checkHardFlag =true;
                break;
           }
        }
        if (checkHardFlag) continue;
        packageCol.add(productPackageDto);
    }
    
    
    Iterator   packageItr =packageCol.iterator();
    while (packageItr.hasNext()){
       ProductPackageDTO productPackageDto1 =(ProductPackageDTO)packageItr.next();           
       pageContext.setAttribute("oneline",productPackageDto1);
%>
         
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
        <td align="center" ><tbl:checkbox name="listID3"  value="oneline:packageID" match="SingleProductList" multipleMatchFlag="true" /></td>  
        <td align="center" ><a href="JavaScript:view_package_detail('<tbl:write name="oneline" property="packageID" />','<tbl:write name="oneline" property="packageName" />')"><tbl:write name="oneline" property="packageName" /></a></td>                 
<%    
        if (packageItr.hasNext()){
            ProductPackageDTO productPackageDto2 =(ProductPackageDTO)packageItr.next();
            pageContext.setAttribute("oneline",productPackageDto2);
%>
        <td align="center" ><tbl:checkbox name="listID3"  value="oneline:packageID" match="SingleProductList" multipleMatchFlag="true" /></td>  
        <td align="center" ><a href="JavaScript:view_package_detail('<tbl:write name="oneline" property="packageID" />','<tbl:write name="oneline" property="packageName" />')"><tbl:write name="oneline" property="packageName" /></a></td>                 
<%      }else { %>    
       	<td align="center" >&nbsp;</td>
       	<td align="center" >&nbsp;</td>
<%      } 

        if (packageItr.hasNext()){
            ProductPackageDTO productPackageDto3 =(ProductPackageDTO)packageItr.next();
            pageContext.setAttribute("oneline",productPackageDto3);
%>
        <td align="center" ><tbl:checkbox name="listID3"  value="oneline:packageID" match="SingleProductList" multipleMatchFlag="true" /></td>  
        <td align="center" ><a href="JavaScript:view_package_detail('<tbl:write name="oneline" property="packageID" />','<tbl:write name="oneline" property="packageName" />')"><tbl:write name="oneline" property="packageName" /></a></td>                 
<%      } else { %>    
       	<td align="center" >&nbsp;</td>
       	<td align="center" >&nbsp;</td>
<%      } %>   
    </tbl:printcsstr>
<% } %>
</table>  
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0" width="80%">
 <tr>
	<td>
		<table align="center"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>请选择有效付费帐户*</td>
		    <td>&nbsp;&nbsp;</td>
		    <td class="title"><d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" width="23"/></td>
		    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		    <td>续费月数*</td>
		    <td>&nbsp;&nbsp;</td>
		    <td class="title"><input type="text" name="txtUsedMonth" value ="<tbl:writeparam name="txtUsedMonth" />" ></td>    
		  </tr>
		</table>
  </td>
 </tr>
 <tr>
 	<td>&nbsp;</td>
 </tr>
 <tr>
  <td>
  	<table align="center"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td><img src="img/button_l.gif" width="11" height="20"></td>
        <td><input name="next" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
        <td><img src="img/button_r.gif" width="22" height="20"></td>
        <td width="20" ></td>
  			<td><img src="img/button_l.gif" width="11" height="20"></td>
        <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
        <td><img src="img/button_r.gif" width="22" height="20"></td>
       </tr>
    </table>
  </td>
 </tr>
</table>
<input type="hidden" name="ProductList" value="">
<input type="hidden" name="CampaignList" value="">
<input type ="hidden" name="txtDoPost" value ="false" >
<input type="hidden" name="SingleProductList" value="<tbl:writeparam name="SingleProductList" />">
<tbl:hiddenparameters pass="txtCsiType/txtCustType/txtCustomerID/saId_indexs" />
<tbl:hiddenparameters pass="txtSoftFlag/txtActionType" />
</form>
