<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>

<Script language=javascript>
<!--  
function check_form(){

	if (check_Blank(document.frmPost.txtProductID, true, "产品ID"))
		return false;
		
	if (check_Blank(document.frmPost.txtProductName, true, "产品名称"))
		return false;
	if (check_Blank(document.frmPost.txtProductClass, true, "产品类型"))
		return false;
	if (check_Blank(document.frmPost.txtDateFrom, true, "有效期起始"))
		return false;
	if (check_Blank(document.frmPost.txtDateTo, true, "有效期截止"))
		return false;
	if (check_Blank(document.frmPost.txtNewSAFlag, true, "新建业务帐户标志"))
		return false;
	if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;	
	
       	if (document.frmPost.txtDateFrom.value != ''){
		if (!check_TenDate(document.frmPost.txtDateFrom, true, "开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtDateTo.value != ''){
		if (!check_TenDate(document.frmPost.txtDateTo, true, "结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtDateFrom,document.frmPost.txtDateTo,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	
	
	return true;
}

function form_submit(){
        if (check_form()){
        	document.frmPost.txtActionType.value="MODIFY";
        	document.frmPost.func_flag.value="102";
        	document.frmPost.submit();
        }
}
function back_submit(){
	document.frmBack.action="<bk:backurl property='product_manage_query.do' />";
	document.frmBack.submit();;
}

function property_submit(){
	document.frmPost.action="product_property_query.do";
	document.frmPost.submit();
}
function relation_submit(){
	document.frmPost.action="menu_product_relation_query.do";
	document.frmPost.submit();
}
function service_submit(){
	document.frmPost.action="product_service_select.screen";
	document.frmPost.submit();
}
function delete_submit(){
	if(confirm("确实要删除该产品吗？")){
		document.frmPost.txtActionType.value="DELETE";
		document.frmPost.func_flag.value="106";
		document.frmPost.submit();
	}
}
function device_submit(){
	document.frmPost.action="config_product_to_device.do";
	document.frmPost.submit();
}

//-->
</SCRIPT>
<form name="frmBack" method="post">
</form>

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">产品基本信息管理-查看</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<br>



<form name="frmPost" method="post" action="product_manage_op_modify.do">
    <input type="hidden" name="txtActionType" value="">
    <input type="hidden" name="func_flag" value="">
    
    <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true">
    <%
    ProductDTO dto=(ProductDTO)pageContext.getAttribute("oneline");
    String pClass=dto.getProductClass();
    %>
    <input type="hidden" name="txtDtCreate" value="<tbl:write name="oneline" property="dtCreate" />">
    <input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="dtLastmod" />">
    
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
    	
    	<tr>
            <td class="list_bg2"><div align="right">产品ID*</div></td>
            <td class="list_bg1"><input type="text" name="txtProductID" size="25"  value="<tbl:write name="oneline" property="productID" />" class="textgray" readonly ></td>
            <td class="list_bg2"><div align="right">产品类型*</div></td>
            <td class="list_bg1"><d:selcmn name="txtProductClass" mapName="SET_P_PRODUCTCLASS" match="oneline:productClass" width="23" /></td>
        </tr>
        
        <tr>
            <td class="list_bg2"><div align="right">产品名称*</div></td>
            <td class="list_bg1"><input type="text" name="txtProductName" size="25"  value="<tbl:write name="oneline" property="productName" />" class="text"></td>
            <td class="list_bg2"><div align="right">新建业务帐户标志*</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewSAFlag" mapName="SET_G_YESNOFLAG" match="oneline:newsaFlag" width="23" /></td>
        </tr>
        
        <tr>
            <td class="list_bg2"><div align="right">有效期起始*</div></td>
            <td class="list_bg1"><input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25" value="<tbl:writedate name="oneline" property="dateFrom" />" class="text">	      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
            <td class="list_bg2"><div align="right">有效期截止*</div></td>
            <td class="list_bg1"><input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writedate name="oneline" property="dateTo" />" class="text">	      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
        </tr>
        
        <tr>
            <td class="list_bg2"><div align="right">状态*</div></td>
            <td class="list_bg1"><d:selcmn name="txtStatus" mapName="SET_P_PRODUCTSTATUS" match="oneline:status" width="23" /></td>
            <td class="list_bg2"></td>
            <td class="list_bg1"></td>            
        </tr>
        
        <tr>
            <td class="list_bg2"><div align="right">产品描述</div></td>
            <td class="list_bg1" colspan="3"><textarea name="txtDescription"  cols="80" rows="4"><tbl:write name="oneline" property="description" /></textarea></td>
        </tr> 
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			 
			<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="返 回" onclick="javascript:back_submit()"></td>
			<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>

      
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="修 改" onclick="javascript:form_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<!--
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="删 除" onclick="javascript:delete_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			-->
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="产品属性" onclick="javascript:property_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="产品关系" onclick="javascript:relation_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                        <td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="产品业务关系" onclick="javascript:service_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>

			<%if(pClass!=null&&CommonKeys.PRODUCTCLASS_H.equals(pClass)){%>
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="设备型号" onclick="javascript:device_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<%}%>
		  </tr>
	   </table>
	   </td>
	</tr>
    </table>    
    
    </lgc:bloop>
    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr><td><img src="img/mao.gif" width="1" height="1"></td></tr>
    </table>
    <br>
  
</form>