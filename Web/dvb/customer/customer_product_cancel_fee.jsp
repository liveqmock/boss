<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>

<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.*" %>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<% 
	String customerID = request.getParameter("txtCustomerID");
	String customerName = Postern.getCustomerNameById(WebUtil.StringToInt(customerID));
	if(customerName == null)customerName = "";
	
	
	String[] cpIDs = request.getParameter("txtCPIDs").split(";");
	List cpIDsList = new ArrayList();
	for(int i=0;i<cpIDs.length;i++) 
   cpIDsList.add(cpIDs[i]);
	
	
	String serviceAccountID = request.getParameter("txtServiceAccountID");
	List	customerProductDTOCol = (List)Postern.getCustomerProductDTOByServiceAccountID(WebUtil.StringToInt(serviceAccountID));
	List customerProductStopList = new ArrayList();
	if(customerProductDTOCol != null)
	{
		for(int i = 0;i<customerProductDTOCol.size();i++)
		{
			CustomerProductDTO tempDTO = (CustomerProductDTO)customerProductDTOCol.get(i);
			String cpID = tempDTO.getPsID()+"";
			if(cpIDsList.contains(cpID))
				customerProductStopList.add(tempDTO);
		}
	}


String psids=request.getParameter("txtCPIDs");
ArrayList terminalDeviceList=Postern.getTerminalDeviceListByPsid(psids);
String strDeviceList="";
String strDeviceIDList="";
for(int i=0;i<terminalDeviceList.size();i++){
	TerminalDeviceDTO terDto=(TerminalDeviceDTO)terminalDeviceList.get(i);
	strDeviceList+=terDto.getDeviceModel();
	strDeviceList+="("+terDto.getSerialNo()+")";
	strDeviceList+="<br>";
}
if(strDeviceList.endsWith("<br>"))strDeviceList=strDeviceList.substring(0,strDeviceList.length()-4);
System.out.println("strDeviceList:"+strDeviceList);
%>
<Script language=JavaScript>
 
function check_frm()
{
   if (check_fee()) return true;
   return false;
} 

function frm_submit()
{
	if(check_frm())
	  document.frmPost.submit();
}
function back_submit()
{
	document.frmPost.action="customer_product_cancel_create.screen";
	document.frmPost.submit();
}
</Script>
<form name="frmPost" method="post" action="customer_product_cancel_confirm.screen" >
<tbl:hiddenparameters pass="txtIsSendBack/txtProductNames/txtDeviceList/txtDeviceFee/func_flag" />	
<tbl:hiddenparameters pass="txtAccount/txtProductIDs/txtActionType/txtCPIDs/txtProductName" />
	
     <table  border="0" align="center" cellpadding="0" cellspacing="5">
        <tr>
          <td><img src="img/list_tit.gif" width="19" height="19"></td>
          <td class="title">产品退定－收费信息录入</td>
        </tr>
      </table>
      <br>
      <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
       </table>
       <BR>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" align="right">客户证号</td>
    <td class="list_bg1"><input name="txtCustomerID" type="text" class="textgray" readonly maxlength="20" size="22" value="<%=customerID%>" /> </td>
    <td class="list_bg2" align="right">客户姓名</td>
    <td class="list_bg1"><input name="txtCustomerName" type="text" class="textgray" readonly maxlength="10" size="22" value="<%=customerName%>" /> </td>
  </tr>
   <tr>
    <td class="list_bg2" align="right">业务帐户ID</td>
    <td class="list_bg1"><input name="txtServiceAccountID" type="text" class="textgray" readonly maxlength="20" size="22" value="<tbl:writeparam name="txtServiceAccountID"/>" /></td>
    <td class="list_bg2" align="right">业务名称</td>
    <td class="list_bg1"><input name="txtSAName" type="text" class="textgray" readonly maxlength="10" size="22" value="<tbl:writeparam name="txtSAName" />"></td>
  </tr>
   <tr>
   	
    <td class="list_bg2" align="right">付费帐户*</td>
    <td class="list_bg1" colspan="3"><input name="txtAcctName" type="text" class="textgray" readonly maxlength="10" size="22" value="<tbl:writeparam name="txtAcctName" />"></td>
  </tr>
    <%if(strDeviceList != null && !"".equals(strDeviceList)){
  %>
  <tr>
    <td class="list_bg2" width="17%" align="right">退还设备</td>
    <td class="list_bg1" width="33%">
    	<input name="txtIsSendBackDesc" type="text" class="textgray" readonly maxlength="10" size="22" value="<tbl:writeparam name="txtIsSendBackDesc" />">
    </td>
    <td class="list_bg2" width="17%" align="right">设备费</td>
    <td class="list_bg1" width="33%">
    	<input name="txtDeviceFee" type="text" class="textgray" readonly maxlength="8" size="25" value="<tbl:writeparam name="txtDeviceFee" />">
    </td>
  </tr> 
  <tr>
  	<td class="list_bg2" align="right">设备</td>
    <td class="list_bg1" colspan="3">
    	<%=strDeviceList%>
    	<input type="hidden" name="txtDeviceList" size="22" value="<%=strDeviceList%>">
    </td>
  </tr>
  <%}%>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
</table>
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
      <tr> 
         <td class="import_tit" align="center" colspan="5"><font size="3">退订产品列表</font></td>
      </tr>
</table>

<table width="100%" border="0" align="center" cellpadding="5"
				cellspacing="1" class="list_bg">
				<tr class="list_head">
					<td class="list_head" nowrap>PSID</td>
					<td class="list_head" nowrap>产品名称</td>
					<td class="list_head" nowrap>产品包</td>
					<td class="list_head" nowrap>激活时间</td>
				</tr>
				<%
				 for(int i=0;i<customerProductStopList.size();i++)
				 {
				 CustomerProductDTO cpDto = (CustomerProductDTO)customerProductStopList.get(i);
				 pageContext.setAttribute("cp", cpDto);
				 ProductDTO pDto = Postern.getProductDTOByProductID(cpDto.getProductID());
				 String strProdName = "";
				 String strPackName = "";
				 if(pDto != null)
						strProdName = pDto.getProductName();
				 strPackName = Postern.getPackagenameByID(cpDto.getReferPackageID());
				 if(strProdName == null)strProdName = "";
				 if(strPackName == null)strPackName = "";
				%>
				<tbl:printcsstr style1="list_bg1" style2="list_bg2"
						overStyle="list_over">
						<td align="center" nowrap><tbl:write name="cp" property="PsID" /></td>
						<td align="center" nowrap><%=strProdName%></td>
						<td align="center" nowrap><%=strPackName%></td>
						<td align="center" nowrap><tbl:writedate name="cp" property="activateTime" /><br><tbl:writedate name="cp" property="activateTime" onlyTime="true"/></td>
				</tbl:printcsstr>
				<%}%>
 
</table>
<input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >

<tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_CP%>" acctshowFlag ="true" confirmFlag="false" checkMoneyFlag="2" />
 <BR>
<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr> 
  <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
   <td height="20">
	 <input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步">
   </td>
   <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
   <td width="20" ></td>
   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   <td height="20">
	 <input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步">
   </td>
   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
</tr>
</table>    
</form> 


      