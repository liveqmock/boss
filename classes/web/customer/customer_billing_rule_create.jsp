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
<%@ page import="java.util.LinkedList" %>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO" %> 
<%@ page import="com.dtv.oss.dto.AcctItemTypeDTO" %>
<%@ page package com.dtv.oss.util.Postern" %>

<% 
   String txtCustomerID = request.getParameter("txtCustomerID");
   String txtServiceAccountID = request.getParameter("txtServiceAccountID");
   String isList = request.getParameter("isList");
   
   //ҵ���ʻ�
   Map mapServiceAccount = Postern.getNotCancleServiceAccountMapByCustID(Integer.parseInt(txtCustomerID));
   pageContext.setAttribute("mapServiceAccount",mapServiceAccount);
   
   //Object[] key = mapServiceAccount.keySet().toArray();
   //String txtServiceAccountID = (String)key[0];
   
   if(!"".equals(isList) && isList != null){
   //�ͻ���Ʒ�б�  ���Ի�����
   Map mapCustomerProduct = new LinkedHashMap();
   pageContext.setAttribute("mapCustomerProduct",mapCustomerProduct);	
   }else{	//�û���Ʒ��Ϣ
   Map mapCustomerProduct = Postern.getCustomerProductByServiceAccountID(txtServiceAccountID);
   pageContext.setAttribute("mapCustomerProduct",mapCustomerProduct);
   }
   
   
   //Map mapFeeType = Postern.getAllFeeType();    
   //pageContext.setAttribute("mapFeeType",mapFeeType);
   String systemSymbolName=Postern.getSystemSymbolName();
%>

<Script language=JavaScript>
<!--

function checkServiceAccountID(){
	if (check_Blank(document.frmPost.txtServiceAccountID, true, "ҵ���ʻ�"))
		return false;
	return true;	
}
function checkCustomerProduct(){
	if (check_Blank(document.frmPost.txtPSID, true, "�ͻ���Ʒ"))
		return false;
	return true;	
}
function checkFeeType(){
	if (check_Blank(document.frmPost.txtFeeType, true, "��������"))
		return false;
	return true;	
}
function checkAcctItemTypeID(){
	if (check_Blank(document.frmPost.txtAcctItemTypeID, true, "��Ŀ����"))
		return false;
	return true;	
}
function checkValue(){
	if (check_Blank(document.frmPost.txtValue, true, "����"))
		return false;
	if (!check_Blank(document.frmPost.txtValue, false, "����")){     
		if (!check_Float(document.frmPost.txtValue,true,"����"))
			return false;
	}
	return true;	
}
function checkStatus(){
	if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
		return false;
	return true;	
}
function checkDate(){
	if (check_Blank(document.frmPost.txtStartDate, true, "��ʼ����"))
		return false;
	if (!check_TenDate(document.frmPost.txtStartDate, true, "��ʼ����")){
		return false;
	}
	if (check_Blank(document.frmPost.txtEndDate, true, "��������"))
		return false;
	if (!check_TenDate(document.frmPost.txtEndDate, true, "��������")){
		return false;
	}
	if(!compareDate(document.frmPost.txtStartDate,document.frmPost.txtEndDate,"�������ڱ�����ڵ��ڿ�ʼ����")){
		return false;
	}
	return true;	
}

function create_submit()
{
	if(!checkServiceAccountID()) return false;
	if(!checkCustomerProduct()) return false;
	if(!checkFeeType()) return false;
	if(!checkAcctItemTypeID()) return false;
	if(!checkValue()) return false;
	if(!checkStatus()) return false;
	if(!checkDate()) return false;
	var psId = document.frmPost.txtPSID.value;
	var index = psId.indexOf("��");
	if(index != -1){
		psId = psId.substring(0,psId.indexOf("��"));
	}
	document.frmPost.txtPSID.value=psId;
        document.frmPost.submit();
}

function cancel_submit()
{
        document.frmPost.action="customer_billing_rule_query.do";
        document.frmPost.submit();
}

function ChangeServiceAccountID()
{
        
   document.FrameUS.submit_update_select('customerSelectOption', document.frmPost.txtServiceAccountID.value, 'txtPSID', '');

}

function ChangeProductID()
{
        
   document.FrameUS.submit_update_select('customerProductSelectOption', document.frmPost.txtPSID.value, '', '');

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
                                <font size="2"> ���ڻ�ȡ���ݡ����� </font>
                        </td>
                </tr>
        </table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ͻ���Ʒ���Ի�����---����</td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>  

<form name="frmPost" method="post" action="customer_billing_rule_create.do" >
<table width="810"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<input type="hidden" name="txtActionType" value="create" >
        <input type="hidden" name="txtCustomerID" value="<%=txtCustomerID%>" >
        <input type="hidden" name="func_flag" value="11020" >
         <tr>
                <td class="list_bg2">
                <div align="right">ҵ���ʻ�*</div>
                </td>
                <td class="list_bg1">
                <% if(!"".equals(isList) && isList != null){ %>
                <tbl:select name="txtServiceAccountID" set="mapServiceAccount" match="txtServiceAccountID" 
                                width="23" onchange="ChangeServiceAccountID()" /></td>
                <%}else{%>
                <input name="txtServiceAccountID" value=<%=txtServiceAccountID%> class="textgray" readonly size="25" />
                <%}%>
                <td class="list_bg2">
                <div align="right">�ͻ���Ʒ*</div>
                </td>
                <td class="list_bg1">
                <% if(!"".equals(isList) && isList != null){
                    if("yiwu".equals(systemSymbolName)){
                %>
                    <tbl:select name="txtPSID" set="mapCustomerProduct" match="txtPSID" width="23" onchange="ChangeProductID()"  />
                <%}else{%>
                    <tbl:select name="txtPSID" set="mapCustomerProduct" match="txtPSID" width="23" />
                <%
                    }
                
                }else{
                	String txtPSID = request.getParameter("txtPSID");
                %>
                <input name="txtPSID" value=<%=txtPSID+"��"+Postern.getProductNameByPSID(Integer.parseInt(txtPSID))%> class="textgray" readonly size="25" />
                <!-- <input type="hidden" name="txtPSID" value=<%=txtPSID%> class="textgray" readonly size="25" /> -->
                <%}%>
                </td>
        </tr>
        <%
            
            if("yiwu".equals(systemSymbolName)){
                CustomerProductDTO cpDTO=Postern.getCustomerProductDTOByPSID(WebUtil.StringToInt(request.getParameter("txtPSID")));
                int productID=cpDTO.getProductID();
                String acctType=Postern.getAcctItmeTypeIDByProductID(productID);
                if(acctType==null)acctType="";
                Map acctItemTypeMap =Postern.getAllAcctItemType();
                pageContext.setAttribute("agreement_acctitemtypemap", acctItemTypeMap);
                String feeType="";
                AcctItemTypeDTO acctItemTypeDTO=Postern.getAcctItemTypeDTOByAcctItemTypeID(acctType);
                if(acctItemTypeDTO!=null)
                    feeType=acctItemTypeDTO.getFeeType();
                if(feeType==null)feeType="";
                
        %>
        <tr>
                <input type="hidden" name="txtFeeType" value="<%=feeType%>">
                <input type="hidden" name="txtAcctItemTypeID" value="<%=acctType%>">
                <td class="list_bg2">
                <div align="right">��������*</div>
                </td>
                <td class="list_bg1">
                <input name="txtShowMessageFeeType" value="<d:getcmnname typeName="SET_F_BRFEETYPE" match="<%=feeType%>" />" class="textgray" readonly size="25" />
                <td class="list_bg2">
                <div align="right">��Ŀ����*</div>
                </td>
                <td class="list_bg1">
                <input name="txtShowMessageAcctItemTypeID" value="<d:getcmnname typeName="agreement_acctitemtypemap" match="<%=acctType%>" />" class="textgray" readonly size="25" />
                </td>
        </tr>
        <%}else{%>
        <tr>
                <td class="list_bg2">
                <div align="right">��������*</div>
                </td>
                <td class="list_bg1">
                <d:selcmn name="txtFeeType" mapName="SET_F_BRFEETYPE"  match="txtFeeType" onchange="ChangeFeeType()" defaultFlag ="true" showAllFlag="false" judgeGradeFlag="true" width="23" /></td>
                <td class="list_bg2">
                <div align="right">��Ŀ����*</div>
                </td>
                <td class="list_bg1">
                <select name="txtAcctItemTypeID" maxlength="10" width="23">
                        <option value='' >-----------------------</option>
                        </select></td>
        </tr>
        <%}%>
        <tr>
                <td class="list_bg2">
                <div align="right">����*</div>
                </td>
                <td class="list_bg1">
                <input name="txtValue" type="text"  maxlength="20" size="25"
                                value=""></td>
                <td class="list_bg2">
                <div align="right">״̬*</div>
                </td>
                <td class="list_bg1">
                	<d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="sa:status" width="23" /></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">��Ч��*</div>
                </td>
                <td class="list_bg1" colspan='3'>
                <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartDate)" onblur="lostFocus(this)" type="text" name="txtStartDate" size="10" maxlength="10" value="<tbl:writeparam name="txtStartDate" />"							class="text">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
		--
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndDate)" onblur="lostFocus(this)" type="text" name="txtEndDate" size="10" maxlength="10" value="<tbl:writeparam name="txtEndDate" />"							class="text">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
                </td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">��ע</div>
                </td>
                <td class="list_bg1" colspan='3'>
                <input name="txtComments" type="text"  maxlength="1024" size="83"
                                value=""></td>
        </tr>
     </table>
     <br>

     <table width="98%" border="0" align="center" cellpadding="5"
                                cellspacing="1" class="list_bg">
                                <tr align="center">
                                        <td >
                                        <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                		<pri:authorized name="customer_billing_rule_create.do" >
                                                                <td width="20"></td>
                                                                <td width="11" height="20"><img src="img/button_l.gif"
                                                                        width="11" height="20"></td>
                                                                <td>
                                                                
                                                                <input name="Submit" type="button" class="button"
                                                                        value="��&nbsp;��" onclick="javascript:create_submit()">
                                                                
                                                                        </td>
                                                                <td width="22" height="20"><img src="img/button_r.gif"
                                                                        width="22" height="20"></td>
                                                                </pri:authorized>        
                                                        
                                                        &nbsp;&nbsp;

                                                                <td width="20"></td>
                                                                <td width="11" height="20"><img src="img/button2_r.gif"
                                                                        width="22" height="20"></td>
                                                                <td background="img/button_bg.gif"  >
                                                                        	<a href="<bk:backurl property='customer_billing_rule_query.do/customer_product_view.do' />" class="btn12">��&nbsp;��</a>
                                                                        </td>
                                                                <td width="22" height="20"><img src="img/button2_l.gif"
                                                                        width="11" height="20"></td>
                                                </tr>
                                        </table>
                                        </td>
                                </tr>
                        </table>
                        
</form>     