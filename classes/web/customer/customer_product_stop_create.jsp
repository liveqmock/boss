<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="cur" prefix="cust" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.*" %>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<form name="frmPost" method="post" action="customer_product_stop_confirm.screen" >
	<tbl:hiddenparameters pass="txtProductIDs/txtActionType/txtCPIDs/txtEventReason/func_flag" />
	<input type="hidden" name="txtcsiPayOption" >
	<input type="hidden" name="txtDoPost" size="22" value="false">   
	<input type="hidden" name="txtConfirmBackFlag" size="22" value="">   
     <table  border="0" align="center" cellpadding="0" cellspacing="5">
        <tr>
          <td><img src="img/list_tit.gif" width="19" height="19"></td>
          <td class="title">产品暂停--收费信息</td>
        </tr>
      </table>
      <br>
      <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
       </table>
       <br>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
<%
  String customerID = request.getParameter("txtCustomerID");
	String customerName = Postern.getCustomerNameById(WebUtil.StringToInt(customerID));
	if(customerName == null)customerName = "";
	
	String[] cpIDs = request.getParameter("txtCPIDs").split(";");
	List cpIDsList = new ArrayList();
	for(int i=0;i<cpIDs.length;i++) cpIDsList.add(cpIDs[i]);
	
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
	
  
   //String eventReason = request.getParameter("txtEventReason");
   //if (eventReason==null) eventReason="";
	 //Map mapEventReason = Postern.getHashKeyValueByName("SET_V_CUSTSERVICEINTERACTIONSTATUSREASON");
	 //String strEventReasonName= (String)mapEventReason.get(eventReason);
	 //if (strEventReasonName==null) strEventReasonName="";
	 
%>
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
    <td class="list_bg2" align="right">付费帐户</td>
    <td class="list_bg1"><input name="txtAccount" type="text" class="textgray" readonly maxlength="20" size="22" value="<tbl:writeparam name="txtAccount"/>" /></td>
    <%if(request.getParameter("txtEventReasonName") != null && !"".equals(request.getParameter("txtEventReasonName"))){%>
    <td class="list_bg2" align="right">暂停原因</td>
    <td class="list_bg1"><input name="txtEventReasonName" type="text" class="textgray" readonly maxlength="10" size="22" value="<tbl:writeparam name="txtEventReasonName"/>" ></td>
    <%}else{%>
    <td class="list_bg2" align="right"></td>
    <td class="list_bg1" align="right"></td>
    <%}%>
  </tr>
  <!--
  <tr>
    <td class="list_bg2" align="right">产品名称</td>
    <td class="list_bg1"><input name="productName" type="text" class="textgray" readonly maxlength="20" size="22" value=""></td>
    <td class="list_bg2" align="right">产品序列号</td>
    <td class="list_bg1"><input name="txtProductId" type="text" class="textgray" readonly maxlength="10" size="22" value=""></td>
  </tr>
  -->
<%
//}


	 String saId = request.getParameter("txtServiceAccountID");
   if (saId==null) saId="";
%>

<!--
  <tr>
    <td class="list_bg2" align="right">用户名</td>
    <td class="list_bg1"><input type="text" name="txtCustomerName" class="textgray" readonly size="25" readonly value="<cust:curcust property="Name" />" ></td>
    <td class="list_bg2" align="right">业务帐号</td>
    <td class="list_bg1"><input name="txtServiceAccountID" type="text" class="textgray" readonly maxlength="10" size="22" value="<%=saId%>"></td>
  </tr>
    <tr>
    <td class="list_bg2" align="right">暂停原因</td>
    <td class="list_bg1" colspan=3><input name="txtEventReasonName" readonly type="text" class="textgray" maxlength="20" size="22" value=""></td>
  </tr>
  -->

</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
</table>
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
      <tr> 
         <td class="import_tit" align="center" colspan="5"><font size="3">暂停产品列表</font></td>
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
<tbl:CommonFeeAndPaymentInfo includeParameter="Fee"  payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_PS%>"  acctshowFlag ="true"  />
<tbl:generatetoken /> 

<BR>
<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr>
   <td><img src="img/button2_r.gif" width="22" height="20"></td>
   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td>

   <jsp:include page = "/fee/common_control.jsp"/> 
</tr>
</table>   
</form> 

<script language=javascript>
<!--
function back_submit() {
	 document.frmPost.action="customer_product_stop.screen";
	 document.frmPost.submit();
}
function frm_next(csiPayOption){
	 document.frmPost.txtcsiPayOption.value =csiPayOption;
	 if (check_fee()){
	    document.frmPost.action="customer_product_stop_fee.screen";
	 } else{
	 	  //document.frmPost.action="customer_product_stop_op.do";
	 	  //document.frmPost.txtDoPost.value="true";
	 	  document.frmPost.action="customer_product_stop_confirm.screen";
	 	  document.frmPost.txtDoPost.value="false";
	 	  document.frmPost.txtConfirmBackFlag.value="caculatefee";
	 }
	 document.frmPost.submit();
}
function frm_finish(csiPayOption){
	 document.frmPost.txtcsiPayOption.value =csiPayOption;
	 //document.frmPost.action="customer_product_stop_op.do";
	 //document.frmPost.txtDoPost.value="true";
	 document.frmPost.action="customer_product_stop_confirm.screen";
	 document.frmPost.txtDoPost.value="false";
	 document.frmPost.submit();
}

//-->
</SCRIPT>

      