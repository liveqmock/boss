<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.*" %>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%
	String customerID = request.getParameter("txtCustomerID");
	String txtDeviceFee = request.getParameter("txtDeviceFee");
	String actionType=request.getParameter("txtActionType");
	String title=null;
	String title2=null;
	if(txtDeviceFee==null ||"".equals(txtDeviceFee))
		txtDeviceFee="0";
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
	Map othersamap=Postern.getOtherServiceAccount(WebUtil.StringToInt(customerID),WebUtil.StringToInt(serviceAccountID));
	pageContext.setAttribute("othersamap", othersamap);
	//System.out.println("_______customerProductStopList="+customerProductStopList);
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
	if(actionType.equalsIgnoreCase(CommonKeys.CUSTOMER_PRODUCT_MOVE+"")){
		title="产品迁移";
		title2="迁移产品列表";
	}
	else{
		title="产品退订";
	    title2="退订产品列表";}
%>

<form name="frmPost" method="post" action="customer_product_stop_confirm.screen" > 
	<tbl:hiddenparameters pass="txtProductIDs/txtCPIDs" />
	<input type="hidden" name="func_flag" value="7002"> 
	<input type="hidden" name="txtActionType" size="22" value="<%=actionType %>">
	

     <table  border="0" align="center" cellpadding="0" cellspacing="5">
        <tr>
          <td><img src="img/list_tit.gif" width="19" height="19"></td>
          <td class="title"><%=title %></td>
        </tr>
      </table>
      <br>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
       </table>
       <br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
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
    <td class="list_bg1"><input name="txtSAName" type="text" class="textgray" readonly maxlength="10" size="22" value="<%=Postern.getServiceNameByServiceAccountID(Integer.parseInt(serviceAccountID))%>"></td>
  </tr>
  <%if(actionType.equalsIgnoreCase(CommonKeys.CUSTOMER_PRODUCT_MOVE+"")) {%>
   <tr>
    <td class="list_bg2" align="right">付费帐户*</td>
    <td class="list_bg1" ><d:selAccByCustId name="txtAccount"
								mapName="self" canDirect="true" match="txtAccount" empty="false"
								width="20" /></td>
								<td class="list_bg2" align="right">目标业务帐户*</td>
    <td class="list_bg1" ><tbl:select name="txtTargetServiceAccountID" set="othersamap" match="txtTargetServiceAccountID" width="23"   defaultIndex="1"/></td>
  </tr>  
  <%}else {%>
   <tr>
    <td class="list_bg2" align="right">付费帐户*</td>
    <td class="list_bg1" colspan="3"><d:selAccByCustId name="txtAccount"
								mapName="self" canDirect="true" match="txtAccount" empty="false"
								width="20" /></td>
  </tr>
    <% }if(strDeviceList != null && !"".equals(strDeviceList)){
  %>
  <tr>
    <td class="list_bg2" width="17%" align="right">退还设备*</td>
    <td class="list_bg1" width="33%">
    	<d:selcmn name="txtIsSendBack" mapName="SET_G_YESNOFLAG"  match="txtIsSendBack"  width="23" />
    </td>
    <td class="list_bg2" width="17%" align="right">设备费*</td>
    <td class="list_bg1" width="33%">
    	<input name="txtDeviceFee" type="text" maxlength="8" size="25" value="<%=txtDeviceFee%>">
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
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
</table>
<table align="center" width="98%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
      <tr> 
         <td class="import_tit" align="center" colspan="5"><font size="3"><%=title2 %></font></td>
      </tr>
</table>

<table width="98%" border="0" align="center" cellpadding="5"
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
 
<table align="center" border="0" cellspacing="0" cellpadding="0">
<BR>
<BR>
<tr>
   <td><img src="img/button2_r.gif" width="22" height="20"></td>
   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="返&nbsp;回"></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td>
   <td width="20" ></td>
<lgc:hasnoterror>   
   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
   <td><input name="next" type="button" class="button" onClick="javascript:frm_finish()" value="下一步"></td>
   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
</lgc:hasnoterror>     
</tr>
</table>   
</form> 

<script language=javascript>
	<!--
	function back_submit(){
		var backurl='';
		<bk:canback url="customer_product_view.do/service_account_query_result_by_sa.do" >
			backurl='<bk:backurl property="customer_product_view.do/service_account_query_result_by_sa.do" />';
		</bk:canback>
		if(!backurl){
			backurl="service_account_query_result_by_sa.do";
		}
		document.frmPost.action=backurl;
		document.frmPost.submit();
	}
	
	function frm_finish(){
		if (check_Blank(document.frmPost.txtAccount, true, "付费帐户"))
		{
	        	return;
	  }
			document.frmPost.action="customer_product_cancel_create.do";
	    if( check_frm())
	    	document.frmPost.submit();
	}
	function check_frm()
{
	<%if(strDeviceList != null && !"".equals(strDeviceList)){
  %>
	var isSendback=document.frmPost.txtIsSendBack;
	if(isSendback !=null)
	{
			if(check_Blank(isSendback, true, "是否退还设备"))
				return false;
	}
   if (isSendback!=null&&isSendback.value=='<%=com.dtv.oss.web.util.CommonKeys.FORCEDDEPOSITFLAG_Y%>')
   {
		if(check_Blank(document.frmPost.txtDeviceFee, true, "设备费")||!check_Float(document.frmPost.txtDeviceFee,true,"设备费")){
			return false;
		}
		//if(document.frmPost.txtDeviceFee.value==0){
		//	alert("无效的金额!");
		//	document.frmPost.txtDeviceFee.focus();
		//	return false;
		//}		
   }else{
   	document.frmPost.txtDeviceFee.value="";
   }
  <%} else if(actionType.equalsIgnoreCase(CommonKeys.CUSTOMER_PRODUCT_MOVE+"")){%>
  if (check_Blank(document.frmPost.txtTargetServiceAccountID, true, "目标业务帐户"))
	    return false;
  <%}%>
   return true;
} 
	-->

</SCRIPT>

      