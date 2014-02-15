<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<%

String strTransferType=request.getParameter("transferType");
boolean isVicinalTransfer=false;
if(strTransferType!=null&&strTransferType.equals("vicinal")){
	isVicinalTransfer=true;
}

String install = "";

if(request.getParameter("txtIsInstall") != null)
	install = request.getParameter("txtIsInstall");
else
	install = CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL;//�԰�װ

String catvFieldName =Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID");
int    catvIdLength =Postern.getSystemsettingIntValue("SET_V_CATVIDLENGTH",10);
		
%>
<Script language=javascript>
<!--
var selfInstall = "<%=CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_INSTALL%>";
function changePrefed(){
   if (document.frmPost.txtIsInstall.value ==selfInstall){
      document.all("prefered").style.display ="";
   } else{
		document.frmPost.txtPreferedDate.value ="";
		document.frmPost.txtPreferedTime.value ="";
		document.all("prefered").style.display ="none";
   }
 
}
function func_txtCustomerSerialNo(){
    <lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTSERIALNO" >
    <lgc:showcontentbysetting settingName="SET_V_CHECK_CUSTSERIALNO" >
    if (check_Blank(document.frmPost.txtCustomerSerialNo, true, "�ͻ�������"))
		return false;
	</lgc:showcontentbysetting>
    </lgc:showcontentbysetting>
    
    return true;
}
    
    
function check_txtNewCardType(){
	if(document.frmPost.txtNewCardType!=null&&document.frmPost.txtNewCardID!=null){
    if(document.frmPost.txtNewCardType.value=='' && document.frmPost.txtNewCardID.value==''){
        alert("������֤����Ϣ��");
        document.frmPost.txtNewCardType.focus();
        return false;
    }
    else if(document.frmPost.txtNewCardType.value==''){
        alert("��ѡ��֤�����ͣ�");
        document.frmPost.txtNewCardType.focus();
        return false;
    }
    else if(document.frmPost.txtNewCardID.value==''){
        alert("������֤���ţ�");
        document.frmPost.txtNewCardID.focus();
        return false;
    }
    }
    return true;
}
<!-- �ʱ��ʽУ��,wangpeng@20080304 -->
function checkPostcode(){
	if (check_Blank(document.frmPost.txtNewPostcode, true, "�ʱ�"))
		return false;
	return true;	
}

function check_txtNewName(){
	if(check_Blank(document.frmPost.txtNewName, true, "����"))
		return false; 	
	return true;  	
}
function check_txtDetailAddress(){
	if(document.frmPost.txtDetailAddress.value==''){
    	alert("�°�װ��ַ����Ϊ�գ�");
      document.frmPost.txtDetailAddress.focus();
      return false;
    }	
	return true;		  	  
}
function check_txtNewDistrict(){
	if (document.frmPost.transferType.value=='strange'&&check_Blank(document.frmPost.txtNewDistrict, true, "��������"))
			return false;
	return true;	    
}
function check_txtNewBirthday(){
	if(document.frmPost.txtNewBirthday.value != ''){
        if(!check_TenDate(document.frmPost.txtNewBirthday, true, "��������"))
        return false;
    }
	return true;		  
}
function check_txtNewTelephone(){
	if(document.frmPost.txtNewTelephone!=null&&document.frmPost.txtNewTelephoneMobile!=null){
	if(document.frmPost.txtNewTelephone.value=='' && document.frmPost.txtNewTelephoneMobile.value==''){
		  alert("�̶��绰���ƶ��绰����ͬʱΪ�գ�");
		  document.frmPost.txtNewTelephone.focus();
		  return false;
	}
	}
	return true;		  
}
function check_txtCustAccount(){
	if(document.frmPost.txtCustAccount.value==''){
        alert("����ѡ�����ĸ���Ч�ʻ����ѣ�");
        document.frmPost.txtCustAccount.focus();
        return false;
    }
	return true;		  
}
function func_Bidimconfig(){
	if (!check_Bidimconfig()) return false;
    return true;
}


function create_submit(){
	if(check_form()&&func_txtCustomerSerialNo&&check_Bidimconfig()){
	   if(document.frmPost.transferType.value=='strange'){
       document.frmPost.action="customer_strange_transfer_fee.do";
     }else{
       document.frmPost.action="customer_vicinal_transfer_fee.do";
   	 }
     document.frmPost.submit();
  }
}

function check_submit()
{
    if(check_addressOrcatv())
    {
        var  catvId =document.frmPost.txtCatvID.value;
        var  detailAddress =document.frmPost.txtDetailAddress.value;
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
    <td class="title">¼�������Ϣ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
	<input type="hidden" name="txtOpenSourceType" value="<tbl:writeparam name="txtOpenSourceType"/>">
    <input type="hidden" name="txtCustomerDTlastmod" value="<tbl:writeparam name="txtCustomerDTlastmod"/>">
    <input type="hidden" name="txtAddressDTlastmod" value="<tbl:writeparam name="txtAddressDTlastmod"/>">
    <input type="hidden" name="txtAddressID" value="<tbl:writeparam name="txtAddressID"/>">
    <input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID" />">
    <input type="hidden" name="txtDistrict" value="<tbl:writeparam name="txtDistrict"/>">
    <input type="hidden" name="txtCustomerStatus" value="<tbl:writeparam name="txtCustomerStatus"/>">
    <input type="hidden" name="txtCustomerType" value="<tbl:writeparam name="txtCustomerType"/>">
    <tbl:hiddenparameters pass="transferType" />

    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
	        <td colspan="4" class="import_tit" align="center"><font size="3">ԭ�ͻ���Ϣ</font></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">����</div></td>
            <td class="list_bg1"><input type="text" name="txtName" size="25"  value="<tbl:writeparam name="txtName"/>" class="textgray" readonly ></td>
            <td class="list_bg2"><div align="right">�ͻ�״̬</div></td>
            <td class="list_bg1"><input type="text" name="txtCustomerStatusName" size="25" value="<d:getcmnname typeName="SET_C_CUSTOMERSTATUS" match="txtCustomerStatus"/>" class="textgray" readonly ></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">�ͻ�����</div></td>
            <td class="list_bg1"><input type="text" name="txtCustomerTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="txtCustomerType"/>" class="textgray" readonly ></td>
            <td class="list_bg2"><div align="right">��������</div></td>
            <td class="list_bg1">
            	<input type="text" name="txtDistrictName" size="25"  value="<tbl:WriteDistrictInfo property="txtDistrict" />" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">��װ��ַ</div></td>
            <td class="list_bg1" colspan="3"><input type="text" name="txtOldDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtOldDetailAddress"/>" class="textgray" readonly ></td>
            <!-- ��������Ҫȥ���ʱ�
            <td class="list_bg2"><div align="right">�ʱ�*</div></td>
            <td class="list_bg1"><input type="text" name="txtPostcode" size="25" value="<tbl:writeparam name="txtPostcode"/>" class="textgray" readonly ></td>
            -->
            
        </tr>
        <tr>
	        <td colspan="4" class="import_tit" align="center"><font size="3">�¿ͻ���Ϣ</font></td>
        </tr>
        <lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTSERIALNO" >
    	<tr>
    		<td valign="middle" class="list_bg2" align="right" colspan="1">
    			�ͻ�������<lgc:showcontentbysetting settingName="SET_V_CHECK_CUSTSERIALNO" >*</lgc:showcontentbysetting>
    		</td>
    		<td class="list_bg1" colspan="3">
    			<font size="2"> 
    			    <input type="text" name="txtCustomerSerialNo" size="25" maxlength="20" value="<tbl:writeparam name="txtCustomerSerialNo" />" class="text"> 
    			</font>
    		</td>
    	</tr>
    	</lgc:showcontentbysetting>

        <%if(isVicinalTransfer){    //21Ϊ���ع���	%>
        <d:DynamicForm formName="VicinalTransferCustomer" functionName="check_form()" column="4">
		
        <d:DynamicFormElement elementName="txtNewName" functionName="check_txtNewName()">
            <td class="list_bg2"><div align="right">����*</div></td>
            <td class="list_bg1"><input type="text" name="txtNewName" size="25"  value="<tbl:writeparam name="txtNewName"/>" class="text"></td>
        </d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtCustAccount" functionName="check_txtCustAccount()">
        	<td class="list_bg2"><div align="right">��Ч�ʻ�*</div></td>
            <td class="list_bg1"><d:selAccByCustId name="txtCustAccount" mapName="self" canDirect="true"  match="txtCustAccount" empty="false" width="23" style="width:185px;"/></td>
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewCardType" functionName="check_txtNewCardType()">
            <td class="list_bg2"><div align="right">֤������*</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtNewCardType" width="23" defaultFlag ="true" showAllFlag="false" /></td>
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewCardID" >
            <td class="list_bg2"><div align="right">֤������*</div></td>
            <td class="list_bg1"><input type="text" name="txtNewCardID" size="25"  value="<tbl:writeparam name="txtNewCardID"/>" class="text"></td>
		</d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewTelephone" functionName="check_txtNewTelephone()"> 
            <td class="list_bg2"><div align="right">�̶��绰</div></td>
            <td class="list_bg1"><input type="text" name="txtNewTelephone" size="25"  value="<tbl:writeparam name="txtNewTelephone"/>" class="text"></td>    
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewTelephoneMobile" >  
            <td class="list_bg2"><div align="right">�ƶ��绰</div></td>
            <td class="list_bg1"><input type="text" name="txtNewTelephoneMobile" size="25"  value="<tbl:writeparam name="txtNewTelephoneMobile"/>" class="text"></td>
        </d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewDistrict" functionName="check_txtNewDistrict()">
            <td class="list_bg2"><div align="right">��������</div></td>
            <td class="list_bg1">
     	        <input type="hidden" name="txtNewDistrict" value="<tbl:writeparam name="txtDistrict" />">
				<input type="text" name="txtNewDistrictDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrict" />" class="textgray"></td>
		</d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtDetailAddress" functionName="check_txtDetailAddress()">
            <td class="list_bg2"><div align="right">��װ��ַ</div></td>
            <td class="list_bg1"><input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtOldDetailAddress"/>" readonly class="textgray"></td>
        </d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewPostcode" functionName="checkPostcode()">
            <td class="list_bg2"><div align="right">�ʱ�*</div></td>
            <td class="list_bg1"><input type="text" name="txtNewPostcode" size="25" readonly value="<tbl:writeparam name="txtPostcode"/>" class="textgray"></td>
		</d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtCatvID" >
            <td class="list_bg2"><div align="right">&nbsp;<%=catvFieldName%></div></td>
            <td class="list_bg1"><font size="2">
                <input type="text" name="txtCatvID" readonly size="25" maxlength="50" value="<tbl:writeparam name="txtCatvID"/>" class="textgray">
            </font>
            </td>
		</d:DynamicFormElement>  

        <d:DynamicFormElement elementName="txtNewGender" >
        	<td class="list_bg2"><div align="right">�Ա�</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewGender" mapName="SET_C_CUSTOMERGENDER" match="txtNewGender" width="23" defaultFlag ="true" showAllFlag="false"/></td>
        </d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtSocialSecCardID" >
			    <td valign="middle" class="list_bg2" align="right" >�籣�����</td>
			    <td class="list_bg1"><font size="2">
			      <input type="text" name="txtSocialSecCardID" size="25" maxlength="25" value="<tbl:writeparam name="txtSocialSecCardID" />" class="text">
			    </font></td>
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtEmail" >
			    <td valign="middle" class="list_bg2" align="right" >EMAIL��ַ</td>
			    <td class="list_bg1"><font size="2">
			      <input type="text" name="txtEmail" size="25" maxlength="100" value="<tbl:writeparam name="txtEmail" />"  class="text">
			     </font></td>
		</d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtFaxNumber" >
			    <td valign="middle" class="list_bg2" align="right" >����</td>
			    <td class="list_bg1"><font size="2">
			      <input type="text" name="txtFaxNumber" size="25" maxlength="100" value="<tbl:writeparam name="txtFaxNumber" />" class="text">
			    </font></td>
		</d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewBirthday" functionName="check_txtNewBirthday()">
		    <td class="list_bg2"><div align="right">��������</div></td>
		    <td class="list_bg1">
		      <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtNewBirthday)" onblur="lostFocus(this)" type="text" name="txtNewBirthday" size="25" value="<tbl:writeparam name="txtNewBirthday"/>" class="text">		      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtNewBirthday,'N')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
		    </td>
		</d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtNationality" >
		     <td valign="middle" class="list_bg2" align="right" >����</td>
				    <td class="list_bg1"><font size="2">
				      <d:selcmn name="txtNationality" mapName="SET_C_NATIONALITY" match="txtNationality" width="23" defaultFlag ="true" showAllFlag="false"/>
				    </font></td>
		</d:DynamicFormElement>
	
	    <d:DynamicFormElement elementName="txtContractNo">
	    	<td class="list_bg2" align="right">Э����</td>
            <td class="list_bg1" align="left" >
              <input type="text" name="txtContractNo" size="25"  value="<tbl:writeparam name="txtContractNo"/>" class="text">
            </td> 
        </d:DynamicFormElement>    
        
        <d:DynamicFormElement elementName="txtLoginID">
	    	<td class="list_bg2" align="right">��½ID</td>
            <td class="list_bg1" align="left">
              <input type="text" name="txtLoginID" size="25"  value="<tbl:writeparam name="txtLoginID"/>" class="text">
            </td> 
        </d:DynamicFormElement>
        
        <d:DynamicFormElement elementName="txtLoginPwd">
	    	<td class="list_bg2" align="right">��½����</td>
            <td class="list_bg1" align="left">
              <input type="text" name="txtLoginPwd" size="25"  value="<tbl:writeparam name="txtLoginPwd"/>" class="text">
            </td> 
        </d:DynamicFormElement> 
        
        </d:DynamicForm>
        <%}else{        //22Ϊ��ع���  %>
        
        <d:DynamicForm formName="StrangeTransferCustomer" functionName="check_form()" column="4">
		
        <d:DynamicFormElement elementName="txtNewName" functionName="check_txtNewName()">
            <td class="list_bg2"><div align="right">����*</div></td>
            <td class="list_bg1"><input type="text" name="txtNewName" size="25"  value="<tbl:writeparam name="txtNewName"/>" class="text"></td>
        </d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtCustAccount" functionName="check_txtCustAccount()">
        	<td class="list_bg2"><div align="right">��Ч�ʻ�*</div></td>
            <td class="list_bg1"><d:selAccByCustId name="txtCustAccount" mapName="self" canDirect="true"  match="txtCustAccount" empty="false" width="23" style="width:185px;"/></td>
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewCardType" functionName="check_txtNewCardType()">
            <td class="list_bg2"><div align="right">֤������*</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtNewCardType" width="23" defaultFlag ="true" showAllFlag="false" /></td>
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewCardID">
            <td class="list_bg2"><div align="right">֤������*</div></td>
            <td class="list_bg1"><input type="text" name="txtNewCardID" size="25"  value="<tbl:writeparam name="txtNewCardID"/>" class="text"></td>
		</d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewTelephone" functionName="check_txtNewTelephone()"> 
            <td class="list_bg2"><div align="right">�̶��绰</div></td>
            <td class="list_bg1"><input type="text" name="txtNewTelephone" size="25"  value="<tbl:writeparam name="txtNewTelephone"/>" class="text"></td>    
        </d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewTelephoneMobile">
            <td class="list_bg2"><div align="right">�ƶ��绰</div></td>
            <td class="list_bg1"><input type="text" name="txtNewTelephoneMobile" size="25"  value="<tbl:writeparam name="txtNewTelephoneMobile"/>" class="text"></td>
        </d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewDistrict" functionName="check_txtNewDistrict()">
            <td class="list_bg2"><div align="right">��������*</div></td>
            <td class="list_bg1">
     	        <input type="hidden" name="txtNewDistrict" value="<tbl:writeparam name="txtNewDistrict" />">
	       			<input type="text" name="txtNewDistrictDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtNewDistrict" />" class="text">
              <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all;leaf','txtNewDistrict','txtNewDistrictDesc')">
            </td>
		</d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtDetailAddress" functionName="check_txtDetailAddress()">
            <td class="list_bg2"><div align="right">�°�װ��ַ*</div></td>
            <td class="list_bg1"><input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtDetailAddress"/>" class="text"></td>
        </d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewPostcode" functionName="checkPostcode()">
            <td class="list_bg2"><div align="right">�ʱ�*</div></td>
            <td class="list_bg1"><input type="text" name="txtNewPostcode" maxlength='6' size="25" value="<tbl:writeparam name="txtNewPostcode"/>" class="text"></td>
        </d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtCatvID" >    
            <td class="list_bg2"><div align="right">&nbsp;<%=catvFieldName%></div></td>
            <td class="list_bg1"><font size="2">
                <input type="text" name="txtCatvID" size="25" maxlength="50" value="<tbl:writeparam name="txtCatvID"/>" class="text">
            </font>
                <input name="checkbutton" type="button" class="button" value="���" onClick="javascript:check_submit()">
            </td>
        </d:DynamicFormElement>    

        <d:DynamicFormElement elementName="txtNewGender" >
        	<td class="list_bg2"><div align="right">�Ա�</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewGender" mapName="SET_C_CUSTOMERGENDER" match="txtNewGender" width="23" defaultFlag ="true" showAllFlag="false"/></td>
        </d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtSocialSecCardID" >
			    <td valign="middle" class="list_bg2" align="right" >�籣�����</td>
			    <td class="list_bg1"><font size="2">
			      <input type="text" name="txtSocialSecCardID" size="25" maxlength="25" value="<tbl:writeparam name="txtSocialSecCardID" />" class="text">
			    </font></td>
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtEmail" >
			    <td valign="middle" class="list_bg2" align="right" >EMAIL��ַ</td>
			    <td class="list_bg1"><font size="2">
			      <input type="text" name="txtEmail" size="25" maxlength="100" value="<tbl:writeparam name="txtEmail" />"  class="text">
			     </font></td>
		</d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtFaxNumber" >
			    <td valign="middle" class="list_bg2" align="right" >����</td>
			    <td class="list_bg1"><font size="2">
			      <input type="text" name="txtFaxNumber" size="25" maxlength="100" value="<tbl:writeparam name="txtFaxNumber" />" class="text">
			    </font></td>
		</d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewBirthday" functionName="check_txtNewBirthday()">
		    <td class="list_bg2"><div align="right">��������</div></td>
		    <td class="list_bg1">
		      <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtNewBirthday)" onblur="lostFocus(this)" type="text" name="txtNewBirthday" size="25" value="<tbl:writeparam name="txtNewBirthday"/>" class="text">		      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtNewBirthday,'N')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
		    </td>
		</d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtNationality" >
		     <td valign="middle" class="list_bg2" align="right" >����</td>
				    <td class="list_bg1"><font size="2">
				      <d:selcmn name="txtNationality" mapName="SET_C_NATIONALITY" match="txtNationality" width="23" defaultFlag ="true" showAllFlag="false"/>
				    </font></td>
		</d:DynamicFormElement>
	
	    <d:DynamicFormElement elementName="txtContractNo">
	    	<td class="list_bg2" align="right">Э����</td>
            <td class="list_bg1" align="left">
              <input type="text" name="txtContractNo" size="25"  value="<tbl:writeparam name="txtContractNo"/>" class="text">
            </td> 
        </d:DynamicFormElement>
        
        <d:DynamicFormElement elementName="txtLoginID">
	    	<td class="list_bg2" align="right">��½ID</td>
            <td class="list_bg1" align="left">
              <input type="text" name="txtLoginID" size="25"  value="<tbl:writeparam name="txtLoginID"/>" class="text">
            </td> 
        </d:DynamicFormElement>
        
        <d:DynamicFormElement elementName="txtLoginPwd">
	    	<td class="list_bg2" align="right">��½����</td>
            <td class="list_bg1" align="left">
              <input type="text" name="txtLoginPwd" size="25"  value="<tbl:writeparam name="txtLoginPwd"/>" class="text">
            </td> 
        </d:DynamicFormElement> 

        </d:DynamicForm>
         <%}%>
         
         

        
         <tbl:dynamicservey serveyType="M"  showType="text" tdWordStyle="list_bg2" tdControlStyle="list_bg1"  controlSize="25" checkScricptName ="check_Bidimconfig()" />
    </table>
    <br>
		<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
		  <tr>
		    <td><img src="img/mao.gif" width="1" height="1"></td>
		  </tr>
		</table>
    <br>
	      <table  border="0" cellspacing="0" cellpadding="0" align="center">
	         <tr>
		     <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		     <td><input name="Submit" type="button" class="button" value="��һ��" onclick="javascript:create_submit()"></td>
		     <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		 </tr>
	      </table>

</form>
</td>
</tr>
</table>