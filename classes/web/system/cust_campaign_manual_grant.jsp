<%--Copyright 2003 Digivision, Inc. All rights reserved.--%>
<%--$Id: cust_campaign_manual_grant.jsp,v 1.1.1.1 2010/01/25 09:11:16 yangyong Exp $--%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
				 com.dtv.oss.dto.CustomerDTO,
                 java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<Script language=JavaScript>
<%
	String customerID=request.getParameter("txtCustomerID");
%>
<!--

function check_frm()
{	
	if (check_Blank(document.frmPost.txtManualActionType, true, "授予对象"))
		return false;
	if (check_Blank(document.frmPost.txtDateFrom, true, "促销开始日期"))
		return false;
	if(!compareDateAndToday(getCurrentDate(),document.frmPost.txtDateFrom,"促销开始日期必须在今天以后"))
	   return false;

	var num=0;
	document.frmPost.txtCampaignID.value="";
	if (document.frames.FrameCampaign.listID!=null){
             if (document.frames.FrameCampaign.listID.length > 1) {
	    	for (j=0;j<document.frames.FrameCampaign.listID.length;j++){
			if (document.frames.FrameCampaign.listID[j].checked){
				document.frmPost.txtCampaignID.value=document.frames.FrameCampaign.listID[j].value;
				num++;
			}
	    	}
	    } else{
	        if (document.frames.FrameCampaign.listID.checked){
	           document.frmPost.txtCampaignID.value=document.frames.FrameCampaign.listID.value;
	           num++;
	        }
	    }    
	}
	if(num>1 || document.frmPost.txtCampaignID.value==""){
		alert("您没有选择优惠，或者您选择的优惠数目大于1");
		return false;
	}
	
	if(document.frmPost.txtAccountID.value=="" && document.frmPost.txtServiceAccountID.value==""){
		alert("帐户跟业务帐户不能同时为空");
		return false;
	}
	if(document.frmPost.txtAccountID.value!="" && document.frmPost.txtServiceAccountID.value!=""){
		alert("帐户跟业务帐户不能同时选择");
		return false;
	}	
	return true;
}

function frm_submit(){
	if (check_frm())  {   
	   document.frmPost.submit();
	}
}

function changeManualGrant(){
	if(document.frmPost.txtManualActionType.options[document.frmPost.txtManualActionType.selectedIndex].text=="帐户"){
     		document.frmPost.txtServiceAccountID.selectedIndex=0;
     		document.frmPost.txtServiceAccountID.disabled=true;
     		document.frmPost.txtAccountID.disabled=false;
     	}
     	else{
     		document.frmPost.txtAccountID.selectedIndex=0;
     		document.frmPost.txtServiceAccountID.disabled=false;	
     		document.frmPost.txtAccountID.disabled=false;
     }
}
//-->
</Script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">手工授予促销活动</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="manual_grant_campaign.do" >
      <input type="hidden" name="txtCampaignID" value=""> 
      <input type="hidden" name="txtActionType" value="manualGrantCampaign">
      <input type="hidden" name="func_flag" value="668">
         <tbl:hiddenparameters pass="txtCustomerID" />

      <%
      	Map actionTypeMap=new HashMap();
      	actionTypeMap.put("A","帐户");
      	actionTypeMap.put("S","业务帐户");
      	pageContext.setAttribute("ACTIONTYPEMAP",actionTypeMap);
      	
      	pageContext.setAttribute("ACCOUNTMAP",Postern.getAccountIDMapByCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID"))));
      	pageContext.setAttribute("SAMAP",Postern.getNormalServiceAccountMapByCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID"))));
      	      	
      %>
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
         <tr>
           <td class="list_bg2" width="17%" align="right">客户ID</td>
           <td class="list_bg1" width="33%"  align="left"><font size="2">
              <input type="text" name="txtCustID" size="25" maxlength="10" value="<tbl:writeparam name="txtCustomerID" />" class="textgray" readonly >
           </font></td>
           <td class="list_bg2" width="17%" align="right">授予对象</td>
           <td class="list_bg1" width="33%"  align="left"><font size="2">
              <tbl:select name="txtManualActionType" set="ACTIONTYPEMAP" match="txtManualActionType" width="23" defaultValue="S" onchange="changeManualGrant();" />
           </font></td> 
        </tr>
        <tr>
	  <td class="list_bg2" align="right">账户</td>
	  <td class="list_bg1" align="left" ><font size="2">
	      <d:selAccByCustId name="txtAccountID" mapName="self"  match="txtAccountID" width="23" disabled="true"/>
	  </font></td>
	  <td class="list_bg2" align="right">业务账户ID</td>
	  <td class="list_bg1" align="left"><font size="2">
	      <tbl:select name="txtServiceAccountID" set="SAMAP" match="txtServiceAccountID" width="23" />
	  </font></td>
	</tr>
        <tr> 
          <td class="list_bg2" align="right">促销开始日期*</td>
          <td class="list_bg1" align="left" colspan="3" >
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25"  value="<tbl:writeparam name="txtDateFrom" />" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
          </td>
        </tr>
    <tr>
  	<td class="list_bg2" ><div align="right">备注信息</div></td>
  	<td class="list_bg1" colspan="3" >
     <input type="text" name="txtComments" size="80" value="<tbl:write name="cust" property="comments" />" >
    </td>
  </tr>
        <tr> 
          <td colspan="4" class="list_bg2" align="center"><font size="3">优惠活动信息</font></td>
        </tr>
        <tr> 
          <td class="list_bg1" colspan="4" align="center"><font size="2">
             <iframe SRC="list_campaign.do?txtCampaignType=A&apply=oldProduct"  name="FrameCampaign" width="320" height="260">  	  
  	     </iframe>
          </font></td>
        </tr>
      </table>  
      <BR>  
      
      <table align="center"  border="0" cellspacing="0" cellpadding="0">
          <tr>  
	     <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	     <td><input name="Submit" type="button" class="button" value="授予促销" onclick="javascript:frm_submit()"></td>
	     <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>        
         </tr>
      </table>
      
      <Script language=JavaScript>
      	    changeManualGrant();
      </Script>
</form>