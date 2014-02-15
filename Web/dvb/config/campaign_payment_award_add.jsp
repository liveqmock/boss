<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
 

<Script language=javascript>
<!--
  
 function check_form(){
    if (check_Blank(document.frmPost.mopID, true, "支付方式"))
        return false;
    if (check_Blank(document.frmPost.value, true, "预存金额"))
        return false;
    if (!check_Money(document.frmPost.value, true, "预存金额"))
            return false;
    return true;
}
   
function add_submit(){
	if(check_form()) 
	document.frmPost.submit();
}
 
function back_submit(){
	url="campaign_payment_award_query.screen?txtCampaignID=<tbl:writeparam name="txtCampaignID"/>";
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
    <td class="title">奖励记录-添加</td>
  </tr>
</table>
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 
<form name="frmPost" method="post" action="campaign_payment_award_create.do">
    
    <input type="hidden" name="txtCampaignID" size="20" value="<tbl:writeparam name="txtCampaignID"/>">
    <input type="hidden" name="txtActionType" size="20" value="create_campaign_payment_award">
    <input type="hidden" name="func_flag" value="1024">
    
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
    
    <%
        pageContext.setAttribute("methodOfPaymentMap",Postern.getMethodOfPaymentMap());
    %>
	<tr> 
	
	    <td class="list_bg2" align="right">支付方式</td>
	     <td class="list_bg1">
	     	<tbl:select name="mopID" set="methodOfPaymentMap" width="23"  />
	     </td>
	     <td class="list_bg2" align="right">预存金额</td>
	     <td class="list_bg1">
	     	<input type="text" name="value" size="23"  value="" >
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
			<td><input name="Submit" type="button" class="button" value="保  存" onclick="javascript:add_submit()"></td>
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
