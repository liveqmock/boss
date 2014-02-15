<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<Script language=JavaScript>
<!--
function frm_submit(){
	if (check_Blank(document.frmPost.txtAccountID, true, "付费帐户"))
	{	
  	return false;
  }	
  if(document.frmPost.txtAccountID.value==document.frmPost.oldAccountID.value){
  	alert("新选择的付费帐户和旧的一样,请重新选择");
  	return ;
  }
  document.frmPost.submit();
}

function colse_click(){
	var psid=document.frmPost.txtPsID.value;
	self.opener.window.location="customer_product_view.do?txtPsID="+psid;
	window.close();	
}

//-->
</Script>


<form name="frmPost" method="post" action="customer_product_provide_modify.do" >
    <input type="hidden" name="txtActionType" value="<%=CommonKeys.CUSTOMER_PRODUCT_ACCOUNT_MODIFY%>" />
    <input type="hidden" name="oldAccountID" value="<tbl:writeparam name="txtAccountID"/>" />
    <input type="hidden" name="txtCustomerID" value="1111" />
    <input type="hidden" name="txtServiceAccountID" value="1111" />
    <input type="hidden" name="txtAccount" value="111" />
      		
		<tbl:hiddenparameters pass="txtPsID"/>
    <table  border="0" align="center" cellpadding="0" cellspacing="5">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">修改付费帐户</td>
      </tr>
    </table>
    <br>
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
    </table>
    <br>
 
    <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
       <tr>
	        <td valign="middle" class="list_bg2" align="right" width="34%">付费帐户*</td>
	        <td class="list_bg1" width="66%">
	           <d:selAccByCustId name="txtAccountID"	mapName="self" canDirect="true" match="txtAccountID" empty="false" width="23" />
	        </td>
       </tr>

	  </table>  

		<BR>  
     <table align="center"  border="0" cellspacing="0" cellpadding="0">
      
        <tr>  
          <td width="20" ></td>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
	        <td><input name="aaa" type="button" class="button" onClick="colse_click()" value="关  闭"></td>           
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
          <td width="20" ></td>
          <td><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="bbb" type="button" class="button" onClick="frm_submit()" value="修  改"></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>  
        </tr>
     </table>
</form>      
		 


