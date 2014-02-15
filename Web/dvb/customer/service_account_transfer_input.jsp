<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>


<form name="frmPost" method="post" action="service_account_transfer_create.do" >     
	
	
<tbl:hiddenparameters pass="txtCustomerID/txtServiceAccountID/txtAccount/txtActionType/txtTransferType/txtSAName/txtSAC/txtSAID" />

<br>
<%//如果是系统外过户
	if(CommonKeys.SA_TRANFER_TYPE_O.equals(request.getParameter("txtTransferType"))){
	
%>
	<jsp:include page="open_create_info_sa_tranfer.jsp" />
		
		
		
		
<% 	} else { %>
 <table  border="0" align="center" cellpadding="0" cellspacing="5">
	<tr>
	  <td><img src="img/list_tit.gif" width="19" height="19"></td>
	  <td class="title">选择客户和帐户</td>
	</tr>
  </table>
  

  <BR>
  
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
<tr>
  <td><img src="img/mao.gif" width="1" height="1"></td>
</tr>
</table>
<br>
<table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
<tr> 
 <td valign="middle" class="list_bg2" width="17%" ><div align="right">客户证号*</div></td>
 <td class="list_bg1" width="33%"><font size="2">
	<input type="text" name="txtNewCustomerID" size="25" maxlength="100" value="<tbl:writeparam name="txtNewCustomerID" />" class="text">
	<input name="checkbutton" type="button" class="button" value="检查" onClick="javascript:check_data()">
 </font></td>
 <td valign="middle" class="list_bg2" width="17%" ><div align="right">帐户号</div></td>
 <td class="list_bg1" width="33%"><font size="2">
	<input type="text"  readonly class="textgray" name="txtNewAccountID" size="25" maxlength="50" value="<tbl:writeparam name="txtNewAccountID" />" class="text">
 </font>
 </td>
</tr>
</TABLE>
<%}%>
<br>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
<tr>
  <td><img src="img/mao.gif" width="1" height="1"></td>
</tr>
</table>
<br>

<table align="center" border="0" cellspacing="0" cellpadding="0">
	
	
	
<tr>
   <td><img src="img/button2_r.gif" width="22" height="20"></td>
   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td>
   <td width="20" ></td>
   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
</tr>
</table>
</form> 

<Script language=JavaScript>
<!--  
function check_frm_sa()
{
	<%if(CommonKeys.SA_TRANFER_TYPE_I.equals(request.getParameter("txtTransferType"))){%>
   
  var customerID=<%=request.getParameter("txtCustomerID")%>;
  var  nCustomerID =document.frmPost.txtNewCustomerID.value;
	if(customerID==nCustomerID){
		alert("过户客户证号与本客户证号相同，不能过户给自己!");
	  return false;	
	}
   
   
   if(check_Blank(document.frmPost.txtNewCustomerID, false, "客户证号")){
	  alert("请输入客户证号定位客户!");
	  return false;	
	}
   if(check_Blank(document.frmPost.txtNewAccountID, false, "帐户号")){
	  alert("请选择客户帐户!");
	  return false;	
	}

	
	
	<%}else if(CommonKeys.SA_TRANFER_TYPE_O.equals(request.getParameter("txtTransferType"))){%>
	
	//检查新客户信息
	if(!check_frm())
		return false;
	<% } %>

	return true;
	
}

function frm_submit()
{
	if (check_frm_sa())
		document.frmPost.submit();
}

function back_submit()
{
	document.frmPost.action="service_account_transfer_view.do";
	document.frmPost.submit();
}

function check_data()
{
	
   if(check_Blank(document.frmPost.txtNewCustomerID, false, "客户证号")){
	  alert("请输入客户证号定位客户!");
	  return false;	
	}
	if(!IsNumber(document.frmPost.txtNewCustomerID.value))
	{
	  alert("客户证号必须为数字!");
	  return false;	
	}
	
	var customerID=<%=request.getParameter("txtCustomerID")%>;
  var  nCustomerID =document.frmPost.txtNewCustomerID.value;
  var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes";
     
 
	if(customerID==nCustomerID){
		alert("过户客户证号与本客户证号相同，不能过户给自己!");
	  return false;	
	}else{  
      window.open("customer_account_query.do?txtFrom=1&txtTo=10&newCustomerID="+nCustomerID,"客户帐户查询",strFeatures);
	}
}
//-->
</Script>
      