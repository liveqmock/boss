<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<Script language=JavaScript>
<!--
	function back_submit(){
		document.frmPost.action='cust_bundle_prepayment_method.do';
		document.frmPost.submit();
	}
	
	function frm_next(csiPayOption){
	   document.frmPost.txtcsiPayOption.value =csiPayOption;
	   if (check_fee()){
	      document.frmPost.action="cust_bundle_prepayment_payment_pay.screen";
	   }else{
	 	    document.frmPost.action="cust_bundle_prepayment_confirm.screen";
	 	    document.frmPost.txtDoPost.value="true";
	   }
	   document.frmPost.submit();
  }
  
  function frm_finish(csiPayOption){
	   document.frmPost.txtcsiPayOption.value =csiPayOption;
	   document.frmPost.action="cust_bundle_prepayment_confirm.screen";
	   document.frmPost.txtDoPost.value="true";
	   document.frmPost.submit();
  }
	
//-->
</Script>
<%
	int ccid=WebUtil.StringToInt(request.getParameter("txtCcID"));
	String rfBillingCycleFlag=request.getParameter("txtMethod");
	Map methodMap=Postern.getBundlePaymentMethodByCustomerCampaign(ccid);
	String methodName=(String)methodMap.get(rfBillingCycleFlag);
	if(methodName==null)methodName="";
%>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户套餐预付费用-支付费用</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<form name="frmPost" method="post" action="" >
	<tbl:hiddenparameters pass="txtMethod" />
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
        <tr>
          <td class="list_bg2" align="right" width="17%">客户套餐ID</td>
          <td class="list_bg1"  align="left" width="33%">
          <input type="text" name="txtCcID" size="25"  value="<tbl:writeparam name="txtCcID"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right" width="17%">套餐名称</td>
          <td class="list_bg1"  align="left" width="33%">
             <input type="text" name="txtCampaignName" size="25"  value="<tbl:writeparam name="txtCampaignName"/>" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">账户ID</td>
          <td class="list_bg1"  align="left"  >
          <input type="text" name="txtAccoutID" size="25"  value="<tbl:writeparam name="txtAccoutID"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">业务帐户ID</td>
          <td class="list_bg1"  align="left" >
          <input type="text" name="txtServiceAccoutID" size="25"  value="<tbl:writeparam name="txtServiceAccoutID"/>" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">套餐续费方式</td>
          <td class="list_bg1"  align="left" colspan="3">
             <input type="text" name="txtMethodDesc" size="25"  value="<%=methodName%>" class="textgray" readonly >
          </td>
        </tr>
                 
      </table>   
	    <input type="hidden" name="txtCustomerID"  value="<tbl:writeparam name="txtCustomerID"/>" >
      <BR>  
   	 <tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BDP%>" acctshowFlag ="true"  />		 
   
<br>       
<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	  <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">上一步</a></td> 
	  <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>  
	  
    <jsp:include page = "/fee/common_control.jsp"/>
	</tr>
</table>
<input type="hidden" name="txtActionType"  value="BundlePrePayment" >
<input type="hidden" name="txtDoPost"  value="false" >
<input type="hidden" name="txtcsiPayOption" >
<tbl:generatetoken />          
</form>

