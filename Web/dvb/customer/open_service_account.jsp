<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.web.util.CommonKeys"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<Script language=JavaScript>

function check_frm(){
	 if(document.frm_Post.txtCustomerID.value=="") {
		 alert("没有当前客户，请先做客户查询！");
		 return false;
	}

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
	 
	 if(!check_csiReason()) return false;   
   return true;
}

function frm_submit() {
   if (check_frm()) {
	    document.frm_Post.submit();
   }
}
</script>
<%		
String customerID = request.getParameter( "txtCustomerID");
String districtID=Postern.getDistrictIDByCustomerID(customerID)+"";

if(customerID == null || customerID == "")
	out.print("没有当前客户，请先做客户查询！");
else
{
%>

<form name="frm_Post" method="post" action="open_service_account_product.do" >
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">创建业务帐户</td>
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
		  </tr>
		</table>
  </td>
  <tbl:csiReason csiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UO%>" name="txtCsiCreateReason" action="N" showType="text"  checkScricptName="check_csiReason()" forceDisplayTD="true" match="txtCsiCreateReason"  controlSize="25" />
  <td>
  	<table align="center"  border="0" cellspacing="0" cellpadding="0">
  		<tr><td><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>
       </tr>
    </table>
  </td>
 </tr>
</table>
<input type="hidden" name="txtCustomerID" value="<%=customerID%>">
<input type="hidden" name="txtDistrictID" value="<%=districtID%>">
<input type="hidden" name="ProductList" value="">
<input type="hidden" name="CampaignList" value="">
<input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_PRODUCTPG_AND_INVOICE%>" />
<input type="hidden" name="confirm_post" value="false" >
<tbl:hiddenparameters pass="txtCustType/txtOpenSourceType/txtCsiType" />
<tbl:generatetoken />	
</form>
<%
}
%>