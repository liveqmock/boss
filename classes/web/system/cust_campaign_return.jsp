<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.CustServiceInteractionDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse"%>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>
<%@ page import="com.dtv.oss.dto.CustServiceInteractionDTO" %>

<BR>
<%String sFlag = request.getParameter("txtActionType");
String hiddenValues=request.getParameter("txtHiddenValues");
String CustomerID=request.getParameter("txtCustomerID");
String PayCsiType=request.getParameter("txtPayCsiType");

			String strErrBackUrl = "";
			String strErrButtonValue = "";
			String strBackUrl = "cust_bundle_detail.do";
			String strButtonValue = "返回客户套餐基本信息";
			String strActionUrl = "cust_bundle_detail.do";


			if (sFlag != null) {
				if (sFlag.equalsIgnoreCase("campaignModify")) {
					strErrBackUrl = "cust_campaign_modify.do";
					strErrButtonValue = "返回客户产品促销维护";
					strBackUrl = "cust_campaign_detail.do";
					strButtonValue = "返回客户促销基本信息";
				}else if(sFlag.equalsIgnoreCase("campaignCancle")){
					strErrBackUrl = "cust_campaign_detail.do";
					strErrButtonValue = "返回客户促销基本信息";
					strBackUrl = "cust_campaign_query.do";
					strButtonValue = "返回客户促销活动查询";
				}else if(sFlag.equalsIgnoreCase("modifyCustBundle")){	
					strErrBackUrl = "cust_bundle_modify.do";
					strErrButtonValue = "返回客户套餐维护";
					strBackUrl = "cust_bundle_detail.do";
					strButtonValue = "返回客户套餐基本信息";
				}else if(sFlag.equalsIgnoreCase("BundlePrePayment")){
					
					CustomerDTO custDto = new CustomerDTO();
					if(CustomerID!=null)
						custDto = Postern.getCustomerByID(Integer.parseInt(CustomerID));
					pageContext.setAttribute("customerDTO", (custDto==null) ? new CustomerDTO() : custDto);
					pageContext.setAttribute("SYSTEMSYMBOLNAME", Postern.getSystemSymbolName());
					CustServiceInteractionDTO csiDto = new CustServiceInteractionDTO();
					csiDto.setType(PayCsiType);
					pageContext.setAttribute("oneline",csiDto);
					
					strErrBackUrl = "cust_bundle_prepayment_method.do";
					strErrButtonValue = "返回客户套餐预付";
					strBackUrl = "cust_bundle_detail.do";
					strButtonValue = "返回客户套餐基本信息";
				}else if(sFlag.equalsIgnoreCase("manualGrantCampaign")){	
					strBackUrl = "manual_grant_campaign_screen.do";
					strErrBackUrl = "manual_grant_campaign_screen.do";
					strActionUrl = "manual_grant_campaign_screen.do";
					strButtonValue = "返回手工授予促销活动";
					strErrButtonValue = "返回手工授予促销活动";
				}
			}
			System.out.println("sFlag=================" + sFlag);
			System.out.println("strErrBackUrl=========" + strErrBackUrl);
			System.out.println("strErrButtonValue=====" + strErrButtonValue);
			System.out.println("strBackUrl============" + strBackUrl);
			System.out.println("strButtonValue========" + strButtonValue);
			System.out.println("strActionUrl==========" + strActionUrl);

			%>
<script language="JavaScript" type="text/JavaScript">
<!--
function frmSubmit(){
	var url="";
	url="<bk:backurl property='<%=strBackUrl%>' />";
	if(url!="")
		document.frmPost.action = url;
        document.frmPost.submit();
}
function errorBack(){
	var url="";
	<%if(strErrBackUrl.endsWith("screen")){%>
		url="<%=strErrBackUrl%>";
	<%}else{%>
		url="<bk:backurl property='<%=strErrBackUrl%>' />";
	<%}%>
	if(url!="")
		document.frmPost.action = url;
        document.frmPost.submit();
}
//-->
</script>
<TABLE border="0" align="center" cellspacing="0" cellpadding="0"
	width="820">
	<TR>
		<TD align="center">
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="10">
			<tr>
				<td class="title" align="center" valign="middle"><img
					src="img/list_tit.gif" width="19" height="19">&nbsp;&nbsp;提示信息</td>
			</tr>
		</table>
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>
		<br>
		<lgc:haserror>
			<table width="50%" border="0" cellspacing="10" cellpadding="0">
				<tr align="center">
					<td width="182"><img src="img/icon_error.gif" width="182"
						height="182"></td>
					<td class="ok"><font color="red"><i>操作不成功，错误信息如下:</i></font>
					<br>
					<tbl:errmsg /></td>
				</tr>
			</table>
		</lgc:haserror> 
		<lgc:hasnoterror>
			<table width="50%" border="0" cellspacing="10" cellpadding="0">
				<tr align="center">
					<td width="182"><img src="img/icon_ok.gif" width="182" height="182"></td>
					<td class="ok">操作成功。</td>
				</tr>
			</table>
		</lgc:hasnoterror> <br>
		<br>
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>
		<form name="frmPost" method="post" action="<%=strActionUrl%>">
		<tbl:hiddenparameters pass="<%=hiddenValues%>" />
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="10">
			<tr align="center">
				<td height="37">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<!-- 有错要显示的按钮 -->
						<lgc:haserror>
							<td width="22" height="20"><img src="img/button2_r.gif" width="22"
								height="20"></td>
							<td><input name="Submit22" type="button" class="button"
								onClick="javascript:errorBack()"
								value="<%=strErrButtonValue%>"></td>
							<td width="11" height="20"><img src="img/button2_l.gif" width="11"
								height="20"></td>
						</lgc:haserror>
						<!-- 正常显示的按钮 -->
						<lgc:hasnoterror>
							<td width="20">&nbsp;&nbsp;</td>
						<td width="11" height="20"><img src="img/button_l.gif" width="11"
							height="20"></td>
						<td><input name="Submit22" type="button" class="button"
							onClick="javascript:frmSubmit()"
							value="<%=strButtonValue%>"></td>
						<td width="22" height="20"><img src="img/button_r.gif" width="22"
							height="20"></td>
						
     					<pri:authorized name="service_interaction_view_for_print_billing_config.do" >
     					<td width="20" height="20"></td> 
						<% if(sFlag.equalsIgnoreCase("BundlePrePayment")){	
							CommandResponseImp CmdRep = (CommandResponseImp)request.getAttribute("ResponseFromEJBEvent");  
        					String csiId = "";
        					CustServiceInteractionDTO csiDto = null;
        					if (CmdRep!=null)
        					{
           						try
           						{
               						csiId = String.valueOf(CmdRep.getPayload());
               						csiDto = Postern.getCsiDTOByCSIID(Integer.parseInt(csiId));
           						}
           						catch (Exception ex)
           						{
               						System.out.println("ex======"+ex);     
           						}
           						
           					if(csiDto != null){
						%>
						<td width="20" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		        <td><input name="Submit22" type="button" class="button" onClick="javascript:customer_service_receipt_print('<%=csiDto.getCustomerID()%>','<%=csiDto.getId()%>')" value="发票单据打印"></td>
		        <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
						<% } } }%>
						</pri:authorized>
						</lgc:hasnoterror>
					</tr>
				</table>
				</td>
				<td width="20%">&nbsp;</td>
			</tr>
		</table>
		</form>
</table>



<script language=javascript>

function config_print(csiId,PrintSheetType,SheetSubType,CsiReason,PrintReason)
{
	document.frmPost.target="_blank";
	document.frmPost.action="config_print.do?txtCsiId="+csiId+"&txtPrintSheetType="+PrintSheetType+"&txtSheetSubType="+SheetSubType+"&txtCsiReason="+CsiReason+"&txtPrintReason="+PrintReason;
	document.frmPost.submit();
	document.frmPost.target="_self";
}

function customer_service_receipt_print(customerID,csiID)
{
	 document.frmPost.target="_blank";
	 document.frmPost.action="customer_service_receipt_print_lijiang.do?txtCustomerID="+customerID+"&csiID="+csiID;
	 document.frmPost.submit();
	 document.frmPost.target="_self";
}

</script>