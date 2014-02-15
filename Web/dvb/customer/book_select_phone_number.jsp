<%--Copyright 2003 Digivision, Inc. All rights reserved.--%>
<%--$Id: book_select_phone_number.jsp,v 1.1.1.1 2010/01/25 09:11:07 yangyong Exp $--%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="operator" prefix="op" %>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO" %>

<%@ page import="com.dtv.oss.dto.ProductPackageDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.service.component.ImmediateCountFeeInfo" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>


<% 
String title = "预约-选择电话号码";
String txtPage = request.getParameter("txtPage");
if(txtPage == null || "null".equals(txtPage)) txtPage = "";

int txtActionType = CommonKeys.CHECK_HARDPRODUCTINFO;
String func_flag = "";
String confirm_post = "";
String txtCustomerID = request.getParameter( "txtCustomerID");
System.out.println("___txtCustomerID="+txtCustomerID);
String txtCounty = "";
if(txtCustomerID != null)
{
		txtCounty = Postern.getDistrictIDByCustomerID(txtCustomerID)+"";
}

if("bookingModify".equals(txtPage))
{
	title = "预约单修改-选择电话号码";
	txtActionType = CommonKeys.CHECK_CUSTOMERINFO;
	func_flag="110";
	confirm_post="true";
}
if("bookingAddAccountBook".equals(txtPage))
{
	title = "预约增机预约-选择电话号码";
	txtActionType = CommonKeys.CHECK_BOOKINGUSER_PRODUCTPG_AND_INVOICE;
	System.out.println("_____txtCounty="+txtCounty);
	//func_flag="110";
	//confirm_post="true";
}

if("bookingAddAccountBookModify".equals(txtPage))
{
	title = "预约增机预约修改-选择电话号码";
	txtActionType = CommonKeys.OPEN_BOOKINGUSER_SERVICE_ACCOUNT_FORMODIFY;
	
	func_flag="116";
	//confirm_post="true";
}
if("agentConfirm".equals(txtPage))
{
	title = "代理商确认-选择电话号码";
	txtActionType = CommonKeys.CHECK_CUSTOMERINFO;
	func_flag="110";
	confirm_post="true";
}

%>
<Script language=JavaScript>   
 
function check_frm()
{
	var i=document.frmPost.length;
	var submitFlag=true;

	var hasPhoneNo = false;
	//检查数据完整性和给TerminalDeviceList赋值
	for(i=0;i<document.frmPost.length;i++)
	{	
		var element=document.frmPost.elements[i];
		if(element.title=="inputID"){
		}
		if((""+element.type)=="text")
		{
			if(element.name == "phoneNo")
				hasPhoneNo = true;
			
		}
	}
	if(hasPhoneNo)
	{
		if(!phoneNo_check())
			return false;
	}		
	
	return submitFlag;
}

function frm_submit()
{
	//检查电话号码是否合法
	
	//检查电话号码资源是否选择
	if (check_frm()) 
	{
		var txtPage = "<%=txtPage%>";
		if(txtPage == "bookingModify")
		{
			document.frmPost.action="book_alter_op.do?ActionFlag="+<%=CommonKeys.MODIFY_OF_BOOKING%>;
		}
		if(txtPage == "bookingAddAccountBook")
		{
			document.frmPost.action="booking_addaccount_confirm.screen";
		}
		if(txtPage == "bookingAddAccountBookModify")
		{
			document.frmPost.action="booking_addacount_update.do?ActionFlag=addAcount";
		}
		if(txtPage == "agentConfirm")
		{
			document.frmPost.action="book_alter_confirm_op.do?ActionFlag="+<%=CommonKeys.CONFIRM_OF_BOOKING%>;
		}
		
		document.frmPost.submit();
	}
		
}

function frm_back()
{
  document.frmPost.txtActionType.value ="";
	document.frmPost.action="check_address.do";
	var txtPage = "<%=txtPage%>";
	if(txtPage == "bookingModify")
	{
		document.frmPost.action="book_alter.screen";
	}
	if(txtPage == "bookingAddAccountBook")
	{
		document.frmPost.action="booking_addacount_book.screen";
	}
	if(txtPage == "bookingAddAccountBookModify")
	{
		document.frmPost.action="booking_addacount_info.screen";
	}
	if(txtPage == "agentConfirm")
	{
		document.frmPost.action="book_alter.screen";
	}
	document.frmPost.submit();
}

function phoneNo_Search()
{
  var phoneNo =document.frmPost.phoneNo.value;
  
  var countyID = "";
  if(document.frmPost.txtCounty != null)
  {
  	countyID = document.frmPost.txtCounty.value;
  }
  else
  {
  	countyID = "<%=txtCounty%>";
  }
  //alert(countyID);
  var  grade=document.frmPost.txtGrade.value;
  var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  window.open("phoneNo_query.do?txtFrom=1&txtTo=10&phoneNo="+phoneNo+"&districtID="+countyID+"&grade="+grade,"电话号码查询",strFeatures);
}

//-->
</Script>
<% 
 CommandResponse RepCmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
 if (RepCmd ==null) RepCmd = (CommandResponse)(request.getSession().getAttribute(CommonKeys.FEE_SESSION_NAME));
 request.getSession().setAttribute(CommonKeys.FEE_SESSION_NAME,RepCmd);
 request.setAttribute("ResponseFromEJBEvent",RepCmd);
 
String txtServiceCodeList = request.getParameter("txtServiceCodeList");
String [] phoneNo = null;
if(txtServiceCodeList!=null)
	phoneNo=txtServiceCodeList.split(",");


String ProductList = request.getParameter("ProductList");
String CampaignList = request.getParameter("CampaignList");

//T_Service.ServiceID == 3，语音业务，需要选择电话号码
boolean bNeedPhoneNo = false;
String txtGroupBargainDetailNo =(request.getParameter("txtGroupBargainDetailNo") ==null ) ? "" :request.getParameter("txtGroupBargainDetailNo") ;
if (!txtGroupBargainDetailNo.equals("")){
     ProductList ="";
     ArrayList list = Postern.getProductPackageDTOByGroupDetail(txtGroupBargainDetailNo);
     //System.out.println("list.size()=================="+list.size());
     Iterator currentPackageIter=list.iterator();
     //System.out.println("list==============="+list);
     while(currentPackageIter.hasNext()){
           ProductPackageDTO dto =(ProductPackageDTO)currentPackageIter.next();
           //System.out.println("dto.getPackageID()**********"+dto.getPackageID());
           ProductList =ProductList+ dto.getPackageID()+";";     
     }
     if (ProductList !=null) ProductList =ProductList.substring(0,ProductList.length()-1);
}
//System.out.println("CampaignList=============="+CampaignList);
if(CampaignList != null && CampaignList.length()>0 && ';'==CampaignList.charAt(CampaignList.length()-1))
	CampaignList = CampaignList.substring(0,CampaignList.length()-1);
String CampaignProList = Postern.getBundle2CampaignPackageColStr(CampaignList);

String allProductList = "";

	allProductList = CampaignProList+ProductList;
//System.out.println("ProductList=============="+ProductList);
//System.out.println("allProductList=============="+allProductList);
if(allProductList != null && allProductList.length()>0 && ';'==allProductList.charAt(allProductList.length()-1))
	allProductList = allProductList.substring(0,allProductList.length()-1);
String strServiceIDs = Postern.getServiceIDByProductPackageIDs(allProductList);
//System.out.println("strServiceIDs=============="+strServiceIDs);
%>



<form name="frmPost" method="post" action="open_confirm.screen" >
	<input type="hidden" name="checkSelect" value="1" >
  <tbl:hiddenparameters pass="txtCsiId/txtCustomerId/txtAddressId/txtBillAddressId/txtAcctID/txtType/txtCsiStatus" />
  <tbl:hiddenparameters pass="txtNewCustomerDtLastmod/txtCsiDtLastmod/txtCsiDtLastMod/txtCustAddrLastmod/txtNewAcctLastmod/txtAcctAddrLastmod" />
  
<%
int n=0;
String itemID="";
if(strServiceIDs != null && !("".equals(strServiceIDs)))
{
	String serviceIDs[] = strServiceIDs.split(";");
	for(int i=0;i<serviceIDs.length;i++)
	{
		if("3".equals(serviceIDs[i]))
			bNeedPhoneNo = true;
	}
	
	if(bNeedPhoneNo)
	{
%>      
	<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<tr>
			<td width="100%" class="import_tit" align="center"><%=title%></td>
		</tr>
	</table>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr>
			<td class="list_bg2" width="17%">
				<div align="right">号码级别</div></td>
			<td class="list_bg1">
				<d:selcmn name="txtGrade" mapName="SET_R_PHONENOGRADE" match="txtGrade" width="23" />
			</td>
		</tr>
		<tr>
			<td class="list_bg2" width="17%"><div align="right">电话号码</div></td>
			<td class="list_bg1">
				<%
					String phone = request.getParameter("phoneNo");
					if(phone==null && phoneNo!=null && phoneNo.length>n)
						phone = phoneNo[n++];
					if(phone==null || "null".equals(phone))
						phone="";
						//查出itemID
					itemID = Postern.getResourcePhoneNoItemIDWithPhoneNo(phone)+"";
				%>
			<input type="text" name="phoneNo" size="25" maxlength="50" value="<%=phone%>" class="text" onChange="javascript:setSelectFalse()" >
			<input name="Submit" type="button" class="button" value="查询" onclick="javascript:phoneNo_Search()"> 支持模糊查询，“_”代表一位，“%”代表多位。</td>
		</tr>
	</table>
	<input type="hidden" name="itemID" value="<tbl:writeparam name="itemID" />">
<%	}
}%>      
<br>
      <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td width="20" ></td>
           <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
           <td height="20">
             <input name="prev" type="button" class="button" onClick="javascript:frm_back()" value="上一步">
           </td>
           <td><img src="img/button2_l.gif" width="11" height="20"></td> 
           <td width="20" >&nbsp;</td> 
           <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
           <td height="20">
             <input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步">
           </td>
           <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>

        </tr>
      </table>
      <tbl:hiddenparameters pass="txtCatvID/txtGroupBargainDetailNo/txtReferBookingSheetID/txtCsiCreateReason/txtCsiType" />
      <tbl:hiddenparameters pass="txtCustType/txtGender/txtName/txtBirthday/txtCardType/txtCardID/txtTelephone/txtMobileNumber/txtEmail" />
      <tbl:hiddenparameters pass="txtContractNo/txtCounty/txtDetailAddress/txtPostcode/txtOpenSourceType/txtOpenSourceID/txtComments/txtLoginID/txtLoginPwd" />
      <tbl:hiddenparameters pass="txtPreferedTime/txtPreferedDate/txtSocialSecCardID/txtNationality/txtFaxNumber" />
      <tbl:hiddenparameters pass="txtBillDetailAddress/txtBillPostcode/txtbillCounty/txtMopID/txtBankAccount/txtBankAccountName/txtAccountType/txtAccountName" />
      <input type="hidden" name="ProductList" value="<%=ProductList%>" >
      <tbl:hiddenparameters pass="CampaignList/OpenFlag/txtIsInstall/txtForceDepostMoney" />
      	
      <tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" />  
      <input type="hidden" name="txtActionType" value="<%=txtActionType%>" />
      <tbl:hiddenparameters pass="txtAgentName/txtAgentTelephone/txtAgentCardType/txtAgentCardId" />
      <tbl:hiddenparameters pass="txtServiceCodeList/oldHasPhoneNo" />
      <tbl:hiddenparameters pass="txtAutoNextOrgID/txtNextOrgID/transferType" />	
      <tbl:hiddenparameters pass="txtContactPerson/txtContactPhone/txtAccount/txtCustomerID/txtID/txtStatus" />
			<input type="hidden" name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" value="<tbl:writeparam name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" />" >   
      	
      <tbl:dynamicservey serveyType="M"  showType="hide" />
      	
      	<input type="hidden" name="func_flag" value="<%=func_flag%>" >
      	<input type="hidden" name="confirm_post" value="<%=confirm_post%>" >
      
      
</form>

<Script language=JavaScript>
<!--
if(<%=!bNeedPhoneNo%>)
{
	var txtPage = "<%=txtPage%>";
	if(txtPage == "bookingModify")
	{
		document.frmPost.action="book_alter_op.do?ActionFlag="+<%=CommonKeys.MODIFY_OF_BOOKING%>+"&needPhoneNo=false";
	}
	if(txtPage == "bookingAddAccountBook")
	{
		document.frmPost.action="booking_addaccount_confirm.screen?needPhoneNo=false";
	}
	if(txtPage == "bookingAddAccountBookModify")
	{
			document.frmPost.action="booking_addacount_update.do?ActionFlag=addAcount&needPhoneNo=false";
	}
	if(txtPage == "agentConfirm")
	{
		document.frmPost.action="book_alter_confirm_op.do?ActionFlag="+<%=CommonKeys.CONFIRM_OF_BOOKING%>+"&needPhoneNo=false";
	}
 document.frmPost.submit();
}

function phoneNo_check()
{
	if(document.frmPost.checkSelect.value != "1")
	{
			alert("电话号码必须选择！");
		return false;
	}
	if(document.frmPost.itemID.value =="")
	{
		document.frmPost.itemID.value = "<%=itemID%>";
	}
	if(document.frmPost.itemID.value =="" || document.frmPost.itemID.value =="0")
	{
		alert("电话号码必须选择！");
		return false;
	}
	return true;
}
function setSelectFalse()
{
	document.frmPost.checkSelect.value = "0";
}

//-->
</Script>