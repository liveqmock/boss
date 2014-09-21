<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>

<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.*" %>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<Script language=JavaScript>
  
function check_frm()
{
	return true;
}

function frm_submit()
{
	if (check_frm()) document.frmPost.submit();
}
function back_submit()
{
	document.frmPost.txtDoPost.value="false";
	//document.frmPost.action="customer_product_resume_fee.screen";
	if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>')
		document.frmPost.action="customer_product_resume_create.screen";
         else
                document.frmPost.action="customer_product_resume_fee.screen";
	document.frmPost.submit();
}
</Script>
<form name="frmPost" method="post" action="customer_product_resume_op.do" >     
     <table  border="0" align="center" cellpadding="0" cellspacing="5">
        <tr>
          <td><img src="img/list_tit.gif" width="19" height="19"></td>
          <td class="title">��Ʒ�ָ�--��Ϣȷ��</td>
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
	if(customerProductDTOCol != null){
		 for(int i = 0;i<customerProductDTOCol.size();i++){
			 CustomerProductDTO tempDTO = (CustomerProductDTO)customerProductDTOCol.get(i);
			 String cpID = tempDTO.getPsID()+"";
			 if(cpIDsList.contains(cpID)) customerProductStopList.add(tempDTO);
		 }
	 }
	 
   String eventReason = request.getParameter("txtEventReason");
   if (eventReason==null) eventReason="";
	 Map mapEventReason = Postern.getHashKeyValueByName("SET_V_CUSTSERVICEINTERACTIONSTATUSREASON");
	 String strEventReasonName= (String)mapEventReason.get(eventReason);
	 if (strEventReasonName==null) strEventReasonName="";
%>
	<tr>
    <td class="list_bg2" align="right">�ͻ�֤��</td>
    <td class="list_bg1"><input name="txtCustomerID" type="text" class="textgray" readonly maxlength="20" size="22" value="<%=customerID%>" /> </td>
    <td class="list_bg2" align="right">�ͻ�����</td>
    <td class="list_bg1"><input name="txtCustomerName" type="text" class="textgray" readonly maxlength="10" size="22" value="<%=customerName%>" /> </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">ҵ���ʻ�ID</td>
    <td class="list_bg1"><input name="txtServiceAccountID" type="text" class="textgray" readonly maxlength="20" size="22" value="<tbl:writeparam name="txtServiceAccountID"/>" /></td>
    <td class="list_bg2" align="right">ҵ������</td>
    <td class="list_bg1"><input name="txtSAName" type="text" class="textgray" readonly maxlength="10" size="22" value="<tbl:writeparam name="txtSAName" />"></td>
  </tr>
   <tr>
    <td class="list_bg2" align="right">�����ʻ�</td>
    <td class="list_bg1" colspan="3"><input name="txtAccount" type="text" class="textgray" readonly maxlength="20" size="22" value="<tbl:writeparam name="txtAccount"/>" /></td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
</table>
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
      <tr> 
         <td class="import_tit" align="center" colspan="5"><font size="3">�ָ���Ʒ�б�</font></td>
      </tr>
</table> 

<table width="100%" border="0" align="center" cellpadding="5"
				cellspacing="1" class="list_bg">
				<tr class="list_head">
					<td class="list_head" nowrap>PSID</td>
					<td class="list_head" nowrap>��Ʒ����</td>
					<td class="list_head" nowrap>��Ʒ��</td>
					<td class="list_head" nowrap>��ͣʱ��</td>
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
						<td align="center" nowrap><tbl:writedate name="cp" property="pauseTime" /><br><tbl:writedate name="cp" property="pauseTime" onlyTime="true"/></td>
				</tbl:printcsstr>
				<%}%>
</table>      
<tbl:hiddenparameters pass="txtProductIDs/txtActionType/txtCPIDs/func_flag" />
<input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >
<input type="hidden" name="txtDoPost" size="22" value="TRUE">
<tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_RM%>" acctshowFlag ="true"  confirmFlag="true" />		
<tbl:generatetoken />
<BR>

<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr> 
  <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
   <td height="20">
	 <input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="��һ��">
   </td>
   <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
   <td width="20" ></td>
   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   <td height="20">
	 <input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="ȷ  ��">
   </td>
   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
</tr>
</table>    
</form> 


      