<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.*,com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*" %>
 

<Script language=javascript>
<!--
  
  
function check_form ()
{
    if (!check_Num(document.frmPost.txtTimeUnitLengthNumber, true, "套餐预付时长"))
	    return false;
     
	return true;
}
 

function modify_submit(){
	   if(check_form())
            document.frmPost.submit();
           
	 
}
 
function back_submit(){
	url="bundle_payment_query.screen?txtCampaignID=<tbl:writeparam name="txtCampaignID"/>";
	document.location.href=url;
}

  

 

//-->
</SCRIPT>
 

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">套餐续费方式-修改</td>
  </tr>
</table>
 
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

<form name="frmPost" method="post" action="modify_bundle_payment.do">
    
    <input type="hidden" name="txtCampaignID" size="20" value="<tbl:writeparam name="txtCampaignID"/>">
    <input type="hidden" name="txtActionType" size="20" value="modify_payment_method">
   
    <input type="hidden" name="func_flag" value="1018">
    
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
    <%
     
   	  int bundleID=WebUtil.StringToInt(request.getParameter("txtBundleID"));
   	  String  flag=request.getParameter("txtRfBillingCycleFlag");
           BundlePaymentMethodDTO cDTO=Postern.getBundlePaymentMethodDTO(bundleID,flag);
           pageContext.setAttribute("oneline",cDTO);
          
    %>
        <tr>
           <td class="list_bg2"><div align="right">套餐ID</div></td>
            <td class="list_bg1">
             <input type="text" name="txtBundleID" size="25" class="textgray" readonly value="<tbl:write name="oneline" property="bundleID"/>">
             </td>
          
          <td class="list_bg2"><div align="right">租费计算周期类型</div></td>
            <td class="list_bg1">
             <input type="text" name="txtRfBillingCycleFlagName" size="25" class="textgray" readonly value="<d:getcmnname typeName="SET_F_RFBILLINGCYCLEFLAG" match="oneline:rfBillingCycleFlag" />">
              <input type="hidden" name="txtRfBillingCycleFlag"  value="<tbl:write name="oneline" property="rfBillingCycleFlag"/>">
             </td>
            
	 </tr> 
        <tr>
            <td class="list_bg2"><div align="right">续费名称</div></td>
              <td class="list_bg1">
             <input type="text" name="txtBundlePaymentName"  maxlength="25" size="25" value="<tbl:write name="oneline" property="bundlePaymentName"/>">
            </td>
            <td class="list_bg2"><div align="right">续费时长单位</div></td>
            <td class="list_bg1">
            <d:selcmn name="txtTimeUnitLengthType" mapName="SET_M_CAMPAIGNTIMELENGTHUNITTYPE" match="oneline:timeUnitLengthType" width="25" /></td>
            
       <input type="hidden" name="txtDtLastmod" size="20" value="<tbl:write name="oneline" property="dtLastmod"/>">
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">套餐预付时长</div></td>
             <td class="list_bg1" colspan="3">
             <input type="text" name="txtTimeUnitLengthNumber" size="25" maxlength="10" value="<tbl:write name="oneline" property="timeUnitLengthNumber"/>">
            
        </tr>
    
         
        
    
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>	
		        
		        
		         <td><img src="img/button2_r.gif" width="22px" height="20px"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
                        <td><img src="img/button2_l.gif" width="11px" height="20px"></td>
            
		  	<td width="20" ></td>		
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="保  存" onclick="javascript:modify_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
			 
			 
		  </tr>
	   </table>
	   </td>
	</tr>
    </table>    
    
    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr><td><img src="img/mao.gif" width="1" height="1"></td></tr>
    </table>

 
  
</form>
