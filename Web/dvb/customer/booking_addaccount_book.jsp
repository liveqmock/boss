<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.web.util.CommonKeys"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>
<%
	String actionFlag=request.getParameter("actionFlag");
	String txtPreferedTime="" , txtContactPerson="" ,txtContactPhone="" ,txtPreferedDate=""  ;
	if(actionFlag!=null&&actionFlag.equals("addAcount")){
		
	}else{
		txtPreferedTime = (request.getParameter("txtPreferedTime")==null) ? "" : request.getParameter("txtPreferedTime");
		txtContactPerson = (request.getParameter("txtContactPerson")==null) ? "" : request.getParameter("txtContactPerson");
		txtContactPhone = (request.getParameter("txtContactPhone")==null) ? "" : request.getParameter("txtContactPhone");
		txtPreferedDate = (request.getParameter("txtPreferedDate")==null) ? "" : request.getParameter("txtPreferedDate");
	
	}
     
    String txtCustomerID = request.getParameter( "txtCustomerID");
    String txtAccount=request.getParameter( "txtAccount");
    CustomerDTO customerDTO = Postern.getCustomerByID(Integer.parseInt(txtCustomerID));
	String custName = customerDTO.getName();              //��ϵ��
	String custTelephone = customerDTO.getTelephone();    //��ϵ�绰
%>
<Script language=JavaScript>

function check_frm(){
	 if(document.frmPost.txtContactPerson.value==""){
	  	alert("����д��ϵ��������");
	  	return false;
	 }

	 if(document.frmPost.txtContactPhone.value==""){
	  	alert("����д��ϵ�绰��");
	  	return false;
	 }
	 
	 if (check_Blank(document.frmPost.txtPreferedTime, true, "ԤԼ����ʱ���"))
	    return false;
   if (check_Blank(document.frmPost.txtPreferedDate, true, "ԤԼ��������"))
  		return false;
   if (!check_TenDate(document.frmPost.txtPreferedDate, true, "ԤԼ��������")) 
        return false;
   if(!compareDateAndToday(getCurrentDate(),document.frmPost.txtPreferedDate,"ԤԼ�������ڱ����ڽ����Ժ�"))
  		return false;
   if(document.frmPost.txtAccount.value==""){
		  alert("��ѡ����Ч�ʻ���");
		  return false;
	 }
	 
	 if(!check_csiReason()) return false; 
	 
   if (add_openCampaign()){
    	 add_productAndGeneralCampaign();
	     document.frmPost.ProductList.value=document.frmPost.MutiProductList.value+document.frmPost.SingleProductList.value;
	     document.frmPost.CampaignList.value=document.frmPost.OpenCampaignList.value+document.frmPost.GeneralCampaignList.value;
	 } else{
	    return false;	
	 } 
   return true;
}

function frm_submit() {
   if (check_frm()) {
    	document.frmPost.submit();
   }
}
function frm_back(){
	document.frmPost.action= "query_book_account.do";
	document.frmPost.submit();
}
</script>
		
<form name="frmPost" method="post" action="booking_addaccount_confirm.do" >
  <table  border="0" align="center" cellpadding="0" cellspacing="10" >
    <tr>
      <td><img src="img/list_tit.gif" width="19" height="19"></td>
      <td class="title">ԤԼ������ԤԼ</td>
   </tr>
  </table>
  <br>
  <table width="780"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
  </table>
  <br>
  <table width="780"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width="17%"><div align="right">��ϵ��*</div></td>
    <td class="list_bg1" width="33%"><input type="text" name="txtContactPerson" size="25"  value="<%=custName%>"  ></td>
    <td class="list_bg2" width="17%"><div align="right">��ϵ�绰*</div></td>
    <td class="list_bg1" width="33%"><input type="text" name="txtContactPhone" size="25" maxlength="20" value="<%=custTelephone%>"  ></td>
  </tr>
  <tr>
    <td class="list_bg2" width="17%"><div align="right">ԤԼ��������*</div></td>
    <td class="list_bg1" width="33%">
    <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPreferedDate)" onblur="lostFocus(this)" type="text" name="txtPreferedDate" size="25" value="<tbl:writeparam name="txtPreferedDate" />"  />    <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedDate,'Y')" src="img/calendar.gif"  style=cursor:hand  border="0"></td>
    <td class="list_bg2" width="17%"><div align="right">ԤԼ����ʱ���*</div></td>
    <td class="list_bg1" width="33%">
    	<d:selcmn name="txtPreferedTime" mapName="SET_C_CSIPREFEREDTIME" match="txtPreferedTime" width="23" defaultFlag ="true" showAllFlag="false"/>
	  </td>
  </tr>
  <tr>
	  <td class="list_bg2" width="17%"><div align="right">��Ч�ĸ����ʻ�*</div></td>
    <td class="list_bg1" width="33%" >
	   <d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" width="23"/>
	  </td>
	  <tbl:csiReason csiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BU%>" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" showType="text" checkScricptName="check_csiReason()" 
				     	  tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" forceDisplayTD ="true" />
  </tr>
 </table>
 <br>
 <jsp:include page="/catch/list_campaignAndproduct.jsp" />

<input type="hidden" name="ProductList" value="">
<input type="hidden" name="CampaignList" value="">
<input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_BOOKINGUSER_PRODUCTPG_AND_INVOICE%>" />
<input type="hidden" name="txtCsiType" value="BU"> 
<input type="hidden" name="txtPage" value="bookingAddAccountBook">
<tbl:hiddenparameters pass="txtCustomerID/txtAccount" />

<tbl:generatetoken />
</form> 	
<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onclick="javascript:frm_back()" value="��  ��"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	   
	   <td width="20" >&nbsp;</td> 
	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td height="20">
		 <input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="��һ��">
	   </td>
	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>

	</tr>
</table>