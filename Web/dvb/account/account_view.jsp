<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>
<%  
    int txtBillingCycleTypeShow =(request.getParameter("txtBillingCycleTypeShow")==null || request.getParameter("txtBillingCycleTypeShow").equals("")) ? 0 : Integer.parseInt(request.getParameter("txtBillingCycleTypeShow"));     
    String billingcyclename = Postern.getBillingCycleTypeNameByID(txtBillingCycleTypeShow);
    if (billingcyclename == null) billingcyclename = "";
    String mopID =request.getParameter("txtMopID");
    
    String mopName = Postern.getNameByMopID(WebUtil.StringToInt(mopID));
    
    int txtCustomerID =Integer.parseInt(request.getParameter("txtCustomerID"));
    CustomerDTO customerDto =Postern.getCustomerByID(txtCustomerID);
%>   	     
      <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">      
         <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%">�ʺ�</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtAcctID" /> </td>
	   <td valign="middle" class="list_bg2" align="right" width="17%">�ʻ�����</td>
           <td width="33%" class="list_bg1"><d:getcmnname typeName="SET_F_ACCOUNTTYPE" match="txtAccountTypeShow" /></td>
      	 </tr>
      	 <tr class="list_bg2">
      	   <td class="list_bg2" align="right">�ͻ�����</td>
      	   <td class="list_bg1" ><%=customerDto.getName()%></td>
           <td class="list_bg2" align="right">�ʱ�*</td>
	   <td class="list_bg1" ><tbl:writeparam name="txtPostcode" /> </td>
	</tr>
      	 <tr>
	   <td class="list_bg2" align="right">����ʱ��</td>
	   <td class="list_bg1"><tbl:writeparam name="txtCreateTime" /></td>
           <td class="list_bg2" align="right">�ʻ�״̬*</td>
	   <td class="list_bg1"><d:getcmnname typeName="SET_F_ACCOUNTSTATUS" match="txtStatus" /></td>
	 </tr>
	 <!--
	 <tr>
	   <td class="list_bg2" align="right">�ʻ����</td>
	   <td class="list_bg1"><tbl:writeparam name="txtBalance" /></td>
	   <td class="list_bg2" align="right">BBF</td>
	   <td class="list_bg1"><tbl:writeparam name="txtBBF" /> </td>
	 </tr>
	 -->
	 <tr>
           <td class="list_bg2" align="right">Ԥ���ֽ�</td>
	   <td class="list_bg1"><tbl:writeparam name="txtCashDeposit" /> </td>
	   <td class="list_bg2" align="right">Ԥ���������</td>
	   <td class="list_bg1"><tbl:writeparam name="txtTokenDeposit" /> </td>
         </tr>
	 <tr>
	   <td class="list_bg2" align="right">��������</td>
	   <td class="list_bg1"><%=billingcyclename%> </td>
           <td class="list_bg2" align="right">����״̬</td>
	   <td class="list_bg1"><d:getcmnname typeName="SET_F_ACCOUNTBANKACCOUNTSTATUS" match="txtBankAccountStatus" />
	 </tr>
      	 <tr>
	   <td class="list_bg2" align="right">�����ʻ�</td>
	   <td class="list_bg1"><tbl:writeparam name="txtBankAccount" /> </td>
	   <td class="list_bg2" align="right">��������*</td>
	  
	   <td class="list_bg1"><%=mopName%> </td>
      	 </tr>
	 <tr>
           <td class="list_bg2" align="right">�ʻ�����</td>
	   <td class="list_bg1"><tbl:writeparam name="txtAccountName" /> </td>
           <td class="list_bg2" align="right">�ʵ���ַ*</td>
	   <td class="list_bg1"><tbl:writeparam name="txtDetailAddress" /> </td>
	</tr>
   </table>
  
 <%
    
    //����Ƿ���δ�����ʵ�
    int txtAcctId =(request.getParameter("txtAcctId")==null || request.getParameter("txtAcctId").equals("")) ? 0 : Integer.parseInt(request.getParameter("txtAcctId"));
    int iUnpaidInvoiceAccount = Postern.getUnpaidInvoiceCountByAcctID(txtAcctId);
     
    if (iUnpaidInvoiceAccount>0){
    
%>
  <table align="center" border="0" cellspacing="0" cellpadding="0">	
  	<tr>
  		<td align="right">
  		<font size="3" color="red" >
		���˻���<%=iUnpaidInvoiceAccount%>��δ���ʵ�!
		</font>
		</td>
  	</tr>
  		
  </table>
<%    
            
    }  
%>  
<tbl:hiddenparameters pass="txtAccountTypeShow/txtPostcode/txtCreateTime/txtStatus/txtBalance/txtBBF/txtCashDeposit" />
<tbl:hiddenparameters pass="txtTokenDeposit/txtBankAccountStatus/txtBankAccount/txtAccountName/txtDetailAddress" />


