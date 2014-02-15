
<jsp:directive.page import="java.io.BufferedReader" />
<jsp:directive.page import="java.util.Iterator" />
<jsp:directive.page import="java.util.Collection" />
<jsp:directive.page import="java.util.ArrayList" />
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>

<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="java.util.Map" %>
<%@ page import="com.dtv.oss.dto.ContractDTO"%>
<%@ page import="com.dtv.oss.dto.wrap.groupcustomer.GroupCustomerWrap"%>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse"%>

<%
	String filePath = (String) request.getAttribute("txtFilePath");
	if (filePath == null)
		filePath = "";

String contractNo=request.getParameter("txtContractNo");

ContractDTO contractDTO=Postern.getContractDTOByContractNo(contractNo);
String contractName=contractDTO.getName();
if(contractName==null)contractName="";
String contractDesc=contractDTO.getDescription();
if(contractDesc==null)contractDesc="";

StringBuffer packageListDesc=new StringBuffer();
StringBuffer packageList=new StringBuffer();
Map packageMap=Postern.getProductPackageListWithContractNO(contractNo);
Iterator it =packageMap.keySet().iterator();
while(it.hasNext()){
	String key=(String)it.next();
	String value=(String)packageMap.get(key);
	packageList.append(key).append(";");
	//packageListDesc.append(key).append(",");
	 if(!"".equals(packageListDesc.toString()))
	 		packageListDesc.append(";");
	packageListDesc.append(value);
}
String accountID=request.getParameter("txtAccount");
String accountName=Postern.getAcctNameById(Integer.parseInt(accountID));
%>

<script language=javascript>
function confirm_submit(){
  document.frmPost.submit();
}
function back_submit(){
	document.frmPost.action="group_customer_batch_import.do?txtContractNo="
	+"<tbl:writeparam name="txtContractNo"/>&txtAccount=<tbl:writeparam name="txtAccount"/>&txtGroupCustID=<tbl:writeparam name="txtGroupCustID"/>";
	document.frmPost.submit();
}
</script>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">
			子客户批量开户费用收取确认
		</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<form name="frmPost" action="group_customer_batch_import_confirm.do?txtActionType=batch_import_confirm" method="post">
	<tbl:hiddenparameters pass="txtContractNo/txtAccount/txtGroupCustID"/>
	<input type="hidden" name="confirm_post" value="false">
	<input type="hidden" name="txtFilePath" value="<%=filePath%>">
	<input type="hidden" name="txtOpenSourceType" value="<tbl:writeparam name="txtOpenSourceType"/>">

	<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr>
			<td colspan="4" class="import_tit" align="center">
				合同信息
			</td>
		</tr>
		<tr>
			<td class="list_bg2" width=17% align="right">
				合同编号
			</td>
			<td class="list_bg1" width=33% align="left">
				<%=contractNo%>
			</td>
			<td class="list_bg2" width=17% align="right">
				合同名称
			</td>
			<td class="list_bg1" width=33% align="left">
				<%=contractName%>
			</td>
		</tr>
		<tr>
			<td class="list_bg2" align="right">
				合同描述
			</td>
			<td class="list_bg1" align="left" colspan="3">
				<%=contractDesc%>
			</td>
		</tr>
		<tr>
			<td class="list_bg2" align="right">
				产品包
			</td>
			<td class="list_bg1" align="left" colspan="3">
				<%=packageListDesc%>
			</td>
		</tr>
		<tr>
			<td class="list_bg2" align="right">
				帐户
			</td>
			<td class="list_bg1" align="left" colspan="3">
				<%=accountName%>
			</td>
		</tr>
	</table>
	<%
		System.out.println("getContentType:" + request.getContentType());
		System.out.println("request.getAttribute:" + request.getAttribute("ResponseFromEJBEvent"));

		CommandResponse cmdResponse = (CommandResponse) request.getAttribute("ResponseFromEJBEvent");
//		session.removeAttribute("ResponseFromEJBEvent");
		if(cmdResponse!=null){
		ArrayList errList = (ArrayList) cmdResponse.getPayload();
		if (errList != null && !errList.isEmpty() && errList.get(0) instanceof GroupCustomerWrap) {
		pageContext.setAttribute("ResponseFromEJBEvent", errList);
	%>
	<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr>
			<td colspan="4" class="import_tit" align="center">
				无效记录
			</td>
		</tr>
		<tr class="list_head">
			<td class="list_head">
				记录编号
			</td>
			<td class="list_head">
				客户名称
			</td>
			<td class="list_head">
				错误描述
			</td>
		</tr>
		<lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline">
			<%
			
						GroupCustomerWrap wrap = (GroupCustomerWrap) pageContext.getAttribute("oneline");
						String custName=wrap.getNewCustomerInfoDTO().getName();
			%>
			<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
				<td align="center" width="10%">
					<tbl:write name="oneline" property="No" />
				</td>
				<td align="center" width="15%">
					<%=custName %>
				</td>
				<td align="left">
					<tbl:write name="oneline" property="errString" />
				</td>
			</tbl:printcsstr>
		</lgc:bloop>
  <tr>
    <td colspan="10" class="list_foot"></td>
  </tr>
</table>			
		<%
	}else{
		%>
		<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
			<tr>
				<td>
					<tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_GS%>" acctshowFlag ="false"  confirmFlag="false" />
				</td>
			</tr>
		</table>
		<%}}%>

		<br>
		<table align="center" border="0" cellspacing="0" cellpadding="0" height="20">
			<tr height="20">
				<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
				<td height="20"><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
				<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
				<td width="20"></td>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td height="20"><input name="next" type="button" class="button" onClick="javascript:confirm_submit()" value="确　认"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			</tr>
		</table>

		</form>