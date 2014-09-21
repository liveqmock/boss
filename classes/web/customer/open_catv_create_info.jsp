<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk"%>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO"%>
<%@ page import="com.dtv.oss.util.Organization"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>
<%@ page import="com.dtv.oss.web.util.WebCurrentOperatorData"%>
<%@ page import="com.dtv.oss.web.util.WebKeys"%>

<%
    	StringBuffer lstBankMop = Postern.getBankMopBuffer();
    	String catvFieldName =Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID");
    	int    catvIdLength =Postern.getSystemsettingIntValue("SET_V_CATVIDLENGTH",50);
    	pageContext.setAttribute("AllMOPList", Postern.getOpeningMop());
	String open_flag=CommonKeys.ACTION_OF_CATVSHOPACCOUNT;
	
	String title = "模拟电视开户－信息录入";
	String csiType =CommonKeys.CUSTSERVICEINTERACTIONTYPE_CAO;

	double txtForceDepostMoney = (double) ((request
			.getParameter("txtForceDepostMoney") == null) ? 0.0 : Double
			.parseDouble(request.getParameter("txtForceDepostMoney")));
	String catvIdSelectFlag = (Postern.getCatvIdSelectFlag() == null) ? ""
			: Postern.getCatvIdSelectFlag();
	String openSourceType = (request.getParameter("txtOpenSourceType") == null) ? ""
			: request.getParameter("txtOpenSourceType");
	String openSourceID = (request.getParameter("txtOpenSourceID") == null) ? ""
			: request.getParameter("txtOpenSourceID");
	String openSourceName = Postern.getOrgNameByID(WebUtil
			.StringToInt(openSourceID));
	if (openSourceName == null)
		openSourceName = "";
	String preferTime = (request.getParameter("txtPreferedTime") == null) ? ""
			: request.getParameter("txtPreferedTime");
	
	String txtMopID =(request.getParameter("txtMopID")==null) ? "7" :request.getParameter("txtMopID");		
%>


<Script language=JavaScript>
<!--
var listBankMop=["9#9"<%=lstBankMop%>];
var open_flag =<%=CommonKeys.ACTION_OF_CATVSHOPACCOUNT %>;
var booking =<%=CommonKeys.ACTION_OF_BOOKING%>;
var bookingAccount =<%=CommonKeys.ACTION_OF_BOOKINGACCOUNT%>;
var shopAccount =<%=CommonKeys.ACTION_OF_CATVSHOPACCOUNT%>;
var bookingAgent =<%=CommonKeys.ACTION_OF_BOOKINGAGENT%>;
var selfInstall ="<%=CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL%>" ;
var install ="<%=CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_INSTALL%>";
var proxy ="<%=CommonKeys.OPENSOURCETYPE_PROXY%>";
var catvIdSelectFlag ='<%=catvIdSelectFlag%>';

function IndexOfBankMop(str)
{
   for (var i=0; i<listBankMop.length; i++){
       if (listBankMop[i] == str) return i;
   }

    return -1;
}
function changePrefed(){
   if (document.frmPost.txtIsInstall.value ==selfInstall){
      document.all("prefered").style.display ="none";
   } else{
      document.all("prefered").style.display ="";
   }
 
}

function checkCustomerType(){
	if (check_Blank(document.frmPost.txtCustType, true, "用户类型"))
		return false;
	return true;	
}
function checkCustomerName(){
	if (check_Blank(document.frmPost.txtName, true, "姓名"))
		return false;
	return true;	
}
function checkBillPostcode(){
	if (check_Blank(document.frmPost.txtBillPostcode, true, "帐单寄送邮编"))
		return false;
	return true;
}
function checkCardType(){
	if (check_Blank(document.frmPost.txtCardType, true, "证件类型"))
		return false;
	return true;	
}
function checkCardID(){
	if (check_Blank(document.frmPost.txtCardID, true, "证件号"))
		return false;
	return true;	
}
function checkForceDepostMoney(){
	if (!check_Blank(document.frmPost.txtForceDepostMoney, false, "押金")){     
		if (!check_Float(document.frmPost.txtForceDepostMoney,true,"押金"))
			return false;
	}
	return true;	
}
function checkCatvID(){
	if (catvIdSelectFlag =='Y'){
		if (check_Blank(document.frmPost.txtCatvID, true,"<%=catvFieldName%>"))
			return false;
	}
	return true;	
}
function checkPhone(){
	var telephone=document.getElementById("txtTelephone");
	var mobile=document.getElementById("txtMobileNumber");
	if(telephone!=null&&mobile!=null){
		if (document.frmPost.txtTelephone.value=='' && document.frmPost.txtMobileNumber.value=='') {
			alert("固定电话和移动电话不能同时为空!");
			document.frmPost.txtTelephone.focus();
			return false;
		}
	}else if(telephone!=null){
		if (check_Blank(document.frmPost.txtTelephone, true, "固定电话"))
			return false;		
	}else if(mobile!=null){
		if (check_Blank(document.frmPost.txtMobileNumber, true, "移动电话"))
			return false;		
	}
	return true;	
}
function checkCounty(){
	if (document.frmPost.txtCounty.value==null||document.frmPost.txtCounty.value==''){
		alert("请选择客户所在区!");
		return false;		
	}
	return true;	
}
function checkDetailAddress(){
	if (check_Blank(document.frmPost.txtDetailAddress, true, "详细地址"))
		return false;	
	return true;	
}
function checkPostcode(){
	if (check_Blank(document.frmPost.txtPostcode, true, "邮编"))
		return false;
	return true;	
}
function checkIsInstall(){
	if (open_flag ==shopAccount){
		if (check_Blank(document.frmPost.txtIsInstall,true,"是否上门安装"))
			return false;
	}
	return true;	
}
function checkPreferedDate(){
	if (open_flag ==booking  || open_flag ==bookingAgent|| ((open_flag ==shopAccount) && (document.frmPost.txtIsInstall.value ==install))) {
		if (check_Blank(document.frmPost.txtPreferedDate, true, "预约上门日期"))
			return false;
		if (!check_TenDate(document.frmPost.txtPreferedDate, true, "预约上门日期")) 
			return false;
		if (check_Blank(document.frmPost.txtPreferedTime, true, "预约上门时间段"))
			return false;
		if(!compareDateAndToday(getCurrentDate(),document.frmPost.txtPreferedDate,"预约上门日期必须在今天以后"))
			return false;
	}
	return true;	
}
function checkOpenSourceType(){
	if (check_Blank(document.frmPost.txtOpenSourceType, true, "来源渠道"))
		return false;
	return true;	
}
function checkOpenSourceID(){
	if (document.frmPost.txtOpenSourceType.value ==proxy){
		if (check_Blank(document.frmPost.txtOpenSourceID, true, "来源渠道ID"))
			return false;
	}
	return true;	
}
function checkGroupBargainDetailNo(){
	var groupbrain ="<%=CommonKeys.OPENSOURCETYPE_GROUPBARGAIN%>";
	if (document.frmPost.txtOpenSourceType.value ==groupbrain){
		if (check_Blank(document.frmPost.txtGroupBargainDetailNo,true,"团购券号"))
			return false;
	} else {
		if (document.frmPost.txtGroupBargainDetailNo.value !=""){
			alert("非团购购买形式不能填写团购券号!");
			return false;
		}
	}
	
	return true;	
}
function checkBirthday(){
	if (!check_Blank(document.frmPost.txtBirthday, false, "出生年月")){
		if (!check_TenDate(document.frmPost.txtBirthday, true, "出生年月")) 
			return false;
	}
	return true;	
}
function checkAccountType(){
	if (check_Blank(document.frmPost.txtAccountType, true, "帐户类型"))
		return false;
	return true;	
}
function checkAccountName(){
	if (check_Blank(document.frmPost.txtAccountName, true,"帐户名"))
		return false;
	return true;	
}
function checkMopID(){
	if (check_Blank(document.frmPost.txtMopID, true, "付费类型"))
		return false;
	return true;	
}

function setAcctName(){
	if (document.frmPost.txtAccountName.value =='')
	   document.frmPost.txtAccountName.value =document.frmPost.txtName.value;
}

function checkBankAccount(){
	if (open_flag ==booking || open_flag ==bookingAgent) return true;
	if (IndexOfBankMop(document.frmPost.txtMopID.value)>=0){
		if (trim(document.frmPost.txtBankAccount.value)=='' || trim(document.frmPost.txtBankAccountName.value)=='' ){
			alert("这种付费类型必须输入银行帐户及银行帐户名！");
			if (trim(document.frmPost.txtBankAccount.value)==''){
			    document.frmPost.txtBankAccount.focus();
			    return false;
			} 
			if (trim(document.frmPost.txtBankAccountName.value)==''){
				  document.frmPost.txtBankAccountName.focus();
				  return false;
		  }
		} else if (document.frmPost.txtMopID.value == '<%=CommonKeys.METHOD_OF_PAYMENT_ICBC%>') {
			  if (!validateICBCAccount(document.frmPost.txtBankAccount)) {
				   return false;
			  }
		}
	} else {
		 if (trim(document.frmPost.txtBankAccount.value) != '' || trim(document.frmPost.txtBankAccountName.value) != '') {
			  alert("这种付费类型不允许输入银行帐户及帐户户名!");
			  return false;
		 }
	}
	return true;
}
 
function check_catvAddPortNum(){
	if(document.frmPost.txtAddPortNum.value=='')
		document.frmPost.txtAddPortNum.value='0';
	
	if (!check_Num(document.frmPost.txtAddPortNum, true, "安装端口数"))
		return false;
	return true;	
}
       
function frm_submit(){
  if (checkCustomerInfo()&&checkAccountInfo()&&check_Bidimconfig()) { 
  	  if (document.frmPost.txtIsInstall!=null && (document.frmPost.txtIsInstall.value ==selfInstall
  	                                              ||document.frmPost.txtIsInstall.value =="")){
  	      document.frmPost.txtPreferedDate.value ="";
			    document.frmPost.txtPreferedTime.value ="";
  	  }
  	  document.frmPost.submit();
  }
}

function check_addressOrcatv(){
   //if (check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>")&& check_Blank(document.frmPost.txtDetailAddress, false, "详细地址")){
   //			alert("请输入至少一个查询条件");		
   //			return false;
   //}
   
   if(!check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>查询")){
      if(document.frmPost.txtCatvID.value.length > <%=catvIdLength%>){
			  alert("<%=catvFieldName%>不能超过<%=catvIdLength%>位");
			  return false;	
	}
    }
    //if(!check_Blank(document.frmPost.txtDetailAddress, false, "详细地址")){
    //			if(document.frmPost.txtDetailAddress.value.length<=3){
    //			   alert("详细地址必须至少4位");
    //			   return false;		
    //			}
    //}		
    return true;
}

function check_submit()
{
   if (check_addressOrcatv()) {
      var  catvId =document.frmPost.txtCatvID.value;
      var  detailAddress =document.frmPost.txtDetailAddress.value;
      var  districtID=document.frmPost.txtCounty.value;
      var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes";
      window.open("catv_terminal_query.do?txtFrom=1&txtTo=10&id="+catvId+"&txtDetailedAddress="+detailAddress+"&select=select&txtDistrictID=" + districtID,"<%=catvFieldName%>",strFeatures);
   }
}

function ChangeOpenSourceType()
{
   document.FrameUS.submit_update_select('osttoid', document.frmPost.txtOpenSourceType.value, 'txtOpenSourceID', '');
}

function check_catvinfo(){
	if(document.frmPost.txtCatvID){
		document.FrameUCATV.submit_update_catv_info(document.frmPost.txtCatvID.value);
	}
}
//-->
</Script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
	<table width="100%" border="0" cellspacing="1" cellpadding="3">
		<tr>
			<td width="100%" align="center">
				<font size="2"> 正在获取内容。。。 </font>
			</td>
		</tr>
	</table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe>
<iframe SRC="update_catv_info.screen" name="FrameUCATV" width="0" height="0" frameborder="0" scrolling="0">
</iframe>

<form name="frmPost" method="post" action="open_catv_check_cust.do">
	
	<input type="hidden" name="txtCsiType" value="<%=csiType%>" />
	<input type="hidden" name="OpenFlag" value="<%=open_flag%>" />
	<input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_CATV_CUSTOMERINOF%>" />
	<tbl:hiddenparameters pass="txtReferBookingSheetID/txtServiceCodeList" />
	<tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" />
	<table border="0" align="center" cellpadding="0" cellspacing="5">
		<tr>
			<td><img src="img/list_tit.gif" width="19" height="19"></td>
			<td class="title">
				<%=title%>
			</td>
		</tr>
	</table>
	<br>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
		<tr>
			<td><img src="img/mao.gif" width="1" height="1"></td>
		</tr>
	</table>
	<br>

	<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<d:DynamicForm formName="openCreateCustomerInfo" functionName="checkCustomerInfo()" column="4">
			<tr>
				<d:DynamicFormElement elementName="customerHead" column="4">
					<td colspan="4" class="import_tit" align="center">
						<font size="3">客户信息</font>
					</td>
				</d:DynamicFormElement>
			</tr>
			<tr>
				<d:DynamicFormElement elementName="customerType" functionName="checkCustomerType()">
					<td valign="middle" class="list_bg2" align="right" width="17%">
						客户类型*
					</td>
					<td width="33%" class="list_bg1">
						<d:selcmn name="txtCustType" mapName="SET_C_CUSTOMERTYPE" match="txtCustType" width="23" defaultFlag ="true" showAllFlag="false" judgeGradeFlag="true" />
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="customerName" functionName="checkCustomerName()">
					<td valign="middle" class="list_bg2" align="right" width="17%">
						姓名*
					</td>
					<td width="33%" class="list_bg1">
						<input type="text" name="txtName" size="25" maxlength="20" value="<tbl:writeparam name="txtName" />" class="text" onBlur="javaScript:setAcctName();" >
					</td>
				</d:DynamicFormElement>
			</tr>
			<%
					if (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)
					|| open_flag.equals(CommonKeys.ACTION_OF_SHOPACCOUNT) 
					|| open_flag.equals(CommonKeys.ACTION_OF_CATVSHOPACCOUNT)) {
			%>
			<tr>
				<d:DynamicFormElement elementName="customerCardType" functionName="checkCardType()">
					<td valign="middle" class="list_bg2" align="right">
						证件类型*
					</td>
					<td class="list_bg1">
						<d:selcmn name="txtCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtCardType" width="23" defaultFlag ="true" showAllFlag="false" />
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="customerCardID" functionName="checkCardID()">
					<td valign="middle" class="list_bg2" align="right">
						证件号*
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtCardID" size="25" maxlength="25"
								value="<tbl:writeparam name="txtCardID" />" class="text"> </font>
					</td>
				</d:DynamicFormElement>
			</tr>
			<%
			}
			%>
			<tr>
				<d:DynamicFormElement elementName="customerTelephone" functionName="checkPhone()">
					<td valign="middle" class="list_bg2" align="right">
						固定电话*
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtTelephone" size="25" maxlength="20"
								value="<tbl:writeparam name="txtTelephone"/>" class="text"> </font>
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="customerMobileNumber">
					<td valign="middle" class="list_bg2" align="right">
						移动电话
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtMobileNumber" size="25" maxlength="20"
								value="<tbl:writeparam name="txtMobileNumber" />" class="text"> </font>
					</td>
				</d:DynamicFormElement>
			</tr>
			<tr>
				<d:DynamicFormElement elementName="customerCounty" functionName="checkCounty()">
					<td valign="middle" class="list_bg2" align="right">
						所在区*
					</td>
					<td class="list_bg1">
						<input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
						<input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly
							value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
						<input name="selDistButton" type="button" class="button" value="选择"
							onClick="javascript:sel_district('leaf','txtCounty','txtCountyDesc')">
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="customerDetailAddress" functionName="checkDetailAddress()">
					<td valign="middle" class="list_bg2" align="right">
						详细地址*
					</td>
					<td class="list_bg1">
						<input type="text" name="txtDetailAddress" size="25" maxlength="100"
							value="<tbl:writeparam name="txtDetailAddress" />" class="text">
					</td>
				</d:DynamicFormElement>
			</tr>
			<tr>
				<d:DynamicFormElement elementName="customerPostcode" functionName="checkPostcode()">
					<td valign="middle" class="list_bg2" align="right">
						邮编*
					</td>
					<td class="list_bg1">
						<input type="text" name="txtPostcode" size="25" maxlength="6" value="<tbl:writeparam name="txtPostcode" />"
							class="text">
					</td>
				</d:DynamicFormElement>
				<%
				if (open_flag.equals(CommonKeys.ACTION_OF_SHOPACCOUNT) || open_flag.equals(CommonKeys.ACTION_OF_CATVSHOPACCOUNT)) {
				%>
				<d:DynamicFormElement elementName="customerIsInstall" functionName="checkIsInstall()">
					<td valign="middle" class="list_bg2" align="right">
						是否上门安装*
					</td>
					<td class="list_bg1">
						<d:selcmn name="txtIsInstall" mapName="SET_V_CUSTSERVICEINTERACTIONINSTYPE" match="txtIsInstall" width="23" defaultFlag ="true" showAllFlag="false"
							onchange="changePrefed()" />
					</td>
				</d:DynamicFormElement>
				<%
				} 
				%>
			</tr>
				<d:DynamicFormElement elementName="customerPreferedDate" functionName="checkPreferedDate()" column="4">
			<tr id="prefered" <%if ((open_flag.equals(CommonKeys.ACTION_OF_CATVSHOPACCOUNT)) && (preferTime.equals(""))){%>
				style="display:none" <%} %>>
					<td valign="middle" class="list_bg2" align="right" width="17%">
						预约上门日期*
					</td>
					<td width="33%" class="list_bg1">
						<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPreferedDate)" onblur="lostFocus(this)" type="text" name="txtPreferedDate" size="25" value="<tbl:writeparam name="txtPreferedDate" />"							<% if (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)){ %> class="textgray" readOnly <%}else{%>							class="text">						<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
						<%
						}
						%>
					</td>
					<td class="list_bg2" align="right" width="17%">
						预约上门时间段*
					</td>
					<td class="list_bg1" width="33%">
						<%
						if (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)) {
						%>
						<input type="text" name="txtPreferedTimeShow" size="25"
							value="<d:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="<%=preferTime%>"/>" class="textgray" readOnly>
						<input type="hidden" name="txtPreferedTime" size="25" value="<%=preferTime%>">
						<%
						} else {
						%>
						<d:selcmn name="txtPreferedTime" mapName="SET_C_CSIPREFEREDTIME" match="txtPreferedTime" width="23" defaultFlag ="true" showAllFlag="false" />
						<%
						}
						%>
					</td>
			</tr>
			</d:DynamicFormElement>
			<tr>
		  	 <d:DynamicFormElement elementName="csiReasion" functionName="check_csiReason()">
				     <tbl:csiReason csiType="<%=csiType%>" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" showType="text" checkScricptName="check_csiReason()" 
				     	  tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" />
			   </d:DynamicFormElement> 
			</tr>
			<%
					OperatorDTO operatorDto = (OperatorDTO) ((WebCurrentOperatorData) request
					.getSession().getAttribute(
							WebKeys.OPERATOR_SESSION_NAME))
					.getCurrentOperator();
					Organization org = Postern.getOrganizationByID(operatorDto
					.getOrgID());
					if (org.getOrgType().equals("P")
					&& org.getOrgSubType().equals("S")) {
			%>
			<tr>
				<d:DynamicFormElement elementName="customerOpenSourceType" functionName="checkOpenSourceType()">
					<td valign="middle" class="list_bg2">
						<div align="right">
							来源渠道*
						</div>
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="hidden" name="txtOpenSourceType" value="P"> <input type="text"
								name="source_TypeName" value="分公司代理商" size="25" class="textgray" readonly> </font>
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="customerOpenSourceID" functionName="checkOpenSourceID()">
					<td valign="middle" class="list_bg2">
						<div align="right">
							来源渠道ID
						</div>
					</td>
					<td class="list_bg1">
						<input type="hidden" name="txtOpenSourceID" value="<%=org.getOrgID()%>">
						<input type="text" name="source_IDName" value="<%=org.getOrgName()%>" size="25" class="textgray" readonly>
					</td>
				</d:DynamicFormElement>
			</tr>
			<%
			} else {
			%>
			<tr>
				<d:DynamicFormElement elementName="customerOpenSourceType" functionName="checkOpenSourceType()">
					<td valign="middle" class="list_bg2">
						<div align="right">
							来源渠道*
						</div>
					</td>
					<td class="list_bg1">
						<%
										if (open_flag
										.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)) {
						%>
						<input type="text" name="txtOpenSourceType1" size="25"
							value="<d:getcmnname typeName="SET_C_CUSTOPENSOURCETYPE" match="<%=openSourceType%>"/>" class="textgray" readOnly>
						<input type="hidden" name="txtOpenSourceType" value="<%=openSourceType%>">
						<%
						} else {
						%>
						<d:selcmn name="txtOpenSourceType" match="txtOpenSourceType" mapName="SET_C_CUSTOPENSOURCETYPE" width="23"
							onchange="ChangeOpenSourceType()" defaultFlag ="true" showAllFlag="false" />
						<%
						}
						%>
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="customerOpenSourceID" functionName="checkOpenSourceID()">
					<td valign="middle" class="list_bg2" align="right">
						来源渠道ID
					</td>
					<td class="list_bg1">
						<%
										if (open_flag
										.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)) {
						%>
						<input type="text" name="txtOpenSourceID1" size="25" value="<%=openSourceName%>" class="textgray" readOnly>
						<input type="hidden" name="txtOpenSourceID" value="<%=openSourceID%>">
						<%
						} else {
						%>
						<d:selopensourceid name="txtOpenSourceID" parentMatch="txtOpenSourceType" match="txtOpenSourceID" width="23" />
						<%
						}
						%>
					</td>
				</d:DynamicFormElement>
			</tr>
			<%
			}
			%>
			<tr>
				<d:DynamicFormElement elementName="customerCatvID" functionName="checkCatvID()">
					<td valign="middle" class="list_bg2">
						<div align="right">
							<%=catvFieldName%>
						</div>
					</td>
					<td class="list_bg1">

						<font size="2"> <input type="text" name="txtCatvID" size="25" 
							maxlength="<%=Postern.getSystemsettingIntValue("SET_V_CATVIDLENGTH",50)%>" 
							value="<tbl:writeparam name="txtCatvID" />" class="text"> </font>
						<input name="checkbutton" type="button" class="button" value="检查" onClick="javascript:check_submit()">
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="customerGroupBargainDetailNo" functionName="checkGroupBargainDetailNo()">
					<td valign="middle" class="list_bg2">
						<div align="right">
							团购券号
						</div>
					</td>
			<%
			  if (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGAGENT)){
			%>
          <td class="list_bg1">
						<font size="2"> <input type="text" name="txtGroupBargainDetailNo" size="25"
								value="<tbl:writeparam name="txtGroupBargainDetailNo" />" class="textgray" readOnly> </font>
					</td>					   
			<%
			 } else{
			%>		
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtGroupBargainDetailNo" size="25"
								value="<tbl:writeparam name="txtGroupBargainDetailNo" />" class="text"> </font>
					</td>
		 <%
		    }
		 %>
				</d:DynamicFormElement>
			</tr>
			<%
					if (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)
					|| open_flag.equals(CommonKeys.ACTION_OF_CATVSHOPACCOUNT)) {
			%>
			<tr>
				<d:DynamicFormElement elementName="customerForceDepostMoney" functionName="checkForceDepostMoney()">
					<td valign="middle" class="list_bg2" align="right">
						押金*
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtForceDepostMoney" size="25" maxlength="10"
								value="<%=WebUtil.bigDecimalFormat(txtForceDepostMoney)%>" class="text"> </font>
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="customerContractNo">
					<td valign="middle" class="list_bg2" align="right">
							协议编号
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtContractNo" size="25"
								value="<tbl:writeparam name="txtContractNo" />" class="text"> </font>
					</td>
				</d:DynamicFormElement>
			</tr>
			<%
			}
			%>
			<tr>
				<d:DynamicFormElement elementName="customerNationality">
					<td valign="middle" class="list_bg2" align="right">
						国籍
					</td>
					<td class="list_bg1">
						<d:selcmn name="txtNationality" mapName="SET_C_NATIONALITY" match="txtNationality" width="23" defaultFlag ="true" showAllFlag="false" />
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="customerGender">
					<td valign="middle" class="list_bg2" align="right" width="17%">
						性别
					</td>
					<td width="33%" class="list_bg1">
						<d:selcmn name="txtGender" mapName="SET_C_CUSTOMERGENDER" match="txtGender" width="23" defaultFlag ="true" />
					</td>
				</d:DynamicFormElement>
			</tr>
			<tr>
				<d:DynamicFormElement elementName="customerSocialSecCardID">
					<td valign="middle" class="list_bg2" align="right">
						社保卡编号
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtSocialSecCardID" size="25" maxlength="25"
								value="<tbl:writeparam name="txtSocialSecCardID" />" class="text"> </font>
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="customerBirthday" functionName="checkBirthday()">
					<td valign="middle" class="list_bg2" align="right">
						出生年月
					</td>
					<td class="list_bg1">
						<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtBirthday)" onblur="lostFocus(this)" type="text" name="txtBirthday" size="25" maxlength="10" value="<tbl:writeparam name="txtBirthday" />"							class="text">						<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtBirthday,'N')" src="img/calendar.gif" style=cursor:hand border="0">
					</td>
				</d:DynamicFormElement>
			</tr>

			<tr>
				<d:DynamicFormElement elementName="customerEmail">
					<td valign="middle" class="list_bg2" align="right">
						EMAIL地址
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtEmail" size="25" maxlength="100"
								value="<tbl:writeparam name="txtEmail" />" class="text"> </font>
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="customerFaxNumber">
					<td valign="middle" class="list_bg2" align="right">
						传真
					</td>
					<td class="list_bg1">
						<input type="text" name="txtFaxNumber" size="25" maxlength="100" value="<tbl:writeparam name="txtFaxNumber" />"
							class="text">
					</td>
				</d:DynamicFormElement>
			</tr>
			<tr>
				<d:DynamicFormElement elementName="customerLoginID">
					<td class="list_bg2">
						<div align="right">
							登陆ID
						</div>
					</td>
					<td class="list_bg1">
						<input type="text" name="txtLoginID" size="25" value="<tbl:writeparam name="txtLoginID" />" class="text">
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="customerLoginPwd">
					<td class="list_bg2">
						<div align="right">
							登陆密码
						</div>
					</td>
					<td class="list_bg1">
						<input type="text" name="txtLoginPwd" size="25" value="<tbl:writeparam name="txtLoginPwd" />" class="text">
					</td>
				</d:DynamicFormElement>
			</tr>
			<tr>
				<d:DynamicFormElement elementName="customerComments" column="4">
					<td valign="middle" class="list_bg2" align="right">
						备注
					</td>
					<td class="list_bg1" colspan="3">
						<input type="text" name="txtComments" size="83" maxlength="100" value="<tbl:writeparam name="txtComments" />"
							class="text">
					</td>
				</d:DynamicFormElement>
			</tr>
		</d:DynamicForm>

		<tbl:dynamicservey serveyType="M" showType="text" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25"
			defaultFlag="true" checkScricptName ="check_Bidimconfig()"/>

		<d:DynamicForm formName="openCreateAccountInfo" functionName="checkAccountInfo()" column="4">
			<tr>
				<d:DynamicFormElement elementName="AccountHead" column="4">
					<td colspan="4" class="import_tit" align="center">
						<font size="3">帐户基本信息(帐单地址如不填写则默认为用户地址)</font>
					</td>
				</d:DynamicFormElement>
			</tr>
			<tr>
				<d:DynamicFormElement elementName="AccountType" functionName="checkAccountType()">
					<td valign="middle" class="list_bg2" align="right">
						帐户类型*
					</td>
					<td class="list_bg1">
						<font size="2"> <d:selcmn name="txtAccountType" mapName="SET_F_ACCOUNTTYPE" match="txtAccountType"
								width="23" defaultFlag ="true" showAllFlag="false" /> </font>
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="AccountName" functionName="checkAccountName()">
					<td valign="middle" class="list_bg2" align="right">
						帐户名*
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtAccountName" size="25" maxlength="25"
								value="<tbl:writeparam name="txtAccountName" />" class="text"> </font>
					</td>
				</d:DynamicFormElement>
			</tr>
			<tr>
				<d:DynamicFormElement elementName="paymentType" functionName="checkMopID()">
					<td valign="middle" class="list_bg2" align="right">
						付费类型*
					</td>
					<td class="list_bg1">
						<tbl:select name="txtMopID" set="AllMOPList" match="<%=txtMopID%>" width="23" />
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="BankAccount" functionName="checkBankAccount()">
					<td valign="middle" class="list_bg2" align="right">
						银行帐号
					</td>
					<td class="list_bg1">
						<input type="text" name="txtBankAccount" size="25" maxlength="25" value="<tbl:writeparam name="txtBankAccount" />"
							class="text">
					</td>
				</d:DynamicFormElement>
			</tr>
			<tr>
				<d:DynamicFormElement elementName="BankAccountName">
					<td valign="middle" class="list_bg2" align="right">
						银行帐户名
					</td>
					<td class="list_bg1">
						<input type="text" name="txtBankAccountName" size="25" maxlength="25"
							value="<tbl:writeparam name="txtBankAccountName" />" class="text">
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="billCounty">
					<td valign="middle" class="list_bg2" align="right">
						帐单所在区
					</td>
					<td class="list_bg1">
						<input type="hidden" name="txtbillCounty" value="<tbl:writeparam name="txtbillCounty" />">
						<input type="text" name="txtbillCountyDesc" size="25" maxlength="50" readonly
							value="<tbl:WriteDistrictInfo property="txtbillCounty" />" class="text">
						<input name="selDistButton1" type="button" class="button" value="选择"
							onClick="javascript:sel_district('leaf','txtbillCounty','txtbillCountyDesc')">
					</td>
				</d:DynamicFormElement>
			</tr>
			<tr>
				<d:DynamicFormElement elementName="billDetailAddress">
					<td class="list_bg2" align="right">
						帐单寄送地址
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtBillDetailAddress" size="25" maxlength="100"
								value="<tbl:writeparam name="txtBillDetailAddress" />" class="text"> </font>
					</td>
				</d:DynamicFormElement>
				<d:DynamicFormElement elementName="billPostcode">
					<td class="list_bg2" align="right">
						帐单寄送邮编
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtBillPostcode" size="25" maxlength="6"
								value="<tbl:writeparam name="txtBillPostcode" />" class="text"> </font>
					</td>
				</d:DynamicFormElement>
		</d:DynamicForm>
		</tr>
	</table>
	
	<!--
	<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<tr>
			<td colspan="4" class="import_tit" align="center"><font size="3">终端信息</font></td>	
		</tr>
	</table>
	
	<table id="tbl_catv_info" width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg" style="display:none">
		<tr>
			<td colspan="4" class="import_tit" align="center">已有终端信息</td>	
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right" width="17%">终端ID</td>
			<td width="33%" class="list_bg1">
				<input type="text" name="txtCATVInfo_ID" size="25" maxlength="20" value="<tbl:writeparam name="txtCATVInfo_ID" />" class="textgray" readonly >
			</td>
			<td valign="middle" class="list_bg2" align="right" width="17%">所在区域</td>
			<td width="33%" class="list_bg1">
				<input type="text" name="txtCATVInfo_District" size="25" maxlength="20" value="<tbl:writeparam name="txtCATVInfo_District" />" class="textgray" readonly >
			</td>
		<tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right" width="17%">详细地址</td>
			<td width="33%" class="list_bg1">
				<input type="text" name="txtCATVInfo_DetailAddress" size="25" value="<tbl:writeparam name="txtCATVInfo_DetailAddress" />" class="textgray" readonly >
			</td>
			<td valign="middle" class="list_bg2" align="right" width="17%">已有端口数</td>
			<td width="33%" class="list_bg1">
				<input type="text" name="txtCATVInfo_PortNum" size="25" maxlength="20" value="<tbl:writeparam name="txtCATVInfo_PortNum" />" class="textgray" readonly >
			</td>
		<tr>
	</table>
	<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<tr>
			<td colspan="4" class="import_tit" align="center">新增终端信息</td>	
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right" width="17%">安装端口数*</td>
			<td width="33%" class="list_bg1">
				<input type="text" name="txtAddPortNum" size="25" maxlength="20" value="<tbl:writeparam name="txtAddPortNum" />" class="text" >
			</td>
			<td valign="middle" class="list_bg2" align="right" width="17%"></td>
			<td width="33%" class="list_bg1"></td>
		<tr>
	</table>
	-->
	<br/>
	<table align="center" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<%
			if (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)) {
			%>
			<td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td background="img/button_bg.gif" height="20"><a href="<bk:backurl property="service_interaction_info.do"/>" class="btn12">返&nbsp;回</a></td>
			<td><img src="img/button2_l.gif" width="13" height="20"></td>
			<%
			}
			%>
			<td width="20"></td>
			<td><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="next" type="button" class="button" onClick="frm_submit()" value="下一步"></td>
			<td><img src="img/button_r.gif" width="22" height="20"></td>
		</tr>
	</table>
	<br/><br/>
</form>
 <Script language=JavaScript>
      function installPrepare(){
	      if(document.frmPost.txtIsInstall!=null){
			   	if (document.frmPost.txtIsInstall.value ==selfInstall||document.frmPost.txtIsInstall.value ==""){
				      document.all("prefered").style.display ="none";
				 } else{
				      document.all("prefered").style.display ="";
				 }
			}
	  }
	  installPrepare();
 </Script>