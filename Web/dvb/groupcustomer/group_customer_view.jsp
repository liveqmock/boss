<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<Script language=javascript>
function check_form(){
	if (check_Blank(document.frmPost.txtName, true, "����"))
		return false;
	if (document.frmPost.txtCardType.value=='' && document.frmPost.txtCardID.value=='') {
    alert("������֤����Ϣ��");
    document.frmPost.txtCardType.focus();
    return false;
  }
  else if(document.frmPost.txtCardType.value=='') {
    alert("��ѡ��֤�����ͣ�");
    document.frmPost.txtCardType.focus();
    return false;
  }
  else if (document.frmPost.txtCardID.value=='') {
    alert("������֤����!");
    document.frmPost.txtCardID.focus();
    return false;
  }
  else if(document.frmPost.txtDistrict.value=='') {
    alert("����������Ϣ����Ϊ��!");
    return false;
  }
  if (document.frmPost.txtTelephone.value=='' && document.frmPost.txtTelephoneMobile.value=='') {
		alert("�̶��绰���ƶ��绰����ͬʱΪ��!");
		document.frmPost.txtTelephone.focus();
		return false;
	}
	if (check_Blank(document.frmPost.txtDetailAddress, true, "��ϸ��ַ"))
		return false;
	
  return true;
}
function edit_submit(){
  if (window.confirm('��ȷ��Ҫ�޸ĸ��û�������?')){
    if (check_form()){
	    document.frmPost.action="customer_edit_done.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
	}
}
</SCRIPT>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">���ſͻ�������Ϣ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" >
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
    pageContext.setAttribute("cust", wrap.getCustDto());
    pageContext.setAttribute("custaddr",  wrap.getAddrDto());
    
    Map markMap = Postern.getServeyResultByCustIdForRealUser(wrap.getCustDto().getCustomerID(),"T_CUSTMARKETINFO","CUSTOMERID");
    pageContext.setAttribute("serveyMarketMap",markMap);
    
%>

<input type="hidden" name="Action" value="update">
<input type="hidden" name="txtAddressID" value="<tbl:write name="custaddr" property="addressID"/>">
<input type="hidden" name="txtCustomerID" value="<tbl:write name="cust" property="customerID" />" >
<input type="hidden" name="txtCustomerType"   value="<tbl:write name="cust" property="customerType" />" >
<input type="hidden" name="txtCustomerDtLastmod"   value="<tbl:write name="cust" property="DtLastmod" />" >
<input type="hidden" name="txtAddressDtLastmod"   value="<tbl:write name="custaddr" property="DtLastmod" />" >
<input type="hidden" name="txtOpenSourceType"   value="<tbl:write name="cust" property="OpenSourceType" />" >

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width="17%" align="right">��������*</td>
    <td class="list_bg1" width="33%" align="left"><input type="text" name="txtName" size="25" maxlength="25" value="<tbl:write name="cust" property="name" />"></td>
    <td class="list_bg2" width="17%" align="right">�ʱ�*</td>
    <td class="list_bg1" width="33%" align="left"><input type="text" name="txtPostcode" size="25" maxlength="6" value="<tbl:write name="custaddr" property="Postcode" />" ></td>
  </tr>
  <tr>
    <td class="list_bg2" width="17%" align="right">�ͻ�����*</td>
    <td class="list_bg1" width="33%" align="left"><input type="text" name="txtCustomerTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="cust:customerType" />" class="textgray" readonly ></td>
    <td class="list_bg2" width="17%" align="right">��ϸ��ַ*</td>
    <td class="list_bg1" width="33%" align="left"><input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<tbl:write name="custaddr" property="DetailAddress" />"  ></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">��Դ����*</td>
    <td class="list_bg1" align="left"><input type="text" name="txtOpenSourceTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOPENSOURCETYPE" match="cust:OpenSourceType" />" class="textgray" readonly ></td>
    <td class="list_bg2" align="right">��Դ����ID</td>
    <td class="list_bg1" align="left"><input type="text" name="txtOpenSourceIDShow" size="25"  value="<tbl:writedate name="cust" property="OpenSourceTypeID" includeTime="true" />" class="textgray" readonly ></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right" width="17%">��������*</td>
    <td class="list_bg1" align="left"  width="83%" colspan="3">
      <input type="hidden" name="txtDistrict" value="<tbl:write name="custaddr" property="districtID" />">
	    <input type="text" name="txtDistrictDesc" size="25" readonly value="<tbl:WriteDistrictInfo name="custaddr" property="DistrictID" />" class="text">
      <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('leaf','txtDistrict','txtDistrictDesc')">
    </td>
  </tr>
  <tr> 
	    <td colspan="4" class="import_tit" align="center"><font size="3">��������Ϣ</font></td>
  </tr>
	<tr>
		<td class="list_bg2" width="17%" align="right">����*</td>
    <td class="list_bg1" width="33%" align="left"><input type="text" name="txtAgentName" size="25" maxlength="25" value="<tbl:write name="cust" property="agentName" />"></td>
    <td class="list_bg2" width="17%" align="right">֤������</td>
    <td class="list_bg1" width="33%" align="left"><d:selcmn name="txtCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="cust:cardType" width="23" /></td>
  </tr>
  <tr>
  	<td class="list_bg2" width="17%" align="right" >֤����*</td>
	  <td class="list_bg1" width="33%" align="left">
	      <input type="text" name="txtCardID" size="25" maxlength="25" value="<tbl:write name="cust" property="cardID" />"  >
	  </font></td>
	  <td class="list_bg2" width="17%" align="right" >�̶��绰*</td>
	  <td class="list_bg1" width="33%" align="left"><font size="2">
	      <input type="text" name="txtTelephone" size="25" maxlength="20" value="<tbl:write name="cust" property="telephone" />"  >
	  </font></td>
  </tr>
  <tr>
  	<td class="list_bg2" width="17%" align="right" >�ƶ��绰 </td>
	  <td class="list_bg1" width="83%" colspan="3"><font size="2">
	     <input type="text" name="txtTelephoneMobile" size="25" maxlength="20" value="<tbl:write name="cust" property="telephoneMobile" />"  >
	  </font></td>
  </tr>
</table>
 </lgc:bloop> 
 <input type="hidden" name="func_flag" value="5">
</form>
<BR>  
<table align="center"  border="0" cellspacing="0" cellpadding="0">
   	<tr>
			 <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
       <td><input name="Submit" type="button" class="button" value="�� ��" onclick="javascript:edit_submit()"></td>
       <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
       <td width="20" ></td>
    </tr>
</table>
