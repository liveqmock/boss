<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>


<form name="frmPost" method="post" action="service_account_transfer_create.do" >     
	
	
<tbl:hiddenparameters pass="txtCustomerID/txtServiceAccountID/txtAccount/txtActionType/txtTransferType/txtSAName/txtSAC/txtSAID" />

<br>
<%//�����ϵͳ�����
	if(CommonKeys.SA_TRANFER_TYPE_O.equals(request.getParameter("txtTransferType"))){
	
%>
	<jsp:include page="open_create_info_sa_tranfer.jsp" />
		
		
		
		
<% 	} else { %>
 <table  border="0" align="center" cellpadding="0" cellspacing="5">
	<tr>
	  <td><img src="img/list_tit.gif" width="19" height="19"></td>
	  <td class="title">ѡ��ͻ����ʻ�</td>
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
 <td valign="middle" class="list_bg2" width="17%" ><div align="right">�ͻ�֤��*</div></td>
 <td class="list_bg1" width="33%"><font size="2">
	<input type="text" name="txtNewCustomerID" size="25" maxlength="100" value="<tbl:writeparam name="txtNewCustomerID" />" class="text">
	<input name="checkbutton" type="button" class="button" value="���" onClick="javascript:check_data()">
 </font></td>
 <td valign="middle" class="list_bg2" width="17%" ><div align="right">�ʻ���</div></td>
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
   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="��һ��"></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td>
   <td width="20" ></td>
   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="��һ��"></td>
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
		alert("�����ͻ�֤���뱾�ͻ�֤����ͬ�����ܹ������Լ�!");
	  return false;	
	}
   
   
   if(check_Blank(document.frmPost.txtNewCustomerID, false, "�ͻ�֤��")){
	  alert("������ͻ�֤�Ŷ�λ�ͻ�!");
	  return false;	
	}
   if(check_Blank(document.frmPost.txtNewAccountID, false, "�ʻ���")){
	  alert("��ѡ��ͻ��ʻ�!");
	  return false;	
	}

	
	
	<%}else if(CommonKeys.SA_TRANFER_TYPE_O.equals(request.getParameter("txtTransferType"))){%>
	
	//����¿ͻ���Ϣ
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
	
   if(check_Blank(document.frmPost.txtNewCustomerID, false, "�ͻ�֤��")){
	  alert("������ͻ�֤�Ŷ�λ�ͻ�!");
	  return false;	
	}
	if(!IsNumber(document.frmPost.txtNewCustomerID.value))
	{
	  alert("�ͻ�֤�ű���Ϊ����!");
	  return false;	
	}
	
	var customerID=<%=request.getParameter("txtCustomerID")%>;
  var  nCustomerID =document.frmPost.txtNewCustomerID.value;
  var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes";
     
 
	if(customerID==nCustomerID){
		alert("�����ͻ�֤���뱾�ͻ�֤����ͬ�����ܹ������Լ�!");
	  return false;	
	}else{  
      window.open("customer_account_query.do?txtFrom=1&txtTo=10&newCustomerID="+nCustomerID,"�ͻ��ʻ���ѯ",strFeatures);
	}
}
//-->
</Script>
      