<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<Script language=JavaScript>

function check_frm(){
	 if (document.frm_Post.txtCustomerID.value=="") {
		  alert("没有当前客户，请先做客户查询！");
		  return false;
	 }
   if (add_openCampaign()){
	    add_productAndGeneralCampaign();
	    document.frm_Post.ProductList.value=document.frm_Post.MutiProductList.value+document.frm_Post.SingleProductList.value;
	    document.frm_Post.CampaignList.value=document.frm_Post.OpenCampaignList.value+document.frm_Post.GeneralCampaignList.value; 
	 } else{
	 	  return false;
	 }
	 return true;
}

function frm_submit() {
   if (check_frm()) {
	    document.frm_Post.submit();
   }
}
function back_submit(){
	 url="<bk:backurl property="query_book_account.do" />";
   document.location.href=url;
}
</script>
<%		    
    String customerID = request.getParameter( "txtCustomerID");
    String districtID=Postern.getDistrictIDByCustomerID(customerID)+"";
    
%>
<form name="frm_Post" method="post" action="book_create_customer_account.do" >
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
    	
<tbl:hiddenparameters pass="txtAccount/txtID/txtCustomerID/txtServiceCodeList/phoneNo/itemID" />
<tbl:hiddenparameters pass="txtCsiType/txtCustType/txtOpenSourceType/txtCsiCreateReason" />
<input type="hidden" name ="ProductList" value="" >
<input type="hidden" name ="CampaignList" value="" >
<input type="hidden" name="txtDistrictID" value="<%=districtID%>">
<input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_PRODUCTPG_AND_INVOICE%>" />
<tbl:generatetoken />
</form>

<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	   <td width="20" ></td>
	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
	   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table> 
