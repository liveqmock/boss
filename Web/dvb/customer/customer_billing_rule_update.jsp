<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="java.util.ArrayList,
                 java.util.Iterator,
                 java.util.HashMap" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.CustomerBillingRuleDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO" %> 
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page package com.dtv.oss.util.Postern" %>

<% 
	String txtCBRID = request.getParameter("txtCBRID");
	CustomerBillingRuleDTO dto = Postern.getCustomerBillingRuleID(Integer.parseInt(txtCBRID));
	int txtPsID = dto.getPsID();
	Timestamp txtDtLastmod = dto.getDtLastmod();
	double txtValue = dto.getValue();
	String txtStatus = dto.getStatus();
	String txtFeeType = dto.getFeeType();
	String txtAcctItemTypeID = dto.getAcctItemTypeID();
	String txtAcctItemTypeName = Postern.getAcctItemTypeDTOByAcctItemTypeID(txtAcctItemTypeID).getAcctItemTypeName();
	String txtComments = dto.getComments();
	if(txtComments == null) txtComments="";
	Timestamp txtStartDate = dto.getStartDate();
	Timestamp txtEndDate = dto.getEndDate();
	CustomerProductDTO customerProductDTO = Postern.getCustomerProductDTOByPSID(txtPsID);
        int txtCustomerID = customerProductDTO.getCustomerID();
        int txtServiceAccountID = customerProductDTO.getServiceAccountID();
        int txtProductID = customerProductDTO.getProductID();
        String txtProductName = Postern.getProductNameByProductID(txtProductID);
        
        Map mapAcctItemType = Postern.getAcctitemtype(txtFeeType);
   	pageContext.setAttribute("mapAcctItemType",mapAcctItemType);
   
        
   //业务帐户
   //Map mapServiceAccount = Postern.getNotCancleServiceAccountMapByCustID(Integer.parseInt(txtCustomerID));
   //pageContext.setAttribute("mapServiceAccount",mapServiceAccount);
   
   //客户产品列表
   //Map mapCustomerProduct = Postern.getCustomerProductByServiceAccountID(txtServiceAccountID);
   //pageContext.setAttribute("mapCustomerProduct",mapCustomerProduct);
        
   //Map mapFeeType = Postern.getAllFeeType();    
   //pageContext.setAttribute("mapFeeType",mapFeeType);
   
%>

<Script language=JavaScript>
<!--

function checkServiceAccountID(){
	if (check_Blank(document.frmPost.txtServiceAccountID, true, "业务帐户"))
		return false;
	return true;	
}
function checkCustomerProduct(){
	if (check_Blank(document.frmPost.txtPSID, true, "客户产品"))
		return false;
	return true;	
}
function checkFeeType(){
	if (check_Blank(document.frmPost.txtFeeType, true, "费用类型"))
		return false;
	return true;	
}
function checkAcctItemTypeID(){
	if (check_Blank(document.frmPost.txtAcctItemTypeID, true, "帐目类型"))
		return false;
	return true;	
}
function checkValue(){
	if (check_Blank(document.frmPost.txtValue, true, "费率"))
		return false;
	if (!check_Blank(document.frmPost.txtValue, false, "费率")){     
		if (!check_Float(document.frmPost.txtValue,true,"费率"))
			return false;
	}
	return true;	
}
function checkStatus(){
	if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;
	return true;	
}
function checkDate(){
	if (check_Blank(document.frmPost.txtStartDate, true, "有效期"))
		return false;
	if (!check_TenDate(document.frmPost.txtStartDate, true, "开始日期")){
		return false;
	}
	if (check_Blank(document.frmPost.txtEndDate, true, "有效期"))
		return false;
	if (!check_TenDate(document.frmPost.txtEndDate, true, "结束日期")){
		return false;
	}	
	if(!compareDate(document.frmPost.txtStartDate,document.frmPost.txtEndDate,"结束日期必须大于等于开始日期")){
		return false;
	}
	return true;	
}

function update_submit()
{
	if(!checkServiceAccountID()) return false;
	if(!checkCustomerProduct()) return false;
	if(!checkFeeType()) return false;
	if(!checkAcctItemTypeID()) return false;
	if(!checkValue()) return false;
	if(!checkStatus()) return false;
	if(!checkDate()) return false;
	if (window.confirm('您确认要修改该客户产品个性化费率信息吗?')){
		document.frmPost.submit();
        }
        
}

function cancel_submit()
{
        document.frmPost.action="customer_billing_rule_query.do";
        document.frmPost.submit();
}


function ChangeFeeType()
{
        
   document.FrameUS.submit_update_select('csi_adjust', document.frmPost.txtFeeType.value, 'txtAcctItemTypeID', '');

}


//-->
</Script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:150px; width:250px; height:24px; display:none">
        <table width="100%" border="0" cellspacing="1" cellpadding="3">
                <tr>
                        <td width="100%" align="center">
                                <font size="2"> 正在获取内容。。。 </font>
                        </td>
                </tr>
        </table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户产品个性化费率---修改 </td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>  

<form name="frmPost" method="post" action="customer_billing_rule_update.do" >
<table width="810"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<input type="hidden" name="txtActionType" value="update" >
        <input type="hidden" name="txtCustomerID" value="<%=txtCustomerID%>" >
        <input type="hidden" name="txtPSID" value="<%=txtPsID%>" >
        <input type="hidden" name="txtCBRID" value="<%=txtCBRID%>" >
        <input type="hidden" name="txtDtLastmod" value="<%=txtDtLastmod%>" >
        <input type="hidden" name="func_flag" value="11021" >
         <tr>
                <td class="list_bg2">
                <div align="right">业务帐户</div>
                </td>
                <td class="list_bg1">
                <input name="txtServiceAccountID" value=<%=txtServiceAccountID%> class="textgray" readonly size="25" /></td>
                <td class="list_bg2">
                <div align="right">客户产品</div>
                </td>
                <td class="list_bg1">
                <input name="txtPSIDView" value="<%=txtPsID+"："+txtProductName%>" class="textgray" readonly size="25" /></td>  
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">费用类型*</div>
                </td>
                <td class="list_bg1">
                <d:selcmn name="txtFeeType" mapName="SET_F_BRFEETYPE"  match="<%=txtFeeType%>" onchange="ChangeFeeType()" width="23" defaultFlag ="true" showAllFlag="false" judgeGradeFlag="true" /></td>
                <td class="list_bg2">
                <div align="right">帐目类型*</div>
                </td>
                <td class="list_bg1">
                <tbl:select name="txtAcctItemTypeID" set="mapAcctItemType" match="<%=txtAcctItemTypeID%>" 
                                width="23" /></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">费率*</div>
                </td>
                <td class="list_bg1">
                <input name="txtValue" type="text"  maxlength="20" size="25"
                                value="<%=WebUtil.bigDecimalFormat(txtValue)%>"></td>
                <td class="list_bg2">
                <div align="right">状态*</div>
                </td>
                <td class="list_bg1">
                	<d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="<%=txtStatus%>" width="23" /></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">有效期*</div>
                </td>
                <td class="list_bg1" colspan='3'>
                <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartDate)" onblur="lostFocus(this)" type="text" name="txtStartDate" size="10" maxlength="10" value="<%=WebUtil.TimestampToString(txtStartDate,"yyyy-MM-dd")%>"							class="text">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
		--
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndDate)" onblur="lostFocus(this)" type="text" name="txtEndDate" size="10" maxlength="10" value="<%=WebUtil.TimestampToString(txtEndDate,"yyyy-MM-dd")%>"							class="text">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
                </td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">备注</div>
                </td>
                <td class="list_bg1" colspan='3'>
                <input name="txtComments" type="text"  maxlength="1024" size="83"
                                value="<%=txtComments%>"></td>
        </tr>
     </table>
     <br>

     <table width="98%" border="0" align="center" cellpadding="5"
                                cellspacing="1" class="list_bg">
                                <tr align="center">
                                        <td >
                                        <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                		<pri:authorized name="customer_billing_rule_update.do" >
                                                                <td width="20"></td>
                                                                <td width="11" height="20">
                                                                <img src="img/button_l.gif" width="11" height="20"></td><td>
                                                                <input name="Submit" type="button" class="button" value="修&nbsp;改" onclick="javascript:update_submit()"></td>
                                                                <td width="22" height="20"><img src="img/button_r.gif"width="22" height="20"></td>
                                                                </pri:authorized>
								&nbsp;&nbsp;
                                                                <td width="20"></td>
                                                                <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
                                                                <td background="img/button_bg.gif"><a href="<bk:backurl property='customer_billing_rule_query.do/customer_product_view.do' />" class="btn12">返&nbsp;回</a></td>
                                                                <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
                                                                       
                                                </tr>
                                        </table>
                                        </td>
                                </tr>
                        </table>
                        
</form>     