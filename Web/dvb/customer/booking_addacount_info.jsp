<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import ="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<%
	String timeFlag="SET_C_CSIPREFEREDTIME";
	Map mapPreferedTime = Postern.getAllPreferedTime(timeFlag);
	pageContext.setAttribute("AllPREFEREDTIME", mapPreferedTime);

%>
<Script language=JavaScript>

function check_frm(){
	if(document.frmPost.txtContactPerson.value=="")
	{
		alert("请填写联系人姓名！");
		return false;
	}

	if(document.frmPost.txtContactPhone.value=="")
	{
		alert("请填写联系电话！");
		return false;
	}
	 if (check_Blank(document.frmPost.txtPreferedTime, true, "预约上门时间段"))
	    return false;
   if (check_Blank(document.frmPost.txtPreferedDate, true, "预约上门日期"))
  		return false;
   if (!check_TenDate(document.frmPost.txtPreferedDate, true, "预约上门日期")) 
      return false;
   if(!compareDateAndToday(getCurrentDate(),document.frmPost.txtPreferedDate,"预约上门日期必须在今天以后"))
  		return false;
  		
   if (!check_csiReason()) return false;
	return true;
}

function frm_update(){
   if (check_frm()){  
   	  if (updateProduct()){
	       document.frmPost.action = "book_select_phone_number.screen?txtPage=bookingAddAccountBookModify";
		     document.frmPost.submit();
		  }
   }
}

function updateProduct(){
		 if (add_openCampaign()){
	      add_productAndGeneralCampaign();
	      document.frmPost.ProductList.value=document.frmPost.MutiProductList.value+document.frmPost.SingleProductList.value;
	      document.frmPost.CampaignList.value=document.frmPost.OpenCampaignList.value+document.frmPost.GeneralCampaignList.value;    
	    } else{
	      return false;
	    }
	   return true;
}

function frm_back(){
	document.frmPost.action= "query_book_account.do";
	document.frmPost.submit();
}

function frm_cancle(action){
	if(confirm("是否要取消该受理单？")){
		document.frmPost.txtActionType.value=action;
		document.frmPost.action= "cancle_book_account.do";
		document.frmPost.submit();
	}
}

</script>
		
<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%" align="center"> 
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>

  <form name="frmPost" method="post" action="booking_addacount_update.do" >
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">预约增机的预约信息</td>
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
    <td class="list_bg2" width="17%"><div align="right">联系人*</div></td>
    <td class="list_bg1" width="33%"><input type="text" name="txtContactPerson" size="25"  value="<tbl:writeparam name="txtContactPerson"  />" ></td>
    <td class="list_bg2" width="17%"><div align="right">联系电话*</div></td>
    <td class="list_bg1" width="33%"><input type="text" name="txtContactPhone" size="25" maxlength="20" value="<tbl:writeparam name="txtContactPhone" />"  ></td>
  </tr>
  <tr>
    <td class="list_bg2" width="17%"><div align="right">预约上门日期*</div></td>
    <td class="list_bg1" width="33%">
    <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPreferedDate)" onblur="lostFocus(this)" type="text" name="txtPreferedDate" size="25" value="<tbl:writeparam name="txtPreferedDate" />"  />    <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedDate,'Y')" src="img/calendar.gif"  style=cursor:hand  border="0"></td>
    <td class="list_bg2" width="17%"><div align="right">预约上门时间段*</div></td>
    <td class="list_bg1" width="33%">
    <tbl:select name="txtPreferedTime" set="AllPREFEREDTIME" match="txtPreferedTime" width="23"   />
	</td>
  </tr>
  <tr>
	<td class="list_bg2" width="17%"><div align="right">有效的付费帐户*</div></td>
    <td class="list_bg1" width="33%" >
	      <d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" width="23"/>
	  </td>
	  <tbl:csiReason csiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BU%>" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" showType="text"  checkScricptName="check_csiReason()" tdWordStyle="list_bg2" tdControlStyle="list_bg1" forceDisplayTD="true" controlSize="25" />
  </tr>
</table>
<br>
  <jsp:include page="/catch/list_campaignAndproduct.jsp" />
	
<input type="hidden" name="txtID" value="<tbl:writeparam name="txtID" />" > 
<input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID" />" > 
<input type="hidden" name="txtCsiDtLastMod" value="<tbl:writeparam name="txtCsiDtLastMod"  />" >
<input type="hidden" name="ProductList" value="">
<input type="hidden" name="CampaignList" value="">
<input type="hidden" name="txtActionType" value="<%=CommonKeys.OPEN_BOOKINGUSER_SERVICE_ACCOUNT_FORMODIFY%>" />
<input type="hidden" name="func_flag" value="116">
<input type="hidden" name="actionFlag" value="addAcount">
<input type="hidden" name="txtCsiType" value="BU">
<tbl:hiddenparameters pass="txtServiceCodeList/txtStatus" />
<tbl:hiddenparameters pass="txtCustType/txtOpenSourceType/txtCsiType" />

</form>

<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>

	   <td><img src="img/button2_r.gif" width="22" height="20"></td>  
       <td background="img/button_bg.gif">
	   <a href="<bk:backurl property="query_book_account.do" />" class="btn12">返  回</a></td>
	   <td><img src="img/button2_l.gif" width="13" height="20"></td>
	   <td width="20" >&nbsp;</td> 
	   <% 
	   String txtStatus =request.getParameter("txtStatus");
	   if (txtStatus.equals("W")) { %>
	   
	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td height="20">
		 <input name="next" type="button" class="button" onClick="javascript:frm_update()" value="修  改">
	   </td>
	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	   <td width="20" >&nbsp;</td> 
	   	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td height="20">
		 <input name="next" type="button" class="button" onClick="javascript:frm_cancle('<%=CommonKeys.OPEN_BOOKINGUSER_SERVICE_ACCOUNT_FORCANCLE %>')" value="取  消">
	   </td>
	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	   <%} %>
	</tr>
</table>