<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>


<script language=javascript>
  function device_swap_prev(){
	   document.frmPost.action ="customer_device_swap_fee.screen"; 
	   document.frmPost.submit();	
  }

  function device_swap_next(){	
     if (check_fee()){   
   	    document.frmPost.action ="customer_device_swap_confirm.screen"; 
	      document.frmPost.submit();	
	   }
  }
</SCRIPT>
<%
String actionType=request.getParameter("txtActionType");
String title=null;
if(actionType.equalsIgnoreCase(CommonKeys.DEVICE_SWAP+"")){
		title="更换设备－费用支付";
		
	}
	else{
		title="设备升级－费用支付";
	}
 %>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
     <td class="title"><strong><%=title %></strong></td>
  </tr>
</table>
 <table width="780"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="" >
   <table width="790"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">      
      <tr> 
       	<td width="100%">
            <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_DS%>" acctshowFlag ="true" confirmFlag="false" checkMoneyFlag="-1" />		 		   
        </td>
      </tr>
    </table>

    <input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >  
    <tbl:hiddenparameters pass="txtServiceAccountID/txtActionType/txtDeviceClass/txtSerialNo/txtDeviceID/txtProdID/txtCustomerID/txtAccountID/txtObjectProduct/txtDeviceFee/txtCsiCreateReason/txtWorkDate" />
    <tbl:hiddenparameters pass="txtPsID/txtOldDeviceID/txtCreateTime/txtProductID/txtNewDeviceSerialNo/sspanList/txtComments" />
    <tbl:hiddenparameters pass="txtNewDeviceSerialNo" /> 	
    
    <BR>   	 	    
    <table align="center"  border="0" cellspacing="0" cellpadding="0">
       <tr>  
         <td><img src="img/button2_r.gif" width="22" height="20"></td>  
         <td background="img/button_bg.gif">
	          <a href="javascript:device_swap_prev()" class="btn12">上一步</a></td>
         <td><img src="img/button2_l.gif" width="13" height="20"></td>
       
         <td width="20" ></td>  
         <td><img src="img/button_l.gif" width="11" height="20"></td>
         <td background="img/button_bg.gif"  ><a href="javascript:device_swap_next()" class="btn12">下一步</a></td>
         <td><img src="img/button_r.gif" width="22" height="20"></td> 
       </tr>
     </table>  
 
</form>





