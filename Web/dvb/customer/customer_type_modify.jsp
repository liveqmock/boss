<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%
	String customerID = request.getParameter("txtCustomerID");
	CustomerDTO customerDTO = Postern.getCustomerByID(WebUtil.StringToInt(customerID));
	AddressDTO addressDTO = Postern.getAddressDtoByID(customerDTO.getAddressID());
	
	pageContext.setAttribute("cust", customerDTO);
	pageContext.setAttribute("addr", addressDTO);
%>




<Script language=JavaScript>
<!--
function frm_submit(){
	if (check_Blank(document.frmPost.txtCustomerType, true, "客户类型"))
	{	
  	return false;
  }	
  if(document.frmPost.txtCustomerType.value==document.frmPost.txtOldCustomerType.value){
  	alert("修改前后的客户类型一样,请重新选择");
  	return ;
  }
  document.frmPost.submit();
}

function colse_click(){
	window.close();	
}
//-->
</Script>


<form name="frmPost" method="post" action="customer_type_modify.do" >
    <input type="hidden" name="Action" value="update">
    <input type="hidden" name="txtOldCustomerType" value="<tbl:writeparam name="txtCustomerType"/>" />
    <input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID"/>" />
    <input type="hidden" name="txtCustomerDtLastmod"   value="<tbl:writeparam name="txtCustomerDtLastmod"/>" >
    
    <input type="hidden" name="txtGender" value="<tbl:write name='cust' property='gender' />">
		<input type="hidden" name="txtName" value="<tbl:write name='cust' property='name' />">
		<input type="hidden" name="txtBirthday"  value="<tbl:write name='cust' property='birthday' />">
		<input type="hidden" name="txtCardType" value="<tbl:write name='cust' property='cardType' />">
		<input type="hidden" name="txtCardID" value="<tbl:write name='cust' property='cardID' />">
		<input type="hidden" name="txtSocialSecCardID" value="<tbl:write name='cust' property='socialSecCardID' />">
		<input type="hidden" name="txtEmail" value="<tbl:write name='cust' property='email' />">
		<input type="hidden" name="txtNationality" value="<tbl:write name='cust' property='nationality' />">
		<input type="hidden" name="txtFax" value="<tbl:write name='cust' property='fax' />">
		<input type="hidden" name="txtLoginID" value="<tbl:write name='cust' property='loginID' />">
		<input type="hidden" name="txtLoginPwd" value="<tbl:write name='cust' property='loginPwd' />">
		<input type="hidden" name="txtTelephone" value="<tbl:write name='cust' property='telephone' />">
		<input type="hidden" name="txtTelephoneMobile" value="<tbl:write name='cust' property='telephoneMobile' />">
		<input type="hidden" name="txtCurrentPoints" value="<tbl:write name='cust' property='currentAccumulatePoint' />">
		<input type="hidden" name="txtTotalPoints" value="<tbl:write name='cust' property='totalAccumulatePoint' />">
		<input type="hidden" name="txtAgentName" value="<tbl:write name='cust' property='agentName' />">
		<!--
		<input type="hidden" name="txtCustomerType" value="<tbl:write name='cust' property='customerType' />">
		-->
		<input type="hidden" name="txtContractNo" value="<tbl:write name='cust' property='contractNo' />">
		<input type="hidden" name="txtComments" value="<tbl:write name='cust' property='comments' />">
		<input type="hidden" name="txtOpenSourceType" value="<tbl:write name='cust' property='openSourceType' />">
		<input type="hidden" name="txtCatvID" value="<tbl:write name='cust' property='catvID' />">
		<input type="hidden" name="txtAddressID" value="<tbl:write name='addr' property='addressID' />">
		<input type="hidden" name="txtPostcode" value="<tbl:write name='addr' property='postcode' />">
		<input type="hidden" name="txtDetailAddress" value="<tbl:write name='addr' property='detailAddress' />">
		<input type="hidden" name="txtDistrict" value="<tbl:write name='addr' property='districtID' />">
		<input type="hidden" name="txtAddressDtLastmod" value="<tbl:write name='addr' property='dtLastmod' />">
				
				
		
    <table  border="0" align="center" cellpadding="0" cellspacing="5">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">修改客户类型</td>
      </tr>
    </table>
    <br>
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
    </table>
    <br>
 
    <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
       <tr>
	        <td valign="middle" class="list_bg2" align="right" width="34%">客户类型*</td>
	        <td class="list_bg1" width="66%">
	          <d:selcmn name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="23" judgeGradeFlag="true"/>
	        </td>
       </tr>

	  </table>  

		<BR>  
     <table align="center"  border="0" cellspacing="0" cellpadding="0">
      
        <tr>  
          <td width="20" ></td>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
	        <td><input name="aaa" type="button" class="button" onClick="colse_click()" value="关  闭"></td>           
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
          <td width="20" ></td>
          <td><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="bbb" type="button" class="button" onClick="frm_submit()" value="修  改"></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>  
        </tr>
     </table>
</form>      
		 


