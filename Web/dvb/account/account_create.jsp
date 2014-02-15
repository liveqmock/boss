<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>

<%@ page import="java.util.ArrayList,
                 java.util.Iterator,
                 java.util.HashMap" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.dto.wrap.customer.Customer2AddressWrap" %>

<Script language=JavaScript>
<% 
   StringBuffer lstBankMop = Postern.getBankMopBuffer(); 
%>
<!--
var listBankMop=["9#9"<%=lstBankMop%>];

function IndexOfBankMop(str)
{
    for (var i=0; i<listBankMop.length; i++)
    {
        if (listBankMop[i] == str) return i;
    }

    return -1;
}

function check_frm()
{	
	if (check_Blank(document.frmPost.txtDetailAddress, true, "�ʵ����͵�ַ"))
		return false;
		
	if (check_Blank(document.frmPost.txtPostcode, true, "�ʵ������ʱ�"))
		return false;
	 
		
        if (document.frmPost.txtMopID.value=='')
        {
        	alert("����ѡ�񸶷�����");
        	document.frmPost.txtMopID.focus();
        	return false;
        }

        if (IndexOfBankMop(document.frmPost.txtMopID.value)>=0){
           if (trim(document.frmPost.txtBankAccount.value)=='')
           {
               alert("���ָ������ͱ������������ʻ�");
               document.frmPost.txtBankAccount.focus();
               return false;
           }
           if (trim(document.frmPost.txtBankAccountName.value)=='')
           {
               alert("���ָ������ͱ������������ʻ���");
               document.frmPost.txtBankAccountName.focus();
               return false;
           }
        } else {
            if (trim(document.frmPost.txtBankAccount.value) != '' || trim(document.frmPost.txtBankAccountName.value) != '') {
                alert("���ָ������Ͳ��������������ʻ����ʻ�����!");
                return false;
            }
        }
         
        if (check_Blank(document.frmPost.txtAccountType, true, "�ʻ�����"))
	    return false;
	
	if (check_Blank(document.frmPost.txtAccountName, true, "�ʻ���"))
	    return false;
	
	return true;
}

function frm_submit()
{

	if (check_frm()) document.frmPost.submit();

}

//-->
</Script>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�������ʻ�--¼���ʻ���Ϣ</td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>  

<form name="frmPost" method="post" action="account_create_feefill.do" >        
  <%  
    String txtStatus =request.getParameter("txtStatus");
    pageContext.setAttribute("AllMOPList" , Postern.getOpeningMop());
   %>
    <input type="hidden" name="txtStatus" value="<%=txtStatus%>" >
    <input type="hidden" name="confirm_post"  value="false" >
    <tbl:hiddenparameters pass="forwardFlag" />
    <table width="810"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
    <%
       if (!txtStatus.equals(CommonKeys.CUSTOMER_STATUS_NORMAL)){
    %> 
         <tr> 
            <td class="import_tit" width="100%"  align="center"> 
               ���û����������û������ܽ��д��������
            </td>
          </tr>
      <%
          } else {
      %>   
      <tr>
          <td valign="middle" class="list_bg2" align="right" width="17%" >�ͻ����</td>
	        <td width="33%" class="list_bg1"><font size="2">
	           <input type="text" name="txtCustomerID" size="25" value="<tbl:writeparam name="txtCustomerID" />" class="text" readonly >
	        </font></td>	
	        <td valign="middle" class="list_bg2" align="right" width="17%" >�ͻ�����</td>
	        <td width="33%" class="list_bg1" ><font size="2">
	           <input type="text" name="txtName" size="25" value="<tbl:writeparam name="txtName" />" class="text" readonly >
          </font></td>		
       </tr>
      <tr>
	       <td valign="middle" class="list_bg2" align="right" >�ʻ�����*</td>
	       <td class="list_bg1"><font size="2">                                  
	           <d:selcmn name="txtAccountType" mapName="SET_F_ACCOUNTTYPE" match="txtAccountType" width="23" defaultFlag ="true" showAllFlag="false" />
	           	 
	       </font></td>
	       <td valign="middle" class="list_bg2" align="right">�ʻ���*</td>
	       <td class="list_bg1"><font size="2">
             <input type="text" name="txtAccountName" size="25" maxlength="25" value="<tbl:writeparam name="txtName" />" class="text" >
	       </font></td>
	     </tr>
	     <tr>
	     	<td valign="middle" class="list_bg2" align="right" >��������*</td>
	        <td class="list_bg1" ><font size="2">
        		<tbl:select name="txtMopID" set="AllMOPList" match="txtMopID" width="23" />
          </font></td>
         <td valign="middle" class="list_bg2" align="right">�����ʺ�</td>
	       <td class="list_bg1"><font size="2">
             <input type="text" name="txtBankAccount" size="25" maxlength="25" value="<tbl:writeparam name="txtBankAccount" />" class="text" >
	       </font></td>
	      </tr>
	     	<tr>
	     		<td valign="middle" class="list_bg2" align="right">�����ʻ���</td>
	       <td class="list_bg1"><font size="2">
             <input type="text" name="txtBankAccountName" size="25" maxlength="25" value="<tbl:writeparam name="txtBankAccountName" />" class="text" >
	       </font></td>
	       <td valign="middle" class="list_bg2" align="right">�ʵ�������</td>
          <td class="list_bg1">
               <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	           <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
             <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all;leaf','txtCounty','txtCountyDesc')">
           </td>
	     	</tr>
	     	<tr> 
          <td valign="middle" class="list_bg2" align="right">�ʵ����͵�ַ*</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtDetailAddress" />" class="text" >
          </font></td>
          <td valign="middle" class="list_bg2" align="right">�ʵ������ʱ�*</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtPostcode" size="25" maxlength="6" value="<tbl:writeparam name="txtPostcode" />" class="text" >
          </font></td> 
       </tr>

    </table>  
    <BR>     
    <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button_l.gif" width="11" height="20"></td>
           <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="��һ��"></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>
        </tr>
      
    </table>
     <% } %>
</form>