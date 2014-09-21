<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
   <tr>
     <td><img src="img/list_tit.gif" width="19" height="19"></td>
     <td class="title">�������ʻ�--������Ϣ¼��</td>
   </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="" >
      <tbl:hiddenparameters pass="txtCustomerID/txtName/txtDetailAddress/txtPostcode/txtCounty/txtAccountType/txtAccountName" />
      <tbl:hiddenparameters pass="txtMopID/txtBankAccountName/txtBankAccount/txtStatus" />
      <tbl:hiddenparameters pass="forwardFlag" />
      <input type="hidden" name="txtcsiPayOption" >
      <input type="hidden" name="confirm_post"  value="false" >
			<input type="hidden" name="txtConfirmBackFlag" value="caculatefee">      
<%
    
    
    //���ѷ�ʽ
    Map mapBankMop=Postern.getAllMethodOfPayments();
    MethodOfPaymentDTO dtoMOP = (MethodOfPaymentDTO)mapBankMop.get(request.getParameter("txtMopID"));
    String strMopName = null;
    if (dtoMOP!=null) strMopName=dtoMOP.getName();
    if (strMopName==null) strMopName="";
    
%>
    <table width="810"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
       <tr>
          <td valign="middle" class="list_bg2" align="right" width="17%" >�ͻ����</td>
	        <td width="33%" class="list_bg1"><font size="2">
	           <tbl:writeparam name="txtCustomerID" />
	        </font></td>	
	        <td valign="middle" class="list_bg2" align="right" width="17%" >�ͻ�����</td>
	        <td width="33%" class="list_bg1" ><font size="2">
	           <tbl:writeparam name="txtName" />
          </font></td>			
       </tr>
       <tr>
	      <td valign="middle" class="list_bg2" align="right" >�ʻ�����</td>
	      <td class="list_bg1" ><font size="2">                                  
	        <d:getcmnname typeName="SET_F_ACCOUNTTYPE" match="txtAccountType" />
	      </font></td>
	      <td class="list_bg2" align="right">�ʻ���</td>
	      <td class="list_bg1"><font size="2">
		      <tbl:writeparam name="txtAccountName" />
	      </font></td>
     </tr>
       <tr> 
       	<td  valign="middle"  class="list_bg2" align="right" width="17%">��������</td>
	        <td width="33%" class="list_bg1"><font size="2">
		        <%=strMopName%>
          </font></td>
         <td class="list_bg2" align="right">�����ʺ�</td>
	       <td class="list_bg1"><font size="2">
		       <tbl:writeparam name="txtBankAccount" />
	       </font></td>
	      </tr>
       	<tr>
       		<td class="list_bg2" align="right">�����ʻ���</td>
	       <td class="list_bg1"><font size="2">
		      <tbl:writeparam name="txtBankAccountName" />
	       </font></td>
	       <td class="list_bg2" align="right" width="17%">�ʵ�������</td>
	       <td width="33%" class="list_bg1"><font size="2">
	     	 <tbl:WriteDistrictInfo property="txtCounty" />
          </font></td>	
	     </tr>
       	<tr>
          <td class="list_bg2" align="right" width="17%">�ʵ����͵�ַ</td>
          <td width="33%" class="list_bg1" ><font size="2">
           <tbl:writeparam name="txtDetailAddress" />
          </font></td>
          <td valign="middle" class="list_bg2" align="right" width="17%">�ʵ������ʱ�</td>
          <td width="33%" class="list_bg1" ><font size="2">
          <tbl:writeparam name="txtPostcode" />
          </font></td>
       </tr>     

   </table>    
     
   <tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_OA%>" acctshowFlag ="false" />

   <BR>
   <table align="center" border="0" cellspacing="0" cellpadding="0">
       <tr> 
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
          <td ><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="��һ��"></td>
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
          <td width="20" ></td>
          <!--
          <td><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="next" type="button" class="button" onClick="javascript:frm_next()" value="��һ��"></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>
          -->
          <jsp:include page = "/fee/common_control.jsp"/>
       </tr>
    </table>
    <tbl:generatetoken />
</form>

<Script language=JavaScript>
function back_submit()
{
	document.frmPost.action="account_create.do";
	document.frmPost.submit();
}
  
//function check_frm(){  
//   if (check_fee()) return true;
//  return false;
//}
function frm_next(csiPayOption)
{
	document.frmPost.txtcsiPayOption.value =csiPayOption;
	if (check_fee()) {
		//�з��� ����֧������
		//����Ժ󵱷���Ϊ0��ʱ��ֱ���ύ����ô��Ҫ������� document.frmPost.confirm_post.value="true";
	   //������üӣ����ﲻ�ܼӸô��룡
     document.frmPost.action="account_create_payment.screen";
	   document.frmPost.submit();
  }
  else
  	//����Ϊ0 ֱ���ύ
  {
     document.frmPost.action="account_create_confirm.do";
	   document.frmPost.submit();
   		
  }
}
function frm_finish()
{
	document.frmPost.action="account_create_confirm.do";
	document.frmPost.submit();
}


</Script>