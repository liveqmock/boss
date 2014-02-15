<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.web.util.CommonKeys"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<Script language=JavaScript>

function check_frm(){
	  if (document.frm_Post.txtCustomerID.value=="") {
	   	 alert("没有当前客户，请先做客户查询！");
		   return false;
	  }

	  if (document.frm_Post.txtAccount.value=="") {
		   alert("请选择有效帐户！");
		   return false;
	  }
	  if(!check_csiReason())
	 	return false;
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

function frm_submit() {
   if (check_frm()) {      
    	 document.frm_Post.submit();
   }
}
</script>
<%
String saID = request.getParameter("txtServiceAccountID");
String customerID = request.getParameter( "txtCustomerID");
if(customerID == null || customerID == "")
	out.print("没有当前客户，请先做客户查询！");
else
{
%>
<form name="frm_Post" method="post" action="cp_add_product_choice.do" >
  <table  border="0" align="center" cellpadding="0" cellspacing="10" >
    <tr>
      <td><img src="img/list_tit.gif" width="19" height="19"></td>
      <td class="title">新增产品</td>
    </tr>
  </table>
  <br>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	  <tr>
	    <td><img src="img/mao.gif" width="1" height="1"></td>
	  </tr>
  </table>
  <br>

<%
   request.setAttribute("openCampaignFlag","true");
%>
  <jsp:include page="/catch/list_campaignAndproduct.jsp" />
  	
<BR><BR>
<table align="center" border="0" cellspacing="0" cellpadding="0" width="80%">
	<tr>
	<td><table align="center"  border="0" cellspacing="0" cellpadding="0">
		<tr><td>请选择有效帐户*</td>
		<td>&nbsp;&nbsp;</td>
		<td class="title"><d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccountID" empty="false" width="23"/>
	  </td>
	  <%
	     Collection protocolList =Postern.getProtocolDTOCol(Integer.parseInt(customerID));
	     if (protocolList !=null && !protocolList.isEmpty()){
	  %>
	  <td>&nbsp;&nbsp;</td>
	  <td>续费月数*</td>
		<td>&nbsp;&nbsp;</td>
		<td class="title"><input type="text" name="txtUsedMonth" value ="<tbl:writeparam name="txtUsedMonth" />" ></td>    
	  <%
	     }
	  %>
	  </tr></table>
	</td>
	  <td><table align="center"  border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td>&nbsp;&nbsp;</td>
		<tbl:csiReason csiType="PN" name="txtCsiCreateReason" action="N" showType="text"  checkScricptName="check_csiReason()" forceDisplayTD="true" match="txtCsiCreateReason"  controlSize="25" />
		<td>&nbsp;&nbsp;</td>
		</tr></table>
	</td>
	<td><table align="center"  border="0" cellspacing="0" cellpadding="0"><tr><td><img src="img/button_l.gif" width="11" height="20"></td>
	   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
	   <td><img src="img/button_r.gif" width="22" height="20"></td></tr></table></td>
	</tr>
</table>
<input type="hidden" name="txtServiceAccountID" value="<%=saID%>">
<input type="hidden" name="txtCustomerID" value="<%=customerID%>">
<input type="hidden" name="ProductList" value="">
<input type="hidden" name="CampaignList" value="">
<input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_PRODUCTPG_AND_INVOICE%>" />
<tbl:hiddenparameters pass="txtCustType/txtOpenSourceType/txtCsiType" />
<tbl:generatetoken />
</form>
<%
}%>