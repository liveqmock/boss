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
    if (!check_Num(document.frmPost.txtTimeLengthUnitNumber, true, "套餐预付时长"))
	    return false;
     
	return true;
}
 

function modify_submit(){
	   if(check_form())
            document.frmPost.submit();
           
	 
}
 
function back_submit(){
	url="campaign_agmt_campaign_query.screen?txtCampaignID=<tbl:writeparam name="txtCampaignID"/>";
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
    <td class="title">市场活动协议-修改</td>
  </tr>
</table>
 
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

<form name="frmPost" method="post" action="modify_agmet_campaign.do">
    
    <input type="hidden" name="txtCampaignID" size="20" value="<tbl:writeparam name="txtCampaignID"/>">
    <input type="hidden" name="txtActionType" size="20" value="modify_agmt_campaign">
   
    <input type="hidden" name="func_flag" value="1022">
    
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
    <%
     
   	  int bundleID=WebUtil.StringToInt(request.getParameter("txtbundleID"));
   	  String campaignId=request.getParameter("txtCampaignID");
   	   int camID = WebUtil.StringToInt(campaignId);
        Map mapCampaign = Postern.getAllCampaignIDAndName();
        String name =(String) mapCampaign.get(campaignId);
        String name1 =(String) mapCampaign.get(request.getParameter("txtbundleID")); 
   	  CampaignAgmtCampaignDTO agmtCamDto=Postern.getCampaignAgmtCampaignDTO(camID,bundleID);
           pageContext.setAttribute("oneline",agmtCamDto);
          
    %>
        <tr>
        
           <td class="list_bg2" align="right">市场活动</td>
	     <td class="list_bg1">
	     <input type="text" name="txtCampaignName" class="textgray" readonly size="23"  value="<%=name%>" >
	     
	 </td>    
           <td class="list_bg2"><div align="right">目标活动</div></td>
            <td class="list_bg1">
             <input type="text" name="txtTargetBundleName" size="25" class="textgray" readonly value="<%=name1%>">
             </td>
         <input type="hidden" name="txtTargetBundleId" size="20" value="<tbl:write name="oneline" property="targetBundleId"/>">
          
            
	 </tr> 
        <tr>
             
            <td class="list_bg2"><div align="right">时长单位</div></td>
            <td class="list_bg1">
            <d:selcmn name="txtTimeLengthUnitType" mapName="SET_M_CAMPAIGNTIMELENGTHUNITTYPE" match="oneline:timeLengthUnitType" width="25" /></td>
             <td class="list_bg2"><div align="right">预付时长</div></td>
             <td class="list_bg1" colspan="3">
             <input type="text" name="txtTimeLengthUnitNumber" size="25" maxlength="10" value="<tbl:write name="oneline" property="timeLengthUnitNumber"/>">
       <input type="hidden" name="txtDtLastmod" size="20" value="<tbl:write name="oneline" property="dtLastmod"/>">
        
            
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
