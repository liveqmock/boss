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

	if (check_Blank(document.frmPost.txtProductName, true, "产品名称"))
		return false;
	if (check_Blank(document.frmPost.txtProductClass, true, "产品类型"))
		return false;
	if (check_Blank(document.frmPost.txtDateFrom, true, "有效期开始"))
		return false;
	if (check_Blank(document.frmPost.txtDateTo, true, "有效期结束"))
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
        if (check_form()) document.frmPost.submit();
}
function back_submit(){
          document.location.href= "product_manage_query.do?txtFrom=1&txtTo=10";
	 
}


//-->
</SCRIPT>

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">产品基本信息管理-添加</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<br>

<form name="frmBack" method="post">
</form>

<form name="frmPost" method="post" action="product_manage_op.do">
    <input type="hidden" name="txtActionType" value="CREATE">
    <input type="hidden" name="func_flag" value="101">
    
    
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
            <td class="list_bg2"><div align="right">产品名称*</div></td>
            <td class="list_bg1"><input type="text" name="txtProductName" size="25"  value="<tbl:writeparam name="txtProductName"/>" class="text"></td>
            <td class="list_bg2"><div align="right">产品类型*</div></td>
            <td class="list_bg1"><d:selcmn name="txtProductClass" mapName="SET_P_PRODUCTCLASS" match="txtProductClass" width="23" /></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">有效期开始*</div></td>
            <td class="list_bg1"><input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25" value="<tbl:writeparam name="txtDateFrom"/>" class="text">	      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
            <td class="list_bg2"><div align="right">有效期结束*</div></td>
            <td class="list_bg1"><input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writeparam name="txtDateTo"/>" class="text">	      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">新建业务帐户标志*</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewSAFlag" mapName="SET_G_YESNOFLAG" match="txtNewSAFlag" width="23" /></td>
            <td class="list_bg2"><div align="right">状态*</div></td>
            <td class="list_bg1"><d:selcmn name="txtStatus" mapName="SET_P_PRODUCTSTATUS" match="txtStatus" width="23" /></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">产品描述</div></td>
            <td class="list_bg1" colspan="3"><textarea name="txtDescription"  cols="80" rows="4"><tbl:writeparam name="txtDescription"/></textarea></td>
        </tr> 
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		  	<td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="返  回" onclick="javascript:back_submit()"></td>
			 <td><img src="img/button2_l.gif" width="11" height="20"></td>
			
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="确  认" onclick="javascript:form_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	   </table>
	   </td>
	</tr>
    </table>    
    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr><td><img src="img/mao.gif" width="1" height="1"></td></tr>
    </table>
    <br>
  
</form>