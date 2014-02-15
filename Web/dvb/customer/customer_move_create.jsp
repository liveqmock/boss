<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="java.util.*, com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<%
  String catvFieldName =Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID");
  int    catvIdLength =Postern.getSystemsettingIntValue("SET_V_CATVIDLENGTH",10);
%>
<Script language=javascript>
<!--


function check_form()
{
    <lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTSERIALNO" >
    <lgc:showcontentbysetting settingName="SET_V_CHECK_CUSTSERIALNO" >
    if (check_Blank(document.frmPost.txtCustomerSerialNo, true, "�ͻ�������"))
		return false;
	</lgc:showcontentbysetting>
    </lgc:showcontentbysetting> 
   if (check_Blank(document.frmPost.txtPostcode,true,"���ʱ�"))
       return false;
       
   if (check_Blank(document.frmPost.txtDetailAddress,true,"���°�װ��ַ"))
       return false;
       
   if (check_Blank(document.frmPost.txtNewDistrict, true, "����������"))
       return false;

   if (check_Blank(document.frmPost.txtCustAccount,true,"��Ч�ʻ�"))
       return false;
       
   if (document.frmPost.txtTelephone.value=='' && document.frmPost.txtTelephoneMobile.value=='') {
		alert("�̶��绰���ƶ��绰����ͬʱΪ��!");
		document.frmPost.txtTelephone.focus();
		return false;
   }

   return true;
}

function create_submit()
{
    document.frmPost.action="customer_move_fee.do";
    if(check_form()) document.frmPost.submit();
}

function check_submit()
{
    if(check_addressOrcatv())
    {
        var  detailAddress =document.frmPost.txtDetailAddress.value;
        var  catvId =document.frmPost.txtCatvID.value;
        var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes";
        window.open("catv_terminal_query.do?txtFrom=1&txtTo=10&catvId="+catvId+"&detailAddress="+detailAddress,"<%=catvFieldName%>",strFeatures);
    }
}

function check_addressOrcatv()
{
   if (check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>")&& check_Blank(document.frmPost.txtDetailAddress, false, "�°�װ��ַ")){
	alert("�°�װ��ַ��<%=catvFieldName%>������������һ����ѯ����");		
	return false;
   }
   if(!check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>")){
       if(document.frmPost.txtCatvID.value.length!=<%=catvIdLength%>){
	  alert("<%=catvFieldName%>��������<%=catvIdLength%>λ");
	  return false;	
	}
    }
    if(!check_Blank(document.frmPost.txtDetailAddress, false, "�°�װ��ַ")){
	if(document.frmPost.txtDetailAddress.value.length<=3){
	   alert("�°�װ��ַ��������4λ");
	   return false;		
	}
    }		
    return true;
}

//-->
</SCRIPT>

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">¼��Ǩ����Ϣ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
    <input type="hidden" name="txtCustomerDTlastmod" value="<tbl:writeparam name="txtCustomerDTlastmod"/>">
    <input type="hidden" name="txtAddressDTlastmod" value="<tbl:writeparam name="txtAddressDTlastmod"/>">
    <input type="hidden" name="txtAddressID" value="<tbl:writeparam name="txtAddressID"/>">
    <input type="hidden" name="txtCustomerType" value="<tbl:writeparam name="txtCustomerType"/>" >
    <input type="hidden" name="txtCardType" value="<tbl:writeparam name="txtCardType"/>">
    <input type="hidden" name="txtGender" value="<tbl:writeparam name="txtGender"/>">
    <input type="hidden" name="txtDistrict" value="<tbl:writeparam name="txtDistrict"/>">
    <input type="hidden" name="txtCustomerStatus" value="<tbl:writeparam name="txtCustomerStatus"/>">
    <input type="hidden" name="txtOpenSourceType" value="<tbl:writeparam name="txtOpenSourceType"/>">
 
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">

        <tr>
            <td class="list_bg2" align="right" width="17%">�ͻ�֤��</td>
            <td class="list_bg1" align="left" width="33%">
                <input type="text" name="txtCustomerID" size="25"  value="<tbl:writeparam name="txtCustomerID"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right" width="17%">&nbsp;<%=catvFieldName%></td>
            <td class="list_bg1" align="left" width="33%">
                <input type="text" name="txtOldCatvID" size="25" maxlength="10" value="<tbl:writeparam name="txtOldCatvID"/>" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">�ͻ�����</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtCustomerTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="txtCustomerType"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">�ͻ�״̬</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtCustomerStatusName" size="25" value="<d:getcmnname typeName="SET_C_CUSTOMERSTATUS" match="txtCustomerStatus"/>" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">��Դ����</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtOpenSourceTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOPENSOURCETYPE" match="txtOpenSourceType"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">��������</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtDtCreateShow" size="25"  value="<tbl:writeparam name="txtDtCreateShow" />" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">����</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtName" size="25"  value="<tbl:writeparam name="txtName"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">�Ա�</td>
            <td class="list_bg1" align="left">
                <d:selcmn name="txtGender" mapName="SET_C_CUSTOMERGENDER" match="txtGender" width="23" selDisabled="true" style="background-color:#E1E1E1"/>
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">֤������</td>
            <td class="list_bg1" align="left">
                <d:selcmn name="txtCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtCardType" width="23" selDisabled="true" style="background-color:#E1E1E1" />
             </td>
            <td class="list_bg2" align="right">֤������</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtCardID" size="25"  value="<tbl:writeparam name="txtCardID"/>" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">��������</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtBirthday" size="25" value="<tbl:writeparam name="txtBirthday"/>" class="textgray" readonly >
             </td>
            <td class="list_bg2" align="right">ԭ��������</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtDistrictName" size="25" value="<tbl:WriteDistrictInfo property="txtDistrict" />" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">�ɰ�װ��ַ</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtOldDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtOldDetailAddress"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">���ʱ�*</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtPostcode" maxlength='6' size="25" value="<tbl:writeparam name="txtPostcode"/>">
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">�¹̶��绰</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtTelephone" size="25"  value="<tbl:writeparam name="txtTelephone"/>" class="text">
            </td>
            <td class="list_bg2" align="right">���ƶ��绰</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtTelephoneMobile" size="25"  value="<tbl:writeparam name="txtTelephoneMobile"/>" class="text">
            </td>
        </tr>        
        <tr>
            <td class="list_bg2" align="right">�°�װ��ַ*</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtDetailAddress" size="25"  value="<tbl:writeparam name="txtDetailAddress"/>">
            </td>
            <td class="list_bg2" align="right">����������*</td>
            <td class="list_bg1" align="left">
              <input type="hidden" name="txtNewDistrict" value="<tbl:writeparam name="txtNewDistrict" />">
	      <input type="text" name="txtNewDistrictDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtNewDistrict" />" class="text">
              <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all;leaf','txtNewDistrict','txtNewDistrictDesc')">
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">&nbsp;<%=catvFieldName%></td>
            <td class="list_bg1" align="left"><font size="2">
                <input type="text" name="txtCatvID" size="25" maxlength="50" value="<tbl:writeparam name="txtCatvID"/>" class="text">
            </font><input name="checkbutton" type="button" class="button" value="���" onClick="javascript:check_submit()">
            </td>
            <td class="list_bg2" align="right">��Ч�ʻ�*</div></td>
            <td class="list_bg1" align="left">
               <d:selAccByCustId name="txtCustAccount" mapName="self" canDirect="true"  match="txtAccountID" empty="false" width="23" style="width:185px;"/>
            </td>
        </tr>
       
       </tr>
        <lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTSERIALNO" >
	<tr>
		<td valign="middle" class="list_bg2" align="right" colspan="1">
			�ͻ�������<lgc:showcontentbysetting settingName="SET_V_CHECK_CUSTSERIALNO" >*</lgc:showcontentbysetting>
		</td>
		<td  class="list_bg1" colspan="3">
			<font size="2"> 
			    <input type="text" name="txtCustomerSerialNo" size="25" maxlength="20" value="<tbl:writeparam name="txtCustomerSerialNo" />" class="text"> 
			</font>
		</td>
	</tr>	
	</lgc:showcontentbysetting>
	<tr>
	<td class="list_bg2" align="right">Э����</td>
    	<td class="list_bg1" align="left" colspan="3">
      	<input type="text" name="txtContractNo" size="25"  value="<tbl:writeparam name="txtContractNo"/>" class="text" >
    	</td>
    </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" >
	    <tr align="center">
	        <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		        <tr>
			        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			        <td><input name="Submit" type="button" class="button" value="��һ��" onclick="javascript:create_submit()"></td>
			        <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		        </tr>
	        </table></td>
	    </tr>
    </table>
</form>
</td>
</tr>
</table>