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

<%
    StringBuffer lstBankMop = Postern.getBankMopBuffer();
   
    pageContext.setAttribute("AllMOPList" , Postern.getOpeningMop());
%>

<SCRIPT language="JAVASCRIPT">
var listBankMop=["9#9"<%=lstBankMop%>];
function IndexOfBankMop(str)
{
    for (var i=0; i < listBankMop.length; i++)
    {
        if (listBankMop[i] == str) return i;      
    }

    return -1;
}

function check_new_form(){
   if (document.frmPost.txtDetailAddress.length >1){
	     for (i=0,j=0;i<document.frmPost.txtDetailAddress.length; i++,j++){
	     	   mopID =document.frmPost.txtMopID[i];
	     	   bankAccount =document.frmPost.txtBankAccount[i];
	     	   bankAccountName =document.frmPost.txtBankAccountName[i];
	     	   detailAddress =document.frmPost.txtDetailAddress[i];
	     	   postcode =document.frmPost.txtPostcode[i];
	     	   accountName =document.frmPost.txtAccountName[i]
	         if (mopID.value==''){
	        	 alert("必须选择付费类型");
	        	 mopID.focus();
	        	 return false;
	         }
 
          if (IndexOfBankMop(mopID.value)>=0){
	            if (trim(bankAccount.value)=='' || trim(bankAccountName.value) == '') {
	                alert("这种付费类型必须输入银行帐户及银行帐户户名!");
	                if (trim(bankAccount.value) ==''){
	                    bankAccount.focus();
	                    return false;
	                }
	                if (trim(bankAccountName.value) ==''){
	                    bankAccountName.focus();
	                    return false;
	                }
	            } 
	        } else {
	            if (trim(bankAccount.value) != '' || trim(bankAccountName.value) != '') {
	                alert("这种付费类型不允许输入银行帐户及帐户户名!");
	                return false;
	            }
	        }        
	         
	        if (check_Blank(detailAddress, true, "帐单地址"))
		          return false;

		    //  if (check_Blank(postcode, true, "邮编"))
		    //      return false;
			      
	        if (check_Blank(accountName, true, "帐户名称"))
		         return false;
		      
		      i=j;
       }
       
	   } else{
	       if (document.frmPost.txtMopID.value==''){
	        	alert("必须选择付费类型");
	        	document.frmPost.txtMopID.focus();
	        	return false;
	       }
	     
	       if (IndexOfBankMop(document.frmPost.txtMopID.value)>=0){
	           if (trim(document.frmPost.txtBankAccount.value)=='' || trim(document.frmPost.txtBankAccountName.value) == ''){
	                alert("这种付费类型必须输入银行帐户及帐户户名!");
	                if (trim(document.frmPost.txtBankAccount.value) ==''){
	                   document.frmPost.txtBankAccount.focus();
	                   return false;
	                }
	                if (trim(document.frmPost.txtBankAccountName.value) ==''){
	                   document.frmPost.txtBankAccountName.focus();
	                   return false;
	                }     
	           }
	       } else{
	           if (trim(document.frmPost.txtBankAccount.value) != '' || trim(document.frmPost.txtBankAccountName.value) != '') {
	                alert("这种付费类型不允许输入银行帐户及帐户户名!");
	                return false;
	            }
	       }        
	       if (check_Blank(document.frmPost.txtDetailAddress, true, "帐单地址"))
		        return false;

		   //  if (check_Blank(document.frmPost.txtPostcode, true, "邮编"))
		   //     return false;
			      
	       if (check_Blank(document.frmPost.txtAccountName, true, "帐户名称"))
		         return false;
	   }
	   return true;
}
function edit_submit(){
     if (check_new_form()){
         document.frmPost.action="modify_account_confirm.do";
       	document.frmPost.submit();
     }
}

</SCRIPT>
<table width="820" align="center" cellpadding="0" cellspacing="0">
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
	private String get_parameter(String pa)
	{
		HttpServletRequest request = (HttpServletRequest) local.get();
		
		String ret = request.getParameter(pa);
		if(ret==null)
			ret = "";
		return ret;
	}

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
	for(int i=0;i<accountsize;i++){
%>
	<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="fulltable" >      
	   <tr>
	      <td class="list_bg2" align="right" width="17%" >帐号</td>
	      <td class="list_bg1" width="33%">
		  <input type="text" name="txtAccountID" size="25"  value="<%=gets("txtAccountID",i)%>" class="textgray" readonly>
	      </td>
	      <td class="list_bg2" align="right" width="17%" >帐户类型</td>
	      <td class="list_bg1" width="33%">
		  <input type="text" name="txtAccountTypeShow" size="25"  value="<%=gets("txtAccountTypeShow",i)%>" class="textgray" readonly   >
	      </td>
	    </tr>
	    <tr>
	      <td class="list_bg2" align="right" width="17%" >创建时间</td>
	      <td class="list_bg1" width="33%">
		  <input type="text" name="txtCreateTime" size="25"  value="<%=gets("txtCreateTime",i)%>"  class="textgray" readonly >
	      </font></td>
	      <td class="list_bg2" align="right" width="17%" >帐户状态</td>
	      <td class="list_bg1" width="33%">
		  <input type="text" name="txtStatusShow" size="25"  value="<%=gets("txtStatusShow",i)%>" class="textgray" readonly   >
	      </td>
	   </tr>
	   <tr>
              <td class="list_bg2" align="right" width="17%" >预存现金</td>
	      <td class="list_bg1" width="33%">
		 <input type="text" name="txtCashDeposit" size="25"  value="<%=gets("txtCashDeposit",i)%>"  class="textgray" readonly >
	      </td>
	      <td class="list_bg2" align="right" width="17%" >预存虚拟货币</td>
	      <td class="list_bg1" width="33%">
		<input type="text" name="txtTokenDeposit" size="25"  value="<%=gets("txtTokenDeposit",i)%>"  class="textgray" readonly >
	      </td>
	  </tr>
	  <tr>
	      <td class="list_bg2" align="right" width="17%" >帐务周期</td>
	      <td class="list_bg1" width="33%">
                 <input type="text" name="txtBillingCycleName" size="25" value="<%=gets("txtBillingCycleName",i)%>" class="textgray" readonly/>
	      </td>
	      <td class="list_bg2" align="right" width="17%" >串配状态</td>
	      <td class="list_bg1" width="33%">
		 <input type="text" name="txtBankAccountStatus" size="25" class="textgray" readonly value="<%=gets("txtBankAccountStatus",i)%>" >
	      </td>
	  </tr>
	  <tr>
      	      <td class="list_bg2" align="right" width="17%" >帐户名称*</td>
	      <td class="list_bg1" width="33%">
		  <input type="text" name="txtAccountName" size="25" maxlength="25" value="<%=gets("txtAccountName",i)%>" class="text" >
	      </td>
      	      <td class="list_bg2" align="right" width="17%" >付费类型*</td>
	      <td class="list_bg1" width="33%">
	      <%
      	        String mopid=gets("txtMopID",i);
      	      %>
		  <tbl:select name="txtMopID" set="AllMOPList"   defaultValue="<%=mopid%>" width="23" />
	      </td>
      	  </tr>
	  <tr>
	      <td class="list_bg2" align="right" width="17%" >银行帐号</td>
	      <td class="list_bg1" width="33%">
		  <input type="text" name="txtBankAccount" size="25" maxlength="25" value="<%=gets("txtBankAccount",i)%>"   class="text">
	      </td>
	      <td class="list_bg2" align="right" width="17%" >银行帐户名</td>
	      <td class="list_bg1" width="33%">
		  <input type="text" name="txtBankAccountName" size="25" maxlength="25" value="<%=gets("txtBankAccountName",i)%>" class="text" >
	       </td>
      	  </tr> 
          <tr>
              <td class="list_bg2" align="right" width="17%" >帐单所在区</td>
              <td class="list_bg1" width="33%">
                <input type="hidden" name="txtbillCounty" value="<%=gets("txtbillCounty",i)%>" >
              <%
                  if (accountsize>1){
                     String billCounty ="txtbillCounty["+i+"]";
              %>
                  <input type="text" name="txtbillCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="<%=billCounty%>" />" class="text">
                  <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all;leaf','txtbillCounty[<%=i%>]','txtbillCountyDesc[<%=i%>]')">
              <%      
                  } else {  
              %>
		  <input type="text" name="txtbillCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtbillCounty" />" class="text">
		  <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all;leaf','txtbillCounty','txtbillCountyDesc')">
 	      <%
 	          }
 	      %>
 	       </td>
	       <td class="list_bg2" align="right" width="17%" >帐单地址*</td>
	       <td class="list_bg1" width="33%">
		  <input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<%=gets("txtDetailAddress",i)%>" class="text" >
	       </td>
	  </tr> 
	  <tr>
	  	 <!-- 因丽江需要去掉邮编
	      <td class="list_bg2" align="right" width="17%" >邮编*</td>
	      <td class="list_bg1" width="33%">
		    <input type="text" name="txtPostcode" size="25" maxlength="6" value="<%=gets("txtPostcode",i)%>"  class="text">
	      </td>
	      -->
	      <td class="list_bg2" align="right" width="17%" >BBF</td>
	      <td class="list_bg1" width="83%" colspan="3">
		    <input type="text" name="txtBBF" size="25"  value="<%=gets("txtBBF",i)%>"  class="textgray" readonly >
	      </td>
	  </tr>
       </table>
       <br>
       <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
       <tr>
         <td><img src="img/mao.gif" width="1" height="1"></td>
       </tr>
       </table>
      <br>
      <input type="hidden" name="txtAccountType"   value="<%=gets("txtAccountType",i)%>"  > 
      <input type="hidden" name="txtAddressID"   value="<%=gets("txtAddressID",i)%>"  >    
      <input type="hidden" name="txtAccountDtLastmod"  value="<%=gets("txtAccountDtLastmod",i)%>"   >
      <input type="hidden" name="txtAddressDtLastmod"    value="<%=gets("txtAddressDtLastmod",i)%>"  >	
    
			
   <% 
      }
   %>

 <tbl:hiddenparameters pass="txtCustomerID/txtCsiID/txtAccountSize" />
  
 
  <table align="center" border="0" cellspacing="0" cellpadding="0">
   <tr>      
      <td><img src="img/button_l.gif" border="0" ></td>
      <td background="img/button_bg.gif"><a href="javascript:edit_submit()" class="btn12">下 一 步</a></td>
      <td><img src="img/button_r.gif" border="0" ></td>
    </tr>
  </table>   
 </form>
  </td>
 </tr>
</table>
