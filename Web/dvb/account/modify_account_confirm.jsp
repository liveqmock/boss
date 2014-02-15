<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="osstags" prefix="o" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.Map,
                 java.util.HashMap,
                 java.util.Iterator,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>

<SCRIPT language="JAVASCRIPT">
function lastpage()
{
            document.frmPost.action="modify_account_view.do";
        	document.frmPost.submit();
}
function update(){

            document.frmPost.action="update_account_confirm.do";
            document.frmPost.submit();
}
</SCRIPT>
<table width="100%" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top">
    <table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">查看帐户资料</td>
      </tr>
    </table>
      <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
         <br>

<form name="frmPost" method="post" action="">

 
<%!
private ThreadLocal local = new ThreadLocal();

private String gets(String pa, int index)
{
	HttpServletRequest request = (HttpServletRequest) local.get();

	String[] rets = request.getParameterValues(pa);
	String ret="";
	try{ ret=rets[index];}catch(Exception e){}
	return ret;
}
%>
<%
	local.set(request);
	String rets = (request.getParameter("txtAccountSize")==null) ? "0" :request.getParameter("txtAccountSize");
	int accountsize=Integer.parseInt(rets);
	//付费方式
	String strMopName ="";
        Map mapBankMop=Postern.getAllMethodOfPayments();
	
	for (int i=0; i<accountsize; i++){
%>
	<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="fulltable" >      
           <tr>
              <td class="list_bg2" align="right" width="17%">帐号</td>
              <td class="list_bg1" width="33%"><%=gets("txtAccountID",i)%>
		  <input type="hidden" name="txtAccountID"  value="<%=gets("txtAccountID",i)%>" >
              </td>
	      <td class="list_bg2" align="right" width="17%">帐户类型</td>
	      <td class="list_bg1" width="33%">
	          <%=gets("txtAccountTypeShow",i)%>
	           <input type="hidden" name="txtAccountTypeShow"  value="<%=gets("txtAccountTypeShow",i)%>" >
	      </td>
	    </tr>
	    <tr>
	      <td class="list_bg2" align="right" width="17%">创建时间</td>
	      <td class="list_bg1">
	          <%=gets("txtCreateTime",i)%>
	          <input type="hidden" name="txtCreateTime"  value="<%=gets("txtCreateTime",i)%>" >
	      </td>
	      <td class="list_bg2" align="right" width="17%">帐户状态</td>
	      <td class="list_bg1" width="33%">
	          <%=gets("txtStatusShow",i)%>
	          <input type="hidden" name="txtStatusShow"  value="<%=gets("txtStatusShow",i)%>" >
	       </td>
	   </tr>
	  <tr>
              <td class="list_bg2" align="right" width="17%">预存现金</td>
	      <td class="list_bg1" width="33%">
	          <%=gets("txtCashDeposit",i)%>
	          <input type="hidden" name="txtCashDeposit"  value="<%=gets("txtCashDeposit",i)%>" >
	      </td>
	      <td class="list_bg2" align="right" width="17%">预存虚拟货币</td>
	      <td class="list_bg1" width="33%">
	          <%=gets("txtTokenDeposit",i)%>
	          <input type="hidden" name="txtTokenDeposit"  value="<%=gets("txtTokenDeposit",i)%>" >
	      </td>
	  </tr>
	  <tr>
	      <td class="list_bg2" align="right" width="17%">帐务周期</td>
	      <td class="list_bg1" width="33%">
	          <%=gets("txtBillingCycleName",i)%>
	          <input type="hidden" name="txtBillingCycleName"  value="<%=gets("txtBillingCycleName",i)%>" >
	      </td>
	      <td class="list_bg2" align="right" width="17%">串配状态</td>
	      <td class="list_bg1" width="33%">
	          <%=gets("txtBankAccountStatus",i)%>	
	          <input type="hidden" name="txtBankAccountStatus"  value="<%=gets("txtBankAccountStatus",i)%>" >
	      </td>
	  </tr>
	  <tr>
      	     <td class="list_bg2" align="right" width="17%">帐户名称*</td>
	     <td class="list_bg1" width="33%"><%=gets("txtAccountName",i)%>
		 <input type="hidden" name="txtAccountName"  value="<%=gets("txtAccountName",i)%>"  >
	     </td>
      	     <td class="list_bg2" align="right" width="17%">付费类型*</td>
	     <td class="list_bg1" width="33%">
	         <%
	           MethodOfPaymentDTO dtoMOP = (MethodOfPaymentDTO)mapBankMop.get(gets("txtMopID",i));
	           if (dtoMOP!=null) strMopName=dtoMOP.getName();
	           if (strMopName==null) strMopName="";
	         %>
	 
	         <%=strMopName%>
		 <input type="hidden" name="txtMopID"  value="<%=gets("txtMopID",i)%>" >
	     </td>
      	  </tr>
      	  <tr>
	     <td class="list_bg2" align="right" width="17%">银行帐号</td>
	     <td class="list_bg1" width="33%">
		  <%=gets("txtBankAccount",i)%>
		  <input type="hidden" name="txtBankAccount"  value="<%=gets("txtBankAccount",i)%>" >
	     </td>
	     <td class="list_bg2" align="right" width="17%">银行帐户名</td>
	     <td class="list_bg1" width="33%">
	         <%=gets("txtBankAccountName",i)%>
	          <input type="hidden" name="txtBankAccountName"  value="<%=gets("txtBankAccountName",i)%>" >	        
	     </td>
      	  </tr>
          <tr>
             <td class="list_bg2" align="right" width="17%">帐单所在区</td>
             <td class="list_bg1" width="33%">
                 <input type="hidden" name="txtbillCounty" value="<%=gets("txtbillCounty",i)%>" >
                 <%
                 if (accountsize >0){
                   String billCounty ="txtbillCounty["+i+"]";
                 %>
                  <tbl:WriteDistrictInfo property="<%=billCounty%>" />
                 <%} else { %>
	          <tbl:WriteDistrictInfo property="txtbillCounty" />   
	         <% } %>       
             </td>
	     <td class="list_bg2" align="right" width="17%">帐单地址*</td>
             <td class="list_bg1" width="33%"><%=gets("txtDetailAddress",i)%>
		  <input type="hidden" name="txtDetailAddress"  value="<%=gets("txtDetailAddress",i)%>"  >
	     </td>
	 </tr> 
	  <tr>
	  	<!-- 因丽江需要去掉邮编
	      <td class="list_bg2" align="right" width="17%">邮编*</td>
	      <td class="list_bg1" width="33%"><%=gets("txtPostcode",i)%>
		     <input type="hidden" name="txtPostcode"  value="<%=gets("txtPostcode",i)%>"  >
	      </td>
	     -->
	      <td class="list_bg2" align="right" width="17%">BBF</td>
	      <td class="list_bg1" width="83%" colspan="3">
	          <%=gets("txtBBF",i)%>
	          <input type="hidden" name="txtBBF"  value="<%=gets("txtBBF",i)%>" >
	      </td>
	  </tr>
       </table>
       <br>
       <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
           <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
       </table>
       <input type="hidden" name="txtAccountType"   value="<%=gets("txtAccountType",i)%>"  >
       <input type="hidden" name="txtAccountDtLastmod"  value="<%=gets("txtAccountDtLastmod",i)%>"   >
       <input type="hidden" name="txtAddressDtLastmod"    value="<%=gets("txtAddressDtLastmod",i)%>"  >	
       <input type="hidden" name="txtAddressID"   value="<%=gets("txtAddressID",i)%>"  >    
<br>
<% 
	}
%>
 <input type="hidden" name="sFlag" value="1107" >
 <tbl:hiddenparameters pass="txtCustomerID/txtCsiID/txtAccountSize" />
 
<table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>      
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
           <td background="img/button_bg.gif"><a href="javascript:lastpage()" class="btn12">上一步</a></td>
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
           <td width="20" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:update()" class="btn12">确　认</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
    </tr>

</table>   

    

 </form>
</td></tr></table>
