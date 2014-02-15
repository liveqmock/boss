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
     <td class="title">创建新帐户--费用支付信息</td>
   </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="" >
      <tbl:hiddenparameters pass="txtCustomerID/txtName/txtDetailAddress/txtPostcode/txtCounty/txtAccountType/txtAccountName" />
      <tbl:hiddenparameters pass="txtMopID/txtBankAccountName/txtBankAccount/txtStatus/txtcsiPayOption" />
      <tbl:hiddenparameters pass="forwardFlag" />
      <input type="hidden" name="confirm_post"  value="false" >
<%
    
    
    //付费方式
    Map mapBankMop=Postern.getAllMethodOfPayments();
    MethodOfPaymentDTO dtoMOP = (MethodOfPaymentDTO)mapBankMop.get(request.getParameter("txtMopID"));
    String strMopName = null;
    if (dtoMOP!=null) strMopName=dtoMOP.getName();
    if (strMopName==null) strMopName="";
    
%>
    <table width="810"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
       <tr>
          <td valign="middle" class="list_bg2" align="right" width="17%" >客户编号</td>
	        <td width="33%" class="list_bg1"><font size="2">
	           <tbl:writeparam name="txtCustomerID" />
	        </font></td>	
	        <td valign="middle" class="list_bg2" align="right" width="17%" >客户姓名</td>
	        <td width="33%" class="list_bg1" ><font size="2">
	           <tbl:writeparam name="txtName" />
          </font></td>			
       </tr>
       <tr>
	      <td valign="middle" class="list_bg2" align="right" >帐户类型</td>
	      <td class="list_bg1" ><font size="2">                                  
	        <d:getcmnname typeName="SET_F_ACCOUNTTYPE" match="txtAccountType" />
	      </font></td>
	      <td class="list_bg2" align="right">帐户名</td>
	      <td class="list_bg1"><font size="2">
		      <tbl:writeparam name="txtAccountName" />
	      </font></td>
     </tr>
       <tr> 
       	<td  valign="middle"  class="list_bg2" align="right" width="17%">付费类型</td>
	        <td width="33%" class="list_bg1"><font size="2">
		        <%=strMopName%>
          </font></td>
         <td class="list_bg2" align="right">银行帐号</td>
	       <td class="list_bg1"><font size="2">
		       <tbl:writeparam name="txtBankAccount" />
	       </font></td>
	      </tr>
       	<tr>
       		<td class="list_bg2" align="right">银行帐户名</td>
	       <td class="list_bg1"><font size="2">
		      <tbl:writeparam name="txtBankAccountName" />
	       </font></td>
	       <td class="list_bg2" align="right" width="17%">帐单所在区</td>
	       <td width="33%" class="list_bg1"><font size="2">
	     	 <tbl:WriteDistrictInfo property="txtCounty" />
          </font></td>	
	     </tr>
       	<tr>
          <td class="list_bg2" align="right" width="17%">帐单寄送地址</td>
          <td width="33%" class="list_bg1" ><font size="2">
           <tbl:writeparam name="txtDetailAddress" />
          </font></td>
          <td valign="middle" class="list_bg2" align="right" width="17%">帐单寄送邮编</td>
          <td width="33%" class="list_bg1" ><font size="2">
          <tbl:writeparam name="txtPostcode" />
          </font></td>
       </tr>     

   </table>    
     
   <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_OA%>" acctshowFlag ="false" confirmFlag="false" />

   <BR>
   <table align="center" border="0" cellspacing="0" cellpadding="0">
       <tr> 
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
          <td ><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
          <td width="20" ></td>
          <td><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="next" type="button" class="button" onClick="javascript:frm_next()" value="下一步"></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>
       </tr>
    </table>
    <tbl:generatetoken />
</form>

<Script language=JavaScript>
function back_submit()
{
	document.frmPost.action="account_create_feefill.do";
	document.frmPost.submit();
}
  

function frm_next()
{
  document.frmPost.action="account_create_confirm.do";
  document.frmPost.submit();
}


</Script>