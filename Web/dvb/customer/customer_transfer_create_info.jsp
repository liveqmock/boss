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
	install = CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL;//自安装

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
    if (check_Blank(document.frmPost.txtCustomerSerialNo, true, "客户条形码"))
		return false;
	</lgc:showcontentbysetting>
    </lgc:showcontentbysetting>
    
    return true;
}
    
    
function check_txtNewCardType(){
	if(document.frmPost.txtNewCardType!=null&&document.frmPost.txtNewCardID!=null){
    if(document.frmPost.txtNewCardType.value=='' && document.frmPost.txtNewCardID.value==''){
        alert("请输入证件信息！");
        document.frmPost.txtNewCardType.focus();
        return false;
    }
    else if(document.frmPost.txtNewCardType.value==''){
        alert("请选择证件类型！");
        document.frmPost.txtNewCardType.focus();
        return false;
    }
    else if(document.frmPost.txtNewCardID.value==''){
        alert("请输入证件号！");
        document.frmPost.txtNewCardID.focus();
        return false;
    }
    }
    return true;
}
<!-- 邮编格式校验,wangpeng@20080304 -->
function checkPostcode(){
	if (check_Blank(document.frmPost.txtNewPostcode, true, "邮编"))
		return false;
	return true;	
}

function check_txtNewName(){
	if(check_Blank(document.frmPost.txtNewName, true, "姓名"))
		return false; 	
	return true;  	
}
function check_txtDetailAddress(){
	if(document.frmPost.txtDetailAddress.value==''){
    	alert("新安装地址不能为空！");
      document.frmPost.txtDetailAddress.focus();
      return false;
    }	
	return true;		  	  
}
function check_txtNewDistrict(){
	if (document.frmPost.transferType.value=='strange'&&check_Blank(document.frmPost.txtNewDistrict, true, "所在区域"))
			return false;
	return true;	    
}
function check_txtNewBirthday(){
	if(document.frmPost.txtNewBirthday.value != ''){
        if(!check_TenDate(document.frmPost.txtNewBirthday, true, "出生日期"))
        return false;
    }
	return true;		  
}
function check_txtNewTelephone(){
	if(document.frmPost.txtNewTelephone!=null&&document.frmPost.txtNewTelephoneMobile!=null){
	if(document.frmPost.txtNewTelephone.value=='' && document.frmPost.txtNewTelephoneMobile.value==''){
		  alert("固定电话和移动电话不能同时为空！");
		  document.frmPost.txtNewTelephone.focus();
		  return false;
	}
	}
	return true;		  
}
function check_txtCustAccount(){
	if(document.frmPost.txtCustAccount.value==''){
        alert("必须选择在哪个有效帐户付费！");
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
   if (check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>")&& check_Blank(document.frmPost.txtDetailAddress, false, "新安装地址")){
	alert("新安装地址和<%=catvFieldName%>，请输入至少一个查询条件");		
	return false;
   }
   if(!check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>")){
       if(document.frmPost.txtCatvID.value.length!=<%=catvIdLength%>){
	  alert("<%=catvFieldName%>必须输入<%=catvIdLength%>位");
	  return false;	
	}
    }
    if(!check_Blank(document.frmPost.txtDetailAddress, false, "新安装地址")){
	if(document.frmPost.txtDetailAddress.value.length<=3){
	   alert("新安装地址必须至少4位");
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
    <td class="title">录入过户信息</td>
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
	        <td colspan="4" class="import_tit" align="center"><font size="3">原客户信息</font></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">姓名</div></td>
            <td class="list_bg1"><input type="text" name="txtName" size="25"  value="<tbl:writeparam name="txtName"/>" class="textgray" readonly ></td>
            <td class="list_bg2"><div align="right">客户状态</div></td>
            <td class="list_bg1"><input type="text" name="txtCustomerStatusName" size="25" value="<d:getcmnname typeName="SET_C_CUSTOMERSTATUS" match="txtCustomerStatus"/>" class="textgray" readonly ></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">客户类型</div></td>
            <td class="list_bg1"><input type="text" name="txtCustomerTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="txtCustomerType"/>" class="textgray" readonly ></td>
            <td class="list_bg2"><div align="right">所在区域</div></td>
            <td class="list_bg1">
            	<input type="text" name="txtDistrictName" size="25"  value="<tbl:WriteDistrictInfo property="txtDistrict" />" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">安装地址</div></td>
            <td class="list_bg1" colspan="3"><input type="text" name="txtOldDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtOldDetailAddress"/>" class="textgray" readonly ></td>
            <!-- 因丽江需要去掉邮编
            <td class="list_bg2"><div align="right">邮编*</div></td>
            <td class="list_bg1"><input type="text" name="txtPostcode" size="25" value="<tbl:writeparam name="txtPostcode"/>" class="textgray" readonly ></td>
            -->
            
        </tr>
        <tr>
	        <td colspan="4" class="import_tit" align="center"><font size="3">新客户信息</font></td>
        </tr>
        <lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTSERIALNO" >
    	<tr>
    		<td valign="middle" class="list_bg2" align="right" colspan="1">
    			客户条形码<lgc:showcontentbysetting settingName="SET_V_CHECK_CUSTSERIALNO" >*</lgc:showcontentbysetting>
    		</td>
    		<td class="list_bg1" colspan="3">
    			<font size="2"> 
    			    <input type="text" name="txtCustomerSerialNo" size="25" maxlength="20" value="<tbl:writeparam name="txtCustomerSerialNo" />" class="text"> 
    			</font>
    		</td>
    	</tr>
    	</lgc:showcontentbysetting>

        <%if(isVicinalTransfer){    //21为本地过户	%>
        <d:DynamicForm formName="VicinalTransferCustomer" functionName="check_form()" column="4">
		
        <d:DynamicFormElement elementName="txtNewName" functionName="check_txtNewName()">
            <td class="list_bg2"><div align="right">姓名*</div></td>
            <td class="list_bg1"><input type="text" name="txtNewName" size="25"  value="<tbl:writeparam name="txtNewName"/>" class="text"></td>
        </d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtCustAccount" functionName="check_txtCustAccount()">
        	<td class="list_bg2"><div align="right">有效帐户*</div></td>
            <td class="list_bg1"><d:selAccByCustId name="txtCustAccount" mapName="self" canDirect="true"  match="txtCustAccount" empty="false" width="23" style="width:185px;"/></td>
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewCardType" functionName="check_txtNewCardType()">
            <td class="list_bg2"><div align="right">证件类型*</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtNewCardType" width="23" defaultFlag ="true" showAllFlag="false" /></td>
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewCardID" >
            <td class="list_bg2"><div align="right">证件号码*</div></td>
            <td class="list_bg1"><input type="text" name="txtNewCardID" size="25"  value="<tbl:writeparam name="txtNewCardID"/>" class="text"></td>
		</d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewTelephone" functionName="check_txtNewTelephone()"> 
            <td class="list_bg2"><div align="right">固定电话</div></td>
            <td class="list_bg1"><input type="text" name="txtNewTelephone" size="25"  value="<tbl:writeparam name="txtNewTelephone"/>" class="text"></td>    
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewTelephoneMobile" >  
            <td class="list_bg2"><div align="right">移动电话</div></td>
            <td class="list_bg1"><input type="text" name="txtNewTelephoneMobile" size="25"  value="<tbl:writeparam name="txtNewTelephoneMobile"/>" class="text"></td>
        </d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewDistrict" functionName="check_txtNewDistrict()">
            <td class="list_bg2"><div align="right">所在区域</div></td>
            <td class="list_bg1">
     	        <input type="hidden" name="txtNewDistrict" value="<tbl:writeparam name="txtDistrict" />">
				<input type="text" name="txtNewDistrictDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrict" />" class="textgray"></td>
		</d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtDetailAddress" functionName="check_txtDetailAddress()">
            <td class="list_bg2"><div align="right">安装地址</div></td>
            <td class="list_bg1"><input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtOldDetailAddress"/>" readonly class="textgray"></td>
        </d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewPostcode" functionName="checkPostcode()">
            <td class="list_bg2"><div align="right">邮编*</div></td>
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
        	<td class="list_bg2"><div align="right">性别</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewGender" mapName="SET_C_CUSTOMERGENDER" match="txtNewGender" width="23" defaultFlag ="true" showAllFlag="false"/></td>
        </d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtSocialSecCardID" >
			    <td valign="middle" class="list_bg2" align="right" >社保卡编号</td>
			    <td class="list_bg1"><font size="2">
			      <input type="text" name="txtSocialSecCardID" size="25" maxlength="25" value="<tbl:writeparam name="txtSocialSecCardID" />" class="text">
			    </font></td>
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtEmail" >
			    <td valign="middle" class="list_bg2" align="right" >EMAIL地址</td>
			    <td class="list_bg1"><font size="2">
			      <input type="text" name="txtEmail" size="25" maxlength="100" value="<tbl:writeparam name="txtEmail" />"  class="text">
			     </font></td>
		</d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtFaxNumber" >
			    <td valign="middle" class="list_bg2" align="right" >传真</td>
			    <td class="list_bg1"><font size="2">
			      <input type="text" name="txtFaxNumber" size="25" maxlength="100" value="<tbl:writeparam name="txtFaxNumber" />" class="text">
			    </font></td>
		</d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewBirthday" functionName="check_txtNewBirthday()">
		    <td class="list_bg2"><div align="right">出生日期</div></td>
		    <td class="list_bg1">
		      <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtNewBirthday)" onblur="lostFocus(this)" type="text" name="txtNewBirthday" size="25" value="<tbl:writeparam name="txtNewBirthday"/>" class="text">		      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtNewBirthday,'N')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
		    </td>
		</d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtNationality" >
		     <td valign="middle" class="list_bg2" align="right" >国籍</td>
				    <td class="list_bg1"><font size="2">
				      <d:selcmn name="txtNationality" mapName="SET_C_NATIONALITY" match="txtNationality" width="23" defaultFlag ="true" showAllFlag="false"/>
				    </font></td>
		</d:DynamicFormElement>
	
	    <d:DynamicFormElement elementName="txtContractNo">
	    	<td class="list_bg2" align="right">协议编号</td>
            <td class="list_bg1" align="left" >
              <input type="text" name="txtContractNo" size="25"  value="<tbl:writeparam name="txtContractNo"/>" class="text">
            </td> 
        </d:DynamicFormElement>    
        
        <d:DynamicFormElement elementName="txtLoginID">
	    	<td class="list_bg2" align="right">登陆ID</td>
            <td class="list_bg1" align="left">
              <input type="text" name="txtLoginID" size="25"  value="<tbl:writeparam name="txtLoginID"/>" class="text">
            </td> 
        </d:DynamicFormElement>
        
        <d:DynamicFormElement elementName="txtLoginPwd">
	    	<td class="list_bg2" align="right">登陆密码</td>
            <td class="list_bg1" align="left">
              <input type="text" name="txtLoginPwd" size="25"  value="<tbl:writeparam name="txtLoginPwd"/>" class="text">
            </td> 
        </d:DynamicFormElement> 
        
        </d:DynamicForm>
        <%}else{        //22为异地过户  %>
        
        <d:DynamicForm formName="StrangeTransferCustomer" functionName="check_form()" column="4">
		
        <d:DynamicFormElement elementName="txtNewName" functionName="check_txtNewName()">
            <td class="list_bg2"><div align="right">姓名*</div></td>
            <td class="list_bg1"><input type="text" name="txtNewName" size="25"  value="<tbl:writeparam name="txtNewName"/>" class="text"></td>
        </d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtCustAccount" functionName="check_txtCustAccount()">
        	<td class="list_bg2"><div align="right">有效帐户*</div></td>
            <td class="list_bg1"><d:selAccByCustId name="txtCustAccount" mapName="self" canDirect="true"  match="txtCustAccount" empty="false" width="23" style="width:185px;"/></td>
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewCardType" functionName="check_txtNewCardType()">
            <td class="list_bg2"><div align="right">证件类型*</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtNewCardType" width="23" defaultFlag ="true" showAllFlag="false" /></td>
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewCardID">
            <td class="list_bg2"><div align="right">证件号码*</div></td>
            <td class="list_bg1"><input type="text" name="txtNewCardID" size="25"  value="<tbl:writeparam name="txtNewCardID"/>" class="text"></td>
		</d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewTelephone" functionName="check_txtNewTelephone()"> 
            <td class="list_bg2"><div align="right">固定电话</div></td>
            <td class="list_bg1"><input type="text" name="txtNewTelephone" size="25"  value="<tbl:writeparam name="txtNewTelephone"/>" class="text"></td>    
        </d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewTelephoneMobile">
            <td class="list_bg2"><div align="right">移动电话</div></td>
            <td class="list_bg1"><input type="text" name="txtNewTelephoneMobile" size="25"  value="<tbl:writeparam name="txtNewTelephoneMobile"/>" class="text"></td>
        </d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtNewDistrict" functionName="check_txtNewDistrict()">
            <td class="list_bg2"><div align="right">所在区域*</div></td>
            <td class="list_bg1">
     	        <input type="hidden" name="txtNewDistrict" value="<tbl:writeparam name="txtNewDistrict" />">
	       			<input type="text" name="txtNewDistrictDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtNewDistrict" />" class="text">
              <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all;leaf','txtNewDistrict','txtNewDistrictDesc')">
            </td>
		</d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtDetailAddress" functionName="check_txtDetailAddress()">
            <td class="list_bg2"><div align="right">新安装地址*</div></td>
            <td class="list_bg1"><input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtDetailAddress"/>" class="text"></td>
        </d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewPostcode" functionName="checkPostcode()">
            <td class="list_bg2"><div align="right">邮编*</div></td>
            <td class="list_bg1"><input type="text" name="txtNewPostcode" maxlength='6' size="25" value="<tbl:writeparam name="txtNewPostcode"/>" class="text"></td>
        </d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtCatvID" >    
            <td class="list_bg2"><div align="right">&nbsp;<%=catvFieldName%></div></td>
            <td class="list_bg1"><font size="2">
                <input type="text" name="txtCatvID" size="25" maxlength="50" value="<tbl:writeparam name="txtCatvID"/>" class="text">
            </font>
                <input name="checkbutton" type="button" class="button" value="检查" onClick="javascript:check_submit()">
            </td>
        </d:DynamicFormElement>    

        <d:DynamicFormElement elementName="txtNewGender" >
        	<td class="list_bg2"><div align="right">性别</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewGender" mapName="SET_C_CUSTOMERGENDER" match="txtNewGender" width="23" defaultFlag ="true" showAllFlag="false"/></td>
        </d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtSocialSecCardID" >
			    <td valign="middle" class="list_bg2" align="right" >社保卡编号</td>
			    <td class="list_bg1"><font size="2">
			      <input type="text" name="txtSocialSecCardID" size="25" maxlength="25" value="<tbl:writeparam name="txtSocialSecCardID" />" class="text">
			    </font></td>
		</d:DynamicFormElement>

		<d:DynamicFormElement elementName="txtEmail" >
			    <td valign="middle" class="list_bg2" align="right" >EMAIL地址</td>
			    <td class="list_bg1"><font size="2">
			      <input type="text" name="txtEmail" size="25" maxlength="100" value="<tbl:writeparam name="txtEmail" />"  class="text">
			     </font></td>
		</d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtFaxNumber" >
			    <td valign="middle" class="list_bg2" align="right" >传真</td>
			    <td class="list_bg1"><font size="2">
			      <input type="text" name="txtFaxNumber" size="25" maxlength="100" value="<tbl:writeparam name="txtFaxNumber" />" class="text">
			    </font></td>
		</d:DynamicFormElement>

        <d:DynamicFormElement elementName="txtNewBirthday" functionName="check_txtNewBirthday()">
		    <td class="list_bg2"><div align="right">出生日期</div></td>
		    <td class="list_bg1">
		      <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtNewBirthday)" onblur="lostFocus(this)" type="text" name="txtNewBirthday" size="25" value="<tbl:writeparam name="txtNewBirthday"/>" class="text">		      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtNewBirthday,'N')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
		    </td>
		</d:DynamicFormElement>
        <d:DynamicFormElement elementName="txtNationality" >
		     <td valign="middle" class="list_bg2" align="right" >国籍</td>
				    <td class="list_bg1"><font size="2">
				      <d:selcmn name="txtNationality" mapName="SET_C_NATIONALITY" match="txtNationality" width="23" defaultFlag ="true" showAllFlag="false"/>
				    </font></td>
		</d:DynamicFormElement>
	
	    <d:DynamicFormElement elementName="txtContractNo">
	    	<td class="list_bg2" align="right">协议编号</td>
            <td class="list_bg1" align="left">
              <input type="text" name="txtContractNo" size="25"  value="<tbl:writeparam name="txtContractNo"/>" class="text">
            </td> 
        </d:DynamicFormElement>
        
        <d:DynamicFormElement elementName="txtLoginID">
	    	<td class="list_bg2" align="right">登陆ID</td>
            <td class="list_bg1" align="left">
              <input type="text" name="txtLoginID" size="25"  value="<tbl:writeparam name="txtLoginID"/>" class="text">
            </td> 
        </d:DynamicFormElement>
        
        <d:DynamicFormElement elementName="txtLoginPwd">
	    	<td class="list_bg2" align="right">登陆密码</td>
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
		     <td><input name="Submit" type="button" class="button" value="下一步" onclick="javascript:create_submit()"></td>
		     <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		 </tr>
	      </table>

</form>
</td>
</tr>
</table>