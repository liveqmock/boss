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
     <td class="title">创建新帐户--费用信息确认</td>
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
      <input type="hidden" name="confirm_post"  value="true" >
      <input type="hidden" name="func_flag" value="201">
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
        <td class="list_bg2" align="right" width="17%">帐单寄送地址</td>
        <td width="33%" class="list_bg1" ><font size="2">
           <tbl:writeparam name="txtDetailAddress" />
        </font></td>
        <td valign="middle" class="list_bg2" align="right" >帐单寄送邮编</td>
        <td width="33%" class="list_bg1" ><font size="2">
          <tbl:writeparam name="txtPostcode" />
        </font></td>
     </tr>     
     <tr>
        <td  valign="middle"  class="list_bg2" align="right" width="17%">付费类型</td>
	      <td width="33%" class="list_bg1"><font size="2">
		       <%=strMopName%>
        </font></td>	
	      <td class="list_bg2" align="right" width="17%">所在区</td>
	      <td width="33%" class="list_bg1"><font size="2">
		       <tbl:WriteDistrictInfo property="txtCounty" />  
        </font></td>	
	   </tr>
	   <tr>
	      <td class="list_bg2" align="right">银行帐户</td>
	      <td class="list_bg1"><font size="2">
		      <tbl:writeparam name="txtBankAccount" />
	      </font></td>
	      <td class="list_bg2" align="right" >帐户户名</td>
	      <td class="list_bg1"><font size="2">
		      <tbl:writeparam name="txtBankAccountName" />
	      </font></td>
	   </tr>
	   <tr>
	      <td valign="middle" class="list_bg2" align="right" >帐户类型</td>
	      <td class="list_bg1" ><font size="2">                                  
	        <d:getcmnname typeName="SET_F_ACCOUNTTYPE" match="txtAccountType" />
	      </font></td>
	      <td class="list_bg2" align="right" >帐户户名</td>
	      <td class="list_bg1"><font size="2">
		      <tbl:writeparam name="txtAccountName" />
	      </font></td>
     </tr>
    </table> 
       
     <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_OA%>" acctshowFlag ="false" confirmFlag="true" />
    <BR>
    <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
           <td ><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
           <td width="20" ></td>
           <td><img src="img/button_l.gif" width="11" height="20"></td>
           <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确  认"></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>
        </tr>
      </table>
      <tbl:generatetoken />
</form>

<Script language=JavaScript>
function back_submit(){
	if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>'){	
		document.frmPost.action="account_create_feefill.do";
	}else{
		document.frmPost.action="account_create_payment.screen";
	}
	document.frmPost.confirm_post.value="false";
	document.frmPost.submit();
}	
  
function check_frm(){  
   return true;
}
function frm_submit()
{
	if (check_frm()) {
	  document.frmPost.confirm_post.value="true";
    document.frmPost.action="account_create_op.do";
	  document.frmPost.submit();
  }
}


</Script>