<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys,
                 com.dtv.oss.dto.CustomerDTO" %>

<Script language=JavaScript>

function check_frm(){
	 if(document.frmPost.txtContactPerson.value==""){
	 	alert("请填写联系人姓名！");
	 	return false;
	 }
	 if(document.frmPost.txtContactPhone.value==""){
	 	alert("请填写联系人电话！");
	 	return false;
	 }
	 if (document.frmPost.txtScheduleTime.value=="") {
		  alert("请选择预约生效时间！");
		  return false;
	 }
	if (!check_TenDate(document.frmPost.txtScheduleTime, true, "预约生效时间")) 
		return false;
	 if(!check_csiReason())
	 	return false;
	 if(!compareDateAndToday(getCurrentDate(),document.frmPost.txtScheduleTime,"预约生效时间必须在今天以后"))
  		return false;
	 if (document.frmPost.txtCustomerID.value=="") {
		  alert("没有当前客户，请先做客户查询！");
		  return false;
	 }
	 if (add_openCampaign()){
	    add_productAndGeneralCampaign();
	    document.frmPost.ProductList.value=document.frmPost.MutiProductList.value+document.frmPost.SingleProductList.value;
	    document.frmPost.CampaignList.value=document.frmPost.OpenCampaignList.value+document.frmPost.GeneralCampaignList.value; 
	 }else{
	 	  return false;
	 }
	 
	 var usedMonth =document.frmPost.txtUsedMonth;
   if (usedMonth !=null){
  	   if (check_Blank(document.frmPost.txtUsedMonth, true, "使用月数"))
	         return false;
	     if (!check_Num(document.frmPost.txtUsedMonth, true, "使用月数"))
	         return false;
	     if (document.frmPost.txtUsedMonth.value*1.0 <=0){
	   	     alert("使用月数不能小于等于0");
	   	     return false;
	     }
    }
    
	  return true;
}

function frm_submit() {
   if (check_frm()) {
	    document.frmPost.submit();
   }
}
function back_submit(url){
   document.location.href=url;
}
</script>
<%		    
    String customerID = request.getParameter( "txtCustomerID");
    String districtID=Postern.getDistrictIDByCustomerID(customerID)+"";
    CustomerDTO custDto =Postern.getCustomerByID(Integer.parseInt(customerID));
    String txtContactPerson =request.getParameter("txtContactPerson");
    if (txtContactPerson ==null || txtContactPerson.equals("")){
        txtContactPerson =custDto.getName();
    }
    String txtContactPhone =request.getParameter("txtContactPhone");
    if (txtContactPhone ==null || txtContactPhone.equals("")){
        txtContactPhone =custDto.getTelephone();
        if (txtContactPhone ==null || txtContactPhone.equals("")){
            txtContactPhone =custDto.getTelephoneMobile();
        }else{
        	 if (custDto.getTelephoneMobile()!=null && !custDto.getTelephoneMobile().equals("")){
        	    txtContactPhone =txtContactPhone +"/"+custDto.getTelephoneMobile();
        	 }
        }
    } 
    
    
%>
<form name="frmPost" method="post" action="book_product_check.do" >
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">预约新增产品录入</td>
  </tr>
</table>
<br>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

<table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
      <tr>   
      <td class="list_bg2" width="17%"  align="right">联系人*</td>
      <td class="list_bg1" width="33%"  align="left">
		  <input type="text" name="txtContactPerson" size="25" value="<%=txtContactPerson%>" >
      </td>
      <td class="list_bg2" width="17%"  align="right">联系电话*</td>
      <td class="list_bg1" width="33%"  align="left">      
		<input type="text" name="txtContactPhone" size="25" value="<%=txtContactPhone%>"  >
      </td>
    </tr>
    <tr>   
      <td class="list_bg2" width="17%"  align="right">业务帐户ID</td>
      <td class="list_bg1" width="33%"  align="left">
      <input type="text" name="txtServiceAccountID" size="25" value="<tbl:writeparam name="txtServiceAccountID"/>"  readonly> 
      
      </td>
      <td class="list_bg2" width="17%"  align="right">付费帐户*</td>
      <td class="list_bg1" width="33%"  align="left">      
		<d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" width="23"/>
      </td>
    </tr>
    <tr>   
      <td class="list_bg2" width="17%"  align="right">预约生效时间*</td>
      <td class="list_bg1"  align="left">
      <d:datetime name="txtScheduleTime" size="25" match="txtScheduleTime"  onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTime,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
      </td>  
      <td class="list_bg2" width="17%"  align="right">续费月数*</td>
      <td class="list_bg1"  align="left"><input type="text" name="txtUsedMonth" value ="<tbl:writeparam name="txtUsedMonth" />" ></td>    
    </tr>
    <tr>
    	<tbl:csiReason csiType="BP" name="txtCsiCreateReason" action="N" showType="text"  checkScricptName="check_csiReason()" tdWordStyle="list_bg2" tdControlStyle="list_bg1" forceDisplayTD="true" match="txtCsiCreateReason"  controlSize="25" />     
    </tr>
</table>

<br>
<%
   request.setAttribute("openCampaignFlag","false");
%>
    <jsp:include page="/catch/list_campaignAndproduct.jsp" />
    	
<tbl:hiddenparameters pass="txtAccount/txtID/txtCustomerID" />
<input type="hidden" name ="ProductList" value="" >
<input type="hidden" name ="CampaignList" value="" >
<input type="hidden" name="txtDistrictID" value="<%=districtID%>">
<input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_PRODUCTPG_CAMPAINPG%>" />
<tbl:hiddenparameters pass="txtCustType/txtOpenSourceType/txtCsiType" />
<tbl:generatetoken />	
</form>

<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		 <bk:canback url="query_book_account.do">
	   <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onClick="javascript:back_submit("<bk:backurl property="query_book_account.do" />")" value="上一步"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	   <td width="20" ></td>
	   </bk:canback>
	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
	   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table> 
