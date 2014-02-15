<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.CommonKeys"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<Script language=JavaScript>

function check_frm(){
	 
	if(document.frm_Post.txtAccount.value=="") {
		 alert("请选择有效帐户！");
		 return false;
	}
	
   if (add_openCampaign()){
	    add_productAndGeneralCampaign();
	    document.frm_Post.ProductList.value=document.frm_Post.MutiProductList.value+document.frm_Post.SingleProductList.value;
	    document.frm_Post.CampaignList.value=document.frm_Post.OpenCampaignList.value+document.frm_Post.GeneralCampaignList.value;  
	 }else{
	    return false;	
	 }
	 
	 var usedMonth =document.frm_Post.txtUsedMonth;
   if (usedMonth !=null){
  	   if (check_Blank(document.frm_Post.txtUsedMonth, true, "使用月数"))
	         return false;
	     if (!check_Num(document.frm_Post.txtUsedMonth, true, "使用月数"))
	         return false;
	     if (document.frm_Post.txtUsedMonth.value*1.0 <=0){
	   	     alert("使用月数不能小于等于0");
	   	     return false;
	     }
    }
	 
   return true;
}

function back_submit(){
	 document.frm_Post.action="batchAddPackage_chooseServiceAccount.do";
	 document.frm_Post.submit();
}

function frm_submit() {
   if (check_frm()) {
   	  if (document.frm_Post.ProductList.value =='' && document.frm_Post.CampaignList.value ==''){
   	     alert("请选择要增加的产品包!");
   	  }else{
	       document.frm_Post.submit();
	    }
   }
}
</script>

<form name="frm_Post" method="post" action="batchAddPackage_fee.do" >
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">批量增加产品包--->选择产品包</td>
  </tr>
</table>
  <br>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
  <br>
<jsp:include page="/catch/list_campaignAndproduct.jsp" />
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0" width="80%">
 <tr>
	<td>
		<table align="center"  border="0" cellspacing="0" cellpadding="0">
			<tr><td>请选择有效付费帐户*</td>
		      <td>&nbsp;&nbsp;</td>
		      <td class="title"><d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" width="23"/></td>
		      <%
		        String txtCustomerID =request.getParameter("txtCustomerID");
	          Collection protocolList =Postern.getProtocolDTOCol(Integer.parseInt(txtCustomerID));
	          if (protocolList !=null && !protocolList.isEmpty()){
	        %> 
		      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		      <td>续费月数*</td>
		      <td>&nbsp;&nbsp;</td>
		      <td class="title"><input type="text" name="txtUsedMonth" value ="<tbl:writeparam name="txtUsedMonth" />" ></td>    
		      <%
		        }
		      %>
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
<tbl:hiddenparameters pass="txtCsiType/txtCustomerID/saId_indexs" />
<tbl:hiddenparameters pass="txtCustType/txtSoftFlag" />
</form>
