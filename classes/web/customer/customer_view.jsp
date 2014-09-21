<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern,
         com.dtv.oss.dto.GroupBargainDetailDTO,
         java.util.Map,
         java.util.HashMap,
         com.dtv.oss.dto.GroupBargainDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
         
<Script language=javascript>
<!--
function check_name(){
	if (check_Blank(document.frmPost.txtName, true, "姓名"))
		return false;
	return true;	
}
function check_district(){
	if (document.frmPost.txtDistrict.value==null||document.frmPost.txtDistrict.value==''){
    	   alert("请选择客户所在区!");
			return false;		
		}
	return true;	
}
function check_card(){
	if (document.frmPost.txtCardType.value=='' && document.frmPost.txtCardID.value=='') {
    alert("请输入证件信息！");
    document.frmPost.txtCardType.focus();
    return false;
  }
  else if(document.frmPost.txtCardType.value=='') {
    alert("请选择证件类型！");
    document.frmPost.txtCardType.focus();
    return false;
  }
  else if (document.frmPost.txtCardID.value=='') {
    alert("请输入证件号!");
    document.frmPost.txtCardID.focus();
    return false;
  }
	return true;	
}
function checkPhone(){
	var telephone=document.getElementById("txtTelephone");
	var mobile=document.getElementById("txtTelephoneMobile");
	if(telephone!=null&&mobile!=null){
		if (telephone.value=='' && mobile.value=='') {
			alert("固定电话和移动电话不能同时为空!");
			document.frmPost.txtTelephone.focus();
			return false;
		}
	}else if(telephone!=null){
		if (check_Blank(document.frmPost.txtTelephone, true, "固定电话"))
			return false;		
	}else if(mobile!=null){
		if (check_Blank(document.frmPost.txtTelephoneMobile, true, "移动电话"))
			return false;		
	}
	return true;	
}
function checkDetailAddress(){
	if (check_Blank(document.frmPost.txtDetailAddress, true, "详细地址"))
		return false;
	return true;	
}
function checkBirthday(){
	if (document.frmPost.txtBirthday.value != ''){
    if (!check_TenDate(document.frmPost.txtBirthday, true, "出生日期"))
      return false;
  }
	return true;	
}
function check_Postcode(){
	if (check_Blank(document.frmPost.txtPostcode, true, "邮编"))
		return false;
	return true;	
}
function checkLogInUser(){
	 if (document.frmPost.txtLoginRePwd ==null || document.frmPost.txtLoginPwd ==null){
	 	   alert("登陆密码或登陆验证密码没有配置，请与管理员联系");
	 	   return false;
	 }
	 if (document.frmPost.txtLoginRePwd.value !='' || document.frmPost.txtLoginPwd.value !=''){
	    if (document.frmPost.txtLoginPwd.value.length !=6){
	 	     alert("登陆密码的长度不为6!");
   	     return false;
	    }
	    var numtype="0123456789"; 
	    for(i=0;i<document.frmPost.txtLoginPwd.value.length;i++){ 
	       if(numtype.indexOf(document.frmPost.txtLoginPwd.value.substring(i,i+1))<0) {
	        	alert("登陆密码输入了非数字字符");
	        	return false; 
	 	     } 
	    }
      if (document.frmPost.txtLoginPwd.value !=document.frmPost.txtLoginRePwd.value){
   	      alert("登陆密码与验证密码不一致!");
   	      return false;
      }
   }
	 return true;
}

function edit_submit(){
	if (check_form()&&check_Bidimconfig()){
  	 if (window.confirm('您确认要修改该用户资料吗?')){
	     document.frmPost.action="customer_edit_done.do";
	     document.frmPost.Action.value="update";
	     document.frmPost.submit();
	   }
	}
}
function edit_cust_type(){
        document.frmPost.action="cust_type_view.screen";
	document.frmPost.submit();
}
function account_create_submit(){
	document.frmPost.action="account_create.screen";
	document.frmPost.submit();
}
function close_submit(){
	document.frmPost.action="customer_close_create.screen";
	document.frmPost.submit();
}
function withdraw_submit(){
	document.frmPost.action="customer_withdraw_create.screen";
	document.frmPost.submit();
}
function move_submit(){
	document.frmPost.action="catv_terminal_query_for_move.screen";
	document.frmPost.txtDetailAddress.value="";
	document.frmPost.submit();
}
function transfer_submit(){
	document.frmPost.action="customer_transfer_create_prepare.do";
	document.frmPost.submit();
}
function exchagepoints_submit(){
	document.frmPost.action="select_activity_for_points.do";
	document.frmPost.submit();
}
function diff_transfer_submit(){
	self.location="customer_diff_transfer_create_query.screen?txtCustomerID="+document.frmPost.txtCustomerID.value;
}

function customer_protocol(){
	self.location="customer_protocol.do?txtCustomerID="+document.frmPost.txtCustomerID.value;
}
function batch_authorize(){
	self.location="batch_authorize.do?txtCustomerID="+document.frmPost.txtCustomerID.value;
}

function modifyCustomerType(){
	var customerId=document.frmPost.txtCustomerID.value;
	var customerType=document.frmPost.txtCustomerType.value;
	var dtlastmod=document.frmPost.txtCustomerDtLastmod.value;
	var strFeatures = "top=120px,left=120px,width=350px,height=350px,resizable=no,toolbar=no,scrollbars=no";
	window.open("customer_type_modify.screen?txtCustomerID="+customerId+"&txtCustomerType="+customerType+"&txtCustomerDtLastmod="+dtlastmod,"客户类型修改",strFeatures);
}
function modifyOpenSourceType(){
	var customerId=document.frmPost.txtCustomerID.value;
	var openSourceType=document.frmPost.txtOpenSourceType.value;
	var dtlastmod=document.frmPost.txtCustomerDtLastmod.value;
	var strFeatures = "top=120px,left=120px,width=350px,height=350px,resizable=no,toolbar=no,scrollbars=no";
	window.open("open_source_type_modify.screen?txtCustomerID="+customerId+"&txtOpenSourceType="+openSourceType+"&txtCustomerDtLastmod="+dtlastmod,"来源渠道修改",strFeatures);
}
function modifyCatvID(){
	var catvName = "<%=Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID")%>";
	var customerId=document.frmPost.txtCustomerID.value;
	var catvID=document.frmPost.txtCatvID.value;
	var dtlastmod=document.frmPost.txtCustomerDtLastmod.value;
	var strFeatures = "top=120px,left=120px,width=350px,height=350px,resizable=no,toolbar=no,scrollbars=no";
	window.open("catvID_modify.screen?txtCustomerID="+customerId+"&txtCatvID="+catvID+"&txtCustomerDtLastmod="+dtlastmod+"&catvName="+catvName,catvName+"修改",strFeatures);
}

function customer_register_onemore_print(customerID)
{
		document.frmForPrintPost.target="_blank";
		document.frmForPrintPost.action="customer_register_onemore_print.do?txtCustomerID="+customerID;
		document.frmForPrintPost.submit();
		document.frmForPrintPost.target="_self";
	
}
function addComments_submit(){
	var customerId=document.frmPost.txtCustomerID.value;
	var comments=document.frmPost.txtComments.value;
	var dtlastmod=document.frmPost.txtCustomerDtLastmod.value;
	var strFeatures = "top=120px,left=120px,width=350px,height=350px,resizable=no,toolbar=no,scrollbars=no";
	window.open("add_comments_for_cust.screen?txtCustomerID="+customerId+"&txtCustomerDtLastmod="+dtlastmod,"添加客户备注信息",strFeatures);
}

function customerInfo_print(customerID){
	  document.frmPost.target="_blank";
	  document.frmPost.action="customer_print_for_authorize.do?txtpagetype=customerInfo&txtcustomerID="+customerID;
	  document.frmPost.submit();
	  document.frmPost.target="_self";
}
//-->
</SCRIPT>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户基本资料</td>
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
    pageContext.setAttribute("SYSTEMSYMBOLNAME", Postern.getSystemSymbolName());
    
    Map markMap = Postern.getServeyResultByCustIdForRealUser(wrap.getCustDto().getCustomerID(),"T_CUSTMARKETINFO","CUSTOMERID");
    pageContext.setAttribute("serveyMarketMap",markMap);
    //市场分区信息
    String  marketSegment=(String)Postern.getAllMarketSegmentName().get(String.valueOf(wrap.getCustDto().getMarketSegmentID()));
    if(marketSegment==null)marketSegment="";
    
%>

<input type="hidden" name="Action" value="update">
<input type="hidden" name="txtAddressID" value="<tbl:write name="custaddr" property="addressID"/>">
<input type="hidden" name="txtCustomerType"   value="<tbl:write name="cust" property="customerType" />" >
<input type="hidden" name="txtOpenSourceType"   value="<tbl:write name="cust" property="OpenSourceType" />" >
<input type="hidden" name="txtCustomerDtLastmod"   value="<tbl:write name="cust" property="DtLastmod" />" >
<input type="hidden" name="txtAddressDtLastmod"   value="<tbl:write name="custaddr" property="DtLastmod" />" >
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTSERIALNO" >
      <tr>
    	<td valign="middle" class="list_bg2" align="right" colspan="1">
    		客户条形码
    	</td>
    	<td colspan="3" class="list_bg1">
    		<font size="2"> 
    		    <input type="text" name="txtCustomerSerialNo" size="25" maxlength="50" value="<tbl:write name="cust" property="customerSerialNo" />" class="textgray" readonly> 
    		</font>
    	</td>
      </tr>
  </lgc:showcontentbysetting>
  <d:DynamicForm formName="CustomerView" functionName="check_form()" column="4">
  <tr>
	<d:DynamicFormElement elementName="customerID" >	
    <td class="list_bg2"><div align="right">客户证号</div></td>
    <td class="list_bg1"><input type="text" name="txtCustomerID" size="25"  value="<tbl:write name="cust" property="customerID" />" class="textgray" readonly ></td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="customerType" >	
    <td class="list_bg2"><div align="right">客户类型</div></td>
    <td class="list_bg1">
    	<input type="text" name="txtCustomerTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="cust:customerType" />" class="textgray" readonly >
     	<d:displayControl id="button_customer_type_modify" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
    	<pri:authorized name="customer_type_modify.do" >
    	<input name="modify" type="button" class="button" value="修改" onclick="javascript:modifyCustomerType()">
    	</pri:authorized>
			</d:displayControl>
    </td>   
	</d:DynamicFormElement>
   </tr>
   <tr>
	<d:DynamicFormElement elementName="customerName" functionName="check_name()">	
    <td class="list_bg2"><div align="right">姓名*</div></td>
    <td class="list_bg1"><input type="text" name="txtName" size="25"  value="<tbl:write name="cust" property="name" />"></td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="customerCreateDate">	
	<td class="list_bg2"><div align="right">创建日期</div></td>
    <td class="list_bg1"><input type="text" name="txtDtCreateShow" size="25"  value="<tbl:write name="cust" property="createTime"/>" class="textgray" readonly ></td>  
	</d:DynamicFormElement>
  </tr>
  <tr>
    <d:DynamicFormElement elementName="customerGender">	
  	<td class="list_bg2"><div align="right">性别</div></td>
    <td class="list_bg1"><d:selcmn name="txtGender" mapName="SET_C_CUSTOMERGENDER" match="cust:gender" width="23" /></td>
    </d:DynamicFormElement>
    <d:DynamicFormElement elementName="customerNationality">	
    <td class="list_bg2"><div align="right">国籍</div></td>
    <td class="list_bg1"><d:selcmn name="txtNationality" mapName="SET_C_NATIONALITY" match="cust:nationality" width="23" /></td>
	</d:DynamicFormElement>
  </tr>
  <tr>
    <d:DynamicFormElement elementName="customerCardType" functionName="check_card()">	
   	<td class="list_bg2"><div align="right">证件类型*</div></td>
    <td class="list_bg1"><d:selcmn name="txtCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="cust:cardType" width="23" /></td>    
    </d:DynamicFormElement>
    <d:DynamicFormElement elementName="customerBirthday" functionName="checkBirthday()">	
    <td class="list_bg2"><div align="right">出生日期</div></td>
    <td class="list_bg1"><d:datetime name="txtBirthday" size="25" myClass="text" match="cust:Birthday"  onclick="MyCalendar.SetDate(this,document.frmPost.txtBirthday,'N')" picURL="img/calendar.gif" style="cursor:hand" /></td>	
	</d:DynamicFormElement>
  </tr>
   <tr>
	<d:DynamicFormElement elementName="customerCardID">	
    <td class="list_bg2"><div align="right">证件号码*</div></td>
    <td class="list_bg1"><input type="text" name="txtCardID" size="25"  value="<tbl:write name="cust" property="cardID" />"  ></td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="customerTelephone" functionName="checkPhone()">	
  	<td class="list_bg2"><div align="right">固定电话*</div></td>
    <td class="list_bg1"><input type="text" name="txtTelephone" size="25"  value="<tbl:write name="cust" property="telephone" />"  ></td>  
	</d:DynamicFormElement>
  </tr>
  <tr>
    <d:DynamicFormElement elementName="customerDistrict" functionName="check_district()">	
    <td class="list_bg2"><div align="right">所在区域*</div></td>
    <td class="list_bg1">         
      <input type="hidden" name="txtDistrict" value="<tbl:write name="custaddr" property="districtID" />">
	    <input type="text" name="txtDistrictDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo name="custaddr" property="DistrictID" />" class="text">
     	<d:displayControl id="button_customer_district_select" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
      <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('leaf','txtDistrict','txtDistrictDesc')">
			</d:displayControl>
   </td>
   </d:DynamicFormElement>
	<d:DynamicFormElement elementName="customerTelephoneMobile">	
    <td class="list_bg2"><div align="right">移动电话</div></td>
    <td class="list_bg1"><input type="text" name="txtTelephoneMobile" size="25"  value="<tbl:write name="cust" property="telephoneMobile" />"  ></td>
	</d:DynamicFormElement>
  </tr>
  <tr>
    <d:DynamicFormElement elementName="customerPostcode" functionName="check_Postcode()">	
    <td class="list_bg2"><div align="right">邮编*</div></td>
    <td class="list_bg1"><input type="text" name="txtPostcode" size="25" maxlength="6" value="<tbl:write name="custaddr" property="Postcode" />" ></td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="customerFax">	
  	<td class="list_bg2"><div align="right">传真</div></td>
    <td class="list_bg1"><input type="text" name="txtFax" size="25"  value="<tbl:write name="cust" property="fax" />" ></td>
	</d:DynamicFormElement>
  </tr>
  <tr>
	<d:DynamicFormElement elementName="customerDetailAddress" functionName="checkDetailAddress()">	
    <td class="list_bg2"><div align="right">详细地址*</div></td>
    <td class="list_bg1"><input type="text" name="txtDetailAddress" size="25"  value="<tbl:write name="custaddr" property="DetailAddress" />"  ></td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="customerEmail">	
  	<td class="list_bg2"><div align="right">Email 地址</div></td>
    <td class="list_bg1"><input type="text" name="txtEmail" size="25"  value="<tbl:write name="cust" property="email" />" ></td>
	</d:DynamicFormElement>
  </tr>
   <tr>
	<d:DynamicFormElement elementName="customerOpenSourceType">	
    <td class="list_bg2"><div align="right">来源渠道</div></td>
    <td class="list_bg1"><input type="text" name="txtOpenSourceTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOPENSOURCETYPE" match="cust:OpenSourceType" />" class="textgray" readonly >
   	<d:displayControl id="button_customer_opensource_modify" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
    <pri:authorized name="open_source_type_modify.do" >
    <input name="modify" type="button" class="button" value="修改" onclick="javascript:modifyOpenSourceType()">
    </pri:authorized>
		</d:displayControl>
    </td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="customerOpenSourceTypeID">	
    <td class="list_bg2"><div align="right">来源渠道ID</div></td>
    <td class="list_bg1"><input type="text" name="txtOpenSourceTypeID" size="25" maxlength="10" value="<tbl:write name="cust" property="OpenSourceTypeID" />"  class="textgray" readonly ></td>
	</d:DynamicFormElement>
  </tr>
  <tr>
	<d:DynamicFormElement elementName="customerLoginID" >	
    <td class="list_bg2"><div align="right">登陆ID</div></td>
    <td class="list_bg1"><input type="text" name="txtLoginID" size="25"  value="<tbl:write name="cust" property="loginID" />" ></td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="customerLoginPwd" functionName="checkLogInUser()">	
    <td class="list_bg2"><div align="right">登陆密码</div></td>
    <td class="list_bg1"><input type="password" name="txtLoginPwd" size="25"  maxlength="6" value="<tbl:write name="cust" property="loginPwd" />" ></td>
	</d:DynamicFormElement>
  </tr>
  <tr>
  	<d:DynamicFormElement elementName="customerLoginRePwd" functionName="checkLogInUser()">	
    <td class="list_bg2"><div align="right">登陆验证密码</div></td>
    <td class="list_bg1"><input type="password" name="txtLoginRePwd" size="25" maxlength="6" value="<tbl:write name="cust" property="loginPwd" />" ></td>
	  </d:DynamicFormElement>
	</tr>
  <tr>
  	<d:DynamicFormElement elementName="customerCurrentPoints">	
    <td class="list_bg2"><div align="right">当前可用积分</div></td>
    <td class="list_bg1"><input type="text" name="txtCurrentPoints" size="25"  value="<tbl:write name="cust" property="currentAccumulatePoint" />"  class="textgray" readonly></td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="customerTotalPoints">	
    <td class="list_bg2"><div align="right">累计历史总积分</div></td>
    <td class="list_bg1"><input type="text" name="txtTotalPoints" size="25"  value="<tbl:write name="cust" property="totalAccumulatePoint" />"  class="textgray" readonly></td>
	</d:DynamicFormElement>	
  </tr>
  <tr>
	<d:DynamicFormElement elementName="customerStatus">	
    <td class="list_bg2"><div align="right">客户状态</div></td>
    <td class="list_bg1"><input type="text" name="txtCustomerStatusName" size="25" value="<d:getcmnname typeName="SET_C_CUSTOMERSTATUS" match="cust:status" />" class="textgray" readonly ></td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="customerDtLastmod">	
    <td class="list_bg2"><div align="right">最后更新时间</div></td>
    <td class="list_bg1"><input type="text" name="txtCustomerShowDtLastmod" size="25" value="<tbl:write name="cust" property="DtLastmod" />" class="textgray" readonly ></td>
	</d:DynamicFormElement>
  </tr>
  <tr>
  	<d:DynamicFormElement elementName="customerCatvID">	
    <td class="list_bg2"><div align="right">&nbsp;<%=Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID")%></div></td>
    <td class="list_bg1"><input type="text" name="txtCatvID" size="25" maxlength="10" value="<tbl:write name="cust" property="CatvID" />"  class="textgray" readonly >
    <d:displayControl id="button_customer_catvid_modify" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
		<pri:authorized name="catvID_modify.do" >
    	<input name="modify" type="button" class="button" value="修改" onclick="javascript:modifyCatvID()">
    </pri:authorized>
		</d:displayControl>
    </td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="customerSocialSecCardID">	
    <td class="list_bg2"><div align="right">社保卡编号</div></td>
    <td class="list_bg1"><input type="text" name="txtSocialSecCardID" size="25"  value="<tbl:write name="cust" property="socialSecCardID" />" ></td>   
	</d:DynamicFormElement>
  </tr>
  <tr>
	<d:DynamicFormElement elementName="customerContractNo" >	
  	<td class="list_bg2"><div align="right">协议编号</div></td>
    <td class="list_bg1" ><input type="text" name="txtContractNo" size="25"  value="<tbl:write name="cust" property="contractNo" />" ></td>
	</d:DynamicFormElement>
	<d:DynamicFormElement elementName="customerMarketSegmentID" >	
  	<td class="list_bg2"><div align="right">市场分区</div></td>
    <td class="list_bg1" ><input type="text" name="txtMarketSegment" size="25"  value="<%=marketSegment%>" class="textgray" readonly></td>
	</d:DynamicFormElement>
  </tr>
	<d:DynamicFormElement elementName="customerCommentsID" column="4">	
  <tr>
  	<td class="list_bg2" ><div align="right">客户资料备注</div></td>
  	<td class="list_bg1" colspan="3" >
      <textarea name="txtComments"  length="5" cols=83 rows=3><tbl:write  name="cust" property="comments" /></textarea>
    </td>
  </tr>
	</d:DynamicFormElement>
</d:DynamicForm>
  <tbl:dynamicservey serveyType="M"  showType="text" tdWordStyle="list_bg2" tdControlStyle="list_bg1"  controlSize="25"  match="serveyMarketMap" checkScricptName ="check_Bidimconfig()" />
</table>
<br>
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
		<td>
			<table border="0" cellspacing="0" cellpadding="0">
  			<tr>
       	<d:displayControl id="button_customer_view_modify" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
			    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                     <td><input name="Submit" type="button" class="button" value="修 改" onclick="javascript:edit_submit()"></td>
       		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
       		 <td width="20" ></td>
		</d:displayControl>
        <d:displayControl id="button_customer_view_for_onemore_print" bean="SYSTEMSYMBOLNAME">
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
        <td><input name="Submit" type="button" class="button" value="补 打" onclick="javascript:customer_register_onemore_print(<tbl:write name="cust" property="customerID" />)"></td>
   		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
       	<td width="20" ></td>
        </d:displayControl>
       	<d:displayControl id="button_customer_view_exchagepoints" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
       		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="Submit" type="button" class="button" value="积分兑换" onclick="javascript:exchagepoints_submit()"></td>
       		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				</d:displayControl>
				<pri:authorized name="add_comments_for_cust.do" >
       		    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
              <td><input name="button_4" type="button" class="button" value="添加备注" onclick="javascript:addComments_submit()"></td>
       		    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        </pri:authorized>
				<pri:authorized name="customer_protocol.do"> 
        	   <td width="20" ></td>
       	     <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
             <td><input name="button_6" type="button" class="button" value="协议价维护" onclick="javascript:customer_protocol()"></td>
       	     <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        </pri:authorized> 
        <pri:authorized name="batch_authorize.do"> 
        	   <td width="20" ></td>
       	     <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
             <td><input name="button_6" type="button" class="button" value="批量重授权" onclick="javascript:batch_authorize()"></td>
       	     <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        </pri:authorized> 
        <pri:authorized name="customer_print_for_authorize.do">
             <td width="20" ></td>
       	     <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
             <td><input name="button_6" type="button" class="button" value="客户资料打印" onclick="javascript:customerInfo_print(<tbl:write name="cust" property="customerID" />)"></td>
       	     <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        </pri:authorized>
				</tr>
			</table>
		</td>
	</tr>
</table>

 </lgc:bloop>
 <input type="hidden" name="func_flag" value="3">
</form>
<!--
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
-->
</TD>
</TR>
</TABLE>
<form name="frmForPrintPost" method="post" >
<input type="hidden" name="txtCustomerID" value="<tbl:write name="cust" property="customerID"/>">
</form>

